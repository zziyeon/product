package com.kh.product.web.form;

import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.form.product.EachInfoForm;
import com.kh.product.web.form.product.EditForm;
import com.kh.product.web.form.product.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductSVC productSVC;

  //등록 양식
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("form", new SaveForm());

    return "product/addForm";
  }

  //등록처리
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute("form") SaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    //검증
    if(bindingResult.hasErrors()){
      log.info("errors={}", bindingResult);
      return "product/addForm";
    }

    if (saveForm.getCount() > 100) {
      bindingResult.rejectValue("count", "countChk1", new String[]{"100"}, "상품수량은 100개를 초과할 수 없습니다.");
      return "product/addForm";
    }
    if (saveForm.getCount() <= 0 || saveForm.getPrice()<=0 ) {
      bindingResult.rejectValue("count", "chk", "상품수량, 가격은 0, 음수를 입력 불가");
      return "product/addForm";
    }

    if ((saveForm.getCount() * saveForm.getPrice())>10000000) {
      bindingResult.reject("totalChk", new String[]{"0", "1000만원"}, "총액이 1000만원을 초과할 수 없음");
      log.info("totalerrors={}", saveForm.getCount()* saveForm.getPrice());
      return "product/addForm";
    }

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setCount(saveForm.getCount());
    product.setPrice(saveForm.getPrice());

    Product savedProduct = productSVC.save(product);

    Long id = savedProduct.getPid();
    redirectAttributes.addAttribute("id", id);
    return "redirect:/product/{id}";
  }

  //상품 개별 조회
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    Product findedProduct = productSVC.findById(id);

    EachInfoForm eachInfoForm = new EachInfoForm();
    eachInfoForm.setPid(findedProduct.getPid());
    eachInfoForm.setPname(findedProduct.getPname());
    eachInfoForm.setCount(findedProduct.getCount());
    eachInfoForm.setPrice(findedProduct.getPrice());

    model.addAttribute("form", eachInfoForm);

    return "product/eachInfoForm";
  }

  //수정화면
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {
    Product findedProduct = productSVC.findById(id);

    EditForm editForm = new EditForm();

    editForm.setPid(findedProduct.getPid());
    editForm.setPname(findedProduct.getPname());
    editForm.setCount(findedProduct.getCount());
    editForm.setPrice(findedProduct.getPrice());

    model.addAttribute("form", editForm);
    return "product/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, @Valid @ModelAttribute("form") EditForm editForm, BindingResult bindingResult) {
    if (editForm.getCount() > 100) {
      bindingResult.rejectValue("count", "countChk1", new String[]{"100"}, "상품수량은 100개를 초과할 수 없습니다.");
      return "product/addForm";
    }
    if (editForm.getCount() <= 0 || editForm.getPrice()<=0 ) {
      bindingResult.rejectValue("count", "chk", "상품수량, 가격은 0, 음수를 입력 불가");
      return "product/addForm";
    }

    if ((editForm.getCount() * editForm.getPrice())>10000000) {
      bindingResult.reject("totalChk", new String[]{"0", "1000만원"}, "총액이 1000만원을 초과할 수 없음");
      log.info("totalerrors={}", editForm.getCount()* editForm.getPrice());
      return "product/addForm";
    }

    Product product = new Product();
    product.setPname(editForm.getPname());
    product.setCount(editForm.getCount());
    product.setPrice(editForm.getPrice());

    int updatedRow = productSVC.update(id, product);
    if (updatedRow == 0) {
      return "product/editForm";
    }
    return "redirect:/product";
  }
  //삭제처리
  @GetMapping("/{id}/del")
  public String delete(@PathVariable("id") Long id){
    int deletedRow = productSVC.delete(id);
    if (deletedRow == 0) {
      return "redirect:/product/" + id;
    }
    return "redirect:/product";
  }

  //목록화면
  @GetMapping("")
  public String all(Model model) {
    List<Product> products = productSVC.findAll();
    List<EachInfoForm> list = new ArrayList<>();

    products.stream().forEach(product -> {
      EachInfoForm eachInfoForm = new EachInfoForm();
      BeanUtils.copyProperties(product, eachInfoForm);
      list.add(eachInfoForm);
    });
    model.addAttribute("list", list);
    return "product/list";
  }
}
