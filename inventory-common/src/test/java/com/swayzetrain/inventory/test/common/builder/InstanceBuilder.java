package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.Instance;
import com.swayzetrain.inventory.common.service.CommonService;

public class InstanceBuilder {
	
	CommonService commonService = new CommonService();
	
	private String instanceName = "Instance";
	private String instanceDescription = "Description";
	private Integer creationUserId = 0;
	private Timestamp dateCreated = commonService.setTimestamp();
	private Timestamp dateModified = commonService.setTimestamp();
	
	public InstanceBuilder addInstanceName(String instanceName) {
		
		this.instanceName = instanceName;
		return this;
		
	}
	
	public InstanceBuilder addInstanceDescription(String instanceDescription) {
		
		this.instanceDescription = instanceDescription;
		return this;
		
	}

	public InstanceBuilder addCreationUserId(Integer userId) {
		
		this.creationUserId = userId;
		return this;
		
	}

	public InstanceBuilder addDateCreated(Timestamp date) {
		
		this.dateCreated = date;
		return this;
		
	}
	
	public InstanceBuilder addDateModified(Timestamp date) {
		
		this.dateModified = date;
		return this;
		
	}
	
	public Instance build() {
		
		return new Instance(instanceName, instanceDescription, creationUserId, dateCreated, dateModified);
		
	}
	
}
