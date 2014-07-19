package com.cspinformatique.wevan.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cspinformatique.wevan.backend.entity.WevanReservation;
import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public void deleteContract(long id);
	
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
	
	public void fetchRecentContractsOnError();
	
	public Contract findLastContractModified();
	
	public Contract findOne(long id);
	
	public long generateNewContractId(long reservationId, Date contractStartDate);
	
	public boolean isContractFetchInProgress();
	
	public void processReservation(long reservationId, WevanReservation wevanReservation, boolean forceUpdate, Date requestedTimestamp);
	
	public void resetContract(long contractId);
	
	public Contract saveContract(Contract contract);
}
