package com.swayzetrain.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryid(Integer categoryid);
	ArrayList<Category> findByCategoryname(String categoryname);
	
	Integer deleteByCategoryid(Integer categoryid);
	Integer deleteByCategoryname(String categoryname);
}
