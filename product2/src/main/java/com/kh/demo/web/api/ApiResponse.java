package com.kh.demo.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private Header header;
    private T data;
    @Data
    @AllArgsConstructor
    public static class Header {
      private String rtcd;    //응답 코드 "00", "99"
      private String rtmsg;   //응답 메시지 "성공, "실패"
    }
  }
