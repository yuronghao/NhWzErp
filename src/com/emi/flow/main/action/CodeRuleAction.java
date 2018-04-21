package com.emi.flow.main.action;

import java.io.IOException;

import com.emi.common.action.BaseAction;
import com.emi.flow.main.service.CodeRuleService;

public class CodeRuleAction extends BaseAction {
	private static final long serialVersionUID = -6262231683530783393L;
	private CodeRuleService codeRuleService;

	public void setCodeRuleService(CodeRuleService codeRuleService) {
		this.codeRuleService = codeRuleService;
	}
	
	/**
	 * @category 获取编码规则
	 * 2015年1月22日 上午9:13:40 
	 * @author 朱晓陈
	 */
	public void getRuleValue(){
		try {
			String ruleId = this.getParameter("ruleId");
			//获取下一个编码
			String nextValue = codeRuleService.getNextRuleValue(ruleId);
			
			getResponse().getWriter().write(nextValue);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				getResponse().getWriter().write("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
