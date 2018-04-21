package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="AA_BaseAttrDetail")
public class AaBaseAttrDetail implements Serializable {
	private static final long serialVersionUID = -3091032279405532951L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	
	//gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	//属性值名称 
	@EmiColumn(name="name" )
    private String name;


	//属性gid
	@EmiColumn(name="base_attr_Uid" )
    private String base_attr_Uid;
	//是否删除
	@EmiColumn(name="isdel" )
    private int isdel;
	

	private int checkFlag;
	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getBase_attr_Uid() {
		return base_attr_Uid;
	}

	public void setBase_attr_Uid(String base_attr_Uid) {
		this.base_attr_Uid = base_attr_Uid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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



   
}