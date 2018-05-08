package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
import com.sun.xml.internal.ws.developer.StreamingAttachment;

@EmiTable(name = "WM_MaterialOut_C")
public class WmMaterialoutC implements Serializable{
	

	private static final long serialVersionUID = 4682832332060053150L;

	@EmiColumn(name = "pk", increment = true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "notes")
    private String notes;
	
	@EmiColumn(name = "materialoutuid")
    private String materialoutuid;//材料出库主表uid
	
	@EmiColumn(name = "goodsuid")
    private String goodsuid;//物品uid
	
	@EmiColumn(name = "number")
    private BigDecimal number;//数量
	
	@EmiColumn(name = "assistNumber")
	private BigDecimal assistNumber;//辅计量数量
	
	@EmiColumn(name = "backNumber")
	private BigDecimal backNumber;//已退数量
	
	@EmiColumn(name = "backAssistNumber")
	private BigDecimal backAssistNumber;//辅计量已退数量
	
	@EmiColumn(name = "unitprice")
    private BigDecimal unitprice;//单价
	
	@EmiColumn(name = "totalprice")
    private BigDecimal totalprice;//金额
	
	@EmiColumn(name = "produceordercuid")
    private String produceordercuid;//生产订单子表uid
	
	@EmiColumn(name = "produceorderc2uid")
    private String produceorderc2uid;//生产订单子表2uid
	
	@EmiColumn(name = "omMaterialuid")
    private String omMaterialuid;//委外订单子表2uid
	
	@EmiColumn(name = "goodsallocationuid")
    private String goodsallocationuid;//货位uid
	
	@EmiColumn(name = "batchcode")
    private String batchcode;//批次
	
	@EmiColumn(name = "processRouteCGoodsUid")
	private String processRouteCGoodsUid;//生产订单工艺材料表uid

	private Integer followmovinggid;

	public Integer getFollowmovinggid() {
		return followmovinggid;
	}

	public void setFollowmovinggid(Integer followmovinggid) {
		this.followmovinggid = followmovinggid;
	}

	@EmiColumn(name="cfree1" )
	private String cfree1;
	
	@EmiColumn(name="cfree2" )
	private String cfree2;
	
	@EmiColumn(name="dvdate" )
	private Timestamp dvdate;//失效日期
	
	@EmiColumn(name="dmadeDate" )
	private Timestamp dmadeDate; //生产日期
	
	@EmiColumn(name="imassDate" )
	private Integer imassDate; //质保期天数
	
	
	@EmiColumn(name="barCode" )
	private String barCode;

	@EmiColumn(name="materialapplycgid" )
	private String materialapplycgid;


	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMaterialapplycgid() {
		return materialapplycgid;
	}

	public void setMaterialapplycgid(String materialapplycgid) {
		this.materialapplycgid = materialapplycgid;
	}

	private String goodsStandard;

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	private AaGoods good;
	private String alocation;	
	private String produceCode;	
	private String goodName;
	private String owhGid;
	private String owhCode;
	private String recordPerson;
	private String departId;
	private String whUid;
	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	
	
	public Timestamp getDmadeDate() {
		return dmadeDate;
	}
	public void setDmadeDate(Timestamp dmadeDate) {
		this.dmadeDate = dmadeDate;
	}
	public Integer getImassDate() {
		return imassDate;
	}
	public void setImassDate(Integer imassDate) {
		this.imassDate = imassDate;
	}
	public String getRecordPerson() {
		return recordPerson;
	}
	public void setRecordPerson(String recordPerson) {
		this.recordPerson = recordPerson;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getWhUid() {
		return whUid;
	}
	public void setWhUid(String whUid) {
		this.whUid = whUid;
	}
	public String getOwhGid() {
		return owhGid;
	}
	public void setOwhGid(String owhGid) {
		this.owhGid = owhGid;
	}
	public String getOwhCode() {
		return owhCode;
	}
	public void setOwhCode(String owhCode) {
		this.owhCode = owhCode;
	}
	public String getRecordPersonName() {
		return recordPersonName;
	}
	public void setRecordPersonName(String recordPersonName) {
		this.recordPersonName = recordPersonName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public String getProduceCode() {
		return produceCode;
	}
	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public AaGoods getGood() {
		return good;
	}
	public void setGood(AaGoods good) {
		this.good = good;
	}
	public String getAlocation() {
		return alocation;
	}
	public void setAlocation(String alocation) {
		this.alocation = alocation;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public Timestamp getDvdate() {
		return dvdate;
	}
	public void setDvdate(Timestamp dvdate) {
		this.dvdate = dvdate;
	}
	public String getOmMaterialuid() {
		return omMaterialuid;
	}
	public void setOmMaterialuid(String omMaterialuid) {
		this.omMaterialuid = omMaterialuid;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMaterialoutuid() {
		return materialoutuid;
	}
	public void setMaterialoutuid(String materialoutuid) {
		this.materialoutuid = materialoutuid;
	}
	public String getGoodsuid() {
		return goodsuid;
	}
	public void setGoodsuid(String goodsuid) {
		this.goodsuid = goodsuid;
	}
	public BigDecimal getNumber() {
		return number;
	}
	public void setNumber(BigDecimal number) {
		this.number = number;
	}
	public BigDecimal getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}
	public BigDecimal getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}
	public String getProduceordercuid() {
		return produceordercuid;
	}
	public void setProduceordercuid(String produceordercuid) {
		this.produceordercuid = produceordercuid;
	}
	public String getProduceorderc2uid() {
		return produceorderc2uid;
	}
	public void setProduceorderc2uid(String produceorderc2uid) {
		this.produceorderc2uid = produceorderc2uid;
	}
	public String getGoodsallocationuid() {
		return goodsallocationuid;
	}
	public void setGoodsallocationuid(String goodsallocationuid) {
		this.goodsallocationuid = goodsallocationuid;
	}
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getProcessRouteCGoodsUid() {
		return processRouteCGoodsUid;
	}
	public void setProcessRouteCGoodsUid(String processRouteCGoodsUid) {
		this.processRouteCGoodsUid = processRouteCGoodsUid;
	}
	public String getCfree1() {
		return cfree1;
	}
	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}
	public String getCfree2() {
		return cfree2;
	}
	public void setCfree2(String cfree2) {
		this.cfree2 = cfree2;
	}
	public BigDecimal getBackNumber() {
		return backNumber;
	}
	public void setBackNumber(BigDecimal backNumber) {
		this.backNumber = backNumber;
	}
	public BigDecimal getAssistNumber() {
		return assistNumber;
	}
	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}
	public BigDecimal getBackAssistNumber() {
		return backAssistNumber;
	}
	public void setBackAssistNumber(BigDecimal backAssistNumber) {
		this.backAssistNumber = backAssistNumber;
	}

  
}