package board.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.JDBCTemplate;

import static common.JDBCTemplate.*;

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

	public List<Board> selectList(Connection conn, int start, int end) {
		List<Board> list = new ArrayList<>();
		String sql = prop.getProperty("selectPagedBoard");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			System.out.println("sql@selectList() @boardDao= " + sql);
			
			while(rset.next()) {
				Board board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
				
				System.out.println(rset.getInt("attach_no"));
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
			
			
		} catch (SQLException e) {
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
			pstmt.setString(3, attach.getRenamedFileNAme());
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
			
		} catch (SQLException e) {
			e.printStackTrace();
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
				attach.setRenamedFileName(rset.getString("renamedFileNAme"));
				attach.setStatus("Y".equals(rset.getString("status"))? true: false);
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

}
