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

	<script type="text/javascript">
		
		$(function(){
			$('.addBtn').click(function() {
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'新增用户',
			    width: '800px',
				height: '500px',
				content: 'url:${ctx}/wms/user_toAddUser.emi',
				zIndex:2000,
				button: [
				          {
				              name: '保存',
				              callback: function () {
				            	  if(!this.content.checkdata()){
					  					return false;
					  				}
				            		$.ajax({
				      				  data: $("#myform",this.content.document).serialize(),
				      				  type: 'POST',
				      				  url: '${ctx}/wms/user_addUser.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/user_getUser.emi";});
				      					  }else{
				      						  $.dialog.alert_e(req);
				      					  }
				      				  },
				      				  error:function(){
				      					  $.dialog.alert_e("添加失败");
				      				  }
				      			});
				                  return false;
				              },
				              focus: true
				          },

				          {
				              name: '关闭'
				          }
				      ]
				});
			});
			
			
			$('.editBtn').click(function() {
				var gid = checkSelectId('strsum');
				if (gid!=''){
					$.dialog({
						drag: true,
						lock: true,
						resize: false,
						title:'修改用户',
					    width: '800px',
						height: '500px',
						zIndex:2000,
						content: 'url:${ctx}/wms/user_toEditUser.emi?gid='+gid,
						button: [
						          {
						              name: '保存',
						              callback: function () {
						            	  if(!this.content.checkdata()){
							  					return false;
							  				}
						            		$.ajax({
						      				  data: $("#myform",this.content.document).serialize(),
						      				  type: 'POST',
						      				  url: '${ctx}/wms/user_addUser.emi',
						      				  success: function(req){
						      					  if(req=='success'){
						      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/user_getUser.emi";});
						      					  }else{
						      						  $.dialog.alert_e(req);
						      					  }
						      				  },
						      				  error:function(){
						      					  $.dialog.alert_e("修改失败");
						      				  }
						      			});
						                  return false;
						              },
						              focus: true
						          },

						          {
						              name: '关闭'
						          }
						      ]
						});
				}
				
			});
			
			
		});
		
	</script>


<body>
<form action="${ctx}/wms/user_getUser.emi" id="myform" method="post">
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<!-- <li class="fl"><input type="button" class="addBtn" value="新增"></li> -->
	 			<!-- <li class="fl"><input type="button" id="Emsearch" class="searchBtn " value="查询"> </li> -->
	 			<li class="fl"><input type="button" class="editBtn" value="编辑"></li>
	 			<!-- <li class="fl"><input type="button" class="delBtn" value="停用"> </li> -->
	 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
	 			<div class="cf"></div>
	 		</ul>
	 	</div>

	 	<!--按钮部分 end-->
	 	<!--表格部分-->
	 	<div class="creattable">
	 		<div class="tabletitle">用户信息</div>		 		
	 		<div class="createdList">
	 			<table>
		 			<tbody>
		 				<tr>
		 					<th style="width: 120px;"><input id="checkAll" type="checkbox" onclick="selectAll('checkAll','strsum')"/></th>
		 					<th style="width: 120px;">序号</th>
		 					<th>用户编码</th>
		 					<th>用户名称</th>
		 					<th>是否停用</th>
		 					<th>备注</th>
		 				</tr>
		 				 
		 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;"><input type="checkbox" value="${bean.gid }" name="strsum" id="strsum" onclick="clickCheck('checkAll','strsum')"/></td>
								<td style="width: 120px;">${stat.count}</td>
								<td>${bean.userCode}</td>
								<td>${bean.userName}</td>
								
								<c:choose>
	 								<c:when test="${bean.isDelete==0 || bean.isDelete==null }">
	 									<td>否</td>
	 								</c:when>
		 							<c:otherwise>
		 								<td>是</td>
		 							</c:otherwise>
	 							</c:choose>
								<td>${bean.notes}</td>
							</tr>
						</c:forEach>
			 				 
		 			</tbody>
		 		</table>
		 		<!--分页部分-->
				<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
				<!--分页部分 end-->
	 		</div>		 		
	 	</div>
	 	<!--表格部分 end-->	
	 	
	</div>
</form>
</body>
</html>