<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.person.model.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String str1 = "안녕";
	String str2 = new String("안녕");
	
	int big = 100;
	int small = 30;
	
	Person p1 = new Person("honngd", "혼길동", '남', 35, true);
	Person p2 = new Person("honngd", "혼길동", '남', 35, true);
	
	List<String> list = null;
	list = new ArrayList<>();
	list.add("hello world");
	
	pageContext.setAttribute("str1", str1);
	pageContext.setAttribute("str2", str1);
	pageContext.setAttribute("big", big);
	pageContext.setAttribute("small", small);
	pageContext.setAttribute("p1", p1);
	pageContext.setAttribute("p2", p2);
	pageContext.setAttribute("list", list);
	
	

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Operator</title>
</head>
<body>
	<h1>El Operator</h1>
	<h2>산술연산</h2>
	<p>${big + small }</p>
	<p>${big - small }</p>
	<p>${big - '20' }</p>
<%-- 	<p>${big - 'ab' }</p> : NumberFormatException --%>
	<p>${big * small }</p>
	<p>${big / small } ${big div small }</p>
	<p>${big % small } ${big mod small }</p> 
	
	<h4>문자열 붙이기</h4>
<%-- 	<p>${'str' + 'str' }</p> : : NumberFormatException --%>
	<p>${str1}${str2} ${str1.concat(str2) }</p>
	
	<h2>비교연산</h2>
	<p>${big > small } ${big gt small }</p> <%-- greater than --%>
	<p>${big < small } ${big lt small }</p> <%-- less than --%>
	<p>${big >= small } ${big ge small }</p> <%-- greater than or equal to --%>
	<p>${big <= small } ${big le small }</p> <%-- less than or equal to --%>
	<p>${big == small } ${big eq small }</p> <%-- equal to --%>
	<p>${big != small } ${big ne small }</p> <%-- not equal to --%>
	
	<hr />
	<%-- el의 동등비교연산은 내부적으로 equals를 사용 --%>
	<p><%= str1 == str2 %> ${str1 == str2 }</p> <%-- false / true --%>
	<p><%= str1 != str2 %> ${str1 != str2 }</p> <%-- true / false --%>
	
	<p>${p1 == p2 } ${p1 eq p2 }</p>
	<!-- 객체 null or 비어있는지 여부 -->
	<p>${empty list }</p> <%-- true --%>
	<%-- new ArrayList<>() : true --%>
	<%-- list.add("hello") : false --%>
	<% pageContext.setAttribute("emptyStr", ""); %>
	<p>${empty emptyStr } ${not empty emptyStr}</p> <%-- true / false --%>

</body>
</html>