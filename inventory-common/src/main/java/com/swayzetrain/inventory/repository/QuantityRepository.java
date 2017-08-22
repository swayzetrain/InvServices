package com.swayzetrain.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

	Quantity findByQuantityid(Integer quantityid);
	Quantity findByItemid(Integer itemid);
	
}
