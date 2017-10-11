package com.swayzetrain.inventory.api.service.quantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.repository.ItemRepository;
import com.swayzetrain.inventory.common.repository.QuantityRepository;

@Service
public class QuantityWriter {
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CommonService commonService;
	
	public ResponseEntity<?> SaveNewQuantity(Quantity quantity, UserAuthorizationDetails userAuthorizationDetails) {
		
		Item item = itemRepository.findByItemidAndInstanceid(quantity.getItemid(), userAuthorizationDetails.getInstanceid());
		
		if(item == null) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		quantity.setDatemodified(commonService.setTimestamp());
		
		return new ResponseEntity<Quantity>(quantityRepository.save(quantity), HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> UpdateExistingQuantity(Integer quantityId, Quantity quantity, UserAuthorizationDetails userAuthorizationDetails) {
		
		Quantity oldQuantity = quantityRepository.findByQuantityid(quantityId);
		
		if(null == oldQuantity) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.QUANTITY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		Item attachedItem = null;
		
		if(null != quantity.getItemid() && quantity.getItemid() != oldQuantity.getItemid()) {
			
			attachedItem = itemRepository.findByItemidAndInstanceid(quantity.getItemid(), userAuthorizationDetails.getInstanceid());
			
		}
		
		else {
			
			attachedItem = itemRepository.findByItemidAndInstanceid(oldQuantity.getItemid(), userAuthorizationDetails.getInstanceid());
			
		}
		
		if(null == attachedItem) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		quantity.setQuantityid(quantityId);
		quantity.setDatemodified(commonService.setTimestamp());
		
		if (null == quantity.getItemid()) {
			
			quantity.setItemid(attachedItem.getItemid());
			
		}
		
		if (null == quantity.getQuantity()) {
			
			quantity.setQuantity(oldQuantity.getQuantity());
			
		}
		
		if (null == quantity.getQuantitytype()) {
			
			quantity.setQuantitytype(oldQuantity.getQuantitytype());
			
		}
		
		return new ResponseEntity<Quantity>(quantityRepository.save(quantity), HttpStatus.OK);
		
	}

}
