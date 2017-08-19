package com.swayzetrain.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.swayzetrain.inventory.model.Item;
import com.swayzetrain.inventory.repository.ItemRepository;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
    
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
		ArrayList<Item> itemList = (ArrayList<Item>) itemRepository.findAll();
        
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
    }
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Item> addItem(@RequestBody Item item) {
		
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		System.out.println("Date: " + date);
		
		item.setDatecreated(date);
		item.setDatemodified(date);
		
		Item itemResponse = itemRepository.save(item);
		
		return new ResponseEntity<Item>(itemResponse, HttpStatus.OK);
		
	}

}