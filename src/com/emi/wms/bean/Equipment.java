package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="equipment")
public class Equipment implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242188817321652795L;

	@EmiColumn(increment=true,name="pk")
	private Integer pk;

	@EmiColumn(ID=true,name="gid")
    private String gid;

	@EmiColumn(name="equipmentcode")
    private String equipmentcode;

	@EmiColumn(name="equipmentname")
    private String equipmentname;

	@EmiColumn(name="equipmentstyle")
    private String equipmentstyle;

	@EmiColumn(name="equipmentspe")
    private String equipmentspe;

	@EmiColumn(name="department")
    private String department;

	@EmiColumn(name="workcenter")
    private String workcenter;

	@EmiColumn(name="position")
    private String position;

	@EmiColumn(name="sobgid")
    private String sobgid;

	@EmiColumn(name="orggid")
    private String orggid;

	@EmiColumn(name="barcode")
    private String barcode;
	
	@EmiColumn(name="isDelete")
    private Integer isDelete;
	
	@EmiColumn(name="equipstatus")
    private Integer equipstatus;
	
	@EmiColumn(name="SBL")
    private String SBL;
	
	private String aadepName;

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

	public String getEquipmentcode() {
		return equipmentcode;
	}

	public void setEquipmentcode(String equipmentcode) {
		this.equipmentcode = equipmentcode;
	}

	public String getEquipmentname() {
		return equipmentname;
	}

	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}

	public String getEquipmentstyle() {
		return equipmentstyle;
	}

	public void setEquipmentstyle(String equipmentstyle) {
		this.equipmentstyle = equipmentstyle;
	}

	public String getEquipmentspe() {
		return equipmentspe;
	}

	public void setEquipmentspe(String equipmentspe) {
		this.equipmentspe = equipmentspe;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSobgid() {
		return sobgid;
	}

	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}

	public String getOrggid() {
		return orggid;
	}

	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getEquipstatus() {
		return equipstatus;
	}

	public void setEquipstatus(Integer equipstatus) {
		this.equipstatus = equipstatus;
	}

	public String getAadepName() {
		return aadepName;
	}

	public void setAadepName(String aadepName) {
		this.aadepName = aadepName;
	}

	public String getSBL() {
		return SBL;
	}

	public void setSBL(String sBL) {
		SBL = sBL;
	}
	
	
  
}