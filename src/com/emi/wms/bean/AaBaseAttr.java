package com.emi.wms.bean;

import java.io.Serializable;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="AA_BaseAttr")
public class AaBaseAttr implements Serializable {

	private static final long serialVersionUID = 1385516960069257339L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	
	//属性gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	//属性名称
	@EmiColumn(name="name" )
    private String name;
	
	//组织ID 
	@EmiColumn(name="orgGid" )
    private String orgGid;

	//帐套ID 
	@EmiColumn(name="sobGid" )
    private String sobGid;
	
	//是否删除
	@EmiColumn(name="isdel" )
    private int isdel;

	List<AaBaseAttrDetail> detailList;
	
	private int checkFlag;
	
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

	public int getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(int checkFlag) {
		this.checkFlag = checkFlag;
	}

	public List<AaBaseAttrDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<AaBaseAttrDetail> detailList) {
		this.detailList = detailList;
	}



   
}