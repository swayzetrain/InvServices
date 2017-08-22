package com.swayzetrain.inventory.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordDecoder {

	public PasswordDecoder() {
		
	}
	
	public boolean decodePassword(String password, String hashedPassword) {
		
		if(BCrypt.checkpw(password, hashedPassword)) {
			
			return true;
		}
		
		return false;
		
	}
	
}
