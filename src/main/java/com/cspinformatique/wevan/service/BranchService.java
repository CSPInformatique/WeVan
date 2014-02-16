package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Branch;

public interface BranchService {
	public List<Branch> findAll();
	
	public Branch findOne(int id);
	
	public Branch findOne(String name);
}
