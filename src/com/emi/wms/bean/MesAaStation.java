package com.emi.wms.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_AA_Station")
public class MesAaStation implements Serializable{

	private static final long serialVersionUID = -9193819253205303795L;

	@EmiColumn(name = "pk", increment=true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "stationName")
    private String stationName;//工位名称

	@EmiColumn(name = "stationBarcode")
    private String stationBarcode;//工位条码

	@EmiColumn(name = "sobgid")
    private String sobgid;

	@EmiColumn(name = "orggid")
    private String orggid;

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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationBarcode() {
		return stationBarcode;
	}

	public void setStationBarcode(String stationBarcode) {
		this.stationBarcode = stationBarcode;
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

	

}