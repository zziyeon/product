package com.kh.demo.web;

import com.kh.demo.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.product.AddReq;
import com.kh.demo.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j    //log.infl()
//@Controller
//@ResponseBody
// @RestController = @Controller + @ResponseBody
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductControllerV1 {

  //  @Autowired   //필드 주입 (테스트할 때 사용하지 잘 안씀)
  private final ProductSVC productSVC;

  //생성자 주입
//  @Autowired
//  public ApiProductController(ProductSVC productSVC) {    //@RequiredArgsConstructor 사용하면 해당 메소드 자동 생성됨.
//    this.productSVC = productSVC;
//  }

//  // 세터메소드 주입 (사용 잘 안함)
//  @Autowired
//  public void setInstance(ProductSVC productSVC) {
//    this.productSVC = productSVC;
//  }

  //  등록	POST	/api/products
  //ResponseBody: view를 찾지 않고 응답메시지에 return에 있는 메시지를 직접 반환하겠다.
  @ResponseBody
  //client 요청과는 상관 없이 sercer에서 json으로 고정
  @PostMapping(value = "/products", produces = "application/json")
  public ApiResponse<Long> add(@RequestBody AddReq addReq) {
    log.info("reqMsg={}", addReq);

//    List<Person> persons = new ArrayList<>();
//    Person p1 = new Person("홍길동", 30);
//    Person p2 = new Person("홍길순", 20);
//    persons.add(p1);
//    persons.add(p2);

////    Set<Person> persons = new HashSet<>();
////    Map<String, Person> persons = new HashMap();
//
////    persons.add(p1);
////    persons.put("1", p1);
//
////    persons.add(p2);
////    persons.put("2", p2);
//
//    return persons;
//    return "<html><body><b>HI</b></body></html>";    //원래 템플릿 뷰 위치가 들어감.

    //검증

    //AddReq를 product로 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq, product);

    //상품등록
    Long id = productSVC.save(product);
    //응답메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    ApiResponse<Long> response = new ApiResponse<>(header, id);
    return response;
  }

//  @Data
//  @AllArgsConstructor
//  static class Person {
//    private String name;
//    private int age;
//  }

  //  조회	GET	/api/products/{id}
  @ResponseBody
  @GetMapping("/products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long id) {
    //상품 조회
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    // 응답 메시지
    ApiResponse<Product> response = null;
    if (findedProduct.isPresent()) {
      Product product = findedProduct.get();

      ApiResponse.Header header = new ApiResponse.Header("00", "성공");
      response = new ApiResponse<>(header, product);
    } else {
      ApiResponse.Header header = new ApiResponse.Header("99", "찾는 정보가 없음");
      response = new ApiResponse<>(header, null);
    }
    return response;
  }
//  수정	PATCH	/api/products/{id}
  @ResponseBody
  @PatchMapping("/products/{id}")
  public ApiResponse<Product> edit(@PathVariable("id") Long id, @RequestBody EditReq editReq) {

    //검증
    ApiResponse<Product> response = null;
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    if (findedProduct.isEmpty()) {
      ApiResponse.Header header = new ApiResponse.Header("99", "수정할 상품이 없습니다.");
      response = new ApiResponse<>(header,null);
      return response;
    }

    //EditReq = > Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);
    //수정
    productSVC.update(id, product);
    //응답메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    response = new ApiResponse<>(header, productSVC.findByProductId(id).get());

    return response;
  }

//  삭제	DELETE	/api/products/{id}
  @ResponseBody
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> del(@PathVariable("id") Long id) {
    //검증
    ApiResponse<Product> response = null;
    Optional<Product> findedProduct = productSVC.findByProductId(id);
    if (findedProduct.isEmpty()) {
      ApiResponse.Header header = new ApiResponse.Header("99", "삭제할 상품이 없습니다.");
      response = new ApiResponse<>(header,null);
      return response;
    }
    //삭제
    productSVC.deleteByProductId(id);

    //응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    response = new ApiResponse<>(header, null);

    return response;
  }

//  목록	GET	/api/products
  @ResponseBody
  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll() {
    List<Product> list = productSVC.findAll();

    //api응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    ApiResponse<List<Product>> response = new ApiResponse<>(header, list);

    return response;
  }
}
