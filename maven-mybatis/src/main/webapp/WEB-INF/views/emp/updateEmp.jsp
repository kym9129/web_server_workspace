<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원정보 수정 :: 동적쿼리 set | trim</title>
<!-- 최신 jquery cdn 사용하기 -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
div.wrapper{
	text-align: center;
}
div.update-wrapper{
	background: lightgray;
	width: 500px;
	padding: 20px;
	margin: 0 auto;
}

table#emp{
	margin: 5px auto;
	border-collapse: collapse;
	border: 1px solid;
}
table#emp th, table#emp td{
	border: 1px solid;
}

</style>



</head>
<body>
<div class="wrapper">
<h2>사원정보 수정 :: 동적쿼리 set | trim</h2>

<%-- 조회한 사번이 존재하는 경우 --%>
<table id="emp">
	<tr>
		<th>사번</th>
		<td>${requestScope.emp.empId}</td>
	</tr>
	<tr>
		<th>사원명</th>
		<td>${requestScope.emp.empName}</td>
	</tr>
	<tr>
		<th>직급</th>
		<td>${requestScope.emp.jobName}</td>
	</tr>
	<tr>
		<th>부서</th>
		<td>${requestScope.emp.deptTitle}</td>
	</tr>
</table>
<hr />
<div class="update-wrapper">
	<!-- 
	@실습문제 : 사용자는 원하는 것만 선택한 후 제출하게 된다.
	제출된 컬럼만 update하도록 한다. (mybatis의 set태그 활용할 것)
	 -->
	<form action="${pageContext.request.contextPath }/emp/updateEmp.do" method="post">
		<input type="hidden" name="empId" value="${param.empId}" />
	   	직급: 
	    <select name="jobCode">
	    	<option value="">선택</option>
	    	<c:forEach items="${jobList}" var="job">
	    	<option value="${job.jobCode}">${job.jobName}</option>
	    	</c:forEach>
		</select>
	   	부서: 
	    <select name="deptCode">
	    	<option value="">선택</option>
	    	<c:forEach items="${deptList}" var="dept">
	    	<option value="${dept.deptId}">${dept.deptTitle}</option>
	    	</c:forEach>
		</select>

	    <input type="submit" value="수정" />
	</form>
</div>

	
</div>
<script>
$("form").on('submit', function(e){
	
	var selectedJobCode = $("select[name=jobCode] option:selected").val();
	var selectedDeptId = $("select[name=deptCode] option:selected").val();
	
	var $inputJobCode = $("<input type='hidden' name='jobCode'></input>").val(selectedJobCode);
	var $inputDeptId = $("<input type='hidden' name='deptId'></input>").val(selectedDeptId);
	$(this).append($inputJobCode).append($inputDeptId);
	
	if(selectedJobCode === '' || selectedDeptId === ''){
		e.preventDefault();
		return false;
	}
	
});
</script>
</body>
</html>
