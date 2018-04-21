package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_ProcessRoute")
public class MesWmProcessroute implements Serializable{
	private static final long serialVersionUID = 8284377821798229010L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;				//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "routcode")
	private String routcode;			//工艺路线编码
	
	@EmiColumn(name = "effdate")
	private Date effdate;			//生效时间
	
	@EmiColumn(name = "deffdate")
	private Date deffdate;			//失效时间
	
	@EmiColumn(name = "routname")
	private String routname;			//工艺路线名称
	
	@EmiColumn(name = "routdes")
	private String routdes;				//工艺描述说明
	
	@EmiColumn(name = "goodsUid")
	private String goodsUid;			//物料编码

	@EmiColumn(name = "routSoCode")
	private String routSoCode;			//版本号
	
	@EmiColumn(name = "designJson")
	private String designJson;			//设计图的json

	@EmiColumn(name = "state")
	private Integer state ;//状态 0:未审核  1：审核
	
	@EmiColumn(name = "isDelete" , hasDefault=true)
	private Integer isDelete;	//是否已删除  0：否 1：是
	
	@EmiColumn(name = "createUser" )
	private String createUser;	//创建用户id
	
	@EmiColumn(name = "modifyUser" )
	private String modifyUser;	//修改用户id
	
	@EmiColumn(name = "modifyDate")
	private Date modifyDate;	//修改时间
	
	@EmiColumn(name = "authDate")
	private Date authDate;	//审核时间
	
	@EmiColumn(name = "authUser")
	private String authUser;	//审核人id
	
	private String goodsCode;
	private String goodsName;
	private String goodsStandard;
	private String goodsUnit;
	private String createUserName;
	private String modifyUserName;
	private String authUserName;
	
	private String changeSrcRouteCid;	//--没用，只为和订单工艺路线统一
	private String changeSrcOrderCid;	//--没用，只为和订单工艺路线统一
	private BigDecimal changeSrcNumber;	//--没用，只为和订单工艺路线统一
	
	private String endFreeName;//末级自由项名称
	
	private List<MesWmProcessroutec> routeCList;//子表

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

	public String getRoutcode() {
		return routcode;
	}

	public void setRoutcode(String routcode) {
		this.routcode = routcode;
	}

	public Date getEffdate() {
		return effdate;
	}

	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}

	public Date getDeffdate() {
		return deffdate;
	}

	public void setDeffdate(Date deffdate) {
		this.deffdate = deffdate;
	}

	public String getRoutname() {
		return routname;
	}

	public void setRoutname(String routname) {
		this.routname = routname;
	}

	public String getRoutdes() {
		return routdes;
	}

	public void setRoutdes(String routdes) {
		this.routdes = routdes;
	}

	public String getRoutSoCode() {
		return routSoCode;
	}

	public void setRoutSoCode(String routSoCode) {
		this.routSoCode = routSoCode;
	}

	public String getDesignJson() {
		return designJson;
	}

	public void setDesignJson(String designJson) {
		this.designJson = designJson;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public List<MesWmProcessroutec> getRouteCList() {
		return routeCList;
	}

	public void setRouteCList(List<MesWmProcessroutec> routeCList) {
		this.routeCList = routeCList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
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

	public String getEndFreeName() {
		return endFreeName;
	}

	public void setEndFreeName(String endFreeName) {
		this.endFreeName = endFreeName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getAuthUser() {
		return authUser;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getAuthUserName() {
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}
	
	
    
}