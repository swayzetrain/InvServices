package com.swayzetrain.inventory.auth.services;

import java.util.Date;

import com.swayzetrain.inventory.auth.enums.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHandler {
	
	public TokenHandler() {

	}
	
	public String parseUserFromToken(String token, String jwtSecret) {
        String username = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        
        return username;
    }
	
	public String GenerateJWT(String username, String jwtSecret, long jwtTTL) {
		
		String jwtStr = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + jwtTTL)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		
		return jwtStr;
		
	}

}
