package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.control.CommandProcess;

import board.bean.BoardDTO;
import board.dao.BoardDAO;

public class GetBoardViewService implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {

		// 세션
		//HttpSession session = request.getSession();
		//String id = (String) session.getAttribute("id");

		// 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));

		// DB
		BoardDAO boardDAO = BoardDAO.getInstance();
		BoardDTO boardDTO = boardDAO.boardView(seq);

		// BoardDTO 데이터를 JSON 형식으로
		JSONObject json = new JSONObject();

		if (boardDTO != null) {
			json.put("subject", boardDTO.getSubject());
			json.put("content", boardDTO.getContent());
			json.put("seq", boardDTO.getSeq());
			json.put("id", boardDTO.getId());
			json.put("hit", boardDTO.getHit());
		}

		// System.out.println(json.toString()); //JSON 데이터 확인

		request.setAttribute("json", json);
		//request.setAttribute("id", id);

		return "/board/getBoardView.jsp";
	}
}