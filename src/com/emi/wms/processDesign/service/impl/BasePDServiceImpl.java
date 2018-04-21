package com.emi.wms.processDesign.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.Base64;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.flow.main.util.FlowNode;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProcessRouteCMould;
import com.emi.wms.bean.MesWmProcessRouteCPre;
import com.emi.wms.bean.MesWmProcessRoutecGoods;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.MesWmProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.processDesign.bean.BasPart;
import com.emi.wms.processDesign.bean.BomBomReq;
import com.emi.wms.processDesign.bean.StockRouteC;
import com.emi.wms.processDesign.dao.BasePDDao;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.processDesign.util.BasePDUtil;
import com.sun.org.apache.xerces.internal.util.URI;

@SuppressWarnings({"rawtypes","unchecked"})
public class BasePDServiceImpl implements BasePDService {
	private BasePDDao basePDDao;
	private CacheCtrlService cacheCtrlService;
	//默认图标
	private static String DEFAULT_ICON = "icon-cog";
	// 图标-开始节点
	private static String ICON_START = "icon-play";
	//默认样式
	private static String DEFAULT_STYLE = "min-width:120px;height:28px;line-height:28px;color:#0e76a8;";
	
	public BasePDDao getBasePDDao() {
		return basePDDao;
	}

	public void setBasePDDao(BasePDDao basePDDao) {
		this.basePDDao = basePDDao;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	@Override
	public PageBean getBaseProcessRouteList(String condition, int pageIndex,
			int pageSize) {
		return basePDDao.getBaseProcessRouteList(condition,pageIndex,pageSize);
	}

	@Override
	public MesWmProcessroute findBaseRoute(String routeId) {
		return basePDDao.findBaseRoute(routeId);
	}

	@Override
	public void updateBaseRoute(MesWmProcessroute route) {
		basePDDao.updateBaseRoute(route);
	}

	@Override
	public void insertBaseRoute(MesWmProcessroute route) {
		route.setEffdate(new Timestamp(System.currentTimeMillis()));
		basePDDao.insertBaseRoute(route);
	}

	@Override
	public List<MesWmProcessroutec> getRouteCList(int increamentId) {
		return basePDDao.getRouteCList(increamentId);
	}

	@Override
	public MesWmProcessroutec finBaseRouteC(String routecId) {
		return basePDDao.finBaseRouteC(routecId);
	}

	@Override
	public List<MesWmProcessRouteCPre> getPreProcessList(String routecId) {
		return basePDDao.getPreProcessList(routecId);
	}

	@Override
	public void saveProcessData(String productId,List<MesWmProcessroutec> processList,String route_id, String process_info,
			String arr_updProcess, String arr_addProcess,
			String arr_delProcess, String process_objs, String process_codeJson,String userid) {
		JSONObject info_json = JSONObject.fromObject(process_info);//连线信息
		JSONArray updProcess = JSONArray.fromObject(arr_updProcess);//id数组
		JSONArray addProcess = JSONArray.fromObject(arr_addProcess);//对象数组
		JSONArray delProcess = JSONArray.fromObject(arr_delProcess);//id数组
		JSONObject objs_json = JSONObject.fromObject(process_objs); //节点详细信息
		JSONObject codeJson = JSONObject.fromObject(process_codeJson);//工序编码信息
		
		/*
		 * 找出末道工序，推算数量（工序应完成数量、应领料数量）
		 */
		Iterator it = info_json.keys();  
		List<String> lastRouteCIds = new ArrayList<String>();//末道工序id
        while (it.hasNext()) {  
            String id = (String) it.next();  
            Object obj = info_json.get(id);
            JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
			JSONArray nextNodes = process_node.getJSONArray("process_to");
			//末道工序
			if(nextNodes==null || nextNodes.size()==0){
				lastRouteCIds.add(id);
			}
        }
        
        BasePDUtil.genNumber(lastRouteCIds,objs_json,"1");
        //开始推算数量,产品数量1
        
	/*	//1、保存主表设计
		//saveDesign(productId,processList, route_id, info_json, updProcess, addProcess, delProcess, objs_json, codeJson);*/
		// 2、保存数据
		saveData(productId,processList, route_id, info_json, updProcess, addProcess, delProcess, objs_json, codeJson,userid);
		
	}


	/*
	 * 保存数据
	 */
	private void saveData(String productId,List<MesWmProcessroutec> processCList,String route_id,JSONObject info_json,
			JSONArray updProcess,JSONArray addProcess,JSONArray delProcess,JSONObject objs_json,JSONObject codeJson,String userid){
		//1 主表信息
		String designJson = getDesignJsonStr(route_id,info_json,objs_json);//设计图
		MesWmProcessroute route = new MesWmProcessroute();
		route.setGid(route_id);
		route.setGoodsUid(productId);
		route.setDesignJson(designJson);
		route.setState(0);
		
		/*
		 * 更新的信息
		 */
		List<MesWmProcessroutec> updateList = new ArrayList<MesWmProcessroutec>();
		//转换json存入updateList
		transProcessJson(route_id,updProcess, codeJson,info_json, objs_json, updateList);
		
		/*
		 * 新增的信息
		 */
		List<MesWmProcessroutec> addList = new ArrayList<MesWmProcessroutec>();
		//转换json存入addList
		transProcessJson(route_id,addProcess, codeJson,info_json, objs_json, addList);
		
		/*
		 * 删除的信息
		 */
		String deleteIds = "";
		for(Object o : delProcess){
			String d = CommonUtil.Obj2String(o);
			deleteIds += d +",";
		}
		CommonUtil.cutLastString(deleteIds, ",");
		
		String updateIds = "";
		for(Object o : updProcess){
			String d = CommonUtil.Obj2String(o);
			updateIds += d +",";
		}
		CommonUtil.cutLastString(updateIds, ",");
		
		route.setModifyUser(userid);
		route.setModifyDate(new Date());
		basePDDao.updateBaseRoute(route);
		basePDDao.addProcessRouteC(addList);//新增的工序
		basePDDao.updateProcessRouteC(updateList);//更新的工序
		basePDDao.deleteProcessRouteC(deleteIds);//删除工序
		basePDDao.deleteRoutecAttributes(updateIds);//删除更新的工序的属性设置，重新添加
		basePDDao.insertRoutecAttributes(updateList,addList);
	}
	

	@Override
	public String getDesignJsonStr(String routeId ,JSONObject info_json,JSONObject objs_json){
		JSONArray designJson = new JSONArray();
		Iterator it = info_json.keys();  
        while (it.hasNext()) {  
            String routeCid = (String) it.next();  
            Object obj = info_json.get(routeCid);
            
            if(obj != null){
//    			String processId = objs_json.get(node.getGid())!=null ? objs_json.getJSONObject(node.getGid()).getJSONObject("base").getString("processId") : node.getOpGid();
//    			MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(processId);
    			
    			JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
    			JSONObject design_node = new JSONObject();
    			design_node.put("id", routeCid);	//节点id
    			design_node.put("flow_id", routeId);		//流程id
    			design_node.put("process_code", objs_json.getJSONObject(routeCid).getJSONObject("base").getString("processIndex"));//节点序号
    			design_node.put("process_name", objs_json.getJSONObject(routeCid).getJSONObject("base").getString("processName"));//节点名称
    			design_node.put("icon", DEFAULT_ICON);	//图标
    			design_node.put("style", FlowNode.generateStyle(process_node.getInt("left"), process_node.getInt("top")));	//样式代码
    			
    			//下一步骤，可以是多个并列
    			JSONArray nextNodes = process_node.getJSONArray("process_to");//得到的是array，转成string入库
    			String nextNodes_str = "";
    			for(int i=0;i<nextNodes.size();i++){
    				nextNodes_str += nextNodes.get(i)+",";
    			}
    			nextNodes_str = CommonUtil.cutLastString(nextNodes_str, ",");
    			design_node.put("process_to", nextNodes_str);
    			
    			//转好的节点json添加进需要入库的流程json里
    			designJson.add(design_node);
    		}	
        }
				
		return designJson.toString();
	}
	
	/*
	 * 将json转换成数据对象
	 */
	private void transProcessJson(String route_id,JSONArray idArray,JSONObject codeJson,JSONObject info_json,JSONObject objs_json,List<MesWmProcessroutec> processClist){
		for(int i=0;i<idArray.size();i++){
			MesWmProcessroutec prc = new MesWmProcessroutec();
			//更新的id
			String routeC_gid = CommonUtil.Obj2String(idArray.get(i));
			if(CommonUtil.isNullString(routeC_gid)){
				continue;
			}
			prc.setGid(routeC_gid); //设置gid
			prc.setUpdateTime(new Date());
			if(codeJson.get(routeC_gid)!=null){
				prc.setGid(routeC_gid); //设置gid
			}
			/*
			 * 上道和下道工序节点id
			 */
			String preGid = "";
			String nextGid = "";
			JSONObject node = info_json.getJSONObject(routeC_gid);
			if(node==null || node.isNullObject()){
				continue;
			}
			JSONArray process_to = node.getJSONArray("process_to");
			for(Object t : process_to){
				String to_id = (String) t;
				if(objs_json.get(to_id)!=null){//前台插件获取的json可能有bug，需要这里校验下
					nextGid += to_id + ",";
				}
			}
			nextGid = CommonUtil.cutLastString(nextGid, ",");//下道工序节点id
			
			Iterator it = info_json.keys();  
	        while (it.hasNext()) {  
	            String id = (String) it.next();  
	            Object obj = info_json.get(id);
	            JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
    			JSONArray nextNodes = process_node.getJSONArray("process_to");
    			for(Object t : nextNodes){
    				String to_id = (String) t;
    				if(to_id.equals(routeC_gid)){
    					preGid += id + ",";
    					break;
    				}
    			}
	        }
	        preGid = CommonUtil.cutLastString(preGid, ",");//上道工序节点id
			prc.setPreGid(preGid);
			prc.setNextGid(nextGid);
	        
			if(objs_json.get(routeC_gid)!=null){
				//2.1 子表基本信息
				//基础信息
				JSONObject p_obj = (JSONObject) objs_json.get(routeC_gid);
				prc.setRoutGid(route_id);
				prc.setOpGid(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("standardProcessId")));//工序gid
				prc.setCindex(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("processIndex")));//工序序号
				prc.setDispatchingType(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("dispatchingType"))?Integer.parseInt(Config.DEFAULT_DISPATCHING):Integer.parseInt(p_obj.getJSONObject("base").getString("dispatchingType")));//派工类型
				prc.setIsCheck(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isCheck"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isCheck")));//是否质检
				prc.setIsOut(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isOut"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isOut")));//是否委外
				prc.setIsStock(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isStock"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isStock")));//是否入库
				prc.setIsSemi(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isSemi"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isSemi")));//是否半成品
				prc.setPassRate(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("passRate"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("passRate")));//检验合格率
				prc.setWorkCenterId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("workCenterId")));//工作中心id
				prc.setStockGoodsId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("stockGoodsId")));//入库物料id
				prc.setSemiGoodsId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("semiGoodsId")));//半成品id
				prc.setNumber(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("number"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("number")));//应完工数量
				prc.setRealPrice(CommonUtil.str2BigDecimal(CommonUtil.Obj2String( p_obj.getJSONObject("base").get("realPrice"))));//实际工价
				prc.setStandardHours(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("standardHours"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("standardHours")));//标准工时
				prc.setOpdes(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("opdes")));//工序描述
				prc.setIsMustScanMould(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isMustScanMould"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isMustScanMould")));		//是否必扫模具 
				prc.setMouldControlFetch(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("mouldControlFetch"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("mouldControlFetch")));	//严格控制模具取数
				//2.2上道工序
				JSONArray preArray = p_obj.get("attrPreProc")==null?new JSONArray():p_obj.getJSONArray("attrPreProc");
				List<MesWmProcessRouteCPre> preList = new ArrayList<MesWmProcessRouteCPre>();
				MesWmProcessRouteCPre prc_pre = null;
				for(Object pre : preArray){
					JSONObject j = (JSONObject) pre;
					prc_pre = new MesWmProcessRouteCPre();
					prc_pre.setRouteCGid(routeC_gid);
					prc_pre.setPreRouteCGId(CommonUtil.Obj2String(j.get("routeCid")));
					prc_pre.setBaseUse(new BigDecimal(j.get("baseUse")==null?"1":j.getString("baseUse")));
					prc_pre.setBaseQuantity(new BigDecimal(j.get("baseQuantity")==null?"1":j.getString("baseQuantity")));
					
					preList.add(prc_pre);
				}
				
				//2.3 物料领用
				JSONArray goodsArray = p_obj.get("attrGoods")==null?new JSONArray():p_obj.getJSONArray("attrGoods");
				List<MesWmProcessRoutecGoods> goodsList = new ArrayList<MesWmProcessRoutecGoods>();
				MesWmProcessRoutecGoods prc_goods = null;
				for(Object goods : goodsArray){
					JSONObject g = (JSONObject) goods;
					prc_goods = new MesWmProcessRoutecGoods();
					prc_goods.setRouteCGid(routeC_gid);
					prc_goods.setGoodsGid(CommonUtil.Obj2String(g.get("goodsId")));
					prc_goods.setBaseUse(new BigDecimal(CommonUtil.isNullObject(g.get("baseUse"))?"1":g.getString("baseUse")));
					prc_goods.setBaseQuantity(new BigDecimal(CommonUtil.isNullObject(g.get("baseQuantity"))?"1":g.getString("baseQuantity")));
					prc_goods.setNumber(new BigDecimal(CommonUtil.isNullObject(g.get("number"))?"1":g.getString("number")));
					
					//从缓存取值，判断是否有自由项
					AaGoods cacheGoods = cacheCtrlService.getGoods(prc_goods.getGoodsGid());
					boolean b_free1 = cacheGoods==null?false:(cacheGoods.getCfree1()==null?false:(cacheGoods.getCfree1()==1));
					if(b_free1){
						prc_goods.setFree1(CommonUtil.Obj2String(g.get("free1")));
					}
					goodsList.add(prc_goods);
				}
				
				//2.4 派工对象（工作组、人员等）
				JSONArray dispathingArray = p_obj.get("attrDispatching")==null?new JSONArray():p_obj.getJSONArray("attrDispatching");
				List<MesWmProcessRouteCDispatching> workCenterList = new ArrayList<MesWmProcessRouteCDispatching>();
				MesWmProcessRouteCDispatching prc_dis = null;
				for(Object dispathing : dispathingArray){
					JSONObject dis = (JSONObject) dispathing;
					prc_dis = new MesWmProcessRouteCDispatching();
					prc_dis.setRouteCGid(routeC_gid);
					prc_dis.setObjGid(CommonUtil.Obj2String(dis.get("objId")));
					prc_dis.setObjType(Integer.parseInt(dis.get("objType")==null?"0":dis.getString("objType")));;
					
					workCenterList.add(prc_dis);
				}
				
				//2.5 设备
				JSONArray equipmentArray = p_obj.get("attrEquipment")==null?new JSONArray():p_obj.getJSONArray("attrEquipment");
				List<MesWmProcessRouteCEquipment> equipmentList = new ArrayList<MesWmProcessRouteCEquipment>();
				MesWmProcessRouteCEquipment prc_equ = null;
				for(Object equipment : equipmentArray){
					JSONObject equ = (JSONObject) equipment;
					prc_equ = new MesWmProcessRouteCEquipment();
					prc_equ.setRouteCGid(routeC_gid);
					prc_equ.setEquipmentGid(CommonUtil.Obj2String(equ.get("equipmentId")));
					
					equipmentList.add(prc_equ);
				}
				
				//2.6 模具
				JSONArray mouldArray = p_obj.get("attrMould")==null?new JSONArray():p_obj.getJSONArray("attrMould");
				List<MesWmProcessRouteCMould> mouldList = new ArrayList<MesWmProcessRouteCMould>();
				MesWmProcessRouteCMould prc_mould = null;
				for(Object mould : mouldArray){
					JSONObject mou = (JSONObject) mould;
					prc_mould = new MesWmProcessRouteCMould();
					prc_mould.setRouteCGid(routeC_gid);                  
					prc_mould.setMouldGid(CommonUtil.Obj2String(mou.get("mouldId")));
					prc_mould.setGoodsCode(CommonUtil.Obj2String(mou.get("goodsCode")));
					prc_mould.setGrossWeight(CommonUtil.Obj2String(mou.get("grossWeight")));
					prc_mould.setNetWeight(CommonUtil.Obj2String(mou.get("netWeight")));
					
					mouldList.add(prc_mould);
				}
				
				prc.setEquipmentList(equipmentList);
				prc.setPreList(preList);
				prc.setGoodsList(goodsList);
				prc.setDispatchingList(workCenterList);
				prc.setMouldList(mouldList);
			}
			processClist.add(prc);
		}
	}
	
	/*
	 * 保存设计图
	 */
	/*private void saveDesign(String productId,List<MesWmProcessroutec> processList,String route_id,JSONObject info_json,
			JSONArray updProcess,JSONArray addProcess,JSONArray delProcess,JSONObject objs_json,JSONObject codeJson){
		JSONArray designJson = new JSONArray();
		//根据连接信息和节点信息，转成流程信息
		//1、数据库已有的节点
		for(MesWmProcessroutec node : processList){
			Object obj = info_json.get(node.getGid());
			if(obj != null){
//				String processId = objs_json.get(node.getGid())!=null ? objs_json.getJSONObject(node.getGid()).getJSONObject("base").getString("processId") : node.getOpGid();
//				MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(processId);
				
				JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
				JSONObject design_node = new JSONObject();
				design_node.put("id", node.getGid());	//节点id
				design_node.put("flow_id", route_id);		//流程id
//				design_node.put("process_code", codeJson.get(node.getGid())!=null ? codeJson.get(node.getGid()).toString() : node.getProcessCode());//节点代码
//				design_node.put("process_name", sp.getOpname());//节点名称
				design_node.put("icon", DEFAULT_ICON);	//图标
				design_node.put("style", FlowNode.generateStyle(process_node.getInt("left"), process_node.getInt("top")));	//样式代码
				
				//下一步骤，可以是多个并列
				JSONArray nextNodes = process_node.getJSONArray("process_to");//得到的是array，转成string入库
				String nextNodes_str = "";
				for(int i=0;i<nextNodes.size();i++){
					nextNodes_str += nextNodes.get(i)+",";
				}
				nextNodes_str = CommonUtil.cutLastString(nextNodes_str, ",");
				design_node.put("process_to", nextNodes_str);
				
				//转好的节点json添加进需要入库的流程json里
				designJson.add(design_node);
				
				//把上一个节点、下一个节点设置进去
				node.setNextGid(nextNodes_str);
//				node.setPreGid(preGid);
				
				
			}
		}
		//2、新增的节点
		List<MesWmProcessroutec> addList = new ArrayList<MesWmProcessroutec>();
		for(int i=0;i<addProcess.size();i++){
			JSONObject design_node = new JSONObject();
			
			designJson.add(design_node);
		}
		//保存节点之间关联信息
		basePDDao.updateObject(processList);
		//保存新增的节点
		basePDDao.addObject(addList);
		//保存流程设计图信息
//		basePDDao.updateProcessInfo(route_id,designJson.toString());
		MesWmProcessroute route = new MesWmProcessroute();
		route.setGid(route_id);
		route.setDesignJson(designJson.toString());
		route.setGoodsUid(productId);
		basePDDao.updateBaseRoute(route);
	}*/

	@Override
	public List<MesWmProcessroutec> getRouteCList(String route_id) {
		return basePDDao.getRouteCList(route_id);
	}
	
	@Override
	public List<MesWmProcessroutec> getRouteCListIn(String route_id) {
		return basePDDao.getRouteCListIn(route_id);
	}
	

	@Override
	public void deleteBaseRoute(String routeId) {
		//1、删除主表（假删除）
		basePDDao.deleteBaseRoute(routeId);
	}

	public void deleteStandardProcess(String processId) {
		//1、删除主表（假删除）
		basePDDao.deleteStandardProcess(processId);
	}
	
	public void deleteWorkCenter(String processId) {
		//1、删除主表（假删除）
		basePDDao.deleteWorkCenter(processId);
	}
	
	@Override
	public JSONObject getProcessJson(String routeId, AaGoods product) {
		JSONObject json = new JSONObject();
		List<MesWmProcessroutec> routecList = basePDDao.getRouteCList(routeId);
		String routeCids = "";
		for(MesWmProcessroutec c : routecList){
			routeCids += c.getGid() + ",";
		}
		routeCids = CommonUtil.cutLastString(routeCids, ",");
		
		List<MesWmProcessRouteCPre> preList = basePDDao.getProcessRouteCPreList(routeCids);
		List<MesWmProcessRoutecGoods> goodsList = basePDDao.getProcessRouteCGoodsList(routeCids);
		List<MesWmProcessRouteCDispatching> dispatchingList = basePDDao.getProcessRouteCDispatchingList(routeCids);
		List<MesWmProcessRouteCEquipment> equipmentList = basePDDao.getProcessRouteCEquipmentList(routeCids);
		List<MesWmProcessRouteCMould> mouldList = basePDDao.getProcessRouteCMouldList(routeCids);
		
		for(MesWmProcessroutec c : routecList){
			JSONObject process_obj = new JSONObject();
			JSONObject base = new JSONObject();
			JSONArray attrPreProc = new JSONArray();
			JSONArray attrGoods = new JSONArray();
			JSONArray attrDispatching = new JSONArray();
			JSONArray attrEquipment = new JSONArray();
			JSONArray attrMould = new JSONArray();
			
			MesWmStandardprocess standardprocess = cacheCtrlService.getMESStandardProcess(c.getOpGid());
			base.put("processName", standardprocess==null?"":standardprocess.getOpname());
			base.put("processCode", standardprocess==null?"":standardprocess.getOpcode());
			base.put("processIndex", c.getCindex());
			base.put("routeCid", c.getGid());
			base.put("standardProcessId", c.getOpGid());
			base.put("dispatchingType", c.getDispatchingType());
			base.put("isCheck", c.getIsCheck());
			base.put("isOut", c.getIsOut());
			base.put("isStock", c.getIsStock());
			base.put("isSemi", c.getIsSemi());
			base.put("passRate", c.getPassRate()==null?new BigDecimal(0): c.getPassRate().stripTrailingZeros().toPlainString());
			base.put("workCenterId", CommonUtil.null2Str( c.getWorkCenterId()));
			MesAaWorkcenter workcenter = cacheCtrlService.getworkCenter(CommonUtil.null2Str(c.getWorkCenterId()));
			base.put("workCenterName", workcenter==null?"":workcenter.getWcname());
			AaGoods stockGoods = cacheCtrlService.getGoods(CommonUtil.null2Str(c.getStockGoodsId()));
			base.put("stockGoodsName", stockGoods==null?"":stockGoods.getGoodsname());
			base.put("stockGoodsId", CommonUtil.null2Str( c.getStockGoodsId()));
			AaGoods semiGoods = cacheCtrlService.getGoods(CommonUtil.null2Str(c.getSemiGoodsId()));
			base.put("semiGoodsId", CommonUtil.null2Str( c.getSemiGoodsId()));
			base.put("semiGoodsName", semiGoods==null?"":semiGoods.getGoodsname());
			//产品信息，给生成bom用
			base.put("productId", product.getGid());
			base.put("productCode", product.getGoodscode());
			//标准工序
			MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(CommonUtil.null2Str(c.getOpGid()));
			base.put("standardPrice",sp==null?new BigDecimal(0): sp.getStandardPrice()==null?new BigDecimal(0):sp.getStandardPrice().stripTrailingZeros().toPlainString());
			base.put("realPrice", c.getRealPrice()==null?new BigDecimal(0): c.getRealPrice().stripTrailingZeros().toPlainString());
			base.put("standardHours", c.getStandardHours()==null?"": c.getStandardHours().stripTrailingZeros().toPlainString());
			base.put("opdes", CommonUtil.null2Str( c.getOpdes()));
			base.put("isMustScanMould",c.getIsMustScanMould());
			base.put("mouldControlFetch",c.getMouldControlFetch());
			//条码(传空字符，为了和订单工艺统一格式)
			base.put("barcode", "");
			//上道工序转入
			for(MesWmProcessRouteCPre pre : preList){
				if(c.getGid().equals(pre.getRouteCGid())){
					JSONObject pre_json = new JSONObject();
					pre_json.put("routeCid", pre.getPreRouteCGId());
					pre_json.put("baseUse", pre.getBaseUse().stripTrailingZeros().toPlainString());
					pre_json.put("baseQuantity", pre.getBaseQuantity().stripTrailingZeros().toPlainString());
//					pre_json.put("standardUse", pre.getBaseUse().stripTrailingZeros().toString());
					attrPreProc.add(pre_json);
				}
			}
			//物料
			for(MesWmProcessRoutecGoods g : goodsList){
				if(c.getGid().equals(g.getRouteCGid())){
					AaGoods goods = cacheCtrlService.getGoods(g.getGoodsGid());
					JSONObject goods_json = new JSONObject();
					goods_json.put("goodsId", g.getGoodsGid());
					goods_json.put("goodscode", goods==null?"":goods.getGoodscode());
					goods_json.put("goodsname", goods==null?"":goods.getGoodsname());
					goods_json.put("goodsstandard", goods==null?"":goods.getGoodsstandard());
					goods_json.put("unitName", goods==null?"":goods.getUnitName());
					goods_json.put("baseUse", g.getBaseUse().stripTrailingZeros().toPlainString());
					goods_json.put("baseQuantity", g.getBaseQuantity().stripTrailingZeros().toPlainString());
					goods_json.put("free1", g.getFree1());
					attrGoods.add(goods_json);
				}
			}
			//派工对象
			for(MesWmProcessRouteCDispatching dis : dispatchingList){
				if(c.getGid().equals(dis.getRouteCGid())){
					
					JSONObject dis_json = new JSONObject();
					if(dis.getObjType()==0){
						AaPerson person = cacheCtrlService.getPerson(dis.getObjGid());
						dis_json.put("objId", dis.getObjGid());
						dis_json.put("objCode", person==null?"":person.getPercode());
						dis_json.put("objName", person==null?"":person.getPername());
						dis_json.put("objType", "0");
					}
					if(dis.getObjType()==1){
						AaGroup group = cacheCtrlService.getAaGroup(dis.getObjGid());
						dis_json.put("objId", dis.getObjGid());
						dis_json.put("objCode", group==null?"":group.getCode());
						dis_json.put("objName", group==null?"":group.getGroupname());
						dis_json.put("objType", "1");
					}
					attrDispatching.add(dis_json);
				}
			}
			//设备
			for(MesWmProcessRouteCEquipment equ : equipmentList){
				if(c.getGid().equals(equ.getRouteCGid())){
					
					JSONObject equ_json = new JSONObject();
					Equipment equipment = cacheCtrlService.getMESEquipment(equ.getEquipmentGid());
					equ_json.put("equipmentId", equ.getEquipmentGid());
					equ_json.put("equipmentCode", equipment==null?"":equipment.getEquipmentcode());
					equ_json.put("equipmentName", equipment==null?"":equipment.getEquipmentname());
					
					attrEquipment.add(equ_json);
				}
			}
			
			//模具 
			for(MesWmProcessRouteCMould mould : mouldList){
				if(c.getGid().equals(mould.getRouteCGid())){
					
					JSONObject mould_json = new JSONObject();
					Mould mould_cach = cacheCtrlService.getMould(mould.getMouldGid());
					mould_json.put("mouldId", mould.getMouldGid());
					mould_json.put("mouldCode", mould_cach==null?"":mould_cach.getMouldcode());
					mould_json.put("mouldName", mould_cach==null?"":mould_cach.getMouldname());
					mould_json.put("mouldRatio", mould_cach==null?"":mould_cach.getMouldRatio());
					
					mould_json.put("goodsCode", CommonUtil.Obj2String(mould.getGoodsCode()));
					mould_json.put("grossWeight", CommonUtil.Obj2String(mould.getGrossWeight()));
					mould_json.put("netWeight", CommonUtil.Obj2String(mould.getNetWeight()));
					
					attrMould.add(mould_json);
				}
			}
		
			process_obj.put("base", base);
			process_obj.put("attrPreProc", attrPreProc);
			process_obj.put("attrGoods", attrGoods);
			process_obj.put("attrEquipment", attrEquipment);
			process_obj.put("attrDispatching", attrDispatching);
			process_obj.put("attrMould", attrMould);
			json.put(c.getGid(), process_obj);
		}
		
		return json;
	}

	@Override
	public String getInitDesignJson(JSONObject process_objs,String routeId) {
		//自动生成设计图-仅支持线性图
		JSONArray resArray = new JSONArray();
		JSONArray array = new JSONArray();
		Iterator it = process_objs.keys();  
        while (it.hasNext()) {  
        	String routeCid = (String) it.next();  
            JSONObject obj = process_objs.getJSONObject(routeCid);
            array.add(obj.get("base"));
        }
        CommonUtil.sortJsonArray(array, "processIndex", true);
        
        int left = 38,top = 72;//初始坐标
        int left_increment = 210, top_increment = 86;//坐标增量
        for(int i=0;i<array.size();i++){
        	JSONObject jo = (JSONObject) array.get(i);
        	JSONObject resJo = new JSONObject();
        	resJo.put("id", jo.get("routeCid"));
        	resJo.put("flow_id", routeId);
        	resJo.put("process_name", jo.get("processName"));
        	resJo.put("process_code", jo.get("processIndex"));
        	resJo.put("process_to", (i==array.size()-1)?"": ((JSONObject)array.get(i+1)).get("routeCid"));
        	resJo.put("icon", DEFAULT_ICON);
        	
        	if(i%6==0 && i!=0){//每6个换行,top增加
        		top += top_increment;
        	}else{
        		if((i/6)%2==0){//奇数行，left坐标增加
        			left += left_increment;
        		}else{//偶数行，left坐标减少
        			left -= left_increment;
        		}
        	}
        	
        	resJo.put("style", DEFAULT_STYLE+"left:"+left+"px;top:"+top+"px");
        	resArray.add(resJo);
        }
		return resArray.toString();
	}

	@Override
	public Map getBaseProcessRouteOverall(String goodsId,String free1) {
		Map res = new HashMap();
		//查询主表
		MesWmProcessroute route = null;
		if(CommonUtil.isNullObject(free1)){
			route=basePDDao.getBaseRouteByGoods(goodsId);//之前的查询方式 没有自由项
		}else{
			route = basePDDao.getBaseRouteByGoodsCfree(goodsId,free1);
		}
		
		if(route!=null){
			/*
			 * 需要判断有没有自由项 
			 */
			AaGoods goods = cacheCtrlService.getGoods(CommonUtil.null2Str(goodsId));
			List<MesWmProcessroutec> cList = null;
			if(goods!=null && goods.getCfree1()!=null && goods.getCfree1()==1){//有自由项
				JSONArray jsona = JSONArray.fromObject(route.getDesignJson());
				/*
				 * TODO 这边注意，以下只有free1是工序时才可用，非工序需要扩充代码
				 */
				String targetId = "";//自由项目标工序id
				JSONArray free_designJson = new JSONArray();
				for(Object o : jsona){
					JSONObject jobj = (JSONObject) o;
					if(jobj.getString("process_name").equals(free1)){
						targetId = jobj.getString("id");
						jobj.put("process_to", "");
						free_designJson.add(jobj);
						break;
					}
				}
				if(CommonUtil.notNullString(targetId)){
					//往前推出工序
					StringBuffer routecids_free = new StringBuffer(targetId+",");
					getPreRoutec(jsona, free_designJson, routecids_free,targetId );
					routecids_free = new StringBuffer(CommonUtil.cutLastString(routecids_free.toString(), ","));
					cList = basePDDao.getRouteCListByIds(routecids_free.toString());
					for(MesWmProcessroutec c : cList){
						//去掉最后自由项目标工序的nextid
						if(c.getGid().equals(targetId)){
							c.setNextGid(null);
						}
					}
					route.setDesignJson(free_designJson.toString());
				}else{
					//如果没有找到目标自由项的工序，就全部复制子表
					cList = basePDDao.getRouteCList(route.getGid());
				}
			}else{
				//查询子表
				cList = basePDDao.getRouteCList(route.getGid());
			}
			//查询属性
			String routeCids = "";
			for(MesWmProcessroutec c : cList){
				routeCids += c.getGid() + ",";
			}
			routeCids = CommonUtil.cutLastString(routeCids, ",");
			List<MesWmProcessRouteCPre> preList = basePDDao.getProcessRouteCPreList(routeCids);
			List<MesWmProcessRoutecGoods> goodsList = basePDDao.getProcessRouteCGoodsList(routeCids);
			List<MesWmProcessRouteCDispatching> dispatchingList = basePDDao.getProcessRouteCDispatchingList(routeCids);
			List<MesWmProcessRouteCEquipment> equipmentList = basePDDao.getProcessRouteCEquipmentList(routeCids);
			List<MesWmProcessRouteCMould> mouldList = basePDDao.getProcessRouteCMouldList(routeCids);
			res.put("cList", cList);
			res.put("preList", preList);
			res.put("goodsList", goodsList);
			res.put("dispatchingList", dispatchingList);
			res.put("equipmentList", equipmentList);
			res.put("mouldList", mouldList);
		}
		
		res.put("route", route);
		return res;
	}
	
	private void getPreRoutec(JSONArray jsona,JSONArray free_designJson,StringBuffer routecids_free, String targetId){
		for(Object o : jsona){
			JSONObject obj = (JSONObject) o;
			if(obj.getString("process_to").equals(targetId)){
				free_designJson.add(obj);
				routecids_free = routecids_free.append(obj.getString("id")+",");
				getPreRoutec(jsona, free_designJson, routecids_free,obj.getString("id"));
			}
		}
	}
	
	public PageBean getStandardProcessList(int pageIndex,int pageSize,String condition){
		return basePDDao.getStandardProcessList(pageIndex,pageSize,condition);
	}
	
	public PageBean getWorkCenterList(int pageIndex,int pageSize,String condition){
		return basePDDao.getWorkCenterList(pageIndex,pageSize,condition);
	}
	
	public boolean saveStandardProcess(MesWmStandardprocess standardprocess){
		return basePDDao.saveStandardProcess(standardprocess);
	}
	
	public boolean saveWorkCenter(MesAaWorkcenter workcenter){
		return basePDDao.saveWorkCenter(workcenter);
	}
	
	public MesWmStandardprocess findStandardProcess(String gid){
		return basePDDao.findStandardProcess(gid);
	}
	
	public MesAaWorkcenter findWorkCenter(String gid){
		return basePDDao.findWorkCenter(gid);
	}
	
	public AaDepartment findDepartment(String gid){
		return basePDDao.findDepartment(gid);
	}
	
	public boolean updateStandardProcess(MesWmStandardprocess standardprocess){
		return basePDDao.updateStandardProcess(standardprocess);
	}
	
	public boolean updateWorkCenter(MesAaWorkcenter workcenter){
		return basePDDao.updateWorkCenter(workcenter);
	}
	
	public List getDepList(String condition){
		return basePDDao.getDepList(condition);
	}
	
	public List getCustomerList(String condition){
		return basePDDao.getCustomerList(condition);
	}

	@Override
	public void saveBomToU8(String process_objs, String process_info,
			String productCode, List<String> msg, MesWmProcessroute route) {
		JSONObject objs_json = JSONObject.fromObject(process_objs); //节点详细信息
		JSONObject info_json = JSONObject.fromObject(process_info);//连线信息
		List<String> invCodes = new ArrayList<String>();//要查的物料编码列表
		if(CommonUtil.notNullString(productCode)){
			invCodes.add(productCode);
		}
		
		List<StockRouteC> stockRouteCIds = new ArrayList<StockRouteC>();//入库工序的id
//		List<String> semiRouteCIds = new ArrayList<String>();//半成品工序的id
		List<String> lastRouteCid = new ArrayList<String>();
		Iterator ito = objs_json.keys();  
		while (ito.hasNext()) {  
		    String id = (String) ito.next();
		    JSONObject obj = objs_json.getJSONObject(id);
		    JSONObject base = obj.getJSONObject("base");
		    Integer isStock = base.get("isStock")==null?0:base.getInt("isStock");
		    Integer isSemi = base.get("isSemi")==null?0:base.getInt("isSemi");
		    String opname = base.getString("processName");
		    if(isSemi == 1){
		    	StockRouteC sg = new StockRouteC(id, 1, 1,opname);
		    	stockRouteCIds.add(sg);
		    	if(base.get("semiGoodsCode")!=null){
		    		invCodes.add(base.getString("semiGoodsCode"));
		    	}
		    }else if(isStock == 1){
		    	StockRouteC sg = new StockRouteC(id, 1, 0,opname);
		    	stockRouteCIds.add(sg);
		    	if(base.get("stockGoodsCode")!=null){
		    		invCodes.add(base.getString("stockGoodsCode"));
		    	}
		    }
		    //最后一道工序
		    if(info_json.getJSONObject(id)!=null && !info_json.getJSONObject(id).isNullObject() && info_json.getJSONObject(id).getJSONArray("process_to").size()==0){
		    	StockRouteC sg = new StockRouteC(id, 0, 0,opname);
		    	stockRouteCIds.add(sg);
		    	lastRouteCid.add(id);
		    	if(base.get("stockGoodsCode")!=null){
		    		invCodes.add(CommonUtil.Obj2String(base.get("productCode")));
		    	}
		    }
		    
		    JSONArray goodsArray = obj.get("attrGoods")==null? new JSONArray(): obj.getJSONArray("attrGoods");
		    for(Object go : goodsArray){
		    	JSONObject goods = (JSONObject) go;
		    	invCodes.add(goods.getString("goodscode"));
		    }
		    
		}
		
		/*
		 * 得到bas_part的数据
		 */
		String invCodeParam = "";
		for(String invCode : invCodes){
			invCodeParam += invCode+",";
		}
		invCodeParam = CommonUtil.cutLastString(invCodeParam, ",");
		Map bas_parameter = new HashMap();
		bas_parameter.put("invCodes", invCodeParam);
		String bas_part_str =new String(Submit.sendPostRequest("invCodes="+Base64.getBase64(invCodeParam) , "http://"+Config.INTERFACEADDRESS+"/u890/wareHouse_getBasPartList.emi")) ;//请求并得到返回值
		List<BasPart> basPartList = new ArrayList<BasPart>();
		JSONObject rsp = EmiJsonObj.fromObject(bas_part_str);
		if("1".equals(rsp.getString("success"))){
			basPartList = (List<BasPart>) EmiJsonArray.toCollection(rsp.getJSONArray("data"),BasPart.class);
		}
		
		//计算应完成数量、应领数量number
		BasePDUtil.genNumber(lastRouteCid, objs_json, "1");
				
		//请求需要用到的BOM参数数据
		List<BomBomReq> reqParams = new ArrayList<BomBomReq>();
		for(StockRouteC stockRouteCid : stockRouteCIds){
			//生成数据
			BomBomReq parameter = BasePDUtil.BomParameter(objs_json,stockRouteCid,basPartList,cacheCtrlService,msg,route);
			if(parameter!=null){
				reqParams.add(parameter);
			}else{
				msg.add("产品编码【"+productCode+"】档案中缺少自由项值：【"+stockRouteCid.getOpname()+"】");
			}
			
		}
		if(reqParams.size()>0 && msg.size()==0){
			//如果有数据才调用请求
			String response = new String(Submit.sendPostRequest("json="+EmiJsonArray.fromObject(reqParams).toString(), "http://"+Config.INTERFACEADDRESS+"/u890/wareHouse_addBom.emi"));//请求并得到返回值
			System.out.println(response);
		}
		
	}

	@Override
	public void copyRoute(String routeId, int copyNum) {
		for(int i=0;i<copyNum;i++){
			MesWmProcessroute route = basePDDao.findBaseRoute(routeId);
			List<MesWmProcessroutec> routec = basePDDao.getRouteCList(routeId);
			String routeCids = "";
			for(MesWmProcessroutec rc : routec){
				routeCids += rc.getGid()+ ",";
			}
			routeCids = CommonUtil.cutLastString(routeCids, ",");
			String oldRouteId = route.getGid();
			List<String> oldRouteCid = null;
			
			
			List<MesWmProcessRouteCPre> pre = basePDDao.getProcessRouteCPreList(routeCids);
			List<MesWmProcessRoutecGoods> goods = basePDDao.getProcessRouteCGoodsList(routeCids);
			List<MesWmProcessRouteCDispatching> dispatching = basePDDao.getProcessRouteCDispatchingList(routeCids);
			List<MesWmProcessRouteCEquipment> equ = basePDDao.getProcessRouteCEquipmentList(routeCids);
			List<MesWmProcessRouteCMould> mould = basePDDao.getProcessRouteCMouldList(routeCids);
			
			//将主表和子表id分别替换掉
		
			oldRouteCid = new ArrayList<String>();
			for(MesWmProcessroutec rc : routec){
				oldRouteCid.add(rc.getGid());
			}
			String newRouteId = UUID.randomUUID().toString();
			Map<String,String> newRouteCidMap = new HashMap<String,String>();
			for(String oldCid : oldRouteCid){
				newRouteCidMap.put(oldCid, UUID.randomUUID().toString());
			}
			//重设id
			route.setRoutname(route.getRoutname()+"-副本");
			route.setGid(newRouteId);
			String designJson = route.getDesignJson();
			designJson = designJson.replaceAll(oldRouteId, newRouteId);
			for(String oldCid : oldRouteCid){
				designJson = designJson.replaceAll(oldCid, newRouteCidMap.get(oldCid));
			}
			route.setDesignJson(designJson);
			
			for(MesWmProcessroutec rc : routec){
				rc.setRoutGid(newRouteId);
				rc.setGid(newRouteCidMap.get(rc.getGid()));
				rc.setUpdateTime(new Date());
			}
			for(MesWmProcessRouteCPre rcp : pre){
				rcp.setRouteCGid(newRouteCidMap.get(rcp.getRouteCGid()));
			}
			for(MesWmProcessRoutecGoods g : goods){
				g.setRouteCGid(newRouteCidMap.get(g.getRouteCGid()));
			}
			for(MesWmProcessRouteCDispatching dis : dispatching){
				dis.setRouteCGid(newRouteCidMap.get(dis.getRouteCGid()));
			}
			for(MesWmProcessRouteCEquipment e : equ){
				e.setRouteCGid(newRouteCidMap.get(e.getRouteCGid()));
			}
			for(MesWmProcessRouteCMould m : mould){
				m.setRouteCGid(newRouteCidMap.get(m.getRouteCGid()));
			}
			basePDDao.insertCopyedRoute(route,routec,pre,goods,dispatching,equ, mould);
		}
		
	}

	@Override
	public void importRoute(File file, int type) throws Exception {
		//excel所有数据
		List<String[]> list = CommonUtil.getAllExcelData(file);
		if(type==0){
			//导入产品工序
			importBaseRoute(list);
		}else{
			//导入bom
			importBom(list);
		}
		
	}

	private String importBaseRoute(List<String[]> list) throws Exception {
		String routeIds = "";
		List<MesWmProcessroute> routeList = new ArrayList<MesWmProcessroute>();
		List<MesWmProcessroutec> routeClist = new ArrayList<MesWmProcessroutec>();
		List<MesAaWorkcenter> workcenterList = basePDDao.getWorkCenterList(0, 0, "").getList();
		
		//得到所有的标准工序
//		List<MesWmStandardprocess> processList = basePDDao.getStandardProcessList(0, 0, "").getList();
		
		Date date = new Date();
		String curProductCode = "0";
		String curRouteGid = "";
		String gen_nextProcessId = "";//预先生成下一个id
		boolean isNew = false;//是否是第一个工序
		boolean isLast = false;//是否是最后一个工序
		int process_index = 1;
		JSONArray designJson = null;
		 
        int left = 38,top = 72;//初始坐标
        int left_increment = 210, top_increment = 86;//坐标增量
        
        boolean reversal = true;//倒序？第一条数据是最后一道
        int start = 0,end = list.size();
        int lastIndex = end - 1;
        if(reversal){//倒序
        	start = list.size()-1;
        	end = -1;
        	lastIndex = end + 1;
        }
        int i = start;
        while(i!=end){
        	int pre_index = i-1;
        	int next_index = i+1;
        	if(reversal){//倒序
        		pre_index = i+1;
            	next_index = i-1;
            }
        	
        	String[] data = list.get(i);
			String product_code = CommonUtil.null2Str( CommonUtil.null2Str(data[0])).trim();//产品编码
			String free1 = CommonUtil.null2Str( data[1]).trim();//自由项
			int index = new BigDecimal(CommonUtil.isNullString(data[2])?"0":data[2].trim()).intValue();//工序顺序号
			String process_name = CommonUtil.null2Str( data[3]).trim();//工序名称
			String wc_name = CommonUtil.null2Str( data[4]).trim();//工作中心名称
			String isStock = CommonUtil.null2Str( data[5]).trim();//是否入库节点
			String isSemi = CommonUtil.null2Str( data[6]).trim();// 是否半成品
			BigDecimal standardHours = CommonUtil.isNullString(data[7])?null:new BigDecimal(data[7].trim());//标准工时
			BigDecimal realPrice = CommonUtil.isNullString(data[8])?null:new BigDecimal(data[8].trim());//实际工价
			String opdes = null;// 工序说明
			if(data.length>9){
				opdes = CommonUtil.null2Str( data[9]).trim();// 
			}
			if(CommonUtil.notNullString(product_code)){
				String routecGid = CommonUtil.isNullString(gen_nextProcessId)? UUID.randomUUID().toString():gen_nextProcessId;
				isNew = false;
				if(!curProductCode.equals(product_code)){
					isNew = true;
					process_index = 1;
					designJson = new JSONArray();
					curRouteGid = UUID.randomUUID().toString();
				}
				
				if(i==lastIndex || !product_code.equals(CommonUtil.null2Str(list.get(next_index)[0].trim()))){
					isLast = true;
				}else{
					isLast = false;
				}
				/*
				 * 子表数据
				 */
				MesWmProcessroutec routec = new MesWmProcessroutec();
				routec.setGid(routecGid);
				routec.setRoutGid(curRouteGid);
				routec.setOpGid(process_name);	//这里先放工序编码，然后通过更新数据库直接将编码匹配替换成id
				//工作中心id
				for(MesAaWorkcenter wc : workcenterList){
					if(wc_name.equals(wc.getWcname())){
						routec.setWorkCenterId(wc.getGid());
					}
				}
				
				if(!isNew){	//不是第一个，则拿上一条数据作为上道
					routec.setPreGid(CommonUtil.null2Str(list.get(pre_index)[1].trim()));
				}
				if(!isLast){
					routec.setNextGid(CommonUtil.null2Str(list.get(next_index)[1].trim()));
				}
				routec.setIsOut(0);
				routec.setCindex(process_index<10?("0"+process_index):(""+process_index));
				routec.setIsStock(isStock.equals("是")?1:0);
				routec.setDispatchingType(Integer.parseInt(Config.DEFAULT_DISPATCHING));
				routec.setNumber(new BigDecimal(1));
				routec.setUpdateTime(date);
				routec.setStockGoodsId(product_code);//这里先放编码
				routec.setIsSemi(isSemi.equals("是")?1:0);
				routec.setMemo(routec.getCindex());
				routec.setRealPrice(realPrice);
				routec.setStandardHours(standardHours);
				routec.setOpdes(opdes);
				routeClist.add(routec);
				
				JSONObject json = new JSONObject();//设置designjson
				json.put("id", routecGid);
				json.put("flow_id", curRouteGid);
				json.put("process_code", routec.getCindex());
				json.put("process_name", process_name);
				/*for(MesWmStandardprocess sp : processList){
					if(process_name.equals(sp.getOpname())){
						json.put("process_name", sp.getOpname());
						break;
					}
				}*/
				gen_nextProcessId = UUID.randomUUID().toString();//生成下一个id
				json.put("process_to", isLast ? "":gen_nextProcessId);
				json.put("icon", DEFAULT_ICON);
				if(process_index%6==1 && process_index!=1){//每6个换行,top增加
	        		top += top_increment;
	        	}else{
	        		if(((process_index-1)/6)%2==0){//奇数行，left坐标增加
	        			left += left_increment;
	        		}else{//偶数行，left坐标减少
	        			left -= left_increment;
	        		}
	        	}
				json.put("style", DEFAULT_STYLE+"left:"+left+"px;top:"+top+"px");
				designJson.add(json);
				
				if(isLast){
					/*
					 * 主表数据
					 */
					MesWmProcessroute route = new MesWmProcessroute();
					route.setGid(curRouteGid);
					route.setEffdate(date);
					route.setRoutname("产品"+product_code+(CommonUtil.isNullObject(free1)?"": "("+free1+")"));
					route.setGoodsUid(product_code);//这里先放产品编码，然后通过更新数据库直接将编码匹配替换成id
					route.setIsDelete(0);
					route.setDesignJson(designJson.toString());
					routeList.add(route);
					
					routeIds += curRouteGid+",";
					
					left = 38;top = 72;//初始坐标
			        left_increment = 210; top_increment = 86;//坐标增量
				}
				
				curProductCode = product_code;
				process_index ++ ;
	        	
				//倒序
	        	if(reversal){
	        		i--;
	        	}else{
	        		i++;
	        	}
			}
			
			
        }
		
		basePDDao.addImportProcessRoute(routeList);
		basePDDao.addImportProcessRouteC(routeClist);
		
		return CommonUtil.cutLastString(routeIds, ",");
	}
	
	private void importBom(List<String[]> list) throws Exception {
		List<MesWmProcessRoutecGoods> goodsList = new ArrayList<MesWmProcessRoutecGoods>();
		for(int i = 0 ; i < list.size() ; i++){
			String[] data = list.get(i);
			String productCode = CommonUtil.null2Str( data[0]).trim();//产品编码
			String productFree = CommonUtil.null2Str( data[1]).trim();//产品自由项
			String goodsCode = CommonUtil.null2Str( data[2]).trim();	//物料编码
			String goodsFree = CommonUtil.null2Str( data[3]).trim();	//物料自由项
			String process_name = CommonUtil.null2Str( data[4]).trim();//工序名称
			String number = CommonUtil.null2Str( data[5]).trim();		//基本用量
			String baseQty = CommonUtil.null2Str( data[6]).trim();		//基础数量
			MesWmProcessRoutecGoods goods = new MesWmProcessRoutecGoods();
			goods.setGid(UUID.randomUUID().toString());
			goods.setGoodsGid(goodsCode);
			goods.setBaseQuantity(CommonUtil.isNullString(baseQty)?new BigDecimal(1):new BigDecimal(baseQty));
			goods.setBaseUse(CommonUtil.isNullString(number)?new BigDecimal(1):new BigDecimal(number));
			goods.setNumber(goods.getBaseUse().divide(goods.getBaseQuantity(), 6, BigDecimal.ROUND_HALF_UP));
			goods.setRouteCGid(productCode+","+process_name);//这里先拼接成逗号隔开的字符串
			goods.setFree1(goodsFree);
			goodsList.add(goods);
		}
		
		basePDDao.addImportProcessRouteCGoods(goodsList);
//		this.saveBomToU8(process_objs, process_info, productCode);
	}

	@Override
	public List<AaFreeSet> getGoodsFreeset() {
		return basePDDao.getGoodsFreeset();
	}

	@Override
	public List<String> auditRoute(String routeId,String authUserId) {
		List<String> msg = new ArrayList<String>();
		String[] routeIds = routeId.split(",");
		List<MesWmProcessroute> auditRoute = new ArrayList<MesWmProcessroute>();
		for(String id : routeIds){
			//工艺路线信息
			MesWmProcessroute route = this.findBaseRoute(id);
			//修改审核状态
			route.setState(1);
			route.setAuthDate(new Date());
			route.setAuthUser(authUserId);
			
			if(Constants.INTERFACE_U890.equalsIgnoreCase(Config.INTERFACETYPE)){
				AaGoods product = cacheCtrlService.getGoods(route.getGoodsUid());
				JSONObject process_objs = new JSONObject();
				if(route!=null && product!=null){
					//获取节点及属性信息，转成json
					process_objs = this.getProcessJson(id,product);
					//把designJson转成process_info(工序连线信息)
					JSONArray designArray = JSONArray.fromObject(route.getDesignJson()==null?"[]":route.getDesignJson());
					if(designArray.size()==0){
						msg.add("工艺路线【"+route.getRoutname()+"】没有设置节点");
						continue;
					}
					if(route.getState()==null){
						msg.add("工艺路线【"+route.getRoutname()+"】没有初始化数据，可能导致BOM不准确");
					}
					JSONObject process_info = new JSONObject();
					for(Object o : designArray){
						JSONObject des = (JSONObject) o;
						JSONObject in_o = new JSONObject();
						
						String processTo = CommonUtil.Obj2String(des.get("process_to"));
						if(processTo.length()>0){
							in_o.put("process_to", "[\""+processTo.replaceAll(",", "\",\"")+"\"]");
						}else{
							in_o.put("process_to", "[]");
						}
						process_info.put(CommonUtil.Obj2String(des.get("id")), in_o);
					}
					
					/*
					 * 保存BOM到用友U890,对需要入库的分别生成bom
					 */
					this.saveBomToU8(process_objs.toString(),process_info.toString(),product.getGoodscode(),msg,route);
				}else{
					msg.add("异常：未从缓存查到工艺路线“"+route.getRoutname()+"”的产品信息");
					System.out.println("=====异常：未从缓存查到工艺路线“"+route.getRoutname()+"”的产品信息");
				}
			}

			auditRoute.add(route);
		}
		//保存更新
		if(auditRoute.size()>0 && msg.size()<=0){
			basePDDao.updateObject(auditRoute);
		}
		return msg;
	}

	@Override
	public void cancelAuditRoute(String routeId) {
		basePDDao.cancelAuditRoute(routeId);
	}

}
