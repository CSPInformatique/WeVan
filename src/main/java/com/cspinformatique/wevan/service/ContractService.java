package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public void deleteContract(long id);
	
	public List<Contract> findByBranch(Branch branch);
	
	public List<Contract> findByBranchAndStatus(Branch branch, List<Status> status);
	
	public Contract findOne(long id);
	
	public Long findLastestContract(Branch branch);
	
	public Contract generateNewContract(Branch branch);
	
	public Contract saveContract(Contract contract);
}
