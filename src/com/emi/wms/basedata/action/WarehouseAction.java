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

import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;

public class WarehouseAction extends BaseAction {

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
	 * @category 获得仓库
	 *2015 2015年12月25日下午2:35:01
	 *String
	 *宋银海
	 */
	public String getWarehouse(){
		int pageIndex = getPageIndex();	
		int pageSize = getPageSize();
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		PageBean aaWarehouses=basicSettingService.getWarehouse(pageIndex,pageSize,orgId, sobId);
		setRequstAttribute("data", aaWarehouses);
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getWarehouseList";
	}
	
	
	/**
	 * @category 获得仓库帮助
	 *2015 2015年12月25日下午2:35:01
	 *String
	 *宋银海
	 */
	public String getWarehouseHelp(){
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		List<AaWarehouse> aaWarehouses=basicSettingService.getWarehouse(orgId, sobId);
		setRequstAttribute("aaWarehouses", aaWarehouses);
		return "getWarehouseHelp";
	}
	
	
	
	/**
	 * @category 增加仓库页
	 *2015 2015年12月25日下午4:00:05
	 *String
	 *宋银海
	 */
	public String toAddWarehouse(){
		return "toAddWarehouse";
	}
	

	/**
	 * @category 添加仓库
	 *2015 2015年12月25日下午4:07:03
	 *void
	 *宋银海
	 */
	public void addWarehouse(){
		try{
			
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			
			AaWarehouse aaWarehouse=new AaWarehouse();
			aaWarehouse.setGid(UUID.randomUUID().toString());
			aaWarehouse.setNotes(getParameter("notes") );
			aaWarehouse.setWhcode(getParameter("whcode") );
			aaWarehouse.setWhname(getParameter("whname") );
			aaWarehouse.setWhaddr(getParameter("whaddr") );
			aaWarehouse.setWhtel(getParameter("whtel") );
			aaWarehouse.setLinkman(getParameter("linkman") );
			aaWarehouse.setLongitude(CommonUtil.isNullObject(getParameter("longitude"))?new Double("0"):Double.parseDouble(getParameter("longitude")) );
			aaWarehouse.setLatitude(CommonUtil.isNullObject(getParameter("latitude"))?new Double("0"):Double.parseDouble(getParameter("latitude")) );
			aaWarehouse.setOrggid(orgId);
			aaWarehouse.setSobgid(sobId);
			//aaWarehouse.setBegintimes(new Timestamp(DateUtil.stringtoDate(getParameter("begintimes"), DateUtil.FORMAT_ONE).getTime()) );
			//aaWarehouse.setEndtimes(CommonUtil.isNullObject(getParameter("endtimes"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("endtimes"), DateUtil.FORMAT_ONE).getTime()));
			aaWarehouse.setBarcode(getParameter("barcode") );
			aaWarehouse.setWhpos(Integer.parseInt(getParameter("whpos")));
			aaWarehouse.setIsDel(0);
			
			boolean ok=basicSettingService.addWarehouse(aaWarehouse);
			
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
	 * @category 修改仓库页
	 *2015 2015年12月28日上午10:46:52
	 *String
	 *宋银海
	 */
	public String toUptWarehouse(){
		String gid=getParameter("gid");//计量单位gid
		
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		AaWarehouse aaWarehouses=basicSettingService.getWarehouse(orgId, sobId, gid);
		setRequstAttribute("aaWarehouses", aaWarehouses);
		
		return "toUptWarehouse";
	}
	
	
	/**
	 * @category 修改仓库
	 *2015 2015年12月28日上午11:15:16
	 *void
	 *宋银海
	 */
	public void uptWarehouse(){
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			AaWarehouse aaWarehouse=new AaWarehouse();
			aaWarehouse.setGid(getParameter("whGid"));
			aaWarehouse.setNotes(getParameter("notes") );
			aaWarehouse.setWhcode(getParameter("whcode") );
			aaWarehouse.setWhname(getParameter("whname") );
			aaWarehouse.setWhaddr(getParameter("whaddr") );
			aaWarehouse.setWhtel(getParameter("whtel") );
			aaWarehouse.setLinkman(getParameter("linkman") );
			aaWarehouse.setLongitude(CommonUtil.isNullObject(getParameter("longitude"))?new Double("0"):Double.parseDouble(getParameter("longitude")) );
			aaWarehouse.setLatitude(CommonUtil.isNullObject(getParameter("latitude"))?new Double("0"):Double.parseDouble(getParameter("latitude")) );
			aaWarehouse.setOrggid(orgId);
			aaWarehouse.setSobgid(sobId);
			//aaWarehouse.setBegintimes(new Timestamp(DateUtil.stringtoDate(getParameter("begintimes"), DateUtil.FORMAT_ONE).getTime()) );
			//aaWarehouse.setEndtimes(CommonUtil.isNullObject(getParameter("endtimes"))?null:new Timestamp(DateUtil.stringtoDate(getParameter("endtimes"), DateUtil.FORMAT_ONE).getTime()));
			aaWarehouse.setBarcode(getParameter("barcode") );
			aaWarehouse.setWhpos(Integer.parseInt(getParameter("whpos")));
			
			boolean ok=basicSettingService.uptWarehouse(aaWarehouse);
			
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
	 * @category 删除仓库
	 *2015 2015年12月28日上午11:24:10
	 *void
	 *宋银海
	 */
	public void deleteWarehouse(){
		try {
			String[] unitGids = getRequest().getParameterValues("strsum");
			boolean ok=basicSettingService.deleteWarehouse(unitGids);
			
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
	 * @category 查询货位
	 *2015 2015年12月28日下午1:14:54
	 *String
	 *宋银海
	 */
	public String getGoodsAllocation(){
		
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
		return "getGoodsAllocationList";
	}
	

	/**
	 * @category 查询仓库树
	 *2015 2015年12月28日下午1:32:10
	 *String
	 *宋银海
	 */
	public String getWarehouseTree(){
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
//		List<Map> warehouseTree=basicSettingService.getWarehouseTree(orgId, sobId);
		List<Map> warehouseTree=basicSettingService.getWarehouseTreeOnly(orgId, sobId);
		setRequstAttribute("warehouseTree", EmiJsonArray.fromObject(warehouseTree).toString() );
		return "getWarehouseTree";
	}
	
	/**
	 * @category 查询仓库树(异步,暂不用)
	 */
	public String getWarehouseTree4Async(){
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		
		List<Map> warehouseTree=basicSettingService.getWarehouseTreeOnly(orgId, sobId);
		setRequstAttribute("warehouseTree", EmiJsonArray.fromObject(warehouseTree).toString() );
		return "getWarehouseTree";
	}
	
	/**
	 * @category 查询仓库货位树(异步)
	 */
	public void getAllocationTree4Async(){
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		String whUid = getParameter("id");
		List<Map> warehouseTree=basicSettingService.getAllocationTreeOnly(orgId, sobId,whUid);
		
		responseWrite( EmiJsonArray.fromObject(warehouseTree).toString() );
	}
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @category 查询右边的货位列表
	 *2015 2015年12月28日下午1:54:14
	 *String
	 *宋银海
	 */
	public String getRightGoodsAllocation() throws UnsupportedEncodingException{
		
		String goodsAllocationGid=getParameter("id");
		String warehouseName=URLDecoder.decode(CommonUtil.Obj2String(getParameter("name"))  ,"UTF-8");
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		AaGoodsallocation aaGoodsallocation=null;
		if(!CommonUtil.isNullObject(goodsAllocationGid)){
			aaGoodsallocation=basicSettingService.getGoodsAllocation(goodsAllocationGid, orgId, sobId);
			if(!CommonUtil.isNullObject(aaGoodsallocation)){
				aaGoodsallocation.setWhName(warehouseName);
			}
		}
		 
		setRequstAttribute("aaGoodsallocation", aaGoodsallocation);	
		return "getRightGoodsAllocation";
	}
	
	
	/**
	 * @category 添加货位
	 *2015 2015年12月28日下午5:16:49
	 *void
	 *宋银海
	 */
	public void addGoodsAllocation(){
		
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			AaGoodsallocation aaGoodsallocation=new AaGoodsallocation();
			aaGoodsallocation.setGid(UUID.randomUUID().toString());
			aaGoodsallocation.setCode(getParameter("code"));
			aaGoodsallocation.setName(getParameter("name") );
			aaGoodsallocation.setWhuid(getParameter("whuid"));
			aaGoodsallocation.setAllocationbarcode(getParameter("allocationbarcode") );
			aaGoodsallocation.setIstemp(Integer.parseInt(getParameter("istemp")));
			aaGoodsallocation.setNotes(getParameter("notes"));
			aaGoodsallocation.setOrggid(orgId);
			aaGoodsallocation.setSobgid(sobId);
			aaGoodsallocation.setPosend(1);
			aaGoodsallocation.setIsdel(0);
			
			boolean ok=basicSettingService.addGoodsAllocation(aaGoodsallocation);
			
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
	 * @category 修改货位
	 *2015 2015年12月28日下午5:16:49
	 *void
	 *宋银海
	 */
	public void uptGoodsAllocation(){
		
		try{
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			AaGoodsallocation aaGoodsallocation=new AaGoodsallocation();
			aaGoodsallocation.setGid(getParameter("gid"));
			aaGoodsallocation.setCode(getParameter("code"));
			aaGoodsallocation.setName(getParameter("name") );
			aaGoodsallocation.setWhuid(getParameter("whuid"));
			aaGoodsallocation.setAllocationbarcode(getParameter("allocationbarcode") );
			aaGoodsallocation.setIstemp(Integer.parseInt(getParameter("istemp")));
			aaGoodsallocation.setNotes(getParameter("notes"));
			aaGoodsallocation.setOrggid(orgId);
			aaGoodsallocation.setSobgid(sobId);
			
			boolean ok=basicSettingService.uptGoodsAllocation(aaGoodsallocation);
			
			if(ok){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("修改失败");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().print("修改失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @category 删除货位
	 *2015 2015年12月29日下午3:33:55
	 *void
	 *宋银海
	 */
	public void deleteGoodsAllocation(){
		try {
			String gid = getParameter("gid");
			boolean ok=basicSettingService.deleteWarehouse(gid);
			
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
