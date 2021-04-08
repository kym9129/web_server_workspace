package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import common.MvcUtils;

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
		try {
			//1. 사용자 입력값 처리 : no
			int no = Integer.parseInt(request.getParameter("no"));
			
			//2. 업무로직 : board객체 조회(첨부파일 조회)
			Board board = boardService.selectBoardByNo(no);
			System.out.println("board@servlet = " + board);
			if(board == null) {
				throw new BoardException("해당 게시물이 존재하지 않습니다.");
			}
			
			//xss공격 방지
			board.setTitle(MvcUtils.escapeHtml(board.getTitle()));
			board.setContent(MvcUtils.escapeHtml(board.getContent()));
			
			// \n개행문자를 <br/>태그로 변경
			board.setContent(MvcUtils.convertLineFeedToBr(board.getContent()));
			
			//이 게시글의 댓글 가져오기
			List<BoardComment> commentList = boardService.selectBoardCommentList(no);
			System.out.println("commentList@servlet = " + commentList);
			
			
			//3. jsp forwarding 리다이렉트
			request.setAttribute("board", board);
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/WEB-INF/views/board/boardView.jsp").forward(request, response);
		}catch(Exception e) {
			//로깅
			e.printStackTrace();
			//예외를 was에 다시 던진다
			throw e;
		}
		

	}

}
