package com.cspinformatique.wevan.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cspinformatique.spring.web.error.service.ErrorService;
import com.cspinformatique.wevan.service.impl.ContractServiceImpl;

@Component
public class DefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	@Autowired private ErrorService errorService;
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception){
		logger.error("A user has encountered an exception. He will be redirected to oups page.", exception);
		
		errorService.sendEmailAlert(exception);
		
		return "error/oups";
	}
}