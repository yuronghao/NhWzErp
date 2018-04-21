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
		if(document.getElementById('whcode').value==""){
			$.dialog.alert_w("仓库编码不能为空!");
		  	return false;
		}else if(document.getElementById('whname').value==""){
			$.dialog.alert_w("仓库名称不能为空!");
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
		<div style="height:20px;"></div>
		<div class="divtext">
			<div class="textbt fl">编码：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="whcode" name="whcode" value="" />
			</div>
			<div class="textbt fl">名称：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="whname" name="whname" value="" />
			</div>
			<div class="textbt fl">仓库条码：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="barcode" name="barcode" value="" />
			</div>
			<div class="cf"></div>
		</div>
		<div class="divtext">
			<div class="textbt fl">地址：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="whaddr" name="whaddr" value="" />
			</div>
			<div class="textbt fl">联系人：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="linkman" name="linkman" value="" />
			</div>
			<div class="textbt fl">联系电话：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="whtel" name="whtel" value="" />
			</div>
			<div class="cf"></div>
		</div>
		<div class="divtext">
			<div class="textbt fl">经度：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="longitude" name="longitude" value="" />
			</div>
			<div class="textbt fl">纬度：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="latitude" name="latitude" value="" />
			</div>
<!-- 			<div class="textbt fl">启用日期：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="" value="" />
			</div>
			
			<div class="textbt fl">停用日期：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="" value="" />
			</div> -->
			
			<div class="textbt fl">货位管理：</div>
			<div class="textipt fl">
				<select id="whpos" name="whpos">
					<option  value="0">否</option>
					<option  value="1">是</option>
				</select>
			</div>
			
			<div class="cf"></div>
		</div>
		<div class="divtext">

			<div class="textbt fl">备注：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="notes" name="notes" value="" />
			</div>
			
			<div class="cf"></div>
		</div>
	</form>

</body>
</html>