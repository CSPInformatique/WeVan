package com.cspinformatique.wevan.thymeleaf.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.thymeleaf.service.ThymeleafService;

@Service(value="thymeleafService")
public class ThymeleafServiceImpl implements ThymeleafService {
	private DateFormat dateFormat;
	
	public ThymeleafServiceImpl() {
		this.dateFormat = new SimpleDateFormat("yyyy");
	}
	
	@Override
	public String getYear() {
		return this.dateFormat.format(new Date());
	}

}
