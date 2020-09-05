package com.rest.demo;


import com.rest.demo.RestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class DemoApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

   @Autowired
   private RestRepository restRepository;

   @Test
   public void whenFindAll() {
       //given
       Product product1 = new Product();
       product1.setName("PEN");
       product1.setPrice(20.0);
       entityManager.persist(product1);
       entityManager.flush();

       Product product2 = new Product();
       product2.setName("NOTEBOOK");
       product2.setPrice(40.0);
       entityManager.persist(product2);
       entityManager.flush();


       //when
       List<Product> products = (List<Product>) restRepository.findAll();

       //then
       assertThat(products.size()).isEqualTo(5);
       assertThat(products.get(4)).isEqualTo(product1);
       assertThat(products.get(5)).isEqualTo(product2);
   }

   @Test
   public void whenFindAllById() {
       //given
	   Product testProduct=null;
       Product product3 = new Product();
       product3.setName("earphone");
       entityManager.persist(product3);
       entityManager.flush();

       testProduct = restRepository.findAllById(product3.getId());
     //then
       assertThat(testProduct.getName()).isEqualTo(product3.getName());
   }
}
