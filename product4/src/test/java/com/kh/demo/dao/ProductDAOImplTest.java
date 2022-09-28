package com.kh.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ProductDAOImplTest {
  @Autowired
  private ProductDAO productDAO;

  @Test
  @DisplayName("회원 가입")
  void save() {
    Product product = new Product();
    product.setPname("세발 자전거");
    product.setQuantity(6l);
    product.setPrice(7200l);

    Long savedProduct = productDAO.save(product);

    log.info("savedProduct={}", savedProduct);
    Assertions.assertThat(savedProduct).isEqualTo(372);
  }

  @Test
  @DisplayName("회원 목록")
  void findAll() {
    List<Product> productList = productDAO.findAll();
    log.info("상품수={}", productList.size());

    productList.stream().forEach(product->log.info(product.toString()));
  }

  @Test
  @DisplayName("회원 조회")
  void findByProductId() {
    Optional<Product> byProductId = productDAO.findByProductId(372l);
    log.info("byProductId={}", byProductId.get());
    Assertions.assertThat(byProductId.get().getPrice()).isEqualTo(7200l);
    Assertions.assertThat(byProductId.get().getQuantity()).isEqualTo(6l);
  }

  @Test
  @DisplayName("회원 수정")
  void update() {
    Long productId = 372l;
    Product product = new Product();
    product.setPrice(6100l);
    product.setQuantity(1l);
    product.setPname("두발 자전거");

    productDAO.update(productId, product);

    Optional<Product> byProductId = productDAO.findByProductId(productId);
    log.info("byProductId={}", byProductId);
    Assertions.assertThat(byProductId.get().getPname()).isEqualTo("두발 자전거");
    Assertions.assertThat(byProductId.get().getPrice()).isEqualTo(6100l);
    Assertions.assertThat(byProductId.get().getQuantity()).isEqualTo(1l);
  }

  @Test
  @DisplayName("회원 삭제")
  void deleteByProductId() {
    Long productId=372l;
    productDAO.deleteByProductId(productId);

    Optional<Product> deletebyProductId = productDAO.findByProductId(productId);
    log.info("deletebyProductId={}", deletebyProductId);
    Assertions.assertThat(deletebyProductId).isEmpty();
  }
}