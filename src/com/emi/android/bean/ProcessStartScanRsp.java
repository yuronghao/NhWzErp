package com.emi.android.bean;

import java.io.Serializable;
import java.util.List;

//开工扫描人员返回的对象
public class ProcessStartScanRsp implements Serializable {

	private static final long serialVersionUID = 7122786953650204813L;
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	private String resultType;//p(人员)，s(工位)，t(任务)
	private Integer isTogether;//多个任务同时开工
	private Integer stationIsMust;//开工时工位是否必填(1是 0否)
	private Integer isMustScanMould;//开工时是否必扫模具(1是0否)
	
	private ProcessTaskDetailRsp task;
	private List<ProcessTaskPersonRsp> personUnitVendor;//人员 或者 工作组 或者 委外供应商
	private List<ProcessTaskStationRsp> station;//工序名称
	private ProcessTaskMouldRsp mould;//模具名称
	
	
	
	public Integer getIsMustScanMould() {
		return isMustScanMould;
	}
	public void setIsMustScanMould(Integer isMustScanMould) {
		this.isMustScanMould = isMustScanMould;
	}
	public ProcessTaskMouldRsp getMould() {
		return mould;
	}
	public void setMould(ProcessTaskMouldRsp mould) {
		this.mould = mould;
	}
	public List<ProcessTaskPersonRsp> getPersonUnitVendor() {
		return personUnitVendor;
	}
	public void setPersonUnitVendor(List<ProcessTaskPersonRsp> personUnitVendor) {
		this.personUnitVendor = personUnitVendor;
	}
	public List<ProcessTaskStationRsp> getStation() {
		return station;
	}
	public void setStation(List<ProcessTaskStationRsp> station) {
		this.station = station;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getFailInfor() {
		return failInfor;
	}
	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}
	public ProcessTaskDetailRsp getTask() {
		return task;
	}
	public void setTask(ProcessTaskDetailRsp task) {
		this.task = task;
	}
	public Integer getIsTogether() {
		return isTogether;
	}
	public void setIsTogether(Integer isTogether) {
		this.isTogether = isTogether;
	}
	public Integer getStationIsMust() {
		return stationIsMust;
	}
	public void setStationIsMust(Integer stationIsMust) {
		this.stationIsMust = stationIsMust;
	}

	

	



	
	
	
	
	
	
}
