package com.cspinformatique.wevan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.repository.ContractRepository;
import com.cspinformatique.wevan.service.ContractService;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
	@Autowired private ContractRepository contractRepository;
	
	@Override
	public Contract generateNewContract(int branchId){
		Long contractId = this.findLastestContract(branchId);
		String sysdatePrefix = new SimpleDateFormat("yyMMdd").format(new Date());
		
		if(contractId == null || !String.valueOf(contractId).startsWith(sysdatePrefix)){
			contractId = Long.parseLong(sysdatePrefix + "001");
		}else{
			++contractId;
		}
		Contract contract = new Contract(contractId, branchId);
		
		this.saveContract(contract);
		
		return contract;
	}
	
	@Override
	public Long findLastestContract(int branchId) {
		return this.contractRepository.findLatestContractId(branchId);
	}

	@Override
	public void saveContract(Contract contract) {
		this.contractRepository.save(contract);
	}
}
