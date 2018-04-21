<!DOCTYPE html >
<%@page import="com.emi.common.bean.core.TreeType"%>
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
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
</head>
<script type="text/javascript">

function saveProcess(){
	$.ajax({
		data: $("#myform").serialize(),
		type: 'POST',
		url: '${ctx}/wms/costItem_addcostItem.emi',
		success: function(req){
			if(req=='success'){
				$.dialog.alert_s('保存成功',function(){
					location.href="${ctx}/wms/costItem_getcostItemList.emi";
				});
				
			}else if(req=='costItemCode'){
				$.dialog.alert_e('成本项目编码不得为空');
			}else if(req=='costItemName'){
				$.dialog.alert_e('成本项目名称不得为空');
			}else if(req=='priorattributeGid'){
				$.dialog.alert_e('成本类型不得为空');
			}else if(req=='allotRateGid'){
				$.dialog.alert_e('成本要素分配率不得为空');
			}else if(req=='error'){
				$.dialog.alert_e('保存失败');
			}
		},
		error:function(){
			$.dialog.alert_e('error');
		}
	});
}

function selectpriorattribute(){
	var pwdWin = $.dialog({ 
		drag: true,
		lock: true,
		resize: false,
		title:'选择成本项目类型',
	    width: '800px',
		height: '400px',
		zIndex:3004,
		content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PRIORATTRIBUTE%>&multi=0&showTree=0',
		okVal:"确定",
		ok:function(){
			var usercheck = this.content.window.jsonArray;
			document.getElementById('priorattributeName').value=usercheck[0].name;
			document.getElementById('priorattributeGid').value=usercheck[0].gid;
		},
		cancelVal:"关闭",
		cancel:true
	});	
}

function selectpriorattributes(){
	var pwdWin = $.dialog({ 
		drag: true,
		lock: true,
		resize: false,
		title:'选择成本项目类型',
	    width: '800px',
		height: '400px',
		zIndex:3004,
		content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PRIORATTRIBUTE%>&multi=0&showTree=0',
		okVal:"确定",
		ok:function(){
			var usercheck = this.content.window.jsonArray;
			document.getElementById('allotRateName').value=usercheck[0].name;
			document.getElementById('allotRateGid').value=usercheck[0].gid;
		},
		cancelVal:"关闭",
		cancel:true
	});	
}
</script>
<body>
	<form action="" method="post"  class="myform" id="myform" onsubmit="return checkdata()">
		<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" class="saveBtn" value="保存" onclick="saveProcess()"> </li>
	 			<li class="fl"><input type="button" class="backBtn" value="返回" onclick="window.history.go(-1)"></li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="creattable">
	 		<div class="tabletitle">新增成本项目</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>成本项目编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="costItemCode" id="costItemCode" maxlength="20"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>成本项目名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="costItemName" id="costItemName" maxlength="50"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>成本类型：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="priorattributeName" name="priorattributeName" class="toDealInput" readonly style="width:260px;height:25px;line-height: 25px;" onclick="selectpriorattribute()">
							<input type="hidden" id="priorattributeGid" name="priorattributeGid"> 
						</div>
							<div class="cf"></div> 
		 				</li>
		 				<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>成本要素分配率：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="allotRateName" name="allotRateName" class="toDealInput" readonly style="width:260px;height:25px;line-height: 25px;" onclick="selectpriorattributes()">
							<input type="hidden" id="allotRateGid" name="allotRateGid"> 
						</div>
							<div class="cf"></div> 
		 				</li>
		 				<li>
						<div style="width:45%;text-align:right;" class="fl">备注：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="note" name="note"  style="width:260px;height:25px;line-height: 25px;">
						</div>
							<div class="cf"></div> 
		 				</li>
	 				<!-- <li>
	 					<div style="width:45%;text-align:right;" class="fl">设备类型：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="costItemStyle" id="costItemStyle" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">设备规格：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="costItemSpe" id="costItemSpe" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li> -->
	 				<!-- <li>
	 					<div style="width:45%;text-align:right;" class="fl">所属工作中心：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="workCenter" id="workCenter" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li> -->
	 			</ul>
	 			<div style="height:10px;"></div>
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	</div>
		
	</form>
              
</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<2;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog.alert('内容填写不完整');
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>