package com.emi.flow.form.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.util.CommonUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.form.dao.FormDao;
import com.emi.flow.form.service.FormService;
import com.emi.flow.main.util.ElementType;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.sys.core.bean.PageBean;

public class FormServiceImpl implements FormService {
	private FormDao formDao;

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	@Override
	public FLOW_Form findForm(String formId) {
		return formDao.findForm(formId);
	}

	@Override
	public List<FLOW_FormDetail> getFormDetail(String formId) {
		return formDao.getFormDetail(formId);
	}
	
	@Override
	public FLOW_Table getFormTable(String formId) {
		return formDao.getFormTable(formId);
	}

	@Override
	public PageBean getFormList(int pageIndex, int pageSize, String condition) {
		return formDao.getFormList(pageIndex, pageSize, condition);
	}

	@Override
	public void saveForm(FLOW_Form form) {
		if(CommonUtil.isNullString(form.getGid())){
			//增加
			form.setCreateTime(new Timestamp(System.currentTimeMillis()));
			formDao.insertForm(form);
		}else{
			//更新
			form.setCreateTime(new Timestamp(System.currentTimeMillis()));
			formDao.updateForm(form);
		}
		
	}

	@Override
	public void deleteForm(String formId) {
		formDao.deleteForm(formId);
	}

}
