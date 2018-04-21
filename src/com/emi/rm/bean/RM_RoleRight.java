package com.emi.rm.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 角色权限
 */
@EmiTable(name = "RM_RoleRight")
public class RM_RoleRight implements Serializable{
	private static final long serialVersionUID = -6427798775292412786L;
	
	@EmiColumn(name = "gid", ID = true)
	private String gid ;
	
	@EmiColumn(name = "roleId")
	private String roleId ;  //角色id
	
	@EmiColumn(name = "rightId")
	private String rightId ; //权限id
	
	@EmiColumn(name = "useFuns")
	private Integer useFuns; //使用功能权值和 1：查看 2：新增 4：修改 8：删除 (使用位运算进行判断含有哪些功能)
	
	@EmiColumn(name = "notes")
	private String notes ;   //备注
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
	public String getRightId() {
		return rightId;
	}
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getUseFuns() {
		return useFuns;
	}
	public void setUseFuns(Integer useFuns) {
		this.useFuns = useFuns;
	}
	
}
