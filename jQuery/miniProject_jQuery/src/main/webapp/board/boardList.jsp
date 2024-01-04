<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.coyote.http2.Http2AsyncUpgradeHandler"%>
<%@page import="board.bean.BoardPaging"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.bean.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.dao.BoardDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<style>
td {
	word-break: break-all;
	padding: 12px;
}
</style>
</head>
<body>
	<input type="hidden" id="pg" value="${pg }"> <!-- BoardListService에서 받는  세션값 -->
	<input type="hidden" id="id" value="${id }"> <!-- GetBoardListService에서 받는  세션값 -->
	<table id="boardListTable" border="1" cellpadding="5" cellspacing="0"
		frame="hsides" rules="rows">
		<tr>
			<th width="100">글번호</th>
			<th id="subject" width="400">제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>작성일</th>
		</tr>
	</table>

	<div id="boardPagingDiv"></div>

	<script>
		function boardPaging(pg) {
			location.href = "boardList.do?pg=" + pg;
		}

		function sessionCheck(id, seq) {
			if (id == 'null') {
				alert("먼저 로그인하세요.");
				location.href = "../index.jsp"
			} else {
				location.href = "./boardView.jsp?seq=" + seq;
			}
		}
	</script>

	<script type="text/javascript" src="http://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="boardList.js"></script>

</body>
</html>