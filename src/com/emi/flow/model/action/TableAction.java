package com.emi.flow.model.action;

import com.emi.common.action.BaseAction;
import com.emi.flow.model.service.TableService;

public class TableAction extends BaseAction{
	private TableService tableService;

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}
	
}
