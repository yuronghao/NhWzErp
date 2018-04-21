package com.emi.wms.basedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaGoods;

public class GoodsAction extends BaseAction {

	private static final long serialVersionUID = -7096047009988156782L;
	private BasicSettingService basicSettingService;
	
	private CacheCtrlService cacheCtrlService;

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}



	/**
	 * @category 进入物品页
	 *2015 2015年12月17日下午3:06:47
	 *String
	 * 宋银海
	 */
	public String getGoods(){
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "getGoods";
	}
	
	/**
	 * @category 进入物品页，查询当前右边的物品
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getRightGoods(){
		
		try{
			int pageIndex = getPageIndex();	
			int pageSize = getPageSize();
			
			String classifyGid=getParameter("id");
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = "";
			if(CommonUtil.notNullString(keyWord)){
				condition = " and (gs.goodscode like '%"+keyWord+"%' or gs.goodsname like '%"+keyWord+"%' or gs.goodsstandard like '%"+keyWord+"%')";
			}


			String xiancunl = getParameter("xiancunl");//现存量条件 yurh -3-28 新增功能
			String conplus = "";
			if(xiancunl != null && !"".equals(xiancunl)){
				int tempxcl = Integer.parseInt(xiancunl);
				if(tempxcl == 1){
					//>0
					conplus = " and number >0 ";

				}else if(tempxcl == 2){
					//<0
					conplus = " and number <0 ";

				}else if(tempxcl == 3){
					//=0
					conplus = " and number =0 ";
				}


			}




			PageBean pageBean=basicSettingService.getGoodsPageBean(pageIndex,pageSize,classifyGid,orgId,sobId,condition,conplus);
			setRequstAttribute("id", classifyGid);
			setRequstAttribute("data", pageBean);	
			setRequstAttribute("keyWord", keyWord);
			return "getRightGoodsHelp";
		}catch(Exception e){
			e.printStackTrace();
			return "getRightGoodsHelp";
		}
	}
	
	/**
	 * @category 查询左边的物品树
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getGoodsTree(){
		return "getGoodsTree";
	}
	
	/**
	 * @category 跳转到添加物品页
	 *2015 2015年12月18日下午1:26:58
	 *String
	 *宋银海
	 */
	public String toAddGoods(){
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		return "toAddGoods";
	}
	
	
	/**
	 * @category 跳转到添加物品页
	 *2015 2015年12月18日下午1:26:58
	 *String
	 *宋银海
	 */
	public String touptGoods(){
		try{
			String gid=getParameter("gid");
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			Map aaGoodsMap=basicSettingService.getGoodsByGid(gid,orgId,sobId);
			setRequstAttribute("aaGoods", aaGoodsMap);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			return "toUptGoods";
			
		}catch(Exception e){
			e.printStackTrace();
			return "toUptGoods";
		}

	}
	
	
	/**
	 * @category 添加物品
	 *2015 2015年12月18日下午2:53:17
	 *void
	 *宋银海
	 */
	public void addGoods(){
		try{
			
			Map values=new HashMap();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			values.put("orgId", orgId);
			values.put("sobId", sobId);
			
			AaGoods aaGoods=new AaGoods();
			aaGoods.setGid(UUID.randomUUID().toString());
			aaGoods.setGoodscode(getParameter("goodsCode") );
			aaGoods.setGoodsname(getParameter("goodsName"));
			aaGoods.setGoodsstandard(getParameter("goodsStandard") );
			aaGoods.setUnitGroupGid(getParameter("goodsUnitGroup") );
			aaGoods.setGoodsunit(CommonUtil.isNullObject(getParameter("goodsUnitGid")) ? null : getParameter("goodsUnitGid") );
			aaGoods.setCstcomunitcode(CommonUtil.isNullObject(getParameter("cassComUnitGid")) ? null : getParameter("cassComUnitGid") );
			aaGoods.setCasscomunitcode(CommonUtil.isNullObject(getParameter("cassComUnitGid")) ? null : getParameter("cassComUnitGid") );
			aaGoods.setGoodsbarcode( CommonUtil.isNullObject(getParameter("goodsBarCode"))?getParameter("goodsCode"):getParameter("goodsBarCode") );
			aaGoods.setScanNum(CommonUtil.isNullObject(getParameter("scanNum"))?Integer.valueOf(0):Integer.valueOf(getParameter("scanNum")));
			aaGoods.setGoodssortuid(getParameter("goodsSortUid"));
			aaGoods.setSoulationgid( getParameter("soulationgid") );//属性方案
			aaGoods.setProcureabovescale(CommonUtil.isNullObject(getParameter("procureAboveScale"))?null:new BigDecimal(getParameter("procureAboveScale")) );//采购超订单入库比例
			aaGoods.setProduabovescale(CommonUtil.isNullObject(getParameter("produAboveScale"))?null:new BigDecimal(getParameter("produAboveScale")) );//生产超订单入库比例
			aaGoods.setValuationgid(getParameter("valuationGid"));
			aaGoods.setDirectstore(Integer.valueOf(getParameter("directStore")));
			aaGoods.setUnitGroupGid(CommonUtil.isNullObject(getParameter("goodsUnitGroupGid")) ? null : getParameter("goodsUnitGroupGid") );
			aaGoods.setOrggid(orgId);
			aaGoods.setSobgid(sobId);
			aaGoods.setCdefwarehouse(CommonUtil.isNullObject(getParameter("whUid")) ? null : getParameter("whUid"));
			aaGoods.setBinvbach(Integer.valueOf(getParameter("binvBach")));
			boolean ok=basicSettingService.addGoods(aaGoods);
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @category 修改物品
	 *2015 2015年12月18日下午2:53:17
	 *void
	 *宋银海
	 */
	public void uptGoods(){
		try{
			
			Map values=new HashMap();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			values.put("orgId", orgId);
			values.put("sobId", sobId);
			
			AaGoods aaGoods=new AaGoods();
			aaGoods.setGid(getParameter("goodsGid") );
			aaGoods.setGoodscode(getParameter("goodsCode") );
			aaGoods.setGoodsname(getParameter("goodsName"));
			aaGoods.setGoodsstandard(getParameter("goodsStandard") );
			aaGoods.setUnitGroupGid(getParameter("goodsUnitGroup") );
			aaGoods.setGoodsunit(CommonUtil.isNullObject(getParameter("goodsUnitGid")) ? null : getParameter("goodsUnitGid") );
			aaGoods.setCstcomunitcode(CommonUtil.isNullObject(getParameter("cassComUnitGid")) ? null : getParameter("cassComUnitGid") );
			aaGoods.setCasscomunitcode(CommonUtil.isNullObject(getParameter("cassComUnitGid")) ? null : getParameter("cassComUnitGid") );
			aaGoods.setGoodsbarcode( CommonUtil.isNullObject(getParameter("goodsBarCode"))?getParameter("goodsCode"):getParameter("goodsBarCode") );
			aaGoods.setScanNum(CommonUtil.isNullObject(getParameter("scanNum"))?Integer.valueOf(0):Integer.valueOf(getParameter("scanNum")));
			aaGoods.setGoodssortuid(getParameter("goodsSortUid"));
			aaGoods.setSoulationgid( getParameter("soulationgid") );//属性方案
			aaGoods.setProcureabovescale(CommonUtil.isNullObject(getParameter("procureAboveScale"))?null:new BigDecimal(getParameter("procureAboveScale")) );//采购超订单入库比例
			aaGoods.setProduabovescale(CommonUtil.isNullObject(getParameter("produAboveScale"))?null:new BigDecimal(getParameter("produAboveScale")) );//生产超订单入库比例
			aaGoods.setValuationgid(getParameter("valuationGid"));
			aaGoods.setDirectstore(Integer.valueOf(getParameter("directStore")));
			aaGoods.setUnitGroupGid(CommonUtil.isNullObject(getParameter("goodsUnitGroupGid")) ? null : getParameter("goodsUnitGroupGid") );
			aaGoods.setOrggid(orgId);
			aaGoods.setSobgid(sobId);
			aaGoods.setCdefwarehouse(CommonUtil.isNullObject(getParameter("whUid")) ? null : getParameter("whUid"));
			aaGoods.setBinvbach(Integer.valueOf(getParameter("binvBach")));
			JSONObject jobj=basicSettingService.uptGoods(aaGoods);
			
			responseWrite(jobj.toString());	
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "保存失败！");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * @category 进入物品页(帮助)
	 *2015 2015年12月17日下午3:06:47
	 *String
	 * 宋银海
	 */
	public String getGoodsHelp(){
		return "getGoodsHelp";
	}
	
	/**
	 * @category 进入物品页，查询当前右边的物品(帮助)
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getRightGoodsHelp(){
		int pageIndex = getPageIndex();	
		int pageSize = getPageSize();
		
		String classifyGid=getParameter("id");
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = "";
		if(CommonUtil.notNullString(keyWord)){
			condition = " and (gs.goodscode like '%"+keyWord+"%' or gs.goodsname like '%"+keyWord+"%' or gs.goodsstandard like '%"+keyWord+"%')";
		}

		String xiancunl = getParameter("xiancunl");//现存量条件 yurh -3-28 新增功能
		String conplus = "";
		if(xiancunl != null && !"".equals(xiancunl)){
			int tempxcl = Integer.parseInt(xiancunl);
			if(tempxcl == 1){
				//>0
				conplus = " and number >0 ";
			}else if(tempxcl == 2){
				//<0
				conplus = " and number <0 ";
			}else if(tempxcl == 3){
				//=0
				conplus = " and number =0 ";
			}
		}
		PageBean pageBean=basicSettingService.getGoodsPageBean(pageIndex, pageSize,classifyGid,orgId,sobId,condition, conplus);
		setRequstAttribute("id", classifyGid);
		setRequstAttribute("data", pageBean);	
		setRequstAttribute("keyWord", keyWord);
		setRequstAttribute("xiancunl", xiancunl);
		return "getRightGoodsHelp";
	}
	
	/**
	 * @category 查询左边的物品树(帮助)
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public String getGoodsTreeHelp(){
		return "getGoodsTreeHelp";
	}
	
	
	
	
	

}
