package com.emi.android.bean;

import java.io.Serializable;

public class ProcessTaskStationRsp implements Serializable{

	private static final long serialVersionUID = -4969836270945022660L;

	private String stationGid;//工位gid
	
	private String stationBarcode;//工位条码
	
	private String stationCode;//工位编码
	
	private String stationName;//工位名称
	
	

	public String getStationBarcode() {
		return stationBarcode;
	}

	public void setStationBarcode(String stationBarcode) {
		this.stationBarcode = stationBarcode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationGid() {
		return stationGid;
	}

	public void setStationGid(String stationGid) {
		this.stationGid = stationGid;
	}
	
	
	
}
