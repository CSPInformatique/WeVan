package com.cspinformatique.spring.web.error.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cspinformatique.spring.web.error.service.ErrorService;
import com.cspinformatique.wevan.entity.JsError;

@Controller
@RequestMapping("/error")
public class ErrorController {
	@Autowired private ErrorService errorService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String loadErrorPage(){
		return "error/oups";
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public String handleClientError(@RequestBody JsError jsError){
		this.errorService.sendEmailAlert(jsError);
		
		return "error/oups";
	}
}
