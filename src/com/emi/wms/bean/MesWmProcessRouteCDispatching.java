package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表-
 */
@EmiTable(name = "MES_WM_ProcessRouteCDispatching")
public class MesWmProcessRouteCDispatching implements Serializable{
	private static final long serialVersionUID = 3851866667767024700L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "objGid")
	private String objGid;			//执行对象id
	
	@EmiColumn(name = "objType",hasDefault=true)
	private Integer objType;			//执行对象类型 0：人 1：工作中心
	
	@EmiColumn(name = "routeCGid")
	private String routeCGid;		//工艺路线子表id

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

	public String getObjGid() {
		return objGid;
	}

	public void setObjGid(String objGid) {
		this.objGid = objGid;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public String getRouteCGid() {
		return routeCGid;
	}

	public void setRouteCGid(String routeCGid) {
		this.routeCGid = routeCGid;
	}
	
}
