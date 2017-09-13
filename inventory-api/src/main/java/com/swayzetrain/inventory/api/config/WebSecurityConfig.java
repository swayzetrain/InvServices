package com.swayzetrain.inventory.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.swayzetrain.inventory.api.filter.JWTAuthenticationFilter;
import com.swayzetrain.inventory.api.filter.JWTLoginFilter;
import com.swayzetrain.inventory.auth.services.UserDetailsServiceImplementation;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImplementation userDetailsService;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.TTL}")
	private long jwtTTL;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
		                 .antMatchers("/").permitAll()
		                 .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
		                 .anyRequest().authenticated()
		                 .and()
		                 // We filter the api/login requests
		                 .addFilterBefore(new JWTLoginFilter("/api/v1/accounts", authenticationManager(), jwtSecret, jwtTTL),
		                         UsernamePasswordAuthenticationFilter.class)
		                 // And filter other requests to check the presence of JWT in header
		                 .addFilterBefore(new JWTAuthenticationFilter(jwtSecret, userDetailsService),
		                         UsernamePasswordAuthenticationFilter.class);
		
	}

}
