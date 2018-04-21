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
<script type="text/javascript">
		$(function(){
			$('.searchBtn').click(function(e) {
				$.dialog({
							drag : false,
							lock : true,
							resize : false,
							title : '查询条件',
							width : '300px',
							height : '100px',
							content : 'url:${ctx}/wms/prty_getSerchAA_Category.emi',
							okVal : '查询',
							ok:function(){
								debugger;
								var Categorname = this.content.document.getElementById('Categorname').value;
								$("#sname").val(Categorname);
								document.forms[0].submit();
							},
							cancelVal : '关闭',
							cancel : true
						});
			});	
			$("tr:even").addClass("oddbg");
			$('.addBtn').click(function() {
				window.location = "${ctx}/wms/prty_getAA_SoulationAdd.emi";
			});
			$('.editBtn').click(function() {
				var chek = document.getElementsByName("strsum");
				var total = 0;
				var gid = '';
				for (var i = 0; i < chek.length; i++) {
					if (chek[i].checked) {
						total += 1;
						gid = chek[i].value;
					}
				}
				if (total > 1) {
					$.dialog.alert(tip_dontMultEdit);
					return false;
				} else if (total < 1) {
					$.dialog.alert(tip_editSelect);
					return false;
				}
				window.location = "${ctx}/wms/prty_getAA_SoulationAdd.emi?gid="+gid;
			});
			$('.delBtn').click(function() {
				var total=0;
				var chek=document.getElementsByName("strsum");	
				for(var i=0;i<chek.length;i++){
					if(chek[i].checked){
						 total += 1;
					}
				}
				if(total<=0)
				{
					$.dialog.alert(tip_deleteSelect);
					return false;
				}
				$.dialog.confirm(tip_confirmDelete, function(){		
					debugger;
					$.ajax({
						  data: $("#myform").serialize(),
						  type: 'POST',
						  url: '${ctx}/wms/prty_DeleteAA_Soulation.emi',
						  dataType:'text',
						  success: function(req){
							  if(req=='success'){
								  $.dialog.alert_s('停用成功',function(){
										document.forms[0].submit();
								  });
							  }else {
								  $.dialog.alert_e("停用失败");
							  }
							  
						  }
					});
				}, function(){
				    //$.dialog.tips('执行取消操作');
				});
			});
		});

		
		</script>
</head>
<body><form action="${ctx }/wms/prty_getAA_SoulationList.emi" name="myform" id="myform" method="post">
<input type="hidden"  value="" id="sname" name="sname" />
	<div class="EMonecontent">
		<div style="width: 100%; height: 15px;"></div>
		<!--按钮部分-->
		<div class="toolbar">
			<ul>
				<li class="fl"><a href="#"><input
						type="button" class="addBtn" value="新增"></a></li>
				<li class="fl"><a href="#"><input
						type="button" class="editBtn" value="编辑"></a></li>
				<li class="fl"><input type="button" class="delBtn" value="删除">
				</li>
				<li class="fl"><input type="button" id="AttributeProjectsearch"
					class="searchBtn" value="查询"></li>
				<div class="cf"></div>
			</ul>
		</div>
		<!--按钮部分 end-->
		<!--表格部分-->
		<div class="creattable">
			<div class="tabletitle">基本属性</div>
			<div >
				<table>
					<tbody>
						<tr>
							<th style="width: 120px;"><input type="checkbox"
								name="lyzj_chose" value=""
								onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" /></th>
							<th style="width: 120px;">序号</th>
							<th>属性方案名</th>
							<th>所属类别</th>
							<th>状态</th>
						</tr>
						<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;"><input type="checkbox" 
									value="${bean.gid }" name="strsum" id="strsum" onclick="changefg('strsum','lyzj_chose')"/></td>
								<td style="width: 120px;">${stat.count}</td>
								<td>${bean.souname}</td>
								<td>${bean.name}</td>
								<td><c:if test="${bean.flag eq 1}">启用</c:if>
									<c:if test="${bean.flag eq 0}"><font color="green">已未启用</font></c:if>
								</td>
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
<!--全选-->
</html>