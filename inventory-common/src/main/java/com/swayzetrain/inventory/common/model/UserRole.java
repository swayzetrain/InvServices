package com.swayzetrain.inventory.common.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "userrole")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userrole_Id")
	@Null(groups = {New.class}, message = "userroleid must be null for this request")
	private Integer userroleid;
	
	@Column(name = "user_Id")
	private Integer userid;
	
	@Column(name = "role_Id")
	private Integer roleid;
	
	@Column(name = "instance_Id")
	@NotNull(groups = {New.class}, message = "instanceid is required for this request")
	private Integer instanceid;
	
	@Column(name = "date_Created", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class}, message = "datecreated must be null for this request")
	private Timestamp datecreated;
	
	@Column(name = "date_Modified", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class}, message = "datecreated must be null for this request")
	private Timestamp datemodified;

	public Integer getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(Integer instanceid) {
		this.instanceid = instanceid;
	}

	public Timestamp getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public Timestamp getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Timestamp datemodified) {
		this.datemodified = datemodified;
	}
	
	public UserRole() {
		
	}
	
	public UserRole(Integer userid, Integer roleid, Integer instanceid, Timestamp datecreated, Timestamp datemodified) {
		this.userid = userid;
		this.roleid = roleid;
		this.instanceid = instanceid;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
	}
	
	public interface New {
		
	}

}
