<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${ param.title }</title>
<style>
	header, section, footer{
		border: 1px solid black;
		margin : 8px;
	}
	section{
		height: 500px;
	}
</style>
</head>
<body>
	<header>
		<h1>${ param.title }</h1>	
	</header>
	<section>