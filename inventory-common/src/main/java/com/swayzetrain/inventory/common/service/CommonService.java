package com.swayzetrain.inventory.common.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	public CommonService() {
		
	}

	public Timestamp setTimestamp() {
	
		return new java.sql.Timestamp(new java.util.Date().getTime());
		
	}
	
}
