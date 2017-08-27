package com.swayzetrain.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.enums.Constants;
import com.swayzetrain.inventory.model.MessageResponse;
import com.swayzetrain.inventory.model.Role;
import com.swayzetrain.inventory.model.User;
import com.swayzetrain.inventory.model.UserRole;
import com.swayzetrain.inventory.model.UserRolePostRequest;
import com.swayzetrain.inventory.repository.RoleRepository;
import com.swayzetrain.inventory.repository.UserRepository;
import com.swayzetrain.inventory.repository.UserRoleRepository;

@Service
public class UserRoleRequestService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	
	public ResponseEntity<?> establishUserRoleObject (UserRolePostRequest userRolePostRequest) {
		
		//check if UserId is set. If not try to set it with username
		if (!convertUsernameToUserId(userRolePostRequest)) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_PROVIDED, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		// Check if RoleId is set. If not try to set it with rolename
		if (!convertRoleNameToRoleId(userRolePostRequest)) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ROLE_NOT_PROVIDED, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
				
		}
		
		return null;
		
	}
	
	public boolean convertUsernameToUserId(UserRolePostRequest userRolePostRequest) {
				
		if (null == userRolePostRequest.getUserid()) {
				
			if (null == userRolePostRequest.getUsername()) {
								
				return false;
								
			}
					
			User user = userRepository.findByUsername(userRolePostRequest.getUsername());
			userRolePostRequest.setUserid(user.getUserid());
				
		}
				
		return true;
	}

	public boolean convertRoleNameToRoleId(UserRolePostRequest userRolePostRequest) {
			
		if (null == userRolePostRequest.getRoleid()) {
				
			if (null == userRolePostRequest.getRolename()) {
				
				return false;
				
			}
			
			Role role = roleRepository.findByRolename(userRolePostRequest.getRolename());
			userRolePostRequest.setRoleid(role.getRoleid());
			
		}
			
		return true;
	}
	
	
	//This needs to be moved to a validation class at somepoint
	public boolean checkUserRoleExists(int userid) {
		
		UserRole userRole = userRoleRepository.findByUserid(userid);
		
		if (null == userRole) {
			return false;
		}
		
		return true;
	}

}
