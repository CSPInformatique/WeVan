package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Contract.Status;
import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.service.BranchService;
import com.cspinformatique.wevan.service.ContractService;
import com.cspinformatique.wevan.service.VehiculeService;

@Controller
@RequestMapping("/branch")
public class BranchController {
	@Autowired private BranchService branchService;
	@Autowired private ContractService contractService;
	@Autowired private VehiculeService vehiculeService;
	
	@RequestMapping(produces="application/json", method=RequestMethod.GET)
	public @ResponseBody List<Branch> findBranchs(){
		return this.branchService.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/{branch}/vehicule")
	public @ResponseBody List<Vehicule> findBranchVehicule(@PathVariable int branch){
		return this.vehiculeService.findByBranch(this.branchService.findOne(branch));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract")
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch
	){
		return this.findBranchContract(branch, null, null, null);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"status"})
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch, 
		@RequestParam List<Status> status
	){
		return this.findBranchContract(branch, status, null, null);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"page", "results"})
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch, 
		@RequestParam Integer page,
		@RequestParam Integer results
	){
		return this.findBranchContract(branch, null, page, results);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"status", "page", "results"})
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch, 
		@RequestParam List<Status> status,
		@RequestParam Integer page,
		@RequestParam Integer results
	){
		if(page == null) page = 0;
		if(results == null) results = 50;
		
		if(status != null){
			return this.contractService.findByBranchAndStatus(branchService.findOne(branch), status, page, results);
		}else{
			return this.contractService.findByBranch(branchService.findOne(branch), page, results);
		}	
	}
}