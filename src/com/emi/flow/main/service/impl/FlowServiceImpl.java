package com.emi.flow.main.service.impl;

import java.util.List;

import com.emi.flow.main.bean.FLOW_FlowDefine;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.dao.FlowDao;
import com.emi.flow.main.service.FlowService;
import com.emi.sys.core.bean.PageBean;

public class FlowServiceImpl implements FlowService{
	private FlowDao flowDao;

	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}

	@Override
	public FLOW_FlowNode getFirstNode(String flowDefineId) {
		return flowDao.getFirstNode(flowDefineId);
	}

	@Override
	public List<FLOW_FlowNode> getNextNodes(String nodeId) {
		return flowDao.getNextNodes(nodeId);
	}

	@Override
	public FLOW_FlowNode getNode(String nodeId) {
		return flowDao.getNode(nodeId);
	}

	@Override
	public PageBean getFlowList(int pageIndex, int pageSize, String condition) {
		return flowDao.getFlowList( pageIndex, pageSize, condition);
	}

	@Override
	public FLOW_FlowDefine findFlowDefine(String flowId) {
		return flowDao.findFlowDefine(flowId);
	}

	@Override
	public List<FLOW_FlowNode> getAllNode(String flowId) {
		return flowDao.getAllNode(flowId);
	}

	@Override
	public boolean updateFlowDefine(FLOW_FlowDefine define) {
		return flowDao.updateFlowDefine(define);
	}

	@Override
	public boolean addNode(FLOW_FlowNode node) {
		return flowDao.addNode(node);
	}

	@Override
	public boolean updateNode(FLOW_FlowNode node) {
		return flowDao.updateNode(node);
	}

	@Override
	public boolean deleteNode(String nodeId) {
		return flowDao.deleteNode(nodeId);
	}

	@Override
	public int getNodeCount(String flowId) {
		return flowDao.getNodeCount(flowId);
	}

	@Override
	public boolean updateNodes(List<FLOW_FlowNode> nodes) {
		return flowDao.updateNodes(nodes);
	}

	@Override
	public boolean setNodeFirst(String nodeId, String flowId) {
		
		return flowDao.setNodeFirst(nodeId, flowId);
	}
	
}
