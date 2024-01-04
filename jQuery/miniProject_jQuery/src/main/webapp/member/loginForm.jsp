<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign in</title>
<style>
table {
	margin-top: 300px;
	margin-right: auto;
	margin-left: auto;
}

#loginResult {
	color: red;
}
</style>
</head>
<body>
	<form name="loginform" id="loginform">
		<table cellpadding="7">
			<tr>
				<th>ID</th>
				<td><input type="text" id="id" name="id" placeholder="아이디 입력">
					<div id=idDiv></div></td>
				</td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" id="pw" name="pw"
					placeholder="비밀번호 입력">
					<div id=pwdDiv></div></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="로그인"
					id="loginBtn">
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					value="회원가입"
					onClick="location.href='/miniProject_jQuery/member/writeForm.do'">
			</tr>
		</table>
		<br> <br>
		<div id="loginResult"></div>
	</form>
	<script type="text/javascript" src="../js/jquery-3.6.4.min.js"></script>
	<script type="text/javascript">
		//제이쿼리는 싱글쿼트
		//id: #, class: .
		$('#loginBtn').click(function() { //여기서 $는 함수를 의미
			$('#idDiv').empty();
			$('#pwdDiv').empty();

			if ($('#id').val() == '') {
				$('#idDiv').text('아이디를 입력하세요');
				$('#id').focus();
			} else if ($('#pw').val() == '') {
				$('#pwdDiv').text('비밀번호를 입력하세요');
				$('#pw').focus();
			} else {
				$.ajax({ //여기서 $는 'jquery' 라는 단어를 대신한다.
					type : 'post',
					url : '/miniProject_jQuery/member/login.do',
					data : 'id=' + $('#id').val() + '&pw=' + $('#pw').val(), //서버로 보낼 데이터
					dataType : 'text', //서버로부터 받는 자료형: text, xml, html, json
					success : function(data) { //반드시 여기로 반환됨
						data = data.trim();
						if (data == 'Ok') {
							location.href = '../index.jsp';
						} else if (data == 'Fail') {
							$('#loginResult').text('아이디 또는 비밀번호가 맞지 않습니다.');
						}
					},
					error : function(err) {
						console.log(err);
					}
				});
			}
		});
	</script>
	<script>
		/* <!-- type="text/javascript" 생략가능 -->
			const form = document.loginform;

			function checkLogin() {
				document.getElementById("id_check").innerText = ""
				document.getElementById("pw_check").innerText = ""

				if (form.login_id.value == "") {
					document.getElementById("id_check").innerText = "아이디를 입력하세요."
					document.getElementById("login_id").focus();
				} else if (form.login_pw.value == "") {
					document.getElementById("pw_check").innerText = "비밀번호를 입력하세요."
					document.getElementById("login_pw").focus();
				} else {
					form.submit();
				}
			}
		 */
	</script>
</body>
</html>