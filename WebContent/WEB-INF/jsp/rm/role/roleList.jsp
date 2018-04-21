<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	 <!--设置content宽度-->
	<script type="text/javascript">

	//更新角色
	function addRole(){
		window.location.href="${ctx}/role_toAddRole.emi";
	}
	//更新角色
	function updateRole(){
		var gid = checkSelectId('userCheck');
		if(gid!=''){
			window.location.href="${ctx}/role_toUpdateRole.emi?gId="+gid;
		}
	}
	//展示角色详情
	function showRole(id){
		window.location.href="${ctx}/role_toUpdateRole.emi?onlyShow=1&gId="+id;
	}
	//删除角色
	function deleteRole(){
		var gid = checkSelectId('userCheck',true);
		if (gid!='' && confirm(tip_confirmDelete)){
			window.location.href="${ctx}/role_deleteRole.emi?gId="+gid;
		}
	}
	//授权
	function authRole(){
		var gid = checkSelectId('userCheck');
		if(gid!=''){
			window.location.href="${ctx}/role_toRoleAuth.emi?gid="+gid;
		}
	}
	
	//checkbox点击事件
	function clickCheck(){
		var allchecked = true;
		var uck = document.getElementsByName('userCheck');
		var allck = document.getElementById('all');
		for(var i=0;i<uck.length;i++){
			if(!uck[i].checked){
				allchecked = false;
			}
		}
		if(allchecked){
			allck.checked=true;
		}else{
			allck.checked=false;
		}
		
	}
	
	//全选按钮
	function selectAll(){
		var ckAll = document.getElementById('all');
		var isck = ckAll.checked;
		var uck = document.getElementsByName('userCheck');
		for(var i=0;i<uck.length;i++){
			uck[i].checked=isck;
		}
	}
</script>

</head>
<body>
	<form action="${ctx}/role_roleList.emi" method="post" >
		<div class="EMonecontent" style="padding-top: 10px">
			<!--按钮栏-->
			<div class="toolBar">
				<ul>
		 			<li class="fl"><input type="button" class="addBtn" value="新增" onclick="addRole()"></li>
		 			<li class="fl"><input type="button" class="editBtn" value="修改" onclick="updateRole()"></li>
		 			<li class="fl"><input type="button" class="editBtn" value="授权" onclick="authRole()"></li>
		 			<li class="fl"><input type="button" class="delBtn" value="删除" onclick="deleteRole()"></li>
		 			<!-- 导出，代码1、复制该按钮onclick统一事件 2、form跳转的action中调用导出方法 -->
		 			<!-- <li class="fl"><input type="button" class="editBtn" value="导出" onclick="emi_export()"></li> -->
		 			<div class="cf"></div>
		 		</ul>
			</div>
			<!--内容部分-->
			<div class="creattable">
		 		<div class="tabletitle">角色列表</div>		 		
		 		<div class="createdList">
				<table>
					<tbody>
						<tr>
							<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
							<th exp_column="gid" width="5%" style="text-align: center;">序号</th>
							<th exp_column="roleName" width="90%" style="text-align: center;">角色名称</th>
						</tr>
						<c:forEach var="role" items="${data.list }" varStatus="stat">
							<tr>
								<td><input type="checkbox" name="userCheck" value="${role.gid }" onclick="clickCheck();"></td>
								<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
								<td><a href="javaScript:void(0);" onclick="showRole('${role.gid}')">${role.roleName }</a> </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		<!-- ---------------------分页开始--------------------- -->
		<%--如果不使用data作为变量名称，在此增加如下代码： <c:set var="data" value="${data }"></c:set> 这里value中的data改成自定义的名称--%> 
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!-- ----------------------分页结束-------------------- -->
	</form>
</body>
</html>