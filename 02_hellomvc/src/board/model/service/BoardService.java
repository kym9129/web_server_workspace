package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import board.model.vo.BoardCommentCount;

public class BoardService {
	
	private BoardDao boardDao = new BoardDao();

	public List<BoardCommentCount> selectList(int start, int end) {
		Connection conn = getConnection();
		List<BoardCommentCount> list = boardDao.selectList(conn, start, end);
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
//			e.printStackTrace();
			rollback(conn); //예외가 발생할 경우 dao메소드 위의 3개 같이 롤백처리
//			result = 0;
			throw new BoardException("게시물 등록 오류", e);
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

	public int deleteBoard(int no) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = boardDao.deleteBoard(conn, no);
			if(result == 0)
				throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. : " + no);
			
			commit(conn);
		} catch(Exception e) {
//			e.printStackTrace();
			rollback(conn);
			throw e; // controller가 예외처리를 결정할 수 있도록 넘김
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		try {
			//1. board update : board테이블에 게시글이 있는 상황
			result = boardDao.updateBoard(conn, board);
			//2. attachment insert : attachment테이블에 data가 없는 상황
			if(board.getAttach() != null) {
				result = boardDao.insertAttachment(conn, board.getAttach());
			}
			
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
			
		}finally {
			close(conn);
		}
		return result;
	}

	public int deleteAttachment(String attachNo) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.deleteAttachment(conn, attachNo);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
			
		}finally {
			close(conn);
		}
		return result;
	}

	public int insertBoardComment(BoardComment bc) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.insertBoardComment(conn, bc);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e; //매우중요 매우매우종요중요중요
			
		}finally {
			close(conn);
		}
		return result;
	}

	public List<BoardComment> selectBoardCommentList(int no) {
		Connection conn = getConnection();
		List<BoardComment> commentList = boardDao.selectBoardCommentList(conn, no);
		close(conn);
		return commentList;
	}

	public int selectCommentCount(int no) {
		Connection conn = getConnection();
		int commentList = boardDao.selectCommentCount(conn, no);
		close(conn);
		return commentList;
	}

}
