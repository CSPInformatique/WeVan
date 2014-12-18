package com.cspinformatique.wevan.security.service;

import com.cspinformatique.wevan.security.entity.Role;

public interface RoleService {
	public Role findByName(String name);
	
	public Role findOne(int id);
}
