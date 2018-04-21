<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>销售列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript">		 
		$(function(){
			$("tr:even").addClass("oddbg");
		})
		
	</script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="EMonecontent">
		<div style="width: 100%; height: 15px;"></div>
		<div style="float: left; margin-left: 30px;">
			<li class="fl"><input type="button" class="inquire" value="查询"></li>
		</div>
		<!--表格部分-->
		<div class="mainaddword" style="margin-top: 40px;">
			<div class="creattable">
				<div class="tabletitle">销售订单列表</div>
				<div>
					<table>
						<tbody>
							<tr>
								<th>序号</th>
								<th>操作</th>
								<th>单据号</th>
								<th>客户</th>
								<th>销售类型</th>
								<th>币种</th>
								<th>汇率</th>
								<th>税率%</th>
								<th>单据日期</th>
								<th>录入人</th>
								<th>录入日期</th>
								<th>审核人</th>
								<th>审核日期</th>
								<th>审核状态</th>
							</tr>
							<tr>
								<td>001</td>
								<td>操作</td>
								<td>345354353</td>
								<td></td>
								<td>销售类型</td>
								<td>人民币</td>
								<td>1.0</td>
								<td>17.0</td>
								<td>2015-08-07</td>
								<td>木木</td>
								<td>2015-08-07</td>
								<td>木木</td>
								<td>2015-8-20</td>
								<td>未审核</td>
							</tr>
							<tr>
								<td>002</td>
								<td>操作</td>
								<td>2334354355</td>
								<td></td>
								<td>销售类型</td>
								<td>人民币</td>
								<td>1.0</td>
								<td>17.0</td>
								<td>2015-08-07</td>
								<td>木木</td>
								<td>2015-08-07</td>
								<td>木木</td>
								<td>2015-8-20</td>
								<td style="color: #4C9AF0;">通过</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!--表格部分 end-->
			<!--分页部分-->
			<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
			<!--分页部分 end-->
		</div>
	</div>
</body>
</html>