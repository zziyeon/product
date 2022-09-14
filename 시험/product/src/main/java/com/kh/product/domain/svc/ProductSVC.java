package com.kh.product.domain.svc;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductSVC {
//  //상품id 생성
//  String generatedPid();
  //상품등록
  Product save(Product product);
  //상품조회
  Product findById(String pid);
  //상품수정
  int update(String pid, Product product);
  //상품삭제
  int delete(String pid);
  //상품목록
  List<Product> findAll();
}
