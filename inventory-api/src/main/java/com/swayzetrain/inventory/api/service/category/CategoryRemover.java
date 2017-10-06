package com.swayzetrain.inventory.api.service.category;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;

@Service
public class CategoryRemover {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public ResponseEntity<?> RemoveCategoryById(Integer categoryId, UserAuthorizationDetails userAuthorizationDetails) {
		
		ArrayList<Category> categoryList = new ArrayList<Category>();
		
		categoryList.add(categoryRepository.findByCategoryidAndInstanceid(categoryId, userAuthorizationDetails.getInstanceid()));
		
		if(0 == categoryList.size()) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<CategoryDeleteResponse>(new CategoryDeleteResponse(categoryRepository.deleteByCategoryidAndInstanceid(categoryId, userAuthorizationDetails.getInstanceid()),categoryList),HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> RemoveCategoryByName(String name, UserAuthorizationDetails userAuthorizationDetails) {
		
		ArrayList<Category> categoryList = (ArrayList<Category>) categoryRepository.findByCategorynameAndInstanceid(name, userAuthorizationDetails.getInstanceid());
		
		if(0 == categoryList.size()) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		return new ResponseEntity<CategoryDeleteResponse>(new CategoryDeleteResponse(categoryRepository.deleteByCategorynameAndInstanceid(name, userAuthorizationDetails.getInstanceid()),categoryList),HttpStatus.OK);
		
	}

}
