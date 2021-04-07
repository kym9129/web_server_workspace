package board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/board/fileDownload")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 : no 게시글 번호
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로직 : 첨부파일 조회
		//originalFileName, renamedFileName
		Attachment attach = boardService.selectBoardByNo(no).getAttach();
		System.out.println("attach@servlet = " + attach);
		
		//3. 파일 다운로드
		//a. 입출력스트림 생성
		String saveDirectory = getServletContext().getRealPath("/upload/board");
		//new File(저장될 경로, 저장될 파일명)
		File f = new File(saveDirectory, attach.getRenamedFileName());
		
		BufferedInputStream bis = 
				new BufferedInputStream(new FileInputStream(f));
		
		//대상이 응답메세지인 출력 스트림 가져오기
		BufferedOutputStream bos = 
				new BufferedOutputStream(response.getOutputStream());
		
		//b. 응답헤더작성
		//파일명 한글로 인코딩 처리
		//ISO-8859-1(톰캣용)으로 처리해서 헤더에 넣으면, 다운받을 때 톰캣이 다시 인코딩을 해준다
		String responseFileName = new String(attach.getOriginalFileName().getBytes("utf-8"), "ISO-8859-1");
		//서버에선 깨져서 나옴
		System.out.println("responseFileName = " + responseFileName);
		response.setContentType("application/octet-stream; charset=utf-8"); //2진데이터
		response.setHeader("Content-Disposition", "attachment;filename=" + responseFileName);
		
		//c. 파일출력
		int read = -1;
		while((read = bis.read()) != -1) {
			bos.write(read);
		}
		
		//d. 자원반납
		bos.close();
		bis.close();
	}

}
