package com.emi.flow.main.dao.impl;

import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.flow.main.bean.FLOW_CodeRuleDetail;
import com.emi.flow.main.bean.FLOW_CodeRuleValue;
import com.emi.flow.main.dao.CodeRuleDao;

@SuppressWarnings({"unchecked","rawtypes"})	
public class CodeRuleDaoImpl extends BaseDao implements CodeRuleDao {

	@Override
	public List<FLOW_CodeRuleDetail> getRuleDetails(String ruleId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_CodeRuleDetail.class)+" from FLOW_CodeRuleDetail "
				+ " where codeRuleId='"+ruleId+"' order by codeRuleIndex";
		return this.emiQueryList(sql, FLOW_CodeRuleDetail.class);
	}

	@Override
	public Integer getCurNumber(String prefixCode) {
		Integer curNumber = null;
		String sql = "select curNumber from FLOW_CodeRuleValue where prefixCode='"+prefixCode+"'";
		Map map = this.queryForMap(sql);
		if(map != null){
			curNumber = (Integer) map.get(curNumber);
		}
		return curNumber;
	}
	
	@Override
	public FLOW_CodeRuleValue getCurValue(String prefixCode) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_CodeRuleValue.class)+" from FLOW_CodeRuleValue where prefixCode='"+prefixCode+"'";
		return (FLOW_CodeRuleValue) this.emiQuery(sql, FLOW_CodeRuleValue.class);
	}

	@Override
	public boolean insertRuleValue(FLOW_CodeRuleValue rv) {
		return this.emiInsert(rv);
	}

	@Override
	public boolean updateRuleValue(FLOW_CodeRuleValue curValue) {
		return this.emiUpdate(curValue);
	}

	

}
