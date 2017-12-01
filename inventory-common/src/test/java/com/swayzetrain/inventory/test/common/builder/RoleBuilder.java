package com.swayzetrain.inventory.test.common.builder;

import com.swayzetrain.inventory.common.model.Role;

public class RoleBuilder {
	
	private String roleName = "Role";
	private String roleDescription = "Role Description";
	
	public RoleBuilder addRoleName(String roleName) {
		
		this.roleName = roleName;
		return this;
		
	}
	
	public RoleBuilder addRoleDescription(String roleDescription) {
		
		this.roleDescription = roleDescription;
		return this;
		
	}
	
	public Role build() {
		
		return new Role(roleName, roleDescription);
		
	}

}
