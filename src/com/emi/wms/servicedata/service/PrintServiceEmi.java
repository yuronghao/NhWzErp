package com.emi.wms.servicedata.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.emi.android.action.Submit;
import com.emi.android.bean.GoodsAllocationInfor;
import com.emi.android.bean.GoodsInforRsp;
import com.emi.android.bean.GoodsOutforRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsByScan;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaBarcodeRule;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaUserDefine;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.CurrentStock;
import com.emi.wms.bean.MesWmProcessRoutecGoods;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.OM_MOMain;
import com.emi.wms.bean.OM_MOMaterials;
import com.emi.wms.bean.RmRoleData;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmBatch;
import com.emi.wms.bean.WmMaterialout;
import com.emi.wms.bean.WmMaterialoutC;
import com.emi.wms.bean.WmOthersout;
import com.emi.wms.bean.WmOthersoutC;
import com.emi.wms.bean.WmOtherwarehouse;
import com.emi.wms.bean.WmOtherwarehouseC;
import com.emi.wms.bean.WmPowarehouse;
import com.emi.wms.bean.WmPowarehouseC;
import com.emi.wms.bean.WmProcurearrivalC;
import com.emi.wms.bean.WmProductionwarehouse;
import com.emi.wms.bean.WmProductionwarehouseC;
import com.emi.wms.bean.WmSaleout;
import com.emi.wms.bean.WmSaleoutC;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.bean.WmTask;
import com.emi.wms.bean.YmRdStyle;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.bean.wmCall;
import com.emi.wms.bean.wmCallC;
import com.emi.wms.servicedata.dao.PrintDaoEmi;
import com.emi.wms.servicedata.dao.PurchaseArrivalDao;
import com.emi.wms.servicedata.dao.SaleDao;
import com.emi.wms.servicedata.dao.TaskDao;
import com.emi.wms.servicedata.dao.WareHouseDao;
import com.opensymphony.xwork2.inject.util.Strings;

import antlr.DocBookCodeGenerator;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PrintServiceEmi extends EmiPluginService {

	private PrintDaoEmi printDaoEmi;
	private CacheCtrlService cacheCtrlService;

	public PrintDaoEmi getPrintDaoEmi() {
		return printDaoEmi;
	}

	@Override
	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public void setPrintDaoEmi(PrintDaoEmi printDaoEmi) {
		this.printDaoEmi = printDaoEmi;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}



	//获得打印模板
	public List<String> getAllTemplate(){
		
		List<String> retList=new ArrayList<String>();
		List<Map> maps=printDaoEmi.getAllTemplate();
		for(Map m:maps){
			retList.add(m.get("templateName").toString());
		}
		
		return retList;
		
	}
	
	
	/**
	 * @category 开始打印
	 * 宋银海
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	public void beginPrint(HttpServletRequest request) throws UnsupportedEncodingException{

		String goodsids=request.getParameter("gid");
		String[] tempgood = goodsids.split(",");
		if(tempgood != null && tempgood.length >0){
			for(int i= 0;i<tempgood.length;i++){
				if(tempgood[i] != null&& !"".equals(tempgood[i])){
					String printType=request.getParameter("printType");
					String billDate=request.getParameter("billDate");
					String printservice=URLDecoder.decode(request.getParameter("printservice")  ,"UTF-8");//所选择的打印机
					String printmodel=URLDecoder.decode(request.getParameter("printmodel"),"UTF-8");//所选择的打印模板
					String ss="";

					AaGoods goods=cacheCtrlService.getGoods(tempgood[i]);
					ss = printservice+"|"+printmodel+"|sntext="+goods.getGoodsname()+"|sncode="+goods.getGoodscode()+"|standard="+goods.getGoodsstandard()+"|date="+billDate;

					FileOutputStream fos = null;
					FileInputStream fis = null;
					try {
						fos = new FileOutputStream(Config.PRINTFILE+UUID.randomUUID()+".txt");
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
	
	
}
