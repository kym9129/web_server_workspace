<%@page import="member.model.service.MemberService"%>
<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <%
   	String msg = (String)session.getAttribute("msg");
   	if(msg != null) session.removeAttribute("msg"); //1회성 메세지
   	
   	String signupMsg = (String)session.getAttribute("signupMsg");
   	if(signupMsg != null) session.removeAttribute("signupMsg");
   	
   	String updateMsg = (String)session.getAttribute("updateMsg");
   	if(updateMsg != null) session.removeAttribute("updateMsg");
   	
   	String deleteMsg = (String)session.getAttribute("deleteMsg");
   	if(deleteMsg != null) session.removeAttribute("deleteMsg");
   	
   	String loc = (String)request.getAttribute("loc");
   	//Member loginMember = (Member)request.getAttribute("loginMember");
   	Member loginMember = (Member)session.getAttribute("loginMember");
   	//로그인 성공 시에만 객체가 담기고, 로그인 안했으면 member = null이 될 것
   			
	//사용자 쿠키처리
	String saveId = null;
	Cookie[] cookies = request.getCookies();
   	if(cookies != null){
   		for(Cookie c : cookies){
   			String name = c.getName();
   			String value = c.getValue();
   			System.out.println("coockies name, value @header.jsp=" + name + " : " + value);
   			if("saveId".equals(name)) saveId = value;
   		}
   	}
	
	
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" />
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
<script>
<% if(msg != null) {%>
	alert("<%= msg %>");
<% } %>

<% if(loc != null){ %>
	location.href = "<%= loc %>";
<% } %>
<%-- 회원가입 성공/실패 시 사용자 메세지 띄우기--%>
<% System.out.println("signupMsg@header=" + signupMsg); %>
<% if(signupMsg != null) {%>
	alert("<%= signupMsg %>");
<% } %>
<%--회원정보 수정 성공/실패 시 사용자 메세지 --%>
<% if(updateMsg != null) {%>
	alert("<%= updateMsg %>");
<% } %>
<%--회원탈퇴 성공/실패 시 사용자 메세지 --%>
<% if(deleteMsg != null) {%>
	alert("<%= deleteMsg %>");
<% } %>
	$(function(){
		/*
		* 로그인 폼 유효성 검사
		*/
		$('#loginFrm').submit(function(){
			var $memberId = $(memberId);
			var $password = $(password);
			
			if(/^.{4,}$/.test($memberId.val()) == false){
				alert("유효한 아이디를 입력하세요.");
				$memberId.select();
				return false;
			}
			if(/^.{4,}$/.test($password.val()) == false){
				alert("유효한 비밀번호를 입력하세요.");
				$password.select();
				return false;
			}
		});
		
		
		/* 
		회원가입 폼 제출전 적절한 유효성 검사를 실시할 것.
		<memberEnroll.jsp에서 처리>
		*/
	/* 	$('[name="memberEnrollFrm"]').submit(function(){
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
			
		}); */
		
	});

</script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			
				<div class="login-container">
				<% if(loginMember == null) { %>
							<!-- 로그인폼 시작 -->
					<form id="loginFrm" action="<%=request.getContextPath() %>/member/login" method="POST">
						<table>
							<tr>
								<td><input type="text" name="memberId" id="memberId" placeholder="아이디" tabindex="1" value="<%= saveId != null ? saveId : "" %>"></td>
								<td><input type="submit" value="로그인" tabindex="3"></td>
							</tr>
							<tr>
								<td><input type="password" name="password" id="password"
								placeholder="비밀번호" tabindex="2"></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="checkbox" name="saveId" id="saveId" <%= saveId != null? "checked" : "" %>/>
									<label for="saveId">아이디저장</label>&nbsp;&nbsp;
									<input type="button" value="회원가입" onclick="location.href='<%= request.getContextPath() %>/member/memberEnroll'">
								</td>
							</tr>
						</table>
					</form>
					<!-- 로그인폼 끝-->
					<% } else{ %>
						<%-- 로그인 성공 시 --%>
						<table id="login">
						<tr>
							<td><%= loginMember.getMemberName() %> 님, 안녕하세요.</td>
						</tr>
						<tr>
							<td>
								<input type="button" value="내정보보기" onclick="location.href='<%= request.getContextPath() %>/member/memberView';" />
								<input type="button" value="로그아웃"
									onclick="location.href='<%= request.getContextPath() %>/member/logout';" />
							</td>
						</tr>
						</table>
					
					<% } %>
				</div>
				<!-- 메인메뉴 시작 -->
				<nav>
					<ul class="main-nav">
						<li class="home"><a href="<%= request.getContextPath() %>">Home</a></li>
						<li class="notice"><a href="#">공지사항</a></li>
						<li class="board"><a href="<%= request.getContextPath() %>/board/boardList">게시판</a></li>
						<%-- 관리자로그인시에만 관리자메뉴 노출 --%>
						<% if(loginMember!=null && MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())){ %>
						<li class="members"><a href="<%= request.getContextPath() %>/admin/memberlist">회원관리</a></li>
						<% } %>
					</ul>
				</nav>
				<!-- 메인메뉴 끝-->
		</header>
		
		<section id="content">