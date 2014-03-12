package com.cspinformatique.wevan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.wevan.entity.Vehicule;
import com.cspinformatique.wevan.service.VehiculeService;

@Controller
@RequestMapping("/vehicule")
public class VehiculeController {
	@Autowired
	private VehiculeService vehiculeService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.DELETE, produces="application/json", value="/{id}")
	public @ResponseBody Integer deleteVehicule(@PathVariable int id){
		this.vehiculeService.delete(id);
		
		return id;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, produces="application/json", value="/{id}")
	public @ResponseBody Vehicule getVehicule(@PathVariable int id){
		return this.vehiculeService.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="text/html")
	public String getVehiculePage(){
		return "vehicule";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces="application/json", method=RequestMethod.GET)
	public @ResponseBody List<Vehicule> getVehicules(){
		return this.vehiculeService.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method={RequestMethod.POST, RequestMethod.PUT}, produces="application/json", value={"", "/{id}"})
	public @ResponseBody Vehicule saveVehicule(@RequestBody Vehicule vehicule){
		this.vehiculeService.save(vehicule);
		
		return vehicule;
	}
}
