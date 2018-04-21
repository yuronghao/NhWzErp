package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="YM_BILLID")
public class YmBILLID implements Serializable{
	private static final long serialVersionUID = -5902230887627826255L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	//备注 
	@EmiColumn(name="notes" )
    private String notes;

	//单据类型编号
	@EmiColumn(name="billType" )
    private String billType;
	
	//随机生成的数字
	@EmiColumn(name="serialNum" )
    private int serialNum;
	
	//当前的年月
	@EmiColumn(name="preFix" )
    private String preFix;

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

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public int getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}

	public String getPreFix() {
		return preFix;
	}

	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}
	
	
}
