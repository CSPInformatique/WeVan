package com.cspinformatique.wevan.elixir.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.wevan.elixir.entity.ElixirAudit;
import com.cspinformatique.wevan.elixir.service.ElixirAuditService;

@Controller
@RequestMapping("/elixirAudit")
public class ElixirAuditController {
	@Autowired private ElixirAuditService elixirAuditService;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<ElixirAudit> findElixirAudits(){
		return this.elixirAuditService.findAllOrderByFetchTimestampDesc();
	}
}
