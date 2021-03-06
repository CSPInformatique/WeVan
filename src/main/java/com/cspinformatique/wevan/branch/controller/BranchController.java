package com.cspinformatique.wevan.branch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.branch.service.BranchService;
import com.cspinformatique.wevan.contract.entity.Contract;
import com.cspinformatique.wevan.contract.entity.Contract.Status;
import com.cspinformatique.wevan.contract.service.ContractService;
import com.cspinformatique.wevan.referential.entity.Vehicule;
import com.cspinformatique.wevan.referential.service.VehiculeService;

@Controller
@RequestMapping("/branch")
public class BranchController {	
	@Autowired private BranchService branchService;
	@Autowired private ContractService contractService;
	@Autowired private VehiculeService vehiculeService;
	
	@RequestMapping(produces="application/json", method=RequestMethod.GET)
	public @ResponseBody List<Branch> findBranchs(){
		return this.branchService.findActiveBranches();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/{branch}/vehicule")
	public @ResponseBody List<Vehicule> findBranchVehicule(@PathVariable int branch){
		return this.vehiculeService.findByBranch(this.branchService.findOne(branch));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{branch}/contract", params={"fetch", "startDate"}, method=RequestMethod.POST)
	public void fetchBranchContracts(@PathVariable int branch, @RequestParam Date startDate){
		this.contractService.fetchContracts(branch, startDate);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"page", "results", "sortBy", "ascending"})
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch, 
		@RequestParam Integer page,
		@RequestParam Integer results,
		@RequestParam String sortBy,
		@RequestParam Boolean ascending
	){
		return this.findBranchContract(branch, null, page, results, sortBy, ascending);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"status", "page", "results", "sortBy", "ascending"})
	public @ResponseBody Page<Contract> findBranchContract(
		@PathVariable int branch, 
		@RequestParam List<Status> status,
		@RequestParam Integer page,
		@RequestParam Integer results,
		@RequestParam String sortBy,
		@RequestParam Boolean ascending
	){		
		if(page == null) page = 0;
		if(results == null) results = 50;
		if(sortBy == null) sortBy = "startDate";
		if(ascending == null) ascending = true;
		
		PageRequest pageRequest = new PageRequest(page, results, new Sort((ascending) ? Direction.ASC : Direction.DESC, sortBy));
		
		if(status != null){
			return this.contractService.findByBranchAndStatus(branchService.findOne(branch), status, pageRequest);
		}else{
			return this.contractService.findByBranch(branchService.findOne(branch), pageRequest);
		}	
	}
	
	@RequestMapping(produces="text/html", method=RequestMethod.GET)
	public String geBranchesPage(){
		return "branches";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.PUT, value={"", "/{id}"})
	public @ResponseBody Branch saveBranch(@RequestBody Branch branch){
		return this.branchService.saveBranch(branch);
	}
 }
