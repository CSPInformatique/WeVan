package com.cspinformatique.wevan.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cspinformatique.wevan.security.entity.User;

public interface UserService extends UserDetailsService {
	public User findOne(String username);
	
	public User save(User user);
}
