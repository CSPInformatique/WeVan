package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vehicule")
public class Vehicule {
	private int id;
	private Branch branch;
	private String name;
	private int number;
	private String model;
	private String registration;
	
	public Vehicule() {
		
	}

	public Vehicule(int id, Branch branch, String name, int number, String model, String registration) {
		this.id = id;
		this.branch = branch;
		this.name = name;
		this.number = number;
		this.model = model;
		this.registration = registration;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}
}
