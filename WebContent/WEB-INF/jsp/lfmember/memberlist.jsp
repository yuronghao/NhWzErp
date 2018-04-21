<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	<style type="text/css">
		.createdListss {
		  margin-left: 15px;
		  margin-right: 15px;
		  }
	</style>
	<script type="text/javascript">
		function recharge(id){
			window.location.href="${ctx}/lfmember/lfmember_showRecharge.emi";
		}
		function goindex(){
			window.location.href="<%=contextPath %>/lfmember/lfmember_lfindex.emi";
		}
		function addMember(){
			window.location.href="<%=contextPath %>/lfmember/lfmember_showAddModifyMember.emi";
		}
		function modifyMember(id){
			$("#modifyid").val(id);
			$("#modifyform").submit();
		}
		function querymember(){
			$("#keyWord").val(jQuery.trim($("#querparam").val()));
			if(jQuery.trim($("#stateselect").val())!=''){
				$("#state").val($("#stateselect").val());
			}
			$("#queryform").submit();
		}
		function consumptionHistory(id){
			$("#consumptionListmemberid").val(id);
			$("#consumptionForm").submit();
		}
		function rechargeList(id){
			$("#rechargeListmemberid").val(id);
			$("#rechargeListForm").submit();
		}
	</script>
  </head>
  
  <body>
    <body>
    	<div style="width:100%;height:35px;line-height:35px;background-color:#edf6fb;margin:0 auto;border-bottom:#b7d5df">
		会员列表
	</div>
	<form action="${ctx}/lfmember_memberlist.emi" method="post" >
		<div class="EMonecontent" style="padding-top: 10px">
			<!--按钮栏-->
			
			<div class="toolBar">
				<ul>
		 			<li class="fl"><input type="button" class="addBtn" value="新增会员" onclick="addMember()"></li>
		 			<li class="fl"><input type="button" class="backBtn" value="返回首页" onclick="goindex()"></li>
		 			<li class="fr"><input type="button" class="searchBtn" value="查询" onclick="querymember()"></li>
		 			<li class="fr"><input type="text" id="querparam" value="${keyWord}" style="height:30px;line-height:30px;border-radius:3px;border:1px #ccc solid;" /></li>
		 			<li class="fr" style="margin-right:20px;">
		 				<select name="stateselect" id="stateselect" style="width:150px;height:30px;line-height:30px;border-radius:3px;">
		 					<option value="">卡状态</option>
		 					<option value="0" <c:if test="${state==0 }">selected</c:if>>正常</option>
		 					<option value="1" <c:if test="${state==1 }">selected</c:if>>暂停</option>
		 					<option value="2" <c:if test="${state==2 }">selected</c:if>>挂失</option>
		 				</select>
		 			</li>
		 			<!-- <li class="fl"><input type="button" class="editBtn" value="修改" onclick="updateRole()"></li>
		 			<li class="fl"><input type="button" class="editBtn" value="授权" onclick="authRole()"></li>
		 			<li class="fl"><input type="button" class="delBtn" value="删除" onclick="deleteRole()"></li> -->
		 			<!-- 导出，代码1、复制该按钮onclick统一事件 2、form跳转的action中调用导出方法 -->
		 			<!-- <li class="fl"><input type="button" class="editBtn" value="导出" onclick="emi_export()"></li> -->
		 			<div class="cf"></div>
		 		</ul>
			</div>
			<!--内容部分-->
			<div class="creattable">
		 		<!-- <div class="tabletitle">会员列表</div> -->	
		 		<div style="margin-top:10px;"></div>	 		
		 		<div class="createdListss">
				<table>
					<tbody>
						<tr>
							<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
							<th exp_column="number" width="10%" style="text-align: center;">卡编号</th>
							<th exp_column="name" width="10%" style="text-align: center;">会员姓名</th>
							<th exp_column="cardno" width="10%" style="text-align: center;">卡号</th>
							<th exp_column="phone" width="10%" style="text-align: center;">手机号</th>
							<th exp_column="blance" width="10%" style="text-align: center;">余额</th>
							<th exp_column="state" width="10%" style="text-align: center;">状态</th>
							<th  width="10%" style="text-align: center;">操作</th>
							
						</tr>
						<c:forEach var="role" items="${data.list }" varStatus="stat">
							<tr>
								<td><input type="checkbox" name="userCheck" value="${role.id }" onclick="clickCheck();"></td>
								<%-- <td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td> --%>
								<td>${role.number }</td>
								<td>${role.name }</td>
								<td>${role.cardno }</td>
								<td>${role.phone }</td>
								<td>${role.blance }</td>
								<td>
									<c:if test="${role.state==0}">
										<font color="green">正常</font>
									</c:if>
									<c:if test="${role.state==1}">
										<font color="black">停用</font>
									</c:if>
									<c:if test="${role.state==2}">
										<font color="red">挂失</font>
									</c:if>
								</td>
								<td><%-- <a href="javaScript:void(0);" onclick="recharge('${role.id}')">充值</a> --%><a href="javaScript:void(0);" onclick="consumptionHistory('${role.id}')">消费记录</a>|<a href="javaScript:void(0);" onclick="rechargeList('${role.id}')">充值记录</a> |<a href="javaScript:void(0);" onclick="modifyMember('${role.id}')">修改</a> </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		<!-- ---------------------分页开始--------------------- -->
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!-- ----------------------分页结束-------------------- -->
	</form>
	<form method="post" id="modifyform" action="<%=contextPath %>/lfmember/lfmember_showAddModifyMember.emi">
		<input type="hidden" id="modifyid" name="id" value="0">
	</form>
	
	<form method="post" id="queryform" action="<%=contextPath %>/lfmember/lfmember_memberlist.emi">
		<input type="hidden" id="keyWord" name="keyWord" value="">
		<input type="hidden" id="state" name="state" value="">
	</form>
	
	<form method="post" id="rechargeListForm" action="<%=contextPath %>/lfmember/lfmember_rechargeList.emi">
		<input type="hidden" id="rechargeListmemberid" name="memberid" value="0">
	</form>
	<form method="post" id="consumptionForm" action="<%=contextPath %>/lfmember/lfmember_consumptionList.emi">
		<input type="hidden" id="consumptionListmemberid" name="memberid" value="0">
	</form>
</body>
  </body>
</html>
