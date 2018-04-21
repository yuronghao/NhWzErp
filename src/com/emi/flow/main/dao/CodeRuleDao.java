package com.emi.flow.main.dao;

import java.util.List;

import com.emi.flow.main.bean.FLOW_CodeRuleDetail;
import com.emi.flow.main.bean.FLOW_CodeRuleValue;

public interface CodeRuleDao {

	/**
	 * @category 规则详情
	 * 2015年1月22日 上午9:35:34 
	 * @author 朱晓陈
	 * @param ruleId
	 * @return
	 */
	public List<FLOW_CodeRuleDetail> getRuleDetails(String ruleId);

	/**
	 * @category 当前值
	 * 2015年1月22日 上午10:46:48 
	 * @author 朱晓陈
	 * @param prefixCode
	 * @return
	 */
	public Integer getCurNumber(String prefixCode);

	/**
	 * @category 
	 * 2015年1月22日 上午11:04:56 
	 * @author 朱晓陈
	 * @param rv
	 */
	public boolean insertRuleValue(FLOW_CodeRuleValue rv);

	/**
	 * @category 当前值
	 * 2015年1月22日 上午11:04:56 
	 * @author 朱晓陈
	 * @param rv
	 */
	public FLOW_CodeRuleValue getCurValue(String prefixCode);

	/**
	 * @category 更新当前值
	 * 2015年1月22日 上午11:14:20 
	 * @author 朱晓陈
	 * @param curValue
	 * @return
	 */
	public boolean updateRuleValue(FLOW_CodeRuleValue curValue);

}
