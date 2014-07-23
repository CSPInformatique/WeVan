package com.cspinformatique.wevan.service;

import com.cspinformatique.wevan.entity.ReservationNotification;

public interface ReservationService {
	public void processNewReservations();

	public ReservationNotification saveNotification(ReservationNotification reservationNotification);
}
