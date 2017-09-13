package com.swayzetrain.inventory.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.common.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findByUserid(Integer userid);
}
