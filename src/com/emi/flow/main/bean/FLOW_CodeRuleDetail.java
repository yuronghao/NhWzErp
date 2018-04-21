package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 编码规则详情
 */
@EmiTable(name = "FLOW_CodeRuleDetail")
public class FLOW_CodeRuleDetail implements Serializable{
	private static final long serialVersionUID = 5017168439573285791L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "codeRuleId")
	private String codeRuleId;			//主表id
	
	@EmiColumn(name = "codeRuleIndex")
	private Integer codeRuleIndex;		//排序号
	
	@EmiColumn(name = "increment")
	private Integer increment;			//步长
	
	@EmiColumn(name = "initValue")
	private String initValue;			//初始值
	
	@EmiColumn(name = "format")
	private String format;				//格式 
	
	@EmiColumn(name = "valueLength")
	private Integer valueLength;		//长度
	
	@EmiColumn(name = "valueType")
	private Integer valueType;			//值类型  0：字符串 1：自增数字 2：日期
	
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

	public String getCodeRuleId() {
		return codeRuleId;
	}

	public void setCodeRuleId(String codeRuleId) {
		this.codeRuleId = codeRuleId;
	}

	public Integer getCodeRuleIndex() {
		return codeRuleIndex;
	}

	public void setCodeRuleIndex(Integer codeRuleIndex) {
		this.codeRuleIndex = codeRuleIndex;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public String getInitValue() {
		return initValue;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getValueLength() {
		return valueLength;
	}

	public void setValueLength(Integer valueLength) {
		this.valueLength = valueLength;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	
}
