package com.cspinformatique.wevan.service;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Vehicule;

public interface CalendarService {
	public void cleanInvalidCalendars();
	
	public void createCalendar(Vehicule vehicule);
	
	public String createEvent(Vehicule vehicule, Contract contract);
	
	public void deleteEvent(Contract contract);
	
	public void generateMissingCalendars();
	
	public void updateVehiculeCalendars(Vehicule vehicule);
}
