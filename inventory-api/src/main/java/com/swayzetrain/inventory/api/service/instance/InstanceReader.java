package com.swayzetrain.inventory.api.service.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.auth.services.TokenAuthenticationService;
import com.swayzetrain.inventory.common.model.Instance;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.InstanceRepository;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@Service
public class InstanceReader {
	
	@Autowired
	private InstanceRepository instanceRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.TTL}")
	private long jwtTTL;
	
	public ResponseEntity<?> GetInstance(Integer instanceId, UserAuthorizationDetails userAuthorizationDetails) {
		
		UserRole userRole = userRoleRepository.findByUseridAndInstanceid(userAuthorizationDetails.getUserid(), instanceId);
		
		if(null == userRole) {
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
		}
		
		Instance instance = instanceRepository.findByInstanceid(instanceId);
		
		HttpHeaders header = new HttpHeaders();
		header.add(com.swayzetrain.inventory.auth.enums.Constants.AUTH_HEADER_STRING, com.swayzetrain.inventory.auth.enums.Constants.TOKEN_PREFIX + " " + tokenAuthenticationService.addInstanceAuthentication(userAuthorizationDetails.getUsername(), instanceId, jwtSecret, jwtTTL));
		
		return new ResponseEntity<Instance>(instance, header, HttpStatus.OK);
		
	}

}
