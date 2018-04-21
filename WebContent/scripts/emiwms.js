//input 可编辑
function inputCanUse(obj){
	for(var i=0;i<obj.length;i++){
		obj.eq(i).removeAttr('readonly');
	}
}

//input 不可编辑
function inputCannotUse(obj){
	for(var i=0;i<obj.length;i++){
		obj.eq(i).attr('readonly','readonly');
	}
}
//select  可编辑
function selectCanUse(obj){
	for(var i=0;i<obj.length;i++){
		obj.eq(i).removeAttr('disabled');
	}
}

//select 不可编辑
function selectCannotUse(obj){
	for(var i=0;i<obj.length;i++){
		obj.eq(i).attr('disabled','disabled');
	}
}

//按钮效果
	$(function(){
		//页面加载默认效果
		$('#saveBtn').attr("disabled","ture");
		$('#saveBtn').attr("style","background-color:#B8B8B8;color:#787878");
	
		$('#giveUpBtn').attr("disabled","ture");
		$('#giveUpBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		
		$('#follBtn').attr("style","display:none");
	});
	
	//点击新增
	function  addBtn(){
				
		$('#addBtn').attr("disabled","ture");
		$('#addBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#revBtn').attr("disabled","ture");
		$('#revBtn').attr("style","background-color:#B8B8B8;color:#787878");

		$('#delBtn').attr("disabled","ture");
		$('#delBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#saveBtn').removeAttr("disabled");
		$('#saveBtn').removeAttr("style");

		$('#giveUpBtn').removeAttr("disabled");
		$('#giveUpBtn').removeAttr("style");
		
		$('#follBtn').removeAttr("style");
	}
	
	//点击修改
	function revBtn(){
		
		$('#addBtn').attr("disabled","ture");
		$('#addBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#revBtn').attr("disabled","ture");
		$('#revBtn').attr("style","background-color:#B8B8B8;color:#787878");

		$('#delBtn').attr("disabled","ture");
		$('#delBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#copyReport').attr("disabled","ture");
		$('#copyReport').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#saveBtn').removeAttr("disabled");
		$('#saveBtn').removeAttr("style");

		$('#giveUpBtn').removeAttr("disabled");
		$('#giveUpBtn').removeAttr("style");
		$('#follBtn').removeAttr("style");
		
	}
	
	
	//点击保存
	function saveBtn(){
		
		$('#addBtn').removeAttr("disabled");
		$('#addBtn').removeAttr("style");
		
		$('#revBtn').removeAttr("disabled");
		$('#revBtn').removeAttr("style");

		$('#delBtn').removeAttr("disabled");
		$('#delBtn').removeAttr("style");
		
		$('#saveBtn').attr("disabled","ture");
		$('#saveBtn').attr("style","background-color:#B8B8B8;color:#787878");

		$('#giveUpBtn').attr("disabled","ture");
		$('#giveUpBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
	}
	
	
	//点击放弃
	function giveUpBtn(){
		
		$('#addBtn').removeAttr("disabled");
		$('#addBtn').removeAttr("style");
		
		$('#revBtn').removeAttr("disabled");
		$('#revBtn').removeAttr("style");

		$('#delBtn').removeAttr("disabled");
		$('#delBtn').removeAttr("style");
		
		$('#saveBtn').attr("disabled","ture");
		$('#saveBtn').attr("style","background-color:#B8B8B8;color:#787878");

		$('#giveUpBtn').attr("disabled","ture");
		$('#giveUpBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
	}
	
	
	//点击放弃
	function adjustmentPeopleBtn(){
		
		$('#addBtn').attr("disabled","ture");
		$('#addBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#adjustmentPeople').attr("disabled","ture");
		$('#adjustmentPeople').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#revBtn').attr("disabled","ture");
		$('#revBtn').attr("style","background-color:#B8B8B8;color:#787878");

		$('#delBtn').attr("disabled","ture");
		$('#delBtn').attr("style","background-color:#B8B8B8;color:#787878");
		
		$('#saveBtn').removeAttr("disabled");
		$('#saveBtn').removeAttr("style");

		$('#giveUpBtn').removeAttr("disabled");
		$('#giveUpBtn').removeAttr("style");
		
	}
	
	
	

	
	




