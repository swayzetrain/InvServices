package com.swayzetrain.inventory.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByUserroleid(Integer userroleid);
	UserRole findByRoleid(Integer roleid);
	UserRole findByUserid(Integer userid);
	
}
