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
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.service.CheckBillService;
import com.emi.wms.servicedata.service.SaleService;

public class CheckBillAction extends BaseAction {
private CheckBillService checkbillService;
private CacheCtrlService cacheCtrlService;

public CheckBillService getCheckbillService() {
	return checkbillService;
}
public void setCheckbillService(CheckBillService checkbillService) {
	this.checkbillService = checkbillService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}
public String toAddCheckBill(){
	try {
		String checkbillgid = getParameter("checkbillgid");
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		Map checkbill = checkbillService.findcheckbill(checkbillgid,orgId,sobId);
		if(!CommonUtil.isNullObject(checkbill)&&!CommonUtil.isNullObject(checkbill.get("checkbillgid"))){
			List checkbillc = checkbillService.getcheckbillclist(checkbill.get("checkbillgid").toString());
			for(int i=0;i<checkbillc.size();i++){
				if(checkbill.get("checkTypeCode").toString().equalsIgnoreCase("PCS")
					|| checkbill.get("checkTypeCode").toString().equalsIgnoreCase("r")
						){
					
					MesWmProduceProcessroutec mwpc=checkbillService.getMesWmProduceProcessroutec(((Map)checkbillc.get(i)).get("produceProcessRouteCGid").toString());
					MesWmStandardprocess ms=cacheCtrlService.getMESStandardProcess(mwpc.getOpGid());
					
					((Map)checkbillc.get(i)).put("tbarcode", mwpc.getBarcode());
					((Map)checkbillc.get(i)).put("opname", ms.getOpname());
					
				}else{
					AaGoods good = cacheCtrlService.getGoods(((Map)checkbillc.get(i)).get("goodsUid").toString());
					((Map)checkbillc.get(i)).put("good", good);
				}
				
			}
			setRequstAttribute("checkbillc", checkbillc);
		}
		setRequstAttribute("checkbill", checkbill);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "checkbillAdd";
	}

public String lookcheckbillreason(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	PageBean reasonlist = checkbillService.getreasonlist(pageIndex,pageSize,getParameter("checkbillcgid"));
	setRequstAttribute("data", reasonlist);
	setRequstAttribute("checkcGid", getParameter("checkbillcgid"));
	return "lookcheckbillreason";
}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void checkbillEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		checkbillService.setcheckbillEnable(enable,id);
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
public void addcheckbill(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			QMCheckBill checkbill = new QMCheckBill();
			String uuid = UUID.randomUUID().toString();
			checkbill.setGid(uuid);
			/*checkbill.setBillcode(getParameter("billCode"));
			checkbill.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			checkbill.setDepartmentuid(getParameter("depName"));
			checkbill.setSalesmanuid(getParameter("perName"));
			checkbill.setPurchasetype(new Integer(getParameter("purchasetype")));
			checkbill.setNotes(getParameter("notes"));
			checkbill.setRecordpersonuid(getParameter("recordPersonUid"));
			checkbill.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			checkbill.setSobgid(getSession().get("SobId").toString());
			checkbill.setOrggid(getSession().get("OrgId").toString());
			checkbill.setFlag(0);*/
			
			List<QMCheckCbill> checkbillcs = new ArrayList<QMCheckCbill>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			QMCheckCbill checkbillc = new QMCheckCbill();
			/*checkbillc.setPurchasecheckbilluid(uuid);
			checkbillc.setGoodsuid(goodsUid[i]);
			checkbillc.setNumber(new BigDecimal(number[i]));
			checkbillc.setAmount(new BigDecimal(amount[i]));
			checkbillc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);*/
			checkbillcs.add(checkbillc);
			}
			}
			
			boolean suc = checkbillService.addcheckbill(checkbill);
			boolean suc1 = checkbillService.addcheckbillc(checkbillcs);
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
	List classify = checkbillService.getClassifyList("");
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
public void updatecheckbill(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			QMCheckBill checkbill = new QMCheckBill();
			checkbill.setGid(getParameter("purchasegid"));
			/*checkbill.setBillcode(getParameter("billCode"));
			checkbill.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			checkbill.setDepartmentuid(getParameter("depName"));
			checkbill.setSalesmanuid(getParameter("perName"));
			checkbill.setPurchasetype(new Integer(getParameter("purchasetype")));
			checkbill.setNotes(getParameter("notes"));
			checkbill.setRecordpersonuid(getParameter("recordPersonUid"));
			checkbill.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			checkbill.setSobgid(getSession().get("SobId").toString());
			checkbill.setOrggid(getSession().get("OrgId").toString());
			checkbill.setFlag(0);*/
			
			List<QMCheckCbill> checkbillcs = new ArrayList<QMCheckCbill>();
			//checkbillService.deletebooks(getParameter("purchasegid"));
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			QMCheckCbill checkbillc = new QMCheckCbill();
			checkbillc.setGid(gid[i]);
			/*checkbillc.setPurchasecheckbilluid(getParameter("purchasegid"));
			checkbillc.setGoodsuid(goodsUid[i]);
			checkbillc.setNumber(new BigDecimal(number[i]));
			checkbillc.setAmount(new BigDecimal(amount[i]));
			checkbillc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);*/
			checkbillcs.add(checkbillc);
			}
			}
			
			boolean suc = checkbillService.updatecheckbill(checkbill);
			boolean suc1 = checkbillService.updatecheckbillc(checkbillcs);
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
public String getcheckbilllist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String keyWord = getParameter("keyWord");//搜索关键字
	String condition = CommonUtil.combQuerySql("checkCode", keyWord);
	setRequstAttribute("keyWord",keyWord);
	String orgId=getSession().get("OrgId").toString();
	String sobId=getSession().get("SobId").toString();
	condition+=" and sobGid='"+sobId+"' and orgGid='"+orgId+"'  ";
	PageBean checkbilllist = checkbillService.getcheckbilllist(pageIndex,pageSize,condition);
	setRequstAttribute("data", checkbilllist);
	return "checkbilllist";
}

public String toaddcheckbillreason(){
	List aareasonlist = checkbillService.getaareasonlist();
	setRequstAttribute("aareasonlist", aareasonlist);
	return "addcheckbillreason";
}

public void addcheckbillreason(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			QMCheckCReasonBill reason = new QMCheckCReasonBill();
			reason.setReasonGid(getParameter("reasonGid"));
			reason.setNum(new BigDecimal(getParameter("num")));
			reason.setCheckcGid(getParameter("checkcGid"));
			boolean suc = checkbillService.addcheckbillreason(reason);
			if(suc){
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

}
