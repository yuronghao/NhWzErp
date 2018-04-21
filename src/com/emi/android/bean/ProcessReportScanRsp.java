package com.emi.android.bean;

import java.io.Serializable;
import java.util.List;

//开工扫描人员返回的对象
public class ProcessReportScanRsp implements Serializable {

	private static final long serialVersionUID = 7122786953650204813L;
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	private String resultType;//p(人员)，s(工位)，t(任务)
	private List<ProcessTaskPersonRsp> personUnitVendor;//人员 或者 工作组 或者 委外供应商
	private List<ProcessTaskStationRsp> station;//工序名称
	private ProcessTaskDetailRsp task;
	
	
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getFailInfor() {
		return failInfor;
	}
	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public List<ProcessTaskPersonRsp> getPersonUnitVendor() {
		return personUnitVendor;
	}
	public void setPersonUnitVendor(List<ProcessTaskPersonRsp> personUnitVendor) {
		this.personUnitVendor = personUnitVendor;
	}

	public ProcessTaskDetailRsp getTask() {
		return task;
	}
	public void setTask(ProcessTaskDetailRsp task) {
		this.task = task;
	}
	public List<ProcessTaskStationRsp> getStation() {
		return station;
	}
	public void setStation(List<ProcessTaskStationRsp> station) {
		this.station = station;
	}
	
	
	

	



	
	
	
	
	
	
}
