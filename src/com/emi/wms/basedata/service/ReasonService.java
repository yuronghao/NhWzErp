package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.ReasonDao;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaReason;

public class ReasonService {

	private ReasonDao reasonDao;

	public ReasonDao getReasonDao() {
		return reasonDao;
	}

	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}

	public PageBean getreasonList(int pageIndex, int pageSize,String conditionSql) {
		return reasonDao.getreasonList(pageIndex, pageSize,conditionSql);
	}
	
	public boolean addreason(AaReason reason) {
		return reasonDao.addreason(reason);
	}
	public AaReason findreason(String gid) {
		return reasonDao.findreason(gid);
	}
	public boolean updatereason(AaReason reason) {
		return reasonDao.updatereason(reason);
	}
	public void deletereason(String gid) {
		//1、删除主表（假删除）
		reasonDao.deletereason(gid);
	}
}
