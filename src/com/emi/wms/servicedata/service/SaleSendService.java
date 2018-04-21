package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.servicedata.dao.SaleDao;
import com.emi.wms.servicedata.dao.SaleSendDao;

/**
 * 销售发货单
 * @author Administrator
 *
 */
public class SaleSendService {
	public SaleSendDao saleSendDao;


	public void setSaleSendDao(SaleSendDao saleSendDao) {
		this.saleSendDao = saleSendDao;
	}
	//销售订单发货单列表
	public PageBean getWmSalesendList(int pageIndex, int pageSize,
				String conditionSql) {
		return saleSendDao.getWmSalesendList(pageIndex, pageSize, conditionSql);
	}
	//根据GID 查询销售发货单主表
	public Map findWmSalesend(String gid)
	{
		return saleSendDao.findWmSalesend(gid);
	}
	//添加销售发货单主表和字表
	public boolean addWmSalesend(WmSalesend mSalesend,List<WmSalesendC> mSalesendC)
	{
		return false;
	}
	//修改销售发货单主表	
	public boolean updateWmSalesend(WmSalesend mSalesend)
	{
		return saleSendDao.updateWmSalesend(mSalesend);
	}
	//修改销售发货单主表和字表	
	public boolean updateWmSalesend(WmSalesend mSalesend,List<WmSalesendC> mSalesendC,List<WmSalesendC> addList,String deleteGids)
	{
		return false;
	}
	//根据主表GID查询销售发货单字表
	public List<Map> findWmSalesendC(String gid)
	{
		return saleSendDao.findWmSalesendC(gid);
	}
	//获取发货单单号
	public String getBillId(String billType, String preFix) {
		return saleSendDao.getBillId(billType, preFix);
	}
}
