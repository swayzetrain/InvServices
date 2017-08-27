package com.swayzetrain.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.model.Category;
import com.swayzetrain.inventory.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.repository.CategoryRepository;

@Service
public class CategoryRequestService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;
	
	public List<Category> GetAllCategoriesFiltering(String name) {
		
		if(null == name) {
			
			return categoryRepository.findAll();
			
		}
		
		return categoryRepository.findByCategoryname(name);
		
	}

	public void DeleteCategoryById(Integer categoryId, CategoryDeleteResponse categoryDeleteResponse) {
		
		categoryDeleteResponse.addCategoriesdeleted(categoryRepository.findByCategoryid(categoryId));
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategoryid(categoryId));
		
	}
	
	public void DeleteCategoriesFiltering(String name, CategoryDeleteResponse categoryDeleteResponse) {
		
		categoryDeleteResponse.setCategoriesDeleted(categoryRepository.findByCategoryname(name));
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategoryname(name));
		
	}
	
	public void PopulateUpdatedCategory(Category newCategory, Integer categoryId) {
		
		newCategory.setCategoryid(categoryId);
		
		Category oldCategory = categoryRepository.findByCategoryid(newCategory.getCategoryid());
		
		if(null == newCategory.getCategoryname()) {
			
			newCategory.setCategoryname(oldCategory.getCategoryname());
			
		}
		
		if(null == newCategory.getDatecreated()) {
			
			newCategory.setDatecreated(oldCategory.getDatecreated());
			
		}
		
		if(null == newCategory.getDatemodified()) {
			
			newCategory.setDatemodified(commonService.setTimestamp());
			
		}
		
	}
	
	public boolean CategoryExists(Integer categoryId) {
		
		if (null == categoryRepository.findByCategoryid(categoryId)) {
			
			return false;
			
		}
		
		return true;
		
	}
	
}
