package com.cspinformatique.wevan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:security/spring-security.xml")
public class SecurityConfig {

}
