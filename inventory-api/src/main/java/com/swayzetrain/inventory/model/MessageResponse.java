package com.swayzetrain.inventory.model;

public class MessageResponse {

	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageResponse () {		
		
	}
	
	public MessageResponse MessageResponseBuilder (String keyValue) {
		this.setMessage(keyValue);
		
		return this;
	}
}
