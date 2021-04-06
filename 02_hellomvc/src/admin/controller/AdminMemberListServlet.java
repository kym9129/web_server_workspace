package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet("/admin/memberlist")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//페이징 기능 추가
		/**
		 * ## Paging Recipe

		A. Contents Section : 쿼리
		
		1. start rownum ~ end rownum
		2. 필요한 변수들 : cPage(현재페이지), numPerPage(페이지 당 표시할 컨텐츠 수)
		
		B. Pagebar Section : html작성
		
		필요한 변수들
		
		1. totalContents(총 컨텐츠 수)
		2. totalPage(전체 페이지 수)
		3. pageBarSize (페이지바에 표시할 페이지 개수) (이전 1 2 3 4 5  다음 ...10)
		4. pageNo (페이지 넘버를 출력할 증감변수)
		5. pageStart ~ pageEnd (pageNo의 범위)
		 */
		
		
		//1. 사용자 입력값 : 현재페이지 cPage
		final int numPerPage = 10; //페이지 당 10건씩 노출
		int cPage = 1;
		try {
			
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			//Integer로 파싱하는 과정에서 cPage파라미터가 Null일 때 발생하는 예외
			//처리코드 없음. 기본값 1 유지
		}
		
		
		//2. (인코딩->필터, 사용자입력값->없음) 업무로직 : 전체회원조회
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		//회원가입일 내림차순으로 조회 + paging기능 추가
		List<Member> list = memberService.selectList(start, end);
		System.out.println("list@servlet=" + list);
		
		//3. pagebar영역 작업
		
		//totalContents : 전체 컨텐츠 수
		int totalContents = memberService.selectMemberCount();
		System.out.println("totalContents @AdminMemberListServlet = : " + totalContents);
		
		String url = request.getRequestURI(); //mvc/admin/memberList
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);
		//url : 링크 눌렀을 때 이동할 주소
		
		
		//4. jsp에 html응답메세지 작성 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);
	}

}
