package com.swayzetrain.inventory.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.model.UserRolePostRequest;
import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.RoleRepository;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@Service
public class UserRoleRequestService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Value("${default.newinstance.userrole}")
	private Integer defaultNewInstanceUserRole;
	
	
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
	
	public boolean establishNewInstanceUserRole (String username, Integer instanceid) {
		
		//Set userID
		User user = userRepository.findByUsername(username);
		
		if(null == user) {
			return false;
		}
		
		UserRole userRole = new UserRole();
		userRole.setUserid(user.getUserid());
		userRole.setInstanceid(instanceid);
		userRole.setRoleid(defaultNewInstanceUserRole);
		userRole.setDatecreated(commonService.setTimestamp());
		userRole.setDatemodified(commonService.setTimestamp());
		
		userRoleRepository.save(userRole);
		
		return true;
		
	}

}
