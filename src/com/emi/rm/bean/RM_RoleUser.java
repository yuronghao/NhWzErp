package com.emi.rm.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "RM_RoleUser")
public class RM_RoleUser implements Serializable{
	private static final long serialVersionUID = 6326750451647303729L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "roleId")
	private String roleId;			//角色id
	
	@EmiColumn(name = "userId")
	private String userId;			//用户id

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
