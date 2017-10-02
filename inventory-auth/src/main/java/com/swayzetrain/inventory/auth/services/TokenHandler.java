package com.swayzetrain.inventory.auth.services;

import java.util.Date;

import com.swayzetrain.inventory.auth.enums.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
	
	public Integer parseInstanceFromToken(String token, String jwtSecret) {
		Jws<Claims> claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""));
		
		Integer instance = (Integer) claims.getBody().get(Constants.TOKEN_INSTANCE_STRING);
		
		return instance;
	}
	
	public String GenerateJWT(String username, String jwtSecret, long jwtTTL) {
		
		String jwtStr = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + jwtTTL)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		
		return jwtStr;
		
	}
	
	public String GenerateJWT(String username, Integer instance, String jwtSecret, long jwtTTL) {
		
		String jwtStr = Jwts.builder().setSubject(username).claim(Constants.TOKEN_INSTANCE_STRING, instance).setExpiration(new Date(System.currentTimeMillis() + jwtTTL)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		
		return jwtStr;
		
	}

}
