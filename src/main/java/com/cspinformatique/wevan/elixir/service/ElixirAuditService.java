package com.cspinformatique.wevan.elixir.service;

import java.util.Date;
import java.util.List;

import com.cspinformatique.wevan.elixir.entity.ElixirAudit;
import com.cspinformatique.wevan.reservation.entity.ReservationDTO;

public interface ElixirAuditService {
	public List<ElixirAudit> findAllOrderByFetchTimestampDesc();
	
	public List<ElixirAudit> findAuditOnErrorSince(Date timestamp);
	
	public List<ElixirAudit> findAuditsOnWaiting();
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, ReservationDTO reservationDTO);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, ReservationDTO reservationDTO, String cause);
	
	public void save(Long reservationId, long contractId, Date requestedTimestamp, String status, ReservationDTO reservationDTO, Throwable ex);
}