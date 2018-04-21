package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.bean.wmCall;
import com.emi.wms.servicedata.dao.AllocationDao;

public class AllocationService {

	private AllocationDao allocationDao;

	public AllocationDao getAllocationDao() {
		return allocationDao;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public List getcolumns() {
		return allocationDao.getcolumns();
	}
	
	public PageBean getallocationlist(int pageIndex,int pageSize,String condition) {
		return allocationDao.getallocationlist(pageIndex,pageSize,condition);
	}
}
