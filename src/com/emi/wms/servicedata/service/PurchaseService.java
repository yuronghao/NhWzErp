package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.WmProcureorder;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.PurchaseDao;

public class PurchaseService {

	private PurchaseDao purchaseDao;

	public PurchaseDao getPurchaseDao() {
		return purchaseDao;
	}

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public List getClassifyList(String conditionSql) {
		return purchaseDao.getClassifyList(conditionSql);
	}
	
	public Map getorganizeInfo(String conditionSql) {
		return purchaseDao.getorganizeInfo(conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return purchaseDao.getCategoryList(conditionSql);
	}
	public boolean addorg(AaOrg aaorg) {
		return purchaseDao.addorg(aaorg);
	}
	public boolean addpurchase(WmProcureorder WmProcureorder) {
		return purchaseDao.addpurchase(WmProcureorder);
	}
	public boolean addprocusbook(List list) {
		return purchaseDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return purchaseDao.findorg(exhTypeId);
	}
	public Map findpurchase(String purchasegid) {
		return purchaseDao.findpurchase(purchasegid);
	}
	public boolean updateorg(AaOrg aaorg) {
		return purchaseDao.updateorg(aaorg);
	}
	public boolean deletebooks(String exhTypeId) {
		return purchaseDao.deletebooks(exhTypeId);
	}
	public List getpurchasebookList(String exhTypeId) {
		return purchaseDao.getpurchasebookList(exhTypeId);
	}
	public boolean deletepurchase(String orgclassid) {
		return  purchaseDao.deletepurchase(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.purchaseDao.findorgchild(orgclassid);
	}
	public void setpurchaseEnable(int enable, String id) {
		purchaseDao.setpurchaseEnable(enable,id);
	}
	public boolean addpurchasec(List list) {
		return purchaseDao.addpurchasec(list);
	}
	public boolean updatepurchasec(List list) {
		return purchaseDao.updatepurchasec(list);
	}
	public boolean updatepurchase(WmProcureorder WmProcureorder) {
		return purchaseDao.updatepurchase(WmProcureorder);
	}
	public boolean updateymuser(YmUser YmUser) {
		return purchaseDao.updateymuser(YmUser);
	}
	public List getpurchaselist(String purchasepurchaseUid) {
		return purchaseDao.getpurchaselist(purchasepurchaseUid);
	}

    public PageBean getAddpurchaseList(int pageIndex, int pageSize, String condition) {
		return purchaseDao.getOtherWarehouseList(pageIndex, pageSize,
				condition);
    }


	public List getpurchaseTypelist() {
		return purchaseDao.getpurchaseTypelist();
	}
}
