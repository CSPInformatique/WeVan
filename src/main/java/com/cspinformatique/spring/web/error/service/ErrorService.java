package com.cspinformatique.spring.web.error.service;

import com.cspinformatique.spring.web.error.entity.JsError;

public interface ErrorService {
	public void sendEmailAlert(Exception exception);
	
	public void sendEmailAlert(JsError jsError);
	
}
