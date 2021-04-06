package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardService {
	
	private BoardDao boardDao = new BoardDao();

	public List<Board> selectList(int start, int end) {
		Connection conn = getConnection();
		List<Board> list = boardDao.selectList(conn, start, end);
		close(conn);
		return list;
	}

	public int selectBoardCount() {
		Connection conn = getConnection();
		int totalContents = boardDao.selectBoardCount(conn);
		close(conn);
		return totalContents;
	}

	/**
	 * 첨부파일이 있는 경우, attach객체를 attachment테이블에 등록한다.
	 *  - board등록, attachment등록은 하나의 트랜잭션으로 처리되어야 한다.
	 *     -> 성공하면 둘다 성공, 실패하면 둘다 실패
	 *     -> 하나의 Connection에 의해 처리되어야 한다.
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.insertBoard(conn, board);
			
			//생성된 board_no를 가져오기(attachment테이블에 보내기 위함)
			int boardNo = boardDao.selectLastBoardNo(conn);
			System.out.println("boardNo@service = " + boardNo);
			
			//첨부파일이 있는 경우
			if(board.getAttach() != null) {
				//참조할 boardNo셋팅
				board.getAttach().setBoardNo(boardNo);
				result = boardDao.insertAttachment(conn, board.getAttach());
			}
			commit(conn);
		} catch(Exception e) {
			e.printStackTrace();
			rollback(conn); //예외가 발생할 경우 dao메소드 위의 3개 같이 롤백처리
			result = 0;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public Board selectBoardByNo(int no) {
		Connection conn = getConnection();
		Board board = boardDao.selectBoardByNo(conn, no);
		Attachment attach = boardDao.selectAttachByBoardNo(conn, no);
		board.setAttach(attach);
		
		close(conn);
		return board;
	}

	public Board selectLastBoard() {
		Connection conn = getConnection();
		Board board = boardDao.selectLastBoard(conn);
		
		close(conn);
		return board;
	}

}