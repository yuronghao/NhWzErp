package com.emi.flow.model.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
/*
 * 流程-枚举
 */
@EmiTable(name = "FLOW_Enum")
public class FLOW_Enum implements Serializable{
	private static final long serialVersionUID = 5518707604762774034L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "enumName")
	private String enumName;			//枚举名称
	
	@EmiColumn(name = "description")
	private String description;			//描述
	
	@EmiColumn(name = "createUserId")
	private String createUserId;		//创建人id
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;		//创建时间
	
	@EmiColumn(name = "enumType")
	private Integer enumType;			//枚举类型	0：自定义 1：数据库读取
	
	@EmiColumn(name = "tableName")
	private String tableName;			//数据来源表名	当enumType等于1时使用
	
	@EmiColumn(name = "columnName")
	private String columnName;			//数据来源字段名	当enumType等于1时使用
	
	@EmiColumn(name = "conditionSql")
	private String conditionSql;		//数据过滤条件	当enumType等于1时使用

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

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getEnumType() {
		return enumType;
	}

	public void setEnumType(Integer enumType) {
		this.enumType = enumType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getConditionSql() {
		return conditionSql;
	}

	public void setConditionSql(String conditionSql) {
		this.conditionSql = conditionSql;
	}

}
