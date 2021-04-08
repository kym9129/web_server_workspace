package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/boardCommentInsert")
public class BoardCommentInsertServlet extends HttpServlet {
	private BoardService boardService = new BoardService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 사용자 입력값 처리
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			int commentLevel = Integer.parseInt(request.getParameter("commentLevel"));
			int commentRef = Integer.parseInt(request.getParameter("commentRef"));
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			BoardComment bc = new BoardComment(0, commentLevel, writer, content, boardNo, commentRef, null);
			System.out.println("boardComment@Servlet = " + bc);
			
			//2. 업무로직
			int result = boardService.insertBoardComment(bc);
//			String msg = result > 0 ? "댓글 등록 성공!" : "댓글 등록 실패!";
			//어차피 실패하면 오류페이지로 넘어가니까 굳이 msg변수가 필요없음
			
			//3. 사용자 피드백 & 리다이렉트
			request.getSession().setAttribute("msg", "댓글 등록 성공!");
			response.sendRedirect(request.getContextPath() + "/board/boardView?no=" + boardNo);
		
		}catch(Exception e) {
			e.printStackTrace();
			throw e; //container(was)에게 던짐
		}
		
	}

}
