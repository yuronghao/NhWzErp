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
		 <div class="EMonecontent">

		 	<!--主体部分-->
		 	<div class="mainword">
		 		<div class="mainaddword" style="min-height: 350px;">
		 			<ul class="EMtree_word fl" style="width: 55%;">
		 				<li>
		 					<div class="fl mainaddword_name">部门编码：</div>
		 					<div class="fl mainaddword_word">
		 						<input type="hidden" id="depGid" name="depGid" class="addwordinput" value="${aaDepartment.gid }">
		 						<input type="text" id="depcode" name="depcode" class="addwordinput" value="${aaDepartment.depcode }">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">部门名称：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="" name="" class="addwordinput" value="${aaDepartment.depname }"></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">部门负责人：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="" name="" class="addwordinput" value="${aaDepartment.principal }"></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">上级部门：</div>
		 					<div class="fl mainaddword_word">
		 						<input type="hidden" id="depparentuid" name="depparentuid" value="${aaDepartment.depparentuid }" class="addwordinput">
		 						<input type="text" id="depparentname" name="depparentname" value="${aaDepartment.depparentname }" class="addwordinput">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">备注：</div>
		 					<div class="fl mainaddword_word"><input type="text" id="" name="" value="${aaDepartment.notes }" class="addwordinput"></div>
		 					<div class="cf"></div>
		 				</li>
		 				 <li>
		 					<div class="fl mainaddword_name">是否车间：</div>
		 					<div class="fl mainaddword_word"><c:if test="${aaDepartment.isWorkshop==0 }">是</c:if><c:if test="${aaDepartment.isWorkshop==1 }">否</c:if></div>
		 					<div class="cf"></div>
		 				</li>
		 			</ul>
		 			<div class="cf"></div>
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
		 	 
		</div>

	</form>
</body>
</html>