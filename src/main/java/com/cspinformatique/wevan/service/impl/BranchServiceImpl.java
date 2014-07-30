package com.cspinformatique.wevan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.repository.BranchRepository;
import com.cspinformatique.wevan.service.BranchService;
import com.cspinformatique.wevan.service.ContractService;

@Service
public class BranchServiceImpl implements BranchService {
	@Autowired private BranchRepository branchRepository;
	@Autowired private ContractService contractService;
	
	@Override
	public List<Branch> findActiveBranches(){
		return this.branchRepository.findActiveBranches();
	}
	
	@Override
	public List<Branch> findAll() {
		return this.branchRepository.findAll();
	}
	
	@Override
	public Branch findOne(int id){
		return this.branchRepository.findOne(id);
	}
	
	@Override
	public Branch findOne(String name){
		return this.branchRepository.findByName(name);
	}
	
	@Override
	public Branch saveBranch(Branch branch){
		if(branch.getRegistrationDate() == null){
			branch.setRegistrationDate(new Date());
		}
		
		boolean reloadContractOnError = false;
		if(branch.getId() != 0){
			Branch oldBranch = this.branchRepository.findOne(branch.getId());
			
			if(!oldBranch.getName().equals(branch.getName())){
				reloadContractOnError = true; 
			}
		}
		
		Branch newBranch = this.branchRepository.save(branch);
		
		if(reloadContractOnError){
			this.contractService.fetchContractsOnWaiting();
		}
		
		return newBranch;
	}
}
