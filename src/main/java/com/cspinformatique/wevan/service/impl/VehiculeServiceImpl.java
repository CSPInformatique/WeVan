package com.cspinformatique.wevan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.repository.VehiculeRepository;
import com.cspinformatique.wevan.service.VehiculeService;

@Service
public class VehiculeServiceImpl implements VehiculeService {
	@Autowired private VehiculeRepository vehiculeRepository;
	
	@Override
	public void deleteVehicule(int id) {
		this.vehiculeRepository.delete(id);
	}
	
	@Override
	public Vehicule getVehicule(int id){
		return this.vehiculeRepository.findOne(id);
	}

	@Override
	public List<Vehicule> getVehicules() {
		return this.vehiculeRepository.findAll();
	}

	@Override
	public List<Vehicule> getVehiculesByBranch(int branch) {
		return this.vehiculeRepository.findByBranch(branch);
	}

	@Override
	public void saveVehicule(Vehicule vehicule) {
		this.vehiculeRepository.save(vehicule);
	}

}
