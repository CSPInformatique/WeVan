package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contract")
public class Contract {
	private long id;
	private int branchId;
	
	public Contract(){
		
	}
	
	public Contract(long id, int branchId){
		this.id = id;
		this.branchId = branchId;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
}
