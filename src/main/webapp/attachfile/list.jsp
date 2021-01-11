<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="../css/style.css" rel="Stylesheet" type="text/css">
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
  $(function() {
    $('#btn_delete').on('click', delete_proc); // 삭제 진행

  });

  function delete_form(attachfileno) {
    $('#btn_delete').attr('data-content', attachfileno); // 삭제할 파일 번호 임시 저장
    
    msg = '파일을 삭제하시겠습니까?<br>파일을 삭제하면 복구 할 수 없습니다.<br>';
    msg += '삭제 진행시 삭제진행 버튼을 클릭하세요.';
    
    $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경
    $('#modal_title').html('파일 삭제'); // 제목 
    $('#modal_content').html(msg);        // 내용
    $('#modal_panel').modal();              // 다이얼로그 출력
    return false;
  }

  function delete_proc() {
    var attachfileno = $('#btn_delete').attr('data-content'); // 삭제할 파일 번호 임시 저장
    var frm = $('#frm')
    $('#attachfileno', frm).val(attachfileno); // frm 폼에서 태그를 검색
    
    frm.submit();
  }
</script> 
</head>
 
<body>
<jsp:include page="/menu/top.jsp" flush='false' />
  <form name='frm' id='frm' method='post' action='./delete.do'>
    <input type='hidden' name='attachfileno' id='attachfileno' value=''>
  </form>
  
  <!-- ********** Modal 알림창 시작 ********** -->
  <div id="modal_panel" class="modal fade"  role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">×</button>
          <h4 class="modal-title" id='modal_title'></h4><!-- 제목 -->
        </div>
        <div class="modal-body">
          <p id='modal_content'></p>  <!-- 내용 -->
        </div>
        <div class="modal-footer">
          <button type="button" id="btn_delete" data-focus="" data-content="" class="btn btn-default" 
                      data-dismiss="modal">삭제 진행</button>
          <button type="button" id="btn_close" data-focus="" class="btn btn-default" 
                      data-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- ********** Modal 알림창 종료 ********** -->

  <DIV class='title_line'>
    전체 첨부 파일
  </DIV>

  <ASIDE class='aside_left'>
    <A href='./list.do'>모든 이미지 파일</A>
  </ASIDE>
  <ASIDE class='aside_right'>
    <A href="javascript:location.reload();">새로고침</A>
<!--     <span class='menu_divide' > | </span> -->

  </ASIDE> 
  <DIV class='menu_line'></DIV>
  
  <div style='width: 100%;'>
    <table class="table table-striped" style='width: 100%;'>
      <colgroup>
        <col style="width: 10%;"></col>
        <col style="width: 15%;"></col>
        <col style="width: 15%;"></col>
        <col style="width: 15%;"></col>
        <col style="width: 15%;"></col>        
        <col style="width: 15%;"></col>
        <col style="width: 15%;"></col>
        
      </colgroup>
      <%-- table 컬럼 --%>
      <thead>
        <tr>
          <th style='text-align: center;'>NO</th>
          <th style='text-align: center;'>파일</th>
          <th style='text-align: center;'>원본<br>파일명</th>
          <th style='text-align: center;'>저장<br>파일명</th>
          <th style='text-align: center;'>Preview<br>파일명</th>
          <th style='text-align: center;'>등록일</th>
          <th style='text-align: center;'>기타</th>
        </tr>
      
      </thead>
      
      <%-- table 내용 --%>
      <tbody>
        <c:forEach var="attachfileVO" items="${list }">
          <c:set var="contentsno" value="${attachfileVO.contentsno }" />
          <c:set var="fname" value="${attachfileVO.fname.toLowerCase() }" />
          
          <tr> 
            <td style='vertical-align: middle; text-align: center;'>${attachfileVO.attachfileno }</td>
            <td style='vertical-align: middle; text-align: center;'>
              <c:choose>
                <c:when test="${fname.endsWith('jpg') || fname.endsWith('png') || fname.endsWith('gif')}">
                  <IMG src="./storage/${attachfileVO.thumb }" style="width: 120px;"> 
                </c:when>
                <c:otherwise> <!-- 이미지가 아닌 일반 파일 -->
                  ${attachfileVO.fname}
                </c:otherwise>
              </c:choose>
            </td> 
            <td style='text-align: center; vertical-align: middle;'>${attachfileVO.fname}</td>
            <td style='text-align: center; vertical-align: middle;'>${attachfileVO.fupname}</td>
            <td style='text-align: center; vertical-align: middle;'>${attachfileVO.thumb}</td>
            <td style='text-align: center; vertical-align: middle;'>${attachfileVO.rdate.substring(0, 10)}</td>
            <td style='text-align: center; vertical-align: middle;'>
              <%-- <a href="./delete.do?attachfileno=${attachfileVO.attachfileno}"><img src="./images/delete.png" title="삭제"></a> --%>
              <A href="javascript: delete_form(${attachfileVO.attachfileno})"><img src="./images/delete.png" title="삭제"></A>
            </td>
          </tr>
        </c:forEach>
        
      </tbody>
    </table>
    <br><br>
  </div>
 
<jsp:include page="/menu/bottom.jsp" flush='false' />
</body>
 
</html>


