package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
	@Query("SELECT branch FROM Branch branch WHERE active = true")
	public List<Branch> findActiveBranches();
	
	public Branch findByName(String name);
}
