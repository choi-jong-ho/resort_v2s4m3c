package dev.mvc.cate;

import java.util.List;

public interface CateProcInter {
  /**
   * 등록
   * @param cateVO
   * @return 등록된 갯수
   */
  public int create(CateVO cateVO);
  
  /**
   * 목록
   * @return
   */
  public List<CateVO> list_cateno_asc();
  
  /**
   * 목록 - 출력순서 정렬
   * @return
   */
  public List<CateVO> list_seqno_asc();
  
  /**
   * 카테고리 그룹별 목록
   * @param categrpno 카테고리 그룹 번호
   * @return
   */
  public List<CateVO> list_by_categrpno(int categrpno);
  
  /**
   * 조회, 수정폼
   * @param cateno 카테고리 번호, PK
   * @return
   */
  public CateVO read(int cateno);
  
  /**
   * 수정 처리
   * @param cateVO
   * @return
   */
  public int update(CateVO cateVO);
  
  /**
   * 삭제 처리 
   * @param cateno
   * @return
   */
  public int delete(int cateno);
  
  /**
   * 출력 순서 상향
   * @param categrpno
   * @return 처리된 레코드 갯수
   */
  public int  update_seqno_up(int cateno);
 
  /**
   * 출력 순서 하향
   * @param categrpno
   * @return 처리된 레코드 갯수
   */
  public int  update_seqno_down(int cateno); 
  
  /**
   * visible 수정
   * @param categrpVO
   * @return
   */
  public int update_visible(CateVO cateVO);
  
  /**
   *  통합 VO 기반 join
   * @return
   */
  public List<Categrp_Cate_join> list_join();  
  
  /**
   *  통합 VO 기반 join
   * @return
   */
  public List<Categrp_Cate_join> list_join_by_categrpno(int categrpno);  
  
  /**
   * 글 수 증가
   * @return
   */
  public int increaseCnt(int cateno);    

  /**
   * 글 수 감소
   * @return
   */
  public int decreaseCnt(int cateno);

  
}


