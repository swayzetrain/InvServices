package com.swayzetrain.inventory.auth.model;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = -5782109935871046429L;

	public RoleNotFoundException(String msg) {
		super(msg);
	}
	
	public RoleNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
