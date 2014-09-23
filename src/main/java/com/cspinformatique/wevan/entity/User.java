package com.cspinformatique.wevan.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="user")
public class User implements UserDetails {
	private static final long serialVersionUID = 487179158734855592L;
	
	private Branch branch;
	private String username;
	private String password;
	private List<Role> roles;
	
	public User(){
		
	}
	
	public User(Branch branch, String username, String password, List<Role> roles) {
		this.branch = branch;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	@Id
	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ManyToOne
	@JoinColumn(name="branch")
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="userrole", 
		joinColumns=@JoinColumn(name="user", referencedColumnName="username"), 
		inverseJoinColumns=@JoinColumn(name="role", referencedColumnName="id")
	)
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	@Transient
	public List<Role> getAuthorities() {
		return this.getRoles();
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return true;
	}
}
