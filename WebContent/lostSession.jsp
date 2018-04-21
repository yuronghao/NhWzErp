<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 系统根
	String contextPath = request.getContextPath();
%>
<html>
  <head>
    <title></title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script>
	   function reforward(){
	       window.top.window.location.href="<%=contextPath%>/login.jsp";
	   }
	</script>
  </head> 
  <body onload="javascript:reforward();">
  </body>
</html>
