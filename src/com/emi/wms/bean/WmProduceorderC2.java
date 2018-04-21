package com.emi.wms.bean;

import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "WM_ProduceOrder_C2")
public class WmProduceorderC2 {
	
	@EmiColumn(name = "pk", increment = true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "notes")
    private String notes;

	@EmiColumn(name = "produceordercuid")
    private String produceordercuid;

    private String materialuid;
    
    @EmiColumn(name = "goodsUid")
    private String goodsUid;
    
    @EmiColumn(name = "number")
    private BigDecimal number;

    @EmiColumn(name = "receivednum")
    private BigDecimal receivednum;

    private String cinvcode;

    private BigDecimal ingredientnum;
    
    @EmiColumn(name = "cfree1")
    private String cfree1;
    
    

    public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
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

    public String getProduceordercuid() {
        return produceordercuid;
    }

    public void setProduceordercuid(String produceordercuid) {
        this.produceordercuid = produceordercuid;
    }

    public String getMaterialuid() {
        return materialuid;
    }

    public void setMaterialuid(String materialuid) {
        this.materialuid = materialuid;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getReceivednum() {
        return receivednum;
    }

    public void setReceivednum(BigDecimal receivednum) {
        this.receivednum = receivednum;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public void setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
    }

    public BigDecimal getIngredientnum() {
        return ingredientnum;
    }

    public void setIngredientnum(BigDecimal ingredientnum) {
        this.ingredientnum = ingredientnum;
    }
}