package dev.mvc.cate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.cate.CateProc")
public class CateProc implements CateProcInter {

  @Autowired
  private CateDAOInter cateDAO;
 
  public CateProc() {
    System.out.println("--> CateProc created");
  }
  
  @Override
  public int create(CateVO cateVO) {
    int cnt = this.cateDAO.create(cateVO);
    return cnt;
  }

  @Override
  public List<CateVO> list_cateno_asc() {
    List<CateVO> list = this.cateDAO.list_cateno_asc();
    return list;
  }

  @Override
  public List<CateVO> list_seqno_asc() {
    List<CateVO> list = this.cateDAO.list_seqno_asc();
    return list;
  }
  
  @Override
  public List<CateVO> list_by_categrpno(int categrpno) {
    List<CateVO> list = this.cateDAO.list_by_categrpno(categrpno);
    return list;
  }
  
  @Override
  public CateVO read(int cateno) {
    CateVO cateVO = this.cateDAO.read(cateno);
    return cateVO;
  }

  @Override
  public int update(CateVO cateVO) {
    int cnt = this.cateDAO.update(cateVO);
    return cnt;
  }

  @Override
  public int delete(int cateno) {
    int cnt = this.cateDAO.delete(cateno);
    return cnt;
  }
  
  @Override
  public int update_seqno_up(int cateno) {
    int cnt = this.cateDAO.update_seqno_up(cateno);
    return cnt;
  }

  @Override
  public int update_seqno_down(int cateno) {
    int cnt = this.cateDAO.update_seqno_down(cateno);
    return cnt;
  }

  @Override
  public int update_visible(CateVO cateVO) {
    if (cateVO.getVisible().equalsIgnoreCase("Y")) {
      cateVO.setVisible("N");
    } else {
      cateVO.setVisible("Y");
    }
    
    int cnt = this.cateDAO.update_visible(cateVO);
    return cnt;
  }

  @Override
  public List<Categrp_Cate_join> list_join() {
    List<Categrp_Cate_join> list = this.cateDAO.list_join();
    return list;
  }
  
  @Override
  public List<Categrp_Cate_join> list_join_by_categrpno(int categrpno) {
    List<Categrp_Cate_join> list = this.cateDAO.list_join_by_categrpno(categrpno);
    return list;
  }
  
  @Override
  public int increaseCnt(int cateno) {
    int cnt = this.cateDAO.increaseCnt(cateno);
    return cnt;
  }

  @Override
  public int decreaseCnt(int cateno) {
    int cnt = this.cateDAO.decreaseCnt(cateno);
    return cnt;
  }
  
}






