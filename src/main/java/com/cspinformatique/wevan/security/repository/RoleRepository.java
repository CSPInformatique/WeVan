package com.cspinformatique.wevan.security.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cspinformatique.wevan.security.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>{
	public Role findByName(String name);
}
