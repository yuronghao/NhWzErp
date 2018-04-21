package com.emi.wms.servicedata.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmBatch;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmSaleout;
import com.emi.wms.bean.WmSaleoutC;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.servicedata.dao.SaleDao;

/**
 * 销售订单
 * @author Administrator
 *
 */
public class SaleService extends EmiPluginService{
	private SaleDao saleDao;
	private CacheCtrlService cacheCtrlService;

	public SaleDao getSaleDao() {
		return saleDao;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}
	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}
	//销售订单列表
	public PageBean getWmSaleorderList(int pageIndex, int pageSize,
				String conditionSql) {
		return saleDao.getWmSaleorderList(pageIndex, pageSize, conditionSql);
	}
	//根据GID 查询销售主表
	public Map findWMSaleOrder(String gid)
	{
		return saleDao.findWMSaleOrder(gid);
	}
	//添加销售主表和字表
	public boolean addWMSaleOrder(WmSaleorder mSaleorder,List<WmSaleorderC> mSaleorderC)
	{
		boolean suc=saleDao.addWMSaleOrder(mSaleorder);
		if(suc)
		{
			suc=saleDao.addWmSaleorderC(mSaleorderC);
		}
		return suc;
	}
	//修改销售主表	
	public boolean updateWMSaleOrder(WmSaleorder mSaleorder)
	{
		return saleDao.updateWMSaleOrder(mSaleorder);
	}
	//修改销售主表和字表	
	public boolean updateWMSaleOrder(WmSaleorder mSaleorder,List<WmSaleorderC> mSaleorderC,List<WmSaleorderC> addList,String deleteGids)
	{
		boolean suc=saleDao.updateWMSaleOrder(mSaleorder);
		if(suc)
		{
			if(mSaleorderC.size()>0)
			{
					suc=saleDao.updateWmSaleorderC(mSaleorderC);
			}
			if(mSaleorderC.size()>0)
			{
					suc=saleDao.addWmSaleorderC(addList);
			}
			if(deleteGids.length()>0)
			{
					suc=saleDao.deleteWmSaleorderC(deleteGids.split(","));
			}
		}

		return suc;
	}
	//根据主表GID查询销售字表
	public List<Map> findWmSaleorderC(String gid)
	{
		return saleDao.findWmSaleorderC(gid);
	}
	//获取单号
	public String getBillId(String billType, String preFix) {
		return saleDao.getBillId(billType, preFix);
	}
	
	
	/**\
	 * @category 销售出库详情
	 * 2016年4月6日 下午1:20:28 
	 * @author Nixer wujinbo
	 * @return
	 */
	public WmsTaskDetailRsp saleSendDetail(String billGid){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		WmSalesend wss=saleDao.SaleSendDetail(billGid);
		
		Map useChildMap=getymsetting("bodyCheckFlag");//表体自定义项目质检标识
		String condition=" and abs(number)>abs(isnull(putoutNum,0))  ";
		
		if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){
			condition+=" and ("+useChildMap.get("paramValue").toString()+"='否' or "+useChildMap.get("paramValue").toString()+" is null) ";
		}
		
		List<WmSalesendC> wssc=saleDao.getSaleSendCbySaleSendGid(billGid,condition);
									   
		if(wssc.get(0).getNumber().doubleValue()<0){
			wdp.setIsReturn(1);
		}else{
			wdp.setIsReturn(0);
		}
		
		wdp.setBillCode(wss.getBillcode());
		wdp.setBillDate(DateUtil.dateToString(wss.getBilldate(), "yyyy-MM-dd"));
		wdp.setBillUid(wss.getGid());
		wdp.setProvidercustomerName(cacheCtrlService.getProviderCustomer(wss.getCustomeruid()).getPcname());
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		for(WmSalesendC wsc:wssc){
			AaGoods goods=cacheCtrlService.getGoods(wsc.getGoodsuid());
			WmsGoods wmsg=new WmsGoods();
			wmsg.setInvBatch(goods.getBinvbach());
			wmsg.setBatch(wsc.getBatch());
			wmsg.setSaleSendCuid(wsc.getGid());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(goods.getCfree1()) && goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(wsc.getCfree1());
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			if((CommonUtil.isNullObject(goods.getCfree2())?0:goods.getCfree2().intValue())==1){
				
			}
			
			wmsg.setWhCode(wsc.getWhcode());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsAllocationUid(wmsg.getGoodsAllocationUid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());//主单位名称
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());//辅单位名称
			wmsg.setRemainNum(BigDecimal.valueOf(Math.abs(wsc.getNumber().subtract(CommonUtil.isNullObject(wsc.getPutoutnum())?BigDecimal.valueOf(0):wsc.getPutoutnum()).doubleValue())));
			wmsg.setRemainQuantity(BigDecimal.valueOf(Math.abs((CommonUtil.isNullObject(wsc.getAssistNumber())?BigDecimal.valueOf(0):wsc.getAssistNumber().subtract(CommonUtil.isNullObject(wsc.getPutoutAssistNum())?BigDecimal.valueOf(0):wsc.getPutoutAssistNum())).doubleValue())));
			wmsg.setCfree(cfrees);
			wmslist.add(wmsg);
		}
		wdp.setSuccess(1);
		wdp.setWmsGoodsLists(wmslist);
		return wdp ;
	}
	
	/**\
	 * @category 销售出库详情(质检)
	 * 2016年4月6日 下午1:20:28 
	 * @author Nixer wujinbo
	 * @return
	 */
	public WmsTaskDetailRsp saleSendCheckDetail(String billGid){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		
		String condition=" cc.checkGid='"+billGid+"' and abs(cc.okNum)> abs(isnull(cc.putoutNum,0)) ";
		List<Map> wssc=saleDao.getSaleSendCbyCheck(condition);
		wdp.setBillCode(wssc.get(0).get("checkCode").toString());
		wdp.setBillDate(wssc.get(0).get("checkDate").toString());
		wdp.setBillUid(wssc.get(0).get("gid").toString());
		wdp.setProvidercustomerName(cacheCtrlService.getProviderCustomer(wssc.get(0).get("customerUid").toString()).getPcname());
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		for(Map map:wssc){
			AaGoods goods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			WmsGoods wmsg=new WmsGoods();
			wmsg.setCheckCuid(map.get("ccgid").toString());
			wmsg.setSaleSendCuid(map.get("saleSendCuid").toString());
			wmsg.setInvBatch(goods.getBinvbach());
			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());
			
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
			wmsg.setWhCode(map.get("whCode").toString());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsAllocationUid(wmsg.getGoodsAllocationUid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setRemainNum(BigDecimal.valueOf(Double.parseDouble(map.get("okNum").toString())- (CommonUtil.isNullObject(map.get("putoutNum"))?0:Double.parseDouble(map.get("putoutNum").toString()))) );
			wmsg.setRemainQuantity(BigDecimal.valueOf(Double.parseDouble(map.get("assistOkNum").toString())- (CommonUtil.isNullObject(map.get("putoutAssistNum"))?0:Double.parseDouble(map.get("putoutAssistNum").toString())))  );
			wmslist.add(wmsg);
		}
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	

	/**
	 * @category 获得销售质检
	 *2016 2016年4月23日上午9:26:02
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp getSaleCheck(String billGid){
		
		Map useHeadMap=getymsetting("headCheckFlag");//表头自定义项目质检标识
		Map useChildMap=getymsetting("bodyCheckFlag");//表体自定义项目质检标识
		
		WmSalesend wss=saleDao.SaleSendDetail(billGid);
		List<WmSalesendC> wssc=null;
		
		if(!CommonUtil.isNullObject(useHeadMap.get("paramValue"))){//如果表头启用自定义质检字段 优先级1
			
			Class<?> plclz=wss.getClass();
			String name="get"+useHeadMap.get("paramValue").toString().substring(0, 1).toUpperCase()+useHeadMap.get("paramValue").toString().substring(1, useHeadMap.get("paramValue").toString().length());
			try{
				Method m=plclz.getMethod(name, null);
				String ischeck=CommonUtil.Obj2String(m.invoke(wss, null));
				if(ischeck.equalsIgnoreCase("是")){
					
					String condition="and abs(number)>abs(isnull(checkOkNumber,0))+ abs(isnull(checkNotOkNumber,0)) ";
					wssc= saleDao.getSaleSendCbySaleSendGid(billGid,condition);
				}else{
					
					String condition="and abs(number)>abs(isnull(checkOkNumber,0))+ abs(isnull(checkNotOkNumber,0)) ";
					if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){
						condition+=" and "+useChildMap.get("paramValue").toString()+"='是'";
					}
					wssc= saleDao.getSaleSendCbySaleSendGid(billGid,condition);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){//如果表体启用自定义质检字段  优先级2
			
			String condition="and abs(number)>abs(isnull(checkOkNumber,0))+ abs(isnull(checkNotOkNumber,0)) ";
			if(!CommonUtil.isNullObject(useChildMap.get("paramValue"))){
				condition+=" and "+useChildMap.get("paramValue").toString()+"='是'";
			}
			wssc= saleDao.getSaleSendCbySaleSendGid(billGid,condition);
			
		}
//		else if(useHeadMap.get("paramValue").equals("0")){    //启用标准的质检功能  优先级3
//			purchaseArrivalc= getpurchaseArrivallistcheck1(billGid);
//		}
		
		
		
		
		
		List wmstasklist = new ArrayList();
		for(int i=0;i<wssc.size();i++){
			AaGoods good = cacheCtrlService.getGoods(wssc.get(i).getGoodsuid());
			WmsGoods wmsgood = new WmsGoods();
			wmsgood.setGoodsUid(good.getGid());
			wmsgood.setSaleSendCuid(wssc.get(i).getGid());
			wmsgood.setGoodsBarCode(good.getGoodsbarcode());
			wmsgood.setGoodsCode(good.getGoodscode());
			wmsgood.setGoodsName(good.getGoodsname());
			wmsgood.setGoodsStandard(good.getGoodsstandard());
			wmsgood.setGoodsUnitMainName(good.getUnitName());
			wmsgood.setGoodsUnitAssistName(good.getCstComUnitName());
			wmsgood.setRemainNum( BigDecimal.valueOf(Math.abs((wssc.get(i).getNumber().subtract(CommonUtil.isNullObject(wssc.get(i).getCheckOkNumber())?BigDecimal.valueOf(0):wssc.get(i).getCheckOkNumber()).subtract(CommonUtil.isNullObject(wssc.get(i).getCheckNotOkNumber())?BigDecimal.valueOf(0):wssc.get(i).getCheckNotOkNumber())).doubleValue())) );
			wmsgood.setRemainQuantity(BigDecimal.valueOf(Math.abs((CommonUtil.isNullObject(wssc.get(i).getAssistNumber())?BigDecimal.valueOf(0):wssc.get(i).getAssistNumber().subtract(CommonUtil.isNullObject(wssc.get(i).getCheckOkAssistNumber())?BigDecimal.valueOf(0):wssc.get(i).getCheckOkAssistNumber()).subtract(CommonUtil.isNullObject(wssc.get(i).getCheckNotOkAssistNumber())?BigDecimal.valueOf(0):wssc.get(i).getCheckNotOkAssistNumber())).doubleValue())) );
			
			wmsgood.setInvBatch(good.getBinvbach());
			if(good.getBinvbach()==0){
				wmsgood.setBatch("");
			}else{
				wmsgood.setBatch(wssc.get(i).getBatch());
			}
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(good.getCfree1())&&good.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(wssc.get(i).getCfree1());
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			wmsgood.setCfree(cfrees);
			wmstasklist.add(wmsgood);
		}
		AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(wss.getCustomeruid());
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		if(wssc.get(0).getNumber().doubleValue()<0){
			wmsTaskDetail.setIsReturn(1);
		}else{
			wmsTaskDetail.setIsReturn(0);
		}
		wmsTaskDetail.setBillCode(wss.getBillcode());
		wmsTaskDetail.setBillDate(DateUtil.dateToString(wss.getBilldate(), "yyyy-MM-dd"));
		wmsTaskDetail.setBillUid(billGid);
		wmsTaskDetail.setProvidercustomerName(aaprovidercustomer.getPcname());
		wmsTaskDetail.setSuccess(1);
		wmsTaskDetail.setWmsGoodsLists(wmstasklist);
		return wmsTaskDetail;
	}
	

	
}
