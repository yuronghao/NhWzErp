package com.emi.rm.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 角色
 */
@EmiTable(name = "RM_Role")
public class RM_Role implements Serializable{
	private static final long serialVersionUID = -2044715777992308535L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "roleCode")
	private String roleCode;  		//角色代码
	
	@EmiColumn(name = "roleName")
	private String roleName;  		//角色名称
	
	@EmiColumn(name = "isDelete",hasDefault=true)
	private Integer isDelete;	//是否删除 0：否 1：是
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}
