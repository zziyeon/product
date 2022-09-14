package com.kh.product.domain.svc;

import com.kh.product.domain.Product;
import com.kh.product.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {
  private final ProductDAO productDAO;

//  //상품id생성
//  @Override
//  public String generatedPid() {
//    return productDAO.generatedPid();
//  }

  //상품 등록
  @Override
  public Product save(Product product) {
    String generatePid = productDAO.generatedPid();
    product.setPid((generatePid));
    productDAO.save(product);

    return productDAO.findById(generatePid);
  }

  @Override
  public Product findById(String pid) {
    return productDAO.findById(pid);
  }

  @Override
  public int update(String pid, Product product) {
    return productDAO.update(pid, product);
  }

  @Override
  public int delete(String pid) {
    return productDAO.delete(pid);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
}
