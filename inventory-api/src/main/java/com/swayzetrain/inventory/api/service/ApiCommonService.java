package com.swayzetrain.inventory.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.model.UserRolePostRequest;
import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.repository.RoleRepository;
import com.swayzetrain.inventory.common.repository.UserRepository;

@Service
public class ApiCommonService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
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

}
