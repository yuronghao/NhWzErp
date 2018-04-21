package com.emi.wms.servicedata.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.emi.android.bean.PrintGood;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.ImagePrint;
import com.emi.wms.bean.*;
import com.emi.wms.servicedata.service.PrintServiceEmi;
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
import com.emi.wms.servicedata.service.PurchaseService;
import com.emi.wms.servicedata.service.SaleService;

public class PurchaseAction extends BaseAction {
private PurchaseService purchaseService;
private CacheCtrlService cacheCtrlService;

	private EmiPluginService emiPluginService;
	private PrintServiceEmi printServiceEmi;

	public PrintServiceEmi getPrintServiceEmi() {
		return printServiceEmi;
	}

	public void setPrintServiceEmi(PrintServiceEmi printServiceEmi) {
		this.printServiceEmi = printServiceEmi;
	}

	public EmiPluginService getEmiPluginService() {
		return emiPluginService;
	}

	public void setEmiPluginService(EmiPluginService emiPluginService) {
		this.emiPluginService = emiPluginService;
	}

	public PurchaseService getPurchaseService() {
	return purchaseService;
}
public void setPurchaseService(PurchaseService purchaseService) {
	this.purchaseService = purchaseService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}


/**
* @Desc  采购订单
* @author yurh
* @create 2018-03-03 09:46:07
**/
public String toAddpurchase(){
	String purchasegid = getParameter("purchasegid");
	Map purchase = purchaseService.findpurchase(purchasegid);
	List purchasec = purchaseService.getpurchaselist(purchase.get("purchasegid").toString());
	for(int i=0;i<purchasec.size();i++){
		AaGoods good = cacheCtrlService.getGoods(((Map)purchasec.get(i)).get("goodsUid").toString());
		if(good!= null){
			((Map)purchasec.get(i)).put("good", good);
			Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
			if(unit != null){
				((Map)purchasec.get(i)).put("unit", unit);
			}
		}

	}

//	List purchaseTypelist = purchaseService.getpurchaseTypelist();//采购类型
	setRequstAttribute("purchase", purchase);
	setRequstAttribute("purchasec", purchasec);
//	setRequstAttribute("purchaseTypelist", purchaseTypelist);
	return "purchaseAdd";
	}



	/**
	* @Desc  采购订单列表 wms显示
	* @author yurh
	* @create 2018-03-01 14:24:44
	**/
	public String toAddpurchaseList(){
		try{

			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = CommonUtil.combQuerySql("wp.billCode", keyWord);
			setRequstAttribute("keyWord",keyWord);
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			condition+=" and wp.sobGid='"+sobId+"' and wp.orgGid='"+orgId+"'";
			if(!CommonUtil.isNullString(keyWord)){
				List<AaGoods> goods=cacheCtrlService.setGoods();
				for (AaGoods aaGoods : goods) {
					String gid="";
					if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
						gid+=aaGoods.getGid();
						condition+="or wpc.goodsUid like '%"+gid+"%'";
					}

				}
			}
			PageBean list = purchaseService.getAddpurchaseList(pageIndex, pageSize, condition);
			for(int i=0;i<list.getList().size();i++){
				WmProcureorder temppr = (WmProcureorder)list.getList().get(i);

				if(!CommonUtil.isNullString(temppr.getRecordpersonuid())){
					YmUser ymuser = cacheCtrlService.getUser(temppr.getRecordpersonuid());
					if(!CommonUtil.isNullObject(ymuser)){
						temppr.setRecordPersonName(ymuser.getUserName());
					}
				}


				if(!CommonUtil.isNullString(temppr.getDepartmentuid())){
					AaDepartment department = cacheCtrlService.getDepartment(temppr.getDepartmentuid());
					if(!CommonUtil.isNullObject(department)){
						temppr.setDepartName(department.getDepname());
					}
				}

				if (!CommonUtil.isNullString(temppr.getGoodsUid())) {
					AaGoods good=cacheCtrlService.getGoods(temppr.getGoodsUid());
					if(good != null ){
						temppr.setGoodsUid(good.getGoodsname());
						temppr.setGoodsCode(good.getGoodscode());
						Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
						if(unit != null){
							temppr.setUnitName(unit.getUnitname());

						}
					}

				}

				if(!CommonUtil.isNullString(temppr.getSupplieruid())){
					AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(temppr.getSupplieruid());
					temppr.setProvidercustomername(aaprovidercustomer.getPcname());
					temppr.setProvidercustomercode(aaprovidercustomer.getPccode());
				}

//


			}
			setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "purchaseAddList";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void purchaseEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		purchaseService.setpurchaseEnable(enable,id);
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
public void addpurchase(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmProcureorder purchase = new WmProcureorder();
			String uuid = UUID.randomUUID().toString();
			purchase.setGid(uuid);
			purchase.setNotes(getParameter("notes"));
			purchase.setSupplieruid(getParameter("supplierUid"));
			purchase.setProcuretype(getParameter("procureType"));
			purchase.setCurrency(getParameter("currency"));
			purchase.setExchangerate(new BigDecimal(getParameter("exchangeRate")));
			purchase.setRate(new BigDecimal(getParameter("rate")));
			purchase.setTransportation(getParameter("transportation"));
			purchase.setBillcode(getParameter("billCode"));
			purchase.setBillstate("0");
			purchase.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			purchase.setRecordpersonuid(getParameter("recordPersonUid"));
			purchase.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//purchase.setAuditpersonuid(getParameter("auditPersonUid"));
			//purchase.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			purchase.setAuditstate(0);
			purchase.setDepartmentuid(getParameter("depName"));
			purchase.setPersonuid(getParameter("perName"));
			purchase.setSobgid(getSession().get("SobId").toString());
			purchase.setOrggid(getSession().get("OrgId").toString());
			purchase.setIsdel(0);
			
			List<WmProcureorderC> purchasecs = new ArrayList<WmProcureorderC>();
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
			WmProcureorderC purchasec = new WmProcureorderC();
			purchasec.setProcureorderuid(uuid);
			purchasec.setGoodsuid(goodsUid[i]);
			purchasec.setNumber(new BigDecimal(number[i]));
			purchasec.setLocalprice(new BigDecimal(localPrice[i]));
			purchasec.setOriginaltaxprice(new BigDecimal(originalTaxPrice[i]));
			purchasec.setOriginaltaxmoney(new BigDecimal(originalTaxMoney[i]));
			purchasec.setOriginalnotaxprice(new BigDecimal(originalNotaxPrice[i]));
			purchasec.setOriginalnotaxmoney(new BigDecimal(originalNotaxMoney[i]));
			purchasec.setOriginaltax(new BigDecimal(originalTax[i]));
			purchasec.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
			purchasec.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
			purchasec.setLocalnotaxprice(new BigDecimal(localNotaxPrice[i]));
			purchasec.setLocalnotaxmoney(new BigDecimal(localNotaxMoney[i]));
			purchasec.setLocaltax(new BigDecimal(localTax[i]));
			purchasec.setPlanaog(planAOG[i].length()>0?new Timestamp(DateUtil.stringtoDate(planAOG[i], "yyyy-MM-dd").getTime()):null);
			purchasec.setAmount(new BigDecimal(amount[i]));
			purchasecs.add(purchasec);
			}
			}
			
			boolean suc = purchaseService.addpurchase(purchase);
			boolean suc1 = purchaseService.addpurchasec(purchasecs);
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
	List classify = purchaseService.getClassifyList("");
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
public void updatepurchase(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmProcureorder purchase = new WmProcureorder();
			purchase.setGid(getParameter("purchasegid"));
			purchase.setNotes(getParameter("notes"));
			purchase.setSupplieruid(getParameter("supplierUid"));
			purchase.setProcuretype(getParameter("procureType"));
			purchase.setCurrency(getParameter("currency"));
			purchase.setExchangerate(new BigDecimal(getParameter("exchangeRate")));
			purchase.setRate(new BigDecimal(getParameter("rate")));
			purchase.setTransportation(getParameter("transportation"));
			purchase.setBillcode(getParameter("billCode"));
			purchase.setBillstate("0");
			purchase.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			purchase.setRecordpersonuid(getParameter("recordPersonUid"));
			purchase.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//purchase.setAuditpersonuid(getParameter("auditPersonUid"));
			//purchase.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			purchase.setAuditstate(0);
			purchase.setDepartmentuid(getParameter("depName"));
			purchase.setPersonuid(getParameter("perName"));
			purchase.setSobgid(getSession().get("SobId").toString());
			purchase.setOrggid(getSession().get("OrgId").toString());
			purchase.setIsdel(0);
			
			List<WmProcureorderC> purchasecs = new ArrayList<WmProcureorderC>();
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
			WmProcureorderC purchasec = new WmProcureorderC();
			purchasec.setGid(gid[i]);
			purchasec.setProcureorderuid(getParameter("purchasegid"));
			purchasec.setGoodsuid(goodsUid[i]);
			purchasec.setNumber(new BigDecimal(number[i]));
			purchasec.setLocalprice(new BigDecimal(localPrice[i]));
			purchasec.setOriginaltaxprice(new BigDecimal(originalTaxPrice[i]));
			purchasec.setOriginaltaxmoney(new BigDecimal(originalTaxMoney[i]));
			purchasec.setOriginalnotaxprice(new BigDecimal(originalNotaxPrice[i]));
			purchasec.setOriginalnotaxmoney(new BigDecimal(originalNotaxMoney[i]));
			purchasec.setOriginaltax(new BigDecimal(originalTax[i]));
			purchasec.setLocaltaxprice(new BigDecimal(localTaxPrice[i]));
			purchasec.setLocaltaxmoney(new BigDecimal(localTaxMoney[i]));
			purchasec.setLocalnotaxprice(new BigDecimal(localNotaxPrice[i]));
			purchasec.setLocalnotaxmoney(new BigDecimal(localNotaxMoney[i]));
			purchasec.setLocaltax(new BigDecimal(localTax[i]));
			purchasec.setPlanaog(planAOG[i].length()>0?new Timestamp(DateUtil.stringtoDate(planAOG[i], "yyyy-MM-dd").getTime()):null);
			purchasec.setAmount(new BigDecimal(amount[i]));
			purchasecs.add(purchasec);
			}
			}
			
			boolean suc = purchaseService.updatepurchase(purchase);
			boolean suc1 = purchaseService.updatepurchasec(purchasecs);
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
public String getpurchaselist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String condition="";
	//PageBean purchaselist = purchaseService.getpurchaselist(pageIndex,pageSize,condition);
	//setRequstAttribute("data", purchaselist);
	return "purchaselist";
}


	public String toopenprintpage(){
		List<String> printTemplate=printServiceEmi.getAllTemplate();
		setRequstAttribute("defaultService", ImagePrint.getAllPrintService());
		setRequstAttribute("printTemplate", printTemplate);//打印模板
		return "openprintpage";
	}


	/**
	* @Desc 打印模板一
	* @author yurh
	* @create 2018-03-15 14:16:29
	**/
	public void printbarcode(){
		try {
			String msg = "";
			boolean pass = true;
			String printservice= URLDecoder.decode(getParameter("printservice")  ,"UTF-8");//所选择的打印机
			String printmodel=URLDecoder.decode(CommonUtil.Obj2String(getParameter("printmodel")),"UTF-8");//所选择的打印模板
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

				String goodsArray = getRequest().getParameter("goodsArray");
				String billDate = getRequest().getParameter("billDate");
                List<PrintGood> printGoodList = JSON.parseArray(goodsArray,PrintGood.class);
				if(printGoodList!=null&&printGoodList.size()>0){
					for(int i=0;i<printGoodList.size();i++){
                        PrintGood printGood = printGoodList.get(i);
						String ss;
						String template="template90501";//90*50 带批次
//						if(CommonUtil.isNullObject(batch[i])){
//							template="template90502";//90*50 不带批次
//						}
//						if(cfree1==1&&!CommonUtil.isNullString(process[i])){
//							ss = getParameter("printservice")+"|"+template+"|sntext="+goodsName[i]+"|sncode="+goodsCode[i]+"&"+process[i]+"|snbatch="+CommonUtil.Obj2String(batch[i])+"|cfree="+cfree[i]+"|standard="+goodsstandard[i]+"|date="+billDate+"|amount="+smallamount[i];
//						}else{
							ss = printservice+"|"+printmodel+"|sntext="+printGood.getGoodsName()+"|sncode="+printGood.getGoodsCode()+"|standard="+printGood.getGoodsstandard()+"|date="+billDate;
//						}
						if(!CommonUtil.isNullString(String.valueOf(printGood.getPrintamount()))){
							for(int j=0;j<printGood.getPrintamount();j++){
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


}
