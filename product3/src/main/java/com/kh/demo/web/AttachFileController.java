package com.kh.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Slf4j
@Controller

@RequestMapping("/attach")
public class AttachFileController {
  @Value("${attach.root_dir}")
  private String attachRoot;    //첨부파일 루트경로



  //이미지
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @GetMapping("/img/{attachCode}/{storeFileName}")
  public Resource img(
          @PathVariable String attachCode,
          @PathVariable String storeFileName) throws MalformedURLException {
    //http://서버:포트/경로...
    //file:///d:/tmp/P0101/xxx--xxx-xxxx.png
    Resource resource = new UrlResource("file:///" + attachRoot+"/" +attachCode+"/"+storeFileName);
    return resource;
  }

  //다운로드
  @GetMapping("/file/{attachCode}/{fid}")
  public ResponseEntity<Resource> file(
          @PathVariable String attachCode,
          @PathVariable Long fid ) {
    ResponseEntity<Resource> res = null;

    O

    uploadFileDAO.findFileByUploadFileId(fid);
    return res;
  }

}
