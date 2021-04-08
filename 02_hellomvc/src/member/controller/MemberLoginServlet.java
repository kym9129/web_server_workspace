package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * WebServlet어노테이션
 *  서블릿 등록을 자동으로 처리함 
 *  
 *  속성
 *  - urlPatterns : String[]
 *  - name : String
 */
//@WebServlet("/member/login")
@WebServlet(urlPatterns = {"/member/login"})
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. encoding처리 (post만 필요 - 근데 나중엔 귀찮아서 둘다한다고함)
		request.setCharacterEncoding("utf-8");
		
		// 2. 사용자 입력값 처리 (자바변수에 옮기기)
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getsha512(request.getParameter("password"));
		String saveId = request.getParameter("saveId");
		System.out.println("memberId@LoginServlet = " + memberId);
		System.out.println("password@LoginServlet = " + password);
		System.out.println("password@LoginServlet = " + saveId);
		
		// 3. 업무로직 (Business logic) : memberId로 회원객체를 조회
		//db와의 연동 -> service에게 위임
		Member member = memberService.selectOne(memberId);
		System.out.println("member@LoginServlet = " + member);
		
		//로그인 성공/실패여부 판단
		//1. 로그인 성공 : member != null && password.equals(member.getPassword())
		//2. 로그인 실패 : member == null
			// 아이디가 존재하지 않을 때 member == null
			// 비번이 틀릴 떄 member != null && !password.equals(membe.getPassword())
		HttpSession session = request.getSession();
		if(member != null && password.equals(member.getPassword())) {
			//로그인 성공
//			request.setAttribute("msg", "로그인에 성공했습니다.");
			
			//로그인한 사용자 정보 담기
//			request.setAttribute("loginMember", member);
			//request.getSession(create : boolean);
			//create : 새로 생성 여부. 기본값 true (만약 id가 존재하지 않는다면 새로 생성해라)
			
			System.out.println(session.getId());
			session.setAttribute("loginMember", member);
			
			//session timeout : web.xml보다 우선순위 높음
			//초단위로 작성
//			session.setMaxInactiveInterval(30); //30초
			
			Cookie c = new Cookie("saveId", memberId);
			c.setPath(request.getContextPath()); //path: 쿠키를 전송할 url
			//saveId : cookie처리
			if(saveId != null) {
				//saveId 체크 시
				c.setMaxAge(60*60*24*7); //원하는 기간만큼 쿠키를 보관. 초단위로 입력
				//7일짜리 영속쿠키로 지정
		
			}
			else {
				//saveId 체크 해제 시 ->MaxAge를 0으로 변경
				c.setMaxAge(0); //0으로 지정하면 즉시 삭제
				//음수로 지정하면 session종료 시 삭제
			}
			response.addCookie(c);
			
			
		}
		else {
			//로그인 실패
//			request.setAttribute("msg", "로그인에 실패했습니다.");
			session.setAttribute("msg", "로그인에 실패했습니다.");
//			request.setAttribute("loc", request.getContextPath());
			// value : '/mvc'를 리턴
			
			//이번엔 응답메세지도 index.html에서 처리
//			RequestDispatcher reqDispatcher = request.getRequestDispatcher("/index.jsp");
//			reqDispatcher.forward(request, response);
			//redirect처리
		}
		
		//리다이렉트 : url변경
		//response.sendRedirect("이동할 url");
//		response.sendRedirect(request.getContextPath());
		
		//이전페이지로 리다이렉트 처리
        String referer = request.getHeader("Referer");
        System.out.println("refere@servlet = " + referer);
        
        //리다이렉트 : url변경
        response.sendRedirect(referer);
    
	}

}
