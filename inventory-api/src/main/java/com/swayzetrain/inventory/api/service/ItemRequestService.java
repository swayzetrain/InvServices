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

	public void PopulateUpdatedItem(Item newItem, Integer itemId, Integer instanceid) {
		
		newItem.setItemid(itemId);
		
		Item oldItem = itemRepository.findByItemidAndInstanceid(newItem.getItemid(), instanceid);
		
		if (null == newItem.getItemname()) {
			newItem.setItemname(oldItem.getItemname());
		}
		
		if (null == newItem.getCategoryid()) {
			newItem.setCategoryid(oldItem.getCategoryid());
		}
		
		newItem.setInstanceid(instanceid);
		
		if (null == newItem.getDatecreated()) {
			newItem.setDatecreated(oldItem.getDatecreated());
		}
		
		if (null == newItem.getDatemodified()) {
			newItem.setDatemodified(commonService.setTimestamp());
		}
		
	}
	
	public boolean ItemExists (Integer itemId, Integer instanceid) {
		
		Item item = itemRepository.findByItemidAndInstanceid(itemId, instanceid);
		
		if (null == item) {
			
			return false;
			
		}
		
		return true;
	}
	
	public boolean CategoryExists (Integer categoryId, Integer instanceid) {
		
		Category category = categoryRepository.findByCategoryidAndInstanceid(categoryId, instanceid);
		
		if (null == category) {
			
			return false;
			
		}
		
		return true;
	}
	
	public List<Item> GetAllItemsFiltering(String name, Integer categoryId, Integer instanceid) {
		
		if (null == name && null == categoryId) {
			
			return itemRepository.findByInstanceid(instanceid);
			
		}
		
		if (null != name && null != categoryId) {
			
			return itemRepository.findByItemnameAndCategoryidAndInstanceid(name, categoryId, instanceid);
			
		}
		
		if (null != name) {
			
			return itemRepository.findByItemnameAndInstanceid(name, instanceid);
			
		}
		
		return itemRepository.findByCategoryidAndInstanceid(categoryId, instanceid);
		
	}
	
	public void DeleteItemsFiltering(String name, Integer categoryId, Integer instanceid, ItemDeleteResponse itemDeleteResponse) {
		
		if (null == name && null == categoryId) {
			
			itemDeleteResponse.setDeletedCount(0);
			
		}
		
		else if (null != name && null != categoryId && null != instanceid) {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByItemnameAndCategoryidAndInstanceid(name, categoryId, instanceid));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemnameAndCategoryidAndInstanceid(name, categoryId, instanceid));
			
		}
		
		else if (null != name && null != instanceid) {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByItemnameAndInstanceid(name, instanceid));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemnameAndInstanceid(name, instanceid));
			
		}
		
		else {
			
			itemDeleteResponse.setItemsdeleted(itemRepository.findByCategoryidAndInstanceid(categoryId, instanceid));
			itemDeleteResponse.setDeletedCount(itemRepository.deleteByCategoryidAndInstanceid(categoryId, instanceid));
			
		}
		
	}
	
	public void DeleteItemById(Integer itemId, Integer instanceid, ItemDeleteResponse itemDeleteResponse) {
		
		itemDeleteResponse.addItemsdeleted(itemRepository.findByItemidAndInstanceid(itemId, instanceid));
		itemDeleteResponse.setDeletedCount(itemRepository.deleteByItemidAndInstanceid(itemId, instanceid));
		
	}
	
}
