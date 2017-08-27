package com.swayzetrain.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.enums.Constants;
import com.swayzetrain.inventory.model.Category;
import com.swayzetrain.inventory.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.model.MessageResponse;
import com.swayzetrain.inventory.repository.CategoryRepository;
import com.swayzetrain.inventory.service.CategoryRequestService;
import com.swayzetrain.inventory.service.CommonService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryRequestService categoryRequestService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories(@RequestParam(value = "categoryname", required = false) String categoryName) {
		
		List<Category> categoryList = categoryRequestService.GetAllCategoriesFiltering(categoryName);
	
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryById(@PathVariable(value="categoryId") Integer categoryId) {
		
		Category categoryResponse = categoryRepository.findByCategoryid(categoryId);
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		
		category.setDatecreated(commonService.setTimestamp());
		category.setDatemodified(commonService.setTimestamp());
		
		Category categoryResponse = categoryRepository.save(category);
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryById(@PathVariable(value = "categoryId") Integer categoryId) {
	
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();

		categoryRequestService.DeleteCategoryById(categoryId, categoryDeleteResponse);
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryByName(@RequestParam(value = "categoryName") String categoryName ) {
		
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();
		
		categoryRequestService.DeleteCategoriesFiltering(categoryName, categoryDeleteResponse);
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategoryById(@PathVariable(value = "categoryId") Integer categoryId, @RequestBody Category category) {
		
		if(!categoryRequestService.CategoryExists(categoryId)) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		categoryRequestService.PopulateUpdatedCategory(category, categoryId);
		
		Category categoryResponse = categoryRepository.save(category);
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
}
