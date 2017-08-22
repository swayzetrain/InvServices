package com.swayzetrain.inventory.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.model.Quantity;
import com.swayzetrain.inventory.repository.QuantityRepository;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {
	
	@Autowired
	private QuantityRepository quantityRepository;

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Quantity> getQuantityByItemId(@PathVariable(value = "itemId") Integer ItemId) {
		
		Quantity quantityResponse = quantityRepository.findByItemid(ItemId);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Quantity> addQuantity(@RequestBody Quantity quantity) {
		
		quantity.setDatemodified(setTimestamp());		
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{quantityId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateQuantityByQuantityId(@PathVariable(value = "quantityId") Integer quantityId, @RequestBody Quantity quantity) {
		
		Quantity oldQuantity = quantityRepository.findByQuantityid(quantityId);
		
		if (null == oldQuantity) {
			return ResponseEntity.status(HttpStatus.OK).body("quantity does not exist");
		}
		
		quantity.setQuantityid(quantityId);
		quantity.setDatemodified(setTimestamp());	
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateQuantityByItemId(@PathVariable(value = "itemId") Integer itemId, @RequestBody Quantity quantity) {
		
		Quantity oldQuantity = quantityRepository.findByItemid(itemId);
		
		if (null == oldQuantity) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("quantity does not exist");
			
		}
		
		quantity.setQuantityid(oldQuantity.getQuantityid());
		quantity.setItemid(oldQuantity.getItemid());
		quantity.setDatemodified(setTimestamp());	
		
		Quantity quantityResponse = quantityRepository.save(quantity);
		
		return new ResponseEntity<Quantity>(quantityResponse, HttpStatus.OK);
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
}
