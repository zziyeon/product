package com.kh.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOImplTest {
  @Autowired
  private  ProductDAO productDAO;
  private static Long testProduct;
  @Test
  @Order(1)
  @DisplayName("상품등록")
  void save() {
//    Product newProduct = new Product("돼지바");
//    testProduct = productDAO.save(newProduct);
  }
}