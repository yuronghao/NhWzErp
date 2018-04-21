package com.emi.common.action;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
/*
 * 系统用户
 */
@EmiTable(name = "YM_User")
public class YmUserTest implements Serializable{
	private static final long serialVersionUID = 6772388831415628612L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	
	@EmiColumn(name = "beginTimes")
    private Date beginTimes;


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


	public Date getBeginTimes() {
		return beginTimes;
	}


	public void setBeginTimes(Date beginTimes) {
		this.beginTimes = beginTimes;
	}

    
}