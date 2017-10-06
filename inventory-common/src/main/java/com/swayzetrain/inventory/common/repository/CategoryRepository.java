package com.swayzetrain.inventory.common.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	ArrayList<Category> findByInstanceid(Integer instanceid);
	Category findByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	Category findByCategoryidAndCategorynameAndInstanceid(Integer categoryid, String categoryname, Integer instanceid);
	ArrayList<Category> findByCategorynameAndInstanceid(String categoryname, Integer instanceid);
	
	Integer deleteByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	Integer deleteByCategorynameAndInstanceid(String categoryname, Integer instanceid);
}
