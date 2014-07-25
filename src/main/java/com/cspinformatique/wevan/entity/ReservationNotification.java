package com.cspinformatique.wevan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class ReservationNotification {
	public enum Status{
		ON_ERROR, NEW, PROCESSED 
	}
	
	public long id;
	public long reservationId;
	public Date timestamp;
	public Status status;
	
	public ReservationNotification(){
		
	}
	
	public ReservationNotification(long id, long reservationId, Date timestamp, Status status) {
		this.id = id;
		this.reservationId = reservationId;
		this.timestamp = timestamp;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
