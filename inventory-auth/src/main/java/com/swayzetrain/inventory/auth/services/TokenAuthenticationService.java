package com.swayzetrain.inventory.auth.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.google.gson.JsonObject;
import com.swayzetrain.inventory.auth.enums.Constants;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {
    
    private static TokenHandler tokenHandler = new TokenHandler();

    public static void addAuthentication(HttpServletResponse response, String username, String jwtSecret, long jwtTTL) {
    	
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty(Constants.MESSAGE,"user authentication successful");
    	
    	try {
    		response.addHeader(Constants.AUTH_HEADER_STRING, Constants.TOKEN_PREFIX + " " + tokenHandler.GenerateJWT(username, jwtSecret, jwtTTL));
    		response.setContentType(Constants.CONTENT_TYPE_JSON);
    		response.getWriter().write(jsonObject.toString());
    	}
    	catch(IOException e) {
    		response.setStatus(500);
    	}

    }

    public static Authentication getAuthentication(HttpServletRequest request, String jwtSecret, UserDetailsServiceImplementation userDetailsService) {
        final String token = request.getHeader(Constants.AUTH_HEADER_STRING);
        
        if (token != null) {
            final String username = tokenHandler.parseUserFromToken(token, jwtSecret);
            if (username != null) {
            	
            	UserAuthorizationDetails userAuthorizationDetails = (UserAuthorizationDetails) userDetailsService.loadUserByUsername(username);
            	
            	return new UsernamePasswordAuthenticationToken(username, null, userAuthorizationDetails.getAuthorities());
            }
        }
        return null;
    }
}