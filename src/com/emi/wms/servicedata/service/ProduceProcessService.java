package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.ProduceProcessDao;

public class ProduceProcessService {
	Logger logger = Logger.getLogger(ProduceProcessService.class);
	private ProduceOrderService produceOrderService;
	private ProduceProcessDao produceprocessDao;
	private CacheCtrlService cacheCtrlService;
	private ProduceOrderDao produceOrderDao;

	public ProduceOrderService getProduceOrderService() {
		return produceOrderService;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public ProduceOrderDao getProduceOrderDao() {
		return produceOrderDao;
	}

	public void setProduceOrderService(ProduceOrderService produceOrderService) {
		this.produceOrderService = produceOrderService;
	}

	public void setProduceOrderDao(ProduceOrderDao produceOrderDao) {
		this.produceOrderDao = produceOrderDao;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public ProduceProcessDao getProduceprocessDao() {
		return produceprocessDao;
	}

	public void setProduceprocessDao(ProduceProcessDao produceprocessDao) {
		this.produceprocessDao = produceprocessDao;
	}

	public boolean addproduceprocess(MesWmProduceProcessroute produceprocess) {
		return produceprocessDao.addproduceprocess(produceprocess);
	}
	public boolean addproduceprocessreason(QMCheckCReasonBill reason) {
		return produceprocessDao.addproduceprocessreason(reason);
	}
	public boolean addprocusbook(List list) {
		return produceprocessDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return produceprocessDao.findorg(exhTypeId);
	}
	public Map findproduceprocess(String produceprocessgid) {
		return produceprocessDao.findproduceprocess(produceprocessgid);
	}
	public boolean updateorg(AaOrg aaorg) {
		return produceprocessDao.updateorg(aaorg);
	}
	public List getproduceprocessbookList(String exhTypeId) {
		return produceprocessDao.getproduceprocessbookList(exhTypeId);
	}
	public boolean deleteproduceprocess(String orgclassid) {
		return  produceprocessDao.deleteproduceprocess(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.produceprocessDao.findorgchild(orgclassid);
	}
	public void setproduceprocessEnable(int enable, String id) {
		produceprocessDao.setproduceprocessEnable(enable,id);
	}
	public boolean addproduceprocessc(List list) {
		return produceprocessDao.addproduceprocessc(list);
	}
	public boolean updateproduceprocessc(List list) {
		return produceprocessDao.updateproduceprocessc(list);
	}
	public boolean updateproduceprocess(MesWmProduceProcessroute produceprocess) {
		return produceprocessDao.updateproduceprocess(produceprocess);
	}
	public boolean updateymuser(YmUser YmUser) {
		return produceprocessDao.updateymuser(YmUser);
	}
	public List getproduceprocessclist(String purchaseproduceprocessUid) {
		return produceprocessDao.getproduceprocessclist(purchaseproduceprocessUid);
	}
	public List getdispatchingorderclist(String purchaseproduceprocessUid) {
		return produceprocessDao.getdispatchingorderclist(purchaseproduceprocessUid);
	}
	public PageBean getreasonlist(int pageIndex,int pageSize,String produceprocesscgid) {
		return produceprocessDao.getreasonlist(pageIndex,pageSize,produceprocesscgid);
	}
	public List getaareasonlist() {
		return produceprocessDao.getaareasonlist();
	}
	public PageBean getproduceprocesslist(int pageIndex,int pageSize,String condition) {
		return produceprocessDao.getproduceprocesslist(pageIndex,pageSize,condition);
	}
	public PageBean dispatchingorderList(int pageIndex,int pageSize,String condition) {
		return produceprocessDao.dispatchingorderList(pageIndex,pageSize,condition);
	}
	
	/**
	 * @category 开工时，根据订单号获取相关信息
	 *2016 2016年7月11日下午1:27:52
	 *void
	 *宋银海
	 */
	public List<Map> getProduceProcessInforByOrder(String produceCuid){
		
		String condition=" ppr.produceCuid='"+produceCuid+"'";
		List<Map> maps=produceprocessDao.getProduceProcessInforByOrder(condition);
		for(Map map:maps){
			MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(map.get("opgid").toString());
			map.put("opName", mesWmStandardprocess.getOpname());
		}
		return maps;
	}
	
	
	//查询开工最后一条记录	
//	@SuppressWarnings("rawtypes")
//	public Map findDispatching(String gid,String orgId,String sobId) {
//		Map map=produceprocessDao.findDispatching(gid, orgId, sobId);
//		
//		
//		return map;
//	}
	
	/**
	 * @category 提交放行
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public boolean letPass(String gid){
		String condition=" gid='"+gid+"'";
		produceprocessDao.letPass(condition);
		return true;
	}

	/**
	 * @category 开工列表
	 * 2016年7月25日 上午10:43:05 
	 * @author zhuxiaochen
	 * @return
	 */
	public PageBean dispatchingStartList(int pageIndex,int pageSize,String condition) {
		PageBean data = produceprocessDao.dispatchingStartList(pageIndex,pageSize,condition);
		List<MesWmDispatchingorderc> list = data.getList();
		for(MesWmDispatchingorderc dis : list){
			String opGid = dis.getOpGid();
			String goodsId = dis.getGoodsId();
			int dispatchingType = dis.getObjectType()==null?0:dis.getObjectType();
			String dispatchingObjId = dis.getPersonUnitVendorGid();
			
			MesWmStandardprocess process = cacheCtrlService.getMESStandardProcess(opGid);//工序
			AaGoods goods = cacheCtrlService.getGoods(goodsId);//产品
			if(process!=null){
				dis.setOpName(process.getOpname());
			}
			if(goods!=null){
				dis.setGoodsCode(goods.getGoodscode());
				dis.setGoodsName(goods.getGoodsname());
				dis.setGoodsStandard(goods.getGoodsstandard());
			}
			if(dispatchingType==0){
				AaPerson person = cacheCtrlService.getPerson(dispatchingObjId);//人
				if(person!=null){
					dis.setDispatchingObjName(person.getPername());
				}
			}
			if(dispatchingType==1){
				AaGroup group = cacheCtrlService.getAaGroup(dispatchingObjId);//组
				if(group!=null){
					dis.setDispatchingObjName(group.getGroupname());
				}
			}
			
//			String stationGid=dis.getStationGid();
//			if(!CommonUtil.isNullObject(stationGid)){
//				Equipment et=cacheCtrlService.getMESEquipment(stationGid);
//				
//				if(et!=null){
//					dis.setEquipmentCode(et.getEquipmentcode());
//					dis.setEquipmentName(et.getEquipmentname());
//					
//					String departmentGid=et.getDepartment();
//					if(departmentGid!=null){
//						AaDepartment dt=cacheCtrlService.getDepartment(departmentGid);
//						dis.setEquipmentDeptName(dt.getDepname());
//					}
//				}
//			}
//			
//			String mouldGid=dis.getMouldGid();
//			if(CommonUtil.isNullObject(mouldGid)){
//				Mould m=cacheCtrlService.getMould(mouldGid);
//				if(m!=null){
//					dis.setMouldCode(m.getMouldcode());//
//					dis.setMouldName(m.getMouldname());//
//					dis.setMouldRatio(m.getMouldRatio());//模比
//				}
//				
//			}
			
			
		}
		return data;
	}

	/**
	 * @category 报工列表
	 * 2016年7月25日 下午5:33:42 
	 * @author zhuxiaochen
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean dispatchingReportList(int pageIndex, int pageSize,String condition) {
		PageBean data = produceprocessDao.dispatchingReportList(pageIndex,pageSize,condition);
		List<MesWmReportorderc> list = data.getList();
		for(MesWmReportorderc dis : list){
			String opGid = dis.getOpGid();
			String goodsId = dis.getGoodsId();
			int dispatchingType = dis.getObjectType()==null?0:dis.getObjectType();
			String dispatchingObjId = dis.getPersonUnitVendorGid();
			
			MesWmStandardprocess process = cacheCtrlService.getMESStandardProcess(opGid);//工序
			AaGoods goods = cacheCtrlService.getGoods(goodsId);//产品
			if(process!=null){
				dis.setOpName(process.getOpname());
			}
			if(goods!=null){
				dis.setGoodsCode(goods.getGoodscode());
				dis.setGoodsName(goods.getGoodsname());
				dis.setGoodsStandard(goods.getGoodsstandard());
			}
			if(dispatchingType==0){
				AaPerson person = cacheCtrlService.getPerson(dispatchingObjId);//人
				if(person!=null){
					dis.setDispatchingObjName(person.getPername());
				}
			}
			if(dispatchingType==1){
				AaGroup group = cacheCtrlService.getAaGroup(dispatchingObjId);//组
				if(group!=null){
					dis.setDispatchingObjName(group.getGroupname());
				}
			}
			
			
//			String stationGid=dis.getStationGid();
//			if(!CommonUtil.isNullObject(stationGid)){
//				Equipment et=cacheCtrlService.getMESEquipment(stationGid);
//				
//				if(et!=null){
//					dis.setEquipmentCode(et.getEquipmentcode());
//					dis.setEquipmentName(et.getEquipmentname());
//					
//					String departmentGid=et.getDepartment();
//					if(departmentGid!=null){
//						AaDepartment dt=cacheCtrlService.getDepartment(departmentGid);
//						dis.setEquipmentDeptName(dt.getDepname());
//					}
//				}
//			}
//			
//			String mouldGid=dis.getMouldGid();
//			if(CommonUtil.isNullObject(mouldGid)){
//				Mould m=cacheCtrlService.getMould(mouldGid);
//				if(m!=null){
//					dis.setMouldCode(m.getMouldcode());//
//					dis.setMouldName(m.getMouldname());//
//					dis.setMouldRatio(m.getMouldRatio());//模比
//				}
//				
//			}
			
			
		}
		return data;
	}
	
	
	/**
	 * @category 跳转到开工页面
	 *2016 2016年7月11日上午10:48:31
	 *String
	 *宋银海
	 */
	public Map toStartWork(HttpServletRequest request){
		
		String disOrderGid = request.getParameter("disOrderGid");
		String orgId=request.getSession().getAttribute("OrgId").toString();
		String sobId=request.getSession().getAttribute("SobId").toString();
		Map disOrder=produceprocessDao.findDispatching(disOrderGid, orgId, sobId);
		
		if(!CommonUtil.isNullObject(disOrder)){

			List<Map> childrenMap=produceprocessDao.findDispatchings(CommonUtil.Obj2String(disOrder.get("gid")));
			for(Map map:childrenMap){//////////////////////////////////////派工信息
				if(disOrder.get("dispatchingObj").toString().equalsIgnoreCase("0")){//人
					AaPerson p=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
					map.put("disObjGid", p.getGid());
					map.put("disObjName", p.getPername());
				}else if(disOrder.get("dispatchingObj").toString().equalsIgnoreCase("1")){//组
					AaGroup g=cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
					map.put("disObjGid", g.getGid());
					map.put("disObjName", g.getGroupname());
				}
				
				MesWmStandardprocess mws=cacheCtrlService.getMESStandardProcess(map.get("opGid").toString());
				map.put("opname", mws.getOpname());
			}
			
			String condition=" pprc.gid='"+childrenMap.get(0).get("produceProcessRouteCGid").toString()+"'";
			Map order=produceOrderDao.getOrderInforByProduceProcessroutec(condition);
			condition=" poc.gid='"+order.get("pocgid").toString()+"'";
			Map orderInfor=produceprocessDao.getOrderInforByOrderId(condition);///////////////////////////////////获取订单产品信息
			AaGoods gs=cacheCtrlService.getGoods(orderInfor.get("goodsUid").toString());
			orderInfor.put("goodsCode", gs.getGoodscode());
			orderInfor.put("goodsName", gs.getGoodsname());
			orderInfor.put("goodsStandand", gs.getGoodsstandard());
			
			ProcessStartScanRsp pssr=produceOrderService.getInfoByBarcodeStart(order.get("barcode").toString(), disOrder.get("dispatchingObj").toString(),null);//返回可派工数量
			
			MesWmDispatchingorderc dis=new MesWmDispatchingorderc();
			Object stationGid=disOrder.get("stationGid");
			if(!CommonUtil.isNullObject(stationGid)){
				Equipment et=cacheCtrlService.getMESEquipment(stationGid.toString());
				
				if(!CommonUtil.isNullObject(et)){
					dis.setEquipmentCode(et.getEquipmentcode());
					dis.setEquipmentName(et.getEquipmentname());
					
					String departmentGid=et.getDepartment();
					if(!CommonUtil.isNullObject(departmentGid)){
						AaDepartment dt=cacheCtrlService.getDepartment(departmentGid);
						dis.setEquipmentDeptName(dt.getDepname());
					}
				}
			}
			
			Object mouldGid=disOrder.get("mouldGid");
			if(!CommonUtil.isNullObject(mouldGid)){
				Mould m=cacheCtrlService.getMould(mouldGid.toString());
				if(!CommonUtil.isNullObject(m)){
					dis.setMouldCode(m.getMouldcode());//
					dis.setMouldName(m.getMouldname());//
					dis.setMouldRatio(m.getMouldRatio());//模比
				}
				
			}
			
			
			disOrder.put("canDisnum", CommonUtil.isNullObject(pssr.getTask())?0:pssr.getTask().getCanDisnum());
			disOrder.put("childrenMap", childrenMap);
			disOrder.put("orderInfor", orderInfor);
			disOrder.put("dis", dis);
			
		}
		
		
		return disOrder;
	}
	
	
	
	/**
	 * @category 跳转到报工页面
	 *2016 2016年7月11日上午10:48:31
	 *String
	 *宋银海
	 */
	public Map toReportWork(HttpServletRequest request){
		
		String reportGid = request.getParameter("reportGid");
		String orgId=request.getSession().getAttribute("OrgId").toString();
		String sobId=request.getSession().getAttribute("SobId").toString();
		Map reportOrder=produceprocessDao.findReport(reportGid, orgId, sobId);
		
		if(!CommonUtil.isNullObject(reportOrder)){
			List<Map> childrenMap=produceprocessDao.findReports(CommonUtil.Obj2String(reportOrder.get("gid")));
			for(Map map:childrenMap){//////////////////////////////////////派工信息
				if(reportOrder.get("dispatchingObj").toString().equalsIgnoreCase("0")){//人
					AaPerson p=cacheCtrlService.getPerson(map.get("personUnitVendorGid").toString());
					map.put("reportObjGid", p.getGid());
					map.put("reportObjName", p.getPername());
				}else if(reportOrder.get("dispatchingObj").toString().equalsIgnoreCase("1")){//组
					AaGroup g=cacheCtrlService.getAaGroup(map.get("personUnitVendorGid").toString());
					map.put("reportObjGid", g.getGid());
					map.put("reportObjName", g.getGroupname());
				}
				
				map.put("candisNum", CommonUtil.object2BigDecimal(map.get("disNum")).subtract(CommonUtil.object2BigDecimal(map.get("docReportOkNum"))).subtract(CommonUtil.object2BigDecimal(map.get("docReportNotOkNum"))) );//可报工数量
				
				MesWmStandardprocess mws=cacheCtrlService.getMESStandardProcess(map.get("opGid").toString());
				map.put("opname", mws.getOpname());
				
				
				Object stationGid=map.get("stationGid");
				if(!CommonUtil.isNullObject(stationGid)){
					Equipment et=cacheCtrlService.getMESEquipment(stationGid.toString());
					
					if(!CommonUtil.isNullObject(et)){
						map.put("equipmentcode", et.getEquipmentcode());
						map.put("equipmentname", et.getEquipmentname());
					}
				}
				
				Object departmentGid=map.get("deptGid");
				if(!CommonUtil.isNullObject(departmentGid)){
					AaDepartment dt=cacheCtrlService.getDepartment(departmentGid.toString());
					map.put("depname", dt.getDepname());
				}
				
				Object mouldGid=map.get("mouldGid");
				if(!CommonUtil.isNullObject(mouldGid)){
					Mould m=cacheCtrlService.getMould(mouldGid.toString());
					if(!CommonUtil.isNullObject(m)){
						map.put("mouldcode",m.getMouldcode());
						map.put("mouldname",m.getMouldname());
						map.put("mouldRatio",m.getMouldRatio());
					}
					
				}
				
				
			}
			
			String condition=" pprc.gid='"+childrenMap.get(0).get("produceProcessRouteCGid").toString()+"'";
			Map order=produceOrderDao.getOrderInforByProduceProcessroutec(condition);
			condition=" poc.gid='"+order.get("pocgid").toString()+"'";
			Map orderInfor=produceprocessDao.getOrderInforByOrderId(condition);///////////////////////////////////获取订单产品信息
			AaGoods gs=cacheCtrlService.getGoods(orderInfor.get("goodsUid").toString());
			orderInfor.put("goodsCode", gs.getGoodscode());
			orderInfor.put("goodsName", gs.getGoodsname());
			orderInfor.put("goodsStandand", gs.getGoodsstandard());
			
			ProcessStartScanRsp pssr=produceOrderService.getInfoByBarcodeStart(order.get("barcode").toString(), reportOrder.get("dispatchingObj").toString(),null);//返回可派工数量
			
			reportOrder.put("canDisnum", pssr.getTask().getCanDisnum());
			reportOrder.put("childrenMap", childrenMap);
			reportOrder.put("orderInfor", orderInfor);
		}
		
		return reportOrder;
	}
	
}
