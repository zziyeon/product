package com.kh.demo.web.api.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class EditReq {
  @NotBlank
  private String pname;     //  PNAME	VARCHAR2(60 BYTE)
  @NotNull
  @Positive(message = "수량은 양수값")
  private Long count;    //  COUNT	NUMBER(10,0)
  @NotNull
  @Positive(message = "단가는 양수값")
  private Long price;    //  PRICE	NUMBER(10,0)
}
