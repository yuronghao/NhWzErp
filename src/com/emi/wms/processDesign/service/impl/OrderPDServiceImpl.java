package com.emi.wms.processDesign.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.sys.util.SysPropertites;
import com.emi.wms.bean.AaDepartment;
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
import com.emi.wms.bean.MesWmProduceProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProduceProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProduceProcessRouteCMould;
import com.emi.wms.bean.MesWmProduceProcessRouteCPre;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.WmProduceorderC2;
import com.emi.wms.bean.WmTask;
import com.emi.wms.processDesign.dao.OrderPDDao;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.processDesign.service.OrderPDService;
import com.emi.wms.processDesign.util.BasePDUtil;
import com.emi.wms.processDesign.util.NotEnoughException;
import com.emi.wms.servicedata.service.ProduceOrderService;
import com.emi.wms.servicedata.service.TaskService;

@SuppressWarnings({"unchecked","rawtypes"})
public class OrderPDServiceImpl extends EmiPluginService implements OrderPDService {
	private OrderPDDao orderPDDao;
	private BasePDService basePDService;
	private CacheCtrlService cacheCtrlService;
	private EmiPluginService emiPluginService;
	private TaskService taskService;
	private ProduceOrderService produceOrderService;

	public void setProduceOrderService(ProduceOrderService produceOrderService) {
		this.produceOrderService = produceOrderService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setEmiPluginService(EmiPluginService emiPluginService) {
		this.emiPluginService = emiPluginService;
	}

	public void setOrderPDDao(OrderPDDao orderPDDao) {
		this.orderPDDao = orderPDDao;
	}

	public void setBasePDService(BasePDService basePDService) {
		this.basePDService = basePDService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	@Override
	public JSONObject genOrderRoute(String orderId,boolean reGen) {
		JSONObject jobj=new JSONObject();
		if(!reGen){
			//不是重新生成的时候，先检测是否有数据
			boolean hasRoute = orderPDDao.checkHasRoute(orderId);
			reGen = hasRoute;
		}
		if(reGen){
			//删除原有数据
			orderPDDao.deleteOldProduceTask(orderId);
			orderPDDao.deleteOldData(orderId);
		}

		List<Map> pocpr = orderPDDao.getProduceOrderCProcessRoute(orderId);
		
		List<Map> maps=new ArrayList<Map>();
		for(Map m:pocpr){
			MesWmStandardprocess iprocess = cacheCtrlService.getMESStandardProcess(CommonUtil.Obj2String(m.get("opGid")));
			String opname_prc = iprocess==null?"":iprocess.getOpname();
			String opname_prd = CommonUtil.Obj2String(m.get("cfree1"));
			if((CommonUtil.isNullObject(m.get("designJson")) || (CommonUtil.isNullObject(m.get("state"))?0:Integer.parseInt(m.get("state").toString()))==0) && (CommonUtil.isNullString(opname_prd) && opname_prd.equals(opname_prc)) ){
				Map mm=new HashMap();
				mm.put("read", 1);
				mm.put("gid", m.get("gid").toString());
				maps.add(mm);
			}
		}
		
		if(maps.size()>0){
			jobj.put("success", 0);
			jobj.put("failInfor", "部分产品不存在工艺路线或未审核！");
			jobj.put("data", maps);
			return jobj;
		}
		
		
		//查询订单的所有物品
		List<WmProduceorderC> pcList = orderPDDao.getProduceOrderCList(orderId);
		
		//生成
		genRouteByOrderCs(pcList,orderId);
		
		jobj.put("success", 1);
		jobj.put("failInfor", "");
		return jobj;
		
	}
	
	@Override
	public void genProductRoute(String orderId, String ordercId) {
		//查询订单的所有物品
		List<WmProduceorderC> pcList = orderPDDao.getOrderCListById(ordercId);
		//生成
		genRouteByOrderCs(pcList,orderId);
	}
	
	private void genRouteByOrderCs(List<WmProduceorderC> pcList,String orderId) {
		//准备订单实体
		List<MesWmProduceProcessroute> p_routeList = new ArrayList<MesWmProduceProcessroute>();
		List<MesWmProduceProcessroutec> p_cList = new ArrayList<MesWmProduceProcessroutec>();
		List<MesWmProduceProcessRouteCPre> p_preList = new ArrayList<MesWmProduceProcessRouteCPre>();
		List<MesWmProduceProcessroutecGoods> p_goodsList = new ArrayList<MesWmProduceProcessroutecGoods>();
		List<MesWmProduceProcessRouteCDispatching> p_dispatchingList = new ArrayList<MesWmProduceProcessRouteCDispatching>();
		List<MesWmProduceProcessRouteCEquipment> p_equipmentList = new ArrayList<MesWmProduceProcessRouteCEquipment>();
		List<MesWmProduceProcessRouteCMould> p_mouldList = new ArrayList<MesWmProduceProcessRouteCMould>();
		Date billDate = new Date();
		for(WmProduceorderC pc : pcList){
			
			if(pc.getProductType().intValue()==1){//标准订单
				
				MesWmProduceProcessroute p_route = new MesWmProduceProcessroute();
				BigDecimal produceNumber = CommonUtil.null2BigDecimal(pc.getNumber(), "1") ;
				String orderCid = pc.getGid();
				String goodsId = pc.getGoodsUid();
				String free1 = pc.getCfree1();
				//查询标准工艺路线
				Map routeData = basePDService.getBaseProcessRouteOverall(goodsId,free1);
				if(routeData.get("route")!=null){
					
					MesWmProcessroute route =  (MesWmProcessroute) routeData.get("route");
					List<MesWmProcessroutec> cList = (List<MesWmProcessroutec>) routeData.get("cList");
					List<MesWmProcessRouteCPre> preList = (List<MesWmProcessRouteCPre>) routeData.get("preList");
					List<MesWmProcessRoutecGoods> goodsList = (List<MesWmProcessRoutecGoods>) routeData.get("goodsList");
					List<MesWmProcessRouteCDispatching> dispatchingList = (List<MesWmProcessRouteCDispatching>) routeData.get("dispatchingList");
					List<MesWmProcessRouteCEquipment> equipmentList = (List<MesWmProcessRouteCEquipment>) routeData.get("equipmentList");
					List<MesWmProcessRouteCMould> mouldList = (List<MesWmProcessRouteCMould>) routeData.get("mouldList");
					
					//与标准工艺路线数据的gid对应，重新生成gid给订单工艺路线，并拷贝数据
					String designJson = route.getDesignJson();
					Map<String,String> gids = new HashMap<String, String>();
					gids.put(route.getGid(), UUID.randomUUID().toString());
					designJson = designJson.replaceAll(route.getGid(), gids.get(route.getGid()));
					for(MesWmProcessroutec c : cList){
						gids.put(c.getGid(), UUID.randomUUID().toString());
						designJson = designJson.replaceAll(c.getGid(), gids.get(c.getGid()));
					}
					
					//拷贝主表
					p_route.setGid(gids.get(route.getGid()));
					p_route.setDesignJson(designJson);
					p_route.setProduceUid(orderId);
					p_route.setProduceCuid(orderCid);
					p_route.setBillDate(billDate);
					p_routeList.add(p_route);
					//拷贝子表
					for(MesWmProcessroutec c : cList){
						MesWmProduceProcessroutec p_routeC = new MesWmProduceProcessroutec();
						p_routeC.setGid(gids.get(c.getGid()));
						p_routeC.setProduceRouteGid(p_route.getGid());
						p_routeC.setCindex(c.getCindex());
						p_routeC.setOpGid(c.getOpGid());
						p_routeC.setWorkCenterId(c.getWorkCenterId());
						p_routeC.setNumber(CommonUtil.null2BigDecimal(c.getNumber(),"1").multiply(produceNumber));
						//上道和下道可以是多个，要拆分
						p_routeC.setNextGid(genIds(gids, c.getNextGid()));
						p_routeC.setPreGid(genIds(gids,c.getPreGid()));
						
						p_routeC.setIsCheck(c.getIsCheck());
						p_routeC.setIsOut(c.getIsOut());
						p_routeC.setIsStock(c.getIsStock());
						p_routeC.setIsSemi(c.getIsSemi());
						p_routeC.setDispatchingType(c.getDispatchingType());
						p_routeC.setPassRate(c.getPassRate());
						p_routeC.setStockGoodsId(c.getStockGoodsId());
						p_routeC.setSemiGoodsId(c.getSemiGoodsId());
						p_routeC.setUpdateTime(new Date());
						p_routeC.setRealPrice(c.getRealPrice());
						p_routeC.setStandardHours(c.getStandardHours());
						p_routeC.setOpdes(c.getOpdes());
						p_routeC.setIsMustScanMould(c.getIsMustScanMould());
						p_routeC.setMouldControlFetch(c.getMouldControlFetch());
						//设置条码
						p_routeC.setBarcode(SysPropertites.get("Ttitle")+emiPluginService.getBillId(Constants.TASKTYPE_DDGYZB));
						//第一道工序设置可开工数量
						/*if(CommonUtil.isNullString(p_routeC.getPreGid()) ){
							p_routeC.setCanDispatchNum(p_routeC.getNumber());
						}*/
						p_cList.add(p_routeC);
					}
					
					//拷贝属性表-上道转入
					for(MesWmProcessRouteCPre pre : preList){
						MesWmProduceProcessRouteCPre p_pre = new MesWmProduceProcessRouteCPre();
						p_pre.setRouteCGid(gids.get(pre.getRouteCGid()));
						p_pre.setPreRouteCGId(gids.get(pre.getPreRouteCGId()));
//								p_pre.setNumber(pre.getBaseUse()==null?null:pre.getBaseUse().divide(CommonUtil.null2BigDecimal(pre.getBaseQuantity(),"1"),6,BigDecimal.ROUND_HALF_UP).multiply(quantity));
						p_pre.setBaseUse(pre.getBaseUse());
						p_pre.setBaseQuantity(pre.getBaseQuantity());
						p_preList.add(p_pre);
					}
					//拷贝属性表-物料领用
					for(MesWmProcessRoutecGoods goods : goodsList){
						MesWmProduceProcessroutecGoods p_goods = new MesWmProduceProcessroutecGoods();
						p_goods.setProduceRouteCGid(gids.get(goods.getRouteCGid()));
						p_goods.setGoodsGid(goods.getGoodsGid());
						p_goods.setBaseUse(goods.getBaseUse());
						p_goods.setBaseQuantity(goods.getBaseQuantity());
						p_goods.setNumber(CommonUtil.null2BigDecimal(goods.getNumber(),"1").multiply(produceNumber));
						p_goods.setFree1(goods.getFree1());
						AaGoods aaGoods=cacheCtrlService.getGoods(goods.getGoodsGid());
						p_goods.setWhUid(aaGoods.getCdefwarehouse());
						p_goodsList.add(p_goods);
					}
					//拷贝属性表-派工对象
					for(MesWmProcessRouteCDispatching dis : dispatchingList){
						MesWmProduceProcessRouteCDispatching p_dis = new MesWmProduceProcessRouteCDispatching();
						p_dis.setRouteCGid(gids.get(dis.getRouteCGid()));
						p_dis.setObjType(dis.getObjType());
						p_dis.setObjGid(dis.getObjGid());
						p_dispatchingList.add(p_dis);
					}
					//拷贝属性表-设备
					for(MesWmProcessRouteCEquipment equ : equipmentList){
						MesWmProduceProcessRouteCEquipment p_equ = new MesWmProduceProcessRouteCEquipment();
						p_equ.setRouteCGid(gids.get(equ.getRouteCGid()));
						p_equ.setEquipmentGid(equ.getEquipmentGid());
						p_equipmentList.add(p_equ);
					}
					//拷贝属性表-模具
					for(MesWmProcessRouteCMould mould : mouldList){
						MesWmProduceProcessRouteCMould p_mould = new MesWmProduceProcessRouteCMould();
						p_mould.setRouteCGid(gids.get(mould.getRouteCGid()));
						p_mould.setMouldGid(mould.getMouldGid());
						p_mould.setGoodsCode(mould.getGoodsCode());
						p_mould.setGrossWeight(mould.getGrossWeight());
						p_mould.setNetWeight(mould.getNetWeight());
						
						p_mouldList.add(p_mould);
					}
				}else{
					//没有找到对应的标准工艺路线的情况下，自动生成一个空的工艺路线
					MesWmProduceProcessroute route =  new MesWmProduceProcessroute();
					route.setProduceUid(orderId);
					route.setProduceCuid(orderCid);
					route.setDesignJson("[]");
					route.setBillDate(billDate);
					p_routeList.add(route);
				}
				
			}else if(pc.getProductType().intValue()==2){//非标准订单
				
				String cfree1=pc.getCfree1();
				if(CommonUtil.isNullObject(cfree1)){
					break;
				}else{
					MesWmStandardprocess mws=orderPDDao.getMesWmStandardprocess(cfree1);
					
					if(mws==null){
						//没有找到对应的标准工序的情况下，自动生成一个空的工艺路线
						MesWmProduceProcessroute route =  new MesWmProduceProcessroute();
						route.setProduceUid(pc.getProduceOrderUid());
						route.setProduceCuid(pc.getGid());
						route.setDesignJson("[]");
						route.setBillDate(billDate);
						p_routeList.add(route);
					}else{
						
						MesWmProduceProcessroute p_route = new MesWmProduceProcessroute();//工艺路线主表
						p_route.setGid(UUID.randomUUID().toString());
						p_route.setProduceUid(pc.getProduceOrderUid());
						p_route.setProduceCuid(pc.getGid());
						p_route.setBillDate(billDate);
						p_routeList.add(p_route);
						
						MesWmProduceProcessroutec p_routeC = new MesWmProduceProcessroutec();//工艺路线子表
						p_routeC.setGid(UUID.randomUUID().toString());
						
						JSONArray jarray=new JSONArray();
						JSONObject jobj=new JSONObject();
						jobj.put("id", p_routeC.getGid());
						jobj.put("flow_id", p_route.getGid());
						jobj.put("process_code", "01");
						jobj.put("process_name", mws.getOpname());
						jobj.put("icon", "icon-cog");
						jobj.put("style", "min-width:120px;height:28px;line-height:28px;color:#0e76a8;left:564px;top:115px;");
						jobj.put("process_to", "");
						jarray.add(jobj);
						p_route.setDesignJson(jarray.toString());
						
						p_routeC.setProduceRouteGid(p_route.getGid());
						p_routeC.setCindex("01");
						p_routeC.setOpGid(mws.getGid());
//						p_routeC.setWorkCenterId(c.getWorkCenterId());
						p_routeC.setNumber(pc.getNumber());
						//上道和下道可以是多个，要拆分
						p_routeC.setNextGid(null);
						p_routeC.setPreGid(null);
						p_routeC.setIsCheck(new Integer(0));//是否质检
						p_routeC.setIsOut(new Integer(0));//是否委外
						p_routeC.setIsStock(new Integer(1));////是否入库
						p_routeC.setIsSemi(new Integer(0));////是否半成品
						p_routeC.setDispatchingType(new Integer(0));//派工对象 0：人  1：组
						p_routeC.setPassRate(new BigDecimal(1));
						p_routeC.setStockGoodsId(pc.getGoodsUid());//入库物料id
						p_routeC.setSemiGoodsId(null);//半成品id
						p_routeC.setUpdateTime(new Date());
						p_routeC.setRealPrice(new BigDecimal(1));
						p_routeC.setStandardHours(mws.getStandardPrice());
						p_routeC.setIsMustScanMould(new Integer(0));//是否必扫模具
						p_routeC.setMouldControlFetch(new Integer(0));//严格控制模具取数
						//设置条码
						p_routeC.setBarcode(SysPropertites.get("Ttitle")+emiPluginService.getBillId(Constants.TASKTYPE_DDGYZB));
						p_cList.add(p_routeC);
						
						List<WmProduceorderC2> materialuid=orderPDDao.getWmProduceorderC2(pc.getGid());
						for(WmProduceorderC2 goods:materialuid){
							MesWmProduceProcessroutecGoods p_goods = new MesWmProduceProcessroutecGoods();//物料领用
							p_goods.setProduceRouteCGid(p_routeC.getGid());
							p_goods.setGoodsGid(goods.getGoodsUid());
							p_goods.setBaseUse(new BigDecimal(1));
							p_goods.setBaseQuantity(new BigDecimal(1));
							p_goods.setNumber(goods.getNumber());
							p_goods.setFree1(goods.getCfree1());
							AaGoods aaGoods=cacheCtrlService.getGoods(goods.getGoodsUid());
							p_goods.setWhUid(aaGoods.getCdefwarehouse());
							p_goodsList.add(p_goods);
						}
						
						
					}
					

				}
				
			}
			
		}
		//插入数据
		orderPDDao.saveCopyedProduceRoute(p_routeList,p_cList,p_preList,p_goodsList,p_dispatchingList,p_equipmentList,p_mouldList);
		
	}

	
	private String genIds(Map<String,String> gidMap,String ids){
		String resIds = "";
		String[] ids_array = CommonUtil.Obj2String(ids).split(",");
		for(String id : ids_array){
			if(CommonUtil.notNullString(id)){
				resIds += gidMap.get(id)+ ",";
			}
		}
		resIds = CommonUtil.cutLastString(resIds, ",");
		return "".equals(resIds)?null:resIds;
	}
	
	@Override
	public boolean checkHasRoute(String orderId){
		return orderPDDao.checkHasRoute(orderId);
	}

	@Override
	public MesWmProduceProcessroute queryProduceRoute(String orderId,
			String orderCid) {
		return orderPDDao.queryProduceRoute( orderId, orderCid);
	}

	@Override
	public JSONObject getProcessJson(String routeId) {
		JSONObject json = new JSONObject();
		List<MesWmProduceProcessroutec> routecList = orderPDDao.getRouteCList(routeId);
		String routeCids = "";
		for(MesWmProduceProcessroutec c : routecList){
			routeCids += c.getGid() + ",";
		}
		routeCids = CommonUtil.cutLastString(routeCids, ",");
		
		List<MesWmProduceProcessRouteCPre> preList = orderPDDao.getProcessRouteCPreList(routeCids);
		List<MesWmProduceProcessroutecGoods> goodsList = orderPDDao.getProcessRouteCGoodsList(routeCids);
		List<MesWmProduceProcessRouteCDispatching> dispatchingList = orderPDDao.getProcessRouteCDispatchingList(routeCids);
		List<MesWmProduceProcessRouteCEquipment> equipmentList = orderPDDao.getProduceProcessRouteCEquipmentList(routeCids);
		List<MesWmProduceProcessRouteCMould> mouldList = orderPDDao.getProduceProcessRouteCMouldList(routeCids);
		
		for(MesWmProduceProcessroutec c : routecList){
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
			base.put("passRate", c.getPassRate()==null?new BigDecimal(0):c.getPassRate().stripTrailingZeros().toPlainString());
			base.put("workCenterId", CommonUtil.null2Str( c.getWorkCenterId()));
			MesAaWorkcenter workcenter = cacheCtrlService.getworkCenter(CommonUtil.null2Str(c.getWorkCenterId()));
			base.put("workCenterName", workcenter==null?"":workcenter.getWcname());
			AaGoods stockGoods = cacheCtrlService.getGoods(CommonUtil.null2Str(c.getStockGoodsId()));
			base.put("stockGoodsId", CommonUtil.null2Str( c.getStockGoodsId()));
			base.put("stockGoodsName", stockGoods==null?"":stockGoods.getGoodsname());
			AaGoods semiGoods = cacheCtrlService.getGoods(CommonUtil.null2Str(c.getSemiGoodsId()));
			base.put("semiGoodsId", CommonUtil.null2Str( c.getSemiGoodsId()));
			base.put("semiGoodsName", semiGoods==null?"":semiGoods.getGoodsname());
			base.put("isMustScanMould", c.getIsMustScanMould());
			base.put("mouldControlFetch", c.getMouldControlFetch());
			//标准工序
			MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(CommonUtil.null2Str(c.getOpGid()));
			base.put("standardPrice",sp.getStandardPrice()==null?new BigDecimal(0):sp.getStandardPrice().stripTrailingZeros().toPlainString());
			base.put("realPrice", c.getRealPrice()==null?new BigDecimal(0): c.getRealPrice().stripTrailingZeros().toPlainString());
			base.put("standardHours", c.getStandardHours()==null?"": c.getStandardHours().stripTrailingZeros().toPlainString());
			base.put("opdes", CommonUtil.null2Str( c.getOpdes()));
			//条码
			base.put("barcode", CommonUtil.null2Str( c.getBarcode()));
			//上道工序转入
			for(MesWmProduceProcessRouteCPre pre : preList){
				if(c.getGid().equals(pre.getRouteCGid())){
					JSONObject pre_json = new JSONObject();
					pre_json.put("routeCid", pre.getPreRouteCGId());
//					pre_json.put("number", pre.getNumber().stripTrailingZeros().toPlainString());
					pre_json.put("baseUse", CommonUtil.null2BigDecimal(pre.getBaseUse(), "1").stripTrailingZeros().toPlainString());
					pre_json.put("baseQuantity", CommonUtil.null2BigDecimal(pre.getBaseQuantity(),"1").stripTrailingZeros().toPlainString());
					attrPreProc.add(pre_json);
				}
			}
			//物料
			for(MesWmProduceProcessroutecGoods g : goodsList){
				if(c.getGid().equals(g.getProduceRouteCGid())){
					AaGoods goods = cacheCtrlService.getGoods(g.getGoodsGid());
					JSONObject goods_json = new JSONObject();
					goods_json.put("goodsId", g.getGoodsGid());
					goods_json.put("goodscode", goods==null?"":goods.getGoodscode());
					goods_json.put("goodsname", goods==null?"":goods.getGoodsname());
					goods_json.put("goodsstandard", goods==null?"":goods.getGoodsstandard());
					goods_json.put("unitName", goods==null?"":goods.getUnitName());
					goods_json.put("baseUse", CommonUtil.null2BigDecimal(g.getBaseUse(), "1").stripTrailingZeros().toPlainString());
					goods_json.put("baseQuantity", CommonUtil.null2BigDecimal(g.getBaseQuantity(), "1").stripTrailingZeros().toPlainString());
					goods_json.put("number", CommonUtil.null2BigDecimal(g.getNumber(),"1").stripTrailingZeros().toPlainString());
					goods_json.put("free1", g.getFree1());
					attrGoods.add(goods_json);
				}
			}
			
			//派工对象
			for(MesWmProduceProcessRouteCDispatching dis : dispatchingList){
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
			for(MesWmProduceProcessRouteCEquipment equ : equipmentList){
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
			for(MesWmProduceProcessRouteCMould mould : mouldList){
				if(c.getGid().equals(mould.getRouteCGid())){
					
					JSONObject mould_json = new JSONObject();
					Mould mould_cach = cacheCtrlService.getMould(mould.getMouldGid());
					mould_json.put("mouldId", mould.getMouldGid());
					mould_json.put("mouldCode", mould_cach==null?"":mould_cach.getMouldcode());
					mould_json.put("mouldName", mould_cach==null?"":mould_cach.getMouldname());
					mould_json.put("mouldRatio", mould_cach==null?"":mould_cach.getMouldRatio());
					
					mould_json.put("goodsCode", CommonUtil.Obj2String(mould.getGoodsCode()));
					mould_json.put("grossWeight",CommonUtil.Obj2String(mould.getGrossWeight()));
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
	public void saveProcessData(String isAlter,String productId,List<MesWmProduceProcessroutec> processList,String route_id, String process_info,
			String arr_updProcess, String arr_addProcess,String arr_delProcess, String process_objs, String process_codeJson,String number, String arr_taskProcess, 
			String arr_taskNextProcess, String changeOrder, String changeSrcInfo) throws NotEnoughException {
		JSONObject info_json = JSONObject.fromObject(process_info);//连线信息
		JSONArray updProcess = JSONArray.fromObject(arr_updProcess);//id数组
		JSONArray addProcess = JSONArray.fromObject(arr_addProcess);//对象数组
		JSONArray delProcess = JSONArray.fromObject(arr_delProcess);//id数组
		JSONObject objs_json = JSONObject.fromObject(process_objs); //节点详细信息
		JSONObject codeJson = JSONObject.fromObject(process_codeJson);//工序编码信息
		JSONArray taskProcess = JSONArray.fromObject(arr_taskProcess);//id数组
		JSONArray taskOldNextProcess = JSONArray.fromObject(arr_taskNextProcess);//id数组
		JSONObject changeSrcJson = JSONObject.fromObject(changeSrcInfo);//改制来源
		
		/*
		 * 找出末道工序，推算数量（工序应完成数量、应领料数量）
		 */
		Iterator it = info_json.keys();  
		List<String> lastRouteCIds = new ArrayList<String>();
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
        
        //开始推算数量,产品数量1
        BasePDUtil.genNumber(lastRouteCIds,objs_json,number);
	/*	//1、保存主表设计
		//saveDesign(productId,processList, route_id, info_json, updProcess, addProcess, delProcess, objs_json, codeJson);*/
		// 2、保存数据
		saveData(isAlter,productId,processList, route_id, info_json, updProcess, addProcess, delProcess, objs_json, codeJson,taskProcess,taskOldNextProcess, changeOrder, changeSrcJson);
		
	}
	
	
	public void changeRealPrice(String routeCid, String realPrice)throws NotEnoughException {
		
		List<Map> maps=new ArrayList<Map>();
		Map m=new HashMap();
		m.put("routeCid", routeCid);
		m.put("realPrice", realPrice);
		maps.add(m);
		
		orderPDDao.changeRealPrice(maps);
	}
	
	/*
	 * 保存数据
	 */
	private void saveData(String isAlter,String productId,List<MesWmProduceProcessroutec> processCList,String route_id,JSONObject info_json,
			JSONArray updProcess,JSONArray addProcess,JSONArray delProcess,JSONObject objs_json,JSONObject codeJson, JSONArray taskProcess, JSONArray taskOldNextProcess,
			String changeOrder, JSONObject changeSrcJson) throws NotEnoughException{
		//1 主表信息
		String designJson = basePDService.getDesignJsonStr(route_id,info_json,objs_json);//设计图
		MesWmProduceProcessroute route = new MesWmProduceProcessroute();
		route.setGid(route_id);
		route.setGoodsUid(productId);
		route.setDesignJson(designJson);
		//如果是改制订单，保存改制来源
		if("1".equals(changeOrder) && changeSrcJson!=null){
			String srcRoutecId = changeSrcJson.get("routeCid")==null?"":changeSrcJson.getString("routeCid");
			String srcOrdercId = changeSrcJson.get("orderCid")==null?"":changeSrcJson.getString("orderCid");
			BigDecimal changeNumber = changeSrcJson.get("changeNumber")==null?null:new BigDecimal(changeSrcJson.getString("changeNumber"));
			/*
			 * 防止数据重复，先清除改制来源
			 */
			this.clearChangeSrc(srcOrdercId, srcRoutecId, route_id);
			
			/*
			 * 二次校验是否有足够数量作为改制来源
			 */
			BigDecimal enabledNum = orderPDDao.getEnabledChangeNumber(srcRoutecId);
			if(enabledNum.compareTo(changeNumber)>=0){
				/*
				 * 重新保存改制来源
				 */
				route.setChangeSrcRouteCid(srcRoutecId);//来源工艺路线字表id
				route.setChangeSrcOrderCid(srcOrdercId);//来源订单子表id
				route.setChangeSrcNumber(changeNumber);//来源工序 转出数量
				//折算成产品数量
				if(CommonUtil.notNullString(route.getChangeSrcRouteCid()) && route.getChangeSrcNumber()!=null){
					MesWmProduceProcessroutec srcRoutec = orderPDDao.findProduceProcessroutec(route.getChangeSrcRouteCid());
					WmProduceorderC srcOrderc = orderPDDao.findProduceOrderc(route.getChangeSrcOrderCid());
					BigDecimal produceNumber = srcOrderc.getNumber()==null?new BigDecimal(0):srcOrderc.getNumber();
					//对应转出产品数量
					BigDecimal realNumber = produceNumber.multiply(route.getChangeSrcNumber()).divide(srcRoutec.getNumber(),6,BigDecimal.ROUND_HALF_UP);
					srcRoutec.setTurnoutedNum((srcRoutec.getTurnoutedNum()==null?new BigDecimal(0):srcRoutec.getTurnoutedNum()) .add(route.getChangeSrcNumber()));
					
					//更新来源工艺路线子表（转出数量）
					orderPDDao.updateObject(srcRoutec);
					//数量回填到订单子表
					orderPDDao.setOrdercTurnoutNum(route.getChangeSrcOrderCid(),realNumber);
				}
			}else{
				throw new NotEnoughException("工艺路线子表ID["+srcOrdercId+"]没有足够的数量用于改制,需求数量：("+changeNumber.stripTrailingZeros().toPlainString()+"),可用数量：("+enabledNum.stripTrailingZeros().toPlainString()+")");
			}
			
		}
		
		/*
		 * 更新的信息
		 */
		List<MesWmProduceProcessroutec> updateList = new ArrayList<MesWmProduceProcessroutec>();
		//转换json存入updateList
		transProcessJson(route_id,updProcess, codeJson,info_json, objs_json, updateList,false);
		
		/*
		 * 新增的信息
		 */
		List<MesWmProduceProcessroutec> addList = new ArrayList<MesWmProduceProcessroutec>();
		//转换json存入addList
		transProcessJson(route_id,addProcess, codeJson,info_json, objs_json, addList,true);
		
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
		
		orderPDDao.updateOrderRoute(route);
		orderPDDao.addProcessRouteC(addList);//新增的工序
		orderPDDao.updateProcessRouteC(updateList);//更新的工序
		orderPDDao.deleteProcessRouteC(deleteIds);//删除工序
		orderPDDao.deleteRoutecAttributes(updateIds);//删除更新的工序的属性设置，重新添加
		orderPDDao.insertRoutecAttributes(updateList,addList);
		
		//4、删除已删除的节点的领料任务
		orderPDDao.deleteTasksByBillId(deleteIds);
		
		
		
	}
	
	/*
	 * 处理任务
	 */
	@Override
	public void dealRouteTask(String route_id) {
		//1、闲置领料任务(还未领的所有的任务)
		List<WmTask> idleTaskList = orderPDDao.getIdleTask(route_id);
		//2、闲置且有料可以领的首道工序
		List<MesWmProduceProcessroutec> idleStartRoutecList = orderPDDao.getIdleStartRoutec(route_id);
		//3、上道工序有开工了的 且 本道料没领的工序
		List<MesWmProduceProcessroutec> preDispatchedRoutec = orderPDDao.getPreDispatchedRoutec(route_id);
		
		//3、删除闲置领料任务
		String taskIds = "";
		for(WmTask t : idleTaskList){
			taskIds += t.getGid()+",";
		}
		taskIds = CommonUtil.cutLastString(taskIds, ",");
		orderPDDao.deleteTasks(taskIds);
		
		//4、生成工序领料任务
		List<WmTask> taskList = new ArrayList<WmTask>();
		for(MesWmProduceProcessroutec p : idleStartRoutecList){
			WmTask task = new WmTask(p.getGid(), p.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
			if(!taskList.contains(task)){
				taskList.add(task);
			}
		}
		for(MesWmProduceProcessroutec p : preDispatchedRoutec){
			WmTask task = new WmTask(p.getGid(), p.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
			if(!taskList.contains(task)){
				taskList.add(task);
			}
		}
		if(taskList.size()>0){
			taskService.createTask(taskList);
		}
	}

	/*
	 * 任务处理（不用了，情况太多，改成整体处理）
	 */
	private void dealTask(JSONArray delProcess,JSONArray taskOldNextProcess,JSONArray taskProcess, JSONObject info_json,JSONObject objs_json,List<MesWmProduceProcessroutec> addList,List<MesWmProduceProcessroutec> updateList) {
		//3、如果是变更工艺，按传值，删除老领料任务
		List<String> oldProcessList = (List<String>) JSONArray.toCollection(taskOldNextProcess, String.class);
		if(oldProcessList.size()>0){
			orderPDDao.deleteProcessGoodsTask(oldProcessList);
		}
		
		List<WmTask> taskList = new ArrayList<WmTask>();
		//4、如果是变更工艺，按传值，重新生成领料任务
		List<String> newNextProcess = new ArrayList<String>();
		for(Object o : taskProcess){
			String tid = (String) o;
            Object obj = info_json.get(tid);
            JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
            if(process_node==null || process_node.isNullObject()){
            	continue;
            }
        	JSONArray nextNodes = process_node.getJSONArray("process_to");
        	for(Object o2 : nextNodes){
        		String nextNodeId = (String) o2;
        		//判断是否有料需要领
        		if(objs_json.get(nextNodeId)!=null && objs_json.getJSONObject(nextNodeId).get("attrGoods")!=null && objs_json.getJSONObject(nextNodeId).getJSONArray("attrGoods").size()>0){
        			newNextProcess.add(nextNodeId);
        		}
        	}
		}
		for(String newId : newNextProcess){
			for(MesWmProduceProcessroutec add_c : addList){
				if(newId.equals(add_c.getGid())){
					WmTask task = new WmTask(newId, add_c.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
					if(!taskList.contains(task)){
						taskList.add(task);
					}
					break;
				}
			}
			for(MesWmProduceProcessroutec upd_c : updateList){
				if(newId.equals(upd_c.getGid())){
					WmTask task = new WmTask(newId, upd_c.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
					if(!taskList.contains(task)){
						taskList.add(task);
					}
					break;
				}
			}
		}
		//5、如果又加入了一个首道工序，也需要生成领料任务
		for(MesWmProduceProcessroutec pc : addList){
			if(CommonUtil.isNullObject(pc.getPreGid())){
				WmTask task = new WmTask(pc.getGid(), pc.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
				if(!taskList.contains(task)){
					taskList.add(task);
				}
			}
		}
		
		//生成任务
		if(taskList.size()>0){
			taskService.createTask(taskList);
		}
		
	}

	/*
	 * 将json转换成数据对象
	 */
	private void transProcessJson(String route_id,JSONArray idArray,JSONObject codeJson,JSONObject info_json,JSONObject objs_json,List<MesWmProduceProcessroutec> processClist,boolean genBarcode){
		for(int i=0;i<idArray.size();i++){
			MesWmProduceProcessroutec prc = new MesWmProduceProcessroutec();
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
				if(objs_json.get(to_id)!=null){
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
				prc.setProduceRouteGid(route_id);
				prc.setOpGid(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("standardProcessId")));//工序gid
				prc.setCindex(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("processIndex")));//工序序号
				prc.setDispatchingType(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("dispatchingType"))?Integer.parseInt(Config.DEFAULT_DISPATCHING):Integer.parseInt(p_obj.getJSONObject("base").getString("dispatchingType")));//派工类型
				prc.setIsCheck(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isCheck"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isCheck")));//是否质检
				prc.setIsOut(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isOut"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isOut")));//是否委外
				prc.setIsStock(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isStock"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isStock")));//是否入库
				prc.setIsSemi(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isSemi"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isSemi")));//是否半成品
				prc.setPassRate(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("passRate"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("passRate")));//检验合格率
				prc.setWorkCenterId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("workCenterId")));//
				prc.setStockGoodsId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("stockGoodsId")));//入库物料id
				prc.setSemiGoodsId(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("semiGoodsId")));//半成品id
				prc.setNumber(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("number"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("number")));//应完工数量
				prc.setRealPrice(CommonUtil.str2BigDecimal(CommonUtil.Obj2String( p_obj.getJSONObject("base").get("realPrice"))));//实际工价
				prc.setStandardHours(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("standardHours"))?null:new BigDecimal(p_obj.getJSONObject("base").getString("standardHours")));//标准工时
				prc.setOpdes(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("opdes")));//工序描述
				prc.setIsMustScanMould(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("isMustScanMould"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("isMustScanMould")));
				prc.setMouldControlFetch(CommonUtil.isNullObject(p_obj.getJSONObject("base").get("mouldControlFetch"))?0:Integer.parseInt(p_obj.getJSONObject("base").getString("mouldControlFetch")));
				//设置条码
				if(genBarcode){
					prc.setBarcode(SysPropertites.get("Ttitle")+emiPluginService.getBillId(Constants.TASKTYPE_DDGYZB));
				}else{
					prc.setBarcode(CommonUtil.Obj2String(p_obj.getJSONObject("base").get("barcode")));
				}
				
				//第一道工序设置可开工数量
				/*if(CommonUtil.isNullString(prc.getPreGid()) ){
					prc.setCanDispatchNum(prc.getNumber());
				}*/
				//2.2上道工序
				JSONArray preArray = p_obj.get("attrPreProc")==null?new JSONArray():p_obj.getJSONArray("attrPreProc");
				List<MesWmProduceProcessRouteCPre> preList = new ArrayList<MesWmProduceProcessRouteCPre>();
				MesWmProduceProcessRouteCPre prc_pre = null;
				for(Object pre : preArray){
					JSONObject j = (JSONObject) pre;
					prc_pre = new MesWmProduceProcessRouteCPre();
					prc_pre.setRouteCGid(routeC_gid);
					prc_pre.setPreRouteCGId(CommonUtil.Obj2String(j.get("routeCid")));
//					prc_pre.setNumber(new BigDecimal(j.get("number")==null?"0":j.getString("number")));
					prc_pre.setBaseUse(new BigDecimal(j.get("baseUse")==null?"1":j.getString("baseUse")));
					prc_pre.setBaseQuantity(new BigDecimal(j.get("baseQuantity")==null?"1":j.getString("baseQuantity")));
					preList.add(prc_pre);
				}
				
				//2.3 物料领用
				JSONArray goodsArray = p_obj.get("attrGoods")==null?new JSONArray():p_obj.getJSONArray("attrGoods");
				List<MesWmProduceProcessroutecGoods> goodsList = new ArrayList<MesWmProduceProcessroutecGoods>();
				MesWmProduceProcessroutecGoods prc_goods = null;
				for(Object goods : goodsArray){
					JSONObject g = (JSONObject) goods;
					prc_goods = new MesWmProduceProcessroutecGoods();
					prc_goods.setProduceRouteCGid(routeC_gid);
					prc_goods.setGoodsGid(CommonUtil.Obj2String(g.get("goodsId")));
					prc_goods.setNumber(new BigDecimal(CommonUtil.isNullObject(g.get("number"))?"1":g.getString("number")));
					prc_goods.setBaseUse(new BigDecimal(CommonUtil.isNullObject(g.get("baseUse"))?"1":g.getString("baseUse")));
					prc_goods.setBaseQuantity(new BigDecimal(CommonUtil.isNullObject(g.get("baseQuantity"))?"1":g.getString("baseQuantity")));
					AaGoods aaGoods=cacheCtrlService.getGoods(CommonUtil.Obj2String(g.get("goodsId")));
					prc_goods.setWhUid(aaGoods.getCdefwarehouse());
					
					//从缓存取值，判断是否有自由项
					AaGoods cacheGoods = cacheCtrlService.getGoods(prc_goods.getGoodsGid());
					boolean b_free1 = cacheGoods==null?false:cacheGoods.getCfree1()==null?false:cacheGoods.getCfree1()==1;
					if(b_free1){
						prc_goods.setFree1(CommonUtil.Obj2String(g.get("free1")));
					}
					goodsList.add(prc_goods);
				}
				
				//2.4 派工对象（工作组、人员等）
				JSONArray disArray = p_obj.get("attrDispatching")==null?new JSONArray():p_obj.getJSONArray("attrDispatching");
				List<MesWmProduceProcessRouteCDispatching> workCenterList = new ArrayList<MesWmProduceProcessRouteCDispatching>();
				MesWmProduceProcessRouteCDispatching prc_dis = null;
				for(Object dispatching : disArray){
					JSONObject dis = (JSONObject) dispatching;
					prc_dis = new MesWmProduceProcessRouteCDispatching();
					prc_dis.setRouteCGid(routeC_gid);
					prc_dis.setObjGid(CommonUtil.Obj2String(dis.get("objId")));
					prc_dis.setObjType(Integer.parseInt(dis.get("objType")==null?"0":dis.getString("objType")));;
					
					workCenterList.add(prc_dis);
				}
				
				//2.5 设备
				JSONArray equipmentArray = p_obj.get("attrEquipment")==null?new JSONArray():p_obj.getJSONArray("attrEquipment");
				List<MesWmProduceProcessRouteCEquipment> equipmentList = new ArrayList<MesWmProduceProcessRouteCEquipment>();
				MesWmProduceProcessRouteCEquipment prc_equ = null;
				for(Object equipment : equipmentArray){
					JSONObject equ = (JSONObject) equipment;
					prc_equ = new MesWmProduceProcessRouteCEquipment();
					prc_equ.setRouteCGid(routeC_gid);
					prc_equ.setEquipmentGid(CommonUtil.Obj2String(equ.get("equipmentId")));
					
					equipmentList.add(prc_equ);
				}
				
				//2.6 模具
				JSONArray mouldArray = p_obj.get("attrMould")==null?new JSONArray():p_obj.getJSONArray("attrMould");
				List<MesWmProduceProcessRouteCMould> mouldList = new ArrayList<MesWmProduceProcessRouteCMould>();
				MesWmProduceProcessRouteCMould prc_mould = null;
				for(Object mould : mouldArray){
					JSONObject mou = (JSONObject) mould;
					prc_mould = new MesWmProduceProcessRouteCMould();
					prc_mould.setRouteCGid(routeC_gid);
					prc_mould.setMouldGid(CommonUtil.Obj2String(mou.get("mouldId")));
					
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

	@Override
	public void createTask(String orderId) {
		//1、删除原有任务
		orderPDDao.deleteOldOrderTask(orderId);
		
		//2、创建新任务
		//首道工序（可以多个并列）
		List<MesWmProduceProcessroutec> routec_list = orderPDDao.getProduceFirstProcess(orderId);
		//首道工序领料
		String routeCids = "";
		for(MesWmProduceProcessroutec routec : routec_list){
			routeCids += routec.getGid()+",";
		}
		routeCids = CommonUtil.cutLastString(routeCids, ",");
		List<MesWmProduceProcessroutecGoods> goods_list = orderPDDao.getProcessRouteCGoodsList(routeCids);
		
		List<WmTask> taskList = new ArrayList<WmTask>();
		for(MesWmProduceProcessroutec rc : routec_list){
			//判断是否有料需要领
			boolean istask = false;
			for(MesWmProduceProcessroutecGoods goods : goods_list){
				if(rc.getGid().equals(goods.getProduceRouteCGid())){
					istask = true;
					break;
				}
			}
			if(istask){
				WmTask task = new WmTask(rc.getGid(), rc.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
				taskList.add(task);
			}
		}
		if(taskList.size()>0){
			taskService.createTask(taskList);
		}
	}
	
	@Override
	public int createTask4Produce(String orderId) {
		//根据订单id查找产品工艺路线
		List<MesWmProduceProcessroute> routeList = orderPDDao.getProduceRouteByOrder(orderId);
		int suc_cnt = 0;
		for(MesWmProduceProcessroute route : routeList){
			//查询是否有料已领（有料领了就无法再派发领料任务）
			boolean hasBro = orderPDDao.checkGoodsTaskByRoute(route.getGid());
			if(!hasBro){
				//1、删除原有任务
				orderPDDao.deleteOldProduceTask(route.getProduceCuid());
				
				//2、创建新任务
				//首道工序（可以多个并列）
				Map param=getymsetting("createPickingTaskTogether");
				List<MesWmProduceProcessroutec> routec_list=new ArrayList<MesWmProduceProcessroutec>();
				
				if(param.get("paramValue").equals("1")){//点击触发领料任务时一起生成出库任务
					routec_list = orderPDDao.getProduceFirstProcessByOrderc(route.getProduceCuid(),1);
				}else{
					routec_list = orderPDDao.getProduceFirstProcessByOrderc(route.getProduceCuid(),0);
				}
				
				//首道工序领料
				String routeCids = "";
				for(MesWmProduceProcessroutec routec : routec_list){
					routeCids += routec.getGid()+",";
				}
				routeCids = CommonUtil.cutLastString(routeCids, ",");
				List<MesWmProduceProcessroutecGoods> goods_list = orderPDDao.getProcessRouteCGoodsList(routeCids);
				
				List<WmTask> taskList = new ArrayList<WmTask>();
				for(MesWmProduceProcessroutec rc : routec_list){
					//判断是否有料需要领
/*					boolean istask = false; 统一调用生成任务的方法
					for(MesWmProduceProcessroutecGoods goods : goods_list){
						if(rc.getGid().equals(goods.getProduceRouteCGid())){
							istask = true;
							break;
						}
					}
					if(istask){
						WmTask task = new WmTask(rc.getGid(), rc.getBarcode(), Constants.BILLGIDSOURCE_SCDDGY, Constants.TASKTYPE_GXLL);
						taskList.add(task);
					}*/
					produceOrderService.createMeterailTask(rc.getGid());//创建领料任务
					
				}
/*				if(taskList.size()>0){
					taskService.createTask(taskList);
				}*/
				suc_cnt ++ ;
			}
		}
		
		return suc_cnt;
	}

	@Override
	public boolean checkDoingTask(String orderId,int type) {
		return orderPDDao.checkDoingTask(orderId,null, type);
	}
	
	@Override
	public boolean checkDoingTaskByOrderc(String ordercId, int type) {
		return orderPDDao.checkDoingTask(null,ordercId, type);
	}

	@Override
	public void deleteProductRoute(String ordercId) {
		//删除领料任务
		orderPDDao.deleteOldProduceTask(ordercId);
		//删除订单工艺路线
		orderPDDao.deleteProductOldData(ordercId);
	}
	
	@Override
	public void deleteOrderRoute(String orderId) {
		//删除领料任务
		orderPDDao.deleteOldOrderTask(orderId);
		//删除订单工艺路线
		orderPDDao.deleteOldData(orderId);
	}

	@Override
	public PageBean getEnabledChangeOrder(String orgId, String sobId,int pageIndex,int pageSize, String condition) {
		return getEnabledChangeOrder(orgId, sobId, pageIndex, pageSize, condition, true);
	}
	@Override
	public PageBean getEnabledChangeOrder(String orgId, String sobId,int pageIndex,int pageSize, String condition,boolean filter) {
		PageBean pagebean = orderPDDao.getEnabledChangeOrder(orgId,sobId, pageIndex, pageSize, condition, filter);
		List<Map> list = pagebean.getList();
//		poc.gid,po.billCode,po.billDate,poc.goodsUid,poc.number,poc.cfree1,po.deptGid,po.managerGid
		for(Map m : list){
			AaGoods goods = cacheCtrlService.getGoods(CommonUtil.Obj2String(m.get("goodsUid")));
			AaDepartment dept = cacheCtrlService.getDepartment(CommonUtil.Obj2String(m.get("deptGid")));
			AaPerson manager = cacheCtrlService.getPerson(CommonUtil.Obj2String(m.get("managerGid")));
			m.put("goodsName",goods==null?"": goods.getGoodsname());
			m.put("goodsCode",goods==null?"": goods.getGoodscode());
			m.put("goodsStandard",goods==null?"": goods.getGoodsstandard());
//			m.put("departmentCode",dept==null?"": dept.getDepcode());
			m.put("departmentName",dept==null?"": dept.getDepname());
			m.put("personName",manager==null?"": manager.getPername());
		}
		return pagebean;
	}

	@Override
	public String checkData(String orderId) {
		String msg = "success";
		List<MesWmProduceProcessroutec> routecList = orderPDDao.getProduceRouteCList(orderId);
		for(MesWmProduceProcessroutec rc : routecList){
			if(rc.getRealPrice()==null || rc.getRealPrice().compareTo(new BigDecimal(0))==0){
				//未填写实际工价
				msg = "实际工价未填写，请检查订单工艺路线！";
				break;
			}
		}
		if(routecList.size()==0){
			msg = "没有订单工艺路线！";
		}
		return msg;
	}

	@Override
	public List<Map> getChangeProcessSrc(String ordercId,String condition ,boolean ignoreNum) {
		List<Map> list ;
		if(ignoreNum){//忽略是否有可用数量，展示用
			list = orderPDDao.getChangeProcessSrcInfo(ordercId,condition);
		}else{
			list = orderPDDao.getChangeProcessSrc(ordercId,condition);
		}
		
		for(Map m : list){
			MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(CommonUtil.Obj2String(m.get("opGid")));
			if(sp!=null){
				m.put("opCode", sp.getOpcode());
				m.put("opName", sp.getOpname());
			}
		}
		return list;
	}

	@Override
	public boolean checkProcessTask(String routeCid) {
		boolean has = false;
		//1、校验是否有派工任务
		has = has || orderPDDao.checkDispatchTask(routeCid);
		if(!has){
			//2、检验是否有领料任务
			has = has || orderPDDao.checkGoodsTask(routeCid);
		}
		return has;
	}

	@Override
	public void inertObject(Object obj) {
		orderPDDao.insertObject(obj);
	}

	@Override
	public WmProduceorderC findProduceOrderc(String orderCid) {
		return orderPDDao.findProduceOrderc(orderCid);
	}

	@Override
	public void clearChangeSrc(String ordercId, String routecId,String thisRouteId) {
		orderPDDao.clearChangeSrc(ordercId,routecId,thisRouteId);
	}


	@Override
	public MesWmProduceProcessroute findProduceRouteByOrderC(String thisOrdercId) {
		return orderPDDao.findProduceRouteByOrderC(thisOrdercId);
	}


	


}
