package com.swayzetrain.inventory.api.service.item;

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
import com.swayzetrain.inventory.common.repository.ItemRepository;

@Service
public class ItemReader {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public ResponseEntity<?> GetItems(Integer itemId, String itemName, Integer categoryId, UserAuthorizationDetails userAuthorizationDetails) {
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		if(null != itemId && null == itemName && null == categoryId) {
			
			itemList.add(itemRepository.findByItemidAndInstanceid(itemId, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else if(null == itemId && null == itemName && null == categoryId) {
			
			itemList = itemRepository.findByInstanceid(userAuthorizationDetails.getInstanceid());
			
		}
		
		else if(null != itemName && null == itemId && null == categoryId) {
			
			itemList = itemRepository.findByItemnameAndInstanceid(itemName, userAuthorizationDetails.getInstanceid());
			
		}
		
		else if(null != categoryId && null == itemId && null == itemName) {
			
			itemList = itemRepository.findByCategoryidAndInstanceid(categoryId, userAuthorizationDetails.getInstanceid());
			
		}
		
		else if(null != itemName && null != categoryId && null == itemId) {
			
			itemList = itemRepository.findByItemnameAndCategoryidAndInstanceid(itemName, categoryId, userAuthorizationDetails.getInstanceid());
			
		}
		
		else if(null != itemId && null != itemName && null != categoryId) {
			
			itemList.add(itemRepository.findByItemidAndItemnameAndCategoryidAndInstanceid(itemId, itemName, categoryId, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else if(null != itemId && null != itemName && null == categoryId) {
			
			itemList.add(itemRepository.findByItemidAndItemnameAndInstanceid(itemId, itemName, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else if(null != itemId && null == itemName && null != categoryId) {
			
			itemList.add(itemRepository.findByItemidAndCategoryidAndInstanceid(itemId, categoryId, userAuthorizationDetails.getInstanceid()));
			
		}
		
		else {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ITEM_500_ERROR_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		if(itemList.size() == 0) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ITEM_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<ArrayList<Item>>(itemList, HttpStatus.OK);
		
	}

}
