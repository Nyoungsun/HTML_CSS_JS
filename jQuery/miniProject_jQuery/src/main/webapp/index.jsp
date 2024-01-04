<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<style>
html, body {
	width: 100%;
	height: 100%;
}

html {
	overflow-y: scroll;
}

#wrap {
	width: 1100px;
	margin: 0 auto;
}

#header {
	height: 10%;
	text-align: center;
}

#container {
	margin: auto;
	width: 1100px;
	height: 500px;
}

#container:after {
	content: '';
	display: block;
	clear: both;
	float: none;
}

#nav {
	margin-left: 10px;
	width: 25%;
	height: 100%;
	float: left;
	border-right: 1px solid #ccc;
}

#section {
	width: 70%;
	height: 100%;
	float: left;
}

#footer {
	width: 1100px;
	height: 10%;
	border-bottom: 1px solid #ccc;
}

#logo {
	cursor: pointer;
}
</style>

</head>
<body>
	<div id="wrap">
		<div id="header">
			<div id="logo"
				onclick="location.href='/miniProject_jQuery/index.jsp'">로고</div>
			<jsp:include page="./main/menu.jsp" />
		</div>
		<div id="container">
			<div id="nav">
				<%--<%@ include file="" %> --%>
				<jsp:include page="./main/nav.jsp" /></div>
			<div id="section">
				<c:if test="${empty display }">안뇽</c:if>
				<c:if test="${not empty display }">
				<jsp:include page="${display }" /></c:if>
			</div>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>