package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.CusproDao;
import com.emi.wms.bean.AaProviderCustomer;

public class CusproService {

	private CusproDao cusproDao;

	public CusproDao getCusproDao() {
		return cusproDao;
	}

	public void setCusproDao(CusproDao cusproDao) {
		this.cusproDao = cusproDao;
	}

	public List getClassifyList(String conditionSql) {
		return cusproDao.getClassifyList(conditionSql);
	}
	
	public PageBean getcusproList(int pageIndex, int pageSize,String conditionSql) {
		return cusproDao.getcusproList(pageIndex, pageSize,conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return cusproDao.getCategoryList(conditionSql);
	}
	public boolean addprocus(AaProviderCustomer AaProviderCustomer) {
		return cusproDao.addprocus(AaProviderCustomer);
	}
	public boolean addprocusbook(List list) {
		return cusproDao.addprocusbook(list);
	}
	public Map findCuspro(String exhTypeId) {
		return cusproDao.findCuspro(exhTypeId);
	}
	public boolean updateCuspro(AaProviderCustomer AaProviderCustomer) {
		return cusproDao.updateCuspro(AaProviderCustomer);
	}
	public boolean deletebooks(String exhTypeId) {
		return cusproDao.deletebooks(exhTypeId);
	}
	public List getcusprobookList(String exhTypeId) {
		return cusproDao.getcusprobookList(exhTypeId);
	}
	public boolean deleteCuspro(String[] strsums) {
		return  cusproDao.deleteCuspro(strsums);
	}
}
