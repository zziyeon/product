package com.kh.myapp3.domain.dao.admin;

import com.kh.myapp3.domain.Member;

import java.util.List;

public interface AdminMemberSVC {
  /**
   * 가입
   * @param member 가입정보
   * @return 회원아이디
   */
  Member insert(Member member);

  /**
   * 조회 by 회원아이디
   * @param memberId 회원아이디
   * @return 회원정보
   */
  Member findById(Long memberId);

  /**
   * 수정
   * @param memberId 아이디
   * @param member  수정할 정보
   * @return 수정건수
   */
  int update(Long memberId, Member member);

  /**
   * 탈퇴
   * @param memberId 아이디
   * @return 삭제건수
   */
  int del(Long memberId);

  /**
   * 목록
   * @return 회원전체
   */
  List<Member> all();

  /**
   * 이메일 중복체크
   * @param email 이메일
   * @return 존재하면 true
   */
  Boolean dupChkOfMemberEmail(String email);
}
