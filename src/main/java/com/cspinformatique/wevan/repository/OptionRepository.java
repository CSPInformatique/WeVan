package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
	public List<Option> findByContract(long contract);
	
	public Option findByLabelAndContract(String label, long contract);
}
