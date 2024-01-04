<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Writing</title>
</head>
<body>

	<form id="boardWriteForm">
		<table cellpadding="5" cellspacing="0">
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" size="45" id="subject">
					<div id="subjectCheck"></div></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" cols="50" rows="15" id="content"></textarea>
					<div id="contentCheck"></div></td>
			</tr>
		</table>
		<div id="button">
			<input type="button" name="write" value="글쓰기" id="boardWriteBtn">
			<input type="reset" name="write" value="초기화">
		</div>
	</form>
</body>
<script type="text/javascript"> 
 $(function(){
    $('#boardWriteBtn').click(function(){
       $('#subjectCheck').empty();
       $('#contentCheck').empty();
       
       if($('#subject').val() == ''){
          $('#subjectCheck').text('제목을 입력하시오');
          $('#subject').focus();
       }else if($('#content').val() == ''){
          $('#contentCheck').text('내용을 입력하시오');
          $('#content').focus();
       }else{
          $.ajax({
             type:'post',
             url:'/miniProject_jQuery/board/boardWrite.do',
             data: $('#boardWriteForm').serialize(),
             success: function(){
                alert('글작성 완료');
                location.href='/miniProject_jQuery/board/boardList.do?pg=1';
             },
             error: function(err){
                console.log(err);
             }
          });
       }
    });
 });
 </script>

</html>