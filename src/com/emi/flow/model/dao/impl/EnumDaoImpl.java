package com.emi.flow.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.flow.model.bean.FLOW_Enum;
import com.emi.flow.model.bean.FLOW_EnumDetail;
import com.emi.flow.model.dao.EnumDao;

@SuppressWarnings({"unchecked","rawtypes"})	
public class EnumDaoImpl extends BaseDao implements EnumDao {

	@Override
	public FLOW_Enum findEnum(String enumId) {
		return (FLOW_Enum) this.emiFind(enumId, FLOW_Enum.class);
	}

	@Override
	public List<FLOW_EnumDetail> getEnumDetails(String enumId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_EnumDetail.class)+" from FLOW_EnumDetail where enumId='"+enumId+"'";
		return this.emiQueryList(sql, FLOW_EnumDetail.class);
	}

	@Override
	public List<FLOW_EnumDetail> getDynamicData(String tableName,
			final String columnName, String conditionSql) {
		String sql = "select distinct "+columnName+" from "+tableName+" where 1=1 "+CommonUtil.null2Str(conditionSql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public FLOW_EnumDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				FLOW_EnumDetail ed = new FLOW_EnumDetail();
				ed.setEnumKey(rs.getString(columnName));
				ed.setEnumDisplay(rs.getString(columnName));
				return ed;
			}
			
		});
	}
	
}
