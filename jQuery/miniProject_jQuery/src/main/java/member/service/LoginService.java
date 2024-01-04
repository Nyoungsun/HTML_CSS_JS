package member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.control.CommandProcess;
import member.bean.MemberDTO;
import member.dao.MemberDAO;

public class LoginService implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {

		// 데이터
		String id = request.getParameter("id").trim();
		String pw = request.getParameter("pw").trim();

		// DB
		MemberDAO memberDAO = MemberDAO.getInstance();
		MemberDTO dto = memberDAO.isExist(id);

		String name = null;
		String email = null;

		if (null != dto) {
			name = dto.getName();
			email = dto.getEmail1() + "@" + dto.getEmail2();
		}
		
		if (name == null || !(dto.getId().equals(id)) || !(dto.getPwd().equals(pw))) {
			return "/member/loginFail.jsp";
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("id", id);
			session.setAttribute("email", email);
			
			return "/member/loginOk.jsp";
		}
	}
}