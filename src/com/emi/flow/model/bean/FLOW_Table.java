package com.emi.flow.model.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-表
 */
@EmiTable(name = "FLOW_Table")
public class FLOW_Table implements Serializable{
	private static final long serialVersionUID = 4370557332054855796L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "tableName")
	private String tableName;			//表名
	
	@EmiColumn(name = "tableDisplayName")
	private String tableDisplayName;	//表显示名
	
	@EmiColumn(name = "tableDescription")
	private String tableDescription;	//表描述
	
	@EmiColumn(name = "note")
	private String note;				//备注
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;		//创建时间

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDisplayName() {
		return tableDisplayName;
	}

	public void setTableDisplayName(String tableDisplayName) {
		this.tableDisplayName = tableDisplayName;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
