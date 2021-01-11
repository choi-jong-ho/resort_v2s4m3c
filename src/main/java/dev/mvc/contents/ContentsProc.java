package dev.mvc.contents;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.contents.ContentsProc")
  public class ContentsProc implements ContentsProcInter {
    @Autowired
    private ContentsDAOInter contentsDAO;

    public ContentsProc() {
      System.out.println("--> ContentsProc created.");
    }

    @Override
    public int create(ContentsVO contentsVO) {
       int cnt=this.contentsDAO.create(contentsVO);
      return cnt;
    }

    @Override
    public List<ContentsVO> list_all() {
      List<ContentsVO> list = this.contentsDAO.list_all();
      return list;
    }
    
    @Override
    public List<ContentsVO> list_by_cateno(int cateno) {
      List<ContentsVO> list = this.contentsDAO.list_by_cateno(cateno);
      return list;
    }
    
    @Override
    public ContentsVO read(int contentsno) {
      ContentsVO contentsVO = this.contentsDAO.read(contentsno);
      
      String title = contentsVO.getTitle();
      String content = contentsVO.getContent();
      
      title = Tool.convertChar(title);  // 
      // content = Tool.convertChar(content); // Ckeditor �궗�슜�떆 �궗�슜 遺덇�
      
      contentsVO.setTitle(title);
      contentsVO.setContent(content);
      
      return contentsVO;
    }
    
    @Override
    public ContentsVO read_update(int contentsno) {
      ContentsVO contentsVO = this.contentsDAO.read(contentsno);
      return contentsVO;
    }

    @Override
    public int update(ContentsVO contentsVO) {
      int cnt = this.contentsDAO.update(contentsVO);
      return cnt;
    }

    @Override
    public int passwd_check(HashMap hashMap) {
      int passwd_cnt = this.contentsDAO.passwd_check(hashMap);
      return passwd_cnt;
    }

    @Override
    public int delete(int contentsno) {
      int cnt = this.contentsDAO.delete(contentsno);
      return cnt;
    }

    @Override
    public int img_create(ContentsVO contentsVO) {
      int cnt = this.contentsDAO.update_img(contentsVO);
      return cnt;
    }

    @Override
    public int img_update(ContentsVO contentsVO) {
      int cnt = this.contentsDAO.update_img(contentsVO);
      return cnt;
    }

    @Override
    public int img_delete(ContentsVO contentsVO) {
      int cnt = this.contentsDAO.update_img(contentsVO);
      return cnt;
    }

    @Override
    public int total_count() {
      int cnt = this.contentsDAO.total_count();
      return cnt;
    }

  
}




