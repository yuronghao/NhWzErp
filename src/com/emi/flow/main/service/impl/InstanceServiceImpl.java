package com.emi.flow.main.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.form.dao.FormDao;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.bean.FLOW_History;
import com.emi.flow.main.bean.FLOW_HistoryMain;
import com.emi.flow.main.bean.FLOW_Instance;
import com.emi.flow.main.dao.FlowDao;
import com.emi.flow.main.dao.InstanceDao;
import com.emi.flow.main.service.InstanceService;
import com.emi.flow.main.util.DefaultConfig;
import com.emi.flow.main.util.FlowHistory;
import com.emi.flow.main.util.FlowNode;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.flow.model.dao.TableDao;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.dao.RoleDao;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.util.UserInfo;

@SuppressWarnings("rawtypes")
public class InstanceServiceImpl implements InstanceService {
	private InstanceDao instanceDao;
	private FormDao formDao;
	private TableDao tableDao;
	private RoleDao roleDao;
//	private ProjectDao projectDao;
	private FlowDao flowDao;
	

	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}

	public void setInstanceDao(InstanceDao instanceDao) {
		this.instanceDao = instanceDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

//	public void setProjectDao(ProjectDao projectDao) {
//		this.projectDao = projectDao;
//	}

	@Override
	public PageBean getInstanceList(String condition, int pageIndex,
			int pageSize) {
		return instanceDao.getInstanceList(condition, pageIndex, pageSize);
	}

	@Override
	public FLOW_Form getInstanceForm(String instanceId) {
		return instanceDao.getInstanceForm(instanceId);
	}

	@Override
	public FLOW_Instance findInstance(String instanceId) {
		return instanceDao.findInstance(instanceId);
	}

	/*
	 * 生成步骤记录
	 */
	private List<FLOW_History> generateHistorys(List<String> acceptUserIds,
			String userId, String formId, String nodeId, String flowId,
			String instanceId,String tableId, String dataId ,boolean isComplete,String historyMainId) {
		List<FLOW_History> historyList = new ArrayList<FLOW_History>();
		for(String acceptUserId : acceptUserIds){
			FLOW_History history = new FLOW_History();
			history.setInstanceId(instanceId);
			history.setFlowId(flowId);
			history.setFormId(formId);
			history.setNodeId(nodeId);
			history.setTableId(tableId);
			history.setDataId(dataId);
			history.setFromUserId(userId);
			history.setFromTime(new Timestamp(System.currentTimeMillis()));
			history.setAcceptUserId(acceptUserId);
			history.setAcceptStatus(1);
			history.setAcceptTime(new Timestamp(System.currentTimeMillis()));
			history.setCompleteStatus(isComplete?1:0);//完成状态 
			history.setHistoryMainId(historyMainId);
			if(isComplete){
				history.setCompleteTime(new Timestamp(System.currentTimeMillis()));
			}
			//添加一条记录至列表
			historyList.add(history);
		}
		return historyList;
	}
	
	@Override
	public int saveInstanceForm(String userId,String sendToUserId,boolean first,boolean last, Map<String, String> values, String formId,
			String nodeId, String flowId, String instanceId, String historyId, String nextNodeId,String comment,boolean reject ,UserInfo userInfo) {
		int retFlag = 0;
		boolean suc = false;
		FLOW_Table table = formDao.getFormTable(formId);
		String tableId = table.getGid();	//表id
		String dataId = CommonUtil.Obj2String(values.get("gid"));	//数据id
		/*
		 * 1、保存表单数据
		 */
		if(first){
			//第一步，插入数据
			suc = instanceDao.insertData(table,values);
		}else{
			//不是第一步，更新数据
			suc = instanceDao.updateData(table,values);
		}
		
		if(suc){
			/*
			 * 2、更新本流程步骤记录
			 */
			//步骤记录主表
			List<String> acceptUserIds = new ArrayList<String>();//接收人id
			//获取接收人
//			acceptUserIds.add("6C353A55-DFCF-45FA-B45A-2F9745129A53");	
			FLOW_HistoryMain historyMain = instanceDao.getHistoryMain(historyId);
			
			if(reject){
				//驳回的接收人-流程发起人
				acceptUserIds.add(historyMain.getOwnerId());
				//驳回的步骤-默认第一步
				FLOW_FlowNode firstNode = flowDao.getFirstNode(flowId);
				nextNodeId = firstNode==null?"":firstNode.getGid();
			}else{
				acceptUserIds = Arrays.asList(sendToUserId.split(","));
			}
			if(first){
				//如果是第一步，生成标题，插入历史主表记录
				String title = "";
				FLOW_Form form = formDao.findForm(formId);
				if(form != null){
					String cfg = form.getTitleCfg();
					//替换日期、部门、人员等值
					title = cfg.replaceAll(DefaultConfig.SYSDATE, DateUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm"));
					if(userInfo != null){
						title = title.replaceAll(DefaultConfig.DEPARTMENTNAME, userInfo.getDepartmentName());
						title = title.replaceAll(DefaultConfig.USERNAME, userInfo.getUserName());
						title = title.replaceAll(DefaultConfig.PERSONNAME, userInfo.getPersonName());
					}
					//替换表单字段值（读取的是 数据库 字段名）
					for (String key : values.keySet()) {
						title = title.replaceAll("@"+key+"@", values.get(key));
			        }
				}
				
				historyMain = saveHistoryMain(historyMain,FlowHistory.STATUS_UNDERWAY,userId,title);
			
				//如果是第一步，插入本次操作记录
				List<String> firstAcceptUserIds = new ArrayList<String>();//第一步接收人id
				firstAcceptUserIds.add(userId);	//第一步的记录，接收人默认本人
				List<FLOW_History> historyList = this.generateHistorys(firstAcceptUserIds,userId, formId,nodeId, flowId, instanceId,tableId, dataId,true,historyMain.getGid());
				historyList.get(0).setComment(comment);
				suc = instanceDao.insertHistory(historyList);
			}else{
				//不是第一步，更新本条记录
				FLOW_History history = new FLOW_History();
				history.setGid(historyId);
				history.setCompleteTime(new Timestamp(System.currentTimeMillis()));
				history.setComment(comment);
				if(reject){
					history.setCompleteStatus(FlowHistory.COMPLETE_REJECT);
				}else{
					history.setCompleteStatus(FlowHistory.COMPLETE_PASS);	//已完成
				}
				suc = instanceDao.updateHistory(history);
			}
			
			/*
			 * 3、插入下一步待办记录 
			 */
			if(suc){
				if(last){
					//如果是最后一步，不生成下一步记录，更改实体表数据状态为已审核
					boolean suc_updData = instanceDao.updateDataStatus(tableId,dataId,1);
					if(!suc_updData){
						throw new RuntimeException("警告：数据状态修改失败！！！");
					}
					//更新步骤记录主表状态-已办结 ,title参数可以不传
					saveHistoryMain(historyMain, FlowHistory.STATUS_DONE,userId,"");
				}else{
					//生成下一步记录
					List<FLOW_History> historyList = this.generateHistorys(acceptUserIds,userId, formId,nextNodeId, flowId, instanceId,tableId, dataId,false,historyMain.getGid());
					//插入数据库
					suc = instanceDao.insertHistory(historyList);
				}
			}
			
		}
		retFlag = suc?1:0;
		return retFlag;
	}

	private boolean updateDataStatus(String tableId, String dataId, int status) {
		FLOW_Table table = tableDao.findTable(tableId);
		
		return false;
	}

	private FLOW_HistoryMain saveHistoryMain(FLOW_HistoryMain main, int status,String userId,String title) {
		if(main==null){
			main = new FLOW_HistoryMain(status,userId,title);
			instanceDao.insertHistoryMain(main);
		}else{
			main.setStatus(status);
			if(status == FlowHistory.STATUS_DONE){
				main.setCompleteTime(new Timestamp(System.currentTimeMillis()));
			}else if(status == FlowHistory.STATUS_DELETE){
				main.setDeleteTime(new Timestamp(System.currentTimeMillis()));
			}
			instanceDao.updateHistoryMain(main);
		}
		return main;
	}

	@Override
	public PageBean getTodoList(int pageIndex, int pageSize, String userId,
			String condition) {
		return instanceDao.getMyList(pageIndex, pageSize, userId, condition,0);
	}
	
	@Override
	public PageBean getDoneList(int pageIndex, int pageSize, String userId,
			String condition) {
		return instanceDao.getMyList(pageIndex, pageSize, userId, condition,1);
	}

	@Override
	public FLOW_History findHistory(String historyId) {
		return instanceDao.findHistory(historyId);
	}

	@Override
	public Map queryData(String dataId, String tableId,
			List<FLOW_FormDetail> formDetails) {
		String tableName = tableDao.findTable(tableId).getTableName();//表名
		String columns = "";	//查询的字段名
		for(FLOW_FormDetail detail : formDetails){
			columns += detail.getColumnName()+",";
		}
		columns += "gid";
		return instanceDao.queryData(dataId,tableName,columns);
	}

	@Override
	public List<FLOW_History> getHistoryList(String instanceId, String dataId) {
		return instanceDao.getHistoryList(instanceId, dataId);
	}

	@Override
	public List<FLOW_History> getNotDoneHistorys(FLOW_History lastHistory) {
		return instanceDao.getNotDoneHistorys(lastHistory);
	}

	@Override
	public PageBean getOverList(int pageIndex, int pageSize, String userId,
			String condition) {
		return instanceDao.getOverList(pageIndex, pageSize, userId, condition);
	}

	@Override
	public String getAutoSendUserId(FLOW_FlowNode node,String projectId) {
		String autoUserId = "";
		if(node.getDoType().compareTo(FlowNode.DOTYPE_PERSON)==0){
			//TODO
		}
		if(node.getDoType().compareTo(FlowNode.DOTYPE_ROLE)==0){
			//节点指定了角色
			int smartType = node.getSmartSendType()==null?0:node.getSmartSendType();
			if((smartType | FlowNode.SMARTTYPE_PM) == smartType){//自动发给项目经理
//				auto = 1;
				if(node.getForIds().indexOf(",")<0){
					String roleId = node.getForIds();//角色id
					RM_Role role = roleDao.findRole(roleId);
					/*if(Constants.ROLE_DEFAULT_XMJL.equals(role.getRoleCode())){
						//选择的角色只是项目经理时,根据项目获取指定项目经理
						if(CommonUtil.notNullString(projectId)){
//							autoUserId = projectDao.getManagerUserId(projectId);
						}
					}*/
				}
			}if((smartType | FlowNode.SMARTTYPE_SINGLE) == smartType){//对象只有一个时 自动选取发送
				String roleId = node.getForIds();//角色id
				List<Map> users = roleDao.getUsersByRole(roleId);
				if(users!=null && users.size()==1){
					autoUserId = CommonUtil.Obj2String(users.get(0).get("gId"));
				}
			}
		}
		return autoUserId;
//		return instanceDao.checkAutoSend(n_node);
	}

	@Override
	public FLOW_HistoryMain getHistoryMain(String historyId) {
		return instanceDao.getHistoryMain(historyId);
	}

	@Override
	public boolean cancelFlow(String historyMainId) {
		return instanceDao.cancelFlow(historyMainId);
	}

}
