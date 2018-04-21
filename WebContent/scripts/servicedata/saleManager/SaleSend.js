//产品序号
		function getOrder(){
			
			var trs=$('.contentTr');
			$('#cSize').val(trs.length);
			for(var i=0;i<trs.length;i++){
				
				trs.eq(i).children().first().children().val(i+1);
				trs.eq(i).children('.flag').children().attr('id','flag'+(i+1));
				trs.eq(i).children('.cgId').children().attr('id','cgId'+(i+1));
				trs.eq(i).children('.gSaleOrderUid').children().attr('id','gSaleOrderUid'+(i+1));
				trs.eq(i).children('.gGoodsUid').children().attr('id','gGoodsUid'+(i+1));
				trs.eq(i).children('.gGoodsCode').children().attr('id','gGoodsCode'+(i+1));
				
				trs.eq(i).children('.gGoodsName').children().attr('id','gGoodsName'+(i+1));
				trs.eq(i).children('.gGoodsType').children().attr('id','gGoodsType'+(i+1));
				trs.eq(i).children('.nNumber').children().attr('id','nNumber'+(i+1));
				trs.eq(i).children('.shoudNum').children().attr('id','shoudNum'+(i+1)); 
				trs.eq(i).children('.gGoodsUnit').children().attr('id','gGoodsUnit'+(i+1));
				trs.eq(i).children('.nnRate').children().attr('id','nnRate'+(i+1));
				
				trs.eq(i).children('.nOriginalTaxPrice').children().attr('id','nOriginalTaxPrice'+(i+1));
				trs.eq(i).children('.nOriginalTaxMoney').children().attr('id','nOriginalTaxMoney'+(i+1));
				trs.eq(i).children('.nOriginalNotaxPrice').children().attr('id','nOriginalNotaxPrice'+(i+1));
				trs.eq(i).children('.nOriginalNotaxMoney').children().attr('id','nOriginalNotaxMoney'+(i+1));
				trs.eq(i).children('.nOriginalTax').children().attr('id','nOriginalTax'+(i+1));
				trs.eq(i).children('.nLocalTaxPrice').children().attr('id','nLocalTaxPrice'+(i+1));
				trs.eq(i).children('.nLocalTaxMoney').children().attr('id','nLocalTaxMoney'+(i+1));
				trs.eq(i).children('.nLocalNotaxPrice').children().attr('id','nLocalNotaxPrice'+(i+1));
				trs.eq(i).children('.nLocalNotaxMoney').children().attr('id','nLocalNotaxMoney'+(i+1));
				trs.eq(i).children('.nLocalTax').children().attr('id','nLocalTax'+(i+1));
				trs.eq(i).children('.nOriginalDeduction').children().attr('id','nOriginalDeduction'+(i+1));				
				trs.eq(i).children('.nLocalDeduction').children().attr('id','nLocalDeduction'+(i+1));
				trs.eq(i).children('.nDiscount').children().attr('id','nDiscount'+(i+1));
				trs.eq(i).children('.nPutoutNum').children().attr('id','nPutoutNum'+(i+1));
				trs.eq(i).children('.gSaleorderCuid').children().attr('id','gSaleorderCuid'+(i+1));
				trs.eq(i).children('.gTaskCuid').children().attr('id','gTaskCuid'+(i+1));
				trs.eq(i).children('.gSaleorderuid').children().attr('id','gSaleorderuid'+(i+1));
				
				trs.eq(i).children('.flag').children().attr('name','flag'+(i+1));
				trs.eq(i).children('.cgId').children().attr('name','cgId'+(i+1));
				trs.eq(i).children('.gSaleOrderUid').children().attr('name','gSaleOrderUid'+(i+1));
				trs.eq(i).children('.gGoodsCode').children().attr('name','gGoodsCode'+(i+1));
				trs.eq(i).children('.gGoodsUid').children().attr('name','gGoodsUid'+(i+1));
				trs.eq(i).children('.gGoodsName').children().attr('name','gGoodsName'+(i+1));
				trs.eq(i).children('.gGoodsType').children().attr('name','gGoodsType'+(i+1));
				trs.eq(i).children('.nNumber').children().attr('name','nNumber'+(i+1));
				trs.eq(i).children('.shoudNum').children().attr('name','shoudNum'+(i+1)); 
				trs.eq(i).children('.gGoodsUnit').children().attr('name','gGoodsUnit'+(i+1));
				trs.eq(i).children('.nnRate').children().attr('name','nnRate'+(i+1));
				
				trs.eq(i).children('.nOriginalTaxPrice').children().attr('name','nOriginalTaxPrice'+(i+1));
				trs.eq(i).children('.nOriginalTaxMoney').children().attr('name','nOriginalTaxMoney'+(i+1));
				trs.eq(i).children('.nOriginalNotaxPrice').children().attr('name','nOriginalNotaxPrice'+(i+1));
				trs.eq(i).children('.nOriginalNotaxMoney').children().attr('name','nOriginalNotaxMoney'+(i+1));
				trs.eq(i).children('.nOriginalTax').children().attr('name','nOriginalTax'+(i+1));
				trs.eq(i).children('.nLocalTaxPrice').children().attr('name','nLocalTaxPrice'+(i+1));
				trs.eq(i).children('.nLocalTaxMoney').children().attr('name','nLocalTaxMoney'+(i+1));
				trs.eq(i).children('.nLocalNotaxPrice').children().attr('name','nLocalNotaxPrice'+(i+1));
				trs.eq(i).children('.nLocalNotaxMoney').children().attr('name','nLocalNotaxMoney'+(i+1));
				trs.eq(i).children('.nLocalTax').children().attr('name','nLocalTax'+(i+1));
				trs.eq(i).children('.nOriginalDeduction').children().attr('name','nOriginalDeduction'+(i+1));				
				trs.eq(i).children('.nLocalDeduction').children().attr('name','nLocalDeduction'+(i+1));
				trs.eq(i).children('.nDiscount').children().attr('name','nDiscount'+(i+1));
				trs.eq(i).children('.nPutoutNum').children().attr('name','nPutoutNum'+(i+1));
				trs.eq(i).children('.gSaleorderCuid').children().attr('name','gSaleorderCuid'+(i+1));
				trs.eq(i).children('.gTaskCuid').children().attr('name','gTaskCuid'+(i+1));
				trs.eq(i).children('.gSaleorderuid').children().attr('name','gSaleorderuid'+(i+1));
			}
		}

//各种事件
$(function(){
	
	getOrder();
	
	$('body').on('click','.fenLuTd',function(){
		$(this).children('.fenLuTool').show();
		
		$('.fenLuTd').mouseleave(function(){
			$(this).children('.fenLuTool').hide();
		});
		
	});
	
	$('body').on('click','.add',function(){
		
		var trHtml='<tr class="saleSendTr contentTr">'+
			'<td class="billBodyTd"><input class="center" type="text" name="" id="" value="" style="text-align: center;"/></td>'+
			'<td class="billBodyTd fenLuTd">'+
				'分录'+
				'<div class="fenLuTool" style="display:none;">'+
					'<div class="add">增加</div>'+
					'<div class="delete">删除</div>'+
				'</div>'+
			'</td>'+
			'<td class="billBodyTd flag" style="display: none;"><input class="center" type="text" name="" id=""  value="2"/></td>'+
			'<td class="billBodyTd cgId" style="display: none;"><input class="center" type="text" name="" id=""  /></td>'+
			'<td class="billBodyTd gSaleOrderUid" style="display: none;"><input style="display: none;" type="text" name="" id=""  /></td>'+
			'<td class="billBodyTd gGoodsUid" style="display: none;"><input class="center" style="display: none;" type="text" name="" id="" /></td>'+
			
			'<td class="billBodyTd gGoodsCode"><input  type="text" name="" id="" class="wlHelp center" /></td>'+
		
			'<td class="billBodyTd gGoodsName"><input class="center" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd gGoodsType"><input class="center" type="text" name="" id="" /></td>'+
			 '<td class="billBodyTd shoudNum" style="display: none;"><input class="right" type="text" name="" id="" /></td>'+ 
			'<td class="billBodyTd nNumber"><input class="right" type="text" name="" id="" onchange="calculate(this)"/></td>'+
			'<td class="billBodyTd gGoodsUnit"><input class="center" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd nnRate"><input class="center" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd nOriginalTaxPrice"><input class="right" type="text" name="" id="" onchange="calculate(this,0)"/></td>'+
			'<td class="billBodyTd nOriginalTaxMoney"><input class="right" type="text" name="" id="" onchange="calculate(this,1)"/></td>'+
			'<td class="billBodyTd nOriginalNotaxPrice"><input class="right" type="text" name="" id="" onchange="calculate(this,2)"/></td>'+
			'<td class="billBodyTd nOriginalNotaxMoney"><input class="right" type="text" name="" id="" onchange="calculate(this,3)"/></td>'+
			'<td class="billBodyTd nOriginalTax"><input class="right" type="text" name="" id="" onchange="calculate(this,4)"/></td>'+
			'<td class="billBodyTd nLocalTaxPrice"><input class="right" type="text" name="" id="" onchange="calculate(this,5)"/></td>'+
			'<td class="billBodyTd nLocalTaxMoney"><input class="right" type="text" name="" id="" onchange="calculate(this,6)"/></td>'+
			'<td class="billBodyTd nLocalNotaxPrice"><input class="right" type="text" name="" id="" onchange="calculate(this,7)"/></td>'+
			'<td class="billBodyTd nLocalNotaxMoney"><input class="right" type="text" name="" id="" onchange="calculate(this,8)"/></td>'+
			'<td class="billBodyTd nLocalTax"><input class="right" type="text" name="" id="" onchange="calculate(this,9)"/></td>'+
			'<td class="billBodyTd nOriginalDeduction" style="display: none"><input class="right" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd nLocalDeduction" style="display: none"><input class="right" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd nDiscount" style="display: none"><input class="right" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd nPutoutNum" style="display: none"><input class="right" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd gSaleorderCuid" style="display: none"><input class="center" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd gTaskCuid" style="display: none"><input class="center" type="text" name="" id="" /></td>'+
			'<td class="billBodyTd gSaleorderuid" style="display: none"><input class="center" type="text" name="" id="" /></td>'+
		'</tr>';	
			
		$(this).parent('.fenLuTool').parent('.fenLuTd').parent().after(trHtml);
		getOrder();
	});
	
	
	$('body').on('click','.delete',function(){
		var deleteGids = "";
		var flag = true;
		flag=confirm("确定是否删除？");

		var trs=$('.contentTr');
		
		if(flag && trs.length>1){
			
			var delId = $(this).attr('gId');
		
			if(delId !=null && delId!=''){
				deleteGids+=$(this).attr('gId')+",";
			}
			$('#deleteGids').val(deleteGids);
			$(this).parent('.fenLuTool').parent('.fenLuTd').parent().remove();
			getOrder();
		}else{
			return;
		}
		
	});
	
	
$('body').on('focus','.wlHelp',function(){
		var order=$(this).parent().parent().children().first().children().val();
		$('#saveOrder').val(order);
		
	});
	
$('body').on('click','.wlHelp',function(e){
	var index=$(this).parent().parent().children().first().children().val();
			$.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'物料查询',
		    width: '700px',
			height: '500px',
			content: 'url:goods!toGoodsHelp.action',
			});
		
			var theFlag =$('#flag'+index).val();
			if(theFlag=='0'){
				document.getElementById("flag"+index).value='1';
			}
	});
	
$('body').on('click','.gCustomerName',function(e){
	
	$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'客户查询',
	    width: '700px',
		height: '500px',
		content: 'url:customer!toCustomerHelp.action',
	});				


});

$('body').on('click','.gWhName',function(e){

	$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'仓库查询',
	    width: '700px',
		height: '500px',
		content: 'url:store!doSelect.action',
		});
				


});

$('body').on('click','#follBtn',function(e){

	$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'销售订单查询',
		width: '1000px',
		height: '500px',
		content: 'url:order!referSoList.action?t='+new Date()+'&typegid='+$("#gcode").val()+'&type='+$("#type").val(),
	});				

});
	
});



//检查表单数据的完整性
		
function checkForm(){	
	var flag = true;
	
	var len = document.getElementById("cSize").value;
	debugger;
	for(var i=1;i<=len;i++){
		if(document.getElementById("gGoodsUid"+i).value=='' || document.getElementById("nNumber"+i).value=='' || document.getElementById("nOriginalTaxPrice"+i).value==''){
			
			flag = false;
			
		}
	}
	
	 if(document.getElementById("gCustomerUid").value==""){
		alert("请正确选择客户");
		return false;

	}
	else if(document.getElementById("gWhUid").value==""){
		alert("请正确选择仓库");
		return  false;
	}
	else if(document.getElementById("nRate").value==""){
		alert("请输入税率");
		flag = false;
	}
	else if(!flag)
	{
	 alert("物料详情填写不完整!");
    	 return false;
     
	}else{
		return true	;
	}
	
}
function changenumber(obj)
{
	var index = $('#'+obj.id).parent().parent().children().first().children().val();

}
/*
 * 计算价格:  value0:原币含税单价，1:原币含税金额，2:原币不含税单价，3:原币不含税金额，4:原币税额
 * 5:本币含税单价，6:本币含税金额，7:本币不含税单价，8:本币不含税金额，9:本币税额
 */
function calculate(obj,valueIndex){
debugger;
	var index = $('#'+obj.id).parent().parent().children().first().children().val();
	var rate = document.getElementById("nRate").value;  //税率
	var exchangeRate = document.getElementById("nExchangeRate").value;  //汇率
	var quantity = document.getElementById("nNumber"+index).value;		//数量	
	var endNumber = document.getElementById("nNumber"+index).value-0;		//数量
	var startNumber=document.getElementById("shoudNum"+index).value-0;
	if(endNumber>0){
		if(endNumber>startNumber)
			{
				alert("填写数量大于销售单剩余数量!");
				$("#nNumber"+index).val(startNumber);
				return false;
			}
		else
			{
			var value = "0";
			//value = document.getElementById(valueId+index).value;
			if(valueIndex==0){
				value = document.getElementById("nOriginalTaxPrice"+index).value;
			}else if(valueIndex==1){
				value = document.getElementById("nOriginalTaxMoney"+index).value;
			}else if(valueIndex==2){
				value = document.getElementById("nOriginalNotaxPrice"+index).value;
			}else if(valueIndex==3){
				value = document.getElementById("nOriginalNotaxMoney"+index).value;
			}else if(valueIndex==4){
				value = document.getElementById("nOriginalTax"+index).value;
			}else if(valueIndex==5){
				value = document.getElementById("nLocalTaxPrice"+index).value;
			}else if(valueIndex==6){
				value = document.getElementById("nLocalTaxMoney"+index).value;
			}else if(valueIndex==7){
				value = document.getElementById("nLocalNotaxPrice"+index).value;
			}else if(valueIndex==8){
				value = document.getElementById("nLocalNotaxMoney"+index).value;
			}else if(valueIndex==9){
				value = document.getElementById("nLocalTax"+index).value;
			}
			
			//如果数量、税率、汇率、任意一个金额填了，则计算
			//if(quantity!='' &&rate!='' &&exchangeRate!='' && (value0!='' || value1!='' || value2!='' || value3!='' || value4!='' || value5!='' || value6!='' || value7!='' || value8!='' || value9!='')){
			if(quantity!='' &&rate!='' &&exchangeRate!='' && value!='' ){
				$.ajax({
					url:'order!calculate.action',
					type:'post',
					timeout:'5000',
					//data:{quantity:quantity,rate:rate,exchangeRate:exchangeRate,value0:value0,value1:value1,
					//	value2:value2,value3:value3,value4:value4,value5:value5,value6:value6,value7:value7,value8:value8,value9:value9},
					data:{quantity:quantity,rate:rate,exchangeRate:exchangeRate,value:value,valueIndex:valueIndex},
					dataType:'text',
					success:function(data){
						data = eval("("+data+")");
						if(data.success=="1"){
							document.getElementById("nOriginalTaxPrice"+index).value=data.value0;
							document.getElementById("nOriginalTaxMoney"+index).value=data.value1;
							document.getElementById("nOriginalNotaxPrice"+index).value=data.value2;
							document.getElementById("nOriginalNotaxMoney"+index).value=data.value3;
							document.getElementById("nOriginalTax"+index).value=data.value4;
							document.getElementById("nLocalTaxPrice"+index).value=data.value5;
							document.getElementById("nLocalTaxMoney"+index).value=data.value6;
							document.getElementById("nLocalNotaxPrice"+index).value=data.value7;
							document.getElementById("nLocalNotaxMoney"+index).value=data.value8;
							document.getElementById("nLocalTax"+index).value=data.value9;
							
						}else{
							alert(data.msg);
						}
					},
					error:function(){
						//alert("服务器异常");
					}
				});
			}
			}
	}
	else
		{
		alert("发货数量不能为0!");
		$("#nNumber"+index).val(startNumber);
		  return false;
		}
	
}
//改变指定行的flag值
function changeFlag(obj, val) {
	var index = $("#" + obj.id).parent().parent().children().first()
			.children().val();
	document.getElementById("flag" + index).value = val;


}
//按钮事件
$(function(){
	var isSave=true;//新增保存、修改保存标识
	//新增 
	$('#addBtn').click(function(){	
		
		$('.billHead').children('input').val("");//头内容全清
		$('tr').children().not($('.NO')).children().val("");//表格内容全清
		$("table tbody tr").eq(1).nextAll().remove();
		$("#type").val("add");
		$('.polastHead').children('input').val("");//尾内容全清	
        getOrder();	
		var toDeal=$('.toDealInput');
		inputCanUse(toDeal);		
		//部门事件，操作分录
	
		$('#gCustomerName').addClass("gCustomerName");
		$('#gWhName').addClass("gWhName");
		$('.fenLuTh').removeAttr("style");
		$('.fenLuTd').removeAttr("style");		
		//日期 事件
		
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanDG').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.gGoodsCode').children().addClass("wlHelp");	
		$.ajax({
			url:'${ctx}/order!toAddSaleSend.action',
			type:"post",
			dataType:"json",
			success:function(da){					
				$('#dBillDate').val(da.nowDate);
				$('#dRecordDate').val(da.nowDate);
				$('#cBillCode').val(da.billId);
				$('#recordPersonName').val(da.recordname);
				$('#gRecordPersonUid').val(da.gRecordPersonUid);
			}				
		});
		addBtn();

	});
	//修改
	$('#revBtn').click(function(){	
		if($('#gId').val()==""){
			return false;
		}
		else{
			isSave=false;	
			getOrder();	
			var toDeal=$('.toDealInput');
			inputCanUse(toDeal);			
			//部门,客户，业务员事件，操作分录
			$('#departmentName').addClass("departmentName");
			$('#gCustomerName').addClass("gCustomerName");
			$('#personName').addClass("personName");
			$('.fenLuTh').removeAttr("style");
			$('.fenLuTd').removeAttr("style");		
			//日期 事件
			$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");		
			$('.dPlanDG').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
			//物品事件
			$('.gGoodsCode').children().addClass("wlHelp");	
			revBtn();
		}
	});	
	//保存订单
	$('#saveBtn').click(function(){		
		
		if(checkForm()){
		if(isSave){//新增保存		
			
			$.ajax({
				url:'${ctx}/order!addSaleSend.action',
				type:"post",
				data:$('#myForm').serialize(),
				dataType:"json",
				success:function(da){				
									
					if(da.success){
						window.location.href = "${ctx}/order!toEditSaleOrder.action?gid="+da.gid;
						
					}					
				}				
			});
		}else{//修改保存			
			$.ajax({
				url:'${ctx}/order!updSaleOrder.action',
				type:"post",
				data:$('#myForm').serialize(),
				dataType:"json",
				success:function(da){					
					if(da.success){
						window.location.href = "${ctx}/order!toEditSaleOrder.action?gid="+da.gid;
						
					}					
				}				
			});			
		}
		}
		
	});
	//查询的显示和隐藏
	$('.selBtn').click(function() {
		$('.selecetDialog').show();

	});
	$('.shut').click(function() {
		$('.selecetDialog').hide();

	});
});
//删除订单
function deleteSaleOrder(gid) {
	if (confirm("是否确定删除?")) {
		$.ajax({
					url : 'order!delSaleOrder.action',
					type : 'post',
					data : {gId : gid},
					dataType : 'json',
					success : function(data) {						
						if (data.success == "1") {
							window.location.href = "${ctx}/order!toEditSaleOrder.action";
						} else {
							alert(data.msg);
						}
					},
					error : function() {

						alert("服务器异常");
					}
				});
	}
}
//跳转审核页面
function toAuditSaleOrder(gid) {

	
if (confirm("确定审核?")) {
		var url = "${ctx}/order!doAudit.action?state=1&tableType=1";
		$.ajax({
					url : url,
					type : 'post',
					data:$('#myForm').serialize(),
					dataType : 'json',
					success : function(data) {						
						if (data.success == "1") {
							location.href = "${ctx}/order!toEditSaleOrder.action?gid="+ data.gid ;
						} else {
							alert(data.msg);
						}
					},

				});
	
}
}
//关闭或者打开订单
function checkSatus(state, gid) {
	var url = "${ctx}/order!checkStatus.action?gId=" + gid + "&status="	+ state;
	$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success == "1") {
						location.href = "${ctx}/order!toEditSaleOrder.action?gid='"
								+ data.gid + "'";
					} else {
						alert(data.msg);
					}
				},

			});
}
//放弃审核
function cancelAudit(gid) {	
	if (confirm("是否确定弃审?")) {
		var url = "${ctx}/order!cancelAudit.action?gId=" + gid+ "&tableType=1";
		$.ajax({
					url : url,
					type : 'post',
					dataType : 'json',
					success : function(data) {						
						if (data.success == "1") {
							location.href = "${ctx}/order!toEditSaleOrder.action?gid='"
									+ data.gid + "'";
						} else {
							alert(data.msg);
						}
					},

				});
	}
}

