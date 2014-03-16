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
	
	private boolean contractFetchInProgress = false;
	
	@PostConstruct
	public void init(){
		final ContractService contractService = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					contractService.fetchContracts();
				}catch(Exception ex){
					logger.error("Unable to retreive contracts from we-van.com", ex);
				}
			}
		}).start();
	}
	
	@Override
	public void deleteContract(long id){
		this.contractRepository.delete(id);
	}
	
	@Override
	public Page<Contract> findByBranch(Branch branch, PageRequest pageRequest){
		return this.contractRepository.findByBranch(
			branch, 
			pageRequest
		);
	}
	
	@Override
	public Page<Contract> findByBranchAndStatus(Branch branch, List<Status> status, PageRequest pageRequest){
		if(status.contains(Status.OPEN) && status.contains(Status.IN_PROGRESS) && status.contains(Status.COMPLETED)){
			return this.contractRepository.findByBranch(branch, pageRequest);
		}else if(status.contains(Status.OPEN) && status.contains(Status.IN_PROGRESS)){
			return this.contractRepository.findOpenAndInProgressContracts(branch, pageRequest);
		}else if(status.contains(Status.OPEN) && status.contains(Status.COMPLETED)){
			return this.contractRepository.findOpenAndCompletedContracts(branch, pageRequest);
		}else if(status.contains(Status.OPEN)){
			return this.contractRepository.findOpenContracts(branch, pageRequest);
		}else if(status.contains(Status.IN_PROGRESS) && status.contains(Status.COMPLETED)){
			return this.contractRepository.findInProgressAndCompletedContracts(branch, pageRequest);
		}else if(status.contains(Status.IN_PROGRESS)){
			return this.contractRepository.findInProgressContracts(branch, pageRequest);
		}else{
			return this.contractRepository.findCompletedContracts(branch, pageRequest);
		}
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
	public long generateNewContractId(Date contractStartDate){
		Contract contract = this.findLastContractModified();
		String sysdatePrefix = new SimpleDateFormat("yyMMdd").format(contractStartDate);
		
		long contractId = Long.parseLong(sysdatePrefix + "0001");
		
		if(contract != null && String.valueOf(contract.getId()).startsWith(sysdatePrefix)){
			contractId = contract.getId() + 1;
		}

		return contractId;
	}
	
	private double calculateDeductible(List<Option> options){
		for(Option option : options){
			if(option.getLabel().equals(Option.LABEL_PARTIAL_DEDUCTIBLE)){
				return 500;
			}
		}
		
		return 2000;
	}
	
	private List<Option> calculateOptions(long contractId, com.cspinformatique.wevan.backend.entity.Contract backendContract){
		List<Option> options = new ArrayList<Option>();
		
		if(backendContract.getPayment().getPartialDeductible() > 0){
			options.add(
				this.optionService.generateOption(
					contractId, 
					true, 
					Option.LABEL_PARTIAL_DEDUCTIBLE, 
					backendContract.getPayment().getPartialDeductible()
				)
			);
		}
	
		if(backendContract.getPayment().getAdditionalDrivers() > 0){
			options.add(
				this.optionService.generateOption(
					contractId,
					true, 
					Option.LABEL_ADDITIONAL_DRIVER, 
					backendContract.getPayment().getAdditionalDriversCost()
				)
			);
		}
		
		if(backendContract.getPayment().isBeddingPack()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_BEDS,
					backendContract.getPayment().getBeddingPackCost()
				)
			);
		}
		
		if(backendContract.getPayment().isCancelOption()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CANCEL_INSURANCE,
					0d
				)
			);
		}
		
		if(backendContract.getPayment().isCarRack()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CAR_RACK,
					backendContract.getPayment().getCarRackCost()
				)
			);
		}
		
		if(backendContract.getPayment().isChildSeat()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CHILD_SEAT,
					backendContract.getPayment().getChildSeatCost()
				)
			);
		}
		
		if(backendContract.getPayment().isCleaningPackage()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CLEANING,
					backendContract.getPayment().getCleaningPackageCost()
				)
			);
		}
		
		if(backendContract.getPayment().isGps()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_GPS,
					backendContract.getPayment().getGpsCost()
				)
			);
		}
		
		if(backendContract.getPayment().isWinterTires()){
			options.add(
				this.optionService.generateOption(
					contractId,
					backendContract.getPayment().isWinterTires(),
					Option.LABEL_WINTER_TIRES,
					backendContract.getPayment().getWinterTiresCost()
				)
			);
		}
		
		if(backendContract.getPayment().isYoungDriver()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_YOUNG_DRIVER,
					backendContract.getPayment().getYoungDriverCost()
				)
			);
		}
	
	return options;
		
	}
	
	@Override
	public void fetchContracts(){
		if(!contractFetchInProgress){
			try{
				contractFetchInProgress = true;
				
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
						
						long contractId = 0l;
						Contract existingContract = this.contractRepository.findByReservationId(reservationId);
						
						Date contractStartDate =	timeFormat.parse(backendContract.getCreationDate().substring(0, 11) + 
														backendContract.getEditableInfo().getStartDate().substring(13)
													);
						
						if(existingContract != null){
							contractId = existingContract.getId();
						}else{
							contractId = this.generateNewContractId(contractStartDate);
						}
						
						logger.info("Generating contract " + contractId + " from reservation " + reservationId);
						
						// Retreiving the branch linked with the reservation.
						Branch branch = this.branchService.findOne(backendContract.getAgency());
						
						if(branch != null){
							List<Option> options = this.calculateOptions(contractId, backendContract);
		
							Vehicule vehicule = vehiculeService.findByRegistration(backendContract.getEditableInfo().getLicense());
							
							double deductible = this.calculateDeductible(options);
							double deposit = deductible;
							
							Contract contract =	new Contract(
													contractId, 
													reservationId,
													branch, 
													contractStartDate, 
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
													backendContract.getPayment().getAlreadyPaid(), 
													backendContract.getPayment().getTotalCost(), 
													vehicule, 
													deductible, 
													deposit, 
													new ArrayList<Driver>(), 
													options,
													false
												);
							
							contractRepository.save(contract);
						}else{
							logger.error("Reservation " + reservationId + " could not be saved since " + backendContract.getAgency() + " isn't configured into the system.");
						}
					}catch(Exception ex){
						logger.error("Error while processing reservation : " + reservationId, ex);
					}			
				}
				
				logger.info("Contract loading completed");
			}finally{
				contractFetchInProgress = false;
			}
		}else{
			logger.info("Contracts are already being fetch from backend.");
		}
	}

	@Override
	public Contract saveContract(Contract contract) {
		return this.contractRepository.save(contract);
	}
}
