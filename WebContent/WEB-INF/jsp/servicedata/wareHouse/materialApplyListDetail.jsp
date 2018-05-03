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
	        var dakd = '${xiancunl}';
			$("select[name='xiancunl']").val(dakd);


	       $("#checkAll").click(function() {
	            $('input[name="lyzj_chose"]').attr("checked",this.checked); 
	        });
	        var $lyzj_chose = $("input[name='lyzj_chose']");
	        $lyzj_chose.click(function(){
	            $("#checkAll").attr("checked",$lyzj_chose.length == $("input[name='lyzj_chose']:checked").length ? true : false);
	        });
	    });
	</script>


<body>
	<form action="${ctx}/wms/wareHouse_getApplyDetailList.emi" id="myform" method="post">
	<input type="hidden" id="id" name="id" value="${id}">
		<!--主体部分-->
	 	<div class="mainword">
	 			
	 			<div class="creattable" style="width:100%;margin-bottom: 0px;">
		 			<div style="margin-top: 5px">
			 			<table id="myTable">
				 			<tbody>
				 				<tr>
				 					<th><input id="checkAll" type="checkbox" /></th>
									<th>物料编号</th>
									<th>物料名称</th>
									<th>规格型号</th>
									<th>主单位</th>
									<th>主数量</th>
									<th>货位号</th>
									<th>批次</th>
									<th>备注</th>
				 				</tr>
				 				
				 				<c:forEach items="${saleApplyWarehouseC }" var="type" varStatus="vs">
				 					
					 				<tr>
					 					<td style="width: 50px;">
					 						<input type="checkbox" id="" class="goodsSelected" name="lyzj_chose" value="${type.gid }"/>
					 					</td>
										<td class="gid" style="display:none"><input type="hidden" id="" name="gid" class="listword" value="${type.gid}" /></td>

										<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
										<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly" ></td>
										<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
										<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
										<td class="mainUnit" ><input type="text" id="" name="mainUnit" class="listword" value="${type.good.unitName}" readonly="readonly"></td>
										<td class="mainNumber"><input type="text" id="" name="mainNumber" class="listword toDealInput numric" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2" groupingUsed="false" />" onchange="changeFlag(this)"></td>
										<td class="goodsAllocationName"><input type="text" id="" name="goodsAllocationName" class="listword  jjjnumric" value="${type.alocation}" readonly="readonly" ></td>
										<td class="goodsAllocationUid" style="display:none"><input type="text" id="" name="goodsAllocationUid" class="listword toDealInput" value="${type.goodsallocationuid}" readonly="readonly"></td>
										<td class="batch"><input type="text" id="" name="batch" class="listword " value="${type.batchcode}" readonly="readonly"></td>
										<td class="note"><input type="text" id="" name="note" class="listword toDealInput" value="${type.notes}" readonly="readonly"></td>
					 				</tr>
				 				
				 				</c:forEach>

				 						 				 
				 			</tbody>
			 			</table>
			 		</div>
			 		
					<!--表格部分 end-->
					<!--分页部分-->
					<!--分页部分 end-->
		
		 		</div>
	 			
	 	</div>
	</form>
</body>
</html>