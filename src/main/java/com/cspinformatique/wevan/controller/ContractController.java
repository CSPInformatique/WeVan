package com.cspinformatique.wevan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

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
import org.springframework.web.servlet.ModelAndView;

import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.service.ContractService;

@Controller
@Transactional
@RequestMapping({"/", "/contract"})
public class ContractController {
	@Autowired private ContractService contractService;
	
	@Autowired private DataSource datasource;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.DELETE, produces="application/json", value="/{id}")
	public @ResponseBody long deleteContract(@PathVariable long id){
		this.contractService.deleteContract(id);
		
		return id;
	}
	
	@RequestMapping(value="/{contractId}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Contract getContract(@PathVariable long contractId, Model model){		
		return contractService.findOne(contractId);
	}	
	
	@RequestMapping(method=RequestMethod.GET, produces="text/html")
	public String getContractPage(){
		return "contract";
	}
	
	@RequestMapping(value="/{contractId}", method=RequestMethod.GET, produces="text/html")
    public ModelAndView generatePdfReport(@PathVariable int contractId){
		Map<String,Object> model = new HashMap<String,Object>();
 
        model.put("jdbcDataSource", this.datasource);
        model.put("CONTRACT_ID", contractId);
 
        return new ModelAndView("pdfReport", model);
    }
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method={RequestMethod.POST, RequestMethod.PUT}, produces="application/json", value={"", "/{id}"})
	public void saveContract(@RequestBody Contract contract){
		this.contractService.saveContract(contract);
	}
}
