package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 组
 */
@EmiTable(name = "AA_Group")
public class AaGroup implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "code")
    private String code;		//编码
	
	@EmiColumn(name = "barcode")
    private String barcode;		//条码
	
	@EmiColumn(name = "groupname")
    private String groupname;	//名称
	
	@EmiColumn(name = "sobGid")
	private String sobGid;		//帐套id
	
	@EmiColumn(name = "orgGid")
	private String orgGid;		//组织id
	
	@EmiColumn(name = "isDelete")
	private Integer isDelete;		//是否删除
	
	@EmiColumn(name = "workcenterId")
	private String workcenterId;		//工作中心id
	
	private String wcname;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getWorkcenterId() {
		return workcenterId;
	}

	public void setWorkcenterId(String workcenterId) {
		this.workcenterId = workcenterId;
	}

	public String getWcname() {
		return wcname;
	}

	public void setWcname(String wcname) {
		this.wcname = wcname;
	}




    
}