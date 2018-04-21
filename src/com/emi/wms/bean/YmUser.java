package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
/*
 * 系统用户
 */
@EmiTable(name = "YM_User")
public class YmUser implements Serializable{
	private static final long serialVersionUID = 6772388831415628612L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	/**
	 * 备注
	 */
	@EmiColumn(name = "notes")
    private String notes;

	/**
	 * 用户编码
	 */
	@EmiColumn(name = "userCode")
    private String userCode;

	/**
	 * 用户名称
	 */
	@EmiColumn(name = "userName")
    private String userName;

	/**
	 * 用户密码
	 */
	@EmiColumn(name = "passWord")
    private String passWord;

	/**
	 * 1、停用，0或Null、启用
	 */
	@EmiColumn(name = "pause")
    private Integer pause;

	/**
	 * 生效时间
	 */
	@EmiColumn(name = "beginTimes")
    private Date beginTimes;

	/**
	 * 失效时间
	 */
	@EmiColumn(name = "endTimes")
    private Date endTimes;

	/**
	 * 是否管理员
	 */
	@EmiColumn(name = "isAdmin")
    private Integer isAdmin;

	/**
	 * 是否用户
	 */
	@EmiColumn(name = "isUser")
    private Integer isUser;

	/**
	 * 帐套编码
	 */

	@EmiColumn(name = "sobgid")
    private String sobgid;

	private String sobName;
	/**
	 * 组织编码
	 */

	@EmiColumn(name = "orggid")
    private String orggid;

	private String orgName;
	/**
	 * 是否启用
	 */
	@EmiColumn(name = "isuse")
    private Integer isuse;

	/**
	 * 是否已删除
	 */
	@EmiColumn(name = "isDelete",hasDefault=true)
    private Integer isDelete;
	
	/**
	 * 最后一次登录时间
	 */
	@EmiColumn(name = "lastLoginTime")
	private Date lastLoginTime;

    public String getSobName() {
		return sobName;
	}

	public void setSobName(String sobName) {
		this.sobName = sobName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


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


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Integer getPause() {
		return pause;
	}

	public void setPause(Integer pause) {
		this.pause = pause;
	}

	public Date getBeginTimes() {
		return beginTimes;
	}

	public void setBeginTimes(Date beginTimes) {
		this.beginTimes = beginTimes;
	}

	public Date getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsUser() {
		return isUser;
	}

	public void setIsUser(Integer isUser) {
		this.isUser = isUser;
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

	public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	
	
	
    
}