<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>属性方案</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/cuspro_getcusproList.emi" name="myform" id="myform" method="post">
	<div class="EMonecontent">
		<!-- <div style="width: 100%; height: 15px;"></div> -->
		<!--表格部分-->
		<div class="creattable">
			<div class="tabletitle">客商档案</div>
			<div>
				<table>
					<tbody>
						<tr>
							<th style="width: 120px;"><input type="checkbox"
								name="lyzj_chose" value=""
								onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" /></th>
							<th style="width: 120px;">序号</th>
							<th>客商编码</th>
							<th>客商名称</th>
							<th>客商地址</th>
						</tr>
						<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;"><input type="checkbox" value="${bean.gid }" name="strsum" id="strsum" onclick="changefg('strsum','lyzj_chose')"/></td>
								<td style="width: 120px;">${stat.count}</td>
								<td>${bean.pccode}</td>
								<td>${bean.pcname}</td>
								<td>${bean.addr}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!--表格部分 end-->
		<!--分页部分-->
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!--分页部分 end-->
	</div>
	</form>
</body>
</html>