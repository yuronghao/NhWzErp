package com.emi.wms.servicedata.dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.emi.android.bean.ProcessTaskPersonRsp;
import com.emi.android.bean.ProcessTaskStationRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmProduceProcessRouteCPre;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmReportorder;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmPurchaserequisition;

public class SaleOrderDao extends BaseDao implements Serializable {

	private static final long serialVersionUID = -7963726065930903876L;

	/**
	 * @category 获得工序领料任务
	 *2016 2016年4月8日下午5:51:54
	 *List<MesWmProduceProcessroutecGoods>
	 *宋银海
	 */
	public List<MesWmProduceProcessroutecGoods> getMesWmProduceProcessroutecGoods(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutecGoods.class, "ppg")
				+ " from MES_WM_ProduceProcessRouteCGoods ppg "
				+ " left join MES_WM_ProduceProcessRouteC pprc on ppg.produceRouteCGid=pprc.gid "
				+ " left join MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessroutecGoods.class);
	}
	
	
	/**
	 * @category 获得生产订单工艺路线主表列表
	 *2016 2016年4月11日上午8:50:21
	 *List<MesWmProduceProcessroute>
	 *宋银海
	 */
	public List<MesWmProduceProcessroute> getMesWmProduceProcessrouteList(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessroute.class);
	}
	
	/**
	 * @category 获得生产订单工艺路线主表
	 *2016 2016年4月12日下午2:54:03
	 *MesWmProduceProcessroute
	 *宋银海
	 */
	public MesWmProduceProcessroute getMesWmProduceProcessroute(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute where "+condition;
		return (MesWmProduceProcessroute) this.emiQuery(sql, MesWmProduceProcessroute.class);
	}
	
	/**
	 * @category 获得订单工艺路线产品信息
	 *2016 2016年4月12日下午2:59:00
	 *Map
	 *宋银海
	 */
	public Map getProduceProcessrouteInforMap(String condition){
		String sql="select ppr.gid,ppr.produceUid,ppr.produceCuid,poc.goodsUid,poc.startDate,poc.endDate,poc.number,"
				+ " po.billCode from MES_WM_ProduceProcessRoute ppr "
				+ " left join WM_SaleOrder_C poc on ppr.produceCuid=poc.gid "
				+ " left join WM_SaleOrder po on po.gid=poc.saleOrderUid where "+condition;
		return this.queryForMap(sql);
	}
	
	/**
	 * @category 获得生产订单工艺路线子表
	 *2016 2016年4月12日下午2:23:33
	 *MesWmProduceProcessroutec
	 *宋银海
	 */
	public MesWmProduceProcessroutec getMesWmProduceProcessroutec(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC where "+condition;
		return (MesWmProduceProcessroutec) this.emiQuery(sql, MesWmProduceProcessroutec.class);
	}
	
	
	/**
	 * @category 根据订单工艺路线子表获得订单信息
	 *2016 2016年5月10日下午2:18:30
	 *Map
	 *宋银海
	 */
	public Map getOrderInforByProduceProcessroutec(String condition){
		String sql="select poc.autoidForSynchro from MES_WM_ProduceProcessRouteC pprc "
				+ " left join MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid"
				+ " left join wm_saleOrder_c poc on ppr.produceCuid=poc.gid where "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 获得派工单对应人员
	 *2016 2016年5月3日下午3:41:16
	 *List<ProcessTaskPersonRsp>
	 *宋银海
	 */
	public List<ProcessTaskPersonRsp> getTaskPersonGroup(String condition){
		String sql="select g.gid,g.code,g.barcode,g.groupname,prcd.routeCGid from MES_WM_ProduceProcessRouteCDispatching prcd left join AA_Group g on prcd.objGid=g.gid where "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				ProcessTaskPersonRsp ptp=new ProcessTaskPersonRsp();
				ptp.setPersonUnitVendorGid(rs.getString("gid"));
				ptp.setPersonUnitVendorCode(rs.getString("code"));
				ptp.setPersonUnitVendorBarcode(rs.getString("barcode"));
				ptp.setPersonUnitVendorName(rs.getString("groupname"));
				ptp.setProduceProcessRoutecGid(rs.getString("routeCGid"));
				return ptp;
			}
			
		});
	}
	
	public List<ProcessTaskPersonRsp> getTaskPerson(String condition){
		String sql="select p.gid,p.perCode,p.barcode,p.perName,prcd.routeCGid from MES_WM_ProduceProcessRouteCDispatching prcd left join aa_person p on prcd.objGid=p.gid where "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				ProcessTaskPersonRsp ptp=new ProcessTaskPersonRsp();
				ptp.setPersonUnitVendorGid(rs.getString("gid"));
				ptp.setPersonUnitVendorCode(rs.getString("perCode"));
				ptp.setPersonUnitVendorBarcode(rs.getString("barcode"));
				ptp.setPersonUnitVendorName(rs.getString("perName"));
				ptp.setProduceProcessRoutecGid(rs.getString("routeCGid"));
				return ptp;
			}
			
		});
	}
	
	
	/**
	 * @category 获得派工单对应设备
	 *2016 2016年5月3日下午3:41:16
	 *List<ProcessTaskPersonRsp>
	 *宋银海
	 */
	public List<ProcessTaskStationRsp> getTaskEquipment(String condition){
		String sql="select e.gid,e.equipmentCode,e.barcode,e.equipmentName from MES_WM_ProduceProcessRouteCEquipment prce left join equipment e on prce.equipmentGid=e.gid where "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				ProcessTaskStationRsp pts=new ProcessTaskStationRsp();
				pts.setStationGid(rs.getString("gid"));
				pts.setStationCode(rs.getString("equipmentCode"));
				pts.setStationBarcode(rs.getString("barcode"));
				pts.setStationName(rs.getString("equipmentName"));
				
				return pts;
			}
			
		});
	}
	
	
	/**
	 * @category 获得生产订单工艺路线子表列表
	 *2016 2016年4月13日下午3:36:20
	 *List<MesWmProduceProcessroutec>
	 *宋银海
	 */
	public List<MesWmProduceProcessroutec> getMesWmProduceProcessroutecList(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}
	
	
	/**
	 * @category 获得生产订单工艺路线上到工序转入
	 *2016 2016年4月13日下午3:36:20
	 *List<MesWmProduceProcessroutec>
	 *宋银海
	 */
	public List<MesWmProduceProcessRouteCPre> getMesWmProduceProcessRouteCPreList(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCPre.class)+" from MES_WM_ProduceProcessRouteCPre where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessRouteCPre.class);
	}
	
	
	/**
	 * @category 获得生产订单信息
	 *2016 2016年4月11日上午8:40:35
	 *WmSaleorder
	 *宋银海
	 */
	public WmSaleorder getSaleOrder(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmSaleorder.class)+" from WM_SaleOrder where "+condition;
		return (WmSaleorder) this.emiQuery(sql, WmSaleorder.class);
	}
	
	
	/**
	 * @category 获得生产订单产品信息
	 *2016 2016年4月11日上午8:40:35
	 *WmSaleorder
	 *宋银海
	 */
	public WmSaleorderC getWmSaleorderC(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmSaleorderC.class)+" from WM_SaleOrder_C where "+condition;
		return (WmSaleorderC) this.emiQuery(sql, WmSaleorderC.class);
	}
	
	
	
	
	
	/**
	 * @category 增加开工单主表
	 *2016 2016年4月15日下午4:28:59
	 *boolean
	 *宋银海
	 */
	public boolean addMesWmDispatchingorder(MesWmDispatchingorder mesWmDispatchingorder){
		return this.emiInsert(mesWmDispatchingorder);
	}
	
	
	/**
	 * @category 增加报工单主表
	 *2016 2016年4月20日下午5:22:24
	 *boolean
	 *宋银海
	 */
	public boolean addMesWmReportorder(MesWmReportorder mesWmReportorder){
		return this.emiInsert(mesWmReportorder);
	}
	
	
	/**
	 * @category 增加开工单子表
	 *2016 2016年4月15日下午4:29:28
	 *boolean
	 *宋银海
	 */
	public boolean addMesWmDispatchingorderc(List<MesWmDispatchingorderc> mesWmDispatchingorderc){
		return this.emiInsert(mesWmDispatchingorderc);
	}
	
	
	/**
	 * @category 增加报工单子表
	 *2016 2016年4月20日下午5:23:36
	 *boolean
	 *宋银海
	 */
	public boolean addMesWmReportorderc(List<MesWmReportorderc> mesWmReportorderc){
		return this.emiInsert(mesWmReportorderc);
	}
	
	
	/**
	 * @category 增加质检单主表
	 *2016 2016年4月20日下午5:22:24
	 *boolean
	 *宋银海
	 */
	public boolean addCheckBill(QMCheckBill qb){
		return this.emiInsert(qb);
	}
	
	
	/**
	 * @category 增加质检单子表
	 *2016 2016年4月15日下午4:29:28
	 *boolean
	 *宋银海
	 */
	public boolean addCheckBillc(List<QMCheckCbill> qbcs){
		return this.emiInsert(qbcs);
	}
	
	/**
	 * @category 增加质检单子表2
	 *2016 2016年4月15日下午4:29:28
	 *boolean
	 *宋银海
	 */
	public boolean addCheckBillc2(List<QMCheckCReasonBill> qbcs2){
		return this.emiInsert(qbcs2);
	}
	
	/**
	 * 回填生产订单工艺路线子表已派工数量
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updProduceProcessRoutecStart(final List<MesWmDispatchingorderc> mesWmDispatchingorderc){
		String sql="update MES_WM_ProduceProcessRouteC set dispatchedNum=isnull(dispatchedNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				MesWmDispatchingorderc mw=mesWmDispatchingorderc.get(index);
				
				ps.setBigDecimal(1, mw.getDisNum());
				ps.setString(2, mw.getProduceProcessRoutecGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmDispatchingorderc.size();
			}
		});
	}
	
	
	/**
	 *@category 回填生产订单工艺路线子表已报工数量
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updProduceProcessRoutecReprot(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="update MES_WM_ProduceProcessRouteC set reportOkNum=isnull(reportOkNum,0)+?,reportNotOkNum=isnull(reportNotOkNum,0)+?,reportProblemNum=isnull(reportProblemNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				MesWmReportorderc mw=mesWmReportordercs.get(index);
				
				ps.setBigDecimal(1, mw.getReportOkNum());
				ps.setBigDecimal(2, mw.getReportNotOkNum());
				ps.setBigDecimal(3, mw.getReportProblemNum());
				ps.setString(4, mw.getProduceProcessRouteCGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
	}
	
	
	/**
	 *@category 回填生产订单工艺路线子表已质检数量（工序质检）
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updProduceProcessRoutecCheck(final List<QMCheckCbill> qbcs){
		String sql="update MES_WM_ProduceProcessRouteC set checkOkNum=isnull(checkOkNum,0)+?,checkNotOkNum=isnull(checkNotOkNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				QMCheckCbill qc=qbcs.get(index);
				
				ps.setBigDecimal(1, qc.getOkNum());
				ps.setBigDecimal(2, qc.getNotOkNum());
				ps.setString(3, qc.getProduceProcessRouteCGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return qbcs.size();
			}
		});
	}
	
	
	/**
	 *@category 回填生产订单工艺路线子表已质检数量（产品质检）
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updProduceProcessRoutecProductCheck(final List<QMCheckCbill> qbcs){
		String sql="update MES_WM_ProduceProcessRouteC set productCheckOkNum=isnull(productCheckOkNum,0)+?,productCheckNotOkNum=isnull(productCheckNotOkNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				QMCheckCbill qc=qbcs.get(index);
				
				ps.setBigDecimal(1, qc.getOkNum());
				ps.setBigDecimal(2, qc.getNotOkNum());
				ps.setString(3, qc.getProduceProcessRouteCGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return qbcs.size();
			}
		});
	}
	
	
	
	/**
	 *@category 回填派工单子表已报工数量
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updDisOrderReprot(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="update MES_WM_DispatchingOrderC set reportOkNum=isnull(reportOkNum,0)+?,reportNotOkNum=isnull(reportNotOkNum,0)+?,reportProblemNum=isnull(reportProblemNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				MesWmReportorderc mw=mesWmReportordercs.get(index);
				
				ps.setBigDecimal(1, mw.getReportOkNum());
				ps.setBigDecimal(2, mw.getReportNotOkNum());
				ps.setBigDecimal(3, mw.getReportProblemNum());
				ps.setString(4, mw.getDiscGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
	}
	
	
	/**
	 * 回填报工单已质检数量
	 *2016 2016年4月21日下午5:41:27
	 *int[]
	 *宋银海
	 */
	public int[] updReprotOrderCheck(final List<QMCheckCbill> qbcs){
		String sql="update MES_WM_ReportOrderC set checkOkNum=isnull(checkOkNum,0)+?,checkNotOkNum=isnull(checkNotOkNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				QMCheckCbill qc=qbcs.get(index);
				
				ps.setBigDecimal(1, qc.getOkNum());
				ps.setBigDecimal(2, qc.getNotOkNum());
				ps.setString(3, qc.getRptcGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return qbcs.size();
			}
		});
	}
	
	
	/**
	 * @category 反填发货单已质检数量
	 *2016 2016年4月23日上午10:50:25
	 *int[]
	 *Ad
	 */
	public int[] updSaleSendCheck(final List<QMCheckCbill> qbcs){
		String sql="update WM_SaleSend_C set checkOkNumber=isnull(checkOkNumber,0)+?,checkNotOkNumber=isnull(checkNotOkNumber,0)+?,"
				+ " checkOkAssistNumber=isnull(checkOkAssistNumber,0)+?,checkNotOkAssistNumber=isnull(checkNotOkAssistNumber,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				QMCheckCbill qc=qbcs.get(index);
				
				ps.setBigDecimal(1, qc.getOkNum());
				ps.setBigDecimal(2, qc.getNotOkNum());
				ps.setBigDecimal(3, qc.getAssistOkNum());
				ps.setBigDecimal(4, qc.getAssistNotOkNum());
				ps.setString(5, qc.getSaleSendCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return qbcs.size();
			}
		});
	}
	
	/**
	 * 
	 * @category 反填到货单子表
	 * 2016年4月25日 上午10:24:15
	 * @author 杨峥铖
	 * @param qbcs
	 * @return
	 */
	public int[] updProcureSendCheck(final List<QMCheckCbill> qbcs){
		String sql="update WM_ProcureArrival_C set checkOkNumber=isnull(checkOkNumber,0)+?,checkNotOkNumber=isnull(checkNotOkNumber,0)+?,"
				+ " checkOkAssistNumber=isnull(checkOkAssistNumber,0)+?,checkNotOkAssistNumber=isnull(checkNotOkAssistNumber,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				QMCheckCbill qc=qbcs.get(index);
				
				ps.setBigDecimal(1, qc.getOkNum());
				ps.setBigDecimal(2, qc.getNotOkNum());
				ps.setBigDecimal(3, qc.getAssistOkNum());
				ps.setBigDecimal(4, qc.getAssistNotOkNum());
				ps.setString(5, qc.getProcureArrivalCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return qbcs.size();
			}
		});
	}
	/**
	 * @category 查询派工情况
	 *2016 2016年4月20日下午2:10:00
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getDispatchingorderc(String condition){
		String sql="select dc.gid,dc.disNum,dc.startTime,dc.reportOkNum,dc.reportNotOkNum,dc.reportProblemNum,dc.notes, "
				+ " dc.personUnitVendorGid,dc.produceProcessRouteCGid,d.stationGid,d.dispatchingObj from MES_WM_DispatchingOrderC dc "
				+ " left join MES_WM_DispatchingOrder d on dc.disGid=d.gid where "+condition;
		return this.queryForList(sql);
	}
	
	
	public List<Map> getReports(String condition){
		String sql=" select rc.gid,rc.reportOkNum,rc.reportNotOkNum,rc.reportProblemNum,rc.checkOkNum,rc.checkNotOkNum,rc.rptGid,rc.discGid,rc.produceProcessRouteCGid,rc.personUnitVendorGid,"
				+ " ro.dispatchingObj,ro.stationGid "
				+ " from MES_WM_ReportOrderC rc left join MES_WM_ReportOrder ro on rc.rptGid=ro.gid where "+condition;
		return this.queryForList(sql);
	}
	
	public PageBean getsaleOrderlist(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("goodsUid", "WmSaleorder.goodsUid");
		match.put("number", "WmSaleorder.number");
		match.put("assistNumber", "WmSaleorder.assistNumber");
		match.put("note", "WmSaleorder.note");
		String sql ="select "+CommonUtil.colsFromBean(WmSaleorder.class,"WmSaleorder")+",WMSaleOrderC.goodsUid goodsUid,WMSaleOrderC.number number,WMSaleOrderC.assistNumber assistNumber,WMSaleOrderC.notes note from WM_SaleOrder WmSaleorder "
				+ "left join WM_SaleOrder_C WMSaleOrderC on WMSaleOrderC.saleOrderUid = WmSaleorder.gid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, WmSaleorder.class,match,"");
	}
	
	public Map findsaleOrder(String purchaseArrivalgid,String orgId,String sobId) {
		String sql="";
		String ext = "";
		if(CommonUtil.isNullString(purchaseArrivalgid)){
			sql="SELECT TOP 1 WMSaleOrder.deptGid saledeptGid,WMSaleOrder.notes salenotes,WMSaleOrder.gid WMsaleOrdergid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_SaleOrder WMSaleOrder LEFT JOIN YM_User ymuser ON ymuser.gid = WMSaleOrder.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = WMSaleOrder.auditPersonUid where 1=1 and WMSaleOrder.sobGid='"+sobId+"' and WMSaleOrder.orgGid='"+orgId+"' "+ext+" ORDER BY WMSaleOrder.pk DESC";
		}else{
			sql="SELECT WMSaleOrder.deptGid saledeptGid,WMSaleOrder.notes salenotes,WMSaleOrder.gid WMsaleOrdergid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_SaleOrder WMSaleOrder LEFT JOIN YM_User ymuser ON ymuser.gid = WMSaleOrder.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = WMSaleOrder.auditPersonUid where WMSaleOrder.gid = '"+purchaseArrivalgid+"' and WMSaleOrder.sobGid='"+sobId+"' and WMSaleOrder.orgGid='"+orgId+"' "+ext+" order by WMSaleOrder.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public List getsaleOrderlist(String purchaseArrivalpurchaseArrivalUid) {
		String sql = "select saleorderc.gid,saleorderc.saleOrderUid,abs(saleorderc.number) number,abs(saleorderc.assistNumber) assistNumber"
				+ ",saleorderc.goodsUid,saleorderc.localTaxPrice localTaxPrice,saleorderc.localTaxMoney localTaxMoney,saleorderc.notes from WM_SaleOrder_C saleorderc where saleorderc.saleOrderUid='"+purchaseArrivalpurchaseArrivalUid+"' ";
		return this.queryForList(sql);
	}
	
	
	public List<Map> getSaleSendCbyCheck(String condition) {
		String sql="select cc.gid ccgid, abs(cc.okNum) okNum,abs(cc.notOkNum) notOkNum,abs(cc.assistOkNum) assistOkNum,"
				+ " abs(cc.assistNotOkNum) assistNotOkNum, abs(cc.putInNum) putInNum,abs(cc.putInAssistNum) putInAssistNum,cc.batch,cc.produceProcessRouteCGid,cc.goodsUid, "
				+ " c.checkCode,c.checkDate,c.gid,"
				+ " poc.cfree1,poc.cfree2 from QM_CheckCbill cc "
				+ " left join MES_WM_ProduceProcessRouteC rc on cc.produceProcessRouteCGid=rc.gid "
				+ " left join MES_WM_ProduceProcessRoute r on rc.produceRouteGid=r.gid "
				+ " left join WM_SaleOrder_C poc on r.produceCuid=poc.gid "
				+ " left join QM_CheckBill c on cc.checkGid=c.gid where "+condition;
		return this.queryForList(sql);
	}

	public boolean addsaleOrder(WmSaleorder WmSaleorder) {
		return this.emiInsert(WmSaleorder);
	}
	
	public boolean addsaleOrderc(List list) {
		return this.emiInsert(list);
	}
	
	public boolean updatesaleOrder(WmSaleorder WmSaleorder) {
		return this.emiUpdate(WmSaleorder);
	}
	
	public boolean updatesaleOrderc(List list) {
		return this.emiUpdate(list);
	}
	
	public boolean deletesaleOrder(String saleorderGid) {
		try {
			String sql = "delete from WM_SaleOrder where gid='"+saleorderGid+"'";
			String sql1 ="delete from WM_SaleOrder_C where saleOrderUid='"+saleorderGid+"'"; 
			this.update(sql);
			this.update(sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deletesaleOrderc(String saleorderGid) {
		try {
			String sql1 ="delete from WM_SaleOrder_C where gid in("+saleorderGid+")"; 
			this.update(sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean auditsaleOrder(String saleorderGid) {
		try {
			String sql = "update WM_SaleOrder set billstate='1' where gid='"+saleorderGid+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean disauditsaleOrder(String saleorderGid) {
		try {
			String sql = "update WM_SaleOrder set billstate='0' where gid='"+saleorderGid+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean stopsaleOrder(String saleorderGid) {
		try {
			String sql = "update WM_SaleOrder set billstate='2' where gid='"+saleorderGid+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int getproduceprocess(String saleorderGid){
		String sql = "select count(*) from MES_WM_ProduceProcessRoute where produceUid='"+saleorderGid+"'";
		return this.queryForInt(sql);
	}
	
	public int getsalesnumber(String saleorderGid){
		String sql = "select count(*) from WM_SaleOrder_C where saleOrderUid='"+saleorderGid+"' and (sendNumber is not null or putoutNumber is not null)";
		return this.queryForInt(sql);
	}
	/**
	 * @category 返回可视化工艺路线 开工报工情况
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStepSituation(String condition){
		//工序信息，订单数量，已派工数量，报工合格，报工不合格，质检合格，质检不合格
		String sql="select pprc.gid,opGid,number,dispatchedNum,reportOkNum,reportNotOkNum,checkOkNum,checkNotOkNum from MES_WM_ProduceProcessRouteC pprc "
				+ " left join MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid where "+condition+"  ";
		return this.queryForList(sql);
	}
	
	/**
	 * @category 返回可视化工艺路线 领料情况
	 *2016 2016年6月6日下午3:58:39
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProductStepMeterialOut(String condition){
		//工序信息，物品信息，应领数量，已领数量
		String sql="select pprc.opGid,pprcg.goodsGid,pprcg.number,pprcg.receivedNum from MES_WM_ProduceProcessRouteCGoods pprcg "
				+ " left join MES_WM_ProduceProcessRouteC pprc on pprcg.produceRouteCGid=pprc.gid "
				+ " left join MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid where "+condition +" ";
		return this.queryForList(sql);
	}
	
}
