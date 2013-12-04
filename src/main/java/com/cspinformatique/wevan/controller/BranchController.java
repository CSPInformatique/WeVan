package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public @ResponseBody List<Branch> getBranchs(){
		return this.branchService.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/{branch}/vehicule")
	public @ResponseBody List<Vehicule> getBranchVehicule(@PathVariable int branch){
		return this.vehiculeService.getVehiculesByBranch(this.branchService.findOne(branch));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract")
	public @ResponseBody List<Contract> getByBranch(@PathVariable int branch){
		return this.contractService.findByBranch(branchService.findOne(branch));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{branch}/contract", params={"status"})
	public @ResponseBody List<Contract> getByBranch(@PathVariable int branch, @RequestParam List<Status> status){
		return this.contractService.findByBranchAndStatus(branchService.findOne(branch), status);
	}
}