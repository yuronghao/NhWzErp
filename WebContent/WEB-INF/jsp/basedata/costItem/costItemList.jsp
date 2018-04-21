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
		location.href = "${ctx}/wms/costItem_toAddcostItem.emi";
	}
	//编辑基本信息
	function editRoute(){
		var costItemId = checkSelectId('userCheck');
		if(costItemId!=''){
			location.href = "${ctx}/wms/costItem_toUpdatecostItem.emi?costItemId="+costItemId;
		}
	}
	
	//设计
	function designRoute(){
		var routeId = checkSelectId('userCheck');
		if(routeId!=''){
			var maxWidth = window.screen.width-40;//document.body.clientWidth;
			var maxHeight = window.screen.height-200;//document.body.clientHeight;
			$.dialog({ 
				drag: true,
				lock: false,
				resize: true,
				title:'标准工艺路线设计',
			    width: maxWidth+'px',
				height: maxHeight+'px',
				content: 'url:${ctx}/wms/basepd_toDesignBaseRoutePage.emi?routeId='+routeId,
				okVal:"关闭",
				ok:function(){
					//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
				},
				//cancelVal:"关闭",
				//cancel:true
			});	
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
					url: "${ctx}/wms/costItem_deletecostItem.emi?costItemId="+gid,
					success: function(req){
						if(req=='success'){
							$.dialog.alert_s('删除成功',function(){
								location.href="${ctx}/wms/costItem_getcostItemList.emi";
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
	
	//报工/入库取数
	function getMethod1(){
		$.ajax({
			data: {year:2017,month:5},
			type: 'POST',
			url: "${ctx}/wms/cost_getCostReportInInfor.emi",
			dataType:"json",
			success: function(req){
				if(req.success==1){
					$.dialog.alert_s('取数成功');
					
				}else if(req=='error'){
					$.dialog.alert_e('取数失败');
				}
			},
			error:function(){
				$.dialog.alert_e('error');
			}
		});
		
	}
	

	

	
	
	
</script>

</head>
<body>
	<form action="${ctx}/wms/costItem_getcostItemList.emi" name="myform" id="myform" method="post" >
	
	<div class="EMonecontent" style="padding-top: 10px">
		<!--按钮栏-->
		<div class="toolBar">
			<ul>
	 			<li class="fl">
						&nbsp;&nbsp;
						<input class="addBtn" type="button" value="新增" onclick="addRoute()">
						<input class="editBtn" type="button" value="修改" onclick="editRoute()">
						<input class="delBtn" type="button" value="删除" onclick="deleteRoute()">
						
<!-- 						<input class="" type="button" value="材料出库单取数" onclick="getMethod2()">
						<input class="" type="button" value="工费取数" onclick="getMethod3()"> -->
						
						&nbsp;&nbsp;
				</li>
	 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
	 			<div class="cf"></div>
	 		</ul>
		</div>
		<!--内容部分-->
		<div class="creattable">
	 		<div class="tabletitle">成本项目列表</div>		 		
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
						<th width="5%" style="text-align: center;">序号</th>
						<th width="20%" style="text-align: center;">成本项目编码</th>
						<th width="20%" style="text-align: center;">成本项目名称</th>
						<th width="20%" style="text-align: center;">类型</th>
						<th width="20%" style="text-align: center;">成本要素分配率</th>
						<th width="20%" style="text-align: center;">备注</th>
					</tr>
					<c:forEach var="costItem" items="${data.list }" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="userCheck" value="${costItem.gid }" onclick="clickCheck();"></td>
							<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
							<td>${costItem.code }</td>
							<td>${costItem.name }</td>
							<td>${costItem.priorname }</td>
							<td>${costItem.allotRateName }</td>
							<td>${costItem.notes }</td>
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