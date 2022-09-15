package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  @Test
  @DisplayName("상품등록")
  void save(){
    Product product = new Product();
    product.setPname("외장하드");
    product.setQuantity(1);
    product.setPrice(50000);

    Product savedProduct = productDAO.save(product);
    log.info("savedProduct={}",savedProduct);
  }

  @Test
  @DisplayName("조회")
  void findById(){
    Long productId = 63l;
    Product findedProduct = productDAO.findById(productId);

    Assertions.assertThat(findedProduct.getPname())
              .isEqualTo("외장하드");
    Assertions.assertThat(findedProduct.getQuantity())
        .isEqualTo(1);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(50000);
  }

  @Test
  @DisplayName("수정")
  void update(){
    Long productId = 63L;

    Product product = new Product();
    product.setProductId(productId);
    product.setPname("선풍기");
    product.setQuantity(5);
    product.setPrice(50000);

    productDAO.update(productId, product);

    Product findedProduct = productDAO.findById(productId);
//    Assertions.assertThat(findedProduct.getPname())
//        .isEqualTo(product.getPname());
//    Assertions.assertThat(findedProduct.getQuantity())
//        .isEqualTo(product.getQuantity());
//    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(product.getPrice());
    Assertions.assertThat(findedProduct).isEqualTo(product);
  }

  @Test
  @DisplayName("삭제")
  void delete(){
    Long productId = 62L;
    productDAO.delete(productId);
    Product findedProduct = productDAO.findById(productId);
    Assertions.assertThat(findedProduct).isNull();
  }

  @Test
  @DisplayName("목록")
  void all(){
    List<Product> list = productDAO.findAll();
    //log.info("전체목록={}",list);
    //람다식
    //list.stream().forEach(ele->log.info("상품:{}",ele));
    //향상된for문
    for(Product p : list){
      log.info("상품:{}",p);
    }

    Assertions.assertThat(list.size()).isEqualTo(5);
  }
}
