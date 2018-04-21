package com.emi.flow.form.dao.impl;

import java.util.List;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.form.dao.FormDao;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.sys.core.bean.PageBean;

@SuppressWarnings("unchecked")
public class FormDaoImpl extends BaseDao implements FormDao{

	@Override
	public FLOW_Form findForm(String formId) {
		return (FLOW_Form) this.emiFind(formId, FLOW_Form.class);
	}
	
	@Override
	public List<FLOW_FormDetail> getFormDetail(String formId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_FormDetail.class)+" from FLOW_FormDetail where formId='"+formId+"'";
		return this.emiQueryList(sql, FLOW_FormDetail.class);
	}

	@Override
	public FLOW_Table getFormTable(String formId) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_Table.class)+" from FLOW_Table"
				+ " where gid=(select tableId from FLOW_Form where gid='"+formId+"')";
		return (FLOW_Table) this.emiQuery(sql, FLOW_Table.class);
	}

	@Override
	public PageBean getFormList(int pageIndex, int pageSize, String condition) {
		String sql = "select "+CommonUtil.colsFromBean(FLOW_Form.class)+" from FLOW_Form where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}

	@Override
	public void insertForm(FLOW_Form form) {
		this.emiInsert(form);
	}

	@Override
	public void updateForm(FLOW_Form form) {
		this.emiUpdate(form);
	}

	@Override
	public void deleteForm(String formId) {
		String sql = "delete from FLOW_Form where gid='"+formId+"'";
		this.update(sql);
	}

}
