package com.swayzetrain.inventory.controller;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.auth.PasswordDecoder;
import com.swayzetrain.inventory.auth.PasswordEncoder;
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
		
		if (null == user) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user does not exist");
		}
		
		PasswordDecoder passwordDecoder = new PasswordDecoder();
		boolean passwordMatch = passwordDecoder.decodePassword(password, user.getPassword());
		
		if(!passwordMatch) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid username or password");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("user authentication successful");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		PasswordEncoder passwordEncoder = new PasswordEncoder();
		user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
		
		user.setDatecreated(setTimestamp());
		user.setDatemodified(setTimestamp());
		
		userRepository.save(user);
		
		return ResponseEntity.status(HttpStatus.OK).body("user was successfully created");
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
}
