package com.swayzetrain.inventory.api.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;

@Service
public class CategoryWriter {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;
	
	public ResponseEntity<Category> saveNewCategory(Category category, UserAuthorizationDetails userAuthorizationDetails) {
		
		category.setCreationuserid(userAuthorizationDetails.getUserid());
		category.setInstanceid(userAuthorizationDetails.getInstanceid());
		category.setDatecreated(commonService.setTimestamp());
		category.setDatemodified(commonService.setTimestamp());
		
		Category categoryResponse = categoryRepository.save(category);
		
		ResponseEntity<Category> response = new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
		return response;
	}
	
	public ResponseEntity<?> updateExisitingCategory(Integer oldCategoryId, Category newCategory, UserAuthorizationDetails userAuthorizationDetails) {
		
		Category oldCategory = categoryRepository.findByCategoryidAndInstanceid(oldCategoryId, userAuthorizationDetails.getInstanceid());
		
		if(null == oldCategory) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		if(null == newCategory.getCategoryname()) {
			
			newCategory.setCategoryname(oldCategory.getCategoryname());
			
		}
		
		newCategory.setInstanceid(userAuthorizationDetails.getInstanceid());
		
		if(null == newCategory.getDatecreated()) {
			
			newCategory.setDatecreated(oldCategory.getDatecreated());
			
		}
		
		newCategory.setDatemodified(commonService.setTimestamp());
		
		Category categoryResponse = categoryRepository.save(newCategory);
		
		ResponseEntity<Category> response = new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
		return response;
	
	}
	
	

}
