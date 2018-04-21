package com.emi.wms.servicedata.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import net.sf.json.JSONArray;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.common.util.ImagePrint;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.sys.util.SysPropertites;
import com.emi.wms.bean.AaBarcodeRule;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmProcureorder;
import com.emi.wms.bean.WmProcureorderC;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.service.PurchaseArrivalService;
import com.emi.wms.servicedata.service.SaleService;

public class PurchaseArrivalAction extends BaseAction {
private PurchaseArrivalService purchaseArrivalService;
private CacheCtrlService cacheCtrlService;
private EmiPluginService emiPluginService;

public EmiPluginService getEmiPluginService() {
	return emiPluginService;
}
public void setEmiPluginService(EmiPluginService emiPluginService) {
	this.emiPluginService = emiPluginService;
}
public PurchaseArrivalService getPurchaseArrivalService() {
	return purchaseArrivalService;
}


public void setPurchaseArrivalService(
		PurchaseArrivalService purchaseArrivalService) {
	this.purchaseArrivalService = purchaseArrivalService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

	public String purchaseArrivalList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("WmProcurearrival.billCode,WmProcurearrival.barCode", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and WmProcurearrival.sobGid='"+sobId+"' and WmProcurearrival.orgGid='"+orgId+"'";
		PageBean purchasearrivallist = purchaseArrivalService.getpurchasearrivallist(pageIndex,pageSize,condition);
		for(int i=0;i<purchasearrivallist.getList().size();i++){
			AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(((WmProcurearrival)purchasearrivallist.getList().get(i)).getSupplieruid().toString());
			((WmProcurearrival)purchasearrivallist.getList().get(i)).setProvidercustomername(aaprovidercustomer.getPcname());
			
		}
		setRequstAttribute("data", purchasearrivallist);
		return "purchaseArrivalList";
	}
	
    public String toAddpurchaseArrival(){
	String purchaseArrivalgid = getParameter("purchaseArrivalgid");
	String orgId=getSession().get("OrgId").toString();
	String sobId=getSession().get("SobId").toString();
	Map purchaseArrival = purchaseArrivalService.findpurchaseArrival(purchaseArrivalgid,orgId,sobId);
	if(!CommonUtil.isNullObject(purchaseArrival)){
		List purchaseArrivalc = purchaseArrivalService.getpurchaseArrivallist(purchaseArrival.get("purchaseArrivalgid").toString(),null);
		for(int i=0;i<purchaseArrivalc.size();i++){
			AaGoods good = cacheCtrlService.getGoods(((Map)purchaseArrivalc.get(i)).get("goodsUid").toString());
			((Map)purchaseArrivalc.get(i)).put("good", good);
		}
		setRequstAttribute("purchaseArrivalc", purchaseArrivalc);
	}
	setRequstAttribute("purchaseArrival", purchaseArrival);
	return "purchaseArrivalAdd";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void purchaseArrivalEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		purchaseArrivalService.setpurchaseArrivalEnable(enable,id);
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
public void addpurchaseArrival(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmProcureorder purchaseArrival = new WmProcureorder();
			String uuid = UUID.randomUUID().toString();
			purchaseArrival.setGid(uuid);
			purchaseArrival.setNotes(getParameter("notes"));
			purchaseArrival.setSupplieruid(getParameter("supplierUid"));
			purchaseArrival.setProcuretype(getParameter("procureType"));
			purchaseArrival.setCurrency(getParameter("currency"));
			purchaseArrival.setExchangerate(new BigDecimal(getParameter("exchangeRate")));
			purchaseArrival.setRate(new BigDecimal(getParameter("rate")));
			purchaseArrival.setTransportation(getParameter("transportation"));
			purchaseArrival.setBillcode(getParameter("billCode"));
			purchaseArrival.setBillstate("0");
			purchaseArrival.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			purchaseArrival.setRecordpersonuid(getParameter("recordPersonUid"));
			purchaseArrival.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//purchaseArrival.setAuditpersonuid(getParameter("auditPersonUid"));
			//purchaseArrival.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			purchaseArrival.setAuditstate(0);
			purchaseArrival.setDepartmentuid(getParameter("depName"));
			purchaseArrival.setPersonuid(getParameter("perName"));
			purchaseArrival.setSobgid(getSession().get("SobId").toString());
			purchaseArrival.setOrggid(getSession().get("OrgId").toString());
			purchaseArrival.setIsdel(0);
			
			List<WmProcureorderC> purchaseArrivalcs = new ArrayList<WmProcureorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] localPrice = getRequest().getParameterValues("localPrice");
			String[] originalTaxPrice = getRequest().getParameterValues("originalTaxPrice");
			String[] originalTaxMoney = getRequest().getParameterValues("originalTaxMoney");
			String[] originalNotaxPrice = getRequest().getParameterValues("originalNotaxPrice");
			String[] originalNotaxMoney = getRequest().getParameterValues("originalNotaxMoney");
			String[] originalTax = getRequest().getParameterValues("originalTax");
			String[] localTaxPrice = getRequest().getParameterValues("localTaxPrice");
			String[] localTaxMoney = getRequest().getParameterValues("localTaxMoney");
			String[] localNotaxPrice = getRequest().getParameterValues("localNotaxPrice");
			String[] localNotaxMoney = getRequest().getParameterValues("localNotaxMoney");
			String[] localTax = getRequest().getParameterValues("localTax");
			String[] planAOG = getRequest().getParameterValues("planAOG");
			String[] amount = getRequest().getParameterValues("amount");
			for(int i=0;i<goodsUid.length;i++){
			WmProcureorderC purchaseArrivalc = new WmProcureorderC();
			purchaseArrivalc.setProcureorderuid(uuid);
			purchaseArrivalc.setGoodsuid(goodsUid[i]);
			purchaseArrivalc.setNumber(new BigDecimal(number[i]));
			purchaseArrivalc.setLocalprice(new BigDecimal(localPrice[i]));
			purchaseArrivalc.setOriginaltaxprice(new BigDecimal(originalTaxPrice[i]));
			purchaseArrivalc.setOriginaltaxmoney(new BigDecimal(originalTaxMoney[i]));
			purchaseArrivalc.setOriginalnotaxprice(new BigDecimal(originalNotaxPrice[i]));
			purchaseArrivalc.setOriginalnotaxmoney(new BigDecimal(originalNotaxMoney[i]));
			purchaseArrivalc.setOriginaltax(new BigDecimal(originalTax[i]));
			purchaseArrivalc.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
			purchaseArrivalc.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
			purchaseArrivalc.setLocalnotaxprice(new BigDecimal(localNotaxPrice[i]));
			purchaseArrivalc.setLocalnotaxmoney(new BigDecimal(localNotaxMoney[i]));
			purchaseArrivalc.setLocaltax(new BigDecimal(localTax[i]));
			purchaseArrivalc.setPlanaog(planAOG[i].length()>0?new Timestamp(DateUtil.stringtoDate(planAOG[i], "yyyy-MM-dd").getTime()):null);
			purchaseArrivalc.setAmount(new BigDecimal(amount[i]));
			purchaseArrivalcs.add(purchaseArrivalc);
			}
			}
			
			boolean suc = purchaseArrivalService.addpurchaseArrival(purchaseArrival);
			boolean suc1 = purchaseArrivalService.addpurchaseArrivalc(purchaseArrivalcs);
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
	List classify = purchaseArrivalService.getClassifyList("");
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
public void updatepurchaseArrival(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmProcureorder purchaseArrival = new WmProcureorder();
			purchaseArrival.setGid(getParameter("purchaseArrivalgid"));
			purchaseArrival.setNotes(getParameter("notes"));
			purchaseArrival.setSupplieruid(getParameter("supplierUid"));
			purchaseArrival.setProcuretype(getParameter("procureType"));
			purchaseArrival.setCurrency(getParameter("currency"));
			purchaseArrival.setExchangerate(new BigDecimal(getParameter("exchangeRate")));
			purchaseArrival.setRate(new BigDecimal(getParameter("rate")));
			purchaseArrival.setTransportation(getParameter("transportation"));
			purchaseArrival.setBillcode(getParameter("billCode"));
			purchaseArrival.setBillstate("0");
			purchaseArrival.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			purchaseArrival.setRecordpersonuid(getParameter("recordPersonUid"));
			purchaseArrival.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//purchaseArrival.setAuditpersonuid(getParameter("auditPersonUid"));
			//purchaseArrival.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			purchaseArrival.setAuditstate(0);
			purchaseArrival.setDepartmentuid(getParameter("depName"));
			purchaseArrival.setPersonuid(getParameter("perName"));
			purchaseArrival.setSobgid(getSession().get("SobId").toString());
			purchaseArrival.setOrggid(getSession().get("OrgId").toString());
			purchaseArrival.setIsdel(0);
			
			List<WmProcureorderC> purchaseArrivalcs = new ArrayList<WmProcureorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] localPrice = getRequest().getParameterValues("localPrice");
			String[] originalTaxPrice = getRequest().getParameterValues("originalTaxPrice");
			String[] originalTaxMoney = getRequest().getParameterValues("originalTaxMoney");
			String[] originalNotaxPrice = getRequest().getParameterValues("originalNotaxPrice");
			String[] originalNotaxMoney = getRequest().getParameterValues("originalNotaxMoney");
			String[] originalTax = getRequest().getParameterValues("originalTax");
			String[] localTaxPrice = getRequest().getParameterValues("localTaxPrice");
			String[] localTaxMoney = getRequest().getParameterValues("localTaxMoney");
			String[] localNotaxPrice = getRequest().getParameterValues("localNotaxPrice");
			String[] localNotaxMoney = getRequest().getParameterValues("localNotaxMoney");
			String[] localTax = getRequest().getParameterValues("localTax");
			String[] planAOG = getRequest().getParameterValues("planAOG");
			String[] amount = getRequest().getParameterValues("amount");
			for(int i=0;i<goodsUid.length;i++){
			WmProcureorderC purchaseArrivalc = new WmProcureorderC();
			purchaseArrivalc.setGid(gid[i]);
			purchaseArrivalc.setProcureorderuid(getParameter("purchaseArrivalgid"));
			purchaseArrivalc.setGoodsuid(goodsUid[i]);
			purchaseArrivalc.setNumber(new BigDecimal(number[i]));
			purchaseArrivalc.setLocalprice(new BigDecimal(localPrice[i]));
			purchaseArrivalc.setOriginaltaxprice(new BigDecimal(originalTaxPrice[i]));
			purchaseArrivalc.setOriginaltaxmoney(new BigDecimal(originalTaxMoney[i]));
			purchaseArrivalc.setOriginalnotaxprice(new BigDecimal(originalNotaxPrice[i]));
			purchaseArrivalc.setOriginalnotaxmoney(new BigDecimal(originalNotaxMoney[i]));
			purchaseArrivalc.setOriginaltax(new BigDecimal(originalTax[i]));
			purchaseArrivalc.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
			purchaseArrivalc.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
			purchaseArrivalc.setLocalnotaxprice(new BigDecimal(localNotaxPrice[i]));
			purchaseArrivalc.setLocalnotaxmoney(new BigDecimal(localNotaxMoney[i]));
			purchaseArrivalc.setLocaltax(new BigDecimal(localTax[i]));
			purchaseArrivalc.setPlanaog(planAOG[i].length()>0?new Timestamp(DateUtil.stringtoDate(planAOG[i], "yyyy-MM-dd").getTime()):null);
			purchaseArrivalc.setAmount(new BigDecimal(amount[i]));
			purchaseArrivalcs.add(purchaseArrivalc);
			}
			}
			
			boolean suc = purchaseArrivalService.updatepurchaseArrival(purchaseArrival);
			boolean suc1 = purchaseArrivalService.updatepurchaseArrivalc(purchaseArrivalcs);
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
public String getpurchaseArrivallist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String condition="";
	//PageBean purchaseArrivallist = purchaseArrivalService.getpurchaseArrivallist(pageIndex,pageSize,condition);
	//setRequstAttribute("data", purchaseArrivallist);
	return "purchaseArrivallist";
}

/**
 * 
 * @category 打印模板一
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			List<AaBarcodeRule> barcoderulelist = emiPluginService.getAaBarcodeRule("g");
			int cfree1=1;
			int cfreelength=3;
			String cfreezero ="";
			for(int k=0;k<barcoderulelist.size();k++){
				if(((AaBarcodeRule)barcoderulelist.get(k)).getBarcodeName().toString().equals("cfree1")){
					cfree1=((AaBarcodeRule)barcoderulelist.get(k)).getIsUse();
					cfreelength=((AaBarcodeRule)barcoderulelist.get(k)).getLength();
				}
			}
			for(int a=0;a<cfreelength;a++){
				cfreezero=cfreezero+"0";
			}
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] goodsName = getRequest().getParameterValues("goodsName");
			String[] batch = getRequest().getParameterValues("batch");
			String[] process = getRequest().getParameterValues("process");
			String[] cfree = getRequest().getParameterValues("cfree");
			String[] goodsstandard = getRequest().getParameterValues("goodsstandard");
			String billDate = getRequest().getParameter("billDate");
			String[] smallamount = getRequest().getParameterValues("smallamount");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
				String ss;
				String template="template90501";//90*50 带批次
				if(CommonUtil.isNullObject(batch[i])){
					template="template90502";//90*50 不带批次
				}
				if(cfree1==1&&!CommonUtil.isNullString(process[i])){
					ss = getParameter("printservice")+"|"+template+"|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"&"+process[i]+"|snbatch="+CommonUtil.Obj2String(batch[i])+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}else{
					ss = getParameter("printservice")+"|"+template+"|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"|snbatch="+CommonUtil.Obj2String(batch[i])+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}
			if(!CommonUtil.isNullString(printamount[i])){
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 打印模板二
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode1(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			List<AaBarcodeRule> barcoderulelist = emiPluginService.getAaBarcodeRule("g");
			int cfree1=1;
			int cfreelength=3;
			String cfreezero ="";
			for(int k=0;k<barcoderulelist.size();k++){
				if(((AaBarcodeRule)barcoderulelist.get(k)).getBarcodeName().toString().equals("cfree1")){
					cfree1=((AaBarcodeRule)barcoderulelist.get(k)).getIsUse();
					cfreelength=((AaBarcodeRule)barcoderulelist.get(k)).getLength();
				}
			}
			for(int a=0;a<cfreelength;a++){
				cfreezero=cfreezero+"0";
			}
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] goodsName = getRequest().getParameterValues("goodsName");
			String[] batch = getRequest().getParameterValues("batch");
			String[] process = getRequest().getParameterValues("process");
			String[] cfree = getRequest().getParameterValues("cfree");
			String[] goodsstandard = getRequest().getParameterValues("goodsstandard");
			String billDate = getRequest().getParameter("billDate");
			String[] smallamount = getRequest().getParameterValues("smallamount");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
				String ss;
				if(cfree1==1&&!CommonUtil.isNullString(process[i])){
					ss = getParameter("printservice")+"|template70451|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"&"+process[i]+"|snbatch="+batch[i]+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}else{
					ss = getParameter("printservice")+"|template70451|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"|snbatch="+batch[i]+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}
			if(!CommonUtil.isNullString(printamount[i])){
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 打印模板三
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode2(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			List<AaBarcodeRule> barcoderulelist = emiPluginService.getAaBarcodeRule("g");
			int cfree1=1;
			int cfreelength=3;
			String cfreezero ="";
			for(int k=0;k<barcoderulelist.size();k++){
				if(((AaBarcodeRule)barcoderulelist.get(k)).getBarcodeName().toString().equals("cfree1")){
					cfree1=((AaBarcodeRule)barcoderulelist.get(k)).getIsUse();
					cfreelength=((AaBarcodeRule)barcoderulelist.get(k)).getLength();
				}
			}
			for(int a=0;a<cfreelength;a++){
				cfreezero=cfreezero+"0";
			}
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] goodsName = getRequest().getParameterValues("goodsName");
			String[] batch = getRequest().getParameterValues("batch");
			String[] process = getRequest().getParameterValues("process");
			String[] cfree = getRequest().getParameterValues("cfree");
			String[] goodsstandard = getRequest().getParameterValues("goodsstandard");
			String billDate = getRequest().getParameter("billDate");
			String[] smallamount = getRequest().getParameterValues("smallamount");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
				String ss;
				if(cfree1==1&&!CommonUtil.isNullString(process[i])){
					ss = getParameter("printservice")+"|template10901|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"&"+process[i]+"|snbatch="+batch[i]+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}else{
					ss = getParameter("printservice")+"|template10901|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"|snbatch="+batch[i]+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
				}
			if(!CommonUtil.isNullString(printamount[i])){
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 打印模板三
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode3(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			List<AaBarcodeRule> barcoderulelist = emiPluginService.getAaBarcodeRule("g");
			int cfree1=1;
			int cfreelength=3;
			String cfreezero ="";
			for(int k=0;k<barcoderulelist.size();k++){
				if(((AaBarcodeRule)barcoderulelist.get(k)).getBarcodeName().toString().equals("cfree1")){
					cfree1=((AaBarcodeRule)barcoderulelist.get(k)).getIsUse();
					cfreelength=((AaBarcodeRule)barcoderulelist.get(k)).getLength();
				}
			}
			for(int a=0;a<cfreelength;a++){
				cfreezero=cfreezero+"0";
			}
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] goodsName = getRequest().getParameterValues("goodsName");
			String[] batch = getRequest().getParameterValues("batch");
			String[] process = getRequest().getParameterValues("process");
			String[] cfree = getRequest().getParameterValues("cfree");
			String[] goodsstandard = getRequest().getParameterValues("goodsstandard");
			String billDate = getRequest().getParameter("billDate");
			String[] smallamount = getRequest().getParameterValues("smallamount");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
				String ss;
				if(cfree1==1&&!CommonUtil.isNullString(process[i])){
					ss = getParameter("printservice")+"|templatewl|sncode="+goodsCode[i]+"&"+process[i];
				}else{
					ss = getParameter("printservice")+"|templatewl|sncode="+goodsCode[i];
				}
			if(!CommonUtil.isNullString(printamount[i])){
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 打印模板三
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode4(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			List<AaBarcodeRule> barcoderulelist = emiPluginService.getAaBarcodeRule("g");
			int cfree1=1;
			int cfreelength=3;
			String cfreezero ="";
			for(int k=0;k<barcoderulelist.size();k++){
				if(((AaBarcodeRule)barcoderulelist.get(k)).getBarcodeName().toString().equals("cfree1")){
					cfree1=((AaBarcodeRule)barcoderulelist.get(k)).getIsUse();
					cfreelength=((AaBarcodeRule)barcoderulelist.get(k)).getLength();
				}
			}
			for(int a=0;a<cfreelength;a++){
				cfreezero=cfreezero+"0";
			}
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] goodsName = getRequest().getParameterValues("goodsName");
			String[] batch = getRequest().getParameterValues("batch");
			String[] process = getRequest().getParameterValues("process");
			String[] cfree = getRequest().getParameterValues("cfree");
			String[] goodsstandard = getRequest().getParameterValues("goodsstandard");
			String billDate = getRequest().getParameter("billDate");
			String[] smallamount = getRequest().getParameterValues("smallamount");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
				String ss;
				if(cfree1==1&&!CommonUtil.isNullString(process[i])){
					ss = getParameter("printservice")+"|templatebatch|snbatch="+batch[i];
				}else{
					ss = getParameter("printservice")+"|templatebatch|snbatch="+batch[i];
				}
			if(!CommonUtil.isNullString(printamount[i])){
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public String toopenprintpage(){
	setRequstAttribute("defaultService", ImagePrint.getAllPrintService());
	return "openprintpage";
}
}
