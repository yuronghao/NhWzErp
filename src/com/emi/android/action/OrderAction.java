package com.emi.android.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DuplicateKeyException;

import com.emi.android.bean.ProcessReportScanRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.android.bean.ProcessTaskDetailSamplingRsp;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmReportorder;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.servicedata.service.ProduceOrderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class OrderAction extends BaseAction implements Serializable
{
	private static final long serialVersionUID = 9060263678701491132L;
	private ProduceOrderService produceOrderService;
	
	public void setProduceOrderService(ProduceOrderService produceOrderService) {
		this.produceOrderService = produceOrderService;
	}


	
	/**
	 * @category 开工时扫描条码（扫描人员、工位即设备、任务）
	 *2016 2016年4月12日下午5:36:44
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeStart(){
		
		try {
			JSONObject json = getJsonObject();
			String barcode = json.getString("barCode");
			String dispatchingObj=json.getString("dispatchingObj");//派工对象
			String produceProcessRoutecGid=json.getString("produceProcessRoutecGid");//工艺路线gid
			ProcessStartScanRsp processBarcodeScanRsp=produceOrderService.getInfoByBarcodeStart(barcode,dispatchingObj,produceProcessRoutecGid);
			System.out.println(EmiJsonObj.fromObject(processBarcodeScanRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processBarcodeScanRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 报工时扫描条码（目前仅支持扫描任务）
	 *2016 2016年4月20日上午10:32:40
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeReport(){
		
		try {
			JSONObject json = getJsonObject();
			String barcode = json.getString("barCode");
			ProcessReportScanRsp processReportScanRsp=produceOrderService.getInfoByBarcodeReport(barcode);
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    
		    System.out.println(EmiJsonObj.fromObject(processReportScanRsp).toString());
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 质检时扫描条码(目前仅支持扫描任务)
	 *2016 2016年4月21日下午2:37:08
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeCheck(){
		
		try {
			JSONObject json = getJsonObject();
			String barcode = json.getString("barCode");
			ProcessReportScanRsp processReportScanRsp=produceOrderService.getInfoByBarcodeCheck(barcode);
//			System.out.println(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 *@category 抽检时扫描条码(目前仅支持扫描任务) 			
	 *2016 2016年7月19日下午2:04:02
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeRandomCheck(){
		
		try {
			JSONObject json = getJsonObject();
			String barcode = json.getString("barCode");
			ProcessReportScanRsp processReportScanRsp=produceOrderService.getInfoByBarcodeRandomCheck(barcode);
//			System.out.println(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 首检、巡检时扫描条码(目前仅支持扫描任务，针对产品)
	 *2016 2016年4月21日下午2:37:08
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeSampling(){
		
		try {
			JSONObject json = getJsonObject();
			String barcode = json.getString("barCode");
			ProcessTaskDetailSamplingRsp processTaskDetailSamplingRsp=produceOrderService.getInfoByBarcodeSampling(barcode);
//			System.out.println(EmiJsonObj.fromObject(processTaskDetailSamplingRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processTaskDetailSamplingRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	

	/**
	 * @category 提交开工
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void startWork(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			String uuid=jsonObject.getString("uuid");//防止重复提交
			
			Integer workingTime=new Integer(0);//0白班 1夜班 默认白班
			if(jsonObject.containsKey("workingTime")){
				workingTime=new Integer(jsonObject.getInt("workingTime"));
			}
			
			
			MesWmDispatchingorder mo=new MesWmDispatchingorder();
			
			mo.setWorkingTime(workingTime);
			
			String mouldGid=jsonObject.getString("mouldGid");
			if (mouldGid!=null&&!mouldGid.trim().equals("")) {
				mo.setMouldGid(mouldGid);
			}
			mo.setOrggid(jsonObject.getString("orggid"));
			mo.setSobgid(jsonObject.getString("sobgid"));
			mo.setGid(UUID.randomUUID().toString());
			mo.setDispatchingObj(jsonObject.getInt("dispatchingObj"));
			mo.setDisDate(new Timestamp(new Date().getTime()));
			mo.setStationGid(jsonObject.getString("stationGid"));
			
			MesWmReportorder mr=new MesWmReportorder();//报工单主
			mr.setOrgGid(jsonObject.getString("orggid"));
			mr.setSobGid(jsonObject.getString("sobgid")); 
			mr.setGid(mo.getGid());
			mr.setDispatchingObj(jsonObject.getInt("dispatchingObj"));
			mr.setBillDate(new Timestamp(new Date().getTime()));
			mr.setStationGid(jsonObject.getString("stationGid"));
			
			List<MesWmDispatchingorderc> mesWmDispatchingordercs=new ArrayList<MesWmDispatchingorderc>();//派工单子表
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//报工单子表
			
			JSONArray personJsonArray=jsonObject.getJSONArray("personUnitVendorGids");
			
			for(Object personObj:personJsonArray){
				JSONObject personJsonObject=(JSONObject)personObj;
				MesWmDispatchingorderc moc=new MesWmDispatchingorderc();
				
				moc.setGid(UUID.randomUUID().toString());
				moc.setDisGid(mo.getGid());
				moc.setDisNum(BigDecimal.valueOf(personJsonObject.getDouble("canDisnum")));
				moc.setStartTime(new Timestamp(new Date().getTime()));
				moc.setPersonUnitVendorGid(personJsonObject.getString("personUnitVendorGid"));
				moc.setProduceProcessRoutecGid(personJsonObject.getString("produceProcessRoutecGid"));
				moc.setNotes(personJsonObject.getString("notes"));
				
				mesWmDispatchingordercs.add(moc);
				
				MesWmReportorderc mrc=new MesWmReportorderc();
				mrc.setRptgid(mr.getGid());
				mrc.setGid(moc.getGid());
				mrc.setDiscGid(moc.getGid());
				mrc.setPersonUnitVendorGid(personJsonObject.getString("personUnitVendorGid"));
//				mrc.setReportNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNum")));
				mrc.setReportOkNum(BigDecimal.valueOf(personJsonObject.getDouble("canDisnum")));
//				mrc.setReportNotOkNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNotOkNum")));
//				mrc.setReportProblemNum(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));
				mrc.setProduceProcessRouteCGid(personJsonObject.getString("produceProcessRoutecGid"));
				mrc.setEndTime(new Timestamp(new Date().getTime()));
				mrc.setNotes(personJsonObject.getString("notes"));
				mesWmReportordercs.add(mrc);
			}
			
			String res=produceOrderService.startWork(uuid,mo,mesWmDispatchingordercs, mr, mesWmReportordercs);
			getResponse().getWriter().write(res);
			
		} catch (Exception e) {
			
			
			if(e instanceof DuplicateKeyException){
				this.writeErrorOrSuccess(0, "禁止重复插入");
            }else{
    			e.printStackTrace();
    			this.writeErrorOrSuccess(0, "开工失败！");
            }
			
			//BadSqlGrammarException
			

		}
	}
	
	
	
	/**
	 * @category 提交报工
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void reportWork(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			MesWmReportorder mr=new MesWmReportorder();
			
			mr.setStationGid(CommonUtil.Obj2String(jsonObject.get("stationGid")));
			mr.setOrgGid(jsonObject.getString("orggid"));
			mr.setSobGid(jsonObject.getString("sobgid")); 
			mr.setGid(UUID.randomUUID().toString());
			mr.setDispatchingObj(jsonObject.getInt("dispatchingObj"));
			mr.setBillDate(new Timestamp(new Date().getTime()));
			mr.setBatch(CommonUtil.Obj2String(jsonObject.get("batch")));
			
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//派工单子表
			JSONArray personJsonArray=jsonObject.getJSONArray("personGids");
			
			String uuid=jsonObject.getString("uuid");//防止重复提交
			
			for(Object personObj:personJsonArray){
				JSONObject personJsonObject=(JSONObject)personObj;
				MesWmReportorderc mrc=new MesWmReportorderc();
				mrc.setRptgid(mr.getGid());
				mrc.setGid(UUID.randomUUID().toString());
				mrc.setDiscGid(personJsonObject.getString("discGid"));
				mrc.setPersonUnitVendorGid(personJsonObject.getString("personUnitVendorGid"));
//				mrc.setReportNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNum")));
				mrc.setReportOkNum(BigDecimal.valueOf(personJsonObject.getDouble("reportOkNum")));
				mrc.setReportNotOkNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNotOkNum")));
				mrc.setReportProblemNum(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));
				mrc.setProduceProcessRouteCGid(personJsonObject.getString("produceProcessRoutecGid"));
				mrc.setEndTime(new Timestamp(new Date().getTime()));
				mrc.setNotes(personJsonObject.getString("notes"));
				mesWmReportordercs.add(mrc);
			}
			
			String res=produceOrderService.reportWork(uuid,mr,mesWmReportordercs);
			getResponse().getWriter().write(res);
//			this.writeSuccess();
			
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException){
				this.writeErrorOrSuccess(0, "禁止重复插入");
            }else{
            	e.printStackTrace();
    			this.writeErrorOrSuccess(0, "报工失败！");
            }
			
		}
	}
	
	
	/**
	 * @category 报工发送打印指令
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void printWhenReportWork(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			produceOrderService.printWhenReportWork(jsonObject);
			this.writeSuccess();
			
		} catch (Exception e) {
            	e.printStackTrace();
    			this.writeErrorOrSuccess(0, "报工失败！");
			
		}
	}
	
	
	
	/**
	 * @category 提交质检
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void checkWork(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("PCS");
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//派工单子表
			JSONArray personJsonArray=jsonObject.getJSONArray("personGids");
			
			for(Object personObj:personJsonArray){
				JSONObject personJsonObject=(JSONObject)personObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setOkNum(BigDecimal.valueOf(personJsonObject.getDouble("canCheckNum")).subtract(BigDecimal.valueOf(personJsonObject.getDouble("checkNotOkNum"))));
				qbc.setNotOkNum(BigDecimal.valueOf(personJsonObject.getDouble("checkNotOkNum")));
				qbc.setRptcGid(personJsonObject.getString("rptcGid"));
				qbc.setProduceProcessRouteCGid(personJsonObject.getString("produceProcessRoutecGid"));
				qbcs.add(qbc);
			}
			
			produceOrderService.checkWork(qb,qbcs);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	
	/**
	 * @category 提交抽检
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void randomCheckWork(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("r");//抽检，随机检验
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//派工单子表
			JSONArray personJsonArray=jsonObject.getJSONArray("personGids");
			
			for(Object personObj:personJsonArray){
				JSONObject personJsonObject=(JSONObject)personObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setOkNum(BigDecimal.valueOf(personJsonObject.getDouble("canCheckNum")).subtract(BigDecimal.valueOf(personJsonObject.getDouble("checkNotOkNum"))));
				qbc.setNotOkNum(BigDecimal.valueOf(personJsonObject.getDouble("checkNotOkNum")));
				qbc.setRptcGid(personJsonObject.getString("rptcGid"));
				qbc.setProduceProcessRouteCGid(personJsonObject.getString("produceProcessRoutecGid"));
				qbcs.add(qbc);
			}
			
			produceOrderService.randomCheckWork(qb,qbcs);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	
	/**
	 * @category 提交巡检 首检
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void addSampling(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			if(jsonObject.getString("samplingType").equalsIgnoreCase("s")){
				qb.setCheckTypeCode("s");//巡检
			}else if(jsonObject.getString("samplingType").equalsIgnoreCase("f")){
				qb.setCheckTypeCode("f");//巡检
			}
			
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//派工单子表
			QMCheckCbill qbc=new QMCheckCbill();
			qbc.setGid(UUID.randomUUID().toString());
			qbc.setCheckGid(qb.getGid());
			qbc.setCheckNum(BigDecimal.valueOf(jsonObject.getDouble("checkNum")));
			qbc.setOkNum(BigDecimal.valueOf(jsonObject.getDouble("checkNum")).subtract(BigDecimal.valueOf(jsonObject.getDouble("checkNotOkNum"))));
			qbc.setNotOkNum(BigDecimal.valueOf(jsonObject.getDouble("checkNotOkNum")));
			qbc.setProduceCuid(jsonObject.getString("produceCuid"));
			qbcs.add(qbc);
			
			produceOrderService.addSampling(qb,qbcs);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	
	
	
	/**
	 * @category 添加销售质检
	 *2016 2016年4月23日上午10:09:59
	 *void
	 *宋银海
	 */
	public void addSaleCheck(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();//质检主表
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("ISS");
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//质检子表
			List<QMCheckCReasonBill> qbc2s=new ArrayList<QMCheckCReasonBill>();//质检子表2
			JSONArray goodsList=jsonObject.getJSONArray("goodsList");
			
			for(Object goodObj:goodsList){
				JSONObject gObject=(JSONObject)goodObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setGoodsUid(gObject.getString("goodsUid"));
				qbc.setSaleSendCuid(gObject.getString("saleSendCuid"));
				qbc.setOkNum(BigDecimal.valueOf(gObject.getDouble("okNum")));
				qbc.setNotOkNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
				qbc.setAssistOkNum(BigDecimal.valueOf(gObject.getDouble("assistOkNum")));
				qbc.setAssistNotOkNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
				qbc.setBatch(CommonUtil.Obj2String(gObject.get("batch")));
				qbcs.add(qbc);
				
				JSONArray notOk=gObject.getJSONArray("notOkReason");
				for(Object notOkObj:notOk){
					JSONObject notOkJobj=(JSONObject)notOkObj;
					QMCheckCReasonBill qbc2=new QMCheckCReasonBill();
					qbc2.setGid(UUID.randomUUID().toString());
					qbc2.setCheckcGid(qbc.getGid());
					qbc2.setNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
					qbc2.setAssistNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
					qbc2.setReasonGid(notOkJobj.getString("reasonGid"));
					qbc2s.add(qbc2);
				}
				
			}
			
			produceOrderService.addSaleCheck(qb,qbcs,qbc2s,jsonObject.getString("taskGid"));
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	
	
	/**
	 * @category 添加产品质检
	 *2016 2016年4月23日上午10:09:59
	 *void
	 *宋银海
	 */
	public void addProductInCheck(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();//质检主表
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("PRO");
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//质检子表
			List<QMCheckCReasonBill> qbc2s=new ArrayList<QMCheckCReasonBill>();//质检子表2
			JSONArray goodsList=jsonObject.getJSONArray("goodsList");
			
			for(Object goodObj:goodsList){
				JSONObject gObject=(JSONObject)goodObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setGoodsUid(gObject.getString("goodsUid"));
				qbc.setProduceProcessRouteCGid(gObject.getString("produceRouteCUid"));
				qbc.setOkNum(BigDecimal.valueOf(gObject.getDouble("okNum")));
				qbc.setNotOkNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
				qbc.setAssistOkNum(BigDecimal.valueOf(gObject.getDouble("assistOkNum")));
				qbc.setAssistNotOkNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
				qbc.setBatch(CommonUtil.Obj2String(gObject.get("batch")));
				qbcs.add(qbc);
				
				JSONArray notOk=gObject.getJSONArray("notOkReason");
				for(Object notOkObj:notOk){
					JSONObject notOkJobj=(JSONObject)notOkObj;
					QMCheckCReasonBill qbc2=new QMCheckCReasonBill();
					qbc2.setGid(UUID.randomUUID().toString());
					qbc2.setCheckcGid(qbc.getGid());
					qbc2.setNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
					qbc2.setAssistNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
					qbc2.setReasonGid(notOkJobj.getString("reasonGid"));
					qbc2s.add(qbc2);
				}
				
			}
			
			produceOrderService.addProductInCheck(qb,qbcs,qbc2s,jsonObject.getString("taskGid"));
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	
	
	
	/**
	 * 
	 * @category 提交采购质检
	 * 2016年4月25日 上午10:00:39
	 * @author 杨峥铖
	 */
	public void addProcureCheck(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();//质检主表
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("ARR");
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//质检子表
			List<QMCheckCReasonBill> qbc2s=new ArrayList<QMCheckCReasonBill>();//质检子表2
			JSONArray goodsList=jsonObject.getJSONArray("goodsList");
			
			for(Object goodObj:goodsList){
				JSONObject gObject=(JSONObject)goodObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setGoodsUid(gObject.getString("goodsUid"));
				qbc.setProcureArrivalCuid(gObject.getString("procureArrivalCuid"));
				qbc.setOkNum(BigDecimal.valueOf(gObject.getDouble("okNum")));
				qbc.setNotOkNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
				qbc.setAssistOkNum(BigDecimal.valueOf(gObject.getDouble("assistOkNum")));
				qbc.setAssistNotOkNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
				qbc.setBatch(CommonUtil.Obj2String(gObject.get("batch")));
				qbcs.add(qbc);
				
				JSONArray notOk=gObject.getJSONArray("notOkReason");
				for(Object notOkObj:notOk){
					JSONObject notOkJobj=(JSONObject)notOkObj;
					QMCheckCReasonBill qbc2=new QMCheckCReasonBill();
					qbc2.setGid(UUID.randomUUID().toString());
					qbc2.setCheckcGid(qbc.getGid());
					qbc2.setNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
					qbc2.setAssistNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
					qbc2.setReasonGid(notOkJobj.getString("reasonGid"));
					qbc2s.add(qbc2);
				}
				
			}
			
			produceOrderService.addProcureCheck(qb,qbcs,qbc2s,jsonObject.getString("taskGid"));
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	

	
	/**
	 * 
	 * @category 提交委外质检
	 * 2016年4月25日 上午10:00:39
	 * @author 杨峥铖
	 */
	public void addOmCheck(){
		try {
			JSONObject jsonObject=getJsonObject();
			
			QMCheckBill qb=new QMCheckBill();//质检主表
			
			qb.setOrgGid(jsonObject.getString("orggid"));
			qb.setSobGid(jsonObject.getString("sobgid")); 
			qb.setGid(UUID.randomUUID().toString());
			qb.setCheckTypeCode("SUB");
			qb.setCheckDate(new Timestamp(new Date().getTime()));
			
			List<QMCheckCbill> qbcs=new ArrayList<QMCheckCbill>();//质检子表
			List<QMCheckCReasonBill> qbc2s=new ArrayList<QMCheckCReasonBill>();//质检子表2
			JSONArray goodsList=jsonObject.getJSONArray("goodsList");
			
			for(Object goodObj:goodsList){
				JSONObject gObject=(JSONObject)goodObj;
				QMCheckCbill qbc=new QMCheckCbill();
				qbc.setGid(UUID.randomUUID().toString());
				qbc.setCheckGid(qb.getGid());
				qbc.setGoodsUid(gObject.getString("goodsUid"));
				qbc.setProcureArrivalCuid(gObject.getString("procureArrivalCuid"));
				qbc.setOkNum(BigDecimal.valueOf(gObject.getDouble("okNum")));
				qbc.setNotOkNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
				qbc.setAssistOkNum(BigDecimal.valueOf(gObject.getDouble("assistOkNum")));
				qbc.setAssistNotOkNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
				qbc.setBatch(CommonUtil.Obj2String(gObject.get("batch")));
				qbcs.add(qbc);
				
				JSONArray notOk=gObject.getJSONArray("notOkReason");
				for(Object notOkObj:notOk){
					JSONObject notOkJobj=(JSONObject)notOkObj;
					QMCheckCReasonBill qbc2=new QMCheckCReasonBill();
					qbc2.setGid(UUID.randomUUID().toString());
					qbc2.setCheckcGid(qbc.getGid());
					qbc2.setNum(BigDecimal.valueOf(gObject.getDouble("notOkNum")));
					qbc2.setAssistNum(BigDecimal.valueOf(gObject.getDouble("assistNotOkNum")));
					qbc2.setReasonGid(notOkJobj.getString("reasonGid"));
					qbc2s.add(qbc2);
				}
				
			}
			
			produceOrderService.addOmCheck(qb,qbcs,qbc2s,jsonObject.getString("taskGid"));
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "提交质检失败！");
		}
	}
	
	/**
	 * @category 扫描模具条码
	 * 2017年5月23日13:17:41
	 * @author cuixn
	 */
	public void getMouldInfoByBarcode(){
		try {
			JSONObject jsonObject=getJsonObject();
			String barcode=jsonObject.getString("barCode");
			Map map=produceOrderService.getMouldInfoByBarcode(barcode);
			getResponse().getWriter().write(EmiJsonObj.fromObject(map).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "条码扫描失败");
		}
	}
	
	/**
	 * @category 根据骆氏号模糊查询
	 * 2017年5月23日13:17:41
	 * @author cuixn
	 */
	public void getMouldInfoByBarcodeList(){
		try {
			JSONObject jsonObject=getJsonObject();
			String condiiton=jsonObject.getString("barCode");//根据骆氏号模糊查询
			int pageIndex = jsonObject.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;//每页总条数
			Map map=produceOrderService.getMouldInfoByBarcodeList(condiiton,pageIndex,pageSize);
			System.out.println(EmiJsonObj.fromObject(map).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(map).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "条码扫描失败");
		}
	}
	
	
	//测试
	public void test(){
		try {
			 String callback = (String)getRequest().getParameter("callback");  
			    String jsonData = "{\"id\":\"3\", \"name\":\"zhangsan\", \"telephone\":\"13612345678\"}";//为了演示效果，json数据是写死的  
			    String retStr = callback + "(" + jsonData + ")";
			    
			getResponse().getWriter().write(retStr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "条码扫描失败");
		}
	}
	
	
}
