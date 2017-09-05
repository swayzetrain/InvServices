package com.swayzetrain.inventory.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.google.gson.JsonObject;

public class MessageResponse {
	
	private HttpHeaders httpHeader = new HttpHeaders();
	private HttpStatus httpStatus;
	JsonObject jsonObject = new JsonObject();
	
	public MessageResponse(String fieldName, String message, MediaType mediaType, HttpStatus httpStatus) {
		
		this.httpHeader.setContentType(mediaType);
		this.httpStatus = httpStatus;
		jsonObject.addProperty(fieldName, message);
		
	}
	
	public HttpHeaders getHttpHeader() {
		return httpHeader;
	}

	public void setHttpHeader(HttpHeaders httpHeader) {
		this.httpHeader = httpHeader;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JsonObject MultiMessageResponseBuilder (String[][] properties) {
		
		for(int i = 0; i < properties.length; i++) {
			
			jsonObject.addProperty(properties[i][0], properties[i][1]);
			
		}
		
		return jsonObject;
		
	}
	
	public JsonObject addParameterPair(String key, String value) {
		
		jsonObject.addProperty(key, value);
		
		return jsonObject;		
		
	}
	
}
