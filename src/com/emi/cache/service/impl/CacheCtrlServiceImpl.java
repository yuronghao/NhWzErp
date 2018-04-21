package com.emi.cache.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.emi.cache.dao.CacheCtrldao;
import com.emi.cache.service.CacheCtrlService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.YmUser;


public class CacheCtrlServiceImpl implements CacheCtrlService {
	private CacheCtrldao cacheCtrldao;

	public CacheCtrldao getCacheCtrldao() {
		return cacheCtrldao;
	}

	public void setCacheCtrldao(CacheCtrldao cacheCtrldao) {
		this.cacheCtrldao = cacheCtrldao;
	}

	@Override
	//
	public List<AaGoods> setGoods() {
		return cacheCtrldao.setGoods();
	}

	@Override
	//
	public boolean addGoods(AaGoods goods) {
		return cacheCtrldao.addGoods(goods);
	}

	@Override
	//
	public boolean delGoods(String goodsGid) {
		return cacheCtrldao.delGoods(goodsGid);
	}

	@Override
	//
	public AaGoods getGoods(String goodsGid) {
		return cacheCtrldao.getGoods(goodsGid);
	}

	@Override
	//
	public List<AaGoods> getGoodsList(String[] goodsList) {
		return cacheCtrldao.getGoodsList(goodsList);
	}

	@Override
	public boolean delStock(String goodsGid) {
		return cacheCtrldao.delStock(goodsGid);
	}

	@Override
	public BigDecimal getStock(String goodsGid) {
		return cacheCtrldao.getStock(goodsGid);
	}

	@Override
	public List<AaDepartment> setDepartments() {
		return cacheCtrldao.setDepartments();
	}

	@Override
	public boolean addDepartment(AaDepartment department) {
		return cacheCtrldao.addDepartment(department);
	}

	@Override
	public boolean delDepartment(String departmentGid) {
		return cacheCtrldao.delDepartment(departmentGid);
	}

	@Override
	public AaDepartment getDepartment(String departmentGid) {
		return cacheCtrldao.getDepartment(departmentGid);
	}

	@Override
	public List<AaDepartment> getDepartmentList(String[] departmentList) {
		return cacheCtrldao.getDepartmentList(departmentList);
	}
//////////////////////////////////////////////////////////////
	@Override
	//
	public List<AaPerson> setPersons() {
		return cacheCtrldao.setPersons();
	}

	@Override
	//
	public boolean addPerson(AaPerson person) {
		return cacheCtrldao.addPerson(person);
	}

	@Override
	//
	public boolean delPerson(String personGid) {
		return cacheCtrldao.delPerson(personGid);
	}

	@Override
	//
	public AaPerson getPerson(String personGid) {
		return cacheCtrldao.getPerson(personGid);
	}

	@Override
	//
	public List<AaPerson> getPersonList(String[] personList) {
		return cacheCtrldao.getPersonList(personList);
	}

	@Override
	public List<AaWarehouse> setWareHouses() {
		return cacheCtrldao.setWareHouses();
	}

	@Override
	public boolean delWareHouse(String wareHouseGid) {
		return cacheCtrldao.delWareHouse(wareHouseGid);
	}

	@Override
	public AaWarehouse getWareHouse(String wareHouseGid) {
		return cacheCtrldao.getWareHouse(wareHouseGid);
	}

	@Override
	public List<AaWarehouse> getWareHouseList(String[] wareHouseList) {
		return cacheCtrldao.getWareHouseList(wareHouseList);
	}

	@Override
	public List<AaGoodsallocation> setGoodsAllocation() {
		return cacheCtrldao.setGoodsAllocation();
	}

	@Override
	public boolean delGoodsAllocation(String goodsAllocationGid) {
		return cacheCtrldao.delGoodsAllocation(goodsAllocationGid);
	}

	@Override
	public AaGoodsallocation getGoodsAllocation(String goodsAllocationGid) {
		return cacheCtrldao.getGoodsAllocation(goodsAllocationGid);
	}

	@Override
	public List<AaGoodsallocation> getGoodsAllocationList(
			String[] goodsAllocationList) {
		return cacheCtrldao.getGoodsAllocationList(goodsAllocationList);
	}

	@Override
	public List<AaProviderCustomer> setProviderCustomer() {
		return cacheCtrldao.setProviderCustomer();
	}

	@Override
	public boolean delProviderCustomer(String providerCustomerGid) {
		return cacheCtrldao.delProviderCustomer(providerCustomerGid);
	}

	@Override
	public AaProviderCustomer getProviderCustomer(String providerCustomerGid) {
		return cacheCtrldao.getProviderCustomer(providerCustomerGid);
	}

	@Override
	public List<AaProviderCustomer> getProviderCustomerList(
			String[] providerCustomerList) {
		return cacheCtrldao.getProviderCustomerList(providerCustomerList);
	}

	@Override
	public boolean delMES_WorkCenter(String workCenterGid) {
		return cacheCtrldao.delMES_WorkCenter(workCenterGid);
	}

	@Override
	public MesAaWorkcenter getworkCenter(String workCenterGid) {
		return cacheCtrldao.getworkCenter(workCenterGid);
	}

	@Override
	public List<MesAaWorkcenter> getworkCenterList(String[] workCenterList) {
		return cacheCtrldao.getworkCenterList(workCenterList);
	}

	@Override
	public MesAaStation getMesAaStation(String mesAaStationGid) {
		return cacheCtrldao.getMesAaStation(mesAaStationGid);
	}
	
	@Override
	public AaGroup getAaGroup(String aaGroupGid) {
		return cacheCtrldao.getAaGroup(aaGroupGid);
	}
	
	
	/**
	 * @category 获得类别
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Classify getClassify(String classifyGid) {
		return cacheCtrldao.getClassify(classifyGid);
	}
	
	/**
	 * @category 获得单位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Unit getUnit(String unitGid) {
		return cacheCtrldao.getUnit(unitGid);
	}
	
	
	@Override
	public MesWmStandardprocess getMESStandardProcess(String standardProcessGid) {
		return cacheCtrldao.getMESStandardProcess(standardProcessGid);
	}

	@Override
	public List<MesWmStandardprocess> getMESStandardProcessList(
			String[] standardProcessList) {
		return cacheCtrldao.getMESStandardProcessList(standardProcessList);
	}

	@Override
	public boolean setDepartmentList() {
		return cacheCtrldao.setDepartmentList();
	}

	@Override
	public List<AaDepartment> getDepartmentList() {
		return cacheCtrldao.getDepartmentList();
	}

	@Override
	//
	public boolean addUser(YmUser user) {
		return cacheCtrldao.addUser(user);
	}

	@Override
	//
	public boolean delUser(String userId) {
		return cacheCtrldao.delUser(userId);
	}

	@Override
	//
	public List<YmUser> setUser() {
		return cacheCtrldao.setUser();
	}

	@Override
	//
	public YmUser getUser(String userId) {
		return cacheCtrldao.getUser(userId);
	}

	@Override
	//
	public List<YmUser> getUserList(String[] userList) {
		return cacheCtrldao.getUserList(userList);
	}

	@Override
	public void setAllocationStockS() {
		cacheCtrldao.setAllocationStockS();
	}

	@Override
	public boolean addAllocationStock(String goodsUid) {
		return cacheCtrldao.addAllocationStock(goodsUid);
	}

	@Override
	public boolean delAllocationStock(String goodsUid) {
		return cacheCtrldao.delAllocationStock(goodsUid);
	}

	@Override
	public List<WmAllocationstock> getAllocationStock(String goodsUid) {
		return cacheCtrldao.getAllocationStock(goodsUid);
	}

	@Override
	public List<WmAllocationstock> getAllocationStockList(String[] goodsUid) {
		return cacheCtrldao.getAllocationStockList(goodsUid);
	}

	@Override
	public void maintenanceStock(int i, String goodsUid) {
		// i=0 增加，修改 =1删除
		switch (i) {
		case 0:
			// cacheCtrldao.setStocksById(goodsUid);
			cacheCtrldao.setAllocationStockBygoodsUid(goodsUid);
			break;
		case 1:
			cacheCtrldao.delStock(goodsUid);
			cacheCtrldao.delAllocationStock(goodsUid);
			break;
		default:
			break;
		}

	}

	@Override
	public void maintenanceGoods(int i, String goodsUid) {
		// i=0增加，修改 =1 删除
		switch (i) {
		case 0:
			cacheCtrldao.setGoodsSortNameByUid(goodsUid);
			cacheCtrldao.setAllocationStockBygoodsUid(goodsUid);
			break;
		case 1:
			cacheCtrldao.delGoods(goodsUid);
			cacheCtrldao.delAllocationStock(goodsUid);
			break;
		default:
			break;
		}
	}

	@Override
	public void maintenanceWareHouse(int i, String wareHouseGid) {
		// i=0增加，修改 =1 删除
		switch (i) {
		case 0:
			cacheCtrldao.setWareHousesByWhUid(wareHouseGid);
			cacheCtrldao.setAllocationStockByWhUid(wareHouseGid);
			break;
		case 1:
			cacheCtrldao.delWareHouse(wareHouseGid);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean addWareHouse(AaWarehouse house) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGoodsAllocation(AaGoodsallocation goodsAllocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProviderCustomer(AaProviderCustomer providerCustomer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addWorkCenter(MesAaWorkcenter workCenter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Equipment> setEquipment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delEquipment(String equipmentGid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addStandardProcess(MesWmStandardprocess standardProcess) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAllocationStock(String goodsUid,
			List<WmAllocationstock> wmas) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MesAaWorkcenter> setMESWorkCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Equipment getMESEquipment(String equipmentGid) {
		return cacheCtrldao.getMESEquipment(equipmentGid);
	}
	
	@Override
	public Mould getMould(String mouldGid) {
		return cacheCtrldao.getMould(mouldGid);
	}

	@Override
	public List<Equipment> getEquipmentList(String[] equipmentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MesWmStandardprocess> setStandardProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delMES_WM_StandardProcess(String standardProcessGid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Equipment> setMould() {
		// TODO Auto-generated method stub
		return null;
	}





}
