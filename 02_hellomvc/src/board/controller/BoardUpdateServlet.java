package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/board/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로직
		Board board = boardService.selectBoardByNo(no);
		
		//3. jsp포워딩
		request.setAttribute("board", board);
		request.getRequestDispatcher("/WEB-INF/views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. MultipartRequest 객체 생성
			//  /WebContent/upload/board/업로드파일명.jpg
			// web root directory를 절대경로로 반환해준다. (맨왼쪽 슬래쉬=WebContent)
			String saveDirectory = getServletContext().getRealPath("/upload/board");
			System.out.println("saveDirectory@servlet = " + saveDirectory);
			
			//최대파일허용크기 10mb = 10 * 1kb * 1kb
			int maxPostSize = 10 * 1024 * 1024;
			
			//인코딩
			String encoding = "utf-8";
			
			//파일명 변경 정책 객체
			//중복 파일인 경우, 넘버링 처리
			//filerename : 20210406191919_123.jpg
	//		FileRenamePolicy policy = new DefaultFileRenamePolicy();
			FileRenamePolicy policy = new MvcFileRenamePolicy();
			
			MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
			
			
			//2. DB에 게시글/첨부파일 정보 저장
			
			//2-1. 사용자 입력값 처리(request -> multipartRequest)
	//		String title = request.getParameter("title");
	//		String writer = request.getParameter("writer");
	//		String content = request.getParameter("content");
			int no = Integer.parseInt(multipartRequest.getParameter("no"));
			String title = multipartRequest.getParameter("title");
			String writer = multipartRequest.getParameter("writer");
			String content = multipartRequest.getParameter("content");
			
			//업로드한 파일명
			//파라미터 : html에서 업로드 인풋의 name
			String originalFileName = multipartRequest.getOriginalFileName("upFile");
			String renamedFileName = multipartRequest.getFilesystemName("upFile"); //중복될 경우 바뀔 이름
			
			//삭제할 첨부파일번호
			String attachNo = multipartRequest.getParameter("delFile");
			System.out.println("attachNo@servlet = " + attachNo);
			
			Board board = new Board();
			board.setNo(no);
			board.setTitle(title);
			board.setWriter(writer);
			board.setContent(content);
			
			//첨부파일이 있는 경우
			//multipartRequest.getFile("upFile"):File != null
			if(originalFileName != null) {
				Attachment attach = new Attachment(); //우리가 만든 vo객체
				attach.setBoardNo(no); //board_no 추가
				attach.setOriginalFileName(originalFileName);
				attach.setRenamedFileName(renamedFileName);
				board.setAttach(attach);
			}
			
			//2. 업무로직 : 
			int result = 0;
			//첨부파일 삭제
			if(attachNo != null) {
				result = boardService.deleteAttachment(attachNo);
			}
			
			//db에 update
			result = boardService.updateBoard(board);
			System.out.println("result@servlet = " + result);
//			System.out.println("board@EnrollServlet = " + board);
			
			HttpSession session = request.getSession();
			
			//3. DML요청 : redirect & user feedback
			// redirect to /mvc/board/boardList
			if(result > 0) {
				session.setAttribute("msg", "게시글 수정 성공!");		
			}
			else {
				session.setAttribute("msg", "게시판 수정 실패!");
			}
			response.sendRedirect(request.getContextPath() + "/board/boardView?no=" + board.getNo());
		}catch(Exception e) {
			e.printStackTrace();
			throw e; //was한테 다시 던져서 에러페이지로 전환
		}
	}

}
