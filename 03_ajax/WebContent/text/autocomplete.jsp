<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Autocomplete - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
 
<div class="ui-widget">
  <label for="name">우리반 이름 : </label>
  <input id="name">
</div>

<h1 class="selected"></h1>
 
<script>
 
 $( "#name" ).autocomplete({
   source: function(request, response){
// 	   console.log(request);
// 	   console.log(response);
	   
// 	   response([{label:'a', value:'a'}, {label: 'b', value:'b'}]);
// 	   return;
	   $.ajax({
		   url: "<%= request.getContextPath() %>/autocomplete",
		   data: {
			   search: request.term
		   },
		   success: function(data){
			  console.log(data);
			  var arr = data.split("\n");
			  //arr = arr.slice(0, arr.length - 1);
			  arr.splice(arr.length -1, 1); //마지막 개행문자 삭제
			  
			  arr = $.map(arr, function(name){
				  return {
					  label : name, //화면에 노출할 텍스트
					  value : name //내부적으로 처리할 값
				  };
			  });
			  console.log(arr);
			  
			  //콜백함수 response()호출
			  response(arr);
		   },
		   error: function(xhr, status, err){
			   console.log(xhr, status, err);
		   }
	   });
   }, 
   select: function(event, selected){
	   //선택한 값 가져오기 
	   console.log(event);
	   console.log(selected);
	   
	   $("h1.selected").html(selected.item.label);
   }
 });
</script>
 
</body>
</html>