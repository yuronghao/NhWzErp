<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>

<style>
	.wordname{
		width:125px;
	}
	.wordli{width:32%;}
	.wordnameinput{width:56%;}
</style>


<script type="text/javascript">

function checkdata()
{
	if(document.getElementById('goodsSortUid').value==""){
		$.dialog.alert_w("物料分类不能为空!");
	  	return false;
	}
	else
	{ 
		
		return true;
	}
}

	function selectcuspro(id){
	    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'选择物料类别',
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
				if(typeid=="2"){
					document.getElementById('goodsSortName').value=name;
					document.getElementById('goodsSortUid').value=id;
				}
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}

	function selectmanager(){
		var pwdWin = $.dialog({ 
			drag: true,
			lock: true,
			resize: false,
			title:'选择仓库',
		    width: '800px',
			height: '400px',
			zIndex:3004,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.WAREHOUSE%>&multi=0&showTree=0',
			okVal:"确定",
			ok:function(){
				debugger;
				var usercheck = this.content.window.jsonArray;
				
				document.getElementById('whName').value=usercheck[0].whname;
				document.getElementById('whUid').value=usercheck[0].gid;
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}
	
	//选择单位
	function selectUnit(flag){
		var ok=true;
		if($('#goodsUnitGroupGid').val()!='' || $('#goodsUnitGroupName').val()!=''){
			
			$.dialog.alert_w("请先删除计量单位组!");
			ok=false;
		}
		if(ok){
		    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择单位',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/unit_getUnitHelp.emi',
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					
					if(flag=="main"){
						document.getElementById('goodsUnitName').value=name;
						document.getElementById('goodsUnitGid').value=id;
						
						document.getElementById('cassComUnitName').value="";
						document.getElementById('cassComUnitGid').value="";
					}
					
					if(flag=="cass"){
						document.getElementById('cassComUnitName').value=name;
						document.getElementById('cassComUnitGid').value=id;
					}
					
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}

	}
	
	//选择单位组
	function selectUnitGroup(){
	    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'选择单位组',
		    width: '800px',
			height: '400px',
			zIndex:2000,
			content: 'url:${ctx}/wms/unit_getUnitConversionHelp.emi',
			okVal:"确定",
			ok:function(){
				var groupid = this.content.document.getElementById("groupid").value;
				var groupname = this.content.document.getElementById("groupname").value;
				
				var mid = this.content.document.getElementById("mid").value;
				var mname = this.content.document.getElementById("mname").value;
				
				var fid = this.content.document.getElementById("fid").value;
				var fname = this.content.document.getElementById("fname").value;
				
				document.getElementById('goodsUnitGroupName').value=groupname;
				document.getElementById('goodsUnitGroupGid').value=groupid;
				
				document.getElementById('goodsUnitName').value=mname;
				document.getElementById('goodsUnitGid').value=mid;
				
				document.getElementById('cassComUnitName').value=fname;
				document.getElementById('cassComUnitGid').value=fid;
				
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}
	
	
	$(function(){
		$('#goodsUnitGroupName').change(function(){
			$('#goodsUnitGroupGid').val("");
		})
	})
	
	
	
	
	
</script>




<body>
	<div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
<!-- 	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" class="backBtn" value="返回"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div> -->

		<form id="myform" method="post" action="" onsubmit="return checkdata()">
		 	<!--主体部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">物料档案</div>		 		
		 		<div class="createdList" style="height:80%;">
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">物料编号：</div>
							<div class="wordnameinput fl">
								<input type="hidden"  id="goodsGid" name="goodsGid" value="${aaGoods.goodsMap.gid }">
								<input type="text"  id="goodsCode" name="goodsCode" value="${aaGoods.goodsMap.goodscode }">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">物料名称：</div>
							<div class="wordnameinput fl"><input type="text"  id="goodsName" name="goodsName" value="${aaGoods.goodsMap.goodsname }"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">规格型号：</div>
							<div class="wordnameinput fl"><input type="text"  id="goodsStandard" name="goodsStandard" value="${aaGoods.goodsMap.goodsstandard }"> </div>
							<div class="cf"></div> 
		 				</li>

		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">计量单位组：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="${aaGoods.unitGroupGid }" id="goodsUnitGroupGid" name="goodsUnitGroupGid" >
								<input type="text" value="${aaGoods.unitGroupName }" id="goodsUnitGroupName" name="goodsUnitGroupName" >
							 </div>
							<img src="${ctx}/img/sousuo.png" class="cf"  onclick="selectUnitGroup()">
							<div class="cf"></div> 
		 				</li>
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">主计量单位：</div>
							<div class="wordnameinput fl">
								<input type="hidden"  id="goodsUnitGid" name="goodsUnitGid" value="${aaGoods.goodsEntity.goodsunit }" >
								<input type="text"  id="goodsUnitName" name="goodsUnitName" value="${aaGoods.goodsEntity.unitName }" >
							 </div>
							<img src="${ctx}/img/sousuo.png" class="cf"  onclick="selectUnit('main')">
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">辅计量单位：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="${aaGoods.goodsEntity.cstcomunitcode }" id="cassComUnitGid" name="cassComUnitGid"> 
								<input type="text" value="${aaGoods.goodsEntity.cstComUnitName }" id="cassComUnitName" name="cassComUnitName" readonly="readonly">
							</div>
							<%-- <img src="${ctx}/img/sousuo.png" class="cf"  onclick="selectcuspro(1)"> --%>
							<div class="cf"></div> 
		 				</li>

		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">物品条码：</div>
							<div class="wordnameinput fl"><input type="text" value="${aaGoods.goodsMap.goodsbarcode }" id="goodsBarCode" name="goodsBarCode"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">默认扫描数量：</div>
							<div class="wordnameinput fl"><input type="text" value="${aaGoods.goodsMap.scanNum }" id="scanNum" name="scanNum" > </div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">物料分类：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="${aaGoods.goodsMap.goodssortuid }" id="goodsSortUid" name="goodsSortUid" > 
								<input type="text" value="${aaGoods.goodsEntity.classificationName }" id="goodsSortName" name="goodsSortName"> 
							</div>
							<img src="${ctx}/img/sousuo.png" class="cf"  onclick="selectcuspro(2)">
							<div class="cf"></div> 
		 				</li>

		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">属性方案：</div>
							<div class="wordnameinput fl">
								<select id="soulationgid" name="soulationgid" >
									<option>方案1</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">采购超订单入库比例：</div>
							<div class="wordnameinput fl"><input type="text" value="${aaGoods.goodsMap.procureabovescale }" id="procureAboveScale" name="procureAboveScale"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">生产超订单入库比例：</div>
							<div class="wordnameinput fl"><input type="text" value="${aaGoods.goodsMap.produabovescale }" id="produAboveScale" name="produAboveScale"> </div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">计价方式：</div>
							<div class="wordnameinput fl">
								<select id="valuationGid" name="valuationGid">
									<option>全月平均法</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">是否直接入库：</div>
							<div class="wordnameinput fl">
								<select id="directStore" name="directStore">
								
								
									<c:if test="${aaGoods.goodsMap.directstore==1 }">
										<option value="0">否</option>
										<option value="1" selected="selected">是</option>
									</c:if>
									
									<c:if test="${aaGoods.goodsMap.directstore==0 }">
										<option value="0" selected="selected">否</option>
										<option value="1">是</option>
									</c:if>
									
									<c:if test="${aaGoods.goodsMap.directstore ==null }">
										<option value="0" selected="selected">否</option>
										<option value="1">是</option>
									</c:if>
									
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">是否批次管理：</div>
							<div class="wordnameinput fl">
								<select id="binvBach" name="binvBach">
								
								
									<c:if test="${aaGoods.goodsMap.binvbach==1 }">
										<option value="0">否</option>
										<option value="1" selected="selected">是</option>
									</c:if>
									
									<c:if test="${aaGoods.goodsMap.binvbach==0 }">
										<option value="0" selected="selected">否</option>
										<option value="1">是</option>
									</c:if>
									
									<c:if test="${aaGoods.goodsMap.binvbach ==null }">
										<option value="0" selected="selected">否</option>
										<option value="1">是</option>
									</c:if>
									
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">默认仓库：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="${aaGoods.goodsMap.cdefwarehouse }" id="whUid" name="whUid" > 
								<input type="text" value="${aaGoods.aaWarehouse.whname }" id="whName" name="whName">
							</div>
							<img src="${ctx}/img/sousuo.png" class="cf"  onclick="selectmanager()">
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div>
		 			</ul>
		 			
		 			<div style="height: 30px;"></div>
		 		</div>		 		
		 	</div>
		 	<!--主体部分 end-->	
	 	</form>
	 	
	</div>
</body>
</html>