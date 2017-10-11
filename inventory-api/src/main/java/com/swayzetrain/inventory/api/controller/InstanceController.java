package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.swayzetrain.inventory.api.service.instance.InstanceReader;
import com.swayzetrain.inventory.api.service.instance.InstanceWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Instance;

@RestController
@RequestMapping("/api/v1/instances")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class InstanceController {
	
	@Autowired
	private InstanceReader instanceRetriever;
	
	@Autowired
	private InstanceWriter instanceWriter;
	
	@RequestMapping(value = "/{instanceId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
    public ResponseEntity<?> getInstanceByInstanceId(@PathVariable(name = "instanceId") Integer instanceid,  @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
        
		return instanceRetriever.GetInstance(instanceid, userAuthorizationDetails);
		
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createInstance(@Validated(Instance.New.class)@RequestBody Instance instance, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return instanceWriter.SaveNewInstance(instance, userAuthorizationDetails);
		
	}

}
