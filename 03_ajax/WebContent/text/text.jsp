<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - text</title>
<style>
table{
	border-collapse: collapse;
	border: 1px solid #000;
	margin: 5px;
}
th, td{
	border: 1px solid #000;
}
table img{
	width: 150px;
}
</style>
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
</head>
<body>
	<h1>text</h1>
	<input type="button" value="실행" id="btn1" />
	<div class="wrapper"></div>
	<hr />
	<input type="button" value="실행" id="btn2" />
	<div class="container"></div>
	
	
	<script>
	$(btn2).click(function(){
		$.ajax({
			url: "<%= request.getContextPath() %>/csv",
			//method: "GET", //비동기 처리는 주소가 안보여서 GET/POST구분이 무의미
			//dataType: "text", //응답메세지를 보고 알아서 처리해주므로 생략 가능
			//beforeSend, complete콜백함수는 원하면 작성
			success: function(data){
				console.log(data);
				/*
				hwangj,황제성,hwang.jpg
				dafun,다프트펑크,daftpunk.jpg
				jsyoon,유재석,유재석.jpg
				
				객체끼리는 개행문자로 구분 -> 1. 개행문자로 쪼갠다
				필드끼리는 콤마로 구분 -> 2. 콤마로 쪼갠다
				*/
				var $table = $("<table></table>");
				var members = data.split("\n");
				members = members.slice(0, members.length - 1); //println이라 마지막 개행 삭제
				console.log(members);
				
				$.each(members, function(index, member){
					console.log(index, member);
					var arr = member.split(",");
					var tr = "<tr>";
					tr += "<td>" + arr[0] + "</td>"; //id
					tr += "<td>" + arr[1] + "</td>"; //이름
					tr += "<td><img src='<%= request.getContextPath() %>/images/" + arr[2] + "' />'</td>"; //이미지경로	
					tr += "</tr>";
					$table.append(tr);
				});
				
				$(".container").html($table);
				
				
			},
			error: function(xhr, status, err){
				console.log(xhr, status, err);
			}
		});
	});
	
	
	
	
	
	
	
	
	
	$(btn1).click(function(){
		//ajax 함수 호출. 인자로 객체를 담는다.
		$.ajax({
			url: "<%= request.getContextPath() %>/text",
			//data: "name=podo&num=12345", //직렬화된 형태
			data: {
				name: "strawberry",
				num: 34.56,
				flag: false
			},
			method: "POST", //GET이 기본값(생략 가능)
			dataType: "text", //응답 데이터 형식 지정(text, html, xml, json, ...)
			beforeSend: function(){
				//전송 전 실행 콜백함수
				console.log("beforeSend call!");
			},
			success: function(data){
				//요청 성공 시 실행 콜백함수. 매개인자로 응답메세지 리턴
				console.log("success call!");
				console.log(data);
				$(".wrapper").html(data);
			},
			error: function(xhr, status, error){
				//요청 오류 시 실행 콜백함수
				console.log("error call!");
				console.log(xhr, status, error);
			},
			complete: function(){
				// 요청 성공/오류 상관없이 무조건 마지막에 호출되는 콜백함수
				console.log("complete call!");
			}
		});
	});
	</script>
</body>
</html>