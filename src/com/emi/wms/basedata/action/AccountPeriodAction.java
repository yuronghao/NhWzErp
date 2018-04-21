package com.emi.wms.basedata.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.sf.json.JSONObject;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.Accountperiod;

public class AccountPeriodAction extends BaseAction {

	private static final long serialVersionUID = -7096047009988156782L;
	private BasicSettingService basicSettingService;

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}


	/**
	 * @category 进入账期页
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getAccountPeriod(){
		return "getAccountPeriod";
	}
	
	
	/**
	 * @category 右击 新建下一年度日历 按钮
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String toAddAccountPeriod(){
		
		Map values=new HashMap();
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		values.put("orgId", orgId);
		values.put("sobId", sobId);
		
		int year=basicSettingService.getMaxAccountPeriodYear(values);
		if(year==0){
			int currentYear=DateUtil.getToYear();
			String[] dateString=DateUtil.getLastDayOfMonth(currentYear, "yyyy-MM-dd");
			
			setRequstAttribute("currentYear", currentYear);
			setRequstAttribute("dateString", dateString);
			setRequstAttribute("pds", null);
			
		}else{
			int currentYear=year+1;
			String[] dateString=DateUtil.getLastDayOfMonth(currentYear, "yyyy-MM-dd");
			
			setRequstAttribute("currentYear", currentYear);
			setRequstAttribute("dateString", dateString);
			setRequstAttribute("pds", null);
		}
		
		return "getRightPeriod";
	}
	
	
	/**
	 * @category 进入账期页，查询当前右边的账期
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getRightPeriod(){
		
		Map values=new HashMap();
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		values.put("orgId", orgId);
		values.put("sobId", sobId);
		
		int year=basicSettingService.getMaxAccountPeriodYear(values);
		
		if(year!=0){
			
			int currentYear=DateUtil.getToYear();
			String condition=" and sobId='"+sobId+"' and orgId='"+orgId+"' and apyear='"+currentYear+"'";
			List<Accountperiod> pds=basicSettingService.getAccountperiod(condition);
			
			setRequstAttribute("pds", pds);
		}
		
		return "getRightPeriod";
	}
	
	
	/**
	 * @category 单机年度时刷新右边列表
	 *2015 2015年12月16日下午5:25:37
	 *String
	 *宋银海
	 */
	public String getRightPeriodByClick(){
		
		Map values=new HashMap();
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		values.put("orgId", orgId);
		values.put("sobId", sobId);
		
		String iyear=getParameter("iyear");
		int year=Integer.parseInt(iyear);
		
		String condition=" and sobId='"+sobId+"' and orgId='"+orgId+"' and apyear='"+year+"'";
		List<Accountperiod> pds=basicSettingService.getAccountperiod(condition);
		
		setRequstAttribute("selectedYear", year);
		setRequstAttribute("pds", pds);
		
		return "getRightPeriod";
	}
	

	/**
	 * @category 查询左边的账期树
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getAccountPeriodTree(){
		
		Map values=new HashMap();
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		values.put("orgId", orgId);
		values.put("sobId", sobId);
		
		List<Map> accountPeriodTree=basicSettingService.getAccountPeriodTree(values);
		setRequstAttribute("accountPeriodTree", EmiJsonArray.fromObject(accountPeriodTree).toString());
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getAccountPeriodTree";
	}
	
	
	/**
	 * @category 添加账期
	 *2015 2015年12月16日上午8:43:30
	 *void
	 *宋银海
	 */
	public void addAccountPeriod(){
		try{
			Map values=new HashMap();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			values.put("orgId", orgId);
			values.put("sobId", sobId);
			
			int year=basicSettingService.getMaxAccountPeriodYear(values);
			
			if(year==0){
				year=DateUtil.getToYear();
			}else{
				year++;
			}
			
			List<Accountperiod> period=new ArrayList<Accountperiod>();
			
			for(int i=1;i<=12;i++){
				Accountperiod p=new Accountperiod();
				p.setGid(UUID.randomUUID().toString());
				p.setApyear(year);
				p.setAp(i);
				p.setBegintime(new Timestamp(DateUtil.stringtoDate(getRequest().getParameter("bdate"+i), "yyyy-MM-dd").getTime()) );
				p.setEndtime(new Timestamp(DateUtil.stringtoDate(getRequest().getParameter("edate"+i), "yyyy-MM-dd").getTime()) );
				p.setSobid(sobId);
				p.setOrgid(orgId);
				
				period.add(p);
			}
			
			boolean ok=basicSettingService.saveAccountperiod(period);//保存
			
			if(ok){
				writeSuccess();
			}else{
				writeError();
			}
			
		}catch(Exception e){
			writeError();
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @category 添加账期
	 *2015 2015年12月16日上午8:43:30
	 *void
	 *宋银海
	 */
	public void uptAccountPeriod(){
		try{
			Map values=new HashMap();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			values.put("orgId", orgId);
			values.put("sobId", sobId);
			
			String iyear=getParameter("selectedYear");
			int year=Integer.parseInt(iyear);
			
			List<Accountperiod> period=new ArrayList<Accountperiod>();
			
			for(int i=1;i<=12;i++){
				Accountperiod p=new Accountperiod();
				p.setGid(getRequest().getParameter("gid"+i));
				p.setApyear(year);
				p.setAp(i);
				p.setBegintime(new Timestamp(DateUtil.stringtoDate(getRequest().getParameter("bdate"+i), "yyyy-MM-dd").getTime()) );
				p.setEndtime(new Timestamp(DateUtil.stringtoDate(getRequest().getParameter("edate"+i), "yyyy-MM-dd").getTime()) );
				p.setSobid(sobId);
				p.setOrgid(orgId);
				
				period.add(p);
			}
			
			boolean ok=basicSettingService.uptAccountperiod(period);//保存
			
			if(ok){
				writeSuccess();
			}else{
				writeError();
			}
			
		}catch(Exception e){
			writeError();
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *@category 删除账期
	 *2015 2015年12月16日上午8:43:30
	 *void
	 *宋银海
	 */
	public void deleteAccountPeriod(){
		try{
			String iyear=getParameter("iyear");
			int year=Integer.parseInt(iyear);
			
			Map values=new HashMap();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			values.put("orgId", orgId);
			values.put("sobId", sobId);
			values.put("year", year);
			
			boolean ok=basicSettingService.deleteAccountperiod(values);
			
			JSONObject jobj=new JSONObject();
			
			if(ok){
				writeSuccess();
			}else{
				writeError();
			}
			
		}catch(Exception e){
			writeError();
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	

}
