package com.swayzetrain.inventory.api.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.repository.CategoryRepository;
import com.swayzetrain.inventory.common.repository.ItemRepository;
import com.swayzetrain.inventory.common.service.CommonService;

@Service
public class ItemWriter {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;
	
	public ResponseEntity<?> SaveNewItem(Item item, UserAuthorizationDetails userAuthorizationDetails) {
		
		if(null == item.getCategoryid() || null == categoryRepository.findByCategoryidAndInstanceid(item.getCategoryid(), userAuthorizationDetails.getInstanceid())) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		item.setDatecreated(commonService.setTimestamp());
		item.setDatemodified(commonService.setTimestamp());
		item.setCreationuserid(userAuthorizationDetails.getUserid());
		item.setInstanceid(userAuthorizationDetails.getInstanceid());
		
		return new ResponseEntity<Item>(itemRepository.save(item), HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> updateExisitingItem(Integer oldItemId, Item newItem, UserAuthorizationDetails userAuthorizationDetails) {
		
		Item oldItem = itemRepository.findByItemidAndInstanceid(oldItemId, userAuthorizationDetails.getInstanceid());
		
		if (null == oldItem) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ITEM_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		newItem.setItemid(oldItemId);
		
		if (null == newItem.getItemname()) {
			newItem.setItemname(oldItem.getItemname());
		}
		
		if (null == newItem.getCategoryid() || null == categoryRepository.findByCategoryidAndInstanceid(newItem.getCategoryid(), userAuthorizationDetails.getInstanceid())) {
			newItem.setCategoryid(oldItem.getCategoryid());
		}
		
		newItem.setInstanceid(userAuthorizationDetails.getInstanceid());
		
		if (null == newItem.getDatecreated()) {
			newItem.setDatecreated(oldItem.getDatecreated());
		}
		
		if (null == newItem.getDatemodified()) {
			newItem.setDatemodified(commonService.setTimestamp());
		}
		
		return new ResponseEntity<Item>(itemRepository.save(newItem), HttpStatus.OK);
		
	}

}
