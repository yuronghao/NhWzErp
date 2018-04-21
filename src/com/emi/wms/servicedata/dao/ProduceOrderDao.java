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
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmProduceorder;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.WmPurchaserequisition;

public class ProduceOrderDao extends BaseDao implements Serializable {

	private static final long serialVersionUID = -7963726065930903876L;

	/**
	 * @category 获得工序领料任务
	 *2016 2016年4月8日下午5:51:54
	 *List<MesWmProduceProcessroutecGoods>
	 *宋银海
	 */
	public List<MesWmProduceProcessroutecGoods> getMesWmProduceProcessroutecGoods(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutecGoods.class, "ppg")
				+ " from MES_WM_ProduceProcessRouteCGoods ppg WITH (NoLock) "
				+ " left join MES_WM_ProduceProcessRouteC pprc WITH (NoLock) on ppg.produceRouteCGid=pprc.gid "
				+ " left join MES_WM_ProduceProcessRoute ppr WITH (NoLock) on pprc.produceRouteGid=ppr.gid where "+condition;
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
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroute.class)+" from MES_WM_ProduceProcessRoute WITH (NoLock) where "+condition;
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
				+ " po.billCode from MES_WM_ProduceProcessRoute ppr WITH (NoLock) "
				+ " left join WM_ProduceOrder_C poc WITH (NoLock) on ppr.produceCuid=poc.gid "
				+ " left join WM_ProduceOrder po WITH (NoLock) on po.gid=poc.produceOrderUid where "+condition;
		return this.queryForMap(sql);
	}
	
	/**
	 * @category 获得生产订单工艺路线子表
	 *2016 2016年4月12日下午2:23:33
	 *MesWmProduceProcessroutec
	 *宋银海
	 */
	public MesWmProduceProcessroutec getMesWmProduceProcessroutec(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC WITH (NoLock) where "+condition;
		return (MesWmProduceProcessroutec) this.emiQuery(sql, MesWmProduceProcessroutec.class);
	}
	
	/**
	 * @category 返回派工单子表条数
	 * @param conditon
	 * @return
	 * 宋银海
	 */
	public int getdisordercCount(String condition){
		String sql="select count(1) from MES_WM_DispatchingOrderC where "+condition;
		return this.queryForInt(sql);
	}
	
	public Map getDisedNum(String condition){
		String sql="select sum(disNum) as disnum from MES_WM_DispatchingOrderC where "+condition;
		return this.queryForMap(sql);
	}
	
	
	public Map getReportNum(String condition){
		String sql="select sum(reportOkNum) as reportOkNum,sum(reportNotOkNum) as reportNotOkNum from MES_WM_ReportOrderC where "+condition;
		return this.queryForMap(sql);
	}
	
	
	public List<Map> getReportNumByDisGid(String condition){
		String sql="select sum(reportOkNum) as reportOkNum,sum(reportNotOkNum) as reportNotOkNum,discGid,produceProcessRouteCGid "+
					"from MES_WM_ReportOrderC where "+condition+" GROUP BY discGid,produceProcessRouteCGid ";
		return this.queryForList(sql);
	}
	
	
	/**
	 * @category 返回报工单子表条数
	 * @param conditon
	 * @return
	 * 宋银海
	 */
	public int getreportcCount(String condition){
		String sql="select count(1) from MES_WM_ReportOrderC where "+condition;
		return this.queryForInt(sql);
	}
	
	
	/**
	 * @category 批量修改派工单
	 * @param mesWmDispatchingordercs
	 * @return
	 * 宋银海
	 */
	public int[] updateDisc(final List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		String sql="update MES_WM_DispatchingOrderC set disNum=?,notes=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmDispatchingorderc md=mesWmDispatchingordercs.get(index);
				ps.setBigDecimal(1, md.getDisNum());
				ps.setString(2, md.getNotes());
				ps.setString(3, md.getGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmDispatchingordercs.size();
			}
		});
	}
	
	
	/**
	 * @category 批量修改派工单
	 * @param mesWmDispatchingordercs
	 * @return
	 * 宋银海
	 */
	public int[] updAdjustmentPeopleGroup(final List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		String sql="update MES_WM_DispatchingOrderC set personUnitVendorGid=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmDispatchingorderc md=mesWmDispatchingordercs.get(index);
				ps.setString(1, md.getPersonUnitVendorGid());
				ps.setString(2, md.getGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmDispatchingordercs.size();
			}
		});
	}
	
	
	/**
	 * @category 批量修改报工单
	 * @param mesWmDispatchingordercs
	 * @return
	 * 宋银海
	 */
	public int[] updateReportc(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="update MES_WM_ReportOrderC set reportOkNum=?,reportNotOkNum=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmReportorderc md=mesWmReportordercs.get(index);
				ps.setBigDecimal(1, md.getReportOkNum());
				ps.setBigDecimal(2, md.getReportNotOkNum());
				ps.setString(3, md.getGid());
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
	}
	
	
	/**
	 * @category 批量修改报工单
	 * @param mesWmDispatchingordercs
	 * @return
	 * 宋银海
	 */
	public int[] updateReportcPeopleGroup(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="update MES_WM_ReportOrderC set personUnitVendorGid=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmReportorderc md=mesWmReportordercs.get(index);
				ps.setString(1, md.getPersonUnitVendorGid());
				ps.setString(2, md.getGid());
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
	}
	
	
	public int updatePprc(String produceProcessRouteCGid,Map map){
		String sql="update MES_WM_ProduceProcessRouteC set dispatchedNum='"+(CommonUtil.isNullObject(map.get("disnum"))?0:map.get("disnum").toString())+"' where gid='"+produceProcessRouteCGid+"'";
		return this.update(sql);
	}
	
	
	public int updatePprcReport(String produceProcessRouteCGid,Map map){
		String sql="update MES_WM_ProduceProcessRouteC set "
				+ " reportOkNum='"+(CommonUtil.isNullObject(map.get("reportOkNum"))?0:map.get("reportOkNum").toString())+"',"
				+ " reportNotOkNum='"+(CommonUtil.isNullObject(map.get("reportNotOkNum"))?0:map.get("reportNotOkNum").toString())+"'"
				+ "where gid='"+produceProcessRouteCGid+"'";
		return this.update(sql);
	}
	
	public int[] updatePprcReportDele(final List<Map> mapDis){
		String sql="update MES_WM_DispatchingOrderC set  reportOkNum=isnull(reportOkNum,0)-?, reportNotOkNum=isnull(reportNotOkNum,0)-?  where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Map m=mapDis.get(index);
				ps.setBigDecimal(1, CommonUtil.object2BigDecimal(m.get("reportOkNum")));
				ps.setBigDecimal(2, CommonUtil.object2BigDecimal(m.get("reportNotOkNum")));
				ps.setString(3, m.get("discGid").toString());
			}
			
			@Override
			public int getBatchSize() {
				return mapDis.size();
			}
		});
	}
	
	public int[] updatePprcReportUpt(final List<Map> mapDis){
		String sql="update MES_WM_DispatchingOrderC set  reportOkNum=?, reportNotOkNum=?  where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Map m=mapDis.get(index);
				ps.setBigDecimal(1, CommonUtil.object2BigDecimal(m.get("reportOkNum")));
				ps.setBigDecimal(2, CommonUtil.object2BigDecimal(m.get("reportNotOkNum")));
				ps.setString(3, m.get("discGid").toString());
			}
			
			@Override
			public int getBatchSize() {
				return mapDis.size();
			}
		});
	}
	
	
	public List<Map> getReportList(String condition){
		String sql="select discGid,reportOkNum,reportNotOkNum from MES_WM_ReportOrderC where "+condition;
		return this.queryForList(sql);
	}
	
	
	//删除派工单主表
	public int deleteDis(String disOrderGid){
		String sql="delete from MES_WM_DispatchingOrder where gid='"+disOrderGid+"'";
		return this.update(sql);
	}
	
	//删除报工单主表
	public int deleteRpt(String rptGid){
		String sql="delete from MES_WM_ReportOrder where gid='"+rptGid+"'";
		return this.update(sql);
	}
	
	//删除派工单子表
	public int deleteDisc(String disOrderGid){
		String sql="delete from MES_WM_DispatchingOrderC where disGid='"+disOrderGid+"'";
		return this.update(sql);
	}
	
	public int deleteRptc(String rptGid){
		String sql="delete from MES_WM_ReportOrderC where rptGid='"+rptGid+"'";
		return this.update(sql);
	}
	
	public int[] deleteDisc(final List<MesWmDispatchingorderc> mesWmDispatchingordercs){
		String sql="delete from MES_WM_DispatchingOrderC where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmDispatchingorderc md=mesWmDispatchingordercs.get(index);
				ps.setString(1, md.getGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmDispatchingordercs.size();
			}
		});
	}
	
	//删除报工单子表
	public int[] deleteRptc(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="delete from MES_WM_ReportOrderC where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				MesWmReportorderc md=mesWmReportordercs.get(index);
				ps.setString(1, md.getGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
	}
	
	
	/**
	 * @category 根据订单工艺路线子表获得订单信息
	 *2016 2016年5月10日下午2:18:30
	 *Map
	 *宋银海
	 */
	public Map getOrderInforByProduceProcessroutec(String condition){
		String sql="select poc.autoidForSynchro,poc.gid pocgid,pprc.barcode from MES_WM_ProduceProcessRouteC pprc "
				+ " left join MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid"
				+ " left join wm_produceOrder_c poc on ppr.produceCuid=poc.gid where "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 获得派工单对应人员
	 *2016 2016年5月3日下午3:41:16
	 *List<ProcessTaskPersonRsp>
	 *宋银海
	 */
	public List<ProcessTaskPersonRsp> getTaskPersonGroup(String condition){
		String sql="select g.gid,g.code,g.barcode,g.groupname,prcd.routeCGid from MES_WM_ProduceProcessRouteCDispatching prcd WITH (NoLock) left join AA_Group g on prcd.objGid=g.gid where "+condition;
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
		String sql="select p.gid,p.perCode,p.barcode,p.perName,prcd.routeCGid from MES_WM_ProduceProcessRouteCDispatching prcd WITH (NoLock) left join aa_person p on prcd.objGid=p.gid where "+condition;
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
		String sql="select e.gid,e.equipmentCode,e.barcode,e.equipmentName from MES_WM_ProduceProcessRouteCEquipment prce WITH (NoLock) left join equipment e on prce.equipmentGid=e.gid where "+condition;
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
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC WITH (NoLock) where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessroutec.class);
	}
	
	
	/**
	 * @category 获得生产订单工艺路线上到工序转入
	 *2016 2016年4月13日下午3:36:20
	 *List<MesWmProduceProcessroutec>
	 *宋银海
	 */
	public List<MesWmProduceProcessRouteCPre> getMesWmProduceProcessRouteCPreList(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessRouteCPre.class)+" from MES_WM_ProduceProcessRouteCPre WITH (NoLock) where "+condition;
		return this.emiQueryList(sql, MesWmProduceProcessRouteCPre.class);
	}
	
	
	/**
	 * @category 获得生产订单信息
	 *2016 2016年4月11日上午8:40:35
	 *WmProduceorder
	 *宋银海
	 */
	public WmProduceorder getProduceOrder(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmProduceorder.class)+" from WM_ProduceOrder where "+condition;
		return (WmProduceorder) this.emiQuery(sql, WmProduceorder.class);
	}
	
	
	/**
	 * @category 获得生产订单产品信息
	 *2016 2016年4月11日上午8:40:35
	 *WmProduceorder
	 *宋银海
	 */
	public WmProduceorderC getWmProduceorderC(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmProduceorderC.class)+" from WM_ProduceOrder_C WITH (NoLock) where "+condition;
		return (WmProduceorderC) this.emiQuery(sql, WmProduceorderC.class);
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
	
	
	public Map getSumDisNum(String routeCgid){
		String sql="SELECT sum(disNum) sumDisNum from MES_WM_DispatchingOrderC where produceProcessRouteCgid='"+routeCgid+"'";
		return this.queryForMap(sql);
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
	 *@category 回填生产订单工艺路线子表已报工数量(自动释放工序可开工数量)
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int updProduceProcessRoutecReprotReleaseStart( String routecGid,String dispatchedNum ){
		String sql="update MES_WM_ProduceProcessRouteC set dispatchedNum='"+dispatchedNum+"' where gid='"+routecGid+"'";
		return this.update(sql);
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
	 *@category 回填生产订单工艺路线子表已抽检检数量（工序质检）
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updProduceProcessRoutecCheckRandom(final List<QMCheckCbill> qbcs){
		String sql="update MES_WM_ProduceProcessRouteC set randomCheckOkNum=isnull(randomCheckOkNum,0)+?,randomCheckNotOkNum=isnull(randomCheckNotOkNum,0)+? where gid=?";
		
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
	 *@category 回填派工单子表已报工数量(自动释放工序可开工数量)
	 *2016 2016年4月15日下午5:46:42
	 *boolean
	 *宋银海
	 */
	public int[] updDisOrderReprotReleaseStart(final List<MesWmReportorderc> mesWmReportordercs){
		String sql="update MES_WM_DispatchingOrderC set disNum=isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0) where gid=? ";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				MesWmReportorderc mw=mesWmReportordercs.get(index);
				ps.setString(1, mw.getDiscGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return mesWmReportordercs.size();
			}
		});
		
	}
	
	
	public int[] updDisOrderReprotReleaseStartListMap(final List<Map> mapDis){
		String sql="update MES_WM_DispatchingOrderC set disNum=isnull(reportOkNum,0)+isnull(reportNotOkNum,0)+isnull(reportProblemNum,0) where gid=? ";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				Map mw=mapDis.get(index);
				ps.setString(1, mw.get("discGid").toString());
				
			}
			
			@Override
			public int getBatchSize() {
				return mapDis.size();
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
	 * 回填报工单已抽检检数量
	 *2016 2016年4月21日下午5:41:27
	 *int[]
	 *宋银海
	 */
	public int[] updReprotOrderCheckRandom(final List<QMCheckCbill> qbcs){
		String sql="update MES_WM_ReportOrderC set randomCheckOkNum=isnull(randomCheckOkNum,0)+?,randomCheckNotOkNum=isnull(randomCheckNotOkNum,0)+? where gid=?";
		
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
				+ " dc.personUnitVendorGid,dc.produceProcessRouteCGid,dc.notes detailNotes,"
				+ " d.stationGid,d.dispatchingObj,d.notes mainNotes ,d.workingTime from MES_WM_DispatchingOrderC dc WITH (NoLock) "
				+ " left join MES_WM_DispatchingOrder d WITH (NoLock) on dc.disGid=d.gid where "+condition +" order by dc.startTime desc";
		return this.queryForList(sql);
	}
	
	
	public List<Map> getReports(String condition){
		String sql=" select rc.deptGid,rc.gid,rc.reportOkNum,rc.reportNotOkNum,rc.reportProblemNum,rc.checkOkNum,rc.checkNotOkNum,rc.randomCheckOkNum,rc.randomCheckNotOkNum, rc.rptGid,rc.discGid,rc.produceProcessRouteCGid,rc.personUnitVendorGid,rc.productInNum,"
				+ " ro.dispatchingObj,ro.stationGid,ro.gid rogid,ro.rptcode,ro.billDate,ro.batch "
				+ " from MES_WM_ReportOrderC rc left join MES_WM_ReportOrder ro on rc.rptGid=ro.gid where "+condition;
		return this.queryForList(sql);
	}
	
	public PageBean getproduceOrderlist(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("goodsUid", "WmProduceorder.goodsUid");
		match.put("number", "WmProduceorder.number");
		match.put("completedNum", "WmProduceorder.completedNum");
		match.put("startDate", "WmProduceorder.startDate");
		match.put("endDate", "WmProduceorder.endDate");
		match.put("turnoutNum", "WmProduceorder.turnoutNum");
		match.put("note", "WmProduceorder.note");
		String sql ="select "+CommonUtil.colsFromBean(WmProduceorder.class,"WmProduceorder")+",WMProduceOrderC.goodsUid goodsUid,WMProduceOrderC.number number,WMProduceOrderC.completedNum completedNum,WMProduceOrderC.startDate startDate,WMProduceOrderC.endDate endDate,WMProduceOrderC.turnoutNum turnoutNum,WMProduceOrderC.notes note from WM_ProduceOrder WmProduceorder "
				+ "left join WM_ProduceOrder_C WMProduceOrderC on WMProduceOrderC.produceOrderUid = WmProduceorder.gid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, WmProduceorder.class,match," billdate desc");
	}
	
	public PageBean getProduceOrderListWithOldBillCode(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("goodsUid", "WmProduceorder.goodsUid");
		match.put("number", "WmProduceorder.number");
		match.put("completedNum", "WmProduceorder.completedNum");
		match.put("startDate", "WmProduceorder.startDate");
		match.put("endDate", "WmProduceorder.endDate");
		match.put("turnoutNum", "WmProduceorder.turnoutNum");
		match.put("note", "WmProduceorder.note");
		match.put("sourcebillCade", "sourcebillCade");
		String sql ="select "+CommonUtil.colsFromBean(WmProduceorder.class,"WmProduceorder")+",WMProduceOrderC.goodsUid goodsUid,WMProduceOrderC.number number,WMProduceOrderC.completedNum completedNum,WMProduceOrderC.startDate startDate,WMProduceOrderC.endDate endDate,WMProduceOrderC.turnoutNum turnoutNum,WMProduceOrderC.notes note,po.billCode sourcebillCade from WM_ProduceOrder WmProduceorder "
				+ "left join WM_ProduceOrder_C WMProduceOrderC on WMProduceOrderC.produceOrderUid = WmProduceorder.gid "
				+" LEFT JOIN MES_WM_ProduceProcessRoute ppra ON WMProduceOrderC.gid = ppra.produceCuid"
				+" LEFT JOIN WM_ProduceOrder_C poc on ppra.changeSrcOrderCid=poc.gid"
				+" LEFT JOIN WM_ProduceOrder po on poc.produceOrderUid=po.gid where WmProduceorder.changeOrder='1'";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, WmProduceorder.class,match," billdate desc");
	}
	
	public Map findproduceOrder(String purchaseArrivalgid, String changeOrder,String orgId,String sobId) {
		String sql="";
		String ext = "";
		if("1".equals(changeOrder)){
			ext += " and WMProduceOrder.changeOrder=1 ";
		}else{
			ext += " and (WMProduceOrder.changeOrder is null or WMProduceOrder.changeOrder=0) ";
		}
		if(CommonUtil.isNullString(purchaseArrivalgid)){
			sql="SELECT TOP 1 WMProduceOrder.managerGid producemanagerGid,WMProduceOrder.deptGid producedeptGid,WMProduceOrder.notes producenotes,WMProduceOrder.gid WMProduceOrdergid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_ProduceOrder WMProduceOrder LEFT JOIN YM_User ymuser ON ymuser.gid = WMProduceOrder.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = WMProduceOrder.auditPersonUid where 1=1 and WMProduceOrder.sobGid='"+sobId+"' and WMProduceOrder.orgGid='"+orgId+"' "+ext+" ORDER BY WMProduceOrder.pk DESC";
		}else{
			sql="SELECT WMProduceOrder.managerGid producemanagerGid,WMProduceOrder.deptGid producedeptGid,WMProduceOrder.notes producenotes,WMProduceOrder.gid WMProduceOrdergid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_ProduceOrder WMProduceOrder LEFT JOIN YM_User ymuser ON ymuser.gid = WMProduceOrder.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = WMProduceOrder.auditPersonUid where WMProduceOrder.gid = '"+purchaseArrivalgid+"' and WMProduceOrder.sobGid='"+sobId+"' and WMProduceOrder.orgGid='"+orgId+"' "+ext+" order by WMProduceOrder.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public List getproduceOrderlist(String purchaseArrivalpurchaseArrivalUid) {
		String sql = "select produceorderc.gid,produceorderc.produceOrderUid,abs(produceorderc.number) number,abs(produceorderc.completedNum) completedNum,abs(produceorderc.assistNumber) assistNumber,abs(produceorderc.completedAssistNumber) completedAssistNumber,"
				+ "produceorderc.startDate,produceorderc.endDate,produceorderc.goodsUid,produceorderc.cfree1,produceorderc.cfree2,produceorderc.notes,produceorderc.turnoutNum,lineNum ,productType,state from WM_ProduceOrder_C produceorderc  where produceorderc.produceOrderUid='"+purchaseArrivalpurchaseArrivalUid+"'  order by lineNum ";
		return this.queryForList(sql);
	}
	
	
	public List<Map> getSaleSendCbyCheck(String condition) {
		String sql="select cc.gid ccgid, abs(cc.okNum) okNum,abs(cc.notOkNum) notOkNum,abs(cc.assistOkNum) assistOkNum,"
				+ " abs(cc.assistNotOkNum) assistNotOkNum, abs(cc.putInNum) putInNum,abs(cc.putInAssistNum) putInAssistNum,cc.batch,cc.produceProcessRouteCGid,cc.goodsUid, "
				+ " c.checkCode,c.checkDate,c.gid,"
				+ " poc.cfree1,poc.cfree2 from QM_CheckCbill cc WITH (NoLock) "
				+ " left join MES_WM_ProduceProcessRouteC rc WITH (NoLock) on cc.produceProcessRouteCGid=rc.gid "
				+ " left join MES_WM_ProduceProcessRoute r WITH (NoLock) on rc.produceRouteGid=r.gid "
				+ " left join WM_ProduceOrder_C  poc WITH (NoLock) on r.produceCuid=poc.gid "
				+ " left join QM_CheckBill c WITH (NoLock) on cc.checkGid=c.gid where "+condition;
		return this.queryForList(sql);
	}

	public boolean addproduceOrder(WmProduceorder WmProduceorder) {
		return this.emiInsert(WmProduceorder);
	}
	
	public boolean addproduceOrderc(List list) {
		return this.emiInsert(list);
	}
	
	public boolean updateproduceOrder(WmProduceorder WmProduceorder) {
		return this.emiUpdate(WmProduceorder);
	}
	
	public boolean updateproduceOrderc(List list) {
		return this.emiUpdate(list);
	}
	
	public boolean deleteproduceOrder(String produceorderGid) {
		try {
			String sql = "delete from WM_ProduceOrder where gid='"+produceorderGid+"'";
			String sql1 ="delete from WM_ProduceOrder_C where produceOrderUid='"+produceorderGid+"'"; 
			this.update(sql);
			this.update(sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteproduceOrderc(String produceorderGid) {
		try {
			String sql1 ="delete from WM_ProduceOrder_C where gid in("+produceorderGid+")"; 
			this.update(sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int getproduceprocess(String produceorderGid){
		String sql = "select count(*) from MES_WM_ProduceProcessRoute where produceUid='"+produceorderGid+"'";
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
		String sql="select pprc.gid,opGid,number,dispatchedNum,reportOkNum,reportNotOkNum,checkOkNum,checkNotOkNum,"
				+ " ppr.produceCuid from MES_WM_ProduceProcessRouteC pprc "
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
	
	public Mould getMouldByBarcode(String barcode){
		String sql="SELECT m.* FROM mould m WITH (NoLock) WHERE m.barcode='"+barcode+"'";
		return (Mould)this.emiQuery(sql, Mould.class);
	}
	
	public PageBean getMouldByLike(String condition,int pageIndex,int pageSize){
		String sql="SELECT m.* FROM mould m WITH (NoLock) WHERE m.cdefine1 like '%"+condition+"%'";
		return this.emiQueryList(sql, pageIndex, pageSize, Mould.class, null);
	}
	
	//变更时更新派工数量
	public void uptDisNum(String num,String changeNum,String routeGid){
		String sql="update MES_WM_ProduceProcessRouteC set number=round(number*'"+changeNum+"'/'"+num+"',2) where produceRouteGid='"+routeGid+"'";
		this.update(sql);
		
	}
	
	//变更时更新物料数量
	public void uptGoodsNum(String num,String changeNum,String routeGid){
		String sql="update MES_WM_ProduceProcessRouteCGoods set number=round(number*'"+changeNum+"'/'"+num+"',2) where produceRouteCGid in (select gid from MES_WM_ProduceProcessRouteC where produceRouteGid='"+routeGid+"')";
		this.update(sql);
	}
	
	//获得订单产品子状态
	public Map getProduceCState(String routeCgid){
		String sql="SELECT poc.state,pprc.barcode from MES_WM_ProduceProcessRouteC pprc WITH (NoLock) "+
					" LEFT JOIN MES_WM_ProduceProcessRoute ppr WITH (NoLock) on pprc.produceRouteGid=ppr.gid "+
					" LEFT JOIN WM_ProduceOrder_C poc WITH (NoLock) on ppr.produceCuid=poc.gid where pprc.gid='"+routeCgid+"'";	
		return this.queryForMap(sql);
	}
	
	
	
}
