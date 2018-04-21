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
	<!--将值传给调用界面-->
	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
		api.button({
			id:'valueOk',
			name:'确定',
			callback:ok
		});
		function ok()
		{
					
				var strs='<tr class="contentTr">'+
				'<td class="NO"><input class="center" type="text" name="" id="" value=""/></td>'+
				'<td class="flag" style="display: none;"><input type="text" name="" id=""  value="2"/></td>'+
				'<td class="gid" style="display: none;"><input type="text" name="" id=""  /></td>'+
				'<td class="goodsUid" style="display:none;"><input  type="text" name="" id="" /></td>'+
				'<td class="goodsCode"><input  type="text" name="" id="" /></td>'+
				'<td class="goodsName"><input  readonly="readonly" type="text" name="" id="" /></td>'+
				'<td class="goodsType"><input readonly="readonly" type="text" name="" id="" /></td>'+
				'<td class="number"><input  type="text" name="" id="" onchange="calculate(this)"/></td>'+
				'<td class="goodsUnit"><input  readonly="readonly" type="text" name="" id="" /></td>'+
				'<td class="planDG"><input  type="text" name="" id="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})"/></td>'+
				'<td class="originalTaxPrice"><input  type="text" name="" id="" onchange="calculate(this,0)"/></td>'+
				'<td class="originalTaxMoney"><input  type="text" name="" id="" onchange="calculate(this,1)"/></td>'+
				'<td class="originalNotaxPrice"><input  type="text" name="" id="" onchange="calculate(this,2)"/></td>'+
				'<td class="originalNotaxMoney"><input  type="text" name="" id="" onchange="calculate(this,3)"/></td>'+
				'<td class="originalTax"><input  type="text" name="" id="" onchange="calculate(this,4)"/></td>'+
				'<td class="localTaxPrice"><input  type="text" name="" id="" onchange="calculate(this,5)"/></td>'+
				'<td class="localTaxMoney"><input  type="text" name="" id="" onchange="calculate(this,6)"/></td>'+
				'<td class="localNotaxPrice"><input type="text" name="" id="" onchange="calculate(this,7)"/></td>'+
				'<td class="localNotaxMoney"><input  type="text" name="" id="" onchange="calculate(this,8)"/></td>'+
				'<td class="localTax"><input  type="text" name="" id="" onchange="calculate(this,9)"/></td>'+
				'</tr>';
				var tbody = W.document.getElementById("newbody");
				var ss=$(tbody).append(strs);	
				//获取主界面上的方法对td进行排序
				W.getOrder();
			
		};
	</script>
	
	
	<!--从界面上接收值-->
	<script type="text/javascript">
	
		var api = frameElement.api, W = api.opener;
		window.onload = function()
		{
		    document.getElementById('valueFromMain').value = W.document.getElementById('saveOrder').value;
		};
	</script>
	
	
	<script type="text/javascript">
		//双击传值到主页面方法
		function getValueByDouble(wlUid,wlCode,wlName,wlType,wlUnit){
				
			var api = frameElement.api, W = api.opener;
			function ok()
			{
				var v=document.getElementById('valueFromMain').value;
				W.document.getElementById('gGoodsUid'+v).value  = wlUid;
				W.document.getElementById('gGoodsCode'+v).value = wlCode;
				W.document.getElementById('gGoodsName'+v).value = wlName;
				W.document.getElementById('gGoodsType'+v).value = wlType;
				W.document.getElementById('gGoodsUnit'+v).value = wlUnit;
			};
			
			ok();
			api.close();
			
		};

	</script>
	

	
</head>

<body>

	
	
<div id="container">
	
	<!--存放调用界面传来的序号值-->
	<input type="hidden" name="" id="valueFromMain" />
	
	<div class="content">
	
		<div id="goodsTitle" class="publictitle">物&nbsp;料&nbsp;列&nbsp;表</div>
		
		<div id="publicrList">
		
			<div class="fl" id="deptTree" style="width:30%">
			
				<!--存放选中的物料类别uid-->
				<input type="hidden" name="currentUid" id="currentUid" />
				<input type="hidden" name="currentCode" id="currentCode" />
				<input type="hidden" name="currentName" id="currentName" />
				
				<input type="hidden" name="wlCodeSelected" id="wlCodeSelected" />
				<input type="hidden" name="wlNameSelected" id="wlNameSelected" />
				<input type="hidden" name="wlTypeSelected" id="wlTypeSelected" />
				<input type="hidden" name="wlUnitSelected" id="wlUnitSelected" />
				<input type="hidden" name="wlGidSelected"  id="wlGidSelected" />


				
				<ul id="ztree" class="ztree" style="height:450px"></ul>
				
			</div>
				
			<div  class="fl" id="goods" style="margin-left:30px;height:450px;width:60%" >
				11111
			</div>
			
			<div class="cf"></div>
			

		</div>
			
	</div>

	
</div>





</body>
</html>
