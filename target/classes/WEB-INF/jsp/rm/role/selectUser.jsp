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

	
	//checkbox点击事件
	function clickCheck(obj){
		
		var selectedUserid=$('#selectedUserIds').val();
		
		if($(obj).attr("checked")=="checked"){
			
			var tempStr=selectedUserid.substring(selectedUserid.length-1,selectedUserid.length);
			
			if(tempStr == ","){
				selectedUserid+=$(obj).val().split(",")[0]+",";
			}else{
				selectedUserid+=","+$(obj).val().split(",")[0]+",";
			}
			
			
			$('#selectedUserIds').val(selectedUserid);
			
		}else{
			selectedUserid=selectedUserid.replace($(obj).val().split(",")[0]+",","");
			$('#selectedUserIds').val(selectedUserid);
		}
		
 		var allchecked = true;
		var uck = document.getElementsByName('userCheck');
		var allck = document.getElementById('all');
		
		for(var i=0;i<uck.length;i++){
			if(!uck[i].checked){
				allchecked = false;
				break;
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
</script>

</head>
<body>
	<form action="${ctx}/role_toSelectUser.emi" method="post" >
		
		<input class="selectedUserIds" name="selectedUserIds" id="selectedUserIds" type="hidden" value="${selectedUserIds }" >
		
		<input class="selectedUserNames" name="selectedUserNames" id="selectedUserNames" type="hidden" value="${selectedUserNames }" >
	
		<div class="EMonecontent">
			
			<!--按钮部分-->
		 	<div class="toolbar" style="margin:20px 0 20px 0">
		 		<ul>
		 			<input type="text" name="keyWord" placeholder="单据编号关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> 
		 			<input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
		 			
		 			
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		
			<!--内容部分-->
			<div class="creattable">
		 		<div class="createdList">
					<table>
						<tbody>
							<tr>
								<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
								<th width="5%" style="text-align: center;">序号</th>
								<th width="30%" style="text-align: center;">用户编码</th>
								<th width="30%" style="text-align: center;">用户名称</th>
								<th width="30%" style="text-align: center;">备注</th>
							</tr>
							<c:forEach var="user" items="${userList }" varStatus="stat">
								<tr>
									<td id="tool" class="toolTd"><input type="checkbox" name="userCheck" value="${user.gid },${user.userName}" <c:if test="${fn:containsIgnoreCase(selectedUserIds, user.gid)}">checked</c:if> onclick="clickCheck(this);"></td>
									<td>${stat.count }</td>
									<td>${user.userCode }</td>
									<td>${user.userName }</td>
									<td>${user.notes }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
		</div>
	</form>
</body>
</html>