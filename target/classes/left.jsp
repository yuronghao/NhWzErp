<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%String contextPath = request.getContextPath();%> 
<c:set var="ctx" value="<%=contextPath %>"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>EMI SOFTWEAR</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="robots" content="index,follow" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico" />

<script src="${ctx }/scripts/plugins/bootstrap-treeview/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/menujs/js/tab.js"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/menujs/js/accordion.js"></script>
<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog.js"></script>
<style type="text/css">
*{
	margin:0px;
	padding:0px;
	text-decoration: none;
	list-style-type: none;
}

body{
	font-family: "微软雅黑";
	font-size:14px;
}
.collect{
	width: 20px;
	height: 20px;
	display: block;
	cursor: pointer;
	z-index: 100;
	margin-right: 10px;
	margin-top: 8px;
}
.collect:hover{
	background-repeat: no-repeat;
	background-image: url('${ctx}/images/index/sc.png');
}
.bg{background-image: url('${ctx}/images/sc.png');}
/*方法三*/
	#tab{overflow: hidden;background: white;width:100%;;}
	#tab div{float: left;color: #000;cursor: pointer;line-height: 35px;}
	#tab div.current{color:#000;background: #ccc;}
/*   #tab div:hover{background-color:#d4e7f0; } */
	#content{border:1px solid #000;border-top-width:0;}
	#content ul{line-height: 25px;display: none;margin:0 30px; padding: 10px 0;}
  .hid{display: none;}
  .show	{  display: block; }
  
  ul{list-style:none}
.demo{  padding:10px; background:#f7f7f7}
.nav {width: 213px; padding: 40px 28px 25px 0; font-family: "Microsoft yahei", Arial, Helvetica, sans-serif;} 
ul.nav {padding: 0; margin: 0; font-size: 1em; line-height: 0.5em; list-style: none;} 
ul.nav li {} 
ul.nav li a {line-height: 10px; font-size: 14px; padding: 10px 5px; color: #000; display: block; text-decoration: none; font-weight: bolder;}
ul.nav li a:hover {background-color:#675C7C;    color:white;}
ul.nav ul { margin: 0; padding: 0;display: none;} 
ul.nav ul li { margin: 0; padding: 0; clear: both;} 
ul.nav ul li a { padding-left: 20px; font-size: 12px; font-weight: normal;}
ul.nav ul li a:hover {background-color:#D3C99C; color:#675C7C;} 
ul.nav ul ul li a { padding-left: 40px;} 
ul.nav ul ul li a:hover { background-color:#D3CEB8; color:#675C7C;} 
ul.nav span{float:right;}
	</style>

<script type="text/javascript">
var ctx = "<%=contextPath %>";
  $(document).ready(function(){
    $("#div1").click(function(){
      $("#div1").css("background-color","#d4e7f0");
      $("#div2").css("background-color","white");
      $("#menu1").css("display","block");
      $("#menu2").css("display","none");
    });
    $("#div2").click(function(){
      $("#div2").css("background-color","#d4e7f0");
      $("#div1").css("background-color","white");
      $("#menu2").css("display","block");
      $("#menu1").css("display","none");
    });
  }); 

  $(function(){
	   
	 	//var menu = [{rightUrl:'/login.jsp',rightName:'控制中心',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[]}]}]}];
		//初始化菜单
		var menu = ${rights};
		var html = "";
		for(var i=0;i<menu.length;i++){
			var mItem = menu[i];
			html += '<li ';
			if(i==0){
				html += 'class="active"';
			}
			html += '>';
			if(mItem.childNodes && mItem.childNodes.length>0){
				//有字节的菜单
				html += '<a href="#">'+mItem.rightName+'</a>';
				html += '<ul>';
				html += 	childMenu(mItem.childNodes);
				html += '</ul>';
			}else{
				//没有字节点的菜单
				html += '<a href="'+ctx+mItem.rightUrl+'" target="rightMain">'+mItem.rightName+'</a>';
			}
			html += '</li>';
		}
		document.getElementById('menu_ul').innerHTML=html;
		
		$(".nav").accordion({
	        //accordion: true,
	        speed: 300,
		    /* closedSign: '<span style="margin-right:10px">[+]</span>',
			openedSign: '<span style="margin-right:10px">[-]</span>' */
	        closedSign: '<span style="margin-right:50%"><img src="'+ctx+'/images/2.png"/></span>',
			openedSign: '<span style="margin-right:50%"><img src="'+ctx+'/images/1.png"/></span>'
		});
	}); 
  
  /*-----------------以下是全部菜单Tab的function------------------*/   
//子菜单
	function childMenu(c_menu){
		var html="";
		for(var i=0;i<c_menu.length;i++){
			var mItem = c_menu[i];
			html += '<li >';
			if(mItem.childNodes && mItem.childNodes.length>0){
				//有字节的菜单
				html += '<a href="#">'+mItem.rightName+'</a>';
				html += '<ul>';
				html += 	childMenu(mItem.childNodes);
				html += '</ul>';
			}else{
				html += '<span class="collect" title="加入收藏" onclick="addFav(\''+mItem.gid+'\',\''+mItem.rightName+'\',\''+mItem.rightUrl+'\',\''+mItem.rightCode+'\')"></span>';
				//没有字节点的菜单
				var rightUrl = mItem.rightUrl;
				if (rightUrl.indexOf("?") > 0) { 
					rightUrl += "&menuCode="+mItem.rightCode;
				}else{
					rightUrl += "?menuCode="+mItem.rightCode;
				}
				html += '<a href="'+ctx+rightUrl+'" onclick="showTips()" target="rightMain">'+mItem.rightName+'</a>';
			}
			html += '</li>';
		}
		return html;
	}
  
	//添加到收藏
	function addFav(rightId,rightName,rightUrl,rightCode){
		$.ajax({
			data:{rightId:rightId,rightName:rightName},
			type: 'POST',
			url:'${ctx}/right_addToMyFavorite.emi',
			dataType:'text',
			success: function (mes){
				var obj = eval("("+mes+")");
				if(obj.success=='0'){
					$.dialog.alert("收藏失败");
				}
				if(obj.success=='1'){
					var favId = obj.favId;
					$.dialog({
						title: '消息',
						time: 1,
					    content: '收藏成功'
					});
					if (rightUrl.indexOf("?") > 0) { 
						rightUrl += "&menuCode="+rightCode;
					}else{
						rightUrl += "?menuCode="+rightCode;
					}
					var txt = '<li id="li_'+favId+'" onmouseover="showOpt(\''+favId+'\')"  onmouseout="hideOpt(\''+favId+'\')">';
						txt += '<span id="opt_'+favId+'" style="float: right;margin-top: 10px;display: none">';
						txt += '	<img style="cursor: pointer;" alt="" title="修改名称" src="'+ctx+'/images/common/edit.png" onclick="editFav(\''+favId+'\')">';
						txt += '	<img style="cursor: pointer;" alt="" title="删除" src="'+ctx+'/images/common/erase.png" onclick="deleteFav(\''+favId+'\')">';
						txt += '</span>';
						txt += '<a id="a_'+favId+'" href="'+ctx+rightUrl+'"  target="rightMain">'+rightName+'</a>';
						txt += '<input type="text" id="text_'+favId+'" style="display: none" maxlength="10" value="'+rightName+'" onblur="saveFav(\''+favId+'\');">';
						txt += '</li>';
					$('#menu_ul2').append(txt);
				}
			}
		});
	}
	/*-----------------以上是全部菜单Tab的function------------------*/
	
	/*-----------------以下是收藏的菜单Tab的function------------------*/
	//编辑收藏的菜单名称
	function editFav(favId){
		document.getElementById('a_'+favId).style.display="none";
		document.getElementById('text_'+favId).style.display="block";
		
		//将光标移到文本末尾
		var esrc = document.getElementById('text_'+favId);
		var rtextRange =esrc.createTextRange();
		rtextRange.moveStart('character',esrc.value.length);
		rtextRange.collapse(true);
		rtextRange.select();
		
	}
	
	//删除收藏的菜单
	function deleteFav(favId){
		if(confirm("确定删除?")){
			var _div = document.getElementById('li_'+favId);
			var content = document.getElementById('menu_ul2');
			content.removeChild(_div);
			//异步删除
			jQuery.ajax({
				data: {gid:favId},
				type: 'POST',
				url: '${ctx}/right_deleteFavorite.emi',
				success: function(req){
					//刷新页面
					//location.reload();
				}
			});
			
		}
	}
	
	//保存收藏菜单的名称
	function saveFav(favId){
		document.getElementById('text_'+favId).style.display="none";
		document.getElementById('a_'+favId).style.display="block";
		//改变文字
		var menuName = document.getElementById('text_'+favId).value.trim();
		document.getElementById('a_'+favId).innerHTML = menuName;
		document.getElementById('text_'+favId).value = menuName;
		//保存名称入库
		jQuery.ajax({
			data: {gid:favId,menuName:menuName},
			type: 'POST',
			url: '${ctx}/right_updateFavorite.emi',
			success: function(req){
			}
		});
	}
	
	function showOpt(favId){
		document.getElementById("opt_"+favId).style.display="block";
	}
	
	function hideOpt(favId){
		document.getElementById("opt_"+favId).style.display="none";
	}
	/*-----------------以上是收藏的菜单function------------------*/
	function showTips(tip){
		if(!tip){
			tip = "页面加载中...";
		}
		$.dialog.tips(tip,30,'loading.gif'); 
	}
	function hideTips(tip,func){
		if(!tip){
			tip = "数据加载完毕";
		}
		$.dialog.tips(tip,0.1,'loading.gif',func);
	}
	var iframe = parent.document.getElementById("rightMain");
	if (iframe.attachEvent){ 
		iframe.attachEvent("onload", function(){ 
			hideTips();
		}); 
	} else { 
		iframe.onload = function(){ 
			hideTips();
		}; 
	}
</script>
</head>
<body style="overflow-x:hidden;">
	  <div style="position:fixed;z-index:9999px;width:99%;">
	        <ul id="tab" style="">
	          <div class="current active" id="div1" style="text-align:center;width:49.8%;background-color:#d4e7f0;">全部</div>
	          <div class="active" id="div2" style="width:50%;text-align:center;background-color:white">收藏</div>
	        </ul>
	  </div>
      <div id="menu1" class="demo" style="background-color:#d4e7f0;width:92%;">
	      <ul id="menu_ul" class="nav" style="margin-left:0%;width:100%;margin-top:30px;">
	        <!--  <li class="active"><a href="#">服务</a>
	              <ul>
	                  <li ><a href="#">JAVASCRIPT</a>
			              <ul>
			                  <li><a href="#">JAVASCRIPT1</a></li>
			                  <li><a href="#">PHP1</a></li>
			              </ul>
			          </li>
	                  <li><a href="#">PHP</a></li>
	              </ul>
	         </li>
	         <li><a href="#">案例</a></li> -->
	      </ul>
  	 </div>
   
    <div id="menu2" class="demo" style="display: none;background-color:#d4e7f0;width:100%;">
   		<ul id="menu_ul2" class="nav" >
   		<c:forEach var="fav" items="${favorites }" varStatus="stat">
   		<li id="li_${fav.gid }" onmouseover="showOpt('${fav.gid }')"  onmouseout="hideOpt('${fav.gid }')">
   			<span id="opt_${fav.gid }" style="float: right;margin-top: 10px;display: none">
				<img style="cursor: pointer;" alt="" title="修改名称" src="${ctx }/images/common/edit.png" onclick="editFav('${fav.gid}')">
				<img style="cursor: pointer;" alt="" title="删除" src="${ctx }/images/common/erase.png" onclick="deleteFav('${fav.gid}')">
			</span>
			<a id="a_${fav.gid }" href="${ctx}${fav.rightUrl }<c:if test="${fn:indexOf(fav.rightUrl,'?')>=0 }">&</c:if><c:if test="${fn:indexOf(fav.rightUrl,'?')<0 }">?</c:if>menuCode=${fav.rightCode}" target="rightMain">${fav.menuName}</a>
			<input type="text" id="text_${fav.gid }" style="display: none" maxlength="10" value="${fav.menuName}" onblur="saveFav('${fav.gid}');">
		</li>
		</c:forEach>
   		</ul>
    </div>
	
</body>
</html>