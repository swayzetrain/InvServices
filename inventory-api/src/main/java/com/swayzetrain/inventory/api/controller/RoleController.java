package com.swayzetrain.inventory.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.service.RoleRequestService;
import com.swayzetrain.inventory.common.model.Role;

@RestController
@RequestMapping("/api/v1/roles")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class RoleController {
	
	@Autowired
	private RoleRequestService roleRequestService;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<List<Role>> getAllRoles(@RequestParam(value = "roleid", required = false) Integer roleId) {
		
		List<Role> roleList = roleRequestService.GetRoles(roleId);
		
		return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
	}
	
}
