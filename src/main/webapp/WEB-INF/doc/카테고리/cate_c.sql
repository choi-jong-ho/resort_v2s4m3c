-- 1. 테이블 삭제
DROP TABLE contents;
DROP TABLE cate;
DROP TABLE categrp;

-- 2. 테이블 생성
categrp
cate

-- 3. CASCADE option을 이용한 자식 테이블을 무시한 테이블 삭제, 관련된 제약조건이 삭제됨.
DROP TABLE cate CASCADE CONSTRAINTS;



/**********************************/
/* Table Name: 카테고리 그룹 */
/**********************************/
CREATE TABLE categrp(
		categrpno                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(50)		 NOT NULL,
		seqno                         		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		visible                       		CHAR(1)		 DEFAULT 'Y'		 NOT NULL,
		rdate                         		DATE		 NOT NULL
);

COMMENT ON TABLE categrp is '카테고리 그룹';
COMMENT ON COLUMN categrp.categrpno is '카테고리 그룹 번호';
COMMENT ON COLUMN categrp.name is '이름';
COMMENT ON COLUMN categrp.seqno is '출력 순서';
COMMENT ON COLUMN categrp.visible is '출력 모드';
COMMENT ON COLUMN categrp.rdate is '그룹 생성일';

DROP SEQUENCE categrp_seq;
CREATE SEQUENCE categrp_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE cate(
		cateno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		categrpno                     		NUMBER(10)		 NULL ,
		name                          		VARCHAR2(100)		 NOT NULL,
		seqno                         		NUMBER(10)		 DEFAULT 0		 NOT NULL,
		visible                       		CHAR(1)		 DEFAULT 'Y'		 NOT NULL,
		rdate                         		DATE		 NOT NULL,
		cnt                           		NUMBER(10)		 DEFAULT 0		 NOT NULL,
  FOREIGN KEY (categrpno) REFERENCES categrp (categrpno)
);

COMMENT ON TABLE cate is '카테고리';
COMMENT ON COLUMN cate.cateno is '카테고리 번호';
COMMENT ON COLUMN cate.categrpno is '카테고리 그룹 번호';
COMMENT ON COLUMN cate.name is '카테고리 이름';
COMMENT ON COLUMN cate.seqno is '출력 순서';
COMMENT ON COLUMN cate.visible is '출력 모드';
COMMENT ON COLUMN cate.rdate is '등록일';
COMMENT ON COLUMN cate.cnt is '등록된 글 수';

DROP SEQUENCE cate_seq;
CREATE SEQUENCE cate_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- 등록
INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
VALUES(cate_seq.nextval, 1000, '가을', 1, 'Y', sysdate, 0);
오류 보고 -
ORA-02291: integrity constraint (AI7.SYS_C008048) violated - parent key not found
-- FK 컬럼의 값 1000은 categrp 테이블에 없어서 에러 발생함.

-- 부모 테이블에 먼저 추가
INSERT INTO categrp(categrpno, name, seqno, visible, rdate)
VALUES(categrp_seq.nextval, '국내 여행', 1, 'Y', sysdate);

INSERT INTO categrp(categrpno, name, seqno, visible, rdate)
VALUES(categrp_seq.nextval, '해외 여행', 2, 'Y', sysdate);

INSERT INTO categrp(categrpno, name, seqno, visible, rdate)
VALUES(categrp_seq.nextval, '쇼핑', 3, 'Y', sysdate);

COMMIT;

SELECT * FROM categrp ORDER BY categrpno ASC;
 CATEGRPNO NAME                                                    SEQNO V RDATE              
---------- -------------------------------------------------- ---------- - -------------------
         1 국내 여행                                                   1 Y 2020-10-23 02:43:44
         2 해외 여행                                                   2 Y 2020-10-23 02:43:44
         3 쇼핑                                                        3 Y 2020-10-23 02:43:44

-- 등록: 국내 여행지중 가을/겨울 여행지 추천
INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
VALUES(cate_seq.nextval, 1, '가을', 1, 'Y', sysdate, 0);

INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
VALUES(cate_seq.nextval, 1, '겨울', 1, 'Y', sysdate, 0);

INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
VALUES(cate_seq.nextval, 1, '봄', 1, 'Y', sysdate, 0);

COMMIT;

-- 목록
SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
FROM cate
ORDER BY cateno ASC;
        PK           FK 
    CATENO  CATEGRPNO NAME     SEQNO V RDATE                      CNT
---------- ---------- ------------------ ---------- - ------------------- ----------
         2           1           가을              1 Y 2020-10-23 02:46:36          0
         3           1           겨울              1 Y 2020-10-23 02:47:53          0
         4           1           봄                 1 Y 2020-10-23 02:48:21          0
         
-- 조회
SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
FROM cate
WHERE cateno=3;

-- 수정
UPDATE cate
SET categrpno=1, name='식당', seqno = 10, visible='N', cnt=0
WHERE cateno = 3;

commit;

-- 삭제
DELETE cate
WHERE cateno = 3;

SELECT * FROM cate;

-- 출력 순서 상향, 10 ▷ 1
UPDATE cate
SET seqno = seqno - 1
WHERE cateno=2;

SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
FROM cate
ORDER BY seqno ASC;

-- 출력순서 하향, 1 ▷ 10
UPDATE cate
SET seqno = seqno + 1
WHERE cateno=2;

-- 출력 모드의 변경
UPDATE cate
SET visible='Y'
WHERE cateno=2;

UPDATE cate
SET visible='N'
WHERE cateno=2;

commit;

---------------------------------------------         
-- FK를 갖는 테이블 추가 구현
---------------------------------------------
-- 카테고리 그룹에따른 카테고리 목록
SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
FROM cate
WHERE categrpno=1
ORDER BY seqno ASC;

-- 부모 테이블 레코드 삭제
DELETE FROM categrp
WHERE categrpno = 1;
오류 보고 -
ORA-02292: integrity constraint (AI7.SYS_C008048) violated - child record found

-- 삭제하려면 레코드의 categrpno가 어디에서 쓰이는지 알려주어야함.
SELECT COUNT(*) as cnt
FROM cate
WHERE categrpno=1;

       CNT
----------
         2
         
-- 자식 테이블에서 FK가 1인 레코드 모두 삭제
DELETE FROM cate
WHERE categrpno=1;

-- 부모 테이블 레코드 삭제
DELETE FROM categrp
WHERE categrpno=1;

commit;

-- 부모 테이블 레코드 삭제 확인
SELECT * FROM categrp ORDER BY categrpno ASC;

-- 통합 VO
SELECT r.categrpno as r_categrpno, r.name as r_name,
           c.cateno, c.categrpno, c.name, c.seqno, c.visible, c.rdate, c.cnt
FROM categrp r, cate c
WHERE r.categrpno = c.categrpno
ORDER BY r.categrpno ASC, c.seqno ASC;

-- 통합 VO, categrpno 별 cate 목록
SELECT r.categrpno as r_categrpno, r.name as r_name,
           c.cateno, c.categrpno, c.name, c.seqno, c.visible, c.rdate, c.cnt
FROM categrp r, cate c
WHERE (r.categrpno = c.categrpno) AND r.categrpno=1
ORDER BY r.categrpno ASC, c.seqno ASC;

R_CATEGRPNO R_NAME  CATENO  CATEGRPNO  NAME      SEQNO VISIBLE RDATE            CNT
----------- --------------- ---------- ----------- --------------------- ---------- - ------------------- ----------
          1 영화                  1           1 SF                              1 Y 2020-05-12 04:04:28          0
          1 영화                  2           1 드라마                         2 Y 2020-05-12 04:04:28          0
          1 영화                  3           1 스릴러                         3 Y 2020-05-12 04:04:28          0

-- contents 추가에따른 등록된 글수의 증가
UPDATE cate 
SET cnt = cnt + 1 
WHERE cateno=1;
 
-- contents 추가에따른 등록된 글수의 감소
UPDATE cate 
SET cnt = cnt - 1 
WHERE cateno=1; 

-- 글수 초기화
UPDATE cate
SET cnt = 0;

COMMIT;










 
 