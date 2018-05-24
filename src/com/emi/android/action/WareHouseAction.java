package com.emi.android.action;

import java.io.IOException;
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

import com.emi.android.bean.GoodsInforRsp;
import com.emi.android.bean.GoodsOutforRsp;
import com.emi.android.bean.GoodsallocationRsp;
import com.emi.android.bean.WarehouseRsp;
import com.emi.android.bean.WmsGoodsByScan;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.common.action.BaseAction;
import com.emi.common.util.Constants;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.basedata.action.WarehouseAction;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.YmRdStyle;
import com.emi.wms.bean.wmCall;
import com.emi.wms.servicedata.service.SaleSendService;
import com.emi.wms.servicedata.service.SaleService;
import com.emi.wms.servicedata.service.WareHouseService;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.servicedata.service.WareHouseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@SuppressWarnings({"rawtypes","unused"})
public class WareHouseAction extends BaseAction
{
	private WareHouseService wareHouseService;

	public WareHouseService getWareHouseService() {
		return wareHouseService;
	}

	public void setWareHouseService(WareHouseService wareHouseService) {
		this.wareHouseService = wareHouseService;
	}

	private static final long serialVersionUID = -1812837459133545899L;

	public void addTask(){
		try {
			
			//JSONObject json = getJsonObject();
			JSONObject json = JSONObject.fromObject("{taskGid:'8cfdc406-f5bc-4c32-884c-a9ebc8cfe928',userGid:'123',status:'1',taskTypeCode:'123',pageIndex:1}");
			String taskGid = json.getString("taskGid");
			//WmsTaskDetailRsp wmsTaskDetailRsp = wareHouseService.getWmsTaskDetail(taskGid);
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			getResponse().getWriter().write(
					EmiJsonObj.fromObject(reqmap).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}

	/**
	 * @category 提交工序材料出库(数量为正数)
	 *2016 2016年4月11日上午10:34:32
	 *void
	 *宋银海
	 */
	public void addProcessMaterialOut(){
		try {
			
 			JSONObject jsonObject = getJsonObject();
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
			String billCode = wareHouseService.getBillId(Constants.TASKTYPE_CLCK);
			wareHouseService.addProcessMaterialOut(wmsTaskDetailRsp,jsonObject,billCode);
			
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	
	
	
	/**
	 * @category 出库时扫描物品、货位条码、批次等
	 *2016 2016年4月14日下午1:19:24
	 *void
	 *宋银海
	 */
	public void getOutforByBarcode(){
		try {
			JSONObject jsonObject = getJsonObject();
			GoodsOutforRsp goodsOutInforRsp=wareHouseService.getOutInforByBarcode(jsonObject);
			System.out.println(EmiJsonObj.fromObject(goodsOutInforRsp).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(goodsOutInforRsp).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 入库时扫描物品、货位条码、批次等
	 *2016 2016年4月19日上午10:56:29
	 *void
	 *宋银海 
	 */
	public void getInforByBarcode(){
		try {
			JSONObject jsonObject = getJsonObject();
			GoodsInforRsp goodsOutInforRsp=wareHouseService.getInforByBarcode(jsonObject);
			System.out.println(EmiJsonObj.fromObject(goodsOutInforRsp).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(goodsOutInforRsp).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 扫描物品条码 获得商品信息
	 *2016 2016年12月29日上午9:45:05
	 *void
	 *宋银海
	 */
	public void getGoodsByBarcode(){
		try {
			JSONObject jsonObject = getJsonObject();
			WmsGoodsByScan wmsGoodsByScan=wareHouseService.getGoodsByBarcode(jsonObject);
			System.out.println(EmiJsonObj.fromObject(wmsGoodsByScan).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(wmsGoodsByScan).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * 
	 * @category 获取仓库
	 * 2016年4月14日 下午3:28:04
	 * @author 杨峥铖
	 */
	public void getwarehouseList(){
		try {
			
			getResponse().setCharacterEncoding("UTF-8");
			List warelist = new ArrayList();
			List warehouselist = wareHouseService.getwarehouseList();
			for(int i=0;i<warehouselist.size();i++){
				WarehouseRsp warehousersp = new WarehouseRsp();
				warehousersp.setWhgid(((AaWarehouse)(warehouselist.get(i))).getGid());
				warehousersp.setWhcode(((AaWarehouse)(warehouselist.get(i))).getWhcode());
				warehousersp.setWhname(((AaWarehouse)(warehouselist.get(i))).getWhname());
				warelist.add(warehousersp);
			}
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",warelist);
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 根据物品获得物品所在的所有仓库
	 *2016 2016年9月9日下午2:14:09
	 *void
	 *宋银海
	 */
	public void getwarehouseListByGoodsCode(){
		try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			String goodsUid=jsonObject.getString("goodsUid");
			
			List<Map> warehouselist = wareHouseService.getwarehouseListByGoodsCode(goodsUid);
			
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",warehouselist);
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 根据物品和仓库获得物品所在的所有货位
	 *2016 2016年9月9日下午2:14:09
	 *void
	 *宋银海
	 */
	public void getGoodsLocationListByGoodsCode(){
		try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject json = getJsonObject();
			String whCode = json.getString("whCode");
			String goodsUid=json.getString("goodsUid");
			String searchKey = json.getString("searchKey");
			int pageIndex = json.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;	
			
			List locationlist = new ArrayList();
			
			PageBean pageBean = wareHouseService.getGoodsLocationPage(pageIndex,pageSize,whCode,searchKey,goodsUid);
			
			for(int i=0;i<pageBean.getList().size();i++){
				Map map=(Map)pageBean.getList().get(i);
				
				GoodsallocationRsp goodsallocationrsp = new GoodsallocationRsp();
				goodsallocationrsp.setLocationgid( map.get("gid").toString());
				goodsallocationrsp.setCode(map.get("code").toString());
				goodsallocationrsp.setName(map.get("name").toString());
				goodsallocationrsp.setAllocationBarCode(map.get("allocationBarCode").toString());
				locationlist.add(goodsallocationrsp);
			}
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",locationlist);
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * 
	 * @category 获取货位
	 * 2016年4月15日 上午9:08:16
	 * @author 杨峥铖
	 */
	public void getGoodsLocationList(){
		try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject json = getJsonObject();
			String whgid = json.getString("whgid");
			String searchKey = json.getString("searchKey");
			int pageIndex = json.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;	
			
			List locationlist = new ArrayList();
			
			PageBean pageBean = wareHouseService.getGoodsLocationPage(pageIndex,pageSize,whgid,searchKey);
			
			for(int i=0;i<pageBean.getList().size();i++){
				Map map=(Map)pageBean.getList().get(i);
				
				GoodsallocationRsp goodsallocationrsp = new GoodsallocationRsp();
				goodsallocationrsp.setLocationgid( map.get("gid").toString());
				goodsallocationrsp.setCode(map.get("code").toString());
				goodsallocationrsp.setName(map.get("name").toString());
				goodsallocationrsp.setAllocationBarCode(map.get("allocationBarCode").toString());
				locationlist.add(goodsallocationrsp);
			}
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",locationlist);
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * 
	 * @category 采购入库 数量为+ （退库数量为-）
	 * 2016年4月15日 下午1:34:48
	 * @author 杨峥铖
	 */
	public void addprocurehouse(){
        try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
			
			String billcode = wareHouseService.getBillId(Constants.TASKTYPE_CGRK);
			wareHouseService.addprocurehouse(wmsTaskDetailRsp,jsonObject,billcode);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	/**
	 * @category  销售出库数量为+（退库数量为-）
	 * 2016年4月21日 上午10:01:15 
	 * @author Nixer wujinbo
	 */
	public void addSaleOut(){
		try{
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
			wareHouseService.subSaleOut(wmsTaskDetailRsp,jsonObject);
			this.writeSuccess();
		}catch(Exception e){
			e.printStackTrace();
			this.writeError();
		
		}
		
		
	}
	/**
	 * 
	 * @category 质检入库
	 * 2016年4月15日 下午1:34:48
	 * @author 杨峥铖
	 */
//	public void addcheckbill(){
//        try {
//			
//			getResponse().setCharacterEncoding("UTF-8");
//			JSONObject jsonObject = getJsonObject();
//			
//			Map<String, Class> classMap = new HashMap<String, Class>();
//			classMap.put("wmsGoodsLists", WmsGoods.class);
//			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
//			
//			wareHouseService.addcheckbill(wmsTaskDetailRsp);
//			
//			this.writeSuccess();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.writeError();
//		}
//		
//	}
	
	
	/**
	 * @category 成品入库（提交正数）
	 *2016 2016年5月11日下午2:15:18
	 *void
	 *宋银海
	 */
	public void addProductionWarehouse(){
        try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
			
			String billCode=wareHouseService.getBillId(Constants.TASKTYPE_CPRK);
			
			wareHouseService.addProductionWarehouse(wmsTaskDetailRsp,jsonObject,billCode);
			this.writeSuccess();
			
		} catch (Exception e) {
			
			if(e instanceof DuplicateKeyException){
				this.writeErrorOrSuccess(0, "禁止重复插入");
            }else{
    			e.printStackTrace();
    			this.writeError();
            }
			
			
		}
		
	}
	
	/**
	 *@category 生产入库 直接入库（无上游单据）
	 *2016 2016年12月29日下午2:28:37
	 *void
	 *宋银海
	 */
	public void addProductionWarehouseDirect(){
        try {
			
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp=(WmsTaskDetailRsp)JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class,classMap);
			
			wareHouseService.addProductionWarehouseDirect(wmsTaskDetailRsp,jsonObject);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	
	/**
	 * 获得出入库类别
	 *2015 2015年9月10日上午10:08:18
	 *void
	 *宋银海
	 */
	public void getRdStyle(){
		try {
			JSONObject jboj=getJsonObject();
			String flag=jboj.getString("irdFlag");
			String tasktype=jboj.getString("tasktype");
			String condition=" and iRdFlag='"+flag+"'and brdend='1' ";
			List<YmRdStyle> result=wareHouseService.getRdstyleEntity(condition,tasktype);
			System.out.println(JSONArray.fromObject(result).toString());
			getResponse().getWriter().print(JSONArray.fromObject(result).toString());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @category  获取用户可操作仓库  falg=user/获取仓库列表flag=all
	 * 2016年5月11日 下午4:44:05 
	 * @author Nixer wujinbo
	 */
	public void getWareHouse(){
		
		try {
			JSONObject jboj=getJsonObject();
			String flag=jboj.getString("flag");
			String userId=jboj.getString("userGid");
			String findtype=jboj.getString("findtype");
			Map reqmap=wareHouseService.getUserWareHouse(userId,flag,findtype);
			reqmap.put("success", "1");
			reqmap.put("failInfor", "");
			getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 获取仓库货物信息
	 * 2016年5月12日 上午8:37:51 
	 * @author Nixer wujinbo
	 */
	public  void getWareHouseAllocation(){
		try{
		JSONObject jboj=getJsonObject();
		String barcode=jboj.getString("barcode");// 物品条码
		
		String allocationBarcode="";
		if(jboj.containsKey("allocationBarcode")){
			allocationBarcode=jboj.getString("allocationBarcode");// 货位条码
		}
		
//		String allocationcode="";
//		if(jboj.containsKey("allocationcode")){
//			allocationcode=jboj.getString("allocationcode");// 货位编码
//		}
		
		String whCode=jboj.getString("whCode");//仓库编码
		Map reqmap=wareHouseService.getWareHouseAllocation(barcode,whCode,allocationBarcode);
		
		System.out.println(JSONObject.fromObject(reqmap).toString());
		getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
	} catch (Exception e) {
		this.writeError();
		e.printStackTrace();
	}
	}
	
	
	/**
	 * 仓库扫描条码
	 *2016 2016年10月31日下午2:19:47
	 *void
	 *
	 */
	public void getWareHouseByBarcode(){
		try{
			JSONObject jboj=getJsonObject();
			String barcode=jboj.getString("barcode");// 仓库编号
			
			Map reqmap=wareHouseService.getWareHouseByBarcode(barcode);
			
//			System.out.println(JSONObject.fromObject(reqmap).toString());
			getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 提交调拨单 
	 * 2016年5月12日 上午9:26:05 
	 * @author Nixer wujinbo
	 */
	public void subCallOrder(){
		
		try{
			JSONObject jboj=getJsonObject();
			String userId=jboj.getString("userId");
			String sobGid=jboj.getString("sobGid");
			String outwid=jboj.getString("outwid");
			String inwid=jboj.getString("inwid");
			String orgGid=jboj.getString("orgGid");// //
			String note=jboj.getString("note");
			String rdStyle=jboj.getString("rdStyle");
			JSONArray jsa=jboj.getJSONArray("data");
			wareHouseService.subCallOrder(inwid,outwid,orgGid,sobGid,jsa,userId,note,rdStyle);
			//TASKTYPE_DBD
			Map reqmap=new HashMap();
			reqmap.put("success", "1");
			reqmap.put("failInfor", "");
			getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
		
	}
	
	
	/** 
	 * @category  调拨单列表
	 * 2016年5月12日 上午10:45:48 
	 * @author Nixer wujinbo
	 */
	public void getWmCallList(){
		try{
		JSONObject jboj=getJsonObject();
		String userId=jboj.getString("userId");
		String whBarcode=jboj.getString("whBarcode");
		int pageIndex=jboj.getInt("pageIndex");
		Map reqmap=wareHouseService.getWmCallList(userId,pageIndex,whBarcode);
		reqmap.put("success", "1");
		reqmap.put("failInfor", "");
//		System.out.println(EmiJsonObj.fromObject(reqmap).toString());
		getResponse().getWriter().print(EmiJsonObj.fromObject(reqmap).toString());
		
	} catch (Exception e) {
		this.writeError();
		e.printStackTrace();
	}
	}

	
	/** 
	 * @category  调拨单详情
	 * 2016年5月12日 上午10:45:48 
	 * @author Nixer wujinbo
	 */
	public void getWmCallDetail(){
		try{
		JSONObject jboj=getJsonObject();
		String gid=jboj.getString("gid");
		
		List<Map> wmclist=wareHouseService.getWmCallDetail(gid);
		Map reqmap=new HashMap();
		reqmap.put("data", wmclist);
		reqmap.put("success", "1");
		reqmap.put("failInfor", "");
		getResponse().getWriter().print(EmiJsonObj.fromObject(reqmap).toString());
	} catch (Exception e) {
		this.writeError();
		e.printStackTrace();
	}
		
	}
	
	/**
	 * @category 提交调拨入库单
	 * 2016年5月13日 上午10:58:24 
	 * @author Nixer wujinbo
	 */
	public void subCallOrderReshep(){
		
		try{
			JSONObject jboj=getJsonObject();
			//String isend=jboj.getString("isend");//是否已完结
			String billUid=jboj.getString("billUid");//调拨单id
			String userId=jboj.getString("userId");//调拨单id
			String sobGid=jboj.getString("sobGid");
			String orgGid=jboj.getString("orgGid");// //
			String note=jboj.getString("note");
			JSONArray jsa=jboj.getJSONArray("data");
			wareHouseService.subCallOrderReshep(jsa,null,billUid,userId,note,orgGid,sobGid,jboj);
			Map reqmap=new HashMap();
			reqmap.put("success", "1");
			reqmap.put("failInfor", "");
			getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * @category 提交委外材料出库（数量为正数）
	 * 2016年5月17日 下午2:34:56 
	 * @author Nixer wujinbo
	 */
	  public void subOMmain(){
		
			try{
				JSONObject jboj=getJsonObject();
				String billcode = wareHouseService.getBillId(Constants.TASKTYPE_CLCK);// 委外材料出库
				wareHouseService.subOMmain(jboj,billcode);
				Map reqmap=new HashMap();
				reqmap.put("success", "1");
				reqmap.put("failInfor", "");
				getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
			} catch (Exception e) {
				this.writeError();
				e.printStackTrace();
			}
		
	  }


	  /**
	  * @Desc 材料出库
	  * @author yurh
	  * @create 2018-03-12 19:22:22
	  **/
	public void subMaterialOut(){

		try{
			JSONObject jboj=getJsonObject();
			String billcode = wareHouseService.getBillId(Constants.TASKTYPE_CLCK);// 材料出库
			wareHouseService.subMaterialOut(jboj,billcode);
			Map reqmap=new HashMap();
			reqmap.put("success", "1");
			reqmap.put("failInfor", "");
			getResponse().getWriter().print(JSONObject.fromObject(reqmap).toString());
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}

	}
	  
	  
	  /**
	   * @category  提交委外入库
	   * 2016年5月19日 上午10:47:37 
	   * @author Nixer wujinbo
	   */
	  public void subOMproductIn(){
		  try {
				
				getResponse().setCharacterEncoding("UTF-8");
				JSONObject jsonObject = getJsonObject();
				String billcode = wareHouseService.getBillId(Constants.TASKTYPE_CGRK);
				wareHouseService.subOMproductIn(jsonObject,billcode);
				this.writeSuccess();
				
			} catch (Exception e) {
				e.printStackTrace();
				this.writeError();
			}
	  }


	  /**
	  * @Desc 其他入库
	  * @author yurh
	  * @create 2018-03-12 20:16:59
	  **/
	public void subOtherIn(){
		try {

			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			String billcode = wareHouseService.getBillId(Constants.TASKTYPE_QTRK);
			wareHouseService.subOtherIn(jsonObject,billcode);
			this.writeSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}


	/**
	* @Desc 其他出库
	* @author yurh
	* @create 2018-03-12 21:15:46
	**/
	public void subOtherOut(){
		try {

			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			String billcode = wareHouseService.getBillId(Constants.TASKTYPE_QTCK);
			wareHouseService.subOtherOut(jsonObject,billcode);
			this.writeSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}

	}


	  
	  /**
	   * @category 退料详情
	   * 2016年5月24日 上午9:07:41 
	   * @author zhuxiaochen
	   */
	  public void backMaterialDetail(){
		try {
			getResponse().setCharacterEncoding("UTF-8");
			JSONObject jsonObject = getJsonObject();
			String billcode = jsonObject.getString("billcode");

			List<WmsGoods> wmsGoodsLists = wareHouseService.materialDetail(billcode);
			Map map = new HashMap();
			map.put("success", "1");
			map.put("wmsGoodsLists", wmsGoodsLists);
			System.out.println(EmiJsonObj.fromObject(map).toString());
			this.responseWrite(EmiJsonObj.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	  }
	
	  /**
	   * @category 提交退料
	   * 2016年5月24日 上午9:07:41 
	   * @author zhuxiaochen
	   */
	public void subBackMaterial() {
		try {
			JSONObject jsonObject = getJsonObject();
//			JSONObject jsonObject = JSONObject.fromObject("{'wmsGoodsLists':[{'batch':'8899','cfree':[{'colName':'cfree1','isShow':1,'name':'工序','value':''}],'checkCuid':'','gid':'','goodsAllocationUid':'5B677803-19D7-4555-ABD8-532E6AD993FB','goodsBarCode':'N456791','goodsCode':'N456791','goodsName':'N456791','goodsStandard':'','goodsUid':'1048C362-E9C3-41E2-8C30-7505E0FE7C3B','goodsUnitAssistName':'KG','goodsUnitMainName':'袋','invBatch':1,'materialOutCuid':'8095faa8-ed1a-48a3-b228-539bb40ed3cf','omMaterialsUid':'','processId':'7C04CFF9-6E91-42BF-AB05-16E2BBA43B90','processName':'清洗','procureArrivalCuid':'','produceRouteCGoodsUid':'','produceRouteCUid':'','remainNum':10,'remainQuantity':0,'saleSendCuid':'','submitNum':1,'submitQuantity':0,'whCode':''}],'success':'1'}");
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("wmsGoodsLists", WmsGoods.class);
			classMap.put("cfree", WmsGoodsCfree.class);
			WmsTaskDetailRsp wmsTaskDetailRsp = (WmsTaskDetailRsp) JSONObject.toBean(jsonObject, WmsTaskDetailRsp.class, classMap);

			wareHouseService.addProcessMaterialBack(wmsTaskDetailRsp, jsonObject);

			this.writeSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
}
