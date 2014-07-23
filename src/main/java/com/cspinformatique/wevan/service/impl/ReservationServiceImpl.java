package com.cspinformatique.wevan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.ReservationNotification;
import com.cspinformatique.wevan.entity.ReservationNotification.Status;
import com.cspinformatique.wevan.repository.ReservationNotificationRepository;
import com.cspinformatique.wevan.service.ContractService;
import com.cspinformatique.wevan.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired private ReservationNotificationRepository reservationNotificationRepository;
	
	@Autowired private ContractService contractService;
	
	@Override
	@Scheduled(fixedDelay=5000)
	public void processNewReservations() {
		for(ReservationNotification reservationNotification : this.reservationNotificationRepository.findByStatusOrderByTimestampAsc(Status.NEW)){
			try{
				this.contractService.fetchContract(reservationNotification.getReservationId(), true);
				
				reservationNotification.setStatus(Status.PROCESSED);
			}catch(Exception ex){
				reservationNotification.setStatus(Status.ON_ERROR);
			}
			
			this.reservationNotificationRepository.save(reservationNotification);
		}

		this.contractService.fetchRecentContractsOnError();
	}

	@Override
	public ReservationNotification saveNotification(ReservationNotification reservationNotification) {
		return this.reservationNotificationRepository.save(reservationNotification);
	}

}
