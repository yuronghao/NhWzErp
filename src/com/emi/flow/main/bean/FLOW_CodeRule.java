package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 编码规则
 */
@EmiTable(name = "FLOW_CodeRule")
public class FLOW_CodeRule implements Serializable{
	private static final long serialVersionUID = 4804340461725788522L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "codeRuleName")
	private String codeRuleName;			//编码规则名称
	
	@EmiColumn(name = "codeRuleNumber")
	private String codeRuleNumber;			//编码规则编码
	
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

	public String getCodeRuleName() {
		return codeRuleName;
	}

	public void setCodeRuleName(String codeRuleName) {
		this.codeRuleName = codeRuleName;
	}

	public String getCodeRuleNumber() {
		return codeRuleNumber;
	}

	public void setCodeRuleNumber(String codeRuleNumber) {
		this.codeRuleNumber = codeRuleNumber;
	}

	
}
