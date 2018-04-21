package com.emi.wms.servicedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProduceProcessDao extends BaseDao {
	public boolean addproduceprocess(MesWmProduceProcessroute produceprocess) {
		return this.emiInsert(produceprocess);
	}
	public boolean addproduceprocessreason(QMCheckCReasonBill reason) {
		return this.emiInsert(reason);
	}
	public boolean addprocusbook(List list) {
		return this.emiInsert(list);
	}
	public Map findorg(String exhTypeId) {
		String sql="select * from AA_Org where gid='"+exhTypeId+"'";
		return  this.queryForMap(sql);
	}
	
	public boolean updateorg(AaOrg aaorg) {
		return this.emiUpdate(aaorg);
	}
	
	public boolean updateproduceprocess(MesWmProduceProcessroute produceprocess) {
		return this.emiUpdate(produceprocess);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deleteproduceprocess(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getproduceprocessbookList(String exhTypeId) {
		String sql="select * from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
		return  this.queryForList(sql);
	}
	public boolean findorgchild(String orgclassid){
		String sql = "select count(*) from AA_Org where parentid = '"+orgclassid+"'";
		int count = this.queryForInt(sql);
		if(count!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public Map findproduceprocess(String produceprocessgid) {
		String sql="";
		if(CommonUtil.isNullString(produceprocessgid)){
			sql="SELECT TOP 1 produceprocess.gid produceprocessgid,produceorderc.pk produceordercpk,* FROM MES_WM_ProduceProcessRoute produceprocess left join WM_ProduceOrder produceorder on produceorder.gid = produceprocess.produceUid left join WM_ProduceOrder_C produceorderc on produceorderc.gid = produceprocess.produceCuid ORDER BY produceprocess.pk DESC";
		}else{
			sql="SELECT produceprocess.gid produceprocessgid,produceorderc.pk produceordercpk,* FROM MES_WM_ProduceProcessRoute produceprocess left join WM_ProduceOrder produceorder on produceorder.gid = produceprocess.produceUid left join WM_ProduceOrder_C produceorderc on produceorderc.gid = produceprocess.produceCuid where produceprocess.gid = '"+produceprocessgid+"' order by produceprocess.pk desc";
		}
		return  this.queryForMap(sql);
	}
	
	public void setproduceprocessEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addproduceprocessc(List list) {
		return this.emiInsert(list);
	}
	public boolean updateproduceprocessc(List list) {
		return this.emiUpdate(list);
	}
	public List getproduceprocessclist(String purchaseproduceprocessUid) {
		String sql = "select *,produceprocessc.gid produceprocesscGid,produceprocessc.isPass"
				+ ",equipname = (stuff((select ',' + equipmentName from equipment equipment left join MES_WM_ProduceProcessRouteCEquipment MES_WM_ProduceProcessRouteCEquipment on equipment.gid=MES_WM_ProduceProcessRouteCEquipment.equipmentGid where MES_WM_ProduceProcessRouteCEquipment.routeCGid = produceprocessc.gid order by equipment.equipmentCode for xml path('')),1,1,'')) "
				+ ",mouldname = (stuff((select ',' + mouldName from mould mould left join MES_WM_ProduceProcessRouteCMould MES_WM_ProduceProcessRouteCMould on mould.gid=MES_WM_ProduceProcessRouteCMould.mouldGid where MES_WM_ProduceProcessRouteCMould.routeCGid = produceprocessc.gid order by mould.mouldCode for xml path('')),1,1,'')) "
				+ ",(select wcname from MES_AA_WorkCenter MES_AA_WorkCenter where MES_AA_WorkCenter.gid = produceprocessc.workCenterId) workcentername "
				+ "from MES_WM_ProduceProcessRouteC produceprocessc left join MES_WM_StandardProcess standardprocess on standardprocess.gid = produceprocessc.opGid where produceRouteGid='"+purchaseproduceprocessUid+"' order by produceprocessc.cindex asc";
		return this.queryForList(sql);
	}
	public List getdispatchingorderclist(String purchaseproduceprocessUid) {
		String sql = "select * from MES_WM_ProduceProcessRouteC produceprocessc left join MES_WM_StandardProcess standardprocess on standardprocess.gid = produceprocessc.opGid left join MES_WM_DispatchingOrderC dispatchingorderc on dispatchingorderc.produceProcessRouteCGid=produceprocessc.gid left join MES_WM_DispatchingOrder dispatchingorder on dispatchingorderc.disGid = dispatchingorder.gid where produceRouteGid='"+purchaseproduceprocessUid+"' order by produceprocessc.cindex asc";
		return this.queryForList(sql);
	}
	public PageBean getreasonlist(int pageIndex,int pageSize,String gid) {
		String sql = "select QM_CheckCReasonbill.pk,QM_CheckCReasonbill.num,AA_Reason.reasonname,AA_Reason.reasoncode,AA_Reason.reasontype from QM_CheckCReasonbill QM_CheckCReasonbill left join AA_Reason AA_Reason on AA_Reason.gid=QM_CheckCReasonbill.reasonGid where checkcGid='"+gid+"'";
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}
	public List getaareasonlist() {
		String sql = "select * from AA_Reason";
		return this.queryForList(sql);
	}
	public PageBean getproduceprocesslist(int pageIndex,int pageSize,String condition) {
		String sql = "select produceprocess.pk,produceprocess.gid,produceorder.billCode,produceorder.billDate,produceorderc.goodsUid from MES_WM_ProduceProcessRoute produceprocess left join WM_ProduceOrder produceorder on produceorder.gid = produceprocess.produceUid left join WM_ProduceOrder_C produceorderc on produceorderc.gid = produceprocess.produceCuid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, " billDate desc");
	}
	public PageBean dispatchingorderList(int pageIndex,int pageSize,String condition) {
		String sql = "SELECT produceprocess.gid,produceprocess.pk,produceorder.billCode,produceorder.billDate,produceorderc.goodsUid,dispatchingorderc.startTime,dispatchingorderc.disNum,dispatchingorderc.reportOkNum,dispatchingorderc.reportNotOkNum,dispatchingorderc.reportProblemNum,standardprocess.opcode,standardprocess.opname,dispatchingorderc.personUnitVendorGid,dispatchingorderc.notes,dispatchingorder.dispatchingObj FROM MES_WM_ProduceProcessRoute produceprocess LEFT JOIN WM_ProduceOrder produceorder ON produceorder.gid = produceprocess.produceUid LEFT JOIN WM_ProduceOrder_C produceorderc ON produceorderc.gid = produceprocess.produceCuid LEFT JOIN MES_WM_ProduceProcessRouteC produceprocessc on produceprocessc.produceRouteGid = produceprocess.gid left join MES_WM_StandardProcess standardprocess on standardprocess.gid = produceprocessc.opGid left join MES_WM_DispatchingOrderC dispatchingorderc on dispatchingorderc.produceProcessRouteCGid=produceprocessc.gid left join MES_WM_DispatchingOrder dispatchingorder on dispatchingorderc.disGid = dispatchingorder.gid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, " billDate desc");
	}
	
	
	/**
	 * @category 开工时，根据订单号获取相关信息
	 *2016 2016年7月11日下午1:27:52
	 *void
	 *宋银海
	 */
	public List<Map> getProduceProcessInforByOrder(String condition){
		String sql="SELECT pprc.opgid,pprc.barcode,pprc.dispatchingType from MES_WM_ProduceProcessRoute ppr "
				+ " LEFT JOIN MES_WM_ProduceProcessRouteC pprc on ppr.gid=pprc.produceRouteGid  where "+condition+" ORDER by cindex";
		return this.queryForList(sql);
	}
	
	//查询开工最后一条记录	
	@SuppressWarnings("rawtypes")
	public Map findDispatching(String gid,String orgId,String sobId){
		String sql="";
		if(CommonUtil.isNullString(gid)){
			sql="SELECT TOP 1 pk,gid,dispatchingObj,billcode,  stationGid,mouldGid,workingTime FROM MES_WM_DispatchingOrder where sobGid = '" + sobId + "' AND orgGid = '" + orgId + "' ORDER BY	pk DESC ";
		}else{
			sql="SELECT pk,gid,dispatchingObj,billcode, stationGid,mouldGid,workingTime FROM MES_WM_DispatchingOrder  WHERE	gid='"+gid+"' AND sobGid = '" + sobId + "' AND orgGid = '" + orgId + "' ORDER By pk DESC ";
		}
		
		return  this.queryForMap(sql);
	}
	
	//根据父id 查询派工单子表
	public List<Map> findDispatchings(String parentGid){
		String sql="SELECT dc.gid,dc.disNum,dc.personUnitVendorGid,dc.produceProcessRouteCGid,dc.notes, "
				+ " pprc.opGid,pprc.gid pprcgid from MES_WM_DispatchingOrderC dc"
				+ " left join MES_WM_ProduceProcessRouteC pprc on dc.produceProcessRouteCGid=pprc.gid where disGid='"+parentGid+"'";
		return  this.queryForList(sql);
	}
	
	//根据订单id获取订单信息
	public Map getOrderInforByOrderId(String condition){
		String sql="SELECT po.billCode,po.billDate,poc.pk pocpk,poc.goodsUid,poc.startDate,poc.endDate,poc.number,poc.gid pocgid "+ 
				"from WM_ProduceOrder po LEFT JOIN WM_ProduceOrder_C poc on po.gid=poc.produceOrderUid where "+condition;
		return this.queryForMap(sql);
	}
	
	//查询报工最后一条记录	
	@SuppressWarnings("rawtypes")
	public Map findReport(String gid,String orgId,String sobId){
		String sql="";
		if(CommonUtil.isNullString(gid)){
			sql="SELECT TOP 1 pk,gid,dispatchingObj,memo,rptCode,batch FROM MES_WM_ReportOrder where sobGid = '" + sobId + "' AND orgGid = '" + orgId + "' ORDER BY	pk DESC ";
		}else{
			sql="SELECT pk,gid,dispatchingObj,memo,rptCode,batch FROM MES_WM_ReportOrder  WHERE	gid='"+gid+"' AND sobGid = '" + sobId + "' AND orgGid = '" + orgId + "' ORDER By pk DESC ";
		}
		
		return  this.queryForMap(sql);
	}
	
	
	//根据父id 查询报工单子表
	public List<Map> findReports(String parentGid){
		String sql="SELECT rc.deptGid, rc.gid,rc.reportOkNum,rc.reportNotOkNum,rc.personUnitVendorGid,rc.produceProcessRouteCGid,rc.notes,rc.discGid, "
				+ " pprc.opGid,pprc.gid pprcgid,pprc.barcode,"
				+ " doc.disNum,doc.reportOkNum docReportOkNum,doc.reportNotOkNum docReportNotOkNum, "
				+ " d.stationGid,d.mouldGid,d.workingTime from MES_WM_ReportOrderC rc"
				+ " left join MES_WM_ProduceProcessRouteC pprc on rc.produceProcessRouteCGid=pprc.gid "
				+ " left join MES_WM_DispatchingOrderC doc on rc.discGid=doc.gid "
				+ " left join mes_wm_DispatchingOrder d on doc.disGid=d.gid where rptGid='"+parentGid+"'";
		return  this.queryForList(sql);
	}
	
	/**
	 * @category 提交放行
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public int letPass(String condition){
		String sql=" update MES_WM_ProduceProcessRouteC set isPass=1 where "+condition;
		return this.update(sql);
	}
	
	public PageBean dispatchingStartList(int pageIndex,int pageSize, String condition) {
		Map match = new HashMap();
		match.put("billCode", "billCode");
		match.put("billDate", "billDate");
		match.put("goodsUid", "goodsId");
		match.put("produceNumber", "produceNumber");
		match.put("startDate", "startDate");
		match.put("endDate", "endDate");
		match.put("objectType", "objectType");
		match.put("opGid", "opGid");
		
//		match.put("stationGid", "stationGid");
//		match.put("mouldGid", "mouldGid");
//		match.put("workingTime", "workingTime");
		
		String sql = "select "+CommonUtil.colsFromBean(MesWmDispatchingorderc.class,"doc")+",po.billCode,po.billDate,poc.goodsUid,poc.number produceNumber,prc.opGid,poc.startDate,poc.endDate,prc.dispatchingType objectType  "
					+ " FROM MES_WM_DispatchingOrderC doc "
					+ " LEFT JOIN MES_WM_ProduceProcessRouteC prc ON doc.produceProcessRouteCGid = prc.gid"
					+ " LEFT JOIN MES_WM_ProduceProcessRoute pr ON prc.produceRouteGid = pr.gid"
					+ " LEFT JOIN WM_ProduceOrder_C poc on poc.gid=pr.produceCuid"
					+ " LEFT JOIN WM_ProduceOrder po on po.gid=pr.produceUid where 1=1 ";
		if(CommonUtil.notNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize,MesWmDispatchingorderc.class,match, "pk desc");
	}
	/**
	 * @category 报工列表
	 * 2016年7月25日 下午5:35:12 
	 * @author zhuxiaochen
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean dispatchingReportList(int pageIndex, int pageSize,String condition) {
		Map match = new HashMap();
		match.put("billCode", "billCode");
		match.put("billDate", "billDate");
		match.put("goodsUid", "goodsId");
		match.put("produceNumber", "produceNumber");
		match.put("startDate", "startDate");
		match.put("endDate", "endDate");
		match.put("objectType", "objectType");
		match.put("opGid", "opGid");
		
//		match.put("stationGid", "stationGid");
//		match.put("mouldGid", "mouldGid");
//		match.put("workingTime", "workingTime");
		
		String sql = "select "+CommonUtil.colsFromBean(MesWmReportorderc.class,"doc")+",po.billCode,po.billDate,poc.goodsUid,poc.number produceNumber,prc.opGid,poc.startDate,poc.endDate,prc.dispatchingType objectType  "
					+ " FROM MES_WM_ReportOrderC doc"
					+ " LEFT JOIN MES_WM_ProduceProcessRouteC prc ON doc.produceProcessRouteCGid = prc.gid"
					+ " LEFT JOIN MES_WM_ProduceProcessRoute pr ON prc.produceRouteGid = pr.gid"
					+ " LEFT JOIN WM_ProduceOrder_C poc on poc.gid=pr.produceCuid"
					+ " LEFT JOIN WM_ProduceOrder po on po.gid=pr.produceUid where 1=1 ";
		if(CommonUtil.notNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize,MesWmReportorderc.class,match, "pk desc");
	}
}
