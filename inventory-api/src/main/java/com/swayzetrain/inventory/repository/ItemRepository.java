package com.swayzetrain.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	Item findByItemname(String itemname);

}
