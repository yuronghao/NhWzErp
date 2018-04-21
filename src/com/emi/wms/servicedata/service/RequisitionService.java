package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.WmPurchaserequisition;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.RequisitionDao;

public class RequisitionService {

	private RequisitionDao requisitionDao;

	public RequisitionDao getrequisitionDao() {
		return requisitionDao;
	}

	public void setrequisitionDao(RequisitionDao requisitionDao) {
		this.requisitionDao = requisitionDao;
	}

	public List getClassifyList(String conditionSql) {
		return requisitionDao.getClassifyList(conditionSql);
	}
	
	public Map getorganizeInfo(String conditionSql) {
		return requisitionDao.getorganizeInfo(conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return requisitionDao.getCategoryList(conditionSql);
	}
	public boolean addorg(AaOrg aaorg) {
		return requisitionDao.addorg(aaorg);
	}
	public boolean addrequisition(WmPurchaserequisition WmPurchaserequisition) {
		return requisitionDao.addrequisition(WmPurchaserequisition);
	}
	public boolean addprocusbook(List list) {
		return requisitionDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return requisitionDao.findorg(exhTypeId);
	}
	public Map findrequisition(String requisitiongid) {
		return requisitionDao.findrequisition(requisitiongid);
	}
	public boolean updateorg(AaOrg aaorg) {
		return requisitionDao.updateorg(aaorg);
	}
	public boolean deletebooks(String exhTypeId) {
		return requisitionDao.deletebooks(exhTypeId);
	}
	public List getrequisitionbookList(String exhTypeId) {
		return requisitionDao.getrequisitionbookList(exhTypeId);
	}
	public boolean deleterequisition(String orgclassid) {
		return  requisitionDao.deleterequisition(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.requisitionDao.findorgchild(orgclassid);
	}
	public void setrequisitionEnable(int enable, String id) {
		requisitionDao.setrequisitionEnable(enable,id);
	}
	public boolean addrequisitionc(List list) {
		return requisitionDao.addrequisitionc(list);
	}
	public boolean updaterequisitionc(List list) {
		return requisitionDao.updaterequisitionc(list);
	}
	public boolean updaterequisition(WmPurchaserequisition WmPurchaserequisition) {
		return requisitionDao.updaterequisition(WmPurchaserequisition);
	}
	public boolean updateymuser(YmUser YmUser) {
		return requisitionDao.updateymuser(YmUser);
	}
	public List getrequisitionlist(String purchaseRequisitionUid) {
		return requisitionDao.getrequisitionlist(purchaseRequisitionUid);
	}
}
