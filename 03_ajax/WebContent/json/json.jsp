<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - json</title>
<script src = "<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
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

</head>
<body>
	<h1>JSON</h1>
	<div>
	<button type="button" id="btn">실행</button>
	</div>
	<div>
		<input type="search" name="" id="searchId" placeholder="id검색" />
		<button type="button" id="btn-search-id">검색</button>
	</div>
	<div>
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="id"/></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<button id="btn-save-member">회원가입</button>
				</td>
			</tr>
		</table>
	</div>
	<div class="wrapper"></div>
</body>

<script>
$("#btn-save-member").click(function(){
	$.ajax({
		url: "<%=request.getContextPath()%>/json",
		method: "post",
		data: { //url에 보낼 data
			id : $("[name=id]").val(),
			name : $("[name=name]").val()
		},
		success: function(data){
			console.log(data);
			
		},
		error: function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
});

$("#btn-search-id").click(function(){
	$.ajax({
		url: "<%=request.getContextPath()%>/json",
		data: { //url에 보낼 data
			searchId : $("#searchId").val()
		},
		success: function(data){
			console.log(data);
			
			if(data != null){
				//data표시
				var $table = $("<table></table>");
				$table.append(`<tr><th>아이디</th><td>\${data.id}</td></tr>`) //ec6문법
					.append(`<tr><th>이름</th><td>\${data.name}</td></tr>`)
					.append(`<tr><th>프로필</th><td><img src="<%= request.getContextPath() %>/images/\${data.profile}"></td></tr>`);
				$(".wrapper").html($table);
				
				
			}else{
				alert("해당 id는 존재하지 않습니다.");
				$("#searchId").select();
			}
			
		},
		error: function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
});


$(btn).click(function(){
	$.ajax({
		url: "<%=request.getContextPath()%>/json",
		success: function(data){
			console.log(data);
			
			var $table = $("<table></table>");
			
			$(data).each(function(index, member){
			var $tr = $("<tr></tr>");
				console.log(index, member);
				$tr.append("<td>"+(member.id)+"</td>")
					.append("<td>"+(member.name)+"</td>")
					.append("<td><img src='<%= request.getContextPath() %>/images/"+(member.profile)+"'/></td>")
					.appendTo($table);
			});
			$("body").append($table);
			
			
		},
		error: function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
});
</script>
</html>