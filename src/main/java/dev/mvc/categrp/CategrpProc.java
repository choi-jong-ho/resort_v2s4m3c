package dev.mvc.categrp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.categrp.CategrpProc")
public class CategrpProc implements CategrpProcInter {
  @Autowired
  private CategrpDAOInter categrpDAO; 
  
  public CategrpProc() {
    System.out.println("--> CategrpProc created.");
  }
  
  @Override
  public int create(CategrpVO categrpVO) {
    int cnt = this.categrpDAO.create(categrpVO);
    return cnt;
  }

  @Override
  public List<CategrpVO> list_categrpno_asc() {
    List<CategrpVO> list = this.categrpDAO.list_categrpno_asc();
    return list;
  }

  @Override
  public CategrpVO read(int categrpno) {
    CategrpVO categrpVO = this.categrpDAO.read(categrpno);
    
    return categrpVO;
  }

  @Override
  public int update(CategrpVO categrpVO) {
    int cnt = this.categrpDAO.update(categrpVO);
    
    return cnt;
  }

  @Override
  public int delete(int categrpno) {
    int cnt = this.categrpDAO.delete(categrpno);
    
    return cnt;
  }

  @Override
  public List<CategrpVO> list_seqno_asc() {
    List<CategrpVO> list = this.categrpDAO.list_seqno_asc();
    return list;
  }

  @Override
  public int update_seqno_up(int categrpno) {
    int cnt = this.categrpDAO.update_seqno_up(categrpno);
    return cnt;
  }

  @Override
  public int update_seqno_down(int categrpno) {
    int cnt = this.categrpDAO.update_seqno_down(categrpno);
    return cnt;
  }

  /**
   * Y -> N, N -> Y
   */
  @Override
  public int update_visible(CategrpVO categrpVO) {
    if (categrpVO.getVisible().equalsIgnoreCase("Y")) {
      categrpVO.setVisible("N");
    } else {
      categrpVO.setVisible("Y");
    }
        
    int cnt = this.categrpDAO.update_visible(categrpVO);
    return cnt;
  }
  
  

}




