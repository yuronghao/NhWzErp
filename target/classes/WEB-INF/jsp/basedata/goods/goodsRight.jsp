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
	            $("#checkAll").attr("checked",$lyzj_chose.length == $("input[name='lyzj_chose']:checked").length ? true : false);
	        });
	    });
	</script>


<body>
	<form action="${ctx}/wms/goods_getRightGoods.emi" id="myform" method="post">
	<input type="hidden" id="id" name="id" value="${id}">
		<div class="EMonecontent" style="">
			<div style="width: 100%;height: 15px;"></div>
		</div>
		<!--主体部分-->
	 	<div class="mainword">
	 		<!-- <div class="tabletitle">物料设置</div> -->	
	 			
	 			<div class="creattable" style="width:100%;margin-top: 20px;margin-bottom: 15px;">
	 			 
		 			<div>
			 			<table id="myTable">
				 			<tbody>
				 				<tr>
				 					<th><input id="checkAll" type="checkbox" /></th>
				 					<th>序号1</th>
				 					<th>物料编码</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>单位</th>
				 					<th>条码</th>
				 					<th>类别</th>
				 					<!-- <th>是否直接入库</th> --> 
				 					<th>现存量</th>
				 				</tr>
				 				
				 				<c:forEach items="${data.list }" var="gs" varStatus="vs">
				 					
					 				<tr>
					 					<td style="width: 120px;">
					 						<input type="checkbox" id="" class="goodsSelected" name="lyzj_chose" value="${gs.gid }" goodsCode="${gs.goodscode }" goodsName="${gs.goodsname}" goodsstandard="${gs.goodsstandard }" unitName="${gs.unitName }" cassComUnitName="${gs.cassComUnitName }" nowsum="${gs.nowsum}"/> 
					 					</td>
					 					<td style="width: 120px;">${vs.count }</td>
					 					<td>${gs.goodscode }</td>
					 					<td>${gs.goodsname }</td>
					 					<td>${gs.goodsstandard }</td>
					 					<td>${gs.unitName }</td>
					 					<td>${gs.goodsbarcode }</td>
					 					<td>${gs.classificationName }</td>
	<%-- 				 					<td><c:if test="${gs.directstore==1 }">
					 						是
					 					</c:if>
					 					<c:if test="${gs.directstore==0 }">
					 						否
					 					</c:if></td> --%>
					 					<td><c:if test="${gs.nowsum==null}">
					 						0
					 					</c:if>
					 					<c:if test="${gs.nowsum!=null}">
					 						<fmt:formatNumber type="number" value="${gs.nowsum}" minFractionDigits="2"/>
					 					</c:if>
					 					</td>
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