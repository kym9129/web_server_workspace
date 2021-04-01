package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet Filter implementation class AdminLoginFilter
 */
//@WebFilter(
//		urlPatterns = { 
//				"/admin/memberlist", 
//				"/admin/memberRoleUpdate",
//				"/admin/memberFinder"
//		})
public class AdminFilter implements Filter {

 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//로그인 여부 확인
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;
		System.out.println("adminlogin filter 실행");
		
		HttpSession session = httpReq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null || !(MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole()))) {
			session.setAttribute("msg", "관리자만 사용할 수 있습니다.");
			httpRes.sendRedirect(httpReq.getContextPath());
			System.out.println("AdminLoginFilter 성공!");
			return; //조기리턴 실행. chain.doFilter가 실행되지 않는다
			//servlet에 접근하지 못한다
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}
