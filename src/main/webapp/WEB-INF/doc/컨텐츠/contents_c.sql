/**********************************/
/* Table Name: 컨텐츠 */
/**********************************/
DROP TABLE attachfile;
DROP TABLE contents CASCADE CONSTRAINTS;
CREATE TABLE contents(
        contentsno                            NUMBER(10)         NOT NULL         PRIMARY KEY,
        memberno                              NUMBER(10)     NOT NULL ,
        cateno                                NUMBER(10)         NOT NULL ,
        title                                 VARCHAR2(300)         NOT NULL,
        content                               CLOB                  NOT NULL,
        recom                                 NUMBER(7)         DEFAULT 0         NOT NULL,
        cnt                                   NUMBER(7)         DEFAULT 0         NOT NULL,
        replycnt                              NUMBER(7)         DEFAULT 0         NOT NULL,
        web                                   VARCHAR2(500)         NULL ,
        map                                   VARCHAR2(1000)         NULL ,
        youtube                               VARCHAR2(1000)         NULL ,
        mp3                                   VARCHAR2(1000)         NULL ,
        mp4                                   VARCHAR2(1000)         NULL ,
        visible                               CHAR(1)         DEFAULT 'Y'         NOT NULL,
        ip                                         VARCHAR2(15)         NOT NULL,
        passwd                                VARCHAR2(15)         NOT NULL,
        grpno                                 NUMBER(8)         DEFAULT 0         NOT NULL,
        indent                                NUMBER(2)         DEFAULT 0         NOT NULL,
        ansnum                                NUMBER(3)         DEFAULT 0         NOT NULL,
        word                                  VARCHAR2(300)         NULL ,
        rdate                                 DATE               NOT NULL,
        file1                                   VARCHAR(100)          NULL,
        thumb1                              VARCHAR(100)          NULL,
        size1                                 NUMBER(10)      DEFAULT 0 NULL,  
  FOREIGN KEY (memberno) REFERENCES member (memberno),
  FOREIGN KEY (cateno) REFERENCES cate (cateno)
);

COMMENT ON TABLE contents is '컨텐츠';
COMMENT ON COLUMN contents.contentsno is '컨텐츠 번호';
COMMENT ON COLUMN contents.memberno is '회원 번호';
COMMENT ON COLUMN contents.cateno is '카테고리 번호';
COMMENT ON COLUMN contents.title is '제목';
COMMENT ON COLUMN contents.content is '내용';
COMMENT ON COLUMN contents.recom is '추천수';
COMMENT ON COLUMN contents.cnt is '조회수';
COMMENT ON COLUMN contents.replycnt is '댓글수';
COMMENT ON COLUMN contents.web is '인터넷 주소';
COMMENT ON COLUMN contents.map is '지도';
COMMENT ON COLUMN contents.youtube is 'Youtube';
COMMENT ON COLUMN contents.mp3 is 'MP3';
COMMENT ON COLUMN contents.mp4 is 'MP4';
COMMENT ON COLUMN contents.visible is '출력 모드';
COMMENT ON COLUMN contents.ip is 'IP';
COMMENT ON COLUMN contents.passwd is '패스워드';
COMMENT ON COLUMN contents.grpno is '그룹번호';
COMMENT ON COLUMN contents.indent is '들여쓰기/답변차수';
COMMENT ON COLUMN contents.ansnum is '답변순서';
COMMENT ON COLUMN contents.word is '검색어';
COMMENT ON COLUMN contents.rdate is '등록일';
COMMENT ON COLUMN contents.file1 is '메인 이미지';
COMMENT ON COLUMN contents.thumb1 is '메인 이미지 Preview';
COMMENT ON COLUMN contents.size1 is ' 메인 이미지 크기';

DROP SEQUENCE contents_seq;
CREATE SEQUENCE contents_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
1) 글 등록(이미지 관련 컬러 추가)
-- memberno: 1, cateno: 1이 사전에 등록이되어 있어야 INSERT 가능
INSERT INTO contents(contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate,
                              file1, thumb1, size1)
VALUES(contents_seq.nextval, 1, 1, '신규확진 2명', '전원 입국검역서 확인', 'http://www.daum.net',
            '127.0.0.1', '123', '코로나', sysdate, 'spring.jpg', 'spring_t.jpg', 23657);

INSERT INTO contents(contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate,
                              file1, thumb1, size1)
VALUES(contents_seq.nextval, 1, 1, '신규확진 2명', '전원 입국검역서 확인', 'http://www.daum.net',
            '127.0.0.1', '123', '코로나', sysdate, 'summer.jpg', 'summer_t.jpg', 23657);
            
INSERT INTO contents(contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate,
                              file1, thumb1, size1)
VALUES(contents_seq.nextval, 1, 2, '스위스', '가을 여행', 'http://www.daum.net',
            '127.0.0.1', '123', '코로나', sysdate, 'maple.jpg', 'maple_t.jpg', 23657);

COMMIT;

SELECT contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate, file1, thumb1, size1
FROM contents 
ORDER BY contentsno ASC;    

-- 현재 sequence의 확인
SELECT contents_seq.nextval FROM dual;
SELECT contents_seq.currval FROM dual;

2) 전체목록
SELECT contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate, file1, thumb1, size1 
FROM contents 
ORDER BY contentsno DESC;

2-1) cateno별 목록
SELECT contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate, file1, thumb1, size1
FROM contents 
WHERE cateno = 1
ORDER BY contentsno DESC;

2-2) cateno별 검색 목록
1) 검색
① cateno별 검색 목록
SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%스위스%'
ORDER BY contentsno DESC;

SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%스의스%'
ORDER BY contentsno DESC;

SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%수의스%'
ORDER BY contentsno DESC;

SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%swiss%'
ORDER BY contentsno DESC;

SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%유럽%'
ORDER BY contentsno DESC;

-- title, content, word column search
SELECT contentsno, memberno, cateno, title, content, 
           recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND (title LIKE '%유럽%' OR content LIKE '%유럽%' OR word LIKE '%유럽%')
ORDER BY contentsno DESC;


② 검색 레코드 갯수
-- 전체 레코드 갯수
SELECT COUNT(*) as cnt
FROM contents
WHERE cateno=25;

-- cateno 별 검색된 레코드 갯수
SELECT COUNT(*) as cnt
FROM contents
WHERE cateno=25 AND word LIKE '%스위스%';

SELECT COUNT(*) as cnt
FROM contents
WHERE cateno=25 AND (title LIKE '%유럽%' OR content LIKE '%유럽%' OR word LIKE '%유럽%')


3) 1건 조회
SELECT contentsno, memberno, cateno, title, content, recom, web, map, youtube, mp3, mp4, ip, passwd,
          word, rdate, file1, thumb1, size1
FROM contents
WHERE contentsno = 1;

4) 수정
UPDATE contents 
SET title='제목', content='내용', web='http://', word='검색어'
WHERE contentsno = 5;

COMMIT;

5) 패스워드 검사
SELECT COUNT(*) as passwd_cnt
FROM contents
WHERE contentsno=5 AND passwd='1234';

SELECT contentsno, passwd
FROM contents 
ORDER BY contentsno DESC;    

6) 삭제
-- 전체 레코드 삭제
DELETE FROM contents;
COMMIT;  

DELETE FROM contents
WHERE contentsno=13;

COMMIT;  


7) 모든 레코드 갯수
SELECT COUNT(*) as cnt
FROM contents;
 
 COUNT
 -----
     5
     
8) 특정 회원이 작성한 글만 출력: cate + contents + member
SELECT c.cateno as c_cateno, c.name as c_name, c.rdate as c_rdate,
          t.contentsno as t_contentsno, t.title as t_title, t.web as t_web, t.ip as t_ip, t.rdate as t_rdate,
          m.memberno as m_memberno, m.mname as m_mname          
FROM cate c, contents t, member m
WHERE c.cateno = t.cateno AND t.memberno = m.memberno AND m.memberno = 1
ORDER BY c.cateno ASC, t.contentsno DESC;

-- ANSI
SELECT c.cateno as c_cateno, c.name as c_name, c.rdate as c_rdate,
          t.contentsno as t_contentsno, t.title as t_title, t.web as t_web, t.ip as t_ip, t.rdate as t_rdate,
          m.memberno as m_memberno, m.mname as m_mname          
FROM cate c
INNER JOIN contents t
ON c.cateno = c.cateno
INNER JOIN member m
ON t.memberno = m.memberno
WHERE m.memberno = 1
ORDER BY c.cateno ASC, t.contentsno DESC;

-- 특정 회원의 글 출력을 위한 cate, contents join
SELECT c.cateno as c_cateno, c.name as c_name, c.rdate as c_rdate,
          t.contentsno as t_contentsno, t.title as t_title, t.web as t_web, t.ip as t_ip, t.rdate as t_rdate        
FROM cate c, contents t, member m
WHERE c.cateno = t.cateno AND t.memberno = m.memberno AND m.memberno = 1
ORDER BY c.cateno ASC, t.contentsno DESC;

9) map
-- map                                   VARCHAR2(1000)         NULL ,
UPDATE contents
SET map='고창 보리밭 축제'
WHERE contentsno=1 AND passwd='123'; 
  
SELECT contentsno, memberno, cateno, web, map
FROM contents 
ORDER BY contentsno ASC;    

10) Youtube
-- map                                   VARCHAR2(1000)         NULL ,
UPDATE contents
SET youtube='youtube URL'
WHERE contentsno=13; 

SELECT contentsno, memberno, cateno, web, map, youtube
FROM contents 
WHERE contentsno=1 AND passwd='123'; 

11) MP3
UPDATE contents
SET mp3='mp3 file name'
WHERE contentsno=1 AND passwd='123'; 
  
SELECT contentsno, memberno, cateno, web, map, youtube, mp3
FROM contents 
ORDER BY contentsno ASC;

12) 첨부 파일 변경(등록, 변경, 수정, 삭제)
UPDATE contents
SET file1='file name', thumb1='thumb file name', size1=5000
WHERE contentsno=7; 
  
SELECT contentsno, memberno, cateno, file1, thumb1, size1
FROM contents 
ORDER BY contentsno ASC; 

COMMIT;

12) 검색 + 페이징
-- step 1
SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, file1, thumb1, size1
FROM contents
WHERE cateno=25 AND word LIKE '%스위스%'
ORDER BY contentsno DESC;

-- step 2
SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, rownum as r
FROM (
          SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, file1, thumb1, size1
          FROM contents
          WHERE cateno=25 AND word LIKE '%스위스%'
          ORDER BY contentsno DESC
);

-- step 3, 1 page
SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, r
FROM (
           SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, rownum as r
           FROM (
                     SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, file1, thumb1, size1
                     FROM contents
                     WHERE cateno=29 AND word LIKE '%스위스%'
                     ORDER BY contentsno DESC
           )          
)
WHERE r >= 1 AND r <= 10;

-- step 3, 2 page
SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, r
FROM (
           SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, rownum as r
           FROM (
                     SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word
                     FROM contents
                     WHERE cateno=25 AND word LIKE '%스위스%'
                     ORDER BY contentsno DESC
           )          
)
WHERE r >= 11 AND r <= 20;

----------------------------------------------------------------------------------------------
답변 관련 시작
----------------------------------------------------------------------------------------------
-- 1번글 기준 답변 등록예: grpno: 1, indent: 1, ansnum: 1
SELECT * FROM member;
SELECT * FROM categrp;
SELECT * FROM cateno;

DROP TABLE contents  CASCADE CONSTRAINTS;

1) 등록 변경
- grpno: 새롭게 글을 등록하면 하나의 신규 그룹이 생성됨, (SELECT NVL(MAX(grpno), 0) + 1 FROM contents), 0, 0)
- indent: 들여 쓰기, n차 답변에 대한 효과
- ansnum: 답변 순서 지정
INSERT INTO contents(contentsno, memberno, cateno, title, content, web, ip, passwd, word, rdate,
                              file1, thumb1, size1,
                              grpno, indent, ansnum)
VALUES(contents_seq.nextval, 1, 1, '신규확진 2명', '전원 입국검역서 확인', 'http://www.daum.net',
           '127.0.0.1', '123', '코로나', sysdate, 'spring.jpg', 'spring_t.jpg', 23657,
           (SELECT NVL(MAX(grpno), 0) + 1 FROM contents), 0, 0);
            
2) 1건 조회 변경
SELECT contentsno, memberno, cateno, title, content, recom, web,
          map, youtube, mp3, mp4, ip, passwd, word, rdate,
          file1, thumb1, size1, grpno, indent, ansnum
FROM contents
WHERE contentsno = 1;

3) 새로운 답변을 최신으로 등록하기위해 기존 답변을 뒤로 미룹니다.
-- 모든 글의 우선 순위가 1씩 증가됨, 1등 -> 2등
UPDATE contents
SET ansnum = ansnum + 1
WHERE grpno = 1 AND ansnum > 0;
 
-- 2등부터 우선 순위가 1씩 증가됨, 2등 -> 3등
UPDATE contents
SET ansnum = ansnum + 1
WHERE grpno = 1 AND ansnum > 1;
 
-- 3등부터 우선 순위가 1씩 증가됨, 3등 -> 4등
UPDATE contents
SET ansnum = ansnum + 1
WHERE grpno = 1 AND ansnum > 2;
 
-- 6등부터 우선 순위가 1씩 증가됨, 6등 -> 7등
UPDATE contents
SET ansnum = ansnum + 1
WHERE grpno = 1 AND ansnum > 5;
 
 
4) 답변 등록
- catenono: FK, mno: FK
 
INSERT INTO contents(contentsno,
                          cateno, mno, title, content, web, ip, passwd, good, thumbs, files, sizes, cnt, replycnt, rdate, 
                          grpno, indent, ansnum, word)  
VALUES((SELECT NVL(MAX(contentsno), 0) + 1 as contentsno FROM contents),
            1, 1, '제목', '내용','주소','172.0.0.1','1234', 0, 'summer_m.jpg', 'summer.jpg', 0, 0, 0, sysdate,
            1, 1, 1,'');
 
 
5) 답변에 따른 정렬 순서 변경    
-- ORDER BY grpno DESC, ansnum ASC
SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, ip,
          file1, thumb1, size1,
          grpno, indent, ansnum, r
FROM (
           SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, ip,
                     file1, thumb1, size1,
                     grpno, indent, ansnum, rownum as r
           FROM (
                     SELECT contentsno, memberno, cateno, title, content, recom, cnt, replycnt, rdate, word, ip,
                               file1, thumb1, size1,
                               grpno, indent, ansnum
                     FROM contents
                     WHERE cateno=29 AND word LIKE '%스위스%'
                     ORDER BY grpno DESC, ansnum ASC
           )          
)
WHERE r >= 1 AND r <= 10;        
   
----------------------------------------------------------------------------------------------
답변 관련 종료
----------------------------------------------------------------------------------------------
   
1) 댓글수 증가
UPDATE contents
SET replycnt = replycnt + 1
WHERE contentsno = 1;

2) 댓글수 감소
UPDATE contents
SET replycnt = replycnt - 1
WHERE contentsno = 1;   
  
  