<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String life = "is very short!";
    String movie = "노인을 위한 나라는 없다";
    pageContext.setAttribute("life", life);
    pageContext.setAttribute("movie", movie);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL Basics</title>
</head>
<body>
	<h1>El Basics</h1>
	<p>${requestScope.coffee }</p>
	<p>${requestScope.serverTime }</p>
	<p>${requestScope.honngd }</p>
	<p>${requestScope.honngd.id }</p>
	<p>${requestScope.honngd.name }</p>
	<%--
	필드명을 잘못 쓸 경우는 에러 발생
	javax.el.PropertyNotFoundException
	: Property [nickname] not found on type [com.kh.person.model.vo.Person]
	 --%>
<%-- 	<p>${requestScope.honngd.nickname }</p> --%>
	<p>${requestScope.honngd.gender }</p>
	<p>${requestScope.honngd.age }</p>
	<p>${requestScope.honngd.married }</p>
	<p>${sessionScope.book }</p> 
	<p>${applicationScope.movie }</p>
	<p>${pageScope.life }</p>
	<%--
	scope 생략하면 pageScope->request->session->application순으로 뒤져서 찾아낸다
	 --%>
	<p>${book }</p> 
	<p>${movie }</p>
	<%--
	EL은 값이 없어도 NullPointerException이 발생하지 않는다.
	Null인 경우에는 ""출력
	 --%>
	<p>[${cow.run }]</p>
	
	<h2>list</h2>
	<p>${list }</p>
	<p>${list[0] }</p>
	<p>${list[1] }</p>
	<p>${list[2] }</p>
	<p>${list[3] }</p>
	
	<h2>map</h2>
	<p>${map }</p>
	<p>${map.language }</p>
	<p>${map["Dr.zang"]}</p> <%--홑따옴표도 가능 --%>
	<p>${map["Dr.zang"].name}</p>
	<p>${map["Dr.zang"]['name']}</p>
	
	<h1>Param</h1>
	<p>${param.pname }</p>
	<p>${param.pcount }</p>
	<p>${paramValues.option[0] }</p>
	<p>${paramValues.option[1] }</p>
	
	<h1>cookie</h1>
	<p>${cookie.JSESSIONID }</p>
	<p>${cookie.JSESSIONID.value }</p>
	
	<h1>header</h1>
	<p>${header.accept }</p>
	<p>${header["User-Agent"] }</p>
	
	<h1>pageContext</h1>
	<%--
		getPage()
		getRequest()
			getMethod() : GET | POST
			getContextPath()
		getResponse()
		getSession()
		getServletContext()
		getErrorDate()
	 --%>
	<p>${pageContext.request.method }</p>
	<p>${pageContext.request.contextPath }</p>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
</body>
</html>