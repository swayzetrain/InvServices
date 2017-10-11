package com.swayzetrain.inventory.api.model;

import java.util.ArrayList;

import com.swayzetrain.inventory.common.model.Item;

public class ItemDeleteResponse {

	private Integer deletedCount;
	private ArrayList<Item> itemsDeleted;
	
	public ItemDeleteResponse()
	{
		
	}
	
	public ItemDeleteResponse(Integer deletedCount, ArrayList<Item> itemsDeleted) {
		
		this.deletedCount = deletedCount;
		this.itemsDeleted = itemsDeleted;
	}
	
	public Integer getDeletedCount() {
		return deletedCount;
	}
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}
	public ArrayList<Item> getItemsDeleted() {
		return itemsDeleted;
	}
	public void setItemsDeleted(ArrayList<Item> itemArray) {
		this.itemsDeleted = itemArray;
	}
	
	public void addItemsDeleted(Item item) {
		
		this.itemsDeleted.add(item);
		
	}
	
	
	
}
