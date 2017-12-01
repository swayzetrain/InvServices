package com.swayzetrain.inventory.api.service.userrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@Service
public class UserRoleReader {

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public ResponseEntity<List<UserRole>> GetAllUserRole(UserAuthorizationDetails userAuthorizationDetails) {

			return new ResponseEntity<List<UserRole>>(userRoleRepository.findByInstanceid(userAuthorizationDetails.getInstanceid()), HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> GetUserRoleByUser(UserAuthorizationDetails userAuthorizationDetails, Integer userId) {
		
		if(null == userId || userId != userAuthorizationDetails.getUserid()) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		else {
			
			return new ResponseEntity<List<UserRole>>(userRoleRepository.findByUserid(userAuthorizationDetails.getUserid()), HttpStatus.OK);
			
		}
		
	}
	
	public ResponseEntity<?> GetUserRoleByUserRole(UserAuthorizationDetails userAuthorizationDetails, Integer userRoleId) {
		
		UserRole userRole = userRoleRepository.findByUserroleid(userRoleId);
		
		if(userRole.getUserid() != userAuthorizationDetails.getUserid() || userRole.getInstanceid() != userAuthorizationDetails.getInstanceid()) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> GetUserRoleByRole(UserAuthorizationDetails userAuthorizationDetails, Integer roleId) {
		
		List<UserRole> userRoleList = userRoleRepository.findByRoleid(roleId);
		
		int foundUser = -1;
		
		for(int i = 0; i < userRoleList.size(); i++) {
			
			if (userRoleList.get(i).getUserid() == userAuthorizationDetails.getUserid() || userRoleList.get(i).getInstanceid() == userAuthorizationDetails.getInstanceid()) {
				
				foundUser = i;
				break;
				
			}
			
		}
		
		if(foundUser == -1) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<UserRole>(userRoleList.get(foundUser), HttpStatus.OK);
		
	}
	
}
