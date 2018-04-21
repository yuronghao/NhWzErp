package com.emi.wms.servicedata.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmProcureorder;
import com.emi.wms.bean.WmProcureorderC;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.YmUser;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.servicedata.service.SaleOrderService;
import com.emi.wms.servicedata.service.SaleService;

public class SaleOrderAction extends BaseAction {
private static final long serialVersionUID = 892882949145541033L;
private SaleOrderService saleOrderService;
private SaleService saleService;
private BasicSettingService basicSettingService;
private CacheCtrlService cacheCtrlService;
private BasePDService basePDService;

public SaleService getSaleService() {
	return saleService;
}
public void setSaleService(SaleService saleService) {
	this.saleService = saleService;
}
public BasePDService getBasePDService() {
	return basePDService;
}
public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}
public SaleOrderService getSaleOrderService() {
	return saleOrderService;
}
public void setSaleOrderService(SaleOrderService saleOrderService) {
	this.saleOrderService = saleOrderService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

public void setBasicSettingService(BasicSettingService basicSettingService) {
	this.basicSettingService = basicSettingService;
}
/**
 * 
 * @category 生产订单列表
 * 2016年4月27日 下午2:01:52
 * @author 杨峥铖
 * @return
 */
	public String saleOrderList(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = "" ;
			if(CommonUtil.notNullString(keyWord)){
				condition = CommonUtil.combQuerySql("WmSaleorder.billCode,WmSaleorder.barCode", keyWord);
			}
			setRequstAttribute("keyWord",keyWord);
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			condition+=" and WmSaleorder.sobGid='"+sobId+"' and WmSaleorder.orgGid='"+orgId+"'";
			PageBean saleOrderlist = saleOrderService.getsaleOrderlist(pageIndex,pageSize,condition);
			for(int i=0;i<saleOrderlist.getList().size();i++){
				if(!CommonUtil.isNullString(((WmSaleorder)saleOrderlist.getList().get(i)).getGoodsUid())){
					AaGoods good = cacheCtrlService.getGoods(((WmSaleorder)saleOrderlist.getList().get(i)).getGoodsUid().toString());
					((WmSaleorder)saleOrderlist.getList().get(i)).setAagoods(good);
				}
				if(!CommonUtil.isNullString(((WmSaleorder)saleOrderlist.getList().get(i)).getRecordpersonuid())){
					YmUser ymuser = cacheCtrlService.getUser(((WmSaleorder)saleOrderlist.getList().get(i)).getRecordpersonuid().toString());
					if(!CommonUtil.isNullObject(ymuser)){
						((WmSaleorder)saleOrderlist.getList().get(i)).setProvidercustomername(ymuser.getUserName());
					}
				}
			}
			setRequstAttribute("data", saleOrderlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "saleOrderList";
	}
	
	/**
	 * 
	 * @category 生产订单详情
	 * 2016年4月27日 下午2:01:58
	 * @author 杨峥铖
	 * @return
	 */
    public String toAddsaleOrder(){
	try {
		String saleOrdergid = getParameter("saleOrdergid");
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		Map saleOrder = saleOrderService.findsaleOrder(saleOrdergid,orgId,sobId);
		if(!CommonUtil.isNullObject(saleOrder)){
			if(!CommonUtil.isNullObject(saleOrder.get("saledeptGid"))){
				AaDepartment department = basePDService.findDepartment(saleOrder.get("saledeptGid").toString());
				setRequstAttribute("department", department);
			}
			if(!CommonUtil.isNullObject(saleOrder.get("customerUid"))){
				AaProviderCustomer aaProviderCustomer = cacheCtrlService.getProviderCustomer(saleOrder.get("customerUid").toString());
				setRequstAttribute("aaProviderCustomer", aaProviderCustomer);
			}
			if(!CommonUtil.isNullObject(saleOrder.get("WMsaleOrdergid"))){
				List saleOrderc = saleOrderService.getsaleOrderlist(saleOrder.get("WMsaleOrdergid").toString());
				for(int i=0;i<saleOrderc.size();i++){
					AaGoods good = cacheCtrlService.getGoods(((Map)saleOrderc.get(i)).get("goodsUid").toString());
					((Map)saleOrderc.get(i)).put("good", good);
				}
				setRequstAttribute("saleOrderc", saleOrderc);
			}
		}
		//得到自由项定义
		setRequstAttribute("saleOrder", saleOrder);
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "saleOrderAdd";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void saleOrderEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		//saleOrderService.setsaleOrderEnable(enable,id);
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
public void addsaleOrder(){
	try {
		String msg = "";
		boolean pass = true;
		if(pass){
			WmSaleorder saleOrder = new WmSaleorder();
			String uuid = UUID.randomUUID().toString();
			saleOrder.setGid(uuid);
			saleOrder.setNotes(getParameter("notes"));
			saleOrder.setBillcode(getParameter("billCode"));
			saleOrder.setBillstate("0");
			saleOrder.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			saleOrder.setDeptGid(getParameter("depUid"));
			saleOrder.setCustomeruid(getParameter("customerUid"));
			saleOrder.setRecordpersonuid(getParameter("recordPersonUid"));
			saleOrder.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//saleOrder.setAuditpersonuid(getParameter("auditPersonUid"));
			//saleOrder.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			saleOrder.setSobgid(getSession().get("SobId").toString());
			saleOrder.setOrggid(getSession().get("OrgId").toString());
			
			List<WmSaleorderC> saleOrdercs = new ArrayList<WmSaleorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] assistNumber = getRequest().getParameterValues("assistNumber");
			String[] localTaxPrice = getRequest().getParameterValues("localTaxPrice");
			String[] localTaxMoney = getRequest().getParameterValues("localTaxMoney");
			String[] note = getRequest().getParameterValues("note");
			for(int i=0;i<goodsUid.length;i++){
				WmSaleorderC saleOrderc = new WmSaleorderC();
			saleOrderc.setSaleOrderUid(uuid);
			saleOrderc.setGoodsUid(goodsUid[i]);
			saleOrderc.setNumber(new BigDecimal(number[i]));
			saleOrderc.setAssistNumber(assistNumber[i].length()>0?new BigDecimal(assistNumber[i]):null);
			saleOrderc.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
			saleOrderc.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
			saleOrderc.setNotes(note[i]);
			saleOrdercs.add(saleOrderc);
			}
			}
			
			boolean suc = saleOrderService.addsaleOrder(saleOrder);
			boolean suc1 = saleOrderService.addsaleOrderc(saleOrdercs);
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
	//List classify = saleOrderService.getClassifyList("");
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	//classify.add(m);
	//getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	return "orgSelect";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void updatesaleOrder(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmSaleorder saleOrder = new WmSaleorder();
			saleOrder.setGid(getParameter("saleOrdergid"));
			saleOrder.setNotes(getParameter("notes"));
			saleOrder.setBillcode(getParameter("billCode"));
			saleOrder.setBillstate("0");
			saleOrder.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			saleOrder.setDeptGid(getParameter("depUid"));
			saleOrder.setCustomeruid(getParameter("customerUid"));
			saleOrder.setRecordpersonuid(getParameter("recordPersonUid"));
			saleOrder.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//saleOrder.setAuditpersonuid(getParameter("auditPersonUid"));
			//saleOrder.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			saleOrder.setSobgid(getSession().get("SobId").toString());
			saleOrder.setOrggid(getSession().get("OrgId").toString());
			
			List<WmSaleorderC> saleOrdercs = new ArrayList<WmSaleorderC>();
			List<WmSaleorderC> saleOrdercs1 = new ArrayList<WmSaleorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] assistNumber = getRequest().getParameterValues("assistNumber");
			String[] localTaxPrice = getRequest().getParameterValues("localTaxPrice");
			String[] localTaxMoney = getRequest().getParameterValues("localTaxMoney");
			String[] note = getRequest().getParameterValues("note");
			for(int i=0;i<goodsUid.length;i++){
				if(CommonUtil.isNullObject(gid[i])){
					WmSaleorderC saleOrderc1 = new WmSaleorderC();
					saleOrderc1.setSaleOrderUid(getParameter("saleOrdergid"));
					saleOrderc1.setGoodsUid(goodsUid[i]);
					saleOrderc1.setNumber(new BigDecimal(number[i]));
					saleOrderc1.setAssistNumber(assistNumber[i].length()>0?new BigDecimal(assistNumber[i]):null);
					saleOrderc1.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
					saleOrderc1.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
					saleOrderc1.setNotes(note[i]);
					saleOrdercs1.add(saleOrderc1);
				}else{
					WmSaleorderC saleOrderc = new WmSaleorderC();
					saleOrderc.setGid(gid[i]);
					saleOrderc.setSaleOrderUid(getParameter("saleOrdergid"));
					saleOrderc.setGoodsUid(goodsUid[i]);
					saleOrderc.setNumber(new BigDecimal(number[i]));
					saleOrderc.setAssistNumber(assistNumber[i].length()>0?new BigDecimal(assistNumber[i]):null);
					saleOrderc.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
					saleOrderc.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
					saleOrderc.setNotes(note[i]);
					saleOrdercs.add(saleOrderc);
				}
			}
			}
			
			boolean suc = saleOrderService.updatesaleOrder(saleOrder);
			boolean suc1 = saleOrderService.updatesaleOrderc(saleOrdercs);
			boolean suc2 = saleOrderService.addsaleOrderc(saleOrdercs1);
			boolean suc3 = saleOrderService.deletesaleOrderc(getParameter("deleteGids").substring(0,getParameter("deleteGids").length()-1));
			if(suc&&suc1&&suc2&&suc3){
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
public String getsaleOrderlist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String condition="";
	//PageBean saleOrderlist = saleOrderService.getsaleOrderlist(pageIndex,pageSize,condition);
	//setRequstAttribute("data", saleOrderlist);
	return "saleOrderlist";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			//List<Map> printlist = new ArrayList<Map>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] batch = getRequest().getParameterValues("batch");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
			String ss = "SmartPrinter Pro|template|sncode="+goodsCode[i]+batch[i];
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
				FileOutputStream fos = null;
				FileInputStream fis = null;
				try {
					fos = new FileOutputStream("E:/temp/11/"+UUID.randomUUID()+".txt");
					System.out.println(UUID.randomUUID());
					ss.getBytes();
					fos.write(ss.getBytes());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
						if (fis != null) {
							fis.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			}
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
   public void deletesaleOrder(){
	try {
		String saleorderGid = getParameter("gid");
		int producecount = saleOrderService.getproduceprocess(saleorderGid);
		if(producecount>0){
			getResponse().getWriter().write("error");
		}else{
			saleOrderService.deletesaleOrder(saleorderGid);
			getResponse().getWriter().write("success");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
   
   public void checksaleOrder(){
	try {
		String saleorderGid = getParameter("gid");
		int producecount = saleOrderService.getproduceprocess(saleorderGid);
		if(producecount>0){
			getResponse().getWriter().write("error");
		}else{
			getResponse().getWriter().write("success");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	public void getBillId() {
		try {
		String billType = getRequest().getParameter("billType");// 与WM_BillType表相匹配
		int year = DateUtil.getToYear();
		int month = DateUtil.getToMonth();
		String strMonth;
		if (String.valueOf(month).length() == 1) {
			strMonth = "0" + String.valueOf(month);
		} else {
			strMonth = String.valueOf(month);
		}

		String currentId = saleService.getBillId(billType, year + strMonth);

		String billId = billType + year + strMonth + currentId;

		String nowDate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String userId = getSession().get("UserId").toString();
		YmUser Record = cacheCtrlService.getUser(userId);// 从缓存查出录入人
		
		Map map = new HashMap();
		map.put("nowDate", nowDate);
		map.put("billId", billId);
		map.put("billId", billId);

		map.put("recordname", Record.getUserName());
		map.put("gRecordPersonUid", Record.getGid());

			getResponse().getWriter().write(
					JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	   public void auditsaleOrder(){
			try {
				String saleorderGid = getParameter("gid");
				boolean suc = saleOrderService.auditsaleOrder(saleorderGid);
				if(suc){
					getResponse().getWriter().write("success");
				}else{
					getResponse().getWriter().write("error");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	   
	   public void disauditsaleOrder(){
			try {
				String saleorderGid = getParameter("gid");
				int salecount = saleOrderService.getsalesnumber(saleorderGid);
				if(salecount>0){
					getResponse().getWriter().write("error");
				}else{
					saleOrderService.disauditsaleOrder(saleorderGid);
					getResponse().getWriter().write("success");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	   
	   public void stopsaleOrder(){
			try {
				String saleorderGid = getParameter("gid");
				boolean suc = saleOrderService.stopsaleOrder(saleorderGid);
				if(suc){
					getResponse().getWriter().write("success");
				}else{
					getResponse().getWriter().write("error");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
