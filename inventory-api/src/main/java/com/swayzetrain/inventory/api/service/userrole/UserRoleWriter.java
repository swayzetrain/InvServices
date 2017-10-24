package com.swayzetrain.inventory.api.service.userrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.model.UserRolePostRequest;
import com.swayzetrain.inventory.api.service.ApiCommonService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;
import com.swayzetrain.inventory.common.service.CommonService;

@Service
public class UserRoleWriter {
	
	@Value("${default.newinstance.userrole}")
	private Integer defaultNewInstanceUserRole;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ApiCommonService apiCommonService;
	
	public ResponseEntity<?> AddUserRole(UserAuthorizationDetails userAuthorizationDetails, UserRolePostRequest userRolePostRequest) {
		
		//check if UserId is set. If not try to set it with username
		if (!apiCommonService.convertUsernameToUserId(userRolePostRequest)) {
					
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_PROVIDED, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
					
		}
				
		// Check if RoleId is set. If not try to set it with rolename
		if (!apiCommonService.convertRoleNameToRoleId(userRolePostRequest)) {
					
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ROLE_NOT_PROVIDED, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
						
		}
		
		if (null != userRoleRepository.findByUseridAndInstanceid(userRolePostRequest.getUserid(), userAuthorizationDetails.getInstanceid())) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USERROLE_EXISTS_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.CONFLICT);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		//Convert userRolePostRequest to UserRole and store it in the database
		UserRole newUserRole = new UserRole(userRolePostRequest.getUserid(), userRolePostRequest.getRoleid(), userAuthorizationDetails.getInstanceid(), commonService.setTimestamp(), commonService.setTimestamp());
		
		return new ResponseEntity<>(userRoleRepository.save(newUserRole), HttpStatus.OK);
		
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
