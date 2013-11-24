package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	 @Query("SELECT MAX(c.id) FROM Contract c WHERE c.branchId = :branchId")
	 public Long findLatestContractId(@Param("branchId") int branchId);
	 
	 public List<Contract> findByStatus(Status status);
}
