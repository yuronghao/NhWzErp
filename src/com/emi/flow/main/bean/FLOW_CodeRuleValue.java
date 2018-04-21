package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 编码规则生成的值
 */
@EmiTable(name = "FLOW_CodeRuleValue")
public class FLOW_CodeRuleValue implements Serializable{
	private static final long serialVersionUID = -1942381568363394819L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "codeRuleId")
	private String codeRuleId;			//主表id
	
	@EmiColumn(name = "prefixCode")
	private String prefixCode;			//编码前缀
	
	@EmiColumn(name = "curNumber")
	private Integer curNumber;			//当前数值
	
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

	public String getPrefixCode() {
		return prefixCode;
	}

	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}

	public Integer getCurNumber() {
		return curNumber;
	}

	public void setCurNumber(Integer curNumber) {
		this.curNumber = curNumber;
	}

}
