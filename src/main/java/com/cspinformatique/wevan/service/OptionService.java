package com.cspinformatique.wevan.service;

import com.cspinformatique.wevan.entity.Option;

public interface OptionService {
	public Option generateOption(long contract, boolean active, String label, double amount);
}
