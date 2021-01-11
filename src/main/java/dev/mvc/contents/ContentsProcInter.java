package dev.mvc.contents;

import java.util.HashMap;
import java.util.List;

public interface ContentsProcInter {
  /**
   * ���
   * @param contentsVO
   * @return
   */
  public int create(ContentsVO contentsVO);
  
  /**
   * ��� ī�װ��� ��ϵ� �۸��
   * @return
   */
  public List<ContentsVO> list_all();

  /**
   * Ư�� ī�װ��� ��ϵ� �۸��
   * @return
   */
  public List<ContentsVO> list_by_cateno(int cateno);
  
  /**
   * ��ȸ
   * @param contentsno
   * @return
   */
  public ContentsVO read(int contentsno);
  
  /**
   * ������ ��ȸ
   * @param contentsno
   * @return
   */
  public ContentsVO read_update(int contentsno);
  
  /**
   * ���� ó��
   * @param contentsVO
   * @return
   */
  public int update(ContentsVO contentsVO);
  
  /**
   * �н����� �˻�
   * @param hashMap
   * @return
   */
  public int passwd_check(HashMap hashMap);
  
  /**
   * ����
   * @param contentsno
   * @return
   */
  public int delete(int contentsno);
  
  /**
   * �̹��� ���
   * @param contentsVO
   * @return
   */
  public int img_create(ContentsVO contentsVO);
  
  /**
   * �̹��� ����
   * @param contentsVO
   * @return
   */
  public int img_update(ContentsVO contentsVO);

  /**
   * �̹��� ����
   * @param contentsVO
   * @return
   */
  public int img_delete(ContentsVO contentsVO);
  
  /**
   * ��ü ���ڵ� ����
   * @return
   */
  public int total_count();
  
}








