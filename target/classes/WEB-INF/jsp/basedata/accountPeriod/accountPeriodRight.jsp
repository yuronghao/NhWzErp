<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账期设置树</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>

<script type="text/javascript">
	
	//格局化日期：yyyy-MM-dd  
	function formatDate(date) {  
		
		var myyear = date.getFullYear();  
		var mymonth = date.getMonth()+1;  
		var myweekday = date.getDate();  
		  
		if(mymonth < 10){  
			mymonth = "0" + mymonth;  
		}  
		if(myweekday < 10){  
			myweekday = "0" + myweekday;  
		}  
		
		return (myyear+"-"+mymonth + "-" + myweekday);  
	} 
	
	
	//获得某月的天数  
	function getMonthDays(nowYear,myMonth){  
		var monthStartDate = new Date(nowYear, myMonth, 1);  
		var monthEndDate = new Date(nowYear, myMonth + 1, 1);  
		var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);  
		return days;  
	}  
	
	//获得本月的停止日期  
	function getMonthEndDate(nowYear,nowMonth){  
		var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear,nowMonth));  
		return formatDate(monthEndDate);  
	}  
	
	//获取当前日期后的一天
	function getNextDay(d){
        d = new Date(d);
        d = +d + 1000*60*60*24;
        d = new Date(d);
        //return d;
        //格式化
        return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
         
    }
	
	//第一行日期变化事件
	function dateChanged(obj){
		var year=$(obj).val();
		year=year.substring(0,4);
		
		getOrderByYear(year);
		
	}
	
	//根据年获得当年每个月最后一天日期
	function getOrderByYear(year){
		var html="";
		for(var i=0;i<=11;i++){
			
			var lastDay=getMonthEndDate(year,i);
			
			var month="0"+(i+1);
			if(month.length>2){
				month=month.substring(1, 3);
			}else{
				month=month.substring(0, 2);
			}
			
			if(i==0){
				html+='<tr class="period">'+
						'<td>'+(i+1)+'</td>'+
						'<td><input readonly="readonly" name=\'bdate'+1+'\'  onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+year+"-"+month+'-01\'  class="Wdate"/></td>'+
						'<td><input name=\'edate'+1+'\' onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+lastDay+'\' class="Wdate" onchange="endDateChange(this)"/></td>'+
					'</tr>';
			}else if(i==11){
				html+='<tr class="period">'+
						'<td>'+(i+1)+'</td>'+
						'<td><input name=\'bdate'+12+'\' readonly="readonly" type="text" value=\''+year+"-"+month+'-01\'  class="Wdate"/></td>'+
						'<td><input name=\'edate'+12+'\' onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+lastDay+'\' class="Wdate" onchange="endDateChange(this)"/></td>'+
					'</tr>';
			}else{
				html+='<tr class="period">'+
						'<td>'+(i+1)+'</td>'+
						'<td><input name=\'bdate'+(i+1)+'\' readonly="readonly" type="text" value=\''+year+"-"+month+'-01\'  class="Wdate"/></td>'+
						'<td><input name=\'edate'+(i+1)+'\' readonly="readonly" type="text" value=\''+lastDay+'\' class="Wdate" onchange="endDateChange(this)"/></td>'+
					'</tr>';
			}
	
		}
		
		$('tbody').children('.period').remove();
		$('tbody').append(html);
		
	}
	
	//结束日期变化事件
	function endDateChange(obj){
		
		var str=$(obj).val();//yyyy-MM-dd
		var date=new Date(str);//年月日

		var year=date.getFullYear();//年
		var month=date.getMonth();//月 从0开始
		var day=date.getDate();//日
		
		var index=$(obj).parent().parent().children().eq(0).text();
		var endDateArray=new Array();//1-11结束日期
		var beginDateArray=new Array();//2-12开始日期 
		
		if(index==1 ){
			if(day==31){
				getOrderByYear(year);
				
				return;
			}else{
				
				for(var i=0;i<11;i++){
					
					if(i+1<10){
						if(i+1==2 && day>=28){
							
							if((year%4==0 && year%100!=0)||(year%100==0 && year%400==0)){//闰年
								endDateArray[i]=year+"-0"+(month+1+i)+"-"+29;//结束日期
							}else{
								endDateArray[i]=year+"-0"+(month+1+i)+"-"+28;//结束日期
							}
							
							beginDateArray[i]=new Date((new Date(endDateArray[i])).getTime()+1000*60*60*24);//开始日期
						}else{
							endDateArray[i]=year+"-0"+(month+1+i)+"-"+day;//结束日期
							beginDateArray[i]=new Date((new Date(endDateArray[i])).getTime()+1000*60*60*24);//开始日期
						}

					}else{
						endDateArray[i]=year+"-"+(month+1+i)+"-"+day;
						beginDateArray[i]=new Date((new Date(endDateArray[i])).getTime()+1000*60*60*24);//开始日期
					}
					
					
				}
				
			}
		}

		
		var gidHidden=$('.gidHidden');
		console.log(gidHidden);
		
		//拼html
		var html="";
		var gid1=$('#gid1').val();
		var gid12=$('#gid12').val();
		for(var i=0;i<10;i++){
			
			html+='<tr class="period">'+
					'<td>'+(i+2)+'</td>'+
					'<input type="hidden" class="gidHidden" value="'+gidHidden.eq(i+1).val()+'"  name="gid'+(i+2)+'"/>'+
					'<td><input name=\'bdate'+(i+2)+'\' type="text" readonly="readonly" value=\''+formatDate(beginDateArray[i])+'\'  class="Wdate"/></td>'+
					'<td><input name=\'edate'+(i+2)+'\' type="text" readonly="readonly" value=\''+formatDate(new Date(endDateArray[i+1]))+'\' class="Wdate" /></td>'+
				  '</tr>';
		}
		
		html='<tr class="period">'+
				'<td>'+1+'</td>'+
				'<input type="hidden" class="gidHidden" value="'+gid1+'"  name="gid1"/>'+
				'<td><input name="bdate1" readonly="readonly" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+year+'-01-01\'  class="Wdate"/></td>'+
				'<td><input name="edate1" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+formatDate(new Date(endDateArray[0]))+'\' class="Wdate" onchange="endDateChange(this)"/></td>'+
			  '</tr>'+html;
		
		html=html+'<tr class="period">'+
					'<td>'+12+'</td>'+
					'<input type="hidden" class="gidHidden" value="'+gid12+'"  name="gid12"/>'+
					'<td><input name="bdate12" readonly="readonly"  type="text" value=\''+formatDate(beginDateArray[10])+'\'  class="Wdate"/></td>'+
					'<td><input name="edate12" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" type="text" value=\''+year+'-12-31\' class="Wdate"/></td>'+
				  '</tr>';
		
				  
		$('tbody').children('.period').remove();
		$('tbody').append(html);
				
	}

</script>


<body>
	<form action="" id="myform" method="post">
	<input type="hidden" id="selectedYear" name="selectedYear" value="${selectedYear }"/>
		<div class="createdList creattable">
			<table>
				<tbody>
					<tr>
						<th style="width: 120px;">期间</th>
						<th>起始日期</th>
						<th>结束日期</th>
					</tr>
					 
				<c:choose>
					<c:when test="${empty pds }">
					
						<c:forEach items="${dateString }"  varStatus="vs" begin="0" end="11" step="1">
							<tr class="period">
								<c:set var="str" value="0${vs.count}"></c:set> 
								<td>${vs.count }</td>
								<c:choose>
									<c:when test="${vs.count==1 }">
										<td><input type="text"  name="bdate${vs.count }" readonly="readonly" value="${currentYear }-${fn:length(str)==3?fn:substring(str,1,3):str }-01" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate"/></td>
										<td><input type="text"  name="edate${vs.count }" value="${dateString[vs.index] }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" onchange="endDateChange(this)"/></td>
									</c:when>
									
									<c:otherwise>
										<td><input type="text" name="bdate${vs.count }" readonly="readonly" value="${currentYear }-${fn:length(str)==3?fn:substring(str,1,3):str }-01"  class="Wdate"/></td>
										<td><input type="text" name="edate${vs.count }" readonly="readonly" value="${dateString[vs.index] }"  class="Wdate" onchange="endDateChange(this)"/></td>
									</c:otherwise>
															
								</c:choose>
		
							</tr>				
						</c:forEach>					
					
					</c:when>
					
					<c:otherwise>
						
						<c:forEach items="${pds }"  varStatus="vs" begin="0" end="11" step="1">
							<tr class="period">
								<c:set var="str" value="0${vs.count}"></c:set> 
								<td>${vs.count }</td>
								<c:choose>
									<c:when test="${vs.count==1 }">
										<input type="hidden" class="gidHidden" value="${ pds[vs.index].gid}" id="gid${vs.count }" name="gid${vs.count }"/>
										<td><input type="text"  name="bdate${vs.count }" readonly="readonly" value="${fn:substring(pds[vs.index].begintime,0,10)}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate"/></td>
										<td><input type="text"  name="edate${vs.count }" value="${fn:substring(pds[vs.index].endtime,0,10)  }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" onchange="endDateChange(this)"/></td>
									</c:when>
									
									<c:otherwise>
										<input type="hidden" class="gidHidden" value="${ pds[vs.index].gid}" id="gid${vs.count }" name="gid${vs.count }"/>
										<td><input type="text" name="bdate${vs.count }" readonly="readonly" value="${fn:substring(pds[vs.index].begintime,0,10)  }"  class="Wdate"/></td>
										<td><input type="text" name="edate${vs.count }" readonly="readonly" value="${fn:substring(pds[vs.index].endtime,0,10)   }"  class="Wdate" onchange="endDateChange(this)"/></td>
									</c:otherwise>
															
								</c:choose>
		
							</tr>				
						</c:forEach>	
					
					</c:otherwise>
					
				</c:choose>
				 			 				 
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>