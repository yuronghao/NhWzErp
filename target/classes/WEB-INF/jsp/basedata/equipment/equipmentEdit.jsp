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
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
</head>
<script type="text/javascript">

function saveProcess(){
	$.ajax({
		data: $("#myform").serialize(),
		type: 'POST',
		url: '${ctx}/wms/equipment_updateequipment.emi',
		success: function(req){
			if(req=='success'){
				$.dialog.alert_s('保存成功',function(){
					location.href="${ctx}/wms/equipment_getequipmentList.emi";
				});
				
			}else if(req=='error'){
				$.dialog.alert_e('保存失败');
			}
		},
		error:function(){
			$.dialog.alert_e('error');
		}
	});
}

function selectdep(){
	var pwdWin = $.dialog({ 
		drag: true,
		lock: true,
		resize: false,
		title:'选择部门',
	    width: '400px',
		height: '400px',
		zIndex:3004,
		content: 'url:${ctx}/wms/basepd_getselectdep.emi',
		okVal:"确定",
		ok:function(){
			var name = this.content.document.getElementById("name").value;
			var id = this.content.document.getElementById("id").value;
			document.getElementById('depName').value=name;
			document.getElementById('depUid').value=id;
		},
		cancelVal:"关闭",
		cancel:true
	});	
}
</script>
<body>
	<form method="post"  class="myform" id="myform" onsubmit="return checkdata()">
		<input type="hidden" name="equipmentId" id="equipmentId" value="${equipment.gid }">
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
	 		<div class="tabletitle">修改标准工序</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>设备编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="equipmentCode" id="equipmentCode" value="${equipment.equipmentcode}" maxlength="20"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>设备名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="equipmentName" id="equipmentName" value="${equipment.equipmentname}" maxlength="50"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">设备类：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="SBL" id="SBL" value="${equipment.SBL}" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<%-- <li>
	 					<div style="width:45%;text-align:right;" class="fl">设备类型：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="equipmentStyle" id="equipmentStyle" value="${equipment.equipmentstyle}" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">设备规格：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="equipmentSpe" id="equipmentSpe" value="${equipment.equipmentspe}" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li> --%>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">部门名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="depName" id="depName" value="${department.depname}"  maxlength="200" onclick="selectdep()">
	 						<input type="hidden" id="depUid" name="depUid" value="${department.gid}" >
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<%-- <li>
	 					<div style="width:45%;text-align:right;" class="fl">所属工作中心：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="workCenter" id="workCenter" value="${equipment.workcenter}"  maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li> --%>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">状态：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<select id="equipstatus" name="equipstatus" style="width:100px;">
	 						<option value="">请选择</option>
	 						<option value="0"<c:if test="${equipment.equipstatus==0}">selected='selected'</c:if>>正常</option>
	 						<option value="1"<c:if test="${equipment.equipstatus==1}">selected='selected'</c:if>>维修</option>
	 						<option value="2"<c:if test="${equipment.equipstatus==2}">selected='selected'</c:if>>停用</option>
	 						</select>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">条码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="barcode" id="barcode" value="${equipment.barcode}" maxlength="20">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
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