package com.cspinformatique.wevan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.Option;
import com.cspinformatique.wevan.repository.OptionRepository;
import com.cspinformatique.wevan.service.OptionService;

@Service
public class OptionServiceImpl implements OptionService {	
	@Autowired private OptionRepository optionRepository;
	
	@Override
	public void deleteOption(long id){
		this.optionRepository.delete(id);
	}
	
	@Override
	public List<Option> findByContract(long contract){
		return this.optionRepository.findByContract(contract);
	}
	
	@Override
	public Option generateOption(long contract, boolean active, String label, double amount) {		
		return new Option(0, contract, active, label, amount);
	}
	
	@Override
	public Option save(Option option){
		return this.optionRepository.save(option);
	}

}
