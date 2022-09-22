drop table product;

create table product(
    pid NUMBER(10),
    pname varchar2(60),
    count number(10),
    price number(10)
);

--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
drop sequence product_pid_seq;
create sequence product_pid_seq;


commit;