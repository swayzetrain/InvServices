package com.swayzetrain.inventory.common.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Instance;

public interface InstanceRepository extends JpaRepository<Instance,Long> {
	
	Instance findByInstanceid(Integer instanceid);
	List<Instance> findByInstanceidIn(ArrayList<Integer> instanceidList);

}
