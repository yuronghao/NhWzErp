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
		function modifyMember(id){
			$("#modifyid").val(id);
			$("#modifyform").submit();
		}
		function querymember(){
			$("#keyWord").val(jQuery.trim($("#querparam").val()));
			$("#queryform").submit();
		}
	</script>
  </head>
  
  <body>
  <div style="width:100%;height:35px;line-height:35px;background-color:#edf6fb;margin:0 auto;border-bottom:#b7d5df">
		消费列表
	</div>
	<form action="${ctx}/lfmember_consumptionList.emi" method="post" >
		<div class="EMonecontent" style="padding-top: 10px">
			<!--按钮栏-->
			<div class="toolBar">
				<ul>
		 			<li class="fl"><input type="button" class="backBtn" value="返回首页" onclick="goindex()"></li>
		 			<li class="fr"><input type="button" class="searchBtn" value="查询" onclick="querymember()"></li>
		 			<li class="fr"><input type="text" id="querparam" value="${keyWord}" style="height:30px;line-height:30px;border-radius:3px;border:1px #ccc solid;" /></li>
		 			<div class="cf"></div>
		 		</ul>
			</div>
			<div style="height:10px;"></div>
			<!--内容部分-->
			<div class="creattable">
		 		<div class="createdListss">
				<table>
					<tbody>
						<tr>
							<th exp_column="id" width="5%" style="text-align: center;">序号</th>
							<th exp_column="name" width="10%" style="text-align: center;">会员姓名</th>
							<th exp_column="cardno" width="10%" style="text-align: center;">卡号</th>
							<th exp_column="phone" width="10%" style="text-align: center;">手机号</th>
							<th exp_column="blance" width="10%" style="text-align: center;">消费金额</th>
							<th exp_column="ctime" width="10%" style="text-align: center;">消费时间</th>
							
						</tr>
						<c:forEach var="role" items="${data.list }" varStatus="stat">
							<tr>
								<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
								<td>${role.name }</td>
								<td>${role.cardno }</td>
								<td>${role.phone }</td>
								<td>${role.amount }</td>
								<td>${fn:substring(role.ctime,0,19)}</td>
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
	
	<form method="post" id="queryform" action="<%=contextPath %>/lfmember/lfmember_consumptionList.emi">
		<input type="hidden" id="keyWord" name="keyWord" value="">
	</form>
</body>
  </body>
</html>
