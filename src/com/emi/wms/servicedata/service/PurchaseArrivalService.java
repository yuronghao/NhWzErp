package com.emi.wms.servicedata.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.*;
import com.emi.wms.servicedata.dao.PurchaseArrivalDao;

public class PurchaseArrivalService  extends EmiPluginService{

	private PurchaseArrivalDao purchaseArrivalDao;
	private CacheCtrlService cacheCtrlService;

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public PurchaseArrivalDao getPurchaseArrivalDao() {
		return purchaseArrivalDao;
	}

	public void setPurchaseArrivalDao(PurchaseArrivalDao purchaseArrivalDao) {
		this.purchaseArrivalDao = purchaseArrivalDao;
	}

	public PageBean getpurchasearrivallist(int pageIndex,int pageSize,String condition){
		return purchaseArrivalDao.getpurchasearrivallist(pageIndex,pageSize,condition);
	}
	
	public List getClassifyList(String conditionSql) {
		return purchaseArrivalDao.getClassifyList(conditionSql);
	}
	
	public Map getorganizeInfo(String conditionSql) {
		return purchaseArrivalDao.getorganizeInfo(conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return purchaseArrivalDao.getCategoryList(conditionSql);
	}
	public boolean addorg(AaOrg aaorg) {
		return purchaseArrivalDao.addorg(aaorg);
	}
	public boolean addpurchaseArrival(WmProcureorder WmProcureorder) {
		return purchaseArrivalDao.addpurchaseArrival(WmProcureorder);
	}
	public boolean addprocusbook(List list) {
		return purchaseArrivalDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return purchaseArrivalDao.findorg(exhTypeId);
	}
	public Map findpurchaseArrival(String purchaseArrivalgid,String orgId,String sobId) {
		return purchaseArrivalDao.findpurchaseArrival(purchaseArrivalgid,orgId,sobId);
	}
	public boolean updateorg(AaOrg aaorg) {
		return purchaseArrivalDao.updateorg(aaorg);
	}
	public boolean deletebooks(String exhTypeId) {
		return purchaseArrivalDao.deletebooks(exhTypeId);
	}
	public List getpurchaseArrivalbookList(String exhTypeId) {
		return purchaseArrivalDao.getpurchaseArrivalbookList(exhTypeId);
	}
	public boolean deletepurchaseArrival(String orgclassid) {
		return  purchaseArrivalDao.deletepurchaseArrival(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.purchaseArrivalDao.findorgchild(orgclassid);
	}
	public void setpurchaseArrivalEnable(int enable, String id) {
		purchaseArrivalDao.setpurchaseArrivalEnable(enable,id);
	}
	public boolean addpurchaseArrivalc(List list) {
		return purchaseArrivalDao.addpurchaseArrivalc(list);
	}
	public boolean updatepurchaseArrivalc(List list) {
		return purchaseArrivalDao.updatepurchaseArrivalc(list);
	}
	public boolean updatepurchaseArrival(WmProcureorder WmProcureorder) {
		return purchaseArrivalDao.updatepurchaseArrival(WmProcureorder);
	}
	public boolean updateymuser(YmUser YmUser) {
		return purchaseArrivalDao.updateymuser(YmUser);
	}
	public List getpurchaseArrivallist(String purchaseArrivalpurchaseArrivalUid,String bodyDefine) {
		String condition="";
		if(!CommonUtil.isNullObject(bodyDefine)){
			condition=" and ( wmprocurearrivalc."+bodyDefine+"='否' or wmprocurearrivalc."+bodyDefine+" is null ) ";
		}
		condition=condition+" order by rowno ";
		return purchaseArrivalDao.getpurchaseArrivallist(purchaseArrivalpurchaseArrivalUid,condition);
	}
	
	public List getpurchaseArrivallistcheck(String purchaseArrivalpurchaseArrivalUid,String bodyDefine) {
		String condition="";
		if(!CommonUtil.isNullObject(bodyDefine)){
			condition=" and "+bodyDefine+"='是'";
		}
		
		return purchaseArrivalDao.getpurchaseArrivallistcheck(purchaseArrivalpurchaseArrivalUid,condition);
	}
	
	public List getpurchaseArrivallistcheck1(String purchaseArrivalpurchaseArrivalUid) {
		return purchaseArrivalDao.getpurchaseArrivallistcheck1(purchaseArrivalpurchaseArrivalUid);
	}
	
	public WmProcurearrival getwmprocure(String billGid){
		return purchaseArrivalDao.getwmprocure(billGid);
	}
	
	public Map getymsetting(){
		return purchaseArrivalDao.getymsetting();
	}
	
	public WmsTaskDetailRsp getwmtask(String billGid){
		
		Map useChildMap=getymsetting("bodyCheckFlag");//表体自定义项目质检标识
		
		WmProcurearrival wmprocure = getwmprocure(billGid);
		List<Map> purchaseArrivalc=getpurchaseArrivallist(billGid,CommonUtil.Obj2String(useChildMap.get("paramValue")));
			
		List<WmsGoods> wmstasklist = new ArrayList<WmsGoods>();
		for(int i=0;i<purchaseArrivalc.size();i++){
			AaGoods good = cacheCtrlService.getGoods((purchaseArrivalc.get(i)).get("goodsUid").toString());
			WmsGoods wmsgood = new WmsGoods();
			wmsgood.setGoodsUid(good.getGid());
			wmsgood.setGoodsBarCode(good.getGoodsbarcode());
			wmsgood.setGoodsCode(good.getGoodscode());
			wmsgood.setGoodsName(good.getGoodsname());
			wmsgood.setGoodsStandard(good.getGoodsstandard());
			wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
			wmsgood.setGoodsUnitMainName(good.getUnitName());
			wmsgood.setRemainNum(new BigDecimal((purchaseArrivalc.get(i)).get("number")!=null?(purchaseArrivalc.get(i)).get("number").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("putinNumber")!=null?(purchaseArrivalc.get(i)).get("putinNumber").toString():"0")));
			wmsgood.setRemainQuantity(new BigDecimal((purchaseArrivalc.get(i)).get("assistNumber")!=null?(purchaseArrivalc.get(i)).get("assistNumber").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("putinAssistNumber")!=null?(purchaseArrivalc.get(i)).get("putinAssistNumber").toString():"0")));
			if(good.getBinvbach()==0){
				wmsgood.setBatch("");
			}else{
				wmsgood.setBatch(CommonUtil.Obj2String(purchaseArrivalc.get(i).get("batch")));
			}
			wmsgood.setInvBatch(good.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(good.getCfree1())&&good.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(purchaseArrivalc.get(i).get("cfree1")));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			if((CommonUtil.isNullObject(good.getCfree2())?0:good.getCfree2().intValue())==1){
				
			}
			wmsgood.setCfree(cfrees);
			wmsgood.setIsInvQuality(good.getIsInvQuality());//有效期管理
			wmsgood.setMassDate(good.getMassDate());
			wmsgood.setProcureArrivalCuid((purchaseArrivalc.get(i)).get("gid").toString());
			wmstasklist.add(wmsgood);
		}
		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wmprocure.getSupplieruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		wmsTaskDetail.setBillCode(wmprocure.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wmprocure.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		return wmsTaskDetail;
	} 
	
	
	
	
	//采购质检详情
	public WmsTaskDetailRsp getwmtaskcheck(String billGid) {
		
		Map useHeadMap=getymsetting("headCheckFlag");//表头自定义项目质检标识
		Map useChildMap=getymsetting("bodyCheckFlag");//表体自定义项目质检标识
		
		WmProcurearrival wmprocure = getwmprocure(billGid);
		List<Map> purchaseArrivalc=null;
		
		if(!CommonUtil.isNullObject(useHeadMap.get("paramValue"))){//如果表头启用自定义质检字段 优先级1
			
			Class<?> plclz=wmprocure.getClass();
			String name="get"+useHeadMap.get("paramValue").toString().substring(0, 1).toUpperCase()+useHeadMap.get("paramValue").toString().substring(1, useHeadMap.get("paramValue").toString().length());
			try{
				Method m=plclz.getMethod(name, null);
				String ischeck=CommonUtil.Obj2String(m.invoke(wmprocure, null));
				if(ischeck.equalsIgnoreCase("是")){
					purchaseArrivalc= getpurchaseArrivallistcheck(billGid,"");
				}else{
					purchaseArrivalc= getpurchaseArrivallistcheck(billGid,useChildMap.get("paramValue").toString());
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){//如果表体启用自定义质检字段  优先级2
			
			purchaseArrivalc= getpurchaseArrivallistcheck(billGid,useChildMap.get("paramValue").toString());
			
		}
//		else if(useHeadMap.get("paramValue").equals("0")){    //启用标准的质检功能  优先级3
//			purchaseArrivalc= getpurchaseArrivallistcheck1(billGid);
//		}
		
		
		List wmstasklist = new ArrayList();
		for(int i=0;i<purchaseArrivalc.size();i++){
			AaGoods good = cacheCtrlService.getGoods((purchaseArrivalc.get(i)).get("goodsUid").toString());
			WmsGoods wmsgood = new WmsGoods();
			wmsgood.setGoodsUid(good.getGid());
			wmsgood.setGoodsBarCode(good.getGoodsbarcode());
			wmsgood.setGoodsCode(good.getGoodscode());
			wmsgood.setGoodsName(good.getGoodsname());
			wmsgood.setGoodsStandard(good.getGoodsstandard());
			wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
			wmsgood.setGoodsUnitMainName(good.getUnitName());
			wmsgood.setRemainNum(new BigDecimal((purchaseArrivalc.get(i)).get("number")!=null?(purchaseArrivalc.get(i)).get("number").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkOkNumber")!=null?(purchaseArrivalc.get(i)).get("checkOkNumber").toString():"0")).subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkNotOkNumber")!=null?(purchaseArrivalc.get(i)).get("checkNotOkNumber").toString():"0")));
			wmsgood.setRemainQuantity(new BigDecimal((purchaseArrivalc.get(i)).get("assistNumber")!=null?(purchaseArrivalc.get(i)).get("assistNumber").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkOkAssistNumber")!=null?(purchaseArrivalc.get(i)).get("checkOkAssistNumber").toString():"0")).subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkNotOkAssistNumber")!=null?(purchaseArrivalc.get(i)).get("checkNotOkAssistNumber").toString():"0")));
			if(good.getBinvbach()==0){
				wmsgood.setBatch("");
			}else{
				wmsgood.setBatch(CommonUtil.Obj2String(purchaseArrivalc.get(i).get("batch")));
			}
			wmsgood.setInvBatch(good.getBinvbach());
			wmsgood.setProcureArrivalCuid((purchaseArrivalc.get(i)).get("gid").toString());
			wmstasklist.add(wmsgood);
		}
		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wmprocure.getSupplieruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		wmsTaskDetail.setBillCode(wmprocure.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wmprocure.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		if(Double.parseDouble((purchaseArrivalc.get(0)).get("num").toString())<0){
			wmsTaskDetail.setIsReturn(1);
		}
		
		return wmsTaskDetail;
	}
	
	
	
	//委外检详情
	public WmsTaskDetailRsp getoMordercheck(String billGid){
		
		Map useHeadMap=getymsetting("headCheckFlag");//表头自定义项目质检标识
		Map useChildMap=getymsetting("bodyCheckFlag");//表体自定义项目质检标识
		
		WmProcurearrival wmprocure = getwmprocure(billGid);
		List<Map> purchaseArrivalc=null;
		
		if(!CommonUtil.isNullObject(useHeadMap.get("paramValue"))){//如果表头启用自定义质检字段 优先级1
			
			Class<?> plclz=wmprocure.getClass();
			String name="get"+useHeadMap.get("paramValue").toString().substring(0, 1).toUpperCase()+useHeadMap.get("paramValue").toString().substring(1, useHeadMap.get("paramValue").toString().length());
			try{
				Method m=plclz.getMethod(name, null);
				String ischeck=CommonUtil.Obj2String(m.invoke(wmprocure, null));
				if(ischeck.equalsIgnoreCase("是")){
					purchaseArrivalc= getpurchaseArrivallistcheck(billGid,"");
				}else{
					purchaseArrivalc= getpurchaseArrivallistcheck(billGid,useChildMap.get("paramValue").toString());
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){//如果表体启用自定义质检字段  优先级2
			
			purchaseArrivalc= getpurchaseArrivallistcheck(billGid,useChildMap.get("paramValue").toString());
			
		}
//		else if(useHeadMap.get("paramValue").equals("0")){    //启用标准的质检功能  优先级3
//			purchaseArrivalc= getpurchaseArrivallistcheck1(billGid);
//		}
		
		
		List<WmsGoods> wmstasklist = new ArrayList<WmsGoods>();
		for(int i=0;i<purchaseArrivalc.size();i++){
			AaGoods good = cacheCtrlService.getGoods((purchaseArrivalc.get(i)).get("goodsUid").toString());
			WmsGoods wmsgood = new WmsGoods();
			wmsgood.setGoodsUid(good.getGid());
			wmsgood.setGoodsBarCode(good.getGoodsbarcode());
			wmsgood.setGoodsCode(good.getGoodscode());
			wmsgood.setGoodsName(good.getGoodsname());
			wmsgood.setGoodsStandard(good.getGoodsstandard());
			wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
			wmsgood.setGoodsUnitMainName(good.getUnitName());
			wmsgood.setRemainNum(new BigDecimal((purchaseArrivalc.get(i)).get("number")!=null?(purchaseArrivalc.get(i)).get("number").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkOkNumber")!=null?(purchaseArrivalc.get(i)).get("checkOkNumber").toString():"0")).subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkNotOkNumber")!=null?(purchaseArrivalc.get(i)).get("checkNotOkNumber").toString():"0")));
			wmsgood.setRemainQuantity(new BigDecimal((purchaseArrivalc.get(i)).get("assistNumber")!=null?(purchaseArrivalc.get(i)).get("assistNumber").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkOkAssistNumber")!=null?(purchaseArrivalc.get(i)).get("checkOkAssistNumber").toString():"0")).subtract(new BigDecimal((purchaseArrivalc.get(i)).get("checkNotOkAssistNumber")!=null?(purchaseArrivalc.get(i)).get("checkNotOkAssistNumber").toString():"0")));
			if(good.getBinvbach()==0){
				wmsgood.setBatch("");
			}else{
				wmsgood.setBatch(CommonUtil.Obj2String(purchaseArrivalc.get(i).get("batch")));
			}
			wmsgood.setInvBatch(good.getBinvbach());
			wmsgood.setProcureArrivalCuid((purchaseArrivalc.get(i)).get("gid").toString());
			wmstasklist.add(wmsgood);
		}
		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wmprocure.getSupplieruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		wmsTaskDetail.setBillCode(wmprocure.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wmprocure.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		if(Double.parseDouble((purchaseArrivalc.get(0)).get("num").toString())<0){
			wmsTaskDetail.setIsReturn(1);
		}
		
		return wmsTaskDetail;
	}
	
	
	
	/**
	 * 
	 * @category
	 * 2016年4月25日 上午11:26:12
	 * @author 杨峥铖
	 * @param billGid
	 * @return
	 */
	public WmsTaskDetailRsp procureCheckDetail(String billGid){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		
		String condition=" cc.checkGid='"+billGid+"' and abs(okNum)>abs(isnull(putInNum,0)) ";
		List<Map> wssc=purchaseArrivalDao.getProcureCbyCheck(condition);
		wdp.setBillCode(wssc.get(0).get("checkCode").toString());
		wdp.setBillDate(wssc.get(0).get("checkDate").toString());
		wdp.setBillUid(wssc.get(0).get("gid").toString());
		wdp.setProvidercustomerName(cacheCtrlService.getProviderCustomer(wssc.get(0).get("supplierUid").toString()).getPcname());
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		for(Map map:wssc){
			WmsGoods wmsg=new WmsGoods();
			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());
			wmsg.setProcureArrivalCuid(map.get("procureArrivalCuid").toString());
			wmsg.setCheckCuid(map.get("ccgid").toString());
			AaGoods goods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsAllocationUid(wmsg.getGoodsAllocationUid());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setRemainNum(BigDecimal.valueOf(Double.parseDouble(map.get("okNum").toString())- (CommonUtil.isNullObject(map.get("putInNum"))?0:Double.parseDouble(map.get("putInNum").toString()))) );
			wmsg.setRemainQuantity(BigDecimal.valueOf(Double.parseDouble(map.get("assistOkNum").toString())- (CommonUtil.isNullObject(map.get("putInAssistNum"))?0:Double.parseDouble(map.get("putInAssistNum").toString())))  );
			wmsg.setInvBatch(goods.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(map.get("cfree1")));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			if((CommonUtil.isNullObject(goods.getCfree2())?0:goods.getCfree2().intValue())==1){
				
			}
			wmsg.setCfree(cfrees);
			wmsg.setIsInvQuality(goods.getIsInvQuality());//是否保质期管理
			wmsg.setMassDate(goods.getMassDate());//保质期天数
			wmslist.add(wmsg);
		}
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**
	 * @category 委外质检详情
	 *2016 2016年5月19日上午10:40:57
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp omCheckDetail(String billGid){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		
		String condition=" cc.checkGid='"+billGid+"' and abs(okNum)>abs(isnull(putInNum,0)) ";
		List<Map> wssc=purchaseArrivalDao.getProcureCbyCheck(condition);
		wdp.setBillCode(wssc.get(0).get("checkCode").toString());
		wdp.setBillDate(wssc.get(0).get("checkDate").toString());
		wdp.setBillUid(wssc.get(0).get("gid").toString());
		wdp.setProvidercustomerName(cacheCtrlService.getProviderCustomer(wssc.get(0).get("supplierUid").toString()).getPcname());
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		for(Map map:wssc){
			WmsGoods wmsg=new WmsGoods();
			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());
			wmsg.setProcureArrivalCuid(map.get("procureArrivalCuid").toString());
			wmsg.setCheckCuid(map.get("ccgid").toString());
			AaGoods goods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsAllocationUid(wmsg.getGoodsAllocationUid());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setRemainNum(BigDecimal.valueOf(Double.parseDouble(map.get("okNum").toString())- (CommonUtil.isNullObject(map.get("putInNum"))?0:Double.parseDouble(map.get("putInNum").toString()))) );
			wmsg.setRemainQuantity(BigDecimal.valueOf(Double.parseDouble(map.get("assistOkNum").toString())- (CommonUtil.isNullObject(map.get("putInAssistNum"))?0:Double.parseDouble(map.get("putInAssistNum").toString())))  );
			wmsg.setInvBatch(goods.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(map.get("cfree1")));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			if((CommonUtil.isNullObject(goods.getCfree2())?0:goods.getCfree2().intValue())==1){
				
			}
			wmsg.setCfree(cfrees);
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
			wmsg.setMassDate(goods.getMassDate());
			
			wmslist.add(wmsg);
		}
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**
	 * @category 获得到货单子表
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public WmProcurearrivalC getProcurearrivalC(String gid){
		String condition=" gid ='"+gid+"'";
		return purchaseArrivalDao.getProcurearrivalC(condition);
	}
	
	
	
	/**
	 * @category 获得到货单子表列表
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public List<WmProcurearrivalC> getProcurearrivalCList(String condition){
		return purchaseArrivalDao.getProcurearrivalCList(condition);
	}


	/**
	 * @category 获得采购订单子表列表
	 *2016 2018年3月9日下午3:49:45
	 *yurh
	 */
	public List<WmProcureorderC> getProcureorderCList(String condition){
		return purchaseArrivalDao.getProcureorderCList(condition);
	}
    
	/**
	 * @category 委外订单
	 * 2016年5月16日 上午11:28:50 
	 * @author Nixer wujinbo
	 * @param billGid
	 * @return
	 */
	public WmsTaskDetailRsp getmOMaterialsTask(String billGid) {
		OM_MOMain ommain=purchaseArrivalDao.getOmMainById(billGid);
		List<OM_MOMaterials> aaglist=purchaseArrivalDao.getmOMaterialsGoods(billGid);
		WmsTaskDetailRsp rsp=new WmsTaskDetailRsp();
		rsp.setBillCode(ommain.getBillCode());
		rsp.setBillDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(ommain.getBillDate()));
		rsp.setBillUid(ommain.getGid());
		AaProviderCustomer aapc=cacheCtrlService.getProviderCustomer(ommain.getSupplierUid());
	 	rsp.setProvidercustomerName(aapc.getPcname());
		rsp.setSuccess(1);
		List <WmsGoods> wmsGoodsLists=new ArrayList();
		for(OM_MOMaterials momat:aaglist){
			
			WmsGoods wms=new WmsGoods();
			AaGoods ag=cacheCtrlService.getGoods(momat.getGoodsGid());
			wms.setGoodsCode(ag.getGoodscode());
			wms.setGoodsBarCode(ag.getGoodsbarcode());
			wms.setGoodsName(ag.getGoodsname());; //物品名称
			wms.setGoodsStandard(ag.getGoodsstandard());; //规格型号
			wms.setGoodsUid(ag.getGid());;//物品uid
			wms.setGoodsUnitMainName(ag.getUnitName());; //物品单位主名称
			wms.setGoodsUnitAssistName(ag.getCassComUnitName());; //物品单位辅名称
			wms.setInvBatch(ag.getBinvbach());; //否 是否批次管理
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			if(!CommonUtil.isNullObject(ag.getCfree1())&&ag.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(momat.getCfree1()));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			wms.setCfree(cfrees);
			wms.setIsInvQuality(ag.getIsInvQuality());//有效期管理
			wms.setMassDate(ag.getMassDate());
			wms.setOmMaterialsUid(momat.getGid());;//委外订单材料表gid
			wms.setRemainNum(momat.getNumber().subtract(CommonUtil.null2BigDecimal(momat.getReceivedNum())));; //剩余应出数量(对应主计量单位)
			wmsGoodsLists.add(wms);
		}
		rsp.setWmsGoodsLists(wmsGoodsLists);
		return rsp;
	}

	
	/**
	 * @category 委外入库任务详情
	 *2016 2016年5月19日上午11:20:02
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp getOutArrivalTask(String billGid) {
		WmProcurearrival wmprocure = getwmprocure(billGid);
		List<Map> purchaseArrivalc = getpurchaseArrivallist(billGid,null);
		List<WmsGoods> wmstasklist = new ArrayList<WmsGoods>();
		for(int i=0;i<purchaseArrivalc.size();i++){
			AaGoods good = cacheCtrlService.getGoods((purchaseArrivalc.get(i)).get("goodsUid").toString());
			WmsGoods wmsgood = new WmsGoods();
			wmsgood.setGoodsUid(good.getGid());
			wmsgood.setGoodsBarCode(good.getGoodsbarcode());
			wmsgood.setGoodsCode(good.getGoodscode());
			wmsgood.setGoodsName(good.getGoodsname());
			wmsgood.setGoodsStandard(good.getGoodsstandard());
			wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
			wmsgood.setGoodsUnitMainName(good.getUnitName());
			wmsgood.setRemainNum(new BigDecimal((purchaseArrivalc.get(i)).get("number")!=null?(purchaseArrivalc.get(i)).get("number").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("putinNumber")!=null?(purchaseArrivalc.get(i)).get("putinNumber").toString():"0")));
			wmsgood.setRemainQuantity(new BigDecimal((purchaseArrivalc.get(i)).get("assistNumber")!=null?(purchaseArrivalc.get(i)).get("assistNumber").toString():"0").subtract(new BigDecimal((purchaseArrivalc.get(i)).get("putinAssistNumber")!=null?(purchaseArrivalc.get(i)).get("putinAssistNumber").toString():"0")));
			if(good.getBinvbach()==0){
				wmsgood.setBatch("");
			}else{
				wmsgood.setBatch(CommonUtil.Obj2String((purchaseArrivalc.get(i)).get("batch")));
			}
			wmsgood.setInvBatch(good.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(good.getCfree1())&&good.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(purchaseArrivalc.get(i).get("cfree1")));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			if((CommonUtil.isNullObject(good.getCfree2())?0:good.getCfree2().intValue())==1){
				
			}
			wmsgood.setCfree(cfrees);
			wmsgood.setIsInvQuality(good.getIsInvQuality());//有效期管理
			wmsgood.setMassDate(good.getMassDate());
			
			wmsgood.setProcureArrivalCuid((purchaseArrivalc.get(i)).get("gid").toString());
			wmstasklist.add(wmsgood);
		}
		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wmprocure.getSupplieruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		wmsTaskDetail.setBillCode(wmprocure.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wmprocure.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		return wmsTaskDetail;
	}



	public WmProcureorder getwmprocureorder(String billGid){
		return purchaseArrivalDao.getwmprocureorder(billGid);
	}


	public List getpurchaseOrderlist(String purchaseOrderUid,String bodyDefine) {
		String condition="";
		if(!CommonUtil.isNullObject(bodyDefine)){
			condition="  ";
		}
		condition=condition+" ";
		return purchaseArrivalDao.getpurchaseOrderlist(purchaseOrderUid,condition);
	}

	/**
	* @Desc 采购入库详情
	* @author yurh
	* @create 2018-03-07 10:56:02
	**/
	public WmsTaskDetailRsp getwmtaskForCGD(String billGid) {
		WmProcureorder wmprocure = getwmprocureorder(billGid);
		List<WmsGoods> wmstasklist = new ArrayList<WmsGoods>();
		List<Map> purchaseOrderc = getpurchaseOrderlist(billGid,null);
		for(int i=0;i<purchaseOrderc.size();i++){
			AaGoods good = cacheCtrlService.getGoods((purchaseOrderc.get(i)).get("goodsUid").toString());
			WmsGoods wmsgood = new WmsGoods();
			if(good != null){
				wmsgood.setGoodsUid(good.getGid());
				wmsgood.setGoodsBarCode(good.getGoodsbarcode());
				wmsgood.setGoodsCode(good.getGoodscode());
				wmsgood.setGoodsName(good.getGoodsname());
				wmsgood.setGoodsStandard(good.getGoodsstandard());
				wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
				wmsgood.setGoodsUnitMainName(good.getUnitName());
				wmsgood.setRemainNum(new BigDecimal((purchaseOrderc.get(i)).get("number")!=null?(purchaseOrderc.get(i)).get("number").toString():"0").subtract(new BigDecimal((purchaseOrderc.get(i)).get("putinNumber")!=null?(purchaseOrderc.get(i)).get("putinNumber").toString():"0")));
				wmsgood.setInvBatch(good.getBinvbach());

				List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();


				wmsgood.setIsInvQuality(good.getIsInvQuality());//有效期管理
				wmsgood.setMassDate(good.getMassDate());

				wmsgood.setProcureOrderCuid((purchaseOrderc.get(i)).get("gid").toString());
				wmstasklist.add(wmsgood);
			}

		}

		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wmprocure.getSupplieruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		wmsTaskDetail.setBillCode(wmprocure.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wmprocure.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		if(aaprovidercustomer != null){
			wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		}
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		wmsTaskDetail.setDptGid(wmprocure.getDepartmentuid());//部门gid
		return wmsTaskDetail;

	}
}
