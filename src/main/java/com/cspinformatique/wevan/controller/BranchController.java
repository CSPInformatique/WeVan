package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.Branch;
import com.cspinformatique.wevan.entity.Contract;
import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.service.ContractService;
import com.cspinformatique.wevan.service.VehiculeService;

@Controller("/branch")
public class BranchController {
	@Autowired private ContractService contractService;
	@Autowired private VehiculeService vehiculeService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/{branch}/vehicule")
	public @ResponseBody List<Vehicule> getBranchVehicule(@PathVariable Branch branch){
		return this.vehiculeService.getVehiculesByBranch(branch);
	}
	
	@RequestMapping("/{branch}/contract")
	public @ResponseBody Contract getNewContract(@PathVariable Branch branch){
		return this.contractService.generateNewContract(branch);
	}
}
