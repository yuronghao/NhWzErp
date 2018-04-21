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
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.WmPurchaserequisition;
import com.emi.wms.bean.WmPurchaserequisitionC;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.service.RequisitionService;
import com.emi.wms.servicedata.service.SaleService;

public class RequisitionAction extends BaseAction {
private RequisitionService requisitionService;
private CacheCtrlService cacheCtrlService;

public RequisitionService getrequisitionService() {
	return requisitionService;
}
public void setrequisitionService(RequisitionService requisitionService) {
	this.requisitionService = requisitionService;
}

public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}
public String toAddrequisition(){
	String requisitiongid = getParameter("requisitiongid");
	Map requisition = requisitionService.findrequisition(requisitiongid);
	List requisitionc = requisitionService.getrequisitionlist(requisition.get("purchasegid").toString());
	for(int i=0;i<requisitionc.size();i++){
		AaGoods good = cacheCtrlService.getGoods(((Map)requisitionc.get(i)).get("goodsUid").toString());
		((Map)requisitionc.get(i)).put("good", good);
	}
	setRequstAttribute("requisition", requisition);
	setRequstAttribute("requisitionc", requisitionc);
	return "requisitionAdd";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void requisitionEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		requisitionService.setrequisitionEnable(enable,id);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void addrequisition(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmPurchaserequisition requisition = new WmPurchaserequisition();
			String uuid = UUID.randomUUID().toString();
			requisition.setGid(uuid);
			requisition.setBillcode(getParameter("billCode"));
			requisition.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			requisition.setDepartmentuid(getParameter("depName"));
			requisition.setSalesmanuid(getParameter("perName"));
			requisition.setPurchasetype(new Integer(getParameter("purchasetype")));
			requisition.setNotes(getParameter("notes"));
			requisition.setRecordpersonuid(getParameter("recordPersonUid"));
			requisition.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//requisition.setAuditpersonuid(getParameter("auditPersonUid"));
			//requisition.setAudittime(getParameter("audittime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("audittime"), "yyyy-MM-dd").getTime()):null);
			requisition.setSobgid(getSession().get("SobId").toString());
			requisition.setOrggid(getSession().get("OrgId").toString());
			requisition.setFlag(0);
			
			List<WmPurchaserequisitionC> requisitioncs = new ArrayList<WmPurchaserequisitionC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			WmPurchaserequisitionC requisitionc = new WmPurchaserequisitionC();
			requisitionc.setPurchaserequisitionuid(uuid);
			requisitionc.setGoodsuid(goodsUid[i]);
			requisitionc.setNumber(new BigDecimal(number[i]));
			requisitionc.setAmount(new BigDecimal(amount[i]));
			requisitionc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);
			requisitioncs.add(requisitionc);
			}
			}
			
			boolean suc = requisitionService.addrequisition(requisition);
			boolean suc1 = requisitionService.addrequisitionc(requisitioncs);
			if(suc&&suc1){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("保存失败");
			}
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public String orgSelect(){
	List classify = requisitionService.getClassifyList("");
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	classify.add(m);
	getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	return "orgSelect";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void updaterequisition(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmPurchaserequisition requisition = new WmPurchaserequisition();
			requisition.setGid(getParameter("purchasegid"));
			requisition.setBillcode(getParameter("billCode"));
			requisition.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			requisition.setDepartmentuid(getParameter("depName"));
			requisition.setSalesmanuid(getParameter("perName"));
			requisition.setPurchasetype(new Integer(getParameter("purchasetype")));
			requisition.setNotes(getParameter("notes"));
			requisition.setRecordpersonuid(getParameter("recordPersonUid"));
			requisition.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//requisition.setAuditpersonuid(getParameter("auditPersonUid"));
			//requisition.setAudittime(getParameter("audittime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("audittime"), "yyyy-MM-dd").getTime()):null);
			requisition.setSobgid(getSession().get("SobId").toString());
			requisition.setOrggid(getSession().get("OrgId").toString());
			requisition.setFlag(0);
			
			List<WmPurchaserequisitionC> requisitioncs = new ArrayList<WmPurchaserequisitionC>();
			//requisitionService.deletebooks(getParameter("purchasegid"));
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			WmPurchaserequisitionC requisitionc = new WmPurchaserequisitionC();
			requisitionc.setGid(gid[i]);
			requisitionc.setPurchaserequisitionuid(getParameter("purchasegid"));
			requisitionc.setGoodsuid(goodsUid[i]);
			requisitionc.setNumber(new BigDecimal(number[i]));
			requisitionc.setAmount(new BigDecimal(amount[i]));
			requisitionc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);
			requisitioncs.add(requisitionc);
			}
			}
			
			boolean suc = requisitionService.updaterequisition(requisition);
			boolean suc1 = requisitionService.updaterequisitionc(requisitioncs);
			if(suc&&suc1){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("保存失败");
			}
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 展位修改界面
 * 2015年3月22日 上午8:18:30
 * @author 杨峥铖
 * @return
 */
public String getrequisitionlist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String condition="";
	//PageBean requisitionlist = requisitionService.getrequisitionlist(pageIndex,pageSize,condition);
	//setRequstAttribute("data", requisitionlist);
	return "requisitionlist";
}
}
