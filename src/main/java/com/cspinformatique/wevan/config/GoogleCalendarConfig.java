package com.cspinformatique.wevan.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.cspinformatique.google.GoogleCalendar;

@Configuration
@PropertySource("classpath:google/calendar/calendar.properties")
public class GoogleCalendarConfig {
	private String PROPERTY_APPLICATION_NAME = "google.calendar.applicatioName";
	private String PROPERTY_SERVICE_ACCOUNT_EMAIL = "google.calendar.serviceAccountEmail";

	@Resource private Environment env;
	
	public @Bean GoogleCalendar googleCalendar(){
		return new GoogleCalendar(
			env.getRequiredProperty(PROPERTY_APPLICATION_NAME), 
			env.getRequiredProperty(PROPERTY_SERVICE_ACCOUNT_EMAIL), 
			"google/calendar/key.p12"
		);
	}
}
