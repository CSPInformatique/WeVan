package com.cspinformatique.wevan.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public void deleteContract(long id);

	public List<Contract> findActiveContractsForVehicule(String vehiculeRegistration);
	
	public Page<Contract> findByBranch(Branch branch, PageRequest pageRequest);
	
	public Page<Contract> findByBranchAndStatus(Branch branch, List<Status> status, PageRequest pageRequest);
	
	public void fetchContracts();
	
	public void fetchRecentContractsOnError();
	
	public Contract findOne(long id);
	
	public long generateNewContractId(long reservationId, Date contractStartDate);
	
	public void resetContract(long id);
	
	public Contract saveContract(Contract contract);
}
