package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import board.model.vo.BoardCommentCount;
import common.JDBCTemplate;

public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() {
		//board-query.properties의 내용 읽어와서 prop에 저장
		//resources/sql/board-query.properties가 아니라
		//WEB-INF/classes/sql/board-query.properties에서 접근해야 함
		
		//꼭 JDBCTemplate클래스가 아녀도 됨. (getResource를 가져오기 위해 사용)
		String filename = JDBCTemplate.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<BoardCommentCount> selectList(Connection conn, int start, int end) {
		List<BoardCommentCount> list = new ArrayList<>();
		String sql = prop.getProperty("selectPagedBoard");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
//			System.out.println("sql@selectList() @boardDao= " + sql);
			
			while(rset.next()) {
				BoardCommentCount board = new BoardCommentCount();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
				int count = selectCommentCount(conn, rset.getInt("no"));
				board.setBoardCommentCount(count);
				
//				System.out.println(rset.getInt("attach_no"));
				//첨부파일이 있는 경우
				if(rset.getInt("attach_no") != 0) {
					Attachment attach = new Attachment();
					attach.setNo(rset.getInt("attach_no"));
					attach.setBoardNo(rset.getInt("no"));
					attach.setOriginalFileName(rset.getString("original_filename"));
					attach.setRenamedFileName(rset.getString("renamed_filename"));
					attach.setStatus("Y".equals(rset.getString("status"))? true : false);
					board.setAttach(attach);
				}
				
				list.add(board);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectBoardCount(Connection conn) {
		String sql = prop.getProperty("selectBoardCount");
		int totalContents = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				totalContents = rset.getInt("count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContents;
	}

		public int insertBoard(Connection conn, Board board) {
			String sql = prop.getProperty("insertBoard");
			int result = 0;
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				/**
				 * insert into board (no,title,writer,content,reg_date,read_count) 
				 * values (seq_board_no.nextval,?,?,?,sysdate,0);
				 * 
				 */
				
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getWriter());
				pstmt.setString(3, board.getContent());
				
				result = pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				throw new BoardException("게시물 등록 오류", e);
			} finally {
				close(pstmt);
			}
		
		return result;
	}

	public int selectLastBoardNo(Connection conn) {
		String sql = prop.getProperty("selectLastBoardNo");
		int boardNo = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				boardNo = rset.getInt("board_no");
			}
			
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 번호 조회 오류", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return boardNo;
	}

	public int insertAttachment(Connection conn, Attachment attach) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertAttachment");
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOriginalFileName());
			pstmt.setString(3, attach.getRenamedFileName());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//RuntimeException을 상속받는 커스텀 예외클래스 생성
			throw new BoardException("게시물 첨부파일 등록 오류", e);
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	public Board selectBoardByNo(Connection conn, int no) {
		Board board = new Board();
		String sql = prop.getProperty("selectBoardByNo");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rset = pstmt.executeQuery();
			System.out.println("sql@selectList() @boardDao= " + sql);
			
			while(rset.next()) {
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			//dao에서 에러메세지를 찍어버리면 controller에선 에러 여부를 알 수가 없음
			throw new BoardException("게시글 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return board;
	}

	public Attachment selectAttachByBoardNo(Connection conn, int no) {
		Attachment attach = new Attachment();
		String sql = prop.getProperty("selectAttachByBoardNo");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rset = pstmt.executeQuery();
			System.out.println("sql@selectList() @boardDao= " + sql);
			
			while(rset.next()) {
				attach.setNo(rset.getInt("no"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setStatus("Y".equals(rset.getString("status"))? true : false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return attach;
	}

	public Board selectLastBoard(Connection conn) {
		Board board = new Board();
		Attachment attach = new Attachment();
		String sql = prop.getProperty("selectLastBoard");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			System.out.println("sql@selectList() @boardDao= " + sql);
			
			while(rset.next()) {
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setStatus("Y".equals(rset.getString("status"))? true: false);
				board.setAttach(attach);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return board;
	}

	public int deleteBoard(Connection conn, int no) {
		int result = 0;
		
		String sql = prop.getProperty("deleteBoard");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int updateBoard(Connection conn, Board board) {
		String sql = prop.getProperty("updateBoard");
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNo());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new BoardException("게시물 수정 오류", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteAttachment(Connection conn, String attachNo) {
		String sql = prop.getProperty("deleteAttachment");
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, attachNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new BoardException("첨부파일 삭제 오류", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int insertBoardComment(Connection conn, BoardComment bc) {
		String sql = prop.getProperty("insertBoardComment");
		int result = 0;
		PreparedStatement pstmt = null;
		/**
		 * insert into board_comment
		 * (no, comment_level, writer, content, board_no, comment_ref)
		 *  values(seq_board_comment_no.nextval, ?, ?, ?, ?, ?)
		 */
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getCommentLevel());
			pstmt.setString(2, bc.getWriter());
			pstmt.setString(3, bc.getContent());
			pstmt.setInt(4, bc.getBoardNo());
//			pstmt.setInt(5, bc.getCommentRef() == 0? null : bc.getCommentRef());
			//int값에 null을 어케 넣어!
			//데이터타입에서 자유로운 Object 타입으로 대입
			pstmt.setObject(5, bc.getCommentRef() == 0? null : bc.getCommentRef());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new BoardException("댓글 등록 오류", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<BoardComment> selectBoardCommentList(Connection conn, int no) {
		List<BoardComment> commentList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoardCommentList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardComment bc = new BoardComment();
				bc.setNo(rset.getInt("no"));
				bc.setCommentLevel(rset.getInt("comment_level"));
				bc.setWriter(rset.getString("writer"));
				bc.setContent(rset.getString("content"));
				bc.setBoardNo(rset.getInt("board_no"));
				bc.setCommentRef(rset.getInt("comment_ref"));
				bc.setRegDate(rset.getDate("reg_date"));
				commentList.add(bc);
			}
			
		} catch (Exception e) {
			throw new BoardException("댓글 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return commentList;
	}

	public int selectCommentCount(Connection conn, int no) {
		int commentCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCommentCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				commentCount = rset.getInt("count");
			}
			
		} catch (Exception e) {
			throw new BoardException("댓글 수 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return commentCount;
	}

}
