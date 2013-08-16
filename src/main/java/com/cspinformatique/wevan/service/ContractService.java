package com.cspinformatique.wevan.service;

import com.cspinformatique.wevan.entity.Contract;

public interface ContractService {
	public Long findLastestContract(int branchId);
	
	public Contract generateNewContract(int branchId);
	
	public void saveContract(Contract contract);
}
