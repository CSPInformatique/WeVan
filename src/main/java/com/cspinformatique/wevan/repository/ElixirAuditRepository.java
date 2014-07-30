package com.cspinformatique.wevan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cspinformatique.wevan.entity.ElixirAudit;

public interface ElixirAuditRepository extends JpaRepository<ElixirAudit, Long> {
	@Query("SELECT elixirAudit FROM ElixirAudit elixirAudit ORDER BY fetchTimestamp DESC")
	public List<ElixirAudit> findAllOrderByFetchTimestampDesc();
	
	@Query("SELECT audit FROM ElixirAudit audit WHERE status = 'ERROR' AND fetchTimestamp > :timestamp ")
	public List<ElixirAudit> findAuditsOnErrorSince(@Param("timestamp") Date timestamp);
	
	@Query("SELECT audit FROM ElixirAudit audit WHERE status = 'WAITING'")
	public List<ElixirAudit> findAuditsOnWaiting();
}