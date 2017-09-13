package com.swayzetrain.inventory.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryid(Integer categoryid);
	List<Category> findByCategoryname(String categoryname);
	
	Integer deleteByCategoryid(Integer categoryid);
	Integer deleteByCategoryname(String categoryname);
}
