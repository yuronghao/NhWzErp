package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "AA_Goods")
public class AaGoods implements Serializable {
	private static final long serialVersionUID = -7437897589002370963L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//物品ID
	@EmiColumn(name = "gid", ID = true)
	private String gid;//物品UID
	@EmiColumn(name = "notes")
	private String notes;//备注
	@EmiColumn(name = "goodscode")
	private String goodscode;//物品编码
	@EmiColumn(name = "goodsname")
	private String goodsname;//物品名称
	@EmiColumn(name = "goodsstandard")
	private String goodsstandard;//物品规格
	@EmiColumn(name = "goodsunit")
	private String goodsunit;//主计量单位
	
	@EmiColumn(name = "unitGroupGid")
	private String unitGroupGid;//计量单位组
	
	@EmiColumn(name = "goodsbarcode")
	private String goodsbarcode;//物品条码
	
	@EmiColumn(name = "scanNum")
	private Integer scanNum;//默认扫描数量


	@EmiColumn(name = "goodssortuid")
	private String goodssortuid;//物品分类Uid
	@EmiColumn(name = "procureabovescale")
	private BigDecimal procureabovescale;//采购超订单入库比例
	@EmiColumn(name = "produabovescale")
	private BigDecimal produabovescale;//生产超订单入库比例
	@EmiColumn(name = "begintimes")
	private Date begintimes;//生效时间
	@EmiColumn(name = "endtimes")
	private Date endtimes;//失效时间
	@EmiColumn(name = "buy")
	private Integer buy;//是否采购
	@EmiColumn(name = "procost")
	private Integer procost;//是否生产耗用
	@EmiColumn(name = "ownpro")
	private Integer ownpro;//是否自制
	@EmiColumn(name = "domestic")
	private Integer domestic;//是否内销
	@EmiColumn(name = "export")
	private Integer export;//是否外销
	@EmiColumn(name = "binvbach")
	private Integer binvbach;//是否批次
	@EmiColumn(name = "directstore")
	private Integer directstore;//是否直接入库
	@EmiColumn(name = "wiptype")
	private Integer wiptype;//是否为倒冲
	@EmiColumn(name = "cinvaddcode")
	private String cinvaddcode;//存货代码
	@EmiColumn(name = "cdefwarehouse")
	private String cdefwarehouse;//默认仓库
	@EmiColumn(name = "casscomunitcode")
	private String casscomunitcode;//辅计量单位编码
	@EmiColumn(name = "cstcomunitcode")
	private String cstcomunitcode;//库存默认计量单位编码
//	@EmiColumn(name = "csacomunitcode")
//	private String csacomunitcode;//销售默认计量单位编码
//	@EmiColumn(name = "cpucomunitcode")
//	private String cpucomunitcode;//采购默认计量单位编码
//	@EmiColumn(name = "ccacomunitcode")
//	private String ccacomunitcode;//成本默认计量单位编码
	@EmiColumn(name = "allowoutnormal")
	private Integer allowoutnormal;//允许超订单出库
	@EmiColumn(name = "scanunit")
	private String scanunit;//默认扫描单位
	@EmiColumn(name = "isvolume")
	private Integer isvolume;//是否卷管理
	@EmiColumn(name = "soulationgid")
	private String soulationgid;//属性分类id
	@EmiColumn(name = "sobgid")
	private String sobgid;//帐套编码
	@EmiColumn(name = "orggid")
	private String orggid;//组织编码
	@EmiColumn(name = "valuationgid")
	private String valuationgid;//计价方式
	@EmiColumn(name = "flag")
	private Integer flag;//
	
	@EmiColumn(name = "isInvQuality")
	private Integer isInvQuality;//是否保质期管理
	
	@EmiColumn(name = "massDate")
	private Integer massDate;//保质期天数

	private String unitName;//主计量单位
	private String cassComUnitName;//辅计量单位名称
	private String cstComUnitName;//库存默认计量单位
//	private String csaComUnitName;//销售默认计量单位
//	private String cpuComUnitName;//采购默认计量单位
//	private String ccaComUnitName;//成本默认计量单位
	private String unitGroupName;//计量单位组名称
	private String classificationName;//物品分类名称
	
	
	@EmiColumn(name="cfree1" )
	private Integer cfree1;//是否启用 0未启用 1启用
	
	@EmiColumn(name="cfree2" )
	private Integer cfree2;//是否启用 0未启用 1启用
	
	private BigDecimal nowsum;
	
	
	
	@EmiColumn(name="invWeight" )
	private BigDecimal invWeight;//单件重量
	
	@EmiColumn(name="weightRate" )
	private BigDecimal weightRate;//称重比率
	
	@EmiColumn(name="grossWeight" )
	private BigDecimal grossWeight;//毛重单个框子重量
	
	@EmiColumn(name="minPackNum" )
	private BigDecimal minPackNum;//最小包装量
	
	
	
	
	
	
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getMinPackNum() {
		return minPackNum;
	}

	public void setMinPackNum(BigDecimal minPackNum) {
		this.minPackNum = minPackNum;
	}

	public Integer getIsInvQuality() {
		return isInvQuality;
	}

	public void setIsInvQuality(Integer isInvQuality) {
		this.isInvQuality = isInvQuality;
	}

	public Integer getMassDate() {
		return massDate;
	}

	public void setMassDate(Integer massDate) {
		this.massDate = massDate;
	}

	public BigDecimal getInvWeight() {
		return invWeight;
	}

	public void setInvWeight(BigDecimal invWeight) {
		this.invWeight = invWeight;
	}

	public BigDecimal getWeightRate() {
		return weightRate;
	}

	public void setWeightRate(BigDecimal weightRate) {
		this.weightRate = weightRate;
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

	public String getGoodscode() {
		return goodscode;
	}

	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsstandard() {
		return goodsstandard;
	}

	public void setGoodsstandard(String goodsstandard) {
		this.goodsstandard = goodsstandard;
	}

	public String getGoodsunit() {
		return goodsunit;
	}

	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
	}

	public String getUnitGroupGid() {
		return unitGroupGid;
	}

	public void setUnitGroupGid(String unitGroupGid) {
		this.unitGroupGid = unitGroupGid;
	}

	public String getGoodsbarcode() {
		return goodsbarcode;
	}

	public void setGoodsbarcode(String goodsbarcode) {
		this.goodsbarcode = goodsbarcode;
	}

	public Integer getScanNum() {
		return scanNum;
	}

	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}

	public String getGoodssortuid() {
		return goodssortuid;
	}

	public void setGoodssortuid(String goodssortuid) {
		this.goodssortuid = goodssortuid;
	}

	public BigDecimal getProcureabovescale() {
		return procureabovescale;
	}

	public void setProcureabovescale(BigDecimal procureabovescale) {
		this.procureabovescale = procureabovescale;
	}

	public BigDecimal getProduabovescale() {
		return produabovescale;
	}

	public void setProduabovescale(BigDecimal produabovescale) {
		this.produabovescale = produabovescale;
	}

	public Date getBegintimes() {
		return begintimes;
	}

	public void setBegintimes(Date begintimes) {
		this.begintimes = begintimes;
	}

	public Date getEndtimes() {
		return endtimes;
	}

	public void setEndtimes(Date endtimes) {
		this.endtimes = endtimes;
	}

	public Integer getBuy() {
		return buy;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

	public Integer getProcost() {
		return procost;
	}

	public void setProcost(Integer procost) {
		this.procost = procost;
	}

	public Integer getOwnpro() {
		return ownpro;
	}

	public void setOwnpro(Integer ownpro) {
		this.ownpro = ownpro;
	}

	public Integer getDomestic() {
		return domestic;
	}

	public void setDomestic(Integer domestic) {
		this.domestic = domestic;
	}

	public Integer getExport() {
		return export;
	}

	public void setExport(Integer export) {
		this.export = export;
	}

	public Integer getBinvbach() {
		return binvbach;
	}

	public void setBinvbach(Integer binvbach) {
		this.binvbach = binvbach;
	}

	public Integer getDirectstore() {
		return directstore;
	}

	public void setDirectstore(Integer directstore) {
		this.directstore = directstore;
	}

	public Integer getWiptype() {
		return wiptype;
	}

	public void setWiptype(Integer wiptype) {
		this.wiptype = wiptype;
	}

	public String getCinvaddcode() {
		return cinvaddcode;
	}

	public void setCinvaddcode(String cinvaddcode) {
		this.cinvaddcode = cinvaddcode;
	}

	public String getCdefwarehouse() {
		return cdefwarehouse;
	}

	public void setCdefwarehouse(String cdefwarehouse) {
		this.cdefwarehouse = cdefwarehouse;
	}

	public String getCasscomunitcode() {
		return casscomunitcode;
	}

	public void setCasscomunitcode(String casscomunitcode) {
		this.casscomunitcode = casscomunitcode;
	}

	public String getCstcomunitcode() {
		return cstcomunitcode;
	}

	public void setCstcomunitcode(String cstcomunitcode) {
		this.cstcomunitcode = cstcomunitcode;
	}

	public Integer getAllowoutnormal() {
		return allowoutnormal;
	}

	public void setAllowoutnormal(Integer allowoutnormal) {
		this.allowoutnormal = allowoutnormal;
	}

	public String getScanunit() {
		return scanunit;
	}

	public void setScanunit(String scanunit) {
		this.scanunit = scanunit;
	}

	public Integer getIsvolume() {
		return isvolume;
	}

	public void setIsvolume(Integer isvolume) {
		this.isvolume = isvolume;
	}

	public String getSoulationgid() {
		return soulationgid;
	}

	public void setSoulationgid(String soulationgid) {
		this.soulationgid = soulationgid;
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

	public String getValuationgid() {
		return valuationgid;
	}

	public void setValuationgid(String valuationgid) {
		this.valuationgid = valuationgid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCassComUnitName() {
		return cassComUnitName;
	}

	public void setCassComUnitName(String cassComUnitName) {
		this.cassComUnitName = cassComUnitName;
	}

	public String getCstComUnitName() {
		return cstComUnitName;
	}

	public void setCstComUnitName(String cstComUnitName) {
		this.cstComUnitName = cstComUnitName;
	}

	public String getUnitGroupName() {
		return unitGroupName;
	}

	public void setUnitGroupName(String unitGroupName) {
		this.unitGroupName = unitGroupName;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public Integer getCfree1() {
		return cfree1;
	}

	public void setCfree1(Integer cfree1) {
		this.cfree1 = cfree1;
	}

	public Integer getCfree2() {
		return cfree2;
	}

	public void setCfree2(Integer cfree2) {
		this.cfree2 = cfree2;
	}

	public BigDecimal getNowsum() {
		return nowsum;
	}

	public void setNowsum(BigDecimal nowsum) {
		this.nowsum = nowsum;
	}
	
	


}