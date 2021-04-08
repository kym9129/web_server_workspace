package board.model.vo;

import java.sql.Date;

public class BoardCommentCount extends Board {
	
	private int boardCommentCount;

	public BoardCommentCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public BoardCommentCount(int no, String title, String writer, String content, Date regDate, int readCount,
			Attachment attach, int boardCommentCount) {
		super(no, title, writer, content, regDate, readCount, attach);
		this.boardCommentCount = boardCommentCount;
		// TODO Auto-generated constructor stub
	}


	public BoardCommentCount(int boardCommentCount) {
		super();
		this.boardCommentCount = boardCommentCount;
	}

	public int getBoardCommentCount() {
		return boardCommentCount;
	}

	public void setBoardCommentCount(int boardCommentCount) {
		this.boardCommentCount = boardCommentCount;
	}


	@Override
	public String toString() {
		return "BoardCommentCount [boardCommentCount=" + boardCommentCount + ", getNo()=" + getNo() + ", getTitle()="
				+ getTitle() + ", getWriter()=" + getWriter() + ", getContent()=" + getContent() + ", getRegDate()="
				+ getRegDate() + ", getReadCount()=" + getReadCount() + ", getAttach()=" + getAttach() + "]";
	}


}
