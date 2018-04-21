<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<%@page import="com.emi.common.bean.core.TreeType"%>
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

$(function() {

	var width = $('#createwords').width();
	$('#content').css('width', width + 10 + "px");

});

/*
 * 打开用户选择界面
 */
function showUser(){
	var roleId="${role.gid}";
	var selectedUserIds = document.getElementById("userIds").value;
	var pwdWin = $.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'角色人员',
	    width: '800px',
		height: '400px',
		content: 'url:role_toSelectUser.emi?roleId='+roleId+"&selectedUserIds="+selectedUserIds, 
		
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

	//选择仓库
	function fn_chooseWareHouse(){
		var roleId="${role.gid}";
		var selectedIds = document.getElementById("wareHouseIds").value;
		$.dialog({ 
		drag: true,
		lock: false,
		resize: true,
		title:'选择仓库',
	    width: '800px',
		height: '500px',
		zIndex:3000,
		content: 'url:role_toSelectWareHouse.emi?roleId='+roleId+"&selectedIds="+selectedIds,
		okVal:"确定",
		ok:function(){
			var usercheck = this.content.document.getElementsByName('warehouseCheck');
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
			document.getElementById("wareHouseIds").value=userIds;
			document.getElementById("wareHouseNames").value=userNames;
		},
		cancelVal:"关闭",
		cancel:true
	});
	}
</script>
<body>
	<form action="${ctx }/role_addRole.emi" method="post"  class="myform"  onsubmit="return checkdata()">
		<s:token ></s:token > 
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<li class="fl"><input type="button" class="backBtn" value="返回" onclick="window.history.go(-1)"></li>
		 			<li class="fl"><input type="submit" class="saveBtn" value="保存"> </li>
		 			<div class="cf"></div>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--主体部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">新增角色</div>		 		
		 		<div class="xz_attribute">
		 			<ul>
		 				<li>
		 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>角色名称：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" value="" name="role.roleName" id="roleName"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>角色代码：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" value="" name="role.roleCode" id="roleCode"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div style="width:45%;text-align:right;" class="fl">分配用户：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<textarea name="userNames" id="userNames"  style="width:260px; height: 50px;" readonly="readonly"></textarea>
		                    	<img alt="添加用户" src="${ctx }/img/add.png" onclick="showUser();">
		                    	<input type="hidden" name="userIds" id="userIds" value="${userIds }">
		 					</div>
		 					<div style="width:45%;text-align:right;" class="fl">分配仓库：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
								<textarea name="wareHouseNames" id="wareHouseNames" style="width:260px; height: 50px;" readonly="readonly" ></textarea>
								<img alt="" src="${ctx }/img/add.png" style="cursor: pointer;" onclick="fn_chooseWareHouse()"></img>
								<input type="hidden" name="wareHouseIds" id="wareHouseIds" value="">
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
	    	  $.dialog({
	    		    title: '系统提示',
	    		    drag: false,
	    		    resize: false,
	    		    lock: true,
	    		    background: '#000', /* 背景色 */
	    		    opacity: 0.5,       /* 透明度 */
	    		    content: '内容填写不完整',
	    		    icon: 'error.gif',
	    		    ok:true
	    		});
	    	  
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>