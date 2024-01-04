package board.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.control.CommandProcess;

import board.bean.BoardDTO;
import board.bean.BoardPaging;
import board.dao.BoardDAO;

public class GetBoardListService implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		
		// 데이터
		int pg = Integer.parseInt(request.getParameter("pg"));

		// DB
		BoardDAO boardDAO = BoardDAO.getInstance();

		int end = pg * 5;
		int start = end - 4;

		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);

		List<BoardDTO> boardList = boardDAO.boardList(map);
		
		// 페이징 처리
		BoardPaging boardPaging = new BoardPaging();
		boardPaging.setCurrentPage(pg);
		boardPaging.setPageBlock(3);
		boardPaging.setPageSize(5);
		boardPaging.setTotalArticle(boardDAO.totalArticle());
		boardPaging.makePaging();
		
		//세션
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		// List 객체를 JSON으로 변환하여 보내야한다.
		JSONObject json = new JSONObject();

		if (boardList != null) {
			JSONArray array = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
			
			for (BoardDTO boardDTO : boardList) {
				JSONObject temp = new JSONObject();
				
				temp.put("seq", boardDTO.getSeq());
				temp.put("name", boardDTO.getName());
				temp.put("id", boardDTO.getId());
				temp.put("email", boardDTO.getEmail());
				temp.put("subject", boardDTO.getSubject());
				temp.put("content", boardDTO.getContent());
				temp.put("hit", boardDTO.getHit());
				temp.put("ref", boardDTO.getRef());
				temp.put("lev", boardDTO.getLev());
				temp.put("step", boardDTO.getStep());
				temp.put("pseq", boardDTO.getPseq());
				temp.put("reply", boardDTO.getReply());
				temp.put("logtime", String.valueOf(boardDTO.getLogtime()));
				
				array.add(temp);
			} //for
			json.put("list", array);
		} //if
		
		//boardPaging을 JSON형식으로 변환
		json.put("pagingHTML", String.valueOf(boardPaging.getPagingHTML())); //StringBuffer →  String으로 변환
		
		request.setAttribute("json", json);
		request.setAttribute("id", id);

		return "/board/getBoardList.jsp";
	}

}
