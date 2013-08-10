package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.entity.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
	public List<Vehicule> findByBranch(int branch);
}
