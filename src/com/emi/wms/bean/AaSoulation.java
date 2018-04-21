package com.emi.wms.bean;

import java.io.Serializable;
import java.util.List;


import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="AA_Soulation")
public class AaSoulation implements Serializable {

	private static final long serialVersionUID = 3061864213497097798L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//属性方案gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	//属性方案名称 
	@EmiColumn(name="souname" )
    private String souname;

	//属性方案类别 
	@EmiColumn(name="categorygid" )
    private String categorygid;

	//是否启用 
	@EmiColumn(name="flag" )
    private Integer flag;

	//组织ID 
	@EmiColumn(name="orgGid" )
    private String orgGid;

	//帐套ID 
	@EmiColumn(name="sobGid" )
    private String sobGid;
	//删除状态
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


    public String getSouname() {
        return souname;
    }


    public void setSouname(String souname) {
        this.souname = souname;
    }

    public String getCategorygid() {
        return categorygid;
    }

    public void setCategorygid(String categorygid) {
        this.categorygid = categorygid;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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