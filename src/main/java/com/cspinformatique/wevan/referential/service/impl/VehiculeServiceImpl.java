package com.cspinformatique.wevan.referential.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.calendar.service.CalendarService;
import com.cspinformatique.wevan.contract.service.ContractService;
import com.cspinformatique.wevan.referential.entity.Vehicule;
import com.cspinformatique.wevan.referential.repository.VehiculeRepository;
import com.cspinformatique.wevan.referential.service.VehiculeService;

@Service
public class VehiculeServiceImpl implements VehiculeService {		
	@Autowired private CalendarService calendarService;
	@Autowired private ContractService contractService;
	@Autowired private VehiculeRepository vehiculeRepository;
	
	@PostConstruct
	public void init(){
		
	}
	
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
		if(vehicule.getGoogleCalendarId() == null){
			this.calendarService.createCalendar(vehicule);
		}
		
		this.vehiculeRepository.save(vehicule);
		
		// Retreive all active contract for the vehicule and update the agenda.
		this.calendarService.updateVehiculeCalendars(vehicule);
	}
}
