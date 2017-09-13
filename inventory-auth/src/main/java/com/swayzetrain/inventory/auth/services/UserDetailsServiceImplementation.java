package com.swayzetrain.inventory.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.RoleRepository;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("LoadByUsername1");
		
		if ( username == null || username.isEmpty() ){
	        throw new UsernameNotFoundException("username is empty");
	    }		
		
		System.out.println("LoadByUsername2");
	    User foundUser = userRepository.findByUsername(username);
	    
	    UserRole foundUserRole = userRoleRepository.findByUserid(foundUser.getUserid());
	    
	    Role foundRole = roleRepository.findByRoleid(foundUserRole.getRoleid());
	    
	    if (null != foundUser && null != foundUserRole && null != foundRole) {
	    	
	    	System.out.println("LoadByUsername3");
	    	UserAuthorizationDetails userAuthorizationDetails = new UserAuthorizationDetails(foundUser.getUserid(), username, foundUser.getPassword(), foundUser.isEnabled(), foundRole.getRolename());
	    
	    	if( userAuthorizationDetails != null ){
	    		System.out.println("roles: " + userAuthorizationDetails.getAuthorities().toString());
		        return userAuthorizationDetails;

		    }
	    	
	    }
	    
	    throw new UsernameNotFoundException( username + "is not found");
	}
		
}