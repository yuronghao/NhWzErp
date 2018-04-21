package com.emi.flow.model.service;

import java.util.List;

import com.emi.flow.model.bean.FLOW_EnumDetail;

public interface EnumService {

	/**
	 * @category 枚举值
	 * 2015年1月21日 上午9:12:05 
	 * @author 朱晓陈
	 * @param enumId
	 * @return
	 */
	public List<FLOW_EnumDetail> getEnumValues(String enumId);

}
