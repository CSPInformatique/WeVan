package com.cspinformatique.wevan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Option;
import com.cspinformatique.wevan.repository.OptionRepository;
import com.cspinformatique.wevan.service.OptionService;

@Service
public class OptionServiceImpl implements OptionService {	
	@Autowired private OptionRepository optionRepository;
	
	@Override
	public Option generateOption(long contract, boolean active, String label, double amount) {		
		Option option = optionRepository.findByLabelAndContract(label, contract);
		
		long optionId = 0;
		if(option != null){
			optionId = option.getId();
		}
		
		return new Option(optionId, contract, active, label, amount);
	}

}
