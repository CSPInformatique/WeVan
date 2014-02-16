package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Vehicule;

public interface VehiculeService {
	public void delete(int id);
	
	public Vehicule findOne(int id);
	
	public List<Vehicule> findAll();
	
	public List<Vehicule> findByBranch(Branch branch);
	
	public Vehicule findByRegistration(String registration);
	
	public void save(Vehicule vehicule);	
}
