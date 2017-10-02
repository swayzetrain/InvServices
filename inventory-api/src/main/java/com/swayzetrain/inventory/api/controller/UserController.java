package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.UserRequestService;
import com.swayzetrain.inventory.api.service.UserRoleRequestService;
import com.swayzetrain.inventory.common.model.User;

@RestController
@RequestMapping("/api/v1/users")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserController {
	
	@Autowired
	private UserRequestService userRequestService;
	
	@Autowired
	UserRoleRequestService userRoleRequestService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Validated(User.New.class)@RequestBody User user) {
		
		//Create user
		userRequestService.createUser(user);
		
		MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_CREATE_SUCESSFUL_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.OK);
		return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
		
	}
	
}
