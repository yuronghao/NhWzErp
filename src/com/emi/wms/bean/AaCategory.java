package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="AA_Category")
public class AaCategory implements Serializable {

	private static final long serialVersionUID = 3371548059843146456L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	
	//属性gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	//属性类别名称 
	@EmiColumn(name="name" )
    private String name;
	
	//组织ID 
	@EmiColumn(name="orgGid" )
    private String orgGid;

	//帐套ID 
	@EmiColumn(name="sobGid" )
    private String sobGid;

	//帐套ID
	@EmiColumn(name="isdel" )
    private int isdel;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}



	
	
}
