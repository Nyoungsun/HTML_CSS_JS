package member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.control.CommandProcess;

import member.bean.MemberDTO;
import member.dao.MemberDAO;

public class CheckIdService implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// 데이터
		String id = request.getParameter("id").trim();

		// DB
		MemberDAO memberDAO = MemberDAO.getInstance();
		MemberDTO dto = memberDAO.isExist(id);
		
		if (dto != null && dto.getId().equals(id)) {
			return "/member/checkIdFail.jsp";
		} else {
			return "/member/checkIdOk.jsp";
		}
	}
}
