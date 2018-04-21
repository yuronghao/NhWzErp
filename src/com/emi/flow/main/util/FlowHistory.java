package com.emi.flow.main.util;

public class FlowHistory {
	/**
	 * 状态-办理中
	 */
	public static final int STATUS_UNDERWAY = 0;
	
	/**
	 * 状态-已办结
	 */
	public static final int STATUS_DONE = 1;	
	
	/**
	 * 状态-已作废
	 */
	public static final int STATUS_DELETE = 2;
	
	
	/**
	 * 审批完成-通过
	 */
	public static final int COMPLETE_PASS = 1;
	
	/**
	 * 审批完成-驳回
	 */
	public static final int COMPLETE_REJECT = 2;
}
