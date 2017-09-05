package com.swayzetrain.inventory.model;

public class UserAuthorizationDetails {
	
	private Integer userid;
	private String username;
	private String password;
	private Integer roleid;
	private String rolename;
	private Integer userroleid;
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public Integer getUserroleid() {
		return userroleid;
	}
	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}

}
