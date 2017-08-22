package com.swayzetrain.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	ArrayList<Item> findByItemname(String itemname);
	Item findByItemid(Integer itemid);
	ArrayList<Item> findByCategoryid(Integer categoryid);
	
	Integer deleteByItemid(Integer itemid);
	Integer deleteByItemname(String itemname);
	Integer deleteByCategoryid(Integer categoryid);
	
}
