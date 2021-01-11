/**********************************/
/* Table Name: 첨부파일 */
/**********************************/
DROP TABLE attachfile;
CREATE TABLE attachfile(
        attachfileno                  NUMBER(10)         NOT NULL         PRIMARY KEY,
        contentsno                   NUMBER(10)         NULL ,
        fname                             VARCHAR2(100)         NOT NULL,
        fupname                      VARCHAR2(100)         NOT NULL,
        thumb                         VARCHAR2(100)         NULL ,
        fsize                                 NUMBER(10)         DEFAULT 0         NOT NULL,
        rdate                           DATE     NOT NULL,
  FOREIGN KEY (contentsno) REFERENCES contents (contentsno)
);

COMMENT ON TABLE attachfile is '첨부파일';
COMMENT ON COLUMN attachfile.attachfileno is '첨부파일번호';
COMMENT ON COLUMN attachfile.contentsno is '컨텐츠번호';
COMMENT ON COLUMN attachfile.fname is '원본 파일명';
COMMENT ON COLUMN attachfile.fupname is '업로드 파일명';
COMMENT ON COLUMN attachfile.thumb is 'Thumb 파일명';
COMMENT ON COLUMN attachfile.fsize is '파일 사이즈';
COMMENT ON COLUMN attachfile.rdate is '등록일';

DROP SEQUENCE attachfile_seq;
CREATE SEQUENCE attachfile_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- 1) 등록
INSERT INTO attachfile(attachfileno, contentsno, fname, fupname, thumb, fsize, rdate)
VALUES(attachfile_seq.nextval, 1, 'samyang.jpg', 'samyang_1.jpg', 'samyang_t.jpg', 1000, sysdate);

INSERT INTO attachfile(attachfileno, contentsno, fname, fupname, thumb, fsize, rdate)
VALUES(attachfile_seq.nextval, 1, 'samyang2.jpg', 'samyang2_1.jpg', 'samyang2_t.jpg', 2000, sysdate);
             
INSERT INTO attachfile(attachfileno, contentsno, fname, fupname, thumb, fsize, rdate)
VALUES(attachfile_seq.nextval,  1, 'samyang3.jpg', 'samyang3_1.jpg', 'samyang3_t.jpg', 3000, sysdate);        

commit;

-- 2) 목록( contentsno 기준 내림 차순, attachfileno 기준 오르차순)
SELECT attachfileno, contentsno, fname, fupname, thumb, fsize, rdate
FROM attachfile
ORDER BY contentsno DESC,  attachfileno ASC;

 ATTACHFILENO CONTENTSNO FNAME        FUPNAME        THUMB          FSIZE RDATE
 ------------ ---------- ------------ -------------- -------------- ----- ---------------------
            1          1 samyang.jpg  samyang_1.jpg  samyang_t.jpg   1000 2019-12-04 14:50:51.0
            2          1 samyang2.jpg samyang2_1.jpg samyang2_t.jpg  2000 2019-12-04 14:50:52.0
            3          1 samyang3.jpg samyang3_1.jpg samyang3_t.jpg  3000 2019-12-04 14:50:53.0

-- 3) PK 기준 하나의 레코드 조회
SELECT attachfileno, contentsno, fname, fupname, thumb, fsize, rdate
FROM attachfile
WHERE attachfileno = 1;

-- 4) FK 기준 contentsno가 동일한 레코드 조회, fname 오름 차순
SELECT attachfileno, contentsno, fname, fupname, thumb, fsize, rdate
FROM attachfile
WHERE contentsno = 1
ORDER BY fname ASC;

 ATTACHFILENO CONTENTSNO FNAME        FUPNAME      THUMB          FSIZE  RDATE
 ------------ ---------- ------------ ------------ -------------- ------ ---------------------
            1          1 car1.jpg     car1_0.jpg   car1_0_t.jpg    12099 2019-12-04 15:01:20.0
            2          1 car2.jpg     car2_0.jpg   car2_0_t.jpg    10553 2019-12-04 15:01:20.0
            3          1 car3.jpg     car3.jpg     car3_t.jpg       9208 2019-12-04 15:01:20.0
            9          1 moon.zip     moon.zip     NULL           292777 2019-12-05 10:14:12.0
            6          1 spring18.jpg spring18.jpg spring18_t.jpg 444455 2019-12-05 09:55:07.0

-- 5) 하나의 파일 삭제
DELETE FROM attachfile
WHERE attachfileno = 1;

COMMIT;

-- 6) 부모키별 갯수 산출
SELECT COUNT(*) as cnt
FROM attachfile
WHERE contentsno=1;

 CNT
 ---
   3             
             
-- 6) FK 부모 테이블별 레코드 삭제
DELETE FROM attachfile
WHERE contentsno=1;

(3 rows affected)

   
-- 7) Contents, Attachfile join
    SELECT c.title, 
               a.attachfileno, a.contentsno, a.fname, a.fupname, a.thumb, a.fsize, a.rdate
    FROM contents c, attachfile a
    WHERE c.contentsno = a.contentsno
    ORDER BY c.contentsno DESC,  a.attachfileno ASC;
   
 TITLE     ATTACHFILENO CONTENTSNO FNAME        FUPNAME        THUMB            FSIZE  RDATE
 --------- ------------ ---------- ------------ -------------- ---------------- ------ ---------------------
 크리스마스            4          2 winter14.jpg winter14.jpg   winter14_t.jpg   160966 2019-12-17 12:43:24.0
 크리스마스            5          2 winter15.jpg winter15.jpg   winter15_t.jpg   180887 2019-12-17 12:43:24.0
 크리스마스            6          2 winter16.jpg winter16.jpg   winter16_t.jpg    53359 2019-12-17 12:43:24.0
 겨울 여행              1          1 winter01.jpg winter01_0.jpg winter01_0_t.jpg 171853 2019-12-17 12:42:46.0
 겨울 여행              2          1 winter02.jpg winter02_0.jpg winter02_0_t.jpg 176114 2019-12-17 12:42:47.0
 겨울 여행              3          1 winter03.jpg winter03_0.jpg winter03_0_t.jpg 192743 2019-12-17 12:42:47.0

-- 8) 조회
SELECT attachfileno, contentsno, fname, fupname, thumb, fsize, rdate
FROM attachfile
WHERE attachfileno=1;



