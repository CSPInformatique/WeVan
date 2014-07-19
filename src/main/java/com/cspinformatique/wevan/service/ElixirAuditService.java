package com.cspinformatique.wevan.service;

import java.util.Date;
import java.util.List;

import com.cspinformatique.wevan.backend.entity.WevanReservation;
import com.cspinformatique.wevan.entity.ElixirAudit;

public interface ElixirAuditService {
	public List<ElixirAudit> findAllOrderByFetchTimestampDesc();
	
	public List<ElixirAudit> findAuditOnErrorSince(Date timestamp);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, WevanReservation wevanReservation);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, WevanReservation wevanReservation, String cause);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, WevanReservation wevanReservation, Throwable ex);
}
