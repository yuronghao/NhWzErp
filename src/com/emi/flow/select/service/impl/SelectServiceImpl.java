package com.emi.flow.select.service.impl;

import com.emi.flow.select.dao.SelectDao;
import com.emi.flow.select.service.SelectService;

public class SelectServiceImpl implements SelectService{
	private SelectDao selectDao;

	public void setSelectDao(SelectDao selectDao) {
		this.selectDao = selectDao;
	}
	
}
