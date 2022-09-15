package com.kh.myapp3.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Member {
  private Long memberId;          //회원아이디(내부관리용) MEMBER_ID	NUMBER(8,0)	No		1
  private String email;            //이메일 EMAIL	VARCHAR2(40 BYTE)	Yes		2
  private String pw;              //비밀번호 PW	VARCHAR2(10 BYTE)	Yes		3
  private String nickname;        //별칭  NICKNAME	VARCHAR2(30 BYTE)	Yes		4
  private LocalDateTime cdate;    //생성일
  private LocalDateTime udate;    //수정일

  public Member(String email, String pw, String nickname) {
    this.email = email;
    this.pw = pw;
    this.nickname = nickname;
  }
}
