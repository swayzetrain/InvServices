package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.service.user.UserWriter;
import com.swayzetrain.inventory.common.model.User;

@RestController
@RequestMapping("/api/v1/users")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserController {
	
	@Autowired
	private UserWriter userWriter;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Validated(User.New.class)@RequestBody User user) {
		
		return userWriter.CreateUser(user);
		
	}
	
}
