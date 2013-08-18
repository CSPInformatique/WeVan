package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.service.VehiculeService;

@Controller
@RequestMapping({"/", "/vehicule"})
public class VehiculeController {
	@Autowired
	private VehiculeService vehiculeService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.DELETE, produces="application/json", value="/{id}")
	public @ResponseBody Integer deleteVehicule(@PathVariable int id){
		this.vehiculeService.deleteVehicule(id);
		
		return id;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(params="branch")
	public @ResponseBody List<Vehicule> getBranchVehicule(@RequestParam int branch){
		return this.vehiculeService.getVehiculesByBranch(branch);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, produces="application/json", value="/{id}")
	public @ResponseBody Vehicule getVehicule(@PathVariable int id){
		return this.vehiculeService.getVehicule(id);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getVehiculePage(){
		return "vehicule";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces="application/json")
	public @ResponseBody List<Vehicule> getVehicules(){
		return this.vehiculeService.getVehicules();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public @ResponseBody Vehicule saveVehicule(@RequestBody Vehicule vehicule){
		this.vehiculeService.saveVehicule(vehicule);
		
		return vehicule;
	}
}
