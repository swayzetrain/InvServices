package com.swayzetrain.inventory.test.common.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.repository.ItemRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.ItemBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class ItemRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	public void whenFindByInstanceId_thenReturnItems() {
		
		Integer instanceIdToFind = 1;
		
		// given		
		Item item1 = new ItemBuilder().addItemName("Item1").addCategoryId(1).addInstanceId(instanceIdToFind).build();
		Item item2 = new ItemBuilder().addItemName("Item2").addCategoryId(1).addInstanceId(instanceIdToFind+1).build();
		entityManager.persist(item1);
		
		// when
		List<Item> foundItemList = itemRepository.findByInstanceid(instanceIdToFind);
		
		// then
		assertThat(foundItemList).contains(item1).doesNotContain(item2);
		
	}
	
	@Test
	public void whenFindByItemIdAndInstanceId_thenReturnItem() {
		
		Integer instanceIdToFind = 1;
		
		// given
		Item item1 = new ItemBuilder().addItemName("Item1").addCategoryId(1).addInstanceId(instanceIdToFind).build();
		entityManager.persistAndFlush(item1);
		
		// when
		Item foundItem = itemRepository.findByItemidAndInstanceid(item1.getItemid(), item1.getInstanceid());
		
		// then
		assertThat(foundItem).isEqualToComparingFieldByField(item1);
		
	}
	
	@Test
	public void whenFindByItemNameAndCategoryIdAndInstanceId_thenReturnItems() {
		
		Integer instanceIdToFind = 1;
		String itemNameToFind = "item";
		Integer categoryIdToFind = 1;
		
		// given		
		Item item1 = new ItemBuilder().addItemName(itemNameToFind).addCategoryId(categoryIdToFind).addInstanceId(instanceIdToFind).build();
		Item item2 = new ItemBuilder().addItemName(itemNameToFind).addCategoryId(categoryIdToFind+1).addInstanceId(instanceIdToFind).build();
		Item item3 = new ItemBuilder().addItemName(itemNameToFind).addCategoryId(categoryIdToFind).addInstanceId(instanceIdToFind+1).build();
		Item item4 = new ItemBuilder().addItemName("item 4").addCategoryId(categoryIdToFind).addInstanceId(instanceIdToFind).build();
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.persist(item3);
		entityManager.persist(item4);
		entityManager.flush();
		
		// when
		List<Item> foundItemList = itemRepository.findByItemnameAndCategoryidAndInstanceid(itemNameToFind, categoryIdToFind, instanceIdToFind);
		
		// then
		assertThat(foundItemList).contains(item1).doesNotContain(item2, item3, item4);
		
	}
	
	@Test
	public void whenFindByItemNameAndInstanceId_thenReturnItems() {
		
		Integer instanceIdToFind = 1;
		String itemNameToFind = "item";
		
		// given	
		Item item1 = new ItemBuilder().addItemName(itemNameToFind).addCategoryId(1).addInstanceId(instanceIdToFind).build();
		Item item2 = new ItemBuilder().addItemName(itemNameToFind).addCategoryId(1).addInstanceId(instanceIdToFind+1).build();
		Item item3 = new ItemBuilder().addItemName("Item 3").addCategoryId(1).addInstanceId(instanceIdToFind).build();
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.persist(item3);
		entityManager.flush();
		
		// when
		List<Item> foundItemList = itemRepository.findByItemnameAndInstanceid(itemNameToFind, instanceIdToFind);
		
		// then
		assertThat(foundItemList).contains(item1).doesNotContain(item2, item3);
		
	}
	
	@Test
	public void whenFindByCategoryIdAndInstanceId_thenReturnItems() {
		
		Integer instanceIdToFind = 1;
		Integer categoryIdToFind = 1;
		
		// given				
		Item item1 = new ItemBuilder().addItemName("item 1").addCategoryId(categoryIdToFind).addInstanceId(instanceIdToFind).build();
		Item item2 = new ItemBuilder().addItemName("item 2").addCategoryId(categoryIdToFind).addInstanceId(instanceIdToFind+1).build();
		Item item3 = new ItemBuilder().addItemName("Item 3").addCategoryId(categoryIdToFind+1).addInstanceId(instanceIdToFind).build();
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.persist(item3);
		entityManager.flush();
		
		// when
		List<Item> foundItemList = itemRepository.findByCategoryidAndInstanceid(categoryIdToFind, instanceIdToFind);
				
		// then
		assertThat(foundItemList).contains(item1).doesNotContain(item2, item3);
	
	}
	
	@Test
	public void whenFindByItemIdAndItemNameAndInstanceId_thenReturnItem() {
		
		Integer instanceIidtoFind = 1;
		
		// given		
		Item item1 = new ItemBuilder().addInstanceId(instanceIidtoFind).build();
		Item item2 = new ItemBuilder().addInstanceId(instanceIidtoFind+1).build();
		Item item3 = new ItemBuilder().addItemName("Item 3").addInstanceId(instanceIidtoFind).build();
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.persist(item3);
		entityManager.flush();
		
		// when
		Item foundItem = itemRepository.findByItemidAndItemnameAndInstanceid(item1.getItemid(), item1.getItemname(), instanceIidtoFind);
		
		// then
		assertThat(foundItem).isEqualToComparingFieldByField(item1).isNotEqualTo(item2).isNotEqualTo(item3);
		
	}
	
	@Test
	public void whenFindByItemIdAndItemNameAndCategoryIdAndInstanceId_thenReturnItem() {
		
		// given
		Item item1 = new ItemBuilder().build();
		entityManager.persistAndFlush(item1);
		
		// when
		Item foundItem = itemRepository.findByItemidAndItemnameAndCategoryidAndInstanceid(item1.getItemid(), item1.getItemname(), item1.getCategoryid(), item1.getInstanceid());
		
		// then
		assertThat(foundItem).isEqualToComparingFieldByFieldRecursively(item1);
		
	}
	
	@Test
	public void whenFindByItemIdAndCategoryIdAndInstanceId_thenReturnItem() {
		
		// given
		Item item1 = new ItemBuilder().build();
		entityManager.persistAndFlush(item1);
		
		// when
		Item foundItem = itemRepository.findByItemidAndCategoryidAndInstanceid(item1.getItemid(), item1.getCategoryid(), item1.getInstanceid());
		
		// then
		assertThat(foundItem).isEqualToComparingFieldByFieldRecursively(item1);
		
	}
	
	@Test
	public void whenDeleteByItemIdAndInstanceId_thenReturnNull() {
		
		// given		
		Item item1 = new ItemBuilder().addItemName("item 1").addCategoryId(1).addInstanceId(1).build();
		entityManager.persistAndFlush(item1);
		
		// when
		entityManager.remove(item1);
		
		Item foundItem = itemRepository.findByItemidAndInstanceid(item1.getItemid(), item1.getInstanceid());
		
		// then
		assertThat(foundItem).isEqualTo(null);
		
	}
	
	@Test
	public void whenDeleteByItemNameAndCategoryIdAndInstanceId_thenReturnNull() {
		
		// given	
		Item item1 = new ItemBuilder().addItemName("item 1").addCategoryId(1).addInstanceId(1).build();
		entityManager.persistAndFlush(item1);
		
		// when
		entityManager.remove(item1);
		
		List<Item> foundItemList = itemRepository.findByItemnameAndCategoryidAndInstanceid(item1.getItemname(), item1.getCategoryid(), item1.getInstanceid());
		
		// then
		assertThat(foundItemList).isEmpty();
	
	}
	
	@Test
	public void whenDeleteByItemNameAndInstanceId_thenReturnNull() {
		
		// given		
		Item item1 = new ItemBuilder().addItemName("item 1").addCategoryId(1).addInstanceId(1).build();
		entityManager.persistAndFlush(item1);
		
		// when
		entityManager.remove(item1);
		
		List<Item> foundItemList = itemRepository.findByItemnameAndInstanceid(item1.getItemname(), item1.getInstanceid());
		
		// then
		assertThat(foundItemList).isEmpty();
		
	}
	
	@Test
	public void whenDeleteByCategoryIdAndInstanceId_thenReturnNull() {
		
		// given
		Item item1 = new ItemBuilder().addItemName("item 1").addCategoryId(1).addInstanceId(1).build();
		entityManager.persistAndFlush(item1);
				
		// when
		entityManager.remove(item1);
				
		List<Item> foundItemList = itemRepository.findByCategoryidAndInstanceid(item1.getCategoryid(), item1.getInstanceid());
				
		// then
		assertThat(foundItemList).isEmpty();
		
	}

}
