//$(document).ready(function(){});
$(function() {
	$.ajax({
		type: 'post',
		url: '/miniProject_jQuery/board/getBoardList.do',
		data: 'pg=' + $('#pg').val(), //JSON - {'pg': $('#pg').val()}
		dataType: 'json', //객체 못 받음 → 객체를 JSON으로 변환 후 가져온다
		success: function(data) {
			$.each(data.list, function(index, items) {
				$('<tr/>').append($('<td/>', {
					align: 'center',
					text: items.seq
				})).append($('<td/>').append($('<a/>', {
					text: items.subject,
					href: '#',
					class: 'subject subjectA_'+items.seq,
				}))).append($('<td/>', {
					align: 'center',
					text: items.id
				})).append($('<td/>', {
					align: 'center',
					text: items.hit
				})).append($('<td/>', {
					align: 'center',
					text: items.logtime
				})).appendTo($('#boardListTable'));
				
				//답글
				for(var i=1; i<items.lev; i++){
					$('.subjectA_'+items.seq).before('&emsp;');	
				}
				
				if(items.pseq != 0) {
					$('.subjectA_'+items.seq).before($('<img/>', {
						src: '../img/reply.gif'
					}));
				}
			});

			//페이징 처리
			$('#boardPagingDiv').html(data.pagingHTML);

			//로그인 여부 확인
			$('.subject').click(function() {
				if ($('#id').val() == '') {
					alert('먼저 로그인하세요');
				} else {
					var seq = $(this).parent().prev().text(); // 글 제목을 클릭하면 해당 글의 글 번호를 가져온다.
					//console.log(seq.prop('tagName'));
					
					var pg = $('#pg').val();
					location.href = "/miniProject_jQuery/board/boardView.do?seq=" + seq + '&pg=' + pg;
				}
			});
		},
		error: function(err) {
			console.log(err);
		}
	});
});