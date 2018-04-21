//上传插件
var emiUpload = {
		tableName : '',			//表名
		idColumnName:'',		//id字段名
		recordId  : '',			//数据id
		buttonText: "", 	//按钮上的文字
		showType:'dialog',		//显示方式 'div'：嵌入在页面 ; 'dialog':弹出框
		targetId:'',			//目标元素id
		width : "800px",		//宽度
		height : "350px",		//高度
		editable:true,			//是否可编辑
		fileTypeExts: '*.*',	//可上传的文件类型  如：'*.gif; *.jpg; *.png' ;*.rar;*.zip;*.doc;*.docx;*.xls;*.xlsx;*.txt;*.jpg;*.jpeg;*.png;*.gif;*.bmp
		reloadParent: false,		//是否刷新父页面（仅对dialog可用）
		uploadify : function(config){
			if(config.tableName){this.tableName = config.tableName;}
			if(config.idColumnName){this.idColumnName = config.idColumnName;}
			if(config.recordId){this.recordId = config.recordId;}
			if(config.buttonText){this.buttonText = encodeURI(config.buttonText);}
			if(config.showType){this.showType = config.showType;}
			if(config.targetId){this.targetId = config.targetId;}
			if(config.width){this.width = config.width;}
			if(config.height){this.height = config.height;}
			if(config.fileTypeExts){this.fileTypeExts = config.fileTypeExts;}
			if(config.editable || config.editable==false){this.editable = config.editable;}
			if(config.reloadParent || config.reloadParent==false){this.reloadParent = config.reloadParent;}
			var contextPath = getContextPath();
			if(this.showType=='div'){
				var i = document.createElement("iframe");
		         
				i.src = contextPath+"/commonfile_toUploadFile.action?targetId="+this.targetId+"&height="+this.height+"&fileTypeExts="+this.fileTypeExts+"&editable="+this.editable+"&idColumnName="+this.idColumnName+"&tableName="+this.tableName+"&recordId="+this.recordId+"&buttonText="+encodeURI(this.buttonText);
				i.scrolling = "auto";
				i.frameBorder = "0";
				i.id=this.tableName+this.recordId;
				i.width = this.width;
				i.height = ((this.height.replace("px", "")-0)+20)+"px";
				document.getElementById(this.targetId).appendChild(i);
				addCountHidden(this.targetId);
			}
			if(this.showType=='dialog'){
				$('#'+this.targetId).bind("click", {'reloadParent':this.reloadParent, 'targetId':this.targetId,'height':this.height,'fileTypeExts':this.fileTypeExts,'editable':this.editable, 'width':this.width,'height':this.height,'tableName':this.tableName,'idColumnName':this.idColumnName,'recordId':this.recordId,'buttonText':this.buttonText}, function(e){
					$.dialog({
						drag: true,
						lock: false,
						resize: false,
						title:'附件',
						zIndex:10000,
					    width: e.data.width,
						height: ((e.data.height.replace("px", "")-0)+20)+"px",
						content: 'url:'+contextPath+'/commonfile_toUploadFile.action?targetId='+e.data.targetId+'&height='+e.data.height+'&fileTypeExts='+e.data.fileTypeExts+'&editable='+e.data.editable+'&idColumnName='+e.data.idColumnName+'&tableName='+e.data.tableName+'&recordId='+e.data.recordId+'&buttonText='+encodeURI(e.data.buttonText),
						ok:false,
						cancelVal:'关闭',
						cancel:function(){
							if(e.data.reloadParent){
								location.reload();
							}
						}
					});
				});
				addCountHidden(this.targetId);
			}
		},
		fileCount:function(targetId){//本次上传的文件数量
			var val = 0;
			if(document.getElementById(targetId+"_hiddenCount")){
				val = document.getElementById(targetId+"_hiddenCount").value;
			}
			return val;
		}
}

//添加文件计数隐藏域
function addCountHidden(targetId){
	var input = document.createElement("input");
	input.type = 'hidden';
	input.name = targetId+'_hiddenCount';
	input.id = targetId+'_hiddenCount';
	input.value = 0;
	document.body.appendChild(input);
}