package com.cspinformatique.wevan.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cspinformatique.spring.web.error.service.ErrorService;

@Component
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver{
	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);
	
	@Autowired private ErrorService errorService;

	@Override
	public ModelAndView resolveException(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Object handler, 
		Exception exception
	) {
		logger.error("A user has encountered an exception. He will be redirected to oups page.", exception);
		
		errorService.sendEmailAlert(exception);
		
		return new ModelAndView("error/oups");
	}
}