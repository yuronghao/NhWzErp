<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>

<script type="text/javascript">
	function checkdata()
	{
		if(document.getElementById('unitCode').value==""){
			$.dialog.alert_w("单位编码不能为空!");
		  	return false;
		}else if(document.getElementById('unitName').value==""){
			$.dialog.alert_w("单位名称不能为空!");
		  	return false;
		}
		else
		{ 
			return true;
		}
	}

</script>


<body>

	<form action="" id="myform">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
		 	<!--主体部分-->
		 	<div class="creattable">		 		
		 		<div class="xz_attribute" style="border:none;">
		 			<ul>
		 				<li style="margin-top:10px;">
		 					<div style="width:40%;text-align:right;" class="fl">计量单位编码：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="hidden" style="" value="${unit.gid }" id="unitGid" name="unitGid"  />
		 						<input type="text" style="" value="${unit.unitcode }" id="unitCode" name="unitCode"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div style="width:40%;text-align:right;" class="fl">计量单位名称：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" style="" value="${unit.unitname }" id="unitName" name="unitName"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
	
		 			</ul>
		 			<div style="height:10px;"></div>
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
		</div>
	</form>

</body>
</html>