package com.cspinformatique.wevan.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:persistence/hibernate.properties")
@EnableJpaRepositories(basePackages="com.cspinformatique.wevan")
public class JpaConfig {
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_GLOBALLY_QUOTED_IDENTIFIERS = "hibernate.globally_quoted_identifiers";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.showSql";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "hibernate.packagesToScan";  
    
	@Autowired private DataSource datasource;
	
	@Resource
    private Environment env;
	
	public @Bean LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(datasource);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}
    	
    private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
		properties.put(HIBERNATE_GLOBALLY_QUOTED_IDENTIFIERS, env.getRequiredProperty(HIBERNATE_GLOBALLY_QUOTED_IDENTIFIERS));
		properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
		return properties;	
	}
    
	public @Bean JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
