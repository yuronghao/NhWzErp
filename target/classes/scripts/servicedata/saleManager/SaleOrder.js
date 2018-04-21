//产品序号
	function getOrder(){
			
			var trs=$('.contentTr');
			$('#cSize').val(trs.length);
			for(var i=0;i<trs.length;i++){				
				trs.eq(i).children().first().children().val(i+1);
				trs.eq(i).children('.gid').children().attr('id','gid'+(i+1));
				trs.eq(i).children('.flag').children().attr('id','flag'+(i+1));
				trs.eq(i).children('.g').children().attr('id','g'+(i+1));
				trs.eq(i).children('.goodsCode').children().attr('id','goodsCode'+(i+1));
				trs.eq(i).children('.goodsName').children().attr('id','goodsName'+(i+1));
				trs.eq(i).children('.goodsType').children().attr('id','goodsType'+(i+1));
				trs.eq(i).children('.number').children().attr('id','number'+(i+1));
				trs.eq(i).children('.goodsUnit').children().attr('id','goodsUnit'+(i+1));				
				trs.eq(i).children('.originalTaxPrice').children().attr('id','originalTaxPrice'+(i+1));
				trs.eq(i).children('.planDG').children().attr('id','planDG'+(i+1));
				trs.eq(i).children('.originalTaxMoney').children().attr('id','originalTaxMoney'+(i+1));
				trs.eq(i).children('.originalNotaxPrice').children().attr('id','originalNotaxPrice'+(i+1));
				trs.eq(i).children('.originalNotaxMoney').children().attr('id','originalNotaxMoney'+(i+1));
				trs.eq(i).children('.originalTax').children().attr('id','originalTax'+(i+1));
				trs.eq(i).children('.localTaxPrice').children().attr('id','localTaxPrice'+(i+1));
				trs.eq(i).children('.localTaxMoney').children().attr('id','localTaxMoney'+(i+1));
				trs.eq(i).children('.localNotaxPrice').children().attr('id','localNotaxPrice'+(i+1));
				trs.eq(i).children('.localNotaxMoney').children().attr('id','localNotaxMoney'+(i+1));
				trs.eq(i).children('.localTax').children().attr('id','localTax'+(i+1));				
			}
		}

//各种事件
$(function(){
	var toDeal=$('.toDealInput');
	inputCannotUse(toDeal);	
	var toDealSelect=$('.toDealSelect');
	selectCannotUse(toDealSelect);	
	getOrder();
	
	$('body').on('click','.addrow',function(){
			$.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'物料基本信息查询',
				width: '800px',
				height: '530px',
				max:false,
				min:false,
				zIndex:2500,
				content: 'url:'+getContextPath()+'/wms/sale_getMaterielHelp.emi'
			});	
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

$('body').on('click','.departmentName',function(e){

	$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'部门查询',
	    width: '700px',
		height: '500px',
		content: 'url:org!toDeptHelp.action',
	});				


});

$('body').on('click','.personName',function(e){

	$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'人员查询',
	    width: '700px',
		height: '500px',
		content: 'url:org!toPersonHelp.action',
	});				

});
	
});



//检查表单数据的完整性
		
function checkForm(){	
/*	var flag = true;
	
	var len = document.getElementById("cSize").value;
	debugger;
	for(var i=1;i<=len;i++){
		if(document.getElementById("planDG"+i).value=='' || document.getElementById("g"+i).value=='' || document.getElementById("nNumber"+i).value=='' || document.getElementById("originalTaxPrice"+i).value==''){
			
			flag = false;
			
		}
	}
	
	 if(document.getElementById("gCustomerUid").value==""){
		alert("请正确选择客户");
		return false;

	}else if(document.getElementById("departmentUid").value==""){
		alert("请正确选择部门");
		return false;
	}
	else if(document.getElementById("personUid").value==""){
		alert("请正确选择人员");
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
	}*/
	
}

/*
 * 计算价格:  value0:原币含税单价，1:原币含税金额，2:原币不含税单价，3:原币不含税金额，4:原币税额
 * 5:本币含税单价，6:本币含税金额，7:本币不含税单价，8:本币不含税金额，9:本币税额
 */
function calculate(obj,valueIndex){
	
	var index = $('#'+obj.id).parent().parent().children().first().children().val();
	var rate = document.getElementById("rate").value;  //税率
	var exchangeRate = document.getElementById("exchangeRate").value;  //汇率
	var quantity = document.getElementById("number"+index).value;		//数量
	var value = "0";
	//value = document.getElementById(valueId+index).value;
	if(valueIndex==0){
		value = document.getElementById("originalTaxPrice"+index).value;
	}else if(valueIndex==1){
		value = document.getElementById("originalTaxMoney"+index).value;
	}else if(valueIndex==2){
		value = document.getElementById("originalNotaxPrice"+index).value;
	}else if(valueIndex==3){
		value = document.getElementById("originalNotaxMoney"+index).value;
	}else if(valueIndex==4){
		value = document.getElementById("originalTax"+index).value;
	}else if(valueIndex==5){
		value = document.getElementById("localTaxPrice"+index).value;
	}else if(valueIndex==6){
		value = document.getElementById("localTaxMoney"+index).value;
	}else if(valueIndex==7){
		value = document.getElementById("localNotaxPrice"+index).value;
	}else if(valueIndex==8){
		value = document.getElementById("localNotaxMoney"+index).value;
	}else if(valueIndex==9){
		value = document.getElementById("localTax"+index).value;
	}
	
	//如果数量、税率、汇率、任意一个金额填了，则计算
	//if(quantity!='' &&rate!='' &&exchangeRate!='' && (value0!='' || value1!='' || value2!='' || value3!='' || value4!='' || value5!='' || value6!='' || value7!='' || value8!='' || value9!='')){
	if(quantity!='' &&rate!='' &&exchangeRate!='' && value!='' ){
		$.ajax({
			url:''+getContextPath()+'/wms/sale_calculate.action',
			type:'post',
			timeout:'5000',
			data:{quantity:quantity,rate:rate,exchangeRate:exchangeRate,value:value,valueIndex:valueIndex},
			dataType:'text',
			success:function(data){
				data = eval("("+data+")");
				if(data.success=="1"){
					document.getElementById("originalTaxPrice"+index).value=data.value0;
					document.getElementById("originalTaxMoney"+index).value=data.value1;
					document.getElementById("originalNotaxPrice"+index).value=data.value2;
					document.getElementById("originalNotaxMoney"+index).value=data.value3;
					document.getElementById("originalTax"+index).value=data.value4;
					document.getElementById("localTaxPrice"+index).value=data.value5;
					document.getElementById("localTaxMoney"+index).value=data.value6;
					document.getElementById("localNotaxPrice"+index).value=data.value7;
					document.getElementById("localNotaxMoney"+index).value=data.value8;
					document.getElementById("localTax"+index).value=data.value9;
					
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
//按钮事件
$(function(){
	debugger;
	var isSave=true;//新增保存、修改保存标识
	//新增 
	$('.addBtn').click(function(){					
		$('.wordul').children('input').val("");//清理文本框内容
		$('tr').children().not($('.NO')).children().val("");//表格内容全清
        getOrder();	
		var toDeal=$('.toDealInput');
		inputCanUse(toDeal);		
		var toDeals=$('.toDealSelect');
		selectCanUse(toDeals);	
		//部门事件，操作分录
		$('#departmentName').addClass("departmentName");
		$('#customerName').addClass("customerName");
		$('#personName').addClass("personName");
		//日期 事件
		
		$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.planDG').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$.ajax({
			data:{billType:13},
			url:'${ctx}/wms/sale_getBillId.emi',
			type:"post",
			dataType:"json",
			success:function(da){					
				$('#billDate').val(da.nowDate);
				$('#recordDate').val(da.nowDate);
				$('#billCode').val(da.billId);
				$('#recordPersonName').val(da.recordname);
				$('#recordPersonUid').val(da.gRecordPersonUid);
			}				
		});
		addBtn();

	});
	//修改
	$('#revBtn').click(function(){	
		if($('#gid').val()==""){
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
			$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");		
			$('.planDG').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
			//物品事件
			$('.goodsCode').children().addClass("wlHelp");	
			revBtn();
		}
	});	
	//保存订单
	$('#saveBtn').click(function(){		
		if(checkForm()){
		if(isSave){//新增保存			
			$.ajax({
				url:'${ctx}/order!addSaleOrder.action',
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
//放弃
function giveup(gid) {
	location.href = '${ctx}/wms/sale_getSaleAdd.emi?gid='
			+ gid + '';
}
//第一条记录
function First(id) {
	location.href = '${ctx}/order!toEditSaleOrder.action?number=1&id='
			+ id + '';
}
//上一条记录
function Previous(id) {
	location.href = '${ctx}/order!toEditSaleOrder.action?number=2&id='
			+ id + '';
}
//下一条记录
function Next(id) {
	location.href = '${ctx}/order!toEditSaleOrder.action?number=3&id='
			+ id + '';
}
//最后一条记录
function Last(id) {
	location.href = '${ctx}/order!toEditSaleOrder.action?number=4&id='
			+ id + '';
}
