package com.swayzetrain.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.enums.Constants;
import com.swayzetrain.inventory.model.MessageResponse;
import com.swayzetrain.inventory.model.Quantity;
import com.swayzetrain.inventory.repository.QuantityRepository;
import com.swayzetrain.inventory.service.CommonService;
import com.swayzetrain.inventory.service.QuantityRequestService;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private QuantityRequestService quantityRequestService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Quantity>> GetQuantityByItem(@RequestParam(value = "itemid", required = false) Integer itemId, @RequestParam(value = "itemname", required = false) String itemName) {
		
		List<Quantity> quantityListResponse = quantityRequestService.GetQuantitesByItemFiltering(itemId, itemName);
		
		return new ResponseEntity<List<Quantity>>(quantityListResponse,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.GET)
	public ResponseEntity<Quantity> GetQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId) {
		
		Quantity quantityResponse = quantityRepository.findByQuantityid(quantityId);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Quantity> AddQuantity(@RequestBody Quantity quantity) {
		
		quantity.setDatemodified(commonService.setTimestamp());		
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{quantityId}", method = RequestMethod.PUT)
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
