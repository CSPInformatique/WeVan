package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.service.VehiculeService;

@Controller
@RequestMapping("/vehicule")
public class VehiculeController {
	@Autowired
	private VehiculeService vehiculeService;
	
	@RequestMapping(params="branch")
	public @ResponseBody List<Vehicule> getBranchVehicule(@RequestParam int branch){
		return this.vehiculeService.getVehiculesByBranch(branch);
	}
}
