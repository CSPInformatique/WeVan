package com.cspinformatique.wevan.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cspinformatique.wevan.service.ReservationService;

@Component
public class ReservationNotificationTask {
	@Autowired private ReservationService reservationService;
	
	@Scheduled(fixedDelay=5 * 1000)
	private void processNotifications(){
		while(reservationService.processNextReservation());
	}
}
