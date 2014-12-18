package com.cspinformatique.wevan.branch.service;

import java.util.List;

import com.cspinformatique.wevan.branch.entity.Branch;

public interface BranchService {
	public List<Branch> findActiveBranches();
	
	public List<Branch> findAll();
		
	public Branch findOne(int id);
	
	public Branch findOne(String name);
	
	public Branch saveBranch(Branch branch);
}
