package com.emi.wms.servicedata.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.wms.bean.*;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;


public class WareHouseDao extends BaseDao {

	/**
	 * @category 增加材料出库单主表
	 *2016 2016年4月11日下午4:55:37
	 *boolean
	 *宋银海
	 */
	public boolean addMaterialOut(List<WmMaterialout> wmMaterialout){
		return this.emiInsert(wmMaterialout);
	}
	
	/**
	 * 
	 * @category 
	 * 2016年4月15日 下午3:09:54
	 * @author 杨峥铖
	 * @param wmMaterialout
	 * @return
	 */
	public boolean addprocureIn(List<WmPowarehouse> wmPowarehouse){
		return this.emiInsert(wmPowarehouse);
	}
	
	
	/**
	 * @category 增加材料出库单子表
	 *2016 2016年4月11日下午4:59:18
	 *boolean
	 *宋银海
	 */
	public boolean addMaterialoutCs(List<WmMaterialoutC> wmMaterialoutCs){
		return this.emiInsert(wmMaterialoutCs);
	}
	
	/**
	 * 
	 * @category
	 * 2016年4月15日 下午3:10:54
	 * @author 杨峥铖
	 * @param wmMaterialoutCs
	 * @return
	 */
	public boolean addprocureIns(List<WmPowarehouseC> wmPowarehouseCs){
		return this.emiInsert(wmPowarehouseCs);
	}
	
	
	/**
	 * @category 添加产品入库单主表
	 *2016 2016年5月12日上午9:11:49
	 *boolean
	 *宋银海
	 */
	public boolean addProductionWarehouse(List<WmProductionwarehouse> wmPowarehouses){
		return this.emiInsert(wmPowarehouses);
	}
	
	/**
	 * @category 添加产品入库单子表
	 *2016 2016年5月12日上午9:11:49
	 *boolean
	 *宋银海
	 */
	public boolean addProductionWarehousec(List<WmProductionwarehouseC> wmPowarehouseCs){
		return this.emiInsert(wmPowarehouseCs);
	}
	
	
	/**
	 * @category 查询货位现存量
	 *2016 2016年4月7日下午4:13:51
	 *List<WmAllocationstock>
	 *宋银海
	 */
	public List<WmAllocationstock> getAllocationStock(String condition, String conplus) {
		String sql="select "+CommonUtil.colsFromBean(WmAllocationstock.class, "")+" from WM_AllocationStock where 1=1 "+condition+conplus;
		return this.emiQueryList(sql, WmAllocationstock.class);
	}
	
	
	/**
	 * @category 查询货位现存量 带批次
	 *2016 2016年4月7日下午4:13:51
	 *List<WmAllocationstock>
	 *宋银海
	 */
	public List<WmAllocationstock> getAllocationStockBatch(String condition) {
//		Map match = new HashMap();
//		match.put("recordDate", "recordDate");
//		String sql="select "+CommonUtil.colsFromBean(WmAllocationstock.class, "wa")+",bt.recordDate from WM_AllocationStock wa "
//				+ " left join WM_Batch bt on wa.batch=bt.batch  where 1=1 "+condition+" order by bt.recordDate desc";
//		return this.emiQueryList(sql, WmAllocationstock.class, match);
		
		String sql="select "+CommonUtil.colsFromBean(WmAllocationstock.class, "wa")+" from WM_AllocationStock wa WITH (NoLock) "
				+ "   where 1=1 "+condition+" order by batch ";
		return this.emiQueryList(sql, WmAllocationstock.class);
	}
	
	
//	public List<WmAllocationstock> getAllocationStockBatchNoZero(String condition) {
//		
//		String sql="select "+CommonUtil.colsFromBean(WmAllocationstock.class, "wa")+" from WM_AllocationStock wa "
//				+ "   where 1=1 "+condition;
//		return this.emiQueryList(sql, WmAllocationstock.class);
//	}
	
	//根据仓库条码查询仓库
	public Map getWareHouseByBarcode(String barcode){
		String sql="select gid,whcode,whname from AA_WareHouse WITH (NoLock) where whcode='"+barcode+"'";
		return this.queryForMap(sql);
	}
	
	
	
	/**
	 * @category 增加货位现存量
	 *2016 2016年4月7日下午4:14:39
	 *boolean
	 *宋银海
	 */
	public boolean addAllocationStock(List<WmAllocationstock> as) {
		return this.emiInsert(as);
	}
	
	/**
	 * @category 修改状态
	 *2016 2016年5月18日上午9:26:26
	 *int
	 *宋银海
	 */
	public int updateCallState(String gid){
		String sql="update wm_call set billstate=2 where gid='"+gid+"'";
		return this.update(sql);
	}
	

	
	/**
	 * @category 增加批次量
	 *2016 2016年4月12日上午10:16:09
	 *boolean
	 *宋银海
	 */
	public boolean addWmBatch(List<WmBatch> wmBatch){
		return this.emiInsert(wmBatch);
	}
	
	/**
	 * @category 批量修改货位现存量
	 *2016 2016年4月7日下午4:18:15
	 *int[]
	 *宋银海
	 */
	public int[] batchUptAllocationStock(final List<WmAllocationstock> as) {
		
		String sql="update WM_AllocationStock set number=isnull(number,0)+?,assistNum=isnull(assistNum,0)+? where gid=?";
			//sql="update WM_AllocationStock set nNumber=isnull(nNumber,0)-?,assistNum=isnull(assistNum,0)-? where gid=?";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmAllocationstock a=as.get(index);
				
				ps.setString(1, a.getNumber().toPlainString());
				ps.setString(2, CommonUtil.isNullObject(a.getAssistnum())?"0":a.getAssistnum().toPlainString());
				ps.setString(3, a.getGid());
				
			}
			
			@Override
			public int getBatchSize() {
				return as.size();
			}
		});
		
	}
	
	
	/**
	 * @category 提交领料时修改订单工艺路线材料表
	 *2016 2016年4月12日上午9:19:12
	 *int[]
	 *宋银海
	 */
	public int[] backFillProcessRouteCGoods(final List<WmsGoods> goods){
		String sql="update MES_WM_ProduceProcessRouteCGoods set receivedNum=isnull(receivedNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setString(2, good.getProduceRouteCGoodsUid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	/**
	 * @category 提交领料时修改订单工艺路线子表已入库数量
	 *2016 2016年4月12日上午9:19:12
	 *int[]
	 *宋银海
	 */
	public int[] backFillProcessRouteC(final List<WmsGoods> goods){
		String sql="update MES_WM_ProduceProcessRouteC set productInNum=isnull(productInNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				WmsGoods good=goods.get(index);
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setString(2, good.getProduceRouteCUid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	/**
	 * 
	 * @category
	 * 2016年4月15日 下午3:24:07
	 * @author 杨峥铖
	 * @param goods
	 * @return
	 */
	public int[] backFillProcureArrivalC(final List<WmsGoods> goods){
		String sql="update WM_ProcureArrival_C set putinNumber=isnull(putinNumber,0)+?,putinAssistNumber=isnull(putinAssistNumber,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setBigDecimal(2, CommonUtil.isNullObject(good.getSubmitQuantity())?new BigDecimal(0):good.getSubmitQuantity());
				ps.setString(3, good.getProcureArrivalCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	//反填销售发货单子表已出库数量
	public int[] backFillSaleSendC(final List<WmsGoods> goods){
		String sql="update WM_SaleSend_C set putoutNum=isnull(putoutNum,0)+?,putoutAssistNum=isnull(putoutAssistNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setBigDecimal(2, good.getSubmitQuantity());
				ps.setString(3, good.getSaleSendCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	//反填质检单子表已出库数量
	public int[] backFillSaleCheckC(final List<WmsGoods> goods){
		String sql="update QM_CheckCbill set putoutNum=isnull(putoutNum,0)+?,putoutAssistNum=isnull(putoutAssistNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setBigDecimal(2, good.getSubmitQuantity());
				ps.setString(3, good.getCheckCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	//反填质检单子表已出库数量
	public int[] backFillProcureCheckC(final List<WmsGoods> goods){
		String sql="update QM_CheckCbill set putInNum=isnull(putInNum,0)+?,putInAssistNum=isnull(putInAssistNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setBigDecimal(2, good.getSubmitQuantity());
				ps.setString(3, good.getCheckCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
		
		
	//反填质检单子表成品已入库数量
	public int[] backFillProductCheckC(final List<WmsGoods> goods){
		String sql="update QM_CheckCbill set putInNum=isnull(putInNum,0)+?,putInAssistNum=isnull(putInAssistNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setBigDecimal(2, good.getSubmitQuantity());
				ps.setString(3, good.getCheckCuid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	//反填生产订单已入库数量
	public int[] updateProduceBillIn(final List<WmsGoods> goods,final String producecGid){
		String sql="update WM_ProduceOrder_C set completedNum=isnull(completedNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setString(2, producecGid);
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	
	public int[] backFillReportOrderC(final List<WmsGoods> goods){
		String sql="update MES_WM_ReportOrderC set productInNum=isnull(productInNum,0)+? where gid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				WmsGoods good=goods.get(index);
				
				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setString(2, good.getReportOrderCUid());
				
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	public List getwarehouseList() {
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class,"aawarehouse")+" from AA_WareHouse aawarehouse WITH (NoLock) where 1=1 order by whcode ";
		return this.emiQueryList(sql, AaWarehouse.class);
	}
	
	/**
	 * @category 根据物品获得物品所在的所有仓库
	 *2016 2016年9月9日下午2:14:09
	 *void
	 *宋银海
	 */
	public List<Map> getwarehouseListByGoodsCode(String goodsUid){
		String sql="select DISTINCT goodsAllocationUid from WM_AllocationStock WITH (NoLock) where goodsUid='"+goodsUid+"' and goodsAllocationUid is not null and goodsAllocationUid <> '' and number>0 ";
		return this.queryForList(sql);
	}
	
	/**
	 * @category 根据物品,仓库获得物品所在的所有货位
	 *2016 2016年9月9日下午2:14:09
	 *void
	 *宋银海
	 */
	public PageBean getwarehouseListByGoodsCode(int pageIndex,int pageSize,String condition){
		String sql="select pk,goodsAllocationUid,goodsAllocationCode from WM_AllocationStock WITH (NoLock) where "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}
	
	public List getGoodsLocationList(String condition) {
		String sql="select "+CommonUtil.colsFromBean(AaGoodsallocation.class,"aagoodslocation")+" from AA_GoodsAllocation aagoodslocation where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, AaGoodsallocation.class);
	}
	
	
	/**
	 * @category 查询货位分页
	 *2016 2016年6月20日下午5:14:20
	 *PageBean
	 *宋银海
	 */
	public PageBean getGoodsLocationPage(int pageIndex,int pageSize,String condition){
		String sql="select pk,gid,code,name,whuid,allocationBarCode from AA_GoodsAllocation WITH (NoLock) where isDel = 0 and "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}
	
	
	
	/**
	 * @category 出入库类别
	 *2016 2016年4月22日下午2:08:01
	 *List<YmRdStyle>
	 *宋银海
	 */
	public List<YmRdStyle> getRdstyleEntity(String condition) {
		String sql="select "+CommonUtil.colsFromBean(YmRdStyle.class)+" from YM_RdStyle where 1=1 "+condition;
		return this.emiQueryList(sql, YmRdStyle.class);
	}

	public List getWareHouse(String findsql,String findtype) {
		List outlist=new ArrayList();
		if(findtype.equals("W")){
			outlist=this.emiQueryList(findsql,AaWarehouse.class);
		}else if (findtype.equals("G")){
			outlist=this.emiQueryList(findsql,AaGoods.class);
		}else{
			outlist=this.emiQueryList(findsql,AaGoodsallocation.class);
		}
		return outlist;
	}
    
	public List<WmAllocationstock> getWareHouseAllocation(String barcode, String wid, String allocationcode) {
		String sql="select "+CommonUtil.colsFromBean(WmAllocationstock.class)+" from WM_AllocationStock where "
				+ " goodsUid=(select gid from AA_Goods where goodsBarCode='"+barcode+"') and whCode='"+wid+"' and goodsAllocationCode='"+allocationcode+"'"
				+ " and number>0";
		return this.emiQueryList(sql, WmAllocationstock.class);
	}

	public PageBean getWmCallList(String userId, int pageIndex,String whBarcode) {
		String sql="select "+CommonUtil.colsFromBean(wmCall.class)+" from WM_Call where inWhUid in (select dataGid from  RM_RoleData where roleId in (SELECT roleId from RM_RoleUser where userId='"+userId+"') and dataType='W' ) and billState='0'";
		
		if(!CommonUtil.isNullObject(whBarcode)){
			sql="select "+CommonUtil.colsFromBean(wmCall.class,"wc")+" from WM_Call wc "
					+ " left join AA_WareHouse wh on wc.inWhUid=wh.gid "
					+ "where wh.whCode='"+whBarcode+"' and wc.inWhUid in (select dataGid from  RM_RoleData where roleId in (SELECT roleId from RM_RoleUser where userId='"+userId+"') and dataType='W' ) and billState='0'";
		}
		
		return this.emiQueryList(sql, pageIndex,Config.PAGESIZE_MOB, wmCall.class, "recordDate desc");
	}

	public List<wmCallC> getWmCallDetail(String gid) {
		String sql="select "+CommonUtil.colsFromBean(wmCallC.class)+" from WM_Call_C WITH (NoLock) where callUid='"+gid+"' and number>outnumber ";
		return this.emiQueryList(sql, wmCallC.class);
	}

	public wmCall getWmCallByGid(String billUid) {
		String sql="select "+CommonUtil.colsFromBean(wmCall.class)+" from WM_Call where gid='"+billUid+"'";
		return (wmCall) this.emiQuery(sql, wmCall.class);
	}

	public List<OM_MOMaterials> findMoMaterialsListByBillUid(String billUid) {
		String sql="select "+CommonUtil.colsFromBean(OM_MOMaterials.class)+" from OM_Materials  where moDetailsGid in(select gid from OM_Details where moMainGid='"+billUid+"')";
		return this.emiQueryList(sql, OM_MOMaterials.class);
	}

	public void backFillMoMaterialsC(List<WmsGoods> goods) {
		String [] sqls=new String [goods.size()];
		int i=0;
		for(WmsGoods wmsg:goods){
			sqls[i]="update OM_Materials set receivedNum='"+wmsg.getSubmitNum()+"',receivedAssistNum='"+wmsg.getSubmitQuantity()+"'";
//			for(WmsGoodsCfree wgc:wmsg.getCfree()){
//            	if(wgc.getColName().equalsIgnoreCase("cfree1")){
//            		sqls[i]+=",cfree1='"+wgc.getValue()+"' ";
//            	}else if(wgc.getColName().equalsIgnoreCase("cfree2")){
//            		sqls[i]+=",cfree2='"+wgc.getValue()+"' ";
//            	}
//            }
			sqls[i]+=" where gid='"+wmsg.getOmMaterialsUid()+"' ";
			i++;
		}
		this.batchUpdate(sqls);
	}

	@SuppressWarnings("unchecked")
	public List<WmsGoods> materialDetail(String billcode) {
		
//      按订单号定位
//		String sql = "select *,moc.gid as materialOutcUid,moc.cfree1 as mocFree1,pg.gid pggid,prc.gid prcgid,moc.dvdate from WM_MaterialOut_C moc "
//				+ "left join MES_WM_ProduceProcessRouteCGoods pg on moc.processRouteCGoodsUid=pg.gid "
//				+ "left join MES_WM_ProduceProcessRouteC prc on prc.gid=pg.produceRouteCGid where prc.produceRouteGid in "
//				+ "(select gid from MES_WM_ProduceProcessRoute pr where pr.produceUid in "
//				+ "(select gid from WM_ProduceOrder where billCode='"+billcode+"' )) and moc.number>=0";
		
//      按子表pk 定位
//		String sql="select *,moc.gid as materialOutcUid,moc.cfree1 as mocFree1,pg.gid pggid,prc.gid prcgid,moc.dvdate,  dmadeDate,imassDate from WM_MaterialOut_C moc " 
//					+"left join MES_WM_ProduceProcessRouteCGoods pg on moc.processRouteCGoodsUid=pg.gid "
//					+"left join MES_WM_ProduceProcessRouteC prc on prc.gid=pg.produceRouteCGid where prc.produceRouteGid in "
//					+"(select gid from MES_WM_ProduceProcessRoute pr where pr.produceCuid in "
//					+"(select gid from WM_ProduceOrder_C where pk='"+billcode+"' )) and moc.number>=0 ";

		//按任务条码号定位
		String sql="select *,moc.gid as materialOutcUid,moc.cfree1 as mocFree1,pg.gid pggid,prc.gid prcgid,moc.dvdate,  dmadeDate,imassDate from WM_MaterialOut_C moc " 
				+"left join MES_WM_ProduceProcessRouteCGoods pg on moc.processRouteCGoodsUid=pg.gid "
				+"left join MES_WM_ProduceProcessRouteC prc on prc.gid=pg.produceRouteCGid where prc.produceRouteGid in "
				+"(select gid from MES_WM_ProduceProcessRoute pr where pr.produceCuid in "
				+"(select gid from WM_ProduceOrder_C where prc.barcode='"+billcode+"' )) and moc.number>=0 ";
		
		System.out.println(sql);
		
		//查询已材料出库详情
		List<WmsGoods> materialList = this.getJdbcTemplate().query(sql, new RowMapper() {
			@Override
			public WmsGoods mapRow(ResultSet rs, int rowNum) throws SQLException {
				WmsGoods goods = new WmsGoods();
				goods.setGoodsUid(CommonUtil.Obj2String(rs.getObject("goodsUid")));
				goods.setBatch(CommonUtil.Obj2String(rs.getObject("batchCode")));
				goods.setGoodsAllocationUid(CommonUtil.Obj2String(rs.getObject("goodsAllocationUid")));
				goods.setMaterialOutCuid(CommonUtil.Obj2String(rs.getObject("materialOutcUid")));
				goods.setRemainNum(CommonUtil.str2BigDecimal(CommonUtil.Obj2String(rs.getObject("number"))).subtract(CommonUtil.str2BigDecimal(CommonUtil.Obj2String(rs.getObject("backNumber")))));
				goods.setRemainQuantity(CommonUtil.str2BigDecimal(CommonUtil.Obj2String(rs.getObject("assistNumber"))).subtract(CommonUtil.str2BigDecimal(CommonUtil.Obj2String(rs.getObject("backAssistNumber")))));
				goods.setProcessId(CommonUtil.Obj2String(rs.getObject("opGid")));
				
				goods.setProduceRouteCGoodsUid(CommonUtil.Obj2String(rs.getObject("pggid")) );
				goods.setProduceRouteCUid(CommonUtil.Obj2String(rs.getObject("prcgid")));
				goods.setDvdate(CommonUtil.isNullObject(rs.getObject("dvdate"))?null:rs.getString("dvdate"));
				
				goods.setDmdate(CommonUtil.isNullObject(rs.getObject("dmadeDate"))?null:rs.getString("dmadeDate"));
				goods.setMassDate(CommonUtil.isNullObject(rs.getObject("imassDate"))?null:Integer.valueOf(rs.getString("imassDate")));
				
				List<WmsGoodsCfree> cfree = new ArrayList<WmsGoodsCfree>();
				WmsGoodsCfree cf = new WmsGoodsCfree();
				cf.setColName("cfree1");
				cf.setIsShow(1);
				cf.setName("工序");
				cf.setValue(CommonUtil.Obj2String(rs.getObject("mocFree1")));
				cfree.add(cf);
				goods.setCfree(cfree);
				
				return goods;
			}
		});
		
		 
		 return materialList;
	}

	public void updateMaterialoutCs(List<WmMaterialoutC> updWoclist) {
		String sqls[] = new String[updWoclist.size()];
		for(int i=0;i<updWoclist.size();i++){
			WmMaterialoutC out = updWoclist.get(i);
			sqls[i] = "update WM_MaterialOut_C set backNumber=isNull(backNumber,0)+"+out.getBackNumber()+",backAssistNumber=isNull(backAssistNumber,0)+"+out.getBackAssistNumber()+" where gid='"+out.getGid()+"'";
		}
		this.batchUpdate(sqls);
	}
	
	
	/**
	 * @category 从用友中查询现存量
	 *2016 2016年5月3日上午10:50:11
	 *List<CurrentStock>
	 *宋银海
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CurrentStock> getAllocationStockYonYou(String condition){
		String sql="select autoID,cwhCode,cinvCode,cbatch,iquantity,inum,cfree1,cfree2,foutQuantity,foutNum,dvdate,dmdate,imassDate from "+Config.BUSINESSDATABASE+"CurrentStock where "+condition;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				CurrentStock cs=new CurrentStock();
				cs.setAutoID(rs.getInt("autoID"));
				cs.setCwhCode(rs.getString("cwhCode"));
				cs.setCinvCode(rs.getString("cinvCode"));
				cs.setCbatch(CommonUtil.Obj2String(rs.getObject("cbatch")));
				cs.setIquantity(rs.getBigDecimal("iquantity"));
				cs.setInum(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("inum"));
				cs.setCfree1(CommonUtil.Obj2String(rs.getObject("cfree1")));
				cs.setCfree2(CommonUtil.Obj2String(rs.getObject("cfree2")));
				cs.setFoutQuantity(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutQuantity"));
				cs.setFoutNum(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutNum"));
				cs.setDvDate(CommonUtil.isNullObject(rs.getObject("dvdate"))?null:Timestamp.valueOf(rs.getString("dvdate")));
				cs.setDmDate(CommonUtil.isNullObject(rs.getObject("dmdate"))?null:Timestamp.valueOf(rs.getString("dmdate")));
				cs.setImassDate(CommonUtil.isNullObject(rs.getObject("imassDate"))?null:Integer.valueOf(rs.getString("imassDate")));
				return cs;
			}
			
		});
	}
	/**
	 * 
	 * @category 根据其他入库主Gid 删除其下的所有子类
	 * 2016年7月7日 下午4:41:03
	 * @author 杨胜
	 * @param gid
	 */
	public void deleteOtherWarehouseC(String gid){
			String sql = "delete from WM_OtherWarehouse_C where otherInUid ='"+gid+"'";
			this.update(sql);
		}


	/**
	 *
	 * @category 根据其他出库主Gid 删除其下的所有子类
	 * 2016年7月7日 下午4:41:03
	 * @author 杨胜
	 * @param gid
	 */
	public void deleteOthersOutC(String gid){
		String sql = "delete from WM_OthersOut_C where othersOutUid ='"+gid+"'";
		this.update(sql);
	}
 /**
  * 删除其他入库主表
  * @category
  * 2016年7月13日 下午3:02:36
  * @author 杨胜
  * @param gid
  */
	public void deleteOtherWarehouse(String gid){
			String sql = "delete from WM_OtherWarehouse where gid ='"+gid+"'";
			this.update(sql);
	}
/**
 * 查询最后一条记录
 * @category
 * 2016年7月13日 下午3:00:42
 * @author 杨胜
 * @param gid
 * @param orgId
 * @param sobId
 * @return
 */
	@SuppressWarnings("rawtypes")
	public Map findOtherWarehouse(String gid,String orgId,String sobId) {
		String sql="";
		if(CommonUtil.isNullString(gid)){
			sql="SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.depUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_OtherWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1  AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
		}else{
			sql="SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.depUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_OtherWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1 AND wowh.gid='"+gid+"' AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
		}
		
		return  this.queryForMap(sql);
	}
/**
 * 根据主其他入库表GID查询其他入库字表
 * @category
 * 2016年7月13日 下午3:01:15
 * @author 杨胜
 * @param gid
 * @return
 */
	@SuppressWarnings("rawtypes")
	public List getOtherWarehouseClist(String gid) {
		String sql = "SELECT wowhc.*,code FROM	WM_OtherWarehouse_C wowhc left join AA_UserDefine aauserdefine on aauserdefine.value = wowhc.cfree1 WHERE wowhc.otherInUid = '"+gid+"'  ";
		return this.queryForList(sql);
	}
/**
 * 分页列表
 * @category
 * 2016年7月13日 下午3:01:58
 * @author 杨胜
 * @param pageIndex
 * @param pageSize
 * @param condition
 * @return
 */
	public PageBean getOtherWarehouseList(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("goodsUid", "goodsUid");
		match.put("number", "number");
		String sql ="select owh.*,wowc.goodsUid goodsUid,wowc.number from WM_OtherWarehouse_C wowc LEFT JOIN WM_OtherWarehouse owh ON wowc.otherInUid=owh.gid  where 1=1";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		String sortSql="billDate desc";
		return this.emiQueryList(sql, pageIndex, pageSize, WmOtherwarehouse.class,match,sortSql);
	}
	//................................................... 生产入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	
/**
 * 查询最后一条记录
 * @category
 * 2016年7月13日 下午3:11:09
 * @author 杨胜
 * @param gid
 * @param orgId
 * @param sobId
 * @return
 */
	@SuppressWarnings("rawtypes")
	public Map findProduceWarehouse(String gid,String orgId,String sobId) {
		String sql="";
		if(CommonUtil.isNullString(gid)){
			sql="SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_ProductionWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1  AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
		}else{
			sql="SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_ProductionWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1 AND wowh.gid='"+gid+"' AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
		}
		
		return  this.queryForMap(sql);
	}
/**
 * 根据主生产入库表GID查询生产入库字表
 * @category
 * 2016年7月13日 下午3:11:47
 * @author 杨胜
 * @param gid
 * @return
 */
	@SuppressWarnings("rawtypes")
	public List getProduceWarehouseClist(String gid) {
			String sql = "SELECT wowhc.*,code FROM	WM_ProductionWarehouse_C wowhc left join AA_UserDefine aauserdefine on aauserdefine.value = wowhc.cfree1 WHERE wowhc.proUid = '"+gid+"'  ";
			return this.queryForList(sql);
	}
	
	//返回生产入库子表表条目
	public int getProduceWarehousecCount(String condition){
		String sql="select count(1) from WM_ProductionWarehouse_C where "+condition;
		return this.queryForInt(sql);
	}
	
	//返回其它入库子表表条目
	public int getOtherWarehouseCount(String condition){
		String sql="select count(1) from WM_OtherWarehouse_C where "+condition;
		return this.queryForInt(sql);
	}
	
/**
 * 根据生产入库主Gid 删除其下的所有子类
 * @category
 * 2016年7月14日 上午8:59:13
 * @author 杨胜
 * @param gid
 */
	public void deleteProduceWarehouseC(String gid){
			String sql = "delete from WM_ProductionWarehouse_C where proUid ='"+gid+"'";
			this.update(sql);
		}
/**
  * 删除生产入库主表
  * @category
  * 2016年7月13日 下午3:02:36
  * @author 杨胜
  * @param gid
  */
	public void deleteProduceWarehouse(String gid){
			String sql = "delete from WM_ProductionWarehouse where gid ='"+gid+"'";
			this.update(sql);
	}
	public WmProductionwarehouseC findWmProductionwarehouseC(String gid) {
		return (WmProductionwarehouseC) this.emiFind(gid, WmProductionwarehouseC.class);
	}
/**
 * 生产分页
 * @category
 * 2016年7月14日 上午10:44:29
 * @author 杨胜
 * @param pageIndex
 * @param pageSize
 * @param condition
 * @return
 */
	public PageBean getProduceWarehouseList(int pageIndex,int pageSize,String condition){
		Map match = new HashMap();
		match.put("goodsGid", "goodsGid");
		String sql ="SELECT owh.*,wpwc.goodsUid goodsGid FROM WM_ProductionWarehouse_C wpwc LEFT JOIN WM_ProductionWarehouse owh on wpwc.proUid=owh.gid where 1=1";			
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		String sortSql="billdate desc";
		return this.emiQueryList(sql, pageIndex, pageSize, WmProductionwarehouse.class,match,sortSql);
	}
	
	//................................................... 采购入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
	/**
	 * 查询最后一条记录
	 * @category
	 * 2016年7月13日 下午3:11:09
	 * @author 杨胜
	 * @param gid
	 * @param orgId
	 * @param sobId
	 * @return
	 */
		@SuppressWarnings("rawtypes")
		public Map findPoWarehouse(String gid,String orgId,String sobId) {
			String sql="";
			if(CommonUtil.isNullString(gid)){
				sql="SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,wowh.providerUid, ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_PoWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1  AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}else{
				sql="SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,wowh.providerUid,ymuser.userName recordpersonName,wowh.recordPersonId FROM	WM_PoWarehouse wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonId WHERE	1 = 1 AND wowh.gid='"+gid+"' AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}
			
			return  this.queryForMap(sql);
		}
		
		
		/**
		 * 根据主生产入库表GID查询采购入库字表
		 * @category
		 * 2016年7月13日 下午3:11:47
		 * @author 杨胜
		 * @param gid
		 * @return
		 */
			@SuppressWarnings("rawtypes")
			public List getPoWarehouseClist(String gid) {
					String sql = "SELECT wowhc.* FROM	WM_PoWarehouse_C wowhc  WHERE wowhc.poWhUid = '"+gid+"'  ";
					return this.queryForList(sql);
			}
			
			/**
			 * 根据采购入库主Gid 删除其下的所有子类
			 * @category
			 * 2016年7月14日 上午8:59:13
			 * @author 杨胜
			 * @param gid
			 */
			public void deletePoWarehouseC(String gid){
					String sql = "delete from WM_PoWarehouse_C where poWhUid ='"+gid+"'";
					this.update(sql);
				}	
			
			
	/**
	  * 删除生产入库主表
	  * @category
	  * 2016年7月13日 下午3:02:36
	  * @author 杨胜
	  * @param gid
	  */
		public void deletePoWarehouse(String gid){
				String sql = "delete from WM_PoWarehouse where gid ='"+gid+"'";
				this.update(sql);
		}
		
		
		/**
		 * 采购入库分页
		 * @category
		 * 2016年7月14日 上午10:44:29
		 * @author 杨胜
		 * @param pageIndex
		 * @param pageSize
		 * @param condition
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public PageBean getPoWarehouseList(int pageIndex,int pageSize,String condition){
			Map match = new HashMap();
			match.put("goodsGid", "goodsGid");
			match.put("quantity", "quantity");
			String sql ="select wpw.*,wpwc.materialUid goodsGid,wpwc.quantity from WM_PoWarehouse_C wpwc LEFT JOIN WM_PoWarehouse wpw on wpw.gid=wpwc.poWhUid where 1=1 ";
			if(!CommonUtil.isNullString(condition)){
				sql += condition;
			}
			String sortSql="billdate desc";
			PageBean list=this.emiQueryList(sql, pageIndex, pageSize,WmPowarehouse.class,match,sortSql);
			/*for (int i = 0; i < list.getList().size(); i++) {
				list.getList().get(index)
			}*/
			return list;
		}
	
	//................................................... 销售出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	
	/**
	 * 查询最后一条记录
	 * @category
	 * 2016年7月13日 下午3:11:09
	 * @author 杨胜
	 * @param gid
	 * @param orgId
	 * @param sobId
	 * @return
	 */
		@SuppressWarnings("rawtypes")
		public Map findSaleOutWarehouse(String gid,String orgId,String sobId) {
			String sql="";
			if(CommonUtil.isNullString(gid)){
				sql="SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,customerUid,ymuser.userName recordpersonName,wowh.recordPerson FROM WM_SaleOut wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPerson WHERE	1 = 1  AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}else{
				sql="SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,customerUid,ymuser.userName recordpersonName,wowh.recordPerson FROM	WM_SaleOut wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPerson WHERE	1 = 1 AND wowh.gid='"+gid+"' AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}
			
			return  this.queryForMap(sql);
		}
	/**
	 * 根据销售出库主表GID查询销售出库字表
	 * @category
	 * 2016年7月13日 下午3:11:47
	 * @author 杨胜
	 * @param gid
	 * @return
	 */
		@SuppressWarnings("rawtypes")
		public List getSaleOutWarehouseClist(String gid) {
				String sql = "SELECT wowhc.* FROM	WM_SaleOut_C wowhc WHERE wowhc.saleoutUid = '"+gid+"'  ";
				return this.queryForList(sql);
		}
		
		//返回生产入库子表表条目
		public int getSaleOutWarehousecCount(String condition){
			String sql="select count(1) from WM_SaleOut_C where "+condition;
			return this.queryForInt(sql);
		}
		
	/**
	 * 根据销售出库主Gid 删除其下的所有子类
	 * @category
	 * 2016年7月14日 上午8:59:13
	 * @author 杨胜
	 * @param gid
	 */
		public void deleteSaleOutWarehouseC(String gid){
				String sql = "delete from WM_SaleOut_C where saleoutUid ='"+gid+"'";
				this.update(sql);
			}
	/**
	  * 删除销售出库主表
	  * @category
	  * 2016年7月13日 下午3:02:36
	  * @author 杨胜
	  * @param gid
	  */
		public void deleteSaleOutWarehouse(String gid){
				String sql = "delete from WM_SaleOut where gid ='"+gid+"'";
				this.update(sql);
		}
		public WmSaleoutC findWmSaleOutWarehouseC(String gid) {
			return (WmSaleoutC) this.emiFind(gid, WmSaleoutC.class);
		}
	/**
	 * 销售出库分页
	 * @category
	 * 2016年7月14日 上午10:44:29
	 * @author 杨胜
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
		public PageBean getSaleOutWarehouseList(int pageIndex,int pageSize,String condition){
			Map match = new HashMap();
			match.put("goodsUid", "goodsUid");
			String sql ="select owh.*,wsoc.goodsUid goodsUid from WM_SaleOut_C wsoc LEFT JOIN WM_SaleOut owh on owh.gid=wsoc.saleoutUid where 1=1";			
			if(!CommonUtil.isNullString(condition)){
				sql += condition;
			}
			String sortSql="billdate desc";
			return this.emiQueryList(sql, pageIndex, pageSize, WmSaleout.class,match,sortSql);
		}	
		
		//................................................... 材料出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//	
		/**
		 * 查询最后一条记录
		 * @category
		 * 2016年7月13日 下午3:11:09
		 * @author 杨胜
		 * @param gid
		 * @param orgId
		 * @param sobId
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public Map findMaterialOut(String gid,String orgId,String sobId) {
			String sql="";
			if(CommonUtil.isNullString(gid)){
				sql="SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPerson FROM WM_MaterialOut wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPerson WHERE	1 = 1  AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}else{
				sql="SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.badge,wowh.departmentUid,wowh.whUid,wowh.notes ,wowh.recordDate,ymuser.userName recordpersonName,wowh.recordPerson FROM	WM_MaterialOut wowh LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPerson WHERE	1 = 1 AND wowh.gid='"+gid+"' AND wowh.sobGid = '" + sobId + "' AND wowh.orgGid = '" + orgId + "' ORDER BY	wowh.pk DESC ";
			}
			
			return  this.queryForMap(sql);
		}
		
		/**
		 * 根据销售出库主表GID查询销售出库字表
		 * @category
		 * 2016年7月13日 下午3:11:47
		 * @author 杨胜
		 * @param gid
		 * @return
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List getMaterialOutClist(String gid) {
			String sql = "SELECT wmc.* FROM	WM_MaterialOut_C wmc where wmc.materialOutUid = '"+gid+"' ";
			
			return this.queryForList(sql);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List getMaterList(String gid){
			Map match = new HashMap();
			match.put("produceCode", "produceCode");
			match.put("goodName", "goodName");
			String sql = "SELECT wmc.*,wpo.billCode produceCode,wpoc.goodsUid goodName FROM	WM_MaterialOut_C wmc ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRouteCGoods mwprcg ON wmc.processRouteCGoodsUid = mwprcg.gid ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRouteC prcgc ON mwprcg.produceRouteCGid = prcgc.gid ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRoute prcg ON prcg.gid = prcgc.produceRouteGid ";
			sql+="LEFT JOIN WM_ProduceOrder wpo ON wpo.gid = prcg.produceUid ";
			sql+="LEFT JOIN WM_ProduceOrder_C wpoc ON wpoc.gid = prcg.produceCUid ";
			sql+="where wmc.materialOutUid = '"+gid+"' ";
			
			return this.emiQueryList(sql, WmMaterialoutC.class, match);
		}
		/**
		 * 材料出库查询方法
		 * @param pageIndex
		 * @param pageSize
		 * @param condition
		 * @return
		 * 2016年9月14日15:38:17
		 * @author cuixn
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public PageBean getAllListMaterialout(int pageIndex,int pageSize,String condition){
			Map match = new HashMap();
			match.put("owhGid", "owhGid");
			match.put("owhCode", "owhCode");
			match.put("produceCode", "produceCode");
			match.put("goodName", "goodName");
			match.put("recordPerson", "recordPerson");
			match.put("departId", "departId");
			match.put("whUid", "whUid");
			String sql="SELECT"+CommonUtil.colsFromBean(WmMaterialoutC.class, "wmc")+",owh.gid owhGid,owh.billCode owhCode,owh.recordPerson recordPerson,owh.departmentUid departId,owh.whUid whUid,wpo.billCode produceCode,wpoc.goodsUid goodName FROM WM_MaterialOut owh ";
			sql+="LEFT JOIN	WM_MaterialOut_C wmc ON wmc.materialOutUid=owh.gid ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRouteCGoods mwprcg ON wmc.processRouteCGoodsUid = mwprcg.gid ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRouteC prcgc ON mwprcg.produceRouteCGid = prcgc.gid ";
			sql+="LEFT JOIN MES_WM_ProduceProcessRoute prcg ON prcg.gid = prcgc.produceRouteGid ";
			sql+="LEFT JOIN WM_ProduceOrder wpo ON wpo.gid = prcg.produceUid ";
			sql+="LEFT JOIN WM_ProduceOrder_C wpoc ON wpo.gid = prcg.producecUid where 1=1 ";
			//sql+="LEFT JOIN AA_Goods ag ON ag.gid = wpoc.goodsUid where 1=1";
			sql+=condition;
			String sortSql="pk desc";
			return emiQueryList(sql, pageIndex, pageSize, WmMaterialoutC.class,match,sortSql);
		}
		
		public MesWmProduceProcessroutecGoods getGoodWithGid(String gid){
			//String sql="select"+CommonUtil.colsFromBean(MesWmProcessRoutecGoods.class)+"from MES_WM_ProcessRouteCGoods where gid='"+gid+"'";
			return (MesWmProduceProcessroutecGoods)emiFind(gid, MesWmProduceProcessroutecGoods.class);
		}
		public int[] updateMesProduceGoods(final List<Map> goods){
			String sql="update MES_WM_ProduceProcessRouteCGoods set receivedNum =isnull(receivedNum,0)-? where gid=?";
			return batchUpdate(sql,new BatchPreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps, int index) throws SQLException {
					
					Map good=goods.get(index);
					
					ps.setString(1, good.get("number").toString());
					ps.setString(2, CommonUtil.Obj2String(good.get("processRouteCGoodsUid")));
					
				}
				
				@Override
				public int getBatchSize() {
					return goods.size();
				}
			});
		}
		
		/**
		 * 根据材料出库主Gid 删除其下的所有子类
		 * @category
		 * 2016年7月14日 上午8:59:13
		 * @author 杨胜
		 * @param gid
		 */
		public void deleteMaterialOutC(String gid){
			String sql = "delete from WM_MaterialOut_C where materialOutUid ='"+gid+"'";
			this.update(sql);
		}

		/**
		  * 删除销售出库主表
		  * @category
		  * 2016年7月13日 下午3:02:36
		  * @author 杨胜
		  * @param gid
		  */
		public void deleteMaterialOutWarehouse(String gid){
				String sql = "delete from WM_MaterialOut where gid ='"+gid+"'";
				this.update(sql);
		}
		
		/**
		 * 材料出库分页
		 * @category
		 * 2016年7月14日 上午10:44:29
		 * @author 杨胜
		 * @param pageIndex
		 * @param pageSize
		 * @param condition
		 * @return
		 */
		public PageBean getMaterialOutWarehouseList(int pageIndex,int pageSize,String condition){

			String sql ="select "+CommonUtil.colsFromBean(WmMaterialout.class,"owh")+"  from WM_MaterialOut owh  where 1=1";			
			if(!CommonUtil.isNullString(condition)){
				sql += condition;
			}
			return this.emiQueryList(sql, pageIndex, pageSize, WmMaterialout.class,"pk");
		}	
		//................................................... 库存操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//		
/**
 * 库存列表		
 * @category
 * 2016年7月19日 上午9:42:02
 * @author 杨胜
 * @param pageIndex
 * @param pageSize
 * @param condition
 * @return
 */
		public PageBean getAllocationStockList(int pageIndex,int pageSize,String condition){

			Map match = new HashMap();

			match.put("goodsName", "goodsName");
			match.put("goodsStandard", "goodsStandard");

			String sql ="select "+CommonUtil.colsFromBean(WmAllocationstock.class,"owh")+",ag.goodsName,ag.goodsStandard  from WM_AllocationStock owh left join AA_Goods ag on ag.goodsCode = owh.goodsCode  where 1=1 and owh.number >0  ";
			if(!CommonUtil.isNullString(condition)){
				sql += condition;
			}
			return this.emiQueryList(sql, pageIndex, pageSize, WmAllocationstock.class,match,"pk");
		}

	public WmProcureorder getBillDetail(String billgid) {
			String sql = " select * from WM_ProcureOrder wo where wo.gid = '"+billgid+"' ";

			return (WmProcureorder) this.emiQuery(sql,WmProcureorder.class);

	}

	public WmProcureorderC getProcureorderCDetail(String procureOrderCuid) {
		String sql = " select * from WM_ProcureOrder_C wo where wo.gid = '"+procureOrderCuid+"' ";

		return (WmProcureorderC) this.emiQuery(sql,WmProcureorderC.class);

	}

	public boolean addOtherWarehouseIn(List<WmOtherwarehouse> wmOtherwarehouses) {
		return this.emiInsert(wmOtherwarehouses);


	}

	public boolean addOtherWarehouseIns(List<WmOtherwarehouseC> wmOtherwarehouseCs) {
		return this.emiInsert(wmOtherwarehouseCs);
	}

	public boolean addOthersOutIn(List<WmOthersout> wmOthersouts) {
		return this.emiInsert(wmOthersouts);
	}

	public boolean addOthersOutIns(List<WmOthersoutC> wmOthersoutCS) {
		return this.emiInsert(wmOthersoutCS);
	}

	public AaGoodsallocation getAAGoodsallocationInfoByWhcode(String whCode) {
		String sql = " select top 1 * from Aa_Goodsallocation ag where ag.whUid in (select gid from aa_Warehouse wa where wa.whCode = '"+whCode+"' ) ";
		return (AaGoodsallocation) this.emiQuery(sql,AaGoodsallocation.class);

	}

	public Map findOtherOut(String otherOutgid, String orgId, String sobId) {

		String sql="";
		if(CommonUtil.isNullString(otherOutgid)){
			sql="    SELECT TOP 1 wowh.gid,wowh.billCode,wowh.billDate,wowh.departmentUid,wowh.warehouseUid,wowh.notes ,wowh.recordDate,  "    +
							"    ymuser.userName recordpersonName,wowh.recordPersonUid   "    +
							"    FROM	WM_OthersOut wowh   "    +
							"    LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonUid   "    +
							"    WHERE	1 = 1  AND wowh.sobGid = '"+sobId+ "' AND wowh.orgGid = '"+orgId +"' ORDER BY	wowh.pk DESC";
		}else{
			sql= " 	SELECT  wowh.gid,wowh.billCode,wowh.billDate,wowh.departmentUid,wowh.warehouseUid,wowh.notes ,wowh.recordDate, " +
					" 	ymuser.userName recordpersonName,wowh.recordPersonUid FROM	WM_OthersOut wowh  " +
					" 	LEFT JOIN YM_User ymuser ON ymuser.gid = wowh.recordPersonUid  " +
					" 	WHERE	1 = 1 AND wowh.gid='"+otherOutgid+"' AND wowh.sobGid = '"+sobId+"' AND wowh.orgGid = '"+ orgId +"' ORDER BY	wowh.pk DESC ";
		}

		return  this.queryForMap(sql);
	}

	public List getOthersOutClist(String gid) {
		String sql = " SELECT wowhc.*,code FROM WM_OthersOut_C wowhc " +
				" left join AA_UserDefine aauserdefine on aauserdefine.value = wowhc.cfree1 " +
				" WHERE wowhc.othersOutUid = '"+gid+"' ";
		return this.queryForList(sql);
	}

	public PageBean getOthersOutList(int pageIndex, int pageSize, String condition) {
		Map match = new HashMap();
		match.put("goodsUid", "goodsUid");
		match.put("number", "number");
		String sql ="select owh.*,wowc.goodsUid goodsUid,wowc.number " +
				"from WM_OthersOut_C wowc " +
				"LEFT JOIN WM_OthersOut owh ON wowc.othersOutUid=owh.gid  where 1=1";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		String sortSql="billDate desc";
		return this.emiQueryList(sql, pageIndex, pageSize, WmOthersout.class,match,sortSql);

	}

	public int[] backFillProcureOrderC(List<WmsGoods> goods) {
		String sql="update WM_ProcureOrder_C set putinNumber=isnull(putinNumber,0)+?  where gid=?";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {

				WmsGoods good=goods.get(index);

				ps.setBigDecimal(1, good.getSubmitNum());
				ps.setString(2, good.getProcureOrderCuid());

			}

			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}

	/**
	* @Desc  删除其他出库主表
	* @author yurh
	* @create 2018-03-19 13:18:19
	**/
	public void deleteOthersOut(String gid) {
		String sql = "delete from WM_OthersOut where gid ='"+gid+"'";
		this.update(sql);
	}


	public List<Map> getAllocationStockListForPrint(int pageIndex, int pageSize, String condition) {
		Map match = new HashMap();

		match.put("goodsName", "goodsName");

		String sql ="select "+CommonUtil.colsFromBean(WmAllocationstock.class,"owh")+",ag.goodsName,ga.name,u.unitName,wa.whName,ag.goodsStandard  " +
				" from WM_AllocationStock owh left join AA_Goods ag on ag.goodsCode = owh.goodsCode  " +
				" left join AA_WareHouse wa on wa.whCode = owh.whCode "+
				" left join AA_GoodsAllocation ga on ga.code = owh.goodsAllocationCode "+
				" left join  unit u on u.gid = ag.goodsUnit "+
				" where 1=1 and owh.number >0   ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		sql += " ORDER BY pk ";
		return this.queryForList(sql);
	}
}
