package com.swayzetrain.inventory.test.common.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.repository.QuantityRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.QuantityBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class QuantityRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Test
	public void whenFindByQuantityId_thenReturnQuantity() {
		
		// given
		Quantity quantity1 = new QuantityBuilder().build();
		entityManager.persistAndFlush(quantity1);
		
		// when
		Quantity foundQuantity = quantityRepository.findByQuantityid(quantity1.getQuantityid());
		
		// then
		assertThat(foundQuantity).isEqualToComparingFieldByField(quantity1);
		
	}
	
	@Test
	public void whenFindByItemId_thenReturnQuantity() {
		
		Integer itemIdToFind = 1;
		
		// given
		Quantity quantity1 = new QuantityBuilder().addItemId(itemIdToFind).build();
		Quantity quantity2 = new QuantityBuilder().addItemId(itemIdToFind+1).build();
		entityManager.persist(quantity1);
		entityManager.persist(quantity2);
		entityManager.flush();
		
		// when
		Quantity foundQuantity = quantityRepository.findByItemid(itemIdToFind);
		
		// then
		assertThat(foundQuantity).isEqualToComparingFieldByField(quantity1).isNotEqualTo(quantity2);
	
	}
	
	@Test
	public void whenFindByItemIdIn_theReturnQuantites() {
		
		List<Integer> itemIdsToFind = new ArrayList<Integer>();
		itemIdsToFind.add(1);
		itemIdsToFind.add(2);
		
		// given
		Quantity quantity1 = new QuantityBuilder().addItemId(itemIdsToFind.get(0)).build();
		Quantity quantity2 = new QuantityBuilder().addItemId(itemIdsToFind.get(1)).build();
		Quantity quantity3 = new QuantityBuilder().addItemId(itemIdsToFind.get(1)+1).build();
		entityManager.persist(quantity1);
		entityManager.persist(quantity2);
		entityManager.persist(quantity3);
		entityManager.flush();
		
		// when
		List<Quantity> foundQuantitiesList = quantityRepository.findByItemidIn(itemIdsToFind);
		
		// then
		assertThat(foundQuantitiesList).contains(quantity1, quantity2).doesNotContain(quantity3);
		
	}

}
