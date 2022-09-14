package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{
  private final JdbcTemplate jt;

  //상품번호 등록
  public String generatedPid(){
    String sql = "select 'm-' || product_pid_seq.nextval from dual";
    String pid = jt.queryForObject(sql, String.class);
    return pid;
  }

  //등록
  @Override
  public Product save(Product product) {
      String sql = ("insert into product values(?, ?, ?,?)");
//    String sql="insert into product values (?,?,?,?) ";

//    jt.update(sql, product.getPid(), product.getPname(), product.getCount(), product.getPrice());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"pid"});
        pstmt.setString(1, product.getPid());
        pstmt.setString(2, product.getPname());
        pstmt.setInt(3, product.getCount());
        pstmt.setLong(4, product.getPrice());
        return pstmt;
      }
    }, keyHolder);

    String pid = String.valueOf(keyHolder.getKeys().get("pid").toString());

    product.setPid(pid);
    return product;
  }
  //조회
  @Override
  public Product findById(String pid) {
    StringBuffer sql = new StringBuffer();
    sql.append("select pid, pname, count, price ");
    sql.append("from product ");
    sql.append("where pid=? ");

    Product findedProduct = null;

    try {
      findedProduct = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class), pid);
    } catch (DataAccessException e) {
      log.info("찾고자하는 상품이 없습니다.=> {}", pid);
    }
    return findedProduct;
  }
  //수정
  @Override
  public int update(String pid, Product product) {
    StringBuffer sql = new StringBuffer();

    sql.append("update product ");
    sql.append("set pname = ?, count=?, price=? ");
    sql.append("where pid=? ");

    return jt.update(sql.toString(), product.getPname(), product.getCount(), product.getPrice(), pid);
  }
  //삭제
  @Override
  public int delete(String pid) {
    String sql = "delete from product where pid = ? ";

    return jt.update(sql, pid);
  }
  //목록
  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select pid, pname, count, price ");
    sql.append("from product ");
    sql.append("order by pid desc ");

    List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
    return result;
  }
}
