package com.cspinformatique.wevan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.repository.BranchRepository;
import com.cspinformatique.wevan.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {
	@Autowired private BranchRepository branchRepository;
	
	@Override
	public List<Branch> findAll() {
		return this.branchRepository.findAll();
	}
	
	@Override
	public Branch findOne(int id){
		return this.branchRepository.findOne(id);
	}

}
