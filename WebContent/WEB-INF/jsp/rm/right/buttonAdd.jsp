<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="robots" content="index,follow" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/common/common_rm.css">
</head>
<script type="text/javascript">

	$(function() {
	
		var width = $('#createwords').width();
		$('#content').css('width', width + 10 + "px");
	
	});
	
	// 打开用户选择界面
	function showUser(){
		
	}
	
	// 保存权限模块
	function saveRight(){
		if(checkdata()){
			/*var useFuns = 0;
			var ck = document.getElementsByName("funs");
			for(var i=0;i<ck.length;i++){
				if(ck[i].checked){
					useFuns += (ck[i].value-0);
				}
			}
			document.getElementById("useFuns").value=useFuns;*/
			$.ajax({
				data: $("#myform").serialize(),
				type: 'POST',
				url: '${ctx}/right_addRight.emi',
				success: function(req){
					if(req=='success'){
						$.dialog.alert_s('添加成功');
						var superGid = document.getElementById('superiorRightId').value;
						var treeWin = parent.document.getElementById("rightTree").contentWindow;
						var tree = treeWin.getZTree();
						if(superGid==''){
							tree.reAsyncChildNodes(treeWin.selectedNode()[0], "refresh");
						}else{
							//tree.reAsyncChildNodes(tree.getNodeByParam("gid",superGid, null),"refresh");
							tree.reAsyncChildNodes(treeWin.selectedNode()[0].getParentNode(),"refresh");
						}
						
						/*var treeWin = parent.document.getElementById("rightTree").contentWindow;
						if(thisgid=='null'){
							 treeWin.getZTree().reAsyncChildNodes(null, "refresh");
						}else{
						 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
						 treeWin.getZTree().reAsyncChildNodes(treeWin.getZTree().getNodeByParam("gid",thisgid, null),"refresh");
						}*/
					}else if(req=='error'){
						$.dialog.alert_e('添加失败');
					}else if(req=='sameCode'){
						$.dialog.alert_e('该功能代码已存在，不能重复');
					}
				}
			});
		}
	}
</script>
<body>
	<div id="container">
		<div id="content">
			<form action="${ctx }/right_addRight.emi" method="post" id="myform" class="myform"  onsubmit="return checkdata()">
				<!--按钮栏-->
				<div id="toolBar">
					<div class="fl btn">
						<input id="saveBtn" type="button" value="保存" onclick="saveRight()"/>
						<input id="backBtn" type="button" value="返回" onclick="window.history.go(-1)"/>
					</div>
					<div class="cf"></div>
				</div>
				<!--内容部分-->
				<div id="createbody">
					<!--标题名-->
					<div id="createName">
						<div class="Name">新增功能</div>
					</div>
                    
	              <div id="createwords" style="height: 320px">
	               <input type="hidden" name="right.rightType" value="1">
		              <div class="createhead">
		                <table style="width: 70%">
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">功能名称</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightName" id="rightName" >
		                    <span class="fl" style="color: red;">*</span></td>
		                  </tr>
		                   <tr >
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">功能代码</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightCode" id="rightCode" value="fun_">
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                  <tr style="display: none">
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">跳转地址</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightUrl" id="rightUrl" value="/">
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                 <%--  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">样式模板</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 40%" class="fl "  name="right.buttonCss" id="buttonCss" >
			                    <option value="">--请选择--</option>
			                    <c:forEach var="css" items="${cssList }">
			                  	  <option value="${css.value }" >${css.name}</option>
			                    </c:forEach>
		                    </select>
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr> --%>
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">所属模块</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl" type="text"  name="right.superiorRightName" id="superiorRightName" readonly="readonly" value="${superiorRightName }">
		                    <input type="hidden" name="right.superiorRightId" id="superiorRightId" value="${superiorRightId }">
		                    <%-- <img alt="选择" src="${ctx }/images/common/chose.png" onclick="showRightTree();"> --%>
		                    </td>
		                  </tr>
		                   <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">所属系统</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 80%" class="fl inputxt"  name="right.ownerSys" id="ownerSys" >
			                    <option value="">--请选择--</option>
			                    <c:forEach var="sys" items="${systems }">
			                  	  <option value="${sys.shortName }" <c:if test="${ownerSys==sys.shortName}">selected</c:if> >${sys.fullName}</option>
			                    </c:forEach>
		                    </select>
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">是否显示</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 80%" class="fl inputxt"  name="right.isShow" id="isShow" >
			                    <option value="1">显示</option>
			                  	<option value="0">不显示</option>
		                    </select>
		                    </td>
		                  </tr>
		                </table>
		              </div>
		            </div>
              </div></form>
              
		</div>
		
	</div>


</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<4;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog({
	    		    title: '系统提示',
	    		    drag: false,
	    		    resize: false,
	    		    lock: true,
	    		    background: '#000', /* 背景色 */
	    		    opacity: 0.5,       /* 透明度 */
	    		    content: '内容填写不完整',
	    		    icon: 'error.gif',
	    		    ok:true
	    		});
	    	  
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>