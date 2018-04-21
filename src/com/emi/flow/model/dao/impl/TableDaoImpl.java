package com.emi.flow.model.dao.impl;

import com.emi.common.dao.BaseDao;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.flow.model.dao.TableDao;

public class TableDaoImpl extends BaseDao implements TableDao {

	@Override
	public FLOW_Table findTable(String tableId) {
		return (FLOW_Table) this.emiFind(tableId, FLOW_Table.class);
	}

}
