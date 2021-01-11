package dev.mvc.attachfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.contents.ContentsProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class AttachfileCont {
  @Autowired
  @Qualifier("dev.mvc.attachfile.AttachfileProc")
  private AttachfileProcInter attachfileProc;
  
  public AttachfileCont(){
    System.out.println("--> AttachfileCont created.");
  }
  
  //http://localhost:9090/resort/attachfile/create.do
  /**
    * ��� ��
    * http://localhost:9090/resort/attachfile/create.do  X
    * http://localhost:9090/resort/attachfile/create.do?cateno=2&contentsno=1  O
    * @return
    */
  @RequestMapping(value="/attachfile/create.do", method=RequestMethod.GET )
  public ModelAndView create(int contentsno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/attachfile/create"); // webapp/attachfile/create.jsp
     
    return mav;
  }
  
  /**
   * ��� ó��
   * @param ra
   * @param request
   * @param attachfileVO
   * @param categrpno
   * @return
   */
  @RequestMapping(value = "/attachfile/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, 
                                           AttachfileVO attachfileVO, int cateno) {
    // System.out.println("--> categrpno: " + categrpno);
    
    ModelAndView mav = new ModelAndView();
    // ---------------------------------------------------------------
    // ���� ���� �ڵ� ����
    // ---------------------------------------------------------------
    int contentsno = attachfileVO.getContentsno(); // �θ�� ��ȣ
    String fname = ""; // ���� ���ϸ�
    String fupname = ""; // ���ε�� ���ϸ�
    long fsize = 0;  // ���� ������
    String thumb = ""; // Preview �̹���
    int upload_count = 0; // ����ó���� ���ڵ� ����
    
    String upDir = Tool.getRealPath(request, "/attachfile/storage");
    
    // ���� ������ ����� fnamesMF ��ü�� ������.
    List<MultipartFile> fnamesMF = attachfileVO.getFnamesMF();
    
    int count = fnamesMF.size(); // ���� ���� ����
    if (count > 0) {
      for (MultipartFile multipartFile:fnamesMF) { // ���� ����, 1���̻� ���� ó��
        fsize = multipartFile.getSize();  // ���� ũ��
        if (fsize > 0) { // ���� ũ�� üũ
          fname = multipartFile.getOriginalFilename(); // ���� ���ϸ�
          fupname = Upload.saveFileSpring(multipartFile, upDir); // ���� ����, ���ε�� ���ϸ�
          
          if (Tool.isImage(fname)) { // �̹������� �˻�
            thumb = Tool.preview(upDir, fupname, 200, 150); // thumb �̹��� ����
          }
        }
        AttachfileVO vo = new AttachfileVO();
        vo.setContentsno(contentsno);
        vo.setFname(fname);
        vo.setFupname(fupname);
        vo.setThumb(thumb);
        vo.setFsize(fsize);
        
        // ���� 1�� ��� ���� dbms ����, ������ 20���̸� 20���� record insert.
        upload_count = upload_count + attachfileProc.create(vo); 
      }
    }    
    // -----------------------------------------------------
    // ���� ���� �ڵ� ����
    // -----------------------------------------------------
    
    mav.addObject("contentsno", contentsno); // redirect parameter ����
    mav.addObject("cateno", cateno); // redirect parameter ����
    mav.addObject("upload_count", upload_count); // redirect parameter ����
    mav.addObject("url", "create_msg"); // create_msg.jsp, redirect parameter ����
    
    mav.setViewName("redirect:/attachfile/msg.do"); // ���ΰ�ħ ����
    
    return mav;
  }
  
  /**
   * ���ΰ�ħ�� �����ϴ� �޽��� ���
   * @param memberno
   * @return
   */
  @RequestMapping(value="/attachfile/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();
    
    // ��� ó�� �޽���: create_msg --> /attachfile/create_msg.jsp
    // ���� ó�� �޽���: update_msg --> /attachfile/update_msg.jsp
    // ���� ó�� �޽���: delete_msg --> /attachfile/delete_msg.jsp
    mav.setViewName("/attachfile/" + url); // forward
    
    return mav; // forward
  }
  
  /**
   * ���
   * http://localhost:9090/ojt/attachfile/list.do
   * 
   * @return
   */
  @RequestMapping(value = "/attachfile/list.do", method = RequestMethod.GET)
  public ModelAndView list() {
    ModelAndView mav = new ModelAndView();

    List<AttachfileVO> list = attachfileProc.list();
    mav.addObject("list", list);

    mav.setViewName("/attachfile/list");

    return mav;
  }
  
  /**
   * ÷�� ���� 1�� ���� ó��
   * 
   * @return
   */
  @RequestMapping(value = "/attachfile/delete.do", 
                             method = RequestMethod.POST)
  public ModelAndView delete_proc(HttpServletRequest request,
                                                int attachfileno) {
    ModelAndView mav = new ModelAndView();

    // ������ ���� ������ �о��.
    AttachfileVO attachfileVO = attachfileProc.read(attachfileno);
    
    String upDir = Tool.getRealPath(request, "/attachfile/storage"); // ���� ���
    Tool.deleteFile(upDir, attachfileVO.getFupname()); // Folder���� 1���� ���� ����
    Tool.deleteFile(upDir, attachfileVO.getThumb()); // 1���� Thumb ���� ����
    
    // DBMS���� 1���� ���� ����
    attachfileProc.delete(attachfileno);
        
    List<AttachfileVO> list = attachfileProc.list(); // ��� ���� ��ħ
    mav.addObject("list", list);
    
    mav.setViewName("redirect:/attachfile/list.do"); 

    return mav;
  }
  
}

