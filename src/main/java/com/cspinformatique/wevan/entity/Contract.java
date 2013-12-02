package com.cspinformatique.wevan.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="contract")
public class Contract {
	public enum Status{
		OPEN, IN_PROGRESS, COMPLETED, CANCELLED
	}
	
	private long id;
	private Branch branch;
	private Status status;
	private Driver driver;
	private Date startDate;
	private Date endDate;
	private int kilometers;
	private double totalAmount;
	private Vehicule vehicule;
	private double deductible;
	private double deposit;
	private List<Driver> additionalDrivers;
	
	public Contract(){
		
	}

	public Contract(
		long id, 
		Branch branch, 
		Status status,
		Driver driver, 
		Date startDate,
		Date endDate, 
		int kilometers, 
		double totalAmount,
		Vehicule vehicule, 
		double deductible, 
		double deposit,
		List<Driver> additionalDrivers
	) {
		this.id = id;
		this.branch = branch;
		this.status = status;
		this.driver = driver;
		this.startDate = startDate;
		this.endDate = endDate;
		this.kilometers = kilometers;
		this.totalAmount = totalAmount;
		this.vehicule = vehicule;
		this.deductible = deductible;
		this.deposit = deposit;
		this.additionalDrivers = additionalDrivers;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@OneToOne
	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getKilometers() {
		return kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ManyToOne
	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public double getDeductible() {
		return deductible;
	}

	public void setDeductible(double deductible) {
		this.deductible = deductible;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	@OneToMany
    @JoinTable(
	    name="contractAdditionalDriver",
	    joinColumns = @JoinColumn( name="contract"),
	    inverseJoinColumns = @JoinColumn( name="driver")
    )
	public List<Driver> getAdditionalDrivers() {
		return additionalDrivers;
	}

	public void setAdditionalDrivers(List<Driver> additionalDrivers) {
		this.additionalDrivers = additionalDrivers;
	}
}
