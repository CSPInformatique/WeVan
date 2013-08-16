package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.entity.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
	@Query("SELECT v FROM Vehicule v ORDER BY v.name ASC")
	public List<Vehicule> findAllOrderByName();
	
	@Query("SELECT v FROM Vehicule v WHERE v.branch = ?1 ORDER BY v.name ASC")
	public List<Vehicule> findByBranch(int branch);
}
