package com.cspinformatique.wevan.reservation.entity;


public class ReservationDTO {
	private String agency;
	private String creationDate;
	private String editionDate;
	private UserDTO userDTO;
	private PaymentDTO paymentDTO;
	private EditableInfoDTO editableInfoDTO;
	
	public ReservationDTO(){
		
	}
	
	public ReservationDTO(
		String agency, 
		String creationDate, 
		String editionDate,
		UserDTO userDTO, 
		PaymentDTO paymentDTO, 
		EditableInfoDTO editableInfoDTO
	) {
		this.agency = agency;
		this.creationDate = creationDate;
		this.editionDate = editionDate;
		this.userDTO = userDTO;
		this.paymentDTO = paymentDTO;
		this.editableInfoDTO = editableInfoDTO;
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

	public UserDTO getUser() {
		return userDTO;
	}

	public void setUser(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public PaymentDTO getPayment() {
		return paymentDTO;
	}

	public void setPayment(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public EditableInfoDTO getEditableInfo() {
		return editableInfoDTO;
	}

	public void setEditableInfo(EditableInfoDTO editableInfoDTO) {
		this.editableInfoDTO = editableInfoDTO;
	}
	
}
