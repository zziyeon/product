delete from product ;

drop SEQUENCE product_product_id_seq;
create sequence product_product_id_seq;

select * from product order by product_id asc;

-- rownum은 무조건 첫행부터
-- case1)
select *
from ( select rownum no, product_id, pname, quantity, price 
        from product) t1
where  t1.no between 1 and 10;

-- case2)
select * 
from ( select row_number() over(order by product_id desc) no,
            product_id, pname, quantity, price
        from product) t1
        where t1.no between 1 and 10
            and ;
        
       
-- 프로시저 생성
create or replace procedure insert_product
is 
begin
    for i in 1..352
    loop
        insert into product values (product_product_id_seq.nextval, '상품명' || i, i,i);
    end loop;
end;
