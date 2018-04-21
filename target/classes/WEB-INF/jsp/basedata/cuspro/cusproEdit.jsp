<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<title>Insert title here</title>
<script type="text/javascript">
		function checkdata()
		{
			if(document.getElementById('pcName').value==""){
				$.dialog.alert_w("客商名称不能为空!");
			  	return false;
			}
			else
			{ 
				
				return true;
			}
		}
		function insertRow(){
			var strs='<tr>'+
			'<td><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>'+
			'<td><input type="text" id="deliverPerson" name="deliverPerson" class="listword"></td>'+
			'<td><input type="text" id="deliverTel" name="deliverTel" class="listword"></td>'+
			'<td><input type="text" id="deliverAddr" name="deliverAddr" class="listword"></td>'+
			'</tr>';
			$("#contr").append(strs);
		}
		$(function(){
			$('body').on('click','.delrow',function(){
				var obj=$(this);
				$.dialog.confirm("确定是否删除？",function(){
					obj.parent().parent().remove();},function(){});
			});
		});
		
		function selectcuspro(id){
			    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择客商类别',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/cuspro_ProCusSelect.emi?id='+id,
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					var typeid = this.content.document.getElementById("typeid").value;
					if(typeid=="0"){
						document.getElementById('customerName').value=name;
						document.getElementById('customerId').value=id;
					}
					if(typeid=="1"){
						document.getElementById('providerName').value=name;
						document.getElementById('providerId').value=id;
					}
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
	</script>
</head>
	<body>
	<form name="myform" id="myform" method="post" action="" onsubmit="return checkdata()">
	<input type="hidden" id="cusprogid" name="cusprogid" value="${cusprogid}">
		<div style="height:20px;"></div>
		<div class="divtext">
			<div class="textbt fl">客商编码：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="pcCode" name="pcCode" value="${aaprocus['pcCode']}" readonly="readonly" style="background:#F0F0F0">
			</div>
			<div class="fl xing">*</div>
			<div class="textbt fl">客商名称：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="pcName" name="pcName" value="${aaprocus['pcName']}">
			</div>
			<div class="fl xing">*</div>
			<div class="textbt fl">客商地址：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="addr" name="addr" value="${aaprocus['addr']}">
			</div>
			<div class="cf"></div>
		</div>
		<div class="divtext">
			<div class="textbt fl">启用时间：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="beginTimes" name="beginTimes" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" value="${aaprocus['beginTimes']}">
			</div>
			<div class="textbt fl" style="margin-left:10px;">停用时间：</div>
			<div class="textipt fl">
				<input type="text" class="writeipt" id="endTimes" name="endTimes" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" value="${aaprocus['endTimes']}">
			</div>
			<div class="textbt fl">属性方案：</div>
			<div class="textipt fl">
				 <select id="category" name="category">
					<c:forEach var="cate" items="${category}">
					<option value="${cate['gid']}" <c:if test="${cate['gid']==aaprocus['soulationId']}">selected="selected"</c:if>>${cate['souname']}</option>
					</c:forEach>
				</select> 
			</div>
			<div class="cl"></div>
		</div>		
		<div class="divtext">
			<div class="textbt fl">客户类别：</div>
			<div class="textipt fl" style="position: relative;">
				<img src="${ctx}/img/sousuo.png" class="div_img" style="margin-left:185px;top:6px;" onclick="selectcuspro(0)">
				<input type="text" class="writeipt" id="customerName" name="customerName" value="${aaprocus['customerName']}">
				<input type="hidden" class="writeipt" id="customerId" name="customerId" value="${aaprocus['customerId']}">
			</div>
			<div class="textbt fl" >供应商类别：</div>
			<div class="textipt fl" style="position: relative;">
				<img src="${ctx}/img/sousuo.png" class="div_img" style="margin-left:185px;top:6px;" onclick="selectcuspro(1)">
				<input type="text" class="writeipt" id="providerName" name="providerName" value="${aaprocus['providerName']}">
				<input type="hidden" class="writeipt" id="providerId" name="providerId" value="${aaprocus['providerId']}">
			</div>
			<div class="cf"></div>
		</div>
		<div class="EMonecontent" style="">
			<div style="height:15px;"></div>
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div style="height:10px;"></div>
		 		<div class="createdList">
		 			<table id="myTable">
			 			<tbody id="contr">
			 				<tr>
			 					<th style="width:80px;">操作</th>
			 					<th>联系人名称</th>
			 					<th>联系人电话</th>
			 					<th>联系人地址</th>
			 					
			 				</tr>
			 			<c:forEach var="type" items="${cusprobook}" varStatus="stat">
				 		<tr>
				 		    <td><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>
			                <td><input type="text" id="deliverPerson" name="deliverPerson" class="listword" value="${type.deliverPerson}"></td>
			                <td><input type="text" id="deliverTel" name="deliverTel" class="listword" value="${type.deliverTel}"></td>
			                <td><input type="text" id="deliverAddr" name="deliverAddr" class="listword" value="${type.deliverAddr}"></td>
				 		</tr>
				 	    </c:forEach>
			 			</tbody>
			 		</table>
					<div class="addrow fl" style="margin-left:50px;margin-top:-23px;" onclick="insertRow();">
					</div>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		</div>
		</form>
	</body>
</html>