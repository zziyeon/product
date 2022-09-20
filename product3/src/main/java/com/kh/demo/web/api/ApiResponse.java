package com.kh.demo.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Rest API 응답메세지 구조
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;    //응답 코드  "00"-성공 ,"99"-실패
    private String rtmsg;   //응답 메세지
  }

  /**
   * api응답 메세지 생성
   * @param rtcd 응답코드
   * @param rtmsg 응답메세지
   * @param data  데이터
   * @return
   * @param <T> 데이터
   */
  public static <T> ApiResponse<T> createApiResMsg(String rtcd, String rtmsg, T data){
//    ApiResponse<T> response = null;
//    Header header = new Header(rtcd, rtmsg);
//    response = new ApiResponse<>(header, data);
//    return response;
    return new ApiResponse<>(new Header(rtcd, rtmsg), data);
  }

}
