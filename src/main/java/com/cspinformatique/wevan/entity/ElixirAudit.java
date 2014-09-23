package com.cspinformatique.wevan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="elixiraudit")
public class ElixirAudit {
	private Long reservationId;
	private long contractId;
	private Date fetchTimestamp;
	private Date requestedTimestamp;
	private String status;
	private String payload;
	private String error;
	
	public ElixirAudit(){
		
	}
	
	public ElixirAudit(
		Long reservationId, 
		long contractId,
		Date fetchTimestamp,
		Date requestedTimestamp, 
		String status,
		String payload, 
		String error
	) {
		this.reservationId = reservationId;
		this.contractId = contractId;
		this.fetchTimestamp = fetchTimestamp;
		this.requestedTimestamp = requestedTimestamp;
		this.status = status;
		this.payload = payload;
		this.error = error;
	}

	@Id
	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public Date getFetchTimestamp() {
		return fetchTimestamp;
	}

	public void setFetchTimestamp(Date fetchTimestamp) {
		this.fetchTimestamp = fetchTimestamp;
	}

	public Date getRequestedTimestamp() {
		return requestedTimestamp;
	}

	public void setRequestedTimestamp(Date requestedTimestamp) {
		this.requestedTimestamp = requestedTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	
}