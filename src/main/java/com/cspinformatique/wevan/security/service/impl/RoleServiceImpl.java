package com.cspinformatique.wevan.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.security.entity.Role;
import com.cspinformatique.wevan.security.repository.RoleRepository;
import com.cspinformatique.wevan.security.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired private RoleRepository roleRepository;
	
	public Role findByName(String name){
		return this.roleRepository.findByName(name);
	}
	
	@Override
	public Role findOne(int id) {
		return this.roleRepository.findOne(id);
	}
}
