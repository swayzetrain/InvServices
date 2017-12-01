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
@Table(name = "item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_Id")
	@Null(groups = {New.class, Existing.class}, message = "itemid must be null for this request")
	private Integer itemid;
	
	@Column(name = "item_Name")
	@Size(min=0, max=75)
	@NotNull(groups = New.class, message = "itemname is required for this request")
	private String itemname;
	
	@Column(name = "category_Id", nullable = false)
	@NotNull(groups = New.class, message = "categoryid is required for this request")
	private Integer categoryid;
	
	@Column(name = "instance_Id")
	private Integer instanceid;
	
	@Column(name = "creation_User_Id")
	private Integer creationuserid;
	
	@Column(name = "date_Created", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class, Existing.class}, message = "datecreated must be null for this request")
	private Timestamp datecreated;
	
	@Column(name = "date_Modified", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class, Existing.class}, message = "datemodified must be null for this request")
	private Timestamp datemodified;
	
	public String getItemname() {
		return itemname;
	}
	
	public void setItemname(String itemName) {
		this.itemname = itemName;
	}
	
	public Integer getItemid() {
		return itemid;
	}
	
	public void setItemid(Integer itemId) {
		this.itemid = itemId;
	}
	
	public Integer getCategoryid() {
		return categoryid;
	}
	
	public void setCategoryid(Integer categoryId) {
		this.categoryid = categoryId;
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
	
	public void setDatecreated(Timestamp ts) {
		this.datecreated = ts;
	}
	
	public Timestamp getDatemodified() {
		return datemodified;
	}
	
	public void setDatemodified(Timestamp dateModified) {
		this.datemodified = dateModified;
	}
	
	public Item() {
		
	}
	
	public Item(String itemName, Integer categoryId, Integer instanceId, Integer creationUserId, Timestamp dateCreated, Timestamp dateModified) {
		
		this.itemname = itemName;
		this.categoryid = categoryId;
		this.instanceid = instanceId;
		this.creationuserid = creationUserId;
		this.datecreated = dateCreated;
		this.datemodified = dateModified;
		
	}
	
	public interface New {
		
	}
	
	public interface Existing {
		
	}
		
}
