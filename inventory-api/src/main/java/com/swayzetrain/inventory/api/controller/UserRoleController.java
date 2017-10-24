package com.swayzetrain.inventory.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.model.UserRolePostRequest;
import com.swayzetrain.inventory.api.service.userrole.UserRoleReader;
import com.swayzetrain.inventory.api.service.userrole.UserRoleWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.UserRole;

@RestController
@RequestMapping("api/v1/userroles")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserRoleController {
	
	@Autowired
	private UserRoleReader userRoleReader;
	
	@Autowired
	private UserRoleWriter userRoleWriter;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<List<UserRole>> getAllUserRoles(@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return userRoleReader.GetAllUserRole(userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<?> getUserRoleByUserId(@PathVariable(value = "userId") Integer userId, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return userRoleReader.GetUserRoleByUser(userAuthorizationDetails, userId);
		
	}
	
	@RequestMapping(value = "/id/{userRoleId}", method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<?> getUserRoleByUserRoleId(@PathVariable(value = "userRoleId") Integer userRoleId,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return userRoleReader.GetUserRoleByUserRole(userAuthorizationDetails, userRoleId);
		
	}
	
	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<?> getUserRoleByRoleId(@PathVariable(value = "roleId") Integer roleId, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return userRoleReader.GetUserRoleByRole(userAuthorizationDetails, roleId);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Admin"})
	public ResponseEntity<?> addUserRole(@Validated(UserRole.New.class)@RequestBody UserRolePostRequest userRolePostRequest, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return userRoleWriter.AddUserRole(userAuthorizationDetails, userRolePostRequest);		
		
	}
	
}
