package member.model.dao;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static common.JDBCTemplate.*;

import common.JDBCTemplate;
import member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		String filename = JDBCTemplate.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member selectOne(Connection conn, String memberId) {
		String sql = prop.getProperty("selectOne");
		ResultSet rset = null;
		Member member = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
//				System.out.println("member@dao = " + member);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberRole());
			pstmt.setString(5, member.getGender());
			pstmt.setDate(6, member.getBirthday());
			pstmt.setString(7, member.getEmail());
			pstmt.setString(8, member.getPhone());
			pstmt.setString(9, member.getAddress());
			pstmt.setString(10, member.getHobby());
			pstmt.setDate(11, member.getEnrollDate());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateAllById(Connection conn, Member member) {
		int result = 0;
		String sql = prop.getProperty("updateAllById");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberName());
			pstmt.setString(3, member.getMemberRole());
			pstmt.setString(4, member.getGender());
			pstmt.setDate(5, member.getBirthday());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			pstmt.setString(10, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		
		String sql = prop.getProperty("deleteMember");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int updatePassword(Connection conn, Member loginMember) {
		int result = 0;
		
		String sql = prop.getProperty("updatePassword");
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginMember.getPassword());
			pstmt.setString(2, loginMember.getMemberId());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<Member> selectList(Connection conn) {
		List<Member> list = new ArrayList<>();
		Member member = null;
		String sql = prop.getProperty("selectList");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				member = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	public List<Member> selectList(Connection conn, int start, int end) {
		List<Member> list = new ArrayList<>();
		Member member = null;
		String sql = prop.getProperty("selectPagedList");
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				member = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int updateRole(Connection conn, String memberId, String memberRole) {
		int result = 0;
		
		String sql = prop.getProperty("updateRole");
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberRole);
			pstmt.setString(2, memberId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<Member> selectMember(Connection conn, Map<String, String> param, int start, int end) {
		List<Member> list = new ArrayList<>();
		Member member = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		//select * from member where member_id like %keyword%
		//select * from member where member_name like %keyword%
		//select * from member where gender = 'M'
		//->.properteis에는 select * from member where만 담기
		String query = prop.getProperty("selectPagedList");
		
		//where절의 #부분을 검색 타입에 맞게 변경
		String searchQuery = null;
		switch(param.get("searchType")) {
		case "memberId" : searchQuery = " member_id like '%" + param.get("searchKeyword") + "%'"; break;
		case "memberName" : searchQuery = " member_name like '%" + param.get("searchKeyword") + "%'"; break;
		case "gender" : searchQuery = " gender = '" + param.get("searchKeyword") + "'"; break;
		}
		query = query.replace("#", searchQuery);
		System.out.println("query@dao = " + query);
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				member = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int selectMemberCount(Connection conn) {
		String sql = prop.getProperty("selectMemberCount");
		int count = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				count = rset.getInt("count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return count;
		
	}
	
	public List<Member> searchMember(Connection conn, Map<String, String> param, int start, int end) {
		List<Member> list = new ArrayList<>();
		Member member = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		//select * from member where member_id like %keyword%
		//select * from member where member_name like %keyword%
		//select * from member where gender = 'M'
		//->.properteis에는 select * from member where만 담기
		String query = prop.getProperty("searchPagedMember");
		
		//where절의 #부분을 검색 타입에 맞게 변경
		String searchQuery = new String();
		switch(param.get("searchType")) {
		case "memberId" : searchQuery = " member_id like '%" + param.get("searchKeyword") + "%'"; break;
		case "memberName" : searchQuery = "member_name like '%" + param.get("searchKeyword") + "%'"; break;
		case "gender" : searchQuery = " gender = '" + param.get("searchKeyword") + "'"; break;
		}
		query = query.replace("#", searchQuery);
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			System.out.println("query@searchMember()@dao = " + query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				member = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;

	}

	public int searchMemberCount(Connection conn, Map<String, String> param) {
		int count = 0;
		String query = prop.getProperty("searchMemberCount");
		
		//where # replace
		String searchQuery = null;
		switch(param.get("searchType")) {
		case "memberId" : searchQuery = " member_id like '%" + param.get("searchKeyword") + "%'"; break;
		case "memberName" : searchQuery = " member_name like '%" + param.get("searchKeyword") + "%'"; break;
		case "gender" : searchQuery = " gender = '" + param.get("searchKeyword") + "'"; break;
		}
		query = query.replace("#", searchQuery);
		System.out.println("query@searchMemberCount()@dao = " + query);

		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				count = rset.getInt("count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return count;
	}



}
