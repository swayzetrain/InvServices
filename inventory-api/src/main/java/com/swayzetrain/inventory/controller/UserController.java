package com.swayzetrain.inventory.controller;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.auth.PasswordDecoder;
import com.swayzetrain.inventory.auth.PasswordEncoder;
import com.swayzetrain.inventory.enums.Constants;
import com.swayzetrain.inventory.model.MessageResponse;
import com.swayzetrain.inventory.model.User;
import com.swayzetrain.inventory.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> authenticateUser(@RequestHeader(value = "username") String username, @RequestHeader(value = "password") String password) {
		
		User user = userRepository.findByUsername(username);
		MessageResponse messageResponse;
		
		if (null == user) {
			
			messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		PasswordDecoder passwordDecoder = new PasswordDecoder();
		boolean passwordMatch = passwordDecoder.decodePassword(password, user.getPassword());
		
		if(!passwordMatch) {
			
			messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_AUTHENTICATION_FAILED_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.FORBIDDEN);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());

		}
		
		messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_AUTHENTICATION_SUCCESSFUL_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.OK);
		return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		PasswordEncoder passwordEncoder = new PasswordEncoder();
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
