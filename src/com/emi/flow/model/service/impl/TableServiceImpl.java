package com.emi.flow.model.service.impl;

import com.emi.flow.model.dao.TableDao;
import com.emi.flow.model.service.TableService;

public class TableServiceImpl implements TableService {
	private TableDao tableDao;

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}
	
}
