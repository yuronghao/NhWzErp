package com.emi.wms.servicedata.service;

import java.util.List;
import java.util.Map;

import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.CheckBillDao;

public class CheckBillService {

	private CheckBillDao checkbillDao;

	public CheckBillDao getCheckbillDao() {
		return checkbillDao;
	}

	public void setCheckbillDao(CheckBillDao checkbillDao) {
		this.checkbillDao = checkbillDao;
	}

	public List getClassifyList(String conditionSql) {
		return checkbillDao.getClassifyList(conditionSql);
	}
	
	public Map getorganizeInfo(String conditionSql) {
		return checkbillDao.getorganizeInfo(conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return checkbillDao.getCategoryList(conditionSql);
	}
	public boolean addorg(AaOrg aaorg) {
		return checkbillDao.addorg(aaorg);
	}
	public boolean addcheckbill(QMCheckBill qmcheckbill) {
		return checkbillDao.addcheckbill(qmcheckbill);
	}
	public boolean addcheckbillreason(QMCheckCReasonBill reason) {
		return checkbillDao.addcheckbillreason(reason);
	}
	public boolean addprocusbook(List list) {
		return checkbillDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return checkbillDao.findorg(exhTypeId);
	}
	public Map findcheckbill(String checkbillgid,String orgId,String sobId) {
		return checkbillDao.findcheckbill(checkbillgid,orgId,sobId);
	}
	public boolean updateorg(AaOrg aaorg) {
		return checkbillDao.updateorg(aaorg);
	}
	public boolean deletebooks(String exhTypeId) {
		return checkbillDao.deletebooks(exhTypeId);
	}
	public List getcheckbillbookList(String exhTypeId) {
		return checkbillDao.getcheckbillbookList(exhTypeId);
	}
	public boolean deletecheckbill(String orgclassid) {
		return  checkbillDao.deletecheckbill(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.checkbillDao.findorgchild(orgclassid);
	}
	public void setcheckbillEnable(int enable, String id) {
		checkbillDao.setcheckbillEnable(enable,id);
	}
	public boolean addcheckbillc(List list) {
		return checkbillDao.addcheckbillc(list);
	}
	public boolean updatecheckbillc(List list) {
		return checkbillDao.updatecheckbillc(list);
	}
	public boolean updatecheckbill(QMCheckBill qmcheckbill) {
		return checkbillDao.updatecheckbill(qmcheckbill);
	}
	public boolean updateymuser(YmUser YmUser) {
		return checkbillDao.updateymuser(YmUser);
	}
	public List getcheckbillclist(String purchasecheckbillUid) {
		return checkbillDao.getcheckbillclist(purchasecheckbillUid);
	}
	public PageBean getreasonlist(int pageIndex,int pageSize,String checkbillcgid) {
		return checkbillDao.getreasonlist(pageIndex,pageSize,checkbillcgid);
	}
	public List getaareasonlist() {
		return checkbillDao.getaareasonlist();
	}
	public PageBean getcheckbilllist(int pageIndex,int pageSize,String condition) {
		return checkbillDao.getcheckbilllist(pageIndex,pageSize,condition);
	}
	
	/**
	 * @category 获得生产订单工艺路线子表
	 *2016 2016年4月12日下午2:23:33
	 *MesWmProduceProcessroutec
	 *宋银海
	 */
	public MesWmProduceProcessroutec getMesWmProduceProcessroutec(String gid){
		String condition="gid='"+gid+"'";
		return checkbillDao.getMesWmProduceProcessroutec(condition);
	}
	
}
