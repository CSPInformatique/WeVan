package com.cspinformatique.wevan.referential.service;

import java.util.List;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.referential.entity.Vehicule;

public interface VehiculeService {
	public void delete(int id);
	
	public Vehicule findOne(int id);
	
	public List<Vehicule> findAll();
	
	public List<Vehicule> findByBranch(Branch branch);
	
	public Vehicule findByRegistration(String registration);
	
	public void save(Vehicule vehicule);	
}
