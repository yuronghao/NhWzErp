package com.emi.wms.servicedata.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.android.bean.ProcessReportScanRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.android.bean.ProcessTaskDetailRsp;
import com.emi.android.bean.ProcessTaskDetailSamplingRsp;
import com.emi.android.bean.ProcessTaskMouldRsp;
import com.emi.android.bean.ProcessTaskPersonRsp;
import com.emi.android.bean.ProcessTaskStationRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmProduceProcessRouteCPre;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmReportorder;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmProduceorder;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.WmTask;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.TaskDao;
import com.emi.wms.servicedata.dao.WareHouseDao;

public class ProduceOrderService extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = 452055854590339604L;
	
	private ProduceOrderDao produceOrderDao;
	private CacheCtrlService cacheCtrlService;
	private TaskService taskService;
	private TaskDao taskDao;
	private WareHouseDao wareHouseDao;

	public ProduceOrderDao getProduceOrderDao() {
		return produceOrderDao;
	}

	@Override
	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public WareHouseDao getWareHouseDao() {
		return wareHouseDao;
	}

	public void setWareHouseDao(WareHouseDao wareHouseDao) {
		this.wareHouseDao = wareHouseDao;
	}
	public void setProduceOrderDao(ProduceOrderDao produceOrderDao) {
		this.produceOrderDao = produceOrderDao;
	}
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	/**
	 * @category 工序领料（未按仓库进行拆分）
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processMaterialOutDetail(String billGid,String taskTypeCode){
		
		
		String condition=" ppg.number-isnull(ppg.receivedNum,0)>0 and pprc.gid='"+billGid+"'";
		List<MesWmProduceProcessroutecGoods> processroutecGoods=produceOrderDao.getMesWmProduceProcessroutecGoods(condition);//从数据库查询生产订单工序领料情况
		
		condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);
		
//		//生产订单信息
//		condition=" and gid='"+mesWmProduceProcessroutes.get(0).getProduceUid()+"'";
//		WmProduceorder wmProduceorder=produceOrderDao.getProduceOrder(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		for(MesWmProduceProcessroutecGoods good:processroutecGoods){
			WmsGoods wmsg=new WmsGoods();
			wmsg.setProduceRouteCGoodsUid(good.getGid());
			AaGoods goods=cacheCtrlService.getGoods(good.getGoodsGid());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期
			wmsg.setMassDate(goods.getMassDate());
			BigDecimal num=good.getNumber();
			BigDecimal ReceivedNum=CommonUtil.isNullObject(good.getReceivedNum())?BigDecimal.valueOf(0):good.getReceivedNum();
			wmsg.setRemainNum(num.subtract(ReceivedNum));
			wmsg.setInvBatch(goods.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(good.getFree1()));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			wmsg.setCfree(cfrees);
			wmslist.add(wmsg);
//			应客户要求暂时注释 工序称重领料
//			if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_GXLL)){//普通工序领料
//				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==0){
//					wmslist.add(wmsg);
//				}
//			}else if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_CZLL)){//工序称重领料
//				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==1){
//					wmslist.add(wmsg);
//				}
//			}
			
		}
		wdp.setWmsGoodsLists(wmslist);
		
		//设置默认类别 设置默认仓库
		Map defaultRdstyle=getDefaultRdstyleByBillType(taskTypeCode);
		
		wdp.setRdStyle(CommonUtil.Obj2String(defaultRdstyle.get("crdCode")));
		wdp.setRdStyleName(CommonUtil.Obj2String(defaultRdstyle.get("crdName")));
		
		wdp.setSuccess(1);
		return wdp ;
	}
	
	/**
	 * @category 工序领料（按仓库进行拆分）
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processMaterialOutDetail(String billGid,String taskTypeCode,String whUid){
		
		String condition=" ppg.number-isnull(ppg.receivedNum,0)>0 and pprc.gid='"+billGid+"'";
		
		if(!CommonUtil.isNullObject(whUid)){//仓库不空
			condition+= " and ppg.whUid='"+whUid+"'";
		}else if(CommonUtil.isNullObject(whUid)){//仓库为空
			condition+= " and (ppg.whUid='' or ppg.whUid is null) ";
		}
		
		List<MesWmProduceProcessroutecGoods> processroutecGoods=produceOrderDao.getMesWmProduceProcessroutecGoods(condition);//从数据库查询生产订单工序领料情况
		
		condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);
		
//		//生产订单信息
//		condition=" and gid='"+mesWmProduceProcessroutes.get(0).getProduceUid()+"'";
//		WmProduceorder wmProduceorder=produceOrderDao.getProduceOrder(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		for(MesWmProduceProcessroutecGoods good:processroutecGoods){
			WmsGoods wmsg=new WmsGoods();
			wmsg.setProduceRouteCGoodsUid(good.getGid());
			AaGoods goods=cacheCtrlService.getGoods(good.getGoodsGid());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期
			wmsg.setMassDate(goods.getMassDate());
			BigDecimal num=good.getNumber();
			BigDecimal ReceivedNum=CommonUtil.isNullObject(good.getReceivedNum())?BigDecimal.valueOf(0):good.getReceivedNum();
			wmsg.setRemainNum(num.subtract(ReceivedNum));
			wmsg.setInvBatch(goods.getBinvbach());
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(CommonUtil.Obj2String(good.getFree1()));
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			wmsg.setCfree(cfrees);
			wmslist.add(wmsg);
//			应客户要求暂时注释 工序称重领料
//			if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_GXLL)){//普通工序领料
//				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==0){
//					wmslist.add(wmsg);
//				}
//			}else if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_CZLL)){//工序称重领料
//				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==1){
//					wmslist.add(wmsg);
//				}
//			}
			
		}
		wdp.setWmsGoodsLists(wmslist);
		
		//设置默认类别 设置默认仓库
		Map defaultRdstyle=getDefaultRdstyleByBillType(taskTypeCode);
		
		wdp.setRdStyle(CommonUtil.Obj2String(defaultRdstyle.get("crdCode")));
		wdp.setRdStyleName(CommonUtil.Obj2String(defaultRdstyle.get("crdName")));
		
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**
	 * @category 成品入库任务详情
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processProductInDetail(String billGid,String taskTypeCode){
		
		String condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
//		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmProduceorderC wmProduceorderC=produceOrderDao.getWmProduceorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		WmsGoods wmsg=new WmsGoods();
		wmsg.setProduceRouteCUid(mwpc.getGid());
		AaGoods goods=cacheCtrlService.getGoods(wmProduceorderC.getGoodsUid());
		wmsg.setGoodsCode(goods.getGoodscode());
		wmsg.setGoodsBarCode(goods.getGoodsbarcode());
		wmsg.setGoodsName(goods.getGoodsname());
		wmsg.setGoodsStandard(goods.getGoodsstandard());
		wmsg.setGoodsUid(goods.getGid());
		wmsg.setGoodsUnitMainName(goods.getUnitName());
		wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
		wmsg.setInvBatch(goods.getBinvbach());
		wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
		wmsg.setMassDate(goods.getMassDate());
//		wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());无批次数据来源
		
		if((CommonUtil.isNullObject(wmsg.getInvBatch())?0:wmsg.getInvBatch().intValue())==1){//如果采用批次管理
			
			if(Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)){
				String urls = "http://" + Config.INTERFACEADDRESS+ "/u890/wareHouse_getBatchCode.emi";

				String data = "";
				StringBuffer sb = Submit.sendPostRequest(data, urls);
				String s = sb.toString();
				wmsg.setBatch(s);
			}
		}
		
		List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
		if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
			WmsGoodsCfree gc=new WmsGoodsCfree();
			gc.setName("工序");
			gc.setValue(wmProduceorderC.getCfree1());
			gc.setColName("cfree1");
			gc.setIsShow(1);
			cfrees.add(gc);
		}
		
		wmsg.setCfree(cfrees);
		
		BigDecimal num=null;
		if((CommonUtil.isNullObject(mwpc.getIsCheck())?0:mwpc.getIsCheck().intValue())==0){//不质检
			num=mwpc.getReportOkNum().subtract(CommonUtil.null2BigDecimal(mwpc.getProductInNum()));//报工合格减去已入库
		}else{//质检
			num=CommonUtil.null2BigDecimal(mwpc.getProductCheckOkNum()).subtract(CommonUtil.null2BigDecimal(mwpc.getProductInNum()));//质检合格减去已入库
		}
		
		wmsg.setRemainNum(num);
		wmslist.add(wmsg);
		
		wdp.setWmsGoodsLists(wmslist);
		
		//设置默认类别 设置默认仓库
		Map defaultRdstyle=getDefaultRdstyleByBillType(taskTypeCode);
		
		wdp.setRdStyle(CommonUtil.Obj2String(defaultRdstyle.get("crdCode")));
		wdp.setRdStyleName(CommonUtil.Obj2String(defaultRdstyle.get("crdName")));
		
		//设置默认部门 根据派工单中派工人所属部门来确定
		condition=" dc.produceProcessRouteCGid='"+mwpc.getGid()+"'";
		List<Map> dis=produceOrderDao.getDispatchingorderc(condition);
		Map d=dis.get(0);
		String dptGid="";
		String dptName="";
		if(d.get("dispatchingObj").toString().equalsIgnoreCase("0")){//人
			AaPerson ap=cacheCtrlService.getPerson(d.get("personUnitVendorGid").toString());
			dptGid=ap.getDepGid();
			dptName=ap.getDepName();
		}
		
		wdp.setDptGid(dptGid);
		wdp.setDptName(dptName);
		
		wdp.setSuccess(1);
		return wdp ;
	}
	
	/**
	 * @category 根据报工单获取成品入库详情
	 *2016 2016年12月5日下午2:54:02
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processProductInDetailByReport(String billGid,String taskTypeCode){
		
		//查询报工单子表
		String condition=" ro.gid='"+billGid+"'";
		List<Map> reports=produceOrderDao.getReports(condition);
		
		condition=" gid='"+reports.get(0).get("produceProcessRouteCGid")+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmProduceorderC wmProduceorderC=produceOrderDao.getWmProduceorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
//		wdp.setBillCode(reports.get(0).get("rptcode").toString());
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(reports.get(0).get("billDate").toString());
		wdp.setBillUid(reports.get(0).get("rogid").toString());
		
		MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mwpc.getOpGid());//标准工序
		wdp.setProcessName(mesWmStandardprocess.getOpname());
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		for(Map reportMap:reports){
			
			WmsGoods wmsg=new WmsGoods();
			wmsg.setProduceRouteCUid(reportMap.get("produceProcessRouteCGid").toString() );
			wmsg.setReportOrderCUid(reportMap.get("gid").toString());
			AaGoods goods=cacheCtrlService.getGoods(wmProduceorderC.getGoodsUid());
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setInvBatch(goods.getBinvbach());
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
			wmsg.setMassDate(goods.getMassDate());
//			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());无批次数据来源
			
			if((CommonUtil.isNullObject(wmsg.getInvBatch())?0:wmsg.getInvBatch().intValue())==1){//如果采用批次管理
				
				if(!CommonUtil.isNullObject(reportMap.get("batch"))){
					wmsg.setBatch(reportMap.get("batch").toString());
					
				}else{
//					if(Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)){
//						String urls = "http://" + Config.INTERFACEADDRESS+ "/u890/wareHouse_getBatchCode.emi";
//
//						String data = "";
//						StringBuffer sb = Submit.sendPostRequest(data, urls);
//						String s = sb.toString();
//						wmsg.setBatch(s);
//					}
				}

			}
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
				WmsGoodsCfree gc=new WmsGoodsCfree();
				gc.setName("工序");
				gc.setValue(wmProduceorderC.getCfree1());
				gc.setColName("cfree1");
				gc.setIsShow(1);
				cfrees.add(gc);
			}
			
			wmsg.setCfree(cfrees);
			
			BigDecimal num=CommonUtil.object2BigDecimal(reportMap.get("reportOkNum")).subtract(CommonUtil.object2BigDecimal(reportMap.get("productInNum")));//报工合格减去已入库
			
			wmsg.setRemainNum(num);
			wmslist.add(wmsg);
		}
		
		wdp.setWmsGoodsLists(wmslist);
		
		//设置默认类别 设置默认仓库
		Map defaultRdstyle=getDefaultRdstyleByBillType(taskTypeCode);
		
		wdp.setRdStyle(CommonUtil.Obj2String(defaultRdstyle.get("crdCode")));
		wdp.setRdStyleName(CommonUtil.Obj2String(defaultRdstyle.get("crdName")));
		
		//设置默认部门 根据报工单中所属部门来确定
		String dptGid=CommonUtil.Obj2String(reports.get(0).get("deptGid"));
		String dptName="";
		if(!CommonUtil.isNullObject(dptGid)){
			AaDepartment dp=cacheCtrlService.getDepartment(dptGid);
			dptName=dp.getDepname();
			
		}else{
			
			condition=" dc.produceProcessRouteCGid='"+mwpc.getGid()+"'";
			List<Map> dis=produceOrderDao.getDispatchingorderc(condition);
			Map d=dis.get(0);
			
			if(d.get("dispatchingObj").toString().equalsIgnoreCase("0")){//人
				AaPerson ap=cacheCtrlService.getPerson(d.get("personUnitVendorGid").toString());
				dptGid=ap.getDepGid();
				if(!CommonUtil.isNullObject(ap.getDepGid())){
					AaDepartment dp=cacheCtrlService.getDepartment(ap.getDepGid());
					dptName=dp.getDepname();
				}
			}
		}

		
		wdp.setDptGid(dptGid);
		wdp.setDptName(dptName);
		
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**
	 * @category 成品入库任务详情   与processProductInDetail区别，没有自动生成批次
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processProductInDetailNoBatch(String billGid){
		
		String condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
//		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmProduceorderC wmProduceorderC=produceOrderDao.getWmProduceorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		WmsGoods wmsg=new WmsGoods();
		wmsg.setProduceRouteCUid(mwpc.getGid());
		AaGoods goods=cacheCtrlService.getGoods(wmProduceorderC.getGoodsUid());
		wmsg.setGoodsCode(goods.getGoodscode());
		wmsg.setGoodsBarCode(goods.getGoodsbarcode());
		wmsg.setGoodsName(goods.getGoodsname());
		wmsg.setGoodsStandard(goods.getGoodsstandard());
		wmsg.setGoodsUid(goods.getGid());
		wmsg.setGoodsUnitMainName(goods.getUnitName());
		wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
		wmsg.setInvBatch(goods.getBinvbach());
		wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
		wmsg.setMassDate(goods.getMassDate());
//		wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());无批次数据来源
		
//		if((CommonUtil.isNullObject(wmsg.getInvBatch())?0:wmsg.getInvBatch().intValue())==1){//如果采用批次管理
//			
//			if(Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)){
//				String urls = "http://" + Config.INTERFACEADDRESS+ "/u890/wareHouse_getBatchCode.emi";
//
//				String data = "";
//				StringBuffer sb = Submit.sendPostRequest(data, urls);
//				String s = sb.toString();
//				wmsg.setBatch(s);
//			}
//		}
		
		List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
		if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
			WmsGoodsCfree gc=new WmsGoodsCfree();
			gc.setName("工序");
			gc.setValue(wmProduceorderC.getCfree1());
			gc.setColName("cfree1");
			gc.setIsShow(1);
			cfrees.add(gc);
		}
		
		wmsg.setCfree(cfrees);
		
		BigDecimal num=null;
		if((CommonUtil.isNullObject(mwpc.getIsCheck())?0:mwpc.getIsCheck().intValue())==0){//不质检
			num=mwpc.getReportOkNum().subtract(CommonUtil.null2BigDecimal(mwpc.getProductInNum()));//报工合格减去已入库
		}else{//质检
			num=CommonUtil.null2BigDecimal(mwpc.getProductCheckOkNum()).subtract(CommonUtil.null2BigDecimal(mwpc.getProductInNum()));//质检合格减去已入库
		}
		
		wmsg.setRemainNum(num);
		wmslist.add(wmsg);
		
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**\
	 * @category 成品入库任务详情(质检)
	 * 2016年4月6日 下午1:20:28 
	 * @author Nixer wujinbo
	 * @return
	 */
	public WmsTaskDetailRsp processProductInCheckDetail(String billGid,String taskTypeCode){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		
		String condition=" cc.checkGid='"+billGid+"' and abs(cc.okNum)> abs(isnull(cc.putInNum,0)) ";
		List<Map> wssc=produceOrderDao.getSaleSendCbyCheck(condition);
		wdp.setBillCode(wssc.get(0).get("checkCode").toString());
		wdp.setBillDate(wssc.get(0).get("checkDate").toString());
		wdp.setBillUid(wssc.get(0).get("gid").toString());
		
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		String produceProcessRouteCGid="";//工艺路线子表gid
		for(Map map:wssc){
			AaGoods goods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			WmsGoods wmsg=new WmsGoods();
			wmsg.setCheckCuid(map.get("ccgid").toString());
			wmsg.setProduceRouteCUid(map.get("produceProcessRouteCGid").toString());
			if(!produceProcessRouteCGid.equals("")){
				produceProcessRouteCGid=map.get("produceProcessRouteCGid").toString();
			}
			wmsg.setInvBatch(goods.getBinvbach());
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
			wmsg.setMassDate(goods.getMassDate());
//			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());
			
			if((CommonUtil.isNullObject(wmsg.getInvBatch())?0:wmsg.getInvBatch().intValue())==1){//如果采用批次管理
				
				if(Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)){
					String urls = "http://" + Config.INTERFACEADDRESS+ "/u890/wareHouse_getBatchCode.emi";

					String data = "";
					StringBuffer sb = Submit.sendPostRequest(data, urls);
					String s = sb.toString();
					wmsg.setBatch(s);
				}
			}
			
			List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
			
			if(!CommonUtil.isNullObject(goods.getCfree1()) && goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
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
			wmsg.setWhCode("");
			wmsg.setGoodsCode(goods.getGoodscode());
			wmsg.setGoodsBarCode(goods.getGoodsbarcode());
			wmsg.setGoodsName(goods.getGoodsname());
			wmsg.setGoodsStandard(goods.getGoodsstandard());
			wmsg.setGoodsUid(goods.getGid());
			wmsg.setGoodsAllocationUid(wmsg.getGoodsAllocationUid());
			wmsg.setGoodsUnitMainName(goods.getUnitName());
			wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
			wmsg.setRemainNum(BigDecimal.valueOf(Double.parseDouble(map.get("okNum").toString())- (CommonUtil.isNullObject(map.get("putInNum"))?0:Double.parseDouble(map.get("putInNum").toString()))) );
			wmsg.setRemainQuantity(BigDecimal.valueOf(Double.parseDouble(map.get("assistOkNum").toString())- (CommonUtil.isNullObject(map.get("putInAssistNum"))?0:Double.parseDouble(map.get("putInAssistNum").toString())))  );
			wmslist.add(wmsg);
		}
		wdp.setWmsGoodsLists(wmslist);
		
		//设置默认类别 设置默认仓库
		Map defaultRdstyle=getDefaultRdstyleByBillType(taskTypeCode);
		
		wdp.setRdStyle(CommonUtil.Obj2String(defaultRdstyle.get("crdCode")));
		wdp.setRdStyleName(CommonUtil.Obj2String(defaultRdstyle.get("crdName")));
		
		//设置默认部门 根据派工单中派工人所属部门来确定
		condition=" dc.produceProcessRouteCGid='"+produceProcessRouteCGid+"'";
		List<Map> dis=produceOrderDao.getDispatchingorderc(condition);
		Map d=dis.get(0);
		String dptGid="";
		String dptName="";
		if(d.get("dispatchingObj").toString().equalsIgnoreCase("0")){//人
			AaPerson ap=cacheCtrlService.getPerson(d.get("personUnitVendorGid").toString());
			dptGid=ap.getDepGid();
			dptName=ap.getDepName();
		}
		
		wdp.setDptGid(dptGid);
		wdp.setDptName(dptName);
		
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	
	/**
	 * @category 成品质检任务详情
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processProductCheckDetail(String billGid){
		
		String condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=produceOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
//		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmProduceorderC wmProduceorderC=produceOrderDao.getWmProduceorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		WmsGoods wmsg=new WmsGoods();
		wmsg.setProduceRouteCUid(mwpc.getGid());
		AaGoods goods=cacheCtrlService.getGoods(wmProduceorderC.getGoodsUid());
		wmsg.setGoodsCode(goods.getGoodscode());
		wmsg.setGoodsBarCode(goods.getGoodsbarcode());
		wmsg.setGoodsName(goods.getGoodsname());
		wmsg.setGoodsStandard(goods.getGoodsstandard());
		wmsg.setGoodsUid(goods.getGid());
		wmsg.setGoodsUnitMainName(goods.getUnitName());
		wmsg.setGoodsUnitAssistName(goods.getCstComUnitName());
		
		List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
		if(!CommonUtil.isNullObject(goods.getCfree1()) && goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
			WmsGoodsCfree gc=new WmsGoodsCfree();
			gc.setName("工序");
			gc.setValue(wmProduceorderC.getCfree1());
			gc.setColName("cfree1");
			gc.setIsShow(1);
			cfrees.add(gc);
		}
		
		wmsg.setCfree(cfrees);
		
		//质检合格减去质检合格减去质检不合格
		BigDecimal num=mwpc.getCheckOkNum().subtract(CommonUtil.null2BigDecimal(mwpc.getProductCheckOkNum())).subtract(CommonUtil.null2BigDecimal(mwpc.getProductCheckNotOkNum()));
		
		wmsg.setRemainNum(num);
		wmslist.add(wmsg);
		
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	
	/**
	 * @category 开工时扫描条码（扫描人员、工位、任务）
	 *2016 2016年4月14日上午10:59:58
	 *JSONObject
	 *宋银海
	 */
	public ProcessStartScanRsp getInfoByBarcodeStart(String barcode,String dispatchingObj,String produceProcessRoutecGid){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessStartScanRsp processBarcodeScanRsp=new ProcessStartScanRsp();
		
		//参数 开工时工位是否必填
		Map stationIsNull=getymsetting("stationIsMust");
		processBarcodeScanRsp.setStationIsMust(Integer.valueOf(stationIsNull.get("paramValue").toString()));
		
		if(!Constants.BARCODE_WORKCENTER.equalsIgnoreCase(barCodeType) && 
				!Constants.BARCODE_PERSON.equalsIgnoreCase(barCodeType) && 
				!Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType) &&
				!Constants.BARCODE_STATION.equalsIgnoreCase(barCodeType) &&
				!Constants.BARCODE_MOULD.equalsIgnoreCase(barCodeType)) {
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("请正确扫描条码！");
				return processBarcodeScanRsp;
				
			}
		processBarcodeScanRsp.setResultType(barCodeType.toLowerCase());
		if(dispatchingObj.equalsIgnoreCase("0")){//人
			
			List<ProcessTaskPersonRsp> ptp=new ArrayList<ProcessTaskPersonRsp>();
			if(Constants.BARCODE_WORKCENTER.equalsIgnoreCase(barCodeType)){//组条码
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("该工序需选人，请重新扫描！");
			}else if(Constants.BARCODE_PERSON.equalsIgnoreCase(barCodeType)){//人员条码
				AaPerson aaPerson=getAaPerson(barcode);
				
				if(aaPerson==null){
					processBarcodeScanRsp.setSuccess(0);
					processBarcodeScanRsp.setFailInfor("人员不存在！");
					return processBarcodeScanRsp;
				}
				
				ProcessTaskPersonRsp processTaskPersonRsp=new ProcessTaskPersonRsp();
				processTaskPersonRsp.setPersonUnitVendorGid(aaPerson.getGid());
				processTaskPersonRsp.setPersonUnitVendorCode(aaPerson.getPercode());
				processTaskPersonRsp.setPersonUnitVendorName(aaPerson.getPername());
				
				ptp.add(processTaskPersonRsp);
				processBarcodeScanRsp.setSuccess(1);
				processBarcodeScanRsp.setPersonUnitVendor(ptp);
				return processBarcodeScanRsp;
			}
			
		}else if(dispatchingObj.equalsIgnoreCase("1")){//组
			List<ProcessTaskPersonRsp> ptp=new ArrayList<ProcessTaskPersonRsp>();
			
			if(Constants.BARCODE_PERSON.equalsIgnoreCase(barCodeType)){//人员条码
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("该工序需选组，请重新扫描！");
			}else if(Constants.BARCODE_WORKCENTER.equalsIgnoreCase(barCodeType)){//组条码
				AaGroup AaGroup=getMesAaGroup(barcode);
				
				if(AaGroup==null){
					processBarcodeScanRsp.setSuccess(0);
					processBarcodeScanRsp.setFailInfor("组不存在！");
					return processBarcodeScanRsp;
				}
				
				ProcessTaskPersonRsp processTaskPersonRsp=new ProcessTaskPersonRsp();
				processTaskPersonRsp.setPersonUnitVendorGid(AaGroup.getGid());
				processTaskPersonRsp.setPersonUnitVendorCode(AaGroup.getCode());
				processTaskPersonRsp.setPersonUnitVendorName(AaGroup.getGroupname());
				
				ptp.add(processTaskPersonRsp);
				processBarcodeScanRsp.setSuccess(1);
				processBarcodeScanRsp.setPersonUnitVendor(ptp);
				return processBarcodeScanRsp;
			}
			
		}
		
		if(Constants.BARCODE_STATION.equalsIgnoreCase(barCodeType)){//工位条码
//			MesAaStation mesAaStation=getMesAaStation(barcode);
			Equipment equipment=getEquipment(barcode);//设备即工位
			
			if(equipment==null){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("工位不存在");
				return processBarcodeScanRsp;
			}
			
			if(equipment.getIsDelete()!=null && equipment.getIsDelete().intValue()==1){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("该工位已删除");
				return processBarcodeScanRsp;
			}
			
			
			if(equipment.getEquipstatus()!=null && equipment.getEquipstatus().intValue()==2){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("该工位已停用");
				return processBarcodeScanRsp;
			}
			
			
			
			
			ProcessTaskStationRsp processTaskStationRsp=new ProcessTaskStationRsp();
			List<ProcessTaskStationRsp> processTaskStationRsps=new ArrayList<ProcessTaskStationRsp>();
			
			processTaskStationRsp.setStationGid(equipment.getGid());
			processTaskStationRsp.setStationCode(equipment.getEquipmentcode());
			processTaskStationRsp.setStationBarcode(equipment.getBarcode());
			processTaskStationRsp.setStationName(equipment.getEquipmentname());
			processTaskStationRsps.add(processTaskStationRsp);
			
			processBarcodeScanRsp.setSuccess(1);
			processBarcodeScanRsp.setStation(processTaskStationRsps);
		}
		
		if(Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType)){//任务条码
			
			String condition=" barcode='"+barcode+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if(mesWmProduceProcessroutec==null){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("任务不存在");
				return processBarcodeScanRsp;
			}
			
			//是否必扫模具
			processBarcodeScanRsp.setIsMustScanMould(mesWmProduceProcessroutec.getIsMustScanMould());
			
			//人
			List<ProcessTaskPersonRsp> taskPerson=new ArrayList<ProcessTaskPersonRsp>();
			if(CommonUtil.isNullObject(mesWmProduceProcessroutec.getDispatchingType())){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("请指定派工对象！");
				return processBarcodeScanRsp;
			}
			
			if(mesWmProduceProcessroutec.getDispatchingType().intValue()==1){//组
				condition=" prcd.routeCGid='"+mesWmProduceProcessroutec.getGid()+"'";
				taskPerson=produceOrderDao.getTaskPersonGroup(condition);
				processBarcodeScanRsp.setPersonUnitVendor(taskPerson);
			}else if(mesWmProduceProcessroutec.getDispatchingType().intValue()==0){//人
				condition=" prcd.routeCGid='"+mesWmProduceProcessroutec.getGid()+"'";
				taskPerson=produceOrderDao.getTaskPerson(condition);
				processBarcodeScanRsp.setPersonUnitVendor(taskPerson);
			}
			
			//设备
			condition=" prce.routeCgid='"+mesWmProduceProcessroutec.getGid()+"'";
			List<ProcessTaskStationRsp> taskStation=produceOrderDao.getTaskEquipment(condition);
			processBarcodeScanRsp.setStation(taskStation);
			
			String preGid=mesWmProduceProcessroutec.getPreGid();//当前工序中的前道工序
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ptd.setDispatchingObj(mesWmProduceProcessroutec.getDispatchingType());
			ptd.setProduceProcessRoutecGid(mesWmProduceProcessroutec.getGid());
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setGoodsCode(goods.getGoodscode());
			ptd.setGoodsStandard(CommonUtil.Obj2String(goods.getGoodsstandard()));
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			
			if(CommonUtil.isNullObject(preGid)){//如果空表示为首工序
				BigDecimal dispatchedNum=CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getDispatchedNum());//已派工数量
				ptd.setCanDisnum(mesWmProduceProcessroutec.getNumber().subtract(dispatchedNum).subtract(CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getTurnoutedNum())));//可派工数量
				
			}else{
				if(preGid.contains(",")){
					preGid=preGid.replace(",", "','");	
				}
				preGid="('"+preGid+"')";
				condition=" gid in "+preGid;
				List<MesWmProduceProcessroutec>  wmppcs=produceOrderDao.getMesWmProduceProcessroutecList(condition);//所有上道工序
				
				condition=" routeCGid ='"+mesWmProduceProcessroutec.getGid()+"'";
				List<MesWmProduceProcessRouteCPre> wmpprcs=produceOrderDao.getMesWmProduceProcessRouteCPreList(condition);//所有上道转入
				
				BigDecimal canDisnum=BigDecimal.valueOf(0);//可派工数量
				int i=0;
				for(MesWmProduceProcessroutec wmppc:wmppcs){
					
					for(MesWmProduceProcessRouteCPre wmpprc:wmpprcs){
						if(wmppc.getGid().equalsIgnoreCase(wmpprc.getPreRouteCGId())){
							
							BigDecimal v=BigDecimal.valueOf(0);//临时周转的值
							BigDecimal ratio=wmpprc.getBaseUse().divide(wmpprc.getBaseQuantity(), 4, BigDecimal.ROUND_HALF_UP);//比率
							BigDecimal numTemp=BigDecimal.valueOf(0); 
							
							if(wmppc.getIsCheck().intValue()==1){//需要质检,工序中设置了质检标志的
								
								numTemp=CommonUtil.bigDecimal2BigDecimal(wmppc.getCheckOkNum()).subtract(CommonUtil.bigDecimal2BigDecimal(wmppc.getTurnoutedNum()));//临时周转数量  质检合格减去已转出数量
								
								if(CommonUtil.isNullObject(wmppc.getCheckOkNum())){
									processBarcodeScanRsp.setSuccess(0);
									processBarcodeScanRsp.setFailInfor("存在上道工序未质检");
									return processBarcodeScanRsp;
								}
								
								if((CommonUtil.isNullObject(wmppc.getIsPass())?0:wmppc.getIsPass().intValue())==1){//如果在web端点击了放行
									
									v=numTemp.divide(ratio, 4, BigDecimal.ROUND_HALF_UP);
									
								}else{
									BigDecimal hgRate=wmppc.getCheckOkNum().divide(wmppc.getReportOkNum(),4, BigDecimal.ROUND_HALF_UP);//实际合格率
									if(hgRate.compareTo(CommonUtil.isNullObject(wmppc.getPassRate())?BigDecimal.valueOf(0):wmppc.getPassRate())==-1){//实际合格率小于指定合格率
										
										processBarcodeScanRsp.setSuccess(0);
										processBarcodeScanRsp.setFailInfor("存在实际合格率小于指定合格率！");
										return processBarcodeScanRsp;
										
									}
									v=numTemp.divide(ratio, 4, BigDecimal.ROUND_HALF_UP);
								}
								

							}else{//无需质检
								numTemp=CommonUtil.bigDecimal2BigDecimal(wmppc.getReportOkNum()).subtract(CommonUtil.bigDecimal2BigDecimal(wmppc.getRandomCheckNotOkNum())).subtract(CommonUtil.bigDecimal2BigDecimal(wmppc.getTurnoutedNum()))  ;//临时周转数量  报工合格减去抽检不合格减去已转出数量
								
								if(!CommonUtil.isNullObject(wmppc.getRandomCheckOkNum()) || !CommonUtil.isNullObject(wmppc.getRandomCheckNotOkNum())){//发生抽检
									
									if((CommonUtil.isNullObject(wmppc.getIsPass())?0:wmppc.getIsPass().intValue())==1){//工序中没有设置质检标志的，但发生了抽检的，如果在web端点击了放行
										
										v=numTemp.divide(ratio, 4, BigDecimal.ROUND_HALF_UP);
										
									}else{
										BigDecimal hgRate=(wmppc.getReportOkNum().subtract(CommonUtil.bigDecimal2BigDecimal(wmppc.getRandomCheckNotOkNum()))).divide(wmppc.getReportOkNum(),4, BigDecimal.ROUND_HALF_UP);//实际合格率
										if(hgRate.compareTo(CommonUtil.isNullObject(wmppc.getPassRate())?BigDecimal.valueOf(0):wmppc.getPassRate())==-1){//实际合格率小于指定合格率
											
											processBarcodeScanRsp.setSuccess(0);
											processBarcodeScanRsp.setFailInfor("存在实际合格率小于指定合格率！");
											return processBarcodeScanRsp;
											
										}
										
										v=numTemp.divide(ratio, 4, BigDecimal.ROUND_HALF_UP);
									}
									
								}else{//没发生抽检
									   v=numTemp.divide(ratio, 4, BigDecimal.ROUND_HALF_UP);
								}
								
							}
							
							if(i==0){
								canDisnum=v;
								i++;
							}
							
							if(canDisnum.compareTo(v)==1){//当canDisnum>v时
								canDisnum=v;
							}
							break;
						}
					}
				}
				
				
				ptd.setCanDisnum(canDisnum.subtract(CommonUtil.null2BigDecimal(mesWmProduceProcessroutec.getDispatchedNum())));//可派工数量
				
			}
			processBarcodeScanRsp.setSuccess(1);
			processBarcodeScanRsp.setTask(ptd);
		}
		
		if(Constants.BARCODE_MOULD.equalsIgnoreCase(barCodeType)){//模具条码
			
			String condition=" gid='"+produceProcessRoutecGid+"'";
			MesWmProduceProcessroutec mwpp=produceOrderDao.getMesWmProduceProcessroutec(condition);
			
			Mould m=null;
			if(mwpp.getMouldControlFetch().intValue()==1){//严格控制模具取数
				m=getMouldByBarcode(barcode, produceProcessRoutecGid);
			}else{//不严格控制
				m=produceOrderDao.getMouldByBarcode(barcode);
			}
			
			if (m==null) {
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("条码显示模具为空");
			}else{
				ProcessTaskMouldRsp mould=new ProcessTaskMouldRsp();
				mould.setMouldGid(m.getGid());
				mould.setMouldName(m.getMouldname());
				processBarcodeScanRsp.setSuccess(1);
				processBarcodeScanRsp.setMould(mould);
			}				
			
		}
		return processBarcodeScanRsp;
	}
	
	
	
	/**
	 * @category 开工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public String startWork(String uuid,MesWmDispatchingorder mesWmDispatchingorder,List<MesWmDispatchingorderc> mesWmDispatchingordercs,MesWmReportorder mr,List<MesWmReportorderc> mesWmReportordercs){
		
//		Map map=getUuidInfor(uuid);//防止重复提交
		JSONObject jobj=new JSONObject();
		
			String routecGid=mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid();
			
			Map res=produceOrderDao.getProduceCState(routecGid);
			
			if(res==null || Integer.parseInt(res.get("state").toString())!=3){
				jobj.put("success", "0");
				jobj.put("failInfor", "订单非审核状态，不允许提交");
				return jobj.toString();
			}
			
			ProcessStartScanRsp processStartScanRsp=getInfoByBarcodeStart(res.get("barcode").toString(),mesWmDispatchingorder.getDispatchingObj().toString(),routecGid);
			
			BigDecimal currentToDis=new BigDecimal(0);
			for(MesWmDispatchingorderc mwdc:mesWmDispatchingordercs){
				
				currentToDis=currentToDis.add(mwdc.getDisNum());
				
				if(!CommonUtil.isNullObject(mesWmDispatchingorder.getStationGid())){//优先取设备对应的部门
					Equipment equipment=cacheCtrlService.getMESEquipment(mesWmDispatchingorder.getStationGid());
					if(equipment!=null){
						mwdc.setDeptGid(equipment.getDepartment());//部门id
					}
					
				}else{
					if(mesWmDispatchingorder.getDispatchingObj().intValue()==0){//人
						AaPerson aaPerson=cacheCtrlService.getPerson(mwdc.getPersonUnitVendorGid());
						if(!CommonUtil.isNullObject(aaPerson)){
							mwdc.setDeptGid(aaPerson.getDepGid());//部门id
						}
						
					}else if(mesWmDispatchingorder.getDispatchingObj().intValue()==1){//组
						AaGroup aaGroup=cacheCtrlService.getAaGroup(mwdc.getPersonUnitVendorGid());//组
						MesAaWorkcenter mesAaWorkcenter=null;
						if(!CommonUtil.isNullObject(aaGroup)){
							mesAaWorkcenter=cacheCtrlService.getworkCenter(aaGroup.getWorkcenterId());//工作中心
						}
						
						if(!CommonUtil.isNullObject(mesAaWorkcenter)){
							mwdc.setDeptGid(mesAaWorkcenter.getDepUid());
						}
					}
				}
				
			}
			
			if(currentToDis.compareTo(processStartScanRsp.getTask().getCanDisnum())==1){
				jobj.put("success", "0");
				jobj.put("failInfor", "开工数量不能超过可开工总数量");
				return jobj.toString();
			}
			
			//插入开工单主表 子表
			String billcode=this.getBillId(Constants.TASKTYPE_PGD);
			mesWmDispatchingorder.setBillCode(billcode);
			
			mesWmDispatchingorder.setRepeatGid(uuid);//防止重复提交
			produceOrderDao.addMesWmDispatchingorder(mesWmDispatchingorder);//开工主表 增加模具gid
			produceOrderDao.addMesWmDispatchingorderc(mesWmDispatchingordercs);//开工子表
			
			//反填生产订单工艺路线子表已派工数量
			produceOrderDao.updProduceProcessRoutecStart(mesWmDispatchingordercs);
			
			String condition=" gid='"+mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid()+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if(mesWmProduceProcessroutec.getDispatchedNum().compareTo(mesWmProduceProcessroutec.getNumber())==1){//已派工超过订单数量
				rollBackWhenError();// 故意犯错 回滚
			}
			
			
			//报工
			//读取参数 是否开工的时候触发报工单据 isReportWhenStart 1是0否
			Map param=getymsetting("isReportWhenStart");
			if(!CommonUtil.isNullObject(param) && param.get("paramValue").toString().equalsIgnoreCase("1")){
				reportWork(uuid,mr, mesWmReportordercs);
			}
			
			
			//统一调用生成任务的方法
	/*		List<WmTask> meterialTask=new ArrayList<WmTask>();/////////////////////////////////////////////////////////////////////////// 触发材料出库任务逻辑开始
			
			String condition=" gid='"+mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid()+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			
			String nextGid=mesWmProduceProcessroutec.getNextGid();//当前工序的后续工序，后续工序只能是一个
			List<MesWmProduceProcessroutecGoods> processroutecGoods=new ArrayList<MesWmProduceProcessroutecGoods>();//判断是否需要生成材料出库任务
			
			List<WmTask> taskGoods=new ArrayList<WmTask>();                                                         //判断是否已经生成材料出库任务
			List<WmTask> taskWeightGoods=new ArrayList<WmTask>();                                                   //判断是否已经生成材料出库称重任务
			
			if(!CommonUtil.isNullObject(nextGid)){
				
				condition=" pprc.gid ='"+nextGid+"'";
				processroutecGoods=produceOrderDao.getMesWmProduceProcessroutecGoods(condition);
				
				String meterialCondition=" billgid ='"+nextGid+"' and taskTypeUid='736AB4DD-FC33-4FE6-BF65-8848FDCF3B33' ";//普通出库任务类型可以写死
				taskGoods=taskDao.getWmTaskList(meterialCondition);
				
				meterialCondition=" billgid ='"+nextGid+"' and taskTypeUid='608D9D1A-7752-43AF-934D-47962C223F9E' ";//称重出库任务类型
				taskWeightGoods=taskDao.getWmTaskList(meterialCondition);
			}
			
			
			boolean toCreateNormalTask=false;//创建普通出库任务
			boolean toCreateWeightTask=false;//创建称重出库任务
			
			for(MesWmProduceProcessroutecGoods mwpg:processroutecGoods){
				AaGoods goods=cacheCtrlService.getGoods(mwpg.getGoodsGid());
				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==0 && taskGoods.size()==0 ){
					toCreateNormalTask=true;
				}else if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==1 && taskWeightGoods.size()==0 ){
					toCreateWeightTask=true;
				}
				
				if(toCreateNormalTask && toCreateWeightTask){
					break;
				}
				
			}
			
			condition=" gid='"+nextGid+"'";
			MesWmProduceProcessroutec next=produceOrderDao.getMesWmProduceProcessroutec(condition);//对应下个工序
			
			if(toCreateNormalTask){
				WmTask wmTask=new WmTask(next.getGid(),next.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_GXLL);
				meterialTask.add(wmTask);
			}

			//按客户要求 称重领料暂时注释掉
			if(toCreateWeightTask){
				WmTask wmTask=new WmTask(next.getGid(),next.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_CZLL);
				meterialTask.add(wmTask);
			}

			//如果存在，触发下道工序领料任务
			if(meterialTask.size()>0){
				taskService.createTask(meterialTask);
			}*/
			
			//触发材料出库任务
			if(!CommonUtil.isNullObject(mesWmProduceProcessroutec.getNextGid())){
				createMeterailTask(mesWmProduceProcessroutec.getNextGid());
			}
			
//			通过索引来处理
//			insertUuidInfor(uuid);//插入uuid
			
			jobj.put("success", "1");
			jobj.put("failInfor", "开工成功");
			
		
		
		return jobj.toString();
		
	}
	
	/**
	 * @category 根据订单工艺路线子表gid 生成任务 通用方法。
	 *2016 2016年9月12日下午1:37:05
	 *boolean
	 *宋银海
	 */
	public boolean createMeterailTask(String currentProduceProcessroutecgid){
		//判断是否需要生成领料任务。
		String condition=" gid='"+currentProduceProcessroutecgid+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		condition=" pprc.gid ='"+currentProduceProcessroutecgid+"'";
		List<MesWmProduceProcessroutecGoods> processroutecGoods=produceOrderDao.getMesWmProduceProcessroutecGoods(condition);//工序材料表
		
		List<WmTask> meterialTask=new ArrayList<WmTask>();
		
		//读取参数 触发工序领料任务是否按仓库来触发
		Map param=getymsetting("isMeterialTaskByWhouse");
		if(processroutecGoods.size()>0 && param.get("paramValue").equals("0")){//普通的领料任务
			
			String meterialCondition=" billgid ='"+currentProduceProcessroutecgid+"' and taskTypeUid='736AB4DD-FC33-4FE6-BF65-8848FDCF3B33' ";//普通出库任务类型可以写死
			List<WmTask> taskGoods=taskDao.getWmTaskList(meterialCondition);
			
			if(taskGoods.size()==0){
				WmTask wmTask=new WmTask(currentProduceProcessroutecgid,mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_GXLL,null,0);
				taskService.createTask(wmTask);
			}
			
		}else if(processroutecGoods.size()>0 && param.get("paramValue").equals("1")){//按仓库触发的领料任务
			Set<String> sets=new HashSet<String>();
			for(MesWmProduceProcessroutecGoods mwpg:processroutecGoods){
				sets.add(CommonUtil.isNullObject(mwpg.getWhUid())?null:mwpg.getWhUid());
			}
			
			Iterator<String> itor=sets.iterator();
			while(itor.hasNext()){
				
				String whUid=itor.next();
				
				String meterialCondition=" billgid ='"+currentProduceProcessroutecgid+"' and taskTypeUid='736AB4DD-FC33-4FE6-BF65-8848FDCF3B33'"
						+ " and isMeterialTaskByWhouse=1 ";//普通出库任务类型可以写死
				
				if(CommonUtil.isNullObject(whUid)){
					meterialCondition+=" and (whUid ='' or whUid is null) ";
				}else if(!CommonUtil.isNullObject(whUid)){
					meterialCondition+=" and whUid='"+whUid+"'";
				}
				
				List<WmTask> taskGoods=taskDao.getWmTaskList(meterialCondition);
				
				if(taskGoods.size()==0){
					WmTask wmTask=new WmTask(currentProduceProcessroutecgid,mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_GXLL,whUid,1);
					meterialTask.add(wmTask);
				}
			}
			if(meterialTask.size()>0){
				taskService.createTask(meterialTask);
			}
			
		}
		
		return true;
	}
	
	
	/**
	 * @category 修改开工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject updateWork(String disorderGid,List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		//判断能否修改
		String condition=" disGid='"+disorderGid+"' and (isnull(reportOkNum,0)>0 or isnull(reportNotOkNum,0)>0 )";
		int count=produceOrderDao.getdisordercCount(condition);
		JSONObject obj=new JSONObject();
		if(count>0){
			obj.put("success", 0);
			obj.put("failInfor", "存在报工单据，不允许修改！");
			return obj;
		}
		
		List<MesWmDispatchingorderc> delete=new ArrayList<MesWmDispatchingorderc>();
		List<MesWmDispatchingorderc> upd=new ArrayList<MesWmDispatchingorderc>();
		for(MesWmDispatchingorderc mw:mesWmDispatchingordercs){
			if(mw.getDisNum().compareTo(new BigDecimal(0))==0){
				delete.add(mw);
			}else{
				upd.add(mw);
			}
		}
		
		produceOrderDao.updateDisc(upd);//修改开工单明细
		produceOrderDao.deleteDisc(delete);//删除开工单明细
		
		//回填订单工艺路线子表已报工数量
		condition=" produceProcessRouteCGid='"+mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid()+"'";
		Map map=produceOrderDao.getDisedNum(condition);
		produceOrderDao.updatePprc(mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid(), map);
		
		obj.put("success", 1);
		obj.put("failInfor", "修改成功");
		return obj;
	}
	
	
	/**
	 * @category 修改开工(仅调整人员，其他不修改)
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject updateWork(List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		//判断能否修改
		JSONObject obj=new JSONObject();
		
		produceOrderDao.updAdjustmentPeopleGroup(mesWmDispatchingordercs);
		
		obj.put("success", 1);
		obj.put("failInfor", "调整人员成功");
		return obj;
	}
	
	
	/**
	 * @category 删除开工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject deleteWork(String disorderGid,String pprcGid){
		//判断能否修改
		String condition=" disGid='"+disorderGid+"' and (isnull(reportOkNum,0)>0 or isnull(reportNotOkNum,0)>0 )";
		int count=produceOrderDao.getdisordercCount(condition);
		JSONObject obj=new JSONObject();
		if(count>0){
			obj.put("success", 0);
			obj.put("failInfor", "存在报工单据，不允许删除！");
			return obj;
		}
		
		produceOrderDao.deleteDis(disorderGid);
		produceOrderDao.deleteDisc(disorderGid);
		
		condition=" produceProcessRouteCGid='"+pprcGid+"'";
		Map map=produceOrderDao.getDisedNum(condition);
		produceOrderDao.updatePprc(pprcGid, map);
		
		obj.put("success", 1);
		obj.put("failInfor", "修改成功");
		return obj;
	}
	
	
	
	/**
	 * @category 修改报工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject updateReportWork(String reportOrderGid,List<MesWmReportorderc> mesWmReportordercs){
		
		//判断能否修改
		String condition=" rptGid='"+reportOrderGid+"'"
				+ " and ( isnull(checkOkNum,0)>0 or isnull(checkNotOkNum,0)>0 or isnull(randomCheckOkNum,0)>0 or"
				+ " isnull(randomCheckNotOkNum,0)>0 )";
		int count=produceOrderDao.getreportcCount(condition);
		
		JSONObject obj=new JSONObject();
		if(count>0){
			obj.put("success", 0);
			obj.put("failInfor", "存在质检单，不允许修改！");
			return obj;
		}
		
		condition=" gid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"'";
		MesWmProduceProcessroutec mwp=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前报工工序
		
		condition=" gid='"+mwp.getNextGid()+"'";
		MesWmProduceProcessroutec next=produceOrderDao.getMesWmProduceProcessroutec(condition);//后续工序
		
		if(!CommonUtil.isNullObject(next) && CommonUtil.bigDecimal2BigDecimal(next.getDispatchedNum()).compareTo(new BigDecimal(0))>0   ){
			obj.put("success", 0);
			obj.put("failInfor", "后续工序已开工，不允许修改！");
			return obj;
		}
		
		if(CommonUtil.isNullObject(next) && (
				  CommonUtil.bigDecimal2BigDecimal(mwp.getProductInNum()).compareTo(new BigDecimal(0))>0 ||
				  CommonUtil.bigDecimal2BigDecimal(mwp.getProductCheckOkNum()).compareTo(new BigDecimal(0))>0 ||
				  CommonUtil.bigDecimal2BigDecimal(mwp.getProductCheckNotOkNum()).compareTo(new BigDecimal(0))>0
				  )
			){
				obj.put("success", 0);
				obj.put("failInfor", "存在产品入库或质检，不允许删除！");
				return obj;
			}
		
		List<MesWmReportorderc> delete=new ArrayList<MesWmReportorderc>();
		List<MesWmReportorderc> upd=new ArrayList<MesWmReportorderc>();
		StringBuffer sb=new StringBuffer();
		for(MesWmReportorderc mw:mesWmReportordercs){
			if(CommonUtil.bigDecimal2BigDecimal(mw.getReportOkNum()).compareTo(new BigDecimal(0))==0 & CommonUtil.bigDecimal2BigDecimal(mw.getReportNotOkNum()).compareTo(new BigDecimal(0))==0){
				delete.add(mw);
			}else{
				upd.add(mw);
			}
			sb.append("'").append(mw.getDiscGid()).append("',");
		}
		
		produceOrderDao.updateReportc(upd);//修改报工单单明细
		produceOrderDao.deleteRptc(delete);//删除报工单明细
		
		//自动释放工序可开工数量 通过参数来控制
//		Map paramMap=getymsetting("releaseStart");
//		if(paramMap.get("paramValue").toString().equalsIgnoreCase("1")){
//			
//			//反填生产订单工艺路线子表已报工数量
//			condition=" produceProcessRouteCGid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"'";
//			Map map=produceOrderDao.getReportNum(condition);
//			produceOrderDao.updatePprcReport(mesWmReportordercs.get(0).getProduceProcessRouteCGid(), map);
//			produceOrderDao.updProduceProcessRoutecReprotReleaseStart(mesWmReportordercs.get(0).getProduceProcessRouteCGid());
//			
//			//反填派工单子表已报工数量
//			String sbs="("+sb.toString().substring(0, sb.toString().length()-1)+")";
//			condition=" produceProcessRouteCGid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"' and discGid in "+sbs;
//			List<Map> mapDis=produceOrderDao.getReportNumByDisGid(condition);
//			produceOrderDao.updatePprcReportUpt(mapDis);
//			produceOrderDao.updDisOrderReprotReleaseStartListMap(mapDis);
//			
//		}
		
		//回填订单工艺路线子表已报工合格、不合格数量											 
		condition=" produceProcessRouteCGid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"'";
		Map map=produceOrderDao.getReportNum(condition);
		produceOrderDao.updatePprcReport(mesWmReportordercs.get(0).getProduceProcessRouteCGid(), map);
		
		//回填派工单已报工数量
		String sbs="("+sb.toString().substring(0, sb.toString().length()-1)+")";
		condition=" produceProcessRouteCGid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"' and discGid in "+sbs;
		List<Map> mapDis=produceOrderDao.getReportNumByDisGid(condition);
		produceOrderDao.updatePprcReportUpt(mapDis);
		
		
		obj.put("success", 1);
		obj.put("failInfor", "");
		return obj;
	}
	
	
	
	/**
	 * @category 修改报工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject updateReportWork(List<MesWmReportorderc> mesWmReportordercs){
		
		JSONObject obj=new JSONObject();
		produceOrderDao.updateReportcPeopleGroup(mesWmReportordercs);//修改报工单单明细
		
		obj.put("success", 1);
		obj.put("failInfor", "");
		return obj;
	}
	
	
	/**
	 * @category 删除报工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public JSONObject deleteReportWork(String rptGid,String pprcGid){
		
		String condition=" rptGid='"+rptGid+"'";
		List<Map> reports=produceOrderDao.getReportList(condition);//删除前报工单子表
		//判断能否修改
		condition=" rptGid='"+rptGid+"'"
				+ " and ( isnull(checkOkNum,0)>0 or isnull(checkNotOkNum,0)>0 or "
				+ " isnull(randomCheckOkNum,0)>0 or isnull(randomCheckNotOkNum,0)>0 ) ";
		int count=produceOrderDao.getreportcCount(condition);
		
		JSONObject obj=new JSONObject();
		if(count>0){
			obj.put("success", 0);
			obj.put("failInfor", "存在质检单或报工单，不允许删除！");
			return obj;
		}
		
		condition=" gid='"+pprcGid+"'";
		MesWmProduceProcessroutec mwp=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前报工工序
		
		condition=" gid='"+mwp.getNextGid()+"'";
		MesWmProduceProcessroutec next=produceOrderDao.getMesWmProduceProcessroutec(condition);//后续工序
		
		if(!CommonUtil.isNullObject(next) && CommonUtil.bigDecimal2BigDecimal(next.getDispatchedNum()).compareTo(new BigDecimal(0))>0    ){
			obj.put("success", 0);
			obj.put("failInfor", "后续工序已开工，不允许删除！");
			return obj;
		}
		
		if(CommonUtil.isNullObject(next) && (
		  CommonUtil.bigDecimal2BigDecimal(mwp.getProductInNum()).compareTo(new BigDecimal(0))>0 ||
		  CommonUtil.bigDecimal2BigDecimal(mwp.getProductCheckOkNum()).compareTo(new BigDecimal(0))>0 ||
		  CommonUtil.bigDecimal2BigDecimal(mwp.getProductCheckNotOkNum()).compareTo(new BigDecimal(0))>0
		  )
		){
			obj.put("success", 0);
			obj.put("failInfor", "存在产品入库或质检，不允许删除！");
			return obj;
		}
		
		produceOrderDao.deleteRpt(rptGid);//删除报工单主表
		produceOrderDao.deleteRptc(rptGid);//删除报工单子表
		
		//回填订单工艺路线子表已报工合格、不合格数量											 
		condition=" produceProcessRouteCGid='"+pprcGid+"'";
		Map map=produceOrderDao.getReportNum(condition);
		produceOrderDao.updatePprcReport(pprcGid, map);
		
		//回填派工单已报工数量
		produceOrderDao.updatePprcReportDele(reports);
		
		obj.put("success", 1);
		obj.put("failInfor", "");
		return obj;
	}
	
	
	/**
	 * @category 报工时扫描条码（扫描人员、工位、任务）
	 *2016 2016年4月14日上午10:59:58
	 *JSONObject
	 *宋银海
	 */
	public ProcessReportScanRsp getInfoByBarcodeReport(String barcode){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessReportScanRsp processReportScanRsp=new ProcessReportScanRsp();
		
		
		if(Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType)){//任务条码
			
			//////////////////////////////////////////////////////////////////////////////////////////////任务开始
			String condition=" barcode='"+barcode+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();//任务
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			ptd.setCurrentDate(DateUtil.getCurrDate("yyyyMMdd"));
			
			double dispatchedNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getDispatchedNum())?0:mesWmProduceProcessroutec.getDispatchedNum().doubleValue();
			double reportOkQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportOkNum())?0:mesWmProduceProcessroutec.getReportOkNum().doubleValue();
			double reportNotOkQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportNotOkNum())?0:mesWmProduceProcessroutec.getReportNotOkNum().doubleValue();
			double reportProblemQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportProblemNum())?0:mesWmProduceProcessroutec.getReportProblemNum().doubleValue();
			
			ptd.setCanReprotNum(BigDecimal.valueOf(dispatchedNum-reportOkQuantity-reportNotOkQuantity-reportProblemQuantity));//可报工数量
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setGoodsCode(goods.getGoodscode());
			ptd.setGoodsStandard(CommonUtil.Obj2String(goods.getGoodsstandard()));
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			ptd.setIsCheck(mesWmProduceProcessroutec.getIsCheck());
			ptd.setDispatchingObj(mesWmProduceProcessroutec.getDispatchingType());
			
			//判断首道工序是否允许超派工单报工 0不允许 1允许
			Map param=getymsetting("isReportExceedDis");
			if(CommonUtil.isNullObject(mesWmProduceProcessroutec.getPreGid()) && Integer.parseInt(param.get("paramValue").toString())==1){
				ptd.setIsReportExceedDis(1);
			}else{
				ptd.setIsReportExceedDis(0);
			}
			
			processReportScanRsp.setTask(ptd);
			//////////////////////////////////////////////////////////////////////////////////////////////任务结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////人员开始
			condition=" produceProcessRouteCGid='"+mesWmProduceProcessroutec.getGid()+"' and disNum>isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0) ";
			List<Map> dismaps=produceOrderDao.getDispatchingorderc(condition);
			
			if(dismaps.size()==0){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("不存在报工数据！");
				return processReportScanRsp;
			}
			
			ptd.setNotes(CommonUtil.Obj2String(dismaps.get(0).get("mainNotes")));//派工单主体备注
			
			String dispatchingObj=dismaps.get(0).get("dispatchingObj").toString();
			List<ProcessTaskPersonRsp> personUnitVendor=new ArrayList<ProcessTaskPersonRsp>();//人员信息
			
			StringBuffer sbf=new StringBuffer();
			for(Map map:dismaps){
				sbf.append(CommonUtil.Obj2String(map.get("stationGid"))+",");
				
				double pdispatchedNum=CommonUtil.isNullObject(map.get("disNum"))?0:Double.valueOf(map.get("disNum").toString());
				double preportOkNum=CommonUtil.isNullObject(map.get("reportOkNum"))?0:Double.valueOf(map.get("reportOkNum").toString()); 
				double preportNotOkNum=CommonUtil.isNullObject(map.get("reportNotOkNum"))?0:Double.valueOf(map.get("reportNotOkNum").toString()); 
				double preportProblemNum=CommonUtil.isNullObject(map.get("reportProblemNum"))?0:Double.valueOf(map.get("reportProblemNum").toString());
				
				ProcessTaskPersonRsp ptpr=new ProcessTaskPersonRsp();
				if(dispatchingObj.equalsIgnoreCase("0")){//人
					AaPerson aaPerson=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaPerson.getGid());
					ptpr.setPersonUnitVendorCode(aaPerson.getPercode());
					ptpr.setPersonUnitVendorName(aaPerson.getPername());
					
					
				}else if(dispatchingObj.equalsIgnoreCase("1")){//组
					
					AaGroup aaGroup=cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaGroup.getGid());
					ptpr.setPersonUnitVendorCode(aaGroup.getCode());
					ptpr.setPersonUnitVendorName(aaGroup.getGroupname());
				}
				ptpr.setNotes(CommonUtil.Obj2String(map.get("detailNotes").toString()));//明细备注
				ptpr.setDiscGid(map.get("gid").toString());
				ptpr.setProduceProcessRoutecGid(map.get("produceProcessRouteCGid").toString());
				ptpr.setCanReprotNum(BigDecimal.valueOf(pdispatchedNum-preportOkNum-preportNotOkNum-preportProblemNum));
				ptpr.setNotes(CommonUtil.Obj2String(map.get("notes")));
				ptpr.setDisTime(map.get("startTime").toString());
				
				ptpr.setWorkingTime( CommonUtil.isNullObject(map.get("workingTime"))?null:new Integer(map.get("workingTime").toString()) );
				personUnitVendor.add(ptpr);
			}
			processReportScanRsp.setPersonUnitVendor(personUnitVendor);
			//////////////////////////////////////////////////////////////////////////////////////////////人员结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////工位开始
			String stations=sbf.toString();
			stations=stations.substring(0, stations.length()-1);
			String[] strs=null;
			if(stations.length()>0){
				strs=stations.split(",");
			}
			
			Set<String> set=new HashSet<String>();
			if(strs != null){
				for(String s:strs){
					if(!CommonUtil.isNullObject(s)){
						set.add(s);
					}
				}
			}
			
			List<ProcessTaskStationRsp> station=new ArrayList<ProcessTaskStationRsp>();
			
			Iterator<String> iterator=set.iterator();
			while(iterator.hasNext()){
				Equipment equipment=cacheCtrlService.getMESEquipment(iterator.next());
				ProcessTaskStationRsp pts=new ProcessTaskStationRsp();
				pts.setStationGid(equipment.getGid());
				pts.setStationCode(equipment.getEquipmentcode());
				pts.setStationName(equipment.getEquipmentname());
				station.add(pts);
			}
			processReportScanRsp.setSuccess(1);
			processReportScanRsp.setStation(station);
			//////////////////////////////////////////////////////////////////////////////////////////////工位结束
		}else{
			processReportScanRsp.setSuccess(0);
			processReportScanRsp.setFailInfor("不是任务条码，请扫描任务");
		}
		
		return processReportScanRsp;
	}


	
	/**
	 * @category 报工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public String reportWork(String uuid,MesWmReportorder mr,List<MesWmReportorderc> mesWmReportordercs){
		
		JSONObject jobj=new JSONObject();
		
		BigDecimal currentToDis=new BigDecimal(0);
		for(MesWmReportorderc mwdc:mesWmReportordercs){
			
			currentToDis=currentToDis.add(mwdc.getReportOkNum().add(mwdc.getReportNotOkNum()).add(mwdc.getReportProblemNum()));
			
			if(!CommonUtil.isNullObject(mr.getStationGid())){//优先取设备对应的部门
				Equipment equipment=cacheCtrlService.getMESEquipment(mr.getStationGid());
				if(equipment!=null){
					mwdc.setDeptGid(equipment.getDepartment());//部门id
				}
				
			}else{
				
				if(mr.getDispatchingObj().intValue()==0){//人
					AaPerson aaPerson=cacheCtrlService.getPerson(mwdc.getPersonUnitVendorGid());
					if(!CommonUtil.isNullObject(aaPerson)){
						mwdc.setDeptGid(aaPerson.getDepGid());//部门id
					}
					
				}else if(mr.getDispatchingObj().intValue()==1){//组
					AaGroup aaGroup=cacheCtrlService.getAaGroup(mwdc.getPersonUnitVendorGid());//组
					MesAaWorkcenter mesAaWorkcenter=null;
					if(!CommonUtil.isNullObject(aaGroup)){
						mesAaWorkcenter=cacheCtrlService.getworkCenter(aaGroup.getWorkcenterId());//工作中心
					}
					
					if(!CommonUtil.isNullObject(mesAaWorkcenter)){
						mwdc.setDeptGid(mesAaWorkcenter.getDepUid());
					}
					
				}
				
			}

		}
		
		String routecGid=mesWmReportordercs.get(0).getProduceProcessRouteCGid();
		Map res=produceOrderDao.getProduceCState(routecGid);
		ProcessReportScanRsp processReportScanRsp=getInfoByBarcodeReport(res.get("barcode").toString());
		
		if(currentToDis.compareTo(processReportScanRsp.getTask().getCanReprotNum())==1){
			jobj.put("success", "0");
			jobj.put("failInfor", "报工数量不能超过可报工总数量");
			return jobj.toString();
		}
		
		
		//插入报工单主表 子表
		mr.setRptcode(getBillId(Constants.TASKTYPE_BGD));
		mr.setRepeatGid(uuid);//防止重复提交
		produceOrderDao.addMesWmReportorder(mr);
		produceOrderDao.addMesWmReportorderc(mesWmReportordercs);
		
		//自动释放工序可开工数量 通过参数来控制
		Map paramMap=getymsetting("releaseStart");
		if(!CommonUtil.isNullObject(paramMap) && paramMap.get("paramValue").toString().equalsIgnoreCase("1")){
			
			//反填派工单子表已报工数量
			produceOrderDao.updDisOrderReprot(mesWmReportordercs);
			produceOrderDao.updDisOrderReprotReleaseStart(mesWmReportordercs);
			
			//反填生产订单工艺路线子表已报工数量
			produceOrderDao.updProduceProcessRoutecReprot(mesWmReportordercs);
			
			Map sumDisNum=produceOrderDao.getSumDisNum(mesWmReportordercs.get(0).getProduceProcessRouteCGid());
			String dispatchedNum="0";
			if(sumDisNum!=null && sumDisNum.get("sumDisNum")!=null){
				dispatchedNum=sumDisNum.get("sumDisNum").toString();
			}
			
			produceOrderDao.updProduceProcessRoutecReprotReleaseStart(mesWmReportordercs.get(0).getProduceProcessRouteCGid(),dispatchedNum);
			
		}else{
			//反填派工单子表已报工数量
			produceOrderDao.updDisOrderReprot(mesWmReportordercs);
			//反填生产订单工艺路线子表已报工数量
			produceOrderDao.updProduceProcessRoutecReprot(mesWmReportordercs);
		}
		
		//根据是否质检判断是否触发入库(最后一道工序，不需要质检进行触发)
		List<WmTask> inTask=new ArrayList<WmTask>();
		String condition=" gid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		BigDecimal reportAll=CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getReportOkNum()).add(CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getReportNotOkNum())).add(CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getReportProblemNum()));
		
		if(reportAll.compareTo(mesWmProduceProcessroutec.getNumber())==1){
			rollBackWhenError();// 故意犯错 回滚
		}
		
		
		if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck())==0
			&& CommonUtil.isNullObject(mesWmProduceProcessroutec.getNextGid()) ){
			
			//之前入库任务是参照工艺路线子表，后经过骆氏调研，改为参照报工单
//			condition=" billgid='"+mesWmProduceProcessroutec.getGid()+"' and tasktypeuid='C8A7F95F-4098-411B-92B4-5426E55D8A60' and state <> '2' ";//判断是否已经生成对应的入库任务(是否存在未完成的任务)
//			List<WmTask> tasks=taskDao.getWmTaskList(condition);
//			if(tasks.size()==0){
//				WmTask wmTask=new WmTask(mesWmProduceProcessroutec.getGid(),mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_CPRK);
//				inTask.add(wmTask);
//			}
			
			//改为参照报工单
			WmTask wmTask=new WmTask(mr.getGid(),mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_BGD,Constants.TASKTYPE_CPRK);
			inTask.add(wmTask);
			
		}
		
		//如果存在，触发入库任务
		if(inTask.size()>0){
			taskService.createTask(inTask);
		}
		
		jobj.put("success", "1");
		jobj.put("failInfor", "报工成功！");
		return jobj.toString();
	}
	
	
	
	public void printWhenReportWork(JSONObject jsonObject) throws UnsupportedEncodingException{
		
		String produceProcessRoutecGid=jsonObject.getString("produceProcessRoutecGid");
		String batch=CommonUtil.Obj2String(jsonObject.get("batch"));
		String printservice=URLDecoder.decode(CommonUtil.Obj2String(jsonObject.get("printservice"))  ,"UTF-8");//所选择的打印机
		String printmodel=URLDecoder.decode(CommonUtil.Obj2String(jsonObject.get("printmodel")),"UTF-8");//所选择的打印模板
		String ss="";
		
		JSONArray personJsonArray=jsonObject.getJSONArray("personGids");
		
		BigDecimal reportOkNum=new BigDecimal(0);
		BigDecimal reportNotOkNum=new BigDecimal(0);
		BigDecimal reportProblemNum=new BigDecimal(0);
		
		for(Object personObj:personJsonArray){
			JSONObject personJsonObject=(JSONObject)personObj;
			reportOkNum=reportOkNum.add(BigDecimal.valueOf(personJsonObject.getDouble("reportOkNum")));
			reportNotOkNum=reportNotOkNum.add(BigDecimal.valueOf(personJsonObject.getDouble("reportNotOkNum")));
			reportProblemNum=reportProblemNum.add(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));
		}
		
			
		String condition=" gid='"+produceProcessRoutecGid+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
		condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
		Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
		AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
		
		ss = printservice+"|"+printmodel+"|barcode="+mesWmProduceProcessroutec.getBarcode()+"|goodscode1="+goods.getGoodscode()+"&"+mesWmStandardprocess.getOpcode()+"|batch="+batch+
				"|goodsname1="+goods.getGoodsname()+"|goodsstandard1="+CommonUtil.Obj2String(goods.getGoodsstandard())+"|reportOkNum="+reportOkNum+"|date="+DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+"|cfree="+mesWmStandardprocess.getOpname();
		
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
	
	
	
	
	
	/**
	 * @category 质检时扫描条码（目前仅支持扫描任务）
	 *2016 2016年4月14日上午10:59:58
	 *JSONObject
	 *宋银海
	 */
	public ProcessReportScanRsp getInfoByBarcodeCheck(String barcode){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessReportScanRsp processReportScanRsp=new ProcessReportScanRsp();
		
		if(Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType)){//任务条码
			
			//////////////////////////////////////////////////////////////////////////////////////////////任务开始
			String condition=" barcode='"+barcode+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck().intValue())!=1){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("该道工序不需要质检");
				return processReportScanRsp;
			}
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();//任务
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			
			double rOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportOkNum())?0:mesWmProduceProcessroutec.getReportOkNum().doubleValue();//报工合格
			double rNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportNotOkNum())?0:mesWmProduceProcessroutec.getReportNotOkNum().doubleValue();//报工不合格
			double rProblemNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportProblemNum())?0:mesWmProduceProcessroutec.getReportProblemNum().doubleValue();//报工问题数量
			
			double cOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckOkNum())?0:mesWmProduceProcessroutec.getCheckOkNum().doubleValue();//质检合格
			double cNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckNotOkNum())?0:mesWmProduceProcessroutec.getCheckNotOkNum().doubleValue();//质检不合格
			
			ptd.setCanCheckNum(BigDecimal.valueOf(rOkNum-cOkNum-cNotOkNum));//可质检数量（总数）
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setGoodsCode(goods.getGoodscode());
			ptd.setGoodsStandard(CommonUtil.Obj2String(goods.getGoodsstandard()));
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			
			processReportScanRsp.setTask(ptd);
			//////////////////////////////////////////////////////////////////////////////////////////////任务结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////人员开始
			condition=" produceProcessRouteCGid='"+mesWmProduceProcessroutec.getGid()+"' and isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0)>isnull(checkOkNum,0)+isnull(checkNotOkNum,0) ";
			List<Map> dismaps=produceOrderDao.getReports(condition);
			if(dismaps.size()==0){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("不存在质检数据！");
				return processReportScanRsp;
			}
			
			
			String dispatchingObj=dismaps.get(0).get("dispatchingObj").toString();
			
			List<ProcessTaskPersonRsp> personUnitVendor=new ArrayList<ProcessTaskPersonRsp>();//人员信息
			StringBuffer sbf=new StringBuffer();
			for(Map map:dismaps){
				if(!CommonUtil.isNullObject(map.get("stationGid"))){
					sbf.append(map.get("stationGid").toString()+",");
				}
				
				double preportOkNum=CommonUtil.isNullObject(map.get("reportOkNum"))?0:Double.valueOf(map.get("reportOkNum").toString()); 
				double preportNotOkNum=CommonUtil.isNullObject(map.get("reportNotOkNum"))?0:Double.valueOf(map.get("reportNotOkNum").toString()); 
				double preportProblemOkNum=CommonUtil.isNullObject(map.get("reportProblemNum"))?0:Double.valueOf(map.get("reportProblemNum").toString());
				
				double checkOkNum=CommonUtil.isNullObject(map.get("checkOkNum"))?0:Double.valueOf(map.get("checkOkNum").toString());
				double checkNotOkNum=CommonUtil.isNullObject(map.get("checkNotOkNum"))?0:Double.valueOf(map.get("checkNotOkNum").toString());
				
				ProcessTaskPersonRsp ptpr=new ProcessTaskPersonRsp();
				if(dispatchingObj.equalsIgnoreCase("0")){//人
					AaPerson aaPerson=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaPerson.getGid());
					ptpr.setPersonUnitVendorCode(aaPerson.getPercode());
					ptpr.setPersonUnitVendorName(aaPerson.getPername());
				}else if(dispatchingObj.equalsIgnoreCase("1")){//组
					AaGroup aaGroup=cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaGroup.getGid());
					ptpr.setPersonUnitVendorCode(aaGroup.getCode());
					ptpr.setPersonUnitVendorName(aaGroup.getGroupname());
				}
				
				ptpr.setCanCheckNum(BigDecimal.valueOf(preportOkNum-checkOkNum-checkNotOkNum));//可质检数量
				
				ptpr.setDiscGid(map.get("discGid").toString());
				ptpr.setRptcGid(map.get("gid").toString());
				ptpr.setProduceProcessRoutecGid(map.get("produceProcessRouteCGid").toString());
				personUnitVendor.add(ptpr);
			}
			processReportScanRsp.setPersonUnitVendor(personUnitVendor);
			//////////////////////////////////////////////////////////////////////////////////////////////人员结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////工位开始
			String stations=sbf.toString();
			String[] strs=null;
			if(stations.length()>0){
				stations=stations.substring(0, stations.length()-1);
				strs=stations.split(",");
			}
			
			Set<String> set=new HashSet<String>();
			if(strs!=null){
				for(String s:strs){
					if(!CommonUtil.isNullObject(s)){
						set.add(s);
					}
				}
			}
			
			List<ProcessTaskStationRsp> station=new ArrayList<ProcessTaskStationRsp>();
			Iterator<String> iterator=set.iterator();
			while(iterator.hasNext()){
				Equipment equipment=cacheCtrlService.getMESEquipment(iterator.next());
				ProcessTaskStationRsp pts=new ProcessTaskStationRsp();
				pts.setStationGid(equipment.getGid());
				pts.setStationCode(equipment.getEquipmentcode());
				pts.setStationName(equipment.getEquipmentname());
				station.add(pts);
			}
			processReportScanRsp.setStation(station);
			//////////////////////////////////////////////////////////////////////////////////////////////工位结束
			processReportScanRsp.setSuccess(1);
		}else{
			processReportScanRsp.setSuccess(0);
			processReportScanRsp.setFailInfor("不是任务条码，请扫描任务");
		}
		
		return processReportScanRsp;
	}
	
	
	
	/**
	 * @category 抽检时扫描条码（目前仅支持扫描任务）
	 *2016 2016年4月14日上午10:59:58
	 *JSONObject
	 *宋银海
	 */
	public ProcessReportScanRsp getInfoByBarcodeRandomCheck(String barcode){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessReportScanRsp processReportScanRsp=new ProcessReportScanRsp();
		
		if(Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType)){//任务条码
			
			//////////////////////////////////////////////////////////////////////////////////////////////任务开始
			String condition=" barcode='"+barcode+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck().intValue())==1){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("该工序是质检工序，不需要抽检");
				return processReportScanRsp;
			}
			
			//检测下道是否已派工，如果发生派工，不允许抽检
			condition=" gid='"+mesWmProduceProcessroutec.getNextGid()+"'";
			MesWmProduceProcessroutec next=produceOrderDao.getMesWmProduceProcessroutec(condition);
			if(!CommonUtil.isNullObject(next) && !CommonUtil.isNullObject(next.getDispatchedNum()) ){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("该工序后续工序已开工，不允许抽检");
				return processReportScanRsp;
			}
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();//任务
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			
			double rOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportOkNum())?0:mesWmProduceProcessroutec.getReportOkNum().doubleValue();//报工合格
			double rNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportNotOkNum())?0:mesWmProduceProcessroutec.getReportNotOkNum().doubleValue();//报工不合格
			double rProblemNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportProblemNum())?0:mesWmProduceProcessroutec.getReportProblemNum().doubleValue();//报工问题数量
			
			double cOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckOkNum())?0:mesWmProduceProcessroutec.getCheckOkNum().doubleValue();//质检合格（工序中设置了质检）
			double cNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckNotOkNum())?0:mesWmProduceProcessroutec.getCheckNotOkNum().doubleValue();//质检不合格
			
			double randomCheckOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getRandomCheckOkNum())?0:mesWmProduceProcessroutec.getRandomCheckOkNum().doubleValue();//抽检质检合格（工序中未设置了质检）
			double randomCheckNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getRandomCheckNotOkNum())?0:mesWmProduceProcessroutec.getRandomCheckNotOkNum().doubleValue();//抽检质检不合格
			
			ptd.setCanCheckNum(BigDecimal.valueOf(rOkNum-randomCheckOkNum-randomCheckNotOkNum));//可抽检数量
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setGoodsCode(goods.getGoodscode());
			ptd.setGoodsStandard(CommonUtil.Obj2String(goods.getGoodsstandard()));
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			
			processReportScanRsp.setTask(ptd);
			//////////////////////////////////////////////////////////////////////////////////////////////任务结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////人员开始
			condition=" produceProcessRouteCGid='"+mesWmProduceProcessroutec.getGid()+"' and isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0)>isnull(checkOkNum,0)+isnull(checkNotOkNum,0) ";
			List<Map> dismaps=produceOrderDao.getReports(condition);
			if(dismaps.size()==0){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("不存在质检数据！");
				return processReportScanRsp;
			}
			
			
			String dispatchingObj=dismaps.get(0).get("dispatchingObj").toString();
			
			List<ProcessTaskPersonRsp> personUnitVendor=new ArrayList<ProcessTaskPersonRsp>();//人员信息
			StringBuffer sbf=new StringBuffer();
			for(Map map:dismaps){
				if(!CommonUtil.isNullObject(map.get("stationGid"))){
					sbf.append(map.get("stationGid").toString()+",");
				}
				
				double preportOkNum=CommonUtil.isNullObject(map.get("reportOkNum"))?0:Double.valueOf(map.get("reportOkNum").toString()); 
				double preportNotOkNum=CommonUtil.isNullObject(map.get("reportNotOkNum"))?0:Double.valueOf(map.get("reportNotOkNum").toString()); 
				double preportProblemOkNum=CommonUtil.isNullObject(map.get("reportProblemNum"))?0:Double.valueOf(map.get("reportProblemNum").toString());
				
				double checkOkNum=CommonUtil.isNullObject(map.get("checkOkNum"))?0:Double.valueOf(map.get("checkOkNum").toString());
				double checkNotOkNum=CommonUtil.isNullObject(map.get("checkNotOkNum"))?0:Double.valueOf(map.get("checkNotOkNum").toString());
				
				double radCheckOkNum=CommonUtil.isNullObject(map.get("randomCheckOkNum"))?0:Double.valueOf(map.get("randomCheckOkNum").toString());
				double radCheckNotOkNum=CommonUtil.isNullObject(map.get("randomCheckNotOkNum"))?0:Double.valueOf(map.get("randomCheckNotOkNum").toString());
				
				ProcessTaskPersonRsp ptpr=new ProcessTaskPersonRsp();
				if(dispatchingObj.equalsIgnoreCase("0")){//人
					AaPerson aaPerson=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaPerson.getGid());
					ptpr.setPersonUnitVendorCode(aaPerson.getPercode());
					ptpr.setPersonUnitVendorName(aaPerson.getPername());
				}else if(dispatchingObj.equalsIgnoreCase("1")){//组
					AaGroup aaGroup=cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
					ptpr.setPersonUnitVendorGid(aaGroup.getGid());
					ptpr.setPersonUnitVendorCode(aaGroup.getCode());
					ptpr.setPersonUnitVendorName(aaGroup.getGroupname());
				}
				
				ptpr.setCanCheckNum(BigDecimal.valueOf(preportOkNum-radCheckOkNum-radCheckNotOkNum));//可抽检数量
				
				ptpr.setDiscGid(map.get("discGid").toString());
				ptpr.setRptcGid(map.get("gid").toString());
				ptpr.setProduceProcessRoutecGid(map.get("produceProcessRouteCGid").toString());
				personUnitVendor.add(ptpr);
			}
			processReportScanRsp.setPersonUnitVendor(personUnitVendor);
			//////////////////////////////////////////////////////////////////////////////////////////////人员结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////工位开始
			String stations=sbf.toString();
			String[] strs=null;
			if(stations.length()>0){
				stations=stations.substring(0, stations.length()-1);
				strs=stations.split(",");
			}
			
			Set<String> set=new HashSet<String>();
			if(strs!=null){
				for(String s:strs){
					if(!CommonUtil.isNullObject(s)){
						set.add(s);
					}
				}
			}
			
			List<ProcessTaskStationRsp> station=new ArrayList<ProcessTaskStationRsp>();
			Iterator<String> iterator=set.iterator();
			while(iterator.hasNext()){
				Equipment equipment=cacheCtrlService.getMESEquipment(iterator.next());
				ProcessTaskStationRsp pts=new ProcessTaskStationRsp();
				pts.setStationGid(equipment.getGid());
				pts.setStationCode(equipment.getEquipmentcode());
				pts.setStationName(equipment.getEquipmentname());
				station.add(pts);
			}
			processReportScanRsp.setStation(station);
			//////////////////////////////////////////////////////////////////////////////////////////////工位结束
			processReportScanRsp.setSuccess(1);
		}else{
			processReportScanRsp.setSuccess(0);
			processReportScanRsp.setFailInfor("不是任务条码，请扫描任务");
		}
		
		return processReportScanRsp;
	}
	
	
	/**
	 * @category 首检、巡检时扫描条码(目前仅支持扫描任务)
	 *2016 2016年4月14日上午10:59:58
	 *JSONObject
	 *宋银海
	 */
	public ProcessTaskDetailSamplingRsp getInfoByBarcodeSampling(String barcode){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessTaskDetailSamplingRsp pts=new ProcessTaskDetailSamplingRsp();//任务
		
		//////////////////////////////////////////////////////////////////////////////////////////////任务开始
		String condition=" barcode='"+barcode+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
		condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
		Map processrouteInfor=produceOrderDao.getProduceProcessrouteInforMap(condition);
		AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
		
		pts.setBillCode(processrouteInfor.get("billCode").toString());
		pts.setCanSamplingNum(BigDecimal.valueOf(Double.parseDouble(processrouteInfor.get("number").toString())));
		pts.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
		pts.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
		pts.setGoodsName(goods.getGoodsname());
		pts.setGoodsCode(goods.getGoodscode());
		pts.setGoodsStandard(CommonUtil.Obj2String(goods.getGoodsstandard()));
		pts.setProduceCuid(processrouteInfor.get("produceUid").toString());
		
		//////////////////////////////////////////////////////////////////////////////////////////////任务结束
		
		pts.setSuccess(1);
		
		return pts;
	}
	
	
	
	/**
	 * @category 提交工序质检
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public void checkWork(QMCheckBill qb,List<QMCheckCbill> qbcs){
		
		//插入质检单主表 子表
		qb.setCheckCode(getBillId(Constants.TASKTYPE_BGZJ));
		produceOrderDao.addCheckBill(qb);
		produceOrderDao.addCheckBillc(qbcs);
		//反填报工单子表已质检数量
		produceOrderDao.updReprotOrderCheck(qbcs);
		//反填生产订单工艺路线子表已质检数量
		produceOrderDao.updProduceProcessRoutecCheck(qbcs);
		
		//根据是否质检判断是否触发入库(最后一道工序，需要质检触发)
		List<WmTask> inTask=new ArrayList<WmTask>();
		String condition=" gid='"+qbcs.get(0).getProduceProcessRouteCGid()+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck())==1
			&& CommonUtil.isNullObject(mesWmProduceProcessroutec.getNextGid()) ){
			
			condition=" billgid='"+mesWmProduceProcessroutec.getGid()+"' and tasktypeuid='7F143C54-195F-4FA7-A208-46E4280C06AF' and state <> '2' ";//判断是否已经生成对应的产品质检任务(是否存在未完成的任务)
			List<WmTask> tasks=taskDao.getWmTaskList(condition);
			if(tasks.size()==0){
				WmTask wmTask=new WmTask(mesWmProduceProcessroutec.getGid(),mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_CPZJ);
				inTask.add(wmTask);
			}
			
		}
		
		//如果存在，触发下道产品质检任务
		if(inTask.size()>0){
			taskService.createTask(inTask);
		}
		
	}
	
	
	/**
	 * @category 提交工序抽检
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public void randomCheckWork(QMCheckBill qb,List<QMCheckCbill> qbcs){
		
		//插入质检单主表 子表
		qb.setCheckCode(getBillId(Constants.TASKTYPE_BGZJ));
		produceOrderDao.addCheckBill(qb);
		produceOrderDao.addCheckBillc(qbcs);
		//反填报工单子表已质检数量
		produceOrderDao.updReprotOrderCheckRandom(qbcs);
		//反填生产订单工艺路线子表已质检数量
		produceOrderDao.updProduceProcessRoutecCheckRandom(qbcs);
		
	}
	
	
	/**
	 * @category 提交工序质检
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public void addSampling(QMCheckBill qb,List<QMCheckCbill> qbcs){
		
		//插入质检单主表 子表
		qb.setCheckCode(getBillId(Constants.TASKTYPE_BGZJ));
		produceOrderDao.addCheckBill(qb);
		produceOrderDao.addCheckBillc(qbcs);
		
	}
	
	
	/**
	 * @category 提交销售发货质检
	 *2016 2016年4月23日上午10:37:04
	 *void
	 *宋银海
	 */
	public void addSaleCheck(QMCheckBill qb,List<QMCheckCbill> qbcs,List<QMCheckCReasonBill> qbc2s,String taskGid){
		
		//插入质检单主表 子表  子表2
		qb.setCheckCode(getBillId(Constants.TASKTYPE_XSZJ));
		produceOrderDao.addCheckBill(qb);
		produceOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			produceOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填发货单子表已质检数量
		produceOrderDao.updSaleSendCheck(qbcs);

		//触发出库任务
		if(qbcs.get(0).getOkNum().doubleValue()>0){//正常的出库任务
			WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_XSZJ, Constants.TASKTYPE_XSCK);
			taskService.createTask(wmTask);
			//结束任务 目前不支持多次质检(结束任务)
			taskDao.updateTaskState(taskGid,"2");
		}else{
			WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_XSZJ, Constants.TASKTYPE_XSTHRK);
			taskService.createTask(wmTask);
			//结束任务 目前不支持多次质检(结束任务)
			taskDao.updateTaskState(taskGid,"2");
		}
		
	}
	
	
	/**
	 * @category 提交成品入库质检
	 *2016 2016年4月23日上午10:37:04
	 *void
	 *宋银海
	 */
	public void addProductInCheck(QMCheckBill qb,List<QMCheckCbill> qbcs,List<QMCheckCReasonBill> qbc2s,String taskGid){
		
		//插入质检单主表 子表  子表2
		qb.setCheckCode(getBillId(Constants.TASKTYPE_CPZJ));
		produceOrderDao.addCheckBill(qb);
		produceOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			produceOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填生产订单工艺路线子表已质检数量
		produceOrderDao.updProduceProcessRoutecProductCheck(qbcs);

		//结束当前任务 目前不支持多次质检(结束任务)
		taskDao.updateTaskState(taskGid,"2");
		
		//触发成品入库任务
//		WmTask task=taskDao.getTask(taskGid);
		WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_CPZJ, Constants.TASKTYPE_CPRK);
		taskService.createTask(wmTask);
		
	}
	
	
/**
 * 
 * @category 提交采购到货质检
 * 2016年4月25日 上午10:21:20
 * @author 杨峥铖
 * @param qb
 * @param qbcs
 * @param qbc2s
 */
	public void addProcureCheck(QMCheckBill qb,List<QMCheckCbill> qbcs,List<QMCheckCReasonBill> qbc2s,String taskGid){
		
		//插入质检单主表 子表  子表2
		qb.setCheckCode(getBillId(Constants.TASKTYPE_CGZJ));
		produceOrderDao.addCheckBill(qb); 
		produceOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			produceOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填到货单子表已质检数量
		produceOrderDao.updProcureSendCheck(qbcs);

		//触发到货任务
		if(qbcs.get(0).getOkNum().doubleValue()>0){//正常的到货任务
			WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_CGZJ, Constants.TASKTYPE_CGRK);
			taskService.createTask(wmTask);
			
			//结束任务 目前不支持多次质检(结束任务)
			taskDao.updateTaskState(taskGid,"2");
		}else{
			WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_CGZJ, Constants.TASKTYPE_CGTHCK);
			taskService.createTask(wmTask);
			
			//结束任务 目前不支持多次质检(结束任务)
			taskDao.updateTaskState(taskGid,"2");
		}
		
	}
	
	
	
	/**
	 * 
	 * @category 提交委外到货质检
	 * 2016年4月25日 上午10:21:20
	 * @author 杨峥铖
	 * @param qb
	 * @param qbcs
	 * @param qbc2s
	 */
		public void addOmCheck(QMCheckBill qb,List<QMCheckCbill> qbcs,List<QMCheckCReasonBill> qbc2s,String taskGid){
			
			//插入质检单主表 子表  子表2
			qb.setCheckCode(getBillId(Constants.TASKTYPE_WWCPZJ));
			produceOrderDao.addCheckBill(qb); 
			produceOrderDao.addCheckBillc(qbcs);
			if(qbc2s.size()>0){
				produceOrderDao.addCheckBillc2(qbc2s);
			}
			
			//反填到货单子表已质检数量
			produceOrderDao.updProcureSendCheck(qbcs);

			//触发到货任务
			if(qbcs.get(0).getOkNum().doubleValue()>0){//正常的到货任务
				WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_WWZJ, Constants.TASKTYPE_WWCPRK);
				taskService.createTask(wmTask);
				
				//结束任务 目前不支持多次质检(结束任务)
				taskDao.updateTaskState(taskGid,"2");
			}else{
				WmTask wmTask=new WmTask(qb.getGid(), qb.getCheckCode(), Constants.BILLGIDSOURCE_WWZJ, Constants.TASKTYPE_WWCPRKTK);
				taskService.createTask(wmTask);
				
				//结束任务 目前不支持多次质检(结束任务)
				taskDao.updateTaskState(taskGid,"2");
			}
			
		}
	
	
	
	public PageBean getproduceOrderlist(int pageIndex,int pageSize,String condition){
		return produceOrderDao.getproduceOrderlist(pageIndex,pageSize,condition);
	}
	
	public PageBean getProduceOrderListWithOldBillCode(int pageIndex,int pageSize,String condition){
		return produceOrderDao.getProduceOrderListWithOldBillCode(pageIndex, pageSize, condition);
	}
	
	public Map findproduceOrder(String purchaseArrivalgid, String changeOrder,String orgId,String sobId) {
		return produceOrderDao.findproduceOrder(purchaseArrivalgid,changeOrder,orgId,sobId);
	}
	
	public List getproduceOrderlist(String purchaseArrivalpurchaseArrivalUid) {
		
		StringBuffer sbf = new StringBuffer();
		List<Map> produceOrderc=produceOrderDao.getproduceOrderlist(purchaseArrivalpurchaseArrivalUid);
		for(int i=0;i<produceOrderc.size();i++){
			AaGoods good = cacheCtrlService.getGoods(((Map)produceOrderc.get(i)).get("goodsUid").toString());
			((Map)produceOrderc.get(i)).put("good", good);
			sbf.append("'" + ((Map)produceOrderc.get(i)).get("goodsUid").toString() + "',");
		}
		
		String condition = sbf.toString();
		if (condition.length() > 0) {
			condition = condition.substring(0, condition.length() - 1);
			condition = " and goodsUid in (" + condition + ")";
		}
		String conplus = "";
		// 查询
		List<WmAllocationstock> currentStock = wareHouseDao.getAllocationStock(condition, conplus);
		
		for(Map m:produceOrderc){
			for(WmAllocationstock wa:currentStock){
				if(m.get("goodsUid").toString().equalsIgnoreCase(wa.getGoodsuid()) &&
					CommonUtil.Obj2String(m.get("cfree1")).equalsIgnoreCase(CommonUtil.Obj2String(wa.getCfree1())) &&//此处待完善
					CommonUtil.Obj2String(m.get("cfree2")).equalsIgnoreCase(CommonUtil.Obj2String(wa.getCfree2()))
					){
					
					m.put("nowsum", CommonUtil.object2BigDecimal(m.get("nowsum")).add(wa.getNumber()));
				}
			}
		}
		
		return produceOrderc;
	}
	
	public boolean addproduceOrder(WmProduceorder WmProduceorder, List<WmProduceorderC> produceOrdercs) {
		return produceOrderDao.addproduceOrder(WmProduceorder) && produceOrderDao.addproduceOrderc(produceOrdercs);
	}
	
	public boolean addproduceOrderc(List list) {
		return produceOrderDao.addproduceOrderc(list);
	}
	
	public boolean updateproduceOrder(WmProduceorder WmProduceorder) {
		return produceOrderDao.updateproduceOrder(WmProduceorder);
	}
	
	public boolean updateproduceOrderc(List list) {
		return produceOrderDao.updateproduceOrderc(list);
	}
	
	public boolean deleteproduceOrder(String produceorderGid) {
		return produceOrderDao.deleteproduceOrder(produceorderGid);
	}
	
	public boolean deleteproduceOrderc(String produceorderGid) {
		return produceOrderDao.deleteproduceOrderc(produceorderGid);
	}
	
	public int getproduceprocess(String produceorderGid) {
		return produceOrderDao.getproduceprocess(produceorderGid);
	}
	
	
	/**
	 * @category 返回可视化工艺路线 开工报工情况
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStepSituation(String orderCid){
		String condition=" ppr.produceCuid='"+orderCid+"'";
		List<Map> maps=produceOrderDao.getProductStepSituation(condition);
		for(Map m:maps){
			MesWmStandardprocess  mws=cacheCtrlService.getMESStandardProcess(m.get("opGid").toString());
			m.put("opName", mws.getOpname());
		}
		 
		return maps;
	}
	
	/**
	 * @category 返回可视化工艺路线 领料情况
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStepMeterialOut(String orderCid){
		String condition=" ppr.produceCuid='"+orderCid+"'";
		List<Map> maps=produceOrderDao.getProductStepMeterialOut(condition);
		
		for(Map m:maps){
			MesWmStandardprocess  mws=cacheCtrlService.getMESStandardProcess(m.get("opGid").toString());
			AaGoods aaGoods=cacheCtrlService.getGoods(m.get("goodsGid").toString());
			m.put("opName", mws.getOpname());
			m.put("goodsName", aaGoods.getGoodsname());
			m.put("goodsCode", aaGoods.getGoodscode());
			m.put("goodsStandard", aaGoods.getGoodsstandard());
		}
		 
		return maps;
	}
	
	/**
	 * @category 返回可视化工艺路线工序状态
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStats(String orderCid){
		String condition=" ppr.produceCuid='"+orderCid+"'";
		List<Map> maps=produceOrderDao.getProductStepSituation(condition);
		List<Map> resultListMap=new ArrayList<Map>();
		for(Map m:maps){
			
			Map resultMap=new HashMap();
			resultMap.put("gid", m.get("gid"));
			
			BigDecimal orderNum=CommonUtil.object2BigDecimal(m.get("number"));//订单数量
			BigDecimal dispatchedNum=CommonUtil.object2BigDecimal(m.get("dispatchedNum"));//已派工数量
			BigDecimal reportNum=CommonUtil.object2BigDecimal(m.get("reportOkNum")).add(CommonUtil.object2BigDecimal(m.get("reportNotOkNum")));//报工合计
			
			if(CommonUtil.object2BigDecimal(m.get("dispatchedNum")).compareTo(new BigDecimal(0))==0){                  //未开始 0
				resultMap.put("stats", 0);
			}else if(orderNum.compareTo(dispatchedNum)==1){//订单数量>派工数量                                                          				   //进行中 1
				resultMap.put("stats", 1);
			}else if(orderNum.compareTo(dispatchedNum)==0 && dispatchedNum.compareTo(reportNum)==1 ){//订单数量=已派工数量 并且   已派工数量!=报工合计         //已完成 5
				resultMap.put("stats", 1);
			}else if(orderNum.compareTo(dispatchedNum)==0 && dispatchedNum.compareTo(reportNum)==0 ){//订单数量=已派工数量 并且   已派工数量=报工合计         //已完成 5
				resultMap.put("stats", 5);
			}
			
			resultListMap.add(resultMap);
		}
		 
		return resultListMap;
	}
	
	/**
	 * @category 扫描模具条码
	 * 2017年5月23日13:17:41
	 * @author cuixn
	 */
	public Map getMouldInfoByBarcode(String barcode){
		Mould mould=produceOrderDao.getMouldByBarcode(barcode);
		Map map=new HashMap();
		if (mould==null) {			
			map.put("success", "0");
			map.put("failInfor", "没有此模具");
			//map.put("mould", "");
			return map;
		}
		Mould m=cacheCtrlService.getMould(mould.getGid());
		map.put("success", "1");
		map.put("failInfor", "");
		map.put("mould", m);
		return map;
	}
	
	public Map getMouldInfoByBarcodeList(String condiiton,int pageIndex,int pageSize){
		PageBean pageBean=produceOrderDao.getMouldByLike(condiiton,pageIndex,pageSize);
		List<Mould> moulds=pageBean.getList();
		Map map=new HashMap();
		if (moulds.size()==0) {			
			map.put("success", "0");
			map.put("failInfor", "没有此模具");
			//map.put("mould", "");
			return map;
		}
		List<Mould> res=new JSONArray();
		for(Mould m:moulds){
			Mould mnew=cacheCtrlService.getMould(m.getGid());
			res.add(mnew);
		}
		
		map.put("success", "1");
		map.put("failInfor", "");
		map.put("mould", res);
		return map;
	}
	
	
	public static void main(String[] args) {
		String regex = "^[1-9]\\d*$"; 
	    boolean b= Pattern.matches(regex,"a"); 
	    System.out.println(b);
	}
	
	//调整订单数量
	public String changeOrderNum(HttpServletRequest request){
		
		String orderId=request.getParameter("orderId");
		String orderCid=request.getParameter("orderCid");
		String changenumber=request.getParameter("changenumber");
		String goodsId=request.getParameter("goodsId");
		
		JSONObject jobj=new JSONObject();
		
		String rex = "^[1-9]\\d*$";//正整数
		Pattern p = Pattern.compile(rex);
		Matcher m= p.matcher(changenumber);
		
		boolean b=m.matches();
		
		if(!b){
			jobj.put("success",0);
			jobj.put("failInfor","请正确输入变更数量");
			return jobj.toString();
		}
		
		//判断是否变小
		String condition=" gid='"+orderCid+"'";
		WmProduceorderC wpc=produceOrderDao.getWmProduceorderC(condition);
		if(CommonUtil.object2BigDecimal(changenumber).compareTo(wpc.getNumber())!=0   ){
			jobj.put("success",0);
			jobj.put("failInfor","变更数量需要等于订单数量");
			return jobj.toString();
		}
		
		condition=" produceCuid='"+orderCid+"'";
		MesWmProduceProcessroute mwp=produceOrderDao.getMesWmProduceProcessroute(condition);
		
		if(mwp!=null){
			condition=" produceRouteGid='"+mwp.getGid()+"' and (nextGid is null or nextGid='')";
			MesWmProduceProcessroutec mwpcLast=produceOrderDao.getMesWmProduceProcessroutec(condition);//最后一道
			
			if(CommonUtil.object2BigDecimal(changenumber).compareTo(mwpcLast.getNumber())==-1){//变更数量小于等于最后一道报工数量
				
				condition=" produceRouteGid='"+mwp.getGid()+"' and (preGid is null or preGid='')";
				List<MesWmProduceProcessroutec> mwpcFirst=produceOrderDao.getMesWmProduceProcessroutecList(condition);//所有第一道
				
				BigDecimal numTemp=new BigDecimal(0);//已派工数量
				for(MesWmProduceProcessroutec mwpc:mwpcFirst){
					BigDecimal currentRate=mwpc.getNumber().divide(mwpcLast.getNumber(),2,BigDecimal.ROUND_HALF_UP);//比率
					
					BigDecimal dised=CommonUtil.object2BigDecimal(mwpc.getDispatchedNum()).divide(currentRate,2,BigDecimal.ROUND_HALF_UP);//折算成已派工的
					if(dised.compareTo(numTemp)==1){
						numTemp=dised;
					}
				}
				
				if(CommonUtil.object2BigDecimal(changenumber).compareTo(numTemp)==-1){
					jobj.put("success",0);
					jobj.put("failInfor","变更数量需要大于首工序已开工数量");
					return jobj.toString();
				}
				
			}else if(CommonUtil.object2BigDecimal(changenumber).compareTo(mwpcLast.getNumber())==0){
				jobj.put("success",0);
				jobj.put("failInfor","数量未改变，无需变更");
				return jobj.toString();
			}
			
			//更新所有可开工数量
			produceOrderDao.uptDisNum(mwpcLast.getNumber().toPlainString(), changenumber, mwp.getGid());
			//更新所有领料数量
			produceOrderDao.uptGoodsNum(mwpcLast.getNumber().toPlainString(), changenumber, mwp.getGid());
			
		}else{
			jobj.put("success",0);
			jobj.put("failInfor","未生成工艺路线，不需变更");
			return jobj.toString();
		}
		
		jobj.put("success",1);
		jobj.put("failInfor","变更成功");

		return jobj.toString();
	}
	
	
}
