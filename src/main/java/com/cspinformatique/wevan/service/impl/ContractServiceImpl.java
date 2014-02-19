package com.cspinformatique.wevan.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cspinformatique.commons.util.RestUtil;
import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Option;
import com.cspinformatique.wevan.entity.Contract.Status;
import com.cspinformatique.wevan.entity.Driver;
import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.repository.ContractRepository;
import com.cspinformatique.wevan.service.BranchService;
import com.cspinformatique.wevan.service.ContractService;
import com.cspinformatique.wevan.service.OptionService;
import com.cspinformatique.wevan.service.VehiculeService;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	@Autowired private BranchService branchService;
	@Autowired private OptionService optionService;
	@Autowired private VehiculeService vehiculeService;
	
	@Autowired private ContractRepository contractRepository;
	
	@PostConstruct
	public void init(){
		try{
			this.fetchContracts();
		}catch(Exception ex){
			logger.error("Unable to retreive contracts from we-van.com", ex);
		}
	}
	
	@Override
	public void deleteContract(long id){
		this.contractRepository.delete(id);
	}
	
	@Override
	public Page<Contract> findByBranch(Branch branch, int page, int results){
		return this.contractRepository.findByBranchOrderByCreationDateDesc(
			branch, 
			new PageRequest(page, results, new Sort(Direction.DESC, "id"))
		);
	}
	
	@Override
	public Page<Contract> findByBranchAndStatus(Branch branch, List<Status> status, int page, int results){
		return this.contractRepository.findByBranchAndStatusInOrderByCreationDateDesc(
			branch, 
			status, 
//			new PageRequest(page, results, new Sort(Direction.DESC, "id"))
			new PageRequest(page, results)
		);
	}
	
	@Override
	public Contract findOne(long id){
		return this.contractRepository.findOne(id);
	}
	
	public Contract findLastContractModified(){
		List<Contract> contracts = this.contractRepository.findLastContractModified();
		
		if(contracts.size() > 0){
			return contracts.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public long generateNewContractId(){
		Contract contract = this.findLastContractModified();
		String sysdatePrefix = new SimpleDateFormat("yyMMdd").format(new Date());
		
		long contractId = Long.parseLong(sysdatePrefix + "0001");
		
		if(contract != null && String.valueOf(contract.getId()).startsWith(sysdatePrefix)){
			contractId = contract.getId() + 1;
		}

		return contractId;
	}
	
	@Override
	public void fetchContracts(){
		// Retreiving last contract inserted.
		Contract latestContract = this.findLastContractModified();
		
		long timestamp = 1;
		if(latestContract != null){
			timestamp = latestContract.getEditionDate().getTime() / 1000;
		}
		
		logger.info("Retreiving contracts older than " + timestamp);
		
		Long[] reservationIds = new RestTemplate().exchange(
			"http://www.we-van.com/api/?t=" + timestamp, 
			HttpMethod.GET, 
			new HttpEntity<Long[]>(
				new Long[0],
				RestUtil.createBasicAuthHeader(
					"wevan-api", 
					"7D4gLg"
				) 
			), 
			Long[].class
		).getBody();
		
		logger.info(reservationIds.length + " contracts received.");
		
		com.cspinformatique.wevan.backend.entity.Contract backendContract;
		DateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		for(long reservationId : reservationIds){
			try{
				backendContract = new RestTemplate().exchange(
					"http://www.we-van.com/api/?id=" + reservationId, 
					HttpMethod.GET, 
					new HttpEntity<com.cspinformatique.wevan.backend.entity.Contract>(
						new com.cspinformatique.wevan.backend.entity.Contract(),
						RestUtil.createBasicAuthHeader(
							"wevan-api", 
							"7D4gLg"
						) 
					), 
					com.cspinformatique.wevan.backend.entity.Contract.class 
				).getBody();
				
				logger.info("Received : " + backendContract);
				
				List<Option> options = new ArrayList<Option>();
				
				long contractId = 0l;
				Contract existingContract = this.contractRepository.findByReservationId(reservationId);
				if(existingContract != null){
					contractId = existingContract.getId();
				}else{
					contractId = this.generateNewContractId();
				}
				
				logger.info("Generating contract " + contractId + " from reservation " + reservationId);
				
				//backendContract.getPayment().getAdditionalDrivers().
				if(backendContract.getPayment().getAdditionalDrivers() > 0){
					options.add(
						this.optionService.generateOption(
							contractId,
							true, 
							"Conducteur(s) additionnel(s)", 
							backendContract.getPayment().getAdditionalDriversCost()
						)
					);
				}
				
				if(backendContract.getPayment().isBeddingPack()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Lits",
							backendContract.getPayment().getBeddingPackCost()
						)
					);
				}
				
				if(backendContract.getPayment().isCancelOption()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Assurance annulation",
							0d
						)
					);
				}
				
				if(backendContract.getPayment().isCarRack()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Porte-bagages",
							backendContract.getPayment().getCarRackCost()
						)
					);
				}
				
				if(backendContract.getPayment().isChildSeat()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Siège enfant",
							backendContract.getPayment().getChildSeatCost()
						)
					);
				}
				
				if(backendContract.getPayment().isCleaningPackage()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Nettoyage",
							backendContract.getPayment().getCleaningPackageCost()
						)
					);
				}
				
				if(backendContract.getPayment().isGps()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"GPS",
							backendContract.getPayment().getGpsCost()
						)
					);
				}
				
				if(backendContract.getPayment().isWinterTires()){
					options.add(
						this.optionService.generateOption(
							contractId,
							backendContract.getPayment().isWinterTires(),
							"Pneus d'hiver",
							backendContract.getPayment().getWinterTiresCost()
						)
					);
				}
				
				if(backendContract.getPayment().isYoungDriver()){
					options.add(
						this.optionService.generateOption(
							contractId,
							true,
							"Jeune conducteur",
							backendContract.getPayment().getYoungDriverCost()
						)
					);
				}
		
				Vehicule vehicule = vehiculeService.findByRegistration(backendContract.getEditableInfo().getLicense());
				
				Contract contract =	new Contract(
										contractId, 
										reservationId,
										this.branchService.findOne(backendContract.getAgency()), 
										timeFormat.parse(backendContract.getCreationDate().substring(0, 11) + 
											backendContract.getCreationDate().substring(
												13
											)), 
										timeFormat.parse(backendContract.getCreationDate().substring(0, 11) + 
											backendContract.getEditionDate().substring(
												13
											)),
										Contract.Status.OPEN, 
										new Driver(
											0, 
											backendContract.getUser().getCompany(), 
											backendContract.getUser().getFirstName(), 
											backendContract.getUser().getLastName(), 
											""
										), 
										dateFormat.parse(backendContract.getEditableInfo().getStartDate()),
										dateFormat.parse(backendContract.getEditableInfo().getEndDate()), 
										backendContract.getPayment().getKmPackage(), 
										backendContract.getPayment().getTotalCost(), 
										vehicule, 
										backendContract.getPayment().getPartialDeductible(), 
										backendContract.getPayment().getAlreadyPaid(), 
										new ArrayList<Driver>(), 
										options
									);
				
				contractRepository.save(contract);
			}catch(Exception ex){
				logger.error("Erreur while processing reservation : " + reservationId, ex);
			}			
		}
		
		logger.info("Contract loading completed");
	}

	@Override
	public Contract saveContract(Contract contract) {
		return this.contractRepository.save(contract);
	}
}
