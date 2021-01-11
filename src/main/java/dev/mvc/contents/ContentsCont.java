package dev.mvc.contents;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.attachfile.AttachfileProcInter;
import dev.mvc.attachfile.AttachfileVO;
import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVO;
import dev.mvc.categrp.CategrpProcInter;
import dev.mvc.categrp.CategrpVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ContentsCont {
  @Autowired
  @Qualifier("dev.mvc.categrp.CategrpProc")
  private CategrpProcInter categrpProc;
  
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;

  @Autowired
  @Qualifier("dev.mvc.attachfile.AttachfileProc")
  private AttachfileProcInter attachfileProc;
  
  public ContentsCont() {
    System.out.println("--> ContentsCont created.");
  }
  
  /**
   * 등록폼 http://localhost:9090/resort/contents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/contents/create"); // /webapp/categrp/create.jsp
    // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
    // mav.addObject("content", content);

    return mav; // forward
  }
  
  /**
   * 등록 처리 http://localhost:9090/resort/contents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, ContentsVO contentsVO) {
    // System.out.println("IP: " + contentsVO.getIp());  // Oracle은 "" -> null로 인식
    // System.out.println("IP: " + request.getRemoteAddr());     
    contentsVO.setIp(request.getRemoteAddr());
    
    ModelAndView mav = new ModelAndView();
    // -------------------------------------------------------------------
    // 파일 전송 코드 시작
    // -------------------------------------------------------------------
    String file1 = "";     // main image
    String thumb1 = ""; // preview image
        
    String upDir = Tool.getRealPath(request, "/contents/storage/main_images"); // 절대 경로
    
    // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택" multiple="multiple">
    MultipartFile mf = contentsVO.getFile1MF();
    
    long size1 = mf.getSize();  // 파일 크기
    if (size1 > 0) { // 파일 크기 체크
      // mp3 = mf.getOriginalFilename(); // 원본 파일명, spring.jpg
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1 = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
        thumb1 = Tool.preview(upDir, file1, 200, 150); 
      }
      
    }    
    
    contentsVO.setFile1(file1);
    contentsVO.setThumb1(thumb1);
    contentsVO.setSize1(size1);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
    
    int cnt = this.contentsProc.create(contentsVO);
    if (cnt == 1) {
      cateProc.increaseCnt(contentsVO.getCateno());
    }
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)

    // <c:import> 정상 작동안됨.
    // mav.setViewName("/contents/create_msg"); // /webapp/contents/create_msg.jsp
    
    mav.addObject("url", "create_msg"); // create_msg.jsp, redirect parameter 적용
    mav.setViewName("redirect:/contents/msg.do"); 
    
    return mav; // forward
  }
  
  /**
   * 목록 http://localhost:9090/resort/contents/list_all.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/list_all.do", method = RequestMethod.GET)
  public ModelAndView list() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/contents/list_all"); // /webapp/contents/list_all.jsp

    List<ContentsVO> list = this.contentsProc.list_all();
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 카테고리별 목록 http://localhost:9090/resort/contents/list.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/list.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno(int cateno) {
    ModelAndView mav = new ModelAndView();
    // /webapp/contents/list_by_cateno.jsp
    // mav.setViewName("/contents/list_by_cateno");
    
    // 테이블 이미지 기반, /webapp/contents/list_by_cateno.jsp
    mav.setViewName("/contents/list_by_cateno_table_img1");

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    mav.addObject("categrpVO", categrpVO);
    
    List<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
    mav.addObject("list", list);

    return mav; // forward
  }

  /**
   * 카테고리별 목록 http://localhost:9090/resort/contents/list_by_cateno_grid1.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/list_by_cateno_grid1.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno_grid1(int cateno) {
    ModelAndView mav = new ModelAndView();
    
    // 테이블 이미지 기반, /webapp/contents/list_by_cateno_grid1.jsp
    mav.setViewName("/contents/list_by_cateno_grid1");

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    mav.addObject("categrpVO", categrpVO);
    
    List<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
    mav.addObject("list", list);

    return mav; // forward
  }
  
  // http://localhost:9090/resort/contents/read.do
  /**
   * 전체 목록
   * @return
   */
  @RequestMapping(value="/contents/read.do", method=RequestMethod.GET )
  public ModelAndView read(int contentsno) {
    ModelAndView mav = new ModelAndView();

    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO); // request.setAttribute("contentsVO", contentsVO);

    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO); 

    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    mav.addObject("categrpVO", categrpVO); 
    
    // 첨부 파일 목록
    List<AttachfileVO> attachfile_list = this.attachfileProc.list_by_contentsno(contentsno);
    mav.addObject("attachfile_list", attachfile_list);
    System.out.println("--> 첨부 파일 갯수: " + attachfile_list.size());
    
    // mav.setViewName("/contents/read"); // /webapp/contents/read.jsp
    // mav.setViewName("/contents/read_img"); // /webapp/contents/read_img.jsp
    mav.setViewName("/contents/read_img_attachfile"); // /webapp/contents/read_img_attachfile.jsp    
    return mav;
  }
  
  // http://localhost:9090/resort/contents/update.do
  /**
   * 수정 폼
   * @return
   */
  @RequestMapping(value="/contents/update.do", method=RequestMethod.GET )
  public ModelAndView update(int contentsno) {
    ModelAndView mav = new ModelAndView();
    
    ContentsVO contentsVO = this.contentsProc.read_update(contentsno); // 수정용 읽기
    mav.addObject("contentsVO", contentsVO); // request.setAttribute("contentsVO", contentsVO);
    
    mav.setViewName("/contents/update"); // webapp/contents/update.jsp
    
    return mav;
  }
  
  // http://localhost:9090/resort/contents/update.do
  /**
   * 수정 처리
   * @param contentsVO
   * @return
   */
  @RequestMapping(value="/contents/update.do", method=RequestMethod.POST )
  public ModelAndView update(ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    // mav.addObject("cateVO", cateVO); // 전달안됨.
    mav.addObject("cate_name", cateVO.getName());
    mav.addObject("cateno", cateVO.getCateno());

    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    // mav.addObject("categrpVO", categrpVO); // 전달안됨.
    mav.addObject("categrp_name", categrpVO.getName());
    
    mav.addObject("contentsno", contentsVO.getContentsno());
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsVO.getContentsno());
    hashMap.put("passwd", contentsVO.getPasswd());
    
    int passwd_cnt = 0; // 패스워드 일치 레코드 갯수
    int cnt = 0;             // 수정된 레코드 갯수 
    
    passwd_cnt = this.contentsProc.passwd_check(hashMap);
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우 글 수정
      cnt = this.contentsProc.update(contentsVO);
    }

    mav.addObject("cnt", cnt); // request에 저장
    mav.addObject("passwd_cnt", passwd_cnt); // request에 저장
        
    mav.setViewName("/contents/update_msg"); // webapp/contents/update_msg.jsp
    
    return mav;
  }
  
     
  // http://localhost:9090/resort/contents/delete.do
  /**
   * 삭제 폼
   * @return
   */
  @RequestMapping(value="/contents/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int contentsno) {
    ModelAndView mav = new ModelAndView();
    
    ContentsVO contentsVO = this.contentsProc.read_update(contentsno); // 수정용 읽기
    mav.addObject("contentsVO", contentsVO); // request.setAttribute("contentsVO", contentsVO);
    
    mav.setViewName("/contents/delete"); // webapp/contents/delete.jsp
    
    return mav;
  }
  
  // http://localhost:9090/resort/contents/delete.do
  /**
   * 삭제 처리 +  파일 삭제
   * @param contentsVO
   * @return
   */
  @RequestMapping(value="/contents/delete.do", method=RequestMethod.POST )
  public ModelAndView delete(HttpServletRequest request,
                                           int cateno, 
                                           int contentsno, 
                                           String passwd) {
    ModelAndView mav = new ModelAndView();
   
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    String title = contentsVO.getTitle();
    mav.addObject("title", title);
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsno);
    hashMap.put("passwd", passwd);
    
    int passwd_cnt = 0; // 패스워드 일치 레코드 갯수
    int cnt = 0;             // 수정된 레코드 갯수 
    
    passwd_cnt = this.contentsProc.passwd_check(hashMap);
    boolean sw = false;
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우 글 수정
      cnt = this.contentsProc.delete(contentsno);
      if (cnt == 1) {
        cateProc.decreaseCnt(cateno);
      }
      
      String upDir = Tool.getRealPath(request, "/contents/storage/main_images"); // 절대 경로
      sw = Tool.deleteFile(upDir, contentsVO.getFile1());  // Folder에서 1건의 파일 삭제
      sw = Tool.deleteFile(upDir, contentsVO.getThumb1());  // Folder에서 1건의 파일 삭제

    }

    mav.addObject("cnt", cnt); // request에 저장
    mav.addObject("passwd_cnt", passwd_cnt); // request에 저장
        
    mav.setViewName("/contents/delete_msg"); // webapp/contents/delete_msg.jsp
    
    return mav;
  }

  /**
   * 메인 이미지 등록 폼 http://localhost:9090/resort/contents/img_create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/img_create.do", method = RequestMethod.GET)
  public ModelAndView img_create(int contentsno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/contents/img_create"); // /webapp/contents/img_create.jsp

    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO); 

    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    mav.addObject("categrpVO", categrpVO); 
    
    return mav; // forward
  }

  /**
   * 메인 이미지 등록 처리 http://localhost:9090/resort/contents/img_create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/img_create.do", method = RequestMethod.POST)
  public ModelAndView img_create(HttpServletRequest request, ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsVO.getContentsno());
    hashMap.put("passwd", contentsVO.getPasswd());
    
    int passwd_cnt = 0; // 패스워드 일치 레코드 갯수
    int cnt = 0;             // 수정된 레코드 갯수 
    
    passwd_cnt = this.contentsProc.passwd_check(hashMap);
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우 파일 업로드
      // -------------------------------------------------------------------
      // 파일 전송 코드 시작
      // -------------------------------------------------------------------
      String file1 = "";     // main image
      String thumb1 = ""; // preview image
          
      String upDir = Tool.getRealPath(request, "/contents/storage/main_images"); // 절대 경로
      // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택" multiple="multiple">
      MultipartFile mf = contentsVO.getFile1MF();
      long size1 = mf.getSize();  // 파일 크기
      if (size1 > 0) { // 파일 크기 체크
        // mp3 = mf.getOriginalFilename(); // 원본 파일명, spring.jpg
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1 = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1, 200, 150); 
        }
      }    
      
      contentsVO.setFile1(file1);
      contentsVO.setThumb1(thumb1);
      contentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
      
      mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno());
      
      cnt = this.contentsProc.img_create(contentsVO);
      // mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
    } else {
      mav.setViewName("/contents/update_msg"); // webapp/contents/update_msg.jsp
      
    }

    mav.addObject("cnt", cnt); // request에 저장
    mav.addObject("passwd_cnt", passwd_cnt); // request에 저장
            
    return mav;    
  }
  
  /**
   * 메인 이미지 삭제/수정 폼 http://localhost:9090/resort/contents/img_update.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/img_update.do", method = RequestMethod.GET)
  public ModelAndView img_update(int contentsno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/contents/img_update"); // /webapp/contents/img_update.jsp

    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO); 

    CategrpVO categrpVO = this.categrpProc.read(cateVO.getCategrpno());
    mav.addObject("categrpVO", categrpVO); 
    
    return mav; // forward
  }
  
  /**
   * 메인 이미지 삭제 처리 http://localhost:9090/resort/contents/img_delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/img_delete.do", method = RequestMethod.POST)
  public ModelAndView img_delete(HttpServletRequest request,
                                                 int contentsno, int cateno, String passwd) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsno);
    hashMap.put("passwd", passwd);
    
    int passwd_cnt = 0; // 패스워드 일치 레코드 갯수
    int cnt = 0;             // 수정된 레코드 갯수 
    
    passwd_cnt = this.contentsProc.passwd_check(hashMap);
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우 파일 업로드
      // -------------------------------------------------------------------
      // 파일 삭제 코드 시작
      // -------------------------------------------------------------------
      // 삭제할 파일 정보를 읽어옴.
      ContentsVO contentsVO = contentsProc.read(contentsno);
      // System.out.println("file1: " + contentsVO.getFile1());
      
      String file1 = contentsVO.getFile1().trim();
      String thumb1 = contentsVO.getThumb1().trim();
      long size1 = contentsVO.getSize1();
      boolean sw = false;
      
      String upDir = Tool.getRealPath(request, "/contents/storage/main_images"); // 절대 경로
      sw = Tool.deleteFile(upDir, contentsVO.getFile1());  // Folder에서 1건의 파일 삭제
      sw = Tool.deleteFile(upDir, contentsVO.getThumb1());  // Folder에서 1건의 파일 삭제
      // System.out.println("sw: " + sw);
      
      file1 = "";
      thumb1 = "";
      size1 = 0;
      
      contentsVO.setFile1(file1);
      contentsVO.setThumb1(thumb1);
      contentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 삭제 종료 시작
      // -------------------------------------------------------------------
      
      mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsno);
      
      cnt = this.contentsProc.img_delete(contentsVO);
      // mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
    } else {
      mav.setViewName("/contents/update_msg"); // webapp/contents/update_msg.jsp
      
    }

    mav.addObject("cnt", cnt); // request에 저장
    mav.addObject("passwd_cnt", passwd_cnt); // request에 저장
            
    return mav;    
  }
  
  /**
   * 메인 이미지 수정 처리 http://localhost:9090/resort/contents/img_update.do
   * 기존 이미지 삭제후 새로운 이미지 등록(수정 처리)
   * @return
   */
  @RequestMapping(value = "/contents/img_update.do", method = RequestMethod.POST)
  public ModelAndView img_update(HttpServletRequest request, ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsVO.getContentsno());
    hashMap.put("passwd", contentsVO.getPasswd());
    
    int passwd_cnt = 0; // 패스워드 일치 레코드 갯수
    int cnt = 0;             // 수정된 레코드 갯수 
    
    passwd_cnt = this.contentsProc.passwd_check(hashMap);
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우 파일 업로드
      // -------------------------------------------------------------------
      // 파일 삭제 코드 시작
      // -------------------------------------------------------------------
      // 삭제할 파일 정보를 읽어옴.
      ContentsVO vo = contentsProc.read(contentsVO.getContentsno());
      // System.out.println("file1: " + contentsVO.getFile1());
      
      String file1 = vo.getFile1().trim();
      String thumb1 = vo.getThumb1().trim();
      long size1 = 0;
      boolean sw = false;
      
      String upDir = Tool.getRealPath(request, "/contents/storage/main_images"); // 절대 경로
      sw = Tool.deleteFile(upDir, contentsVO.getFile1());  // Folder에서 1건의 파일 삭제
      sw = Tool.deleteFile(upDir, contentsVO.getThumb1());  // Folder에서 1건의 파일 삭제
      // System.out.println("sw: " + sw);
      // -------------------------------------------------------------------
      // 파일 삭제 종료 시작
      // -------------------------------------------------------------------
      
      // -------------------------------------------------------------------
      // 파일 전송 코드 시작
      // -------------------------------------------------------------------
      // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택" multiple="multiple">
      MultipartFile mf = contentsVO.getFile1MF();
      size1 = mf.getSize();  // 파일 크기
      if (size1 > 0) { // 파일 크기 체크
        // mp3 = mf.getOriginalFilename(); // 원본 파일명, spring.jpg
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1 = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1, 200, 150); 
        }
      }    
      
      contentsVO.setFile1(file1);
      contentsVO.setThumb1(thumb1);
      contentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------

      mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno());
      
      cnt = this.contentsProc.img_create(contentsVO);
      // mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
    } else {
      mav.setViewName("/contents/update_msg"); // webapp/contents/update_msg.jsp
      
    }

    mav.addObject("cnt", cnt); // request에 저장
    mav.addObject("passwd_cnt", passwd_cnt); // request에 저장
            
    return mav;    
  }
  
  /**
   * 새로고침을 방지하는 메시지 출력
   * @return
   */
  @RequestMapping(value="/contents/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();
    
    // 등록 처리 메시지: create_msg --> /contents/create_msg.jsp
    // 수정 처리 메시지: update_msg --> /contents/update_msg.jsp
    // 삭제 처리 메시지: delete_msg --> /contents/delete_msg.jsp
    mav.setViewName("/contents/" + url); // forward
    
    return mav; // forward
  }
  
}





