package com.cspinformatique.wevan.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	private String options;
	
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
		List<Driver> additionalDrivers,
		String options
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
		this.options = options;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="branchId")
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

	@JoinColumn(name="driver")
	@OneToOne(cascade=CascadeType.ALL)
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
	@JoinColumn(name="vehicule")
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

    @JoinTable(
	    name="contractAdditionalDriver",
	    joinColumns = @JoinColumn( name="contract", referencedColumnName="id"),
	    inverseJoinColumns = @JoinColumn( name="driver", referencedColumnName="id")
    )
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<Driver> getAdditionalDrivers() {
		return additionalDrivers;
	}

	public void setAdditionalDrivers(List<Driver> additionalDrivers) {
		this.additionalDrivers = additionalDrivers;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
}
