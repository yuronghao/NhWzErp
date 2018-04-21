package com.emi.wms.processDesign.bean;

import java.util.Date;
import java.util.List;

public class BomBomReq {
	private Integer bomType;
	private Integer version;
	private String versionDesc;
	private Date versionEffDate;
	private Date versionEndDate;
	private Date createTime;
	private String createUser;
	private Date modifyTime;
	private String modifyUser;
	private Integer status;
	private String relsUser;
	private Date relsTime;
	
	private String lugGoodsId;//
	
	private BomParentReq bomParent;
	private List<BomOpcomponentReq> bomOpcomponentList;
	public Integer getBomType() {
		return bomType;
	}
	public void setBomType(Integer bomType) {
		this.bomType = bomType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public Date getVersionEffDate() {
		return versionEffDate;
	}
	public void setVersionEffDate(Date versionEffDate) {
		this.versionEffDate = versionEffDate;
	}
	public Date getVersionEndDate() {
		return versionEndDate;
	}
	public void setVersionEndDate(Date versionEndDate) {
		this.versionEndDate = versionEndDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRelsUser() {
		return relsUser;
	}
	public void setRelsUser(String relsUser) {
		this.relsUser = relsUser;
	}
	public Date getRelsTime() {
		return relsTime;
	}
	public void setRelsTime(Date relsTime) {
		this.relsTime = relsTime;
	}
	public BomParentReq getBomParent() {
		return bomParent;
	}
	public void setBomParent(BomParentReq bomParent) {
		this.bomParent = bomParent;
	}
	public String getLugGoodsId() {
		return lugGoodsId;
	}
	public void setLugGoodsId(String lugGoodsId) {
		this.lugGoodsId = lugGoodsId;
	}
	public List<BomOpcomponentReq> getBomOpcomponentList() {
		return bomOpcomponentList;
	}
	public void setBomOpcomponentList(List<BomOpcomponentReq> bomOpcomponentList) {
		this.bomOpcomponentList = bomOpcomponentList;
	}
	
	
}
