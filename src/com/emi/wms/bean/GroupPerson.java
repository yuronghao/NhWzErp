package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 组
 */
@EmiTable(name = "groupperson")
public class GroupPerson implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;
	@EmiColumn(name = "gid",ID = true)
	private String gid;
	
	@EmiColumn(name = "groupgid")
    private String groupgid;		//组gid
	
	@EmiColumn(name = "persongid")
    private String persongid;	//人员gid

	public String getGroupgid() {
		return groupgid;
	}

	public void setGroupgid(String groupgid) {
		this.groupgid = groupgid;
	}

	public String getPersongid() {
		return persongid;
	}

	public void setPersongid(String persongid) {
		this.persongid = persongid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	

    
}