package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 组
 */
@EmiTable(name = "AA_Reason")
public class AaReason implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "reasoncode")
    private String reasoncode;		//编码
	
	@EmiColumn(name = "reasonname")
    private String reasonname;		//条码
	
	@EmiColumn(name = "reasontype")
    private Integer reasontype;	//名称
	
	@EmiColumn(name = "isDelete")
    private Integer isDelete;	//删除

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

	public String getReasoncode() {
		return reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public String getReasonname() {
		return reasonname;
	}

	public void setReasonname(String reasonname) {
		this.reasonname = reasonname;
	}

	public Integer getReasontype() {
		return reasontype;
	}

	public void setReasontype(Integer reasontype) {
		this.reasontype = reasontype;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}