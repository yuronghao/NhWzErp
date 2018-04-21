package com.emi.flow.main.service;

import java.util.List;
import java.util.Map;

import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.bean.FLOW_History;
import com.emi.flow.main.bean.FLOW_HistoryMain;
import com.emi.flow.main.bean.FLOW_Instance;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.util.UserInfo;

public interface InstanceService {

	/**
	 * @category 流程实例列表
	 * 2015年1月15日 上午9:48:54 
	 * @author 朱晓陈
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getInstanceList(String condition, int pageIndex, int pageSize);

	/**
	 * 根据实例id获取绑定的表单
	 * @category 实例绑定的表单
	 * 2015年1月15日 上午11:13:48 
	 * @author 朱晓陈
	 * @param instanceId
	 * @return
	 */
	public FLOW_Form getInstanceForm(String instanceId);

	/**
	 * @category 单个实例对象
	 * 2015年1月15日 上午11:39:14 
	 * @author 朱晓陈
	 * @param instanceId
	 * @return
	 */
	public FLOW_Instance findInstance(String instanceId);

	/**
	 * 保存表单数据及流程记录
	 * @category 保存表单
	 * 2015年1月15日 下午5:52:53 
	 * @author 朱晓陈
	 * @param fromUserId 发送来的人id
	 * @param userId	此用户id
	 * @param first	是否第一步
	 * @param values 对应各字段值
	 * @param formId 表单id
	 * @param nodeId 节点id
	 * @param flowId 流程id
	 * @param instanceId 实例id
	 * @param historyId	步骤记录id
	 * @param nextNodeId 下一发送的节点id
	 * @param userInfo 
	 * @return
	 */
	public int saveInstanceForm(String userId,String sendToUserId,boolean first,boolean last, Map<String, String> values, String formId,
			String nodeId, String flowId, String instanceId, String historyId, String nextNodeId,String comment,boolean reject, UserInfo userInfo);

	/**
	 * @category 待办列表
	 * 2015年1月16日 下午5:11:45 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param userId
	 * @param condition
	 * @return
	 */
	public PageBean getTodoList(int pageIndex, int pageSize, String userId,
			String condition);

	/**
	 * @category 已办列表
	 * 2015年1月16日 下午5:11:45 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param userId
	 * @param condition
	 * @return
	 */
	public PageBean getDoneList(int pageIndex, int pageSize, String userId,
			String condition);

	/**
	 * @category 查询流程记录
	 * 2015年1月20日 上午10:20:43 
	 * @author 朱晓陈
	 * @param historyId
	 * @return
	 */
	public FLOW_History findHistory(String historyId);

	/**
	 * @category 查询自定义表单的数据
	 * 2015年1月20日 上午10:50:02 
	 * @author 朱晓陈
	 * @param dataId
	 * @param tableId
	 * @param formDetails
	 * @return
	 */
	public Map queryData(String dataId, String tableId,
			List<FLOW_FormDetail> formDetails);

	/**
	 * @category 历史记录
	 * 2015年1月23日 下午5:06:27 
	 * @author 朱晓陈
	 * @param instanceId
	 * @return
	 */
	public List<FLOW_History> getHistoryList(String instanceId, String dataId);

	/**
	 * @category 
	 * 2015年1月26日 上午8:32:36 
	 * @author 朱晓陈
	 * @param lastHistory
	 * @return
	 */
	public List<FLOW_History> getNotDoneHistorys(FLOW_History lastHistory);

	/**
	 * @category 已办结列表
	 * 2015年2月7日 下午4:48:14 
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
	 * @category 检测节点是否自动发送
	 * 2015年2月11日 下午4:26:30 
	 * @author 朱晓陈
	 * @param n_node
	 * @return
	 */
	public String getAutoSendUserId(FLOW_FlowNode n_node,String projectId);

	/**
	 * 根据流程历史记录获取历史记录主表
	 * @category 获取历史记录主表
	 * 2015年2月27日 上午11:19:37 
	 * @author 朱晓陈
	 * @param historyId
	 * @return
	 */
	public FLOW_HistoryMain getHistoryMain(String historyId);

	/**
	 * @category 作废
	 * 2015年2月28日 下午4:01:46 
	 * @author 朱晓陈
	 * @param historyMainId
	 * @return
	 */
	public boolean cancelFlow(String historyMainId);

}
