package com.cspinformatique.wevan.backend.entity;

public class Payment {
	private String transactionId;
	private String type;
	private double discount;
	private double vehiculeCost;
	private double insurance;
	private String kmPackage;
	private double kmPackageCost;
	private boolean gps;
	private double gpsCost;
	private boolean cleaningPackage;
	private double cleaningPackageCost;
	private boolean cancelOption;
	private double assistanceCost;
	private double partialDeductible;
	private boolean youngDriver;
	private double youngDriverCost;
	private int additionalDrivers;
	private double additionalDriversCost;
	private boolean carRack;
	private double carRackCost;
	private boolean winterTires;
	private double winterTiresCost;
	private boolean childSeat;
	private double childSeatCost;
	private boolean beddingPack;
	private double beddingPackCost;
	private double totalCost;
	private double alreadyPaid;
	
	public Payment(){
		
	}
	
	public Payment(
		String transactionId, 
		String type, 
		double discount,
		double vehiculeCost, 
		double insurance, 
		String kmPackage,
		double kmPackageCost, 
		boolean gps, 
		double gpsCost,
		boolean cleaningPackage, 
		double cleaningPackageCost,
		boolean cancelOption, 
		double assistanceCost,
		double partialDeductible, 
		boolean youngDriver,
		double youngDriverCost, 
		int additionalDrivers,
		double additionalDriversCost, 
		boolean carRack, 
		double carRackCost,
		boolean winterTires, 
		double winterTiresCost, 
		boolean childSeat,
		double childSeatCost, 
		boolean beddingPack, 
		double beddingPackCost,
		double totalCost, 
		double alreadyPaid
	){
		this.transactionId = transactionId;
		this.type = type;
		this.discount = discount;
		this.vehiculeCost = vehiculeCost;
		this.insurance = insurance;
		this.kmPackage = kmPackage;
		this.kmPackageCost = kmPackageCost;
		this.gps = gps;
		this.gpsCost = gpsCost;
		this.cleaningPackage = cleaningPackage;
		this.cleaningPackageCost = cleaningPackageCost;
		this.cancelOption = cancelOption;
		this.assistanceCost = assistanceCost;
		this.partialDeductible = partialDeductible;
		this.youngDriver = youngDriver;
		this.youngDriverCost = youngDriverCost;
		this.additionalDrivers = additionalDrivers;
		this.additionalDriversCost = additionalDriversCost;
		this.carRack = carRack;
		this.carRackCost = carRackCost;
		this.winterTires = winterTires;
		this.winterTiresCost = winterTiresCost;
		this.childSeat = childSeat;
		this.childSeatCost = childSeatCost;
		this.beddingPack = beddingPack;
		this.beddingPackCost = beddingPackCost;
		this.totalCost = totalCost;
		this.alreadyPaid = alreadyPaid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getVehiculeCost() {
		return vehiculeCost;
	}

	public void setVehiculeCost(double vehiculeCost) {
		this.vehiculeCost = vehiculeCost;
	}

	public double getInsurance() {
		return insurance;
	}

	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}

	public String getKmPackage() {
		return kmPackage;
	}

	public void setKmPackage(String kmPackage) {
		this.kmPackage = kmPackage;
	}

	public double getKmPackageCost() {
		return kmPackageCost;
	}

	public void setKmPackageCost(double kmPackageCost) {
		this.kmPackageCost = kmPackageCost;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public double getGpsCost() {
		return gpsCost;
	}

	public void setGpsCost(double gpsCost) {
		this.gpsCost = gpsCost;
	}

	public boolean isCleaningPackage() {
		return cleaningPackage;
	}

	public void setCleaningPackage(boolean cleaningPackage) {
		this.cleaningPackage = cleaningPackage;
	}

	public double getCleaningPackageCost() {
		return cleaningPackageCost;
	}

	public void setCleaningPackageCost(double cleaningPackageCost) {
		this.cleaningPackageCost = cleaningPackageCost;
	}

	public boolean isCancelOption() {
		return cancelOption;
	}

	public void setCancelOption(boolean cancelOption) {
		this.cancelOption = cancelOption;
	}

	public double getAssistanceCost() {
		return assistanceCost;
	}

	public void setAssistanceCost(double assistanceCost) {
		this.assistanceCost = assistanceCost;
	}

	public double getPartialDeductible() {
		return partialDeductible;
	}

	public void setPartialDeductible(double partialDeductible) {
		this.partialDeductible = partialDeductible;
	}

	public boolean isYoungDriver() {
		return youngDriver;
	}

	public void setYoungDriver(boolean youngDriver) {
		this.youngDriver = youngDriver;
	}

	public double getYoungDriverCost() {
		return youngDriverCost;
	}

	public void setYoungDriverCost(double youngDriverCost) {
		this.youngDriverCost = youngDriverCost;
	}

	public int getAdditionalDrivers() {
		return additionalDrivers;
	}

	public void setAdditionalDrivers(int additionalDrivers) {
		this.additionalDrivers = additionalDrivers;
	}

	public double getAdditionalDriversCost() {
		return additionalDriversCost;
	}

	public void setAdditionalDriversCost(double additionalDriversCost) {
		this.additionalDriversCost = additionalDriversCost;
	}

	public boolean isCarRack() {
		return carRack;
	}

	public void setCarRack(boolean carRack) {
		this.carRack = carRack;
	}

	public double getCarRackCost() {
		return carRackCost;
	}

	public void setCarRackCost(double carRackCost) {
		this.carRackCost = carRackCost;
	}

	public boolean isWinterTires() {
		return winterTires;
	}

	public void setWinterTires(boolean winterTires) {
		this.winterTires = winterTires;
	}

	public double getWinterTiresCost() {
		return winterTiresCost;
	}

	public void setWinterTiresCost(double winterTiresCost) {
		this.winterTiresCost = winterTiresCost;
	}

	public boolean isChildSeat() {
		return childSeat;
	}

	public void setChildSeat(boolean childSeat) {
		this.childSeat = childSeat;
	}

	public double getChildSeatCost() {
		return childSeatCost;
	}

	public void setChildSeatCost(double childSeatCost) {
		this.childSeatCost = childSeatCost;
	}

	public boolean isBeddingPack() {
		return beddingPack;
	}

	public void setBeddingPack(boolean beddingPack) {
		this.beddingPack = beddingPack;
	}

	public double getBeddingPackCost() {
		return beddingPackCost;
	}

	public void setBeddingPackCost(double beddingPackCost) {
		this.beddingPackCost = beddingPackCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(double alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}	
}
