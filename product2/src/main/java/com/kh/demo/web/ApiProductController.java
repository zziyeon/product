package com.kh.demo.web;

import com.kh.demo.dao.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j    //log.infl()
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductController {

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
  public ApiResponse<List<Person>> add(@RequestBody String reqMsg) {
    log.info("reqMsg={}", reqMsg);
    List<Person> persons = new ArrayList<>();
    Person p1 = new Person("홍길동", 30);
    Person p2 = new Person("홍길순", 20);
    persons.add(p1);
    persons.add(p2);

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
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    ApiResponse<List<Person>> response = new ApiResponse<>(header, persons);
    return response;
  }

  @Data
  @AllArgsConstructor
  static class Person {
    private String name;
    private int age;
  }

//  조회	GET	/api/products/{id}
  @GetMapping("/products/{id}")
  public String findById() {
    return "ok";
  }
//  수정	PATCH	/api/products/{id}
  @PatchMapping("/products/{id}")
  public String edit() {
    return "ok";
  }
//  삭제	DELETE	/api/products/{id}
  @DeleteMapping("/products/{id}")
  public String del() {
    return "ok";
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
