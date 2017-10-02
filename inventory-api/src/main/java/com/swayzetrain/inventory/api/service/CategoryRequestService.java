package com.swayzetrain.inventory.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;

@Service
public class CategoryRequestService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;
	
	public List<Category> GetAllCategoriesFiltering(String name, Integer instanceid) {
		
		if(null == name) {
			
			return categoryRepository.findByInstanceid(instanceid);
			
		}
		
		return categoryRepository.findByCategorynameAndInstanceid(name, instanceid);
		
	}

	public void DeleteCategoryById(Integer categoryId, Integer instanceid, CategoryDeleteResponse categoryDeleteResponse) {
		
		categoryDeleteResponse.addCategoriesdeleted(categoryRepository.findByCategoryidAndInstanceid(categoryId, instanceid));
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategoryidAndInstanceid(categoryId, instanceid));
		
	}
	
	public void DeleteCategoriesFiltering(String name, Integer instanceid, CategoryDeleteResponse categoryDeleteResponse) {
		
		categoryDeleteResponse.setCategoriesDeleted(categoryRepository.findByCategorynameAndInstanceid(name, instanceid));
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategorynameAndInstanceid(name, instanceid));
		
	}
	
	public void PopulateUpdatedCategory(Category newCategory, Integer categoryId, Integer instanceid) {
		
		newCategory.setCategoryid(categoryId);
		
		Category oldCategory = categoryRepository.findByCategoryidAndInstanceid(newCategory.getCategoryid(), instanceid);
		
		if(null == newCategory.getCategoryname()) {
			
			newCategory.setCategoryname(oldCategory.getCategoryname());
			
		}
		
		newCategory.setInstanceid(instanceid);
		
		if(null == newCategory.getDatecreated()) {
			
			newCategory.setDatecreated(oldCategory.getDatecreated());
			
		}
		
		if(null == newCategory.getDatemodified()) {
			
			newCategory.setDatemodified(commonService.setTimestamp());
			
		}
		
	}
	
	public boolean CategoryExists(Integer categoryId, Integer instanceid) {
		
		if (null == categoryRepository.findByCategoryidAndInstanceid(categoryId, instanceid)) {
			
			return false;
			
		}
		
		return true;
		
	}
	
}
