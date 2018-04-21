package com.emi.flow.form.dao;

import java.util.List;

import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.sys.core.bean.PageBean;

public interface FormDao {

	/**
	 * @category 得到单个表单
	 * 2015年1月15日 上午11:49:31 
	 * @author 朱晓陈
	 * @param formId
	 * @return
	 */
	public FLOW_Form findForm(String formId);

	/**
	 * @category 表单元素详情
	 * 2015年1月15日 下午4:42:17 
	 * @author 朱晓陈
	 * @param formId
	 * @param showHidden 
	 * @return
	 */
	public List<FLOW_FormDetail> getFormDetail(String formId);

	/**
	 * 根据表单id得到关联的数据表
	 * @category 表单关联的表
	 * 2015年1月16日 上午9:55:44 
	 * @author 朱晓陈
	 * @param formId
	 * @return
	 */
	public FLOW_Table getFormTable(String formId);

	/**
	 * @category 表单列表
	 * 2015年5月15日 下午3:14:27 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageBean getFormList(int pageIndex, int pageSize, String condition);

	public void insertForm(FLOW_Form form);

	public void updateForm(FLOW_Form form);

	public void deleteForm(String formId);

}
