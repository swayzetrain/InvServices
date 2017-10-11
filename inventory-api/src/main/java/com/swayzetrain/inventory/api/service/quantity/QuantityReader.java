package com.swayzetrain.inventory.api.service.quantity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.repository.ItemRepository;
import com.swayzetrain.inventory.common.repository.QuantityRepository;

@Service
public class QuantityReader {
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public ResponseEntity<?> GetQuantityByItem(Integer itemId, String itemName, UserAuthorizationDetails userAuthorizationDetails) {
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		if(itemName != null && itemId != null) {
			
			itemList.add(itemRepository.findByItemidAndItemnameAndInstanceid(itemId, itemName, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else if (itemId != null && itemName == null) {
			
			itemList.add(itemRepository.findByItemidAndInstanceid(itemId, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else if (itemName != null && itemId == null) {
			
			itemList = itemRepository.findByItemnameAndInstanceid(itemName, userAuthorizationDetails.getInstanceid());
			
		}
		
		if (itemList.size() == 0) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ITEM_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		ArrayList<Quantity> quantityList = new ArrayList<Quantity>();
		
		for(int i = 0; i < itemList.size(); i++ ) {
			
			quantityList.add(quantityRepository.findByItemid(itemList.get(i).getItemid()));
			
		}
		
		return new ResponseEntity<ArrayList<Quantity>>(quantityList, HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> GetQuantityByQuantityId(Integer quantityId, UserAuthorizationDetails userAuthorizationDetails) {
		
		Quantity quantity = quantityRepository.findByQuantityid(quantityId);
		
		Item item = itemRepository.findByItemidAndInstanceid(quantity.getItemid(), userAuthorizationDetails.getInstanceid());
		
		if (null == item) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.USER_NOT_AUTHORIZED, MediaType.APPLICATION_JSON, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<Quantity>(quantity, HttpStatus.OK);
		
	}

}