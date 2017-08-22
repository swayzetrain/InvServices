package com.swayzetrain.inventory.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.model.Category;
import com.swayzetrain.inventory.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.repository.CategoryRepository;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories() {
		ArrayList<Category> categoryList = (ArrayList<Category>) categoryRepository.findAll();
	
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryById(@PathVariable(value="categoryId") Integer categoryId) {
		
		Category categoryResponse = categoryRepository.findByCategoryid(categoryId);
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		
		category.setDatecreated(setTimestamp());
		category.setDatemodified(setTimestamp());
		
		Category categoryResponse = categoryRepository.save(category);
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{categoryId}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryById(@PathVariable(value = "categoryId") Integer categoryId) {
		
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();
		ArrayList<Category> categoryList = new ArrayList<Category>();
		
		categoryList.add(categoryRepository.findByCategoryid(categoryId));
		
		categoryDeleteResponse.setCategoriesDeleted(categoryList);
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategoryid(categoryId));
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/name/{categoryName}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryByName(@PathVariable(value = "categoryId") String categoryName) {
		
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();
		
		categoryDeleteResponse.setCategoriesDeleted(categoryRepository.findByCategoryname(categoryName));
		categoryDeleteResponse.setDeletedCount(categoryRepository.deleteByCategoryname(categoryName));
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/id/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategoryById(@PathVariable(value = "categoryId") Integer categoryId, @RequestBody Category category) {
		
		Category oldCategory = categoryRepository.findByCategoryid(categoryId);
		
		if(null == oldCategory) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category does not exist");
			
		}
		
		category.setCategoryid(categoryId);
		category.setDatemodified(setTimestamp());
		category.setDatecreated(oldCategory.getDatecreated());
		
		Category categoryResponse = categoryRepository.save(category);
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	public Timestamp setTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
}
