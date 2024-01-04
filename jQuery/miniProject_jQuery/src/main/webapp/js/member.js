$('#id').keydown(function(){
	$('#idCheck').val('0');
});

$('#writeBtn').click(function() {
	$('#checkNameDiv').empty();
	$('#checkIdDiv').empty();
	$('#checkPwdDiv').empty();
	$('#checkRePwDiv').empty();

	if ($('#name').val() == '') {
		$('#checkNameDiv').text('이름을 입력하세요');
		$('#name').focus();
	} else if ($('#id').val() == '') {
		$('#checkIdDiv').text('아이디를 입력하세요');
		$('#id').focus();
	} else if ($('#pwd').val() == '') {
		$('#checkPwdDiv').text('비밀번호를 입력하세요');
		$('#pwd').focus();
	} else if ($('#pw_check').val() == '') {
		$('#checkRePwDiv').text('비밀번호 재확인을 입력하세요');
		$('#pw_check').focus();
	} else if ($('#pwd').val() != $('#pw_check').val()) {
		$('#checkRePwDiv').text('비밀번호 재확인이 맞지않습니다.');
		$('#pw_check').focus();
	} else if ($('#idCheck').val() == '0') {
		$('#checkIdDiv').text('아이디 중복확인을 하세요');
		$('#id').focus();
	} else {
		//$('#writeForm').submit(); //새로운 페이지로 열어줌
		$.ajax({				    //현재창에서
			type: 'post',
			url: '/miniProject_jQuery/member/write.do',
			data: $('#writeForm').serialize(), //form안에 있는 데이터 값을 문자열 형식으로 가져온다.
			dataType: 'text',
			success: function(data) {
				data = data.trim();
				if (data == 'Ok') {
					alert('회원가입 성공');
					location.href = '../index.jsp';
				} else {
					alert('회원가입 실패');
				}
			},
			error: function(err) {
				console.log(err);
			}

		});
	}

});

$('#checkIdBtn').click(function() { //여기서 $는 함수를 의미
	$('#checkIdDiv').empty();

	if ($('#id').val() == '') {
		$('#checkIdDiv').text('먼저 아이디를 입력하세요');
		$('#id').focus();
	}
	else {
		$.ajax({ //여기서 $는 'jquery' 라는 단어를 대신한다.
			type: 'post',
			url: '/miniProject_jQuery/member/checkId.do',
			data: 'id=' + $('#id').val(), //서버로 보낼 데이터
			dataType: 'text',			  //서버로부터 받는 자료형: text, xml, html, json
			success: function(data) {     //반드시 여기로 반환됨
				data = data.trim();
				if (data == 'Ok') {
					alert("사용 가능");
				} else if (data == 'Fail') {
					alert("사용 불가능");
				}
				$('#idCheck').val('1');
			},
			error: function(err) {
				console.log(err);
			}
		});
	} 
});

function select() {
	if (document.writeForm.mail_select.value == 'self') {
		document.writeForm.email2.readOnly = false;
		document.writeForm.email2.value = '';
		document.writeForm.email2.focus();
	}
	else {
		document.writeForm.email2.readOnly = true;
		document.writeForm.email2.value = document.writeForm.mail_select.value;
	}
}

function search() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}
			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById("d_zipcode").value = data.zonecode;
			document.getElementById("d_addr1").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("d_addr2").focus();
		}
	}).open();
}