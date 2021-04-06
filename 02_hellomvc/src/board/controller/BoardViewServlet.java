package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * 게시글 상세보기
	 *  - board + attachment 조회
	 *  - join없이 2번의 쿼리 요청할 것
	 *  
	 *  
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 사용자 입력값 처리 : no
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로직 : board객체 조회(첨부파일 조회)
		Board board = boardService.selectBoardByNo(no);
		System.out.println("board@servlet = " + board);
		
		//3. jsp forwarding 리다이렉트
		request.setAttribute("board", board);
		request.getRequestDispatcher("/WEB-INF/views/board/boardView.jsp").forward(request, response);
		
		

	}

}
