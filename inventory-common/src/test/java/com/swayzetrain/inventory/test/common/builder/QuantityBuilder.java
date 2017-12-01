package com.swayzetrain.inventory.test.common.builder;

import java.sql.Timestamp;

import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.service.CommonService;

public class QuantityBuilder {
	
	CommonService commonService = new CommonService();
	
	private Integer itemId = 1;
	private Integer quantity = 10;
	private String quantityType = "pints";
	private Timestamp dateModified = commonService.setTimestamp();
	
	public QuantityBuilder addItemId(Integer itemId) {
		
		this.itemId = itemId;
		return this;
		
	}
	
	public QuantityBuilder addQuantity(Integer quantity) {
		
		this.quantity = quantity;
		return this;
		
	}
	
	public QuantityBuilder addQuantityType(String quantityType) {
		
		this.quantityType = quantityType;
		return this;
		
	}
	
	public QuantityBuilder addDateModified(Timestamp date) {
		
		this.dateModified = date;
		return this;
		
	}

	public Quantity build() {
		
		return new Quantity(itemId, quantity, quantityType, dateModified);
		
	}

}
