package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Vehicule;

public interface VehiculeService {
	public void deleteVehicule(int id);
	
	public Vehicule getVehicule(int id);
	
	public List<Vehicule> getVehicules();
	
	public List<Vehicule> getVehiculesByBranch(Branch branch);
	
	public void saveVehicule(Vehicule vehicule);	
}