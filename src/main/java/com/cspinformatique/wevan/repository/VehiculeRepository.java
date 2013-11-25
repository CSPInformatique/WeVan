package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
	@Query("SELECT v FROM Vehicule v ORDER BY v.branch ASC, v.model ASC, v.name ASC")
	public List<Vehicule> findAll();
	
	@Query("SELECT v FROM Vehicule v WHERE v.branch = ?1 ORDER BY v.branch ASC, v.name ASC, v.number ASC, v.model ASC")
	public List<Vehicule> findByBranch(Branch branch);
}
