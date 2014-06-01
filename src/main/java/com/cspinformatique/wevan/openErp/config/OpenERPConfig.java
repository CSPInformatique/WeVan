package com.cspinformatique.wevan.openErp.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.cspinformatique.openerp.adapter.OpenERPAdapter;
import com.cspinformatique.openerp.adapter.PartnerAdapter;

@Configuration
@PropertySource("classpath:openerp/openerp.properties")
public class OpenERPConfig {
	@Resource private Environment env;
	
	public @Bean OpenERPAdapter openERPAdapter(){
		return new OpenERPAdapter(
			env.getRequiredProperty(OpenERPAdapter.PROPERTY_KEY_HOST),
			env.getRequiredProperty(OpenERPAdapter.PROPERTY_KEY_PORT, Integer.class),
			env.getRequiredProperty(OpenERPAdapter.PROPERTY_KEY_DATABASE),
			env.getRequiredProperty(OpenERPAdapter.PROPERTY_KEY_USER),
			env.getRequiredProperty(OpenERPAdapter.PROPERTY_KEY_PASSWORD)
		);
	}
	
	public @Bean PartnerAdapter PartnerAdapter(){
		return new PartnerAdapter(openERPAdapter());
	}
}