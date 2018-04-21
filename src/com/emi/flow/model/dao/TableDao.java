package com.emi.flow.model.dao;

import com.emi.flow.model.bean.FLOW_Table;

public interface TableDao {

	/**
	 * @category 获得表
	 * 2015年1月20日 上午11:01:35 
	 * @author 朱晓陈
	 * @param tableId
	 * @return
	 */
	public FLOW_Table findTable(String tableId);

}
