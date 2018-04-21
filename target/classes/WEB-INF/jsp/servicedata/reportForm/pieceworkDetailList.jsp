<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<%@page import="com.emi.common.bean.core.TreeType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>计件工资详情表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
	<body>
		<form action="${ctx}/wms/reportForm_getPieceworkDetail.emi" name="myform" id="myform" method="post">
			<div class="EMonecontent">
				<div style="width: 100%;height: 15px;"></div>
				<!--按钮部分-->
			 	<div class="toolbar">
			 		<ul class="wordulList">
			 			<li class="wordliNoWidth fl">
							<div class="wordname fl">订单号：</div>
							<div class="wordnameinput fl">
								<input type="text" id="billcode" name="billcode"  class="toDealInput" value="${billcode }" >
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
								<input type="text" id="depName" name="depName"  class="toDealInput" value="${depName}" onclick="selectdep();">
	 							<input type="hidden" id="depUid" name="depUid" value="${depUid }">
							</div>
							<div class="cf"></div> 
		 				</li> 
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">人员：</div>
							<div class="wordnameinput fl">
								<input type="text" id="personName" name="personName"  class="toDealInput" value="${personName }" onclick="selectPerson();">
								<input type="hidden" id="personId" name="personId" value="${personId }"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">班组：</div>
							<div class="wordnameinput fl">
								<input type="text" id="groupName" name="groupName"  class="toDealInput" value="${groupName }" onclick="selectGroup();">
								<input type="hidden" id="groupId" name="groupId" value="${groupId }"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">开始日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="startDate" name="startDate"  class="toDealInput" value="${ startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">结束日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="endDate" name="endDate"  class="toDealInput" value="${endDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl"><input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();"></div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl"><input class="exportBtn" type="button" value="导出" onclick="exportData()"></div>
							<div class="cf"></div> 
		 				</li>
		 				<!-- <li class="wordliNoWidth fl">
							<div class="wordname fl"><input class="searchBtn" type="button" value="清空" onclick="clearCondition();"></div>
							<div class="cf"></div> 
		 				</li> -->
		 				
		 				<div class="cf"></div> 		 				
		 			</ul>
			 	</div>
			 	<!--按钮部分 end-->
			 	<!--表格部分-->
			 	<div class="creattable">
			 		<div class="tabletitle">计件工资详情表</div>		 		
			 		<div>
			 			<table>
				 			<tbody>
				 				<tr>
				 					<th style="width: 50px;">序号</th>
				 					<th>订单编号</th>
				 					<th>产品编码</th>
				 					<th>产品名称</th>
				 					<th>规格型号</th>
				 					<th>生产数量</th>
				 					<th>完工数量</th>
				 					<th>工序名称</th>
				 					<th>工序说明</th>
				 					<th>人员编码</th>
				 					<th>人员名称</th>
				 					<th>班组编码</th>
				 					<th>班组名称</th>
				 					<th>部门</th>
				 					<th>开工数量</th>
				 					<th>开工时间</th>
				 					<th>报工数量</th>
				 					<th>报工时间</th>
				 					<th>工序单价</th>
				 					<th>合计金额</th>
				 				</tr>
				 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
									<tr>
										<td style="width: 50px;">${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
										<td nowrap class="longText" title="${bean.billCode }">${bean.billCode}</td>
										<td nowrap class="longText" title="${bean.goodsCode }">${bean.goodsCode}</td>
										<td nowrap class="longText" title="${bean.goodsName }">${bean.goodsName}</td>
										<td nowrap class="longText" title="${bean.goodsstandard }">${bean.goodsstandard }</td>
										<td nowrap class="longText" title="${bean.number }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.number}"></fmt:formatNumber> </td>
										<td nowrap class="longText" title="${bean.completedNum }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.completedNum}"></fmt:formatNumber></td>
										
										<td nowrap class="longText" title="${bean.processName }">${bean.processName}</td>
										<td nowrap class="longText" title="${bean.opdes }">${bean.opdes}</td>
										
										<td nowrap class="longText" title="${bean.personCode }">${bean.personCode}</td>
										<td nowrap class="longText" title="${bean.personName }">${bean.personName}</td>
										<td nowrap class="longText" title="${bean.groupCode }">${bean.groupCode}</td>
										<td nowrap class="longText" title="${bean.groupName }">${bean.groupName}</td>
										<td nowrap class="longText" title="${bean.departmentName }">${bean.departmentName}</td>
										<td nowrap class="longText" title="${bean.disNum }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.disNum}"></fmt:formatNumber></td>
										<td nowrap class="longText" title="${bean.startTime }">${fn:substring(bean.startTime,0,10) }</td>
										<td nowrap class="longText" title="${bean.reportOkNum }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.reportOkNum}"></fmt:formatNumber></td>
										<td nowrap class="longText" title="${bean.endTime }">${fn:substring(bean.endTime,0,10) }</td>
										<td nowrap class="longText" title="${bean.realPrice }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.realPrice}"></fmt:formatNumber></td>
										<td nowrap class="longText" title="<fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.totalPrice}"></fmt:formatNumber>"><fmt:formatNumber minFractionDigits="2" type="number" value="${bean.totalPrice}"></fmt:formatNumber></td>
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
		
		<script type="text/javascript">
			function exportData(){
				location.href = "${ctx}/wms/reportForm_exportPieceworkDetail.emi?"+$('#myform').serialize();
			}
			
			/*查询部门*/
			function selectdep(){
				var pwdWin = $.dialog({ 
					drag: true,
					lock: true,
					resize: false,
					title:'选择部门',
				    width: '400px',
					height: '400px',
					zIndex:3004,
					content: 'url:${ctx}/wms/basepd_getselectdep.emi',
					okVal:"确定",
					ok:function(){
						var name = this.content.document.getElementById("name").value;
						var id = this.content.document.getElementById("id").value;
						document.getElementById('depName').value=name;
						document.getElementById('depUid').value=id;
					},
					cancelVal:"关闭",
					cancel:true
				});	
			}
			
			/*查询人员*/
			function selectPerson(){
		 		$.dialog({ 
					drag: true,
					lock: false,
					resize: false,
					title:'选择人员',
				    width: '800px',
					height: '500px',
					zIndex:3000,
					content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PERSON%>&multi=1&showTree=1 ', 
					okVal:"确定",
					ok:function(){
						var returnArray = this.content.window.jsonArray;
						var objgid=""; 
						var objname="";
						for(var i=0;i<returnArray.length;i++){
							objgid = objgid+returnArray[i].gid+",";
							objname = objname+returnArray[i].pername+",";
						}
						
						if(objgid!=""){
							objgid=objgid.substring(0, objgid.length-1);
							objname=objname.substring(0, objname.length-1);
						}
						document.getElementById('personName').value=objname;
						document.getElementById('personId').value=objgid;
					},
					cancelVal:"关闭",
					cancel:true
				});
			}
			
			/*查询班组*/
			function selectGroup(){
		 		$.dialog({ 
					drag: true,
					lock: false,
					resize: false,
					title:'选择班组',
				    width: '800px',
					height: '500px',
					zIndex:3000,
					content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GROUP%>&multi=1&showTree=0 ', 
					okVal:"确定",
					ok:function(){
						var returnArray = this.content.window.jsonArray;
						var objgid=""; 
						var objname="";
						for(var i=0;i<returnArray.length;i++){
							objgid = objgid+returnArray[i].gid+",";
							objname = objname+returnArray[i].groupname+",";
						}
						
						if(objgid!=""){
							objgid=objgid.substring(0, objgid.length-1);
							objname=objname.substring(0, objname.length-1);
						}
						document.getElementById('groupName').value=objname;
						document.getElementById('groupId').value=objgid;
					},
					cancelVal:"关闭",
					cancel:true
				});
			}
			
			//清空查询条件
			function clearCondition(){
				
			}
		</script>
		
	</body>
</html>