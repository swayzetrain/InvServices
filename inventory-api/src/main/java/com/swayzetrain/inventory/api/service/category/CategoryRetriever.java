package com.swayzetrain.inventory.api.service.category;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;

@Service
public class CategoryRetriever {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public ResponseEntity<?> GetCategories(String categoryName, Integer categoryId, UserAuthorizationDetails userAuthorizationDetails) {
		
		if(null != categoryName && null != categoryId) {
			
			return new ResponseEntity<Category>(categoryRepository.findByCategoryidAndCategorynameAndInstanceid(categoryId, categoryName, userAuthorizationDetails.getInstanceid()),HttpStatus.OK);
			
		}

		else if(null == categoryName && null != categoryId) {
			
			return new ResponseEntity<Category>(categoryRepository.findByCategoryidAndInstanceid(categoryId, userAuthorizationDetails.getInstanceid()),HttpStatus.OK);
			
		}
		
		else if(null == categoryId && null != categoryName) {
			
			return new ResponseEntity<ArrayList<Category>>(categoryRepository.findByCategorynameAndInstanceid(categoryName, userAuthorizationDetails.getInstanceid()),HttpStatus.OK);
			
		}
		
		else {
			
			return new ResponseEntity<ArrayList<Category>>(categoryRepository.findByInstanceid(userAuthorizationDetails.getInstanceid()),HttpStatus.OK);
			
		}
		
	}

}
