package com.swayzetrain.inventory.auth.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.swayzetrain.inventory.auth.services.TokenAuthenticationService;
import com.swayzetrain.inventory.auth.services.UserDetailsServiceImplementation;

import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {
	
	private String jwtSecret;
	private long jwtTTL;
	private UserDetailsServiceImplementation userDetailsService;

    public JWTAuthenticationFilter(String jwtSecret, Long jwtTTL, UserDetailsServiceImplementation userDetailsService) {
		
    	this.setJwtSecret(jwtSecret);
    	this.setJwtTTL(jwtTTL);
    	this.setUserDetailsService(userDetailsService);
    	
	}

	@Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
		
        Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)request, this.jwtSecret, userDetailsService);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        
        filterChain.doFilter(request,response);
    }

	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public Long getJwtTTL() {
		return jwtTTL;
	}

	public void setJwtTTL(Long jwtTTL) {
		this.jwtTTL = jwtTTL;
	}

	public UserDetailsServiceImplementation getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsServiceImplementation userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}