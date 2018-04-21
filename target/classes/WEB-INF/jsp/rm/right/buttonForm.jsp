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
	
		if("${isRoot}"=="true"){
			document.getElementById("rightName").disabled=true;
			document.getElementById("rightCode").disabled=true;
			document.getElementById("rightUrl").disabled=true;
			document.getElementById("ownerSys").disabled=true;
			document.getElementById("chooseImg").disabled=true;
			document.getElementById("saveBtn").style.display="none";
			document.getElementById("delBtn").style.display="none";
		}
		var fun = "${right.functions}"-0;
		//判断checkbox是否需要选中
		var funsCk = document.getElementsByName('funs');
		for(var i=0;i<funsCk.length;i++){
			if(fun&Math.pow(2,i+1)){
				funsCk[i].checked=true;
			}
		}
	});
	
	// 打开用户选择界面
	function showUser(){
		
	}
	
	// 保存权限模块
	function saveRight(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
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
					  url: '${ctx}/right_updateRight.emi',
					  success: function(req){
						  if(req=='success'){
							  var treeWin = parent.document.getElementById("rightTree").contentWindow;
							  if(document.getElementById("superiorRightIdChanged").value=="1"){
								  //如果父节点改变了
								  var thisupgid ="${right.superiorRightId}";
								  var thisgid = document.getElementById('superiorRightId').value;
									if(thisgid==''){
										thisgid="null";
									}
						     		$.ajax({
										type: 'POST',
										url:'${ctx}/right_updateRight.emi?upgid='+thisgid+"&superupgid="+thisupgid,
										data:$('#myform').serialize(),
										success: function (mes){
											$.dialog.alert_s('保存成功',function(){
												window.location.href="${ctx}/right_toRightForm.emi";
												//document.getElementById("myform").reset();
											});
											treeWin.location.href="${ctx}/right_toRightTree.emi";
											
											/*if(thisgid=='null'){
												// treeWin.getZTree().reAsyncChildNodes(null, "refresh");
												 //treeWin.reload();
												 var rootNodes = treeWin.getZTree().getNodesByFilter(function (node) { return node.level == 0; });
												 for(var ri=0;ri<rootNodes.length;ri++){
													 treeWin.getZTree().reAsyncChildNodes(rootNodes[ri],"refresh");
												 }
												// treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
											}else{
												 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
												 treeWin.getZTree().reAsyncChildNodes(treeWin.getZTree().getNodeByParam("gid",thisgid, null),"refresh");
											}*/
										}
									});
							  }else{
								  $.dialog.alert_s('保存成功');
								  treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
							  }
						  }else if(req=='sameCode'){
								$.dialog.alert_e('该功能代码已存在，不能重复');
						  }else {
							  $.dialog.alert_e("保存失败");
						  }
					  }
				});
			}
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
	
	//添加子模块
	function addChild(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
			location.href="${ctx}/right_toAddRight.emi?ownerSys=${right.ownerSys}&gId=${right.gid}";
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
	
	//校验数据
	function checkdata(){
		for(var i=0;i<5;i++){
		      if($('.inputxt').eq(i).val()==''){
		    	  $.dialog.alert_e('内容填写不完整');
		    	  return false;
		      }
		}
		return true;
	}
	
	function deleteRight(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
			if(confirm("确定删除?")){
				$.ajax({
					  data: $("#myform").serialize(),
					  type: 'POST',
					  url: '${ctx}/right_deleteRight.emi?gid=${right.gid}',
					  success: function(req){
						  if(req=='success'){
							 // $.dialog.alert_s('删除成功');
							 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
							 location.href="${ctx}/right_toRightForm.emi";
						  }else{
							  $.dialog.alert_e("删除失败");
						  }
					  }
				});
			}
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
	
	function showRightTree(){
		var gid="${right.gid}";
		var systeam=$('#ownerSys').val();
		//alert(gid+"\\"+systeam);
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		/* var pid=$('#gid').val();
		var enterid= $('#enterpriseId').val();
		var levelnum= $('#levelNum').val();
		var gid=$('#gid').val(); */
		var pwdWin = $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'修改上级部门',
		    width: '800px',
			height: '300px',
			content: 'url:right_updateUpRight.emi?gid='+gid+'&sys='+systeam,
			ok:function(){
				var thisgid = this.content.document.getElementById('onselectgid').value;
				var thisName = this.content.document.getElementById('onselectName').value;
				document.getElementById("superiorRightId").value=thisgid;
				document.getElementById("superiorRightName").value=thisName;
				document.getElementById("superiorRightIdChanged").value="1";
				/*var thisupgid ="${right.superiorRightId}";//代码移到：saveRight()
				alert(thisgid);
				if(thisgid==''){
					thisgid="null";
				}
	     	$.ajax({
					type: 'POST',
					url:'${ctx}/right_updateRight.emi?upgid='+thisgid+"&superupgid="+thisupgid,
					data:$('#myform').serialize(),
					success: function (mes){
						if(thisgid=='null'){
							 treeWin.getZTree().reAsyncChildNodes(null, "refresh");
						}else{
						 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
						 treeWin.getZTree().reAsyncChildNodes(treeWin.getZTree().getNodeByParam("gid",thisgid, null),"refresh");
						}
						}
				});*/
				
				
			},
			cancel:true
		});	
		
	}
	//添加按钮
	function addButton(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
			location.href="${ctx}/right_toAddRightBtn.emi?ownerSys=${right.ownerSys}&gId=${right.gid}";
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
</script>
<body>
	<div id="container">
		<div id="content">
			<form action="${ctx }/right_updateRight.emi" method="post" id="myform" class="myform"  onsubmit="return checkdata()">
				<!--按钮栏-->
				<div id="toolBar">
					<div class="fl btn">
						<input id="saveBtn" type="button" value="保存" onclick="saveRight()"/>
						<input id="delBtn" type="button" value="删除" onclick="deleteRight()"/>
						&nbsp;&nbsp;
						<!-- <input id="addBtn" style="width: 100px" type="button" value="添加按钮" onclick="addButton()"/> -->
					</div>
					<div class="cf"></div>
				</div>
				<!--内容部分-->
				<div id="createbody">
					<!--标题名-->
					<div id="createName">
						<div class="Name">编辑功能</div>
					</div>
                    
	              <div id="createwords" style="height: 320px">
	               <input type="hidden" name="right.rightType" value="1">
		              <div class="createhead">
		              <input type="hidden" name="right.gid" id="right.gid" value="${right.gid }">
		                <table style="width: 70%">
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">功能名称</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightName" id="rightName" value="${right.rightName }">
		                    <span class="fl" style="color: red;">*</span></td>
		                  </tr>
		                   <tr >
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">功能代码</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightCode" id="rightCode" value="${right.rightCode }">
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                  <tr style="display: none">
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">跳转地址</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl inputxt" type="text"  name="right.rightUrl" id="rightUrl" value="${right.rightUrl }">
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                  <%-- <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">样式模板</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 40%" class="fl inputxt"  name="right.buttonCss" id="buttonCss" >
			                    <option value="">--请选择--</option>
			                    <c:forEach var="css" items="${cssList }">
			                  	  <option value="${css.value }" <c:if test="${right.buttonCss==css.value}">selected</c:if> >${css.name}</option>
			                    </c:forEach>
		                    </select>
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr> --%>
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">所属模块</span></td>
		                    <td width="50%" align="left"><input style="width: 80%" class="fl" type="text"  name="right.superiorRightName" id="superiorRightName" readonly="readonly" value="${superiorRightName }">
		                    <input type="hidden" name="right.superiorRightId" id="superiorRightId" value="${right.superiorRightId }">
		                    <input type="hidden" name="superiorRightIdChanged" id="superiorRightIdChanged" value="0">
		                    <img id="chooseImg" alt="选择" src="${ctx }/images/common/chose.png" onclick="showRightTree();">
		                    </td>
		                  </tr>
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">所属系统</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 40%" class="fl inputxt"  name="right.ownerSys" id="ownerSys" >
			                    <option value="">--请选择--</option>
			                    <c:forEach var="sys" items="${systems }">
			                  	  <option value="${sys.shortName }" <c:if test="${right.ownerSys==sys.shortName}">selected</c:if> >${sys.fullName}</option>
			                    </c:forEach>
		                    </select>
		                    <span class="fl" style="color: red;">*</span>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td width="50%" height="30" align="right"><span class="labelName1 fl">是否显示</span></td>
		                    <td width="50%" align="left">
		                    <select style="width: 40%" class="fl inputxt"  name="right.isShow" id="isShow" >
			                    <option value="1" <c:if test="${right.isShow==1}">selected</c:if>>显示</option>
			                  	<option value="0" <c:if test="${right.isShow!=1}">selected</c:if>>不显示</option>
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
</html>