package com.cspinformatique.wevan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.repository.VehiculeRepository;
import com.cspinformatique.wevan.service.VehiculeService;

@Service
public class VehiculeServiceImpl implements VehiculeService {
	@Autowired private VehiculeRepository vehiculeRepository;
	
	@Override
	public void delete(int id) {
		this.vehiculeRepository.delete(id);
	}
	
	@Override
	public Vehicule findOne(int id){
		return this.vehiculeRepository.findOne(id);
	}

	@Override
	public List<Vehicule> findAll() {
		return this.vehiculeRepository.findAll();
	}

	@Override
	public List<Vehicule> findByBranch(Branch branch) {
		return this.vehiculeRepository.findByBranch(branch);
	}
	
	@Override
	public Vehicule findByRegistration(String registration){
		return this.vehiculeRepository.findByRegistration(registration);
	}

	@Override
	public void save(Vehicule vehicule) {
		this.vehiculeRepository.save(vehicule);
	}

}
