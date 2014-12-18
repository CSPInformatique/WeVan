package com.cspinformatique.wevan.elixir.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cspinformatique.wevan.contract.service.ContractService;

@Component
public class FetchNotificationsTask {
	@Autowired private ContractService contractService;
	
	@Scheduled(fixedDelay=60 * 1000)
	public void fetchContracts(){
		contractService.fetchLatestContracts();
	}
}
