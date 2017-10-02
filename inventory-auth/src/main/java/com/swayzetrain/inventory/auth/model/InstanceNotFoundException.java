package com.swayzetrain.inventory.auth.model;

import org.springframework.security.core.AuthenticationException;

public class InstanceNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = -6604741501305298975L;

	public InstanceNotFoundException(String msg) {
		super(msg);
	}
	
	public InstanceNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
