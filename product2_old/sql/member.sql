drop table member;
create table member(
    member_id   number(8),
    email       varchar2(40),
    pw          varchar2(10),
    nickname    varchar2(30),
    cdate       timestamp default systimestamp,
    udate       timestamp default systimestamp
);

--primary key
alter table member add constraint member_member_id_pk primary key(member_id);
--unique
alter table member add constraint member_email_uk unique (email);
--not null
alter table member modify email constraint member_email_nn not null;
alter table member modify pw constraint member_pw_nn not null;
alter table member modify cdate constraint member_cdate_nn not null;
alter table member modify udate constraint member_udate_nn not null;

--시퀀스생성
drop sequence member_member_id_seq;
create sequence member_member_id_seq;

--생성
insert into member (member_id,email,pw,nickname)
 values(member_member_id_seq.nextval, 'test1@test.com','1234','별칭1');
insert into member (member_id,email,pw,nickname)
 values(member_member_id_seq.nextval, 'test2@test.com','1234','별칭2');
insert into member (member_id,email,pw,nickname)
 values(member_member_id_seq.nextval, 'test3@test.com','1234','별칭3');

--조회
select member_id,email,pw,nickname,cdate,udate
  from member
 where member_id = '2';

--수정
update member
   set nickname = '별칭4',
       udate = systimestamp
 where member_id = '2'
   and pw = 5678,;

commit;
 --삭제
delete from member
 where member_id = '2';

--회원번호생성
select member_member_id_seq.nextval from dual;
select member_member_id_seq.currval from dual;
rollback;
select * from member;
