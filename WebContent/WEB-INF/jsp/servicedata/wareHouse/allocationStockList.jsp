<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>生产订单</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css">
	<%--<link rel="stylesheet" type="text/css" href="../demo.css">--%>
	<script type="text/javascript" src="${ctx}/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/jquery.easyui.min.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
	<style type="text/css">
		.outpBtn {
			padding: 0 6px 0px 6px;
			height: 30px;
			background-color: #f5faff;
			border-radius: 5px;
			border: 1px solid #E1E1E1;
			cursor: pointer;
			/*background-image: url(../img/search.png);*/
			background-size: 23px 23px;
			background-repeat: no-repeat;
			background-position: 8px 2px;
			text-align: right;
			line-height: 30px;
		}

	</style>
<script>

    $(function(){

        //初始化多选复选框
        initCombobox('xsry','XSRY_CD');

        var areahouselist = '${warehouses}'
        if (areahouselist != null && areahouselist!= ""){
            $("#xsry").combobox('setValues',areahouselist.split(','));
        }
        //学术荣誉的字典编码是XSRY_CD



    	}


    )
    //参数：id  控件id   code 字典编码
    function initCombobox(id,code){
        var value = "";
        //加载下拉框复选框
        $('#'+id).combobox({
            url:'${ctx}/wms/wareHouse_getWareHouseList.emi', //后台获取下拉框数据的url
            method:'post',
            panelHeight:200,//设置为固定高度，combobox出现竖直滚动条
            valueField:'whcode',
            textField:'whname',
            multiple:true,
            formatter: function (row) { //formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
            },
            onLoadSuccess: function () {  //下拉框数据加载成功调用
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');//获取选中的值的values
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onSelect: function (row) { //选中一个选项时调用
                var opts = $(this).combobox('options');
                //获取选中的值的values
                $("#"+id).val($(this).combobox('getValues'));

                //设置选中值所对应的复选框为选中状态
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {//不选中一个选项时调用
                var opts = $(this).combobox('options');
                //获取选中的值的values
                $("#"+id).val($(this).combobox('getValues'));

                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }


    function toSubmitForm(){


        $("#myform").submit();
    }


    function toOutPut(){
        var dtitlelist="{\"goodscode\":\"物料编码\",\"goodsName\":\"物品名称\",\"goodsStandard\":\"物品规格\",\"whName\":\"仓库名称\",\"goodsallocationcode\":\"货位编码\",\"name\":\"货位名称\",\"batch\":\"批次\",\"unitName\":\"主单位\",\"number\":\"主数量\"}";


        $("#warehouses").val($("#xsry").combobox('getValues'));

		$('#dtype').val("down");
		$('#doutname').val("库存列表");
		$('#dtitlelist').val(dtitlelist);
		$('#downform').submit();

    }

</script>
</head>
<body>



<form action="${ctx}/wms/wareHouse_allocationStockList.emi" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
 		 	<div class="toolbar">
		 		<ul>
					仓库选择：<input id="xsry" name="xsry"  style="width: 150px;"  class="easyui-combobox" name="warehouses" value="">
					<input type="text" name="goodscode" placeholder="请输入物料编码" class="write_input" style="" value="${goodscode}">
					<input type="text" name="goodsname" placeholder="请输入物料名称" class="write_input" style="" value="${goodsname}">
					<input class="searchBtn" type="button" value="查询" onclick="toSubmitForm();">
					<input class="outpBtn" type="button" value="导出Excel" onclick="toOutPut();">

				</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">库存列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>物品编码</th>
			 					<th>物品名称</th>
								<th>物品规格</th>
			 					<th>仓库名称</th>
			 					<th>货位编码</th>
			 					<th>货位名称</th>
			 					<th>批次</th>
			 					<th>主单位</th>
			 					<th>主数量</th>
			 					<%--<th>辅单位</th>--%>
			 					<%--<th>辅数量</th>--%>

			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${(data.pageIndex-1)*data.pageSize+stat.count}</td>
								<td>${bean.goodscode}</td>
								<td>${bean.goodname}</td>
								<td>${bean.goodsStandard}</td>
								<td>${bean.whname}</td>
								<td>${bean.goodsallocationcode}</td>
								<td>${bean.allocationname}</td>
								<td>${bean.batch}</td>
								<td>${bean.classificationName}</td>
								<td><fmt:formatNumber type="number" value="${bean.number}" minFractionDigits="2"/></td>
								<%--<td>${bean.cstComUnitName}</td>--%>
								<%--<td><fmt:formatNumber type="number" value="${bean.assistnum}" minFractionDigits="2"/></td>--%>
							</tr>
						</c:forEach>
			 			</tbody>
			 		</table>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<!--分页部分-->
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!--分页部分 end-->
		</div>
		</form>
	</body>

<form action="${ctx}/wms/wareHouse_downorprint.emi" id="downform">
	<input type="hidden" name="goodscode"  value="${goodscode }">
	<input type="hidden" name="goodsname"  value="${goodsname }">
	<input type="hidden" name="warehouses" id="warehouses" value="">

	<input type="hidden" name="type" id="dtype" value="">
	<input type="hidden" name="outname" id="doutname" value="">
	<%--gravewallreserve--%>
	<input type="hidden" name="dataname" id="ddataname" value="allocationstock">
	<input type="hidden" name="titlelist" id="dtitlelist" value="">
</form>



</html>



