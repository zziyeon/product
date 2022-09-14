package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDAOImplTest {
  @Autowired
  private  ProductDAO productDAO;
  private static Product testProduct;
  
  @Test
  @Order(1)
  @DisplayName("상품등록")
  void save() {
    Product newProduct = new Product(null, "돼지바", 8, 8800l);
    testProduct = productDAO.save(newProduct);
    Assertions.assertThat(testProduct.getPname()).isEqualTo(newProduct.getPname());
    Assertions.assertThat(testProduct.getCount()).isEqualTo(newProduct.getCount());
    Assertions.assertThat(testProduct.getPrice()).isEqualTo(newProduct.getPrice());
  }

  @Test
  @Order(2)
  @DisplayName("상품조회")
  void findById() {
    log.info("findById() called");
    Product findedProduct = productDAO.findById(testProduct.getPid());
    Assertions.assertThat(findedProduct).isEqualTo(testProduct);
    Assertions.assertThat(testProduct).isNotNull();
  }

  @Test
  @Order(3)
  @DisplayName("상품수정")
  void update() {
    log.info("update() called");
    Long pid= testProduct.getPid();
    Product product = new Product(pid, "치즈", 7, 700000l);
    productDAO.update(pid, product);

    Product findedProduct = productDAO.findById(pid);
    Assertions.assertThat(findedProduct).isEqualTo(product);
  }

  @Test
  void delete() {
  }

  @Test
  void findAll() {
  }
}