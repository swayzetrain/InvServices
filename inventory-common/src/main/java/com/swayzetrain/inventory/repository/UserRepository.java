package com.swayzetrain.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayzetrain.inventory.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findByUserid(Integer userid);
}
