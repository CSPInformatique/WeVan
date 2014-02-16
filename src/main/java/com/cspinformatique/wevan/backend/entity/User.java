package com.cspinformatique.wevan.backend.entity;

public class User {
	private String language;
	private String civility;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String address;
	private String address2;
	private String postalCode;
	private String city;
	private String country;
	private String company;
	
	public User(){
		
	}
	
	public User(
		String language, 
		String civility, 
		String firstName,
		String lastName,
		String phone, 
		String email, 
		String address, 
		String address2,
		String postalCode, 
		String city, 
		String country, 
		String company
	){
		this.language = language;
		this.civility = civility;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.address2 = address2;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.company = company;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCivility() {
		return civility;
	}

	public void setCivility(String civility) {
		this.civility = civility;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}	
}
