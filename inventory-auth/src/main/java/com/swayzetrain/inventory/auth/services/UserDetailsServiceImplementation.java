package com.swayzetrain.inventory.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.swayzetrain.inventory.auth.model.InstanceNotFoundException;
import com.swayzetrain.inventory.auth.model.RoleNotFoundException;
import com.swayzetrain.inventory.auth.model.UserAuthorizationDetails;
import com.swayzetrain.inventory.auth.model.UserRoleNotFoundException;
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
		
		if ( username == null || username.isEmpty() ){
	        throw new UsernameNotFoundException("username is empty");
	    }		

	    User foundUser = userRepository.findByUsername(username);
	    
	    if (null != foundUser) {
	    	
	    
	    UserAuthorizationDetails userAuthorizationDetails = loadRoles(foundUser,null);
	    
	    if( userAuthorizationDetails != null ){

	        return userAuthorizationDetails;

	    	}
	    
	    }
	    
	    throw new UsernameNotFoundException( username + "is not found");
	}
	
	
	public UserDetails loadUserByUsernameAndInstanceid(String username, Integer instanceid) throws UsernameNotFoundException, InstanceNotFoundException, UserRoleNotFoundException, RoleNotFoundException {
		
		if (null == username || username.isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}
		
		if (null == instanceid) {
			throw new InstanceNotFoundException("instance is empty");
		}
		
		User foundUser = userRepository.findByUsername(username);
		
		if (null == foundUser) {
			throw new UsernameNotFoundException(username + "is not found");
		}
		
		UserRole foundUserRole = userRoleRepository.findByUseridAndInstanceid(foundUser.getUserid(), instanceid);
		
		if (null == foundUserRole) {
			throw new UserRoleNotFoundException("The specifid userrole is not defined");
		}
		
		Role foundRole = roleRepository.findByRoleid(foundUserRole.getRoleid());
		
		if (null == foundRole) {
			throw new RoleNotFoundException("The specified Role is not defined");
		}
		
		UserAuthorizationDetails userAuthorizationDetails = new UserAuthorizationDetails(foundUser.getUserid(), foundUser.getUsername(), foundUser.getPassword(), foundUser.isEnabled(), foundRole.getRolename(), foundUserRole.getInstanceid());
		
		return userAuthorizationDetails;
		
	}
	
	public UserAuthorizationDetails loadRoles(User user, Integer instanceId) {
		
		UserAuthorizationDetails userAuthorizationDetails = null;
		UserRole foundUserRole = null;
		
		if (null != instanceId) {
			foundUserRole = userRoleRepository.findByUseridAndInstanceid(user.getUserid(),instanceId);
		}
		
		else {
			foundUserRole = userRoleRepository.findByUserid(user.getUserid());
		}
		
	    
	    if(null == foundUserRole) {
	    	
	    	userAuthorizationDetails = new UserAuthorizationDetails(user.getUserid(), user.getUsername(), user.getPassword(), user.isEnabled());

	    	return userAuthorizationDetails;
	    	
	    }
	    
	    Role foundRole = roleRepository.findByRoleid(foundUserRole.getRoleid());
	    
	    if (null != foundRole) {
	    	
	    	userAuthorizationDetails = new UserAuthorizationDetails(user.getUserid(), user.getUsername(), user.getPassword(), user.isEnabled(), foundRole.getRolename());
	    	
	    }
	    
	    return userAuthorizationDetails;
		
	}
		
}