package com.swayzetrain.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.model.Role;
import com.swayzetrain.inventory.service.RoleRequestService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
	
	@Autowired
	private RoleRequestService roleRequestService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Role>> getAllRoles(@RequestParam(value = "roleid", required = false) Integer roleId) {
		
		List<Role> roleList = roleRequestService.GetRoles(roleId);
		
		return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
	}
	
}
