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
 * Servlet implementation class BoardEnrollServlet
 */
@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * 0. form의 속성 enctype="multipart/form-data" 추가
	 * 1. MultipartRequest객체 생성
	 * 게시글 등록 성공 시 바로 상세보기 페이지로 이동할 것
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String title = multipartRequest.getParameter("title");
		String writer = multipartRequest.getParameter("writer");
		String content = multipartRequest.getParameter("content");
		
		//업로드한 파일명
		//파라미터 : html에서 업로드 인풋의 name
		String originalFileName = multipartRequest.getOriginalFileName("upFile");
		String renamedFileName = multipartRequest.getFilesystemName("upFile"); //중복될 경우 바뀔 이름
		
		
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		//첨부파일이 있는 경우
		//multipartRequest.getFile("upFile"):File != null
		if(originalFileName != null) {
			Attachment attach = new Attachment(); //우리가 만든 vo객체
			attach.setOriginalFileName(originalFileName);
			attach.setRenamedFileName(renamedFileName);
			board.setAttach(attach);
		}
		
		//2. 업무로직 : db에 insert
		int result = boardService.insertBoard(board);
		System.out.println("result@servlet = " + result);
		System.out.println("board@EnrollServlet = " + board);
		
		HttpSession session = request.getSession();
		
		//3. DML요청 : redirect & user feedback
		// redirect to /mvc/board/boardList
		if(result > 0) {
			session.setAttribute("msg", "게시판에 글이 등록되었습니다.");
			//게시글 등록 성공 시 바로 상세보기 페이지로 이동할 것
			
			//작성한 게시글 db에서 가져오기
			board = boardService.selectLastBoard();
			System.out.println("board.no@EnrollServlet = " + board.getNo());
			
			response.sendRedirect(request.getContextPath() + "/board/boardView?no=" + board.getNo());
		}
		else {
			session.setAttribute("msg", "게시판 글 등록에 실패했습니다.");
			response.sendRedirect(request.getContextPath() + "/board/boardList");
		}
		
	}

}
