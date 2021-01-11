package dev.mvc.contents;

import java.util.HashMap;
import java.util.List;

public interface ContentsProcInter {
  /**
   * 등록
   * @param contentsVO
   * @return
   */
  public int create(ContentsVO contentsVO);
  
  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public List<ContentsVO> list_all();

  /**
   * 특정 카테고리의 등록된 글목록
   * @return
   */
  public List<ContentsVO> list_by_cateno(int cateno);
  
  /**
   * 조회
   * @param contentsno
   * @return
   */
  public ContentsVO read(int contentsno);
  
  /**
   * 수정용 조회
   * @param contentsno
   * @return
   */
  public ContentsVO read_update(int contentsno);
  
  /**
   * 수정 처리
   * @param contentsVO
   * @return
   */
  public int update(ContentsVO contentsVO);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int passwd_check(HashMap hashMap);
  
  /**
   * 삭제
   * @param contentsno
   * @return
   */
  public int delete(int contentsno);
  
  /**
   * 이미지 등록
   * @param contentsVO
   * @return
   */
  public int img_create(ContentsVO contentsVO);
  
  /**
   * 이미지 수정
   * @param contentsVO
   * @return
   */
  public int img_update(ContentsVO contentsVO);

  /**
   * 이미지 삭제
   * @param contentsVO
   * @return
   */
  public int img_delete(ContentsVO contentsVO);
  
  /**
   * 전체 레코드 갯수
   * @return
   */
  public int total_count();
  
}








