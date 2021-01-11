/**********************************/
/* Table Name: 댓글 */
/**********************************/
DROP TABLE reply;

CREATE TABLE reply(
        replyno                                NUMBER(10)         NOT NULL         PRIMARY KEY,
        contentsno                           NUMBER(10)    NOT     NULL ,
        memberno                            NUMBER(6)         NOT NULL ,
        content                               VARCHAR2(1000)         NOT NULL,
        passwd                                VARCHAR2(20)         NOT NULL,
        rdate                              DATE NOT NULL,
  FOREIGN KEY (contentsno) REFERENCES contents (contentsno),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE reply is '댓글';
COMMENT ON COLUMN reply.replyno is '댓글번호';
COMMENT ON COLUMN reply.contentsno is '컨텐츠번호';
COMMENT ON COLUMN reply.memberno is '회원 번호';
COMMENT ON COLUMN reply.content is '내용';
COMMENT ON COLUMN reply.passwd is '비밀번호';
COMMENT ON COLUMN reply.rdate is '등록일';

1) 등록
INSERT INTO reply(replyno, contentsno, memberno, content, passwd, rdate)
VALUES((SELECT NVL(MAX(replyno), 0) + 1 as replyno FROM reply),
             1, 1, '댓글1', '1234', sysdate);
INSERT INTO reply(replyno, contentsno, memberno, content, passwd, rdate)
VALUES((SELECT NVL(MAX(replyno), 0) + 1 as replyno FROM reply),
             1, 1, '댓글2', '1234', sysdate);
INSERT INTO reply(replyno, contentsno, memberno, content, passwd, rdate)
VALUES((SELECT NVL(MAX(replyno), 0) + 1 as replyno FROM reply),
             1, 1, '댓글3', '1234', sysdate);             

commit;

2) 전체 목록
SELECT replyno, contentsno, memberno, content, passwd, rdate
FROM reply
ORDER BY replyno DESC;

 REPLYNO CONTENTSNO MEMBERNO CONTENT PASSWD RDATE
 ------- ---------- -------- ------- ------ ---------------------
       3          1        1 댓글3     1234   2019-12-17 16:59:38.0
       2          1        1 댓글2     1234   2019-12-17 16:59:37.0
       1          1        1 댓글1     1234   2019-12-17 16:59:36.0


3) contentsno 별 목록
SELECT replyno, contentsno, memberno, content, passwd, rdate
FROM reply
WHERE contentsno=1
ORDER BY replyno DESC;

 REPLYNO CONTENTSNO MEMBERNO CONTENT PASSWD RDATE
 ------- ---------- -------- ------- ------ ---------------------
       3          1        1 댓글3     1234   2019-12-17 16:59:38.0
       2          1        1 댓글2     1234   2019-12-17 16:59:37.0
       1          1        1 댓글1     1234   2019-12-17 16:59:36.0


4) 삭제
-- 패스워드 검사
SELECT count(passwd) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   1
   
-- 삭제
DELETE FROM reply
WHERE replyno=1;

COMMIT;

5) contentsno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE contentsno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE contentsno=1;

6) memberno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE memberno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE memberno=1;

7) 회원 ID의 출력
SELECT m.id,
           r.replyno, r.contentsno, r.memberno, r.content, r.passwd, r.rdate
FROM member m,  reply r
WHERE (m.memberno = r.memberno) AND r.contentsno=1
ORDER BY r.replyno DESC;

 ID    REPLYNO CONTENTSNO MEMBERNO CONTENT                                                                                                                                                                         PASSWD RDATE
 ----- ------- ---------- -------- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ------ ---------------------
 user1       3          1        1 댓글 3                                                                                                                                                                            123    2019-12-18 16:46:43.0
 user1       2          1        1 댓글 2                                                                                                                                                                            123    2019-12-18 16:46:39.0
 user1       1          1        1 댓글 1                                                                                                                                                                            123    2019-12-18 16:46:35.0
 
 
8) 삭제용 패스워드 검사
SELECT COUNT(*) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   0

9) 삭제
DELETE FROM reply
WHERE replyno=1;

