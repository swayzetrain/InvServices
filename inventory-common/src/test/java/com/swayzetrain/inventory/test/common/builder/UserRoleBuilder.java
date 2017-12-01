package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.service.CommonService;

public class UserRoleBuilder {
	
	CommonService commonService = new CommonService();
	
	private Integer userId = 1;
	private Integer roleId = 1;
	private Integer instanceId = 1;
	private Timestamp dateCreated = commonService.setTimestamp();
	private Timestamp dateModified = commonService.setTimestamp();
	
	public UserRoleBuilder addUserId(Integer userId) {
		
		this.userId = userId;
		return this;
		
	}
	
	public UserRoleBuilder addRoleId(Integer roleId) {
		
		this.roleId = roleId;
		return this;
		
	}
	
	public UserRoleBuilder addInstanceId(Integer instanceId) {
		
		this.instanceId = instanceId;
		return this;
		
	}
	
	public UserRoleBuilder addDateCreated(Timestamp dateCreated) {
		
		this.dateCreated = dateCreated;
		return this;
		
	}
	
	public UserRoleBuilder addDateModified(Timestamp dateModified) {
		
		this.dateModified = dateModified;
		return this;
		
	}
	
	public UserRole build() {
		
		return new UserRole(userId, roleId, instanceId, dateCreated, dateModified);
		
	}

}
