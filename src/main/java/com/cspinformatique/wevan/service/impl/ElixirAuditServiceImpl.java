package com.cspinformatique.wevan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.backend.entity.Contract;
import com.cspinformatique.wevan.entity.ElixirAudit;
import com.cspinformatique.wevan.repository.ElixirAuditRepository;
import com.cspinformatique.wevan.service.ElixirAuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElixirAuditServiceImpl implements ElixirAuditService {
	private static final Logger logger = LoggerFactory.getLogger(ElixirAuditServiceImpl.class);
	
	@Autowired
	private ElixirAuditRepository elixirAuditRepository;

	@Override
	public List<ElixirAudit> findAuditOnErrorSince(Date timestamp){
		return this.elixirAuditRepository.findAuditOnErrorSince(timestamp);
	}
	
	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		Contract contract
	){
		this.save(reservationId, contractId, requestedTimestamp, status, contract, "");
	}

	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		Contract contract,
		Throwable cause
	){
		// Error handling.
		String error = null;
		try{
			if(cause != null){
				error = ExceptionUtils.getStackTrace(cause);
			}
		}catch(Exception ex){
			logger.error("Could not extract stack trace from exception.", ex);
			
			error = "Cause could not be transcoded to text.";
		}
		
		this.save(reservationId, contractId, requestedTimestamp, status, contract, error);
	}
	
	@Override
	public void save(
		Long reservationId, 
		long contractId,
		Date requestedTimestamp, 
		String status, 
		Contract contract,
		String cause
	){
		try{
			// Payload hanlding.
			String payload = null;
			try{
				payload = new ObjectMapper().writeValueAsString(contract);
			
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
