package com.emi.flow.main.dao.impl;

import java.util.List;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.flow.main.bean.FLOW_FlowDefine;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.dao.FlowDao;
import com.emi.flow.main.util.FlowNode;
import com.emi.sys.core.bean.PageBean;

@SuppressWarnings("unchecked")
public class FlowDaoImpl extends BaseDao implements FlowDao{

	@Override
	public FLOW_FlowNode getFirstNode(String flowDefineId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_FlowNode.class)+" from FLOW_FlowNode "
				+ " where flowId='"+flowDefineId+"' and isFirst=1";
		return (FLOW_FlowNode) this.emiQuery(sql, FLOW_FlowNode.class);
	}

	@Override
	public List<FLOW_FlowNode> getNextNodes(String nodeId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_FlowNode.class)+" from FLOW_FlowNode "
				+ " where charindex(gid,(select nextNodeId from FLOW_FlowNode where gid='"+nodeId+"'))>0";
		return this.emiQueryList(sql, FLOW_FlowNode.class);
	}

	@Override
	public FLOW_FlowNode getNode(String nodeId) {
		return (FLOW_FlowNode) this.emiFind(nodeId, FLOW_FlowNode.class);
	}

	@Override
	public PageBean getFlowList(int pageIndex, int pageSize, String condition) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_FlowDefine.class)+" from FLOW_FlowDefine where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}

	@Override
	public FLOW_FlowDefine findFlowDefine(String flowId) {
		return (FLOW_FlowDefine) this.emiFind(flowId, FLOW_FlowDefine.class);
	}

	@Override
	public List<FLOW_FlowNode> getAllNode(String flowId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_FlowNode.class)+" from FLOW_FlowNode where flowId='"+flowId+"'";
		return this.emiQueryList(sql, FLOW_FlowNode.class);
	}

	@Override
	public boolean updateFlowDefine(FLOW_FlowDefine define) {
		return this.emiUpdate(define);
	}

	@Override
	public boolean addNode(FLOW_FlowNode node) {
		return this.emiInsert(node);
	}

	@Override
	public boolean updateNode(FLOW_FlowNode node) {
		return this.emiUpdate(node);
	}

	@Override
	public boolean deleteNode(String nodeId) {
		try {
			String sql = "delete from FLOW_FlowNode where gid='"+nodeId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getNodeCount(String flowId) {
		String sql = "select count(1) from FLOW_FlowNode where flowId='"+flowId+"'";
		return this.queryForInt(sql);
	}

	@Override
	public boolean updateNodes(List<FLOW_FlowNode> nodes) {
		return this.emiUpdate(nodes);
	}

	@Override
	public boolean setNodeFirst(String nodeId, String flowId) {
		try {
			//1、将已有的开始节点取消，icon改成默认的
			String sql = "update FLOW_FlowNode set isFirst=0,icon='"+FlowNode.DEFAULT_ICON+"' where flowId='"+flowId+"' and isFirst=1";
			
			//2、设置此节点为开始节点，icon改成开始节点的默认icon
			String sql2 = "update FLOW_FlowNode set isFirst=1,icon='"+FlowNode.ICON_START+"' where gid='"+nodeId+"'";
			this.batchUpdate(new String[]{sql,sql2});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
