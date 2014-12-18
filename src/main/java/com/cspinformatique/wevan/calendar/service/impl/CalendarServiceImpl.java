package com.cspinformatique.wevan.calendar.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.google.GoogleCalendar;
import com.cspinformatique.wevan.calendar.service.CalendarService;
import com.cspinformatique.wevan.contract.entity.Contract;
import com.cspinformatique.wevan.contract.service.ContractService;
import com.cspinformatique.wevan.referential.entity.Vehicule;
import com.cspinformatique.wevan.referential.service.VehiculeService;

@Service
public class CalendarServiceImpl implements CalendarService {
	private static final Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);
	
	@Autowired private GoogleCalendar googleCalendar;
	
	@Autowired private ContractService contractService;
	@Autowired private VehiculeService vehiculeService;
	
	@Override
	public void cleanInvalidCalendars(){
		List<String> calendarsIds = this.googleCalendar.findAllCalendar();
		
		for(Vehicule vehicule : this.vehiculeService.findAll()){
			if(	calendarsIds.contains(vehicule.getGoogleCalendarId()) && 
				this.googleCalendar.isCalendarActive(
					vehicule.getGoogleCalendarId(), 
					vehicule.getBranch().getGoogleEmailAccount()
				)
			){
				calendarsIds.remove(vehicule.getGoogleCalendarId());
			}
		}
		
		for(String calendarId : calendarsIds){
			this.googleCalendar.deleteCalendar(calendarId);
		}
	}
	
	@Override
	public void createCalendar(Vehicule vehicule) {
		if(vehicule.getBranch().getGoogleEmailAccount() != null){
			vehicule.setGoogleCalendarId(
				this.googleCalendar.createCalendar(
					vehicule.getName() + " " + vehicule.getNumber(),
					"Vehicule : " + vehicule.getName() + " " + vehicule.getNumber() + "\n" +
						"Model " + vehicule.getModel(),
					vehicule.getBranch().getGoogleEmailAccount()
				)
			);
		}else{
			logger.warn(
				"Google account email undefined for branch " + 
					vehicule.getBranch().getName() + 
					". Skipping calendar creation for vehicule " + 
					vehicule.getName() + " " + 
					vehicule.getNumber() + "."
			);
		}
	}

	@Override
	public String createEvent(Vehicule vehicule, Contract contract) {
		return googleCalendar.createEvent(
			contract.getGoogleCalendarId(), 
			vehicule.getName() + " " + vehicule.getNumber() + " " + contract.getId() + " - " + contract.getDriver().getLastName(), 
			contract.getStartDate(), 
			contract.getEndDate()
		);
	}

	@Override
	public void deleteEvent(Contract contract) {
		googleCalendar.deleteEvent(contract.getGoogleCalendarId(), contract.getGoogleCalendarEventId());

	}
	
	@Override
	public void generateMissingCalendars(){
		for(Vehicule vehicule : this.vehiculeService.findAll()){
			if(vehicule.getGoogleCalendarId() == null){
				// Saving the vehicule will trigger the generation of a calendar for the vehicule.
				this.vehiculeService.save(vehicule);
			}
		}
	}
	
	@Override
	public void updateVehiculeCalendars(Vehicule vehicule){
		for(Contract contract : this.contractService.findActiveContractsForVehicule(vehicule.getRegistration())){
			// Triggering the saveContract method will generate a new event. 
			contract.setGoogleCalendarId(vehicule.getGoogleCalendarId());
			
			this.contractService.saveContract(contract);
		}
	}
}
