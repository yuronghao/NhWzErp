package com.emi.flow.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.emi.common.dao.BaseDao;
import com.emi.flow.model.bean.FLOW_Enum;
import com.emi.flow.model.bean.FLOW_EnumDetail;
import com.emi.flow.model.dao.EnumDao;
import com.emi.flow.model.service.EnumService;

public class EnumServiceImpl implements EnumService {
	private EnumDao enumDao;

	public void setEnumDao(EnumDao enumDao) {
		this.enumDao = enumDao;
	}

	@Override
	public List<FLOW_EnumDetail> getEnumValues(String enumId) {
		List<FLOW_EnumDetail> enums = new ArrayList<FLOW_EnumDetail>();
		FLOW_Enum e = enumDao.findEnum(enumId);
		if(e.getEnumType().compareTo(1)==0){
			//数据库读取
			enums = enumDao.getDynamicData(e.getTableName(),e.getColumnName(),e.getConditionSql());
		}else if(e.getEnumType().compareTo(0)==0){
			//自定义值
			enums = enumDao.getEnumDetails(enumId);
		}
		
		return enums;
	}
	
}
