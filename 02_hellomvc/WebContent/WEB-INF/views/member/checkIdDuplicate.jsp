<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String memberId = request.getParameter("memberId");
    boolean available = (Boolean)request.getAttribute("available");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디중복검사</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<style>
div#checkId-container{text-align:center; padding-top:50px;}
span#duplicated{color:red; font-weight:bold;}
</style>
</head>
<body>
	<div id="checkId-container"></div>
	<% if(available){ %>
		<%-- 아이디를 사용 가능한 경우 --%>
		<p>[<%= memberId %>]는 사용 가능합니다.</p>
		<input type="button" value="사용하기" onclick="setMemberId();" />
	<% } else { %>
		<%-- 아이디를 사용 불가한 경우 --%>
		<p>[<span id="duplicated"><%= memberId %></span>]는 이미 사용 중입니다.</p>
		<form name="checkIdDuplicateFrm" action="">
			<input type="text" name="memberId" placeholder="아이디를 입력하세요." />
			<input type="button" value="중복검사" onclick="checkIdDuplicate();" />
		</form>
		
	<% } %>
	<script>
	/*
	* 사용 가능한 아이디를 찾은 경우
		1. 아이디를 부모 윈도우의 #memberId_에 대입
		2. #idValid의 값을 1로 변경
	*/
	function setMemberId(){
		//부모윈도우 : opener
		var $frm = $(opener.document.memberEnrollFrm);
		$frm.find("#memberId_").val('<%= memberId %>');
		$frm.find("#idValid").val(1);
		self.close();
	}

	
	/*
	* 아이디 중복 검사 함수
	*/
	 function checkIdDuplicate(){
			var $memberId = $("[name=memberId]");
			if(/^[a-zA-Z0-9_]{4,}$/.test($memberId.val()) == false){
				alert("유효한 아이디를 입력해주세요.");
				$memberId.select();
				return;
			}
			
			//2. 폼 제출
			$frm = $(document.checkIdDuplicateFrm);
			
			$frm.attr("action", "<%=request.getContextPath()%>/member/checkIdDuplicate")
					.attr("method", "POST")
					.submit();
		}
	</script>
</body>
</html>
