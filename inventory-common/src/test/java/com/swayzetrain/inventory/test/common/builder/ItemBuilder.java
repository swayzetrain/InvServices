package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.service.CommonService;

public class ItemBuilder {
	
	CommonService commonService = new CommonService();
	
	private String itemName = "Item";
	private Integer categoryId = 1;
	private Integer instanceId = 1;
	private Integer creationUserId = 1;
	private Timestamp dateCreated = commonService.setTimestamp();
	private Timestamp dateModified = commonService.setTimestamp();
	
	public ItemBuilder addItemName(String itemName) {
		
		this.itemName = itemName;
		return this;
		
	}
	
	public ItemBuilder addCategoryId(Integer categoryId) {
		
		this.categoryId = categoryId;
		return this;
		
	}
	
	public ItemBuilder addInstanceId(Integer instanceId) {
		
		this.instanceId = instanceId;
		return this;
		
	}
	
	public ItemBuilder addCreationUserId(Integer userId) {
		
		this.creationUserId = userId;
		return this;
		
	}

	public ItemBuilder addDateCreated(Timestamp date) {
		
		this.dateCreated = date;
		return this;
		
	}
	
	public ItemBuilder addDateModified(Timestamp date) {
		
		this.dateModified = date;
		return this;
		
	}
	
	public Item build() {
		
		return new Item(itemName, categoryId, instanceId, creationUserId, dateCreated, dateModified);
		
	}

}
