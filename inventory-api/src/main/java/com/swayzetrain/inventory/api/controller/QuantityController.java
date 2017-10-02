package com.swayzetrain.inventory.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.api.service.QuantityRequestService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.repository.QuantityRepository;

@RestController
@RequestMapping("/api/v1/quantities")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class QuantityController {
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private QuantityRequestService quantityRequestService;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> GetQuantityByItem(@RequestParam(value = "itemid", required = false) Integer itemId, @RequestParam(value = "itemname", required = false) String itemName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		boolean itemInstanceCheck = quantityRequestService.CheckItemInstance(itemId, userAuthorizationDetails.getInstanceid());
		
		if(!itemInstanceCheck) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		List<Quantity> quantityListResponse = quantityRequestService.GetQuantitesByItemFiltering(itemId, itemName, userAuthorizationDetails.getInstanceid());
		
		return new ResponseEntity<List<Quantity>>(quantityListResponse,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Quantity> GetQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId) {
		
		Quantity quantityResponse = quantityRepository.findByQuantityid(quantityId);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Quantity> AddQuantity(@Validated(Quantity.New.class)@RequestBody Quantity quantity) {
		
		quantity.setDatemodified(commonService.setTimestamp());		
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId, @RequestBody Quantity quantity) {
		
		if (!quantityRequestService.QuantityExists(quantityId)) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.QUANTITY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());

		}
		
		quantityRequestService.PopulateUpdatedQuantity(quantity, quantityId);
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
}
