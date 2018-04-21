<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<style>
	.wordname{
		width:125px;
	}
	.wordli{width:32%;}
	.wordnameinput{width:56%;}
</style>
</head>

<script type="text/javascript">

function checkdata()
{
	if(document.getElementById('depcode').value==""){
		$.dialog.alert_w("部门编码不能为空!");
	  	return false;
	}else if(document.getElementById('depname').value==""){
		$.dialog.alert_w("部门名称不能为空!");
	  	return false;
	}
	else
	{ 
		return true;
	}
}
	
	//选择部门
	function selectDepartmet(){
		
	    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'选择部门',
		    width: '400px',
			height: '300px',
			zIndex:2000,
			content: 'url:${ctx}/wms/org_getDepartmentTreeHelp.emi',
			okVal:"确定",
			ok:function(){
				var id = this.content.document.getElementById("id").value;
				var name = this.content.document.getElementById("name").value;
				
				document.getElementById('depparentname').value=name;
				document.getElementById('depparentuid').value=id;
				
			},
			cancelVal:"关闭",
			cancel:true
		});	

	}
	
	
</script>




<body>
	<div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
<!-- 	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" class="backBtn" value="返回"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div> -->

		<form id="myform" method="post" action="" onsubmit="return checkdata()">
		 <div class="EMonecontent">

		 	<!--主体部分-->
		 	<div class="mainword">
		 		<div class="mainaddword" style="border:solid 1px white">
		 			<ul class="EMtree_word fl" style="width: 55%;">
		 				<li>
		 					<div class="fl mainaddword_name">部门编码：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="depcode" name="depcode" class="addwordinput"  value=""></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">部门名称：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="depname" name="depname" class="addwordinput" value=""></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">部门负责人：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="principal" name="principal" class="addwordinput" value=""></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">上级部门：</div>
		 					<div class="fl mainaddword_word">
		 						<input type="hidden" id="depparentuid" name="depparentuid" value="" class="addwordinput">
		 						<input type="text" id="depparentname" name="depparentname" value="" class="addwordinput">
		 						<img src="${ctx}/img/sousuo.png" onclick="selectDepartmet()">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">备注：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="notes" name="notes" value="" class="addwordinput"></div>
		 					<div class="cf"></div>
		 				</li>
		 				 
		 			</ul>
		 			<div class="cf"></div>
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
		 	 
		</div>
	 	</form>
	 	
	</div>
</body>
</html>