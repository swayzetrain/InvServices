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

import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;
import com.swayzetrain.inventory.test.common.TestConfiguration;
import com.swayzetrain.inventory.test.common.builder.CategoryBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class CategoryRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void whenFindByInstance_thenReturnCategoryList() {
		
		// given
		Integer instanceIdToFind = 1;
		
		Category fruit = new CategoryBuilder().addCategoryName("Fruit").addInstanceId(instanceIdToFind).addCreationUserId(1).build();
		Category vegetable = new CategoryBuilder().addCategoryName("Vegetable").addInstanceId(instanceIdToFind).addCreationUserId(2).build();
		Category meat = new CategoryBuilder().addCategoryName("Meat").addInstanceId(instanceIdToFind+1).addCreationUserId(1).build();
		
		entityManager.persist(fruit);
		entityManager.persist(vegetable);
		entityManager.persist(meat);
		entityManager.flush();
		
		// when
		List<Category> foundList = categoryRepository.findByInstanceid(instanceIdToFind);
		
		//then
		assertThat(foundList).contains(fruit, vegetable).doesNotContain(meat);
		
	}
	
	@Test
	public void whenFindByIdAndInstance_thenReturnCategory() {
		
	    // given
	    Category fruit = new CategoryBuilder().addCategoryName("Fruit").addInstanceId(1).addCreationUserId(1).build();
	    Category meat = new CategoryBuilder().addCategoryName("Meat").addInstanceId(1).addCreationUserId(1).build();
	    entityManager.persist(fruit);
	    entityManager.persist(meat);
	    entityManager.flush();
	 
	    // when
	    Category found = categoryRepository.findByCategoryidAndInstanceid(fruit.getCategoryid(), fruit.getInstanceid());
	 
	    // then
	    assertThat(found).isEqualToComparingFieldByFieldRecursively(fruit).isNotEqualTo(meat);
	
	}
	
	@Test
	public void whenFindByCategoryidAndCategorynameAndInstanceid_thenReturnCategory() {
		
		// given
		Category cereal = new CategoryBuilder().addCategoryName("Cereal").addInstanceId(1).addCreationUserId(1).build();
		Category dairy = new CategoryBuilder().addCategoryName("Dairy").addInstanceId(1).addCreationUserId(1).build();
		entityManager.persist(cereal);
	    entityManager.persist(dairy);
	    entityManager.flush();
		
		// when
		Category found = categoryRepository.findByCategoryidAndCategorynameAndInstanceid(cereal.getCategoryid(), cereal.getCategoryname(), cereal.getInstanceid());
		
		// then
		assertThat(found).isEqualToComparingFieldByFieldRecursively(cereal).isNotEqualTo(dairy);
		
	}
	
	@Test
	public void whenFindByCategoryNameAndInstanceId_thenReturnCategoryList() {
		
		// given
		String categoryNameToFind = "Fruit";
		Integer instanceIdToFind = 1;
				
		Category fruit1 = new CategoryBuilder().addCategoryName(categoryNameToFind).addInstanceId(instanceIdToFind).addCreationUserId(1).build();
		Category fruit2 = new CategoryBuilder().addCategoryName(categoryNameToFind).addInstanceId(instanceIdToFind).addCreationUserId(2).build();
		Category fruit3 = new CategoryBuilder().addCategoryName(categoryNameToFind).addInstanceId(instanceIdToFind+1).addCreationUserId(1).build();
		Category vegetable1 = new CategoryBuilder().addCategoryName("Vegetable").addInstanceId(instanceIdToFind).addCreationUserId(1).build();
		Category vegetable2 = new CategoryBuilder().addCategoryName("Vegetable").addInstanceId(instanceIdToFind).addCreationUserId(2).build();
		
		entityManager.persist(fruit1);
		entityManager.persist(fruit2);
		entityManager.persist(fruit3);
		entityManager.persist(vegetable1);
		entityManager.persist(vegetable2);
		entityManager.flush();
				
		// when
		List<Category> foundList = categoryRepository.findByCategorynameAndInstanceid(categoryNameToFind, instanceIdToFind);
				
		//then
		assertThat(foundList).contains(fruit1, fruit2).doesNotContain(fruit3, vegetable1, vegetable2);
		
	}
	
	@Test
	public void whenDeleteByCategoryIdAndInstanceId_thenReturnNull() {
		
		// given
		Category fruit1 = new CategoryBuilder().addCategoryName("Fruit").addInstanceId(1).addCreationUserId(1).build();

		entityManager.persist(fruit1);
		entityManager.flush();
		
		// when
		entityManager.remove(fruit1);
		
		Category foundCategory = categoryRepository.findByCategoryidAndInstanceid(fruit1.getCategoryid(), fruit1.getInstanceid());
		
		//then
		assertThat(foundCategory).isEqualTo(null);
		
	}
	
	@Test
	public void whenDeleteByCategoryNameAndInstanceId_thenReturnNull() {
		
		Category fruit1 = new CategoryBuilder().addCategoryName("Fruit").addInstanceId(1).addCreationUserId(1).build();
		
		entityManager.persist(fruit1);
		entityManager.flush();
		
		// when
		entityManager.remove(fruit1);
		
		List<Category> foundCategoryList = categoryRepository.findByCategorynameAndInstanceid(fruit1.getCategoryname(), fruit1.getInstanceid());
		
		//then
		assertThat(foundCategoryList).isEmpty();
		
	}

}
