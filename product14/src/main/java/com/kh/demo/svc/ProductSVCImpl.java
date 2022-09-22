package com.kh.demo.svc;

import com.kh.demo.Product;
import com.kh.demo.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{
  private final ProductDAO productDAO;

  //등록
  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  //목록
  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  //조회
  @Override
  public Optional<Product> findByPid(Long pid) {
    return productDAO.findByPid(pid);
  }

  //수정
  @Override
  public int update(Long pid, Product product) {
    return productDAO.update(pid,product);
  }

  //삭제
  @Override
  public int deleteByProductId(Long pid) {
    return productDAO.deleteByProductId(pid);
  }
}
