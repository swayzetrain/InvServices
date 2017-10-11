package com.swayzetrain.inventory.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swayzetrain.inventory.api.service.item.ItemRemover;
import com.swayzetrain.inventory.api.service.item.ItemReader;
import com.swayzetrain.inventory.api.service.item.ItemWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Item;

@RestController
@RequestMapping("/api/v1/items")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ItemController {
	
	@Autowired
	private ItemWriter itemWriter;
	
	@Autowired
	private ItemRemover itemRemover;
	
	@Autowired ItemReader itemRetriever;
    
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
    public ResponseEntity<?> getAllItems(@RequestParam(value="itemname", required = false) String itemName, @RequestParam(value="categoryid", required = false) Integer categoryId,
    		@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return 	itemRetriever.GetItems(null, itemName, categoryId, userAuthorizationDetails);
		
    }
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?>  getItemById(@PathVariable(value="itemId") Integer itemId, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return itemRetriever.GetItems(itemId, null, null, userAuthorizationDetails);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> addItem(@Validated(Item.New.class) @RequestBody Item item, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return itemWriter.SaveNewItem(item, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<?> deleteItemById(@PathVariable(value="itemId") Integer itemId, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return itemRemover.DeleteItemById(itemId, userAuthorizationDetails);
	
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<?> deleteItem(@RequestParam(value="itemName", required = false) String itemName, @RequestParam(value="categoryId", required = false) Integer categoryId,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return itemRemover.DeleteItemByOther(itemName, categoryId, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateItemById(@PathVariable(value = "itemId") Integer itemId, @Validated(Item.Existing.class) @RequestBody Item item,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return itemWriter.updateExisitingItem(itemId, item, userAuthorizationDetails);
		
	}

}