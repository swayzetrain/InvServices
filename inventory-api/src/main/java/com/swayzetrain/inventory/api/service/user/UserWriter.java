package com.swayzetrain.inventory.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.auth.services.PasswordHandler;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.common.service.CommonService;

@Service
public class UserWriter {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommonService commonService;
	
	public ResponseEntity<?> CreateUser(User user) {
	
		PasswordHandler passwordEncoder = new PasswordHandler();
		user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
		
		user.setDatecreated(commonService.setTimestamp());
		user.setDatemodified(commonService.setTimestamp());
		user.setEnabled(true);
		
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
	
	}
	
}
