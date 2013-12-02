package com.cspinformatique.wevan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;
import com.cspinformatique.wevan.repository.ContractRepository;
import com.cspinformatique.wevan.service.ContractService;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
	@Autowired private ContractRepository contractRepository;
	
	@Override
	public List<Contract> findBranchAndStatus(Branch branch, Status status){
		return this.contractRepository.findByBranchAndStatus(branch, status);
	}
	
	@Override
	public Contract generateNewContract(Branch branch){
		Long contractId = this.findLastestContract(branch);
		String sysdatePrefix = new SimpleDateFormat("yyMMdd").format(new Date());
		
		if(contractId == null || !String.valueOf(contractId).startsWith(sysdatePrefix)){
			contractId = Long.parseLong(sysdatePrefix + "001");
		}else{
			++contractId;
		}

		Contract contract =	new Contract(
								contractId, 
								branch, 
								null, 
								null,
								null,
								null, 
								0, 
								0d,
								null, 
								0d, 
								0d,
								null
							);
		
		this.saveContract(contract);
		
		return contract;
	}
	
	@Override
	public Long findLastestContract(Branch branch) {
		return this.contractRepository.findLatestContractId(branch);
	}

	@Override
	public void saveContract(Contract contract) {
		this.contractRepository.save(contract);
	}
}
