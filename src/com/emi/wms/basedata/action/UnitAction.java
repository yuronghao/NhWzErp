package com.emi.wms.basedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;

public class UnitAction extends BaseAction {

	private static final long serialVersionUID = -7096047009988156782L;
	private BasicSettingService basicSettingService;
	
	private CacheCtrlService cacheCtrlService;

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	
	
	/**
	 *@category 获得计量单位
	 *2015 2015年12月22日下午2:23:34
	 *String
	 *宋银海
	 */
	public String getUnit(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<Unit> units=basicSettingService.getUnit(orgId, sobId);
		setRequstAttribute("units", units);
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getUnit";
	}
	
	
	/**
	 *@category 获得计量单位（帮助）
	 *2015 2015年12月22日下午2:23:34
	 *String
	 *宋银海
	 */
	public String getUnitHelp(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<Unit> units=basicSettingService.getUnit(orgId, sobId);
		setRequstAttribute("units", units);
		return "getUnitHelp";
	}
	
	
	
	/**
	 *@category 增加单位页
	 *2015 2015年12月22日下午2:45:50
	 *String
	 *宋银海
	 */
	public String toAddUnit(){
		return "toAddUnit";
	}
	
	/**
	 * @category 增加单位
	 *2015 2015年12月22日下午3:03:58
	 *String
	 *宋银海
	 */
	public void addUnit(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			Unit unit=new Unit();
			unit.setGid(UUID.randomUUID().toString());
			unit.setUnitcode(getParameter("unitCode"));
			unit.setUnitname(getParameter("unitName"));
			unit.setSobid(sobId);
			unit.setOrgid(orgId);
			unit.setIsDel(0);
			
			boolean ok=basicSettingService.addUnit(unit);
			
			if(ok){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("添加失败");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().print("添加失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 *@category 增加单位页
	 *2015 2015年12月22日下午2:45:50
	 *String
	 *宋银海
	 */
	public String toUptUnit(){
		String gid=getParameter("gid");//计量单位gid
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		Unit unit=basicSettingService.getUnit(orgId, sobId, gid);
		setRequstAttribute("unit", unit);
		
		
		return "toUptUnit";
	}
	
	
	/**
	 * @category 修改单位
	 *2015 2015年12月23日上午8:39:37
	 *void
	 *宋银海
	 */
	public void uptUnit(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			Unit unit=new Unit();
			unit.setGid(getParameter("unitGid"));
			unit.setUnitcode(getParameter("unitCode"));
			unit.setUnitname(getParameter("unitName"));
			unit.setSobid(sobId);
			unit.setOrgid(orgId);
			
			boolean ok=basicSettingService.uptUnit(unit);
			
			if(ok){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("添加失败");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().print("添加失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 删除计量单位
	 *2015 2015年12月23日上午9:06:03
	 *void
	 *宋银海
	 */
	public void deleteUnit(){
		try {
			String[] unitGids = getRequest().getParameterValues("strsum");
			boolean ok=basicSettingService.deleteUnit(unitGids);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * @category 计量单位换算页
	 *2015 2015年12月23日上午11:06:56
	 *String
	 *宋银海
	 */
	public String getUnitConversion(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<Map> maps=basicSettingService.getUnitConversion(orgId, sobId);
		setRequstAttribute("maps", maps);
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getUnitConversion";
	}
	
	
	/**
	 * @category 计量单位换算页（帮助）
	 *2015 2015年12月23日上午11:06:56
	 *String
	 *宋银海
	 */
	public String getUnitConversionHelp(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<Map> maps=basicSettingService.getUnitConversion(orgId, sobId);
		setRequstAttribute("maps", maps);
		
		return "getUnitConversionHelp";
	}
	
	
	/**
	 * @category 增加计量单位组页面
	 *2015 2015年12月23日下午1:15:08
	 *String
	 *宋银海
	 */
	public String toAddUnitConversion(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<Unit> units=basicSettingService.getUnit(orgId, sobId);
		setRequstAttribute("units", units);
		
		return "toAddUnitConversion";
	}
	

	/**
	 * @category 增加计量单位组
	 *2015 2015年12月23日下午1:37:26
	 *String
	 *宋银海
	 */
	public void addUnitConversion(){
		
		try{
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			Unitconversion unitconversion=new Unitconversion();
			
			unitconversion.setGid(UUID.randomUUID().toString());
			unitconversion.setSobid(sobId);
			unitconversion.setOrgid(orgId);
			unitconversion.setUnitofmeasurement(Integer.valueOf(getParameter("unitofmeasurement")));
			unitconversion.setMainunit(getParameter("mainunit"));
			unitconversion.setMasterquantity(new BigDecimal(1));
			unitconversion.setAuxiliaryunit(getParameter("auxiliaryunit"));
			unitconversion.setAuxiliaryquantity(new BigDecimal(getParameter("auxiliaryquantity")) );
			unitconversion.setUnitGroupName(getParameter("unitGroupName"));
			unitconversion.setIsDel(0);
			
			boolean ok=basicSettingService.addUnitConversion(unitconversion);
			
			if(ok){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("添加失败");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().print("添加失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * @category 增加计量单位组页面
	 *2015 2015年12月23日下午1:15:08
	 *String
	 *宋银海
	 */
	public String toUptUnitConversion(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		String gid=getParameter("gid");
		Map map=basicSettingService.getUnitConversion(orgId, sobId, gid);
		
		List<Unit> units=basicSettingService.getUnit(orgId, sobId);
		
		setRequstAttribute("map", map);
		setRequstAttribute("units", units);
		
		return "toUptUnitConversion";
	}
	
	
	/**
	 * @category 增加计量单位组页面
	 *2015 2015年12月23日下午1:15:08
	 *String
	 *宋银海
	 */
	public void uptUnitConversion(){
		
		try{
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			Unitconversion unitconversion=new Unitconversion();
			
			unitconversion.setGid(getParameter("gid"));
			unitconversion.setSobid(sobId);
			unitconversion.setOrgid(orgId);
			unitconversion.setUnitofmeasurement(Integer.valueOf(getParameter("unitofmeasurement")));
			unitconversion.setMainunit(getParameter("mainunit"));
			unitconversion.setMasterquantity(new BigDecimal(1));
			unitconversion.setAuxiliaryunit(getParameter("auxiliaryunit"));
			unitconversion.setAuxiliaryquantity(new BigDecimal(getParameter("auxiliaryquantity")) );
			unitconversion.setUnitGroupName(getParameter("unitGroupName"));
			
			boolean ok=basicSettingService.uptUnitConversion(unitconversion);
			
			if(ok){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("添加失败");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().print("添加失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 删除计量单位组
	 *2015 2015年12月23日下午4:50:40
	 *void
	 *宋银海
	 */
	public void deleteUnitConversion(){
		try {
			String[] unitGids = getRequest().getParameterValues("strsum");
			boolean ok=basicSettingService.deleteUnitConversion(unitGids);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
