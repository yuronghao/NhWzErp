<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>EMI SOFTWEAR</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico" />
<link rel="stylesheet" href="${ctx }/css/common/common_rm.css" />
<link rel="stylesheet" href="${ctx }/login.css" />
<link rel="stylesheet" href="${ctx }/css/ztree/demo.css" type="text/css">
<link rel="stylesheet"
	href="${ctx }/css/ztree/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${ctx }/scripts/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${ctx }/scripts/ztree/jquery.ztree.excheck-3.5.js"></script>
		
<style type="text/css">
	body {
	     overflow-x : hidden;   
	     overflow-y : hidden;   
	}
</style>	

</head>
<body>

	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>

<SCRIPT type="text/javascript">
        var userdata;
		//$(document).ready(function(){
			var setting = {
				treeId:"ztree",
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						idKey: "gid",
					},
					key: {
						children: "childNodes",
						name: "rightName"
					}
				},
				async: {
					enable: true,
					url:"${ctx}/right_getRightTree.emi",
					autoParam:["gid","rightName","ownerSys"],
					//otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				},
				callback: {
					 beforeClick: beforeClick,
					 onClick: zTreeOnClick,
					 /*beforeAsync: beforeAsync,
					onAsyncError: onAsyncError,
					onAsyncSuccess: onAsyncSuccess */
				}
			};

			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					childNodes[i].isParent = childNodes[i].isLast==0;
				}
				return childNodes;
			}
			
			function beforeClick(treeId, treeNode) {
				/* if (!treeNode.isParent) {
					alert("请选择父节点");
					return false;
				} else {
					return true;
				} */
			}
			var	zNodes = ${zNodes};
			var treeObj = $.fn.zTree.init($("#treeDemo"), setting,zNodes);
			
			function zTreeOnClick(event, treeId, treeNode) {
                var formWin = parent.document.getElementById("rightAuth").contentWindow;
                formWin.location.href="${ctx}/right_toRightAuth.emi?gId="+treeNode.gid+"&ownerSys="+treeNode.ownerSys+"&isRoot="+treeNode.isRoot;
            };
            
            function selectedNode(){
            	return treeObj.getSelectedNodes();
            }
            
            function getZTree(){
            	return treeObj;
            }

	</SCRIPT>



</html>