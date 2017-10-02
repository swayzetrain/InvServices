package com.swayzetrain.inventory.common.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Instance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "instance_Id")
	@Null(groups = {New.class}, message = "instanceid must be null for this request")
	private Integer instanceid;
	
	@Column(name = "instance_Name")
	@Size(min=0, max=75)
	@NotNull(groups = {New.class}, message = "instancename is required for this request")
	private String instancename;
	
	@Column(name = "instance_Description")
	@Size(min=0, max=150)
	private String instancedescription;
	
	@Column(name = "creation_User_Id")
	private Integer creationuserid;
	
	@Column(name = "date_Created", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class}, message = "datecreated must be null for this request")
	private Timestamp datecreated;
	
	@Column(name = "date_Modified", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class}, message = "datemodified must be null for this request")
	private Timestamp datemodified;

	public Integer getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(Integer instanceid) {
		this.instanceid = instanceid;
	}

	public String getInstancename() {
		return instancename;
	}

	public void setInstancename(String instancename) {
		this.instancename = instancename;
	}

	public String getInstancedescription() {
		return instancedescription;
	}

	public void setInstancedescription(String instancedescription) {
		this.instancedescription = instancedescription;
	}

	public Integer getCreationuserid() {
		return creationuserid;
	}

	public void setCreationuserid(Integer creationuserid) {
		this.creationuserid = creationuserid;
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
	
	public interface New {
		
	}

}
