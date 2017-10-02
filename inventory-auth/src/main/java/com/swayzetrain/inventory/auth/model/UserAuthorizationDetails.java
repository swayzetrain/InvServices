package com.swayzetrain.inventory.auth.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.swayzetrain.inventory.auth.enums.Constants;

public class UserAuthorizationDetails implements UserDetails {
	
	private static final long serialVersionUID = 3245333581916076330L;
	private Integer userid;
	private String username;
	private String password;
	private boolean enabled;
	private String rolename;
	private Integer instanceid;
	
	public UserAuthorizationDetails() {
		
	}
	
	public UserAuthorizationDetails(Integer userid, String username, String password, boolean enabled, String rolename) {
		// Used for an established user
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.rolename = rolename;
		
	}
	
	public UserAuthorizationDetails(Integer userid, String username, String password, boolean enabled, String rolename, Integer instanceid) {
		// Used for an established user
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.rolename = rolename;
		this.instanceid = instanceid;
		
	}
	
	public UserAuthorizationDetails(Integer userid, String username, String password, boolean enabled) {
		// Used for a new user
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		
	}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(Integer instanceid) {
		this.instanceid = instanceid;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		
		list.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + rolename));
		
		return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return enabled;
	}
}
