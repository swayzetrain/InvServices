package com.swayzetrain.inventory.api.controller;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.services.PasswordHandler;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		PasswordHandler passwordEncoder = new PasswordHandler();
		user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
		
		user.setDatecreated(setTimestamp());
		user.setDatemodified(setTimestamp());
		
		userRepository.save(user);
		
		MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_CREATE_SUCESSFUL_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.OK);
		return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
}
