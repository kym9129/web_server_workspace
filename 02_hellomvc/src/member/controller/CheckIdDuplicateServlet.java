package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class CheckIdDuplicateServlet
 */
@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		
		//2. 업무 로직 : 해당 id를 DB에서 조회
		Member member = new MemberService().selectOne(memberId);
		boolean available = member == null;
		//member가 null이면 중복X. 해당 ID 사용 가능
		request.setAttribute("available", available);
		
		//3. 응답 처리 : 사용가능여부를 jsp에서 html로 작성
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp");
		reqDispatcher.forward(request, response);
	}

}
