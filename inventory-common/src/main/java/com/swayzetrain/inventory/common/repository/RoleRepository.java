package com.swayzetrain.inventory.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRoleid(Integer roleid);
	Role findByRolename(String rolename);
	
}
