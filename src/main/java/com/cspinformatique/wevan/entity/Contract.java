package com.cspinformatique.wevan.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="contract")
public class Contract {
	public enum Status{
		OPEN, IN_PROGRESS, COMPLETED
	}
	
	private long id;
	private Long reservationId;
	private Branch branch;
	private Date creationDate;
	private Date editionDate;
	private Driver driver;
	private Date startDate;
	private Date endDate;
	private String kilometersPackage;
	private double amountAlreadyPaid;
	private double totalAmount;
	private Vehicule vehicule;
	private double deductible;
	private double deposit;
	private List<Driver> additionalDrivers;
	private List<Option> options;
	private boolean showOptionsPrices;
	
	public Contract(){
		
	}

	public Contract(
		long id, 
		Long reservationId,
		Branch branch, 
		Date creationDate,
		Date editionDate,
		Status status,
		Driver driver, 
		Date startDate,
		Date endDate, 
		String kilometersPackage, 
		double amountAlreadyPaid,
		double totalAmount,
		Vehicule vehicule, 
		double deductible, 
		double deposit,
		List<Driver> additionalDrivers,
		List<Option> options,
		boolean showOptionsPrices
	) {
		this.id = id;
		this.reservationId = reservationId;
		this.branch = branch;
		this.creationDate = creationDate;
		this.editionDate = editionDate;
		this.driver = driver;
		this.startDate = startDate;
		this.endDate = endDate;
		this.kilometersPackage = kilometersPackage;
		this.amountAlreadyPaid = amountAlreadyPaid;
		this.totalAmount = totalAmount;
		this.vehicule = vehicule;
		this.deductible = deductible;
		this.deposit = deposit;
		this.additionalDrivers = additionalDrivers;
		this.options = options;
		this.showOptionsPrices = showOptionsPrices;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	@ManyToOne
	@JoinColumn(name="branchId")
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEditionDate() {
		return editionDate;
	}

	public void setEditionDate(Date editionDate) {
		this.editionDate = editionDate;
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

	public String getKilometersPackage() {
		return kilometersPackage;
	}

	public void setKilometersPackage(String kilometersPackage) {
		this.kilometersPackage = kilometersPackage;
	}

	public double getAmountAlreadyPaid() {
		return amountAlreadyPaid;
	}

	public void setAmountAlreadyPaid(double amountAlreadyPaid) {
		this.amountAlreadyPaid = amountAlreadyPaid;
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
    @OneToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
	public List<Driver> getAdditionalDrivers() {
		return additionalDrivers;
	}

	public void setAdditionalDrivers(List<Driver> additionalDrivers) {
		this.additionalDrivers = additionalDrivers;
	}


    @OneToMany(cascade=CascadeType.ALL, mappedBy="contract")
    @LazyCollection(LazyCollectionOption.FALSE)
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public boolean isShowOptionsPrices() {
		return showOptionsPrices;
	}

	public void setShowOptionsPrices(boolean showOptionsPrices) {
		this.showOptionsPrices = showOptionsPrices;
	}
}
