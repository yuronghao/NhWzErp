package com.emi.wms.basedata.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;

public class OrgAction extends BaseAction {

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
	 * @category 部门列表
	 *2015 2015年12月30日上午8:33:05
	 *String
	 *宋银海
	 */
	public String getDepartment(){
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getDepartment";
	}
	
	
	/**
	 * @category 部门树
	 *2015 2015年12月30日上午8:45:54
	 *String
	 *宋银海
	 */
	public String getDepartmentTree(){
		
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
		List<Map> departmentTree=basicSettingService.getDepartmentTree(orgId, sobId);
		setRequstAttribute("departmentTree", JSONArray.fromObject(departmentTree).toString());
		
		return "getDepartmentTree";
	}
	
	
	/**
	 * 
	 *2015 2015年12月30日下午3:59:33
	 *String
	 *Administrator
	 */
	public String getDepartmentTreeHelp(){
		
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
		List<Map> departmentTree=basicSettingService.getDepartmentTree(orgId, sobId);
		setRequstAttribute("departmentTree", JSONArray.fromObject(departmentTree).toString());
		
		return "getDepartmentTreeHelp";
	}
	
	
	/**
	 * @category 部门详情
	 *2015 2015年12月30日上午8:47:37
	 *String
	 *宋银海
	 */
	public String getDepartmentDetail(){
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
		String gid=getParameter("id");
		AaDepartment aaDepartment=null;
		if(!CommonUtil.isNullObject(gid)){
			aaDepartment=basicSettingService.getDepartmentDetail(orgId, sobId, gid);
		}
		
		setRequstAttribute("aaDepartment", aaDepartment);
		return "getDepartmentDetail";
	}
	
	
	/**
	 * @category 跳转到增加部门页
	 *2015 2015年12月30日上午10:01:14
	 *String
	 *宋银海
	 */
	public String toAddDepartment(){
		
		return "toAddDepartment";
	}
	
	
	/**
	 * @category 添加部门
	 *2015 2015年12月30日上午10:12:54
	 *void
	 *宋银海
	 */
	public void addDepartment(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id

			AaDepartment dpt=new AaDepartment();
			dpt.setGid(UUID.randomUUID().toString());
			dpt.setDepcode(getParameter("depcode"));
			dpt.setDepname(getParameter("depname"));
			dpt.setDepparentuid(CommonUtil.isNullObject(getParameter("depparentuid"))?null:getParameter("depparentuid"));
			dpt.setNotes(CommonUtil.isNullObject(getParameter("notes"))?"":getParameter("notes"));
			dpt.setOrggid(orgId);
			dpt.setSobgid(sobId);
			dpt.setIsdel(0);
			dpt.setIsWorkshop(1);
			
			boolean ok=basicSettingService.addDepartment(dpt);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 跳转到修改部门页
	 *2015 2015年12月30日下午1:43:50
	 *String
	 *宋银海
	 */
	public String toEditDepartment(){
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		String gid=getParameter("gid");
		
		AaDepartment aaDepartment=basicSettingService.getDepartmentDetail(orgId, sobId, gid);
		setRequstAttribute("aaDepartment", aaDepartment);
		return "toEditDepartment";
	}
	
	
	/**
	 * @category 修改部门
	 *2015 2015年12月30日下午2:30:01
	 *void
	 *宋银海
	 */
	public void editDepartment(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id

			AaDepartment dpt=new AaDepartment();
			dpt.setGid(getParameter("depGid"));
			dpt.setDepcode(getParameter("depcode"));
			dpt.setDepname(getParameter("depname"));
			dpt.setDepparentuid(CommonUtil.isNullObject(getParameter("depparentuid"))?null:getParameter("depparentuid"));
			dpt.setNotes(CommonUtil.isNullObject(getParameter("notes"))?"":getParameter("notes"));
			dpt.setOrggid(orgId);
			dpt.setSobgid(sobId);
			dpt.setIsdel(0);
			dpt.setIsWorkshop(new Integer(getParameter("isworkshop")));
			
			boolean ok=basicSettingService.editDepartment(dpt);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 删除部门
	 *2015 2015年12月30日下午3:10:14
	 *void
	 *宋银海
	 */
	public void deleteDepartment(){
		try {
			String gid = getParameter("gid");
			boolean ok=basicSettingService.deleteDepartment(gid);
			
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
	 * @category 获得人员
	 *2015 2015年12月31日上午8:30:17
	 *String
	 *宋银海
	 */
	public String getPerson(){
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getPerson";
	}
	
	
	/**
	 * @category 获得人员树右边的列表
	 *2015 2015年12月31日上午8:32:02
	 *String
	 *宋银海
	 */
	public String getPersonList(){
		
		int pageIndex = getPageIndex();	
		int pageSize = getPageSize();
		
		String classifyGid=getParameter("id");
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		PageBean pageBean=basicSettingService.getPersonPageBean(pageIndex,pageSize,classifyGid,orgId,sobId);
		setRequstAttribute("data", pageBean);
		
		return "getPersonList";
	}
	
	
	/**
	 * @category 跳转到增加人员页面
	 *2015 2015年12月31日上午9:10:56
	 *String
	 *宋银海
	 */
	public String toAddPerson(){
		return "toAddPerson";
	}
	
	
	/**
	 * @category 增加人员
	 *2015 2015年12月31日上午9:46:26
	 *void
	 *宋银海
	 */
	public void addAaPerson(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id

			AaPerson aaPerson=new AaPerson();
			aaPerson.setGid(UUID.randomUUID().toString());
			aaPerson.setPercode(getParameter("percode") );
			aaPerson.setPername(getParameter("pername") );
			aaPerson.setPersex(Integer.parseInt(getParameter("persex")) );
			aaPerson.setDepGid(getParameter("depparentuid"));
			aaPerson.setPerbirthday(CommonUtil.isNullObject(getParameter("perbirthday"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("perbirthday"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setBegindate(CommonUtil.isNullObject(getParameter("begindate"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("begindate"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setEnddate(CommonUtil.isNullObject(getParameter("enddate"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("enddate"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setUseruid(CommonUtil.isNullObject(getParameter("useruid"))?null:getParameter("useruid") );
			aaPerson.setNotes(CommonUtil.isNullObject(getParameter("notes"))?null:getParameter("notes") );
			aaPerson.setOrggid(orgId);
			aaPerson.setSobgid(sobId);
			
			boolean ok=basicSettingService.addPerson(aaPerson);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 跳转到修改人员页
	 *2015 2015年12月31日下午4:18:55
	 *String
	 *宋银海
	 */
	public String toEditPerson(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		String gid=getParameter("gid");
		
		Map map=basicSettingService.getPersonMap(gid, orgId, sobId);
		setRequstAttribute("map", map);
		
		return "toEditPerson";
	}
	
	
	/**
	 * @category 修改人员
	 *2015 2015年12月31日下午5:02:34
	 *void
	 *宋银海
	 */
	public void editAaPerson(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id

			AaPerson aaPerson=new AaPerson();
			aaPerson.setGid(getParameter("pergid"));
			aaPerson.setPercode(getParameter("percode") );
			aaPerson.setPername(getParameter("pername") );
			aaPerson.setPersex(Integer.parseInt(getParameter("persex")) );
			aaPerson.setDepGid(getParameter("depparentuid"));
			aaPerson.setPerbirthday(CommonUtil.isNullObject(getParameter("perbirthday"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("perbirthday"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setBegindate(CommonUtil.isNullObject(getParameter("begindate"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("begindate"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setEnddate(CommonUtil.isNullObject(getParameter("enddate"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("enddate"), DateUtil.LONG_DATE_FORMAT).getTime()) );
			aaPerson.setUseruid(CommonUtil.isNullObject(getParameter("useruid"))?null:getParameter("useruid") );
			aaPerson.setNotes(CommonUtil.isNullObject(getParameter("notes"))?null:getParameter("notes") );
			aaPerson.setOrggid(orgId);
			aaPerson.setSobgid(sobId);
			
			boolean ok=basicSettingService.uptPerson(aaPerson);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 删除人员
	 *2016 2016年1月4日上午10:38:55
	 *void
	 *宋银海
	 */
	public void deletePerson(){
		try {
			String gid = getParameter("gid");
			boolean ok=basicSettingService.deletePerson(gid);
			
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
