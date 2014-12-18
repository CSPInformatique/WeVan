package com.cspinformatique.wevan.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@PropertySource("classpath:thymeleaf.properties")
public class ThymeleafConfig {
	@Resource Environment env;
	
	public @Bean TemplateResolver defaultTemplateResolver(){
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		
		templateResolver.setPrefix(env.getRequiredProperty("thymeleaf.prefix"));
		templateResolver.setSuffix(env.getRequiredProperty("thymeleaf.suffix"));
		templateResolver.setTemplateMode(env.getRequiredProperty("thymeleaf.templateMode"));
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	
	public @Bean TemplateResolver classLoaderTemplateResolver(){
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		
		templateResolver.setPrefix(env.getRequiredProperty("thymeleaf.prefix"));
		templateResolver.setSuffix(env.getRequiredProperty("thymeleaf.suffix"));
		templateResolver.setTemplateMode(env.getRequiredProperty("thymeleaf.templateMode"));
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	
	public @Bean TemplateResolver servletContextTemplateResolver(){
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		
		templateResolver.setPrefix(env.getRequiredProperty("thymeleaf.prefix"));
		templateResolver.setSuffix(env.getRequiredProperty("thymeleaf.suffix"));
		templateResolver.setTemplateMode(env.getRequiredProperty("thymeleaf.templateMode"));
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	
	public @Bean SpringTemplateEngine templateEngine(){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		
		templateEngine.addDialect(springSecurityDialect());
		templateEngine.setTemplateResolver(defaultTemplateResolver());
		
		return templateEngine;
	}
	
	public @Bean ViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		
		return thymeleafViewResolver;
	}
	
	public @Bean SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}
}
