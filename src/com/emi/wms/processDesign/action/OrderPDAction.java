package com.emi.wms.processDesign.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.processDesign.service.OrderPDService;
import com.emi.wms.processDesign.util.NotEnoughException;
import com.emi.wms.servicedata.service.ProduceOrderService;

/*
 * 订单工艺路线设计
 */
@SuppressWarnings({"unchecked","rawtypes"})	
public class OrderPDAction extends BaseAction{
	private static final long serialVersionUID = 4920894976410036457L;
	private BasePDService basePDService;
	private OrderPDService orderPDService;
	private CacheCtrlService cacheCtrlService;
	private ProduceOrderService produceOrderService;
	

	public void setBasePDService(BasePDService basePDService) {
		this.basePDService = basePDService;
	}
	public void setOrderPDService(OrderPDService orderPDService) {
		this.orderPDService = orderPDService;
	}
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}
	
	public void setProduceOrderService(ProduceOrderService produceOrderService) {
		this.produceOrderService = produceOrderService;
	}
	/**
	 * @category 检测是否已有进行的任务
	 * 2016年4月28日 下午5:11:43 
	 * @author zhuxiaochen
	 */
	public void checkDoingTask(){
		try {
			String orderId = getParameter("orderId");
			String type = getParameter("type");//0:领料任务 
			boolean hasDoingTask = orderPDService.checkDoingTask(orderId,Integer.parseInt(type));
			responseWrite(hasDoingTask?"1":"0");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkDoingTaskByOrderc(){
		try {
			String ordercId = getParameter("ordercId");
			String type = getParameter("type");//0:领料任务 
			boolean hasDoingTask = orderPDService.checkDoingTaskByOrderc(ordercId,Integer.parseInt(type));
			responseWrite(hasDoingTask?"1":"0");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 生成订单工艺路线
	 * 2016年4月28日 下午5:11:43 
	 * @author zhuxiaochen
	 */
	public void genOrderRoute(){
		try {
			String orderId = getParameter("orderId");
			String reGen = getParameter("reGen");//是否重新生成（先暂时不用，以后拓展再说）
			
			boolean re = "1".equals(reGen);
			JSONObject jobj=orderPDService.genOrderRoute(orderId,false);
			responseWrite(jobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorOrSuccess(0, "生成失败！");
		}
	}
	
	/**
	 * @category 单个产品生成工艺路线
	 * 2016年7月15日 下午5:22:36 
	 * @author zhuxiaochen
	 */
	public void genProductRoute(){
		try {
			String orderId = getParameter("orderId");
			String ordercId = getParameter("ordercId");
			
			orderPDService.genProductRoute(orderId,ordercId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 设计订单工艺路线
	 * 2016年5月3日 上午9:23:04 
	 * @author zhuxiaochen
	 * @return
	 */
	public String toDesignOrderRoutePage(){
		try {
			String changeOrder = getParameter("changeOrder");//是否是改制订单的工艺路线
			String orderId = getParameter("orderId");
			String orderCid = getParameter("orderCid");
			String number = getParameter("number");//生产数量
			String goodsId = getParameter("goodsId");
			//工艺路线信息
			
			MesWmProduceProcessroute route = orderPDService.queryProduceRoute(orderId,orderCid);
			AaGoods product = null;
			//工序属性详情 
//			String process_json = "[]";
			if(route!=null){
				//获取节点及属性信息，转成json
				JSONObject process_objs = orderPDService.getProcessJson(route.getGid());
				
				if(CommonUtil.isNullString(route.getDesignJson())){
					// 首先查是否有子表数据，有就自动生成json，没有则输出空array
//					if(process_objs.isNullObject() || process_objs.isEmpty()){
						route.setDesignJson("[]");//输出空array
//					}else{
//						String designJson = basePDService.getInitDesignJson(process_objs,routeId);
//						route.setDesignJson(designJson);
//					}
				}
				setRequstAttribute("process_objs", process_objs.toString());
				
			}else{
				route = new MesWmProduceProcessroute();
				route.setDesignJson("[]");
				/*if("1".equals(changeOrder)){
					route.setBillDate(new Date());
					route.setGoodsUid(goodsId);
					route.setProduceUid(orderId);
					route.setProduceCuid(orderCid);
					route.setGid(UUID.randomUUID().toString());
					orderPDService.inertObject(route);
				}*/
			}
			//产品信息
			product = cacheCtrlService.getGoods(goodsId);
			setRequstAttribute("product", product);
			
			int nodeTotal = JSONArray.fromObject(route.getDesignJson()).size();
			
			setRequstAttribute("number", number);
			setRequstAttribute("nodeTotal", nodeTotal);
			setRequstAttribute("route", route);
			setRequstAttribute("isOrder", "1");
			setRequstAttribute("orderId", orderId);
			setRequstAttribute("ordercId", orderCid);
			setRequstAttribute("routeName",product==null?"": product.getGoodsname());
			setRequstAttribute("changeOrder", changeOrder);
//			setRequstAttribute("process_json", process_json);
			return "basepd_design";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * @category 保存工艺路线数据
	 * 2016年5月3日 下午3:45:27 
	 * @author zhuxiaochen
	 */
	public void saveProcessData(){
		try {
			String route_id = getParameter("flow_id");//工艺路线id
			String process_info = getParameter("process_info");//工艺路线设计信息
			String arr_updProcess = getParameter("arr_updProcess");//更新的工序，存id
			String arr_addProcess = getParameter("arr_addProcess");//新增的工序，存节点对象
			String arr_delProcess = getParameter("arr_delProcess");//删除的工序，存id
			String process_objs = getParameter("process_objs");//工序属性详情 数组
			String process_codeJson = getParameter("process_codeJson");//工序属性详情 数组
			String changeSrcJson = getParameter("changeSrcJson");//改制来源json
			String productId = getParameter("productId");//产成品id
			String number = getParameter("number");//产成品数量
			String isAlter = getParameter("isAlter");//是否是变更单
			String changeOrder = getParameter("changeOrder");//是否是改制订单
			String arr_taskProcess = getParameter("arr_taskProcess");//关联的有任务的工序
			String arr_taskNextProcess = getParameter("arr_taskNextProcess");//被删的关联有任务的下一个工序
			//现有的工序
			List<MesWmProduceProcessroutec> processList = new ArrayList<MesWmProduceProcessroutec>();//basePDService.getRouteCList(route_id);
			/*String[] spIds = new String[processList.size()];
			for(int i=0;i<processList.size();i++){
				spIds[i] = Constants.CACHE_MESSTANDARDPROCESS+"_"+processList.get(i).getProcessId();
			}
			List<MES_WM_StandardProcess> spList = cacheCtrlService.getMESStandardProcessList(spIds);*/
			
			//保存数据
			orderPDService.saveProcessData(isAlter,productId,processList,route_id,process_info,arr_updProcess,arr_addProcess,
					arr_delProcess,process_objs,process_codeJson,number,arr_taskProcess,arr_taskNextProcess,changeOrder,changeSrcJson);
			//处理任务
			if("1".equals(isAlter)){
				orderPDService.dealRouteTask(route_id);
			}
			responseWrite("success");
		} catch (NotEnoughException e) {
			e.printStackTrace();
			responseWrite("notEnough");
		}catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
		
	}
	
	/**
	 * @category 调整实际工价
	 *2017 2017年1月15日下午5:01:34
	 *void
	 *宋银海
	 */
	public void changeRealPrice(){
		try {
			String routeCid = getParameter("routeCid");//工艺路线子表
			String realPrice = getParameter("realPrice");//实际单价
			
			//保存数据
			orderPDService.changeRealPrice(routeCid,realPrice);
			//处理任务
			responseWrite("success");
		} catch (NotEnoughException e) {
			e.printStackTrace();
			responseWrite("notEnough");
		}catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
		
	}
	
	
	
	/**
	 * @category 启动任务（生成领料任务）
	 * 2016年5月11日 上午9:21:31 
	 * @author zhuxiaochen
	 */
	public void startTask(){
		try {
			String orderId = getParameter("orderId");
//			orderPDService.createTask(orderId);
			int suc_cnt = orderPDService.createTask4Produce(orderId);
			if(suc_cnt>0){
				responseWrite("success");
			}else{
				responseWrite("领料任务都已执行，无法再次派发");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
		
	}
	
	/**
	 * @category 显示产品加工明细
	 * 2016年6月6日 上午8:47:09 
	 * @author zhuxiaochen
	 * @return
	 */
	public String showProductStep(){
		try {
			//可视化工艺路线
			toDesignOrderRoutePage();
			
			String orderCid=getParameter("orderCid");
			//开工报工情况
			List<Map> stepSituation=produceOrderService.getProductStepSituation(orderCid);
			
			//领料情况
			List<Map> meterialOut=produceOrderService.getProductStepMeterialOut(orderCid);
			
			//工序节点状态
			List<Map> stats=produceOrderService.getProductStats(orderCid);
			
			setRequstAttribute("stepSituation", stepSituation);
			setRequstAttribute("meterialOut", meterialOut);
			setRequstAttribute("stats", JSONArray.fromObject(stats).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "orderpd_show";
	}
	
	/**
	 * @category 删除订单工艺路线
	 * 2016年6月6日 上午8:48:11 
	 * @author zhuxiaochen
	 */
	public void deleteOrderRoute(){
		try {
			String orderId = getParameter("orderId");
			orderPDService.deleteOrderRoute(orderId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	/**
	 * @category 删除产品工艺路线
	 * 2016年6月6日 上午8:48:11 
	 * @author zhuxiaochen
	 */
	public void deleteProductRoute(){
		try {
			String orderId = getParameter("orderId");
			orderPDService.deleteProductRoute(orderId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	/**
	 * @category 检测是否有工艺路线
	 * 2016年6月6日 下午3:22:20 
	 * @author zhuxiaochen
	 */
	public void checkProduceRoute(){
		try {
			String orderId = getParameter("orderId");
			boolean check = orderPDService.checkHasRoute(orderId);
			if(check){
				responseWrite("success");
			}else{
				responseWrite("fail");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("fail");
		}
	}
	
	/**
	 * @category 校验订单工艺路线数据的正确性
	 * 2016年6月16日 下午1:18:31 
	 * @author zhuxiaochen
	 */
	public void checkData(){
		try {
			String orderId = getParameter("orderId");//订单id
			String msg = orderPDService.checkData(orderId);
			responseWrite(msg,true);
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	/**
	 * @category 选择改制来源界面
	 * 2016年7月11日 下午4:41:45 
	 * @author zhuxiaochen
	 * @return
	 */
	public String toSetChangeSrc(){
		
		return "orderpd_changeSrc";
	}
	
	/**
	 * @category 选择改制来源订单
	 * 2016年6月14日 下午2:51:23 
	 * @author zhuxiaochen
	 * @return
	 */
	public String changeOrderSrc(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize(8);
			String billCode = getParameter("billCode");//过滤条件 订单编号
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = "";
			if(CommonUtil.notNullString(billCode)){
				condition += " and po.billCode like '%"+billCode+"%' ";
			}
			//获取可选的订单
			PageBean data = orderPDService.getEnabledChangeOrder(orgId,sobId,pageIndex,pageSize,condition);
			setRequstAttribute("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "changeOrderSrc";
	}
	
	/**
	 * @category 选择改制来源工序
	 * 2016年6月14日 下午2:51:23 
	 * @author zhuxiaochen
	 * @return
	 */
	public String changeProcessSrc(){
		try {
			String orderCId = getParameter("orderCId");
			if(CommonUtil.notNullString(orderCId)){
				//获取可选的工序
				List<Map> processList = orderPDService.getChangeProcessSrc(orderCId,"",false);
				setRequstAttribute("processList", processList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "changeProcessSrc";
	}
	
	/**
	 * @category 获取选择的改制来源信息
	 * 2016年7月1日 下午2:26:25 
	 * @author zhuxiaochen
	 */
	public void getChangeOrderInfo(){
		try {
			String ordercId = getParameter("ordercId");
			String routecId = getParameter("routecId");
			String thisOrdercId = getParameter("thisOrdercId");
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			List<Map> ordercList = orderPDService.getEnabledChangeOrder(orgId, sobId, 1, 1, " and poc.gid='"+ordercId+"' ",false).getList();
			Map orderc = new HashMap();
			if(ordercList.size()>0){
				orderc = ordercList.get(0);
			}
			Map routec = new HashMap(); 
			List<Map> routeclist = orderPDService.getChangeProcessSrc(ordercId," and gid='"+routecId+"'",true);
			if(routeclist.size()>0){
				routec = routeclist.get(0);
			}
			
			Map rsp = new HashMap();
			rsp.put("success", "1");
			rsp.put("orderc", orderc);
			rsp.put("routec", routec);
			//本工艺路线信息
			if(CommonUtil.notNullString(thisOrdercId)){
				MesWmProduceProcessroute thisRoute = orderPDService.findProduceRouteByOrderC(thisOrdercId);
				rsp.put("thisRoute", thisRoute);
			}
			responseWrite(EmiJsonObj.fromObject(rsp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			writeError(); 
		}
	}
	
	/**
	 * @category 清空改制来原
	 * 2016年7月15日 下午1:47:10 
	 * @author zhuxiaochen
	 */
	public void clearChangeSrc(){
		try {
			String ordercId = getParameter("ordercId");
			String routecId = getParameter("routecId");
			String thisRouteId = getParameter("thisRouteId");
			orderPDService.clearChangeSrc(ordercId,routecId,thisRouteId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 检测工序是否有任务
	 * 2016年6月22日 下午3:35:13 
	 * @author zhuxiaochen
	 */
	public void checkProcessTask(){
		try {
			String routeCid = getParameter("routeCid");
			//检测
			boolean has = orderPDService.checkProcessTask(routeCid);
			String res = "none";
			if(has){
				res = "has"+":"+routeCid;
			}
			
			responseWrite(res);
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	
}
