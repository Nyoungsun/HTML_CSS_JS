
$(function() {
	$.ajax({
		type: 'post',
		url: '/miniProject_jQuery/board/getBoardView.do',
		data: 'seq=' + $('#seq').val(), //GET 방식처럼 작성
		dataType: 'json', //객체 못 받음 → 객체를 JSON으로 변환 후 가져온다
		success: function(data) {
			//alert(JSON.stringify(data)); JSON 데이터 확인
			$('#subject').text(data.subject);
			$('#num').text(data.seq);
			$('#id').text(data.id);
			$('#hit').text(data.hit);
			$('#prewrap').text(data.content);

			if ($('#session_id').val() == data.id) {
				$('#session_btn').show();	
			} else {
				$('#session_btn').hide();	
			}
		},
		error: function(err) {
			console.log(err);
		}
	});
});

//글 수정 폼
$('#boardUpdateFormBtn').click(function(){ 
	$('#boardViewForm').attr('action', '/miniProject_jQuery/board/boardUpdateForm.do');
	$('#boardViewForm').submit();
});

//답글 폼
$('#boardReplyFormBtn').click(function(){ 
	$('#boardViewForm').attr('action', '/miniProject_jQuery/board/boardReplyForm.do');
	$('#boardViewForm').submit();
});