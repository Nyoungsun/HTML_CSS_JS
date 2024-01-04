<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign up</title>
</head>
<meta charset="UTF-8">
<body>
	<form name="writeForm" id="writeForm" method="post" action="/miniPoject_jQuery/member/write.do">
		<table border="1" cellspacing="0" cellpadding="5">
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" id="name" style="width: 100px;"
					placeholder="이름 입력">
					<div class="check" id="checkNameDiv"></div>
				</td>
			</tr>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="id" id="id" style="width: 150px;"
					placeholder="아이디 입력" >
					<input type="button" value="중복확인" id="checkIdBtn" >
					<div class="check" id="checkIdDiv"></div>
					<input type="text" name="idCheck" id="idCheck" value="0"></td>
			</tr>
			<tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="pwd" id="pwd">
					<div class="check" id="checkPwdDiv"></div></td>
			</tr>
			<tr>
				<th>재확인</th>
				<td><input type="password" name="pw_check" id="pw_check">
				<div class="check" id="checkRePwDiv"></div></td>
			</tr>
			<tr>
				<th>성별</th>
				<td><input type="radio" name="gender" id="man" value="0">
					<label for="man">남자</label> &nbsp; 
					<input type="radio" name="gender" id="woman" value="1"> 
					<label for="woman">여자</label>
					<div class="check" id="checkGenderDiv" ></div>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email1" style="width: 100px;">
					@ <input type="text" name="email2" style="width: 90px;"> 
					<select name="mail_select" style="width: 110px;" onChange="select()">
						<option value="self" selected>직접입력</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="nate.com">nate.com</option>
				</select>
				<div class="check" id="checkEmailDiv"></div></td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td><select name="tel1" style="width: 80px;">
						<option value="010" selected>010</option>
						<option value="011">011</option>
						<option value="019">019</option>
						<option value="070">070</option>
				</select> 
				- <input type="text" name="tel2" style="width: 70px;"> 
				- <input type="text" name="tel3" style="width: 70px;">
			      <div class="check" id="checktelDiv"></div></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type="text" name="zipcode" id="d_zipcode"
					placeholder="우편번호" size="5" readonly> 
					<input type="button" value="우편번호검색" onClick="search()"><br>
					<input type="text" name="addr1" id="d_addr1" style="width: 350px;"
					placeholder="주소" readonly><br> 
					<input type="text" name="addr2" id="d_addr2" style="width: 350px;" 
					placeholder="상세주소">
					<div class="check"  id="checkaddrDiv"></div>
				</td>
			</tr>
			</table>
			<br>
			<div id="btnDiv">
			<input type="button" value="회원가입" id="writeBtn"> 
			<input type="reset" value="다시작성">
			</div>

	</form>
	<script type="text/javascript" src="http://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="../js/member.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

</body>
</html>



<!-- function checkId() {
	if (document.writeForm.id.value == "") {
		document.getElementById("checkIdDiv").innerText = "먼저 아이디를 입력하세요."
	} else {
		var url = "/mvcMember/member/checkId.do?id=" + document.writeForm.id.value;
		window.open(url, "checkId",
			"width=300 height=150 left=900 top=200"); //같은 이름은 하나만 열린다
	}
}

function inputId() {
	document.writeForm.idCheck.value = "0";
} -->