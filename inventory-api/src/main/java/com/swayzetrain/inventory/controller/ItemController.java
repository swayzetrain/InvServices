package com.swayzetrain.inventory.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swayzetrain.inventory.model.Item;
import com.swayzetrain.inventory.model.ItemDeleteResponse;
import com.swayzetrain.inventory.model.MessageResponse;
import com.swayzetrain.inventory.repository.ItemRepository;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
    
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
		ArrayList<Item> itemList = (ArrayList<Item>) itemRepository.findAll();
        
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/name/{itemName}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>>  getItemsByName(@PathVariable(value="itemName") String itemName) {
		
		ArrayList<Item> itemResponse = (ArrayList<Item>) itemRepository.findByItemname(itemName);
		
		return new ResponseEntity<List<Item>>(itemResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item>  getItemById(@PathVariable(value="itemId") Integer itemId) {
		
		Item itemResponse = itemRepository.findByItemid(itemId);
		
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>>  getItemsByCategoryId(@PathVariable(value="categoryId") Integer categoryId) {
		
		ArrayList<Item> itemResponse = (ArrayList<Item>) itemRepository.findByCategoryid(categoryId);
		
		return new ResponseEntity<List<Item>>(itemResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Item> addItem(@RequestBody Item item) {
		
		item.setDatecreated(setTimestamp());
		item.setDatemodified(setTimestamp());
		
		Item itemResponse = itemRepository.save(item);
		
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{itemId}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<ItemDeleteResponse> deleteItemById(@PathVariable(value="itemId") Integer itemId) {
		
		ItemDeleteResponse itemDeleteResponse = new ItemDeleteResponse();
		ArrayList<Item> itemArray = new ArrayList<Item>();
		
		itemArray.add(itemRepository.findByItemid(itemId));
		itemDeleteResponse.setItemsdeleted(itemArray);
		
		itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemid(itemId));
		
		return new ResponseEntity<ItemDeleteResponse>(itemDeleteResponse, HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/name/{itemName}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<ItemDeleteResponse> deleteItemByName(@PathVariable(value = "itemName") String itemName) {
		
		ItemDeleteResponse itemDeleteResponse = new ItemDeleteResponse();
		
		itemDeleteResponse.setItemsdeleted(itemRepository.findByItemname(itemName));
		itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemname(itemName));
		
		return new ResponseEntity<ItemDeleteResponse>(itemDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<ItemDeleteResponse> deleteItemsByCategoryId(@PathVariable(value = "categoryId") Integer categoryId) {
		
		ItemDeleteResponse itemDeleteResponse = new ItemDeleteResponse();
		
		itemDeleteResponse.setItemsdeleted(itemRepository.findByCategoryid(categoryId));
		itemDeleteResponse.setDeletedCount(itemRepository.deleteByCategoryid(categoryId));
		
		return new ResponseEntity<ItemDeleteResponse>(itemDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateItemById(@PathVariable(value = "itemId") Integer itemId, @RequestBody Item item) {
		
		Item oldItem = itemRepository.findByItemid(itemId);
		
		if (null == oldItem) {
			
			MessageResponse messageResponse = new MessageResponse();
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse.MessageResponseBuilder("item does not exist"));
			
		}
		
		item.setItemid(itemId);
		item.setDatemodified(setTimestamp());
		item.setDatecreated(oldItem.getDatecreated());
		
		Item itemResponse = itemRepository.save(item);
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}

}