package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/** 
* @author  作者 :张向龙    E-mail: 
* @date 创建时间：2017年6月7日 上午9:11:32 
* @version 1.0  
*/
@EmiTable(name="MES_WM_CostItemSourceSet")
public class MESWMCostItemSourceSet implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5884390133266915808L;

	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
    private String gid;
	
	@EmiColumn(name="costItemGid")
    private String costItemGid;
	
	@EmiColumn(name="notes")
    private String notes;
	
	@EmiColumn(name="orgGid")
    private String orgGid;
	
	@EmiColumn(name="sobGid")
    private String sobGid;
	
	/*@EmiColumn(name="isDelete")
    private Integer isDelete;
	*/
	@EmiColumn(name="subjectCode")
    private String subjectCode;
	
	@EmiColumn(name="depGid")
    private String depGid;
	
	@EmiColumn(name="rdStyleGid")
    private String rdStyleGid;
	
	@EmiColumn(name="sourceMode")
    private String sourceMode;

	 private String rdStyleName;
		
	 private String depName;
	 
	 private String costItemName;
	 
	 private String apName;
	
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

	public String getCostItemGid() {
		return costItemGid;
	}

	public void setCostItemGid(String costItemGid) {
		this.costItemGid = costItemGid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getDepGid() {
		return depGid;
	}

	public void setDepGid(String depGid) {
		this.depGid = depGid;
	}

	public String getRdStyleGid() {
		return rdStyleGid;
	}

	public void setRdStyleGid(String rdStyleGid) {
		this.rdStyleGid = rdStyleGid;
	}

	/*public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}*/

	public String getSourceMode() {
		return sourceMode;
	}

	public void setSourceMode(String sourceMode) {
		this.sourceMode = sourceMode;
	}

	public String getRdStyleName() {
		return rdStyleName;
	}

	public void setRdStyleName(String rdStyleName) {
		this.rdStyleName = rdStyleName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	

	public String getCostItemName() {
		return costItemName;
	}

	public void setCostItemName(String costItemName) {
		this.costItemName = costItemName;
	}
	
	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
