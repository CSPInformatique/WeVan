package com.cspinformatique.wevan.reservation.entity;

public class EditableInfoDTO {
	private String status;
	private String startDate;
	private boolean eveningPickup;
	private String endDate;
	private String vehicule;
	private String license;
	
	public EditableInfoDTO(){
		
	}
	
	public EditableInfoDTO(
		String status, 
		String startDate, 
		boolean eveningPickup,
		String endDate, 
		String vehicule, 
		String license
	){
		this.status = status;
		this.startDate = startDate;
		this.eveningPickup = eveningPickup;
		this.endDate = endDate;
		this.vehicule = vehicule;
		this.license = license;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public boolean isEveningPickup() {
		return eveningPickup;
	}

	public void setEveningPickup(boolean eveningPickup) {
		this.eveningPickup = eveningPickup;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getVehicule() {
		return vehicule;
	}

	public void setVehicule(String vehicule) {
		this.vehicule = vehicule;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
}
