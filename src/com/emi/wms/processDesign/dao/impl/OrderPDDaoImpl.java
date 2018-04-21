package com.emi.wms.processDesign.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.MesWmProduceProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProduceProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProduceProcessRouteCMould;
import com.emi.wms.bean.MesWmProduceProcessRouteCPre;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.WmProduceorderC2;
import com.emi.wms.bean.WmTask;
import com.emi.wms.processDesign.dao.OrderPDDao;

@SuppressWarnings("unchecked")
public class OrderPDDaoImpl extends BaseDao implements OrderPDDao {

	@Override
	public List<WmProduceorderC> getProduceOrderCList(String orderId) {
		String sql = "select "+CommonUtil.colsFromBean(WmProduceorderC.class)+" from WM_ProduceOrder_C where produceOrderUid='"+orderId+"'";
		return this.emiQueryList(sql, WmProduceorderC.class);
	}

	@Override
	public void saveCopyedProduceRoute(List<MesWmProduceProcessroute> p_routeList,
			List<MesWmProduceProcessroutec> p_cList,
			List<MesWmProduceProcessRouteCPre> p_preList,
			List<MesWmProduceProcessroutecGoods> p_goodsList,
			List<MesWmProduceProcessRouteCDispatching> p_dispatchingList, List<MesWmProduceProcessRouteCEquipment> p_equipmentList,List<MesWmProduceProcessRouteCMould> p_mouldList) {
		this.emiInsert(p_routeList);
		this.emiInsert(p_cList);
		this.emiInsert(p_preList);
		this.emiInsert(p_goodsList);
		this.emiInsert(p_dispatchingList);
		this.emiInsert(p_equipmentList);
		this.emiInsert(p_mouldList);
	}
	
	
	/**
	 * @category 根据订单gid 获得订单产品表跟标准工艺路线主表关联
	 *2016 2016年9月14日上午9:43:55
	 *List<Map>
	 *宋银海
	 */
	@Override
	public List<Map> getProduceOrderCProcessRoute(String orderGid) {
		String sql="SELECT poc.gid,pr.designJson,pr.state,prc.opGid,poc.cfree1 from WM_ProduceOrder_C poc LEFT JOIN MES_WM_ProcessRoute pr on poc.goodsUid=pr.goodsUid LEFT JOIN MES_WM_ProcessRouteC prc on prc.routGid=pr.gid where produceOrderUid='"+orderGid+"' and (prc.nextGid is null or prc.nextGid ='' ) and isnull(pr.isdelete,0)=0 ";
		return this.queryForList(sql);
	}

	@Override
	public boolean checkHasRoute(String orderId) {
		String sql = "select count(1) from MES_WM_ProduceProcessRoute where produceUid='"+orderId+"'";
		int count = this.queryForInt(sql);
		return count>0;
	}

	@Override
	public void deleteOldData(String orderId) {
		String sel_sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where produceUid='"+orderId+"'";
		MesWmProduceProcessroute p_route = (MesWmProduceProcessroute) this.emiQuery(sel_sql, MesWmProduceProcessroute.class);
		if(p_route!=null){
			String clearChangeSql1 = "update WM_ProduceOrder_C set turnoutNum=null where gid='"+p_route.getChangeSrcOrderCid()+"'";//改制关联的已转数量清空-订单子表
			String clearChangeSql2 = "update MES_WM_ProduceProcessRouteC set turnoutedNum=null where gid='"+p_route.getChangeSrcRouteCid()+"'";//改制关联的已转数量清空-工艺路线子表
			String sql1 = "delete from MES_WM_ProduceProcessRouteCDispatching where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql2 = "delete from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql3 = "delete from MES_WM_ProduceProcessRouteCPre where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql4 = "delete from MES_WM_ProduceProcessRouteCEquipment where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			
			String sql7 = "delete from MES_WM_ProduceProcessRouteCMould where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')";
			
			String sql5 = "delete from MES_WM_ProduceProcessRoute where produceUid='"+orderId+"'"; 
			String sql6 = "delete from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"'"; 
			
			this.bathUpdate(new String[]{clearChangeSql1,clearChangeSql2,sql1,sql2,sql3,sql4,sql7,sql5,sql6});
		}
		
	}
	
	@Override
	public void deleteProductOldData(String ordercId) {
		String sel_sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where produceCuid='"+ordercId+"'";
		MesWmProduceProcessroute p_route = (MesWmProduceProcessroute) this.emiQuery(sel_sql, MesWmProduceProcessroute.class);
		if(p_route!=null){
			String clearChangeSql1 = "update WM_ProduceOrder_C set turnoutNum=null where gid='"+p_route.getChangeSrcOrderCid()+"'";//改制关联的已转数量清空-订单子表
			String clearChangeSql2 = "update MES_WM_ProduceProcessRouteC set turnoutedNum=null where gid='"+p_route.getChangeSrcRouteCid()+"'";//改制关联的已转数量清空-工艺路线子表
			String sql1 = "delete from MES_WM_ProduceProcessRouteCDispatching where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql2 = "delete from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql3 = "delete from MES_WM_ProduceProcessRouteCPre where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql4 = "delete from MES_WM_ProduceProcessRouteCEquipment where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')"; 
			String sql5 = "delete from MES_WM_ProduceProcessRoute where produceCuid='"+ordercId+"'"; 
			String sql6 = "delete from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"'"; 
			String sql7 = "delete from MES_WM_ProduceProcessRouteCMould where routeCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+p_route.getGid()+"')";
			this.bathUpdate(new String[]{clearChangeSql1,clearChangeSql2,sql1,sql2,sql3,sql4,sql5,sql6,sql7});
		}
	}

	@Override
	public MesWmProduceProcessroute queryProduceRoute(String orderId,
			String orderCid) {
		Map<String , String> match = new HashMap<String, String>();
		match.put("goodsUid", "goodsUid");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class,"pr")+",poc.goodsUid from MES_WM_ProduceProcessRoute pr left join WM_ProduceOrder_C poc "
				+ " on pr.produceCuid=poc.gid where produceUid='"+orderId+"' and produceCuid='"+orderCid+"'";
		return (MesWmProduceProcessroute) this.emiQuery(sql, MesWmProduceProcessroute.class,match);
	}

	@Override
	public List<MesWmProduceProcessroutec> getRouteCList(String routeId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC where produceRouteGid='"+routeId+"'";
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}

	@Override
	public List<MesWmProduceProcessRouteCPre> getProcessRouteCPreList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCPre.class)+" from MES_WM_ProduceProcessRouteCPre where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProduceProcessRouteCPre.class);
	}

	@Override
	public List<MesWmProduceProcessroutecGoods> getProcessRouteCGoodsList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutecGoods.class)+" from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProduceProcessroutecGoods.class);
	}

	@Override
	public List<MesWmProduceProcessRouteCDispatching> getProcessRouteCDispatchingList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCDispatching.class)+" from MES_WM_ProduceProcessRouteCDispatching where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProduceProcessRouteCDispatching.class);
	}

	@Override
	public void updateOrderRoute(MesWmProduceProcessroute route) {
		this.emiUpdate(route);
	}

	@Override
	public void addProcessRouteC(List<MesWmProduceProcessroutec> addList) {
		this.emiInsert(addList);
	}

	@Override
	public void updateProcessRouteC(List<MesWmProduceProcessroutec> updateList) {
		this.emiUpdate(updateList);
	}

	@Override
	public void deleteProcessRouteC(String deleteIds) {
		deleteIds = deleteIds.replaceAll(",", "','");
		String sql = "delete from MES_WM_ProduceProcessRouteC where gid in ('"+deleteIds+"')";
		//关联的子表
		String sql_pre = "delete from MES_WM_ProduceProcessRouteCPre where routeCGid in ('"+deleteIds+"')";
		String sql_goods = "delete from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in ('"+deleteIds+"')";
		String sql_dis = "delete from MES_WM_ProduceProcessRouteCDispatching where routeCGid in ('"+deleteIds+"')";
		String sql_equ = "delete from MES_WM_ProduceProcessRouteCEquipment where routeCGid in ('"+deleteIds+"')";
		String sql_mould = "delete from MES_WM_ProduceProcessRouteCMould where routeCGid in ('"+deleteIds+"')";
		this.bathUpdate(new String[]{sql,sql_pre,sql_goods,sql_dis,sql_equ,sql_mould});
	}

	@Override
	public void deleteRoutecAttributes(String updateIds) {
		updateIds = updateIds.replaceAll(",", "','"); 
		String sql1 = "delete from MES_WM_ProduceProcessRouteCPre where routeCGid in ('"+updateIds+"')";
		String sql2 = "delete from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in ('"+updateIds+"')";
		String sql3 = "delete from MES_WM_ProduceProcessRouteCDispatching where routeCGid in ('"+updateIds+"')";
		String sql4 = "delete from MES_WM_ProduceProcessRouteCEquipment where routeCGid in ('"+updateIds+"')";
		String sql5 = "delete from MES_WM_ProduceProcessRouteCMould where routeCGid in ('"+updateIds+"')";
		this.batchUpdate(new String[]{sql1,sql2,sql3,sql4,sql5});
		
	}

	@Override
	public void insertRoutecAttributes(
			List<MesWmProduceProcessroutec> updateList,
			List<MesWmProduceProcessroutec> addList) {
		List<MesWmProduceProcessRouteCPre> preList = new ArrayList<MesWmProduceProcessRouteCPre>();
		List<MesWmProduceProcessroutecGoods> goodsList = new ArrayList<MesWmProduceProcessroutecGoods>();
		List<MesWmProduceProcessRouteCDispatching> workCenterList = new ArrayList<MesWmProduceProcessRouteCDispatching>();
		List<MesWmProduceProcessRouteCEquipment> equipmentList = new ArrayList<MesWmProduceProcessRouteCEquipment>();
		List<MesWmProduceProcessRouteCMould> mouldList = new ArrayList<MesWmProduceProcessRouteCMould>();
		
		//合并同类列表
		for(MesWmProduceProcessroutec routec : updateList){
			preList.addAll(routec.getPreList());
			goodsList.addAll(routec.getGoodsList());
			workCenterList.addAll(routec.getDispatchingList());
			equipmentList.addAll(routec.getEquipmentList());
			mouldList.addAll(routec.getMouldList());
		}
		for(MesWmProduceProcessroutec routec : addList){
			preList.addAll(routec.getPreList());
			goodsList.addAll(routec.getGoodsList());
			workCenterList.addAll(routec.getDispatchingList());
			equipmentList.addAll(routec.getEquipmentList());
			mouldList.addAll(routec.getMouldList());
		}
		
		//执行新增
		this.emiInsert(preList);
		this.emiInsert(goodsList);
		this.emiInsert(workCenterList);
		this.emiInsert(equipmentList);
		this.emiInsert(mouldList);
	}

	@Override
	public List<MesWmProduceProcessRouteCEquipment> getProduceProcessRouteCEquipmentList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCEquipment.class)+" from MES_WM_ProduceProcessRouteCEquipment where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProduceProcessRouteCEquipment.class);
	}
	@Override
	public List<MesWmProduceProcessRouteCMould> getProduceProcessRouteCMouldList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCMould.class)+" from MES_WM_ProduceProcessRouteCMould where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProduceProcessRouteCMould.class);
	}

	@Override
	public List<MesWmProduceProcessroutec> getProduceFirstProcess(String orderId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"rc")+" from MES_WM_ProduceProcessRouteC rc ,MES_WM_ProduceProcessRoute r  "
				+ " where rc.produceRouteGid=r.gid and r.produceUid='"+orderId+"' "
				+ " and (rc.preGid is null or rc.preGid='')";
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}
	
	@Override
	public List<MesWmProduceProcessroutec> getProduceFirstProcessByOrderc(String ordercId,int flag) {
		
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"rc")+" from MES_WM_ProduceProcessRouteC rc ,MES_WM_ProduceProcessRoute r  "
				+ " where rc.produceRouteGid=r.gid and r.produceCuid='"+ordercId+"' ";
		
		if(flag==0){
			sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"rc")+" from MES_WM_ProduceProcessRouteC rc ,MES_WM_ProduceProcessRoute r  "
					+ " where rc.produceRouteGid=r.gid and r.produceCuid='"+ordercId+"' "
					+ " and (rc.preGid is null or rc.preGid='')";
		}
		
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}

	@Override
	public boolean checkDoingTask(String orderId,String ordercId,int type) {
		String orderCond = "";
		if(CommonUtil.isNullString(ordercId)){
			orderCond = "produceUid='"+orderId+"'";
		}else{
			orderCond = "producecUid='"+ordercId+"'";
		}
		String sql1 = "select count(1) from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in "
				+ "(select gid from MES_WM_ProduceProcessRouteC where produceRouteGid in "
				+ "(select gid from MES_WM_ProduceProcessRoute where "+orderCond+")) and receivedNum>0";
		String sql2 = "select count(1) from MES_WM_ProduceProcessRouteC where produceRouteGid in "
				+ "(select gid from MES_WM_ProduceProcessRoute where "+orderCond+") and dispatchedNum>0";
		int count1 = 0; 
		int count2 = 0; 
		count1 = this.queryForInt(sql1);
		//if(type==0){
			count2 = this.queryForInt(sql2);
		//}
		return (count1 + count2)>0;
	}

	@Override
	public void deleteOldProduceTask(String ordercId) {
		String sql = "delete from WM_Task where billGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid in (select gid from MES_WM_ProduceProcessRoute where produceCuid='"+ordercId+"'))"
				+ " and taskTypeUid in (select gid from WM_BillType where billCode='0210')"; 
		this.update(sql);
	}
	
	@Override
	public void deleteOldOrderTask(String orderId) {
		String sql = "delete from WM_Task where billGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid in (select gid from MES_WM_ProduceProcessRoute where produceUid='"+orderId+"'))"
				+ " and taskTypeUid in (select gid from WM_BillType where billCode='0210')"; 
		this.update(sql);
	}

	@Override
	public PageBean getEnabledChangeOrder(String orgId, String sobId,int pageIndex,int pageSize, String condition,boolean filter) {
		String sql = "select poc.pk,poc.gid,po.billCode,po.billDate,poc.goodsUid,po.deptGid,po.managerGid,poc.number,poc.cfree1 from WM_ProduceOrder po LEFT JOIN WM_ProduceOrder_C poc ";
			sql += "on po.gid=poc.produceOrderUid  ";
			sql += " where po.sobgid='"+sobId+"' and po.orggid='"+orgId+"' ";
			if(filter){
				sql += " and (poc.completedNum is null or poc.number <> poc.completedNum) and (po.changeOrder is null or po.changeOrder=0) ";
			}//and (poc.turnoutNum is null or poc.turnoutNum=0) 
		if(CommonUtil.notNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, "billDate desc");
	}

	@Override
	public List<MesWmProduceProcessroutec> getProduceRouteCList(String orderId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"rc")+" from MES_WM_ProduceProcessRouteC rc left join MES_WM_ProduceProcessRoute r on r.gid=rc.produceRouteGid where r.produceUid='"+orderId+"' ";
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}

	@Override
	public List<Map> getChangeProcessSrc(String ordercId,String condition) {
		return getChangeProcessSrcInfo(ordercId, " and t.canChange > 0 "+condition);
	}
	
	@Override
	public List<Map> getChangeProcessSrcInfo(String ordercId,String condition) {
		String sql = "SELECT DISTINCT * FROM ( SELECT ( SELECT "
				+ "(isnull( prc.checkOkNum,prc.reportOkNum ) "
				+ "- ( "
				+ "		( SELECT isnull(dispatchedNum, 0) FROM MES_WM_ProduceProcessRouteC WHERE gid = prc.nextGid ) * prp.baseUse/prp.baseQuantity "
				+ "	 ) "
				+ ")-isnull(prc.turnoutedNum, 0) ) AS canChange,prc.* "
				+ "FROM "
				+ "	MES_WM_ProduceProcessRouteC prc "
				+ "LEFT JOIN MES_WM_ProduceProcessRoute pr ON prc.produceRouteGid = pr.gid "
				+ "LEFT JOIN MES_WM_ProduceProcessRouteCPre prp on prp.routeCGid=prc.nextGid "
				+ "WHERE "
				+ "	pr.produceCuid = '"+ordercId+"' "
				+ "AND prc.nextGid IS NOT NULL "
				+ ") AS t "
				+ "WHERE 1=1 ";//t.canChange > 0 
		if(CommonUtil.notNullString(condition)){
			sql += condition;
		}
		return this.queryForList(sql);
	}

	@Override
	public boolean checkDispatchTask(String routeCid) {
		boolean has = false;
		String sql = "select dispatchedNum from MES_WM_ProduceProcessRouteC where gid='"+routeCid+"'";
		Map res = this.queryForMap(sql);
		if(res!=null){
			BigDecimal resNum = res.get("dispatchedNum")==null?new BigDecimal(0):((BigDecimal)res.get("dispatchedNum")) ;
			//派工数量是否大于0
			has = resNum.compareTo(new BigDecimal(0))>0;
		}
		return has;
	}

	@Override
	public boolean checkGoodsTask(String routeCid) {
		String sql = "select count(1) from MES_WM_ProduceProcessRouteCGoods where produceRouteCGid in "
				+ "(select gid from MES_WM_ProduceProcessRouteC where gid='"+routeCid+"') and receivedNum>0";
		return this.queryForInt(sql)>0;
	}

	@Override
	public void deleteProcessGoodsTask(List<String> oldProcessList) {
		String[] sqls = new String[oldProcessList.size()];
		for(int i=0;i<sqls.length;i++){
			sqls[i] = "delete from WM_Task where billGid='"+oldProcessList.get(i)+"' and billCode='0210'";
		}
		this.batchUpdate(sqls);
	}

	@Override
	public void reGenProcessGoodsTask(List<String> newNextProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WmTask> getIdleTask(String route_id) {
		String sql = "select "+CommonUtil.colsFromBean(WmTask.class,"t")+" FROM WM_Task t "
				+ "LEFT JOIN MES_WM_ProduceProcessRouteC prc ON t.billGid = prc.gid AND t.taskTypeUid = (SELECT	gid FROM WM_BillType WHERE billCode = '0210') "
				+ "LEFT JOIN MES_WM_ProduceProcessRoute pr on pr.gid=prc.produceRouteGid "
//				+ "LEFT JOIN MES_WM_ProduceProcessRouteC preprc on preprc.gid=prc.preGid "
				+ "WHERE prc.gid NOT IN ( SELECT produceRouteCGid FROM MES_WM_ProduceProcessRouteCGoods where receivedNum>0 ) "
				+ "and pr.gid='"+route_id+"' ";
//				+ "and ( preprc.dispatchedNum is null or preprc.dispatchedNum=0) ";
		return this.emiQueryList(sql, WmTask.class);
	}

	@Override
	public List<MesWmProduceProcessroutec> getIdleStartRoutec(String route_id) {
		//条件有 ：1、没有上道工序的  2、料没领的  3、没有开工的
		String sql = "SELECT "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"prc")
				+ " from MES_WM_ProduceProcessRouteC prc  "
				+ "LEFT JOIN MES_WM_ProduceProcessRoute pr on pr.gid=prc.produceRouteGid "
				+ "WHERE prc.gid IN ( SELECT produceRouteCGid FROM MES_WM_ProduceProcessRouteCGoods where receivedNum is null or receivedNum=0 ) "
				+ "and pr.gid='"+route_id+"'  "
				+ "and ( prc.dispatchedNum is null or prc.dispatchedNum=0) "
				+ "and (prc.preGid is null or prc.preGid='')";
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}
	
	@Override
	public List<MesWmProduceProcessroutec> getPreDispatchedRoutec(
			String route_id) {
		String sql = "SELECT "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class,"prc")
					+ "from  MES_WM_ProduceProcessRouteC preprc  "
					+ "LEFT JOIN MES_WM_ProduceProcessRouteC prc on charindex(preprc.gid,prc.preGid)>0 "
					+ "LEFT JOIN MES_WM_ProduceProcessRoute pr on pr.gid=prc.produceRouteGid  "
					+ "WHERE prc.gid IN ( SELECT produceRouteCGid FROM MES_WM_ProduceProcessRouteCGoods where receivedNum is null or receivedNum=0 )  "
					+ "and pr.gid='"+route_id+"'  "
					+ "and ( preprc.dispatchedNum>0) ";
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}

	@Override
	public void deleteTasks(String taskIds) {
		taskIds = taskIds.replaceAll(",", "','");
		String sql = "delete from WM_Task where gid in ('"+taskIds+"')";
		this.update(sql);
	}

	@Override
	public void deleteTasksByBillId(String billIds) {
		billIds = billIds.replaceAll(",", "','");
		String sql = "delete from WM_Task where billGid in ('"+billIds+"')";
		this.update(sql);
	}

	@Override
	public void insertObject(Object obj) {
		this.emiInsert(obj);
	}
	
	@Override
	public void updateObject(Object routec) {
		this.emiUpdate(routec);
	}

	@Override
	public MesWmProduceProcessroutec findProduceProcessroutec(
			String changeSrcRouteCid) {
		return (MesWmProduceProcessroutec) this.emiFind(changeSrcRouteCid, MesWmProduceProcessroutec.class);
	}

	@Override
	public void setOrdercTurnoutNum(String changeSrcOrderCid,
			BigDecimal realNumber) {
		String sql = "update WM_ProduceOrder_C set turnoutNum=isnull(turnoutNum,0)+"+this.formatValue(realNumber)+" where gid='"+changeSrcOrderCid+"'";
		this.update(sql);
	}

	@Override
	public WmProduceorderC findProduceOrderc(String changeSrcOrderCid) {
		return (WmProduceorderC) this.emiFind(changeSrcOrderCid, WmProduceorderC.class);
	}

	@Override
	public void clearChangeSrc(String ordercId, String routecId, String thisRouteId) {
		//先查询改制来源有多少数量
		String sqlNum = "select changeSrcNumber from MES_WM_ProduceProcessRoute where gid='"+thisRouteId+"'";
		Map numMap = this.queryForMap(sqlNum);
		if(numMap!=null){
			BigDecimal changeSrcNumber = numMap.get("changeSrcNumber")==null?new BigDecimal(0):(BigDecimal)numMap.get("changeSrcNumber");
			String sql1 = "update WM_ProduceOrder_C set turnoutNum=(case(turnoutNum-"+changeSrcNumber.doubleValue()+") when 0 then null else (turnoutNum-"+changeSrcNumber.doubleValue()+") end) where gid='"+ordercId+"'";//改制关联的已转数量清空-订单子表
			String sql2 = "update MES_WM_ProduceProcessRouteC set turnoutedNum=(case(turnoutedNum-"+changeSrcNumber.doubleValue()+") when 0 then null else (turnoutedNum-"+changeSrcNumber.doubleValue()+") end) where gid='"+routecId+"'";//改制关联的已转数量清空-工艺路线子表
			String sql3 = "update MES_WM_ProduceProcessRoute set changeSrcRouteCid=null,changeSrcOrderCid=null,changeSrcNumber=null where gid='"+thisRouteId+"' ";//changeSrcRouteCid='"+routecId+"' and changeSrcOrderCid='"+ordercId+"'";//本工艺路线子表
			this.bathUpdate(new String[]{sql1,sql2,sql3});
		}
	}

	@Override
	public List<WmProduceorderC> getOrderCListById(String ordercId) {
		ordercId = ordercId.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(WmProduceorderC.class)+" from WM_ProduceOrder_C where gid in ('"+ordercId+"')";
		return this.emiQueryList(sql, WmProduceorderC.class);
	}

	@Override
	public MesWmProduceProcessroute findProduceRouteByOrderC(String thisOrdercId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where produceCuid='"+thisOrdercId+"'";
		return (MesWmProduceProcessroute) this.emiQuery(sql, MesWmProduceProcessroute.class);
	}

	@Override
	public List<MesWmProduceProcessroute> getProduceRouteByOrder(String orderId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where produceUid='"+orderId+"'";
		return this.emiQueryList(sql, MesWmProduceProcessroute.class);
	}

	@Override
	public boolean checkGoodsTaskByRoute(String routeId) {
		String sql = "select count(1) from MES_WM_ProduceProcessRouteCGoods g,MES_WM_ProduceProcessRouteC rc where rc.produceRouteGid='"+routeId+"' and g.produceRouteCGid=rc.gid and g.receivedNum>0";
		return this.queryForInt(sql)>0;
	}

	@Override
	public BigDecimal getEnabledChangeNumber(String srcRoutecId) {
		String sql = "SELECT ( SELECT "
				+ "(isnull( prc.checkOkNum,prc.reportOkNum ) "
				+ "- ( "
				+ "		( SELECT isnull(dispatchedNum, 0) FROM MES_WM_ProduceProcessRouteC WHERE gid = prc.nextGid ) * prp.baseUse/prp.baseQuantity "
				+ "	 ) "
				+ ")-isnull(prc.turnoutedNum, 0) ) AS canChange "
				+ "FROM "
				+ "	MES_WM_ProduceProcessRouteC prc "
				+ "LEFT JOIN MES_WM_ProduceProcessRouteCPre prp on prp.routeCGid=prc.nextGid "
				+ "WHERE "
				+ "	prc.gid = '"+srcRoutecId+"' ";
		Map map = this.queryForMap(sql);
		return (BigDecimal) map.get("canChange");
	}

	@Override
	public int[] changeRealPrice(final List<Map> maps) {
		String sql="update MES_WM_ProduceProcessRouteC set realPrice=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Map m=maps.get(index);
				ps.setString(1, m.get("realPrice").toString());
				ps.setString(2, m.get("routeCid").toString());
				
			}
			
			@Override
			public int getBatchSize() {
				return maps.size();
			}
		});
	}
	
	@Override
	public MesWmStandardprocess getMesWmStandardprocess(String opname) {
		String sql="select "+CommonUtil.colsFromBean(MesWmStandardprocess.class)+" from MES_WM_StandardProcess where opname='"+opname+"'";
		return (MesWmStandardprocess)emiQuery(sql, MesWmStandardprocess.class);
	}
	
	
	public List<WmProduceorderC2> getWmProduceorderC2(String produceCuid) {
		String sql="select "+CommonUtil.colsFromBean(WmProduceorderC2.class)+" from WM_ProduceOrder_C2 where produceOrderCuid='"+produceCuid+"'";
		return this.emiQueryList(sql, WmProduceorderC2.class);
	}


}
