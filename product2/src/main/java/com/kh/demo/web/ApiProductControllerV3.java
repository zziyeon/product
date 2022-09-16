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
public class ApiProductControllerV3 {
  private final ProductSVC productSVC;
  //  등록	POST	/api/products
  //  @ResponseBody
  //client 요청과는 상관 없이 sercer에서 json으로 고정
  @PostMapping(value = "/products", produces = "application/json")
  public ApiResponse<Object> add(@Valid @RequestBody AddReq addReq, BindingResult bindingResult) {
    log.info("reqMsg={}", addReq);
    //검증_어노테이션 기본 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return ApiResponse.createApiREsMsg("99", "실패", getErrMsg(bindingResult));
    }

    //비즈니스 규칙
    //필드 검증
    if (addReq.getQuantity() > 100) {
      bindingResult.rejectValue("quantity", null,"상품 수량은 100개를 초과할 수 없음");
    }
    //오브젝트 검증
    if (addReq.getQuantity() * addReq.getPrice() > 10_000_000) {
      bindingResult.reject(null, "총액(상품수량*단가)이 1000만원을 초과할 수 없음");
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return ApiResponse.createApiREsMsg("99", "실패", getErrMsg(bindingResult));
    }

    //AddReq를 product로 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq, product );

    //상품등록
    Long id = productSVC.save(product);
    //응답메시지
    return ApiResponse.createApiREsMsg("00", "성공", id);
  }

  //검증 오류 메세지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
      errmsg.put(objectError.getCodes()[0],objectError.getDefaultMessage());

    });
    return errmsg;
  }

  //  조회	GET	/api/products/{id}
  @GetMapping("/products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long id) {
    //상품 조회
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    //응답 메시지
    ApiResponse<Product> response = null;
    if (findedProduct.isPresent()) {
      response = ApiResponse.createApiREsMsg("00", "성공", findedProduct.get());
    } else {
      response = ApiResponse.createApiREsMsg("99", "찾는 상품이 없음", null);
    }
    return response;
  }
//  수정	PATCH	/api/products/{id}
  @PatchMapping("/products/{id}")
  public ApiResponse<Object> edit(@PathVariable("id") Long id, @Valid @RequestBody EditReq editReq, BindingResult bindingResult) {

    //검증_어노테이션 기본 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return ApiResponse.createApiREsMsg("99", "실패", getErrMsg(bindingResult));
    }

    //비즈니스 규칙
    //필드 검증
    if (editReq.getQuantity() > 100) {
      bindingResult.rejectValue("quantity", null,"상품 수량은 100개를 초과할 수 없음");
    }
    //오브젝트 검증
    if (editReq.getQuantity() * editReq.getPrice() > 10_000_000) {
      bindingResult.reject(null, "총액(상품수량*단가)이 1000만원을 초과할 수 없음");
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return ApiResponse.createApiREsMsg("99", "실패", getErrMsg(bindingResult));
    }
    //검증
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    if (findedProduct.isEmpty()) {
      return ApiResponse.createApiREsMsg("99", "찾는 정보가 없음", null);
    }

    //EditReq = > Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);
    //수정
    productSVC.update(id, product);
    //응답메시지
    return ApiResponse.createApiREsMsg("00", "성공", productSVC.findByProductId(id).get());
  }

//  삭제	DELETE	/api/products/{id}
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> del(@PathVariable("id") Long id) {
    //검증
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    if (findedProduct.isEmpty()) {
      return ApiResponse.createApiREsMsg("99", "삭제하고자 하는 상품이 없음", null);
    }
    //삭제
    productSVC.deleteByProductId(id);

    //응답 메시지
    return ApiResponse.createApiREsMsg("00", "성공", null);
  }

//  목록	GET	/api/products
  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll() {
    List<Product> list = productSVC.findAll();
    
    //api응답 메시지
    return ApiResponse.createApiREsMsg("00", "성공", list);
  }
}
