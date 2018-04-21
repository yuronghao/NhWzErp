/**
 * 
 */
package com.emi.wms.servicedata.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.WmTask;

/**
 * @author Administrator
 *
 */
public class TaskDao extends BaseDao{

	//仓储的任务列表
	public PageBean getTaskListWms(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("taskTypeCode", "WmTask.taskTypeCode");
		
		String sql = "select "+CommonUtil.colsFromBean(WmTask.class,"wmtask")+",wmbilltype.billCode taskTypeCode from wm_task wmtask  With (NoLock) "
				+ " left join WM_BillType wmbilltype on wmbilltype.gid = wmtask.taskTypeUid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return (PageBean)this.emiQueryList(sql,pageIndex, pageSize,WmTask.class,match,"billCode desc");
	}
	
	//生产的任务列表
	public PageBean getProcessTaskList(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("taskTypeCode", "WmTask.taskTypeCode");
		match.put("orderCode", "WmTask.orderCode");
		match.put("goodsGid", "WmTask.goodsGid");
		
		String sql = "select "+CommonUtil.colsFromBean(WmTask.class,"wmtask")+",wmbilltype.billCode taskTypeCode,po.billCode orderCode,"
				+ " poc.goodsUid goodsGid from wm_task wmtask WITH (NoLock) "
				+ " left join WM_BillType wmbilltype on wmbilltype.gid = wmtask.taskTypeUid "
				+ " left join MES_WM_ProduceProcessRouteC pprc WITH (NoLock) on pprc.gid=wmtask.billgid "
				+ " left join MES_WM_ProduceProcessRoute ppr WITH (NoLock) on pprc.produceRouteGid=ppr.gid "
				+ " left join WM_ProduceOrder po WITH (NoLock) on ppr.produceUid=po.gid "
				+ " left join WM_ProduceOrder_C poc WITH (NoLock) on ppr.produceCuid=poc.gid  where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return (PageBean)this.emiQueryList(sql,pageIndex, pageSize,WmTask.class,match,"taskTypeCode asc,completetime desc");
	}
	
	//根据gid 查询任务
	public WmTask getTask(String taskGid){
		Map match = new HashMap();
		match.put("taskTypeCode", "WmTask.taskTypeCode");
		String sql = "select "+CommonUtil.colsFromBean(WmTask.class,"wmtask")+",wmbilltype.billCode taskTypeCode from wm_task wmtask WITH (NoLock) left join WM_BillType wmbilltype on wmbilltype.gid = wmtask.taskTypeUid where wmtask.gid='"+taskGid+"'";
		return (WmTask)this.emiQuery(sql,WmTask.class,match);
	}

	//根据条码 查询任务
	public WmTask getTaskByBarcode(String barcode){
		Map match = new HashMap();
		match.put("taskTypeCode", "WmTask.taskTypeCode");
		String sql = "select "+CommonUtil.colsFromBean(WmTask.class,"wmtask")+",wmbilltype.billCode taskTypeCode from wm_task wmtask WITH (NoLock) left join WM_BillType wmbilltype on wmbilltype.gid = wmtask.taskTypeUid where wmtask.barcode='"+barcode+"'";
		return (WmTask)this.emiQuery(sql,WmTask.class,match);
	}
	
	
	/**
	 * @category 返回任务列表
	 *2016 2016年4月19日下午3:58:42
	 *List<WmTask>
	 *宋银海
	 */
	public List<WmTask> getWmTaskList(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmTask.class)+" from wm_task where "+condition;
		return this.emiQueryList(sql, WmTask.class);
	}
	
	
	/**
	 * @category 修改任务状态
	 *2016 2016年4月12日下午4:48:01
	 *int
	 *宋银海
	 */
	public int updateTaskState(String taskGid,String state){
		String sql="update wm_task set state='"+state+"' where gid='"+taskGid+"'";
		return this.update(sql);
	}
	
	
	public int updateTaskState(String taskGid,String state,String acceptUerGid){
		String sql="update wm_task set state='"+state+"',acceptUserGid='"+acceptUerGid+"' where gid='"+taskGid+"'";
		return this.update(sql);
	}
	
	
	/**
	 * @category 创建任务
	 *2016 2016年4月19日上午11:21:46
	 *boolean
	 *宋银海
	 */
	public boolean createTask(List<WmTask> wmTask){
		return this.emiInsert(wmTask);
	}
	
	

	
	
	
	
}
