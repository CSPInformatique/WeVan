package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="option")
public class Option {
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
