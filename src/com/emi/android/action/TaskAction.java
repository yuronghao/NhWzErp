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
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.android.bean.ProcessTaskDetailRsp;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.common.action.BaseAction;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmTask;
import com.emi.wms.servicedata.service.ProduceOrderService;
import com.emi.wms.servicedata.service.TaskService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

@SuppressWarnings(
{ "unused", "rawtypes" })
public class TaskAction extends BaseAction{
	private static final long serialVersionUID = -8568656084850818042L;
	
	private TaskService taskService;
	private ProduceOrderService produceOrderService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * @category 仓库任务列表
	 *2016 2016年3月25日下午2:25:22
	 *String
	 *杨峥铖
	 */
	public void getTaskList(){
		try {
			
			JSONObject json = getJsonObject();
			String userGid = json.getString("userGid");
			String status = json.getString("status");//0待办 1在办
			String taskTypeCode = json.getString("taskTypeCode");
			int pageIndex = json.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;//每页总条数
			PageBean tasklist = taskService.getTaskListWms(pageIndex,pageSize,status,taskTypeCode,userGid,null);
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",tasklist.getList());
//			System.out.println(EmiJsonObj.fromObject(reqmap).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 返回工序任务列表
	 *2016 2016年4月13日上午11:25:06
	 *void
	 *宋银海
	 */
	public void getProcessTaskList(){
		try {
			
			JSONObject json = getJsonObject();
			String userGid = json.getString("userGid");                 //用户uid
			String status = json.getString("status");                   //单据状态
			String taskTypeCode = json.getString("taskTypeCode");       //单据类型
			int pageIndex = json.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;							//每页总条数
			PageBean tasklist = taskService.getProcessTaskList(pageIndex,pageSize,status,taskTypeCode,userGid,null);
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",tasklist.getList());
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 获得任务详情(仓储管理)
	 *2016 2016年4月6日上午11:07:54
	 *void
	 *宋银海
	 */
	public void getTaskDetail(){
		try {
			
			JSONObject json = getJsonObject();
			String taskGid = json.getString("taskGid");
			WmsTaskDetailRsp wmsTaskDetailRsp = taskService.getWmsTaskDetail(taskGid);
//			System.out.println(EmiJsonObj.fromObject(wmsTaskDetailRsp).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(wmsTaskDetailRsp).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	
	/**
	 * @category 接收任务
	 *2016 2016年4月12日下午4:05:36
	 *void
	 *宋银海
	 */
	public void acceptTask(){
		try {
			JSONObject json = getJsonObject();
			String taskGid = json.getString("taskGid");
			String userGid = json.getString("userGid");
			JSONObject jsonObject = taskService.acceptTask(userGid,taskGid);
			getResponse().getWriter().write(jsonObject.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "接收失败！");
		}
	}
	
	
	/**
	 * @category 取消任务
	 *2016 2016年4月14日上午9:00:39
	 *void
	 *宋银海
	 */
	public void cancelTask(){
		try {
			JSONObject json = getJsonObject();
			String taskGid = json.getString("taskGid");
			JSONObject jsonObject = taskService.cancelTask(taskGid);
			getResponse().getWriter().write(jsonObject.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "取消失败！");
		}
	}
	/**
	 * @category 查询任务列表与仓储管理表
	 * 2016年9月9日10:27:19
	 * cuixn
	 */
	public void queryTaskList(){
		try{
			JSONObject json = getJsonObject();
			String userGid = json.getString("userGid");
			String status = json.getString("status");//0待办 1在办
			String taskTypeCode = json.getString("taskTypeCode");//用户权限
			//int pageIndex = json.getInt("pageIndex");					//页码，从1开始
			int pageSize = Config.PAGESIZE_MOB;	
			String billCode=json.getString("billCode");//任务表中的billcode
			String taskType=json.getString("taskType");//传入判断类型
			PageBean tasklist=new PageBean();
			if (taskType.equals("W")) {//判断为何种查询，W仓库管理列表/X工序列表
				tasklist = taskService.getTaskListWms(0,pageSize,status,taskTypeCode,userGid,billCode);//仓库列表(默认页码为0)
			}else if(taskType.equals("X")){
				tasklist = taskService.getProcessTaskList(0,pageSize,status,taskTypeCode,userGid,billCode);//返回工序列表（默认页码为0）
			}
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",tasklist.getList());
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	
	
}
