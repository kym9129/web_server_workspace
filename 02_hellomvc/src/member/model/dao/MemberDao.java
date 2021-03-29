package member.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
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

}
