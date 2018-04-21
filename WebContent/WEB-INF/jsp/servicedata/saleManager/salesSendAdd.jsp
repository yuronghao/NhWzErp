<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>销售列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript">
	$(function() {
		$("tr:even").addClass("oddbg");
	})
</script>
<style type="text/css">
.fan {
	width: 525px;
}
</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="EMonecontent">
		<div style="width: 100%; height: 15px;"></div>
		<!--按钮部分-->
		<div class="toolbar">
			<div class="fan fl" id="box1" style="display: block;">
				<ul>
					<li class="fl"><input type="button" class="addBtn" value="新增"
						id="addBtn"></li>
					<li class="fl"><input type="button" class="editBtn" value="修改"
						id="revBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="停用"
						id="delBtn"></li>
					<li class="fl"><input type="button" class="saveBtn" value="保存"
						id="saveBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="放弃"
						id="giveUpBtn"></li>
					<div class="cf"></div>
				</ul>
			</div>
			<div class="fan fl" id="box2" style="display: none">
				<ul>

					<li class="fl"><input type="button" class="shenheBtn"
						value="审核" id="auditBtn"></li>
					<li class="fl"><input type="button" class="giveupBtn"
						value="弃审" id="auditBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="列表"
						id="tableBtn"></li>

					<div class="cf"></div>
				</ul>
			</div>
			<div class="fan fl" id="box3" style="display: none">
				<ul>
					<li class="fl"><input type="button" class="delBtn" value="首张"
						id="firstBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="上账"
						id="frontBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="下张"
						id="nextBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="末张"
						id="lastBtn"></li>
					<li class="fl"><input type="button" class="delBtn" value="定位"
						id="locationBtn"></li>
						
					<li class="fl"><input type="button" class="printBtn"
						value="打印" id="printBtn"></li>
					<div class="cf"></div>
				</ul>
			</div>
			<!--<button class="dowmbtn1 fl" id="button1">下翻</button>-->
			<input type="button" class="dowmbtn1 fl" id="button1" value=""
				title="下翻" />
			<!--<button class="btn2" id="button2">上翻</button>-->
			<div class="cf"></div>
		</div>

		<!--按钮部分 end-->
		<!--表格部分-->
		<!-- <div class="mainaddword" style="margin-top:40px;"> -->
		<div class="creattable">
			<div class="tabletitle" style="margin-top: 6px;">销售订单</div>
			<div class="createdList">
				<!--12-->
				<ul class="wordul">
					<li class="wordli fl">
						<div class="wordname fl">单据编号：</div>
						<div class="wordnameinput fl">
							<input type="text" value="003432233" id="billCode"
								name="billCode">
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">单据日期：</div>
						<div class="wordnameinput fl">
							<input type="text" value="2015-08-06" id="billDate"
								name="billDate">
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">销售类型：</div>
						<div class="wordnameinput fl">
							<select id="saleType" name="saleType">
								<option>普通销售</option>
								<option>成品销售</option>
							</select>
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
					<li class="wordli fl">
						<div class="wordname fl">部门：</div>
						<div class="wordnameinput fl">
							<input type="text" value="研发部" id="departmentUid"
								name="departmentUid">
						</div>
						<div class="cf"></div>
					</li>
					<div class="cf"></div>
				</ul>
				<ul class="wordul">
					<li class="wordli fl">
						<div class="wordname fl">客户：</div>
						<div class="wordnameinput fl">
							<input type="text" value="" id="customerUid" name="customerUid">
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">业务员：</div>
						<div class="wordnameinput fl ">
							<input type="text" value="admin" class="toDealInput"
								id="personUid" name="personUid">
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">币种：</div>
						<div class="wordnameinput fl">
							<select class="toDealInput" id="currency" name="currency">
								<option>人民币</option>
								<option>美元</option>
							</select>
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">税率%：</div>
						<div class="wordnameinput fl">
							<input type="text" value="12.00" id="rate" name="rate"
								class="toDealInput">
						</div>
						<div class="cf"></div>
					</li>
					<div class="cf"></div>
				</ul>
				<ul class="wordul">
					<li class="wordli fl">
						<div class="wordname fl">汇率：</div>
						<div class="wordnameinput fl">
							<input type="text" value="1.00" id="exchangeRate"
								name="exchangeRate" class="toDealInput">
						</div>
						<div class="cf"></div>
					</li>
					<li class="wordli fl">
						<div class="wordname fl">备注：</div>
						<div class="wordnameinput fl">
							<input type="text" value="" id="notes" name="notes"
								class="toDealInput">
						</div>
						<div class="cf"></div>
					</li>
					<div class="cf"></div>
				</ul>
				<div style="height: 20px;"></div>
				<!--end-->
				<div style="max-height: 300px; overflow: auto;">
					<table>
						<tbody>
							<tr>
								<th style="width: 80px;" class="NO">操作</th>
								<th>物料编号</th>
								<th>物料名称</th>
								<th>规格型号</th>
								<th>数量</th>
								<th>单位</th>
								<th>计划发货日期</th>
								<th>原币含税单价</th>
								<th>原币含税金额</th>
								<th>原币不含税单价</th>
								<th>原币不含税金额</th>
								<th>原币税额</th>
								<th>本币含税单价</th>
								<th>本币含税金额</th>
								<th>本币不含税单价</th>
								<th>本币不含税金额</th>
								<th>本币税额</th>

							</tr>
							<c:forEach var="saleOrder" items="${saleOrderCList}" varStatus="stat">
										<tr class="contentTr">
											<td class="NO"><input class="center" type="text"
												name="" id="" value="${stat.count }" readonly="readonly" /></td>
											<td class=" flag" style="display: none;"><input
												class="center" type="text" name="" id="" value="0" /></td>
											<td class="gid" style="display: none;"><input
												class="center" type="text" name="" id=""
												value="${saleOrder.gid }" /></td>
											<td class="saleOrderUid" style="display: none;"><input
												style="display: none;" type="text" name="" id=""
												value="${saleOrder.saleOrderUid }" /></td>
											<td class="goodsUid" style="display: none;"><input
												class="center" style="display: none;" type="text" name=""
												id="" value="${saleOrder.goodsUid}" /></td>
											<td class="goodsCode"><input type="text"
												name="" id="" class="center "
												value="${goods[stat.index].goodsCode }"
												onchange="changeFlag(this,'1');" readonly="readonly" /></td>
											<td class="goodsName"><input class="center "
												type="text" name="" id=""
												value="${ goods[stat.index].goodsName}" readonly="readonly" /></td>
											<td class="goodsType"><input class="center "
												type="text" name="" id=" "
												value="${ goods[stat.index].goodsStandard}"
												readonly="readonly" /></td>
											<td class="number"><input
												class="toDealInput " type="text" name="" id=""
												value="<fmt:formatNumber value='${ saleOrder.number}' pattern='.######'></fmt:formatNumber>"
												onchange="changeFlag(this,'1');calculate(this,0)"
												readonly="readonly" /></td>
											<td class="goodsUnit"><input class="center "
												type="text" name="" id=""
												value="${ goods[stat.index].comUnitName}" readonly="readonly" /></td>
											<td class="planDG"><input class="center "
												type="text" name="" id=""
												value="${fn:substring(saleOrder.planDG,0,10)}"
												onchange="changeFlag(this,'1');" /></td>
											<td class="originalTaxPrice"><input
												class="toDealInput " type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,0)"
												value="<fmt:formatNumber value='${  saleOrder.originalTaxPrice}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="originalTaxMoney"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,1)"
												value="<fmt:formatNumber value='${  saleOrder.originalTaxMoney}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="originalNotaxPrice"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,2)"
												value="<fmt:formatNumber value='${  saleOrder.originalNotaxPrice}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="originalNotaxMoney"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,3)"
												value="<fmt:formatNumber value='${  saleOrder.originalNotaxMoney}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="originalTax"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,4)"
												value="<fmt:formatNumber value='${  saleOrder.originalTax}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="localTaxPrice"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,5)"
												value="<fmt:formatNumber value='${  saleOrder.localTaxPrice}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="localTaxMoney"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,6)"
												value="<fmt:formatNumber value='${  saleOrder.localTaxMoney}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="localNotaxPrice"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,7)"
												value="<fmt:formatNumber value='${ saleOrder.localNotaxPrice}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="localNotaxMoney"><input
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,8)"
												value="<fmt:formatNumber value='${ saleOrder.localNotaxMoney}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>
											<td class="localTax"><input 
												type="text" name="" id=""
												onchange="changeFlag(this,'1');calculate(this,9)"
												value="<fmt:formatNumber value='${saleOrder.localTax}' pattern='.######'></fmt:formatNumber>"
												readonly="readonly" /></td>


										</tr>

							</c:forEach>

						</tbody>
					</table>
					<div class="addrow fl"
						style="margin-left: 50px; margin-top: -23px;"></div>
				</div>
			</div>
		</div>
		<!--表格部分 end-->
		<ul class="wordul fr" style="width: 80%; margin-right: 15px;">
			<li class="wordli fl">
				<div class="wordname fl">录入人：</div>
				<div class="wordnameinput fl">
					<input type="text" value="admin" id="recordPersonUid"
						name="recordPersonUid">
				</div>
				<div class="cf"></div>
			</li>
			<li class="wordli fl">
				<div class="wordname fl">录入日期：</div>
				<div class="wordnameinput fl">
					<input type="text" value="2015-08-06" id="recordDate"
						name="recordDate">
				</div>
				<div class="cf"></div>
			</li>
			<li class="wordli fl">
				<div class="wordname fl">审核人：</div>
				<div class="wordnameinput fl">
					<input type="text" value="" id="auditPersonUid"
						name="auditPersonUid">
				</div>
				<div class="cf"></div>
			</li>
			<li class="wordli fl">
				<div class="wordname fl">审核日期：</div>
				<div class="wordnameinput fl">
					<input type="text" value="" id="auditDate" name="auditDate">
				</div>
				<div class="cf"></div>
			</li>
			<div class="cf"></div>
		</ul>
		<div class="cf"></div>
		<!-- </div> -->
	</div>
</body>
<script type="text/javascript">
	var i = 0;
	document.getElementById('button1').onclick = function() {
		var a = document.getElementsByClassName('fan'), l = a.length;
		a[i++].style.display = 'none';
		if (i >= l)
			i = 0;
		a[i].style.display = 'block';
	}
</script>
</html>