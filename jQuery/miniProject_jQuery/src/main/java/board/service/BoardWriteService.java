package board.service;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.control.CommandProcess;
import board.dao.BoardDAO;

public class BoardWriteService implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		String id = (String) session.getAttribute("id");
		String email = (String) session.getAttribute("email");

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");

		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("id", id);
		map.put("email", email);
		map.put("subject", subject);
		map.put("content", content);

		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.boardWrite(map);

		return "/board/boardWrite.jsp";
	}
}