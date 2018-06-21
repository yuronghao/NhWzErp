<!DOCTYPE html >
<%@page import="com.emi.sys.util.SysPropertites"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/common/header.jsp"%>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath() %>/emi.ico"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/canclose.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common.css">
<%-- 	<script type="text/javascript" src="<%=contextPath %>/scripts/plugins/jquery.cycle.all.js"></script> --%>
	<script type="text/javascript" src="<%=contextPath %>/scripts/websjy.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/lhgdialog.js"></script>
	<!--自动调整宽度\高度-->
	<script type="text/javascript">
		function getHeight(){
			var valueH=document.body.clientHeight;
			var mainH=valueH-0;
			var cH=valueH-113;
			//alert(cH);
			$('#mainContent').attr("style","height:"+mainH+"px");
			$('#rightMain').attr("style","height:"+cH+"px");
		}
		$(function(){				
			window.onresize = function winResize() {					
				if( $('#mainBody').width()<1204  ){
					$('#mainContainer').attr("style","min-width:1204px;");
				}else{
					$('#mainContainer').removeAttr("style");
				}
			};
			getHeight();
			
		});	
		
 					//frame 展开收藏	
		$(function(){
			
			var rh=$('body').height();
			$('#openClose').attr('style','top:'+(rh/2)+"px");
			$('#openClose').addClass('open');
			
			$('#openClose').click(function(){
				if($('#leftFrame').width()>0 ){
					$('#leftFrame').attr('style','width:0%;height:100%;display: none;');
					$('#rightFrame').attr('style','width:100%;height:100%');
					
					$('#openClose').removeClass('open');
					$('#openClose').addClass('close');
				}else if($('#leftFrame').width()==0){
					$('#leftFrame').attr('style','width:15%;height:100%;display: block;');
					$('#rightFrame').attr('style','width:85%;height:100%');
					
					$('#openClose').removeClass('close');
					$('#openClose').addClass('open');
				}				
			});
			
		
		});
		
		$(function(){
			var eh=$('body').height();
			$('#div_pannel').attr('style','height:'+(eh-110)+"px");
		})
		
		$(function(){
			var wh=$('#leftFrame').width();
			$('#nav').attr('style','width:'+wh+"px");
			$('#nav').addClass('whone')
		})
	 
		
		
		$(function(){
			$(".EMmessagedown").mouseover(function(){
				$(".EMmessage").slideDown(400);
				$(this).addClass("messgb");
			})
			
		})
		$(function(){
			$(".EMmessagedown").mouseleave(function(){
				$(".EMmessage").slideUp(300);
				$(this).removeClass("messgb");
			})
		})
	
	</script>
	<script>
		 
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
					/* var txt = '<li id="li_'+favId+'" onmouseover="showOpt(\''+favId+'\')"  onmouseout="hideOpt(\''+favId+'\')">';
						txt += '<span id="opt_'+favId+'" style="float: right;margin-top: 10px;display: none">';
						txt += '	<img style="cursor: pointer;" alt="" title="修改名称" src="'+ctx+'/images/common/edit.png" onclick="editFav(\''+favId+'\')">';
						txt += '	<img style="cursor: pointer;" alt="" title="删除" src="'+ctx+'/images/common/erase.png" onclick="deleteFav(\''+favId+'\')">';
						txt += '</span>';
						txt += '<a id="a_'+favId+'" href="'+ctx+rightUrl+'"  target="rightMain">'+rightName+'</a>';
						txt += '<input type="text" id="text_'+favId+'" style="display: none" maxlength="10" value="'+rightName+'" onblur="saveFav(\''+favId+'\');">';
						txt += '</li>'; 
						$('#menu_ul2').append(txt);*/
					var txt = '<li><a href="javaScript:void(0);" onclick="clickFav(\''+rightUrl+'\',\''+rightName+'\');">'+rightName+'</a> </li>';
					$('#scmenu').append(txt);
				}
			}
		});
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
	
	
	//创建菜单
 	function createMenu(c_menu,level){
		var html="";
		var mItem = c_menu;
		html += '<li style="overflow:hidden"><a href="javaScript:void(0)" ';
		if(mItem.childNodes && mItem.childNodes.length>0){
			html += 'class="inactive active"';
		}else if(mItem.rightUrl && mItem.rightUrl!=''){
			html += 'onclick="CreateDiv(\''+mItem.rightCode+'\',\'${ctx}/'+mItem.rightUrl+'\',\''+mItem.rightName+'\',true)"';
		}
		html += ' style="vertical-align: middle;">';
		if(mItem.imgUrl && mItem.imgUrl!=''){
			html += '<img src="${ctx}/'+mItem.imgUrl+'" class="menuImg"/>';
		}else{
			var defaultImg = level==1?'${ctx}/img/menu/control.png':level==2?'${ctx}/img/menu/paper.png':level==3?'${ctx}/img/menu/docs.png':'${ctx}/img/menu/docs.png';
			html += '<img src="'+defaultImg+'" class="menuImg"/>';
		}
		html += mItem.rightName+'</a>';
		if(mItem.childNodes && mItem.childNodes.length>0){
			html +='<ul style="overflow:hidden">';
			for(var j=0;j<mItem.childNodes.length;j++){
				var c_mItem = mItem.childNodes[j];
				//递归
				html += createMenu(c_mItem,level+1);
			}
			html += '</ul>';
		}
		return html;
	} 
	
 	 $(function(){
		//var menu = [{rightUrl:'/login.jsp',rightName:'控制中心',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[]}]}]}];
		//初始化菜单
		var menu=[];
		var menu_json = '${rights}';
		if(menu_json!=''){
			menu = eval('('+menu_json+')');
		}
		var html="<ul>";
		for(var i=0;i<menu.length;i++){
			html += createMenu(menu[i],1);
		}
		html += '</ul>';
		document.getElementById('menuList').innerHTML=html;
		
	});  
	
	function clickFav(rightUrl,rightName){
		//创建tab
		CreateDiv(rightName,'${ctx}/'+rightUrl,rightName);
	}
	
	//退出系统
	function exit(){
		parent.window.location.href="${ctx }/login_logout.emi";
	}
	
	//修改密码
	function modifyPassword(){
		var pwdWin = $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'修改密码',
		    width: '400px',
			height: '180px',
			content: 'url:login_toModifyPassword.emi',
			ok:function(){
				var oldPwd = this.content.document.getElementById('oldPassword').value;
				var newPwd = this.content.document.getElementById('newPassword').value;
				var confirmPwd = this.content.document.getElementById('confirmPassword').value;
				if(oldPwd==''){
					$.dialog.alert("请填写原密码");
				}else if(newPwd==''){
					$.dialog.alert("请填写新密码");
				}else if(confirmPwd!=newPwd){
					$.dialog.alert("新密码与确认密码不一致");
				}
				$.ajax({
					type: 'POST',
					url:'${ctx}/login_modifyPassword.emi',
					data:{oldPassword:oldPwd,newPassword:newPwd},
					dataType:'text',
					success: function (mes){
						var data = eval("("+mes+")");
						if(data.success=='0'){
							$.dialog.alert("修改失败");
							return false;
						}
						if(data.success=='1'){
							$.dialog.alert("修改成功,请重新登录",function(){parent.window.location.href="${ctx}/login_logout.emi";});
							return true;
						}
						if(data.success=='2'){
							$.dialog.alert("原密码错误");
							return false;
						}
					}
				});
				
			},
			cancel:true
		});	
	}
	
	</script>
	<script type="text/javascript">
			$(document).ready(function(){
			  $(".al").mouseover(function(e){
              	$(this).find('.EMshoucang').css("display","block");
			    //$(".EMshoucang").css("display","block");
			  });
			  $(".al").mouseout(function(){
			    $(".EMshoucang").css("display","none");
			  });
			});
		</script>
		<script type="text/javascript">
			$(document).ready(function(){
			  $(".al").mouseover(function(e){
              	$(this).find('.QXshoucang').css("display","block");
			    //$(".EMshoucang").css("display","block");
			  });
			  $(".al").mouseout(function(){
			    $(".QXshoucang").css("display","none");
			  });
			});
			
			$(document).ready(function() {
				$('.inactive').click(function(){
					if($(this).siblings('ul').css('display')=='none'){
						$(this).parent('li').siblings('li').children('a').removeClass('inactives');
						//$(this).parent('li').siblings('li').children('a').addClass('inactive');
						$(this).addClass('inactives');
						$(this).siblings('ul').slideDown(100).children('li');
						$(this).parent('li').siblings('li').children('ul').slideUp(100).children('li');
						if($(this).parents('li').siblings('li').children('ul').css('display')=='block'){
							$(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('inactives');
							$(this).parents('li').siblings('li').children('ul').slideUp(100);

						}
					}else{
						//控制自身变成+号
						$(this).removeClass('inactives');
						//控制自身菜单下子菜单隐藏
						$(this).siblings('ul').slideUp(100);
						//控制自身子菜单变成+号
						$(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('inactives');
						//控制自身菜单下子菜单隐藏
						$(this).siblings('ul').children('li').children('ul').slideUp(100);
						//控制同级菜单只保持一个是展开的（-号显示）
						$(this).siblings('ul').children('li').children('a').removeClass('inactives');
					}
				})
			});
			
		</script>
		<style>
		/*body{
			background-image: url(img/bj.jpg);
			background-size: 100% 100%;
		}
		 */
		.whone{
			/*background-color: #6699c8;border-top: 1px solid #5e8db8;
*/		}
		#openClose {
		  position: absolute;
		  width: 10px;
		  height: 74px;
		  z-index: 99; 				 
		}
		.close {
		  background: url(img/mainToRight.png) no-repeat;		 	   
		}
		.open {
		  background: url(img/mainToLeft.png) no-repeat;
		   
		}
		.changeimg{
			/* background-image: url(../img/souc.png);
			background-repeat: no-repeat; */
			position: relative;
			z-index: 9999;
			top: 3px;
			margin-right: 2px;
			cursor: pointer;
		}
		.EMshoucang{
			width: 25px;
			height: 25px;
			margin-top:6px;
			background-image: url(img/souc.png);
			background-repeat: no-repeat;
		 	display: none;

		}
		.QXshoucang{
			width: 25px;
			height: 25px;
			background-image: url(img/ysouc.png);
			background-repeat: no-repeat;
			display: none;
		}
		.QXchangeimg{
			position: relative;
			z-index: 9999;
			top: 11px;
			margin-right: 2px;
			cursor: pointer;
		}
		.selected1{background-color:rgba(24,130,182,0.4);}
		
		.menuImg{width:22px;height:22px;vertical-align:middle;margin-right:10px}
		
			*{margin: 0;padding: 0}
			body{font-size: 14px;font-family: "宋体","微软雅黑";}
			ul,li{list-style: none;}
			a:link,a:visited{text-decoration: none;}
			.list{width: 100%;border-bottom:1px #286eb3 solid;margin:40px auto 0 auto;}
			.list ul li{ border:1px #286eb3 solid; border-top:0;border-left: 0;border-right: 0}
			.list ul li a{padding-left: 10px;color: #fff; font-size:14px; display: block; font-weight:bold; height:44px;line-height: 44px;position: relative;
			}
			.list ul li .inactive{ background:url(img/menu/off.png) no-repeat right center;margin-right: 15px;}
			.list ul li .inactives{background:url(img/menu/on.png) no-repeat right center;margin-right: 15px;} 
			.list ul li ul{display: none;}
			.list ul li ul li { border-left:0; border-right:0; background-color:rgba(108,155,250,0.2); border-color:#286eb3;}
			.list ul li ul li ul{display: none;}
			.list ul li ul li a{ padding-left:20px;}
			.list ul li ul li ul li { background-color:rgba(210,210,250,0.2); border-color:#286eb3; }
			.last{ background-color:background-color:rgba(210,210,250,0.2); border-color:#286eb3; }
			.list ul li ul li ul li a{ 	 padding-left:30px;}

		.ui_title{
			background: #58967B;
			border-bottom:1px solid #58967B;
		}

	</style>	

</head>
<body id="mainbody" style="background-image: none;background-color: #009A7A;">
	<div id="mainContainer">		
		<!--整体部分-->
		<div id="mainContent" >
			<!--菜单栏-->
			<div class="fl leftmenu" id="leftFrame">
				<!--<iframe src="EMleft.html" frameborder="0" id="leftMian" name="leftMain"  width="100%" height="100%"></iframe>-->
				<div class="leftcontent" style="overflow: auto;">
					<!--logo-->
					<div class="EMlogo">
						<img src="${ctx }/img/EMlogo.png" />
					</div>
					<!--消息部分-->
					<!-- <div class="EMinfor">
						<ul>
							<li class="fl EMinfor_lione">
								<a href="#">
									<ul>
										<li class="EMinfor_word">未完成</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_litwo">
								<a href="#">
									<ul>
										<li class="EMinfor_word">未接收</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_lithree">
								<a href="#">
									<ul>
										<li class="EMinfor_word">已发布</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_lifour" >
								<a href="#">
									<ul>
										<li class="EMinfor_word">已完成</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
						</ul>
					</div> -->
					 <!--<div class="closemenu"> 12</div>-->
					<!--导航菜单-->
					<div id="menuList" class="list">
						
					</div>
					</div>
				</div>
			<!---->
		 
			<div class="fl" id="rightFrame"  style="width: 86%;/* min-width:1000px; */">
				 <div class="navbar">
					 <ul class="EMtime">
					 	<li class="fl EMtimeli"><img src="${ctx }/img/login_time.png">&nbsp;&nbsp;登录时间：${sessionScope.LoginTime }</li>
					 	<%-- <li class="fl EMtimeli"><img src="${ctx }/img/Ezt.png">&nbsp;&nbsp;账套：${sessionScope.SobName }</li>--%>
					 	<li class="fl EMtimeli"><img src="${ctx }/img/zz.png">&nbsp;&nbsp;组织：${sessionScope.OrgName }</li> 
					 	<li class="fr EMtimeli EMmessagedown"><img src="img/Euser.png">&nbsp;&nbsp;<a href="#">Hi,${sessionScope.Person.pername }<c:if test="${sessionScope.isAdmin==1 }">(超级管理员)</c:if> <span><img src="${ctx }/img/down.png"> </span></a>
					 		<ul class="EMmessage">
					 			<li class="downimg"></li>	
					 			<li>
					 				<ul>
					 					<li><a href="#" onclick="modifyPassword();"><img src="${ctx }/img/key.png" />&nbsp;&nbsp;密码修改</a></li>
					 					<li><a href="#" onclick="exit();"><img src="${ctx }/img/out.png" />&nbsp;&nbsp;退出登录</a></li>	
					 				</ul>
					 			</li>
					 		</ul>
					 	</li>
					 </ul>
				</div>
				<!--可关闭tab-->
				  	<div class="emistep" style="position: absolute;left: 2px;z-index: 99;display: none" onmouseover="showStep()"><a id="next" href="#" onclick="document.getElementById('div_tab').scrollLeft=document.getElementById('div_tab').scrollLeft-100"><img alt="" src="${ctx }/img/br_pre.png" style="width: 10px;margin-bottom: -18px"/> </a></div>
					 <div style="float: left;width: 100%/* ;padding-right: 20px;padding-left: 10px */;background: #def1ff;" onmouseover="showStep()" onmouseout="hideStep()">
					 	<div style="width: 98%;margin-left: 0px">
							<ul class="clearfix EMclosetab" id="div_tab" style="white-space: nowrap;overflow:hidden;background: #def1ff;">
							  <li class="crent" id="首页" onclick="javascript:CreateDiv('首页','${ctx}/loginWelcome.jsp','首页')"><span><a href="javascript:void(0);"  title="首页" class="menua">&nbsp;首页</a></span></li>
							</ul>
						</div>
					</div>
					<div class="emistep" style="position: absolute;right: 2px;z-index: 99;display: none" onmouseover="showStep()"><a id="next" href="#" onclick="document.getElementById('div_tab').scrollLeft=document.getElementById('div_tab').scrollLeft+100"><img alt="" src="${ctx }/img/br_next.png" style="width: 10px;margin-top: 20px"/> </a></div> 
				<!--可关闭 end-->
				  
				<div id="openClose" ></div>		
				<!--<iframe src="index.html" framemagin="0" frameborder="0" id="rightMain" name="rightMain" width="100%" height="100%"></iframe>-->
				<div id="div_pannel" >
					<iframe id="div_首页" src="${ctx}/loginWelcome.jsp" height="100%" frameborder="0" width="100%" style="display: block;"></iframe>
				</div>
			</div>
			<div class="cf"></div>
		</div>		 
	</div>
</body>
<script>	
	/*tab切换*/
	function showStep(){
		$('.emistep').show();
	}
	
	function hideStep(){
		$('.emistep').hide();
	}
		$(function(){
			var i=0;
			var j=0;
		$(".gf_tag").each(function(e){
			
			$(this).attr("id","gf_tag_"+i)
			i++;
		})
		$(".gf_tag_a").each(function(e){
			var i=0;
			$(this).attr("id","gf_tag_a_"+j)
			j++;
		})

		$(".gf_tag_a").click(function(){
			var tagid=$(this).attr("id");
			var divids=tagid.split("_");
			var divid="gf_tag_"+divids[divids.length-1];
			//先给所有的赋值为隐藏
			$(".gf_tag").hide()
			//点击的展示
			$("#"+divid).show();
			//给样式
			/*$(".gf_tag_a").attr("class","gf_tag_a");
			$(this).attr("class","selected1 gf_tag_a");*/
			$(".gf_tag_a").removeClass("selected1");
			$(this).addClass("selected1");
		})
	    })	
	    
 
	</script>
	<script language="JavaScript">
 
	function ShowMenu(obj,n){
		 var Nav = obj.parentNode;
		 if(!Nav.id){
		  var BName = Nav.getElementsByTagName("ul");
		  var HName = Nav.getElementsByTagName("h2");
		  var t = 2;
		 }else{
		  var BName = document.getElementById(Nav.id).getElementsByTagName("span");
		  var HName = document.getElementById(Nav.id).getElementsByTagName("h1");
		  var t = 1;
		 }
		 for(var i=0; i<HName.length;i++){
		  HName[i].innerHTML = HName[i].innerHTML.replace("-","+");
		  HName[i].className = "";
		 }
		 obj.className = "h" + t;
		 for(var i=0; i<BName.length; i++){if(i!=n){BName[i].className = "no";}}
		 if(BName[n].className == "no"){
		  BName[n].className = "";
		  obj.innerHTML = obj.innerHTML.replace("+","-");
		 }else{
		  BName[n].className = "no";
		  obj.className = "";
		  obj.innerHTML = obj.innerHTML.replace("-","+");
		 }
		}
		 
 
 
$(".changeimg").click(function(){
    $(".changeimg>img").toggle();
});


    $(function () {
        isexitnopayment();
    })


    function isexitnopayment(){
        //ajax判断是
        $.ajax({
            type: 'POST',
            url: '${ctx}/wms/wareHouse_getNoticeInfo.emi',
            success: function(req){
                var jsonO =  eval('(' + req + ')');
                if(jsonO.success== 1){
                    var  a = jsonO.datamap.lysqcount;
                    var  b = jsonO.datamap.clckcount;
                    var  c = jsonO.datamap.dbdcount;
                    var  d = jsonO.datamap.bfdcount;
                    var html = "" ;
                    if(a >0){
                        html+="<a href=\"javaScript:void(0)\" onclick=\"CreateDiv('erp_04_01_01','/erp/wms/wareHouse_gtasksMymaterialApplyWarehouseList.emi','领用申请单列表',true)\" >您有"+a+"个领用申请单待处理</a><br> ";
                    }
                    if(b >0){
                        html+=" <a href=\"javaScript:void(0)\" onclick=\"CreateDiv('erp_04_01_02','/erp/wms/wareHouse_gtasksMymaterialOutWarehouseList.emi','材料出库单列表',true)\" >您有"+b+"个材料出库单待处理</a><br> ";
                    }
                    if(c >0){
                        html+=" <a href=\"javaScript:void(0)\" onclick=\"CreateDiv('erp_04_01_03','/erp/wms/wareHouse_gtasksMygetAllocationList.emi','调拨单列表',true)\" >您有"+c+"个调拨单待处理</a><br> ";
                    }
                    if(d >0){
                        html+=" <a href=\"javaScript:void(0)\" onclick=\"CreateDiv('erp_04_01_04','/erp/wms/wareHouse_gtasksMyothersOutList.emi','报废单列表',true)\" >您有"+d+"个报废单待处理</a><br> ";
                    }
                    if(html != ""){
                        $.dialog.notice({
                            title: '消息提醒',
                            width: 220,
                            max: false,
                            content: ''+html+'',
                            time: null

                        });
                    }


                }else{

                }
            }
        });
    }


</script>
</html>