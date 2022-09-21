package com.kh.demo.domain.product;

import com.kh.demo.domain.common.file.AttachCode;
import com.kh.demo.domain.common.file.FileUtils;
import com.kh.demo.domain.common.file.UploadFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional
public class ProductSVCImpl implements ProductSVC{
  private final ProductDAO productDAO;
  private final UploadFileDAO uploadFileDAO;
  private final FileUtils fileUtils;

  //등록
  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  @Override
//  @Transactional(readOnly = false)      //상단에 걸어줬으니까 default로 걸려있음
  public Long save(Product product, MultipartFile file) {
    //1) 상품 등록
    Long id = save(product);

    //2) 첨부파일-상세설명
    uploadFileDAO.addFile(fileUtils.multipartFileToUpLoadFile(file, AttachCode.P0101, id));

    return id;
  }
  //여러건
  @Override
  public Long save(Product product, List<MultipartFile> files) {
    //1) 상품등록
    Long id = save(product);

    //2) 첨부파일-이미지
    uploadFileDAO.addFile(fileUtils.multipartFilesToUpLoadFiles(files,AttachCode.P0102,id));
    return id;
  }

  @Override
  public Long save(Product product, MultipartFile file, List<MultipartFile> files) {
    //1)상품등록
    Long id = save(product);

    //2)첨부파일-상품설명
    uploadFileDAO.addFile(fileUtils.multipartFileToUpLoadFile(file, AttachCode.P0101,id));

    //2)첨부파일-이미지
    uploadFileDAO.addFile(fileUtils.multipartFilesToUpLoadFiles(files,AttachCode.P0102,id));

    return id;
  }


  //목록
  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  //조회
  @Override
  public Optional<Product> findByProductId(Long productId) {
    return productDAO.findByProductId(productId);
  }

  //수정
  @Override
  public int update(Long productId, Product product) {
    return productDAO.update(productId,product);
  }

  //삭제
  @Override
  public int deleteByProductId(Long productId) {
    return productDAO.deleteByProductId(productId);
  }
}
