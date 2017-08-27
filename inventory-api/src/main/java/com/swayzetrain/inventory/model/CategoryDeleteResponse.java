package com.swayzetrain.inventory.model;

import java.util.List;

public class CategoryDeleteResponse {

	private Integer deletedCount;
	private List<Category> categoriesDeleted;
	
	public CategoryDeleteResponse()
	{
		
	}
	
	public Integer getDeletedCount() {
		return deletedCount;
	}
	
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}

	public List<Category> getCategoriesDeleted() {
		return categoriesDeleted;
	}

	public void setCategoriesDeleted(List<Category> categoriesDeleted) {
		this.categoriesDeleted = categoriesDeleted;
	}
	
	public void addCategoriesdeleted(Category category) {
		
		this.categoriesDeleted.add(category);
		
	}
}
