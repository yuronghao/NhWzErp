package com.emi.wms.servicedata.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.ReportFormDao;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ReportFormService {

	private CacheCtrlService cacheCtrlService;
	private ReportFormDao reportFormDao;
	private BasicSettingService basicSettingService;
	private ProduceOrderDao produceOrderDao;

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public ReportFormDao getReportFormDao() {
		return reportFormDao;
	}

	public BasicSettingService getBasicSettingService() {
		return basicSettingService;
	}

	public ProduceOrderDao getProduceOrderDao() {
		return produceOrderDao;
	}

	public void setReportFormDao(ReportFormDao reportFormDao) {
		this.reportFormDao = reportFormDao;
	}
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	public void setProduceOrderDao(ProduceOrderDao produceOrderDao) {
		this.produceOrderDao = produceOrderDao;
	}

	/**
	 * @category 获得计件工资汇总表
	 *2016 2016年6月16日下午4:30:39
	 *List<Map>
	 *宋银海
	 */
	public PageBean getPieceworkSum(Map params){
		
		String depUid=CommonUtil.Obj2String(params.get("depUid"));//部门gid
		
		String docCondition=" 1=1 ";
		String rocCondition=" 1=1 ";
		
		String personGid=CommonUtil.Obj2String(params.get("personGid"));
		String groupGid=CommonUtil.Obj2String(params.get("groupGid"));
		String startMonth=CommonUtil.Obj2String(params.get("startMonth"));
		String endMonth=CommonUtil.Obj2String(params.get("endMonth"));
		
		if(!CommonUtil.isNullObject(personGid) && CommonUtil.isNullObject(groupGid)){
			
			docCondition=docCondition+" and  t.personUnitVendorGid in ("+CommonUtil.cutLastString(personGid, ",")+") ";
			rocCondition=rocCondition+" and t2.personUnitVendorGid in ("+CommonUtil.cutLastString(personGid, ",")+") ";
		
		}else if(CommonUtil.isNullObject(personGid) && !CommonUtil.isNullObject(groupGid)){
			
			docCondition=docCondition+" and   t.personUnitVendorGid in ("+CommonUtil.cutLastString(groupGid, ",")+") ";
			rocCondition=rocCondition+" and  t2.personUnitVendorGid in ("+CommonUtil.cutLastString(groupGid, ",")+") ";
		
		}else if(!CommonUtil.isNullObject(personGid) && !CommonUtil.isNullObject(groupGid)){
			docCondition=docCondition+" and  (t.personUnitVendorGid in ("+CommonUtil.cutLastString(personGid, ",")+") or  t.personUnitVendorGid in ("+CommonUtil.cutLastString(groupGid, ",")+")) ";
			rocCondition=rocCondition+" and (t2.personUnitVendorGid in ("+CommonUtil.cutLastString(personGid, ",")+") or t2.personUnitVendorGid in ("+CommonUtil.cutLastString(groupGid, ",")+")) ";
		}
		
		if (!CommonUtil.isNullObject(depUid)) {
			
			docCondition=docCondition+" and  (t.deptgid in ("+CommonUtil.cutLastString(depUid, ",")+") or t.deptgid in ("+CommonUtil.cutLastString(depUid, ",")+")) ";
			rocCondition=rocCondition+" and (t2.deptgid in ("+CommonUtil.cutLastString(depUid, ",")+") or t2.deptgid in ("+CommonUtil.cutLastString(depUid, ",")+")) ";
		}
		
		if(!CommonUtil.isNullObject(startMonth)){
			docCondition=docCondition+" and t.startTime >= '"+startMonth+"'";
			rocCondition=rocCondition+" and t2.endTime  >= '"+startMonth+"'";
		}
		
		if(!CommonUtil.isNullObject(endMonth)){
			String[] strs=endMonth.split("-");
			int day=DateUtil.getDaysOfMonth(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
			
			docCondition=docCondition+" and t.startTime <= '"+endMonth+" 23:59:59'";
			rocCondition=rocCondition+" and t2.endTime  <= '"+endMonth+" 23:59:59'";
		}
		
		int pageIndex =Integer.parseInt(params.get("pageIndex").toString());
		int pageSize =Integer.parseInt(params.get("pageSize").toString());
		
		PageBean pageBean=reportFormDao.getPieceworkSum(pageIndex, pageSize, docCondition,rocCondition);
		
		List<Map> maps=pageBean.getList();
		for(Map map:maps){
			
			if(!CommonUtil.isNullObject(map.get("deptgid"))){
				AaDepartment ad=cacheCtrlService.getDepartment(map.get("deptgid").toString());
				map.put("deptName", ad.getDepname());
			}
			
			if(Integer.parseInt(map.get("dispatchingObj").toString())==0){
				AaPerson aaPerson=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
				map.put("totalPrice", CommonUtil.object2BigDecimal(map.get("totalPrice")));
				map.put("personCode", aaPerson.getPercode());
				map.put("personName", aaPerson.getPername());
				map.put("personGid", aaPerson.getGid());
				
			}else if(Integer.parseInt(map.get("dispatchingObj").toString())==1){
				AaGroup aaGroup =cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
				map.put("totalPrice", CommonUtil.object2BigDecimal(map.get("totalPrice")));
				map.put("groupCode", CommonUtil.isNullObject(aaGroup)?"":aaGroup.getCode());
				map.put("groupName", CommonUtil.isNullObject(aaGroup)?"":aaGroup.getGroupname());
				map.put("groupGid", CommonUtil.isNullObject(aaGroup)?"":aaGroup.getGid());
			}
			
		}
		
		return pageBean;
	}


	/**
	 * @category 计件工资详情表
	 * 2016年6月16日 下午5:50:33 
	 * @author zhuxiaochen
	 * @param billcode
	 * @param deptId
	 * @param personId
	 * @param groupId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean getPieceworkDetail(String billcode, String deptId,
			String personId, String groupId, String startDate, String endDate,int pageIndex,int pageSize) {
		PageBean pageBean = reportFormDao.getPieceworkDetail(billcode, deptId,personId, groupId, startDate, endDate,pageIndex,pageSize);
		List<Map> list = pageBean.getList();
		
		for(Map m : list){
			AaGoods goods = cacheCtrlService.getGoods(CommonUtil.Obj2String(m.get("goodsUid")));
			
			AaPerson person=null;
			AaGroup group=null;
			
			if(CommonUtil.Obj2String(m.get("dispatchingObj")).equalsIgnoreCase("0")){//人
				person = cacheCtrlService.getPerson(CommonUtil.Obj2String(m.get("personUnitVendorGid")));
			}else if(CommonUtil.Obj2String(m.get("dispatchingObj")).equalsIgnoreCase("1")){//组
				group = cacheCtrlService.getAaGroup(CommonUtil.Obj2String(m.get("personUnitVendorGid")));
			}
			
/*			AaPerson person = cacheCtrlService.getPerson(CommonUtil.Obj2String(m.get("personUnitVendorGid")));
			AaGroup group = cacheCtrlService.getAaGroup(CommonUtil.Obj2String(m.get("personUnitVendorGid")));*/
			
//			MesWmStandardprocess process = cacheCtrlService.getMESStandardProcess(CommonUtil.Obj2String(m.get("opGid")));
//			AaDepartment dept = cacheCtrlService.getDepartment(CommonUtil.Obj2String(m.get("deptGid")));
			
			String condition=" gid='"+ m.get("produceProcessRouteCGid")+"'";
//			MesWmProduceProcessroutec mWmProduceProcessroutec=produceOrderDao.getMesWmProduceProcessroutec(condition);
			m.put("goodsCode", goods==null?"": goods.getGoodscode());
			m.put("goodsName", goods==null?"": goods.getGoodsname());
			m.put("goodsstandard", goods==null?"":goods.getGoodsstandard());
			m.put("processName", CommonUtil.Obj2String(m.get("opname")));
			m.put("personCode",  person==null?"": person.getPercode());
			m.put("personName",  person==null?"": person.getPername());
			m.put("groupCode",  group==null?"": group.getCode());
			m.put("groupName",  group==null?"": group.getGroupname());
			m.put("departmentName",  CommonUtil.Obj2String(m.get("depName")));
//			m.put("opdes",mWmProduceProcessroutec==null?"":mWmProduceProcessroutec.getOpdes());
		}
		return pageBean;
	}
	
	
	/**
	 * @category 获得订单状态
	 * 宋银海
	 */
	public PageBean  getOrderStatusList(Map params){
		
		int pageIndex =Integer.parseInt(params.get("pageIndex").toString());
		int pageSize =Integer.parseInt(params.get("pageSize").toString());
		
		String condition="";
		String keyWord="";
		String startDate="";
		String endDate="";
		if(!CommonUtil.isNullObject(params.get("keyWord"))){
			keyWord=params.get("keyWord").toString();
			condition+="  (goodsCode like '%"+keyWord+"%' or goodsName like '%"+keyWord+"%' or goodsStandard like '%"+keyWord+"%')";
		}
		
		List<Map> goodsList=new ArrayList<Map>();
		if(!CommonUtil.isNullObject(condition)){
			goodsList=basicSettingService.getGoodsListMap(condition);
		}
		
		StringBuffer sb=new StringBuffer();
		for(Map map:goodsList){
			sb.append("'"+map.get("gid").toString()+"',");
		}
		condition=sb.toString();
		if(!CommonUtil.isNullObject(condition)){
			condition="("+condition.substring(0, condition.length()-1)+")";
			condition="  poc.goodsUid in "+condition+" or billCode='"+keyWord+"'";
		}else{
			condition=" 1=1 ";
			if(!CommonUtil.isNullObject(keyWord)){
				condition+=" and billCode='"+keyWord+"'";
			}
		}
		
		if(!CommonUtil.isNullObject(params.get("startDate"))){
			startDate=params.get("startDate").toString();
			condition+="  and po.billDate>='"+startDate+"'  ";
		}
		if(!CommonUtil.isNullObject(params.get("endDate"))){
			endDate=params.get("endDate").toString();
			condition+="  and po.billDate<='"+endDate+" 23:59:59' ";
		}
		
		PageBean pb=reportFormDao.getOrderStatusList(pageIndex, pageSize, condition);//满足条件的订单
		List<Map> results=pb.getList();
		
		StringBuffer sbf=new StringBuffer();
		for(Map m:results){
			sbf.append("'"+m.get("pocgid").toString()+"',");
		}
		condition=sbf.toString();
		if(!CommonUtil.isNullObject(condition)){
			condition="("+condition.substring(0, condition.length()-1)+")";
			condition="  ppr.produceCuid in "+condition+" and (nextGid is null or nextGid='') ";
		}else{
			condition=" 1=1 ";
		}
		
		List<Map> routec=produceOrderDao.getProductStepSituation(condition);//订单工艺路线信息
		
		for(Map m:results){
			AaGoods gs=cacheCtrlService.getGoods(m.get("goodsUid").toString());
			m.put("goodsCode", gs.getGoodscode());
			m.put("goodsName", gs.getGoodsname());
			m.put("goodsStandard", gs.getGoodsstandard());
			m.put("reportedNum", 0);
			m.put("unFinishNum", CommonUtil.object2BigDecimal(m.get("number")).subtract(CommonUtil.object2BigDecimal(m.get("reportedNum"))).subtract(CommonUtil.object2BigDecimal(m.get("turnoutNum")))  );
			
			for(Map rc:routec){
				if(m.get("pocgid").toString().equalsIgnoreCase(rc.get("produceCuid").toString())){
					m.put("reportedNum", CommonUtil.object2BigDecimal(rc.get("reportOkNum")).add(CommonUtil.object2BigDecimal(rc.get("reportNotOkNum"))));
					m.put("unFinishNum", CommonUtil.object2BigDecimal(m.get("number")).subtract(CommonUtil.object2BigDecimal(m.get("reportedNum"))).subtract(CommonUtil.object2BigDecimal(m.get("turnoutNum"))) );
					
					break;
				}
			}
		}
		
		
		return pb;
	}

	/**
	 * @category 订单详情表
	 * 2017年2月27日 下午1:26:18 
	 * @author zhuxiaochen
	 * @param params
	 * @return
	 */
	public PageBean getOrderDetailList(Map params) {
		int pageIndex =Integer.parseInt(params.get("pageIndex").toString());
		int pageSize =Integer.parseInt(params.get("pageSize").toString());
		String condition="";
		String keyWord="";
		String startDate="";
		String endDate="";
		if(!CommonUtil.isNullObject(params.get("keyWord"))){
			keyWord=params.get("keyWord").toString();
			condition+=" and (po.billCode like '%"+keyWord+"%' or standardprocess.opname like '%"+keyWord+"%' )";
		}
		if(!CommonUtil.isNullObject(params.get("startDate"))){
			startDate=params.get("startDate").toString();
			condition+=" and po.billDate>='"+startDate+"'";
		}
		if(!CommonUtil.isNullObject(params.get("endDate"))){
			endDate=params.get("endDate").toString();
			condition+=" and po.billDate<='"+endDate+" 23:59:59'";
		}
		
		PageBean bean = reportFormDao.getOrderDetailList(pageIndex,pageSize,condition);
		List<Map> results=bean.getList();
		for(Map m:results){
			AaGoods gs=cacheCtrlService.getGoods(m.get("goodsUid").toString());
			m.put("goodsCode", gs.getGoodscode());
			m.put("goodsName", gs.getGoodsname());
			m.put("goodsStandard", gs.getGoodsstandard());
			m.put("goodsUnitName", gs.getUnitName());
//			m.put("reportedNum", 0);
//			m.put("unFinishNum", CommonUtil.object2BigDecimal(m.get("number")).subtract(CommonUtil.object2BigDecimal(m.get("reportedNum"))).subtract(CommonUtil.object2BigDecimal(m.get("turnoutNum")))  );
			
//			m.put("reportedNum", CommonUtil.object2BigDecimal(rc.get("reportOkNum")).add(CommonUtil.object2BigDecimal(rc.get("reportNotOkNum"))));
//			m.put("unFinishNum", CommonUtil.object2BigDecimal(m.get("number")).subtract(CommonUtil.object2BigDecimal(m.get("reportedNum"))).subtract(CommonUtil.object2BigDecimal(m.get("turnoutNum"))) );
					
		}
		
		return bean;
	}
	
	
}
