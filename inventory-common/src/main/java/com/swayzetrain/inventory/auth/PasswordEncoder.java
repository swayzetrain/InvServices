package com.swayzetrain.inventory.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

	public PasswordEncoder() {
		
	}
	
	public String encodePassword(String password) {
		
		String salt = BCrypt.gensalt();
		
		return BCrypt.hashpw(password, salt);
		
	}
}
