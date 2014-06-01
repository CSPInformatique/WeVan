package com.cspinformatique.wevan.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:wevan.properties")
public class WevanConfig {
	private static String PROPERTY_KEY_OPENERP_ACTIVATED = "com.cspinformatique.wevan.openerp.activated";
	
	@Resource private Environment env;
	
	private boolean openerpActivated;
	
	@PostConstruct
	public void init(){
		this.openerpActivated = env.getRequiredProperty(PROPERTY_KEY_OPENERP_ACTIVATED, Boolean.class);
	}
	
	public boolean isOpenerpActivated(){
		return this.openerpActivated;
	}
}
