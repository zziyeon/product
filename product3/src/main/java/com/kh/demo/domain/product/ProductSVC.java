package com.kh.demo.domain.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  /**
   * 상품등록
   * @param product
   * @return
   */
  Long save(Product product);
  Long save(Product product, MultipartFile file);
  Long save(Product product, MultipartFile file, List<MultipartFile> files);
  Long save(Product product, List<MultipartFile> files);

  /**
   * 상품목록
   * @return
   */
  List<Product> findAll();

  /**
   * 상품조회
   * @return
   */
  Optional<Product> findByProductId(Long productId);

  /**
   * 상품변경
   * @param productId 상품아이디
   * @param product 상품
   * @return
   */
  int update(Long productId, Product product);
  int update(Long productId, Product product, MultipartFile file);
  int update(Long productId, Product product, MultipartFile file, List<MultipartFile> files);
  int update(Long productId, Product product, List<MultipartFile> files);

  /**
   * 상품삭제
   * @param productId
   * @return
   */
  int deleteByProductId(Long productId);
}
