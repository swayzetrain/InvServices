package com.swayzetrain.inventory.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.ItemDeleteResponse;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.api.service.ItemRequestService;
import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.repository.ItemRepository;

@RestController
@RequestMapping("/api/v1/items")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ItemRequestService itemRequestService;
	
	@Autowired
	private CommonService commonService;
    
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(value="itemname", required = false) String itemName, @RequestParam(value="categoryid", required = false) Integer categoryId) {
		
		List<Item> itemList = itemRequestService.GetAllItemsFiltering(itemName, categoryId);		
        
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Item>  getItemById(@PathVariable(value="itemId") Integer itemId) {
		
		Item itemResponse = itemRepository.findByItemid(itemId);
		
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> addItem(@Validated(Item.New.class) @RequestBody Item item) {
		
		if (!itemRequestService.CategoryExists(item.getCategoryid())) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		item.setDatecreated(commonService.setTimestamp());
		item.setDatemodified(commonService.setTimestamp());
		
		Item itemResponse = itemRepository.save(item);
		
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<ItemDeleteResponse> deleteItemById(@PathVariable(value="itemId") Integer itemId) {
		
		ItemDeleteResponse itemDeleteResponse = new ItemDeleteResponse();
		
		itemRequestService.DeleteItemById(itemId, itemDeleteResponse);
		
		return new ResponseEntity<ItemDeleteResponse>(itemDeleteResponse, HttpStatus.OK);
	
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<ItemDeleteResponse> deleteItem(@RequestParam(value="itemName", required = false) String itemName, @RequestParam(value="categoryId", required = false) Integer categoryId) {
		
		ItemDeleteResponse itemDeleteResponse = new ItemDeleteResponse();
		
		itemRequestService.DeleteItemsFiltering(itemName, categoryId, itemDeleteResponse);
		
		return new ResponseEntity<ItemDeleteResponse>(itemDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateItemById(@PathVariable(value = "itemId") Integer itemId, @Validated(Item.Existing.class) @RequestBody Item item) {
		
		if (!itemRequestService.ItemExists(itemId)) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.ITEM_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		itemRequestService.PopulateUpdatedItem(item, itemId);
		
		Item itemResponse = itemRepository.save(item);
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
		
	}

}