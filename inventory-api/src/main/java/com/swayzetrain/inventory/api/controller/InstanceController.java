package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.api.service.UserRoleRequestService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.auth.services.TokenHandler;
import com.swayzetrain.inventory.common.model.Instance;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.InstanceRepository;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@RestController
@RequestMapping("/api/v1/instances")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class InstanceController {
	
	@Autowired
	private InstanceRepository instanceRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserRoleRequestService userRoleRequestService;
	
	@Autowired
	private CommonService commonService;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.TTL}")
	private long jwtTTL;
	
	@RequestMapping(value = "/{instanceId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
    public ResponseEntity<?> getInstanceByInstanceId(@PathVariable(name = "instanceId") Integer instanceid,  @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		Instance instance = instanceRepository.findByInstanceid(instanceid);
		
		User user = userRepository.findByUsername(userAuthorizationDetails.getUsername());
		UserRole userRole = userRoleRepository.findByUseridAndInstanceid(user.getUserid(), instanceid);
		
		if(null == userRole) {
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
		}
		
		HttpHeaders header = new HttpHeaders();
		TokenHandler tokenHandler = new TokenHandler();
		
		header.add(com.swayzetrain.inventory.auth.enums.Constants.AUTH_HEADER_STRING, com.swayzetrain.inventory.auth.enums.Constants.TOKEN_PREFIX + " " + tokenHandler.GenerateJWT(userAuthorizationDetails.getUsername(), instanceid, jwtSecret, jwtTTL));
        
		return new ResponseEntity<Instance>(instance, header, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createInstance(@Validated(Instance.New.class)@RequestBody Instance instance, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		User user = userRepository.findByUsername(userAuthorizationDetails.getUsername());
		
		instance.setCreationuserid(user.getUserid());
		instance.setDatemodified(commonService.setTimestamp());
		instance.setDatecreated(commonService.setTimestamp());
		
		Instance instanceResponse = instanceRepository.save(instance);
		
		userRoleRequestService.establishNewInstanceUserRole(user.getUsername(), instanceResponse.getInstanceid());
		
		
		
		return new ResponseEntity<Instance>(instanceResponse, HttpStatus.OK);
		
	}

}
