<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--
	jsp 주석
	jsp : java + html
	jsp의 모든 자바코드 <%..%>는 모두 서버단에서 처리되고,
	그 결과만 html에 반영된다.
 --%>

<%@ page import="java.util.Arrays" %>
<%
	//jsp scriptlet : 자바공간
	System.out.println(123);
	//웹페이지 실행 시 콘솔에 찍힌다

	//사용자 입력값 가져오기
	//foward()에서 request, response객체를 파라미터로 전달받았기 때문에
	//request, response에 선언 없이 접근 가능
	String name = request.getParameter("name");
	String color = request.getParameter("color");
	String animal = request.getParameter("animal");
	String[] foodArr = request.getParameterValues("food");
	
	System.out.println("name@jsp = " + name);
	System.out.println("color@jsp = " + color);
	System.out.println("animal@jsp = " + animal);
	System.out.println("foodArr@jsp = " + Arrays.toString(foodArr));
	
	//저장된 속성 가져오기
	//Object->String으로 형변환
	String recommendation = (String)request.getAttribute("recommendation");
	System.out.println("recommendation@jsp = " + recommendation);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.recommendation{
		font-size: 2em; 
		color: green; 
		text-decoration: underline;
	}
</style>
</head>
<body>
<body>
    <h1>개인 취향 검사 결과 jsp</h1>
    <p><%= name %>님의 개인취향 검사 결과는</p>
    <p><%= color %>색을 좋아합니다.</p>
    <p>좋아하는 동물은 <%= animal %>입니다.</p>
    <p>좋아하는 음식은 <%= Arrays.toString(foodArr) %>입니다.</p>
    <hr>
    <p class='recommendation'>오늘은 <%= recommendation %> 어떠세요?</p>
</body>
</html>