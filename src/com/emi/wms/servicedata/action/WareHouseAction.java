package com.emi.wms.servicedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;


import com.alibaba.fastjson.JSON;
import com.emi.wms.bean.*;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.servicedata.service.WareHouseService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class WareHouseAction extends BaseAction{

	private static final long serialVersionUID = -6298081992262909980L;
	private WareHouseService wareHouseService;
	private CacheCtrlService cacheCtrlService;
	private BasicSettingService basicSettingService;
	public BasicSettingService getBasicSettingService() {
		return basicSettingService;
	}

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public WareHouseService getWareHouseService() {
		return wareHouseService;
	}

	public void setWareHouseService(WareHouseService wareHouseService) {
		this.wareHouseService = wareHouseService;
	}

	/**
	 * @category 跳转到其他入库单增加页面
	 *2016 2016年7月4日下午1:31:42
	 *String
	 *宋银海
	 */
	public String toAddOtherWarehouse(){
		try{
			String otherWarehousegid = getParameter("otherWarehousegid");
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			Map otherWarehouse = wareHouseService.findOtherWarehouse(otherWarehousegid,orgId,sobId);
			if(!CommonUtil.isNullObject(otherWarehouse)){
				if(!CommonUtil.isNullObject(otherWarehouse.get("depUid"))){
					AaDepartment department = cacheCtrlService.getDepartment(otherWarehouse.get("depUid").toString());
					setRequstAttribute("department", department);
				}
				if(!CommonUtil.isNullObject(otherWarehouse.get("whUid"))){
					AaWarehouse warehouse = cacheCtrlService.getWareHouse(otherWarehouse.get("whUid").toString());
					setRequstAttribute("warehouse", warehouse);
				}
				if(!CommonUtil.isNullObject(otherWarehouse.get("recordPersonId"))){
					AaPerson aaperson = cacheCtrlService.getPerson(otherWarehouse.get("recordPersonId").toString());
					setRequstAttribute("aaperson", aaperson);
				}
				if(!CommonUtil.isNullObject(otherWarehouse.get("gid"))){
					List otherWarehouseC = wareHouseService.getOtherWarehouseClist(otherWarehouse.get("gid").toString());
					for(int i=0;i<otherWarehouseC.size();i++){
						AaGoods good = cacheCtrlService.getGoods(String.valueOf(((Map)otherWarehouseC.get(i)).get("goodsUid")));
						((Map)otherWarehouseC.get(i)).put("good", good);
						AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(String.valueOf(((Map)otherWarehouseC.get(i)).get("goodsAllocationUid")));
						if(alocation != null){
							((Map)otherWarehouseC.get(i)).put("goodsAllocationName", alocation.getName());
						}

					}
					setRequstAttribute("otherWarehouseC", otherWarehouseC);
				}
			}
			String time = DateUtil.dateToString(new Date(), "yyMMdd");
			setRequstAttribute("time", time);
			setRequstAttribute("otherWarehouse", otherWarehouse);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			return "toAddOtherWarehouse";
		}catch(Exception e){
			return "toAddOtherWarehouse";
		}
	}





	/**
	* @Desc  其他出库单
	* @author yurh
	* @create 2018-03-13 21:12:14
	**/
	public String toAddOthersOut(){
		try{
			String otherOutgid = getParameter("otherOutgid");
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			Map otherOut = wareHouseService.findOtherOut(otherOutgid,orgId,sobId);
			if(!CommonUtil.isNullObject(otherOut)){
				if(!CommonUtil.isNullObject(otherOut.get("departmentUid"))){
					AaDepartment department = cacheCtrlService.getDepartment(otherOut.get("departmentUid").toString());
					setRequstAttribute("department", department);
				}
				if(!CommonUtil.isNullObject(otherOut.get("warehouseUid"))){
					AaWarehouse warehouse = cacheCtrlService.getWareHouse(otherOut.get("warehouseUid").toString());
					setRequstAttribute("warehouse", warehouse);
				}
				if(!CommonUtil.isNullObject(otherOut.get("recordPersonUid"))){
					AaPerson aaperson = cacheCtrlService.getPerson(otherOut.get("recordPersonUid").toString());
					setRequstAttribute("aaperson", aaperson);
				}
				if(!CommonUtil.isNullObject(otherOut.get("gid"))){
					List othersOutC = wareHouseService.getOthersOutClist(otherOut.get("gid").toString());
					for(int i=0;i<othersOutC.size();i++){
						AaGoods good = cacheCtrlService.getGoods(String.valueOf(((Map)othersOutC.get(i)).get("goodsUid")));
						((Map)othersOutC.get(i)).put("good", good);
						AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(String.valueOf(((Map)othersOutC.get(i)).get("goodsAllocationUid")));
						if(alocation != null){
							((Map)othersOutC.get(i)).put("goodsAllocationName", alocation.getName());
						}

					}
					setRequstAttribute("othersOutC", othersOutC);
				}
			}
			String time = DateUtil.dateToString(new Date(), "yyMMdd");
			setRequstAttribute("time", time);
			setRequstAttribute("otherOut", otherOut);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			return "toAddOthersOut";
		}catch(Exception e){
			return "toAddOthersOut";
		}
	}






	/**
	 * 添加其他入库
	 * @category
	 * 2016年7月6日 下午3:16:37
	 * @author 杨胜
	 */
	public void addOtherWarehouse(){
		try{
			
			//添加其他入库 主表 子表
			String badge=getParameter("badge");
			String wmohuid=UUID.randomUUID().toString();     //主其他入库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			WmOtherwarehouse wmoh=new WmOtherwarehouse();   			
		    wmoh.setDocumentTypeId("24AD0F1F-6D94-4EE1-8728-896472A3E0C6");
		    wmoh.setGid(wmohuid);
			wmoh.setWhUid(whUid);
			wmoh.setDepUid(depUid);
			wmoh.setNotes(getParameter("notes"));
			wmoh.setBillState("0");
			wmoh.setBillDate(new Date());
			wmoh.setRecordDate(new Date());
			wmoh.setRecordPersonId(getParameter("recordPersonUid"));
			wmoh.setSobGid(getSession().get("SobId").toString());
			wmoh.setOrgGid(getSession().get("OrgId").toString());
//			wmoh.setBadge(Integer.parseInt(badge));
			wmoh.setBillCode(billCode);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmOtherwarehouseC> wmohclist=new ArrayList<WmOtherwarehouseC>();                     //其他入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
//				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
					WmOtherwarehouseC wmohc=new WmOtherwarehouseC();//其它入库子表
					wmohc.setGid(UUID.randomUUID().toString());
//					wmohc.setNotes(note[i]);
					wmohc.setOtherInUid(wmohuid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
					wmohc.setBarCode(billCode+(i+1));
					/*wmohc.setCallCuid(wmcc.getGid());*/
					wmohc.setGoodsAllocationUid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
//					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
//					}
					
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
					wmcat.setBatch(batch[i]);
					}
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]));
//				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
//					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]));
//			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
//			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(wmohc);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.addOtherWarehouse(wmoh, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}





	/**
	* @Desc 添加其他出库
	* @author yurh
	* @create 2018-03-19 10:18:50
	**/
	public void addOthersOut(){
		try{

			//添加其他出库 主表 子表
//			String badge=getParameter("badge");
			String wmohuid=UUID.randomUUID().toString();     //主其他出库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			WmOthersout wmoh = new WmOthersout();
//			WmOtherwarehouse wmoh=new WmOtherwarehouse();
			wmoh.setDocumentTypeUid("24AD0F1F-6D94-4EE1-8728-896472A3E0C6");
			wmoh.setGid(wmohuid);
			wmoh.setWarehouseUid(whUid);
			wmoh.setDepartmentUid(depUid);
			wmoh.setNotes(getParameter("notes"));
			wmoh.setBillState("0");
			wmoh.setBillDate(new Date());
			wmoh.setRecordDate(new Date());
			wmoh.setRecordPersonUid(getParameter("recordPersonUid"));
			wmoh.setSobGid(getSession().get("SobId").toString());
			wmoh.setOrgGid(getSession().get("OrgId").toString());
//			wmoh.setBadge(Integer.parseInt(badge));
			wmoh.setBillCode(billCode);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmOthersoutC> wmohclist=new ArrayList<WmOthersoutC>();                     //其他出库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
//				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
					WmOthersoutC wmohc=new WmOthersoutC();//其它出库子表
					wmohc.setGid(UUID.randomUUID().toString());
//					wmohc.setNotes(note[i]);
					wmohc.setOthersOutUid(wmohuid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
//					wmohc.setBarCode(billCode+(i+1));
					/*wmohc.setCallCuid(wmcc.getGid());*/
					wmohc.setGoodsAllocationUid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
//					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
//					}

					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
						wmcat.setBatch(batch[i]);
					}
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());//相反
//				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
//					 }
					wmcat.setOrggid(getSession().get("OrgId").toString());
					wmcat.setSobgid(getSession().get("SobId").toString());
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
						WmBatch wmb=new WmBatch();
						wmb.setGid(UUID.randomUUID().toString());
						wmb.setGoodsUid(goodsUid[i]);
						wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
						wmb.setBatch(CommonUtil.Obj2String(batch[i]));
						wmb.setNumber(new BigDecimal(mainNumber[i]));
//			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
//			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
						wmb.setRecordDate(new Timestamp(new Date().getTime()));
						wmBatchs.add(wmb);
					}
					wmohclist.add(wmohc);
					asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.addOthersOut(wmoh, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());

		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}






	/**
	 * 修改其他入库
	 * @category
	 * 2016年7月6日 下午3:16:37
	 * @author 杨胜
	 */
	public void updateOtherWarehouse(){
		try{
			//添加其他入库 主表 子表
//			String badge=getParameter("badge");
			//String wmohuid=UUID.randomUUID().toString();     //主其他入库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			String otherWarehousegid=getParameter("otherWarehousegid"); 
			WmOtherwarehouse wmoh=new WmOtherwarehouse();   			
		    wmoh.setGid(otherWarehousegid);
			wmoh.setWhUid(whUid);
			wmoh.setDepUid(depUid);
			wmoh.setRecordDate(new Date());
//			wmoh.setNotes(getParameter("notes"));
			wmoh.setRecordPersonId(getParameter("recordPersonUid"));
			wmoh.setBillCode(billCode);
//			wmoh.setBadge(Integer.parseInt(badge));
			List otherC = wareHouseService.getOtherWarehouseClist(otherWarehousegid);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmOtherwarehouseC> wmohclist=new ArrayList<WmOtherwarehouseC>();                     //其他入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
//				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
//				String[] note = getRequest().getParameterValues("note");	// 备注
//				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
					WmOtherwarehouseC wmohc=new WmOtherwarehouseC();//其它入库子表
					wmohc.setGid(UUID.randomUUID().toString());
//					wmohc.setNotes(note[i]);
					wmohc.setOtherInUid(otherWarehousegid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
//					if(!CommonUtil.isNullObject(barCode[i])){
//						wmohc.setBarCode(barCode[i]);
//					}
//					else
//					{
//						wmohc.setBarCode(billCode+(i+1+otherC.size()));
//					}
					/*wmohc.setCallCuid(wmcc.getGid());*/
					wmohc.setGoodsAllocationUid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
//					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
//					}
					
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]));
//				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
//					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]));
//			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
//			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(wmohc);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.updateOtherWarehouse(wmoh, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}





	/**
	* @Desc 修改其他出库
	* @author yurh
	* @create 2018-03-19 10:55:11
	**/
	public void updateOthersOut(){
		try{
			//添加其他入库 主表 子表
//			String badge=getParameter("badge");
			//String wmohuid=UUID.randomUUID().toString();     //主其他入库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			String othersOutgid=getParameter("othersOutgid");
			WmOthersout wmoh=new WmOthersout();
			wmoh.setGid(othersOutgid);
			wmoh.setWarehouseUid(whUid);
			wmoh.setDepartmentUid(depUid);
			wmoh.setRecordDate(new Date());
//			wmoh.setNotes(getParameter("notes"));
			wmoh.setRecordPersonUid(getParameter("recordPersonUid"));
			wmoh.setBillCode(billCode);
//			wmoh.setBadge(Integer.parseInt(badge));
			List otherC = wareHouseService.getOthersOutClist(othersOutgid);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmOthersoutC> wmohclist=new ArrayList<WmOthersoutC>();                     //其他出库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
//				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
//				String[] note = getRequest().getParameterValues("note");	// 备注
//				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
					WmOthersoutC wmohc=new WmOthersoutC();//其它出库子表
					wmohc.setGid(UUID.randomUUID().toString());
//					wmohc.setNotes(note[i]);
					wmohc.setOthersOutUid(othersOutgid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
					wmohc.setGoodsAllocationUid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
//					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
//					}

					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());
//				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
//					 }
					wmcat.setOrggid(getSession().get("OrgId").toString());
					wmcat.setSobgid(getSession().get("SobId").toString());
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
						WmBatch wmb=new WmBatch();
						wmb.setGid(UUID.randomUUID().toString());
						wmb.setGoodsUid(goodsUid[i]);
						wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
						wmb.setBatch(CommonUtil.Obj2String(batch[i]));
						wmb.setNumber(new BigDecimal(mainNumber[i]));
//			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){
//			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
//			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
						wmb.setRecordDate(new Timestamp(new Date().getTime()));
						wmBatchs.add(wmb);
					}
					wmohclist.add(wmohc);
					asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.updateOthersOut(wmoh, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	/**
	 * @category 进入货位页，根据仓库号查询货位
	 */
	public String getGoodsAllocationHelp(){
		
		int pageIndex = getPageIndex();	
		int pageSize = getPageSize();
		String keyWord=getParameter("keyWord");
		String whUid=getParameter("id");
		String orgId=getSession().get("OrgId").toString();//组织id
		String sobId=getSession().get("SobId").toString();//账套id
		StringBuffer sbf=new StringBuffer();
		if(!CommonUtil.isNullObject(keyWord)){
			sbf.append(" and (code='"+keyWord+"' or name like'%"+keyWord+"%')");
		}
		if(!CommonUtil.isNullObject(whUid)){
			sbf.append(" and whUid='"+whUid+"' and orgGid='"+orgId+"' and sobGid='"+sobId+"'");
		}
		String condition=sbf.toString();
		PageBean pageBean=basicSettingService.getGoodsAllocationPageBean(pageIndex, pageSize,condition);
		setRequstAttribute("keyWord", keyWord);
		setRequstAttribute("id", whUid);
		setRequstAttribute("data", pageBean);		
		return "getGoodsAllocationHelp";
	}
/**
 * 删除
 * @category
 * 2016年7月14日 上午8:54:33
 * @author 杨胜
 */
	public void deleteOtherWarehouse(){
			try {
				String gid = getParameter("gid");
				WmOtherwarehouse wmoh=new WmOtherwarehouse();
				wmoh.setGid(gid);
				wmoh.setSobGid(getSession().get("SobId").toString());
				wmoh.setOrgGid(getSession().get("OrgId").toString());
				JSONObject jobj=wareHouseService.deleteOtherWarehouse(wmoh);
				getResponse().getWriter().write(jobj.toString());
				
			} catch (Exception e) {
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}

		/** 删除其他出库单
		* @Desc
		* @author yurh
		* @create 2018-03-19 13:14:08
		**/
	public void deleteOthersOut(){
		try {
			String gid = getParameter("gid");
			WmOthersout wmoh=new WmOthersout();
			wmoh.setGid(gid);
			wmoh.setSobGid(getSession().get("SobId").toString());
			wmoh.setOrgGid(getSession().get("OrgId").toString());
			JSONObject jobj=wareHouseService.deleteOthersOut(wmoh);
			getResponse().getWriter().write(jobj.toString());

		} catch (Exception e) {
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}


	/**
	 * @category 跳转到其他入库列表
	 *2016 2016年7月4日下午1:46:16
	 *String
	 *宋银海
	 */
	public String otherWarehouseList(){
		try{
			
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("owh.billCode", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
		if(!CommonUtil.isNullString(keyWord)){
			List<AaGoods> goods=cacheCtrlService.setGoods();
			for (AaGoods aaGoods : goods) {
				String gid="";
				if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
					gid+=aaGoods.getGid();
					condition+="or wowc.goodsUid like '%"+gid+"%'";
				}
				
			}
		}
		PageBean list = wareHouseService.getOtherWarehouseList(pageIndex, pageSize, condition);
		for(int i=0;i<list.getList().size();i++){
				if(!CommonUtil.isNullString(((WmOtherwarehouse)list.getList().get(i)).getRecordPersonId())){
				YmUser ymuser = cacheCtrlService.getUser(((WmOtherwarehouse)list.getList().get(i)).getRecordPersonId().toString());
				if(!CommonUtil.isNullObject(ymuser)){
					((WmOtherwarehouse)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
				}
			}
				if(!CommonUtil.isNullString(((WmOtherwarehouse)list.getList().get(i)).getDepUid())){
					AaDepartment department = cacheCtrlService.getDepartment(((WmOtherwarehouse)list.getList().get(i)).getDepUid().toString());
					if(!CommonUtil.isNullObject(department)){
						((WmOtherwarehouse)list.getList().get(i)).setDepartName(department.getDepname());
					}
				}
				if(!CommonUtil.isNullString(((WmOtherwarehouse)list.getList().get(i)).getWhUid())){
					AaWarehouse warehouse  = cacheCtrlService.getWareHouse(((WmOtherwarehouse)list.getList().get(i)).getWhUid().toString());
					if(!CommonUtil.isNullObject(warehouse)){
						((WmOtherwarehouse)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
					}
				}
				if (!CommonUtil.isNullString(((WmOtherwarehouse)list.getList().get(i)).getGoodsUid())) {
					AaGoods good=cacheCtrlService.getGoods(((WmOtherwarehouse)list.getList().get(i)).getGoodsUid());
					if(good!= null){
						((WmOtherwarehouse)list.getList().get(i)).setGoodsUid(good.getGoodsname());
						((WmOtherwarehouse)list.getList().get(i)).setGoodsCode(good.getGoodscode());
						Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
						if(unit != null){
							((WmOtherwarehouse)list.getList().get(i)).setUnitName(unit.getUnitname());
						}

					}

				}
		}
		setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "otherWarehouseList";
	}








	/**
	* @Desc 其他出库单列表
	* @author yurh
	* @create 2018-03-13 22:23:12
	**/
	public String othersOutList(){
		try{

			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = CommonUtil.combQuerySql("owh.billCode", keyWord);
			setRequstAttribute("keyWord",keyWord);
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
			if(!CommonUtil.isNullString(keyWord)){
				List<AaGoods> goods=cacheCtrlService.setGoods();
				for (AaGoods aaGoods : goods) {
					String gid="";
					if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
						gid+=aaGoods.getGid();
						condition+="or wowc.goodsUid like '%"+gid+"%'";
					}

				}
			}
			PageBean list = wareHouseService.getOthersOutList(pageIndex, pageSize, condition);
			for(int i=0;i<list.getList().size();i++){
				if(!CommonUtil.isNullString(((WmOthersout)list.getList().get(i)).getRecordPersonUid())){
					YmUser ymuser = cacheCtrlService.getUser(((WmOthersout)list.getList().get(i)).getRecordPersonUid());
					if(!CommonUtil.isNullObject(ymuser)){
						((WmOthersout)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
					}
				}
				if(!CommonUtil.isNullString(((WmOthersout)list.getList().get(i)).getDepartmentUid())){
					AaDepartment department = cacheCtrlService.getDepartment(String.valueOf(((WmOthersout)list.getList().get(i)).getDepartmentUid()));
					if(!CommonUtil.isNullObject(department)){
						((WmOthersout)list.getList().get(i)).setDepartName(department.getDepname());
					}
				}
				if(!CommonUtil.isNullString(((WmOthersout)list.getList().get(i)).getWarehouseUid())){
					AaWarehouse warehouse  = cacheCtrlService.getWareHouse(String.valueOf(((WmOthersout)list.getList().get(i)).getWarehouseUid()));
					if(!CommonUtil.isNullObject(warehouse)){
						((WmOthersout)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
					}
				}
				if (!CommonUtil.isNullString(((WmOthersout)list.getList().get(i)).getGoodsUid())) {
					AaGoods good=cacheCtrlService.getGoods(((WmOthersout)list.getList().get(i)).getGoodsUid());
					if(good!= null){
						((WmOthersout)list.getList().get(i)).setGoodsUid(good.getGoodsname());
						((WmOthersout)list.getList().get(i)).setGoodsCode(good.getGoodscode());
						Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
						if(unit != null){
							((WmOthersout)list.getList().get(i)).setUnitName(unit.getUnitname());
						}

					}

				}
			}
			setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "othersOutList";
	}




	//...................................................生产入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	
/**
 *  查询最后一条记录
 * @category
 * 2016年7月13日 下午3:14:52
 * @author 杨胜
 * @return
 */
	public String toAddProduceWarehouse(){
		try{
			String produceWarehousegid = getParameter("produceWarehousegid");
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			int mainNum=0;
			int assistNum=0;
			Map produceWarehouse = wareHouseService.findProduceWarehouse(produceWarehousegid,orgId,sobId);
			if(!CommonUtil.isNullObject(produceWarehouse)){
				if(!CommonUtil.isNullObject(produceWarehouse.get("departmentUid"))){
					AaDepartment department = cacheCtrlService.getDepartment(produceWarehouse.get("departmentUid").toString());
					setRequstAttribute("department", department);
				}
				if(!CommonUtil.isNullObject(produceWarehouse.get("whUid"))){
					AaWarehouse warehouse = cacheCtrlService.getWareHouse(produceWarehouse.get("whUid").toString());
					setRequstAttribute("warehouse", warehouse);
				}
				if(!CommonUtil.isNullObject(produceWarehouse.get("recordPersonId"))){
					AaPerson aaperson = cacheCtrlService.getPerson(produceWarehouse.get("recordPersonId").toString());
					setRequstAttribute("aaperson", aaperson);
				}
				if(!CommonUtil.isNullObject(produceWarehouse.get("gid"))){
					
					List produceWarehouseC = wareHouseService.getProduceWarehouseClist(produceWarehouse.get("gid").toString());
					for(int i=0;i<produceWarehouseC.size();i++){
						AaGoods good = cacheCtrlService.getGoods(((Map)produceWarehouseC.get(i)).get("goodsUid").toString());
						if (!CommonUtil.isNullObject(((Map)produceWarehouseC.get(i)).get("number"))) {
							mainNum+=Integer.valueOf(((Map)produceWarehouseC.get(i)).get("number").toString().substring(0,(((Map)produceWarehouseC.get(i)).get("number").toString()).indexOf(".")));
						}
						if (!CommonUtil.isNullObject(((Map)produceWarehouseC.get(i)).get("assistNumber"))) {
							assistNum+=Integer.valueOf(((Map)produceWarehouseC.get(i)).get("assistNumber").toString().substring(0,(((Map)produceWarehouseC.get(i)).get("assistNumber").toString()).indexOf(".")));
						}
						((Map)produceWarehouseC.get(i)).put("good", good);
						AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((Map)produceWarehouseC.get(i)).get("goodsAllocationUid").toString());
						((Map)produceWarehouseC.get(i)).put("goodsAllocationName", alocation.getName());
					}
					setRequstAttribute("produceWarehouseC", produceWarehouseC);
					
				}
			}
			setRequstAttribute("mainNum", mainNum);
			setRequstAttribute("assistNum", assistNum);
			String time = DateUtil.dateToString(new Date(), "yyMMdd");
			setRequstAttribute("time", time);
			setRequstAttribute("produceWarehouse", produceWarehouse);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "addProduceWarehouse";
	}
/**
 * 添加生产入库表
 * @category
 * 2016年7月14日 上午8:48:13
 * @author 杨胜
 */
	public void addProduceWarehouse(){
		try{
			
			//添加生产入库 主表 子表
			String badge=getParameter("badge");
			String wmohuid=UUID.randomUUID().toString();     //生产入库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			
			WmProductionwarehouse whouse=new WmProductionwarehouse();

			
			whouse.setDocumenttypeid("DF911FEF-0DCC-4D2D-896C-081DA208BF35");//WM_BillType
			whouse.setGid(wmohuid);
			whouse.setBadge(Integer.parseInt(badge));
			whouse.setBillcode(billCode);
			whouse.setWhuid(whUid);
			whouse.setDepartmentuid(depUid);
			whouse.setRecordpersonid(getParameter("recordPersonUid"));//录入人
			whouse.setSobgid(getSession().get("SobId").toString());
			whouse.setOrggid(getSession().get("OrgId").toString());
			whouse.setBilldate(new Date());
			whouse.setRecorddate(new Date());
			whouse.setNotes(getParameter("notes"));
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmProductionwarehouseC> wmohclist=new ArrayList<WmProductionwarehouseC>();                     //生产入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
					WmProductionwarehouseC wmohc=new WmProductionwarehouseC();//其它入库子表
					wmohc.setGid(UUID.randomUUID().toString());
					wmohc.setNotes(note[i]);
					wmohc.setProUid(wmohuid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
					wmohc.setBarcode(billCode+(i+1));
					/*wmohc.setCallCuid(wmcc.getGid());*/
					wmohc.setGoodsallocationuid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
					}
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]));
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
						wmcat.setBatch(batch[i]);
					}
				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]));
			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(wmohc);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.addProduceWarehouse(whouse, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}	
	public void updateProduceWarehouse(){
		try{
			//添加生产入库 主表 子表
			String badge=getParameter("badge");
			//String wmohuid=UUID.randomUUID().toString();     //主其他入库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String billCode=getParameter("billCode"); //部门
			String produceWarehousegid=getParameter("produceWarehousegid"); 
			WmProductionwarehouse wmoh=new WmProductionwarehouse();  			
		    wmoh.setGid(produceWarehousegid);
			wmoh.setWhuid(whUid);
			wmoh.setDepartmentuid(depUid);
			wmoh.setNotes(getParameter("notes"));
			wmoh.setRecordpersonid(getParameter("recordPersonUid"));
			wmoh.setBillcode(billCode);
			wmoh.setBadge(Integer.parseInt(badge));
			List otherC = wareHouseService.getProduceWarehouseClist(produceWarehousegid);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmProductionwarehouseC> wmohclist=new ArrayList<WmProductionwarehouseC>();                    //生产入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] gid = getRequest().getParameterValues("gid");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
				
					WmProductionwarehouseC wc=wareHouseService.findWmProductionwarehouseC(gid[i]);
					WmProductionwarehouseC wmohc=new WmProductionwarehouseC();//生产入库子表
					wmohc.setGid(UUID.randomUUID().toString());
					wmohc.setNotes(note[i]);
					wmohc.setProUid(produceWarehousegid);
					wmohc.setGoodsUid(goodsUid[i]);
					wmohc.setNumber(new BigDecimal(mainNumber[i]));
					if(CommonUtil.notNullObject(wc)){
					wmohc.setDefine22(wc.getDefine22());
					wmohc.setDefine23(wc.getDefine23());
					wmohc.setDefine24(wc.getDefine24());
					}
					if(!CommonUtil.isNullObject(barCode[i])){ 
						wmohc.setBarcode(barCode[i]);
					}
					else
					{
						wmohc.setBarcode(billCode+(i+1+otherC.size()));
					}
					/*wmohc.setCallCuid(wmcc.getGid());*/
					wmohc.setGoodsallocationuid(goodsAllocationUid[i]);
					wmohc.setBatch(CommonUtil.Obj2String(batch[i]));
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
					wmohc.setAssistNumber(new BigDecimal(assistNumber[i]));
					}
					
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]));
				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]));
			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
			                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(wmohc);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.updateProduceWarehouse(wmoh, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}
/**
 * 删除
 * @category
 * 2016年7月14日 上午8:55:04
 * @author 杨胜
 */
	public void deleteProduceWarehouse(){
		try {
			String gid = getParameter("gid");
			WmProductionwarehouse wmoh=new WmProductionwarehouse();
			wmoh.setGid(gid);
			wmoh.setSobgid(getSession().get("SobId").toString());
			wmoh.setOrggid(getSession().get("OrgId").toString());
			JSONObject jobj=wareHouseService.deleteProduceWarehouse(wmoh);
			
			getResponse().getWriter().write(jobj.toString());
			
		} catch (Exception e) {
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}
	public String produceWarehouseList(){
		try{
			
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("owh.billCode", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
		if(!CommonUtil.isNullString(keyWord)){
			List<AaGoods> goods=cacheCtrlService.setGoods();
			for (AaGoods aaGoods : goods) {
				String gid="";
				if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
					gid+=aaGoods.getGid();
					condition+="or wpwc.goodsUid like '%"+gid+"%'";
				}			
			}
		}
		PageBean list = wareHouseService.getProduceWarehouseList(pageIndex, pageSize, condition);
		for(int i=0;i<list.getList().size();i++){
				if(!CommonUtil.isNullString(((WmProductionwarehouse)list.getList().get(i)).getRecordpersonid())){
				YmUser ymuser = cacheCtrlService.getUser(((WmProductionwarehouse)list.getList().get(i)).getRecordpersonid().toString());
				if(!CommonUtil.isNullObject(ymuser)){
					((WmProductionwarehouse)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
				}
			}
				if(!CommonUtil.isNullString(((WmProductionwarehouse)list.getList().get(i)).getDepartmentuid())){
					AaDepartment department = cacheCtrlService.getDepartment(((WmProductionwarehouse)list.getList().get(i)).getDepartmentuid().toString());
					if(!CommonUtil.isNullObject(department)){
						((WmProductionwarehouse)list.getList().get(i)).setDepartName(department.getDepname());
					}
				}
				if(!CommonUtil.isNullString(((WmProductionwarehouse)list.getList().get(i)).getWhuid())){
					AaWarehouse warehouse  = cacheCtrlService.getWareHouse(((WmProductionwarehouse)list.getList().get(i)).getWhuid().toString());
					if(!CommonUtil.isNullObject(warehouse)){
						((WmProductionwarehouse)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
					}
				}
				if (!CommonUtil.isNullString(((WmProductionwarehouse)list.getList().get(i)).getGoodsGid())) {
					AaGoods good=cacheCtrlService.getGoods(((WmProductionwarehouse)list.getList().get(i)).getGoodsGid());
					((WmProductionwarehouse)list.getList().get(i)).setGoodsGid(good.getGoodsname());
					((WmProductionwarehouse)list.getList().get(i)).setGoodsCode(good.getGoodscode());
				}
		}
		setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "produceWarehouseList";
	}
	
	//...................................................采购入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
	/**
	 *  查询最后一条记录
	 * @category
	 * 2016年7月13日 下午3:14:52
	 * @author 杨胜
	 * @return
	 */
		public String toAddPoWarehouse(){
			try{
				String poWarehousegid = getParameter("poWarehousegid");
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				Map poWarehouseWarehouse = wareHouseService.findPoWarehouse(poWarehousegid,orgId,sobId);
				if(!CommonUtil.isNullObject(poWarehouseWarehouse)){
					if(!CommonUtil.isNullObject(poWarehouseWarehouse.get("departmentUid"))){
						AaDepartment department = cacheCtrlService.getDepartment(poWarehouseWarehouse.get("departmentUid").toString());
						setRequstAttribute("department", department);
					}
					
					if(!CommonUtil.isNullObject(poWarehouseWarehouse.get("providerUid"))){
						AaProviderCustomer customer = cacheCtrlService.getProviderCustomer(poWarehouseWarehouse.get("providerUid").toString());
						setRequstAttribute("customer", customer);
					}
					
					if(!CommonUtil.isNullObject(poWarehouseWarehouse.get("whUid"))){
						AaWarehouse warehouse = cacheCtrlService.getWareHouse(poWarehouseWarehouse.get("whUid").toString());
						setRequstAttribute("warehouse", warehouse);
					}
					if(!CommonUtil.isNullObject(poWarehouseWarehouse.get("recordPersonId"))){
						AaPerson aaperson = cacheCtrlService.getPerson(poWarehouseWarehouse.get("recordPersonId").toString());
						setRequstAttribute("aaperson", aaperson);
					}
					if(!CommonUtil.isNullObject(poWarehouseWarehouse.get("gid"))){
						List produceWarehouseC = wareHouseService.getPoWarehouseClist(poWarehouseWarehouse.get("gid").toString());
						for(int i=0;i<produceWarehouseC.size();i++){
							AaGoods good = cacheCtrlService.getGoods(((Map)produceWarehouseC.get(i)).get("materialUid").toString());
							((Map)produceWarehouseC.get(i)).put("good", good);
							AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((Map)produceWarehouseC.get(i)).get("goodsAllocationUid").toString());
							((Map)produceWarehouseC.get(i)).put("goodsAllocationName", alocation.getName());
						}
						setRequstAttribute("produceWarehouseC", produceWarehouseC);
					}
				}
				String time = DateUtil.dateToString(new Date(), "yyMMdd");
				setRequstAttribute("time", time);
				setRequstAttribute("produceWarehouse", poWarehouseWarehouse);
				setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return "poWarehouseAdd";
		}
	
		/**
		 * 添加采购入库表
		 * @category
		 * 2016年7月14日 上午8:48:13
		 * @author 杨胜
		 */
		public void addPoWarehouse(){
			try{
				
				//添加采购入库 主表 子表
				String badge=getParameter("badge");
				String wmohuid=UUID.randomUUID().toString();     //生产入库Gid
				String whUid=getParameter("whUid"); //仓库号
				String depUid=getParameter("depUid"); //部门
				String billCode=getParameter("billCode"); //部门
				
				WmPowarehouse whouse=new WmPowarehouse();
				
				whouse.setDocumenttypeuid("B89815B2-21B4-4CF5-9D02-B67BFF507C8E");//WM_BillType
				whouse.setGid(wmohuid);
				whouse.setBadge(Integer.parseInt(badge));
				whouse.setBillcode(billCode);
				whouse.setWhuid(whUid);
				whouse.setDepartmentuid(depUid);
				whouse.setRecordpersonid(getParameter("recordPersonUid"));//录入人
				whouse.setSobgid(getSession().get("SobId").toString());
				whouse.setOrggid(getSession().get("OrgId").toString());
				whouse.setBilldate(new Date());
				whouse.setRecorddate(new Date());
				whouse.setNotes(getParameter("notes"));
				whouse.setProvideruid(getParameter("providerUid"));
				List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
				List<WmPowarehouseC> wmohclist=new ArrayList<WmPowarehouseC>();                     //生产入库子记录表
				List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
				String[] goodsUid = getRequest().getParameterValues("goodsUid");
				if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
					String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
					String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
					String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
					String[] batch = getRequest().getParameterValues("batch");								//批次
					String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
					String[] note = getRequest().getParameterValues("note");	// 备注
					String[] price = getRequest().getParameterValues("price");	// 单价
					String[] amount = getRequest().getParameterValues("amount");	// 金额
					for(int i=0;i<goodsUid.length;i++){
						WmPowarehouseC wmohc=new WmPowarehouseC();//其它入库子表
						wmohc.setGid(UUID.randomUUID().toString());
						wmohc.setNotes(note[i]);
						wmohc.setPowhuid(wmohuid);
						wmohc.setMaterialuid(goodsUid[i]);
						wmohc.setQuantity(new BigDecimal(mainNumber[i]));
						wmohc.setBarcode(billCode+(i+1));
						wmohc.setGoodsallocationuid(goodsAllocationUid[i]);
						wmohc.setBatchcode(CommonUtil.Obj2String(batch[i]));
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
							wmohc.setAssistquantity(new BigDecimal(assistNumber[i]));
						}
						wmohc.setPrice(CommonUtil.object2BigDecimal(price[i]) );
						wmohc.setAmount(CommonUtil.object2BigDecimal(amount[i]) );
						
						WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
						wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
						AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
						wmcat.setGoodsallocationcode(gaIn.getCode());
						wmcat.setGoodsallocationuid(gaIn.getGid());
						wmcat.setWhCode(gaIn.getWhcode());
						wmcat.setGoodsuid(goodsUid[i]);
						wmcat.setGoodscode(goodsCode[i]);
						wmcat.setNumber(new BigDecimal(mainNumber[i]));
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
							wmcat.setBatch(batch[i]);
						}
					    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
						 }
			            wmcat.setOrggid(getSession().get("OrgId").toString());
			            wmcat.setSobgid(getSession().get("SobId").toString());
						 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
				                WmBatch wmb=new WmBatch();
				                wmb.setGid(UUID.randomUUID().toString());
				                wmb.setGoodsUid(goodsUid[i]);
				                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
				                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
				                wmb.setNumber(new BigDecimal(mainNumber[i]));
				                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
				                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
				                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
				                wmb.setRecordDate(new Timestamp(new Date().getTime()));
				                wmBatchs.add(wmb);
				            }
						 wmohclist.add(wmohc);
						 asList.add(wmcat);
					}
				}
				JSONObject jobj=wareHouseService.addPoWarehouse(whouse, wmohclist, wmBatchs, asList);
				getResponse().getWriter().write(jobj.toString());
				
			}catch(Exception e){
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}
		
		//修改采购入库
		public void updatePoWarehouse(){
			try{
				//添加生产入库 主表 子表
				String badge=getParameter("badge");
				//String wmohuid=UUID.randomUUID().toString();     //主其他入库Gid
				String whUid=getParameter("whUid"); //仓库号
				String depUid=getParameter("depUid"); //部门
				String billCode=getParameter("billCode"); //部门
				String poWarehousegid=getParameter("poWarehousegid"); 
				WmPowarehouse wmoh=new WmPowarehouse();  			
			    wmoh.setGid(poWarehousegid);
				wmoh.setWhuid(whUid);
				wmoh.setDepartmentuid(depUid);
				wmoh.setNotes(getParameter("notes"));
				wmoh.setRecordpersonid(getParameter("recordPersonUid"));
				wmoh.setBillcode(billCode);
				wmoh.setBadge(Integer.parseInt(badge));
				wmoh.setProvideruid(getParameter("providerUid"));
//					List otherC = wareHouseService.getProduceWarehouseClist(produceWarehousegid);
				List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
				List<WmPowarehouseC> wmohclist=new ArrayList<WmPowarehouseC>();                    //生产入库子记录表
				List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
				String[] goodsUid = getRequest().getParameterValues("goodsUid");
				if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
					String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
					String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
					String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
					String[] batch = getRequest().getParameterValues("batch");								//批次
					String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
					String[] note = getRequest().getParameterValues("note");	// 备注
					String[] gid = getRequest().getParameterValues("gid");	// 备注
					String[] barCode = getRequest().getParameterValues("barCode");
					String[] price = getRequest().getParameterValues("price");	// 单价
					String[] amount = getRequest().getParameterValues("amount");	// 金额
					for(int i=0;i<goodsUid.length;i++){
					
						WmPowarehouseC wmohc=new WmPowarehouseC();//生产入库子表
						wmohc.setGid(UUID.randomUUID().toString());
						wmohc.setNotes(note[i]);
						wmohc.setPowhuid(poWarehousegid);
						wmohc.setMaterialuid(goodsUid[i]);
						wmohc.setQuantity(new BigDecimal(mainNumber[i]));
						if(!CommonUtil.isNullObject(barCode[i])){ 
							wmohc.setBarcode(barCode[i]);
						}
						
						/*wmohc.setCallCuid(wmcc.getGid());*/
						wmohc.setGoodsallocationuid(goodsAllocationUid[i]);
						wmohc.setBatchcode(CommonUtil.Obj2String(batch[i]));
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
							wmohc.setAssistquantity(new BigDecimal(assistNumber[i]));
						}
						wmohc.setPrice(CommonUtil.object2BigDecimal(price[i]) );
						wmohc.setAmount(CommonUtil.object2BigDecimal(amount[i]) );
						
						WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
						wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
						AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
						wmcat.setGoodsallocationcode(gaIn.getCode());
						wmcat.setGoodsallocationuid(gaIn.getGid());
						wmcat.setWhCode(gaIn.getWhcode());
						wmcat.setGoodsuid(goodsUid[i]);
						wmcat.setGoodscode(goodsCode[i]);
						wmcat.setNumber(new BigDecimal(mainNumber[i]));
					    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			             wmcat.setAssistnum(new BigDecimal(assistNumber[i]));
						 }
			            wmcat.setOrggid(getSession().get("OrgId").toString());
			            wmcat.setSobgid(getSession().get("SobId").toString());
						 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
				                WmBatch wmb=new WmBatch();
				                wmb.setGid(UUID.randomUUID().toString());
				                wmb.setGoodsUid(goodsUid[i]);
				                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
				                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
				                wmb.setNumber(new BigDecimal(mainNumber[i]));
				                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
				                wmb.setAssistNum(new BigDecimal(assistNumber[i]));}
				                wmb.setRedBlueFlag(Integer.parseInt(badge));//1、蓝字单据，0、红字单据
				                wmb.setRecordDate(new Timestamp(new Date().getTime()));
				                wmBatchs.add(wmb);
				            }
						 wmohclist.add(wmohc);
						 asList.add(wmcat);
					}
				}
				JSONObject jobj=wareHouseService.updatePoWarehouse(wmoh, wmohclist, wmBatchs, asList);
				getResponse().getWriter().write(jobj.toString());
				
			}catch(Exception e){
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}
		/**
		 * 删除采购入库
		 * @category
		 * 2016年7月14日 上午8:55:04
		 * @author 杨胜
		 */
		public void deleteWarehouse(){
			try {
				String gid = getParameter("gid");
				WmPowarehouse wmoh=new WmPowarehouse();
				wmoh.setGid(gid);
				wmoh.setSobgid(getSession().get("SobId").toString());
				wmoh.setOrggid(getSession().get("OrgId").toString());
				JSONObject jobj=wareHouseService.deleteWmPowarehouse(wmoh);
				getResponse().getWriter().write(jobj.toString());
				
			} catch (Exception e) {
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}
		
		//入库列表
		public String powarehouseList(){
			try{
				int pageIndex = getPageIndex();
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("wpw.billCode", keyWord);
				setRequstAttribute("keyWord",keyWord);
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				condition+=" and wpw.sobGid='"+sobId+"' and wpw.orgGid='"+orgId+"'";
				if(!CommonUtil.isNullString(keyWord)){
					List<AaGoods> goods=cacheCtrlService.setGoods();
					for (AaGoods aaGoods : goods) {
						String gid="";
						if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
							gid+=aaGoods.getGid();
							condition+="or wpwc.materialUid like '%"+gid+"%'";
						}			
					}
				}
				PageBean list = wareHouseService.getPoWarehouseList(pageIndex, pageSize, condition);
				for(int i=0;i<list.getList().size();i++){
						if(!CommonUtil.isNullString(((WmPowarehouse)list.getList().get(i)).getRecordpersonid())){
						YmUser ymuser = cacheCtrlService.getUser(((WmPowarehouse)list.getList().get(i)).getRecordpersonid().toString());
						if(!CommonUtil.isNullObject(ymuser)){
							((WmPowarehouse)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
							}
						}
						if(!CommonUtil.isNullString(((WmPowarehouse)list.getList().get(i)).getDepartmentuid())){
							AaDepartment department = cacheCtrlService.getDepartment(((WmPowarehouse)list.getList().get(i)).getDepartmentuid().toString());
							if(!CommonUtil.isNullObject(department)){
								((WmPowarehouse)list.getList().get(i)).setDepartName(department.getDepname());
							}
						}
						if(!CommonUtil.isNullString(((WmPowarehouse)list.getList().get(i)).getWhuid())){
							AaWarehouse warehouse  = cacheCtrlService.getWareHouse(((WmPowarehouse)list.getList().get(i)).getWhuid().toString());
							if(!CommonUtil.isNullObject(warehouse)){
								((WmPowarehouse)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
							}
						}
						if (!CommonUtil.isNullString(((WmPowarehouse)list.getList().get(i)).getGoodsGid())) {
							AaGoods goods=cacheCtrlService.getGoods(((WmPowarehouse)list.getList().get(i)).getGoodsGid());
							if(goods != null){
								((WmPowarehouse)list.getList().get(i)).setGoodsName(goods.getGoodsname());
								((WmPowarehouse)list.getList().get(i)).setGoodsCode(goods.getGoodscode());
								Unit  unit = cacheCtrlService.getUnit(goods.getGoodsunit());
								if(unit!= null){
									((WmPowarehouse)list.getList().get(i)).setGoodsUnit(unit.getUnitname());//计量单位
								}

							}

						}
				}
				setRequstAttribute("data", list);
			}catch(Exception e){
				e.printStackTrace();
			}
			return "poWarehouseList";
		}
			
	//...................................................销售出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	
/**
 *  查询最后一条记录
 * @category
 * 2016年7月13日 下午3:14:52
 * @author 杨胜
 * @return
 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toAddSaleOutWarehouse(){
		try{
			String saleOutWarehousegid = getParameter("saleOutWarehousegid");
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			Map saleOutWarehouse = wareHouseService.findSaleOutWarehouse(saleOutWarehousegid,orgId,sobId);
			if(!CommonUtil.isNullObject(saleOutWarehouse)){
				if(!CommonUtil.isNullObject(saleOutWarehouse.get("departmentUid"))){
					AaDepartment department = cacheCtrlService.getDepartment(saleOutWarehouse.get("departmentUid").toString());
					setRequstAttribute("department", department);
				}
				if(!CommonUtil.isNullObject(saleOutWarehouse.get("whUid"))){
					AaWarehouse warehouse = cacheCtrlService.getWareHouse(saleOutWarehouse.get("whUid").toString());
					setRequstAttribute("warehouse", warehouse);
				}
				if(!CommonUtil.isNullObject(saleOutWarehouse.get("recordPerson"))){
					AaPerson aaperson = cacheCtrlService.getPerson(saleOutWarehouse.get("recordPerson").toString());
					setRequstAttribute("aaperson", aaperson);
				}
				if(!CommonUtil.isNullObject(saleOutWarehouse.get("customerUid"))){
					AaProviderCustomer customer = cacheCtrlService.getProviderCustomer(saleOutWarehouse.get("customerUid").toString());
					setRequstAttribute("customer", customer);
				}
				if(!CommonUtil.isNullObject(saleOutWarehouse.get("gid"))){
					List saleOutWarehouseC = wareHouseService.getSaleOutWarehouseClist(saleOutWarehouse.get("gid").toString());
					for(int i=0;i<saleOutWarehouseC.size();i++){
						AaGoods good = cacheCtrlService.getGoods(((Map)saleOutWarehouseC.get(i)).get("goodsUid").toString());
						((Map)saleOutWarehouseC.get(i)).put("good", good);
						AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((Map)saleOutWarehouseC.get(i)).get("goodsAllocationUid").toString());
						((Map)saleOutWarehouseC.get(i)).put("goodsAllocationName", alocation.getName());
					}
					setRequstAttribute("saleOutWarehouseC", saleOutWarehouseC);
				}
			}
			String time = DateUtil.dateToString(new Date(), "yyMMdd");
			setRequstAttribute("time", time);
			setRequstAttribute("saleOutWarehouse", saleOutWarehouse);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "addSaleOutWarehouse";
	}
/**
 * 添加销售出库
 * @category
 * 2016年7月14日 上午8:48:13
 * @author 杨胜
 */
	public void addSaleOutWarehouse(){
		try{
			
			//添加销售出库 主表 子表
			String badge=getParameter("badge");
			String saleOutGid=UUID.randomUUID().toString();     //销售出库Gid
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String customerUid=getParameter("customerUid"); //客户
			String billCode=getParameter("billCode"); //部门			
			WmSaleout saleOut=new WmSaleout();
			
			saleOut.setGid(saleOutGid);
			saleOut.setDocumenttypeuid("7A41D669-9F38-442F-AB4C-A9937D02434F");//WM_BillType
			saleOut.setBillcode(billCode);
			saleOut.setWhuid(whUid);
			saleOut.setDepartmentuid(depUid);
			saleOut.setCustomeruid(customerUid);
			saleOut.setNotes(getParameter("notes"));
			saleOut.setRecordperson(getParameter("recordPersonUid"));//录入人
			saleOut.setSobgid(getSession().get("SobId").toString());
			saleOut.setOrggid(getSession().get("OrgId").toString());
			saleOut.setBilldate(new Date());
			saleOut.setRecorddate(new Date());
			saleOut.setBadge(Integer.parseInt(badge));
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmSaleoutC> wmohclist=new ArrayList<WmSaleoutC>();                     //生产入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){

					WmSaleoutC saleOutC=new WmSaleoutC();//销售出库单字表
					String saleOutCGid=UUID.randomUUID().toString();
					saleOutC.setGid(saleOutCGid);
					saleOutC.setSaleoutuid(saleOutGid);
					saleOutC.setGoodsuid(goodsUid[i]);
					saleOutC.setNumber(new BigDecimal(mainNumber[i]));
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
						saleOutC.setAssistNumber(new BigDecimal(assistNumber[i]));
					}
					saleOutC.setGoodsallocationuid(goodsAllocationUid[i]);
//					saleOutC.setBarcode(billCode+(i+1));
					saleOutC.setBatchcode(CommonUtil.Obj2String(batch[i]));
					saleOutC.setNotes(note[i]);
					
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());//取相反数
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
						wmcat.setBatch(batch[i]);
					}
				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]).negate());
					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]).negate());
			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			                	wmb.setAssistNum(new BigDecimal(assistNumber[i]).negate());
			                }
			                if(wmb.getNumber().compareTo(BigDecimal.valueOf(0))>=0){
			                	wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
			                }else{
			                	wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
			                }
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(saleOutC);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.addSaleOutWarehouse(saleOut, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}	
	
	//修改销售出库 主表 子表
	public void updateSaleOutWarehouse(){
		try{
			
			String badge=getParameter("badge");
			String saleOutWarehousegid=getParameter("saleOutWarehousegid"); 
			String whUid=getParameter("whUid"); //仓库号
			String depUid=getParameter("depUid"); //部门
			String customerUid=getParameter("customerUid"); //客户
			String billCode=getParameter("billCode"); //部门			
			WmSaleout saleOut=new WmSaleout();
			saleOut.setDepartmentuid(depUid);
			saleOut.setGid(saleOutWarehousegid);
			saleOut.setWhuid(whUid);
			saleOut.setCustomeruid(customerUid);
			saleOut.setNotes(getParameter("notes"));
			saleOut.setBadge(Integer.parseInt(badge));
			
			List otherC = wareHouseService.getSaleOutWarehouseClist(saleOutWarehousegid);
			List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
			List<WmSaleoutC> wmohclist=new ArrayList<WmSaleoutC>();                    //生产入库子记录表
			List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
				String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
				String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
				String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
				String[] batch = getRequest().getParameterValues("batch");								//批次
				String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
				String[] note = getRequest().getParameterValues("note");	// 备注
				String[] gid = getRequest().getParameterValues("gid");	// 备注
				String[] barCode = getRequest().getParameterValues("barCode");
				for(int i=0;i<goodsUid.length;i++){
				
					WmSaleoutC wc=wareHouseService.findWmSaleOutwarehouseC(gid[i]);
					WmSaleoutC saleOutC=new WmSaleoutC();//销售出库单字表
					String saleOutCGid=UUID.randomUUID().toString();
					saleOutC.setGid(saleOutCGid);
					saleOutC.setSaleoutuid(saleOutWarehousegid);
					saleOutC.setGoodsuid(goodsUid[i]);
					saleOutC.setNumber(new BigDecimal(mainNumber[i]));
					if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
						saleOutC.setAssistNumber(new BigDecimal(assistNumber[i]));
					}
					saleOutC.setGoodsallocationuid(goodsAllocationUid[i]);
					saleOutC.setBatchcode(CommonUtil.Obj2String(batch[i]));
					if(CommonUtil.notNullObject(wc)){
						saleOutC.setDefine22(wc.getDefine22());
						saleOutC.setDefine23(wc.getDefine23());
						saleOutC.setDefine24(wc.getDefine24());
					}
					if(!CommonUtil.isNullObject(barCode[i])){ 
						saleOutC.setBarcode(barCode[i]);
					}
					else
					{
						saleOutC.setBarcode(billCode+(i+1+otherC.size()));
					}
					saleOutC.setNotes(note[i]);
					WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
					wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
					AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
					wmcat.setGoodsallocationcode(gaIn.getCode());
					wmcat.setGoodsallocationuid(gaIn.getGid());
					wmcat.setWhCode(gaIn.getWhcode());
					wmcat.setGoodsuid(goodsUid[i]);
					wmcat.setGoodscode(goodsCode[i]);
					wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());//取相反数
				    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
		             wmcat.setAssistnum(new BigDecimal(assistNumber[i]).negate());
					 }
		            wmcat.setOrggid(getSession().get("OrgId").toString());
		            wmcat.setSobgid(getSession().get("SobId").toString());
					 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
			                WmBatch wmb=new WmBatch();
			                wmb.setGid(UUID.randomUUID().toString());
			                wmb.setGoodsUid(goodsUid[i]);
			                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
			                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
			                wmb.setNumber(new BigDecimal(mainNumber[i]).negate());
			                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			                	wmb.setAssistNum(new BigDecimal(assistNumber[i]).negate());
			                }
			                if(wmb.getNumber().compareTo(BigDecimal.valueOf(0))>=0){
			                	wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
			                }else{
			                	wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
			                }
			                wmb.setRecordDate(new Timestamp(new Date().getTime()));
			                wmBatchs.add(wmb);
			            }
					 wmohclist.add(saleOutC);
					 asList.add(wmcat);
				}
			}
			JSONObject jobj=wareHouseService.updateSaleOutWarehouse(saleOut, wmohclist, wmBatchs, asList);
			getResponse().getWriter().write(jobj.toString());
			
		}catch(Exception e){
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}
/**
 * 删除销售出库
 * @category
 * 2016年7月14日 上午8:55:04
 * @author 杨胜
 */
	public void deleteSaleOutWarehouse(){
		try {
			String gid = getParameter("gid");
			WmSaleout wmoh=new WmSaleout();
			wmoh.setGid(gid);
			wmoh.setSobgid(getSession().get("SobId").toString());
			wmoh.setOrggid(getSession().get("OrgId").toString());
			JSONObject jobj=wareHouseService.deleteSaleOutWarehouse(wmoh);
			getResponse().getWriter().write(jobj.toString());
			
		} catch (Exception e) {
			writeErrorOrSuccess(0, "提交失败！");
			e.printStackTrace();
		}
	}
	public String saleOutWarehouseList(){
		try{
			
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("owh.billCode", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
		if(!CommonUtil.isNullString(keyWord)){
			List<AaGoods> goods=cacheCtrlService.setGoods();
			for (AaGoods aaGoods : goods) {
				String gid="";
				if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
					gid+=aaGoods.getGid();
					condition+="or wsoc.goodsUid like '%"+gid+"%'";
				}				
			}
		}
		PageBean list = wareHouseService.getSaleOutWarehouseList(pageIndex, pageSize, condition);
		for(int i=0;i<list.getList().size();i++){
				if(!CommonUtil.isNullString(((WmSaleout)list.getList().get(i)).getRecordperson())){
				YmUser ymuser = cacheCtrlService.getUser(((WmSaleout)list.getList().get(i)).getRecordperson().toString());
				if(!CommonUtil.isNullObject(ymuser)){
					((WmSaleout)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
				}
			   }
				if(!CommonUtil.isNullString(((WmSaleout)list.getList().get(i)).getDepartmentuid())){
					AaDepartment department = cacheCtrlService.getDepartment(((WmSaleout)list.getList().get(i)).getDepartmentuid().toString());
					if(!CommonUtil.isNullObject(department)){
						((WmSaleout)list.getList().get(i)).setDepartName(department.getDepname());
					}
				}
				if(!CommonUtil.isNullString(((WmSaleout)list.getList().get(i)).getCustomeruid())){
					AaProviderCustomer customer = cacheCtrlService.getProviderCustomer(((WmSaleout)list.getList().get(i)).getCustomeruid().toString());
					if(!CommonUtil.isNullObject(customer)){
						((WmSaleout)list.getList().get(i)).setCustomerName(customer.getPcname());
					}
					
				}
				if(!CommonUtil.isNullString(((WmSaleout)list.getList().get(i)).getWhuid())){
					AaWarehouse warehouse  = cacheCtrlService.getWareHouse(((WmSaleout)list.getList().get(i)).getWhuid().toString());
					if(!CommonUtil.isNullObject(warehouse)){
						((WmSaleout)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
					}
				}
				if (!CommonUtil.isNullString(((WmSaleout)list.getList().get(i)).getGoodsUid())) {
					AaGoods good=cacheCtrlService.getGoods(((WmSaleout)list.getList().get(i)).getGoodsUid());
					((WmSaleout)list.getList().get(i)).setGoodsUid(good.getGoodsname());
					((WmSaleout)list.getList().get(i)).setGoodsCode(good.getGoodscode());
				}
		}
		setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "saleOutWarehouseList";
	}
	
	//...................................................领料出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
	/**
	 *  查询最后一条记录
	 * @category
	 * 2016年7月13日 下午3:14:52
	 * @author 杨胜
	 * @return
	 */
		
		public String toAddMaterialOut(){
			try{
				String materialOutgid = getParameter("materialOutgid");
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				Map saleOutWarehouse = wareHouseService.findMaterialOutWarehouse(materialOutgid,orgId,sobId);

				if(!CommonUtil.isNullObject(saleOutWarehouse)){
					if(!CommonUtil.isNullObject(saleOutWarehouse.get("departmentUid"))){
						AaDepartment department = cacheCtrlService.getDepartment(saleOutWarehouse.get("departmentUid").toString());
						setRequstAttribute("department", department);
					}

					if(!CommonUtil.isNullObject(saleOutWarehouse.get("notes"))){
						//使用部门 放在备注里了
						AaDepartment departmentuse = cacheCtrlService.getDepartment(saleOutWarehouse.get("notes").toString());
						setRequstAttribute("departmentuse", departmentuse);
					}


					if(!CommonUtil.isNullObject(saleOutWarehouse.get("whUid"))){
						AaWarehouse warehouse = cacheCtrlService.getWareHouse(saleOutWarehouse.get("whUid").toString());
						setRequstAttribute("warehouse", warehouse);
					}
					if(!CommonUtil.isNullObject(saleOutWarehouse.get("recordPerson"))){
						AaPerson aaperson = cacheCtrlService.getPerson(saleOutWarehouse.get("recordPerson").toString());
						setRequstAttribute("aaperson", aaperson);
					}
					if(!CommonUtil.isNullObject(saleOutWarehouse.get("customerUid"))){
						AaProviderCustomer customer = cacheCtrlService.getProviderCustomer(saleOutWarehouse.get("customerUid").toString());
						setRequstAttribute("customer", customer);
					}
					if(!CommonUtil.isNullObject(saleOutWarehouse.get("gid"))){
						List materialOutC = wareHouseService.getMaterList(saleOutWarehouse.get("gid").toString());
						for(int i=0;i<materialOutC.size();i++){
							AaGoods good = cacheCtrlService.getGoods(((WmMaterialoutC)materialOutC.get(i)).getGoodsuid().toString());
							((WmMaterialoutC)materialOutC.get(i)).setGood(good);
							AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((WmMaterialoutC)materialOutC.get(i)).getGoodsallocationuid().toString());
							((WmMaterialoutC)materialOutC.get(i)).setAlocation(alocation.getName());
							if (!CommonUtil.isNullObject(((WmMaterialoutC)materialOutC.get(i)).getGoodName())) {
								AaGoods goods = cacheCtrlService.getGoods(((WmMaterialoutC)materialOutC.get(i)).getGoodName().toString());
								((WmMaterialoutC)materialOutC.get(i)).setGoodName(goods.getGoodsname());
								((WmMaterialoutC)materialOutC.get(i)).setGoodsStandard(goods.getGoodsstandard());
							}
							
						}
						setRequstAttribute("saleOutWarehouseC", materialOutC);
					}
				}
				String time = DateUtil.dateToString(new Date(), "yyMMdd");
				setRequstAttribute("time", time);
				setRequstAttribute("saleOutWarehouse", saleOutWarehouse);
				setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return "materialOutAdd";
		}
		
		/**
		 * 添加材料出库单
		 * @category
		 * 2016年7月14日 上午8:48:13
		 * @author 杨胜
		 */
		public void addMaterialOut(){
			try{
				//添加材料出库 主表 子表
				String badge=getParameter("badge");
				String materialOutGid=UUID.randomUUID().toString();     //材料出库Gid
				String whUid=getParameter("whUid"); //仓库号
				String depUid=getParameter("depUid"); //部门
				String billCode=getParameter("billCode"); //编号
				WmMaterialout wmMaterialout=new WmMaterialout();
				
				wmMaterialout.setGid(materialOutGid);
				wmMaterialout.setDocumenttypeuid("B65B212E-3C78-44CB-8F3E-43913F3664B7");//WM_BillType
				wmMaterialout.setBillcode(billCode);
				wmMaterialout.setWhuid(whUid);
				wmMaterialout.setDepartmentuid(depUid);
				wmMaterialout.setNotes(getParameter("notes"));
				wmMaterialout.setRecordperson(getParameter("recordPersonUid"));//录入人
				wmMaterialout.setSobgid(getSession().get("SobId").toString());
				wmMaterialout.setOrggid(getSession().get("OrgId").toString());
				wmMaterialout.setBilldate(new Date());
				wmMaterialout.setRecorddate(new Date());
				wmMaterialout.setBadge(Integer.parseInt(badge));
				List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
				List<WmMaterialoutC> wmohclist=new ArrayList<WmMaterialoutC>();                     //销售出库子记录表
				List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
				String[] goodsUid = getRequest().getParameterValues("goodsUid");
				if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
					String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
					String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
					String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
					String[] batch = getRequest().getParameterValues("batch");								//批次
					String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
					String[] note = getRequest().getParameterValues("note");	// 备注
					String[] barCode = getRequest().getParameterValues("barCode");
					for(int i=0;i<goodsUid.length;i++){

						WmMaterialoutC wmMaterialoutC=new WmMaterialoutC();//销售出库单字表
						String saleOutCGid=UUID.randomUUID().toString();
						wmMaterialoutC.setGid(saleOutCGid);
						wmMaterialoutC.setMaterialoutuid(materialOutGid);
						wmMaterialoutC.setGoodsuid(goodsUid[i]);
						wmMaterialoutC.setNumber(new BigDecimal(mainNumber[i]));
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
							wmMaterialoutC.setAssistNumber(new BigDecimal(assistNumber[i]));
						}
						wmMaterialoutC.setGoodsallocationuid(goodsAllocationUid[i]);
//							saleOutC.setBarcode(billCode+(i+1));
						wmMaterialoutC.setBatchcode(CommonUtil.Obj2String(batch[i]));
						wmMaterialoutC.setNotes(note[i]);
						
						WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
						wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
						AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
						wmcat.setGoodsallocationcode(gaIn.getCode());
						wmcat.setGoodsallocationuid(gaIn.getGid());
						wmcat.setWhCode(gaIn.getWhcode());
						wmcat.setGoodsuid(goodsUid[i]);
						wmcat.setGoodscode(goodsCode[i]);
						wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());//取相反数
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){
							wmcat.setBatch(batch[i]);
						}
					    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
					    	wmcat.setAssistnum(new BigDecimal(assistNumber[i]).negate());
						 }
			            wmcat.setOrggid(getSession().get("OrgId").toString());
			            wmcat.setSobgid(getSession().get("SobId").toString());
						 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
				                WmBatch wmb=new WmBatch();
				                wmb.setGid(UUID.randomUUID().toString());
				                wmb.setGoodsUid(goodsUid[i]);
				                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
				                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
				                wmb.setNumber(new BigDecimal(mainNumber[i]).negate());
				                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
				                	wmb.setAssistNum(new BigDecimal(assistNumber[i]).negate());
				                }
				                if(wmb.getNumber().compareTo(BigDecimal.valueOf(0))>=0){
				                	wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
				                }else{
				                	wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
				                }
				                wmb.setRecordDate(new Timestamp(new Date().getTime()));
				                wmBatchs.add(wmb);
				            }
						 wmohclist.add(wmMaterialoutC);
						 asList.add(wmcat);
					}
				}
				JSONObject jobj=wareHouseService.addMaterialOutWarehouse(wmMaterialout, wmohclist, wmBatchs, asList);
				
				getResponse().getWriter().write(jobj.toString());
				
				
			}catch(Exception e){
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}		

		//修改材料出库 主表 子表
		public void updateMaterialOutWarehouse(){
			try{
				String badge=getParameter("badge");
				String materialOutWarehousegid=getParameter("materialOutgid"); 
				String whUid=getParameter("whUid"); //仓库号
				String depUid=getParameter("depUid"); //部门
				String billCode=getParameter("billCode"); //编号		
				WmMaterialout wmMaterialout=new WmMaterialout();
				wmMaterialout.setDepartmentuid(depUid);
				wmMaterialout.setGid(materialOutWarehousegid);
				wmMaterialout.setWhuid(whUid);
				wmMaterialout.setNotes(getParameter("notes"));
				wmMaterialout.setBadge(Integer.parseInt(badge));
				
				List<WmAllocationstock> asList=new ArrayList<WmAllocationstock>();
				List<WmMaterialoutC> wmohclist=new ArrayList<WmMaterialoutC>();                    //生产入库子记录表
				List<WmBatch> wmBatchs=new ArrayList<WmBatch>();                                          //批次记录表
				String[] goodsUid = getRequest().getParameterValues("goodsUid");
				if(goodsUid!=null&&goodsUid.length>0){//判断是否有明细信息
					String[] mainNumber = getRequest().getParameterValues("mainNumber");          					 // 主计量数量
					String[] goodsCode = getRequest().getParameterValues("goodsCode");   					 //商品code
					String[] assistNumber = getRequest().getParameterValues("assistNumber");				// 辅计量数量
					String[] batch = getRequest().getParameterValues("batch");								//批次
					String[] goodsAllocationUid = getRequest().getParameterValues("goodsAllocationUid");	//货位号的Gid
					String[] note = getRequest().getParameterValues("note");	// 备注
					String[] gid = getRequest().getParameterValues("gid");	// 备注
					String[] barCode = getRequest().getParameterValues("barCode");
					for(int i=0;i<goodsUid.length;i++){
					
						WmMaterialoutC wmMaterialoutC=new WmMaterialoutC();//销售出库单字表
						String saleOutCGid=UUID.randomUUID().toString();
						wmMaterialoutC.setGid(saleOutCGid);
						wmMaterialoutC.setMaterialoutuid(materialOutWarehousegid);
						wmMaterialoutC.setGoodsuid(goodsUid[i]);
						wmMaterialoutC.setNumber(new BigDecimal(mainNumber[i]));
						if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
							wmMaterialoutC.setAssistNumber(new BigDecimal(assistNumber[i]));
						}
						wmMaterialoutC.setGoodsallocationuid(goodsAllocationUid[i]);
						wmMaterialoutC.setBatchcode(CommonUtil.Obj2String(batch[i]));
						if(!CommonUtil.isNullObject(barCode[i])){ 
							wmMaterialoutC.setBarCode(barCode[i]);
						}
						wmMaterialoutC.setNotes(note[i]);
						WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
						wmcat.setBatch(CommonUtil.Obj2String(batch[i]));
						AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(goodsAllocationUid[i]);
						wmcat.setGoodsallocationcode(gaIn.getCode());
						wmcat.setGoodsallocationuid(gaIn.getGid());
						wmcat.setWhCode(gaIn.getWhcode());
						wmcat.setGoodsuid(goodsUid[i]);
						wmcat.setGoodscode(goodsCode[i]);
						wmcat.setNumber(new BigDecimal(mainNumber[i]).negate());//取相反数
					    if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
			             wmcat.setAssistnum(new BigDecimal(assistNumber[i]).negate());
						 }
			            wmcat.setOrggid(getSession().get("OrgId").toString());
			            wmcat.setSobgid(getSession().get("SobId").toString());
						 if(!CommonUtil.isNullObject(CommonUtil.Obj2String(batch[i]))){                //判断是否有批次，有则添加到批次表
				                WmBatch wmb=new WmBatch();
				                wmb.setGid(UUID.randomUUID().toString());
				                wmb.setGoodsUid(goodsUid[i]);
				                wmb.setGoodsAllocationUid(goodsAllocationUid[i]);
				                wmb.setBatch(CommonUtil.Obj2String(batch[i]));
				                wmb.setNumber(new BigDecimal(mainNumber[i]).negate());
				                if(!CommonUtil.isNullObject(CommonUtil.Obj2String(assistNumber[i]))){ 
				                	wmb.setAssistNum(new BigDecimal(assistNumber[i]).negate());
				                }
				                if(wmb.getNumber().compareTo(BigDecimal.valueOf(0))>=0){
				                	wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
				                }else{
				                	wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
				                }
				                wmb.setRecordDate(new Timestamp(new Date().getTime()));
				                wmBatchs.add(wmb);
				            }
						 wmohclist.add(wmMaterialoutC);
						 asList.add(wmcat);
					}
				}
				JSONObject jobj=wareHouseService.updateMaterialOutWarehouse(wmMaterialout, wmohclist, wmBatchs, asList);
				getResponse().getWriter().write(jobj.toString());
				
			}catch(Exception e){
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}

		/**
		 * 删除材料出库单
		 * @category
		 * 2016年7月14日 上午8:55:04
		 * @author 杨胜
		 */
		public void deleteMaterialOutWarehouse(){
			try {
				String gid = getParameter("gid");
				WmMaterialout wmMaterialout=new WmMaterialout();
				wmMaterialout.setGid(gid);
				wmMaterialout.setSobgid(getSession().get("SobId").toString());
				wmMaterialout.setOrggid(getSession().get("OrgId").toString());
				JSONObject jobj=wareHouseService.deleteMaterialOutWarehouse(wmMaterialout);
				getResponse().getWriter().write(jobj.toString());
				
			} catch (Exception e) {
				writeErrorOrSuccess(0, "提交失败！");
				e.printStackTrace();
			}
		}
		
		//材料出库列表
		public String materialOutWarehouseList(){
			try{
				//String page=this.getRequest().getParameter("pageIndex");//Integer.valueOf(page);
				int pageIndex = getPageIndex();
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("owh.billCode,wpo.billCode", keyWord);
				String sortCon="";
				if(!CommonUtil.isNullString(keyWord)){
					List<AaGoods> goods=cacheCtrlService.setGoods();
					for (AaGoods aaGoods : goods) {
						String gid="";
						if (aaGoods.getGoodsname().equals(keyWord)||aaGoods.getGoodscode().equals(keyWord)) {
							gid+=aaGoods.getGid();
							sortCon+="or wmc.goodsUid like '%"+gid+"%'";
						}						
					}
				}
				sortCon=" "+sortCon+")";
				condition=condition.replace(")", sortCon);
				setRequstAttribute("keyWord",keyWord);
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
				
				PageBean list = wareHouseService.getAllListMaterialout(pageIndex, pageSize, condition);
				for(int i=0;i<list.getList().size();i++){
						if(!CommonUtil.isNullString(((WmMaterialoutC)list.getList().get(i)).getRecordPerson())){
						YmUser ymuser = cacheCtrlService.getUser(((WmMaterialoutC)list.getList().get(i)).getRecordPerson().toString());
						if(!CommonUtil.isNullObject(ymuser)){
							((WmMaterialoutC)list.getList().get(i)).setRecordPersonName(ymuser.getUserName());
						}
					   }
						if(!CommonUtil.isNullString(((WmMaterialoutC)list.getList().get(i)).getDepartId())){
							AaDepartment department = cacheCtrlService.getDepartment(((WmMaterialoutC)list.getList().get(i)).getDepartId().toString());
							if(!CommonUtil.isNullObject(department)){
								((WmMaterialoutC)list.getList().get(i)).setDepartName(department.getDepname());
							}
						}
						if(!CommonUtil.isNullString(((WmMaterialoutC)list.getList().get(i)).getWhUid())){
							AaWarehouse warehouse  = cacheCtrlService.getWareHouse(((WmMaterialoutC)list.getList().get(i)).getWhUid().toString());
							if(!CommonUtil.isNullObject(warehouse)){
								((WmMaterialoutC)list.getList().get(i)).setWareHouseName(warehouse.getWhname());
							}
						}
						if (!CommonUtil.isNullString(((WmMaterialoutC)list.getList().get(i)).getGoodName())) {
							AaGoods good=cacheCtrlService.getGoods(((WmMaterialoutC)list.getList().get(i)).getGoodName());
							((WmMaterialoutC)list.getList().get(i)).setGoodName(good.getGoodsname());
						}
				}
				for(int i=0;i<list.getList().size();i++){
					AaGoods good = cacheCtrlService.getGoods(((WmMaterialoutC)list.getList().get(i)).getGoodsuid().toString());
					((WmMaterialoutC)list.getList().get(i)).setGood(good);
					AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((WmMaterialoutC)list.getList().get(i)).getGoodsallocationuid());
					if(alocation != null){
						((WmMaterialoutC)list.getList().get(i)).setAlocation(alocation.getName());
					}
				}
				setRequstAttribute("data", list);
				//setRequstAttribute("outlist", listMaterialout);
			}catch(Exception e){
				e.printStackTrace();
			}
			return "materialOutList";
		}
	//...................................................库存操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	

	public String allocationStockList(){
		try{
			
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String goodscode = getParameter("goodscode");//物料编码
			String goodsname = getParameter("goodsname");//物料名称
			String[] warehouses = getRequest().getParameterValues("xsry");//仓库子集
			StringBuffer sb = new StringBuffer();

			if(warehouses != null && warehouses.length >0 && !"".equals(warehouses[0])){
				String codes = "";
				for(int i = 0;i<warehouses.length;i++){
					String tempcode = warehouses[i];
//					System.out.println(tempcode);
					codes+= "'"+tempcode+"',";
				}
				codes += "'-1'";

				sb.append(" and    owh.whCode in ("+codes+") ");
			}


			if(goodscode != null && !"".equals(goodscode)){
				sb.append(" and ag.goodsCode like '%"+goodscode+"%' ");

			}
			if(goodsname != null && !"".equals(goodsname)){
				sb.append(" and  ag.goodsName like '%"+goodsname+"%' ");

			}
			String  condition = sb.toString();

		setRequstAttribute("goodscode",goodscode);
		setRequstAttribute("goodsname",goodsname);
		if(warehouses != null){
			setRequstAttribute("warehouses",String.join(",",warehouses));
		}


		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and owh.sobGid='"+sobId+"' and owh.orgGid='"+orgId+"'";
		PageBean list = wareHouseService.getAllocationStockList(pageIndex, pageSize, condition);
		for(int i=0;i<list.getList().size();i++){
				if(!CommonUtil.isNullString(((WmAllocationstock)list.getList().get(i)).getGoodsuid())){
					AaGoods good = cacheCtrlService.getGoods(((WmAllocationstock)list.getList().get(i)).getGoodsuid().toString());
					if(!CommonUtil.isNullObject(good)){
						((WmAllocationstock)list.getList().get(i)).setGoodname(good.getGoodsname());
						((WmAllocationstock)list.getList().get(i)).setClassificationName(good.getUnitName());
						((WmAllocationstock)list.getList().get(i)).setCstComUnitName(good.getCstComUnitName());
					}
				}
				if(!CommonUtil.isNullString(((WmAllocationstock)list.getList().get(i)).getGoodsallocationuid())){
					AaGoodsallocation alocation=cacheCtrlService.getGoodsAllocation(((WmAllocationstock)list.getList().get(i)).getGoodsallocationuid().toString());
					if(!CommonUtil.isNullObject(alocation)){
						((WmAllocationstock)list.getList().get(i)).setAllocationname(alocation.getName());
						AaWarehouse warehouse  = cacheCtrlService.getWareHouse(alocation.getWhuid());
						if(!CommonUtil.isNullObject(warehouse)){
							((WmAllocationstock)list.getList().get(i)).setWhname(warehouse.getWhname());
						}
					}
					
				}
				
		}
		setRequstAttribute("data", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "allocationStockList";
	}

	public void getWareHouseList(){
		List list = wareHouseService.getwarehouseList();
		try {
			getResponse().getWriter().write(JSON.toJSONString(list));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}



	public void downorprint(){
		String outname=getParameter("outname");
		String type=getParameter("type");
//		String serachEm=getParameter("serachEm");
		String dataname=getParameter("dataname");
		String titlelist=getParameter("titlelist");



		String goodscode = getParameter("goodscode");//物料编码
		String goodsname = getParameter("goodsname");//物料名称
		String warehouses = getParameter("warehouses");//仓库子集



		StringBuffer sb = new StringBuffer();

		if(warehouses != null  && !"".equals(warehouses)){
			String codes = "";
			String[] tem = warehouses.split(",");
			for(int i = 0;i<tem.length;i++){
				String tempcode = tem[i];
				codes+= "'"+tempcode+"',";
			}
			codes += "'-1'";

			sb.append(" and    owh.whCode in ("+codes+") ");
		}


		if(goodscode != null && !"".equals(goodscode)){
			sb.append(" and ag.goodsCode like '%"+goodscode+"%' ");

		}
		if(goodsname != null && !"".equals(goodsname)){
			sb.append(" and  ag.goodsName like '%"+goodsname+"%' ");

		}
		String  condition = sb.toString();




		List<Map> pb =downloadprintdata("",dataname,condition);
		try {
			if(type.equals("down")){
				//生成excel
				double totalmoney = 0;
				double discountedtotalPrice = 0;
				double shouldtotalprice = 0;
				Map mapforkc = new HashMap();
				List<Map> classinfo = new ArrayList<Map>();
				if(dataname.equals("graveWallYear")){
//					classinfo = reportService.getClassListInfo();

				}
				HSSFWorkbook workbook=createxcel(outname,JSONObject.fromObject(titlelist),pb,totalmoney,discountedtotalPrice,shouldtotalprice,classinfo,dataname,mapforkc);
				getResponse().reset();
				getResponse().setContentType("application/vnd.ms-excel");
				getResponse().setHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(outname + ".xls", "UTF-8"));
				workbook.write(getResponse().getOutputStream());
				getResponse().getOutputStream().flush();
				getResponse().getOutputStream().close();

			}else{
				getResponse().getWriter().write(JSONObject.fromObject(pb).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}






	}



	public List<Map> downloadprintdata(String serachEm, String dataname, String condition){


		List<Map> pb =null;
		if(dataname.equals("allocationstock")){
			pb = wareHouseService.getReportList(0, 0,serachEm,condition);
		}

		return pb;
	}




	public HSSFWorkbook createxcel(String title, JSONObject titlejso, List<Map> datalist, double totalmoney, double discountedtotalPrice, double shouldtotalprice, List<Map> classinfo, String dataname, Map mapforkc) throws ParseException,
			IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		/******************* 样式定义开始 *******************/
		// 定义全局文本样式，也可为某行某列单独定义
		HSSFCellStyle style = workbook.createCellStyle();
//	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 字体设置
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");// 设置字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置字体加粗
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 设置文本居中
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));//设置单元格格式 文本-text

		HSSFCellStyle style1 = workbook.createCellStyle();
//	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 字体设置
		HSSFFont font1 = workbook.createFont();
		font1.setFontName("宋体");// 设置字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font1.setFontHeightInPoints((short) 12);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置字体加粗
		style1.setFont(font);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置文本居中
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFDataFormat format1 = workbook.createDataFormat();

		style1.setDataFormat(format1.getFormat("@"));//设置单元格格式 文本-text


		//TODO yurh 2017 -08-10
		HSSFCellStyle style3 = workbook.createCellStyle();
//	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 字体设置
		HSSFFont font3 = workbook.createFont();
		font3.setFontName("宋体");// 设置字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font3.setFontHeightInPoints((short) 12);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置字体加粗
		style3.setFont(font);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置文本居中
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFDataFormat format3 = workbook.createDataFormat();
//HSSFDataFormat.getBuiltinFormat("0.00")   内建的自定义格式
		style3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.0000"));//设置单元格格式 文本-text
		//TODO yurh 2017 -08-10


		HSSFCellStyle style2 = workbook.createCellStyle();
//	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 字体设置
		HSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");// 设置字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font2.setFontHeightInPoints((short)32);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置字体加粗
		style2.setFont(font);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置文本居中
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFDataFormat format2 = workbook.createDataFormat();

		style2.setDataFormat(format2.getFormat("@"));//设置单元格格式 文本-text





		//给安息堂/公墓 库存数量（新增报表） 设置的样式 2018-1-10
		HSSFCellStyle styleforkc = workbook.createCellStyle();
		// 字体设置
		HSSFFont fontforkc = workbook.createFont();
		fontforkc.setFontName("宋体");// 设置字体
		fontforkc.setFontHeightInPoints((short) 12);// 设置字体大小
		styleforkc.setFont(fontforkc);
		styleforkc.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置文本居中
		styleforkc.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleforkc.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleforkc.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleforkc.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleforkc.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));//设置单元格格式
		/******************* 样式定义结束 *******************/



		//设置sellname方法  cellName
		Iterator it = titlejso.keys();
		int  step=0;
		while (it.hasNext()) {
			step++;
			it.next();
		}
		String [] cellName;
		String [] keyNames;

		cellName=new String[step];
		keyNames=new String[step];

		Iterator its = titlejso.keys();
		int  steps=0;
		while (its.hasNext()) {
			String key=its.next().toString();
			cellName[steps]=titlejso.getString(key);
			keyNames[steps]=key;
			steps++;
		}




		// 设置模板sheet
		HSSFSheet sheet1 = workbook.createSheet();
		for(int n=0;n<cellName.length;n++){
			sheet1.setDefaultColumnStyle(n, style);//前n列默认文本格式
		}
		// 设置模板名称，防止中文乱码，重要
		workbook.setSheetName(0, "sheet1");
		HSSFRow rowm = sheet1.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);
		CellRangeAddress region = new CellRangeAddress(0, 1, 0, (cellName.length-1)); //合并单元格
		sheet1.addMergedRegion(region);
		int border = 1; //边框宽度
		RegionUtil.setBorderBottom(border,region, sheet1, workbook);  //单元格加边框底部
		RegionUtil.setBorderLeft(border,region, sheet1, workbook);  //单元格左边
		RegionUtil.setBorderRight(border,region, sheet1, workbook);  //单元格右边
		RegionUtil.setBorderTop(border,region, sheet1, workbook);  //单元格上边
		cellTiltle.setCellValue(title);
		cellTiltle.setCellStyle(style1);
		// 添加标题行
		HSSFRow row_title = sheet1.createRow((short)2);
		// 添加标题行所属的列
		for (int i = 0; i < cellName.length; i++) {
			HSSFCell cell = row_title.createCell((short) (i));
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 设置编码格式，防止中文乱码，重要
			cell.setCellValue(cellName[i]);
			// sheet1.setColumnWidth((short) i, (short) length);
			HSSFCellStyle styletitle = workbook.createCellStyle();
			HSSFFont fonttitle = workbook.createFont();
			// fonttitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//fonttitle.setFontHeightInPoints((short) 12);
			styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styletitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styletitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styletitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styletitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// fonttitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fonttitle.setFontName("黑体");
			styletitle.setFont(fonttitle);
			cell.setCellStyle(styletitle);
			// 设置文本样
		}

		int totlerow = 0;
		Map map = new HashMap();

		String tempmouth = "";
		Double tempshouldprice = 0.00;
		Double tempdiscountedPrice = 0.00;
		Double tempprice = 0.00;

		String tempareaname = "";




		for (Map<String,Object> m:datalist) {
			int cellnum=0;

			HSSFRow row = sheet1.createRow(totlerow +3);// 生成一行

			for(String keys:keyNames){
				String a = "";
				for (String key : m.keySet()) {

					if(keys.equals(key)){
						a = "flag";
						//明细单独处理？
						HSSFCell cell = row.createCell((short) cellnum);
						if(key.equals("number")){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}else{
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}

						if(CommonUtil.notNullObject(m.get(key))){
							if(key.equals("number")){
								cell.setCellValue(new BigDecimal(m.get(key).toString()).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
								cell.setCellStyle(style3);
							}else{
								cell.setCellValue(m.get(key).toString());
								cell.setCellStyle(style1);
							}
						}else{
							cell.setCellValue("");
							cell.setCellStyle(style1);
						}

						cellnum++;
					}
				}

				if("".equals(a)){
					HSSFCell cell = row.createCell((short) cellnum);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(0);
					cell.setCellStyle(style3);
					cellnum++;
				}


			}
			totlerow++;


		}


		for (int i = 0; i < (cellName.length+2); i++) {
			sheet1.autoSizeColumn((short) i);
		}

		return workbook;
	}














}
