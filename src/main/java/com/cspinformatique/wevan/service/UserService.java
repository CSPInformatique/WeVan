package com.cspinformatique.wevan.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cspinformatique.wevan.entity.User;

public interface UserService extends UserDetailsService {
	public User findOne(String username);
}
