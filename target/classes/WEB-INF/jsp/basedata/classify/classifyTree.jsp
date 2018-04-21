<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>EMI SOFTWEAR</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico" />
<link rel="stylesheet" href="${ctx }/css/common.css" />
<link rel="stylesheet" href="${ctx }/scripts/ztree/css/demo.css" type="text/css">
<link rel="stylesheet"
	href="${ctx }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${ctx }/scripts/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${ctx }/scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script>
		
<style type="text/css">
	body {
	     overflow-x : hidden;   
	     overflow-y : hidden;   
	}
</style>	

</head>
<body>
	<div id="createwords" style="height: 420px;border-right:1px solid #dddddd">
		<div class="zTreeDemoBackground fl">
			<ul id="treeDemo" class="ztree" style="height: 420px;width: 200px;border: none;background: #ffffff;" ></ul>
		</div>
	</div>
</body>

<SCRIPT type="text/javascript">
		
        var userdata;
        var treeObj;
		$(document).ready(function(){
			var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey:"gid",
							pIdKey:"parentid"
						},
						key:{
							children: "childNodes",
							name:"classificationname"
						}
					},
					check: {
						enable: false,
					},
					async: {
						enable: true,
						url:"${ctx}/wms/classify_getClassifyChildren.emi",
						autoParam:["gid","classificationname","isRoot","stylegid"],
						//otherParam:{"otherParam":"zTreeAsyncTest"},
						dataFilter: filter
					},
					callback:{
						onClick: zTreeOnClick
					}

				};
				var	zNodes = ${classifyList};
				treeObj =$.fn.zTree.init($("#treeDemo"), setting,zNodes);
				//treeObj.expandAll(true);
		});

			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					childNodes[i].isParent = childNodes[i].childrenNum>0;
				}
				return childNodes;
			};
			
			function beforeClick(treeId, treeNode) {
				/* if (!treeNode.isParent) {
					alert("请选择父节点");
					return false;
				} else {
					return true;
				} */
			}
			
			function zTreeOnClick(event, treeId, treeNode) {
                var formWin = parent.document.getElementById("rightForm").contentWindow;
                formWin.location.href="${ctx}/wms/classify_toClassifyForm.emi?gid="+treeNode.gid+"&isRoot="+treeNode.isRoot+"&stylegid="+treeNode.stylegid;
                
            };
            
            function selectedNode(){
            	return treeObj.getSelectedNodes();
            }
            
            function getZTree(){
            	return treeObj;
            }
			

	</SCRIPT>



</html>