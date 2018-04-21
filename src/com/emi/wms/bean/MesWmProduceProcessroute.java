package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_ProduceProcessRoute")
public class MesWmProduceProcessroute implements Serializable{
	private static final long serialVersionUID = 8284377821798229010L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;				//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "produceUid")
	private String produceUid;			//生产订单主表uid
	
	@EmiColumn(name = "produceCuid")
	private String produceCuid;         //生产订单产品表uid

	@EmiColumn(name = "designJson")
	private String designJson;         //设计json
	
	@EmiColumn(name = "billDate")
	private Date billDate;		//订单日期
	
	@EmiColumn(name = "changeSrcRouteCid")
	private String changeSrcRouteCid;	//改制来源工艺路线子表id
	
	@EmiColumn(name = "changeSrcOrderCid")
	private String changeSrcOrderCid;	//改制来源订单子表id
	
	@EmiColumn(name = "changeSrcNumber")
	private BigDecimal changeSrcNumber;	//改制来源工序转出数量
	
	private String goodsUid;//成品id
	

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

	public String getProduceUid() {
		return produceUid;
	}

	public void setProduceUid(String produceUid) {
		this.produceUid = produceUid;
	}

	public String getProduceCuid() {
		return produceCuid;
	}

	public void setProduceCuid(String produceCuid) {
		this.produceCuid = produceCuid;
	}

	public String getDesignJson() {
		return designJson;
	}

	public void setDesignJson(String designJson) {
		this.designJson = designJson;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getChangeSrcRouteCid() {
		return changeSrcRouteCid;
	}

	public void setChangeSrcRouteCid(String changeSrcRouteCid) {
		this.changeSrcRouteCid = changeSrcRouteCid;
	}

	public String getChangeSrcOrderCid() {
		return changeSrcOrderCid;
	}

	public void setChangeSrcOrderCid(String changeSrcOrderCid) {
		this.changeSrcOrderCid = changeSrcOrderCid;
	}

	public BigDecimal getChangeSrcNumber() {
		return changeSrcNumber;
	}

	public void setChangeSrcNumber(BigDecimal changeSrcNumber) {
		this.changeSrcNumber = changeSrcNumber;
	}
	
}