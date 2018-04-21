package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;


@EmiTable(name="mould")
public class Mould implements Serializable{
	
	private static final long serialVersionUID = -8554013260875146819L;

	@EmiColumn(increment=true,name="pk")
	private Integer pk;

	@EmiColumn(ID=true,name="gid")
    private String gid;

	@EmiColumn(name="mouldcode")
    private String mouldcode;

	@EmiColumn(name="mouldname")
    private String mouldname;

	@EmiColumn(name="mouldstyle")
    private String mouldstyle;

	@EmiColumn(name="position")
    private String position;

	@EmiColumn(name="sobgid")
    private String sobgid;

	@EmiColumn(name="orggid")
    private String orggid;

	@EmiColumn(name="barcode")
    private String barcode;
	
	@EmiColumn(name="isDelete")
    private Integer isDelete;
	
	@EmiColumn(name="mouldstatus")
    private Integer mouldstatus;

	@EmiColumn(name="mouldBeginTime")
	private Timestamp mouldBeginTime;
	
	@EmiColumn(name="texture")
	private String texture;
	
	@EmiColumn(name="size")
	private String size;
	
	@EmiColumn(name="cost")
	private BigDecimal cost;
	
	@EmiColumn(name="processingUnit")
	private String processingUnit;
	
	@EmiColumn(name="mouldScrapTime")
	private Timestamp mouldScrapTime;
	
	@EmiColumn(name="mouldScrapReason")
	private String mouldScrapReason;
	
	@EmiColumn(name="currentDeptGid")
	private String currentDeptGid;
	
	@EmiColumn(name="partNumber")
	private String partNumber;
	
	@EmiColumn(name="partName")
	private String partName;
	
	@EmiColumn(name="multimodeOrder")
	private Integer multimodeOrder;
	
	@EmiColumn(name="mouldRatio")
	private String mouldRatio;
	
	@EmiColumn(name="cavity")
	private String cavity;
	
	@EmiColumn(name="dataCode")
	private String dataCode;
	
	@EmiColumn(name="fileLocation")
	private String fileLocation;
	
	@EmiColumn(name="placingTooling")
	private String placingTooling;
	
	@EmiColumn(name="preTooling")
	private String preTooling;
	
	@EmiColumn(name="mouldFlow")
	private String mouldFlow;
	
	@EmiColumn(name="storageTime")
	private Timestamp storageTime;
	
	@EmiColumn(name="life")
	private Integer life;
	
	@EmiColumn(name="addlife")
	private Integer addlife;
	
	@EmiColumn(name="usedlife")
	private Integer usedlife;
	
	@EmiColumn(name="canuselife")
	private Integer canuselife;
	
	@EmiColumn(name="outTime")
	private Timestamp outTime;
	
	@EmiColumn(name="cutomerGid")
	private String cutomerGid;
	
	@EmiColumn(name="providerGid")
	private String providerGid;
	
	@EmiColumn(name="openCost")
	private BigDecimal openCost;
	
	@EmiColumn(name="isreturn")
	private Integer isreturn;
	
	@EmiColumn(name="returnTime")
	private Timestamp returnTime;
	
	@EmiColumn(name="isShareOrver")
	private Integer isShareOrver;
	
	@EmiColumn(name="shareOrverTime")
	private Timestamp shareOrverTime;
	
	@EmiColumn(name="notes")
	private String notes;
	
	@EmiColumn(name="cdefine1")
	private String cdefine1;
	
	private String depName;
	
	private String customer;
	
	private String provider;
	
	private String mouldStyleName;
	
	private String classificationName;
	
	
	@EmiColumn(name="createUser")
	private String createUser;
	
	private String createName;
	
	@EmiColumn(name="createDate")
	private Timestamp createDate;
	
	@EmiColumn(name="modifyUser")
	private String modifyUser;
	
	private String modifyName;
	
	@EmiColumn(name="modifyDate")
	private Timestamp modifyDate;
	
	
	@EmiColumn(name="supplyunit")
	private String supplyunit;
	
	
	
	public String getSupplyunit() {
		return supplyunit;
	}

	public void setSupplyunit(String supplyunit) {
		this.supplyunit = supplyunit;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public String getMouldStyleName() {
		return mouldStyleName;
	}

	public void setMouldStyleName(String mouldStyleName) {
		this.mouldStyleName = mouldStyleName;
	}

	public Timestamp getMouldBeginTime() {
		return mouldBeginTime;
	}

	public void setMouldBeginTime(Timestamp mouldBeginTime) {
		this.mouldBeginTime = mouldBeginTime;
	}

	public Timestamp getMouldScrapTime() {
		return mouldScrapTime;
	}

	public void setMouldScrapTime(Timestamp mouldScrapTime) {
		this.mouldScrapTime = mouldScrapTime;
	}

	public Timestamp getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Timestamp storageTime) {
		this.storageTime = storageTime;
	}

	public Timestamp getOutTime() {
		return outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public Timestamp getShareOrverTime() {
		return shareOrverTime;
	}

	public void setShareOrverTime(Timestamp shareOrverTime) {
		this.shareOrverTime = shareOrverTime;
	}

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

	public String getMouldcode() {
		return mouldcode;
	}

	public void setMouldcode(String mouldcode) {
		this.mouldcode = mouldcode;
	}

	public String getMouldname() {
		return mouldname;
	}

	public void setMouldname(String mouldname) {
		this.mouldname = mouldname;
	}

	public String getMouldstyle() {
		return mouldstyle;
	}

	public void setMouldstyle(String mouldstyle) {
		this.mouldstyle = mouldstyle;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSobgid() {
		return sobgid;
	}

	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}

	public String getOrggid() {
		return orggid;
	}

	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getMouldstatus() {
		return mouldstatus;
	}

	public void setMouldstatus(Integer mouldstatus) {
		this.mouldstatus = mouldstatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getProcessingUnit() {
		return processingUnit;
	}

	public void setProcessingUnit(String processingUnit) {
		this.processingUnit = processingUnit;
	}

	public String getMouldScrapReason() {
		return mouldScrapReason;
	}

	public void setMouldScrapReason(String mouldScrapReason) {
		this.mouldScrapReason = mouldScrapReason;
	}

	public String getCurrentDeptGid() {
		return currentDeptGid;
	}

	public void setCurrentDeptGid(String currentDeptGid) {
		this.currentDeptGid = currentDeptGid;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getMultimodeOrder() {
		return multimodeOrder;
	}

	public void setMultimodeOrder(Integer multimodeOrder) {
		this.multimodeOrder = multimodeOrder;
	}

	public String getMouldRatio() {
		return mouldRatio;
	}

	public void setMouldRatio(String mouldRatio) {
		this.mouldRatio = mouldRatio;
	}

	public String getCavity() {
		return cavity;
	}

	public void setCavity(String cavity) {
		this.cavity = cavity;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getPlacingTooling() {
		return placingTooling;
	}

	public void setPlacingTooling(String placingTooling) {
		this.placingTooling = placingTooling;
	}

	public String getPreTooling() {
		return preTooling;
	}

	public void setPreTooling(String preTooling) {
		this.preTooling = preTooling;
	}

	public String getMouldFlow() {
		return mouldFlow;
	}

	public void setMouldFlow(String mouldFlow) {
		this.mouldFlow = mouldFlow;
	}

	public Integer getLife() {
		return life;
	}

	public void setLife(Integer life) {
		this.life = life;
	}

	public Integer getAddlife() {
		return addlife;
	}

	public void setAddlife(Integer addlife) {
		this.addlife = addlife;
	}

	public Integer getUsedlife() {
		return usedlife;
	}

	public void setUsedlife(Integer usedlife) {
		this.usedlife = usedlife;
	}

	public Integer getCanuselife() {
		return canuselife;
	}

	public void setCanuselife(Integer canuselife) {
		this.canuselife = canuselife;
	}


	public String getCutomerGid() {
		return cutomerGid;
	}

	public void setCutomerGid(String cutomerGid) {
		this.cutomerGid = cutomerGid;
	}

	public String getProviderGid() {
		return providerGid;
	}

	public void setProviderGid(String providerGid) {
		this.providerGid = providerGid;
	}

	public BigDecimal getOpenCost() {
		return openCost;
	}

	public void setOpenCost(BigDecimal openCost) {
		this.openCost = openCost;
	}

	public Integer getIsreturn() {
		return isreturn;
	}

	public void setIsreturn(Integer isreturn) {
		this.isreturn = isreturn;
	}

	public Integer getIsShareOrver() {
		return isShareOrver;
	}

	public void setIsShareOrver(Integer isShareOrver) {
		this.isShareOrver = isShareOrver;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getCdefine1() {
		return cdefine1;
	}

	public void setCdefine1(String cdefine1) {
		this.cdefine1 = cdefine1;
	}

	
	
  
}