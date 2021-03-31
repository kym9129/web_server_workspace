package admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class AdminRoleUpdateServlet
 */
@WebServlet("/admin/memberRoleUpdate")
public class AdminRoleUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String memberRole = request.getParameter("memberRole");
		HttpSession session = request.getSession();
		
		//2. 업무로직
		int result = memberService.updateRole(memberId, memberRole);
		String msg = null;
		String roleStr = MemberService.ADMIN_ROLE.equals(memberRole)? "관리자" : "일반";
		
		//3. 리다이렉트 및 사용자 피드백
		// /mvc/admin/memberList
		if(result > 0) {
			msg = "[" + memberId + "]회원의 권한을 ["+roleStr+"]로(으로) 변경했습니다.";
			
		} else {
			msg = memberId + " 회원의 권한 변경에 실패했습니다.";
		}
		
		session.setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/admin/memberlist");
		
	}

}