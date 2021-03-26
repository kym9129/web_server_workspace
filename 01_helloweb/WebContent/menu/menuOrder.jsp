<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String mainMenu = request.getParameter("main_menu");
String sideMenu = request.getParameter("side_menu");
String drinkMenu = request.getParameter("drink_menu");

System.out.println("mainMenu@jsp=" + mainMenu);
System.out.println("sideMenu@jsp=" + sideMenu);
System.out.println("drinkMenu@jsp=" + drinkMenu);

//속성 가져오기
int sum = (int)request.getAttribute("sum");
System.out.println("sum@jsp=" + sum);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>감사합니다.</h3>
	<p><%=mainMenu %>, <%= sideMenu %>, <%=drinkMenu %>을/를 주문하셨습니다.</p>
	<p>총 결제금액은 <span><%= sum %>원</span> 입니다.</p>
</body>
</html>