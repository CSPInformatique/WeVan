package com.cspinformatique.wevan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	 @Query("SELECT MAX(c.id) FROM Contract c WHERE c.branch = :branch")
	 public Long findLatestContractId(@Param("branch") Branch branch);
}
