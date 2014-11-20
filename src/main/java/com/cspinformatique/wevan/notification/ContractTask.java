package com.cspinformatique.wevan.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cspinformatique.wevan.service.ContractService;

@Component
public class ContractTask {
	@Autowired private ContractService contractService;
	
	@Scheduled(fixedDelay=60 * 1000)
	public void fetchContracts(){
		contractService.fetchLatestContracts();
	}
}
