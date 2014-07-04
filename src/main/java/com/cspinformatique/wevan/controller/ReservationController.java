package com.cspinformatique.wevan.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.ReservationNotification;
import com.cspinformatique.wevan.entity.ReservationNotification.Status;
import com.cspinformatique.wevan.service.ReservationService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
	@Autowired private ReservationService reservationService;
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{reservationId}")
	public void notifyReservation(@PathVariable long reservationId){
		this.reservationService.saveNotification(new ReservationNotification(0, reservationId, new Date(), Status.NEW));
	}
}
