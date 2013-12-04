package com.cspinformatique.wevan.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configurable
public class DomainClassConfig extends WebMvcConfigurationSupport{
	public @Bean DomainClassConverter<?> domainClassConverter(){
        return new DomainClassConverter<FormattingConversionService>(
               this.mvcConversionService()
       );
	}
}
