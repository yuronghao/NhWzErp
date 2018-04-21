package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Equipment;

public class EquipmentDao extends BaseDao {

	public PageBean getequipmentList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("aadepName", "Equipment.aadepName");
		String sql = "select "+CommonUtil.colsFromBean(Equipment.class,"Equipment")+",aadepartment.depName aadepName FROM equipment Equipment left join AA_Department aadepartment on aadepartment.gid = Equipment.department where 1=1 and Equipment.isDelete=0 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Equipment.class,match,"");
	}
	
	public boolean addequipment(Equipment equipment) {
		return this.emiInsert(equipment);
	}
	
	public Equipment findequipment(String gid) {
		return  (Equipment)this.emiFind(gid, Equipment.class);
	}
	
	public boolean updateequipment(Equipment equipment) {
		return this.emiUpdate(equipment);
	}

	public void deleteequipment(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update equipment set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}
}
