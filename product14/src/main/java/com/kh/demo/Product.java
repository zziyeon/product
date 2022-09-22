package com.kh.demo;

import lombok.Data;

@Data
public class Product {
  private Long pid; //  PRODUCT_ID	NUMBER(10,0)
  private String pname;   //  PNAME	VARCHAR2(30 BYTE)
  private Long count;  //  QUANTITY	NUMBER(10,0)
  private Long price;   //  PRICE	NUMBER(10,0)
}
