package com.swayzetrain.inventory.api.model;

import java.util.List;

import com.swayzetrain.inventory.common.model.Category;

public class CategoryDeleteResponse {

	private Integer deletedCount;
	private List<Category> categoriesDeleted;
	
	public CategoryDeleteResponse()
	{
		
	}
	
	public CategoryDeleteResponse(Integer deletedCount, List<Category> categoriesDeleted) {
		
		this.deletedCount = deletedCount;
		this.categoriesDeleted = categoriesDeleted;
		
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
