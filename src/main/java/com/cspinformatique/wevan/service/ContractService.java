package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractService {
	public List<Contract> findByStatus(Status status);
	
	public Long findLastestContract(int branchId);
	
	public Contract generateNewContract(int branchId);
	
	public void saveContract(Contract contract);
}
