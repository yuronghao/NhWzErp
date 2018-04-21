package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="RM_RoleData")
public class RmRoleData implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4100589375724731017L;


	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;


	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "roleId")
    private String roleId;

	@EmiColumn(name = "dataGid")
    private String dataGid;
	
	@EmiColumn(name = "dataType")
    private String dataType;
	
	@EmiColumn(name = "sobId")
    private String sobId;
	
	@EmiColumn(name = "orgId")
    private String orgId;

	
	
	
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDataGid() {
		return dataGid;
	}

	public void setDataGid(String dataGid) {
		this.dataGid = dataGid;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSobId() {
		return sobId;
	}

	public void setSobId(String sobId) {
		this.sobId = sobId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
	
	
	
	
	
	

	
}
