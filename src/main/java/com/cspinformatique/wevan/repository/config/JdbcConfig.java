package com.cspinformatique.wevan.repository.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cspinformatique.commons.property.PropertyUtil;

@Configuration
@PropertySource("classpath:persistence/datasource.properties")
public class JdbcConfig {
	private static final String datasourcePropertiesfile = "persistence/datasource.properties";
	
	private static String getProperty(String propertyName){
		return PropertyUtil.getProperty(propertyName, datasourcePropertiesfile);
	}
	
	public @Bean DataSource datasource(){
		BasicDataSource datasource = new BasicDataSource();
		
		datasource.setDriverClassName(getProperty("wevan.datasource.driver"));
		datasource.setUrl(getProperty("wevan.datasource.url"));
		datasource.setUsername(getProperty("wevan.datasource.username"));
		datasource.setPassword(getProperty("wevan.datasource.password"));
		
		return datasource;
	}
	
	public @Bean JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(datasource());
	}
}