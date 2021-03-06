package com.swayzetrain.inventory.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

	Quantity findByQuantityid(Integer quantityid);
	Quantity findByItemid(Integer itemid);
	List<Quantity> findByItemidIn(List<Integer> itemIdList);
	
}
