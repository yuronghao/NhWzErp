
package com.emi.wms.servicedata.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.emi.android.bean.ProcessTaskDetailRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.WmBillType;
import com.emi.wms.bean.WmTask;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.TaskDao;

/**
 * @author Administrator
 *
 */
public class TaskService extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = 6006303492557475142L;
	private TaskDao taskDao;
	private PurchaseArrivalService purchaseArrivalService;
	private ProduceOrderService produceOrderService;
    private SaleService saleService;
    private CacheCtrlService cacheCtrlService;
    private ProduceOrderDao produceOrderDao;

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public PurchaseArrivalService getPurchaseArrivalService() {
		return purchaseArrivalService;
	}

	public ProduceOrderService getProduceOrderService() {
		return produceOrderService;
	}

	@Override
	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public ProduceOrderDao getProduceOrderDao() {
		return produceOrderDao;
	}

	public void setProduceOrderDao(ProduceOrderDao produceOrderDao) {
		this.produceOrderDao = produceOrderDao;
	}



	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}



	public SaleService getSaleService() {
		return saleService;
	}



	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}



	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}



	public void setPurchaseArrivalService(
			PurchaseArrivalService purchaseArrivalService) {
		this.purchaseArrivalService = purchaseArrivalService;
	}



	public void setProduceOrderService(ProduceOrderService produceOrderService) {
		this.produceOrderService = produceOrderService;
	}


	//仓储的任务列表
	public PageBean getTaskListWms(int pageIndex,int pageSize,String status,String taskTypeCode,String acceptUserGid,String billCode){
		
		String condition=" and wmtask.state<>2 ";
		if(!CommonUtil.isNullObject(status)){
			condition=" and wmtask.state='"+status+"'";
		}
		
		if(!CommonUtil.isNullObject(taskTypeCode)){
			condition=condition+" and wmbilltype.billCode in ("+taskTypeCode+")";
		}
		if(CommonUtil.isNullObject(taskTypeCode)){
			condition=condition+" and wmbilltype.billCode='' ";
		}
		
		//不需要通过人员来限制
//		if(status.equalsIgnoreCase("1")){
//			condition=condition+" and wmtask.acceptUserGid='"+acceptUserGid+"'";
//		}
		if(!CommonUtil.isNullObject(billCode)){
			condition=condition+" and wmtask.billCode='"+billCode+"'";
		}
		
		PageBean pb=taskDao.getTaskListWms(pageIndex,pageSize,condition);
		
		List<WmTask> wt=pb.getList();
		for(WmTask w:wt){
			if(w.getTaskTypeCode().equalsIgnoreCase(Constants.TASKTYPE_CPRK) && w.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CPZJ)){
				
				WmsTaskDetailRsp wtdr=produceOrderService.processProductInCheckDetail(w.getBillgid(),Constants.TASKTYPE_CPRK);
				List<WmsGoods> wgs=wtdr.getWmsGoodsLists();
				w.setGoodsName(wgs.get(0).getGoodsName());
				w.setGoodsStandard(wgs.get(0).getGoodsStandard());
				
				
			}else if(w.getTaskTypeCode().equalsIgnoreCase(Constants.TASKTYPE_CPRK) && w.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_SCDDGY)){
				
				WmsTaskDetailRsp wtdr=produceOrderService.processProductInDetailNoBatch(w.getBillgid());
				List<WmsGoods> wgs=wtdr.getWmsGoodsLists();
				w.setGoodsName(wgs.get(0).getGoodsName());
				w.setGoodsStandard(wgs.get(0).getGoodsStandard());
				
			}else if(w.getTaskTypeCode().equalsIgnoreCase(Constants.TASKTYPE_CPRK) && w.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_BGD)){
				
				
				condition=" ro.gid='"+w.getBillgid()+"'";
				List<Map> reports=produceOrderDao.getReports(condition);
				
				condition=" gid='"+reports.get(0).get("produceProcessRouteCGid")+"'";
				MesWmProduceProcessroutec mwpc=produceOrderDao.getMesWmProduceProcessroutec(condition);//工艺路线子表
				
				MesWmStandardprocess mesWmStandardprocess=cacheCtrlService.getMESStandardProcess(mwpc.getOpGid());//标准工序
				
				BigDecimal waitNum=new BigDecimal(0);
				for(Map reportMap:reports){
					
					waitNum=waitNum.add( CommonUtil.object2BigDecimal(reportMap.get("reportOkNum")).subtract(CommonUtil.object2BigDecimal(reportMap.get("productInNum"))) );//报工合格减去已入库
					
				}
				
				w.setBatch(CommonUtil.isNullObject(reports.get(0).get("batch"))?"":reports.get(0).get("batch").toString());
				w.setRoute(mesWmStandardprocess.getOpname());
				w.setWaitNum(waitNum.toPlainString());
				
			}
			
		}
		
		return pb;
	}
	
	//生产的任务列表
	public PageBean getProcessTaskList(int pageIndex,int pageSize,String status,String taskTypeCode,String acceptUserGid,String billCode){

		String condition=" and wmtask.state<>2 ";
		if(!CommonUtil.isNullObject(status)){
			condition=" and wmtask.state='"+status+"'";
		}
		
		if(!CommonUtil.isNullObject(taskTypeCode)){
			condition=condition+" and wmbilltype.billCode in ("+taskTypeCode+")";
		}
		if(CommonUtil.isNullObject(taskTypeCode)){
			condition=condition+" and wmbilltype.billCode='' ";
		}
		
		//不需要通过人员来限制
//		if(status.equalsIgnoreCase("1")){
//			condition=condition+" and wmtask.acceptUserGid='"+acceptUserGid+"'";
//		}
		
		if (!CommonUtil.isNullObject(billCode)) {
			condition=condition+" and wmtask.billCode='"+billCode+"'";
		}
		
		PageBean pb=taskDao.getProcessTaskList(pageIndex,pageSize,condition);
		List<WmTask> wt=pb.getList();
		for(WmTask w:wt){
			AaGoods g=cacheCtrlService.getGoods(w.getGoodsGid());
			if(!CommonUtil.isNullObject(g)){
				w.setGoodsName(g.getGoodsname());
			}
			
			AaWarehouse ah=cacheCtrlService.getWareHouse(w.getWhUid());
			if(!CommonUtil.isNullObject(ah)){
				w.setWhName(ah.getWhname());
			}
			
		}
		
		return pb;
				
	}
	
	/**
	 * @category 返回任务详情
	 *2016 2016年4月5日下午4:57:47
	 *WmsTaskDetail
	 *宋银海
	 */
	public WmsTaskDetailRsp getWmsTaskDetail(String taskGid){
		WmTask wmtask = taskDao.getTask(taskGid);
		String taskTypeCode=wmtask.getTaskTypeCode();//根据任务gid获得任务类型（从数据库查）
		String billGidSource=wmtask.getBillGidSource();//单据来源（从数据库查）
		String billGid=wmtask.getBillgid();//单据gid(从数据库查)
		
		WmsTaskDetailRsp wmsTaskDetail=new WmsTaskDetailRsp();
		if(Constants.TASKTYPE_CGZJ.equalsIgnoreCase(taskTypeCode)){//采购质检                           ////////////////////////////////////////采购开始
			wmsTaskDetail = purchaseArrivalService.getwmtaskcheck(billGid);
			
		}else if(Constants.TASKTYPE_CGRK.equalsIgnoreCase(taskTypeCode)){//采购入库任务详情
			
			if(Constants.BILLGIDSOURCE_CGDH.equalsIgnoreCase(billGidSource)){//单据来源（到货单）
				wmsTaskDetail = purchaseArrivalService.getwmtask(billGid);
			}else if(Constants.BILLGIDSOURCE_CGZJ.equalsIgnoreCase(billGidSource)){//单据来源（质检单）
				wmsTaskDetail=purchaseArrivalService.procureCheckDetail(billGid);
			}
			else if(Constants.BILLGIDSOURCE_CGD.equalsIgnoreCase(billGidSource)){//单据来源（采购订单） 2018-3-6 yurh
				wmsTaskDetail=purchaseArrivalService.getwmtaskForCGD(billGid);
			}



			
		}else if(Constants.TASKTYPE_CGTHCK.equalsIgnoreCase(taskTypeCode)){
			
			if(Constants.BILLGIDSOURCE_CGDH.equalsIgnoreCase(billGidSource)){//单据来源（到货单）
				wmsTaskDetail = purchaseArrivalService.getwmtask(billGid);
			}else if(Constants.BILLGIDSOURCE_CGZJ.equalsIgnoreCase(billGidSource)){//单据来源（质检单）
				wmsTaskDetail=purchaseArrivalService.procureCheckDetail(billGid);
			}                                                                           ////////////////////////////////////////采购结束
			
		}else if(Constants.TASKTYPE_XSZJ.equalsIgnoreCase(taskTypeCode)){//销售质检              ////////////////////////////////////////销售开始
			
			wmsTaskDetail=saleService.getSaleCheck(billGid);
			
		}else if(Constants.TASKTYPE_XSCK.equalsIgnoreCase(taskTypeCode)){//销售出库
			
			if(Constants.BILLGIDSOURCE_XSFH.equalsIgnoreCase(billGidSource)){//单据来源（发货单）
				wmsTaskDetail=saleService.saleSendDetail(billGid);
			}else if(Constants.BILLGIDSOURCE_XSZJ.equalsIgnoreCase(billGidSource)){//单据来源（质检单）
				wmsTaskDetail=saleService.saleSendCheckDetail(billGid);
			}                                                                         
			
		}else if(Constants.TASKTYPE_XSTHRK.equalsIgnoreCase(taskTypeCode)){
			
			if(Constants.BILLGIDSOURCE_XSFH.equalsIgnoreCase(billGidSource)){//单据来源（发货单）
				wmsTaskDetail=saleService.saleSendDetail(billGid);
			}else if(Constants.BILLGIDSOURCE_XSZJ.equalsIgnoreCase(billGidSource)){//单据来源（质检单）
				wmsTaskDetail=saleService.saleSendCheckDetail(billGid);
			}                                                                            ////////////////////////////////////////销售结束
		}else if(Constants.TASKTYPE_GXLL.equalsIgnoreCase(taskTypeCode)){//工序普通领料
			
			if(Constants.BILLGIDSOURCE_SCDDGY.equalsIgnoreCase(billGidSource)){//单据来源（生产订单工艺表）
				
				//读取参数 触发工序领料任务是否按仓库来触发
				if((CommonUtil.isNullObject(wmtask.getIsMeterialTaskByWhouse())?0:wmtask.getIsMeterialTaskByWhouse().intValue())==0){//工序领料未按仓库拆分
					wmsTaskDetail=produceOrderService.processMaterialOutDetail(billGid,taskTypeCode);
				}else if((wmtask.getIsMeterialTaskByWhouse().intValue())==1){//工序领料按仓库拆分
					wmsTaskDetail=produceOrderService.processMaterialOutDetail(billGid,taskTypeCode,wmtask.getWhUid());
				}
				
				
			}
			
		}else if(Constants.TASKTYPE_CZLL.equalsIgnoreCase(taskTypeCode)){//工序称重领料
			
			if(Constants.BILLGIDSOURCE_SCDDGY.equalsIgnoreCase(billGidSource)){//单据来源（生产订单工艺表）
				wmsTaskDetail=produceOrderService.processMaterialOutDetail(billGid,taskTypeCode,null);
			}
			
		}else if(Constants.TASKTYPE_CPRK.equalsIgnoreCase(taskTypeCode)){//产品入库
			
			if(Constants.BILLGIDSOURCE_SCDDGY.equalsIgnoreCase(billGidSource)){//单据来源（生产订单工艺路线子表）
				wmsTaskDetail=produceOrderService.processProductInDetail(billGid,taskTypeCode);
			}else if(Constants.BILLGIDSOURCE_CPZJ.equalsIgnoreCase(billGidSource)){//单据来源（质检单）
				wmsTaskDetail=produceOrderService.processProductInCheckDetail(billGid,taskTypeCode);
			}else if(Constants.BILLGIDSOURCE_BGD.equalsIgnoreCase(billGidSource)){//单据来源（报工）
				wmsTaskDetail=produceOrderService.processProductInDetailByReport(billGid,taskTypeCode);
			}
			
		}else if(Constants.TASKTYPE_CPZJ.equalsIgnoreCase(taskTypeCode)){//产品质检
			
			if(Constants.BILLGIDSOURCE_SCDDGY.equalsIgnoreCase(billGidSource)){//单据来源（生产订单工艺路线子表）
				wmsTaskDetail=produceOrderService.processProductCheckDetail(billGid);
			}
		}else if(Constants.TASKTYPE_WWCLCK.equalsIgnoreCase(taskTypeCode)){//委外材料出库
			
			wmsTaskDetail = purchaseArrivalService.getmOMaterialsTask(billGid);
			
		}else if(Constants.TASKTYPE_WWCPRK.equalsIgnoreCase(taskTypeCode)){//委外产品入库
			
			if(Constants.BILLGIDSOURCE_WWDH.equalsIgnoreCase(billGidSource)){//单据来源（委外到货单）
				wmsTaskDetail = purchaseArrivalService.getOutArrivalTask(billGid);
			}else if(Constants.BILLGIDSOURCE_WWZJ.equalsIgnoreCase(billGidSource)){//单据来源（委外质检单）
				wmsTaskDetail=purchaseArrivalService.omCheckDetail(billGid);
			} 
			
		}else if(Constants.TASKTYPE_WWCPZJ.equalsIgnoreCase(taskTypeCode)){//委外产品质检
			
			wmsTaskDetail = purchaseArrivalService.getoMordercheck(billGid);
		}
		
		return wmsTaskDetail;
	}
	
	
	/**
	 * @category 开工时获得相关任务详情（暂不用）
	 *2016 2016年4月12日下午1:47:34
	 *ProcessTaskDetailRsp
	 *宋银海
	 */
//	public ProcessTaskDetailRsp getProcessTaskDetail(String taskGid){
//		WmTask wmtask = taskDao.getTask(taskGid);
//		String taskTypeCode=wmtask.getTaskTypeCode();//根据任务gid获得任务类型（从数据库查）
//		String billGidSource=wmtask.getBillGidSource();//单据来源（从数据库查）
//		String billGid=wmtask.getBillgid();//单据gid(从数据库查)
//		
//		ProcessTaskDetailRsp processTaskDetailRsp=new ProcessTaskDetailRsp();
//		
//		if(Constants.TASKTYPE_PGD.equalsIgnoreCase(taskTypeCode)){//正常派工任务详情
//			if(Constants.BILLGIDSOURCE_SCDDGY.equalsIgnoreCase(billGidSource)){//单据来源（生产订单工艺路线表）
//				processTaskDetailRsp=produceOrderService.disTaskDetail(billGid);
//			}
//		}else if(Constants.TASKTYPE_BGD.equalsIgnoreCase(taskTypeCode)){//正常报工任务详情 
//			
//		}else if(Constants.TASKTYPE_BGZJ.equalsIgnoreCase(taskTypeCode)){//工序质检任务详情
//			
//		}
//		
//		return processTaskDetailRsp;
//	}
	
	
	/**
	 * @category 接收任务
	 *2016 2016年4月12日下午4:10:23
	 *boolean
	 *宋银海
	 */
	public JSONObject acceptTask(String userGid,String taskGid){
		JSONObject jsonObject=new JSONObject();
		WmTask wmtask = taskDao.getTask(taskGid);
		if(wmtask.getState().intValue()!=0){
			jsonObject.put("success", 0);
			jsonObject.put("failInfor", "任务已接收,请重新选择任务！");
			return jsonObject;
		}else{
			taskDao.updateTaskState(taskGid, "1",userGid);//将任务改为接收状态
			jsonObject.put("success", 1);
			jsonObject.put("failInfor", "");
		}
		
		return jsonObject;
	}
	
	
	/**
	 * @category 取消任务
	 *2016 2016年4月14日上午8:50:40
	 *JSONObject
	 *宋银海
	 */
	public JSONObject cancelTask(String taskGid){
		JSONObject jsonObject=new JSONObject();
		WmTask wmtask = taskDao.getTask(taskGid);
		if(wmtask.getState().intValue()!=1){
			jsonObject.put("success", 0);
			jsonObject.put("failInfor", "任务未接收或者已结束,请重新选择任务！");
			return jsonObject;
		}else{
			taskDao.updateTaskState(taskGid, "0","");//将任务改为未接收状态
			jsonObject.put("success", 1);
			jsonObject.put("failInfor", "");
		}
		
		return jsonObject;
	}
	
	
	
	
	/**
	 * @category 创建任务
	 *2016 2016年4月19日上午11:21:46
	 *boolean
	 *宋银海
	 */
	public boolean createTask(Object wmTaskBeanOrList){
		
		List<WmTask> wmTasks=new ArrayList<WmTask>();
		List<WmBillType> wmBillType=this.getWmBillType();//单据类型基础表
		
		if(wmTaskBeanOrList instanceof ArrayList){
			wmTasks=(List<WmTask>)wmTaskBeanOrList;
			for(WmTask wt:wmTasks){
				wt.setGid(UUID.randomUUID().toString());
				for(WmBillType wb:wmBillType){
					if(wt.getTasktypeuid().equalsIgnoreCase(wb.getBillCode())){
						wt.setTasktypeuid(wb.getGid());
						wt.setTaskname(wb.getBillName());
						break;
					}
				}
				
				wt.setState(0);
				wt.setDistributetime(new Date());
				wt.setCompletetime(new Date());
			}
			
		}else{
			WmTask wmTask=(WmTask)wmTaskBeanOrList;
			wmTask.setGid(UUID.randomUUID().toString());
			for(WmBillType wb:wmBillType){
				if(wmTask.getTasktypeuid().equalsIgnoreCase(wb.getBillCode())){
					wmTask.setTasktypeuid(wb.getGid());
					wmTask.setTaskname(wb.getBillName());
					break;
				}
			}
			
			wmTask.setState(0);
			wmTask.setDistributetime(new Date());
			wmTask.setCompletetime(new Date());
			wmTasks.add(wmTask);
		}
		
		return taskDao.createTask(wmTasks);
	}

	
	
	
	
}
