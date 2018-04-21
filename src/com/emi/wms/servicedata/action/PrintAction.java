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
import com.emi.wms.servicedata.service.PrintServiceEmi;
import com.emi.wms.servicedata.service.PurchaseArrivalService;
import com.emi.wms.servicedata.service.SaleService;

public class PrintAction extends BaseAction {
/**
	 * 
	 */
	private static final long serialVersionUID = 2190563887824238336L;
private CacheCtrlService cacheCtrlService;
private EmiPluginService emiPluginService;
private PrintServiceEmi printServiceEmi;

public EmiPluginService getEmiPluginService() {
	return emiPluginService;
}
public void setEmiPluginService(EmiPluginService emiPluginService) {
	this.emiPluginService = emiPluginService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

public PrintServiceEmi getPrintServiceEmi() {
	return printServiceEmi;
}
public void setPrintServiceEmi(PrintServiceEmi printServiceEmi) {
	this.printServiceEmi = printServiceEmi;
}

/**
 * @category 开始打印
 * 宋银海
 */
public void printbarcode(){
	try {
		String quantity=getRequest().getParameter("quantity");
		int n = CommonUtil.isNullString(quantity)?1:Integer.parseInt(quantity);
		for(int i=0;i<n;i++){
			printServiceEmi.beginPrint(getRequest());
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}


/**
 * @category 获取打印机列表
 * 宋银海
 * @return
 */
public String toopenprintpage(){
	
	List<String> printTemplate=printServiceEmi.getAllTemplate();
	setRequstAttribute("defaultService", ImagePrint.getAllPrintService());//打印机列表
	setRequstAttribute("printTemplate", printTemplate);//打印模板
	return "openprintpagePrintAction";
}
}
