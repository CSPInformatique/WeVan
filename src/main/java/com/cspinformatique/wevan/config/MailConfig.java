package com.cspinformatique.wevan.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	private static String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private static String MAIL_SMTPS_AUTH = "mail.smtps.auth";
	private static String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
	private static String MAIL_DEBUG = "mail.debug";
	
	private static String MAIL_HOST = "mail.host";
	private static String MAIL_PORT = "mail.port";
	private static String MAIL_PROTOCOL = "mail.protocol";
	private static String MAIL_USERNAME = "mail.username";
	private static String MAIL_PASSWORD = "mail.password";
	
	@Resource private Environment env;
	
	public @Bean Properties javaMailProperties(){
		Properties javaMailProperties = new Properties();

		javaMailProperties.put(MAIL_TRANSPORT_PROTOCOL, env.getRequiredProperty(MAIL_TRANSPORT_PROTOCOL));
		javaMailProperties.put(MAIL_SMTPS_AUTH, env.getRequiredProperty(MAIL_SMTPS_AUTH, Boolean.class));
		javaMailProperties.put(MAIL_SMTP_SSL_ENABLE, env.getRequiredProperty(MAIL_SMTP_SSL_ENABLE, Boolean.class));
		javaMailProperties.put(MAIL_DEBUG, env.getRequiredProperty(MAIL_DEBUG, Boolean.class));
		
		return javaMailProperties;
	}
	
	public @Bean JavaMailSender javaMailSender(){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost(env.getRequiredProperty(MAIL_HOST));;
		javaMailSender.setPort(env.getRequiredProperty(MAIL_PORT, Integer.class));
		javaMailSender.setProtocol(env.getRequiredProperty(MAIL_PROTOCOL));
		javaMailSender.setUsername(env.getRequiredProperty(MAIL_USERNAME));
		javaMailSender.setPassword(env.getRequiredProperty(MAIL_PASSWORD));
		
		javaMailSender.setJavaMailProperties(javaMailProperties());
		
		return javaMailSender;
	}
}
