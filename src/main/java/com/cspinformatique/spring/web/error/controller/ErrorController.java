package com.cspinformatique.spring.web.error.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.spring.web.error.entity.JsError;
import com.cspinformatique.spring.web.error.service.ErrorService;

@Controller
@RequestMapping("/error")
public class ErrorController {
	@Autowired private ErrorService errorService;
	
	@RequestMapping(produces="application/json", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean handleClientError(@RequestBody JsError jsError, HttpServletRequest request){
		jsError.setUserAgent(request.getRemoteAddr());
		
		this.errorService.sendEmailAlert(jsError);
		
		return true;
	}
}
