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
@Table(name = "quantity")
public class Quantity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "quantity_Id")
	@Null(groups = {New.class}, message = "quantityid must be null for this request")
	private Integer quantityid;
	
	@Column(name = "item_Id")
	@NotNull(groups = {New.class}, message = "itemid is required for this request")
	private Integer itemid;
	
	@Column(name = "quantity")
	@NotNull(groups = {New.class}, message = "quantity is required for this request")
	private Integer quantity;
	
	@Column(name = "quantity_Type")
	@Size(min=0, max=50)
	@NotNull(groups = {New.class}, message = "quantitytype is required for this request")
	private String quantitytype;
	
	@Column(name = "date_Modified", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "EST")
	@Null(groups = {New.class}, message = "datemodified must be null for this request")
	private Timestamp datemodified;

	public Integer getQuantityid() {
		return quantityid;
	}

	public void setQuantityid(Integer quantityid) {
		this.quantityid = quantityid;
	}

	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getQuantitytype() {
		return quantitytype;
	}

	public void setQuantitytype(String quantitytype) {
		this.quantitytype = quantitytype;
	}

	public Timestamp getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Timestamp dateModified) {
		this.datemodified = dateModified;
	}
	
	public Quantity() {
		
	}
	
	public Quantity(Integer itemId, Integer quantity, String quantityType, Timestamp dateModified) {
		
		this.itemid = itemId;
		this.quantity = quantity;
		this.quantitytype = quantityType;
		this.datemodified = dateModified;
		
	}
	
	public interface New {
		
	}
}
