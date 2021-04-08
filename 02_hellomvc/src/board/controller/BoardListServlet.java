package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.BoardCommentCount;
import common.MvcUtils;

/**
 * 
 * 페이징
 *  - 한 페이지당 5개씩 numPerPage : 5
 *  
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 인코딩 : EncodingFilter
		//1. 사용자 입력값 처리
		final int numPerPage = 5;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			
		}
		
		
		//2. 업무로직
		//a. contents영역 : start ~ end (rnum 1~5, 6~10...)
		//cPage 1, numPerPage 5 -> 1 ~ 5
		//cPage 2, numPerPage 5 -> 6 ~ 10
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		
		List<BoardCommentCount> list = boardService.selectList(start, end);
		System.out.println("boardList@servlet = " + list);
		
		//b. pageBar영역(MvcUtil클래스 활용)
		int totalContents = boardService.selectBoardCount();
//		System.out.println("totalContents@BoardListServlet = " + totalContents);
		String url = request.getRequestURI(); // /mvc/board/boardList
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);
		
//		//각 게시글의 댓글 수 가져오기
//		int commentCount = boardService.selectCommentCount(no);
//		System.out.println("boardCommentCount@servlet = " + commentCount);
//		
		//3. 응답 html처리 jsp에 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp").forward(request, response);
	}

}
