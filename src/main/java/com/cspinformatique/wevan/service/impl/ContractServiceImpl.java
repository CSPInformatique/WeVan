package com.cspinformatique.wevan.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
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
import com.cspinformatique.wevan.backend.entity.WevanReservation;
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
					Date startDate = new Date(1);

					// If no contracts exists in the system, a full load will be launched. Otherwise,
					// the edition date of the latest contract will be used to retreived all the missing contract.
					Contract latestContract = contractService.findLastContractModified();
					if(latestContract != null){
						startDate = latestContract.getEditionDate();
					}
					
					contractService.fetchContracts(startDate);
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
	
	@Override
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
		Contract contract = null;
		if(reservationId != 0l){
			contract = this.contractRepository.findByReservationId(reservationId);
		}
		
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
	
	private List<Option> calculateOptions(long contractId, WevanReservation wevanReservation){
		List<Option> options = new ArrayList<Option>();
		
		if(wevanReservation.getPayment().getPartialDeductible() > 0){
			options.add(
				this.optionService.generateOption(
					contractId, 
					true, 
					Option.LABEL_PARTIAL_DEDUCTIBLE, 
					wevanReservation.getPayment().getPartialDeductible()
				)
			);
		}
	
		if(wevanReservation.getPayment().getAdditionalDrivers() > 0){
			options.add(
				this.optionService.generateOption(
					contractId,
					true, 
					Option.LABEL_ADDITIONAL_DRIVER, 
					wevanReservation.getPayment().getAdditionalDriversCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isBeddingPack()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_BEDS,
					wevanReservation.getPayment().getBeddingPackCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isCancelOption()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CANCEL_INSURANCE,
					0d
				)
			);
		}
		
		if(wevanReservation.getPayment().isCarRack()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CAR_RACK,
					wevanReservation.getPayment().getCarRackCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isChildSeat()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CHILD_SEAT,
					wevanReservation.getPayment().getChildSeatCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isCleaningPackage()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_CLEANING,
					wevanReservation.getPayment().getCleaningPackageCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isGps()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_GPS,
					wevanReservation.getPayment().getGpsCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isWinterTires()){
			options.add(
				this.optionService.generateOption(
					contractId,
					wevanReservation.getPayment().isWinterTires(),
					Option.LABEL_WINTER_TIRES,
					wevanReservation.getPayment().getWinterTiresCost()
				)
			);
		}
		
		if(wevanReservation.getPayment().isYoungDriver()){
			options.add(
				this.optionService.generateOption(
					contractId,
					true,
					Option.LABEL_YOUNG_DRIVER,
					wevanReservation.getPayment().getYoungDriverCost()
				)
			);
		}
	
		return options;
		
	}

	@Override
	public void fetchContract(long reservationId){
		this.fetchContract(reservationId, false);
	}
	
	@Override
	public void fetchContract(long reservationId, boolean forceUpdate){
		this.fetchContract(reservationId, forceUpdate, new Date());
	}
	
	@Override
	public void fetchContract(long reservationId, boolean forceUpdate, Date requestedTimestamp){
		this.fetchContract(reservationId, 0, forceUpdate, requestedTimestamp);
	}
	
	@Override
	public void fetchContract(long reservationId, int branchId, boolean forceUpdate, Date requestedTimestamp){
		WevanReservation wevanReservation = null;
		long contractId = 0;
		
		Branch branch = null;
		if(branchId != 0){
			branch = this.branchService.findOne(branchId);
		}
		
		try{
			wevanReservation = new RestTemplate().exchange(
					"http://www.we-van.com/api/?id=" + reservationId, 	
					HttpMethod.GET, 
					new HttpEntity<WevanReservation>(
						new WevanReservation(),
						RestUtil.createBasicAuthHeader(
							"wevan-api", 
							"7D4gLg"
						) 
					), 
					WevanReservation.class 
				).getBody();

			logger.info("Received : " + wevanReservation);
			
			if(branchId == 0 || branch.getName().equals(wevanReservation.getAgency())){				
				this.processReservation(reservationId, wevanReservation, forceUpdate, requestedTimestamp);
			}else{
				logger.debug("Reservation " + reservationId + " is not assigned to branch " + branch.getName() + ". Skipping.");
			}
		}catch(RuntimeException ex){
			this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "ERROR", wevanReservation, ex);

			logger.error("Error while processing reservation : " + reservationId, ex);
		}
	}

	@Override
	public void fetchContracts(Date startDate){
		this.fetchContracts(false, startDate);
	}
	
	@Override
	public void fetchContracts(int branch, Date startDate){
		this.fetchContract(branch, false, startDate);
	}
	
	@Override
	public void fetchContracts(boolean forceUpdate, Date startDate){
		this.fetchContracts(0, forceUpdate, startDate);
	}
	
	@Override
	public void fetchContracts(int branchId, boolean forceUpdate, Date startDate){	
		if(!contractFetchInProgress){
			try{
				contractFetchInProgress = true;
				
				long timestamp = startDate.getTime() / 1000;
				
				logger.info("Retreiving contracts younger than " + startDate);
				
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
					this.fetchContract(reservationId, branchId, forceUpdate, new Date(timestamp));
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
	public void fetchContractsOnWaiting(){
		logger.info("Fetching contracts on WAITING.");
		
		for(ElixirAudit audit : this.elixirAuditService.findAuditsOnWaiting()){
			this.fetchContract(audit.getReservationId(), true, new Date());
		}
		
		logger.info("Finished fetching contracts on WAITING");
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
	public boolean isContractFetchInProgress(){
		return this.contractFetchInProgress;
	}
	
	@Override
	public void processReservation(long reservationId, WevanReservation wevanReservation, boolean forceUpdate, Date requestedTimestamp){
		try{			
			Date contractStartDate = dateFormat.parse(wevanReservation.getEditableInfo().getStartDate());
			Date contractEditionDate =	timeFormat.parse(wevanReservation.getEditionDate().substring(0, 11) + 
											wevanReservation.getEditionDate().substring(13)
										);
			
			long contractId = this.generateNewContractId(reservationId, contractStartDate);
			
			Contract existingContract = this.contractRepository.findByReservationId(reservationId);
			
			if(existingContract != null){
				contractId = existingContract.getId();
				
				// Clean options.
				
			}
			
			/*	Any of the following condition will allow the contract to be persisted.
			 * 		1 - forceUpdate flag to true.
			 * 		2 - No existing contract for the reservationId.
			 * 		3 - The date of the existing contrat doesn't match with the one retreived from wevan.
			 * 		4 - Vehicule from the old and new contract doesn't match.
			 */
			
			if(	forceUpdate || 
				existingContract == null || 
				existingContract.getEditionDate().getTime() > contractEditionDate.getTime() ||
				(wevanReservation.getEditableInfo().getLicense() == null && existingContract.getVehiculeRegistration() != null) || 
				!wevanReservation.getEditableInfo().getLicense().equals(existingContract.getVehiculeRegistration())
			){
				logger.info("Generating contract " + contractId + " from reservation " + reservationId);
				
				// Retreiving the branch linked with the reservation.
				Branch branch = this.branchService.findOne(wevanReservation.getAgency());
				
				if(branch != null){
					List<Option> options = this.calculateOptions(contractId, wevanReservation);
	
					Vehicule vehicule = vehiculeService.findByRegistration(wevanReservation.getEditableInfo().getLicense());
					
					String vehiculeName = "";
					String vehiculeModel = "";
					String vehiculeRegistration = "";
					if(vehicule != null){
						vehiculeName = vehicule.getName() + " " + vehicule.getNumber();
						vehiculeModel = vehicule.getModel();
						vehiculeRegistration = vehicule.getRegistration();
					}
					
					double deductible = this.calculateDeductible(options);
					double deposit = deductible;
					
					String kilometersPackage = wevanReservation.getPayment().getKmPackage();
					if(kilometersPackage == null){
						kilometersPackage = "";
					}
					
					Contract contract =	new Contract(
											contractId, 
											reservationId,
											branch, 
											this.timeFormat.parse(wevanReservation.getCreationDate().substring(0, 11) + 
											wevanReservation.getCreationDate().substring(
												13
											)), 
											this.timeFormat.parse(wevanReservation.getEditionDate().substring(0, 11) + 
												wevanReservation.getEditionDate().substring(
													13
												)),
											Contract.Status.OPEN, 
											new Driver(
												0, 
												wevanReservation.getUser().getCompany(), 
												wevanReservation.getUser().getFirstName(), 
												wevanReservation.getUser().getLastName(), 
												""
											), 
											this.dateFormat.parse(wevanReservation.getEditableInfo().getStartDate()),
											this.dateFormat.parse(wevanReservation.getEditableInfo().getEndDate()), 
											kilometersPackage, 
											wevanReservation.getPayment().getAlreadyPaid(), 
											wevanReservation.getPayment().getTotalCost(), 
											vehiculeName,
											vehiculeModel,
											vehiculeRegistration,
											deductible, 
											deposit, 
											new ArrayList<Driver>(), 
											options,
											false,
											null,
											null
										);
					
					contract = this.saveContract(contract);
					
					this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "OK", wevanReservation);
				}else{
					String message = "ReservationNotification " + reservationId + " could not be saved since " + wevanReservation.getAgency() + " isn't configured into the system.";
					
					logger.error(message);
					
					this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "WAITING", wevanReservation, message);
				}
			}else{
				String message = "ReservationNotification " + reservationId + " as already been loaded in the system. Skipping.";
				
				logger.info(message);
	
				this.elixirAuditService.save(reservationId, contractId, requestedTimestamp, "SKIPPED", wevanReservation, message);
			}
		}catch(ParseException parseEx){
			throw new RuntimeException(parseEx);
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
		logger.info("Deleting options for contract " + contract.getId());
		for(Option option : contract.getOptions()){
			logger.info("Deleting option " + option.getId());
			
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
//			Contract oldContract = this.contractRepository.findOne(contract.getId());
//			if(oldContract != null){
//				if(	oldContract.getGoogleCalendarEventId() != null &&
//					oldContract.getGoogleCalendarId().equals(contract.getGoogleCalendarId()) && (
//						oldContract.getStartDate().getTime() != contract.getStartDate().getTime() || 
//						oldContract.getEndDate().getTime() != contract.getEndDate().getTime()
//					)
//				){
//					// Old calender event.
//					this.calendarService.deleteEvent(oldContract);
//				}
//			}
		}else{
			// Generating a new contract ID.
			contract.setId(this.generateNewContractId(0, contract.getStartDate()));
		}
		
		// Checking if new calendar event needs to be created.
//		Vehicule vehicule = vehiculeService.findByRegistration(contract.getVehiculeRegistration());
//		if(vehicule != null){
//			if(vehicule.getGoogleCalendarId() != null){
//				if(contract.getEndDate().getTime() > new Date().getTime()){
//					contract.setGoogleCalendarId(vehicule.getGoogleCalendarId());
//					contract.setGoogleCalendarEventId(this.calendarService.createEvent(vehicule, contract));
//				}else{
//					logger.debug("Innactive contract. Skipping calendar event creation.");
//				}
//			}else{
//				logger.debug("Vehicule " + vehicule.getRegistration() + " has no Google Calendar Id configured. Skipping calendar event creation.");
//			}
//		}else{
//			logger.debug("Skipping calendar event creating since no vehicule could be found for registration " + contract.getVehiculeRegistration());
//		}
		
		return this.contractRepository.save(contract);
	}
	
}
