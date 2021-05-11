
--게시판생성
create table board (	
    no number, 
    title varchar2(50), 
    writer varchar2(15),
    content varchar2(2000), 
    reg_date date default sysdate, 
    read_count number default 0, 
    constraint pk_board_no primary key(no),
    constraint fk_writer foreign key(writer) references member(member_id) on delete set null
);

comment on column board.no is '게시글번호';
comment on column board.title is '게시글제목';
comment on column board.writer is '게시글작성자 아이디';
comment on column board.content is '게시글내용';
comment on column board.reg_date is '게시글올린날짜';
comment on column board.read_count is '조회수';

--게시판시퀀스생성
create sequence seq_board_no;

--첨부파일테이블 생성
create table attachment (
    no number primary key,
    board_no number not null,
    original_filename varchar2(255) not null,
    renamed_filename varchar2(255) not null,
    status char(1) default 'Y',
    constraint fk_attach_board_no foreign key(board_no) references board(no) on delete cascade,
	constraint ck_status check(status in ('Y','N'))
);

comment on column attachment.no is '첨부파일번호(PK)';
comment on column attachment.board_no is '게시판글번호(FK)';
comment on column attachment.original_filename is '업로드한 첨부파일명';
comment on column attachment.renamed_filename is '서버에 저장된 첨부파일명';
comment on column attachment.status is '사용여부';


--첨부파일 시퀀스생성
create sequence seq_attachment_no;
	


insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 1','honggd','반갑습니다',to_date('18/02/10','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 2','qwerty','안녕하세요',to_date('18/02/12','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 3','admin','반갑습니다',to_date('18/02/13','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 4','honggd','안녕하세요',to_date('18/02/14','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 5','qwerty','반갑습니다',to_date('18/02/15','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 6','admin','안녕하세요',to_date('18/02/16','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 7','honggd','반갑습니다',to_date('18/02/17','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 8','qwerty','안녕하세요',to_date('18/02/18','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 9','admin','반갑습니다',to_date('18/02/19','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 10','honggd','안녕하세',to_date('18/02/20','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 11','qwerty','반갑습니다',to_date('18/03/11','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 12','admin','안녕하세',to_date('18/03/12','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 13','honggd','반갑습니다',to_date('18/03/13','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 14','qwerty','안녕하세',to_date('18/03/14','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 15','admin','반갑습니다',to_date('18/03/15','RR/MM/DD'),0);


insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 16','honggd','안녕하세',to_date('18/03/16','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 17','qwerty','반갑습니다',to_date('18/03/17','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 18','admin','안녕하세',to_date('18/03/18','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 19','honggd','반갑습니다',to_date('18/03/19','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 20','qwerty','안녕하세',to_date('18/03/20','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 21','admin','반갑습니다',to_date('18/04/01','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 22','honggd','안녕하세',to_date('18/04/02','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 23','qwerty','반갑습니다',to_date('18/04/03','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 24','admin','안녕하세',to_date('18/04/04','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 25','honggd','반갑습니다',to_date('18/04/05','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 26','qwerty','안녕하세',to_date('18/04/06','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 27','admin','반갑습니다',to_date('18/04/07','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 28','honggd','안녕하세',to_date('18/04/08','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 29','qwerty','반갑습니다',to_date('18/04/09','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 30','admin','안녕하세',to_date('18/04/10','RR/MM/DD'),0);

insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 31','honggd','반갑습니다',to_date('18/04/16','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 32','qwerty','안녕하세',to_date('18/04/17','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 33','admin','반갑습니다',to_date('18/04/18','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 34','honggd','안녕하세',to_date('18/04/19','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 35','qwerty','반갑습니다',to_date('18/04/20','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 36','admin','안녕하세',to_date('18/05/01','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 37','honggd','반갑습니다',to_date('18/05/02','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 38','qwerty','안녕하세',to_date('18/05/03','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 39','admin','반갑습니다',to_date('18/05/04','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 40','honggd','안녕하세',to_date('18/05/05','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 41','qwerty','반갑습니다',to_date('18/05/06','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 42','admin','안녕하세',to_date('18/05/07','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 43','honggd','반갑습니다',to_date('18/05/08','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 44','qwerty','안녕하세',to_date('18/05/09','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 45','admin','반갑습니다',to_date('18/05/10','RR/MM/DD'),0);

insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 46','honggd','안녕하세',to_date('18/05/16','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 47','qwerty','반갑습니다',to_date('18/05/17','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 48','admin','안녕하세',to_date('18/05/18','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 49','honggd','반갑습니다',to_date('18/05/19','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 50','qwerty','안녕하세',to_date('18/05/20','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 51','admin','반갑습니다',to_date('18/05/01','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 52','honggd','안녕하세',to_date('18/06/02','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 53','qwerty','반갑습니다',to_date('18/06/03','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 54','admin','안녕하세',to_date('18/06/04','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 55','honggd','반갑습니다',to_date('18/06/05','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 56','qwerty','안녕하세',to_date('18/06/06','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 57','admin','반갑습니다',to_date('18/06/07','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 58','honggd','안녕하세',to_date('18/06/08','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 59','qwerty','반갑습니다',to_date('18/06/09','RR/MM/DD'),0);
insert into web.board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'안녕하세요, 게시판입니다 - 60','admin','안녕하세',to_date('18/06/10','RR/MM/DD'),0);


commit;

select * from board;
select * from attachment;
select * from member;

--selectBoardCount
select *
from(
    select row_number() over(order by reg_date desc) rnum, B.*
    from board B
    )B
where rnum between 1 and 5;

--insertBoard
--insert into board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,'제목제목','kym9129','내용내용내용',sysdate,0);
--insert into board (no,title,writer,content,reg_date,read_count) values (seq_board_no.nextval,?,?,?,sysdate,0);

--selectLastBoardNo = 
select seq_board_no.currval board_no 
from dual;

--insertAttachment = 
insert into attachment(no, board_no, original_filename, renamed_filename)
values(seq_attachment_no.nextval, ?, ?, ?);

--board를 조회(첨부파일 조회)
select b.* , 
        a.no attach_no,
        a.original_filename,
        a.renamed_filename,
        a.status
from board B left join attachment A
    on b.no = a.board_no
order by b.no desc;

--selectPagedList + join attach 
select * 
from(
    select row_number() over(order by b.no desc) rnum,
        b.* , 
        a.no attach_no,
        a.original_filename,
        a.renamed_filename,
        a.status
    from board B
        left join attachment A
         on b.no = a.board_no and a.status = 'Y'
    )B 
where rnum between 1 and 5;

--@boardViewServlet
--selectBoardByNo
select * from board where no = 68;
select * from attachment where board_no = 77 and status = 'Y';

--selectLastBoard
select rownum, B.*
from(
    select B.*, A.board_no, A.original_filename, A.renamed_filename, A.status
    from board B left join attachment A
    on B.no = A.board_no
    order by b.no desc)B
where rownum = 1;

select * from board order by no desc;
--deleteBoard
--delete from board where no = 66;
--commit;

select * from attachment order by no desc;

--댓글 테이블 작성
create table board_comment(
    no number,
    comment_level number default 1,
    writer varchar2(15),
    content varchar2(2000),
    board_no number,
    comment_ref number,
    reg_date date default sysdate,
    constraint pk_board_comment_no primary key(no),
    constraint fk_board_comment_writer foreign key(writer) references member(member_id) on delete set null,
    constraint fk_board_comment_board_no foreign key(board_no) references board(no) on delete cascade,
    constraint fk_board_comment_ref foreign key(comment_ref) references board_comment(no) on delete cascade
);

comment on column board_comment.no is '게시판댓글번호';
comment on column board_comment.comment_level is '게시판댓글 레벨';
comment on column board_comment.writer is '게시판댓글 작성자';
comment on column board_comment.content is '게시판댓글';
comment on column board_comment.board_no is '참조원글번호';
comment on column board_comment.comment_ref is '게시판댓글 참조번호';
comment on column board_comment.reg_date is '게시판댓글 작성일';

--create sequence
create sequence seq_board_comment_no;

--샘플 테스트
--74번 글
select* from member;

--댓글 추가
insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'qwer', '댓글 남긴다.', 74, null);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'kym9129', '졸린뎀...', 74, null);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'imamu', '댓글놀이 시작!', 74, null);

--대댓글 추가
insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'honggd', '관심 ㄱㅅㄱㅅ', 74, 1);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'honggd', '졸리면 졸리G', 74, 1);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'honggd', '2번 댓글에 대댓글 남긴다.', 74, 2);



select * from board_comment;

--계층형 쿼리
/*
- 기준 컬럼을 이용해 행간의 수직구조를 표현한 쿼리
- 댓글, 조직도, 메뉴 등의 트리구조를 표현할 수 있다.
- `start with` : 최상위 행을 지정
- `connect by` : 부모행-자식행의 관계 작성. 부모행의 컬럼 앞에 prior키워드를 작성
*/

--level가상컬럼을 사용할 수 있다
select lpad(' ', (level-1) * 5) || content,
        level,
        bc.*
from board_comment bc
start with comment_level = 1
connect by prior no = comment_ref
order siblings by reg_date desc;

commit;

--selectBoardCommentList
select bc.*
from board_comment bc
where board_no = 74
start with comment_level = 1
connect by prior no = comment_ref
order siblings by reg_date;

--select bc.* from board_comment bc where board_no = ? start with comment_level = 1 connect by prior no = comment_ref order siblings by reg_date

--selectCommentCount
select count(*) count
from(
    select bc.*
    from board_comment bc
    where board_no = ?
    start with comment_level = 1
    connect by prior no = comment_ref
    order siblings by reg_date
);




--시험용 쿼리
CREATE TABLE BOARD(
BOARD_NUM NUMBER,
BOARD_WRITER VARCHAR2(20) NOT NULL,
BOARD_TITLE VARCHAR2(50) NOT NULL,
BOARD_CONTENT VARCHAR2(2000) NOT NULL,
BOARD_DATE DATE DEFAULT SYSDATE,
CONSTRAINT PK_BOARD PRIMARY KEY (BOARD_NUM),
CONSTRAINT FK_BOARD_WRITER FOREIGN KEY (BOARD_WRITER) REFERENCES MEMBER (USERID) ON DELETE SET NULL

);

create sequence seq_board_no;

insert into board(
values(seq_board_no.nextval, ?, ?, ?, sysdate)