package com.cspinformatique.wevan.backend.entity;

public class WevanReservation {
	private String agency;
	private String creationDate;
	private String editionDate;
	private User user;
	private Payment payment;
	private EditableInfo editableInfo;
	
	public WevanReservation(){
		
	}
	
	public WevanReservation(
		String agency, 
		String creationDate, 
		String editionDate,
		User user, 
		Payment payment, 
		EditableInfo editableInfo
	) {
		this.agency = agency;
		this.creationDate = creationDate;
		this.editionDate = editionDate;
		this.user = user;
		this.payment = payment;
		this.editableInfo = editableInfo;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getEditionDate() {
		return editionDate;
	}

	public void setEditionDate(String editionDate) {
		this.editionDate = editionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public EditableInfo getEditableInfo() {
		return editableInfo;
	}

	public void setEditableInfo(EditableInfo editableInfo) {
		this.editableInfo = editableInfo;
	}
	
}
