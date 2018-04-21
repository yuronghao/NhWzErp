<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<%@page import="com.emi.common.bean.core.TreeType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>计件工资汇总表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
	<body>
		<form action="${ctx}/wms/reportForm_getPieceworkSum.emi" name="myform" id="myform" method="post">
			<div class="EMonecontent">
				<div style="width: 100%;height: 15px;"></div>
				<!--按钮部分-->
			 	<div class="toolbar">
			 		<ul class="wordulList">
<!-- 		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
								<input type="text" id="depName" name="depName"  class="toDealInput" value="" onclick="selectdep();">
	 							<input type="hidden" id="depUid" name="depUid" value="">
							</div>
							<div class="cf"></div> 
		 				</li> -->
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">人员：</div>
							<div class="wordnameinput fl">
								<input type="text" id="personName" name="personName"  class="toDealInput" value="${personName }" onclick="selectPerson();">
								<input type="hidden" id="personGid" name="personGid" value="${personGid }"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">工作组：</div>
							<div class="wordnameinput fl">
								<input type="text" id="groupName" name="groupName"  class="toDealInput" value="${groupName }" onclick="selectGroup();">
								<input type="hidden" id="groupGid" name="groupGid" value="${groupGid }"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
							<input type="text" name="depName" id="depName" class="toDealInput" value="${depName}" onclick="selectdep();">
	 						<input type="hidden" id="depUid" name="depUid" value="${depUid}">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">开始日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="startMonth" name="startMonth"  class="toDealInput" value="${startMonth }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">结束日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="endMonth" name="endMonth"  class="toDealInput" value="${endMonth }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
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
		 				
		 				<div class="cf"></div> 		 				
		 			</ul>
			 	</div>
			 	<!--按钮部分 end-->
			 	<!--表格部分-->
			 	<div class="creattable">
			 		<div class="tabletitle">计件工资汇总表</div>		 		
			 		<div>
			 			<table>
				 			<tbody>
				 				<tr>
				 					<th style="width: 120px;">序号</th>
				 					<th>人员编码</th>
				 					<th>人员名称</th>
				 					<th>组编码</th>
				 					<th>组名称</th>
				 					<th>部门名称</th>
				 					<th>总开工</th>
				 					<th>总报工</th>
				 					<th>总金额</th>
				 				</tr>
				 				
				 				<c:forEach var="bean" items="${data.list }" varStatus="stat" >
									<tr class="trDblclick" personId="${bean.personGid}" personName="${bean.personName}" groupId="${bean.groupGid}" groupName="${bean.groupName}" startDate="${startMonth }" endDate="${endMonth }">
										<td style="width: 120px;">${(bean.pageIndex-1)*bean.pageSize+stat.count}</td>
										<td>${bean.personCode }</td>
										<td>${bean.personName}</td>
										<td>${bean.groupCode }</td>
										<td>${bean.groupName}</td>
										<td>${bean.deptName}</td>
										<td>${bean.disNum}</td>
										<td>${bean.reportOkNum}</td>
										<td>${bean.totalPrice}</td>
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
				location.href = "${ctx}/wms/reportForm_exportPieceworkSum.emi?"+$('#myform').serialize();
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
						document.getElementById('depUid').value="'"+id+"'"; 
						
						
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
							if(!$('#disObjId_'+returnArray[i].gid).val() || $('#disObjId_'+returnArray[i].gid).val()==''){
								
								objgid = objgid+"'"+returnArray[i].gid+"',";
								objname = objname+returnArray[i].pername+",";
								
							}
						}
						
						if(objgid!=""){
							objgid=objgid.substring(0, objgid.length-1);
							objname=objname.substring(0, objname.length-1);
							document.getElementById('personName').value=objname;
							document.getElementById('personGid').value=objgid;
						}
						
					},
					cancelVal:"关闭",
					cancel:true
				});
			}
			
			
			/*查询组*/
			function selectGroup(){
		 		$.dialog({ 
					drag: true,
					lock: false,
					resize: false,
					title:'选择组',
				    width: '800px',
					height: '500px',
					zIndex:3000,
					content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GROUP%>&multi=1&showTree=0 ', 
					okVal:"确定",
					ok:function(){
						debugger;
						var returnArray = this.content.window.jsonArray;
						var objgid=""; 
						var objname="";
						for(var i=0;i<returnArray.length;i++){
							if(!$('#disObjId_'+returnArray[i].gid).val() || $('#disObjId_'+returnArray[i].gid).val()==''){
								
								objgid = objgid+"'"+returnArray[i].gid+"',";
								objname = objname+returnArray[i].groupname+",";
								
							}
						}
						
						if(objgid!=""){
							objgid=objgid.substring(0, objgid.length-1);
							objname=objname.substring(0, objname.length-1);
							document.getElementById('groupName').value=objname;
							document.getElementById('groupGid').value=objgid;
						}
						
					},
					cancelVal:"关闭",
					cancel:true
				});
			}
			
 			$(function(){
				$('.trDblclick').dblclick(function(){
					
					debugger;
					
					var myDate = new Date();
					var year=myDate.getFullYear();
					var month=myDate.getMonth();
					month=month+1;
					if(month<10){
						month="0"+month;
					}
					
					
					var personId=$(this).attr("personId");
					var personName=$(this).attr("personName");
					var groupId=$(this).attr("groupId");
					var groupName=$(this).attr("groupName");
					
					var startDate=$(this).attr("startDate");
					var endDate=$(this).attr("endDate");
					if(startDate==""){
						startDate=year+"-"+month;
					}
					
					if(endDate==""){
						endDate=year+"-"+month;
					}
					
			 		$.dialog({ 
						drag: true,
						lock: false,
						resize: false,
						title:'选择组',
					    width: '1200px',
						height: '500px',
						zIndex:3000,
						content: 'url:${ctx}/wms/reportForm_getPieceworkDetail.emi?personId='+personId+'&personName='+encodeURI(encodeURI(personName))+'&groupId='+groupId+'&groupName='+encodeURI(encodeURI(groupName))+'&startDate='+startDate+'&endDate='+endDate, 
						cancelVal:"关闭",
						cancel:true
					});
				});
				
			});
			
		</script>
		
	</body>
</html>