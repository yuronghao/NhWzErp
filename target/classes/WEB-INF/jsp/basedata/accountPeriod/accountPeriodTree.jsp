<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账期设置树</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

<link rel="stylesheet" href="${ctx }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/jquery.contextmenu.css">
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.core-3.5.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script src="${ctx}/scripts/jquery.contextmenu.js"></script>
	    

</head>

<script type="text/javascript">

$(document).ready(
	function() {

		var setting = {
			 check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			check: {
				enable: false,
				chkboxType : {
					"Y" : "s",
					"N" : "ps"
				}
			},
			
 			callback:{
				onClick: zTreeOnClick
			}  

		};
		var	zNodes = ${accountPeriodTree};
		var treeObj =$.fn.zTree.init($("#ztree"), setting, zNodes);
		treeObj.expandAll(true);

	});
	

var level;//当前等级
var parentNode;//当前父节点
var parentNodeCode;//当前父节点编码
var isParent;//是否父节点

function zTreeOnClick(event, treeId, treeNode) {

	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var sNodes = treeObj.getSelectedNodes();
	if (sNodes.length > 0) {
		level = sNodes[0].level;
		parentNode = sNodes[0].getParentNode();
		if(parentNode !=null){
			parentNodeCode=parentNode.code;
		}
		
		isParent = sNodes[0].isParent;
	}


	var id=treeNode.id;//此处值为年
	var name=treeNode.name;//此处值为年
	
	$('#editFlag').val("1");//1 保存时需要修改
	$('#selectedYear').val(name);
	$("#saveBtn",parent.document).removeAttr("disabled");
	
	parent.document.getElementById("rightframe").src="${ctx}/wms/accountPeriod_getRightPeriodByClick.emi?iyear="+id;
	
	
}
	
	
</script>

<script>
   $(function() {
		$('.level0,.level1').contextPopup({
			  items: [
			    {label:'删除', action:function(e){
			    	
			    	var iyear=e.currentTarget.innerText;
			    	
			    	var ok=true;
			    	if(iyear=='帐期年度'){
			    		ok=false;
			    		$.dialog.alert_e("请选择年度");
			    	}
			    	
			    	if(ok){
						$.dialog.confirm(tip_confirmDelete, function(){		
							$.ajax({
								  data:{iyear:iyear},
								  type: 'post',
								  url: '${ctx}/wms/accountPeriod_deleteAccountPeriod.emi',
								  dataType:'json',
								  success: function(mes){
									  
									  if(mes.success==1){
										  $.dialog.alert_s('删除成功',function(){
											  window.parent.location.href="${ctx}/wms/accountPeriod_getAccountPeriod.emi";
										  });
									  }else {
										  $.dialog.alert_e("删除失败");
									  }
									  
								  }
							});
						});
			    	}

					
			    	
			    }},
			    {label:'新建下一年度日历',action:function(){
			    	$('#editFlag').val("0");//0保存时需要增加
			    	$("#saveBtn",parent.document).removeAttr("disabled");
			    	window.parent.document.getElementById("rightframe").src="${ctx}/wms/accountPeriod_toAddAccountPeriod.emi";
			    }},
			  ]
		});
		
		
		
		
		
		
		
   });
</script>


<body>
	<input type="hidden" id="editFlag" name="editFlag" value="0"/>

	<ul id="ztree" class="ztree"></ul>

</body>
</html>