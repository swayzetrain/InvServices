package com.swayzetrain.inventory.test.common.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.swayzetrain.inventory.common.model.User;
import com.swayzetrain.inventory.common.repository.UserRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.UserBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByUserId_thenReturnUser() {
		
		// given
		User user1 = new UserBuilder().build();
		entityManager.persistAndFlush(user1);
		
		// when
		User foundUser = userRepository.findByUserid(user1.getUserid());
		
		// then
		assertThat(foundUser).isEqualToComparingFieldByFieldRecursively(user1);
		
	}
	
	@Test
	public void whenFindByUsername_thenReturnUser() {
		
		// given
		User user1 = new UserBuilder().build();
		entityManager.persistAndFlush(user1);
		
		// when
		User foundUser = userRepository.findByUsername(user1.getUsername());
		
		// then
		assertThat(foundUser).isEqualToComparingFieldByFieldRecursively(user1);
		
	}

}
