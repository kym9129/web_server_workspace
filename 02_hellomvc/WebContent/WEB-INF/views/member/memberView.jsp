<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%

	String memberId = loginMember.getMemberId();
	String password = loginMember.getPassword();
	String memberName = loginMember.getMemberName();
	Date birthday = loginMember.getBirthday();
	String email = loginMember.getEmail() != null? loginMember.getEmail() : "";
	String phone = loginMember.getPhone();
	String address = loginMember.getAddress() != null? loginMember.getAddress() : "";
	String gender = loginMember.getGender() !=null? loginMember.getGender() : "";
	String hobby = loginMember.getHobby();
	
	List<String> hobbyList = null;
	if(hobby != null){
		String[] arr = hobby.split(",");
		hobbyList = Arrays.asList(arr); //String[] -> List<String>
	}

%>
<form name="deleteMemberFrm" action ="<%= request.getContextPath() %>/member/deleteMember" method = "POST">
	<input type="hidden" name="deleteMemberId" value = "<%= loginMember.getMemberId() %>" />
</form>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form id="memberUpdateFrm" method="post" action = "<%= request.getContextPath() %>/member/updateMember">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="memberId" id="memberId_" value="<%= memberId %>" readonly>
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" value="<%=password %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password2" value="" required><br>
				</td>
			</tr> 
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%=memberName %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%=birthday%>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%=email%>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= phone %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%= address %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       		 <input type="radio" name="gender" id="gender0" value="M" <%= "M".equals(gender)? "checked" : "" %>>
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F" <%= "F".equals(gender)? "checked" : "" %>>
						 <label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= hobbyChecked(hobbyList, "운동") %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= hobbyList != null && hobbyList.contains("등산") ? "checked" : "" %>>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= hobbyList != null && hobbyList.contains("독서") ? "checked" : "" %>>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= hobbyList != null && hobbyList.contains("게임") ? "checked" : "" %>>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= hobbyList != null && hobbyList.contains("여행") ? "checked" : "" %>>><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
        <input type="submit" value="정보수정"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<<script>
/*
 *@실습문제 : 회원정보 수정 & 탈퇴
 회원정보 수정 & 탈퇴 - 페이지 이동 필수
 회원정보 수정시 유효성필요.
 탈퇴 POST방식으로 처리할 것.
 회원정보 수정후에 Session의 loginMember의 정보도 변경되었는가?
 회원탈퇴후에 로그아웃 처리되었는가? 
 */
 
	/* 
	회원수정 폼 제출전 적절한 유효성 검사를 실시할 것.
	*/
	$('[name="memberUpdateFrm"]').submit(function(){
		//id 4자이상
		var $memberId_ = $("#memberId_");
		if(/^.{4,}$/.test($memberId_.val()) == false){
			alert("아이디는 4자 이상이어야 합니다.");
			$memberId_.select();
			return false;
		}
		//전화번호 -없이
		var $phone = $("#phone");
		if(/^[0-9]{10,11}/.test($phone.val()) == false){
			alert("전화번호는 숫자로만 입력해주세요.");
			$phone.select();
			return false;
		}
		
	});
	
	
	function deleteMember(){
		var $frm = $("[name=deleteMemberFrm]");
		$frm.submit();
	}


</script>
<%!
	//메소드 선언문
	public String hobbyChecked(List<String> hobbyList, String hobby){
		return hobbyList != null && hobbyList.contains(hobby) ? "checked" : "" ;
	}
%>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
