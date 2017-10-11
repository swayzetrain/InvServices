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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.service.quantity.QuantityReader;
import com.swayzetrain.inventory.api.service.quantity.QuantityWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Quantity;

@RestController
@RequestMapping("/api/v1/quantities")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class QuantityController {
	
	@Autowired
	private QuantityWriter quantityWriter;
	
	@Autowired
	private QuantityReader quantityReader;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> GetQuantityByItem(@RequestParam(value = "itemid", required = false) Integer itemId, @RequestParam(value = "itemname", required = false) String itemName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return quantityReader.GetQuantityByItem(itemId, itemName, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> GetQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return quantityReader.GetQuantityByQuantityId(quantityId, userAuthorizationDetails);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> AddQuantity(@Validated(Quantity.New.class)@RequestBody Quantity quantity, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return quantityWriter.SaveNewQuantity(quantity, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId, @RequestBody Quantity quantity, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return quantityWriter.UpdateExistingQuantity(quantityId, quantity, userAuthorizationDetails);
		
	}
	
}
