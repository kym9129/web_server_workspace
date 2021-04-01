package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
       


	/**
	 * 회원가입 페이지 보여주기 (상태 안바뀜. 멱등)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp").forward(request, response);
	}

	/**
	 * 회원가입 처리 - db에 저장(상태바뀜. 멱등X) (DML은 다 POST)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getsha512(request.getParameter("password"));
		String memberName = request.getParameter("memberName");
		//member_role은 기본값 U 로 처리. 상수 사용할것.
		String memberRole = MemberService.MEMBER_ROLE;
		String gender = request.getParameter("gender");
		
		String bdayStr = request.getParameter("birthday");
		//브라우저에서 미입력 시 기본값
		if(bdayStr.equals("")) bdayStr = "0001-01-01";
		System.out.println("bdayStr@enrollServlet=" + bdayStr);
		Date birthday = Date.valueOf(bdayStr);
		
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String[] hobbies = request.getParameterValues("hobby");
		String hobby = Arrays.toString(hobbies).replace("[", "").replace("]", "").replace(" ", "");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ss = sdf.format(new java.util.Date());
		Date enrollDate = Date.valueOf(ss);
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setPassword(password);
		member.setMemberName(memberName);
		member.setMemberRole(memberRole);
		member.setGender(gender);
		member.setBirthday(birthday);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		//hobby컬럼은 , (컴마) 기준으로 하나의 문자열로 합쳐서 처리할 것.
		member.setHobby(hobby);
		member.setEnrollDate(enrollDate);
		
		//3. 업무로직 (db연동 ->service에게 위임)
		int result = memberService.insertMember(member);
		System.out.println("result@enrollServlet=" + result);
		System.out.println("member@mMemberEnrollServlet = " + member);
		
		HttpSession session = request.getSession();
		//4. response
		if(result == 1) {
			//회원가입성공|실패시 사용자 메시지를 띄운다.
			session.setAttribute("signupMsg", "회원가입에 성공했습니다.");
			System.out.println("가입성공");
		}
		else {
			//실패 메세지 전송
			session.setAttribute("signupMsg", "회원가입에 실패했습니다.");
			System.out.println("가입실패");
			
		}
		
		//요청 url을 반드시 변경(/mvc)해서 새로고침 오류를 예방한다.
		response.sendRedirect(request.getContextPath());
		
	}

}
