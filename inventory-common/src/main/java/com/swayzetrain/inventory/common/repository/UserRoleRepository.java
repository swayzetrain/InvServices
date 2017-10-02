package com.swayzetrain.inventory.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	List<UserRole> findByInstanceid(Integer instanceid);
	UserRole findByUserroleid(Integer userroleid);
	UserRole findByRoleid(Integer roleid);
	UserRole findByUserid(Integer userid);
	UserRole findByUseridAndInstanceid(Integer userid, Integer instanceid);
	
}
