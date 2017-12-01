package com.swayzetrain.inventory.test.common.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.swayzetrain.inventory.common.model.UserRole;
import com.swayzetrain.inventory.common.repository.UserRoleRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.UserRoleBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class UserRoleRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Test
	public void whenFindByInstanceId_thenReturnUserRoles() {
		
		// given
		UserRole userRole1 = new UserRoleBuilder().build();
		UserRole userRole2 = new UserRoleBuilder().addInstanceId(2).build();
		entityManager.persist(userRole1);
		entityManager.persist(userRole2);
		entityManager.flush();
		
		// when
		List<UserRole> foundUserRoleList = userRoleRepository.findByInstanceid(userRole1.getInstanceid());
		
		// then
		assertThat(foundUserRoleList).contains(userRole1).doesNotContain(userRole2);
		
	}
	
	@Test
	public void whenFindByUserRoleId_thenReturnUserRole() {
		
		// given
		UserRole userRole1 = new UserRoleBuilder().build();
		entityManager.persistAndFlush(userRole1);
		
		// when
		UserRole foundUserRole = userRoleRepository.findByUserroleid(userRole1.getUserroleid());
		
		// then
		assertThat(foundUserRole).isEqualToComparingFieldByFieldRecursively(userRole1);
		
	}
	
	@Test
	public void whenFindByRoleId_thenReturnRoles() {
		
		// given
		UserRole userRole1 = new UserRoleBuilder().build();
		UserRole userRole2 = new UserRoleBuilder().addRoleId(2).build();
		entityManager.persist(userRole1);
		entityManager.persist(userRole2);
		entityManager.flush();
		
		// when
		List<UserRole> foundUserRoleList = userRoleRepository.findByRoleid(userRole1.getRoleid());
		
		// then
		assertThat(foundUserRoleList).contains(userRole1).doesNotContain(userRole2);
		
	}
	
	@Test
	public void whenFindByUserId_thenReturnUserRoles() {
		
		// given
		UserRole userRole1 = new UserRoleBuilder().build();
		UserRole userRole2 = new UserRoleBuilder().addUserId(2).build();
		entityManager.persist(userRole1);
		entityManager.persist(userRole2);
		entityManager.flush();
		
		// when
		List<UserRole> foundUserRoleList = userRoleRepository.findByUserid(userRole1.getUserid());
		
		// then
		assertThat(foundUserRoleList).contains(userRole1).doesNotContain(userRole2);
		
	}
	
	@Test
	public void whenFindByUserIdAndInstanceId_thenReturnUserRole() {
		
		// given
		UserRole userRole1 = new UserRoleBuilder().build();
		entityManager.persistAndFlush(userRole1);
		
		// when
		UserRole foundUserRole = userRoleRepository.findByUseridAndInstanceid(userRole1.getUserid(), userRole1.getInstanceid());
		
		// then
		assertThat(foundUserRole).isEqualToComparingFieldByFieldRecursively(userRole1);
		
	}

}
