package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表-
 */
@EmiTable(name = "MES_WM_ProcessRouteCEquipment")
public class MesWmProcessRouteCEquipment implements Serializable{
	private static final long serialVersionUID = 3851866667767024700L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "equipmentGid")
	private String equipmentGid;			//设备id
	
	@EmiColumn(name = "routeCGid")
	private String routeCGid;		//工艺路线子表id

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getRouteCGid() {
		return routeCGid;
	}

	public void setRouteCGid(String routeCGid) {
		this.routeCGid = routeCGid;
	}

	public String getEquipmentGid() {
		return equipmentGid;
	}

	public void setEquipmentGid(String equipmentGid) {
		this.equipmentGid = equipmentGid;
	}
	
}
