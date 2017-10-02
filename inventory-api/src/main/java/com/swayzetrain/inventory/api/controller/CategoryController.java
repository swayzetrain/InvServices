package com.swayzetrain.inventory.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.swayzetrain.inventory.api.enums.Constants;
import com.swayzetrain.inventory.api.model.CategoryDeleteResponse;
import com.swayzetrain.inventory.api.model.MessageResponse;
import com.swayzetrain.inventory.api.service.CategoryRequestService;
import com.swayzetrain.inventory.api.service.CommonService;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;

@RestController
@RequestMapping("/api/v1/categories")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryRequestService categoryRequestService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<List<Category>> getCategories(@RequestParam(value = "categoryname", required = false) String categoryName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		List<Category> categoryList = categoryRequestService.GetAllCategoriesFiltering(categoryName, userAuthorizationDetails.getInstanceid());
	
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	@Secured({"ROLE_Viewer","ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Category> getCategoryById(@PathVariable(value="categoryId") Integer categoryId, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		Category categoryResponse = categoryRepository.findByCategoryidAndInstanceid(categoryId, userAuthorizationDetails.getInstanceid());
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<Category> addCategory(@Validated(Category.New.class)@RequestBody Category category, @AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		category.setDatecreated(commonService.setTimestamp());
		category.setDatemodified(commonService.setTimestamp());
		category.setCreationuserid(userAuthorizationDetails.getUserid());
		category.setInstanceid(userAuthorizationDetails.getInstanceid());
		
		Category categoryResponse = categoryRepository.save(category);
		
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryById(@PathVariable(value = "categoryId") Integer categoryId,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
	
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();

		categoryRequestService.DeleteCategoryById(categoryId, userAuthorizationDetails.getInstanceid(), categoryDeleteResponse);
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	@Transactional
	public ResponseEntity<CategoryDeleteResponse> deleteCategoryByName(@RequestParam(value = "categoryName") String categoryName,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		CategoryDeleteResponse categoryDeleteResponse = new CategoryDeleteResponse();
		
		categoryRequestService.DeleteCategoriesFiltering(categoryName, userAuthorizationDetails.getInstanceid(), categoryDeleteResponse);
		
		return new ResponseEntity<CategoryDeleteResponse>(categoryDeleteResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
	@Secured({"ROLE_Creator","ROLE_Admin"})
	public ResponseEntity<?> updateCategoryById(@PathVariable(value = "categoryId") Integer categoryId, @RequestBody Category category,
			@AuthenticationPrincipal UserAuthorizationDetails userAuthorizationDetails) {
		
		if(!categoryRequestService.CategoryExists(categoryId, userAuthorizationDetails.getInstanceid())) {
			
			MessageResponse messageResponse = new MessageResponse(Constants.MESSAGE, Constants.CATEGORY_NOT_FOUND_MESSAGE, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>(messageResponse.getJsonObject().toString(), messageResponse.getHttpHeader(), messageResponse.getHttpStatus());
			
		}
		
		categoryRequestService.PopulateUpdatedCategory(category, categoryId, userAuthorizationDetails.getInstanceid());
		
		Category categoryResponse = categoryRepository.save(category);
		return new ResponseEntity<Category>(categoryResponse, HttpStatus.OK);
		
	}
	
}
