package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	@Query(
		"SELECT " 
		+ "	contract "
		+ "FROM "
		+ "	Contract contract "
		+ "WHERE "
		+ "	editionDate = ( SELECT MAX(editionDate) FROM Contract )")
	public List<Contract> findLastContractModified();
	 
	public Page<Contract> findByBranchOrderByCreationDateDesc(Branch branch, Pageable pageable);
	 
	public Page<Contract> findByBranchAndStatusInOrderByCreationDateDesc(Branch branch, List<Status> status, Pageable pageable);
	
	public Contract findByReservationId(long reservationId);
}
