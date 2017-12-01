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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_Id")
	@Null(groups = {New.class}, message = "categoryid must be null for this request")
	private Integer categoryid;
	
	@Column(name = "category_Name")
	@Size(min=0, max=75)
	@NotNull(groups = {New.class}, message = "categoryname is required for this request")
	private String categoryname;
	
	@Column(name = "instance_Id")
	private Integer instanceid;
	
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

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Integer getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(Integer instanceid) {
		this.instanceid = instanceid;
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
	
	public Category() {
		
		
	}
	
	public Category(String categoryname, Integer instanceid, Integer creationuserid, Timestamp datecreated, Timestamp datemodified) {
		
		this.categoryname = categoryname;
		this.instanceid = instanceid;
		this.creationuserid = creationuserid;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		
	}
	
	public interface New {
		
	}
	
}
