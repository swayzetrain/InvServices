package com.swayzetrain.inventory.test.common.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.swayzetrain.inventory.common.model.Role;
import com.swayzetrain.inventory.common.repository.RoleRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.RoleBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class RoleRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void whenFindByRoleId_thenReturnRole() {
		
		// given
		Role role1 = new RoleBuilder().build();
		entityManager.persistAndFlush(role1);
		
		// when
		Role foundRole = roleRepository.findByRoleid(role1.getRoleid());
		
		// then
		assertThat(foundRole).isEqualToComparingFieldByFieldRecursively(role1);
		
	}
	
	@Test
	public void whenFindByRoleName_thenReturnRole() {
		
		// given
		Role role1 = new RoleBuilder().build();
		entityManager.persistAndFlush(role1);
		
		// when
		Role foundRole = roleRepository.findByRolename(role1.getRolename());
		
		// then
		assertThat(foundRole).isEqualToComparingFieldByFieldRecursively(role1);
		
	}

}