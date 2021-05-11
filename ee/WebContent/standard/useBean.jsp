<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="honggd" class="com.kh.person.model.vo.Person" scope="application"></jsp:useBean>
<%-- 
	해당 scope에서 id와 동일한 속성명으로 저장된 객체를 가져온다. 
	존재하지 않으면, 해당 타입의 객체를 하나 생성한다.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>useBean</title>
</head>
<body>
	<h1>useBean</h1>
	<table>
		<tr>
			<th>id</th>
			<%--property속성값은 vo객체의 getter에서 get을 제외하고 소문자로 시작하는 나머지 이름 (보통은 필드명과 같음) --%>
			<td><jsp:getProperty property="id" name="honggd" /></td>
		</tr>
		<tr>
			<th>name</th>
			<td></td>
		</tr>
		<tr>
			<th>gender</th>
			<td></td>
		</tr>
		<tr>
			<th>age</th>
			<td></td>
		</tr>
		<tr>
			<th>married</th>
			<td></td>
		</tr>
	</table>
</body>
</html>