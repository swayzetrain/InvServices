package com.swayzetrain.inventory.auth.model;

import org.springframework.security.core.AuthenticationException;

public class UserRoleNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 5901362547060789590L;

	public UserRoleNotFoundException(String msg) {
		super(msg);
	}
	
	public UserRoleNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}
