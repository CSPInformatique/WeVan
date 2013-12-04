package com.cspinformatique.wevan.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.service.ContractService;

@Controller
@Transactional
@RequestMapping("/contract")
public class ContractController {
	@Autowired private ContractService contractService;
	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.DELETE, produces="application/json", value="/{id}")
	public @ResponseBody long deleteContract(@PathVariable long id){
		this.contractService.deleteContract(id);
		
		return id;
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="text/html")
	public String getContractPage(){
		return "contract";
	}
	
	@RequestMapping(value="/{contractId}", method=RequestMethod.GET, produces="text/html")
	public String printContract(@PathVariable long contractId, Model model){
		model.addAttribute("contract", contractService.findOne(contractId));
		model.addAttribute("date", new Date());
		
		return "contract/print";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method={RequestMethod.POST, RequestMethod.PUT}, produces="application/json", value={"", "/{id}"})
	public @ResponseBody Contract saveContract(@RequestBody Contract contract){
		return this.contractService.saveContract(contract);
	}
}
