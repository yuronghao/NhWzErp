package com.emi.flow.form.service;

import java.util.List;
import java.util.Map;

import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.model.bean.FLOW_Table;
import com.emi.sys.core.bean.PageBean;

public interface FormService {

	/**
	 * @category 查出单个表单
	 * 2015年1月15日 上午11:48:37 
	 * @author 朱晓陈
	 * @param formId
	 * @return
	 */
	public FLOW_Form findForm(String formId);

	/**
	 * 页面上表单元素的属性详细
	 * @category 表单元素详情
	 * 2015年1月15日 下午4:39:24 
	 * @author 朱晓陈
	 * @param formId
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
	 * 2015年5月15日 下午3:11:18 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageBean getFormList(int pageIndex, int pageSize, String condition);

	/**
	 * @category 保存表单
	 * 2015年5月15日 下午3:20:55 
	 * @author 朱晓陈
	 * @param form
	 */
	public void saveForm(FLOW_Form form);

	/**
	 * @category 删除表单
	 * 2015年5月23日 下午5:02:00 
	 * @author 朱晓陈
	 * @param formId
	 */
	public void deleteForm(String formId);

}
