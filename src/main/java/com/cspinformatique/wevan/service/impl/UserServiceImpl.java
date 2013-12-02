package com.cspinformatique.wevan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.entity.User;
import com.cspinformatique.wevan.repository.UserRepository;
import com.cspinformatique.wevan.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired private UserRepository userRepository;

	@Override 
	public User findOne(String username){
		User user = this.userRepository.findOne(username);
		
		user.setPassword(null);
		
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try{
			return userRepository.findOne(username);
		}catch(EmptyResultDataAccessException emptyResultDataAccessEx){
			throw new UsernameNotFoundException("Username " + username + " could not be found.", emptyResultDataAccessEx);
		}
	}
}
