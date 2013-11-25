package com.cspinformatique.wevan.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="contract")
public class Contract {
	private long id;
	private Branch branch;
	
	public Contract(){
		
	}
	
	public Contract(long id, Branch branch){
		this.id = id;
		this.branch = branch;
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
}
