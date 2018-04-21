package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "QM_CheckBill")
public class QMCheckBill implements Serializable {

	private static final long serialVersionUID = 1112998033548553389L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "notes")
    private String notes;//备注

	@EmiColumn(name = "checkCode")
    private String checkCode;//检验单号

	@EmiColumn(name = "checkTypeCode")
    private String checkTypeCode;
	// ARR-采购检验,SUB-委外检验,PRO-产品检验,PCS-工序检验,PER-在库检验,ISS-发货检验,RET-退货检验,
	//SAM-留样检验,OTH-其他检验,IMP-进口检验,PSB-工序委外检验,EXP-出口发货检验,ERT-出口退货检验,
	//s 巡检
	//f 首检
	//r 抽检

	@EmiColumn(name = "checkDate")
    private Date checkDate;//检验日期

	@EmiColumn(name = "checkDptGid")
    private String checkDptGid;//检验部门
	
	@EmiColumn(name = "checkPersonGid")
	private String checkPersonGid;//检验人员gid
	
	@EmiColumn(name = "sobGid")
    private String sobGid;

	@EmiColumn(name = "orgGid")
    private String orgGid;

	
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

	public String getCheckTypeCode() {
		return checkTypeCode;
	}

	public void setCheckTypeCode(String checkTypeCode) {
		this.checkTypeCode = checkTypeCode;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckDptGid() {
		return checkDptGid;
	}

	public void setCheckDptGid(String checkDptGid) {
		this.checkDptGid = checkDptGid;
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

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getCheckPersonGid() {
		return checkPersonGid;
	}

	public void setCheckPersonGid(String checkPersonGid) {
		this.checkPersonGid = checkPersonGid;
	}

	
	

  
}