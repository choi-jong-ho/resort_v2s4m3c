package dev.mvc.categrp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategrpCont {
  @Autowired
  @Qualifier("dev.mvc.categrp.CategrpProc")
  private CategrpProcInter categrpProc;

  public CategrpCont() {
    System.out.println("--> CategrpCont created.");
  }

  /**
   * 등록폼 http://localhost:9090/resort/categrp/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/categrp/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/categrp/create"); // /webapp/categrp/create.jsp

    return mav; // forward
  }

  /**
   * 등록 처리 http://localhost:9090/resort/categrp/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/categrp/create.do", method = RequestMethod.POST)
  public ModelAndView create(CategrpVO categrpVO) {
    // request.setAttribute("categrpVO", categrpVO) 자동 실행

    ModelAndView mav = new ModelAndView();
    mav.setViewName("/categrp/create_msg"); // /webapp/categrp/create_msg.jsp

    int cnt = this.categrpProc.create(categrpVO); // 등록 처리
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)

    return mav; // forward
  }

  /**
   * 목록 http://localhost:9090/resort/categrp/list.do
   * 
   * @return
   */
  @RequestMapping(value = "/categrp/list.do", method = RequestMethod.GET)
  public ModelAndView list() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/categrp/list"); // /webapp/categrp/list.jsp

    // List<CategrpVO> list = this.categrpProc.list_categrpno_asc();
    List<CategrpVO> list = this.categrpProc.list_seqno_asc();
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 조회 + 수정폼 http://localhost:9090/resort/categrp/read_update.do
   * 
   * @return
   */
  @RequestMapping(value = "/categrp/read_update.do", method = RequestMethod.GET)
  public ModelAndView read_update(int categrpno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/categrp/read_update"); // /webapp/categrp/read_update.jsp

    CategrpVO categrpVO = this.categrpProc.read(categrpno);
    mav.addObject("categrpVO", categrpVO);

    List<CategrpVO> list = this.categrpProc.list_seqno_asc();
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 수정 처리
   * 
   * @param categrpVO
   * @return
   */
  @RequestMapping(value = "/categrp/update.do", method = RequestMethod.POST)
  public ModelAndView update(CategrpVO categrpVO) {
    // CategrpVO categrpVO <FORM> 태그의 값으로 자동 생성됨.
    // request.setAttribute("categrpVO", categrpVO); 자동 실행

    ModelAndView mav = new ModelAndView();

    int cnt = this.categrpProc.update(categrpVO);
    mav.addObject("cnt", cnt); // request에 저장

    mav.setViewName("/categrp/update_msg"); // webapp/categrp/update_msg.jsp

    return mav;
  }

  /**
   * 삭제폼 http://localhost:9090/resort/categrp/read_delete.do
   * @return
   */
  @RequestMapping(value = "/categrp/read_delete.do", method = RequestMethod.GET)
  public ModelAndView read_delete(int categrpno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/categrp/read_delete"); // /webapp/categrp/read_delete.jsp

    CategrpVO categrpVO = this.categrpProc.read(categrpno);
    mav.addObject("categrpVO", categrpVO);

    List<CategrpVO> list = this.categrpProc.list_seqno_asc();
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 삭제 처리
   * @param categrpno
   * @return
   */
  @RequestMapping(value = "/categrp/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(int categrpno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.categrpProc.delete(categrpno);
    mav.addObject("cnt", cnt); // request에 저장

    mav.setViewName("/categrp/delete_msg"); // /webapp/categrp/delete_msg.jsp

    return mav;
  }
 
  // http://localhost:9090/resort/categrp/update_seqno_up.do?categrpno=1
  // http://localhost:9090/resort/categrp/update_seqno_up.do?categrpno=1000
  /**
   * 우선순위 상향 up 10 ▷ 1
   * @param categrpno 카테고리 번호
   * @return
   */
  @RequestMapping(value="/categrp/update_seqno_up.do", 
                              method=RequestMethod.GET )
  public ModelAndView update_seqno_up(int categrpno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.categrpProc.update_seqno_up(categrpno);
    mav.addObject("cnt", cnt); // request에 저장
    
    CategrpVO categrpVO = this.categrpProc.read(categrpno);
    mav.addObject("categrpVO", categrpVO);
    

    mav.setViewName("/categrp/update_seqno_up_msg"); // /categrp/update_seqno_up_msg.jsp

    return mav;
  }
  
  // http://localhost:9090/resort/categrp/update_seqno_down.do?categrpno=1
  // http://localhost:9090/resort/categrp/update_seqno_down.do?categrpno=1000
  /**
   * 우선순위 하향 up 1 ▷ 10
   * @param categrpno 카테고리 번호
   * @return
   */
  @RequestMapping(value="/categrp/update_seqno_down.do", 
                              method=RequestMethod.GET )
  public ModelAndView update_seqno_down(int categrpno) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.categrpProc.update_seqno_down(categrpno);
    mav.addObject("cnt", cnt); // request에 저장

    CategrpVO categrpVO = this.categrpProc.read(categrpno);
    mav.addObject("categrpVO", categrpVO);
    
    mav.setViewName("/categrp/update_seqno_down_msg"); // /categrp/update_seqno_down_msg.jsp 

    return mav;
  }
  
  /**
   * 출력모드 변경
   * 
   * @param categrpVO
   * @return
   */
  @RequestMapping(value = "/categrp/update_visible.do", method = RequestMethod.GET)
  public ModelAndView update_visible(CategrpVO categrpVO) {
    ModelAndView mav = new ModelAndView();

    // System.out.println("--> categrpno: " + categrpVO.getCategrpno());
    // System.out.println("--> visible: " + categrpVO.getVisible());
        
    int cnt = this.categrpProc.update_visible(categrpVO);
    mav.addObject("cnt", cnt); // request에 저장

    mav.setViewName("redirect:/categrp/list.do"); // request 객체가 전달이 안됨. 

    return mav;
  }
  
}



