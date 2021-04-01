package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	public static final String MEMBER_ROLE = "U";
	public static final String ADMIN_ROLE = "A";

	public Member selectOne(String memberId) {
		Connection conn = getConnection();
		Member member = memberDao.selectOne(conn, memberId);
		close(conn);
		
		return member;
	}
	
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	public int updateAllById(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateAllById(conn, member);
		if(result > 0) {
			commit(conn);
		} else {
			close(conn);
		}
		return result;
	}

	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, memberId);
		if(result > 0) {
			commit(conn);
		} else {
			close(conn);
		}
		return result;
	}

	public int updatePassword(Member loginMember) {
		Connection conn = getConnection();
		int result = memberDao.updatePassword(conn, loginMember);
		if(result > 0) {
			commit(conn);
		}else {
			close(conn);
		}
		return result;
	}

	public List<Member> selectList() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectList(conn);
		close(conn);
		
		return list;
	}
	
	public int updateRole(String memberId, String memberRole) {
		Connection conn = getConnection();
		int result = memberDao.updateRole(conn, memberId, memberRole);
		if(result > 0) {
			commit(conn);
		}else {
			close(conn);
		}
		return result;
	}
	
	public List<Member> selectList(int start, int end) {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectList(conn, start, end);
		close(conn);
		
		return list;
	}
	
	public int selectMemberCount() {
		Connection conn = getConnection();
		int totalContents = memberDao.selectMemberCount(conn);
		close(conn);
		
		return totalContents;
	}



	public List<Member> searchMember(Map<String, String> param, int start, int end) {
		Connection conn = getConnection();
		List<Member> list = memberDao.searchMember(conn, param, start, end);
		close(conn);
		
		return list;
	}

	public int searchMemberCount(Map<String, String> param) {
		Connection conn = getConnection();
		int totalContents = memberDao.searchMemberCount(conn, param);
		close(conn);
		
		return totalContents;
	}
	
	

}
