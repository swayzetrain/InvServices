package com.swayzetrain.inventory.api.model;

import java.util.List;

import com.swayzetrain.inventory.common.model.Item;

public class ItemDeleteResponse {

	private Integer deletedCount;
	private List<Item> itemsdeleted;
	
	public ItemDeleteResponse()
	{
		
	}
	
	public Integer getDeletedCount() {
		return deletedCount;
	}
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}
	public List<Item> getItemsdeleted() {
		return itemsdeleted;
	}
	public void setItemsdeleted(List<Item> itemArray) {
		this.itemsdeleted = itemArray;
	}
	
	public void addItemsdeleted(Item item) {
		
		this.itemsdeleted.add(item);
		
	}
	
	
	
}
