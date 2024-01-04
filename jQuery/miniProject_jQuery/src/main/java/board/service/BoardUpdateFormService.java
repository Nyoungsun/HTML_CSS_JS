package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.control.CommandProcess;

import board.bean.BoardDTO;
import board.dao.BoardDAO;

public class BoardUpdateFormService implements CommandProcess{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		
		//데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		String pg = request.getParameter("pg");
		
		//DB
		BoardDAO boardDAO = BoardDAO.getInstance();
		BoardDTO boardDTO = boardDAO.boardView(seq);
		
		//응답
		
		
		return pg;
	}

}
