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

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet("/member/updateMember")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberService memberService = new MemberService();
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String memberRole = MemberService.MEMBER_ROLE;
		String gender = request.getParameter("gender");
		
		String bdayStr = request.getParameter("birthday");
		System.out.println("bdayStr@updateServlet=" + bdayStr);
		//브라우저에서 미입력 시 기본값
		if(bdayStr.equals("") || bdayStr == null) bdayStr = "0001-01-01";
		System.out.println("bdayStr@updateServlet=" + bdayStr);
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
		int result = memberService.updateAllById(member);
		
		HttpSession session = request.getSession();
		
		//4. response
		if(result > 0) {
			session.setAttribute("updateMsg", "회원정보가 수정되었습니다.");
			session.setAttribute("loginMember", member);
			System.out.println("update succeed");
		}
		else {
			session.setAttribute("updateMsg", "회원정보 수정에 실패했습니다.");
			System.out.println("update fail");
		}
		
		//result값에 따라 jsp처리 + redirect
		response.sendRedirect(request.getContextPath());
		
		
	}

}
