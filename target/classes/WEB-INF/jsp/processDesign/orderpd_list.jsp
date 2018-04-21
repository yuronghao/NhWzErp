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

	<link rel="stylesheet" type="text/css" href="${ctx }/css/common/common_rm.css">
	<!-- 分页引用的文件 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/plugins/jqPaginator/bootstrap.min.css">
	<script type="text/javascript" src="${ctx}/scripts/plugins/jqPaginator/jqPaginator.js"></script>

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

	//编辑流程基本信息
	function editFlow(flowId){
		location.href = "${ctx}/flow/flow_toEditFlow.emi?flowId="+flowId;
	}
	
	//设计流程
	function designFlow(flowId){
		var maxWidth = window.screen.width;//document.body.clientWidth;
		var maxHeight = window.screen.height-200;//document.body.clientHeight;
		var pwdWin = $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'流程图设计',
		    width: maxWidth+'px',
			height: maxHeight+'px',
			zIndex:2000,
			content: 'url:flow/flow_toDesignPage.emi?flowId='+flowId,
			okVal:"关闭",
			ok:function(){
				//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
			},
			//cancelVal:"关闭",
			//cancel:true
		});	
	}
</script>

</head>
<body>
	<form action="${ctx}/flow/instance_getInstanceList.emi" method="post" >
		<div id="container">
			<div id="content">
				<!--按钮栏-->
				<!--内容部分-->
				<div id="createbody" >
					<!--标题名-->
					<div id="createName">
						<div class="Name">流程列表</div>	
					</div>
					<input type="hidden" id="keyWord"name="keyWord" value="">
					<!--列表部分-->
					<div id="createdList">
						<table id="createdTable" style="table-layout:fixed">
							<tbody>
								<tr>
									<th width="5%" style="text-align: center;">序号</th>
									<th width="5%" style="text-align: center;">操作</th>
									<th width="60%" style="text-align: center;">流程名称</th>
									<th width="30%" style="text-align: center;">创建时间</th>
								</tr>
								<c:if test="${fn:length(data.list)<=0 }">
										<tr><td colspan="4" style="color: red">暂无数据!</td></tr>
									</c:if>
								<c:forEach var="flow" items="${data.list }" varStatus="stat">
									<tr>
										<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
										<td id="tool" class="toolTd"><img src="${ctx }/images/common/function.png">

											<div id="toolDiv" class="tools" style="display:none;" >
												<div class="toolsword">
													<%-- <a href="javaScript:void(0)" onclick="editFlow('${flow.gid }')" target="rightMain" style="text-decoration: none" >
														<div class="fl wordLeft"><img src="${ctx }/images/common/sq.png"></div>
														<div class="fl wordRight">编辑</div>
														<div class="cf"></div>
													</a> --%>
													<a href="javaScript:void(0)" onclick="designFlow('${flow.gid }')" target="rightMain" style="text-decoration: none" >
														<div class="fl wordLeft"><img src="${ctx }/images/common/sq.png"></div>
														<div class="fl wordRight">设计</div>
														<div class="cf"></div>
													</a>
												</div>
												<div class="cf"></div>
											</div>
										</td>
										<td nowrap class="longText" title="${flow.flowName }">${flow.flowName }</td>
										<td nowrap class="longText" title="${flow.createTime }">${fn:substring(flow.createTime,0,19) }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="cf"></div>			 
				</div>
			</div>
		</div>
		<!-- 分页 -->
		<div class="pageContainer" style="margin-top: px" align="center">
			<input type="hidden" name="pageIndex" id="pageIndex"  value="">
			<ul class="pagination" id="pagination"></ul>
		</div>
	    <script type="text/javascript">
	    	var init = 0;
	    	var pageIndex = ${data.pageIndex};	 //当前页码
	    	var pageCount = ${data.pageCount};	 //总页数
		</script>
		<script type="text/javascript" src="${ctx}/scripts/plugins/jqPaginator/page.js"></script>
	</form>
</body>
</html>