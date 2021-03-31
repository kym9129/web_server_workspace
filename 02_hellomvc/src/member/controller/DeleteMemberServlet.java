package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class DeleteMemberServlet
 */
@WebServlet("/member/deleteMember")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//id를 어떻게 가져오지? session??
		String memberId = request.getParameter("deleteMemberId");
		
		int result = memberService.deleteMember(memberId);
		HttpSession session = request.getSession();
		
		if(result > 0) {
			session.setAttribute("deleteMsg", "회원탈퇴가 정상처리되었습니다.");
//			session.removeAttribute("loginMember");
			System.out.println("deleteMember completed!");
			//로그아웃
			//리다이렉션
			response.sendRedirect(request.getContextPath() + "/member/logout");
		}
		else {
			session.setAttribute("deleteMsg", "회원탈퇴에 실패했습니다.");
		}
	}

}
