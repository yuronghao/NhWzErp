<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>

	<script type="text/javascript">
	    $(function() {
	       $("#checkAll").click(function() {
	            $('input[name="lyzj_chose"]').attr("checked",this.checked); 
	        });
	        var $lyzj_chose = $("input[name='lyzj_chose']");
	        $lyzj_chose.click(function(){
	        	 $("#cutomerGid").val($("input[name='lyzj_chose']:checked").parent().nextAll(".cutomerGid").text());
	        	 $("#currentDeptGid").val($("input[name='lyzj_chose']:checked").parent().nextAll(".currentDeptGid").text());
	        	 $("#providerGid").val($("input[name='lyzj_chose']:checked").parent().nextAll(".providerGid").text());
	        	 $("#mouldStyleId").val($("input[name='lyzj_chose']:checked").parent().nextAll(".mouldStyleId").text());
	             $("#checkAll").attr("checked",$lyzj_chose.length == $("input[name='lyzj_chose']:checked").length ? true : false); 
	        });
	    });
	</script>


<body>
	<form action="${ctx}/wms/mould_getRightMould.emi" id="myform" method="post">
	<input type="hidden" id="id" name="id" value="${id}">
		<!--主体部分-->
	 	<div class="mainword" style="width:350%;">
	 		<!-- <div class="tabletitle">模具设置</div> -->	
	 			 <input type="hidden" id="cutomerGid" name="cutomerGid">
	 			  <input type="hidden" id="currentDeptGid" name="currentDeptGid">
	 			 <input type="hidden" id="providerGid" name="providerGid">
	 			 <input type="hidden" id="mouldStyleId" name="mouldStyleId">
	 			<div class="creattable" style="width:100%;margin-bottom: 0px;">
	 			 <input type="text" name="keyWord" placeholder="请输入模具名称" class="write_input" value="${keyWord }">

					<input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit()">
		 			<div style="margin-top: 5px">
			 			<table id="myTable">
				 			<tbody>
				 				<tr>
				 					<th></th>
				 					<th>序号</th>
				 					<th>模具分类名称</th>
				 					<th>模具编码</th>
				 					<th>模具名称</th>
				 					<th>模具条码</th>
				 					<th>多模序号</th>
				 					<th>骆氏号</th>
				 					<th>零件号</th>
				 					<th>零件名称</th>
				 					<th>模比</th>
				 					<th>型腔号</th>
				 					<th>档案位置</th>
				 					<th>模具库位</th>
				 					<th>置料工装</th>
				 					<th>顶出工装</th>
									<th>模具流向</th>
									<th>模具入库时间</th>
									<th>模具资料编号</th>
									<th>目前所在部门</th>
									<th>目前状态</th>
									<th>备注</th>
				 					<th>开模时间</th>
				 					<th>材质</th> 
				 					<th>尺寸</th>
				 					<th>模具成本</th>
				 					<th>加工单位</th>
									<th>报废时间</th>
									<th>报废原因</th>
									<th>模具寿命</th>
									<th>增加寿命</th>
									<th>已用寿命</th>
									<th>可用寿命</th>
									<th>模具出库时间</th>
									<th>客户编码</th>
									<th>供应商编码</th>
									<th>开模费</th>
									<th>是否返还</th>
									<th>返还时间</th>
									<th>是否分摊结束</th>
									<th>分摊结束时间</th>
									<th>供货单位</th>
									
									<th>创建人</th>
									<th>创建时间</th>
									<th>修改人</th>
									<th>修改时间</th>
									
				 				</tr>
				 				
				 				 <c:forEach items="${data.list }" var="gs" varStatus="vs"> 
					 				<tr>
					 				 	<td style="width: 50px;">
					 						<input type="checkbox" id="" class="goodsSelected" name="lyzj_chose" value="${gs.gid}" /> 
					 					</td> 
					 				<td>${vs.count }</td>
					 				<td>${gs.classificationName}</td>
					 				<td class="mouldStyleId" hidden>${gs.mouldstyle}</td>
				 					<td>${gs.mouldcode}</td>
				 					<td>${gs.mouldname}</td>
				 					<td>${gs.barcode}</td>
				 					<td>${gs.multimodeOrder}</td>
				 					<td>${gs.cdefine1}</td>
				 					<td>${gs.partNumber}</td>
				 					<td>${gs.partName}</td>
				 					<td>${gs.mouldRatio}</td>
				 					<td>${gs.cavity}</td>
				 					<td>${gs.fileLocation}</td>
				 					<td>${gs.position}</td>
				 					<td>${gs.placingTooling}</td>
				 					<td>${gs.preTooling}</td>
									<td>${gs.mouldFlow}</td>
									<td>${gs.storageTime}</td>
									<td>${gs.dataCode}</td>
									<td >${gs.depName}</td>
									<td class="currentDeptGid" hidden>${gs.currentDeptGid}</td>
									<c:choose>
   										<c:when test="${gs.mouldstatus==0}">  
   										<td>正常</td>      
   										</c:when>
   										<c:when test="${gs.mouldstatus==1}">  
   										<td>维修</td>      
   										</c:when>
   										<c:when test="${gs.mouldstatus==2}">  
   										<td>报废</td>      
   										</c:when>
   										<c:when test="${gs.mouldstatus==3}">  
   										<td>试模</td>      
   										</c:when>
   										<c:otherwise> 
   										<td></td>
   										</c:otherwise>
									</c:choose>
									<td>${gs.notes}</td>
				 					<td>${gs.mouldBeginTime}</td>
				 					<td>${gs.texture}</td> 
				 					<td>${gs.size}</td>
				 					<td>${gs.cost}</td>
				 					<td>${gs.processingUnit}</td>
									<td>${gs.mouldScrapTime}</td>
									<td>${gs.mouldScrapReason}</td>
									<td>${gs.life}</td>
									<td>${gs.addlife}</td>
									<td>${gs.usedlife}</td>
									<td>${gs.canuselife}</td>
									<td>${gs.outTime}</td>
									<td class="cutomerGid" hidden>${gs.cutomerGid}</td>
									<td >${gs.customer}</td>
									<td class="providerGid" hidden>${gs.providerGid}</td>
									<td >${gs.provider}</td>
									<td>${gs.openCost}</td>
									<c:choose>
   										<c:when test="${gs.isreturn==0}">  
   										<td>否</td>      
   										</c:when>
   										<c:when test="${gs.isreturn==1}">  
   										<td>是</td>      
   										</c:when>
   										<c:otherwise> 
   										<td></td>
   										</c:otherwise>
									</c:choose>
									<td>${gs.returnTime}</td>
									<c:choose>
   										<c:when test="${gs.isShareOrver==0}">  
   										<td>否</td>      
   										</c:when>
   										<c:when test="${gs.isShareOrver==1}">  
   										<td>是</td>      
   										</c:when>
   										<c:otherwise> 
   										<td></td>
   										</c:otherwise>
									</c:choose>
									<td>${gs.shareOrverTime}</td>
									<td>${gs.supplyunit}</td>
									
									<td>${gs.createName}</td>
									<td>${gs.createDate}</td>
									<td>${gs.modifyName}</td>
									<td>${gs.modifyDate}</td>
									
					 				</tr>
				 				 </c:forEach>
				 			</tbody>
			 			</table>
			 		</div>
			 		
					<!--表格部分 end-->
					<!--分页部分-->
					<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
					<!--分页部分 end-->
		
		 		</div>
	 			
	 	</div>
	</form>
</body>
</html>