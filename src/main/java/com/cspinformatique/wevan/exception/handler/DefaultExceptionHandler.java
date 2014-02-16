package com.cspinformatique.wevan.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cspinformatique.wevan.service.impl.ContractServiceImpl;

@Component
public class DefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	public String handleException(Exception ex){
		return "error/message";
	}
}