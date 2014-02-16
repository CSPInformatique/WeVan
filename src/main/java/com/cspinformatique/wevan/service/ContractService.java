package com.cspinformatique.wevan.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public void deleteContract(long id);
	
	public Page<Contract> findByBranch(Branch branch, int page, int results);
	
	public Page<Contract> findByBranchAndStatus(Branch branch, List<Status> status, int page, int results);
	
	public Contract findOne(long id);
	
	public long generateNewContractId();
	
	public Contract saveContract(Contract contract);
}
