package com.kh.myapp3.domain.admin;

import com.kh.myapp3.domain.dao.admin.AdminMemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AdminMemberDAOImplTest {

  @Autowired
  private AdminMemberDAO adminMemberDAO;

  @Test
  @DisplayName("이메일중복체크")
  void dupChkOfMemberEmail() {
    Boolean isExist = adminMemberDAO.dupChkOfMemberEmail("test4@test.com");
    Assertions.assertThat(isExist).isTrue();

    isExist = adminMemberDAO.dupChkOfMemberEmail("test4@test.com1");
    Assertions.assertThat(isExist).isFalse();
  }
}