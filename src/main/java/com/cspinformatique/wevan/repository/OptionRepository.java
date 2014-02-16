package com.cspinformatique.wevan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
	public Option findByLabelAndContract(String label, long contract);
}
