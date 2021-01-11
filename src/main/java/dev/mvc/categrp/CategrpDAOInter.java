package dev.mvc.categrp;

import java.util.List;

// MyBATIS의 <mapper namespace="dev.mvc.categrp.CategrpDAOInter">에 선언 
public interface CategrpDAOInter {
  // 매퍼에 들어가는 메소드명은 MyBATIS의 XML 파일안의 id와 동일해야합니다.

  /**
   * <Xmp>
   * 카테고리 그룹 등록
   * <insert id="create" parameterType="CategrpVO"> 
   * </Xmp>
   * @param categrpVO
   * @return 처리된 레코드 갯수
   */
  public int create(CategrpVO categrpVO);
  
  /**
   * 목록
   * <xmp>
   * <select id="list_categrpno_asc" resultType="CategrpVO">
   * </xmp> 
   * @return 레코드 목록
   */
  public List<CategrpVO> list_categrpno_asc();
  
  /**
   * 조회
   * <xmp>
   *   <select id="read" resultType="CategrpVO" parameterType="int">
   * </xmp>  
   * @param categrpno
   * @return
   */
  public CategrpVO read(int categrpno);
  
  /**
   * 수정 처리
   * <xmp>
   *   <update id="update" parameterType="CategrpVO"> 
   * </xmp>
   * @param categrpVO
   * @return 처리된 레코드 갯수
   */
  public int update(CategrpVO categrpVO);
  
  /**
   * 삭제 처리
   * <xmp>
   *   <delete id="delete" parameterType="int">
   * </xmp> 
   * @param categrpno
   * @return 처리된 레코드 갯수
   */
  public int delete(int categrpno);
  
  /**
   * 목록
   * <xmp>
   * <select id="list_seqno_asc" resultType="CategrpVO">
   * </xmp> 
   * @return
   */
  public List<CategrpVO> list_seqno_asc();
 
  /**
   * 출력 순서 상향
   * <xmp>
   * <update id="update_seqno_up" parameterType="int">
   * </xmp>
   * @param categrpno
   * @return 처리된 레코드 갯수
   */
  public int  update_seqno_up(int categrpno);

 
  /**
   * 출력 순서 하향
   * <xmp>
   * <update id="update_seqno_down" parameterType="int">
   * </xmp>
   * @param categrpno
   * @return 처리된 레코드 갯수
   */
  public int  update_seqno_down(int categrpno); 
  
  /**
   * visible 수정
   * @param categrpVO
   * @return
   */
  public int update_visible(CategrpVO categrpVO);
  
}





