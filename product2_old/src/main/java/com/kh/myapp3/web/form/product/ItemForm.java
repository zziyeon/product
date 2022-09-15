package com.kh.myapp3.web.form.product;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long productId;       //상품아이디
  private String pname;         //상품명 PNAME	VARCHAR2(30 BYTE)	Yes		2
  private Integer quantity;     //수량  QUANTITY	NUMBER(10,0)	Yes		3
  private Integer price;        //가격  PRICE	NUMBER(10,0)	Yes		4
}
