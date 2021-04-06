package admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();
       
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
 		
 		//페이징 처리
 		final int numPerPage = 10;
 		int cPage = 1;
 		try {
 			cPage = Integer.parseInt(request.getParameter("cPage"));
 		} catch (NumberFormatException e) {
 			
 		}
 		
 		//검색타입+검색어
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		Map<String, String> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);
		
		System.out.println("param@servlet = " + param);
		
		//2. 업무 로직
//		List<Member> list = memberService.searchMember(param);
//		System.out.println("list@servlet = " + list);
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		List<Member> list = memberService.searchMember(param, start, end);
		System.out.println("list@servlet = " + list);
		
		//3. pageBar영역 작업
		int totalContents = memberService.searchMemberCount(param);
//		int totalContents = 115;
		System.out.println("totalCounts@AdminMemberFinderServlet = " + totalContents);
		
		String url = request.getRequestURI() + "?searchType=" + searchType + "&searchKeyword=" + searchKeyword; // mvc/admin/memberFinder
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);
		
		//4. jsp에 html응답메세지 작성 위임
		//검색결과를 jsp에서 사용할 수 있도록 속성값 넣기
		request.setAttribute("param", param);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);
	}

}
