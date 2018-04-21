package com.emi.wms.processDesign.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.HttpRequester;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.YmUser;
import com.emi.wms.processDesign.bean.BasPart;
import com.emi.wms.processDesign.bean.BomBomReq;
import com.emi.wms.processDesign.bean.BomOpcomponentReq;
import com.emi.wms.processDesign.bean.BomParentReq;
import com.emi.wms.processDesign.bean.StockRouteC;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BasePDUtil {
	/**
	 * 根据末道工序推算工序应完成数量、应领料数量
	 */
	public static void genNumber(List<String> lastRouteCIds, JSONObject objs_json,String produceNumber) {
		//1、设置应完工数量，需要根据末道往前推算
		for(String lastId : lastRouteCIds){
			JSONObject obj = objs_json.getJSONObject(lastId);
			JSONObject base = obj.getJSONObject("base");
			//末道的应完工数量
			base.put("number", produceNumber);
			JSONArray attrPreProc = obj.get("attrPreProc")==null? new JSONArray(): obj.getJSONArray("attrPreProc");
			//调用递归方法
			setRouteCNumber(objs_json, attrPreProc, base);
		}
		//2、设置物料应领数量     =  应完成数量   X 物料标准用量（基本用量/基础数量）
		Iterator it = objs_json.keys();  
        while (it.hasNext()) {  
            String id = (String) it.next();
            JSONObject obj = objs_json.getJSONObject(id);
            JSONObject base = obj.getJSONObject("base");
            JSONArray attrGoods = obj.get("attrGoods")==null? new JSONArray(): obj.getJSONArray("attrGoods");
            for(Object attr : attrGoods){
    			JSONObject goods = (JSONObject) attr;
    			String gBaseUse = CommonUtil.isNullObject(goods.get("baseUse"))?"1": goods.getString("baseUse");
    			String gBaseQuantity = CommonUtil.isNullObject(goods.get("baseQuantity"))?"1": goods.getString("baseQuantity");
    			goods.put("number",new BigDecimal(base.get("number")==null?"1":base.getString("number")).multiply(new BigDecimal(gBaseUse)).divide(new BigDecimal(gBaseQuantity),6,BigDecimal.ROUND_HALF_UP));
    		}
        }
		
	}
	

	
	/*
	 * 设置应完工数量
	 */
	private static void setRouteCNumber(JSONObject objs_json,JSONArray attrPreProc,JSONObject base){
		for(Object attr : attrPreProc){
			JSONObject pre = (JSONObject) attr;
			String preId = pre.getString("routeCid");
			String preBaseUse = CommonUtil.isNullObject(pre.get("baseUse"))?"1": pre.getString("baseUse");
			String preBaseQuantity = CommonUtil.isNullObject(pre.get("baseQuantity"))?"1": pre.getString("baseQuantity");
			//设置上道的应完工数量   =  本道应完成数量   X 上道转入基本用量  / 上道转入基础数量
			JSONObject preObj = objs_json.getJSONObject(preId);
			if(preObj==null || preObj.isNullObject()){
				continue;
			}
			JSONObject preBase = preObj.getJSONObject("base");
			preBase.put("number", new BigDecimal(base.getString("number")).multiply(new BigDecimal(preBaseUse)).divide(new BigDecimal(preBaseQuantity),6,BigDecimal.ROUND_HALF_UP) );
			JSONArray pre_attrPreProc = preObj.get("attrPreProc")==null? new JSONArray(): preObj.getJSONArray("attrPreProc");
			//递归
			setRouteCNumber(objs_json, pre_attrPreProc, preBase);
		}
	}
	
	//调用保存bom接口的参数
	public static BomBomReq BomParameter(JSONObject objs_json,StockRouteC StockRouteC,List<BasPart> basPartList, CacheCtrlService cacheCtrlService,List<String> msg, MesWmProcessroute route) { 
		
//		Map parameter = new HashMap();
		BomBomReq bom_bom = new BomBomReq();
		BomParentReq bom_parent = new BomParentReq();
		List<BomOpcomponentReq> bom_opcomponent_temp = new ArrayList<BomOpcomponentReq>();
		List<BomOpcomponentReq> bom_opcomponent = new ArrayList<BomOpcomponentReq>();
		
		JSONObject obj = objs_json.getJSONObject(StockRouteC.getRoutecId());
		YmUser createUser =	cacheCtrlService.getUser(route.getCreateUser());
		YmUser modifyUser =	cacheCtrlService.getUser(route.getModifyUser());
		YmUser authUser =	cacheCtrlService.getUser(route.getAuthUser());
		if(createUser!=null){
			bom_bom.setCreateUser(createUser.getUserName());	//
		}
		if(modifyUser!=null){
			bom_bom.setModifyUser(modifyUser.getUserName());	//	
		}
		if(authUser!=null){
			bom_bom.setRelsUser(authUser.getUserName());	//
		}
		bom_bom.setBomType(1);
		bom_bom.setCreateTime(route.getEffdate());
		bom_bom.setModifyTime(route.getModifyDate());
		bom_bom.setRelsTime(route.getAuthDate());
		bom_bom.setStatus(3);
		bom_bom.setVersion(10);
		bom_bom.setVersionDesc("无");
		bom_bom.setVersionEffDate(DateUtil.stringtoDate("2000-01-01", "yyyy-MM-dd"));
		bom_bom.setVersionEndDate(DateUtil.stringtoDate("2099-12-31", "yyyy-MM-dd"));
		
		JSONObject base = obj.get("base")==null? new JSONObject(): obj.getJSONObject("base");
		//设置这边的goodsId到U8，方便匹配
		bom_bom.setLugGoodsId((CommonUtil.isNullString(base.getString("stockGoodsId"))?base.getString("productId"):base.getString("stockGoodsId"))+"," + base.getString("processName") );//
		
		AaGoods goods_cache = cacheCtrlService.getGoods(CommonUtil.Obj2String(base.get("productId")));
		boolean isInvFree = goods_cache.getCfree1()==null?false:goods_cache.getCfree1()==0?false:true;
		
		boolean invGet = false;//是否找到匹配的物料（bas_part）
		
		for(BasPart bp : basPartList){
			if(CommonUtil.null2Str(bp.getInvCode()).equals(base.get("productCode")) && (isInvFree?CommonUtil.null2Str(bp.getFree1()).equals(base.get("processName")):true) ){
				bom_parent.setParentId(bp.getPartId());	
				invGet = true;
				break;
			}
		}
		
		if(!invGet){
			return null;
		}
				
		String rootId = StockRouteC.getRoutecId();
		goodsToBomOpcomponent(StockRouteC.getRoutecId(), basPartList, bom_opcomponent_temp, objs_json,rootId, cacheCtrlService,msg);
		
		//合并同类的子件
		for(BomOpcomponentReq bot : bom_opcomponent_temp){
			boolean has = false;
			for(BomOpcomponentReq bo : bom_opcomponent){
				if(bo.getComponentId()==bot.getComponentId()){
					//合并
					bo.setBaseQtyN(bo.getBaseQtyN().add(bot.getBaseQtyN()));
					bo.setSortSeq(Math.min(bo.getSortSeq(), bot.getSortSeq()) );
					has = true;
				}
			}
			if(!has){
				bom_opcomponent.add(bot);
			}
		}
		
		bom_bom.setBomParent(bom_parent);
		bom_bom.setBomOpcomponentList(bom_opcomponent);
//		parameter.put("bom_bom", bom_bom);
		return bom_bom;
	}
	
	private static void goodsToBomOpcomponent(String routeCid,List<BasPart> basPartList,List<BomOpcomponentReq> bom_opcomponent,JSONObject objs_json,String rootId,CacheCtrlService cacheCtrlService,List<String> msg){
		int sortIndex = 1;
		
		JSONObject obj = objs_json.getJSONObject(routeCid);
		JSONObject base = obj.get("base")==null? new JSONObject(): obj.getJSONObject("base");
		
		boolean invGet = false;//是否找到匹配的物料（bas_part）
		//如果本道工序是半成品(并且不是最后一个工序)，则只存半成品作为子件
		if(base.getInt("isSemi")==1 && !base.getString("routeCid").equals(rootId)){
			BomOpcomponentReq bo = new BomOpcomponentReq();
			bo.setBaseQtyN(base.get("number")==null?new BigDecimal(1):new BigDecimal(base.getString("number")));	//分子
			bo.setBaseQtyD(new BigDecimal(1));	//分母
			//查询物品是否启用自由项
			AaGoods goods_cache = cacheCtrlService.getGoods(CommonUtil.Obj2String(base.get("productId")));
			if(goods_cache==null){
				msg.add("异常：未从缓存查到物料信息，物料id【"+CommonUtil.Obj2String(base.get("productId"))+"】");
				return;
			}
			boolean isInvFree = goods_cache.getCfree1()==null?false:goods_cache.getCfree1()==0?false:true;
			
			for(BasPart bp : basPartList){
				if(CommonUtil.null2Str(bp.getInvCode()).equals(base.get("productCode")) && (isInvFree?CommonUtil.null2Str(bp.getFree1()).equals(base.get("processName")):true) ){
					bo.setComponentId(bp.getPartId());
					invGet = true;
					break;
				}
			}
			if(invGet){
				bo.setEffBegDate(DateUtil.stringtoDate("2000-01-01", "yyyy-MM-dd"));
				bo.setEffEndDate(DateUtil.stringtoDate("2099-12-31", "yyyy-MM-dd"));
				bo.setOpSeq("0000");	
				bo.setProductType(1);
				bo.setSortSeq(Integer.parseInt( CommonUtil.Obj2String(base.get("processIndex")).equals("")?"0":base.getString("processIndex")));//sortIndex*10
				sortIndex ++;
				bom_opcomponent.add(bo);
			}else{
				if(isInvFree){
					msg.add("产品编码【"+base.get("productCode")+"】-工序【"+base.get("processName")+"】-物料【"+goods_cache.getGoodsname()+"】档案中缺少自由项【"+base.get("processName")+"】");
				}else{
					msg.add("未匹配到物料【"+goods_cache.getGoodsname()+"】");
				}
			}
		}else{
			JSONArray goodsArray = obj.get("attrGoods")==null? new JSONArray(): obj.getJSONArray("attrGoods");
			for(Object o : goodsArray){
				JSONObject goods = (JSONObject) o; 
				
				BomOpcomponentReq bo = new BomOpcomponentReq();
				bo.setBaseQtyN(goods.get("number")==null?new BigDecimal(1):new BigDecimal(goods.getString("number")));	//分子
				bo.setBaseQtyD(new BigDecimal(1));	//分母
				
				//查询物品是否启用自由项
				AaGoods goods_cache = cacheCtrlService.getGoods(CommonUtil.Obj2String(goods.get("goodsId")));
				if(goods_cache==null){
					msg.add("异常：未从缓存查到物料信息，物料id【"+CommonUtil.Obj2String(goods.get("goodsId"))+"】");
					return;
				}
				boolean isInvFree = goods_cache.getCfree1()==null?false:goods_cache.getCfree1()==0?false:true;
				
				for(BasPart bp : basPartList){
					if(CommonUtil.null2Str(bp.getInvCode()).equals(goods.get("goodscode")) && (isInvFree?CommonUtil.null2Str(bp.getFree1()).equals(goods.get("free1")):true)){
						bo.setComponentId(bp.getPartId());
						invGet = true;
						break;
					}
				}
				if(invGet){
					bo.setEffBegDate(DateUtil.stringtoDate("2000-01-01", "yyyy-MM-dd"));
					bo.setEffEndDate(DateUtil.stringtoDate("2099-12-31", "yyyy-MM-dd"));
					bo.setOpSeq("0000");	
					bo.setProductType(1);
					bo.setSortSeq(Integer.parseInt( CommonUtil.Obj2String(base.get("processIndex")).equals("")?"1":base.getString("processIndex")));//sortIndex*10
					sortIndex ++;
					bom_opcomponent.add(bo);
				}else{
					if(isInvFree){
						msg.add("产品编码【"+base.get("productCode")+"】-工序【"+base.get("processName")+"】-物料【"+goods_cache.getGoodsname()+"】档案中缺少自由项【"+goods.get("free1")+"】");
					}else{
						msg.add("未匹配到物料【"+goods_cache.getGoodsname()+"】");
					}
				}
				
			}
			//递归  获取之前的物料
			String thisId = base.getString("routeCid");
			JSONArray preProc = ((JSONObject)objs_json.get(thisId)).get("attrPreProc")==null?new JSONArray() : ((JSONObject)objs_json.get(thisId)).getJSONArray("attrPreProc");
			for(Object p : preProc){
				JSONObject preP = (JSONObject) p;
				goodsToBomOpcomponent(preP.getString("routeCid"), basPartList, bom_opcomponent, objs_json,rootId,cacheCtrlService,msg);
			}
		}
		
	}
}
