<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账期</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		
		$(function(){
			$('#saveBtn').click(function(){
				
				var editFlag=$("#editFlag",document.getElementById("leftframe").contentDocument).val();
				
				if(editFlag==1){
					
					$.ajax({
						
						 type: "post",
						 url: "${ctx}/wms/accountPeriod_uptAccountPeriod.emi",
						 data:$("#myform",document.getElementById("rightframe").contentDocument).serialize(),
						 
						 dataType:"json",
						 success: function(msg){

							 if(msg.success==1){
								  $.dialog.alert_s('保存成功',function(){
									  window.location.href="${ctx }/wms/accountPeriod_getAccountPeriod.emi";
								  });
							 }
						 	
						 }
						
					});
					
				}else{
					$.ajax({
						
						 type: "post",
						 url: "${ctx}/wms/accountPeriod_addAccountPeriod.emi",
						 data:$("#myform",document.getElementById("rightframe").contentDocument).serialize(),
						 
						 dataType:"json",
						 success: function(msg){

							 if(msg.success==1){
								 
								  $.dialog.alert_s('保存成功',function(){
									  window.location.href="${ctx }/wms/accountPeriod_getAccountPeriod.emi";
								  });
							 }
						 	
						 }
						
					});
				}
				
			});
		});
	
	
	</script>
	
	




<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<!--<li class="fl"><input type="button" class="backBtn" value="返回" onclick="history.go(-1)"> </li>-->
	 			<li class="fl"><input type="button" class="saveBtn" id="saveBtn" value="保存" disabled="disabled"> </li>
<!-- 	 			<li class="fl"><a href="addperdoninfo.html"> <input type="button" class="addBtn"  value="新增"> </a></li>
	 			<li class="fl"><input type="button" class="editBtn" value="编辑" /> </li>
	 			<li class="fl"><input type="button" class="delBtn" value="停用"> </li> -->
	 			<div class="cf"></div>
	 		</ul>
	 	</div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">会计期间设置</div>	
	 		<div class="mainaddword" >
	 			<div class=" fl" style="width:17%;">
	 				<iframe height="100%" frameborder="0" width="100%" id="leftframe" src="${ctx }/wms/accountPeriod_getAccountPeriodTree.emi"></iframe>
	 			</div>
	 			
	 			
	 			<div class="creattable fl" style="width:81%;margin-top: 20px">
	 				<iframe height="100%" frameborder="0" width="100%" id="rightframe" name="rightframe" src="${ctx }/wms/accountPeriod_getRightPeriod.emi"></iframe>
	 			</div>
	 			
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>


</body>
</html>