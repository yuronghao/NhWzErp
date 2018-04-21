
/*
 * 初始化权限
 */
function initRight(sys_isAdmin,sys_rightFuns) {
	//加载完页面，如果不是管理员，初始化权限  元素自定义属性 emiRight : show 1/add 2/edit 4/delete 8/ editform 16/audit 32/check 64
	//var sys_isAdmin = "${sessionScope.isAdmin}";
	if(sys_isAdmin!=1){
		//var sys_rightFuns = ${sessionScope.rightFuns};
		//1、放出有权限的元素
		if(sys_rightCode){
			//查询所有权限
			var fun_value = sys_rightFuns[sys_rightCode]; 
			//控制 input 和div  如果要改成所有，用*
			var inputElements = document.getElementsByTagName("input"); 
			var divElements = document.getElementsByTagName("div"); 
			for(var i=0;i<inputElements.length;i++){
				setShow(inputElements[i],fun_value);
			}
			for(var i=0;i<divElements.length;i++){
				setShow(divElements[i],fun_value);
			}
		}
		
	}
}

function setShow(e,fun_value){
	var emiRight = e.getAttribute("emiRight");
	if(emiRight && emiRight!=''){
		e.style.display="none";
	}
	var show = false;
	if(emiRight=="show" && (fun_value&1)){
		show = true;
	}
	if(emiRight=="add" && (fun_value&2)){
		show = true;
	}
	if(emiRight=="edit" && (fun_value&4)){
		show = true;
	}
	if(emiRight=="delete" && (fun_value&8)){
		show = true;
	}
	if(emiRight=="audit" && (fun_value&32)){
		show = true;
	}
	if(emiRight=="check" && (fun_value&64)){
		show = true;
	}
	if(show){
		e.style.display="";
	}
	
}

