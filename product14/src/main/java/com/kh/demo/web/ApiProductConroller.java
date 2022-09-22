package com.kh.demo.web;

import com.kh.demo.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.product.AddReq;
import com.kh.demo.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductConroller {
  private final ProductSVC productSVC;

  //등록 POST
  @PostMapping(value = "/products", produces = "application/json")
  public ApiResponse<Object> add(@Valid @RequestBody AddReq addReq, BindingResult bindingResult) {
    log.info("reqMsg={}", addReq);
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }

    //AddReq를 product로 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq, product);

    //상품등록
    Long pid = productSVC.save(product);

    return ApiResponse.createApiResMsg("00", "성공", pid);
  }

  //검증 오류 메세지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
//      errmsg.put(objectError.getCode()[0], objectError.getDefaultMessage());
    });
    return errmsg;
  }

  //  조회	GET	/api/products/{id}
  @GetMapping("/products/{pid}")
  public ApiResponse<Product> findById(@PathVariable("pid") Long pid) {
    //상품 조회
    Optional<Product> findedProduct = productSVC.findByPid(pid);
    //응답 메시지
    ApiResponse<Product> response = null;
    if (findedProduct.isPresent()) {
      response = ApiResponse.createApiResMsg("00", "성공", findedProduct.get());
    } else {
      response = ApiResponse.createApiResMsg("99", "찾는 상품이 없음", null);
    }
    return response;
  }

  //수정
  @PatchMapping("/products/{pid}")
  public ApiResponse<Object> edit(@PathVariable("pid") Long pid, @Valid @RequestBody EditReq editReq, BindingResult bindingResult){
    //검증_어노테이션 기본 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }
    //검증
    Optional<Product> findedProduct = productSVC.findByPid(pid);
    if (findedProduct.isEmpty()) {
      return ApiResponse.createApiResMsg("99", "찾는 정보가 없음", null);
    }

    //EditReq = > Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);
    //수정
    productSVC.update(pid, product);
    //응답메시지
    return ApiResponse.createApiResMsg("00", "성공", productSVC.findByPid(pid).get());
  }

  //  삭제	DELETE	/api/products/{id}
  @DeleteMapping("/products/{pid}")
  public ApiResponse<Product> del(@PathVariable("pid") Long pid) {
    //검증
    Optional<Product> findedProduct = productSVC.findByPid(pid);
    if (findedProduct.isEmpty()) {
      return ApiResponse.createApiResMsg("99", "삭제하고자 하는 상품이 없음", null);
    }
    //삭제
    productSVC.deleteByProductId(pid);

    //응답 메시지
    return ApiResponse.createApiResMsg("00", "성공", null);
  }
  //목록 GET  /api/products
  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll() {
    List<Product> list = productSVC.findAll();

    return ApiResponse.createApiResMsg("00", "성공", list);
  }
}
