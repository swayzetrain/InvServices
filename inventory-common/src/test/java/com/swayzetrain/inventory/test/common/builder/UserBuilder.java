package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.service.CommonService;

public class UserBuilder {
	
	CommonService commonService = new CommonService();
	
	private String username = "user1";
	private String password = "Password1";
	private Timestamp dateCreated = commonService.setTimestamp();
	private Timestamp dateModified = commonService.setTimestamp();
	private boolean enabled = true;
	
	public UserBuilder addUsername(String username) {
		
		this.username = username;
		return this;
		
	}
	
	public UserBuilder addPassword(String password) {
		
		this.password = password;
		return this;
		
	}
	
	public UserBuilder addDateCreated(Timestamp dateCreated) {
		
		this.dateCreated = dateCreated;
		return this;
		
	}
	
	public UserBuilder addDateModified(Timestamp dateModified) {
		
		this.dateModified = dateModified;
		return this;
		
	}
	
	public UserBuilder setEnabled(Boolean enabled) {
		
		this.enabled = enabled;
		return this;
		
	}
	
	public User build() {
		
		return new User(username, password, dateCreated, dateModified, enabled);
		
	}

}
