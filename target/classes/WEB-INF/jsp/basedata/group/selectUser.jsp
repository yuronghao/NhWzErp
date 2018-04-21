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

	<!-- 分页引用的文件 -->
	
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />

	 <!--设置content宽度-->
	<script type="text/javascript">
	$(function() {

		var width = $('#createdTable').width();
	
		$('#content').css('width', width+10+"px");

	});
	//点击列表功能，弹出对话框
	$(function(){
		$('tr > td.toolTd').click(function(){
			$(this).children().show();
			$('.tools').children().mouseover(function(){
				$(this).addClass('liEffect');
			});

			$('.tools').children().mouseleave(function(){
					$(this).removeClass('liEffect');
			});		
		});
		
		$('tr > td.toolTd').mouseleave(function(){
			$(this).children('.tools').hide();
		});				

	});

	//更新角色
	function addRole(){
		window.location.href="${ctx}/role_toAddRole.emi";
	}
	//更新角色
	function updateRole(id){
		window.location.href="${ctx}/role_toUpdateRole.emi?gId="+id;
	}

	//展示角色详情
	function showRole(id){
		window.location.href="${ctx}/role_showRole.emi?gId="+id;
	}
	//删除角色
	function deleteRole(id){	
		if (confirm("确定要删除?")){
			window.location.href="${ctx}/role_deleteRole.emi?gId="+id;
		}
	}
	//授权
	function authRole(id){
		window.location.href="${ctx}/role_toRoleAuth.emi?gid="+id;
	}
	
	//checkbox点击事件
	function clickCheck(obj){
		
		var selectedUserid=$('#selectedUserIds').val();
		
		
		if($(obj).attr("checked")=="checked"){
			
			selectedUserid+=$(obj).val().split(",")[0]+",";
			$('#selectedUserIds').val(selectedUserid);
			
		}else{
			selectedUserid=selectedUserid.replace($(obj).val().split(",")[0],"");
			$('#selectedUserIds').val(selectedUserid);
		}
		
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
	<form action="${ctx}/wms/group_toSelectUser.emi" method="post" >
	
		<input class="selectedUserIds" name="selectedUserIds" id="selectedUserIds" type="hidden" value="${selectedUserIds }" >
	
		<div class="EMonecontent">
			
			<!--按钮部分-->
		 	<div class="toolbar" style="margin:20px 0 20px 0">
		 		<ul>
		 			<input type="text" name="keyWord" placeholder="单据编号关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> 
		 			<input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
		 			
		 			
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
	
			<!--内容部分-->
			<div class="creattable" >
				<!--列表部分-->
				<div class="createdList">
					<table id="" >
						<tbody>
							<tr>
								<th width="5%" style="text-align: center;"><input type="checkbox" id="all" style="display:none" onclick="selectAll()"></th>
								<th width="5%" style="text-align: center;">序号</th>
								<th width="20%" style="text-align: center;">人员编码</th>
								<th width="20%" style="text-align: center;">人员姓名</th>
								<th width="20%" style="text-align: center;">性别</th>
								<th width="30%" style="text-align: center;">出生年月</th>
							</tr>
							<c:forEach var="user" items="${userList }" varStatus="stat">
								<tr>
									<td id="tool" class="toolTd"><input type="checkbox" name="userCheck" value="${user.gid },${user.perName}" <c:if test="${fn:containsIgnoreCase(selectedUserIds, user.gid)}">checked</c:if> onclick="clickCheck(this);"></td>
									<td>${stat.count }</td>
									<td>${user.perCode}</td>
									<td>${user.perName }</td>
									<td>${user.perSex }</td>
									<td>${user.perBirthday }</td>
								</tr>
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