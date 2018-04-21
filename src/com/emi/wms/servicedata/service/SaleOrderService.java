package com.emi.wms.servicedata.service;

import java.io.Serializable;
import java.math.BigDecimal;
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

import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.android.bean.ProcessReportScanRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.android.bean.ProcessTaskDetailRsp;
import com.emi.android.bean.ProcessTaskDetailSamplingRsp;
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
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaStation;
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
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmPurchaserequisition;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.bean.WmTask;
import com.emi.wms.servicedata.dao.SaleOrderDao;
import com.emi.wms.servicedata.dao.TaskDao;

public class SaleOrderService extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = 452055854590339604L;
	
	private SaleOrderDao saleOrderDao;
	private CacheCtrlService cacheCtrlService;
	private TaskService taskService;
	private TaskDao taskDao;
	
	public void setSaleOrderDao(SaleOrderDao saleOrderDao) {
		this.saleOrderDao = saleOrderDao;
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
	 * @category 工序领料
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processMaterialOutDetail(String billGid,String taskTypeCode){
		
		String condition=" ppg.number-isnull(ppg.receivedNum,0)>0 and pprc.gid='"+billGid+"'";
		List<MesWmProduceProcessroutecGoods> processroutecGoods=saleOrderDao.getMesWmProduceProcessroutecGoods(condition);//从数据库查询生产订单工序领料情况
		
		condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=saleOrderDao.getMesWmProduceProcessroutec(condition);
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=saleOrderDao.getMesWmProduceProcessroute(condition);
		
//		//生产订单信息
//		condition=" and gid='"+mesWmProduceProcessroutes.get(0).getProduceUid()+"'";
//		WmSaleorder wmSaleorder=saleOrderDao.getSaleOrder(condition);
		
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
			
			if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_GXLL)){//普通工序领料
				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==0){
					wmslist.add(wmsg);
				}
			}else if(taskTypeCode.equalsIgnoreCase(Constants.TASKTYPE_CZLL)){//工序称重领料
				if(CommonUtil.bigDecimal2BigDecimal(goods.getInvWeight()).compareTo(new BigDecimal(0))==1){
					wmslist.add(wmsg);
				}
			}
			
			
		}
		wdp.setWmsGoodsLists(wmslist);
		wdp.setSuccess(1);
		return wdp ;
	}
	
	
	/**
	 * @category 成品入库任务详情
	 *2016 2016年4月11日上午8:18:30
	 *WmsTaskDetailRsp
	 *宋银海
	 */
	public WmsTaskDetailRsp processProductInDetail(String billGid){
		
		String condition=" gid='"+billGid+"'";
		MesWmProduceProcessroutec mwpc=saleOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=saleOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
//		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmSaleorderC wmSaleorderC=saleOrderDao.getWmSaleorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		WmsGoods wmsg=new WmsGoods();
		wmsg.setProduceRouteCUid(mwpc.getGid());
		AaGoods goods=cacheCtrlService.getGoods(wmSaleorderC.getGoodsUid());
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
		
		List<WmsGoodsCfree> cfrees=new ArrayList<WmsGoodsCfree>();
		if(!CommonUtil.isNullObject(goods.getCfree1())&&goods.getCfree1().intValue()==1){//目前简单的写if判断    后面优化可用反射
			WmsGoodsCfree gc=new WmsGoodsCfree();
			gc.setName("工序");
			//gc.setValue(wmSaleorderC.getCfree1());
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
	public WmsTaskDetailRsp processProductInCheckDetail(String billGid){
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		
		String condition=" cc.checkGid='"+billGid+"' and abs(cc.okNum)> abs(isnull(cc.putInNum,0)) ";
		List<Map> wssc=saleOrderDao.getSaleSendCbyCheck(condition);
		wdp.setBillCode(wssc.get(0).get("checkCode").toString());
		wdp.setBillDate(wssc.get(0).get("checkDate").toString());
		wdp.setBillUid(wssc.get(0).get("gid").toString());
		
		List<WmsGoods> wmslist=new ArrayList<WmsGoods>();
		for(Map map:wssc){
			AaGoods goods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			WmsGoods wmsg=new WmsGoods();
			wmsg.setCheckCuid(map.get("ccgid").toString());
			wmsg.setProduceRouteCUid(map.get("produceProcessRouteCGid").toString());
			wmsg.setInvBatch(goods.getBinvbach());
			wmsg.setIsInvQuality(goods.getIsInvQuality());//有效期管理
			wmsg.setMassDate(goods.getMassDate());
			wmsg.setBatch(CommonUtil.isNullObject(map.get("batch"))?"":map.get("batch").toString());
			
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
		MesWmProduceProcessroutec mwpc=saleOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
		
		condition=" gid='"+mwpc.getProduceRouteGid()+"'";
		MesWmProduceProcessroute mesWmProduceProcessroute=saleOrderDao.getMesWmProduceProcessroute(condition);//工艺路线主表
//		
//		//生产订单信息
		condition=" gid='"+mesWmProduceProcessroute.getProduceCuid()+"'";
		WmSaleorderC wmSaleorderC=saleOrderDao.getWmSaleorderC(condition);
		
		WmsTaskDetailRsp wdp=new WmsTaskDetailRsp();
		wdp.setBillCode(mwpc.getBarcode());
		wdp.setBillDate(DateUtil.dateToString(mesWmProduceProcessroute.getBillDate(), "yyyy-MM-dd"));
		wdp.setBillUid(mwpc.getGid());
		
		List<WmsGoods> wmslist= new ArrayList<WmsGoods>();
		
		WmsGoods wmsg=new WmsGoods();
		wmsg.setProduceRouteCUid(mwpc.getGid());
		AaGoods goods=cacheCtrlService.getGoods(wmSaleorderC.getGoodsUid());
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
			//gc.setValue(wmSaleorderC.getCfree1());
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
	public ProcessStartScanRsp getInfoByBarcodeStart(String barcode,String dispatchingObj){
		
		String barCodeType=barcode.substring(0, 1);//根据首字母判断编码类型
		ProcessStartScanRsp processBarcodeScanRsp=new ProcessStartScanRsp();
		
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
			
			if(Constants.BARCODE_PERSON.equalsIgnoreCase(barCodeType)){//组条码
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("该工序需选组，请重新扫描！");
			}else if(Constants.BARCODE_WORKCENTER.equalsIgnoreCase(barCodeType)){//人员条码
				MesAaWorkcenter mesAaWorkcenter=getMesAaWorkcenter(barcode);
				
				ProcessTaskPersonRsp processTaskPersonRsp=new ProcessTaskPersonRsp();
				processTaskPersonRsp.setPersonUnitVendorGid(mesAaWorkcenter.getGid());
				processTaskPersonRsp.setPersonUnitVendorCode(mesAaWorkcenter.getWccode());
				processTaskPersonRsp.setPersonUnitVendorName(mesAaWorkcenter.getWcname());
//				processBarcodeScanRsp.setPersonUnitVendor(processTaskPersonRsp);
				
			}
			
		}
		
		if(Constants.BARCODE_STATION.equalsIgnoreCase(barCodeType)){//工位条码
			MesAaStation mesAaStation=getMesAaStation(barcode);
			
			ProcessTaskStationRsp processTaskStationRsp=new ProcessTaskStationRsp();
			processBarcodeScanRsp.setSuccess(1);
			processTaskStationRsp.setStationCode(mesAaStation.getStationBarcode());
			processTaskStationRsp.setStationName(mesAaStation.getStationName());
//			processBarcodeScanRsp.setStation(processTaskStationRsp);
		}
		
		if(Constants.BARCODE_PROCESSTASK.equalsIgnoreCase(barCodeType)){//任务条码
			
			String condition=" barcode='"+barcode+"'";
			MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if(mesWmProduceProcessroutec==null){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("任务不存在");
				return processBarcodeScanRsp;
			}
			
			//人
			List<ProcessTaskPersonRsp> taskPerson=new ArrayList<ProcessTaskPersonRsp>();
			if(CommonUtil.isNullObject(mesWmProduceProcessroutec.getDispatchingType())){
				processBarcodeScanRsp.setSuccess(0);
				processBarcodeScanRsp.setFailInfor("请指定派工对象！");
				return processBarcodeScanRsp;
			}
			
			if(mesWmProduceProcessroutec.getDispatchingType().intValue()==1){//组
				condition=" prcd.routeCGid='"+mesWmProduceProcessroutec.getGid()+"'";
				taskPerson=saleOrderDao.getTaskPersonGroup(condition);
				processBarcodeScanRsp.setPersonUnitVendor(taskPerson);
			}else if(mesWmProduceProcessroutec.getDispatchingType().intValue()==0){//人
				condition=" prcd.routeCGid='"+mesWmProduceProcessroutec.getGid()+"'";
				taskPerson=saleOrderDao.getTaskPerson(condition);
				processBarcodeScanRsp.setPersonUnitVendor(taskPerson);
			}
			
			//设备
			condition=" prce.routeCgid='"+mesWmProduceProcessroutec.getGid()+"'";
			List<ProcessTaskStationRsp> taskStation=saleOrderDao.getTaskEquipment(condition);
			processBarcodeScanRsp.setStation(taskStation);
			
			String preGid=mesWmProduceProcessroutec.getPreGid();//当前工序中的前道工序
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=saleOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ptd.setDispatchingObj(mesWmProduceProcessroutec.getDispatchingType());
			ptd.setProduceProcessRoutecGid(mesWmProduceProcessroutec.getGid());
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			
			if(CommonUtil.isNullObject(preGid)){//如果空表示为首工序
				BigDecimal dispatchedNum=CommonUtil.bigDecimal2BigDecimal(mesWmProduceProcessroutec.getDispatchedNum());//已派工数量
				ptd.setCanDisnum(mesWmProduceProcessroutec.getNumber().subtract(dispatchedNum));//可派工数量
				
			}else{
				if(preGid.contains(",")){
					preGid=preGid.replace(",", "','");	
				}
				preGid="('"+preGid+"')";
				condition=" gid in "+preGid;
				List<MesWmProduceProcessroutec>  wmppcs=saleOrderDao.getMesWmProduceProcessroutecList(condition);
				
				condition=" routeCGid ='"+mesWmProduceProcessroutec.getGid()+"'";
				List<MesWmProduceProcessRouteCPre> wmpprcs=saleOrderDao.getMesWmProduceProcessRouteCPreList(condition);
				
				BigDecimal canDisnum=BigDecimal.valueOf(0);//可派工数量
				for(MesWmProduceProcessroutec wmppc:wmppcs){
					int i=0;
					for(MesWmProduceProcessRouteCPre wmpprc:wmpprcs){
						if(wmppc.getGid().equalsIgnoreCase(wmpprc.getPreRouteCGId())){
							BigDecimal v=BigDecimal.valueOf(0);
							if(wmppc.getIsCheck().intValue()==1){//需要质检
								
								if(CommonUtil.isNullObject(wmppc.getCheckOkNum())){
									processBarcodeScanRsp.setSuccess(0);
									processBarcodeScanRsp.setFailInfor("存在上道工序未质检");
									return processBarcodeScanRsp;
								}
								
								BigDecimal hgRate=wmppc.getCheckOkNum().divide(wmppc.getReportOkNum(),4, BigDecimal.ROUND_HALF_UP);//实际合格率
								if(hgRate.compareTo(CommonUtil.isNullObject(wmppc.getPassRate())?BigDecimal.valueOf(1):wmppc.getPassRate())==-1){//实际合格率小于指定合格率
									
									processBarcodeScanRsp.setSuccess(0);
									processBarcodeScanRsp.setFailInfor("存在实际合格率小于指定合格率！");
									return processBarcodeScanRsp;
									
								}
								
								v=(CommonUtil.bigDecimal2BigDecimal(wmppc.getCheckOkNum())).divide(CommonUtil.isNullObject(wmpprc.getBaseUse())?BigDecimal.valueOf(1):wmpprc.getBaseUse(), 4, BigDecimal.ROUND_HALF_UP);
								
							}else{//无需质检
								v=(CommonUtil.bigDecimal2BigDecimal(wmppc.getReportOkNum())).divide(CommonUtil.isNullObject(wmpprc.getBaseUse())?BigDecimal.valueOf(1):wmpprc.getBaseUse(), 4, BigDecimal.ROUND_HALF_UP);
							}
							
							if(i==0){
								canDisnum=v;
								i++;
							}
							
							if(canDisnum.compareTo(v)==-1){//当canDisnum<v时
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
		
		return processBarcodeScanRsp;
	}
	
	
	/**
	 * @category 开工
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public void startWork(MesWmDispatchingorder mesWmDispatchingorder,List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		
		for(MesWmDispatchingorderc mwdc:mesWmDispatchingordercs){
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
		
		//插入开工单主表 子表
		String billcode=this.getBillId(Constants.TASKTYPE_PGD);
		mesWmDispatchingorder.setBillCode(billcode);
		saleOrderDao.addMesWmDispatchingorder(mesWmDispatchingorder);
		saleOrderDao.addMesWmDispatchingorderc(mesWmDispatchingordercs);
		
		//反填生产订单工艺路线子表已派工数量
		saleOrderDao.updProduceProcessRoutecStart(mesWmDispatchingordercs);
		
		List<WmTask> meterialTask=new ArrayList<WmTask>();/////////////////////////////////////////////////////////////////////////// 触发材料出库任务逻辑开始
		
		String condition=" gid='"+mesWmDispatchingordercs.get(0).getProduceProcessRoutecGid()+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		String nextGid=mesWmProduceProcessroutec.getNextGid();//当前工序的后续工序，后续工序只能是一个
		List<MesWmProduceProcessroutecGoods> processroutecGoods=new ArrayList<MesWmProduceProcessroutecGoods>();//判断是否需要生成材料出库任务
		
		List<WmTask> taskGoods=new ArrayList<WmTask>();                                                         //判断是否已经生成材料出库任务
		List<WmTask> taskWeightGoods=new ArrayList<WmTask>();                                                   //判断是否已经生成材料出库称重任务
		
		if(!CommonUtil.isNullObject(nextGid)){
			
			condition=" pprc.gid ='"+nextGid+"'";
			processroutecGoods=saleOrderDao.getMesWmProduceProcessroutecGoods(condition);
			
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
		MesWmProduceProcessroutec next=saleOrderDao.getMesWmProduceProcessroutec(condition);//对应下个工序
		
		if(toCreateNormalTask){
			WmTask wmTask=new WmTask(next.getGid(),next.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_GXLL);
			meterialTask.add(wmTask);
		}
		
		if(toCreateWeightTask){
			WmTask wmTask=new WmTask(next.getGid(),next.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_CZLL);
			meterialTask.add(wmTask);
		}
		

		//如果存在，触发下道工序领料任务
		if(meterialTask.size()>0){
			taskService.createTask(meterialTask);
		}
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
			MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=saleOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();//任务
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			
			double dispatchedNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getDispatchedNum())?0:mesWmProduceProcessroutec.getDispatchedNum().doubleValue();
			double reportOkQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportOkNum())?0:mesWmProduceProcessroutec.getReportOkNum().doubleValue();
			double reportNotOkQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportNotOkNum())?0:mesWmProduceProcessroutec.getReportNotOkNum().doubleValue();
			double reportProblemQuantity=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportProblemNum())?0:mesWmProduceProcessroutec.getReportProblemNum().doubleValue();
			
			ptd.setCanReprotNum(BigDecimal.valueOf(dispatchedNum-reportOkQuantity-reportNotOkQuantity-reportProblemQuantity));//可报工数量
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
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
			List<Map> dismaps=saleOrderDao.getDispatchingorderc(condition);
			
			if(dismaps.size()==0){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("不存在报工数据！");
				return processReportScanRsp;
			}
			
			String dispatchingObj=dismaps.get(0).get("dispatchingObj").toString();
			
			List<ProcessTaskPersonRsp> personUnitVendor=new ArrayList<ProcessTaskPersonRsp>();//人员信息
			
			StringBuffer sbf=new StringBuffer();
			for(Map map:dismaps){
				sbf.append(map.get("stationGid").toString()+",");
				
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
				ptpr.setDiscGid(map.get("gid").toString());
				ptpr.setProduceProcessRoutecGid(map.get("produceProcessRouteCGid").toString());
				ptpr.setCanReprotNum(BigDecimal.valueOf(pdispatchedNum-preportOkNum-preportNotOkNum-preportProblemNum));
				ptpr.setNotes(CommonUtil.Obj2String(map.get("notes")));
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
	public void reportWork(MesWmReportorder mr,List<MesWmReportorderc> mesWmReportordercs){
		
		
		for(MesWmReportorderc mwdc:mesWmReportordercs){
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
		
		
		//插入报工单主表 子表
		mr.setRptcode(getBillId(Constants.TASKTYPE_BGD));
		saleOrderDao.addMesWmReportorder(mr);
		saleOrderDao.addMesWmReportorderc(mesWmReportordercs);
		//反填派工单子表已报工数量
		saleOrderDao.updDisOrderReprot(mesWmReportordercs);
		//反填生产订单工艺路线子表已报工数量
		saleOrderDao.updProduceProcessRoutecReprot(mesWmReportordercs);
		
		//根据是否质检判断是否触发入库(最后一道工序，不需要质检触发)
		List<WmTask> inTask=new ArrayList<WmTask>();
		String condition=" gid='"+mesWmReportordercs.get(0).getProduceProcessRouteCGid()+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck())==0
			&& CommonUtil.isNullObject(mesWmProduceProcessroutec.getNextGid()) ){
			
			condition=" billgid='"+mesWmProduceProcessroutec.getGid()+"' and tasktypeuid='C8A7F95F-4098-411B-92B4-5426E55D8A60' and state <> '2' ";//判断是否已经生成对应的入库任务(是否存在未完成的任务)
			List<WmTask> tasks=taskDao.getWmTaskList(condition);
			if(tasks.size()==0){
				WmTask wmTask=new WmTask(mesWmProduceProcessroutec.getGid(),mesWmProduceProcessroutec.getBarcode(),Constants.BILLGIDSOURCE_SCDDGY,Constants.TASKTYPE_CPRK);
				inTask.add(wmTask);
			}

		}
		
		//如果存在，触发入库任务
		if(inTask.size()>0){
			taskService.createTask(inTask);
		}
	}
	
	
	/**
	 * @category 质检时扫描条码（扫描人员、工位、任务）
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
			MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
			if((CommonUtil.isNullObject(mesWmProduceProcessroutec.getIsCheck())?0:mesWmProduceProcessroutec.getIsCheck().intValue())!=1){
				processReportScanRsp.setSuccess(0);
				processReportScanRsp.setFailInfor("该道工序不需要质检");
				return processReportScanRsp;
			}
			
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
			condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
			Map processrouteInfor=saleOrderDao.getProduceProcessrouteInforMap(condition);
			AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
			
			ProcessTaskDetailRsp ptd=new ProcessTaskDetailRsp();//任务
			ptd.setBillCode(processrouteInfor.get("billCode").toString());
			
			double rOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportOkNum())?0:mesWmProduceProcessroutec.getReportOkNum().doubleValue();//报工合格
			double rNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportNotOkNum())?0:mesWmProduceProcessroutec.getReportNotOkNum().doubleValue();//报工不合格
			double rProblemNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getReportProblemNum())?0:mesWmProduceProcessroutec.getReportProblemNum().doubleValue();//报工不合格
			
			double cOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckOkNum())?0:mesWmProduceProcessroutec.getCheckOkNum().doubleValue();//质检合格
			double cNotOkNum=CommonUtil.isNullObject(mesWmProduceProcessroutec.getCheckNotOkNum())?0:mesWmProduceProcessroutec.getCheckNotOkNum().doubleValue();//质检不合格
			
			ptd.setCanCheckNum(BigDecimal.valueOf(rOkNum+rNotOkNum+rProblemNum-cOkNum-cNotOkNum));//可质检数量
			ptd.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
			ptd.setGoodsName(goods.getGoodsname());
			ptd.setProcessName(mesWmStandardprocess.getOpname());
			
			processReportScanRsp.setTask(ptd);
			//////////////////////////////////////////////////////////////////////////////////////////////任务结束
			
			//////////////////////////////////////////////////////////////////////////////////////////////人员开始
			condition=" produceProcessRouteCGid='"+mesWmProduceProcessroutec.getGid()+"' and isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0)>isnull(checkOkNum,0)+isnull(checkNotOkNum,0) ";
			List<Map> dismaps=saleOrderDao.getReports(condition);
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
				
				double checkOkNum=CommonUtil.isNullObject(map.get("disNum"))?0:Double.valueOf(map.get("disNum").toString());
				double checkNotOkNum=CommonUtil.isNullObject(map.get("reportProblemNum"))?0:Double.valueOf(map.get("reportProblemNum").toString());
				
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
				
				ptpr.setCanCheckNum(BigDecimal.valueOf(preportOkNum+preportNotOkNum+preportProblemOkNum-checkOkNum-checkNotOkNum));
				
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
		MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
		
		MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mesWmProduceProcessroutec.getOpGid());//标准工序
		condition=" ppr.gid='"+mesWmProduceProcessroutec.getProduceRouteGid()+"'";
		Map processrouteInfor=saleOrderDao.getProduceProcessrouteInforMap(condition);
		AaGoods goods=cacheCtrlService.getGoods(processrouteInfor.get("goodsUid").toString());//获得订单工艺路线产品信息
		
		pts.setBillCode(processrouteInfor.get("billCode").toString());
		pts.setCanSamplingNum(BigDecimal.valueOf(Double.parseDouble(processrouteInfor.get("number").toString())));
		pts.setStartTime(DateUtil.stringtoDate(processrouteInfor.get("startDate").toString(), DateUtil.LONG_DATE_FORMAT));
		pts.setEndTime(DateUtil.stringtoDate(processrouteInfor.get("endDate").toString(), DateUtil.LONG_DATE_FORMAT));
		pts.setGoodsName(goods.getGoodsname());
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
		saleOrderDao.addCheckBill(qb);
		saleOrderDao.addCheckBillc(qbcs);
		//反填报工单子表已质检数量
		saleOrderDao.updReprotOrderCheck(qbcs);
		//反填生产订单工艺路线子表已质检数量
		saleOrderDao.updProduceProcessRoutecCheck(qbcs);
		
		//根据是否质检判断是否触发入库(最后一道工序，需要质检触发)
		List<WmTask> inTask=new ArrayList<WmTask>();
		String condition=" gid='"+qbcs.get(0).getProduceProcessRouteCGid()+"'";
		MesWmProduceProcessroutec mesWmProduceProcessroutec=saleOrderDao.getMesWmProduceProcessroutec(condition);//当前任务对应的生产订单工艺路线子表
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
	 * @category 提交工序质检
	 *2016 2016年4月15日上午11:21:23
	 *void
	 *宋银海
	 */
	public void addSampling(QMCheckBill qb,List<QMCheckCbill> qbcs){
		
		//插入质检单主表 子表
		qb.setCheckCode(getBillId(Constants.TASKTYPE_BGZJ));
		saleOrderDao.addCheckBill(qb);
		saleOrderDao.addCheckBillc(qbcs);
		
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
		saleOrderDao.addCheckBill(qb);
		saleOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			saleOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填发货单子表已质检数量
		saleOrderDao.updSaleSendCheck(qbcs);

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
		saleOrderDao.addCheckBill(qb);
		saleOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			saleOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填生产订单工艺路线子表已质检数量
		saleOrderDao.updProduceProcessRoutecProductCheck(qbcs);

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
		saleOrderDao.addCheckBill(qb); 
		saleOrderDao.addCheckBillc(qbcs);
		if(qbc2s.size()>0){
			saleOrderDao.addCheckBillc2(qbc2s);
		}
		
		//反填到货单子表已质检数量
		saleOrderDao.updProcureSendCheck(qbcs);

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
			saleOrderDao.addCheckBill(qb); 
			saleOrderDao.addCheckBillc(qbcs);
			if(qbc2s.size()>0){
				saleOrderDao.addCheckBillc2(qbc2s);
			}
			
			//反填到货单子表已质检数量
			saleOrderDao.updProcureSendCheck(qbcs);

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
	
	
	
	public PageBean getsaleOrderlist(int pageIndex,int pageSize,String condition){
		return saleOrderDao.getsaleOrderlist(pageIndex,pageSize,condition);
	}
	
	public Map findsaleOrder(String purchaseArrivalgid,String orgId,String sobId) {
		return saleOrderDao.findsaleOrder(purchaseArrivalgid,orgId,sobId);
	}
	
	public List getsaleOrderlist(String purchaseArrivalpurchaseArrivalUid) {
		return saleOrderDao.getsaleOrderlist(purchaseArrivalpurchaseArrivalUid);
	}
	
	public boolean addsaleOrder(WmSaleorder WmSaleorder) {
		return saleOrderDao.addsaleOrder(WmSaleorder);
	}
	
	public boolean addsaleOrderc(List list) {
		return saleOrderDao.addsaleOrderc(list);
	}
	
	public boolean updatesaleOrder(WmSaleorder WmSaleorder) {
		return saleOrderDao.updatesaleOrder(WmSaleorder);
	}
	
	public boolean updatesaleOrderc(List list) {
		return saleOrderDao.updatesaleOrderc(list);
	}
	
	public boolean deletesaleOrder(String saleorderGid) {
		return saleOrderDao.deletesaleOrder(saleorderGid);
	}
	
	public boolean deletesaleOrderc(String saleorderGid) {
		return saleOrderDao.deletesaleOrderc(saleorderGid);
	}
	
	public boolean auditsaleOrder(String saleorderGid) {
		
		 saleOrderDao.auditsaleOrder(saleorderGid);
		return true;
	}
	
	public boolean disauditsaleOrder(String saleorderGid) {
		saleOrderDao.disauditsaleOrder(saleorderGid);
		return true;
		
	}
	
	public boolean stopsaleOrder(String saleorderGid) {
		return saleOrderDao.stopsaleOrder(saleorderGid);
	}
	
	public int getproduceprocess(String saleorderGid) {
		return saleOrderDao.getproduceprocess(saleorderGid);
	}
	
	public int getsalesnumber(String saleorderGid) {
		return saleOrderDao.getsalesnumber(saleorderGid);
	}
	
	/**
	 * @category 返回可视化工艺路线 开工报工情况
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStepSituation(String orderCid){
		String condition=" ppr.produceCuid='"+orderCid+"'";
		List<Map> maps=saleOrderDao.getProductStepSituation(condition);
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
		List<Map> maps=saleOrderDao.getProductStepMeterialOut(condition);
		
		for(Map m:maps){
			MesWmStandardprocess  mws=cacheCtrlService.getMESStandardProcess(m.get("opGid").toString());
			AaGoods aaGoods=cacheCtrlService.getGoods(m.get("goodsGid").toString());
			m.put("opName", mws.getOpname());
			m.put("goodsName", aaGoods.getGoodsname());
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
		List<Map> maps=saleOrderDao.getProductStepSituation(condition);
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
}
