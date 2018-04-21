package com.emi.flow.main.dao;

import java.util.List;

import com.emi.flow.main.bean.FLOW_FlowDefine;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.sys.core.bean.PageBean;

public interface FlowDao {

	/**
	 * @category 流程第一个节点
	 * 2015年1月15日 上午11:51:03 
	 * @author 朱晓陈
	 * @param flowDefineId
	 * @return
	 */
	public FLOW_FlowNode getFirstNode(String flowDefineId);

	/**
	 * @category 下一步节点
	 * 2015年1月15日 下午2:15:42 
	 * @author 朱晓陈
	 * @param nodeId
	 * @return
	 */
	public List<FLOW_FlowNode> getNextNodes(String nodeId);
	/**
	 * 根据节点id获取节点
	 * @category 获取节点
	 * 2015年1月20日 上午9:12:08 
	 * @author 朱晓陈
	 * @param nodeId
	 * @return
	 */
	public FLOW_FlowNode getNode(String nodeId);

	/**
	 * @category 流程列表
	 * 2015年1月26日 下午3:58:37 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageBean getFlowList(int pageIndex, int pageSize, String condition);

	/**
	 * @category 主流程信息
	 * 2015年1月27日 上午11:07:55 
	 * @author 朱晓陈
	 * @param flowId
	 * @return
	 */
	public FLOW_FlowDefine findFlowDefine(String flowId);

	/**
	 * @category 流程所有节点信息
	 * 2015年1月27日 下午1:34:45 
	 * @author 朱晓陈
	 * @param flowId
	 * @return
	 */
	public List<FLOW_FlowNode> getAllNode(String flowId);

	public boolean updateFlowDefine(FLOW_FlowDefine define);

	/**
	 * @category 添加节点
	 * 2015年1月27日 下午3:39:19 
	 * @author 朱晓陈
	 * @param node
	 * @return
	 */
	public boolean addNode(FLOW_FlowNode node);

	/**
	 * @category 更新节点
	 * 2015年1月28日 上午9:26:43 
	 * @author 朱晓陈
	 * @param node
	 * @return
	 */
	public boolean updateNode(FLOW_FlowNode node);

	/**
	 * @category 删除节点
	 * 2015年1月28日 下午3:39:08 
	 * @author 朱晓陈
	 * @param nodeId
	 * @return
	 */
	public boolean deleteNode(String nodeId);

	/**
	 * @category 流程的节点数
	 * 2015年1月28日 下午5:07:44 
	 * @author 朱晓陈
	 * @param flowId
	 * @return
	 */
	public int getNodeCount(String flowId);

	/**
	 * @category 批量保存节点
	 * 2015年1月28日 下午5:36:31 
	 * @author 朱晓陈
	 * @param nodes
	 * @return
	 */
	public boolean updateNodes(List<FLOW_FlowNode> nodes);

	/**
	 * @category 设置开始节点
	 * 2015年1月29日 上午8:20:12 
	 * @author 朱晓陈
	 * @param nodeId
	 * @param flowId
	 * @return
	 */
	public boolean setNodeFirst(String nodeId, String flowId);

}
