package com.kh.app3.web.form.bbs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ListForm {
  private Long bbsId;           //  게시글번호
  private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private LocalDateTime cdate;  //  작성일
  private int hit;              //  조회수
  private int bindent;          //  들여쓰기
}
