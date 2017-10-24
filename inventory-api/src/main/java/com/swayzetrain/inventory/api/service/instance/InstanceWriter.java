package com.swayzetrain.inventory.api.service.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.service.userrole.UserRoleWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Instance;
import com.swayzetrain.inventory.common.repository.InstanceRepository;
import com.swayzetrain.inventory.common.service.CommonService;

@Service
public class InstanceWriter {
	
	@Autowired
	private InstanceRepository instanceRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserRoleWriter userRoleWriter;
	
	public ResponseEntity<?> SaveNewInstance(Instance instance, UserAuthorizationDetails userAuthorizationDetails) {
		
		instance.setCreationuserid(userAuthorizationDetails.getUserid());
		instance.setDatemodified(commonService.setTimestamp());
		instance.setDatecreated(commonService.setTimestamp());
		
		Instance instanceResponse = instanceRepository.save(instance);
		
		userRoleWriter.establishNewInstanceUserRole(userAuthorizationDetails.getUsername(), instanceResponse.getInstanceid());
		
		return new ResponseEntity<Instance>(instanceResponse, HttpStatus.OK);
		
	}

}
