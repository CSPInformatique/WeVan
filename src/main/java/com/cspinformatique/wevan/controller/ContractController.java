package com.cspinformatique.wevan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.service.ContractService;

@Controller
@RequestMapping("/contract")
public class ContractController {
	@Autowired private ContractService contractService;
	
	@RequestMapping(produces="application/json", params="status")
	public @ResponseBody Contract
	
	@RequestMapping(produces="application/json")
	public @ResponseBody Contract getNewContract(@RequestParam int branch){
		return this.contractService.generateNewContract(branch);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getDemoContratPage(){
		return "contract/demo";
	}
}
