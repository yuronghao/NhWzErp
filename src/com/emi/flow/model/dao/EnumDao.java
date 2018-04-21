package com.emi.flow.model.dao;

import java.util.List;

import com.emi.flow.model.bean.FLOW_Enum;
import com.emi.flow.model.bean.FLOW_EnumDetail;

public interface EnumDao {

	/**
	 * @category 获取枚举表主表信息
	 * 2015年1月21日 上午9:17:05 
	 * @author 朱晓陈
	 * @param enumId
	 * @return
	 */
	public FLOW_Enum findEnum(String enumId);

	/**
	 * @category 自定义的枚举数据
	 * 2015年1月21日 上午9:21:57 
	 * @author 朱晓陈
	 * @param enumId
	 * @return
	 */
	public List<FLOW_EnumDetail> getEnumDetails(String enumId);

	/**
	 * @category 数据表读取的动态数据
	 * 2015年1月22日 下午5:25:13 
	 * @author 朱晓陈
	 * @param tableName
	 * @param columnName
	 * @param conditionSql
	 * @return
	 */
	public List<FLOW_EnumDetail> getDynamicData(String tableName,
			String columnName, String conditionSql);

}
