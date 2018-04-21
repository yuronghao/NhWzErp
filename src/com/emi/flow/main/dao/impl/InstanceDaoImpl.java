package com.emi.flow.main.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.bean.FLOW_History;
import com.emi.flow.main.bean.FLOW_HistoryMain;
import com.emi.flow.main.bean.FLOW_Instance;
import com.emi.flow.main.dao.InstanceDao;
import com.emi.flow.main.util.FlowHistory;
import com.emi.flow.main.util.FlowNode;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.flow.model.dao.TableDao;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.dao.RoleDao;
import com.emi.rm.service.RoleService;
import com.emi.sys.core.bean.PageBean;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InstanceDaoImpl extends BaseDao implements InstanceDao {

	private TableDao tableDao;
	
	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}


	@Override
	public PageBean getInstanceList(String condition, int pageIndex,
			int pageSize) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_Instance.class)+" from FLOW_Instance where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, FLOW_Instance.class,"");
	}

	@Override
	public FLOW_Form getInstanceForm(String instanceId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_Form.class)+" from FLOW_Form "
				+ " where gid=(select formId from FLOW_Instance where gid='"+instanceId+"')";
		return (FLOW_Form) this.emiQuery(sql, FLOW_Form.class);
	}

	@Override
	public FLOW_Instance findInstance(String instanceId) {
		return (FLOW_Instance) this.emiFind(instanceId, FLOW_Instance.class);
	}

	@Override
	public boolean insertData(FLOW_Table table, Map<String, String> valuesMap) {
		boolean suc = false;
		try {
			if(valuesMap.size()>0){
				boolean hasGid = false;		//是否有设置gid
				StringBuffer sql = new StringBuffer();	//sql
				String tableName = table.getTableName();//表名
				
				StringBuffer columns = new StringBuffer();
				StringBuffer values = new StringBuffer();
				for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
					if(CommonUtil.notNullString(entry.getValue())){
						columns.append(entry.getKey()).append(",");
						values.append("'"+entry.getValue()+"',");
					}
					if("gid".equals(entry.getKey()) && CommonUtil.notNullString(entry.getValue())){
						//设置了gid，改为true
						hasGid = true;
					}
				}
				if(hasGid){
					/*
					 * 只有设置了gid才能往下执行，此处不可以生成gid
					 * gid需要再之前生成好了带过来，因为gid需要在保存流程记录时使用
					 */
					String columnSql = "";
					String valueSql = "";
					if(columns.length()>0){
						columnSql = "("+columns.substring(0, columns.length()-1)+")";
						valueSql = "values("+values.substring(0, values.length()-1)+")";
						//拼接sql
						sql.append("insert into ").append(tableName);
						sql.append(columnSql).append(" ").append(valueSql);
						//执行插入
						this.update(sql.toString());	
						suc = true;
					}else{
						System.out.println("错误：字段值都为空，终止插入!!!");
					}
					
				}else{
					System.out.println("错误：未设置gid的值，不允许插入数据!!!");
				}
			}else{
				System.out.println("错误：没有设置需要插入值的字段!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return suc;
		}
		return suc;
	}

	@Override
	public boolean updateData(FLOW_Table table, Map<String, String> valuesMap) {
		boolean suc = false;
		try {
			String gidValue = "";	//gid值
			StringBuffer sql = new StringBuffer();	//sql
			String tableName = table.getTableName();//表名
			
			StringBuffer sets = new StringBuffer();
			for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
				if(!entry.getKey().equals("gid")){
					String value = entry.getValue();
					if(CommonUtil.isNullString(value)){
						sets.append(entry.getKey()+"=null,");
					}else{
						sets.append(entry.getKey()+"='"+entry.getValue()+"',");
					}
					
				}else{
					gidValue = entry.getValue();
				}
			}
			if(sets.length()>0 && CommonUtil.notNullString(gidValue)){
				//set的参数值不空，且gid有值,拼接sql
				sql.append("update ").append(tableName).append(" set ");
				sql.append(sets.substring(0, sets.length()-1)).append(" ").append("where gid='"+gidValue+"'");
				//执行更新
				this.update(sql.toString());
				suc = true;
			}else if(CommonUtil.isNullString(gidValue)){
				System.out.println("错误：未设置gid的值，无法更新数据!!!");
			}else{
				System.out.println("错误：没有设置需要更新的字段!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return suc;
		}
		return suc;
	}

	@Override
	public boolean insertHistory(Object historys) {
		return this.emiInsert(historys);
	}
	
	@Override
	public boolean updateHistory(Object historys) {
		return this.emiUpdate(historys);
	}

	@Override
	public PageBean getMyList(int pageIndex, int pageSize, String userId,
			String condition,int completeStatus) {
		String completeSql = "";
		String orderSql = "acceptTime desc";//排序字段，默认按接收时间排序
		if(completeStatus==0){
			completeSql = " AND completeStatus=0";
		}else if(completeStatus==1){
			completeSql = " AND completeStatus>0";
			orderSql = "completeTime desc";//已完成的按完成时间排序
		}
		Map match = new HashMap();
		match.put("instanceName", "FLOW_History.instanceName");
		match.put("fromUserName", "FLOW_History.fromUserName");
		match.put("nodeName", "FLOW_History.nodeName");
		match.put("title", "FLOW_History.title");
		match.put("status", "FLOW_History.status");
		
		String sql = "select "+CommonUtil.colsFromBean(FLOW_History.class,"h")+""
				+ ",i.instanceName,fu.cPerName as fromUserName,n.nodeName,m.status,m.titleValue as title"
				+ " FROM FLOW_History h "
				+ " LEFT JOIN FLOW_Instance i ON i.gid=h.instanceId "
				+ " LEFT JOIN AA_Person fu ON h.fromUserId=fu.gUserUid "
//				+ " LEFT JOIN YM_User fu ON h.fromUserId=fu.gId "
				+ " LEFT JOIN FLOW_FlowNode n ON h.nodeId=n.gid "
				+ " LEFT JOIN FLOW_HistoryMain m ON m.gid=h.historyMainId "
				+ " where m.status!=2 "
//				+ " WHERE h.acceptUserId='"+userId+"' AND h.acceptStatus=1 "
				+ " and h.pk in (select max(pk) from FLOW_History where acceptUserId='"+userId+"' and acceptStatus=1 "+ completeSql+" group by historyMainId ) "
				+ condition;
		return this.emiQueryList(sql, pageIndex, pageSize,FLOW_History.class,match, orderSql);
	}

	@Override
	public FLOW_History findHistory(String historyId) {
		return (FLOW_History) this.emiFind(historyId, FLOW_History.class);
	}

	@Override
	public Map queryData(String dataId, String tableName, String columns) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(columns).append(" from ");
		sql.append(tableName).append(" where gid='").append(dataId).append("'");
		return this.queryForMap(sql.toString());
	}

	@Override
	public List<FLOW_History> getHistoryList(String instanceId,String dataId) {
		Map match = new HashMap();
		match.put("acceptUserName", "FLOW_History.acceptUserName");
		match.put("nodeName", "FLOW_History.nodeName");
		
		String sql = "select "+CommonUtil.colsFromBean(FLOW_History.class,"h")+",au.cPerName acceptUserName,n.nodeName from FLOW_History h"
				//+ " LEFT JOIN YM_User au ON h.acceptUserId=au.gId "
				+ " LEFT JOIN AA_Person au ON h.acceptUserId=au.gUserUid "
				+ " LEFT JOIN FLOW_FlowNode n ON h.nodeId=n.gid "
				+ " where instanceId='"+instanceId+"' and dataId='"+dataId+"'";
		return this.emiQueryList(sql, FLOW_History.class,match);
	}

	@Override
	public List<FLOW_History> getNotDoneHistorys(final FLOW_History lastHistory) {
//		String sql = "select nodeName, from FLOW_FlowNode";
		String sql = "with histroyTree(gid,nodeName,preNodeId,nextNodeId,doType,forIds)"
				+ " AS(select gid,nodeName,preNodeId,nextNodeId,doType,forIds from FLOW_FlowNode "
				+ "	where CHARINDEX(gid,(select nextNodeId from FLOW_FlowNode where gid='" + lastHistory.getNodeId() + "'))>0"
				+ " union ALL"
				+ " select subr.gid,subr.nodeName,subr.preNodeId,subr.nextNodeId,subr.doType,subr.forIds from FLOW_FlowNode as subr"
				+ " INNER JOIN histroyTree as c on subr.gid=c.nextNodeId)"
				+ " SELECT * from histroyTree";
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public FLOW_History mapRow(ResultSet rs, int rowNum) throws SQLException {
				FLOW_History hist = new FLOW_History();
				hist.setInstanceName(lastHistory.getInstanceName());
				hist.setNodeId(rs.getString("gid"));
				hist.setNodeName(rs.getString("nodeName"));
				hist.setDoType(rs.getInt("doType"));
				hist.setForIds(rs.getString("forIds"));
				return hist;
			}
			
		});
	}

	@Override
	public PageBean getOverList(int pageIndex, int pageSize, String userId,
			String condition) {
		Map match = new HashMap();
		match.put("instanceName", "FLOW_History.instanceName");
		match.put("fromUserName", "FLOW_History.fromUserName");
		match.put("nodeName", "FLOW_History.nodeName");
		match.put("title", "FLOW_History.title");
		
		String userCondition = "";
		if(CommonUtil.notNullString(userId)){//如果传了用户id，只查该用户的办结列表
			userCondition = "h.acceptUserId='"+userId+"' AND h.acceptStatus=1";
		}
		String sql = "select "+CommonUtil.colsFromBean(FLOW_History.class,"h")+",i.instanceName as title,i.instanceName,fu.cUserName as fromUserName,n.nodeName FROM FLOW_History h "
				+ " LEFT JOIN FLOW_Instance i ON i.gid=h.instanceId "
				+ " LEFT JOIN YM_User fu ON h.fromUserId=fu.gId "
				+ " LEFT JOIN FLOW_FlowNode n ON h.nodeId=n.gid "
				+ " LEFT JOIN FLOW_HistoryMain m ON m.gid=h.historyMainId "
				+ " WHERE pk in (select max(pk) from FLOW_History group by historyMainId) "
				+ userCondition
				+ " and m.status="+FlowHistory.STATUS_DONE+" "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize,FLOW_History.class,match, "completeTime desc");
	}

	@Override
	public void updateHistoryMain(FLOW_HistoryMain main) {
		this.emiUpdate(main);
	}

	@Override
	public boolean insertHistoryMain(FLOW_HistoryMain main) {
		return this.emiInsert(main);
	}

	@Override
	public FLOW_HistoryMain getHistoryMain(String historyId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_HistoryMain.class)+" from FLOW_HistoryMain "
				+ " where gid=(select historyMainId from FLOW_History where gid='"+historyId+"')";
		return (FLOW_HistoryMain) this.emiQuery(sql, FLOW_HistoryMain.class);
	}

	@Override
	public boolean updateDataStatus(String tableId, String dataId, int status) {
		try {
			FLOW_Table table = tableDao.findTable(tableId);
			String tableName = table.getTableName();
			String sql = "update "+tableName+" set status="+status+" where gid='"+dataId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean cancelFlow(String historyMainId) {
		FLOW_HistoryMain main = new FLOW_HistoryMain();
		main.setGid(historyMainId);
		main.setStatus(FlowHistory.STATUS_DELETE);
		return this.emiUpdate(main);
	}

}
