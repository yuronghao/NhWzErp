<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>

<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>

	 	<!--主体部分-->
	 	<form action="${ctx}/wms/org_getPersonList.emi" id="myform" method="post" >
		 	<div class="mainword">
		 		<div class="mainaddword" style="">
		 			<div class="creattable" style="/* width:90%; */margin-top: 20px;margin-bottom: 15px;">
			 			<div class="">
				 			<table>
					 			<tbody>
					 				<tr>
					 					<th style="width: 50px;"><input id="" type="checkbox" onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" /></th>
					 					<th>序号</th>
					 					<th>人员编码</th>
					 					<th>人员名称</th>
					 					<th>性别</th>
					 					<th>出生年月</th>
					 					<th>入职日期</th>
					 					<th>离职日期</th>
					 					<th>备注</th>
					 				</tr>
					 				
					 				<c:forEach items="${data.list }" var="ps" varStatus="vs">
						 				<tr>
						 					<td style="width: 50px;"><input type="checkbox" id="" name="strsum" value="${ps.gid }"/> </td>
						 					<td >${vs.count}</td>
						 					<td>${ps.percode}</td>
						 					<td>${ps.pername}</td>
						 					<c:choose>
						 						<c:when test="${ps.persex==0}">
						 							<td>男</td>
						 						</c:when>
						 						
						 						<c:when test="${ps.persex==1}">
						 							<td>女</td>
						 						</c:when>
						 						<c:otherwise>
						 							<td></td>
						 						</c:otherwise>
						 					</c:choose>
						 					
						 					<td>${fn:substring(ps.perbirthday,0,10)}</td>
						 					<td>${fn:substring(ps.begindate,0,10)}</td>
						 					<td>${fn:substring(ps.enddate,0,10)}</td>
						 					<td>${ps.notes}</td>
						 				</tr>
					 				</c:forEach>
	
					 				 		 
					 			</tbody>
					 		</table>
				 		</div>
				 		<!--分页部分-->
				 		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
					 	<!--分页部分 end-->
			 		</div>
		 			
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
	 	</form>
	</div>

</body>
</html>