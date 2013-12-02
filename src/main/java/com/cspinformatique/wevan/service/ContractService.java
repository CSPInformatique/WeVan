package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public List<Contract> findBranchAndStatus(Branch branch, Status status);
	
	public Long findLastestContract(Branch branch);
	
	public Contract generateNewContract(Branch branch);
	
	public void saveContract(Contract contract);
}
