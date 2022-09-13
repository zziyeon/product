package com.kh.product.domain.svc;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductSVC {
  //상품id 생성
  Long generatedPid();
  //상품등록
  Product save(Product product);
  //상품조회
  Product findById(Long pid);
  //상품수정
  int update(Long pid, Product product);
  //상품삭제
  int delete(Long pid);
  //상품목록
  List<Product> findAll();
}
