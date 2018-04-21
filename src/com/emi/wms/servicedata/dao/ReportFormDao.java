package com.emi.wms.servicedata.dao;


import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.WmProduceorder;

public class ReportFormDao extends BaseDao{

	/**
	 * @category 获得计件工资汇总表
	 *2016 2016年6月16日下午4:30:39
	 *List<Map>
	 *宋银海
	 */
	public PageBean getPieceworkSum(int pageIndex,int pageSize,String docCondition,String rocCondition){
		String sql ="SELECT isnull(doc.dispatchingObj,roc.dispatchingObj) dispatchingObj, "+
						"isnull(doc.personUnitVendorGid,roc.personUnitVendorGid) personUnitVendorGid, isnull(doc.deptgid,roc.deptgid) deptgid, "+
					    "SUM(doc.disNum) disNum, SUM(roc.reportOkNum) reportOkNum,  isnull(SUM(prc.realPrice * roc.reportOkNum),0) totalPrice FROM "+
					"(SELECT t_m.dispatchingObj,t.produceProcessRouteCGid,t.gid,t.disNum,t.personUnitVendorGid,t.deptgid FROM MES_WM_DispatchingOrderC t "+
						"LEFT JOIN MES_WM_DispatchingOrder t_m ON t.disGid = t_m.gid WHERE "+docCondition+") doc "+
					"FULL JOIN "+
					"(SELECT t2_m.dispatchingObj,t2.produceProcessRouteCGid,t2.gid,t2.reportOkNum,t2.personUnitVendorGid,t2.discGid,t2.deptgid FROM MES_WM_ReportOrderC t2 "+
						"LEFT JOIN MES_WM_ReportOrder t2_m ON t2.rptGid = t2_m.gid WHERE "+rocCondition+") roc "+
					"ON doc.gid = roc.discGid "+
					"LEFT JOIN MES_WM_ProduceProcessRouteC prc ON ISNULL(doc.produceProcessRouteCGid,roc.produceProcessRouteCGid) = prc.gid "+
					"LEFT JOIN MES_WM_ProduceProcessRoute pr ON pr.gid = prc.produceRouteGid "+
					"LEFT JOIN WM_ProduceOrder_C poc ON poc.gid = pr.produceCuid "+
					"LEFT JOIN WM_ProduceOrder po ON po.gid = pr.produceUid "+
					"GROUP BY isnull(doc.dispatchingObj,roc.dispatchingObj),isnull(doc.personUnitVendorGid,roc.personUnitVendorGid),isnull(doc.deptgid,roc.deptgid) ";
		
		return this.emiQueryList(sql, pageIndex, pageSize, " personUnitVendorGid ");
	}

	/**
	 * @category 计件工资详情
	 * 2016年6月16日 下午5:52:14 
	 * @author zhuxiaochen
	 * @param billcode
	 * @param deptId
	 * @param personId
	 * @param groupId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PageBean getPieceworkDetail(String billcode, String deptId,
			String personId, String groupId, String startDate, String endDate,int pageIndex,int pageSize) {
		String sql = "SELECT po.billCode,poc.goodsUid,poc.number,poc.completedNum,prc.productInNum, ";
			sql += "isnull(doc.dispatchingObj,roc.dispatchingObj) dispatchingObj, ";
			sql += "isnull(doc.personUnitVendorGid,roc.personUnitVendorGid) personUnitVendorGid, ";
			sql += "isnull(doc.produceProcessRouteCGid,roc.produceProcessRouteCGid) produceProcessRouteCGid, ";
			sql += "isnull(doc.depName,roc.depName) depName, ";
			sql += "prc.opGid,mws.opname,doc.disNum,roc.reportOkNum,prc.realPrice,doc.startTime,roc.endTime, ";
			sql += "(prc.realPrice * roc.reportOkNum) totalPrice ";
			sql += "FROM ";
			sql += "( ";
			sql += "	SELECT t_m.dispatchingObj,t.produceProcessRouteCGid,t.gid,t.disNum,t.personUnitVendorGid,t.startTime,t.deptGid,t_d.depName ";
			sql += "	FROM MES_WM_DispatchingOrderC t LEFT JOIN MES_WM_DispatchingOrder t_m ON t.disGid = t_m.gid "
					+ " left join AA_Department t_d on t.deptGid=t_d.gid ";
			sql += "	WHERE 1=1 ";
			if(CommonUtil.notNullString(startDate)){
				sql += "	AND t.startTime > '"+startDate+" 00:00:00' ";
			}
			if(CommonUtil.notNullString(endDate)){
				/*String lastDay = DateUtil.getLastDayOfMonth(Integer.parseInt(endDate.substring(0,4)), "dd")[Integer.parseInt(endDate.substring(5,7))-1];*/
				sql += "	AND t.startTime < '"+endDate+" 23:59:59' ";
			}
			sql += ") doc ";
			sql += "FULL JOIN ( ";
			sql += "	SELECT t2_m.dispatchingObj,t2.produceProcessRouteCGid,t2.gid,t2.reportOkNum,t2.personUnitVendorGid,t2.discGid,t2.endTime,t2.deptGid,t2_d.depName ";
			sql += "	FROM MES_WM_ReportOrderC t2 LEFT JOIN MES_WM_ReportOrder t2_m ON t2.rptGid = t2_m.gid "
					+ " LEFT JOIN AA_Department t2_d on t2.deptGid=t2_d.gid ";
			sql += "	WHERE 1=1 ";
			if(CommonUtil.notNullString(startDate)){
				sql += "	AND t2.endTime > '"+startDate+" 00:00:00' ";
			}
			if(CommonUtil.notNullString(endDate)){
			/*	String lastDay = DateUtil.getLastDayOfMonth(Integer.parseInt(endDate.substring(0,4)), "dd")[Integer.parseInt(endDate.substring(5,7))-1];*/
				sql += "	AND t2.endTime < '"+endDate+" 23:59:59' ";
			}
			sql += " ) roc ON doc.gid = roc.discGid ";
			sql += "LEFT JOIN MES_WM_ProduceProcessRouteC prc ON ISNULL( doc.produceProcessRouteCGid,roc.produceProcessRouteCGid ) = prc.gid ";
			sql += "LEFT JOIN MES_WM_ProduceProcessRoute pr ON pr.gid = prc.produceRouteGid ";
			sql += "LEFT JOIN WM_ProduceOrder_C poc ON poc.gid = pr.produceCuid ";
			sql += "LEFT JOIN WM_ProduceOrder po ON po.gid = pr.produceUid "
					+ "LEFT JOIN MES_WM_StandardProcess mws on prc.opGid=mws.gid ";
			sql += "WHERE 1=1 ";
			boolean both = false;
			if(CommonUtil.notNullString(personId) && CommonUtil.notNullString(groupId)){
				both = true;
			}
			if(CommonUtil.notNullString(personId)){
				sql += " AND "+(both?"(":"")+"(ISNULL(doc.personUnitVendorGid,roc.personUnitVendorGid) in ('"+personId+"') and doc.dispatchingObj=0 ) ";
			}
			if(CommonUtil.notNullString(groupId)){
				sql += (both?" OR ":" AND ")+"(ISNULL(doc.personUnitVendorGid,roc.personUnitVendorGid) in ('"+groupId+"') and doc.dispatchingObj=1 ) "+(both?")":"")+" ";
			}
			if (CommonUtil.notNullString(deptId)) {
				sql+= (both?" OR ":" AND ")+"(ISNULL(doc.deptGid,roc.deptGid) in ('"+deptId+"')) "+(both?")":"")+" ";
			}
			
			if(CommonUtil.notNullString(billcode)){
				sql += "AND billCode LIKE '%"+billcode+"%'";
			}
			
		return this.emiQueryList(sql, pageIndex, pageSize, "endtime DESC");
	}

	
	/**
	 * @category 获得订单状态
	 * 宋银海
	 */
	public PageBean getOrderStatusList(int pageIndex,int pageSize,String condition){
		String sql ="SELECT billCode,billDate,po.notes ponotes,poc.goodsUid,poc.number,poc.turnoutNum,poc.notes pocnotes,poc.gid pocgid  "+
					"FROM WM_ProduceOrder po LEFT JOIN WM_ProduceOrder_C poc on po.gid=poc.produceOrderUid where "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, " billDate desc ");
	}

	public PageBean getOrderDetailList(int pageIndex, int pageSize,
			String condition) {
		String sql = "select produceprocessc.*,standardprocess.opcode,standardprocess.opname,poc.goodsUid,po.billDate,po.billCode "
//				+ ",equipname = (stuff((select ',' + equipmentName from equipment equipment left join MES_WM_ProduceProcessRouteCEquipment MES_WM_ProduceProcessRouteCEquipment on equipment.gid=MES_WM_ProduceProcessRouteCEquipment.equipmentGid where MES_WM_ProduceProcessRouteCEquipment.routeCGid = produceprocessc.gid order by equipment.equipmentCode for xml path('')),1,1,'')) "
//				+ ",mouldname = (stuff((select ',' + mouldName from mould mould left join MES_WM_ProduceProcessRouteCMould MES_WM_ProduceProcessRouteCMould on mould.gid=MES_WM_ProduceProcessRouteCMould.mouldGid where MES_WM_ProduceProcessRouteCMould.routeCGid = produceprocessc.gid order by mould.mouldCode for xml path('')),1,1,'')) "
//				+ ",(select wcname from MES_AA_WorkCenter MES_AA_WorkCenter where MES_AA_WorkCenter.gid = produceprocessc.workCenterId) workcentername "
				+ "from MES_WM_ProduceProcessRouteC produceprocessc "
				+ "left join MES_WM_ProduceProcessRoute ppr on produceprocessc.produceRouteGid=ppr.gid "
				+ "left join WM_ProduceOrder po on ppr.produceUid=po.gid "
				+ "left join WM_ProduceOrder_C poc on ppr.produceCuid=poc.gid "
				+ "left join MES_WM_StandardProcess standardprocess on standardprocess.gid = produceprocessc.opGid "
				+ "where 1=1 "+condition;
		
		return this.emiQueryList(sql, pageIndex, pageSize, "updateTime desc");
	}
	
	
	
}
