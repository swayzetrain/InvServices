package com.swayzetrain.inventory.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.repository.RoleRepository;

@Service
public class RoleRequestService {
	
	@Autowired
	private RoleRepository roleRepository;

	public List<Role> GetRoles (Integer roleId) {
		
		if (null == roleId) {
			
			return roleRepository.findAll();
			
		}
		
		List<Role> roleList = new ArrayList<Role>();
		
		roleList.add(roleRepository.findByRoleid(roleId));
		
		return roleList;
		
	}
	
}
