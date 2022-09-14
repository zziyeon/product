package com.kh.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long pid;       //상품아이디 PID	NUMBER(10,0)
  private String pname;   //상품명 PNAME	VARCHAR2(30 BYTE)
  private Integer count;  //수량 COUNT	NUMBER(10,0)
  private Long price;  //가격 PRICE	NUMBER(10,0)
}
