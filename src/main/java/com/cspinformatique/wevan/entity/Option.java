package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="option")
public class Option {
	public static final String LABEL_ADDITIONAL_DRIVER = "Conducteur(s) additionnel(s)";
	public static final String LABEL_BEDS = "Lits";
	public static final String LABEL_CANCEL_INSURANCE = "Assurance annulation";
	public static final String LABEL_CAR_RACK = "Porte-bagages";
	public static final String LABEL_CHILD_SEAT = "Siège enfant";
	public static final String LABEL_CLEANING = "Nettoyage";
	public static final String LABEL_GPS = "GPS";
	public static final String LABEL_PARTIAL_DEDUCTIBLE = "Rachat Partiel de Franchise";
	public static final String LABEL_WINTER_TIRES = "Pneus d'hiver";
	public static final String LABEL_YOUNG_DRIVER = "Jeune conducteur";
	
	private long id;
	private long contract;
	private boolean active;
	private String label;
	private double amount;
	
	public Option(){
		
	}

	public Option(long id, long contract, boolean active, String label, double amount) {
		this.id = id;
		this.contract = contract;
		this.active = active;
		this.label = label;
		this.amount = amount;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getContract() {
		return contract;
	}

	public void setContract(long contract) {
		this.contract = contract;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}