package com.cspinformatique.wevan.service;

import java.util.List;

import com.cspinformatique.wevan.entity.Option;

public interface OptionService {
	public void deleteByContract(long contract);
	
	public void deleteOption(long id);
	
	public List<Option> findByContract(long contract);
	
	public Option generateOption(long contract, boolean active, String label, double amount);
	
	public Option save(Option option);
}
