package board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.control.CommandProcess;

public class BoardViewService implements CommandProcess{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {

		//데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		request.setAttribute("seq", seq);
		request.setAttribute("pg", pg);
		request.setAttribute("display", "/board/boardView.jsp");
		
		return "/index.jsp";
	}
}
