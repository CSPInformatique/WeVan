package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="driver")
public class Driver {
	private int id;
	private String corporateName;
	private String firstName;
	private String lastName;
	private String driverLicense;
	
	private String phone;
	private String email;
	private String address;
	private String address2;
	private String postalCode;
	private String city;
	private String country;
	private String civility;
	
	
	public Driver(){
		
	}

	public Driver(
		int id, 
		String corporateName, 
		String firstName,
		String lastName, 
		String driverLicense,
		String phone,
		String email,
		String address,
		String address2,
		String postalCode,
		String city,
		String country,
		String civility
	) {
		this.id = id;
		this.corporateName = corporateName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.driverLicense = driverLicense;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.address2 = address2;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.civility = civility;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getCivility() {
		return civility;
	}

	public void setCivility(String civility) {
		this.civility = civility;
	}
}
