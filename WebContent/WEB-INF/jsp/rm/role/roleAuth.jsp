<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>创建新企业</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="robots" content="index,follow" />

<link rel="stylesheet" type="text/css" href="${ctx }/css/common.css">
<link rel="stylesheet" href="${ctx }/css/emicom.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/scripts/plugins/treetable/css/jquery.treetable.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/scripts/plugins/treetable/css/jquery.treetable.theme.default.css">
<script type="text/javascript"
	src="${ctx}/scripts/plugins/treetable/jquery.treetable.js"></script>
<style type="text/css">
.Name1 {
	color: #2b6c94;
	font-weight: bold;
	font-size: 14px;
	padding-left: 30px;
}
</style>
<!--设置content宽度-->
<script type="text/javascript">
	function doBack() {
		window.location.href = "${ctx}/role_roleList.emi";
	}

	$(function() {
		//普通table转成treetable
		$('#createdTable').treetable({
			expandable : true,
			clickableNodeNames:true
		});
		$('#createdTable').treetable('expandAll');
		//checkbox的初始化
		var rights = "${rightFunctions}";
		var rightFuns = rights.split(";");
		for(var i=0;i<rightFuns.length;i++){
			var rf = rightFuns[i].split(",");
			var rId = rf[0];
			var funVal = rf[1]-0;
			var roleFun = rf[2]-0;
			var funItems = document.getElementsByName(rId+"_fun");
			for(var n=0;n<funItems.length;n++){
				if(!(funVal&Math.round(Math.pow(2,n+1)))){
					document.getElementById(rId+"_fun_"+(n+1)).disabled=true;
				}
				if(roleFun&Math.round(Math.pow(2,n+1))){
					document.getElementById(rId+"_fun_"+(n+1)).checked=true;
				}
			}
			if(roleFun&1){
				document.getElementById(rId+"_fun_0").checked=true;
			}
		}
	});
	
	function saveAuth(){
		var rightFuns = "";
		var ck = document.getElementsByName("rightShow");
		for(var i=0;i<ck.length;i++){
			var rId = ck[i].value;
			var funs = 0;
			if(ck[i].checked){
				funs += 1;//查看权
			}
			var funItems = document.getElementsByName(rId+"_fun");
			for(var n=0;n<funItems.length;n++){
				var f_n = document.getElementById(rId+"_fun_"+(n+1));
				if(f_n.checked){
					funs += f_n.value-0;
				}
			}
			rightFuns += rId+","+funs+";";
		}
		if(rightFuns.length>0){
			rightFuns = rightFuns.substring(0,rightFuns.length-1);
		}else{
			$.dialog.alert_e('请选择权限');
			return;
		}
		$.ajax({
			  data: {roleId:'${role.gid}',rightFuns:rightFuns},
			  type: 'POST',
			  url: '${ctx}/role_saveRoleRight.emi',
			  success: function(req){
				  if(req=='success'){
					  $.dialog.alert_s('保存成功',function(){
						  
					  });
				  }else{
					  $.dialog.alert_e("保存失败");
				  }
			  }
		});
	}
	//全选/全不选
	function selectAll(statusIndex){
		var all = document.getElementById("select_all_"+statusIndex);
		var beCheck = all.checked;
		var checks = document.getElementsByTagName("input");
		for(var i=0;i<checks.length;i++){
			var e = checks[i];
			if(e.getAttribute("type")=="checkbox" &&e.getAttribute("ckIndex")==statusIndex && !e.disabled){
				e.checked = beCheck;
			}
		}
	}
</script>

</head>
<body >
	<form action="${ctx}/role_roleList.emi" method="post" id="myform">
		<div class="EMonecontent" style="margin-top: 10px">
			<div class="toolBar">
				<ul>
		 			<li class="fl"><input class="saveBtn" type="button" value="保存" onclick="saveAuth();" /></li>
		 			<li class="fl"><input class="backBtn" type="button" value="返回" onclick="doBack();" /></li>
		 			<div class="cf"></div>
		 		</ul>
			</div>
			<!--内容部分-->
			<div class="creattable">
				<div class="tabletitle">角色授权：${role.roleName }</div>		 		
				<!--列表部分-->
				<div class="createdList">
					<table id="createdTable" >
						<tbody>
							<tr>
								<th width="28%" style="text-align: center;">模块名称</th>
								<c:if test="${rangeSwitch=='on' }">
									<th width="18%" style="text-align: center;">查看</th>
									<th width="18%" style="text-align: center;display: none">新增</th>
									<th width="18%" style="text-align: center;display: none">修改</th>
									<th width="18%" style="text-align: center;display: none">删除</th> 
									<th width="18%" style="text-align: center;">表单自定义</th>
									<th style="text-align: center;"></th>
								</c:if>
								<!-- 不控制增删改等 -->
								<c:if test="${rangeSwitch!='on' }">
									<th width="18%" style="text-align: center;">授权</th>
									<th width="54%" style="text-align: center;"></th>
								</c:if>
							</tr>
							<c:forEach var="item" items="${allRights }" varStatus="stat">
								<tr data-tt-id="${item.shortName }" >
									<td style="text-align: left">
										<span class='folder'>${item.systemName }</span>
									</td>
									<c:if test="${rangeSwitch=='on' }">
									<td colspan="3"></td>
									</c:if>
									<!-- 不控制增删改等 -->
									<c:if test="${rangeSwitch!='on' }">
									<td>
										<span class="Name1"><input type="checkbox" id="select_all_${stat.index }" onclick="selectAll('${stat.index }');" style="min-width: 30px">全选</span>
									</td>
									<td>
									</td>
									</c:if>
								</tr>
								<c:forEach var="right" items="${item.list }" varStatus="stat2">
									<c:if test="${ right.superiorRightId=='' || right.superiorRightId eq null}">
										<tr data-tt-id="${right.gid }" data-tt-parent-id="${item.shortName  }" >
									</c:if>
									<c:if test="${right.superiorRightId ne null && right.superiorRightId!='' }">
										<tr data-tt-id="${right.gid }" data-tt-parent-id="${right.superiorRightId }" >
									</c:if>
										<td style="text-align: left">
											<c:if test="${right.isLast==1 }"><span class='file'>${right.rightName }</span></c:if>
											<c:if test="${right.isLast==0 }"><span class='folder'>${right.rightName }</span></c:if>
										</td>
										<td ><input type="checkbox" ckIndex="${stat.index }" name="rightShow" id="${right.gid }_fun_0" value="${right.gid }"></td>
										
										<c:if test="${rangeSwitch=='on' }">
										<td style="display: none"><input type="checkbox" name="${right.gid }_fun" id="${right.gid }_fun_1" value="2"></td>
										<td style="display: none"><input type="checkbox" name="${right.gid }_fun" id="${right.gid }_fun_2" value="4"> </td>
										<td style="display: none"><input type="checkbox" name="${right.gid }_fun" id="${right.gid }_fun_3" value="8"></td>
										<td >					  <input type="checkbox" name="${right.gid }_fun" id="${right.gid }_fun_4" value="16"></td>
										</c:if>
										<!-- 不控制增删改等 -->
										<c:if test="${rangeSwitch!='on' }">
										<td ></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="cf"></div>
			</div>
		</div>
	</form>
</body>
</html>