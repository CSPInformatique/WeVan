package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="branch")
public class Branch {
	private int id;
	private String name;
	private String addressNumber;
	private String addressStreet;
	private String postalCode;
	private String city;
	private String country;
	private String phone;
	
	public Branch(){
		
	}

	public Branch(
		int id, 
		String name, 
		String addressNumber,
		String addressStreet, 
		String postalCode, 
		String city,
		String country, 
		String phone
	){
		this.id = id;
		this.name = name;
		this.addressNumber = addressNumber;
		this.addressStreet = addressStreet;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.phone = phone;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
