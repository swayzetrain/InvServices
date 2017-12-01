package com.swayzetrain.inventory.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_Id")
	private Integer roleid;
	
	@Column(name = "role_Name")
	@Size(min=0,max=50)
	private String rolename;
	
	@Column(name = "role_Description")
	@Size(min=0, max=150)
	private String roledescription;

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledescription() {
		return roledescription;
	}

	public void setRoledescription(String roledescription) {
		this.roledescription = roledescription;
	}
	
	public Role() {
		
	}
	
	public Role(String roleName, String roleDescription) {
		
		this.rolename = roleName;
		this.roledescription = roleDescription;
		
	}
	
}
