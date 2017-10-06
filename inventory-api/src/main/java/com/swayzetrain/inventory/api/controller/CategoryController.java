package com.swayzetrain.inventory.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayzetrain.inventory.api.service.category.CategoryRemover;
import com.swayzetrain.inventory.api.service.category.CategoryRetriever;
import com.swayzetrain.inventory.api.service.category.CategoryWriter;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Category;

@RestController
@RequestMapping("/api/v1/categories")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CategoryController {
	
	@Autowired
	private CategoryWriter categoryWriter;
	
	@Autowired
	private CategoryRemover categoryRemover;
	
	@Autowired
	private CategoryRetriever categoryRetriever;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> getCategories(@RequestParam(value = "categoryname", required = false) String categoryName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return categoryRetriever.GetCategories(categoryName, null, userAuthorizationDetails);
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> getCategoryById(@PathVariable(value="categoryId") Integer categoryId, @RequestParam(value = "categoryname", required = false) String categoryName, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return categoryRetriever.GetCategories(categoryName, categoryId, userAuthorizationDetails);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Category> addCategory(@Validated(Category.New.class)@RequestBody Category category, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		
		return categoryWriter.saveNewCategory(category, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<?> deleteCategoryById(@PathVariable(value = "categoryId") Integer categoryId,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
	
		return categoryRemover.RemoveCategoryById(categoryId, userAuthorizationDetails);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<?> deleteCategoryByName(@RequestParam(value = "categoryName") String categoryName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return categoryRemover.RemoveCategoryByName(categoryName, userAuthorizationDetails);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateCategoryById(@PathVariable(value = "categoryId") Integer categoryId, @RequestBody Category category,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		return categoryWriter.updateExisitingCategory(categoryId, category, userAuthorizationDetails);
		
	}
	
}
