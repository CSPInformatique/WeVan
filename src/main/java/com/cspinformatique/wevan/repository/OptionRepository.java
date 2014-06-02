package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.wevan.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
	@Query("DELETE FROM Option WHERE contract = ?")
	public void deleteByContract(long contract);
	
	public List<Option> findByContract(long contract);
	
	public Option findByLabelAndContract(String label, long contract);
}
