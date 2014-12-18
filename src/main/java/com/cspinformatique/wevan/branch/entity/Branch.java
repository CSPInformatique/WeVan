package com.cspinformatique.wevan.branch.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="branch")
public class Branch {
	private int id;
	private String name;
	private boolean active;
	private String addressNumber;
	private String addressStreet;
	private String companyName;
	private String companyType;
	private String euVatNumber;
	private String headOffice;
	private String postalCode;
	private String city;
	private String country;
	private String phone;
	private String registration;
	private Date registrationDate;
	private String registrationLocation;
	private String siret;
	private String googleEmailAccount;
	
	public Branch(){
		
	}

	public Branch(
		int id, 
		String name, 
		boolean active,
		String addressNumber,
		String addressStreet, 
		String companyName,
		String companyType,
		String euVatNumber,
		String headOffice,
		String postalCode, 
		String city,
		String country, 
		String phone,
		String registration,
		Date registrationDate,
		String registrationLocation,
		String siret,
		String googleEmailAccount
	){
		this.id = id;
		this.name = name;
		this.active = active;
		this.addressNumber = addressNumber;
		this.addressStreet = addressStreet;
		this.companyName = companyName;
		this.companyType = companyType;
		this.headOffice = headOffice;
		this.euVatNumber = euVatNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.registration = registration;
		this.registrationDate = registrationDate;
		this.registrationLocation = registrationLocation;
		this.siret = siret;
		this.googleEmailAccount = googleEmailAccount;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getEuVatNumber() {
		return euVatNumber;
	}

	public void setEuVatNumber(String euVatNumber) {
		this.euVatNumber = euVatNumber;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
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

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRegistrationLocation() {
		return registrationLocation;
	}

	public void setRegistrationLocation(String registrationLocation) {
		this.registrationLocation = registrationLocation;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getGoogleEmailAccount() {
		return googleEmailAccount;
	}

	public void setGoogleEmailAccount(String googleEmailAccount) {
		this.googleEmailAccount = googleEmailAccount;
	}
}
