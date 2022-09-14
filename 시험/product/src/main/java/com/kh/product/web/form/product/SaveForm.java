package com.kh.product.web.form.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SaveForm {
  @NotBlank
  private String pname;   //상품명 PNAME	VARCHAR2(30 BYTE)
  private Integer count;  //수량 COUNT	NUMBER(10,0)
  private Long price;  //가격 PRICE	NUMBER(10,0)
}
