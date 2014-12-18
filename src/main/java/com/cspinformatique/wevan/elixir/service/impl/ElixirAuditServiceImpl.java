package com.cspinformatique.wevan.elixir.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.elixir.entity.ElixirAudit;
import com.cspinformatique.wevan.elixir.repository.ElixirAuditRepository;
import com.cspinformatique.wevan.elixir.service.ElixirAuditService;
import com.cspinformatique.wevan.reservation.entity.ReservationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElixirAuditServiceImpl implements ElixirAuditService {
	private static final Logger logger = LoggerFactory.getLogger(ElixirAuditServiceImpl.class);
	
	@Autowired
	private ElixirAuditRepository elixirAuditRepository;

	@Override
	public List<ElixirAudit> findAllOrderByFetchTimestampDesc(){
		return this.elixirAuditRepository.findAllOrderByFetchTimestampDesc();
	}
	
	@Override
	public List<ElixirAudit> findAuditOnErrorSince(Date timestamp){
		return this.elixirAuditRepository.findAuditsOnErrorSince(timestamp);
	}
	
	@Override
	public List<ElixirAudit> findAuditsOnWaiting(){
		return this.elixirAuditRepository.findAuditsOnWaiting();
	}
	
	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		ReservationDTO reservationDTO
	){
		this.save(reservationId, contractId, requestedTimestamp, status, reservationDTO, "");
	}

	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		ReservationDTO reservationDTO,
		Throwable cause
	){		
		this.save(reservationId, contractId, requestedTimestamp, status, reservationDTO, ExceptionUtils.getStackTrace(cause));
	}
	
	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		ReservationDTO reservationDTO,
		String cause
	){
		try{
			// Payload hanlding.
			String payload = null;
			try{
				payload = new ObjectMapper().writeValueAsString(reservationDTO);
			
			}catch(JsonProcessingException jsonProcessingEx){
				logger.error("Payload will not be transcoded to JSON.", jsonProcessingEx);
				
				payload = "Payload could not be deserialized";
			}
			
			// Persisting the calculated audit.
			this.elixirAuditRepository.save(
				new ElixirAudit(
					reservationId, 
					contractId, 
					new Date(), 
					requestedTimestamp, 
					status, 
					payload, 
					cause
				)
			);
		}catch(Exception ex){
			logger.error("Could not persist Elixis Audit for reservation " + reservationId + ". Audit will be skipped.", ex);
		}
	}
}
