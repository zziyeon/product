package com.kh.demo.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UploadFileSVC {
  /**
   * 업로드 파일 등록 - 단건
   * @param multiparFile  첨부파일
   * @param code    분류코드
   * @param rid 참조번호
   * @return
   */
  Long addFile(MultipartFile multiparFile, AttachCode code, Long rid);

  /**
   * 업로드 파일 등록 - 여러건
   * @param multiparFiles    첨부파일
   * @param code    분류코드
   * @param rid``참조번호
   */
  void addFile(List<MultipartFile> multiparFiles, AttachCode code, Long rid);

  /**
   * 업로드파일조회 단건
   * @param code
   * @param rid
   * @return
   */
  List<UploadFile> getFilesByCodeWithRid(String code,Long rid);

  /**
   * 업로드파일조회 단건
   * @param uploadfileId
   * @return
   */
  Optional<UploadFile> findFileByUploadFileId(Long uploadfileId);


  /**
   * 첨부파일 삭제 by uplaodfileId
   * @param uploadfileId 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByUploadFildId(Long uploadfileId);

  /**
   * 첨부파일 삭제 By code, rid
   * @param code 첨부파일 분류코드
   * @param rid 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByCodeWithRid(String code, Long rid);
}
