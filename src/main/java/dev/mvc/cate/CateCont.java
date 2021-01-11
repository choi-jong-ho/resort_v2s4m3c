package dev.mvc.cate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.categrp.CategrpProcInter;
import dev.mvc.categrp.CategrpVO;
import dev.mvc.contents.ContentsProcInter;

@Controller
public class CateCont {
  @Autowired
  @Qualifier("dev.mvc.categrp.CategrpProc")
  private CategrpProcInter categrpProc;
  
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;

  public CateCont() {
    System.out.println("--> CateCont created.");
  }

  /**
   * 등록폼 http://localhost:9090/resort/cate/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/cate/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cate/create"); // /cate/create.jsp

    return mav;
  }

  /**
   * 등록처리
   * http://localhost:9090/resort/cate/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/cate/create.do", method = RequestMethod.POST)
  public ModelAndView create(CateVO cateVO) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cate/create_msg");

    // System.out.println("--> categrpno: " + cateVO.getCategrpno());
    
    int cnt = this.cateProc.create(cateVO);
    mav.addObject("cnt", cnt);

    return mav;
  }
  
  /**
   * 전체 목록 http://localhost:9090/resort/cate/list_all.do
   * 
   * @return
   */
  @RequestMapping(value = "/cate/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cate/list_all"); // /webapp/cate/list_all.jsp

    // List<CateVO> list = this.cateProc.list_cateno_asc();
    List<CateVO> list = this.cateProc.list_seqno_asc();
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 목록 http://localhost:9090/resort/cate/list.do
   * 
   * @return
   */
  /*
   * @RequestMapping(value = "/cate/list.do", method = RequestMethod.GET) public
   * ModelAndView list(int categrpno) { ModelAndView mav = new ModelAndView();
   * mav.setViewName("/cate/list"); // /webapp/cate/list.jsp
   * 
   * // Categrp 접근 CategrpVO categrpVO = this.categrpProc.read(categrpno);
   * mav.addObject("categrpVO", categrpVO);
   * 
   * // List<CateVO> list = this.cateProc.list_cateno_asc(); List<CateVO> list =
   * this.cateProc.list_by_categrpno(categrpno); mav.addObject("list", list);
   * 
   * return mav; // forward }
   */
  
  /**
   * 조회 + 수정폼 http://localhost:9090/resort/cate/read_update.do
   * 
   * @return
   */
  @RequestMapping(value = "/cate/read_update.do", method = RequestMethod.GET)
  public ModelAndView read_update(int cateno, int categrpno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cate/read_update"); // /webapp/cate/read_update.jsp

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);

    List<CateVO> list = this.cateProc.list_by_categrpno(categrpno);
    mav.addObject("list", list);

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * 
   * @param cateVO
   * @return
   */
  @RequestMapping(value = "/cate/update.do", method = RequestMethod.POST)
  public ModelAndView update(CateVO cateVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.cateProc.update(cateVO);
    mav.addObject("cnt", cnt); // request에 저장

    mav.setViewName("/cate/update_msg"); // webapp/cate/update_msg.jsp

    return mav;
  }
  
  /**
   * 조회 + 삭제폼 http://localhost:9090/resort/cate/read_delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/cate/read_delete.do", method = RequestMethod.GET)
  public ModelAndView read_delete(int cateno, int categrpno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cate/read_delete"); // /webapp/cate/read_delete.jsp

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);

    List<CateVO> list = this.cateProc.list_by_categrpno(categrpno);
    mav.addObject("list", list);

    return mav; // forward
  }
  
  /**
   * 삭제 처리
   * 
   * @param cateVO
   * @return
   */
  @RequestMapping(value = "/cate/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(int cateno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.cateProc.delete(cateno);
    mav.addObject("cnt", cnt); // request에 저장

    mav.setViewName("/cate/delete_msg"); // webapp/cate/delete_msg.jsp

    return mav;
  }
  
  /**
   * 우선순위 상향 up 10 ▷ 1
   * @param cateno 카테고리 번호
   * @return
   */
  @RequestMapping(value="/cate/update_seqno_up.do", 
                              method=RequestMethod.GET )
  public ModelAndView update_seqno_up(int cateno, int categrpno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.cateProc.update_seqno_up(cateno);
    // mav.addObject("cnt", cnt); // request에 저장
    
    mav.setViewName("redirect:/cate/list.do?categrpno=" + categrpno); // 파라미터 자동 전달 안됨. 

    return mav;
  }
  
  /**
   * 우선순위 하향 up 1 ▷ 10
   * @param cateno 카테고리 번호
   * @return
   */
  @RequestMapping(value="/cate/update_seqno_down.do", 
                              method=RequestMethod.GET )
  public ModelAndView update_seqno_down(int cateno, int categrpno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.cateProc.update_seqno_down(cateno);
    // mav.addObject("cnt", cnt); // request에 저장
    
    mav.setViewName("redirect:/cate/list.do?categrpno=" + categrpno); // 파라미터 자동 전달 안됨. 

    return mav;
  }
  
  /**
   * 출력모드 변경
   * 
   * @param cateVO
   * @return
   */
  @RequestMapping(value = "/cate/update_visible.do", method = RequestMethod.GET)
  public ModelAndView update_visible(CateVO cateVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.cateProc.update_visible(cateVO);
    mav.addObject("cnt", cnt); // request에 저장

    if (cnt == 1) { 
      mav.setViewName("redirect:/cate/list.do?categrpno=" + cateVO.getCategrpno()); // request 객체가 전달이 안됨. 
    } else {
      CateVO vo = this.cateProc.read(cateVO.getCateno());
      String name = vo.getName();
      mav.addObject("name", name);
      mav.setViewName("/cate/update_visible_msg"); // /cate/update_visible_msg.jsp
    }
    return mav;
  }
  
  // http://localhost:9090/resort/cate/list_join.do
  /**
   * categrp + cate join 전체 목록
   * @return
   */
  @RequestMapping(value="/cate/list_join.do", method=RequestMethod.GET )
  public ModelAndView list_join() {
    ModelAndView mav = new ModelAndView();
    
    List<Categrp_Cate_join> list = this.cateProc.list_join();
    mav.addObject("list", list); // request.setAttribute("list", list);

    mav.setViewName("/cate/list_join"); // webapp/cate/list_join.jsp
    return mav;
  }
  
  // http://localhost:9090/resort/cate/list.do 기존의 url 사용
  /**
   * categrp + cate join 전체 목록
   * @return
   */
  @RequestMapping(value="/cate/list.do", method=RequestMethod.GET )
  public ModelAndView list_join_by_categrpno(int categrpno) {
    ModelAndView mav = new ModelAndView();
    
    CategrpVO categrpVO = this.categrpProc.read(categrpno);
    mav.addObject("categrpVO", categrpVO);
    
    List<Categrp_Cate_join> list = this.cateProc.list_join_by_categrpno(categrpno);
    mav.addObject("list", list); // request.setAttribute("list", list);

    mav.setViewName("/cate/list_join_by_categrpno"); // webapp/cate/list_join_by_categrpno.jsp
    return mav;
  }
 
  /**
   * 결합된 카테고리 그룹별 카테고리 목록
   * http://localhost:9090/resort/cate/list_index_left.do
   * @param request
   * @return
   */
 @RequestMapping(value="/cate/list_index_left.do", method=RequestMethod.GET)
 public ModelAndView list_index_left(HttpServletRequest request){
   // System.out.println("--> list_index() GET called.");
   ModelAndView mav = new ModelAndView();
   mav.setViewName("/cate/list_index_left"); // webapp/cate/list_index_left.jsp
   
   List<CategrpVO> categrp_list = categrpProc.list_seqno_asc(); // 카테고리 그룹 목록
   
   // Categrp: name, Cate: name 결합 목록
   ArrayList<String> name_title_list = new ArrayList<String>();   
   
   StringBuffer url = new StringBuffer(); // 카테고리 제목 링크 조합
 
   // 카테고리 그룹 갯수 만큼 순환
   for (int index = 0; index < categrp_list.size(); index++) {
     CategrpVO categrpVO = categrp_list.get(index); // 하나의 카테고리 그룹 추출
 
     // 카테고리그룹명 추가
     name_title_list.add("<LI class='categrp_name'>"+ categrpVO.getName() + "</LI>");

     // 카테고리 Join 목록, 특정 카테고리 그룹에 해당하는 카테고리 가져옴.
     List<Categrp_Cate_join> cate_list = cateProc.list_join_by_categrpno(categrpVO.getCategrpno()); 
     
     // 카테고리 갯수만큼 순환
     for (int j=0; j < cate_list.size(); j++) {
       Categrp_Cate_join categrp_Cate_join = cate_list.get(j);
       
       String name = categrp_Cate_join.getName(); // 카테고리 이름
       int cnt = categrp_Cate_join.getCnt();
       
       url.append("<LI class='cate_name'>");
       url.append("  <A href='" + request.getContextPath()+ "/contents/list.do?cateno="+categrp_Cate_join.getCateno()+"'>");
       url.append(name);
       url.append("  </A>");
       url.append("  <span style='font-size: 0.9em; color: #555555;'>("+cnt+")</span>");
       url.append("</LI>");
       
       name_title_list.add(url.toString()); // 출력 목록에 하나의 cate명 추가 
       
       url.delete(0, url.toString().length()); // 새로운 카테고리 목록을 구성하기위해 StringBuffer 문자열 삭제
       
     }
   }
   
   mav.addObject("name_title_list", name_title_list);
   mav.addObject("total_count", contentsProc.total_count());
   
   return mav;
 } 
  
  
}




