package com.cspinformatique.wevan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.cspinformatique.wevan.security.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private javax.sql.DataSource dataSource;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			.formLogin().loginPage("/login").loginProcessingUrl("/authenticate")
			.usernameParameter("agency").passwordParameter("password")
			.defaultSuccessUrl("/contract").and()
			
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
			
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/css**").permitAll()
			.antMatchers("/img/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/lib/**").permitAll()
			.antMatchers("/**").access("isAuthenticated()").and()
			
			.csrf().disable();
	}
}
