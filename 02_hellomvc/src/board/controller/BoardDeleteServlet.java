package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/board/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println("no@servlet = " + no);
			int result = boardService.deleteBoard(no);
			
			HttpSession session = request.getSession();
			System.out.println("result@deleteServlet = " + result);
			
			if(result > 0) {
				session.setAttribute("msg", "게시물이 삭제되었습니다");
				//boardList로 redirection
				response.sendRedirect(request.getContextPath() + "/board/boardList");
			}
			else {
				session.setAttribute("msg", "게시물 삭제에 실패했습니다.");
			}
		}catch(Exception e) {
			//예외 로깅을 컨트롤러에서 한다
			e.printStackTrace();
			//예외페이지 전환을 위해서 was에 예외 다시 던지기
			throw e;
		}
//		throw new BoardException("게시물 삭제 오류");
	}

}
