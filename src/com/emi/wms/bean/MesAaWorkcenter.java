package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_AA_WorkCenter")
public class MesAaWorkcenter implements Serializable{
 

	private static final long serialVersionUID = -6058801400376146277L;

	@EmiColumn(name = "pk", increment=true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "wccode")
    private String wccode;//工作中心编码

	@EmiColumn(name = "wcname")
    private String wcname;//工作中心编码

	@EmiColumn(name = "depUid")
    private String depUid;//部门Uid

    @EmiColumn(name = "sobgid")
    private String sobgid;

    @EmiColumn(name = "orggid")
    private String orggid;
    
    @EmiColumn(name = "barcode")
    private String barcode;
    
    @EmiColumn(name = "isDelete")
    private Integer isDelete;//是否删除
    
    private String depCode;	//所属部门编码
    private String depName;	//所属部门名称

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

	public String getWccode() {
		return wccode;
	}

	public void setWccode(String wccode) {
		this.wccode = wccode;
	}

	public String getWcname() {
		return wcname;
	}

	public void setWcname(String wcname) {
		this.wcname = wcname;
	}

	public String getDepUid() {
		return depUid;
	}

	public void setDepUid(String depUid) {
		this.depUid = depUid;
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

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
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


   
}