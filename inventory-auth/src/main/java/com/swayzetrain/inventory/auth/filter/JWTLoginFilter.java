package com.swayzetrain.inventory.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayzetrain.inventory.auth.services.TokenAuthenticationService;
import com.swayzetrain.inventory.common.model.AccountCredentials;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private String jwtSecret;
	private long jwtTTL;
	

    public JWTLoginFilter(String url, AuthenticationManager authManager, String jwtSecret, long jwtTTL) {
    	
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        
        this.setJwtSecret(jwtSecret);
    	this.setJwtTTL(jwtTTL);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
    	
        AccountCredentials creds = new ObjectMapper()
                .readValue(req.getInputStream(), AccountCredentials.class);
        
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
                
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
    	
    	System.out.println("Entering JWTLoginFilter successfulAuthentication");
    	
        TokenAuthenticationService.addAuthentication(res, auth.getName(), this.jwtSecret, this.jwtTTL);
        
    }

	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public long getJwtTTL() {
		return jwtTTL;
	}

	public void setJwtTTL(long jwtTTL) {
		this.jwtTTL = jwtTTL;
	}
}