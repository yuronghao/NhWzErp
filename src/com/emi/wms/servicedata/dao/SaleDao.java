package com.emi.wms.servicedata.dao;

import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;

/**
 * 销售订单
 * @author Administrator
 *
 */

public class SaleDao extends BaseDao {
	//销售订单列表
	public PageBean getWmSaleorderList(int pageIndex, int pageSize,
			String conditionSql) {
		/* Map match = new HashMap(); */
		// match.put("descriptionparent", "Asset_change.descriptionparent");
		String sql = "select s.* from WM_SaleOrder s "
				+ " where 1=1 and s.isdel=0 ";
		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, " pk desc");
	}
	//根据GID 查询销售主表
	public Map findWMSaleOrder(String gid)
	{
		String sql="select top 1 s.* from WM_SaleOrder s where 1=1 ";
		return this.queryForMap(sql);
	}
	//添加销售主表
	public boolean addWMSaleOrder(WmSaleorder mSaleorder)
	{
		return this.emiInsert(mSaleorder);
	}
	//修改销售主表	
	public boolean updateWMSaleOrder(WmSaleorder mSaleorder)
	{
		return this.emiUpdate(mSaleorder);
	}
	//根据GID查询销售字表
	public List<Map> findWmSaleorderC(String gid)
	{
		String sql="select s.* from WM_SaleOrder_C s where s.saleorderuid='"+gid+"'";
		return this.queryForList(sql);
	}
	//添加销售字表
	public boolean addWmSaleorderC(List<WmSaleorderC> mSaleorderC)
	{
		return this.emiInsert(mSaleorderC);
	}
	//修改销售字表	
	public boolean updateWmSaleorderC(List<WmSaleorderC> mSaleorderC)
	{
		return this.emiUpdate(mSaleorderC);
	}
	//删除销售字表	
	public boolean deleteWmSaleorderC(String[] psList)
	{
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "update  WM_SaleOrder_C set isdel=1 where gid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//获取单号
	public String getBillId(String billType, String preFix) {
		return this.getCallBillId(billType, preFix);
	}
	public WmSalesend SaleSendDetail(String billGid) {
		return (WmSalesend) this.emiFind(billGid, WmSalesend.class);
	}
	public List<WmSalesendC> getSaleSendCbySaleSendGid(String billGid,String condition) {
		String sql="select "+CommonUtil.colsFromBean(WmSalesendC.class)+" from WM_SaleSend_C WITH (NoLock)  where salesendUid='"+billGid+"' "+condition;
		return this.emiQueryList(sql, WmSalesendC.class);
	}
	
	
	public List<WmSalesendC> getSaleSendCbySaleSendGid(String condition) {
		String sql="select "+CommonUtil.colsFromBean(WmSalesendC.class)+" from WM_SaleSend_C WITH (NoLock) where "+condition;
		return this.emiQueryList(sql, WmSalesendC.class);
	}
	
	
	/**
	 * @category 通过质检单查询发货单信息
	 *2016 2016年4月23日上午11:46:06
	 *List<Map>
	 * 宋银海
	 */
	public List<Map> getSaleSendCbyCheck(String condition) {
		String sql="select cc.gid ccgid, abs(cc.okNum) okNum,abs(cc.notOkNum) notOkNum,abs(cc.assistOkNum) assistOkNum,"
				+ " abs(cc.assistNotOkNum) assistNotOkNum, abs(cc.putoutNum) putoutNum,abs(cc.putoutAssistNum) putoutAssistNum,cc.batch,cc.saleSendCuid,cc.goodsUid, "
				+ " c.checkCode,c.checkDate,c.gid,"
				+ " ss.customerUid,ss.gid ssgid,"
				+ " ssc.cfree1,ssc.whCode from QM_CheckCbill cc WITH (NoLock) "
				+ " left join WM_SaleSend_C ssc WITH (NoLock) on cc.saleSendCuid=ssc.gid "
				+ " left join WM_SaleSend ss WITH (NoLock) on ssc.salesendUid=ss.gid "
				+ " left join QM_CheckBill c WITH (NoLock) on cc.checkGid=c.gid where "+condition;
		return this.queryForList(sql);
	}
	
	
	public boolean toUpdateList(List obj) {
		return this.emiUpdate(obj);
	}

}
