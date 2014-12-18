package com.cspinformatique.wevan.calendar.service;

import com.cspinformatique.wevan.contract.entity.Contract;
import com.cspinformatique.wevan.referential.entity.Vehicule;

public interface CalendarService {
	public void cleanInvalidCalendars();
	
	public void createCalendar(Vehicule vehicule);
	
	public String createEvent(Vehicule vehicule, Contract contract);
	
	public void deleteEvent(Contract contract);
	
	public void generateMissingCalendars();
	
	public void updateVehiculeCalendars(Vehicule vehicule);
}
