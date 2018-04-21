<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>原因列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<script type="text/javascript">
function addreasons(){
	var pwdWin = $.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'添加原因',
	    width: '600px',
		height: '200px',
		content: 'url:wms/checkbill_toaddcheckbillreason.emi',
		okVal:"保存",
		ok:function(){
			var reasonGid = this.content.document.getElementById('reasonGid').value;
			var num = this.content.document.getElementById('num').value;
			if(!this.content.checkdata()){
				return false;
			}
			$.ajax({
				  data: {reasonGid:reasonGid,num:num,checkcGid:$('#checkcGid').val()},
				  type: 'POST',
				  url: '${ctx}/wms/checkbill_addcheckbillreason.emi',
				  success: function(req){
					  if(req=='success'){
						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/checkbill_lookcheckbillreason.emi?checkbillcgid=${checkcGid}";});
					  }else{
						  $.dialog.alert_e(req);
					  }
				  },
				  error:function(){
					  $.dialog.alert_e("添加失败");
				  }
			});
		},
		cancelVal:"关闭",
		cancel:true
	});	
}
</script>
<body>
<form action="${ctx}/wms/sob_getsoblist.emi" name="myform" id="myform" method="post">
<input type="hidden" id="checkcGid" name="checkcGid" value="${checkcGid}">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<li class="fl"><input type="button" class="btns" value="新增" id="addBtn" onclick="addreasons()"></li>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">原因列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>原因名称</th>
			 					<th>不合格数量</th>
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${stat.count}</td>
								<td>${bean.reasonname}</td>
								<td>${bean.num}</td>
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