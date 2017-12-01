package com.swayzetrain.inventory.common.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	ArrayList<Item> findByInstanceid(Integer instanceid);
	Item findByItemidAndInstanceid(Integer itemid, Integer instanceid);
	ArrayList<Item> findByItemnameAndCategoryidAndInstanceid(String itemname, Integer categoryid, Integer instanceid);
	ArrayList<Item> findByItemnameAndInstanceid(String itemname, Integer instanceid);
	ArrayList<Item> findByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	Item findByItemidAndItemnameAndInstanceid(Integer itemid, String itemname, Integer instanceid);
	Item findByItemidAndItemnameAndCategoryidAndInstanceid(Integer itemid, String itemname, Integer categoryid, Integer instanceid);
	Item findByItemidAndCategoryidAndInstanceid(Integer itemid, Integer categoryid, Integer instanceid);
	
	Integer deleteByItemidAndInstanceid(Integer itemid, Integer instanceid);
	Integer deleteByItemnameAndCategoryidAndInstanceid(String itemname, Integer categoryid, Integer instanceid);
	Integer deleteByItemnameAndInstanceid(String itemname, Integer instanceid);
	Integer deleteByCategoryidAndInstanceid(Integer categoryid, Integer instanceid);
	
}
