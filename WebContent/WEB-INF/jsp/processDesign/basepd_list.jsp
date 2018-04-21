<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>创建新企业</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />

	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	 <!--设置content宽度-->
	<script type="text/javascript">
	$(function() {

		var width = $('#createdTable').width();
	
		$('#content').css('width', width+10+"px");

	});
	//点击列表功能，弹出对话框
	$(function(){
		$('tr > td.toolTd').click(function(){
			$(this).children().show();
			$('.tools').children().mouseover(function(){
				$(this).addClass('liEffect');
			});

			$('.tools').children().mouseleave(function(){
				$(this).removeClass('liEffect');
			});		
		});
		
		$('tr > td.toolTd').mouseleave(function(){
			$(this).children('.tools').hide();
		});				

	});

	//新增
	function addRoute(){
		location.href = "${ctx}/wms/basepd_toEditBaseRoute.emi";
	}
	//编辑基本信息
	function editRoute(){
		var routeId = checkSelectId('userCheck');
		if(routeId!=''){
			if($('#state_'+routeId).val()=='1'){
				$.dialog.alert('该工艺路线已审核，不可修改');
			}else{
				location.href = "${ctx}/wms/basepd_toEditBaseRoute.emi?routeId="+routeId;
			}
			
		}
	}
	
	//设计
	function designRoute(){
		var routeId = checkSelectId('userCheck');
		if(routeId!=''){
			var maxWidth = window.screen.width-40;//document.body.clientWidth;
			var maxHeight = window.screen.height-200;//document.body.clientHeight;
			$.dialog({ 
				drag: true,
				lock: false,
				resize: true,
				title:'标准工艺路线设计',
			    width: maxWidth+'px',
				height: maxHeight+'px',
				zIndex:2000,
				content: 'url:${ctx}/wms/basepd_toDesignBaseRoutePage.emi?routeId='+routeId,
				okVal:"关闭",
				ok:function(){
					//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
				},
				//cancelVal:"关闭",
				//cancel:true
			});	
		}
	}
	
	//导入工艺路线
	function importRoute(){
		$.dialog({ 
			drag: true,
			lock: false,
			resize: true,
			title:'导入工艺路线',
		    width: '700px',
			height: '300px',
			zIndex:2000,
			content: 'url:${ctx}/wms/basepd_toImportRoutePage.emi',
			okVal:"关闭",
			ok:function(){
				//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
				window.location.reload();
			},
			//cancelVal:"关闭",
			//cancel:true
		});	
	}
	
	//删除
	function deleteRoute(){
		var gid = checkSelectId('userCheck',true);
		if (gid!=''){
			if($('#state_'+gid).val()=='1'){
				$.dialog.alert('该工艺路线已审核，不可修改');
			}else{
				$.dialog.confirm(tip_confirmDelete,function(){
					$.ajax({
						data: $("#myform").serialize(),
						type: 'POST',
						url: "${ctx}/wms/basepd_deleteBaseRoute.emi?routeId="+gid,
						success: function(req){
							if(req=='success'){
								$.dialog.alert_s('删除成功',function(){
									location.href="${ctx}/wms/basepd_toBaseRouteList.emi";
								});
								
							}else if(req=='error'){
								$.dialog.alert_e('删除失败');
							}
						},
						error:function(){
							$.dialog.alert_e('error');
						}
					});
				});
			}
		}
	}
	
	function copyRoute(){
		var gid = checkSelectId('userCheck',false,'请选择要复制的数据');
		if (gid!=''){
			$.dialog.prompt("请输入复制份数",function(val){
				if(val==''){
					$.dialog.alert('未输入份数');
				}else if (!(/(^[1-9]\d*$)/.test(val))){
					$.dialog.alert('输入的不是正整数');
				}else{
					$.ajax({
						data: $("#myform").serialize(),
						type: 'POST',
						url: "${ctx}/wms/basepd_copyRoute.emi?routeId="+gid+"&copyNumber="+val,
						dataType:'text',
						success: function(req){
							if(req=='success'){
								$.dialog.alert_s('复制成功',function(){
									location.href="${ctx}/wms/basepd_toBaseRouteList.emi";
								});
								
							}else if(req=='error'){
								$.dialog.alert_e('复制失败');
							}
						},
						error:function(){
							$.dialog.alert_e('error');
						}
					});
				}
			},'1');
		}
	}
	
	//搜索
	function searchRoute(){
		document.forms[0].submit();
	}
	
	//checkbox点击事件
	function clickCheck(){
		var allchecked = true;
		var uck = document.getElementsByName('userCheck');
		var allck = document.getElementById('all');
		
		for(var i=0;i<uck.length;i++){
			if(!uck[i].checked){
				allchecked = false;
			}
		}
		if(allchecked){
			allck.checked=true;
		}else{
			allck.checked=false;
		}
		
	}
	
	//全选按钮
	function selectAll(){
		var ckAll = document.getElementById('all');
		var isck = ckAll.checked;
		var uck = document.getElementsByName('userCheck');
		for(var i=0;i<uck.length;i++){
			uck[i].checked=isck;
		}
	}
	
	//审核工艺路线
	function auditRoute(){
		var chek=document.getElementsByName("userCheck");
		var gid='';
		for(var i=0;i<chek.length;i++){
			if(chek[i].checked){
				 gid +=chek[i].value+",";
			}
		}
		if(gid.length>0){gid=gid.substring(0, gid.length-1);}
		if (gid!=''){
			$.dialog.confirm('确定通过审核已选数据？',function(){
				$.ajax({
					data: $("#myform").serialize(),
					type: 'POST',
					url: "${ctx}/wms/basepd_auditRoute.emi?routeId="+gid,
					dataType:'json',
					success: function(req){
						if(req.success=='1'){
							$.dialog.alert_s('审核成功！',function(){
								document.forms[0].submit();
							});
						}else{
							if(req.msg && req.msg.length>0){
								var message = '审核失败，生成BOM出现异常：<br>';
								for(var i=0;i<req.msg.length;i++){
									message += (i+1)+'、'+req.msg[i]+"<br>";
								}
								message += '&nbsp;&nbsp;&nbsp;（注：请确保数据准确后重新审核！）';
								$.dialog.alert(message,function(){
									//document.forms[0].submit();
								});
							}else{
								$.dialog.alert_e('审核失败，服务异常！');
							}
						}
					},
					error:function(){
						$.dialog.alert_e('error');
					}
				});
				
			});
		}else{
			$.dialog.alert('请选择要审核的数据');
		}
		
	}
	
	//弃审工艺路线
	function cancelRoute(){
		$.dialog.confirm('确定弃审已选数据？',function(){
			var chek=document.getElementsByName("userCheck");
			var gid='';
			for(var i=0;i<chek.length;i++){
				if(chek[i].checked){
					 gid +=chek[i].value+",";
				}
			}
			if(gid.length>0){gid=gid.substring(0, gid.length-1);}
			if (gid!=''){
				$.ajax({
					data: $("#myform").serialize(),
					type: 'POST',
					url: "${ctx}/wms/basepd_cancelAuditRoute.emi?routeId="+gid,
					dataType:'text',
					success: function(req){
						if(req=='success'){
							$.dialog.alert_s('弃审成功',function(){
								document.forms[0].submit();
								//location.href="${ctx}/wms/basepd_toBaseRouteList.emi";
							});
							
						}else if(req=='error'){
							$.dialog.alert_e('弃审失败');
						}
					},
					error:function(){
						$.dialog.alert_e('error');
					}
				});
			}else{
				$.dialog.alert('请选择要弃审的数据');
			}
		});
	}
	
</script>

</head>
<body>
	<form action="${ctx}/wms/basepd_toBaseRouteList.emi" method="post" >
	
	<div class="EMonecontent"  style="padding-top: 10px">
		<!--按钮栏-->
		<div class="toolBar">
		
			<ul>
	 			<li class="fl"><input class="designBtn" type="button" value="设计" onclick="designRoute()"></li>
	 			<li class="fl">
						&nbsp;&nbsp;
						<input class="addBtn" type="button" value="新增" onclick="addRoute()">
						<input class="editBtn" type="button" value="修改" onclick="editRoute()">
						<input class="delBtn" type="button" value="删除" onclick="deleteRoute()">
						<!-- <input class="copyBtn" type="button" value="复制" onclick="copyRoute()"> -->
						<input class="checkBtn" type="button" value="审核" onclick="auditRoute()" title="没有设置任何工序的工艺路线的将跳过审核">
						<input class="cancelBtn" type="button" value="弃审" onclick="cancelRoute()" title="">
						<input class="importBtn" type="button" value="导入" onclick="importRoute()">
						&nbsp;&nbsp;
				</li>
	 			<li class="fl">
	 				<select id="status" name="status" style="width: 120px;margin-left: 100px">
	 					<option value="" <c:if test="${status=='' }">selected="selected"</c:if>>全部</option>
	 					<option value="0" <c:if test="${status=='0' }">selected="selected"</c:if> >未审核</option>
	 					<option value="1" <c:if test="${status=='1' }">selected="selected"</c:if> >已审核</option>
	 				</select>
	 				<input style="margin-left: 10px" type="text" id="keyWord" name="keyWord" placeholder="请输入搜索关键字" class="write_input" value="${keyWord }">
	 				<input type="button" class="searchBtn" placeholder="编码或名称" value="搜索" onclick="searchRoute();">
	 			</li>
	 			<div class="cf"></div>
	 		</ul>
		</div>
		<!--内容部分-->
		<div class="creattable">
	 		<div class="tabletitle">标准工艺路线列表</div>		 		
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="30px" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
						<th width="" style="text-align: center;">序号</th>
						<!-- <th width="20%" style="text-align: center;">编码</th> -->
						<th width="" style="text-align: center;">工艺路线名称</th>
						<th width="" style="text-align: center;">产品编码</th>
						<th width="" style="text-align: center;">末级自由项名称</th>
						<th width="" style="text-align: center;">产品名称</th>
						<th width="" style="text-align: center;">产品规格</th>
						<th width="" style="text-align: center;">产品单位</th>
						<th style="text-align: center;">备注</th>
						<th width="" style="text-align: center;">创建人</th>
						<th width="" style="text-align: center;">创建时间</th>
						<th width="" style="text-align: center;">修改人</th>
						<th width="" style="text-align: center;">修改时间</th>
						<th width="" style="text-align: center;">审核人</th>
						<th width="" style="text-align: center;">审核时间</th>
						<th style="text-align: center;">状态</th>
					</tr>
					<c:forEach var="flow" items="${data.list }" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="userCheck" value="${flow.gid }" onclick="clickCheck();"></td>
							<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
							<%-- <td nowrap class="longText" title="${flow.routcode }">${flow.routcode }</td> --%>
							<td nowrap class="longText" title="${flow.routname }">${flow.routname }</td>
							<td nowrap class="longText" title="${flow.goodsCode }">${flow.goodsCode }</td>
							<td nowrap class="longText" title="">${flow.endFreeName }</td>
							<td nowrap class="longText" title="${flow.goodsName }">${flow.goodsName}</td>
							<td nowrap class="longText" title="${flow.goodsStandard }">${flow.goodsStandard }</td>
							<td nowrap class="longText" title="${flow.goodsUnit }">${flow.goodsUnit }</td>
							<td nowrap class="longText" title="${flow.routdes }">${flow.routdes }</td>
							<td nowrap class="longText" title="${flow.createUserName }">${flow.createUserName }</td>
							<td nowrap class="longText" title="${flow.effdate }">${fn:substring(flow.effdate,0,19) }</td>
							<td nowrap class="longText" title="${flow.modifyUserName }">${flow.modifyUserName }</td>
							<td nowrap class="longText" title="${flow.modifyDate }">${fn:substring(flow.modifyDate,0,19) }</td> 
							<td nowrap class="longText" title="${flow.authUserName }">${flow.authUserName}</td>
							<td nowrap class="longText" title="${flow.authDate }">${fn:substring(flow.authDate,0,19) }</td>
							<td nowrap class="longText" title=""><c:if test="${flow.state=='1' }"><font color="green">已审核</font> </c:if>
								<c:if test="${flow.state!='1' }"><font color="red">未审核</font> </c:if>
								<input type="hidden" id="state_${flow.gid }" value="${flow.state }">
							 </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!-- ---------------------分页开始--------------------- -->
	<%--如果不使用data作为变量名称，在此增加如下代码： <c:set var="data" value="${data }"></c:set> 这里value中的data改成自定义的名称--%> 
	<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
	<!-- ----------------------分页结束-------------------- -->
	</form>
</body>
</html>