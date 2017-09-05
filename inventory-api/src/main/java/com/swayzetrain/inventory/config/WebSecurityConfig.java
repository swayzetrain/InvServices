package com.swayzetrain.inventory.config;

import javax.sql.DataSource;

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

import com.swayzetrain.inventory.auth.UserDetailsServiceImplementation;
import com.swayzetrain.inventory.enums.SqlConstants;
import com.swayzetrain.inventory.filter.JWTAuthenticationFilter;
import com.swayzetrain.inventory.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private UserDetailsServiceImplementation userDetailsService;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.TTL}")
	private long jwtTTL;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder()).dataSource(datasource).usersByUsernameQuery(SqlConstants.FIND_USER_QUERY).authoritiesByUsernameQuery(SqlConstants.FIND_ROLES_QUERY);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
		                 .antMatchers("/").permitAll()
		                 .antMatchers(HttpMethod.POST, "/api/v1/accounts").permitAll()
		                 .anyRequest().authenticated()
		                 .and()
		                 // We filter the api/login requests
		                 .addFilterBefore(new JWTLoginFilter("/api/v1/accounts", authenticationManager(), jwtSecret, jwtTTL),
		                         UsernamePasswordAuthenticationFilter.class)
		                 // And filter other requests to check the presence of JWT in header
		                 .addFilterBefore(new JWTAuthenticationFilter(jwtSecret),
		                         UsernamePasswordAuthenticationFilter.class);
		
	}

}
