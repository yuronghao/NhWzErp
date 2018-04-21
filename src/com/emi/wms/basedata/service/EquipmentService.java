package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.EquipmentDao;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Equipment;

public class EquipmentService {

	private EquipmentDao equipmentDao;

	public EquipmentDao getEquipmentDao() {
		return equipmentDao;
	}

	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}

	public PageBean getequipmentList(int pageIndex, int pageSize,String conditionSql) {
		return equipmentDao.getequipmentList(pageIndex, pageSize,conditionSql);
	}
	
	public boolean addequipment(Equipment equipment) {
		return equipmentDao.addequipment(equipment);
	}
	public Equipment findequipment(String gid) {
		return equipmentDao.findequipment(gid);
	}
	public boolean updateequipment(Equipment equipment) {
		return equipmentDao.updateequipment(equipment);
	}
	public void deleteequipment(String gid) {
		//1、删除主表（假删除）
		equipmentDao.deleteequipment(gid);
	}
}
