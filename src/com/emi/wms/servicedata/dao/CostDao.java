package com.emi.wms.servicedata.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import com.emi.android.bean.ProcessTaskPersonRsp;
import com.emi.android.bean.ProcessTaskStationRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.MESWMCostFeeMain;
import com.emi.wms.bean.MESWMCostGoodsBalance;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MESWMCostRdRecordsOut;
import com.emi.wms.bean.MESWMCostReportInInfor;
import com.emi.wms.bean.MESWmCostDirectMaterialWork2;
import com.emi.wms.bean.MESWmCostDirectMaterialWorkMain2;
import com.emi.wms.bean.MESWmCostGoodsBalanceMain;
import com.emi.wms.bean.MESWmCostMaterialWork;
import com.emi.wms.bean.MESWmCostMaterialWorkMain;
import com.emi.wms.bean.MESWmCostProcessInWork;
import com.emi.wms.bean.MESWmCostProcessInWorkMain;
import com.emi.wms.bean.MESWmCostRdRecordsOutMain;
import com.emi.wms.bean.MESWmCostReportInInforMain;
import com.emi.wms.bean.MesWmCostFee;
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

public class CostDao extends BaseDao implements Serializable {

	private static final long serialVersionUID = -7963726065930903876L;

	//获得会计期间
	public Map getPeriod(int year,int month,String caccid){
		String sql="select * from ufsystem.dbo.UA_Period where cacc_id='"+caccid+"' and iYear='"+year+"' and iId='"+month+"'";
		return this.queryForMap(sql);
	}
	
	public int deleteCostReportInMain(int year,int month){
		String sql="delete from MES_WM_CostReportInInforMain where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	public int deleteRdRecordsOutMain(int year,int month){
		String sql="delete from MES_WM_CostRdRecordsOutMain where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	//删除之前的取数
	public int deleteCostReportInInfor(int year,int month){
		String sql="delete from MES_WM_CostReportInInfor where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	//根据条件取报工单，包括末级
	public int addReport(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostReportInInfor(gid,produceOrderCode,goodsGid,rptGid,discGid,reportOkNum,reportNotOkNum,reportProblemNum,iyear,imonth,ctime,cfree1,billdate,deptGid,standardPrice,mainGid) "+
					"SELECT NEWID(),po.billCode,poc.goodsUid,rc.rptGid,rc.discGid,rc.reportOkNum,rc.reportNotOkNum,rc.reportProblemNum,'"+year+"','"+month+"', GETDATE(),poc.cfree1,rc.endtime,rc.deptGid,pprc.realPrice,'"+mainGid+"' from MES_WM_ReportOrderC rc "+
					"LEFT JOIN MES_WM_ProduceProcessRouteC pprc on rc.produceProcessRouteCGid=pprc.gid "+
					"LEFT JOIN MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid "+
					"LEFT JOIN WM_ProduceOrder_C poc on ppr.produceCuid=poc.gid "+
					"LEFT JOIN WM_ProduceOrder po ON poc.produceOrderUid = po.gid "+
					"where endTime>='"+dbegin+"' and '"+dend+"' >=endTime "
					+ " and (rc.reportOkNum>0 or rc.reportNotOkNum>0 or rc.reportProblemNum>0 )";
		return this.update(sql);
	}
	
	public int addReportNoIn(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostReportInInfor(gid,produceOrderCode,goodsGid,rptGid,discGid,reportOkNum,reportNotOkNum,reportProblemNum,iyear,imonth,ctime,cfree1,billdate,deptGid,standardPrice,mainGid) "+
					"SELECT NEWID(),po.billCode,poc.goodsUid,rc.rptGid,rc.discGid,rc.reportOkNum-isnull(rc.productInNum,0),rc.reportNotOkNum,rc.reportProblemNum,'"+year+"','"+month+"', GETDATE(),poc.cfree1,rc.endtime,rc.deptGid,pprc.realPrice,'"+mainGid+"' from MES_WM_ReportOrderC rc "+
					"LEFT JOIN MES_WM_ProduceProcessRouteC pprc on rc.produceProcessRouteCGid=pprc.gid "+
					"LEFT JOIN MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid "+
					"LEFT JOIN WM_ProduceOrder_C poc on ppr.produceCuid=poc.gid "+
					"LEFT JOIN WM_ProduceOrder po ON poc.produceOrderUid = po.gid "+
					"where  isnull(pprc.nextGid,'')='' and   endTime>='"+dbegin+"' and '"+dend+"' >=endTime "
					+ " and (rc.reportOkNum-isnull(rc.productInNum,0)>0) ";
		return this.update(sql);
	}
	
	//根据条件入库单
	public int addRdRecordIn(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostReportInInfor(gid,produceOrderCode,cwhCode,goodsCode,produceInNum,cfree1,iyear,imonth,ctime,billdate,mainGid,deptCode ,autoidForSynchro) "+
					"SELECT NEWID(),r.cMPoCode,r.cWhCode,rs.cInvCode,rs.iQuantity,rs.cFree1,'"+year+"','"+month+"', GETDATE(),r.ddate ,'"+mainGid+"',r.cDepCode ,rs.AutoID   from "+Config.BUSINESSDATABASE+"RdRecord r LEFT JOIN "+Config.BUSINESSDATABASE+"RdRecords rs on r.ID=rs.ID "+
					"where r.cBusType='成品入库' and r.dDate>='"+dbegin+"'  and '"+dend+"'>=r.dDate";
		return this.update(sql);
	}
	
	//更改相关字段属性
	public int uptCostReportInInfor(int year,int month){
		String sql="UPDATE MES_WM_CostReportInInfor SET goodsGid=gs.gid,cwhGid=wh.gid,deptGid=d.gid "+
					" FROM MES_WM_CostReportInInfor mwcr "+
					" LEFT JOIN AA_Goods gs on mwcr.goodsCode=gs.goodsCode "+
					" LEFT JOIN AA_WareHouse wh on mwcr.cwhCode=wh.whCode "
					+ " left join AA_Department d on mwcr.deptCode=d.depcode "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and (mwcr.goodsGid is NULL or (mwcr.cwhCode is not null  and cwhGid is NULL) or mwcr.deptGid is null)";
		return this.update(sql);
	}
	
	
	public int uptCostReportInInfor2(int year,int month){
		String sql="UPDATE MES_WM_CostReportInInfor SET goodsCode=gs.goodscode,deptCode=d.depCode "+
					" FROM MES_WM_CostReportInInfor mwcr "+
					" LEFT JOIN AA_Goods gs on mwcr.goodsGid=gs.gid "+
					" LEFT JOIN AA_Department d on mwcr.deptGid=d.gid "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and (mwcr.goodsCode is NULL or mwcr.deptCode is null)";
		return this.update(sql);
	}
	
	
	public int uptCostReportInInfor3(int year,int month){
		String sql="update MES_WM_CostReportInInfor set produceRoutecGid=pprc.gid, preGid=pprc.preGid, nextGid=pprc.nextGid,opname=ps.opname "+
					" from MES_WM_CostReportInInfor i "+
					" LEFT JOIN MES_WM_DispatchingOrderC dc on i.discGid=dc.gid "+
					" LEFT JOIN MES_WM_ProduceProcessRouteC pprc on dc.produceProcessRouteCGid=pprc.gid "+
					" left join MES_WM_StandardProcess ps on pprc.opGid=ps.gid "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and i.discGid is not NULL and i.produceRoutecGid is NULL";
		return this.update(sql);
	}
	
	
	public int uptCostReportInInfor4(String dbegin,String dend){
		String sql="UPDATE MES_WM_CostReportInInfor SET islastpre=1 "
				+ " where produceRoutecGid in ( "+
					" SELECT pprc.preGid from MES_WM_ReportOrderC rc "+
					" LEFT JOIN MES_WM_ProduceProcessRouteC pprc on rc.produceProcessRouteCGid=pprc.gid "+
					" LEFT JOIN MES_WM_ProduceProcessRoute ppr on pprc.produceRouteGid=ppr.gid "+
					" LEFT JOIN WM_ProduceOrder_C poc on ppr.produceCuid=poc.gid "+
					" LEFT JOIN WM_ProduceOrder po ON poc.produceOrderUid = po.gid "+
					" where  isnull(pprc.nextGid,'')='' and  endTime>='"+dbegin+"' and '"+dend+"' >=endTime ) ";
					
		return this.update(sql);
	}
	
	
	
	//删除材料出库单取数
	public int deleteCostRdRecordsOut(int year,int month){
		String sql="delete from MES_WM_CostRdRecordsOut where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	//根据条件取材料出库单
	public int addRdRecordOut(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostRdRecordsOut(gid,produceOrderCode,cwhCode,goodsCode,inum,cfree1,iyear,imonth,ctime  ,iunitCost,iprice,imPoIds,cbaccounter,billdate,cdefine30,costSourceName,  autoidForSynchro, cdepCode,   maingid ) "+
					"SELECT NEWID(),r.cMPoCode,r.cWhCode,rs.cInvCode,rs.iQuantity,rs.cFree1,'"+year+"','"+month+"', GETDATE(), rs.iUnitCost,rs.iPrice,rs.iMPoIds,rs.cbaccounter,r.dDate,rs.cdefine30,'直接材料1',rs.AutoID, r.cDepCode, '"+mainGid+"'  from "+Config.BUSINESSDATABASE+"RdRecord r LEFT JOIN "+Config.BUSINESSDATABASE+"RdRecords rs on r.ID=rs.ID "+
					"where r.cBusType='领料' and r.dDate>='"+dbegin+"'  and '"+dend+"'>=r.dDate and r.cMPoCode is not null ";
		return this.update(sql);
	}
	
	public int addRdRecordOut2(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostRdRecordsOut(gid,produceOrderCode,cwhCode,goodsCode,inum,cfree1,iyear,imonth,ctime  ,iunitCost,iprice,imPoIds,cbaccounter,billdate,cdefine30,costSourceName,productCode,productCfree1,productNum ,cdepCode,maingid) "+
					"SELECT NEWID(),r.cMPoCode,r.cWhCode,rs.cInvCode,rs.iQuantity,rs.cFree1,'"+year+"','"+month+"', GETDATE(), rs.iUnitCost,rs.iPrice,rs.iMPoIds,rs.cbaccounter,r.dDate,rs.cdefine30,'直接材料2',rs.cDefine25,rs.cDefine22,rs.cDefine35 ,r.cDepCode, '"+mainGid+"'   from "+Config.BUSINESSDATABASE+"RdRecord r LEFT JOIN "+Config.BUSINESSDATABASE+"RdRecords rs on r.ID=rs.ID "+
					"where r.cBusType='领料' and r.dDate>='"+dbegin+"'  and '"+dend+"'>=r.dDate and cRdCode = '20102' ";
		return this.update(sql);
	}
	
	
	public int addRdRecordOut3(int year,int month,String dbegin,String dend,String mainGid){
		String sql="INSERT into MES_WM_CostRdRecordsOut(gid,produceOrderCode,cwhCode,goodsCode,inum,cfree1,iyear,imonth,ctime  ,iunitCost,iprice,imPoIds,cbaccounter,billdate,cdefine30,costSourceName,productCode,productCfree1, cdepCode, maingid) "+
					"SELECT NEWID(),r.cMPoCode,r.cWhCode,rs.cInvCode,rs.iQuantity,rs.cFree1,'"+year+"','"+month+"', GETDATE(), rs.iUnitCost,rs.iPrice,rs.iMPoIds,rs.cbaccounter,r.dDate,rs.cdefine30,'直接材料2',rs.cDefine25,rs.cDefine22, r.cDepCode ,'"+mainGid+"'   from "+Config.BUSINESSDATABASE+"RdRecord r LEFT JOIN "+Config.BUSINESSDATABASE+"RdRecords rs on r.ID=rs.ID "+
					"where r.cBusType='领料' and r.dDate>='"+dbegin+"'  and '"+dend+"'>=r.dDate and cRdCode = '20103' ";
		return this.update(sql);
	}
	
	
	public int uptCostRdRecordsOutInfor(int year,int month){									  
		String sql="UPDATE MES_WM_CostRdRecordsOut SET routeCgid=mwprcg.produceRouteCGid "+
					" FROM MES_WM_CostRdRecordsOut mwcr "+
					" LEFT JOIN WM_MaterialOut_C mc on mwcr.cdefine30=mc.gid "+
					" LEFT JOIN MES_WM_ProduceProcessRouteCGoods mwprcg on mc.processRouteCGoodsUid=mwprcg.gid "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and mwcr.cdefine30 is not null and mwcr.routeCGid is NULL ";
		return this.update(sql);
	}
	
	
	public int uptCostRdRecordsOutInfor2(int year,int month){									  
		String sql="update MES_WM_CostRdRecordsOut set routeCgid=pprc.gid "+
					"from MES_WM_CostRdRecordsOut cro "+
					"LEFT JOIN WM_ProduceOrder_C2 poc2 on cro.impoids=poc2.autoidForSynchro "+
					"LEFT JOIN MES_WM_ProduceProcessRoute ppr on poc2.produceOrderCuid=ppr.produceCuid "+
					"LEFT JOIN MES_WM_ProduceProcessRouteC pprc on ppr.gid=pprc.produceRouteGid "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and preGid is NULL and produceOrderCode is not NULL and cdefine30 is null ";
		return this.update(sql);
	}
	
	public int uptCostRdRecordsOutInfor3(int year,int month){									  
		String sql="update MES_WM_CostRdRecordsOut set productCfree1=poc.cfree1 "+
					"from MES_WM_CostRdRecordsOut cro "+
					"LEFT JOIN WM_ProduceOrder_C2 poc2 on cro.impoids=poc2.autoidForSynchro "+ 
					"LEFT JOIN WM_ProduceOrder_C poc on poc2.produceOrderCuid=poc.gid "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'"+
					" and produceOrderCode is not NULL and cdefine30 is null and routeCgid is NULL ";
		return this.update(sql);
	}
	
	
	//更新物料gid,仓库gid
	public int uptCostRdRecordsOutInforCommon(int year,int month){									  
		String sql="UPDATE MES_WM_CostRdRecordsOut SET goodsGid=gs.gid,cwhGid=wh.gid "+
					" FROM MES_WM_CostRdRecordsOut mwcr "+
					" LEFT JOIN AA_Goods gs on mwcr.goodsCode=gs.goodsCode "+ 
					" LEFT JOIN AA_WareHouse wh on mwcr.cwhCode=wh.whCode "+
					" WHERE iyear='"+year+"' and imonth= '"+month+"'";
		return this.update(sql);
	}
	
	//删除工费取数
	public int deleteCostFee(int year,int month){
		String sql="delete from MES_WM_CostFee where year='"+year+"' and month='"+month+"'";
		return this.update(sql);
	}
	
	
	public List<Map> getSourceSetDeptListMap(){
		String sql="select cset.depgid from MES_WM_CostItemSourceSet cset "
				+ " left join MES_WM_CostItem citem on cset.costitemgid=citem.gid "
				+ "where citem.sourceGid='E457A0D5-79F0-4F40-9BD1-97611736C163' ";
		return this.queryForList(sql);
	}
	
	public List<Map> getCostFeeListMap(){
		String sql="select * from MES_WM_CostItemSourceSet where sourceMode='C5FAD155-A366-4E8D-AAEC-B23282A02BF1' and isDelete=0 ";
		return this.queryForList(sql);
	}
	
	public Map getAccass(String subjectCode,String deptCode,int iperoid){
		String sql="select * from "+Config.BUSINESSDATABASE+"GL_accass where ccode='"+subjectCode+"' and iperiod='"+iperoid+"'";
		
		if(!CommonUtil.isNullObject(deptCode)){
			sql="select * from "+Config.BUSINESSDATABASE+"GL_accass where ccode='"+subjectCode+"' and iperiod='"+iperoid+"' and cdept_id='"+deptCode+"'";
		}
		
		return this.queryForMap(sql);
	} 
	
	
	public List<MESWMCostReportInInfor> getCostReportInInforList(String year,String month){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostReportInInfor.class)+" from MES_WM_CostReportInInfor where iyear='"+year+"' and imonth='"+month+"'";
		return this.emiQueryList(sql, MESWMCostReportInInfor.class);
	}
	
	
	public List<MESWMCostRdRecordsOut> getProduceCgoods(String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostRdRecordsOut.class)+" from MES_WM_CostRdRecordsOut where "+condition;
		return this.emiQueryList(sql, MESWMCostRdRecordsOut.class);
	}
	
	
	
	
	public Map getDispatchingOrderC(String discGid){
		String sql="select * from MES_WM_DispatchingOrderC where gid='"+discGid+"'";
		return this.queryForMap(sql);
	}
	
	
	public List<Map> getProcessRouteC(String condition){
		String sql="select pr.goodsUid,prc.* from MES_WM_ProcessRouteC prc "
				+ " left join MES_WM_ProcessRoute pr on prc.routGid=pr.gid "
				+ " where "+condition;
		return this.queryForList(sql);
	}
	
	
	public List<MESWMCostItem> getMESWMCostItem(){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostItem.class)+" from MES_WM_CostItem ";
		return this.emiQueryList(sql, MESWMCostItem.class);
	}
	
	
	public Map getReportSum(String condition){
		String sql="select sum(reportOkNum)+sum(reportNotOkNum)+sum(reportProblemNum) allNum from MES_WM_CostReportInInfor"
				+ " where "+condition;
		return this.queryForMap(sql);
	}

	
	public MesWmCostFee getCostFee(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmCostFee.class)+" from MES_WM_CostFee cf"
				+ " left join MES_WM_CostItem ci on cf.costItemGid=ci.gid"
				+ " left join AA_PriorAttribute pa on ci.sourceGid=pa.gid where ";
		return (MesWmCostFee)this.emiQuery(sql, MesWmCostFee.class);
	}
	
	
	public int reSetCostFees(String year,String month){
		
		String sql="update MES_WM_CostReportInInfor set f001=0,f002=0,f003=0,f004=0,f005=0,f006=0,f007=0,f008=0,f009=0,f010=0,"
				+ " pricePref001=0,pricePref002=0,pricePref003=0,pricePref004=0,pricePref005=0,pricePref006=0,pricePref007=0,pricePref008=0,pricePref009=0,pricePref010=0,"
				+ " iunitCostf001=0,iunitCostf002=0,iunitCostf003=0,iunitCostf004=0,iunitCostf005=0,iunitCostf006=0,iunitCostf007=0,iunitCostf008=0,iunitCostf009=0,iunitCostf010=0, "
				+ " turnedOutNum=0, "
				+ " iCostf001=0,iCostf002=0,iCostf003=0,iCostf004=0,iCostf005=0,iCostf006=0,iCostf007=0,iCostf008=0,iCostf009=0,iCostf010=0,isdealPre=0,isdealCurrent=0,"
				+ " sumUnitPrice=0 "
				+ " where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	public int reSetRdrecordOut(String year,String month){
		
		String sql="update MES_WM_CostRdRecordsOut set "
				+ " averageOutUnitPrice=0 "
				+ " where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public List<MesWmCostFee> getCostFees(String condition){
		Map match = new HashMap();
		match.put("allotRateGid", "MesWmCostFee.allotRateGid");
		match.put("sourceGid", "MesWmCostFee.sourceGid");
		match.put("deptGid", "MesWmCostFee.deptGid");
		
		String sql="select "+CommonUtil.colsFromBean(MesWmCostFee.class,"cf")+",ci.allotRateGid,ci.sourceGid,d.gid deptGid from MES_WM_CostFee cf"
				+ " left join MES_WM_CostItem ci on cf.costItemGid=ci.gid "
				+ " left join AA_Department d on cf.deptCode =d.depCode "
				+ " where "+condition;
		return this.emiQueryList(sql, MesWmCostFee.class, match);
	}
	
	//更新低阶码2   先更新第1级
	public int updateSecondLowOrderCode(int year,int month){
		String sql="update MES_WM_CostReportInInfor set secondLowOrderCode=1 where isnull(preGid,'') not in "+
					" (SELECT  DISTINCT produceRoutecGid from MES_WM_CostReportInInfor where produceRoutecGid is not NULL and iyear='"+year+"' and imonth='"+month+"' ) and discGid is NOT NULL "+
					" and iyear='"+year+"' and imonth='"+month+"' ";
		
		return this.update(sql);
	}
	
	
	public int updateSecondLowOrderCode2(int year,int month,int docount){
		String sql="update MES_WM_CostReportInInfor set secondLowOrderCode='"+docount+"'"+
					" where isnull(preGid,'') in  "+
					" (SELECT  DISTINCT produceRoutecGid from MES_WM_CostReportInInfor where produceRoutecGid is not NULL and secondLowOrderCode='"+(docount-1)+"' and iyear='"+year+"' and imonth='"+month+"') "+
					" and iyear='"+year+"' and imonth='"+month+"' ";
		
		return this.update(sql);
	}
	
	public int updateSecondLowOrderCode3(int year,int month){
		String sql="update MES_WM_CostReportInInfor set secondLowOrderCode=50 "+
					" where goodsGid+isnull(cfree1,'')  IN (SELECT goodsGid+isnull(cfree1,'') from MES_WM_CostReportInInfor where discGid is not NULL)  and discGid is NULL "
					+ " and iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public int updateSecondLowOrderCode4(int year,int month){
		String sql="update MES_WM_CostReportInInfor set secondLowOrderCode=1 "+
					" where goodsGid+isnull(cfree1,'')  not IN (SELECT goodsGid+isnull(cfree1,'') from MES_WM_CostReportInInfor where discGid is not NULL) "
					+ " and iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	public String getOrderProcedure(final int year, final int month) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {

				String psql = "{call getOrderProcedure(?,?)}";

				CallableStatement call = arg0.prepareCall(psql);

				call.setInt(1,year);
				call.setInt(2, month);
				call.execute();

	            call.close();
				return "";
			}
		}).toString();
	}
	
	
	public Map getCountByMainLowOrderCode(int year,int month){
		String sql=" SELECT COUNT(*) itemCount from MES_WM_CostReportInInfor where mainLowOrderCode is null "+
					" and iyear='"+year+"' and imonth='"+month+"'";
		
		return this.queryForMap(sql);
	}
	
	

	
	public Map getSum(String year,String month,String deptCode,String deptGid,String costItemFlagGid){
		String sql="";
		if(deptCode!=null && !deptCode.equals("")){
			sql="select sum( "+
					" (isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,1) "+
					" ) sumvalue "+
					" from MES_WM_CostReportInInfor cri "+
					" LEFT JOIN "+
					" (SELECT allot.goodsGid,allot.cfree1,allot.depGid,allot.ratio from MES_WM_CostItemAllotRate allot "+
					" LEFT JOIN MES_WM_CostItem citem on allot.costItemGid=citem.gid where citem.allotRateGid='"+costItemFlagGid+"' and allot.depGid in "+deptGid+" ) crate "+
					" on cri.goodsGid=crate.goodsGid and isnull(case isnull(cri.opname,'') when '' THEN cri.cfree1 else cri.opname end,'')=isnull(crate.cfree1,'') and isnull(cri.deptGid,'')=isnull(crate.depGid,'') "+
					" where cri.deptCode in "+deptCode+" and cri.iyear='"+year+"' and cri.imonth='"+month+"'  and cri.produceRoutecGid is not null ";
		}else{
			sql="select sum( "+
					" (isnull(cri.reportOkNum,0)+isnull(cri.produceInNum,0))*isnull(crate.ratio,1) "+
					" ) sumvalue "+
					" from MES_WM_CostReportInInfor cri "+
					" LEFT JOIN "+
					" (SELECT allot.goodsGid,allot.cfree1,allot.depGid,allot.ratio from MES_WM_CostItemAllotRate allot "+
					" LEFT JOIN MES_WM_CostItem citem on allot.costItemGid=citem.gid where citem.allotRateGid='"+costItemFlagGid+"') crate "+
					" on cri.goodsGid=crate.goodsGid and isnull(case isnull(cri.opname,'') when '' THEN cri.cfree1 else cri.opname end,'')=isnull(crate.cfree1,'') and isnull(cri.deptGid,'')=isnull(crate.depGid,'') "+
					" where cri.iyear='"+year+"' and cri.imonth='"+month+"' and cri.produceRoutecGid is not null ";
			
		}
		
		return this.queryForMap(sql);
	}
	
	public int updateFee(String year,String month,String deptCode,String deptGid,String costItemFlagGid,double totalCoefficient,String sqlcondition){
		
		String sql="";
		
		if(deptCode!=null && !deptCode.equals("")){
			sql="update MES_WM_CostReportInInfor set  "+sqlcondition+
					" from MES_WM_CostReportInInfor cri "+
					" LEFT JOIN "+
					" (SELECT allot.goodsGid,allot.cfree1,allot.depGid,allot.ratio from MES_WM_CostItemAllotRate allot "+
					" LEFT JOIN MES_WM_CostItem citem on allot.costItemGid=citem.gid where citem.allotRateGid='"+costItemFlagGid+"' and allot.depGid='"+deptGid+"' ) crate "+
					" on cri.goodsGid=crate.goodsGid and isnull(case isnull(cri.opname,'') when '' THEN cri.cfree1 else cri.opname end,'')=isnull(crate.cfree1,'') and isnull(cri.deptGid,'')=isnull(crate.depGid,'') "+
					" where cri.deptCode='"+deptCode+"' and cri.iyear='"+year+"' and cri.imonth='"+month+"' and cri.produceRoutecGid is not null ";
		}else{
			sql="update MES_WM_CostReportInInfor set  "+sqlcondition+
					" from MES_WM_CostReportInInfor cri "+
					" LEFT JOIN "+
					" (SELECT allot.goodsGid,allot.cfree1,allot.depGid,allot.ratio from MES_WM_CostItemAllotRate allot "+
					" LEFT JOIN MES_WM_CostItem citem on allot.costItemGid=citem.gid where citem.allotRateGid='"+costItemFlagGid+"' ) crate "+
					" on cri.goodsGid=crate.goodsGid and isnull(case isnull(cri.opname,'') when '' THEN cri.cfree1 else cri.opname end,'')=isnull(crate.cfree1,'') and isnull(cri.deptGid,'')=isnull(crate.depGid,'') "+
					" where  cri.iyear='"+year+"' and cri.imonth='"+month+"' and cri.produceRoutecGid is not null ";
		}
		
		return this.update(sql);
	}
	
	
	public PageBean dispatchingStartList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostReportInInfor.class)+" from MES_WM_CostReportInInfor where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWMCostReportInInfor.class, " goodsCode,mainLowOrderCode,secondLowOrderCode ");
	}
	
	
	
	public PageBean getCostGoodsBalanceList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostGoodsBalance.class)+" from MES_WM_CostGoodsBalance where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWMCostGoodsBalance.class, " goodsCode ");
	}
	
	
	public PageBean getCostProcessInWorkList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostProcessInWork.class)+" from MES_WM_CostProcessInWork where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostProcessInWork.class, " goodsCode ");
	}
	
	
	public PageBean getCostMaterialWorkList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostMaterialWork.class)+" from MES_WM_CostMaterialWork where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostMaterialWork.class, "  ");
	}
	
	
	
	public PageBean getCostDirectMaterialWorkList2(int pageIndex,int pageSize, String condition){
		Map match = new HashMap();
		match.put("depName", "MESWmCostDirectMaterialWork2.deptName");
		String sql="select "+CommonUtil.colsFromBean(MESWmCostDirectMaterialWork2.class,"w2")+",ad.depName from MES_WM_CostDirectMaterialWork2 w2"
				+ " left join AA_Department ad on w2.deptCode=ad.depCode where 1=1 "+condition;
		
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostDirectMaterialWork2.class,match, " worktype ");
	}
	
	
	public PageBean getRdRecordsOutList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostRdRecordsOut.class)+" from MES_WM_CostRdRecordsOut where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWMCostRdRecordsOut.class, "pk ");
	}
	
	
	public PageBean getCostFeeMainList(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostFeeMain.class)+" from MES_WM_CostFeeMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWMCostFeeMain.class, " iyear desc,imonth asc ");
	}
	
	public List<Map> dispatchingStartListNoPage(String condition){
		String sql="select produceOrderCode,billdate,r.goodsCode,g.goodsName,r.cfree1,g.goodsStandard,opname,"+
					" reportOkNum,reportNotOkNum,reportProblemNum,produceInNum,mainLowOrderCode,secondLowOrderCode,"+
					" f001,f002,f003,f004,f005,f006,f007,f008,f009,f010,"
					+ "pricePref001,pricePref002,pricePref003,pricePref004,pricePref005,pricePref006,pricePref007,pricePref008,pricePref009,pricePref010,"
					+ "iunitCostf001,iunitCostf002,iunitCostf003,iunitCostf004,iunitCostf005,iunitCostf006,iunitCostf007,iunitCostf008,iunitCostf009,iunitCostf010 "
					+ "  from MES_WM_CostReportInInfor r"+
					" LEFT JOIN AA_Goods g on r.goodsGid=g.gid  where 1=1 "+condition;
		return this.queryForList(sql);
	}
	
	public PageBean getCostRdRecordsOutMain(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostRdRecordsOutMain.class)+" from MES_WM_CostRdRecordsOutMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostRdRecordsOutMain.class, "iyear desc,imonth asc ");
	}
	
	public PageBean getCostReportInInforMain(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostReportInInforMain.class)+" from MES_WM_CostReportInInforMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostReportInInforMain.class, "iyear desc,imonth asc ");
	}
	
	
	public PageBean getCostGoodsBalanceMain(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostGoodsBalanceMain.class)+" from MES_WM_CostGoodsBalanceMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostGoodsBalanceMain.class, "iyear desc,imonth asc ");
	}
	
	
	public PageBean getCostProcessInWorkMain(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostProcessInWorkMain.class)+" from MES_WM_CostProcessInWorkMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostProcessInWorkMain.class, "iyear desc,imonth asc ");
	}
	
	public PageBean getCostMaterialWorkMain(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostMaterialWorkMain.class)+" from MES_WM_CostMaterialWorkMain where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostMaterialWorkMain.class, "iyear desc,imonth asc ");
	}
	
	
	public PageBean getCostDirectMaterialWorkMain2(int pageIndex,int pageSize, String condition){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostDirectMaterialWorkMain2.class)+" from MES_WM_CostDirectMaterialWorkMain2 where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, MESWmCostDirectMaterialWorkMain2.class, "iyear desc,imonth asc ");
	}
	
	
	public MESWmCostReportInInforMain getMESWmCostReportInInforMain(String mainGid){
		String sql="select "+CommonUtil.colsFromBean(MESWmCostReportInInforMain.class)+" from MES_WM_CostReportInInforMain where gid='"+mainGid+"'";
		return (MESWmCostReportInInforMain)this.emiQuery(sql, MESWmCostReportInInforMain.class);
	}
	
	
	public List<MesWmCostFee> getCostFeeList(String maingid){
		
		Map match = new HashMap();
		match.put("deptName", "MesWmCostFee.deptName");
		match.put("costItemName", "MesWmCostFee.costItemName");
		
		String sql="select "+CommonUtil.colsFromBean(MesWmCostFee.class,"f")+",d.depName deptName,ci.name costItemName from MES_WM_CostFee f "
				+ " left join MES_WM_CostItem ci on f.costItemGid=ci.gid"
				+ " left join AA_Department d on f.deptCode=d.depCode where f.maingid='"+maingid+"'";
		return this.emiQueryList(sql, MesWmCostFee.class, match);
	}
	
	
	public int deleteCostFeeMain(int year,int month){
		String sql="delete from MES_WM_CostFeeMain where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	public int deleteCostGoodsBalanceMain(int year,int month){
		String sql="delete from MES_WM_CostGoodsBalanceMain where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public List<MESWMCostReportInInfor> getMESWMCostReportInInforByLowOrderCode(int mainLowOrderCode,int secondLowOrderCode,String year,String month){
		String sql="select "+CommonUtil.colsFromBean(MESWMCostReportInInfor.class)+" from MES_WM_CostReportInInfor where mainLowOrderCode='"+mainLowOrderCode+"' and secondLowOrderCode='"+secondLowOrderCode+"' and iyear='"+year+"' and imonth='"+month+"'";
		return this.emiQueryList(sql, MESWMCostReportInInfor.class);
	}
	
	
	public List<MESWMCostReportInInfor> getMESWMCostReportInInforFirst(String maingid){
		String sql="select  top 1 "+CommonUtil.colsFromBean(MESWMCostReportInInfor.class)+" from MES_WM_CostReportInInfor where maingid='"+maingid+"'";
		return this.emiQueryList(sql, MESWMCostReportInInfor.class);
	}
	
	public String calculateCost(final int year, final int month,final int mainLowOrderCode) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {

				String psql = "{call calculateByLowOrderCode(?,?,?)}";

				CallableStatement call = arg0.prepareCall(psql);

				call.setInt(1,year);
				call.setInt(2, month);
				call.setInt(3, mainLowOrderCode);
				call.execute();

	            call.close();
				return "";
			}
		}).toString();
	}

	public String getMeterial2(final int year, final int month) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {

				String psql = "{call getMeterial2(?,?)}";

				CallableStatement call = arg0.prepareCall(psql);

				call.setInt(1,year);
				call.setInt(2, month);
				call.execute();

	            call.close();
				return "1";
			}
		}).toString();
	}
	
	public Map getMainLowOrderCode(String maingid){
		String sql="SELECT max(mainLowOrderCode) mainLowOrderCode from MES_WM_CostReportInInfor  where maingid='"+maingid+"'";
		return this.queryForMap(sql);
	}
	
	
	public int uptCostReportInInforMainState(String maingid,String state){
		String sql="update MES_WM_CostReportInInforMain set state='"+state+"' where gid='"+maingid+"'";
		return this.update(sql);
	}
	
	
	public int uptCostFee(String feeGid,String toUptFee){
		String sql="update MES_WM_CostFee set iprice="+toUptFee+" where gid='"+feeGid+"'";
		return this.update(sql);
	}
	
	
	public int deleteCostGoodsBalance(int preyear,int premonth){
		String sql="delete from MES_WM_CostGoodsBalance where iyear='"+preyear+"' and imonth='"+premonth+"'";
		return this.update(sql);
	}
	
	
	
	public int insertCostGoodsBalance(int preyear,int premonth,int yonyoumonth,String mainGid){
		String sql="insert into MES_WM_CostGoodsBalance(gid,goodsCode,cfree1,cwhCode,iyear,imonth,iunitCost,number,iprice,maingid) "
				+ " select NEWID(),cInvCode,cFree1,cWhCode,"+preyear+","+premonth+",isnull(iUnitPrice,0),isnull(iNum,0),isnull(iMoney,0),'"+mainGid+"' from "+Config.BUSINESSDATABASE+"IA_summary where imonth='"+yonyoumonth+"'";
		return this.update(sql);
	}
	
	public int upttCostGoodsBalance(int preyear,int premonth){
		String sql="update MES_WM_CostGoodsBalance set goodsGid=gs.gid,cwhGid=wh.gid "+
					"from MES_WM_CostGoodsBalance cgb LEFT JOIN AA_Goods gs on cgb.goodsCode=gs.goodsCode "+
					"LEFT JOIN AA_WareHouse wh on cgb.cwhCode=wh.whCode "+
					"where cgb.iyear="+preyear+" and cgb.imonth="+premonth;
		return this.update(sql);
	}
	

	public int deleteCostProcessInWork(int year,int month){
		String sql="delete from  MES_WM_CostProcessInWork "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public int deleteCostProcessInWorkMain(int year,int month){
		String sql="delete from  MES_WM_CostProcessInWorkMain "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public int deleteCostMaterialWork(int year,int month){
		String sql="delete from  MES_WM_CostMaterialWork "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public int deleteCostMaterialWorkMain(int year,int month){
		String sql="delete from  MES_WM_CostMaterialWorkMain "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	
	
	
	public int deleteCostDirectMaterialWork2(int year,int month){
		String sql="delete from  MES_WM_CostDirectMaterialWork2 "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	public int deleteCostDirectMaterialWorkMain2(int year,int month){
		String sql="delete from  MES_WM_CostDirectMaterialWorkMain2 "
				+ "  where iyear='"+year+"' and imonth='"+month+"'";
		return this.update(sql);
	}
	
	
	
	
	public int uptRdrecordsIn(int year,int month){
		String sql="update "+Config.BUSINESSDATABASE+"RdRecords rs set rs.iUnitCost=ci.sumUnitPrice,rs.iPrice=round(ci.sumUnitPrice*rs.iQuantity,2) "
				+ " from "+Config.BUSINESSDATABASE+"RdRecords rs "
				+ " left join MES_WM_CostReportInInfor ci on rs.AutoID=ci.autoidForSynchro"
				+ " where ci.iyear='"+year+"' and ci.imonth='"+month+"'";
		
		return this.update(sql);
	}
	
	
	public int uptRdrecordsOut(int year,int month){
		String sql="update "+Config.BUSINESSDATABASE+"RdRecords rs set rs.iUnitCost=co.averageOutUnitPrice,rs.iPrice=round(co.averageOutUnitPrice*rs.iQuantity,2) "
				+ " from "+Config.BUSINESSDATABASE+"RdRecords rs "
				+ " left join MES_WM_CostRdRecordsOut co on rs.AutoID=co.autoidForSynchro"
				+ " where co.iyear='"+year+"' and co.imonth='"+month+"' and co.costSourceName='直接材料1' and isnull(co.cbaccounter,'')='' ";
		
		return this.update(sql);
	}
	
	
	
}
