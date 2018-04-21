package com.emi.wms.servicedata.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.bean.wmCall;
import com.emi.wms.servicedata.service.AllocationService;
import com.emi.wms.servicedata.service.SaleService;

public class AllocationAction extends BaseAction {
private AllocationService allocationService;
private CacheCtrlService cacheCtrlService;

public AllocationService getAllocationService() {
	return allocationService;
}
public void setAllocationService(AllocationService allocationService) {
	this.allocationService = allocationService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

/**
 * 
 * @category 展位修改界面
 * 2015年3月22日 上午8:18:30
 * @author 杨峥铖
 * @return
 */
public String getAllocationList(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String keyWord = getParameter("keyWord");//搜索关键字
	String condition = CommonUtil.combQuerySql("wmcall.billCode", keyWord);
	setRequstAttribute("keyWord",keyWord);
	String orgId=getSession().get("OrgId").toString();
	String sobId=getSession().get("SobId").toString();
	condition+=" and wmcall.sobGid='"+sobId+"' and wmcall.orgGid='"+orgId+"'";
	PageBean allocationlist = allocationService.getallocationlist(pageIndex,pageSize,condition);
	for(int i=0;i<allocationlist.getList().size();i++){
		if(!CommonUtil.isNullObject(((Map)allocationlist.getList().get(i)).get("goodsUid"))){
			AaGoods good = cacheCtrlService.getGoods(((Map)allocationlist.getList().get(i)).get("goodsUid").toString());
			((Map)allocationlist.getList().get(i)).put("good", good);
		}
		if(!CommonUtil.isNullObject(((Map)allocationlist.getList().get(i)).get("outWhUid"))){
			AaWarehouse aawarehouseout = cacheCtrlService.getWareHouse(((Map)allocationlist.getList().get(i)).get("outWhUid").toString());
			((Map)allocationlist.getList().get(i)).put("aawarehouseout", aawarehouseout);
				}
		if(!CommonUtil.isNullObject(((Map)allocationlist.getList().get(i)).get("inWhUid"))){
			AaWarehouse aawarehousein = cacheCtrlService.getWareHouse(((Map)allocationlist.getList().get(i)).get("inWhUid").toString());
			((Map)allocationlist.getList().get(i)).put("aawarehousein", aawarehousein);
		}
		if(!CommonUtil.isNullObject(((Map)allocationlist.getList().get(i)).get("outgoodsAllocationUid"))){
			AaGoodsallocation aagoodsallocationout= cacheCtrlService.getGoodsAllocation(((Map)allocationlist.getList().get(i)).get("outgoodsAllocationUid").toString());
			((Map)allocationlist.getList().get(i)).put("aagoodsallocationout", aagoodsallocationout);
		}
		if(!CommonUtil.isNullObject(((Map)allocationlist.getList().get(i)).get("ingoodsAllocationUid"))){
			AaGoodsallocation aagoodsallocationin= cacheCtrlService.getGoodsAllocation(((Map)allocationlist.getList().get(i)).get("ingoodsAllocationUid").toString());
			((Map)allocationlist.getList().get(i)).put("aagoodsallocationin", aagoodsallocationin);
		}
	}
	List columns = allocationService.getcolumns();
	setRequstAttribute("columns", columns);
	setRequstAttribute("data", allocationlist);
	return "allocationlist";
}

}
