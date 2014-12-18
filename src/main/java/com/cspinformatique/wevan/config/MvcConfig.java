package com.cspinformatique.wevan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@EnableScheduling
@EnableTransactionManagement
@ComponentScan("com.cspinformatique.spring.web.error" )
public class MvcConfig extends WebMvcConfigurerAdapter{
	 @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/resources/**").addResourceLocations("classpath:resources/");
     }
	
	 public @Bean MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		 return new MappingJackson2HttpMessageConverter();
	 }	 
}