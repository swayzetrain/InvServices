package com.swayzetrain.inventory.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_Id")
	private Integer itemid;
	
	@Column(name = "item_Name", nullable = true)
	private String itemname;
	
	@Column(name = "category_Id", nullable = false)
	private Integer categoryid;
	
	@Column(name = "date_Created", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	private Timestamp datecreated;
	
	@Column(name = "date_Modified", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
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
		
}
