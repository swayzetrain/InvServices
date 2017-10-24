package com.swayzetrain.inventory.api.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.repository.RoleRepository;

@Service
public class RoleReader {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public ResponseEntity<?> GetRoles(Integer roleid) {
		
		if(null == roleid) {
			
			return new ResponseEntity<List<Role>>(roleRepository.findAll(), HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Role>(roleRepository.findByRoleid(roleid), HttpStatus.OK);
		
	}

}
