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
    private String rtcd;
    private String rtmsg;
  }

  public static <T> ApiResponse<T> createApiResMsg(String rtcd, String rtmsg, T data) {
    return new ApiResponse<>(new Header(rtcd, rtmsg), data);
  }

}
