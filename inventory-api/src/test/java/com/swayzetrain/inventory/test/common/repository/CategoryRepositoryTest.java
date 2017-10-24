package com.swayzetrain.inventory.test.common.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.swayzetrain.inventory.api.Init;
import com.swayzetrain.inventory.common.model.Category;
import com.swayzetrain.inventory.common.repository.CategoryRepository;
import com.swayzetrain.inventory.common.service.CommonService;
import com.swayzetrain.inventory.test.common.builder.CategoryBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Init.class)
@DataJpaTest
public class CategoryRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Test
	public void whenFindByIdAndInstance_thenReturnCategory() {
	    // given
	    Category fruit = new CategoryBuilder().addCategoryName("Fruit").addInstanceId(1).addCreationUserId(1).addDateCreated(commonService.setTimestamp()).addDateModified(commonService.setTimestamp()).build();
	    entityManager.persist(fruit);
	    entityManager.flush();
	 
	    // when
	    Category found = categoryRepository.findByCategoryidAndInstanceid(fruit.getCategoryid(), fruit.getInstanceid());
	 
	    // then
	    assertThat(fruit).isEqualToComparingFieldByFieldRecursively(found);
	}

}
