package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.BasicSettingDao;
import com.emi.wms.basedata.dao.MouldDao;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.servicedata.dao.WareHouseDao;

public class MouldService {

	private MouldDao mouldDao;
	
	private BasicSettingDao basicSettingDao;
	private WareHouseDao wareHouseDao;
	private CacheCtrlService cacheCtrlService;
	
	public void setBasicSettingDao(BasicSettingDao basicSettingDao) {
		this.basicSettingDao = basicSettingDao;
	}
	
	public void setWareHouseDao(WareHouseDao wareHouseDao) {
		this.wareHouseDao = wareHouseDao;
	}
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}


	public MouldDao getmouldDao() {
		return mouldDao;
	}

	public void setmouldDao(MouldDao mouldDao) {
		this.mouldDao = mouldDao;
	}

	public PageBean getmouldList(int pageIndex, int pageSize,String conditionSql) {
		return mouldDao.getmouldList(pageIndex, pageSize,conditionSql);
	}
	
	public boolean addmould(Mould mould) {
		return mouldDao.addmould(mould);
	}
	public Mould findmould(String gid) {
		return mouldDao.findmould(gid);
	}
	public boolean updatemould(Mould mould) {
		return mouldDao.updatemould(mould);
	}
	public void deletemould(String gid) {
		//1、删除主表（假删除）
		mouldDao.deletemould(gid);
	}
	
	public PageBean getmouldListDatils(int pageIndex, int pageSize,String classifyGid,String conditionSql){
		 
		return mouldDao.selectMouldListDatils(pageIndex, pageSize,classifyGid,conditionSql);
	}

	public List getClassifyList() {
		return mouldDao.getClassifyList();
	}
	
	public AaProviderCustomer selectCustomer(String cutomerGid){
		
		return mouldDao.selectCustomer(cutomerGid);
	}
	
	public AaProviderCustomer selectCurrentDeptGid(String providerGid){
		
		return mouldDao.selectCurrentDept(providerGid);
	}
	
	public AaDepartment selectDepartment(String currentDeptGid){
		
		return mouldDao.selectDepartment(currentDeptGid);
	}
	
	public Classify selectMouldStyle(String mouldStyleId){
		
		return mouldDao.selectMouldStyle(mouldStyleId);
	}
	
}
