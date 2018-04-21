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
 * 销售发货单
 * @author Administrator
 *
 */

public class SaleSendDao extends BaseDao {
	//销售发货单列表
	public PageBean getWmSalesendList(int pageIndex, int pageSize,
			String conditionSql) {
		/* Map match = new HashMap(); */
		// match.put("descriptionparent", "Asset_change.descriptionparent");
		String sql = "select s.*,a.name from WM_SaleOrder s "
				+ " where 1=1 and s.isdel=0";
		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, " pk desc");
	}
	//根据GID 查询销售发货单主表
	public Map findWmSalesend(String gid)
	{
		String sql="";
		return this.queryForMap(sql);
	}
	//添加销售发货单主表
	public boolean addWmSalesend(WmSalesend mSalesend)
	{
		return this.emiInsert(mSalesend);
	}
	//修改销售发货单主表	
	public boolean updateWmSalesend(WmSalesend mSalesend)
	{
		return this.emiUpdate(mSalesend);
	}

	//根据GID查询销售发货单字表
	public List<Map> findWmSalesendC(String gid)
	{
		String sql="select s.* from WM_SaleOrder_C s where s.saleorderuid='"+gid+"'";
		return this.queryForList(sql);
	}
	//添加销售发货单字表
	public boolean addWmSalesendC(List<WmSalesendC> mSalesendC)
	{
		return this.emiInsert(mSalesendC);
	}
	//修改销售发货单字表	
	public boolean updateWmSalesendC(List<WmSalesendC> mSalesendC)
	{
		return this.emiUpdate(mSalesendC);
	}
	//删除销售发货单字表	
	public boolean deleteWmSalesendC(String[] psList)
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
	//获取发货单单号
	public String getBillId(String billType, String preFix) {
		return this.getCallBillId(billType, preFix);
	}

}
