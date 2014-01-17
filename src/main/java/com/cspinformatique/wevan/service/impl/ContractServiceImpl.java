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
	public void deleteContract(long id){
		this.contractRepository.delete(id);
	}
	
	@Override
	public List<Contract> findByBranch(Branch branch){
		return this.contractRepository.findByBranchOrderByIdDesc(branch);
	}
	
	@Override
	public List<Contract> findByBranchAndStatus(Branch branch, List<Status> status){
		return this.contractRepository.findByBranchAndStatusInOrderByIdDesc(branch, status);
	}
	
	@Override
	public Contract findOne(long id){
		return this.contractRepository.findOne(id);
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
								null,
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
	public Contract saveContract(Contract contract) {
		return this.contractRepository.save(contract);
	}
}
