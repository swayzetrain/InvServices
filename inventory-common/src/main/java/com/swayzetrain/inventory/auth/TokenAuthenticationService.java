package com.swayzetrain.inventory.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.swayzetrain.inventory.enums.CommonConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    
    private static TokenHandler tokenHandler = new TokenHandler();

    public static void addAuthentication(HttpServletResponse response, String username, String jwtSecret, long jwtTTL) {
    	
        response.addHeader(CommonConstants.AUTH_HEADER_STRING, CommonConstants.TOKEN_PREFIX + " " + tokenHandler.GenerateJWT(username, jwtSecret, jwtTTL));
    
    }

    public static Authentication getAuthentication(HttpServletRequest request, String jwtSecret) {
        final String token = request.getHeader(CommonConstants.AUTH_HEADER_STRING);
        
        System.out.println("token: " + token);
        
        if (token != null) {
            final String username = tokenHandler.parseUserFromToken(token, jwtSecret);
            if (username != null) {
            	
            	
            	
            	return new UsernamePasswordAuthenticationToken(username, null, emptyList());
            }
        }
        return null;
    }
}