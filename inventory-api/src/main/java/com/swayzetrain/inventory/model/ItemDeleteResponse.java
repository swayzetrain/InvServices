package com.swayzetrain.inventory.model;

import java.util.ArrayList;

public class ItemDeleteResponse {

	private Integer deletedCount;
	private ArrayList<Item> itemsdeleted;
	
	public ItemDeleteResponse()
	{
		
	}
	
	public Integer getDeletedCount() {
		return deletedCount;
	}
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}
	public ArrayList<Item> getItemsdeleted() {
		return itemsdeleted;
	}
	public void setItemsdeleted(ArrayList<Item> itemsdeleted) {
		this.itemsdeleted = itemsdeleted;
	}
	
	
	
}
