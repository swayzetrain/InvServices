package com.swayzetrain.inventory.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.model.ItemDeleteResponse;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.repository.CategoryRepository;
import com.swayzetrain.inventory.common.repository.ItemRepository;

@Service
public class ItemRequestService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;

	public void PopulateUpdatedItem(Item newItem, Integer itemId) {
		
		newItem.setItemid(itemId);
		
		Item oldItem = itemRepository.findByItemid(newItem.getItemid());
		
		if (null == newItem.getItemname()) {
			newItem.setItemname(oldItem.getItemname());
		}
		
		if (null == newItem.getCategoryid()) {
			newItem.setCategoryid(oldItem.getCategoryid());
		}
		
		if (null == newItem.getDatecreated()) {
			newItem.setDatecreated(oldItem.getDatecreated());
		}
		
		if (null == newItem.getDatemodified()) {
			newItem.setDatemodified(commonService.setTimestamp());
		}
		
	}
	
	public boolean ItemExists (Integer itemId) {
		
		Item item = itemRepository.findByItemid(itemId);
		
		if (null == item) {
			
			return false;
			
		}
		
		return true;
	}
	
	public boolean CategoryExists (Integer categoryId) {
		
		Category category = categoryRepository.findByCategoryid(categoryId);
		
		if (null == category) {
			
			return false;
			
		}
		
		return true;
	}
	
	public List<Item> GetAllItemsFiltering(String name, Integer categoryId) {
		
		if (null == name && null == categoryId) {
			
			return itemRepository.findAll();
			
		}
		
		if (null != name && null != categoryId) {
			
			return itemRepository.findByItemnameAndCategoryid(name, categoryId);
			
		}
		
		if (null != name) {
			
			return itemRepository.findByItemname(name);
			
		}
		
		return itemRepository.findByCategoryid(categoryId);
		
	}
	
	public void DeleteItemsFiltering(String name, Integer categoryId, ItemDeleteResponse itemDeleteResponse) {
		
		if (null == name && null == categoryId) {
			
			itemDeleteResponse.setDeletedCount(0);
			
		}
		
		else if (null != name && null != categoryId) {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByItemnameAndCategoryid(name, categoryId));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemnameAndCategoryid(name, categoryId));
			
		}
		
		else if (null != name) {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByItemname(name));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemname(name));
			
		}
		
		else {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByCategoryid(categoryId));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByCategoryid(categoryId));
			
		}
		
	}
	
	public void DeleteItemById(Integer itemId, ItemDeleteResponse itemDeleteResponse) {
		
		itemDeleteResponse.addItemsdeleted(itemRepository.findByItemid(itemId));
		itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemid(itemId));
		
	}
	
}
