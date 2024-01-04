<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<c:if test="${sessionScope.id == null }">
		<input type="button" value="로그인"
			onclick="location.href='/miniProject_jQuery/member/loginForm.do'">
		<input type="button" value="회원가입"
			onclick="location.href='/miniProject_jQuery/member/writeForm.do'">
	</c:if>

	<c:if test="${id != null }">
		<h3>${id }님 로그인</h3>
		<input type="button" value="로그아웃" id="logoutBtn">
	</c:if>
</div>
<!-- CDN 방식 -->
<script type="text/javascript"
	src="http://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
$('#logoutBtn').click(function() {
	$.ajax({
		type: 'post',
		url: '/miniProject_jQuery/member/logout.do',
		success : function(data) { //반드시 여기로 반환됨
			alert("로그아웃");
			location.href = '/miniProject_jQuery/index.jsp';
		},
		error : function(err) {
			console.log(err);
		}
	});
})
</script>