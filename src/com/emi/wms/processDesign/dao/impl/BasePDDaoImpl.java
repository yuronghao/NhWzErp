package com.emi.wms.processDesign.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProcessRouteCMould;
import com.emi.wms.bean.MesWmProcessRouteCPre;
import com.emi.wms.bean.MesWmProcessRoutecGoods;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.MesWmProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.processDesign.dao.BasePDDao;
@SuppressWarnings("unchecked")
public class BasePDDaoImpl extends BaseDao implements BasePDDao {

	@Override
	public PageBean getBaseProcessRouteList(String condition, int pageIndex,
			int pageSize) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessroute.class,"r")+" from MES_WM_ProcessRoute r,AA_Goods g where (r.isDelete = 0 OR r.isDelete IS NULL) "+condition+" and r.goodsUid=g.gid";
		return this.emiQueryList(sql, pageIndex, pageSize,MesWmProcessroute.class, "isnull(state,0),pk");
	}

	@Override
	public MesWmProcessroute findBaseRoute(String routeId) {
		return (MesWmProcessroute) this.emiFind(routeId, MesWmProcessroute.class);
	}

	@Override
	public void updateBaseRoute(MesWmProcessroute route) {
		this.emiUpdate(route);
	}

	@Override
	public void insertBaseRoute(MesWmProcessroute route) {
		this.emiInsert(route);
	}

	@Override
	public List<MesWmProcessroutec> getRouteCList(int increamentId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessroutec.class)+" from MES_WM_ProcessRouteC where routid="+increamentId;
		return this.emiQueryList(sql, MesWmProcessroutec.class);
	}

	@Override
	public MesWmProcessroutec finBaseRouteC(String routecId) {
		return (MesWmProcessroutec) this.emiFind(routecId,MesWmProcessroutec.class);
	}

	@Override
	public List<MesWmProcessRouteCPre> getPreProcessList(String routecId) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRouteCPre.class)+" from MES_WM_ProcessRouteCPre "
				+ " where routeCGid='"+routecId+"'";
		return this.emiQueryList(sql, MesWmProcessRouteCPre.class);
	}

	@Override
	public List<MesWmProcessroutec> getRouteCList(String route_id) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessroutec.class)+" from MES_WM_ProcessRouteC where routGid='"+route_id+"'";
		return this.emiQueryList(sql, MesWmProcessroutec.class);
	}

	@Override
	public List<MesWmProcessroutec> getRouteCListIn(String route_id) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessroutec.class)+" from MES_WM_ProcessRouteC where routGid in "+route_id+" and "
				+ " (nextGid is null or nextGid ='' )  ";
		return this.emiQueryList(sql, MesWmProcessroutec.class);
	}
	
	@Override
	public void updateProcessInfo(String route_id, String designJson) {
		String sql = "update MES_WM_ProcessRoute set designJson='"+designJson.replaceAll("'", "''")+"' where gid='"+route_id+"'";
		this.update(sql);
	}

	@Override
	public void updateObject(Object object) {
		this.emiUpdate(object);
	}

	@Override
	public void addObject(Object obj) {
		this.emiInsert(obj);
	}

	@Override
	public void deleteBaseRoute(String routeId) {
		routeId = routeId.replaceAll(",", "','"); 
		String sql = "update MES_WM_ProcessRoute set isDelete=1 where gid in ('"+routeId+"')";
		this.update(sql);
	}
	
	public void deleteStandardProcess(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update MES_WM_StandardProcess set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}
	
	public void deleteWorkCenter(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update MES_AA_WorkCenter set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}

	@Override
	public void addProcessRouteC(List<MesWmProcessroutec> addList) {
		this.emiInsert(addList);
		
	}

	@Override
	public void updateProcessRouteC(List<MesWmProcessroutec> updateList) {
		this.emiUpdate(updateList);
	}

	@Override
	public void deleteRoutecAttributes(String updateIds) {
		updateIds = updateIds.replaceAll(",", "','"); 
		String sql1 = "delete from MES_WM_ProcessRouteCPre where routeCGid in ('"+updateIds+"')";
		String sql2 = "delete from MES_WM_ProcessRouteCGoods where routeCGid in ('"+updateIds+"')";
		String sql3 = "delete from MES_WM_ProcessRouteCDispatching where routeCGid in ('"+updateIds+"')";
		String sql4 = "delete from MES_WM_ProcessRouteCEquipment where routeCGid in ('"+updateIds+"')";
		String sql5 = "delete from MES_WM_ProcessRouteCMould where routeCGid in ('"+updateIds+"')";
		this.batchUpdate(new String[]{sql1,sql2,sql3,sql4,sql5});
		
	}

	@Override
	public void insertRoutecAttributes(List<MesWmProcessroutec> updateList,
			List<MesWmProcessroutec> addList) {
		List<MesWmProcessRouteCPre> preList = new ArrayList<MesWmProcessRouteCPre>();
		List<MesWmProcessRoutecGoods> goodsList = new ArrayList<MesWmProcessRoutecGoods>();
		List<MesWmProcessRouteCDispatching> workCenterList = new ArrayList<MesWmProcessRouteCDispatching>();
		List<MesWmProcessRouteCEquipment> equipmentList = new ArrayList<MesWmProcessRouteCEquipment>();
		List<MesWmProcessRouteCMould> mouldList = new ArrayList<MesWmProcessRouteCMould>();
		//合并同类列表
		for(MesWmProcessroutec routec : updateList){
			preList.addAll(routec.getPreList());
			goodsList.addAll(routec.getGoodsList());
			workCenterList.addAll(routec.getDispatchingList());
			equipmentList.addAll(routec.getEquipmentList());
			mouldList.addAll(routec.getMouldList());
		}
		for(MesWmProcessroutec routec : addList){
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
	public List<MesWmProcessRouteCPre> getProcessRouteCPreList(String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRouteCPre.class)+" from MES_WM_ProcessRouteCPre where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProcessRouteCPre.class);
	}

	@Override
	public List<MesWmProcessRoutecGoods> getProcessRouteCGoodsList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRoutecGoods.class)+" from MES_WM_ProcessRouteCGoods where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProcessRoutecGoods.class);
	}

	@Override
	public List<MesWmProcessRouteCDispatching> getProcessRouteCDispatchingList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRouteCDispatching.class)+" from MES_WM_ProcessRouteCDispatching where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProcessRouteCDispatching.class);
	}

	@Override
	public void deleteProcessRouteC(String deleteIds) {
		deleteIds = deleteIds.replaceAll(",", "','");
		String sql = "delete from MES_WM_ProcessRouteC where gid in ('"+deleteIds+"')";
		//关联的子表
		String sql_pre = "delete from MES_WM_ProcessRouteCPre where routeCGid in ('"+deleteIds+"')";
		String sql_goods = "delete from MES_WM_ProcessRouteCGoods where routeCGid in ('"+deleteIds+"')";
		String sql_dis = "delete from MES_WM_ProcessRouteCDispatching where routeCGid in ('"+deleteIds+"')";
		String sql_equ = "delete from MES_WM_ProcessRouteCEquipment where routeCGid in ('"+deleteIds+"')";
		String sql_mould = "delete from MES_WM_ProcessRouteCMould where routeCGid in ('"+deleteIds+"')";
		this.bathUpdate(new String[]{sql,sql_pre,sql_goods,sql_dis,sql_equ,sql_mould});
	}
	
	public PageBean getStandardProcessList(int pageIndex,int pageSize,String condition){
		String sql ="select "+CommonUtil.colsFromBean(MesWmStandardprocess.class,"MesWmStandardprocess")+" from MES_WM_StandardProcess MesWmStandardprocess where 1=1 and isDelete=0 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, MesWmStandardprocess.class, "");
	}
	
	public PageBean getWorkCenterList(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("depName", "MesAaWorkcenter.depName");
		String sql ="select "+CommonUtil.colsFromBean(MesAaWorkcenter.class,"MesAaWorkcenter")+",AADepartment.depName depName from MES_AA_WorkCenter MesAaWorkcenter"
				+ " left join AA_Department AADepartment on AADepartment.gid = MesAaWorkcenter.depUid where 1=1 and (isDelete=0 or isDelete is null)";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, MesAaWorkcenter.class, match,"");
	}
	
	public boolean saveStandardProcess(MesWmStandardprocess standardprocess){
		return this.emiInsert(standardprocess);
	}
	
	public boolean saveWorkCenter(MesAaWorkcenter workcenter){
		return this.emiInsert(workcenter);
	}
	
	public MesWmStandardprocess findStandardProcess(String gid){
		return (MesWmStandardprocess)this.emiFind(gid, MesWmStandardprocess.class);
	}
	
	public MesAaWorkcenter findWorkCenter(String gid){
		return (MesAaWorkcenter)this.emiFind(gid, MesAaWorkcenter.class);
	}
	
	public AaDepartment findDepartment(String gid){
		return (AaDepartment)this.emiFind(gid, AaDepartment.class);
	}
	
	public boolean updateStandardProcess(MesWmStandardprocess standardprocess){
		return this.emiUpdate(standardprocess);
	}
	
	public boolean updateWorkCenter(MesAaWorkcenter workcenter){
		return this.emiUpdate(workcenter);
	}
	
	public List getDepList(String condition){
		String sql ="select * from AA_Department where 1=1 ";
		if(!CommonUtil.isNullObject(condition)){
			sql=sql+condition;
		}
		return this.queryForList(sql);
	}
	
	public List getCustomerList(String condition){
		String sql ="select * from AA_Provider_Customer where 1=1 and customerId is not null";
		if(!CommonUtil.isNullObject(condition)){
			sql=sql+condition;
		}
		return this.queryForList(sql);
	}

	
	/*@Override
	public MesWmProcessroute getBaseProcessRouteOverall(String goodsId) {
		//查询主表
		String sql_route = "select "+CommonUtil.colsFromBean(MesWmProcessroute.class)+" from MES_WM_ProcessRoute where goodsUid='"+goodsId+"'";
		MesWmProcessroute route = (MesWmProcessroute) this.emiQuery(sql_route, MesWmProcessroute.class);
		if(route!=null){
			//查询子表
			String sql_routeC = "select "+CommonUtil.colsFromBean(MesWmProcessroutec.class)+" from MES_WM_ProcessRouteC where routGid='"+route.getGid()+"'";
		}
		
		return route;
	}*/

	@Override
	public MesWmProcessroute getBaseRouteByGoods(String goodsId) {
		String sql_route = "select "+CommonUtil.colsFromBean(MesWmProcessroute.class)+" from MES_WM_ProcessRoute where goodsUid='"+goodsId+"' and (isDelete=0 or isDelete is null) order by pk desc";
		return (MesWmProcessroute) this.emiQuery(sql_route, MesWmProcessroute.class);
	}

	@Override
	public MesWmProcessroute getBaseRouteByGoodsCfree(String goodsId,String cfree) {
		String sql="SELECT "+CommonUtil.colsFromBean(MesWmProcessroute.class,"r")+" from MES_WM_ProcessRoute r LEFT JOIN MES_WM_ProcessRouteC rc on r.gid=rc.routGid "+
					"LEFT JOIN MES_WM_StandardProcess s on rc.opgid=s.gid "+
					"where r.goodsUid='"+goodsId+"' "+  
					"and s.opname='"+cfree+"' and (rc.nextGid is null or rc.nextGid ='' ) "+
					"and (r.isDelete=0 or r.isDelete is null) "+
					"order by r.pk desc";
		
		return (MesWmProcessroute) this.emiQuery(sql, MesWmProcessroute.class);
	}
	
	@Override
	public List<MesWmProcessRouteCEquipment> getProcessRouteCEquipmentList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRouteCEquipment.class)+" from MES_WM_ProcessRouteCEquipment where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProcessRouteCEquipment.class);
	}
	
	@Override
	public List<MesWmProcessRouteCMould> getProcessRouteCMouldList(
			String routeCids) {
		routeCids = routeCids.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessRouteCMould.class)+" from MES_WM_ProcessRouteCMould where routeCGid in ('"+routeCids+"')";
		return this.emiQueryList(sql, MesWmProcessRouteCMould.class);
	}

	@Override
	public void addProcessRoute(List<MesWmProcessroute> routeList) {
		this.emiInsert(routeList);
	}

	/**
	 * -为导入工艺定制
	 */
	@Override
	public void addImportProcessRoute(List<MesWmProcessroute> routeList) {
		String[] sqls = new String[routeList.size()];
		int i = 0;
		for(MesWmProcessroute r : routeList){
			sqls[i] = "insert into MES_WM_ProcessRoute(gid,effdate,routname,goodsUid,isDelete,designJson) "
					+ "values("+this.formatValue(r.getGid())+","+this.formatValue(r.getEffdate())+","+this.formatValue(r.getRoutname())+",(select gid from AA_Goods where goodsCode='"+r.getGoodsUid()+"'),0,"
					+ "'"+r.getDesignJson()+"')";
			i++;
		}
		this.bathUpdate(sqls);
		
	}

	/**
	 * -为导入工艺定制
	 * @throws Exception 
	 */
	@Override
	public void addImportProcessRouteC(List<MesWmProcessroutec> routeClist) throws Exception {
		String[] sqls = new String[routeClist.size()];
		int i = 0;
		for(MesWmProcessroutec rc : routeClist){
			String presql = CommonUtil.isNullString( rc.getPreGid())?"null":"(select gid from MES_WM_StandardProcess where opname='"+rc.getPreGid()+"' and (isDelete=0 or isDelete is null)) ";
			String nextsql = CommonUtil.isNullString( rc.getNextGid())?"null":"(select gid from MES_WM_StandardProcess where opname='"+rc.getNextGid()+"' and (isDelete=0 or isDelete is null)) ";
			sqls[i] = "insert into MES_WM_ProcessRouteC(gid,routGid,cindex,opGid,isCheck,isStock,passRate,realPrice,preGid,nextGid,isOut,dispatchingType,number,updateTime,stockGoodsId,isSemi,workCenterId,standardHours,opdes) "
					+ " select "+this.formatValue(rc.getGid())+","+this.formatValue(rc.getRoutGid())+",'"+rc.getCindex()+"',gid,isCheck,isStock,checkRate/100,"+(rc.getRealPrice()==null?"standardPrice":this.formatValue(rc.getRealPrice()))+","
					+ presql+","+nextsql
					+ ","+rc.getIsOut()+","+rc.getDispatchingType()+","+this.formatValue(rc.getNumber())+","
					+ ""+this.formatValue(rc.getUpdateTime())+",'"+(rc.getIsStock()==1?rc.getStockGoodsId():"")+"',"+rc.getIsSemi()+",'"+rc.getWorkCenterId()+"',"+this.formatValue(rc.getStandardHours())+","+this.formatValue(rc.getOpdes())+" from MES_WM_StandardProcess where opname='"+rc.getOpGid()+"' and (isDelete=0 or isDelete is null)";
			i++;
		}
		int result[] = this.bathUpdate(sqls);
		for(int r : result){
			if(r==0){
				throw new Exception("导入错误，存在未知的工序名称");
			}
		}
	}

	/**
	 * -为导入工艺定制
	 * @throws Exception 
	 */
	@Override
	public void addImportProcessRouteCGoods(List<MesWmProcessRoutecGoods> goodsList) throws Exception {
		String[] sqls = new String[goodsList.size()];
		int i = 0;
		for(MesWmProcessRoutecGoods g : goodsList){
			String[] codes = g.getRouteCGid().split(",");
			String productCode = codes[0];
			String opName = codes[1];
			sqls[i] = "insert into MES_WM_ProcessRouteCGoods(gid,routeCGid,goodsGid,baseUse,baseQuantity,number,free1) "
					+ "values("+this.formatValue(g.getGid())+""
					+ ",(select top 1 rc.gid from MES_WM_ProcessRouteC rc left join MES_WM_ProcessRoute r on rc.routGid=r.gid "
					+ "where (r.deffdate is null or r.deffdate>getdate() ) and (r.isDelete=0 or r.isDelete is null) and r.goodsUid=(select gid from AA_Goods where goodsCode='"+productCode+"') "
					+ "and rc.opGid=(select gid from MES_WM_StandardProcess where opname='"+opName+"' and (isDelete=0 or isDelete is null)))"
					+ ",(select gid from AA_Goods where goodsCode='"+g.getGoodsGid()+"'),"+this.formatValue(g.getBaseUse())+","+this.formatValue(g.getBaseQuantity())+","+this.formatValue(g.getNumber())+""
					+ ",'"+g.getFree1()+"')";
			i++;
		}
		int result[] = this.bathUpdate(sqls);
		for(int r : result){
			if(r==0){
				throw new Exception("导入错误，存在未知的存货（物料）编码");
			}
		}
	}

	@Override
	public void insertCopyedRoute(MesWmProcessroute route,
			List<MesWmProcessroutec> routec, List<MesWmProcessRouteCPre> pre,
			List<MesWmProcessRoutecGoods> goods,
			List<MesWmProcessRouteCDispatching> dispatching,
			List<MesWmProcessRouteCEquipment> equ,List<MesWmProcessRouteCMould> mould) {
		this.emiInsert(route);
		this.emiInsert(routec);
		this.emiInsert(pre);
		this.emiInsert(goods);
		this.emiInsert(dispatching);
		this.emiInsert(equ);
		this.emiInsert(mould);
	}

	@Override
	public List<AaFreeSet> getGoodsFreeset() {
		String sql = "select "+CommonUtil.colsFromBean(AaFreeSet.class)+" from AA_freeSet where type='g' and projectName is not null and projectName!=''";
		return this.emiQueryList(sql, AaFreeSet.class);
	}

	@Override
	public void cancelAuditRoute(String routeId) {
		routeId = routeId.replaceAll(",", "','");
		String sql = "update MES_WM_ProcessRoute set state='0',authDate=null,authUser=null where gid in ('"+routeId+"')";
		this.update(sql);
	}

	@Override
	public List<MesWmProcessroutec> getRouteCListByIds(String routecids_free) {
		routecids_free = routecids_free.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(MesWmProcessroutec.class)+" from MES_WM_ProcessRouteC where gid in('"+routecids_free+"')";
		return this.emiQueryList(sql, MesWmProcessroutec.class);
	}
	

}
