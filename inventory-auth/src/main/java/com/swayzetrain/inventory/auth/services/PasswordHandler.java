package com.swayzetrain.inventory.auth.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHandler {
	
public PasswordHandler() {
		
	}
	
	public String encodePassword(String password) {
		
		String salt = BCrypt.gensalt();
		
		return BCrypt.hashpw(password, salt);
		
	}
	
	public boolean CheckPassword(String password, String hashedPassword) {
		
		if(BCrypt.checkpw(password, hashedPassword)) {
			
			return true;
		}
		
		return false;
		
	}

}
