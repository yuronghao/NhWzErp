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
function checkForm()
{
	if(document.getElementById('workCenterName').value==""){
		$.dialog.alert_w("工作中心不能为空!");
	  	return false;
	}
	return true;
}

function saveProcess(){
	if(checkForm()){
		$.ajax({
			data: $("#myform").serialize(),
			type: 'POST',
			url: '${ctx}/wms/group_updategroup.emi',
			success: function(req){
				if(req=='success'){
					$.dialog.alert_s('保存成功',function(){
						location.href="${ctx}/wms/group_getgroupList.emi";
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
function showUser(){
	//var roleId="${role.gid}";
	var selectedUserIds = document.getElementById("userIds").value;
	var pwdWin = $.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'选取人员',
	    width: '800px',
		height: '400px',
		zIndex:5000,
		content: 'url:wms/group_toSelectUser.emi?selectedUserIds='+selectedUserIds,
		ok:function(){
			var usercheck = this.content.document.getElementsByName('userCheck');
			var userIds = "";
			var userNames = "";
			for(var i=0;i<usercheck.length;i++){
				var ckVal = usercheck[i];
				var idAndName = ckVal.value.split(",");
				if(ckVal.checked){
					userIds += idAndName[0]+",";
					userNames += idAndName[1]+",";
				}
			}
			if(userIds.length>0){
				userIds = userIds.substring(0, userIds.length-1);
				userNames = userNames.substring(0, userNames.length-1);
			}
			document.getElementById("userIds").value=userIds;
			document.getElementById("userNames").value=userNames;
		},
		cancel:true
	});	
}
//选择工作中心
function fn_chooseWorkCenter(){
	$.dialog({ 
	drag: true,
	lock: false,
	resize: true,
	title:'选择工作中心',
    width: '800px',
	height: '500px',
	zIndex:3000,
	content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.WORKCENTER%>&multi=0&showTree=0', 
	okVal:"确定",
	ok:function(){
		var returnArray = this.content.window.jsonArray;
		if(returnArray.length>0){
			$('#workCenterId').val(returnArray[0].gid);
			$('#workCenterName').val(returnArray[0].wcname);
		}
	},
	cancelVal:"关闭",
	cancel:true
});
}
</script>
<body>
	<form method="post"  class="myform" id="myform" onsubmit="return checkdata()">
		<input type="hidden" name="gid" id="gid" value="${aagroup.gid }">
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
	 		<div class="tabletitle">修改组</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>组编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="code" id="code" value="${aagroup.code}" maxlength="20"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>组名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="groupname" id="groupname" value="${aagroup.groupname}" maxlength="50"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">条码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="barcode" id="barcode" value="${aagroup.barcode}" maxlength="50">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 				<div style="width:45%;text-align:right;" class="fl">工作中心：</div>
					<input type="text" name="workCenterName" id="workCenterName" style="width:150px" readonly="readonly" value="${aagroup.wcname}"><img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseWorkCenter()"></img>
					<input type="hidden" name="workCenterId" id="workCenterId" value="${aagroup.workcenterId}">
					</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">人员：</div>
	 					<textarea name="userNames" id="userNames" style="border:1px solid #b7d5df;width:260px;height:100px;">${userNames}</textarea>
		                <img alt="添加用户" style="cursor:pointer;" src="${ctx }/img/addPerson.png" onclick="showUser();" style="cursor: pointer">
		                <input type="hidden" name="userIds" id="userIds" value="${userIds}">
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