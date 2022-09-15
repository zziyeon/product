package com.kh.myapp3.web;

import com.kh.myapp3.domain.Product;
import com.kh.myapp3.domain.svc.ProductSVC;
import com.kh.myapp3.web.form.product.EditForm;
import com.kh.myapp3.web.form.product.ItemForm;
import com.kh.myapp3.web.form.product.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록양식
  @GetMapping("/add")
  public String saveForm(){

    return "product/addForm"; //상품등록 view
  }

  //등록처리
  @PostMapping("/add")
  public String save(SaveForm saveForm){
    log.info("saveForm:{}",saveForm);

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Product savedProduct = productSVC.save(product);

    return "redirect:/products/"+savedProduct.getProductId();  //상품상세 요청 url
  }

  //상품개별조회
  @GetMapping("/{pid}")
  public String findByProductId(
      @PathVariable("pid") Long pid,
      Model model
  ){
    //db에서 상품조회
    Product findedProduct = productSVC.findById(pid);

    //Product => ItemForm 복사
    ItemForm itemForm = new ItemForm();
    itemForm.setProductId(findedProduct.getProductId());
    itemForm.setPname(findedProduct.getPname());
    itemForm.setQuantity(findedProduct.getQuantity());
    itemForm.setPrice(findedProduct.getPrice());

    //view에서 참조하기위에 model객체에 저장
    model.addAttribute("itemForm",itemForm);

    return "product/itemForm"; //상품 상세 view
  }

  //수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm(@PathVariable("pid") Long pid, Model model){

    Product findedProduct = productSVC.findById(pid);

    //Product => EditForm 변환
    EditForm editForm = new EditForm();
    editForm.setProductId(findedProduct.getProductId());
    editForm.setPname(findedProduct.getPname());
    editForm.setQuantity(findedProduct.getQuantity());
    editForm.setPrice(findedProduct.getPrice());

    model.addAttribute("editForm",editForm);

    return "product/editForm";  //상품 수정 view
  }


  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid, EditForm editForm){
    Product product = new Product();
    product.setProductId(pid);
    product.setPname(editForm.getPname());
    product.setQuantity(editForm.getQuantity());
    product.setPrice(editForm.getPrice());

    productSVC.update(pid, product);
    return "redirect:/products/"+pid; //상품 상세 url
  }

  //삭제처리
  @GetMapping("/{pid}/del")
  public String delete(@PathVariable("pid") Long pid){

    productSVC.delete(pid);
    return "redirect:/products"; // 전체 목록 view
  }

  //목록화면
  @GetMapping
  public String list(Model model){

    List<Product> list = productSVC.findAll();
    model.addAttribute("list",list);
    return "product/all"; //전체목록 view
  }
}





