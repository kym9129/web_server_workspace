package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardWriteServlet
 */
@WebServlet("/binsert")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 사용자 입력 처리
			String title = request.getParameter("btitle");
			String writer = request.getParameter("bwriter");
			String content = request.getParameter("bcontent");
			
			Board board = new Board();
			board.setTitle(title);
			board.setWriter(writer);
			board.setContent(content);
			
			//2. 비즈니스 로직 (db에 insert)
			int result = new BoardService().insertBoard(board);
			
			//3. 응답 
			//(게시판 목록 blist 서블릿 실행)
			request.getSession().setAttribute("msg", "게시글이 등록되었습니다.");
			response.sendRedirect(request.getContextPath() + "/blist");
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
