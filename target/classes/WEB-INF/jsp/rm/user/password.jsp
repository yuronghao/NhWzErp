<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="robots" content="index,follow" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/common/common.css">
<style type="text/css">
  .labelName_pwd {
	width: 100px;
	margin-right: 20px;
	margin-left: 30px;
	text-align: right;
	margin-top: 8px;
	color: #2b6c94;
  }
</style>
</head>

<script type="text/javascript">

</script>
<body>
	<div id="container">
		<div id="content">
			<form action="${ctx }/login_modifyPassword.emi" method="post"  class="myform" >
				<!--内容部分-->
		              <div class="createhead">
		                <table style="width: 100%">
		                
		                  <tr>
		                    <td width="140px" height="30" align="right"><span class="labelName_pwd fl">原密码</span></td>
		                    <td width="280px" align="left"><input style="width: 150px" class="fl " type="password"  name="oldPassword" id="oldPassword" value="">
		                    </td>
		                  </tr>
		                   <tr>
		                    <td width="140px" height="30" align="right"><span class="labelName_pwd fl">新密码</span></td>
		                    <td width="280px" align="left"><input style="width: 150px" class="fl " type="password"  name="newPassword" id="newPassword" value="">
		                    
		                    </td>
		                  </tr>
		                  <tr>
		                    <td width="140px" height="30" align="right"><span class="labelName_pwd fl">确认密码</span></td>
		                    <td width="280px" align="left"><input style="width: 150px" class="fl " type="password"  name="confirmPassword" id="confirmPassword" value="">
		                    
		                    </td>
		                  </tr>
		                  
		                </table>
              </div></form>
              
		</div>
		
	</div>


</body>
<script type="text/javascript">



</script>
</html>