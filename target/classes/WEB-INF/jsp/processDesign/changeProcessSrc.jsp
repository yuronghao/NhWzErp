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
	var rsp_processId = '';
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
	function searchRoute(){
		document.forms[0].submit();
	}
	
	//checkbox点击事件
	function clickCheck(obj){
		var re_check=obj.checked;
		$('input[name="my_chose"]').each(function(index, element) {
			this.checked = false;
        });
		obj.checked = re_check;
		if(re_check){
			rsp_processId = obj.value;
		}else{
			rsp_processId = '';
		}
		
	}
	function clickCheckTr(elementId){
		var obj = document.getElementById(elementId);
		var re_check=true;
		$('input[name="my_chose"]').each(function(index, element) {
			this.checked = false;
        });
		obj.checked = re_check;
		if(re_check){
			rsp_processId = obj.value;
		}else{
			rsp_processId = '';
		}
		
	}
	
</script>

</head>
<body>
	<form action="${ctx}/wms/orderpd_changeOrderSrc.emi" method="post" >
	<div class="EMonecontent" style="padding-top: 10px;min-height: 250px">
		<!-- <div class="toolBar">
			<ul>
	 			<li class="fl" style="margin-left: 15px;margin-bottom: 2px">订单编号：
	 				<input type="text" id="billCode" name="billCode" placeholder="" class="write_input">
	 				<input type="button" class="searchBtn" placeholder="" value="搜索" onclick="searchSrc();">
	 			</li>
	 			<div class="cf"></div>
	 		</ul>
		</div> -->
		<!--内容部分-->
		<div class="creattable">
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="50px" style="text-align: center;">选择</th>
						<th width="50px" style="text-align: center;">序号</th>
						<th style="text-align: center;">工序编码</th>
						<th style="text-align: center;">工序名称</th>
						<th style="text-align: center;">可转数量</th>
					</tr>
					<c:forEach var="process" items="${processList }" varStatus="stat">
						<tr class="srcList" onclick="clickCheckTr('my_chose_${process.gid }')">
							<td style="text-align: center;"><input type="checkbox" id="my_chose_${process.gid }" name="my_chose" value="${process.gid }" onclick="clickCheck(this)"> </td>
							<td>${stat.count }</td>
							<td nowrap class="longText" title="${process.opCode }">${process.opCode }</td>
							<td nowrap class="longText" title="${process.opName }">${process.opName }</td>
							<td nowrap class="longText" title="${process.canChange }"><fmt:formatNumber type="number" groupingUsed="false"  minFractionDigits="2" value="${process.canChange}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	</form>
</body>
</html>