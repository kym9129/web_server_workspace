package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MeberLogoutServlet
 */
@WebServlet({ "/MemberLogoutServlet", "/member/logout" })
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션 무효화 : 세션에 저장된 속성값을 모두 폐기
		//만약 세션이 존재하지 않으면, 새로 만들지 않고, null을 리턴
		HttpSession session = request.getSession(false);
		
		//로그아웃
		if(session != null) session.invalidate();
		
		//인덱스 페이지로 리다이렉션
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response); //요청이 get/post 뭐로 와도 동일하게 처리하겠다
	}

}
