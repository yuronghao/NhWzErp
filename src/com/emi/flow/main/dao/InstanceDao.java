package com.emi.flow.main.dao;

import java.util.List;
import java.util.Map;

import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.bean.FLOW_History;
import com.emi.flow.main.bean.FLOW_HistoryMain;
import com.emi.flow.main.bean.FLOW_Instance;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.sys.core.bean.PageBean;

public interface InstanceDao {

	/**
	 * @category 流程实例列表
	 * 2015年1月15日 上午9:49:49 
	 * @author 朱晓陈
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getInstanceList(String condition, int pageIndex, int pageSize);

	/**
	 * @category 实例绑定的表单
	 * 2015年1月15日 上午11:15:49 
	 * @author 朱晓陈
	 * @param instanceId
	 * @return
	 */
	public FLOW_Form getInstanceForm(String instanceId);

	public FLOW_Instance findInstance(String instanceId);

	/**
	 * @category 插入绑定数据表数据
	 * 2015年1月16日 上午10:05:41 
	 * @author 朱晓陈
	 * @param table
	 * @param values
	 * @return
	 */
	public boolean insertData(FLOW_Table table, Map<String, String> valuesMap);

	/**
	 * @category 更新绑定数据表的数据
	 * 2015年1月16日 上午10:05:41 
	 * @author 朱晓陈
	 * @param table
	 * @param values
	 * @return
	 */
	public boolean updateData(FLOW_Table table, Map<String, String> valuesMap);

	/**
	 * @category 插入流程历史记录
	 * 2015年1月16日 下午1:12:28 
	 * @author 朱晓陈
	 * @param history
	 * @return
	 */
	public boolean insertHistory(Object historys);

	/**
	 * @category 更新流程记录
	 * 2015年1月16日 下午1:18:25 
	 * @author 朱晓陈
	 * @param history
	 * @return
	 */
	public boolean updateHistory(Object historys);

	/**
	 * @category 待办、已办列表
	 * 2015年1月16日 下午5:13:43 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param userId
	 * @param condition
	 * @return
	 */
	public PageBean getMyList(int pageIndex, int pageSize, String userId,
			String condition,int completeStatus);

	/**
	 * @category 查询流程记录
	 * 2015年1月20日 上午10:21:35 
	 * @author 朱晓陈
	 * @param historyId
	 * @return
	 */
	public FLOW_History findHistory(String historyId);

	/**
	 * @category 查询自定义表的数据
	 * 2015年1月20日 上午11:21:48 
	 * @author 朱晓陈
	 * @param dataId
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public Map queryData(String dataId, String tableName, String columns);

	/**
	 * @category 流转历史记录
	 * 2015年1月23日 下午5:07:36 
	 * @author 朱晓陈
	 * @param instanceId
	 * @return
	 */
	public List<FLOW_History> getHistoryList(String instanceId, String dataId);

	/**
	 * @category 未流转的后续步骤
	 * 2015年1月26日 上午8:36:30 
	 * @author 朱晓陈
	 * @param lastHistory
	 * @return
	 */
	public List<FLOW_History> getNotDoneHistorys(FLOW_History lastHistory);

	/**
	 * @category 已办结列表
	 * 2015年2月7日 下午4:49:46 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param userId
	 * @param condition
	 * @return
	 */
	public PageBean getOverList(int pageIndex, int pageSize, String userId,
			String condition);

	/**
	 * 根据步骤历史id更新历史主表状态
	 * @category 
	 * 2015年2月9日 上午8:53:17 
	 * @author 朱晓陈
	 * @param historyId
	 * @param status
	 */
	public void updateHistoryMain(FLOW_HistoryMain main);

	/**
	 * @category 插入步骤历史主表数据
	 * 2015年2月9日 上午9:09:11 
	 * @author 朱晓陈
	 * @param main
	 * @return
	 */
	public boolean insertHistoryMain(FLOW_HistoryMain main);

	/**
	 * @category 根据步骤记录id获取主表对象
	 * 2015年2月9日 上午9:15:42 
	 * @author 朱晓陈
	 * @param historyId
	 * @return
	 */
	public FLOW_HistoryMain getHistoryMain(String historyId);

	/**
	 * @category 更新数据状态
	 * 2015年2月9日 下午1:02:44 
	 * @author 朱晓陈
	 * @param tableId
	 * @param dataId
	 * @param i
	 * @return
	 */
	public boolean updateDataStatus(String tableId, String dataId, int i);

	/**
	 * @category 作废
	 * 2015年2月28日 下午4:02:51 
	 * @author 朱晓陈
	 * @param historyMainId
	 * @return
	 */
	public boolean cancelFlow(String historyMainId);


}
