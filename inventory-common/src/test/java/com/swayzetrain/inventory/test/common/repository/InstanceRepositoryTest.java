package com.swayzetrain.inventory.test.common.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.swayzetrain.inventory.common.model.Instance;
import com.swayzetrain.inventory.common.repository.InstanceRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.InstanceBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class InstanceRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private InstanceRepository instanceRepository;
	
	@Test
	public void whenFindByInstanceId_thenReturnInstance() {
		
		// given
		Instance instance1 = new InstanceBuilder().addInstanceName("Instance1").addInstanceDescription("Instance 1 Description").addCreationUserId(1).build();
		
		entityManager.persist(instance1);
		entityManager.flush();
		
		// when
		Instance foundInstance = instanceRepository.findByInstanceid(instance1.getInstanceid());
		
		// then
		assertThat(foundInstance).isEqualToComparingFieldByField(instance1);
		
	}
	
	@Test
	public void whenFindByInstanceIdIn_thenReturnInstances() {
		
		// given
		Instance instance1 = new InstanceBuilder().addInstanceName("Instance1").addInstanceDescription("Instance 1 Description").addCreationUserId(1).build();
		Instance instance2 = new InstanceBuilder().addInstanceName("Instance2").addInstanceDescription("Instance 2 Description").addCreationUserId(1).build();
		Instance instance3 = new InstanceBuilder().addInstanceName("Instance3").addInstanceDescription("Instance 3 Description").addCreationUserId(1).build();
		
		entityManager.persist(instance1);
		entityManager.persist(instance2);
		entityManager.persist(instance3);
		entityManager.flush();
		
		// when
		ArrayList<Integer> instanceIds = new ArrayList<Integer>();
		instanceIds.add(instance1.getInstanceid());
		instanceIds.add(instance2.getInstanceid());
		
		List<Instance> foundInstanceList = instanceRepository.findByInstanceidIn(instanceIds);
		
		// then
		assertThat(foundInstanceList).contains(instance1, instance2).doesNotContain(instance3);
		
	}
	
}