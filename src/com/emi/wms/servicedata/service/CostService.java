package com.emi.wms.servicedata.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.chainsaw.Main;

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
import com.emi.common.util.MathEval;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MESWMCostFeeMain;
import com.emi.wms.bean.MESWMCostGoodsBalance;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MESWMCostRdRecordsOut;
import com.emi.wms.bean.MESWMCostReportInInfor;
import com.emi.wms.bean.MESWmCostDirectMaterialWork2;
import com.emi.wms.bean.MESWmCostDirectMaterialWorkMain2;
import com.emi.wms.bean.MESWmCostGoodsBalanceMain;
import com.emi.wms.bean.MESWmCostMaterialWork;
import com.emi.wms.bean.MESWmCostProcessInWork;
import com.emi.wms.bean.MESWmCostRdRecordsOutMain;
import com.emi.wms.bean.MESWmCostReportInInforMain;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmCostFee;
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
import com.emi.wms.bean.WmPurchaserequisition;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.bean.WmTask;
import com.emi.wms.servicedata.dao.CostDao;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.TaskDao;
import com.emi.wms.servicedata.dao.WareHouseDao;
import com.sun.java.swing.plaf.motif.resources.motif;

public class CostService extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = 452055854590339604L;
	
	private ProduceOrderDao produceOrderDao;
	private CacheCtrlService cacheCtrlService;
	private WareHouseDao wareHouseDao;
	private CostDao costDao;

	public ProduceOrderDao getProduceOrderDao() {
		return produceOrderDao;
	}

	@Override
	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public WareHouseDao getWareHouseDao() {
		return wareHouseDao;
	}

	public CostDao getCostDao() {
		return costDao;
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

	public void setCostDao(CostDao costDao) {
		this.costDao = costDao;
	}

	//报工单、入库单取数
	public String getCostReportInInfor(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String start=request.getParameter("start");
		if(CommonUtil.isNullObject(start)){
			jobj.put("success", 0);
			jobj.put("failInfor", "年月不能为空");
			return jobj.toString();
		}
		
		int year=Integer.parseInt(start.split("-")[0]);
		int month=Integer.parseInt(start.split("-")[1]);
		
		Map period=getPeriod(year, month);
		
		if(CommonUtil.isNullObject(period)){
			jobj.put("success", 0);
			jobj.put("failInfor", "会计期间不能为空");
			return jobj.toString();
		}
		
		MESWmCostReportInInforMain m=new MESWmCostReportInInforMain();
		m.setIyear(Integer.valueOf(year));
		m.setImonth(Integer.valueOf(month));
		m.setCtime(new Date());
		m.setGid(UUID.randomUUID().toString());
		
		costDao.deleteCostReportInMain(year, month);
		
		costDao.emiInsert(m);
		
		//删除之前的取数
		costDao.deleteCostReportInInfor(year, month);
		
		//根据条件取报工单，包括末级
		costDao.addReport(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//根据条件取报工单，末级未入库
//		costDao.addReportNoIn(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//根据条件取入库单
		costDao.addRdRecordIn(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//更改相关属性
		costDao.uptCostReportInInfor(year, month);
		
		costDao.uptCostReportInInfor2(year, month);
		
		costDao.uptCostReportInInfor3(year, month);
		
//		costDao.uptCostReportInInfor4(period.get("dBegin").toString(), period.get("dEnd").toString());
		
		jobj.put("success", 1);
		jobj.put("failInfor", "取数成功");
		return jobj.toString();
		
	}
	
	
	
	//材料出库单取数
	public String getCostRdRecordsOut(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String start=request.getParameter("start");
		if(CommonUtil.isNullObject(start)){
			jobj.put("success", 0);
			jobj.put("failInfor", "年月不能为空");
			return jobj.toString();
		}
		
		int year=Integer.parseInt(start.split("-")[0]);
		int month=Integer.parseInt(start.split("-")[1]);
		
		Map period=getPeriod(year, month);
		
		if(CommonUtil.isNullObject(period)){
			jobj.put("success", 0);
			jobj.put("failInfor", "会计期间不能为空");
			return jobj.toString();
		}
		
		
		MESWmCostRdRecordsOutMain m=new MESWmCostRdRecordsOutMain();
		m.setIyear(Integer.valueOf(year));
		m.setImonth(Integer.valueOf(month));
		m.setCtime(new Date());
		m.setGid(UUID.randomUUID().toString());
		
		costDao.deleteRdRecordsOutMain(year, month);
		costDao.emiInsert(m);
		
		//删除材料出库单取数
		costDao.deleteCostRdRecordsOut(year, month);
		
		//直接材料1（可以完全对应到工序，指定不到工序 ，全部计入到该产品的第一道）
		costDao.addRdRecordOut(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//更改相关属性（直接到工序）
		costDao.uptCostRdRecordsOutInfor(year, month);
		
		//二是未按PDA发，是U8直接生单发料的，U8生产订单可以匹配到一米生产订单工艺路线工序的就匹配
		costDao.uptCostRdRecordsOutInfor2(year, month);
		
		//没有一米的工艺路线 就按订单产品自由项做为工序归集
		costDao.uptCostRdRecordsOutInfor3(year, month);
		
		//直接材料2(指定单独的出库类型---按单领用，收发类别在系统中编码20102,此种类型的材料出库单上已经指明 产品编码 +工序 )
		costDao.addRdRecordOut2(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//直接材料2(指定单独的出库类型---按单领用，收发类别在系统中编码20103,此类型的材料出库单上只指明工序，但产品不指定 )
		costDao.addRdRecordOut3(year, month, period.get("dBegin").toString(), period.get("dEnd").toString(),m.getGid());
		
		//更新物料gid,仓库gid
		costDao.uptCostRdRecordsOutInforCommon(year, month);
		
		
		jobj.put("success", 1);
		jobj.put("failInfor", "取数成功");
		return jobj.toString();
		
	}
	
	
	//根据会计科目获得工和费
	public String getCostFee(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String start=request.getParameter("start");
		if(CommonUtil.isNullObject(start)){
			jobj.put("success", 0);
			jobj.put("failInfor", "年月不能为空");
			return jobj.toString();
		}
		
		int year=Integer.parseInt(start.split("-")[0]);
		int month=Integer.parseInt(start.split("-")[1]);
		
		Map period=getPeriod(year, month);
		
		if(CommonUtil.isNullObject(period)){
			jobj.put("success", 0);
			jobj.put("failInfor", "会计期间不能为空");
			return jobj.toString();
		}
		
		MESWMCostFeeMain m=new MESWMCostFeeMain();
		m.setIyear(Integer.valueOf(year));
		m.setImonth(Integer.valueOf(month));
		m.setCtime(new Date());
		m.setGid(UUID.randomUUID().toString());
		
		List<Map> feeMaps=costDao.getCostFeeListMap();
		List<MesWmCostFee> mwcfs=new ArrayList<MesWmCostFee>();
		for(Map map:feeMaps){
			
			MesWmCostFee mwcf=new MesWmCostFee();
			
			String subjectCodeInitial=map.get("subjectCode").toString();
			String subjectCode=map.get("subjectCode").toString();
			
			subjectCode=subjectCode.replace("(", "");
			subjectCode=subjectCode.replace(")", "");
			subjectCode=subjectCode.replace("+", ",");
			subjectCode=subjectCode.replace("-", ",");
			subjectCode=subjectCode.replace("*", ",");
			subjectCode=subjectCode.replace("/", ",");
			
			String[] codes=subjectCode.split(",");
			String depGid=CommonUtil.Obj2String(map.get("depGid"));
			AaDepartment dept=cacheCtrlService.getDepartment(depGid);
			
			boolean canGo=true;
			for(String s:codes){
				Map accass=costDao.getAccass(s.trim(), CommonUtil.isNullObject(dept)?null:dept.getDepcode(), month);
//				if(!CommonUtil.isNullObject(accass)){
//					subjectCodeInitial=subjectCodeInitial.replace(s, accass.get("md").toString());
//				}else{
//					canGo=false;
//				}
				
				if(CommonUtil.isNullObject(accass)){
					subjectCodeInitial=subjectCodeInitial.replace(s,"0");
				}else{
					subjectCodeInitial=subjectCodeInitial.replace(s,CommonUtil.isNullObject(accass.get("md"))?"0":accass.get("md").toString());
				}
				
				
			}
			
			if(canGo){
				double d=MathEval.eval(subjectCodeInitial);
				mwcf.setCostItemGid(map.get("costItemGid").toString());
				mwcf.setIprice(BigDecimal.valueOf(d));
				mwcf.setYear(Integer.valueOf(year));
				mwcf.setMonth(Integer.valueOf(month));
				mwcf.setMaingid(m.getGid());
				mwcf.setDeptCode(CommonUtil.isNullObject(dept)?null:dept.getDepcode());
				mwcfs.add(mwcf);
			}
			
		}
		
		if(mwcfs.size()>0){
			costDao.deleteCostFeeMain(year, month);
			costDao.emiInsert(m);
			costDao.deleteCostFee(year, month);
			costDao.emiInsert(mwcfs);
		}
		
		//删除
		int preyear=0;//上个月
		int premonth=0;//上个月对应的年份
		int yonyoumonth=month-1;//对应用友中的年份
		
		if(month==1){
			premonth=12;
			preyear=year-1;
		}else{
			premonth=month-1;
			preyear=year;
		}
		
		//处理上月材料月末结余
//		costDao.deleteCostGoodsBalance(preyear,premonth);
//		costDao.insertCostGoodsBalance(preyear,premonth,yonyoumonth);
//		costDao.upttCostGoodsBalance(preyear,premonth);
		
		
		jobj.put("success", 1);
		jobj.put("failInfor", "取数成功");
		return jobj.toString();
		
	}
	
	
	
	
	
	//根据会计科目获得月末结存
	public String getCostGoodsBalance(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String start=request.getParameter("start");
		if(CommonUtil.isNullObject(start)){
			jobj.put("success", 0);
			jobj.put("failInfor", "年月不能为空");
			return jobj.toString();
		}
		
		int year=Integer.parseInt(start.split("-")[0]);
		int month=Integer.parseInt(start.split("-")[1]);
		
		Map period=getPeriod(year, month);
		
		if(CommonUtil.isNullObject(period)){
			jobj.put("success", 0);
			jobj.put("failInfor", "会计期间不能为空");
			return jobj.toString();
		}
		
		MESWmCostGoodsBalanceMain m=new MESWmCostGoodsBalanceMain();
		m.setIyear(Integer.valueOf(year));
		m.setImonth(Integer.valueOf(month));
		m.setCtime(new Date());
		m.setGid(UUID.randomUUID().toString());
		
		costDao.deleteCostGoodsBalanceMain(year, month);
		costDao.emiInsert(m);
		
//		int preyear=0;//上个月
//		int premonth=0;//上个月对应的年份
//		
//		if(month==1){
//			premonth=12;
//			preyear=year-1;
//		}else{
//			premonth=month-1;
//			preyear=year;
//		}
		
		//处理上月材料月末结余
		int yonyoumonth=month-1;//对应用友中的年份
		costDao.deleteCostGoodsBalance(year,month);
		costDao.insertCostGoodsBalance(year,month,yonyoumonth,m.getGid());
		costDao.upttCostGoodsBalance(year,month);
		
		
		jobj.put("success", 1);
		jobj.put("failInfor", "取数成功");
		return jobj.toString();
		
	}
	
	
	
	//低阶码运算
	public String getLowOrderCode(HttpServletRequest request){
		
		
		JSONObject jobj=new JSONObject();
		
		String mainGid=request.getParameter("mainGid");
		MESWmCostReportInInforMain rmain=costDao.getMESWmCostReportInInforMain(mainGid);
		
		if(CommonUtil.isNullObject(rmain)){
			jobj.put("success", 0);
			jobj.put("failInfor", "年月不能为空");
			return jobj.toString();
		}
		
		int year=rmain.getIyear().intValue();
		int month=rmain.getImonth().intValue();
		
		Map period=getPeriod(year, month);
		
		if(CommonUtil.isNullObject(period)){
			jobj.put("success", 0);
			jobj.put("failInfor", "会计期间不能为空");
			return jobj.toString();
		}
		
		//计算1级低阶码
		costDao.getOrderProcedure(year, month);
		
		Map itemCount=costDao.getCountByMainLowOrderCode(year, month);
		if(Integer.parseInt(itemCount.get("itemCount").toString())>0){
			jobj.put("success", 1);
			jobj.put("failInfor", "主低阶码取数存在错误");
		}
		
		//更新二段低阶码开始
		int i=costDao.updateSecondLowOrderCode(year, month);
		
		int docount=2;
		while(i>0){
			i=costDao.updateSecondLowOrderCode2(year, month, docount);
			docount++;
		}
		
		//更新入库单的二段低阶码
		costDao.updateSecondLowOrderCode3(year, month);
		
		costDao.updateSecondLowOrderCode4(year, month);
		//更新二段低阶码结束
		
		jobj.put("success", 1);
		jobj.put("failInfor", "低阶码运算成功");
		return jobj.toString();
		
	}
	
	
	//分摊费用
	public String shareFee(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String mainGid=request.getParameter("mainGid");
		MESWmCostReportInInforMain rmain=costDao.getMESWmCostReportInInforMain(mainGid);
		
		String year=rmain.getIyear().toString();
		String month=rmain.getImonth().toString();
		
		List<MESWMCostReportInInfor> rmains=costDao.getMESWMCostReportInInforFirst(mainGid);
		if(CommonUtil.isNullObject(rmains.get(0).getMainLowOrderCode())){
			jobj.put("success", 0);
			jobj.put("failInfor", "请先计算低阶码");
			return jobj.toString();
		}
		
		//费用清零
		costDao.reSetCostFees(year,month);
		costDao.reSetRdrecordOut(year,month);
		
		//删除当月工序在制
		costDao.deleteCostProcessInWork(Integer.parseInt(year),Integer.parseInt(month));
		costDao.deleteCostProcessInWorkMain(Integer.parseInt(year),Integer.parseInt(month));
		
		//删除当月材料在制
		costDao.deleteCostMaterialWork(Integer.parseInt(year),Integer.parseInt(month));
		costDao.deleteCostMaterialWorkMain(Integer.parseInt(year),Integer.parseInt(month));
		
		//分摊工和费
		String condition=" cf.year='"+year+"' and cf.month='"+month+"'";
		List<MesWmCostFee> costfees=costDao.getCostFees(condition);//当月的工和费
		for(MesWmCostFee cf:costfees){
			
			String deptCode="";
			String deptGid="";
			
//			if(cf.getSourceGid().equalsIgnoreCase("5B77CEE4-9BEB-466D-85B2-B4601040CF39")){//二次分配f010
//				//直接人工对应的部门
//				List<Map> depsgid=costDao.getCostFeeListMap();
//				
//				StringBuffer deptCodesbf=new StringBuffer();
//				StringBuffer deptGidsbf=new StringBuffer();
//				
//				for(Map dep :depsgid){
//					String depGid=CommonUtil.Obj2String(dep.get("depGid"));
//					AaDepartment dept=cacheCtrlService.getDepartment(depGid);
//					if(dept!=null){
//						deptCodesbf.append("'").append(dept.getDepcode()).append("',");
//						deptGidsbf.append("'").append(dept.getGid()).append("',");
//					}
//				}
//				
//				if(!deptCodesbf.toString().equals("")){
//					deptCode="("+deptCodesbf.toString().substring(0, deptCodesbf.toString().length()-1)+")";
//					deptGid="("+deptGidsbf.toString().substring(0, deptGidsbf.toString().length()-1)+")";
//				}
//			}else{
//				if(cf.getDeptCode()!=null && !cf.getDeptCode().equals("")){
//					deptCode="('"+cf.getDeptCode()+"')";
//					deptGid="('"+cf.getDeptGid()+"')";
//				}
//
//			}
			
			
			if(cf.getDeptCode()!=null && !cf.getDeptCode().equals("")){
				deptCode="('"+cf.getDeptCode()+"')";
				deptGid="('"+cf.getDeptGid()+"')";
			}
			
			
			
			Map mapSum=costDao.getSum(year, month,deptCode,deptGid,cf.getAllotRateGid());
			
			//cf.getIprice()
			//分摊总系数   对应分摊规则中D项
			BigDecimal totalCoefficient=cf.getIprice().divide(CommonUtil.object2BigDecimalOne(mapSum.get("sumvalue")), 20, BigDecimal.ROUND_HALF_UP);
			
			
			
			if(cf.getSourceGid().equalsIgnoreCase("A6FDC381-D8CA-4E96-B47C-6217BABFBFAA")){//委托加工费用f004
				String sql="f004=isnull(f004,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("E457A0D5-79F0-4F40-9BD1-97611736C163")){//直接人工f005
				String sql="f005=isnull(f005,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("A560F35A-798B-40FF-8629-89830840E110")){//间接人工f006
				String sql="f006=isnull(f006,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("3EFBD08F-C230-4346-B989-EEB374988B68")){//燃料动力f007
				String sql="f007=isnull(f007,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("31605736-2D1A-4370-81A9-665E70B28ADE")){//折旧摊销f008
				String sql="f008=isnull(f008,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("7F1ECC63-FFBC-40BA-8923-E09BC8555A81")){//其他费用f009
				String sql="f009=isnull(f009,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			
			}else if(cf.getSourceGid().equalsIgnoreCase("5B77CEE4-9BEB-466D-85B2-B4601040CF39")){//二次分配f010
				String sql="f010=isnull(f010,0)+(isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,0)*totalCoefficient ";
				sql=sql.replace("totalCoefficient", totalCoefficient.toPlainString());
				costDao.updateFee(year, month, cf.getDeptCode(),cf.getDeptGid(),cf.getAllotRateGid(),totalCoefficient.doubleValue(),sql);
			}
			
		}
		
		costDao.uptCostReportInInforMainState(mainGid,"2");
		
		//删除当月材料在制2
		costDao.deleteCostDirectMaterialWork2(Integer.parseInt(year),Integer.parseInt(month));
		costDao.deleteCostDirectMaterialWorkMain2(Integer.parseInt(year),Integer.parseInt(month));
		
		//分摊直接材料2
		String s=costDao.getMeterial2(Integer.parseInt(year),Integer.parseInt(month));
		
		jobj.put("success", 1);
		jobj.put("failInfor", "分摊工和费成功");
		return jobj.toString();
		
	}
	
	
	//计算成本 
//	IA_Summary存货核算存货总账表中的iMonth等于上月的，按cWhCode &cInvCode &cFree1 取iUnitPrice 单价
//	如计算月份是1月的，iMonth=0
	
	public String calculateCost(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String mainGid=request.getParameter("mainGid");
		MESWmCostReportInInforMain rmain=costDao.getMESWmCostReportInInforMain(mainGid);
		
		if(CommonUtil.isNullObject(rmain.getState())){
			jobj.put("success", 0);
			jobj.put("failInfor", "请先分摊工和费");
			return jobj.toString();
		}
		
		
		if(rmain.getState().intValue()!=2){
			jobj.put("success", 0);
			jobj.put("failInfor", "请先分摊工和费,或已完成，请检查状态");
			return jobj.toString();
		}
		
		
		
		int year=rmain.getIyear().intValue();
		int month=rmain.getImonth().intValue();
		
		Map map=costDao.getMainLowOrderCode(mainGid);
		if(map!=null){
			
			costDao.calculateCost(year,month,Integer.parseInt(map.get("mainLowOrderCode").toString()) );
		}else{
			jobj.put("success", 0);
			jobj.put("failInfor", "计算失败");
			return jobj.toString();
		}
		
		
		jobj.put("success", 1);
		jobj.put("failInfor", "计算成功");
		return jobj.toString();
		
	}
	
	
	
	//根据领料出库情况  获得工艺路线
	public List<Map> getRouteC(List<MESWMCostRdRecordsOut> goodsList){
		
		//判断领取的料是否是自制件(在工艺路线中有没有出现,出现即为自制件)
		StringBuffer sb=new StringBuffer();
		for(MESWMCostRdRecordsOut m:goodsList){
			sb.append("'").append(m.getGoodsGid()).append("',");
		}
		
		String goods=sb.toString();
		List<Map> routeC=new ArrayList<Map>();
		if(!CommonUtil.isNullObject(goods)){
			goods="("+goods.substring(0, goods.length()-1)+")";
			//查询工艺路线
			String condition=" nextGid is null and pr.goodsUid in "+goods;
			routeC=costDao.getProcessRouteC(condition);
		}

		return routeC;
	}
	
	
	
	public Map getReportProductInList(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"' ";
		
		PageBean data = costDao.dispatchingStartList(pageIndex ,pageSize,condition);
		List<MESWMCostReportInInfor> list = data.getList();
		
		for(MESWMCostReportInInfor cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
			AaDepartment adt=cacheCtrlService.getDepartment(cri.getDeptGid());
			if(adt!=null){
				cri.setDeptName(adt.getDepname());
			}
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	public Map getCostGoodsBalanceList(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"' ";
		
		PageBean data = costDao.getCostGoodsBalanceList(pageIndex ,pageSize,condition);
		List<MESWMCostGoodsBalance> list = data.getList();
		
		for(MESWMCostGoodsBalance cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
			AaWarehouse ah=cacheCtrlService.getWareHouse(cri.getCwhGid());
			cri.setCwhName(ah.getWhname());
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	public Map getCostProcessInWorkList(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"' ";
		
		PageBean data = costDao.getCostProcessInWorkList(pageIndex ,pageSize,condition);
		List<MESWmCostProcessInWork> list = data.getList();
		
		for(MESWmCostProcessInWork cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
			AaDepartment adt=cacheCtrlService.getDepartment(cri.getDeptGid());
			if(adt!=null){
				cri.setDeptName(adt.getDepname());
			}
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	public Map getCostMaterialWorkList(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"' ";
		
		PageBean data = costDao.getCostMaterialWorkList(pageIndex ,pageSize,condition);
		List<MESWmCostMaterialWork> list = data.getList();
		
		for(MESWmCostMaterialWork cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsCode(aaGoods.getGoodscode());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
			AaGoods aaProductGoods=cacheCtrlService.getGoods(cri.getProductGoodsGid());
			cri.setProductGoodsCode(aaProductGoods.getGoodscode());
			cri.setProductGoodsName(aaProductGoods.getGoodsname());
			cri.setProductGoodsStandard(aaProductGoods.getGoodsstandard());
			
			AaDepartment adt=cacheCtrlService.getDepartment(cri.getDeptGid());
			if(adt!=null){
				cri.setDeptName(adt.getDepname());
			}
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	
	public Map getCostDirectMaterialWorkList2(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"' ";
		
		PageBean data = costDao.getCostDirectMaterialWorkList2(pageIndex ,pageSize,condition);
		List<MESWmCostDirectMaterialWork2> list = data.getList();
		
		for(MESWmCostDirectMaterialWork2 cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsCode(aaGoods.getGoodscode());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	
	public Map getCostFeeList(HttpServletRequest request){
		String mainGid=request.getParameter("mainGid");
		
		List<MesWmCostFee> costFee = costDao.getCostFeeList(mainGid);
		
		Map mapRes=new HashMap();
		mapRes.put("data", costFee);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	public Map getRdRecordsOutList(HttpServletRequest request,int pageIndex,int pageSize){
		String mainGid=request.getParameter("mainGid");
		String condition=" and mainGid='"+mainGid+"'";
		
		PageBean data = costDao.getRdRecordsOutList(pageIndex ,pageSize,condition);
		List<MESWMCostRdRecordsOut> list = data.getList();
		
		for(MESWMCostRdRecordsOut cri:list){
			AaGoods aaGoods=cacheCtrlService.getGoods(cri.getGoodsGid());
			cri.setGoodsName(aaGoods.getGoodsname());
			cri.setGoodsStandard(aaGoods.getGoodsstandard());
			
			AaWarehouse ah=cacheCtrlService.getWareHouse(cri.getCwhGid());
			cri.setCwhName(ah.getWhname());
			
		}
		
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		mapRes.put("mainGid", mainGid);
		
		return mapRes;
	}
	
	
	public List<Map> getReportProductInListNoPage(String mainGid){
		String condition=" and mainGid='"+mainGid+"' order by r.goodsCode,r.mainLowOrderCode,r.secondLowOrderCode ";
		List<Map> list = costDao.dispatchingStartListNoPage(condition); 
		return list;
	}
	
	public Map getCostFeeMainList(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostFeeMainList(pageIndex ,pageSize,condition);
		List<MesWmCostFee> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	public Map getCostRdRecordsOutMain(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostRdRecordsOutMain(pageIndex ,pageSize,condition);
		List<MESWmCostRdRecordsOutMain> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	public Map getCostReportInInforMain(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostReportInInforMain(pageIndex ,pageSize,condition);
		List<MESWmCostReportInInforMain> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	public Map getCostGoodsBalanceMain(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostGoodsBalanceMain(pageIndex ,pageSize,condition);
		List<MESWmCostGoodsBalanceMain> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	public Map getCostProcessInWorkMain(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostProcessInWorkMain(pageIndex ,pageSize,condition);
		List<MESWmCostReportInInforMain> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	public Map getCostMaterialWorkMain(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostMaterialWorkMain(pageIndex ,pageSize,condition);
		List<MESWmCostReportInInforMain> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	public Map getCostDirectMaterialWorkMain2(HttpServletRequest request,int pageIndex,int pageSize){
		String condition="";
		PageBean data = costDao.getCostDirectMaterialWorkMain2(pageIndex ,pageSize,condition);
		List<MESWmCostDirectMaterialWorkMain2> list = data.getList();
		Map mapRes=new HashMap();
		mapRes.put("pageBean", data);
		return mapRes;
	}
	
	
	//获得会计期间
	Map getPeriod(int year,int month){
		String businessDataBase=Config.BUSINESSDATABASE;
		String[] strs=businessDataBase.split("_");
		String caccid=strs[1];
		return costDao.getPeriod(year, month, caccid);
	}
	
	
	public String uptCostFee(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String feeGid=request.getParameter("feeGid");
		String toUptFee=request.getParameter("toUptFee");
		
		costDao.uptCostFee(feeGid,toUptFee);
		
		jobj.put("success", 1);
		jobj.put("failInfor", "计算成功");
		return jobj.toString();
		
	}
	
	
	//更新u8单据
	public String uptRecords(HttpServletRequest request){
		
		JSONObject jobj=new JSONObject();
		
		String mainGid=request.getParameter("mainGid");
		MESWmCostReportInInforMain rmain=costDao.getMESWmCostReportInInforMain(mainGid);
		
		if(CommonUtil.isNullObject(rmain.getState())){
			jobj.put("success", 0);
			jobj.put("failInfor", "请先分摊工和费");
			return jobj.toString();
		}
		
		
		if(rmain.getState().intValue()!=2){
			jobj.put("success", 0);
			jobj.put("failInfor", "请先分摊工和费,或已完成，请检查状态");
			return jobj.toString();
		}
		
		
		
		int year=rmain.getIyear().intValue();
		int month=rmain.getImonth().intValue();
		
		Map map=costDao.getMainLowOrderCode(mainGid);

		//更新入库单据
		costDao.uptRdrecordsIn(year,month);
		
		//更新出库单价
		costDao.uptRdrecordsOut(year,month);
		
		jobj.put("success", 1);
		jobj.put("failInfor", "计算成功");
		return jobj.toString();
		
	}
	
	
	
	public static void main(String[] args) {
		
	}
	
}
