package com.swayzetrain.inventory.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.auth.services.PasswordHandler;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.repository.UserRepository;

@Service
public class UserRequestService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommonService commonService;
	
	public void createUser(User user) {
		
		PasswordHandler passwordEncoder = new PasswordHandler();
		user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
		
		user.setDatecreated(commonService.setTimestamp());
		user.setDatemodified(commonService.setTimestamp());
		user.setEnabled(true);
		
		userRepository.save(user);
		
	}
	
}
