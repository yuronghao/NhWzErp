<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 这个页面lhgdialog需要在整个窗口弹出，设置如下 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:set var="lhg_self" value="false"/>
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
	<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog/lhgdialog.js"></script>
	<style type="text/css">
		.effect{
			background:#f7fb7d !important;
		}
	</style>
	 <!--设置content宽度-->
	<script type="text/javascript">
	var rsp_orderCid = '';
	
	$(function() {

		var width = $('#createdTable').width();
	
		$('#content').css('width', width+10+"px");
		$('.srcList').click(function(){
			$('.srcList').removeClass('effect');
			$(this).addClass('effect');
		});	


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

	
	//搜索
	function searchSrc(){
		document.forms[0].submit();
	}
	
	function showProcess(orderCid){
		rsp_orderCid = orderCid;
		document.getElementById('frame_detail').contentWindow.location.href='${ctx}/wms/orderpd_changeProcessSrc.emi?orderCId='+orderCid;
	}
</script>

</head>
<body>
	<form action="${ctx}/wms/orderpd_changeOrderSrc.emi" method="post" >
	<div class="EMonecontent" style="padding-top: 10px;min-height: 250px">
		<div class="toolBar">
			<ul>
	 			<li class="fl" style="margin-left: 15px;margin-bottom: 2px">订单编号：
	 				<input type="text" id="billCode" name="billCode" placeholder="" class="write_input">
	 				<input type="button" class="searchBtn" placeholder="" value="搜索" onclick="searchSrc();">
	 			</li>
	 			<div class="cf"></div>
	 		</ul>
		</div>
		<!--内容部分-->
		<div class="creattable">
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="50px" style="text-align: center;">序号</th>
						<th style="text-align: center;">订单编号</th>
						<th style="text-align: center;">订单日期</th>
						<th style="text-align: center;">部门</th>
						<th style="text-align: center;">负责人</th>
						<th style="text-align: center;">产品编码</th>
						<th style="text-align: center;">产品名称</th>
						<th style="text-align: center;">产品规格</th>
						<th style="text-align: center;">生产数量</th>
					</tr>
					<c:forEach var="order" items="${data.list }" varStatus="stat">
						<tr class="srcList" onclick="showProcess('${order.gid}')">
							<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
							<td nowrap class="longText" title="${order.billCode }">${order.billCode }</td>
							<td nowrap class="longText" title="${order.billDate }"><fmt:formatDate value="${order.billDate }" type="date"/></td>
							<td nowrap class="longText" title="${order.departmentName }">${order.departmentName }</td>
							<td nowrap class="longText" title="${order.personName }">${order.personName }</td>
							<td nowrap class="longText" title="${order.goodsCode }">${order.goodsCode }</td>
							<td nowrap class="longText" title="${order.goodsName }">${order.goodsName }</td>
							<td nowrap class="longText" title="${order.goodsStandard }">${order.goodsStandard }</td>
							<td nowrap class="longText" title="${order.number }"><fmt:formatNumber type="number" groupingUsed="false"  minFractionDigits="2" value="${order.number}" /></td>
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
	<hr style="margin-left: 15px;margin-right: 15px;border: 1px #cccccc solid">
	<iframe id="frame_detail" src="${ctx}/wms/orderpd_changeProcessSrc.emi" height="300px" frameborder="0" width="100%" style="overflow: auto;"></iframe>
	</form>
</body>
</html>