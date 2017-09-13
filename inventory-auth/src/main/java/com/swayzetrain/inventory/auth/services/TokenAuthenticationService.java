package com.swayzetrain.inventory.auth.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.swayzetrain.inventory.auth.enums.Constants;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {
    
    private static TokenHandler tokenHandler = new TokenHandler();

    public static void addAuthentication(HttpServletResponse response, String username, String jwtSecret, long jwtTTL) {
    	
        response.addHeader(Constants.AUTH_HEADER_STRING, Constants.TOKEN_PREFIX + " " + tokenHandler.GenerateJWT(username, jwtSecret, jwtTTL));
    
    }

    public static Authentication getAuthentication(HttpServletRequest request, String jwtSecret, UserDetailsServiceImplementation userDetailsService) {
        final String token = request.getHeader(Constants.AUTH_HEADER_STRING);
        
        System.out.println("token: " + token);
        
        if (token != null) {
            final String username = tokenHandler.parseUserFromToken(token, jwtSecret);
            if (username != null) {
            	
            	UserAuthorizationDetails userAuthorizationDetails = (UserAuthorizationDetails) userDetailsService.loadUserByUsername(username);
            	
            	System.out.println("getAuth: " + userAuthorizationDetails.getAuthorities().toString());
            	return new UsernamePasswordAuthenticationToken(username, null, userAuthorizationDetails.getAuthorities());
            }
        }
        return null;
    }
}