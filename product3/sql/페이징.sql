delete from product ;

drop SEQUENCE product_product_id_seq;
create sequence product_product_id_seq;

select * from product order by product_id asc;

-- rownum�� ������ ù�����
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
        
       
-- ���ν��� ����
create or replace procedure insert_product
is 
begin
    for i in 1..352
    loop
        insert into product values (product_product_id_seq.nextval, '��ǰ��' || i, i,i);
    end loop;
end;
