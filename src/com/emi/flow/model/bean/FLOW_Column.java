package com.emi.flow.model.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-字段表
 */
@EmiTable(name = "FLOW_Column")
public class FLOW_Column implements Serializable{
	private static final long serialVersionUID = 4880117860032706600L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "columnName")
	private String columnName;			//字段名
	
	@EmiColumn(name = "columnDisplayName")
	private String columnDisplayName;	//字段显示名
	
	@EmiColumn(name = "tableId")
	private String tableId;				//所属表id
	
	@EmiColumn(name = "columnType")
	private String columnType;			//字段类型
	
	@EmiColumn(name = "columnLength")
	private Integer columnLength;		//长度
	
	@EmiColumn(name = "columnDescription")
	private String columnDescription;	//描述
	
	@EmiColumn(name = "isNull")
	private Integer isNull;				//是否可空	0：不可空  1：可空
	
	@EmiColumn(name = "isshow")
	private Integer isshow;				//是否显示
	
	@EmiColumn(name = "columnIndex")
	private Integer columnIndex;		//字段排序
	
	@EmiColumn(name = "formLocation")
	private String formLocation;		//
	
	@EmiColumn(name = "increment")
	private Integer increment;			//自增长步长 0表示不自增长
	
	@EmiColumn(name = "isPk")
	private Integer isPk;				//是否是主键

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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDisplayName() {
		return columnDisplayName;
	}

	public void setColumnDisplayName(String columnDisplayName) {
		this.columnDisplayName = columnDisplayName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}

	public String getColumnDescription() {
		return columnDescription;
	}

	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

	public Integer getIsNull() {
		return isNull;
	}

	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getFormLocation() {
		return formLocation;
	}

	public void setFormLocation(String formLocation) {
		this.formLocation = formLocation;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public Integer getIsPk() {
		return isPk;
	}

	public void setIsPk(Integer isPk) {
		this.isPk = isPk;
	}
	
	
}
