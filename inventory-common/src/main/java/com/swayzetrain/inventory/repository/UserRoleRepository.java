package com.swayzetrain.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByUserroleid(Integer userroleid);
	UserRole findByRoleid(Integer roleid);
	UserRole findByUserid(Integer userid);
	
}
