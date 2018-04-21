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

	<link rel="stylesheet" type="text/css" href="${ctx }/css/common/common_rm.css">
	<!-- 分页引用的文件 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/plugins/jqPaginator/bootstrap.min.css">
	<script type="text/javascript" src="${ctx}/scripts/plugins/jqPaginator/jqPaginator.js"></script>

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
</script>

</head>
<body>
	<form action="${ctx}/role_roleList.emi" method="post" >
		<div id="container">
			<div id="content">
				<!--按钮栏-->
				<div id="toolBar">
					<div class="fl ">
						<input id="saveBtn" type="button" value="保存" onclick="saveAuth()">
					</div>
					<div class="cf"></div>
				</div>
				<!--内容部分-->
				<div id="createbody" >
					<!--标题名-->
					<!-- <div id="createName">
						<div class="Name">角色列表</div>	
					</div>
						  -->
					<!--列表部分-->
					<div id="createdList">
						<table id="createdTable" style="table-layout:fixed">
							<tbody>
								<tr>
									<th width="28%" style="text-align: center;">名称</th>
									<th width="18%" style="text-align: center;">查看</th>
									<th width="18%" style="text-align: center;">新增</th>
									<th width="18%" style="text-align: center;">修改</th>
									<th width="18%" style="text-align: center;">删除</th>
								</tr>
								<c:forEach var="item" items="${childRights }" varStatus="stat">
									<tr>
										<td>${item.rightName }</td>
										<td><input type="checkbox" name="ck_show" id="ck_show${stat.index }" value="${item.gid }"></td>
										<td><input type="checkbox" name="ck_add" id="ck_show${stat.index }" value="${item.gid }"></td>
										<td><input type="checkbox" name="ck_modify" id="ck_modify${stat.index }" value="${item.gid }"></td>
										<td><input type="checkbox" name="ck_delete" id="ck_delete${stat.index }" value="${item.gid }"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="cf"></div>			 
				</div>
			</div>
		</div>
		<!-- 分页 -->
		<div class="pageContainer" style="margin-top: px" align="center">
			<input type="hidden" name="pageIndex" id="pageIndex"  value="">
			<ul class="pagination" id="pagination"></ul>
		</div>
	    <script type="text/javascript">
	    	var init = 0;
	    	var pageIndex = ${data.pageIndex};	 //当前页码
	    	var pageCount = ${data.pageCount};	 //总页数
		</script>
		<script type="text/javascript" src="${ctx}/scripts/plugins/jqPaginator/page.js"></script>
	</form>
</body>
</html>