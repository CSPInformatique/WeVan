package com.cspinformatique.wevan.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.wevan.entity.User;
import com.cspinformatique.wevan.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired private UserService userService;
	
	public @ResponseBody User getUser(Principal principal){
		return this.userService.findOne(principal.getName()); 
	}
}