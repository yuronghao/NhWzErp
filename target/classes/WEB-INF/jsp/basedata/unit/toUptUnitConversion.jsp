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
		if(document.getElementById('unitGroupName').value==""){
			$.dialog.alert_w("单位组名称不能为空!");
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
		
		<input type="hidden" id="gid" name="gid" value="${map.gid }" />
	
		<div style="height:20px;"></div>
		<div class="divtext">
			<div class="textbt fl">计量单位类别：</div>
			<div class="textipt fl">
				<select class="writeselect" name="unitofmeasurement">
					<c:if test="${map.UnitOfMeasurement==1 }">
						<option value="1" selected="selected">固定换算</option>
						<option value="2">浮动换算</option>
					</c:if>
					
					<c:if test="${map.UnitOfMeasurement==2 }">
						<option value="1">固定换算</option>
						<option value="2" selected="selected">浮动换算</option>
					</c:if>

				</select>
			</div>
			<div class="cf"></div>
		</div>
		
		<div class="divtext">
			<div class="textbt fl">计量单位组：</div>
			<div class="textipt fl">
				<input type="text" id="unitGroupName" name="unitGroupName" value="${map.unitGroupName }" />
			</div>
			<div class="cf"></div>
		</div>
		
		<div class="divtext" style="width:100%;" >
			<div class="textbt fl">
				<input type="text" class="writeipt" style="border:1px #CDCDCD solid;width:80px;" id="" value="1" readonly="readonly" />
			</div>
			<div class="textipt fl" style="margin-left:30px;width:120px;">
				<select class="writeselect" style="width:140px;" name="mainunit">
					<c:forEach items="${units}" var="unit">
						<c:choose>
							<c:when test="${unit.gid==map.mainUnit }">
								<option value="${unit.gid }" selected="selected">${unit.unitname }</option>
							</c:when>
							<c:otherwise>
								<option value="${unit.gid }">${unit.unitname }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="textbt fl" style="width:60px;text-align: center;margin-left:60px;">=</div>
			<div class="textbt fl">
				<input type="text" class="writeipt" style="border:1px #CDCDCD solid;width:80px;" id="" value="${map.auxiliaryQuantity }" name="auxiliaryquantity"/>
			</div>
			<div class="textipt fl" style="margin-left:30px;">
				<select class="writeselect" style="width:140px;" name="auxiliaryunit">
					<c:forEach items="${units}" var="unit">
						<c:choose>
							<c:when test="${unit.gid==map.auxiliaryUnit }">
								<option value="${unit.gid }" selected="selected">${unit.unitname }</option>
							</c:when>
							<c:otherwise>
								<option value="${unit.gid }">${unit.unitname }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="cf"></div>
		</div>
	</form>

</body>
</html>