package com.cspinformatique.wevan.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.cspinformatique.wevan.entity.ElixirAudit;
import com.cspinformatique.wevan.entity.Option;
import com.cspinformatique.wevan.entity.Contract.Status;
import com.cspinformatique.wevan.entity.Driver;
import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.repository.ContractRepository;
import com.cspinformatique.wevan.service.BranchService;
import com.cspinformatique.wevan.service.CalendarService;
import com.cspinformatique.wevan.service.ContractService;
import com.cspinformatique.wevan.service.ElixirAuditService;
import com.cspinformatique.wevan.service.OptionService;
import com.cspinformatique.wevan.service.VehiculeService;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	private DateFormat timeFormat;
	private DateFormat dateFormat;
	
	@Autowired private BranchService branchService;
	@Autowired private CalendarService calendarService;
	@Autowired private ElixirAuditService elixirAuditService;
	@Autowired private OptionService optionService;
	@Autowired private VehiculeService vehiculeService;
	
	@Autowired private ContractRepository contractRepository;
	
	private boolean contractFetchInProgress = false;
	
	public ContractServiceImpl(){
		this.timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@PostConstruct
	public void init(){
		final ContractService contractService = this;
		
//		this.calendarService.cleanInvalidCalendars();
//		this.calendarService.generateMissingCalendars();
		
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
	public List<Contract> findActiveContractsForVehicule(String vehiculeRegistration){
		return this.contractRepository.findActiveContractsForVehicule(vehiculeRegistration);
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
	public long generateNewContractId(long reservationId, Date contractStartDate){
		Contract contract = this.contractRepository.findByReservationId(reservationId);
		
		if(contract != null){
			return contract.getId();
		}else{
			String sysdatePrefix = new SimpleDateFormat("yyMMdd").format(contractStartDate);
			
			long contractId = Long.parseLong(sysdatePrefix + "0001");
			
			boolean newId = false;
			
			do{
				if(this.contractRepository.findOne(contractId) != null){
					contractId += 1;
				}else{
					newId = true;
				}
				
			}while(!newId);

			return contractId;
		}

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

	public void fetchContract(long reservationId){
		this.fetchContract(reservationId, false, null);
	}
	
	public void fetchContract(long reservationId, boolean forceUpdate){
		this.fetchContract(reservationId, forceUpdate, null);
	}
	
	public void fetchContract(long reservationId, boolean forceUpdate, Date requestedTimestamp){
		com.cspinformatique.wevan.backend.entity.Contract backendContract = null;
		long contractId = 0;
		
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
			
			Date contractStartDate = dateFormat.parse(backendContract.getEditableInfo().getStartDate());
			Date contractEditionDate =	timeFormat.parse(backendContract.getEditionDate().substring(0, 11) + 
											backendContract.getEditionDate().substring(13)
										);
			
			// Retreiving the branch linked with the reservation.
			Branch branch = this.branchService.findOne(backendContract.getAgency());
			
			contractId = this.generateNewContractId(reservationId, contractStartDate);
			
			Contract existingContract = this.contractRepository.findByReservationId(reservationId);
			
			if(existingContract != null){
				contractId = existingContract.getId();
			}
			
			if(forceUpdate || existingContract == null || existingContract.getEditionDate().getTime() > contractEditionDate.getTime()){
				logger.info("Generating contract " + contractId + " from reservation " + reservationId);
				
				if(branch != null){
					List<Option> options = this.calculateOptions(contractId, backendContract);

					Vehicule vehicule = vehiculeService.findByRegistration(backendContract.getEditableInfo().getLicense());
					
					String vehiculeName = "";
					String vehiculeModel = "";
					String vehiculeRegistration = "";
					String googleCalendarId = null;
					if(vehicule != null){
						vehiculeName = vehicule.getName() + " " + vehicule.getNumber();
						vehiculeModel = vehicule.getModel();
						vehiculeRegistration = vehicule.getRegistration();
						googleCalendarId = vehicule.getGoogleCalendarId();
					}
					
					double deductible = this.calculateDeductible(options);
					double deposit = deductible;
					
					String kilometersPackage = backendContract.getPayment().getKmPackage();
					if(kilometersPackage == null){
						kilometersPackage = "";
					}
					
					Date startDate = this.dateFormat.parse(backendContract.getEditableInfo().getStartDate().substring(0, 10) + " 09:00");
					Date endDate = this.dateFormat.parse(backendContract.getEditableInfo().getEndDate().substring(0, 10) + " 17:00");

					Contract contract =	new Contract(
											contractId, 
											reservationId,
											branch, 
											this.timeFormat.parse(backendContract.getCreationDate().substring(0, 11) + 
											backendContract.getCreationDate().substring(
												13
											)), 
											this.timeFormat.parse(backendContract.getEditionDate().substring(0, 11) + 
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
											startDate,
											endDate, 
											kilometersPackage, 
											backendContract.getPayment().getAlreadyPaid(), 
											backendContract.getPayment().getTotalCost(), 
											vehiculeName,
											vehiculeModel,
											vehiculeRegistration,
											deductible, 
											deposit, 
											new ArrayList<Driver>(), 
											options,
											false,
											googleCalendarId,
											null
										);
					
					contract = this.saveContract(contract);
					
					this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "OK", backendContract);
				}else{
					String message = "Reservation " + reservationId + " could not be saved since " + backendContract.getAgency() + " isn't configured into the system.";
					
					logger.error(message);
					
					this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "SKIPPED", backendContract, message);
				}
			}else{
				String message = "Reservation " + reservationId + " as already been loaded in the system. Skipping.";
				
				logger.info(message);

				this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "SKIPPED", backendContract, message);
			}
		}catch(Exception ex){
			this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "ERROR", backendContract, ex);
			
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void fetchRecentContractsOnError(){
		logger.info("Fetching recent contracts on error.");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		for(ElixirAudit audit : this.elixirAuditService.findAuditOnErrorSince(calendar.getTime())){
			this.fetchContract(audit.getReservationId(), true, new Date());
		}

		logger.info("Contract on error fetch completed.");
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
				
				logger.info("Retreiving contracts older than " + new Date(timestamp));
				
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
				
				for(long reservationId : reservationIds){
					try{
						this.fetchContract(reservationId, false, new Date(timestamp));
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
	public void resetContract(long id){
		Contract contract = this.findOne(id);
		
		if(contract == null){
			throw new RuntimeException("Contract " + id + " does not exist.");
		}
		
		if(contract.getReservationId() == null){
			throw new RuntimeException("The contract " + id + " does not have a reservation reference.");
		}
		
		// Removing all options.
		for(Option option : contract.getOptions()){
			this.optionService.deleteOption(option.getId());
		}
		
		this.fetchContract(contract.getReservationId(), true);
	}

	@Override
	@Transactional
	public Contract saveContract(Contract contract) {
		if(contract.getCreationDate() == null){
			contract.setCreationDate(new Date());
		}
		
		if(contract.getEditionDate() == null){
			contract.setEditionDate(new Date());
		}
		
		if(contract.getId() != 0){
			// Cleaning deleted contract.
			for(Option option : this.optionService.findByContract(contract.getId())){
				boolean optionFound = false;
				for(Option newOption : contract.getOptions()){
					if(newOption.getId() == option.getId()){
						optionFound = true;
					}
				}
				
				if(optionFound){
					this.optionService.save(option);
				}else{
					this.optionService.deleteOption(option.getId());
				}
			}
			
			// Checking if old calendar event needs to be deleted.
			Contract oldContract = this.contractRepository.findOne(contract.getId());
			if(oldContract != null){
				if(	oldContract.getGoogleCalendarEventId() != null &&
					oldContract.getGoogleCalendarId().equals(contract.getGoogleCalendarId()) && (
						oldContract.getStartDate().getTime() != contract.getStartDate().getTime() || 
						oldContract.getEndDate().getTime() != contract.getEndDate().getTime()
					)
				){
					// Old calender event.
					this.calendarService.deleteEvent(oldContract);
				}
			}
		}else{
			// Generating a new contract ID.
			contract.setId(this.generateNewContractId(0, contract.getStartDate()));
		}
		
		// Checking if new calendar event needs to be created.
		Vehicule vehicule = vehiculeService.findByRegistration(contract.getVehiculeRegistration());
		if(vehicule != null){
			if(vehicule.getGoogleCalendarId() != null){
				if(contract.getEndDate().getTime() > new Date().getTime()){
					contract.setGoogleCalendarId(vehicule.getGoogleCalendarId());
					contract.setGoogleCalendarEventId(this.calendarService.createEvent(vehicule, contract));
				}else{
					logger.debug("Innactive contract. Skipping calendar event creation.");
				}
			}else{
				logger.debug("Vehicule " + vehicule.getRegistration() + " has no Google Calendar Id configured. Skipping calendar event creation.");
			}
		}else{
			logger.debug("Skipping calendar event creating since no vehicule could be found for registration " + contract.getVehiculeRegistration());
		}
		
		return this.contractRepository.save(contract);
	}
}
