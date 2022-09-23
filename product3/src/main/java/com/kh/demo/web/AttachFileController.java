package com.kh.demo.web;

import com.kh.demo.domain.common.file.*;
import com.kh.demo.web.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
//@Controller
//@RestController = @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachFileController {

  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  //이미지
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/img/{attachCode}/{storeFileName}")
  public Resource img(
          @PathVariable String attachCode,
          @PathVariable String storeFileName) throws MalformedURLException {
    // http://서버:포트/경로...
    // file:///d:/tmp/P0101/xxx-xxx-xxx-xxx.png
    String url = "file:///" + fileUtils.getAttachFilePath(AttachCode.valueOf(attachCode), storeFileName);
    Resource resource = new UrlResource(url);
    return resource;
  }
  //다운로드
  @GetMapping("/file/{attachCode}/{fid}")
  //반환 타입이 ResponseEntity<>인경우 응답 메시지 바디에 직접 쓰기 작업시도함 (@ResponseBody 불필요)
  public ResponseEntity<Resource> file(
          @PathVariable String attachCode,
          @PathVariable Long fid) throws MalformedURLException {
    ResponseEntity<Resource> res = null;

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(fid);
    if(uploadFile.isEmpty()) return res;

    UploadFile attachFile = uploadFile.get();
    log.info("attachFile={}", attachFile);
    String storeFileName = attachFile.getStoreFilename();
    String uploadFileName = attachFile.getUploadFilename();

    String url = "file:///" + fileUtils.getAttachFilePath(AttachCode.valueOf(attachCode), storeFileName);
    Resource resource = new UrlResource(url);

    //다운받은 파일명이 한글일때 깨질까봐 UTF_8 사용
    String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
    //클라이언트가 파일을 다운로드해주기 위해 응답 메시지 헤더에 포함
    String contentDisposition = "attachmemt; filename=\""+encode+"\"";

    res = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
            .body(resource);
    return res;
  }

  //첨부파일 삭제
  @DeleteMapping("/fid")
  public Object deleteAttachFile(@PathVariable Long fid) {
    // 메타정보 읽어오기
    //1) 스토리지 파일을 삭제하기 위해 첨부분류코드(code)와 저장파일명(storeFilename)을 가져온다.
    Optional<UploadFile> optional = uploadFileSVC.findFileByUploadFileId(fid);
    if (optional.isEmpty()) {
      return ApiResponse.createApiResMsg("01", "찾는 파일이 없습니다.", null);
    }
    //2) 첨부파일 삭제
    int affectedRow = uploadFileSVC.deleteFileByUploadFildId(fid);

    if (affectedRow == 1) {     //삭제가 잘 됐으면
      return ApiResponse.createApiResMsg("00", "성공", null);
    }
    return ApiResponse.createApiResMsg("99", "실패", null);
  }
}