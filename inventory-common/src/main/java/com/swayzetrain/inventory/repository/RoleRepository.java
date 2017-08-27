package com.swayzetrain.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRoleid(Integer roleid);
	Role findByRolename(String rolename);
	
}
