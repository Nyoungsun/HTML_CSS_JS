<%@page import="board.bean.BoardDTO"%>
<%@page import="board.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
#prewrap {
	white-space: pre-wrap;
	word-break: break-all;
	overflow-y: auto;
	width: 100%;
	height: 100%;
	padding-right: 15px;
}

td, th {
	padding: 12px;
}

#subject {
	font-size: 35px;
	text-align: center;
	width: 100%;
	word-break: break-all;
}
</style>

</head>
<body>
	<form id="boardViewForm">
		<input type="hidden" name="seq" id="seq" value="${seq }">
		<input type="hidden" name ="pg" id="pg" value="${pg }">
		<input type="text" id="session_id" value="${id }"> <!-- submit 불가하므로 name 필요 X -->
		<table border="1" cellpadding="10" cellspacing="0" frame="hsides"
			rules="rows">
			<tr>
				<td colspan="6"><span id="subject"></span></td>
			</tr>
			<tr>
				<th>글번호</th>
				<td><span id="num"></span></td>
				<th>작성자</th>
				<td><span id="id"></span></td>
				<th>조회수</th>
				<td><span id="hit"></span></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="6" height='200'>
					<div id='prewrap'></div>
				</td>
			</tr>
		</table>
	</form>
	<div id="center">
		<input type="button" value="목록" onclick="location.href='/miniProject_jQuery/board/boardList.do?pg=${pg}'">
		<span id="session_btn">
		<input type="button" id="boardUpdateFormBtn" value="글수정" >
		<input type="button" id="boardDeleteFormBtn" value="글삭제">
		</span>
		
		<input type="button" id="boardReplyFormBtn" value="답글">
	</div>
	<script src="http://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="boardView.js"></script>
</body>
</html>