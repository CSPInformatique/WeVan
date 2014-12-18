package com.cspinformatique.wevan.elixir.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cspinformatique.wevan.reservation.service.ReservationService;

@Component
public class ProcessNotificationsTask {
	@Autowired private ReservationService reservationService;
	
	@Scheduled(fixedDelay=5 * 1000)
	private void processNotifications(){
		while(reservationService.processNextReservation());
	}
}
