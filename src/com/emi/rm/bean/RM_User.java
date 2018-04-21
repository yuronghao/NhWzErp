package com.emi.rm.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 用户
 */
@EmiTable(name = "RM_User")
public class RM_User implements Serializable{
	
	private static final long serialVersionUID = 2967519448106384798L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "userCode")
	private String userCode;			//用户代码
	
	@EmiColumn(name = "userName")
	private String userName;			//用户名称
	
	@EmiColumn(name = "passWord")
	private String passWord;			//密码
	
	@EmiColumn(name = "realName")
	private String realName;			//真是姓名
	
	@EmiColumn(name = "isPause")
	private Integer isPause;			//是否停用
	
	@EmiColumn(name = "beginTime")
	private Timestamp beginTime ;		//开始时间
	
	@EmiColumn(name = "endTime")
	private Timestamp endTime;			//结束时间
	
	@EmiColumn(name = "notes")
	private String notes;				//备注

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsPause() {
		return isPause;
	}

	public void setIsPause(Integer isPause) {
		this.isPause = isPause;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
