package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.Category;

public class CategoryBuilder {
	
	private String categoryName = "category";
	private Integer instanceId = 0;
	private Integer creationUserId = 0;
	private Timestamp dateCreated = new java.sql.Timestamp(new java.util.Date().getTime());
	private Timestamp dateModified = new java.sql.Timestamp(new java.util.Date().getTime());
	
	public CategoryBuilder addCategoryName(String name) {
		
		this.categoryName = name;
		return this;
		
	}
	
	public CategoryBuilder addInstanceId(Integer instance) {
		
		this.instanceId = instance;
		return this;
		
	}
	
	public CategoryBuilder addCreationUserId(Integer userId) {
		
		this.creationUserId = userId;
		return this;
		
	}
	
	public CategoryBuilder addDateCreated(Timestamp date) {
		
		this.dateCreated = date;
		return this;
		
	}
	
	public CategoryBuilder addDateModified(Timestamp date) {
		
		this.dateModified = date;
		return this;
		
	}
	
	public Category build() {
		
		return new Category(categoryName, instanceId, creationUserId, dateCreated, dateModified);
		
	}

}
