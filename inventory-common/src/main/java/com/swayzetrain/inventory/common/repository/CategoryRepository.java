package com.swayzetrain.inventory.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	List<Category> findByInstanceid(Integer instanceid);
	Category findByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	List<Category> findByCategorynameAndInstanceid(String categoryname, Integer instanceid);
	
	Integer deleteByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	Integer deleteByCategorynameAndInstanceid(String categoryname, Integer instanceid);
}
