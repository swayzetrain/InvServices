package com.swayzetrain.inventory.auth.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.swayzetrain.inventory.auth.enums.Constants;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
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
        
        if (null != token) {
        	
            final String username = tokenHandler.parseUserFromToken(token, jwtSecret);
            Integer instanceid = tokenHandler.parseInstanceFromToken(token, jwtSecret);
            
            if(null == instanceid) {
            	
            	String uri = request.getServletPath();
            	System.out.println("getAuth URI Path: " + uri); 
            	
            	if(uri.startsWith("/api/v1/instances/")) {
            		
            		System.out.println("getAuth match on uri starts with");
            		
            		instanceid = Integer.parseInt(uri.replaceAll("/api/v1/instances/", ""));
            		System.out.println("getAuth converted instance id: " + instanceid);
            	
            	}
            	
            }
            
            if (null != username && null != instanceid) {
            	
            	System.out.println("TokenAService: Adding with username and instanceid");
            	
            	UserAuthorizationDetails userAuthorizationDetails = (UserAuthorizationDetails) userDetailsService.loadUserByUsernameAndInstanceid(username, instanceid);
            	
            	return new UsernamePasswordAuthenticationToken(userAuthorizationDetails, null, userAuthorizationDetails.getAuthorities());
            }
            
            if (null != username && null == instanceid) {
            	
            	System.out.println("TokenAService: Adding with username only");
            	
            	UserAuthorizationDetails userAuthorizationDetails = (UserAuthorizationDetails) userDetailsService.loadUserByUsername(username);
            	
            	return new UsernamePasswordAuthenticationToken(userAuthorizationDetails, null, userAuthorizationDetails.getAuthorities());
            	
            }
        }
        return null;
    }
}