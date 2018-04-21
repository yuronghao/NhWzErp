<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 这个页面lhgdialog需要在整个窗口弹出，设置如下 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:set var="lhg_self" value="false"/>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>创建新企业</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />

	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog/lhgdialog.js"></script>
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

	//新增
	function addRoute(){
		location.href = "${ctx}/wms/basepd_toAddStandardProcess.emi?pno=${data.pageIndex}";
	}
	//编辑基本信息
	function editRoute(){
		var processId = checkSelectId('userCheck');
		if(processId!=''){
			location.href = "${ctx}/wms/basepd_toUpdateStandardProcess.emi?pno=${data.pageIndex}&processId="+processId;
		}
	}
	
	//删除
	function deleteRoute(){
		var gid = checkSelectId('userCheck',true);
		if (gid!=''){
			$.dialog.confirm(tip_confirmDelete,function(){
				$.ajax({
					data: $("#myform").serialize(),
					type: 'POST',
					url: "${ctx}/wms/basepd_deleteStandardProcess.emi?processId="+gid,
					success: function(req){
						if(req=='success'){
							$.dialog.alert_s('删除成功',function(){
								location.href="${ctx}/wms/basepd_toStandardProcessList.emi";
							});
							
						}else if(req=='error'){
							$.dialog.alert_e('删除失败');
						}
					},
					error:function(){
						$.dialog.alert_e('error');
					}
				});
			});
		}
	}
	
	//搜索
	function searchRoute(){
		document.forms[0].submit();
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
	<form action="${ctx}/wms/basepd_toStandardProcessList.emi" method="post" >
	
	<div class="EMonecontent" style="padding-top: 10px">
		<!--按钮栏-->
		<div class="toolBar">
		
			<ul>
	 			<li class="fl">
						&nbsp;&nbsp;
						<input class="addBtn" type="button" value="新增" onclick="addRoute()">
						<input class="editBtn" type="button" value="修改" onclick="editRoute()">
						<input class="delBtn" type="button" value="删除" onclick="deleteRoute()">
						&nbsp;&nbsp;
				</li>
	 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
	 			<div class="cf"></div>
	 		</ul>
		</div>
		<!--内容部分-->
		<div class="creattable">
	 		<div class="tabletitle">标准工序列表</div>		 		
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
						<th width="5%" style="text-align: center;">序号</th>
						<th width="20%" style="text-align: center;">编码</th>
						<th width="20%" style="text-align: center;">名称</th>
						<th width="10%" style="text-align: center;">是否质检</th>
						<th width="10%" style="text-align: center;">是否必扫模具</th>
						<th width="10%" style="text-align: center;">检验合格率</th>
						<th width="10%" style="text-align: center;">是否入库节点</th>
						<th style="text-align: center;">标准工价</th>
					</tr>
					<c:forEach var="flow" items="${data.list }" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="userCheck" value="${flow.gid }" onclick="clickCheck();"></td>
							<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
							<td nowrap class="longText" title="${flow.opcode }">${flow.opcode }</td>
							<td nowrap class="longText" title="${flow.opname }">${flow.opname }</td>
							<td nowrap class="longText" title=""><c:if test="${flow.isCheck==1 }">是</c:if> <c:if test="${flow.isCheck!=1 }">否</c:if></td>
							<td nowrap class="longText" title=""><c:if test="${flow.isMustScanMould==1 }">是</c:if> <c:if test="${flow.isMustScanMould==0 }">否</c:if></td>
							<td nowrap class="longText" title="${flow.checkRate }"><fmt:formatNumber type="number" groupingUsed="false"  minFractionDigits="2" value="${flow.checkRate }" /> </td>
							<td nowrap class="longText" title=""><c:if test="${flow.isStock==1 }">是</c:if> <c:if test="${flow.isStock!=1 }">否</c:if></td>
							<td nowrap class="longText" title="${flow.standardPrice }"><fmt:formatNumber type="number" groupingUsed="false"  minFractionDigits="2" value="${flow.standardPrice}" /></td>
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