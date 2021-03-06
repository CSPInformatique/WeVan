package com.cspinformatique.wevan.contract.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.contract.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	@Query("SELECT contract FROM Contract contract WHERE vehiculeRegistration = ?1 AND endDate > CURRENT_TIMESTAMP")
	public List<Contract> findActiveContractsForVehicule(String vehiculeRegistration);
	
	public Page<Contract> findByBranch(Branch branch, Pageable pageable);
	
	public Contract findByReservationId(long reservationId);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND endDate < CURRENT_TIMESTAMP")
	public Page<Contract> findCompletedContracts(Branch branch, Pageable pageable);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND startDate < CURRENT_TIMESTAMP AND endDate > CURRENT_TIMESTAMP")
	public Page<Contract> findInProgressContracts(Branch branch, Pageable pageable);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND ((startDate < CURRENT_TIMESTAMP AND endDate < CURRENT_TIMESTAMP) OR endDate < CURRENT_TIMESTAMP)")
	public Page<Contract> findInProgressAndCompletedContracts(Branch branch, Pageable pageable);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND startDate > CURRENT_TIMESTAMP")
	public Page<Contract> findOpenContracts(Branch branch, Pageable pageable);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND (startDate > CURRENT_TIMESTAMP OR endDate < CURRENT_TIMESTAMP)")
	public Page<Contract> findOpenAndCompletedContracts(Branch branch, Pageable pageable);
	
	@Query("SELECT contract FROM Contract contract WHERE branch = ?1 AND (startDate > CURRENT_TIMESTAMP OR endDate > CURRENT_TIMESTAMP)")
	public Page<Contract> findOpenAndInProgressContracts(Branch branch, Pageable pageable);
	
	@Query(
		"SELECT " 
			+ "	contract "
			+ "FROM "
			+ "	Contract contract "
			+ "WHERE "
			+ "	editionDate = ( SELECT MAX(editionDate) FROM Contract )")
	public List<Contract> findLastContractModified();
}
