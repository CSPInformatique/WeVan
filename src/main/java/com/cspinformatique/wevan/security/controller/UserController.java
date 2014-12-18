package com.cspinformatique.wevan.security.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.wevan.security.entity.User;
import com.cspinformatique.wevan.security.service.UserService;

@Controller
@RequestMapping({"", "/user"})
public class UserController {
	@Autowired private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody User getUser(Principal principal){
		return this.userService.findOne(principal.getName()); 
	}
	
	@RequestMapping("/login")
	public String getLoginPage(){
		return "login";
	}
}