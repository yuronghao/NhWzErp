package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.SobDao;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.YmUser;

public class SobService {

	private SobDao sobDao;

	public SobDao getSobDao() {
		return sobDao;
	}

	public void setSobDao(SobDao sobDao) {
		this.sobDao = sobDao;
	}

	public List getClassifyList(String conditionSql) {
		return sobDao.getClassifyList(conditionSql);
	}
	
	public Map getorganizeInfo(String conditionSql) {
		return sobDao.getorganizeInfo(conditionSql);
	}
	
	public List getCategoryList(String conditionSql) {
		return sobDao.getCategoryList(conditionSql);
	}
	public boolean addorg(AaOrg aaorg) {
		return sobDao.addorg(aaorg);
	}
	public boolean addsob(MesWmAccountinginform MesWmAccountinginform) {
		return sobDao.addsob(MesWmAccountinginform);
	}
	public boolean addprocusbook(List list) {
		return sobDao.addprocusbook(list);
	}
	public Map findorg(String exhTypeId) {
		return sobDao.findorg(exhTypeId);
	}
	public Map findsob(String sobgid) {
		return sobDao.findsob(sobgid);
	}
	public boolean updateorg(AaOrg aaorg) {
		return sobDao.updateorg(aaorg);
	}
	public boolean deletebooks(String exhTypeId) {
		return sobDao.deletebooks(exhTypeId);
	}
	public List getsobbookList(String exhTypeId) {
		return sobDao.getsobbookList(exhTypeId);
	}
	public boolean deletesob(String orgclassid) {
		return  sobDao.deletesob(orgclassid);
	}
	public boolean findorgchild(String orgclassid){
		return this.sobDao.findorgchild(orgclassid);
	}
	public void setsobEnable(int enable, String id) {
		sobDao.setsobEnable(enable,id);
	}
	public boolean addymuser(YmUser YmUser) {
		return sobDao.addymuser(YmUser);
	}
	public boolean updatesob(MesWmAccountinginform MesWmAccountinginform) {
		return sobDao.updatesob(MesWmAccountinginform);
	}
	public boolean updateymuser(YmUser YmUser) {
		return sobDao.updateymuser(YmUser);
	}
	public PageBean getsoblist(int pageIndex, int pageSize,String conditionSql) {
		return sobDao.getsoblist(pageIndex, pageSize, conditionSql);
	}
	
	public List getsystemsetting(){
		return sobDao.getsystemsetting();
	}
	
	public boolean updatesystemsetting(String interfaceType,String interfaceAddress,String cacheserverIp,String cacheport,String printfile,String printmachine,String headCheckFlag,String bodyCheckFlag,String isReportExceedDis){
		return sobDao.updatesystemsetting(interfaceType,interfaceAddress,cacheserverIp,cacheport,printfile,printmachine,headCheckFlag,bodyCheckFlag,isReportExceedDis);
	}
}
