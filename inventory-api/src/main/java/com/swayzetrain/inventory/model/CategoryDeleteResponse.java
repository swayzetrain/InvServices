package com.swayzetrain.inventory.model;

import java.util.ArrayList;

public class CategoryDeleteResponse {

	private Integer deletedCount;
	private ArrayList<Category> categoriesDeleted;
	
	public CategoryDeleteResponse()
	{
		
	}
	
	public Integer getDeletedCount() {
		return deletedCount;
	}
	
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}

	public ArrayList<Category> getCategoriesDeleted() {
		return categoriesDeleted;
	}

	public void setCategoriesDeleted(ArrayList<Category> categoriesDeleted) {
		this.categoriesDeleted = categoriesDeleted;
	}
}
