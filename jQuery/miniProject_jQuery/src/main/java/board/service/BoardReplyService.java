package board.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.control.CommandProcess;

import board.bean.BoardDTO;
import board.dao.BoardDAO;

public class BoardReplyService implements CommandProcess{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		//데이터
		int pseq = Integer.parseInt(request.getParameter("seq")); //원글 번호
		int pg = Integer.parseInt(request.getParameter("pg"));    //원글이 있는 페이지 번호
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		//세션
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email");
		
		//DB
		Map<String, Object> map = new HashMap<>();
		map.put("pseq", pseq);
		map.put("pg", pg);
		map.put("subject", subject);
		map.put("content", content);
		map.put("id", id);
		map.put("name", name);
		map.put("email", email);
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.boardReply(map);
		
		return "/board/boardReply.jsp";
	}

}