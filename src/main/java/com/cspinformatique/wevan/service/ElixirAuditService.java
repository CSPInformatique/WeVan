package com.cspinformatique.wevan.service;

import java.util.Date;
import java.util.List;

import com.cspinformatique.wevan.backend.entity.Contract;
import com.cspinformatique.wevan.entity.ElixirAudit;

public interface ElixirAuditService {
	public List<ElixirAudit> findAuditOnErrorSince(Date timestamp);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, Contract contract);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, Contract contract, String cause);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, Contract contract, Throwable ex);
}
