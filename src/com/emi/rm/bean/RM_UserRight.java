package com.emi.rm.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 人员权限
 */
@EmiTable(name = "RM_UserRight")
public class RM_UserRight implements Serializable{
	private static final long serialVersionUID = 2525246863294779346L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "userId")
	private String userId;  		//用户id
	
	@EmiColumn(name = "rightId")
	private String rightId;  		//权限id
	
	@EmiColumn(name = "useFuns")
	private Integer useFuns;		//使用功能权值和 1：查看 2：新增 4：修改 8：删除 (使用位运算进行判断含有哪些功能)

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public Integer getUseFuns() {
		return useFuns;
	}

	public void setUseFuns(Integer useFuns) {
		this.useFuns = useFuns;
	}
	
}
