<%--page 지시어 directive --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.io.*"%>
<% 
	//jsp 스크립팅 요소 : scriptlet
	int sum = 0;
	for(int i = 1; i <= 10; i++)
	    sum += i;
	
    //현재시각
    Date now = new Date();
	
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp - basic</title>
<script>
	window.onload = function(){
		var sum = 0;
		for(var i = 1; i <= 10; i++){
			sum += i;
		}
		
		document.querySelector("#sum").innerText = sum;
		
 		var date = new Date();
		now.innerText = date + "(" + date.getTime() + ")"; 
		
	};
</script>
</head>
<body>
    <h1>Basic</h1>
    <%--jsp주석은 java파일 변환시 제거된다. --%>
    <!-- html주석은 client까지 전달된다. -->
    <p>java로 계산된 결과 : <%= sum %></p> <%--jsp스크립팅 요소 : 출력식. 세미콜론 찍지말것! --%>
    <p>java로 계산된 결과 : <% out.print(sum); %></p>
    
    <p>client-side : javascript로 계산된 결과 : <span id="sum"></span></p>

	<hr />
	<p>server-side : 현재시각 <%= now %>(<%= now.getTime() %></p>
	<p>client-side : 현재시각 <span id="now"></span></p>
</body>
</html>