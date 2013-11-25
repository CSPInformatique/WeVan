package com.cspinformatique.wevan.service;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;

public interface ContractService {
	public Long findLastestContract(Branch branch);
	
	public Contract generateNewContract(Branch branch);
	
	public void saveContract(Contract contract);
}
