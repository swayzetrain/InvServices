package com.swayzetrain.inventory.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.model.UserRolePostRequest;
import com.swayzetrain.inventory.api.service.UserRoleRequestService;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@RestController
@RequestMapping("api/v1/userroles")
public class UserRoleController {

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserRoleRequestService userRoleRequestService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserRole>> getAllUserRoles() {
		
		ArrayList<UserRole> userRoleList = (ArrayList<UserRole>) userRoleRepository.findAll();
		
		return new ResponseEntity<List<UserRole>>(userRoleList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserRole> getUserRoleByUserId(@PathVariable(value = "userId") Integer userId) {
		
		UserRole userRole = userRoleRepository.findByUserid(userId);
		
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{userRoleId}", method = RequestMethod.GET)
	public ResponseEntity<UserRole> getUserRoleByUserRoleId(@PathVariable(value = "userRoleId") Integer userRoleId) {
		
		UserRole userRole = userRoleRepository.findByUserroleid(userRoleId);
		
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
	public ResponseEntity<UserRole> getUserRoleByRoleId(@PathVariable(value = "roleId") Integer roleId) {
		
		UserRole userRole = userRoleRepository.findByRoleid(roleId);
		
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addUserRole(@RequestBody UserRolePostRequest userRolePostRequest) {
		
		
		ResponseEntity<?> establishUserRoleObject = userRoleRequestService.establishUserRoleObject(userRolePostRequest);
		
		if (null != establishUserRoleObject) {
			return establishUserRoleObject;
		}
		
		
		if (userRoleRequestService.checkUserRoleExists(userRolePostRequest.getUserid())) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USERROLE_EXISTS_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.CONFLICT);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		
		//Convert userRolePostRequest to UserRole and store it in the database
		UserRole newUserRole = new UserRole(userRolePostRequest.getUserid(), userRolePostRequest.getRoleid(), setTimestamp(), setTimestamp());
		UserRole userRoleResponse = userRoleRepository.save(newUserRole);
		
		return new ResponseEntity<>(userRoleResponse, HttpStatus.OK);
		
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
}
