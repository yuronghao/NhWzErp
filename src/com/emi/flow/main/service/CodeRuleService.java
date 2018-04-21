package com.emi.flow.main.service;

public interface CodeRuleService {

	/**
	 * 根据规则获取下一个编码，并更新库
	 * @category 获取下一个编码
	 * 2015年1月22日 上午9:33:31 
	 * @author 朱晓陈
	 * @param ruleId
	 * @return
	 */
	public String getNextRuleValue(String ruleId);

}
