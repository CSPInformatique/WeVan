package com.cspinformatique.wevan.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.redis.core.entity.Message;
import com.cspinformatique.redis.message.client.service.MessageService;
import com.cspinformatique.wevan.contract.service.ContractService;
import com.cspinformatique.wevan.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired private MessageService messageService;
	
	@Autowired private ContractService contractService;
	
	@Override
	public boolean processNextReservation(){		
		Message message = messageService.consumeMessageFromQueue();
			
		if(message != null){
			this.contractService.fetchContract(Long.valueOf(message.getMessage()), true);
			
			return true;
		}
		
		return false;
	}
}
