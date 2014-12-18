package com.cspinformatique.wevan.contract.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.contract.entity.Contract;
import com.cspinformatique.wevan.contract.entity.Contract.Status;
import com.cspinformatique.wevan.reservation.entity.ReservationDTO;

public interface ContractService {
	public void deleteContract(long id);

	public List<Contract> findActiveContractsForVehicule(String vehiculeRegistration);
	
	public Page<Contract> findByBranch(Branch branch, PageRequest pageRequest);
	
	public Page<Contract> findByBranchAndStatus(Branch branch, List<Status> status, PageRequest pageRequest);
	
	public void fetchContract(long reservationId);
	
	public void fetchContract(long reservationId, boolean forceUpdate);
		
	public void fetchContract(long reservationId, boolean forceUpdate, Date requestedTimestamp);
	
	public void fetchContract(long reservationId, int branchId, boolean forceUpdate, Date requestedTimestamp);
	
	public void fetchContracts(boolean forceUpdate, Date startDate);
	
	public void fetchContracts(Date startDate);
	
	public void fetchContracts(int branch, Date startDate);
	
	public void fetchContracts(int branchId, boolean forceUpdate, Date startDate);

	public void fetchContractsOnWaiting();
	
	public void fetchLatestContracts();
	
	public void fetchRecentContractsOnError();
	
	public Contract findLastContractModified();
	
	public Contract findOne(long id);
	
	public long generateNewContractId(long reservationId, Date contractStartDate);
	
	public boolean isContractFetchInProgress();
	
	public void processReservation(long reservationId, ReservationDTO reservationDTO, boolean forceUpdate, Date requestedTimestamp);
	
	public void resetContract(long id);
	
	public Contract saveContract(Contract contract);
}
