package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.service.role.RoleReader;

@RestController
@RequestMapping("/api/v1/roles")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class RoleController {
	
	@Autowired
	private RoleReader roleReader;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<?> getAllRoles(@RequestParam(value = "roleid", required = false) Integer roleId) {
		
		return roleReader.GetRoles(roleId);
	}
	
}
