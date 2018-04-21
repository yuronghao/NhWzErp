<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>title</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
<style type="text/css">
	html,body{
	height:100%;
	margin: 0px;
}


#container{
	width:100%;
	height:100%;
}
.welcome{
	width: 100%;
	height: 100%;
	
	background-position: center center;
}
	
</style>
</head>

<script type="text/javascript">
	/* $(document).ready(function(){
		
		$.ajax({
			  type: 'POST',
			  url: "${ctx}/style!findStyle.action",
			  success: function(req){
				  var reqdata=eval("(" + req+ ")");
				  var filedirname=reqdata.name;
				  var dataJson=eval("(" + reqdata.dataJson+ ")");
				  var logoimg=dataJson.wel;
				  if(logoimg=="y"){
					  $('#welcome').css('background-image','url("${ctx }/demo/'+filedirname+'/welcom.jpg")');
				  }else{
					  $('#welcome').css('background-image','url("${ctx}/images/index/firstpicture.jpg")');
						  
				  }
				  
			  },
			});
		}); */
	</script>

<body>
	<div id="container" style="width: 100%;height: 100%">
		<div class="welcome" id="welcome" style="background-image: url('${ctx}/img/welcome.png')"></div>
	</div>
</body>
</html>
